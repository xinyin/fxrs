package com.yzj.jep.app.enums;

/**
 * @author whx
 */
public enum RdsDataSrc {

    GLJY("关联交易", "08");

    private String label;
    private String value;

    RdsDataSrc(String label, String value) {
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
}
