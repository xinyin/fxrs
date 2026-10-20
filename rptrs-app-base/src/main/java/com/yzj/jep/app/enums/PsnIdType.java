package com.yzj.jep.app.enums;

/**
 * @author whx
 */
public enum PsnIdType {

    RESIDENT_ID_CARD("居民身份证", "1"),
    HOUSEHOLD_REGISTER("居民户口薄", "2"),
    MILITARY_OFFICER_CARD("军官证", "3"),
    PASSPORT("护照", "4"),
    OTHER_DOCUMENTS("其他证件", "5");

    private String label;
    private String value;

    PsnIdType(String label, String value) {
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
