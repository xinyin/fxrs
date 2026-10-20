package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.service.UnifiedProtocolService;
import com.yzj.jep.app.bus.vo.ProtocolVo;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.ins.model.TraOther;
import com.yzj.jep.app.ins.model.TraOtherVo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 统一执行协议统计导出数据
 */
@Service(value = IsAgreementImplExportCallBackImpl.BEANID)
public class IsAgreementImplExportCallBackImpl implements XlsExportCallback {

    public static final String BEANID = "IsAgreementImplExportCallBackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "isAgreementImpl.xls";
    private static final String DICTCODE = "dictCode";

    @Autowired
    private UnifiedProtocolService unifiedProtocolService;

    @Autowired
    private DictItemService dictItemService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "统一交易执行情况统计" + sf.format(new Date()) + ".xls";
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
     * @param parameters
     * @return
     * @throws ServiceException
     * @desc 业务代码
     */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        Map result = new HashMap();
        String dictCode = DICTCODE;
        Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
        List<ProtocolVo> protocolVos = new ArrayList<>();
        String params = (String) parameters.get("id");
        JSONObject jsonObject = JSONObject.parseObject(params);
        String relName = (String) jsonObject.get("relName");
        String relNumber = (String) jsonObject.get("relNumber");
        List<ProtocolVo> isAgreementImplList = unifiedProtocolService.getIsAgreementImpl(relNumber, relName);
        for (ProtocolVo protocolVo:isAgreementImplList){
            ProtocolVo entity = new ProtocolVo();
            builderPsnBasicInfo(entity,protocolVo, dictCodeMap);
            protocolVos.add(entity);
        }
        result.put("protocolVo", protocolVos);
        return result;
    }
    //构筑基础信息
    public void builderPsnBasicInfo(ProtocolVo entity, ProtocolVo protocolVo, Map<String, Map<String, String>> dictCodeMap) {
        entity.setContractDateStr(protocolVo.getContractDateStr());
        entity.setRelName(protocolVo.getRelName());
        entity.setRelType(dictCodeMap.get("RelTypeMap").get(protocolVo.getRelType()));
        entity.setRelNumber(protocolVo.getRelNumber());
        entity.setTranType(dictCodeMap.get("TranTypeMap").get(protocolVo.getTranType()));
        entity.setBusType(dictCodeMap.get("BusTypeMap").get(protocolVo.getBusType()));
        entity.setContractNo(protocolVo.getContractNo());
        entity.setAgreementNum(protocolVo.getAgreementNum());
        entity.setContractAmount(protocolVo.getContractAmount());
        entity.setPracTradeAmount(protocolVo.getPracTradeAmount());
        entity.setRat(protocolVo.getRat());

    }

    public Map<String, Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        //关联方类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ORGORPSN.getCode(), "", ""));
        Map TypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("RelTypeMap", TypeMap);
        //交易类型
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_TRANTYPE.getCode(), "", ""));
        Map TranTypeMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("TranTypeMap", TranTypeMap);
        //业务类型
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_INSBUSINESSTYPE.getCode(), "", ""));
        Map BusTypeMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("BusTypeMap", BusTypeMap);
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
}
