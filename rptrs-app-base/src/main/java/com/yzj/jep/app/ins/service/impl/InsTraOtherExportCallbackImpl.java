package com.yzj.jep.app.ins.service.impl;

import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.ins.model.TraOther;
import com.yzj.jep.app.ins.model.TraOtherVo;
import com.yzj.jep.app.ins.service.TraOtherService;
import com.yzj.jep.app.enums.DictionaryEnum;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;

@Service(value = InsTraOtherExportCallbackImpl.BEANID)
public class InsTraOtherExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "InsTraOtherExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "InsTraOtherExportTemplate.xls";

    private static final String DICTCODE = "dictCode";
    @Autowired
    private TraOtherService traOtherService;

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
        String fileName = "其他类交易_" + sf.format(new Date()) + ".xls";
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
        List<TraOtherVo> traOtherVos = new ArrayList<>();
        //参数
        List<Filter> filters = builderFilter(parameters);
        List<TraOther> traOthersList = traOtherService.select(filters);
        traOthersList.stream().forEach( e -> {
            e.setRelNumber(idEncrypt(e.getRelNumber()));
        });
        if (traOthersList == null || traOthersList.size() == 0) {
            resultMap.put("traOtherVos", traOthersList);
            return resultMap;
        }
        for (TraOther traOther : traOthersList) {
            TraOtherVo traOtherVo = new TraOtherVo();
            builderPsnBasicInfo(traOtherVo,traOther, dictCodeMap);
            traOtherVos.add(traOtherVo);
        }
        resultMap.put("traOtherVos",traOtherVos);
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
        filters.add(new Filter("delflag",Operator.EQ,"1"));
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
        return filters;
    }

    public void builderPsnBasicInfo(TraOtherVo traOtherVo,TraOther traOther, Map<String, Map<String, String>> dictCodeMap) {
        traOtherVo.setTransNum(traOther.getTransNum());
        traOtherVo.setConsiderType(dictCodeMap.get("considerTypeMap").get(traOther.getConsiderType()));
        traOtherVo.setTraSubName(traOther.getTraSubName());
        traOtherVo.setRelName(traOther.getRelName());
        traOtherVo.setRelType(dictCodeMap.get("relTypeMap").get(traOther.getRelType()));
        traOtherVo.setRelNumber(traOther.getRelNumber());
        traOtherVo.setIsHoldingSubsidiary(dictCodeMap.get("isHoldingSubsidiaryMap").get(traOther.getIsHoldingSubsidiary()));
        traOtherVo.setHoldingSubsidiaryName(traOther.getHoldingSubsidiaryName());
        traOtherVo.setHoldingSubsidiaryNumber(traOther.getHoldingSubsidiaryNumber());
        traOtherVo.setBusTypStr(dictCodeMap.get("otherBusTypeMap").get(traOther.getBusType()));
        traOtherVo.setContractNo(traOther.getContractNo());
        traOtherVo.setTranDateString(traOther.getTranDateString());
        traOtherVo.setBusStartTimeString(traOther.getBusStartTimeString());
        traOtherVo.setBusEndTimeString(traOther.getBusEndTimeString());
        traOtherVo.setMajorType(dictCodeMap.get("majorTypeMap").get(traOther.getMajorType()));
        traOtherVo.setTotolTradeAmount(traOther.getAccuPracTradeAmount());
        traOtherVo.setTradeAmount(traOther.getTradeAmount());
        traOtherVo.setIsDealPrice(dictCodeMap.get("isDealPriceMap").get(traOther.getIsDealPrice()));
        traOtherVo.setIsCashSubscribe(dictCodeMap.get("isCashSubscribeMap").get(traOther.getIsCashSubscribe()));
        traOtherVo.setTradePrice(traOther.getTradePrice());
        traOtherVo.setPricingPolicy(traOther.getPricingPolicy());
        traOtherVo.setTradeSubject(traOther.getTradeSubject());
        traOtherVo.setFinancialImpact(traOther.getFinancialImpact());
        traOtherVo.setRemark(traOther.getRemark());
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
        //其他交易类型
        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_OTHERBUSTYPE.getCode(), "", ""));
        Map otherBusTypeMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("otherBusTypeMap", otherBusTypeMap);
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
