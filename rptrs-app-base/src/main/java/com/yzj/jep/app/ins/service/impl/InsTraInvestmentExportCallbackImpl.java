package com.yzj.jep.app.ins.service.impl;

import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.sys.util.Constants;
import com.yzj.jep.app.util.ConfidentialInfoUtil;
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

@Service(value = InsTraInvestmentExportCallbackImpl.BEANID)
public class InsTraInvestmentExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "InsTraInvestmentExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "InsTraInvestmentsExportTemplate.xls";

    private static final String DICTCODE = "dictCode";
    
    @Autowired
    private TraInvestmentService traInvestmentService;

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
        String fileName = "投资入股类交易_" + sf.format(new Date()) + ".xls";
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
        List<TraInvestmentVo> traInvestmentVos = new ArrayList<>();
        //参数
        List<Filter> filters = builderFilter(parameters);
        List<TraInvestment> traInvestmentList = traInvestmentService.select(filters);
        traInvestmentList.stream().forEach( e -> {
            e.setRelNumber(idEncrypt(e.getRelNumber()));
        });
        if (traInvestmentList == null || traInvestmentList.size() == 0) {
            resultMap.put("traInvestmentVos", traInvestmentList);
            return resultMap;
        }
        for (TraInvestment traInvestment : traInvestmentList) {
            TraInvestmentVo traInvestmentVo = new TraInvestmentVo();
            builderPsnBasicInfo(traInvestmentVo,traInvestment, dictCodeMap);
            traInvestmentVos.add(traInvestmentVo);
        }
        resultMap.put("inv",traInvestmentVos);
        return resultMap;
    }

    public List<Filter> builderFilter(Map parameters) {
        String data = (String) parameters.get("id");
        List<Filter> filters = null;
        String[] ids = data.split(",");
        String type = ids[0];
        String relName = ids[1];
        String investmentType = ids[2];
        String relNumber = ids[3];
        String status = ids[4];
        String considerType = ids[5];
        String majorTrade = ids[6];
        String startTranDate = ids[7];
        String endTranDate = ids[8];
        filters = new Filter.Builder().eq("delflag", "1").build();
        if (!"%".equals(relName.trim())) {
            filters.add(new Filter("relName", relName));
        }
        if (!"".equals(investmentType.trim())) {
            filters.add(new Filter("investmentType", investmentType));
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
            filters.add(new Filter("tranDate", Operator.GE, format));
        }
        if (!"%".equals(endTranDate.trim())) {
            Date endTranDate1 = DateUtils.parseDate(endTranDate);
            String format = DateUtils.format(endTranDate1, "yyyy-MM-dd");
            filters.add(new Filter("tranDate", Operator.LE, format));
        }
        return filters;
    }

    public void builderPsnBasicInfo(TraInvestmentVo traInvestmentVo,TraInvestment traInvestment, Map<String, Map<String, String>> dictCodeMap) {
        traInvestmentVo.setRelName(traInvestment.getRelName());
        traInvestmentVo.setRelType(dictCodeMap.get("relTypeMap").get(traInvestment.getRelType()));
        traInvestmentVo.setRelNumber(traInvestment.getRelNumber());
        traInvestmentVo.setInvestmentType(dictCodeMap.get("investmentTypeMap").get(traInvestment.getInvestmentType()));
        traInvestmentVo.setInstead(traInvestmentVo.getInvestmentType());
        traInvestmentVo.setIsHoldingSubsidiary(dictCodeMap.get("isHoldingSubsidiaryMap").get(traInvestment.getIsHoldingSubsidiary()));
        traInvestmentVo.setHoldingSubsidiaryName(traInvestment.getHoldingSubsidiaryName());
        traInvestmentVo.setHoldingSubsidiaryNumber(traInvestment.getHoldingSubsidiaryNumber());
        traInvestmentVo.setContractNo(traInvestment.getContractNo());
        traInvestmentVo.setTranDateString(traInvestment.getTranDateString());
        traInvestmentVo.setBusStartTimeString(traInvestment.getBusStartTimeString());
        traInvestmentVo.setBusEndTimeString(traInvestment.getBusEndTimeString());
        traInvestmentVo.setApprovalNum(traInvestment.getApprovalNum());
        traInvestmentVo.setSecuritiesType(dictCodeMap.get("securitiesTypeMap").get(traInvestment.getSecuritiesType()));
        traInvestmentVo.setVariableshareratio(traInvestment.getVariableshareratio());
        traInvestmentVo.setShareNum(traInvestment.getShareNum());
        traInvestmentVo.setVariableshareratioAfter(traInvestment.getVariableshareratioAfter());
        traInvestmentVo.setEffectiveTimeString(traInvestment.getEffectiveTimeString());
        traInvestmentVo.setMajorType(dictCodeMap.get("majorTypeMap").get(traInvestment.getMajorType()));
        traInvestmentVo.setTotolTradeAmount(traInvestment.getAccuPracTradeAmount());
        traInvestmentVo.setTradeAmount(traInvestment.getTradeAmount());
        traInvestmentVo.setIsCashSubscript(dictCodeMap.get("isCashSubscribeMap").get(traInvestment.getIsCashSubscript()));
        traInvestmentVo.setIsDealPrice(dictCodeMap.get("isDealPriceMap").get(traInvestment.getIsDealPrice()));
        traInvestmentVo.setTradePrice(traInvestment.getTradePrice());
        traInvestmentVo.setYield_(traInvestment.getYield_());
        traInvestmentVo.setPricingPolicy(traInvestment.getPricingPolicy());
        traInvestmentVo.setTradeSubject(traInvestment.getTradeSubject());
        traInvestmentVo.setFinancialImpact(traInvestment.getFinancialImpact());
        traInvestmentVo.setRemark(traInvestment.getRemark());
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
        //交易类型
        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_OTHERBUSTYPE.getCode(), "", ""));
        Map busTypeMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("busTypeMap", busTypeMap);
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
        //投资入股类业务类型
        List<DictItem> dictItems8 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_INVESTMENTTYPE.getCode(), "", ""));
        Map investmentTypeMap = dictItems8.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("investmentTypeMap", investmentTypeMap);
        //证券类型
        List<DictItem> dictItems9 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_INVESTMENTTYPE.getCode(), "", ""));
        Map securitiesTypeMap = dictItems9.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("securitiesTypeMap", securitiesTypeMap);
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
