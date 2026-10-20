package com.yzj.jep.app.ins.service.impl;

import com.yzj.jep.app.ins.model.TraCounterparty;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.model.TraInvestmentVo;
import com.yzj.jep.app.ins.service.BaseService;
import com.yzj.jep.app.ins.service.TraCounterpartyService;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;


/**
 * 投资入股类关联交易导入
 *
 */
@Service(value = InsTraInvestmentImportCallbackImpl.BEANID)
public class InsTraInvestmentImportCallbackImpl implements XlsImportCallback {

    public static final String BEANID = "InsTraInvestmentImportCallbackImpl";

    //配置导入的模板（XML）文件
    private static final String XML_TEMP_FILE = "TraInvestmentImportMapper.xml";
    private static final String IMPORT_DATAS = "traInvestments";

    @Autowired
    TraInvestmentService traInvestmentService;
    @Autowired
    BaseService baseService;
    @Autowired
    DictItemService dictItemService;
    @Override
    public String getImportXmlTemplate() {
        return XLSTEMPLATE_PATH + XML_TEMP_FILE;
    }

    @Override
    public void setXlsReaderBeans(Map beans) {
        //一般而言，有几个sheet页，就有几个元素
        beans.put(IMPORT_DATAS, new ArrayList());
    }

    @Override
    public XlsMessage executeSaveCallback(Map parameters, Map beans) throws ServiceException {
        TraCounterpartyService traCounterpartyService = SpringContextHolder.getBean(TraCounterpartyService.class);
        XlsMessage res = null;
        int index = 0;
        String falNum = "";
        String resMsg = "";
        @SuppressWarnings("unchecked")
        List<TraInvestmentVo> list = (List<TraInvestmentVo>) beans.get(IMPORT_DATAS);

        //业务类型 INT-BusinessType
        List<Filter> filter1 = new Filter.Builder().eq("dictCode", "INS-InvestmentType").build();
        List<DictItem> dictItemList1 = dictItemService.select(filter1);
        Map busTypeMap = new HashMap();
        for (DictItem dic : dictItemList1) {
            busTypeMap.put(dic.getItemLabel(), dic.getItemValue());
        }

        Map result = new HashMap();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                TraInvestmentVo tra = list.get(i);

                try {
                    result = baseService.getRelInfo(tra.getRelNumber(), tra.getRelName());
                    boolean flag = (boolean) result.get("success");
                    if (!flag) {
                        falNum += i + 1 + ",";
                        resMsg += "第" + (i + 1) + "条此关联方不存在,";
                        continue;
                    }
                    TraInvestment tri = new TraInvestment();
                    BeanUtils.copyProperties(tra,tri);
                    tri.setRelId((Integer) result.get("relId"));
                    tri.setRelType((String) result.get("relType"));
                    tri.setCaliber((String) result.get("caliber"));
                    tri.setMajorTrade("0");
                    tri.setTotolTradeAmount((BigDecimal) result.get("totalTradeAmount"));
                    UserProfile profile = UserProfileUtils.getUserProfile();
                    tri.setTradeOrg(profile.getUnitCode());
                    tri.setTranType("01");
                    tri.setDataSources("1");
                    tri.setDelflag("1");
                    tri.setStatus("3");
                    tri.setModifiedTime(new Date());

                    List<Filter> filters = new Filter.Builder().eq("contractNo", tri.getContractNo()).build();
                    List<TraInvestment> traInvestments = traInvestmentService.select(filters);
                    if (null != traInvestments && traInvestments.size() > 0) {
                        tri.setPid(traInvestments.get(0).getPid());
                        traInvestmentService.updateSelective(tri);
                        Boolean counterpartyBoolean = traCounterpartyService.updataCounterpartyByTransNum(tri.getTransNum(), tri.getStatus(), tri.getTranDate(), tri.getRelTradeAmount().toString(), null);
                    } else {
                        //设置交易号
                        tri.setTransNum(BatchNumberUtil.makeBatchNumber());
                        traInvestmentService.insert(tri);
                        TraCounterparty traCounterparty = new TraCounterparty();
                        BeanUtils.copyProperties(tri, traCounterparty);
                        int inserttraCounterparty = traCounterpartyService.insertSelective(traCounterparty);
                    }
                } catch (Exception e) {
                    index++;
                    falNum += i + 1 + ",";
                }

            }
            res = new XlsMessage();
            //int size = list.size() - 1 - index;
            res.setMessage("成功导入" + index + "条信息");
        } else {
            //导入失败时，添加提示信息
            res = new XlsMessage();
            res.setMessage("导入失败：");
            res.setException("导入失败，请检查导入数据。");
        }
        return res;
    }

    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        //如果通过检查，就保持result为null，否则给出返回错误描述
        String result = null;
//        @SuppressWarnings("unchecked")
//        List<TraInvestmentVo> list = (List<TraInvestmentVo>) mapReaded.get(IMPORT_DATAS);
//        if (list != null && list.size() > 0) {
//            for (int i = 1; i <= list.size(); i++) {
//                TraInvestmentVo tra = list.get(i - 1);
//                //因为有一条空数据
//                if(StringUtils.isEmpty(tra.getRelType())){
//                    break;
//                }
//                String resBasic = checkBasicInfo(tra);
//                if (!"".equals(resBasic)) {
//                    result = "检测到第" + (i) + "条数据问题含有：" + resBasic;
//                    return result;
//                }
//            }
//        } else {
//            result = "导入文件模板不匹配或者导入数据为空。";
//        }
        return result;
    }

    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXLS) throws ServiceException {
        //对导入的数据做特殊处理，如不处理则直接返回beans

        return beans;
    }

    public String checkBasicInfo(TraInvestmentVo traInvestment) {
        String res = "";
        if (StringUtils.isEmpty(traInvestment.getRelType())) {
            return "关联方类别不能为空！";
        }
        if (StringUtils.isEmpty(traInvestment.getRelName())) {
            return "关联方名称不能为空！";
        }
        if (StringUtils.isEmpty(traInvestment.getRelNumber())) {
            res += "关联方证件号码不能为空！";
        }
        if (traInvestment.getInvestmentType() == null) {
            res += "业务类型不能为空！";
        }
        if (traInvestment.getTranDateString() == null) {
            res += "合同签订日期不能为空！";
        }
        if (StringUtils.isEmpty(traInvestment.getContractNo())) {
            res += "合同编号不能为空！";
        }
        if (traInvestment.getTradeAmount() == null) {
            res += "交易金额不能为空！";
        }
/*        if (traInvestment.getBusStartTimeString() == null) {
            res += "业务起始时间为空";
        }*/
        if (StringUtils.isEmpty(traInvestment.getInvestmentType())) {
            res += "投资方式不能为空！";
        }
/*        if (traInvestment.getInTime() == null) {
            res += "入股时间为空";
        }*/
        if(Dict.INS_INVESTMENT_TYPE_04.equals(traInvestment.getInvestmentType())){
           if(traInvestment.getSecuritiesType()==null){
               res += "当业务类型为04保险公司发行的优先股、债券、或其他证券时，证券类型不能为空！";
           }
        }
        if(traInvestment.getVariableshareratio()==null){
            res += "投资入股类，变动股比或份额占比不能为空！";
        }

        if (StringUtils.isEmpty(traInvestment.getShareNum())) {
            res += "股份数不能为空！";
        }
        if(traInvestment.getVariableshareratioAfter()==null){
            res += "变动后股比或份额占比不能为空！";
        }
/*        if (StringUtils.isEmpty(traInvestment.getShareRetio())) {
            res += "股份占比为空";
        }*/
        if (StringUtils.isEmpty(traInvestment.getEffectiveTimeString())) {
            res += "生效时间不能为空！";
        }
        if (StringUtils.isEmpty(traInvestment.getIsHoldingSubsidiary())) {
            res += "是否控股子公司不能为空！";
        }
        if(Dict.SF_1.equals(traInvestment.getIsHoldingSubsidiary())){
            if (StringUtils.isEmpty(traInvestment.getHoldingSubsidiaryName())) {
                res += "控股子公司名字不能为空！";
            }
            if (StringUtils.isEmpty(traInvestment.getHoldingSubsidiaryNumber())) {
                res += "控股子公司证件号不能为空！";
            }
        }
        if (StringUtils.isEmpty(traInvestment.getTradePrice())) {
            res += "交易价格不能为空！";
        }
        if (StringUtils.isEmpty(traInvestment.getYield_())) {
            res += "收益率不能为空！";
        }
        if (StringUtils.isEmpty(traInvestment.getPricingPolicy())) {
            res += "定价政策不能为空！";
        }
        if (StringUtils.isEmpty(traInvestment.getTradeSubject())) {
            res += "交易标的不能为空！";
        }
        if (StringUtils.isEmpty(traInvestment.getIsCashSubscript())) {
            res += "是否现金认购标识不能为空！";
        }
        if (StringUtils.isEmpty(traInvestment.getFinancialImpact())) {
            res += "对财务状况的影响不能为空！";
        }
        if (StringUtils.isEmpty(traInvestment.getIsDealPrice())) {
            res += "是否为国家定价标识不能为空！";
        }
        return res;
    }
}
