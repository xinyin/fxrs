package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.sub.model.RelatedRelation;
import com.yzj.jep.app.sub.service.RelatedRelationService;
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

@Service(value = RelationExcelCallbackImpl.BEANID)
public class RelationExcelCallbackImpl implements XlsExportCallback {
    private static Logger logger = LoggerFactory.getLogger(RelationExcelCallbackImpl.class);
    public static final String BEANID = "RelationExcelCallbackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "subRelation.xls";
    private static final String DICTCODE = "dictCode";
    @Autowired
    RelatedRelationService relatedRelationService;
    @Autowired
    private DictItemService dictItemService;

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
//        List<Filter> filters = new Filter.Builder().eq("status","3").build();
        List<Filter> filters2 = new Filter.Builder().eq("status", "3").build();
//        List<RelatedRelation> relations = relatedRelationService.select(filters);
        List<RelatedRelation> relatedRelations = relatedRelationService.select(filters2);
        for (int i = 0; i < relatedRelations.size(); i++) {
            relatedRelations.get(i).setEntityType(dictCodeMap.get("entityType").get(relatedRelations.get(i).getEntityType()));
            relatedRelations.get(i).setSuperEntityType(dictCodeMap.get("superEntityType").get(relatedRelations.get(i).getSuperEntityType()));
            relatedRelations.get(i).setRelationType(dictCodeMap.get("relationType").get(relatedRelations.get(i).getRelationType()));
            relatedRelations.get(i).setValidStatus(dictCodeMap.get("validStatus").get(relatedRelations.get(i).getValidStatus()));
            relatedRelations.get(i).setEquityShare(dictCodeMap.get("equityShare").get(relatedRelations.get(i).getEquityShare()));
        }
        resultMap.put("relations", relatedRelations);
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
        List<DictItem> dictItems0 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.ENTITY_TYPE.getCode()));
        Map entityTypeMap = dictItems0.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("entityType", entityTypeMap);
        Map superEntityTypeMap = dictItems0.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("superEntityType", superEntityTypeMap);

        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_RELZZD.getCode()));
        Map relationTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("relationType", relationTypeMap);

        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_VALID.getCode()));
        Map validStatusMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("validStatus", validStatusMap);
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_RELEQUITYSHARE.getCode()));
        Map equityShareMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("equityShare", equityShareMap);
        return dictCodeMap;
    }
}
