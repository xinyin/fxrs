package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.TraCredit;
import com.yzj.jep.app.bus.model.TraUsecredit;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.TraCreditService;
import com.yzj.jep.app.bus.service.TraUsecreditService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 内部授信类导入实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = TraUsecreditImportCallbackImpl.BEANID)
public class TraUsecreditImportCallbackImpl implements XlsImportCallback {

    @Autowired
    DictItemService dictItemService;
    @Autowired
    RelPsnService relPsnService;
    @Autowired
    RelOrgService relOrgService;
    @Autowired
    TraCreditService traCreditService;
    @Autowired
    TraUsecreditService traUsecreditService;


    public static final String BEANID = "TraUsecreditImportCallbackImpl";

    //配置导入的模板（XML）文件
    private static final String XML_TEMP_FILE = "BusTraUsecreditImportMapper.xml";
    private static final String IMPORT_DATAS = "traUseCzs";

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
        XlsMessage res = new XlsMessage();
        int num = 0;

        @SuppressWarnings("unchecked")
        List<TraUsecredit> list = (List<TraUsecredit>) beans.get(IMPORT_DATAS);
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        if (list != null && list.size() > 0) {

            //业务类型 BUS-UseCredit
            List<Filter> filter2 = new Filter.Builder().eq("dictCode", "BUS-UseCredit").build();
            List<DictItem> dictItemList2 = dictItemService.select(filter2);
            Map busUseCredit = new HashMap();
            for (DictItem dic : dictItemList2) {
                //busUseCredit.put(dic.getItemValue(), dic.getItemLabel());
                busUseCredit.put(dic.getItemLabel(), dic.getItemValue());
            }
            //交易价格类型 BUS-traPrice
            List<Filter> filter4 = new Filter.Builder().eq("dictCode", "BUS-traPrice").build();
            List<DictItem> dictItemList4 = dictItemService.select(filter4);
            Map busTraPrice = new HashMap();
            for (DictItem dic : dictItemList4) {
                //busTraPrice.put(dic.getItemValue(), dic.getItemLabel());
                busTraPrice.put(dic.getItemLabel(), dic.getItemValue());
            }

            //利率类型 BUS-rateType
            List<Filter> filter1 = new Filter.Builder().eq("dictCode", "BUS-rateType").build();
            List<DictItem> dictItemList1 = dictItemService.select(filter1);
            Map rateTypeMap = new HashMap();
            for (DictItem dic : dictItemList1) {
                //busTraPrice.put(dic.getItemValue(), dic.getItemLabel());
                busTraPrice.put(dic.getItemLabel(), dic.getItemValue());
            }

            //资金投向 BUS-Capitalinvestment
            List<Filter> filter3 = new Filter.Builder().eq("dictCode", "BUS-Capitalinvestment").build();
            List<DictItem> dictItemList3 = dictItemService.select(filter3);
            Map capitalinvestmentMap = new HashMap();
            for (DictItem dic : dictItemList3) {
                //busTraPrice.put(dic.getItemValue(), dic.getItemLabel());
                capitalinvestmentMap.put(dic.getItemLabel(), dic.getItemValue());
            }

            for (TraUsecredit traUseC : list) {

                //用信导入数据需要依据(银行内部授信编号)授信表中有效数据
                if (null == traUseC.getInternalApplyNo()) {
                    continue;
                }
                List<Filter> filters = new Filter.Builder()
                        .eq("internalApplyNo", traUseC.getInternalApplyNo()).build();
                TraCredit traCredit = traCreditService.get(filters);
                if (null != traCredit) {
                    traUseC.setRelId(traCredit.getRelId());
                    traUseC.setRelType(traCredit.getRelType());
                    traUseC.setInternalApplyNo(traCredit.getInternalApplyNo());
                    traUseC.setIdentifyType(traCredit.getIdentifyType());
                    traUseC.setIdentNumber(traCredit.getIdentNumber());
                } else {
                    //授信中为查询到对应数据时，结束当前循环
                    continue;
                }

                //业务-同一个银行内部审批编号下可以有多个用信编号---多字段判断数据的唯一
                List<Filter> traFilters = new Filter.Builder()
                        .eq("internalApplyNo", traUseC.getInternalApplyNo())
                        .eq("useCreditNum", traUseC.getUseCreditNum()).build();
                TraUsecredit traUsecredit = traUsecreditService.get(traFilters);

                traUseC.setBusinessType((String) busUseCredit.get(traUseC.getBusinessType()));
                traUseC.setTraPriceType((String) busTraPrice.get(traUseC.getTraPriceType()));
                traUseC.setRateType((String) rateTypeMap.get(traUseC.getRateType()));
                traUseC.setCapitalInvestment((String) capitalinvestmentMap.get(traUseC.getCapitalInvestment()));

                traUseC.setBusinessStartTime(dateStr(traUseC.getBusinessStartTime()));
                traUseC.setBusinessEndTime(dateStr(traUseC.getBusinessEndTime()));
                traUseC.setContractSignDate(dateStr(traUseC.getContractSignDate()));

                traUseC.setManageOrg(userProfile.getUnitCode());
                traUseC.setDataSources("1");
                traUseC.setDelflag("1");
                traUseC.setStatus("3");
                if (null != traUsecredit) {
                    traUseC.setPid(traUsecredit.getPid());
                    traUsecreditService.updateSelective(traUseC);

                } else {
                    traUseC.setUseCreditNum(traUseC.getUseCreditNum());
                    traUsecreditService.insert(traUseC);
                }

                num++;
            }
            res.setMessage("成功导入" + num);
        } else {
            //导入失败时，添加提示信息
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
        List<TraUsecredit> list = (List<TraUsecredit>) mapReaded.get(IMPORT_DATAS);
        //检查导入文件中是否有数据
        if (list != null && list.size() > 0) {
            //TODO 执行导入前的检查，如xxx编码是否存在，xxx引用的编码不存在等

            for (TraUsecredit traUsecredit : list) {
                if (null == traUsecredit.getInternalApplyNo()) {
                    continue;
                }
                //验证文件中是否存在证件号为空的数据
                if (null == traUsecredit.getInternalApplyNo()) {
                    result = "导入数据中存在银行授信编号为空";
                    continue;
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
        @SuppressWarnings("unchecked")
        List<TraUsecredit> list = (List<TraUsecredit>) beans.get(IMPORT_DATAS);

        if (CollectionUtils.isNotEmpty(list)) {
            for (TraUsecredit traUsecredit : list) {
                if (StringUtils.isEmpty(traUsecredit.getRelatedName())) {
                    //TODO 演示对从excel读取后的数据在做导入处理前做业务处理
                    System.out.println("演示，在这里对：" + traUsecredit.getInternalApplyNo() + "做进一步处理。");
                }
            }
        }
        return beans;
    }

    /*
     * 判断是否重大关联
     * */
    /*public boolean judgeIsImportTrade(TraCredit traCredit, String relType, Integer relId, Integer pid) {
        BigDecimal diff = null;
        BigDecimal creditAmount = BigDecimal.valueOf(traCredit.getCreditAmount());//授信金额
        if (pid != null) {
            TraCredit credit = traCreditService.get(pid);
            if ("1".equals(credit.getIsEffective())) {//是否生效
                Long creditAmount1 = credit.getCreditAmount();
                diff = creditAmount.subtract(BigDecimal.valueOf(creditAmount1));
            }
        }
        if (TraUtil.isImportantTrade(creditAmount, relId, relType, diff)) {
            traCredit.setImportantTrade("1");
            traCreditService.update(traCredit);
        } else {
            traCredit.setImportantTrade("0");
            traCreditService.update(traCredit);
        }

        return TraUtil.isImportantTrade(creditAmount, relId, relType, diff);
    }
*/
    private Date strDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (str != null && !"".equals(str.trim())) {
            return HSSFDateUtil.getJavaDate(Double.parseDouble(str));
        }
        return null;
    }

    private String dateStr(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (str != null && !"".equals(str.trim())) {
            return sdf.format(HSSFDateUtil.getJavaDate(Double.parseDouble(str)));
        }
        return null;
    }
}
