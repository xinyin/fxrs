package com.yzj.jep.app.util;

import cn.hutool.core.collection.CollUtil;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author by Liqiang
 * @Date 2022/10/11
 * @Description 字典工具类
 */
@Component
public class DicItemUtils {

    private static final Logger logger = LoggerFactory.getLogger(DicItemUtils.class);

    private static final DictItemService dictItemService = SpringContextHolder.getBean(DictItemService.class);

    /**
     * 根据dicCode和itemLabel进行查询itemValue
     *
     * @param map       数据字典集合
     * @param dicCode   查询的dicCode
     * @param itemLabel 字典的value的描述
     * @return 字典的value
     */
    public static String getItemValue(Map<String, List<DictItem>> map, String dicCode, String itemLabel) {
        List<DictItem> items = map.get(dicCode);
        String itemValue = "";
        if (CollUtil.isNotEmpty(items)) {
            try {
                itemValue = items.stream().filter(item -> item.getItemLabel().equals(itemLabel)).findFirst().map(DictItem::getItemValue).get();
            } catch (Exception e) {
                throw new RuntimeException("在字典项中该字典码值【 " + dicCode + "】中【" + itemLabel + "】类型没有配置!");
            }
        }
        return itemValue;
    }



    /**
     * 根据dicCode和itemValue进行查询itemLabel
     *
     * @param map       数据字典集合
     * @param dicCode   查询的dicCode
     * @param itemValue 字典的值
     * @return 字典的描述
     */
    public static String getItemLabel(Map<String, List<DictItem>> map, String dicCode, String itemValue) {
        List<DictItem> items = map.get(dicCode);
        String itemLabel = "";
        if (CollUtil.isNotEmpty(items)) {
            try {
                itemLabel = items.stream().filter(item -> item.getItemValue().equals(itemValue)).findFirst().map(DictItem::getItemLabel).get();
            } catch (Exception e) {
                logger.warn("在字典项中该字典码值【 " + dicCode + "】中【" + itemValue + "】类型没有配置!");
            }
        }
        return itemLabel;
    }


    /**
     * 查询字段集合
     */
    public static Map<String, List<DictItem>> getDicValue() {

        Map<String, List<DictItem>> dicItemMap = new HashMap();
        List<DictItem> dictItemList = dictItemService.selectAll();
        if (CollUtil.isNotEmpty(dictItemList)) {
            dicItemMap = dictItemList.stream().collect(Collectors.groupingBy(DictItem::getDictCode));
        }

        return dicItemMap;
    }
}
