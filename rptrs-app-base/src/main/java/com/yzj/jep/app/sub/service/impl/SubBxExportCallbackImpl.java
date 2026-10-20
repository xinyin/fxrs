package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.model.TraFundsVo;
import com.yzj.jep.app.sub.model.MidBxywhqtlgljyxyxxb;
import com.yzj.jep.app.sub.service.MidBxywhqtlgljyxyxxbService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = SubBxExportCallbackImpl.BEANID)
public class SubBxExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "SubBxExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "SubBxExportTemplate.xls";

    private static final String DICTCODE = "dictCode";

    @Autowired
    private MidBxywhqtlgljyxyxxbService midBxywhqtlgljyxyxxbService;

    @Autowired
    private UnitService unitService;

    @Autowired
    DictItemService dictItemService;
    @Autowired
    ProductService productService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "保险业务类交易信息_" + sf.format(new Date()) + ".xls";
        return fileName;
    }

    @Override
    public Map<Integer, List<int[]>> getMergeColumns() {
        return null;
    }

    @Override
    public Map<Integer, Integer> getMergeStartRowNum() {
        return null;
    }

    /*
     * 编写业务代码
     * */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        String dictCode = DICTCODE;
        Map resultMap = new HashMap();
        Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
        List<TraFundsVo> traFundsVos = new ArrayList<>();
        //参数
        List<Filter> filters = builderFilter(parameters);
        List<MidBxywhqtlgljyxyxxb> select = midBxywhqtlgljyxyxxbService.select(filters);

        if (select == null || select.size() == 0) {
            resultMap.put("east", select);
            return resultMap;
        }
//        for (MidBxywhqtlgljyxyxxb2 traFunds : select) {
//
//        }
        resultMap.put("east",select);
        return resultMap;
    }

    public List<Filter> builderFilter(Map parameters) {
        String data = (String) parameters.get("id");
        List<Filter> filters = new ArrayList<>();
        String[] ids = data.split(",");
        String type = ids[0];

        filters = new Filter.Builder().eq("delflag", "1").build();
        if (!"%".equals(type.trim())) {
            filters.add(new Filter("batchno", type));
        }
        return filters;
    }

    public void builderPsnBasicInfo(TraFundsVo traFundsVo, TraFunds traFunds, Map<String, Map<String, String>> dictCodeMap) {
        traFundsVo.setTransNum(traFunds.getTransNum());
        traFundsVo.setRelName(traFunds.getRelName());
        traFundsVo.setRelType(dictCodeMap.get("relTypeMap").get(traFunds.getRelType()));
        traFundsVo.setRelNumber(traFunds.getRelNumber());
        traFundsVo.setIsHoldingSubsidiary(dictCodeMap.get("isNoMap").get(traFunds.getIsHoldingSubsidiary()));
        traFundsVo.setHoldingSubsidiaryName(traFunds.getHoldingSubsidiaryName());
        traFundsVo.setHoldingSubsidiaryNumber(traFunds.getHoldingSubsidiaryNumber());
        traFundsVo.setBusType(dictCodeMap.get("otherBusTypeMap").get(traFunds.getBusType()));
        traFundsVo.setContractNo(traFunds.getContractNo());
        traFundsVo.setTranDateString(traFunds.getTranDateString());
        traFundsVo.setBusStartTimeString(traFunds.getBusStartTimeString());
        traFundsVo.setBusEndTimeString(traFunds.getBusEndTimeString());
        /*traFundsVo.setMajorType(dictCodeMap.get("majorTypeMap").get(traFunds.getMajorType()));*/
        traFundsVo.setTotolTradeAmount(String.valueOf(traFunds.getAccuPracTradeAmount()));
        traFundsVo.setTradeAmount(String.valueOf(traFunds.getTradeAmount()));
        traFundsVo.setIsDealPrice(dictCodeMap.get("isNoMap").get(traFunds.getIsDealPrice()));
        traFundsVo.setInvestmentType(dictCodeMap.get("fundsTypeMap").get(traFunds.getInvestmentType()));
        traFundsVo.setTradePrice(traFunds.getTradePrice());
        traFundsVo.setPricingPolicy(traFunds.getPricingPolicy());
        traFundsVo.setTradeSubject(traFunds.getTradeSubject());
        traFundsVo.setFinancialImpact(traFunds.getFinancialImpact());
        traFundsVo.setInvestedEnterprises(traFunds.getInvestedEnterprises());
        traFundsVo.setImLastTradePrice(traFunds.getImLastTradePrice());
        traFundsVo.setImSubjectMatter(traFunds.getImSubjectMatter());
        traFundsVo.setInvestmentMethod(dictCodeMap.get("fundsInsMethodMap").get(traFunds.getInvestmentMethod()));
        traFundsVo.setShareDebtRatio(traFunds.getShareDebtRatio());
        traFundsVo.setFinancialAssstsName(traFunds.getFinancialAssstsName());
        traFundsVo.setFinancialAssstsNum(traFunds.getFinancialAssstsNum());
        traFundsVo.setFinancialProScale(traFunds.getFinancialProScale());
        traFundsVo.setFinancialProRatio(traFunds.getFinancialProRatio());
        traFundsVo.setUnderIsRel(dictCodeMap.get("isNoMap").get(traFunds.getUnderIsRel()));
        traFundsVo.setUnderInvolveRel(dictCodeMap.get("isNoMap").get(traFunds.getUnderInvolveRel()));
        traFundsVo.setUnderlyingAsset(traFunds.getUnderlyingAsset());
        traFundsVo.setUnderRel(traFunds.getUnderRel());
        traFundsVo.setInvestedEnterprisesName(traFunds.getInvestedEnterprisesName());
        traFundsVo.setBusModel(traFunds.getBusModel());
        traFundsVo.setEarningsExpectaions(traFunds.getEarningsExpectaions());
        traFundsVo.setInvestmentRatio(traFunds.getInvestmentRatio());
        traFundsVo.setInvestmentAllocation(traFunds.getInvestmentAllocation());
        traFundsVo.setLossShare(traFunds.getLossShare());
        traFundsVo.setIsOverseasinvestment(dictCodeMap.get("isOverseasinvestmentMap").get(traFunds.getIsOverseasinvestment()));
        traFundsVo.setTotalAssets(traFunds.getTotalAssets());
        traFundsVo.setTranType(dictCodeMap.get("tranTypeMap").get(traFunds.getTranType()));
        traFundsVo.setBusType(dictCodeMap.get("busTypeMap").get(traFunds.getBusType()));
        traFundsVo.setTranName(traFunds.getTranName());
        traFundsVo.setRelTradeAmountString(traFunds.getRelTradeAmount()==null?"0":traFunds.getRelTradeAmount().toString());
        traFundsVo.setPracTradeAmount(traFunds.getPracTradeAmount());
        traFundsVo.setIsconsiderSituation(dictCodeMap.get("isconsiderSituationMap").get(traFunds.getIsconsiderSituation()));
        traFundsVo.setConsiderSituation(dictCodeMap.get("considerSituationMap").get(traFunds.getConsiderSituation()));
        traFundsVo.setUniqueCode(traFunds.getUniqueCode());
        traFundsVo.setSourceSystem(traFunds.getSourceSystem());
        traFundsVo.setSourceModule(traFunds.getSourceModule());
        traFundsVo.setPaymentId(selectByValue(traFunds.getPaymentId(), "INS-paymentId"));
        traFundsVo.setScene(traFunds.getScene());
        traFundsVo.setSingleRelatedTranType(selectByValue(traFunds.getSingleRelatedTranType(),"INS-SingleRelatedTranType"));
        traFundsVo.setMajorType(selectByValue(traFunds.getMajorType(),"INS-majorType"));
        traFundsVo.setConsiderType(selectByValue(traFunds.getConsiderType(),"INS-considerType"));
        traFundsVo.setEachReport(selectByValue(traFunds.getEachReport(),"BUS-isNo"));
        traFundsVo.setEachDisclosure(selectByValue(traFunds.getEachDisclosure(),"BUS-isNo"));
        traFundsVo.setMajorTrade(selectByValue(traFunds.getMajorTrade(),"BUS-isNo"));
        traFundsVo.setIsCollectRelated(selectByValue(traFunds.getIsCollectRelated(),"BUS-isNo"));
        traFundsVo.setRemark(traFunds.getRemark());
    }

    public Map<String, Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        //关系类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_RELTYPE.getCode(), "", ""));
        Map relTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("relTypeMap", relTypeMap);
        //是否
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ISNO.getCode(), "", ""));
        Map isNoMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("isNoMap", isNoMap);
        //审议类型
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_CONSIDERTYPE.getCode(), "", ""));
        Map considerTypeMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("considerTypeMap", considerTypeMap);
        //其他交易类型
        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_OTHERBUSTYPE.getCode(), "", ""));
        Map otherBusTypeMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("otherBusTypeMap", otherBusTypeMap);
        //重大类型
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_MAJORTYPE.getCode(), "", ""));
        Map majorTypeMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("majorTypeMap", majorTypeMap);
        //投资类别
        List<DictItem> dictItems6 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_FUNDSTYPE.getCode(), "", ""));
        Map fundsTypeMap = dictItems6.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("fundsTypeMap", fundsTypeMap);
        //投资方式
        List<DictItem> dictItems7 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_FUNDSINVMETHOD.getCode(), "", ""));
        Map fundsInsMethodMap = dictItems7.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("fundsInsMethodMap", fundsInsMethodMap);
        //是否可免于审议
        List<DictItem> dictItems8 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ISNO.getCode(), "", ""));
        Map isconsiderSituationMap = dictItems8.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("isconsiderSituationMap", isconsiderSituationMap);
        //免于审议情形
        List<DictItem> dictItems9 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_CONSIDERSITUATION.getCode(), "", ""));
        Map considerSituationMap = dictItems9.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("considerSituationMap", considerSituationMap);
        //交易类型
        List<DictItem> dictItems10 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_TRANTYPE.getCode(), "", ""));
        Map tranTypeMap = dictItems10.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("tranTypeMap", tranTypeMap);
        //业务类型
        List<DictItem> dictItems11 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_FUNDSTYPE.getCode(), "", ""));
        Map busTypeMap = dictItems11.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("busTypeMap", busTypeMap);
        //是否为境外投资
        List<DictItem> dictItems12 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ISNO.getCode(), "", ""));
        Map isOverseasinvestmentMap = dictItems12.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("isOverseasinvestmentMap", isOverseasinvestmentMap);


        return  dictCodeMap;
    }

    /**
     * @param property1
     * @param property2
     * @param value1
     * @param value2
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(String property1, String value1, String property2, String value2) {
        if ("".equals(property2.trim())) {
            List<Filter> filters = new Filter.Builder().eq(property1, value1).build();
            return filters;
        }
        List<Filter> filters = new Filter.Builder().eq(property1, value1).eq(property2, value2).build();
        return filters;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }

    private String selectByValue(String value, String simpleName) {
        try {
            List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
            List<DictItem> list = dictItemService.select(filters);
            //过滤掉不要的字典项
            if (!CollectionUtils.isEmpty(list)) {
                for (DictItem dictItem : list) {
                    if (dictItem.getItemValue().equals(value)) {
                        return dictItem.getItemLabel();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
