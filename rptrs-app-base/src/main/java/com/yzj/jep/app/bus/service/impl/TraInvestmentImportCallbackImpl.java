package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.service.BaseService;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;


/**
 * 内部交易管理导入实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = TraInvestmentImportCallbackImpl.BEANID)
public class TraInvestmentImportCallbackImpl implements XlsImportCallback {

    public static final String BEANID = "TraInvestmentImportCallbackImpl";

    //配置导入的模板（XML）文件
    private static final String XML_TEMP_FILE = "TraInvestmentImportMapper.xml";
    private static final String IMPORT_DATAS = "traInvestments";

    @Autowired
    TraInvestmentService traInvestmentService;
    @Autowired
    BaseService baseService;

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
        XlsMessage res = null;
        int index = 0;
        String falNum = "";
        String resMsg = "";
        @SuppressWarnings("unchecked")
        List<TraInvestment> list = (List<TraInvestment>) beans.get(IMPORT_DATAS);
        Map result = new HashMap();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                TraInvestment tra = list.get(i);
//                BigDecimal db = new BigDecimal(tra.getRelNumber());//科学计算法转换
                try {
                    result = baseService.getRelInfo(tra.getRelNumber(), tra.getRelName());
                    boolean flag = (boolean) result.get("success");
                    if (!flag) {
                        falNum += i + 1 + ",";
                        resMsg += "第" + (i + 1) + "条此关联方不存在,";
                        continue;
                    }
                    tra.setRelId((Integer) result.get("relId"));
                    tra.setRelType((String) result.get("relType"));
                    tra.setCaliber((String) result.get("caliber"));
                    tra.setMajorTrade("1");
                    tra.setTotolTradeAmount((BigDecimal) result.get("totalTradeAmount"));
                    UserProfile profile = UserProfileUtils.getUserProfile();
                    tra.setTradeOrg(profile.getUnitCode());
                    tra.setTranType("01");
                    tra.setDataSources("1");
                    tra.setDelflag("1");
                    tra.setStatus("3");
                    tra.setModifiedTime(new Date());

                    List<Filter> filters = new Filter.Builder().eq("contractNo", tra.getContractNo()).build();
                    List<TraInvestment> traInvestments = traInvestmentService.select(filters);
                    if (null != traInvestments && traInvestments.size() > 0) {
                        tra.setPid(traInvestments.get(0).getPid());
                        traInvestmentService.updateSelective(tra);
                    } else {
                        //设置交易号
                        tra.setTransNum(BatchNumberUtil.makeBatchNumber());
                        traInvestmentService.insert(tra);
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
        @SuppressWarnings("unchecked")
        List<TraInvestment> list = (List<TraInvestment>) mapReaded.get(IMPORT_DATAS);
        if (list != null && list.size() > 0) {
            for (int i = 1; i <= list.size(); i++) {
                TraInvestment tra = list.get(i - 1);
                String resBasic = checkBasicInfo(tra);
                if (!"".equals(resBasic)) {
                    result = "检测到第" + (i) + "条数据问题含有：" + resBasic;
                    return result;
                }
            }
        } else {
            result = "导入数据为空。";
        }
        return result;
    }

    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXLS) throws ServiceException {
        //对导入的数据做特殊处理，如不处理则直接返回beans

        return beans;
    }

    public String checkBasicInfo(TraInvestment traInvestment) {
        String res = "";
        if (StringUtils.isEmpty(traInvestment.getRelName())) {
            return "";
        }
        if (StringUtils.isEmpty(traInvestment.getRelNumber())) {
            res += "关联方证件号码为空";
        }
/*        if (traInvestment.getTranDate() == null) {
            res += "交易日期为空";
        }*/
        if (StringUtils.isEmpty(traInvestment.getContractNo())) {
            res += "合同编号为空";
        }
        if (traInvestment.getTradeAmount() == null) {
            res += "交易金额为空";
        }
        if (traInvestment.getBusStartTimeString() == null) {
            res += "业务起始时间为空";
        }
        if (StringUtils.isEmpty(traInvestment.getInvestmentType())) {
            res += "投资方式为空";
        }
/*        if (traInvestment.getInTime() == null) {
            res += "入股时间为空";
        }*/
        if (StringUtils.isEmpty(traInvestment.getShareNum())) {
            res += "股份数为空";
        }
/*        if (StringUtils.isEmpty(traInvestment.getShareRetio())) {
            res += "股份占比为空";
        }*/
        if (StringUtils.isEmpty(traInvestment.getTradePrice())) {
            res += "交易价格为空";
        }
        if (StringUtils.isEmpty(traInvestment.getPricingPolicy())) {
            res += "定价政策为空";
        }
        if (StringUtils.isEmpty(traInvestment.getTradeSubject())) {
            res += "交易标的为空";
        }
        return res;
    }
}
