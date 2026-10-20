package com.yzj.jep.app.enums;

/**
 * @author whx
 */
public enum OrgIdType {

    UNIFIED_SOCIAL_CREDIT_CODE("统一社会信用代码", "1"),
    BUSINESS_REGISTRATION_NUMBER("工商注册号", "2"),
    FOREIGN_VALID_CODE("境外有效代码", "3"),
    OTHER("其他", "99");

    private String label;
    private String value;

    OrgIdType(String label, String value) {
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
