package com.yzj.jep.app.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @title: DictUtil
 * @Author Whx
 * @Date: 2025/1/23 17:38
 * @Version 1.0
 */
public class DictUtil {

    private static final String REL_TYPE_PSN = "PSN";
    private static final String CODE_01 = "01";
    private static final String CODE_02 = "02";
    private static final String EMPTY_STRING = "";

    private static DictItemService dictItemService = SpringContextHolder.getBean(DictItemService.class);

    public static Map<String, String> convertToMap(String dictCode) {
        if (StrUtil.isNotBlank(dictCode)) {
            List<Filter> filters = new Filter.Builder()
                    .eq("dictCode", dictCode)
                    .build();
            List<DictItem> dictItems = dictItemService.select(filters);
            if (CollectionUtil.isNotEmpty(dictItems)) {
                return dictItems.stream().filter(en -> StrUtil.isNotBlank(en.getItemValue())).collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
            }
        }
        return new HashMap<>(16);
    }

    public static String convertRelType(String relType) {
        if (relType == null || relType.trim().isEmpty()) {
            return EMPTY_STRING;
        }
        if (REL_TYPE_PSN.equals(relType)) {
            return CODE_01;
        } else {
            return CODE_02;
        }
    }

}
