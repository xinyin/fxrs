package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.sub.model.Person;
import com.yzj.jep.app.sub.service.PersonService;
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

@Service(value = SubPersonExcelCallbackImpl.BEANID)
public class SubPersonExcelCallbackImpl implements XlsExportCallback {
    private static Logger logger = LoggerFactory.getLogger(SubPersonExcelCallbackImpl.class);
    public static final String BEANID = "SubPersonExcelCallbackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "subPerson.xls";
    private static final String DICTCODE = "dictCode";
    @Autowired
    PersonService personService;
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
        List<Filter> filters = new Filter.Builder().eq("effectRelation", "1").eq("status", "3").build();
        List<Person> personList = personService.select(filters);
        for (int i = 0; i < personList.size(); i++) {
            personList.get(i).setIdentityType(dictCodeMap.get("identityType").get(personList.get(i).getIdentityType()));
            personList.get(i).setDomesticOrForeign(dictCodeMap.get("domesticOrForeign").get(personList.get(i).getDomesticOrForeign()));
        }
        resultMap.put("person", personList);
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
        List<DictItem> dictItems0 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_IDENTIFYTYPE.getCode()));
        Map identityTypeMap = dictItems0.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("identityType", identityTypeMap);
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_DOMESTICORFOREIGN.getCode()));
        Map domesticOrForeign = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("domesticOrForeign", domesticOrForeign);
        return dictCodeMap;
    }
}
