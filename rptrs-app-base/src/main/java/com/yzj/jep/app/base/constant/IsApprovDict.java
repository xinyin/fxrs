package com.yzj.jep.app.base.constant;

import com.yzj.jep.core.code.FixedDict;

@FixedDict(label = "getLabel", value = "getValue")
public enum IsApprovDict {

    SUB("待提交", "0"),
    APPOVEING("审批中", "1"),
    APPOVERJ("审批退回", "2"),
    APPOVED("审批通过", "3");

    String label;
    String value;

    private IsApprovDict(String label, String value) {
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
            for (IsApprovDict s : IsApprovDict.values()) {
                if (s.getValue().equals(value)) {
                    return s.getLabel();
                }
            }
        }
        return "";
    }

}
