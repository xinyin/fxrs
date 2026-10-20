package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.service.UnifiedProtocolService;
import com.yzj.jep.app.bus.vo.ProtocolVo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: UnifiedProtocolExportCallBackImpl
 * @Author Whx
 * @Date: 2021/7/17 11:57
 * @Version 1.0
 * 统一执行协议统计导出数据  unifiedProtocol
 */
@Service(value = UnifiedProtocolExportCallBackImpl.BEANID)
public class UnifiedProtocolExportCallBackImpl implements XlsExportCallback {

    public static final String BEANID = "UnifiedProtocolExportCallBackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "unifiedProtocol.xls";

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
        String params = (String) parameters.get("id");
        JSONObject jsonObject = JSONObject.parseObject(params);
        String year = (String) jsonObject.get("year");
        String quarter = (String) jsonObject.get("quarter");
        String protocolCode = (String) jsonObject.get("protocolCode");
        PageInfo<ProtocolVo> pageInfo = unifiedProtocolService.queryByConditions(1, Integer.MAX_VALUE, year, quarter, protocolCode);
        List<ProtocolVo> list = pageInfo.getList();
        if (CollectionUtil.isNotEmpty(list)) {
            //转换数据中的字典字段
            //是否是isHoldingSubsidiary
            Map<String, Object> isHoldingSubsidiaryMap = new HashMap<>();
            isHoldingSubsidiaryMap.put("0", "是");
            isHoldingSubsidiaryMap.put("1", "否");
            //关联方类别
            Map<String, Object> relTypeMap = queryDictItemsByDictCode("INS-relType");
            //交易类型
            Map<String, Object> tranTypeMap = queryDictItemsByDictCode("INS-tranType");
            //业务类型
            Map<String, Object> busTypeMap = queryDictItemsByDictCode("INS-ServiceType");
            for (ProtocolVo protocolVo : list) {
                String relType = protocolVo.getRelType();
                String busType = protocolVo.getBusType();
                String tranType = protocolVo.getTranType();
                String isHoldingSubsidiary = protocolVo.getIsHoldingSubsidiary();
                protocolVo.setRelType(StrUtil.isNotBlank(relType) ? (String) relTypeMap.get(relType) : relType);
                protocolVo.setTranType(StrUtil.isNotBlank(tranType) ? (String) tranTypeMap.get(tranType) : tranType);
                protocolVo.setBusType(StrUtil.isNotBlank(busType) ? (String) busTypeMap.get(busType) : busType);
                protocolVo.setIsHoldingSubsidiary(StrUtil.isNotBlank(isHoldingSubsidiary) ? (String) isHoldingSubsidiaryMap.get(isHoldingSubsidiary) : isHoldingSubsidiary);
            }
        }
        result.put("protocolVo", list);
        return result;
    }

    private Map<String, Object> queryDictItemsByDictCode(String dictCode) {
        Map<String, Object> map = new HashMap<>();
        DictItem dictItem = new DictItem();
        dictItem.setDictCode(dictCode);
        List<DictItem> dictItems = dictItemService.select(dictItem);
        if (CollectionUtil.isNotEmpty(dictItems)) {
            for (DictItem result : dictItems) {
                map.put(result.getItemValue(), result.getItemLabel());
            }
            return map;
        }
        return map;
    }


    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}
