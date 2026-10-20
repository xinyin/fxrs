package com.yzj.jep.app.enums;

/**
 * @author whx
 */
public enum EastRelType {

    PSN("自然人", "01"),
    ORG("法人或非法人组织", "02");

    private String label;
    private String value;

    EastRelType(String label, String value) {
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
