package com.yzj.jep.app.base.constant;

import com.yzj.jep.core.code.FixedDict;

/**
 * @author by Liqiang
 * @Date 2023/2/3
 * @Description 备案类型枚举
 */
@FixedDict(label = "getLabel", value = "getValue")
public enum RecordTypeDic {

    ADD("新增", "1"),
    UPDATE("变更", "2"),
    DELETE("删除","3");

    String label;
    String value;

    private RecordTypeDic(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 根据给定的value查找对应的label
     *
     * @param value 给定的value，支持null
     * @return 对应的label，没有找到或给定的value为null都返回""空字符串
     */
    public static String getLabel(String value) {
        if (value != null) {
            for (RecordTypeDic s : RecordTypeDic.values()) {
                if (s.getValue().equals(value)) {
                    return s.getLabel();
                }
            }
        }
        return "";
    }
    public static String getValue(String label) {
        if (label != null) {
            for (RecordTypeDic s : RecordTypeDic.values()) {
                if (s.getLabel().equals(label)) {
                    return s.value;
                }
            }
        }
        return "";
    }
}
