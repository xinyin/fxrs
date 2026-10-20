package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraBusinessVo;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.app.sys.util.Constants;
import com.yzj.jep.app.util.ConfidentialInfoUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.entity.NameValuePair;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = InsTradeBusinessExportCallbackImpl.BEANID)
public class InsTradeBusinessExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "InsTradeBusinessExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "InsTraBusinessExportTemplate.xls";

    private static final String DICTCODE = "dictCode";

    @Autowired
    private TraBusinessService traBusinessService;

    @Autowired
    DictItemService dictItemService;
    @Autowired
    ProductService productService;

    @Autowired
    private RelPsnService relPsnService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "保险业务类类交易信息_" + sf.format(new Date()) + ".xls";
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
        List<TraBusinessVo> traBusinessVos = new ArrayList<>();
        //参数
        List<Filter> filters = builderFilter(parameters);
        List<TraBusiness> traBusinessList = traBusinessService.select(filters);
        if (traBusinessList == null || traBusinessList.size() == 0) {
            resultMap.put("business", traBusinessList);
            return resultMap;
        }
        for (TraBusiness traBusiness : traBusinessList) {
            TraBusinessVo traBusinessVo = new TraBusinessVo();
            builderPsnBasicInfo(traBusinessVo, traBusiness, dictCodeMap);
            traBusinessVos.add(traBusinessVo);
        }
        resultMap.put("business", traBusinessVos);
        return resultMap;
    }

    public List<Filter> builderFilter(Map parameters) {
        String data = (String) parameters.get("id");
        List<Filter> filters = null;
        String[] ids = data.split(",");
        String type = ids[0];
        String relName = ids[1];
        String busType = ids[2];
        String relNumber = ids[3];
        String status = ids[4];
        String considerType = ids[5];
        String majorTrade = ids[6];
        String startTranDate = ids[7];
        String endTranDate = ids[8];
        String contractNo = ids[9];
        filters = new Filter.Builder().eq("delflag", "1").build();
        if (!"%".equals(relName.trim())) {
            filters.add(new Filter("relName", relName));
        }
        if (!"".equals(busType.trim())) {
            filters.add(new Filter("busType", busType));
        }
        if (!"%".equals(relNumber.trim())) {
            filters.add(new Filter("relNumber", relNumber));
        }

        if (!"".equals(status.trim())) {
            filters.add(new Filter("status", status));
        }
        if (!"".equals(considerType.trim())) {
            filters.add(new Filter("considerType", considerType));
        }
        if (!"".equals(majorTrade.trim())) {
            filters.add(new Filter("majorTrade", majorTrade));
        }
        if (!"%".equals(startTranDate.trim())) {
            Date startTranDate1 = DateUtils.parseDate(startTranDate);
            String format = DateUtils.format(startTranDate1, "yyyy-MM-dd");
            filters.add(new Filter("contractDate", Operator.GE, format));
        }
        if (!"%".equals(endTranDate.trim())) {
            Date endTranDate1 = DateUtils.parseDate(endTranDate);
            String format = DateUtils.format(endTranDate1, "yyyy-MM-dd");
            filters.add(new Filter("contractDate", Operator.LE, format));
        }

        if (!"%".equals(contractNo.trim())) {
            Date contractNo1 = DateUtils.parseDate(contractNo);
            String format = DateUtils.format(contractNo1, "yyyy-MM-dd");
            filters.add(new Filter("contractNo", Operator.LIKE, format));
        }
        return filters;
    }

    public void builderPsnBasicInfo(TraBusinessVo traBusinessVo, TraBusiness traBusiness, Map<String, Map<String, String>> dictCodeMap) {

        traBusinessVo.setTransNum(traBusiness.getTransNum());
        traBusinessVo.setRelName(traBusiness.getRelName());
        traBusinessVo.setTranType(dictCodeMap.get("tranTypeMap").get(traBusiness.getTranType()));
        traBusinessVo.setTraSubName(traBusiness.getTraSubName());
        traBusinessVo.setRelType(dictCodeMap.get("relTypeMap").get(traBusiness.getRelType()));
        traBusinessVo.setRelNumber(traBusiness.getRelNumber());
        traBusinessVo.setIsHoldingSubsidiary(dictCodeMap.get("isNoMap").get(traBusiness.getIsHoldingSubsidiary()));
        traBusinessVo.setHoldingSubsidiaryName(traBusiness.getHoldingSubsidiaryName());
        traBusinessVo.setHoldingSubsidiaryNumber(traBusiness.getHoldingSubsidiaryNumber());
        traBusinessVo.setBusType(dictCodeMap.get("busTypeMap").get(traBusiness.getBusType()));
        traBusinessVo.setContractNo(traBusiness.getContractNo());
        traBusinessVo.setTranDateString(traBusiness.getContractDateString());
        traBusinessVo.setBusStartTimeString(traBusiness.getBusStartTimeString());
        traBusinessVo.setBusEndTimeString(traBusiness.getBusEndTimeString());
        traBusinessVo.setTotolTradeAmount(String.valueOf(traBusiness.getAccuPracTradeAmount()));
        traBusinessVo.setTradeAmount(String.valueOf(traBusiness.getTradeAmount()));
        traBusinessVo.setIsDealPrice(dictCodeMap.get("isNoMap").get(traBusiness.getIsDealPrice()));
        traBusinessVo.setIsCashSubscript(dictCodeMap.get("isNoMap").get(traBusiness.getIsCashSubscript()));
        traBusinessVo.setIsAgreement(dictCodeMap.get("isNoMap").get(traBusiness.getIsAgreement()));
        traBusinessVo.setTradePrice(traBusiness.getTradePrice());
        traBusinessVo.setPricingPolicy(traBusiness.getPricingPolicy());
        traBusinessVo.setTradeSubject(traBusiness.getTradeSubject());
        traBusinessVo.setFinancialImpact(traBusiness.getFinancialImpact());
        traBusinessVo.setRelTradeAmount(traBusiness.getRelTradeAmount() == null ? "" : traBusiness.getRelTradeAmount().toString());
        traBusinessVo.setPracTradeAmount(traBusiness.getPracTradeAmount());
        traBusinessVo.setIsAgreementImpl(dictCodeMap.get("isAgreementImplMap").get(traBusiness.getIsAgreementImpl()));
        traBusinessVo.setConsiderSituation(dictCodeMap.get("considerSituationMap").get(traBusiness.getConsiderSituation()));
        traBusinessVo.setTradeAgreementType(dictCodeMap.get("tradeAgreementTypeMap").get(traBusiness.getTradeAgreementType()));
        traBusinessVo.setIsconsiderSituation(dictCodeMap.get("isconsiderSituationMap").get(traBusiness.getIsconsiderSituation()));
        traBusinessVo.setTranName(traBusiness.getTranName());
        // traBusinessVo.setTotalAssets(traBusiness.getTotalAssets());
        traBusinessVo.setRemark(traBusiness.getRemark());
        traBusinessVo.setUniqueCode(traBusiness.getUniqueCode());
        traBusinessVo.setSourceSystem(traBusiness.getSourceSystem());
        traBusinessVo.setSourceModule(traBusiness.getSourceModule());
        traBusinessVo.setPaymentId(selectByValue(traBusiness.getPaymentId(), "INS-paymentId"));
        traBusinessVo.setScene(traBusiness.getScene());
        traBusinessVo.setSingleRelatedTranType(selectByValue(traBusiness.getSingleRelatedTranType(), "INS-SingleRelatedTranType"));
        traBusinessVo.setConsiderType(selectByValue(traBusiness.getConsiderType(), "INS-considerType"));
        traBusinessVo.setEachReport(selectByValue(traBusiness.getEachReport(), "BUS-isNo"));
        traBusinessVo.setEachDisclosure(selectByValue(traBusiness.getEachDisclosure(), "BUS-isNo"));
        traBusinessVo.setMajorTrade(selectByValue(traBusiness.getMajorTrade(), "BUS-isNo"));
        traBusinessVo.setMajorType(selectByValue(traBusiness.getMajorType(), "INS-majorType"));
        traBusinessVo.setIsCollectRelated(selectByValue(traBusiness.getIsCollectRelated(), "BUS-isNo"));
        traBusinessVo.setNumberOfPolicy(traBusiness.getNumberOfPolicy());
        traBusinessVo.setGroupTheChar(selectByValue(traBusiness.getGroupTheChar(), "BUS-groupTheChar"));

        traBusinessVo.setProtocolTransactionSymbol(dictCodeMap.get("isconsiderSituationMap").get(traBusiness.getProtocolTransactionSymbol()));
        traBusinessVo.setFundsFlowNumber(traBusiness.getFundsFlowNumber());
        traBusinessVo.setContractStatus(dictCodeMap.get("contractStatusMap").get(traBusiness.getContractStatus()));
        traBusinessVo.setDataVersion(traBusiness.getDataVersion());
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
        //保险业务交易类型
        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_BUSINESSTYPE.getCode(), "", ""));
        Map busTypeMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("busTypeMap", busTypeMap);
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
        //交易类型
        List<DictItem> dictItems8 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_TRANTYPE.getCode(), "", ""));
        Map tranTypeMap = dictItems8.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("tranTypeMap", tranTypeMap);
        //统一交易协议执行情况
        List<DictItem> dictItems9 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_ISAGREEMENTIMPL.getCode(), "", ""));
        Map isAgreementImplMap = dictItems9.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("isAgreementImplMap", isAgreementImplMap);
        //是否可免于审议
        List<DictItem> dictItems10 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ISNO.getCode(), "", ""));
        Map isconsiderSituationMap = dictItems10.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("isconsiderSituationMap", isconsiderSituationMap);
        //免于审议情形
        List<DictItem> dictItems11 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_CONSIDERSITUATION.getCode(), "", ""));
        Map considerSituationMap = dictItems11.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("considerSituationMap", considerSituationMap);
        //统一交易协议类型
        List<DictItem> dictItems12 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_CONSIDERSITUATION.getCode(), "", ""));
        Map tradeAgreementTypeMap = dictItems12.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("tradeAgreementTypeMap", tradeAgreementTypeMap);

        //交易币种
        List<DictItem> regCapitalCurrency = dictItemService.select(builderFilter(dictCode, "BUS-regCapitalCurrency", "", ""));
        Map regCapitalCurrencyMap = regCapitalCurrency.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("regCapitalCurrencyMap", regCapitalCurrencyMap);

        // 合同状态
        List<DictItem> dictItems13 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_CONTRACTSTATUS.getCode(), "", ""));
        Map contractStatusMap = dictItems13.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("contractStatusMap", contractStatusMap);
        return dictCodeMap;


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
        List<NameValuePair> items = new ArrayList<>();
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


    //身份证前六后四脱敏
    public static String idEncrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 10)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{6})\\w(?=\\w{4})", "*");
    }

    //自然人非身份证脱敏
    public static String otherIdEncrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{2})", "*");

    }
}
