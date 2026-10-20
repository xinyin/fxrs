package com.yzj.jep.app.ins.service.impl;

import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.model.TraServiceVo;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.sys.util.Constants;
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
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;

@Service(value = InsTraServiceExportCallbackImpl.BEANID)
public class InsTraServiceExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "InsTraServiceExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "InsTraServicesExportTemplate.xls";

    private static final String DICTCODE = "dictCode";

    @Autowired
    private TraServiceService traServiceService;

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
        String fileName = "提供货物或服务交易_" + sf.format(new Date()) + ".xls";
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

    /**
     * 编写业务代码
     */

    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {

        String dictCode = DICTCODE;
        Map resultMap = new HashMap();
        Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
        List<TraServiceVo> traServiceVos = new ArrayList<>();
        //参数
        List<Filter> filters = builderFilter(parameters);
        List<TraService> serviceList = traServiceService.select(filters);
        serviceList.stream().forEach( e -> {
            e.setRelNumber(idEncrypt(e.getRelNumber()));
        });
        if (serviceList == null || serviceList.size() == 0) {
            resultMap.put("service", serviceList);
            return resultMap;
        }
        for (TraService traService : serviceList) {
            TraServiceVo traServiceVo = new TraServiceVo();
            builderPsnBasicInfo(traServiceVo,traService, dictCodeMap);
            traServiceVos.add(traServiceVo);
        }
        resultMap.put("service", traServiceVos);
        return resultMap;
    }


    public List<Filter> builderFilter(Map parameters) {
        String data = (String) parameters.get("id");
        List<Filter> filters = new ArrayList<>();
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

    public void builderPsnBasicInfo(TraServiceVo traServiceVo, TraService traService, Map<String, Map<String, String>> dictCodeMap) {
        traServiceVo.setTransNum(traService.getTransNum());
        traServiceVo.setConsiderType(dictCodeMap.get("considerTypeMap").get(traService.getConsiderType()));
        traServiceVo.setTraSubName(traService.getTraSubName());
        traServiceVo.setRelName(traService.getRelName());
        traServiceVo.setRelType(dictCodeMap.get("relTypeMap").get(traService.getRelType()));
        traServiceVo.setRelNumber(traService.getRelNumber());
        traServiceVo.setIsHoldingSubsidiary(dictCodeMap.get("isHoldingSubsidiaryMap").get(traService.getIsHoldingSubsidiary()));
        traServiceVo.setHoldingSubsidiaryName(traService.getHoldingSubsidiaryName());
        traServiceVo.setHoldingSubsidiaryNumber(traService.getHoldingSubsidiaryNumber());
        traServiceVo.setBusType(dictCodeMap.get("BusTypeMap").get(traService.getBusType()));
        traServiceVo.setContractNo(traService.getContractNo());
        traServiceVo.setTranDateString(traService.getTranDateString());
        traServiceVo.setBusStartTimeString(traService.getBusStartTimeString());
        traServiceVo.setBusEndTimeString(traService.getBusEndTimeString());
        /*traServiceVo.setMajorType(dictCodeMap.get("majorTypeMap").get(traService.getMajorType()));*/
        traServiceVo.setTotolTradeAmount(traService.getAccuPracTradeAmount());
        traServiceVo.setTradeAmount(traService.getTradeAmount());
        traServiceVo.setIsDealPrice(dictCodeMap.get("isDealPriceMap").get(traService.getIsDealPrice()));
        traServiceVo.setTradePrice(traService.getTradePrice());
        traServiceVo.setPricingPolicy(traService.getPricingPolicy());
        traServiceVo.setTradeSubject(traService.getTradeSubject());
        traServiceVo.setFinancialImpact(traService.getFinancialImpact());
        traServiceVo.setIsTradeAgreement(dictCodeMap.get("isCashSubscribeMap").get(traService.getIsTradeAgreement()));
        traServiceVo.setIscashsubscribe(dictCodeMap.get("isCashSubscribeMap").get(traService.getIscashsubscribe()));
        traServiceVo.setRemark(traService.getRemark());
        traServiceVo.setIsAgreementImpl(dictCodeMap.get("isAgreementImplMap").get(traService.getIsAgreementImpl()));
        traServiceVo.setTranName(traService.getTranName());
        traServiceVo.setRelTradeAmount(traService.getRelTradeAmount()==null?"":traService.getRelTradeAmount().toString());
        traServiceVo.setPracTradeAmount(traService.getPracTradeAmount());
        traServiceVo.setTradingDirection(dictCodeMap.get("tradingDirectionMap").get(traService.getTradingDirection()));
        traServiceVo.setIsconsiderSituation(dictCodeMap.get("isconsiderSituationMap").get(traService.getIsconsiderSituation()));
        traServiceVo.setConsiderSituation(dictCodeMap.get("considerSituationMap").get(traService.getConsiderSituation()));
        traServiceVo.setTradeAgreementType(dictCodeMap.get("tradeAgreementTypeMap").get(traService.getTradeAgreementType()));
        traServiceVo.setTranType(dictCodeMap.get("tranTypeMap").get(traService.getTranType()));
        traServiceVo.setUniqueCode(traService.getUniqueCode());
        traServiceVo.setSourceSystem(traService.getSourceSystem());
        traServiceVo.setSourceModule(traService.getSourceModule());
        traServiceVo.setPaymentId(selectByValue(traService.getPaymentId(), "INS-paymentId"));
        traServiceVo.setScene(traService.getScene());
        traServiceVo.setSingleRelatedTranType(selectByValue(traService.getSingleRelatedTranType(),"INS-SingleRelatedTranType"));
        traServiceVo.setEachReport(selectByValue(traService.getEachReport(),"BUS-isNo"));
        traServiceVo.setConsiderType(selectByValue(traService.getConsiderType(),"INS-considerType"));
        traServiceVo.setEachDisclosure(selectByValue(traService.getEachDisclosure(),"BUS-isNo"));
        traServiceVo.setMajorTrade(selectByValue(traService.getMajorTrade(),"BUS-isNo"));
        traServiceVo.setMajorType(selectByValue(traService.getMajorType(), "INS-majorType"));
        traServiceVo.setIsCollectRelated(selectByValue(traService.getIsCollectRelated(),"BUS-isNo"));

        traServiceVo.setProtocolTransactionSymbol(dictCodeMap.get("isconsiderSituationMap").get(traService.getProtocolTransactionSymbol()));
        traServiceVo.setFundsFlowNumber(traService.getFundsFlowNumber());
        traServiceVo.setContractStatus(dictCodeMap.get("contractStatusMap").get(traService.getContractStatus()));
        traServiceVo.setDataVersion(traService.getDataVersion());


        boolean  flag = org.apache.commons.lang3.StringUtils.equals(traService.getIsAgreement(), "0");
        if (flag) {
            traServiceVo.setIsAgreement("否");
        } else {
            traServiceVo.setIsAgreement("是");
        }
    }

    public Map<String, Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        //关系类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_RELTYPE.getCode(), "", ""));
        Map relTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("relTypeMap", relTypeMap);
        //是否控股子公司
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ISSUBSIDIARY.getCode(), "", ""));
        Map isHoldingSubsidiaryMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("isHoldingSubsidiaryMap", isHoldingSubsidiaryMap);
        //审议类型
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_CONSIDERTYPE.getCode(), "", ""));
        Map considerTypeMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("considerTypeMap", considerTypeMap);
        //业务类型
        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_SERVICETYPE.getCode(), "", ""));
        Map BusTypeMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("BusTypeMap", BusTypeMap);
        //重大类型
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_MAJORTYPE.getCode(), "", ""));
        Map majorTypeMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("majorTypeMap", majorTypeMap);
        //交易定价是否为国家规定
        List<DictItem> dictItems6 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ISSUBSIDIARY.getCode(), "", ""));
        Map isDealPriceMap = dictItems6.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("isDealPriceMap", isDealPriceMap);
        //是否现金认购
        List<DictItem> dictItems7 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ISSUBSIDIARY.getCode(), "", ""));
        Map isCashSubscribeMap = dictItems7.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("isCashSubscribeMap", isCashSubscribeMap);
        //统一交易协议下执行情况
        List<DictItem> dictItems8 = dictItemService.select(builderFilter(dictCode, "INS-isAgreementImpl", "", ""));
        Map isAgreementImplMap = dictItems8.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("isAgreementImplMap", isAgreementImplMap);
        //是否可免于审议
        List<DictItem> dictItems9 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ISNO.getCode(), "", ""));
        Map isconsiderSituationMap = dictItems9.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("isconsiderSituationMap", isconsiderSituationMap);
        //免于审议情形
        List<DictItem> dictItems10 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_CONSIDERSITUATION.getCode(), "", ""));
        Map considerSituationMap = dictItems10.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("considerSituationMap", considerSituationMap);
        //交易类型
        List<DictItem> dictItems11 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_TRANTYPE.getCode(), "", ""));
        Map tranTypeMap = dictItems11.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("tranTypeMap", tranTypeMap);
        //统一交易协议类型
        List<DictItem> dictItems12 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_CONSIDERSITUATION.getCode(), "", ""));
        Map tradeAgreementTypeMap = dictItems12.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("tradeAgreementTypeMap", tradeAgreementTypeMap);
        //交易方向
        List<DictItem> dictItems13 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_TRA_DIRECTION.getCode(), "", ""));
        Map tradingDirectionMap = dictItems13.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("tradingDirectionMap", tradingDirectionMap);

        //交易币种
        List<DictItem> regCapitalCurrency = dictItemService.select(builderFilter(dictCode, "BUS-regCapitalCurrency", "", ""));
        Map regCapitalCurrencyMap = regCapitalCurrency.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("regCapitalCurrencyMap", regCapitalCurrencyMap);

        // 合同状态
        List<DictItem> dictItems14 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_CONTRACTSTATUS.getCode(), "", ""));
        Map contractStatusMap = dictItems14.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("contractStatusMap", contractStatusMap);
        return  dictCodeMap;
    }

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

    //----------------------------------------
    public String selectByValue(String value, String simpleName) {
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
        }
        return null;
    }

}
