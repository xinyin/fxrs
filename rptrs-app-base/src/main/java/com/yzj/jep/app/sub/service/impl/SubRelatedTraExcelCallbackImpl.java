package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(value = SubRelatedTraExcelCallbackImpl.BEANID)
public class SubRelatedTraExcelCallbackImpl implements XlsExportCallback {
    private static Logger logger = LoggerFactory.getLogger(SubRelatedTraExcelCallbackImpl.class);
    public static final String BEANID = "SubRelatedTraExcelCallbackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "subRelatedTra.xls";
    private static final String DICTCODE = "dictCode";

    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private TraInvestmentService traInvestmentService;
    @Autowired
    private TraFundsService traFundsService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private TraServiceService traServiceService;
    @Autowired
    private TraOtherService traOtherService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        return null;
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
        logger.info("业务逻辑开始。。。。。");
        Map resultMap = new HashMap();
        //字典码
        String dictCode = DICTCODE;
        //获取字典码
        Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
        List<Filter> filters = new Filter.Builder().eq("majorTrade", "0").eq("status", "6").build();
        switch (parameters.get("subType").toString()) {
            case "01"://投资入股类
                List<TraInvestment> traInvestments = traInvestmentService.select(filters);
                for (int i = 0; i < traInvestments.size(); i++) {
                    traInvestments.get(i).setRelType(dictCodeMap.get("relType").get(traInvestments.get(i).getRelType()));
                    traInvestments.get(i).setTranType(dictCodeMap.get("tranType").get(traInvestments.get(i).getTranType()));
                    traInvestments.get(i).setInvestmentType(dictCodeMap.get("investmentType").get(traInvestments.get(i).getInvestmentType()));
                    if ("0".equals(traInvestments.get(i).getIsHoldingSubsidiary())) {
                        traInvestments.get(i).setIsHoldingSubsidiary("是");
                    } else if ("1".equals(traInvestments.get(i).getIsHoldingSubsidiary())) {
                        traInvestments.get(i).setIsHoldingSubsidiary("否");
                    }
                }
                resultMap.put("subRelatedTra", traInvestments);
                break;
            case "02"://资金运用类
                List<TraFunds> funds = traFundsService.select(filters);
                for (int i = 0; i < funds.size(); i++) {
                    funds.get(i).setRelType(dictCodeMap.get("relType").get(funds.get(i).getRelType()));
                    funds.get(i).setTranType(dictCodeMap.get("tranType").get(funds.get(i).getTranType()));
                    funds.get(i).setInvestmentType(dictCodeMap.get("fundsType").get(funds.get(i).getInvestmentType()));
                    if ("0".equals(funds.get(i).getIsHoldingSubsidiary())) {
                        funds.get(i).setIsHoldingSubsidiary("是");
                    } else if ("1".equals(funds.get(i).getIsHoldingSubsidiary())) {
                        funds.get(i).setIsHoldingSubsidiary("否");
                    }
                }
                resultMap.put("subRelatedTra", funds);
                break;
            case "03"://保险业务类
                List<TraBusiness> businesses = traBusinessService.select(filters);
                for (int i = 0; i < businesses.size(); i++) {
                    businesses.get(i).setRelType(dictCodeMap.get("relType").get(businesses.get(i).getRelType()));
                    businesses.get(i).setTranType(dictCodeMap.get("tranType").get(businesses.get(i).getTranType()));
                    businesses.get(i).setInvestmentType(dictCodeMap.get("businessType").get(businesses.get(i).getBusType()));
                    if ("0".equals(businesses.get(i).getIsHoldingSubsidiary())) {
                        businesses.get(i).setIsHoldingSubsidiary("是");
                    } else if ("1".equals(businesses.get(i).getIsHoldingSubsidiary())) {
                        businesses.get(i).setIsHoldingSubsidiary("否");
                    }
                }
                resultMap.put("subRelatedTra", businesses);
                break;
            case "04"://利益转移类
                List<TraInterest> interests = traInterestService.select(filters);
                for (int i = 0; i < interests.size(); i++) {
                    interests.get(i).setRelType(dictCodeMap.get("relType").get(interests.get(i).getRelType()));
                    interests.get(i).setTranType(dictCodeMap.get("tranType").get(interests.get(i).getTranType()));
                    interests.get(i).setInvestmentType(dictCodeMap.get("interestType").get(interests.get(i).getBusType()));
                    if ("0".equals(interests.get(i).getIsHoldingSubsidiary())) {
                        interests.get(i).setIsHoldingSubsidiary("是");
                    } else if ("1".equals(interests.get(i).getIsHoldingSubsidiary())) {
                        interests.get(i).setIsHoldingSubsidiary("否");
                    }
                }
                resultMap.put("subRelatedTra", interests);
                break;
            case "05"://提供货物或服务类
                List<TraService> services = traServiceService.select(filters);
                for (int i = 0; i < services.size(); i++) {
                    services.get(i).setRelType(dictCodeMap.get("relType").get(services.get(i).getRelType()));
                    services.get(i).setTranType(dictCodeMap.get("tranType").get(services.get(i).getTranType()));
                    services.get(i).setInvestmentType(dictCodeMap.get("serviceType").get(services.get(i).getBusType()));
                    if ("0".equals(services.get(i).getIsHoldingSubsidiary())) {
                        services.get(i).setIsHoldingSubsidiary("是");
                    } else if ("1".equals(services.get(i).getIsHoldingSubsidiary())) {
                        services.get(i).setIsHoldingSubsidiary("否");
                    }
                }
                resultMap.put("subRelatedTra", services);
                break;
            default://其他类
                List<TraOther> others = traOtherService.select(filters);
                for (int i = 0; i < others.size(); i++) {
                    others.get(i).setRelType(dictCodeMap.get("relType").get(others.get(i).getRelType()));
                    others.get(i).setTranType(dictCodeMap.get("tranType").get(others.get(i).getTranType()));
                    others.get(i).setInvestmentType(dictCodeMap.get("otherType").get(others.get(i).getBusType()));
                    if ("0".equals(others.get(i).getIsHoldingSubsidiary())) {
                        others.get(i).setIsHoldingSubsidiary("是");
                    } else if ("1".equals(others.get(i).getIsHoldingSubsidiary())) {
                        others.get(i).setIsHoldingSubsidiary("否");
                    }
                }
                resultMap.put("subRelatedTra", others);
        }

        return resultMap;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }

    /**
     * @param property1
     * @param value1
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(String property1, String value1) {
        List<Filter> filters = new Filter.Builder().eq(property1, value1).build();
        return filters;
    }

    /**
     * @param dictCode
     * @return
     * @desc 获取字典信息
     */
    public Map<String, Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        //交易类型
        List<DictItem> dictItemsRel = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_RELTYPE.getCode()));
        Map relTypeMap = dictItemsRel.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("relType", relTypeMap);
        List<DictItem> dictItemsTran = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_TRANTYPE.getCode()));
        Map tranTypeMap = dictItemsTran.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("tranType", tranTypeMap);
        List<DictItem> dictItemsInvestment = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_INVESTMENTTYPE.getCode()));
        Map investmentTypeMap = dictItemsInvestment.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("investmentType", investmentTypeMap);
        List<DictItem> dictItemsFunds = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_FUNDSTYPE.getCode()));
        Map fundsTypeMap = dictItemsFunds.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("fundsType", fundsTypeMap);
        List<DictItem> dictItemsBusiness = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_BUSINESSTYPE.getCode()));
        Map businessTypeMap = dictItemsBusiness.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("businessType", businessTypeMap);
        List<DictItem> dictItemsInterest = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_INTERESTTYPE.getCode()));
        Map interestTypeMap = dictItemsInterest.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("interestType", interestTypeMap);
        List<DictItem> dictItemsService = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_SERVICETYPE.getCode()));
        Map serviceTypeMap = dictItemsService.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("serviceType", serviceTypeMap);
        List<DictItem> dictItemsOther = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_OTHERBUSTYPE.getCode()));
        Map otherTypeMap = dictItemsOther.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("otherType", otherTypeMap);
        return dictCodeMap;
    }
}
