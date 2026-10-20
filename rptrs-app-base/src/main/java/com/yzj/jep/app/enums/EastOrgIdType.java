package com.yzj.jep.app.enums;

/**
 * @author whx
 */
public enum EastOrgIdType {

    UNIFIED_SOCIAL_CREDIT_CODE("统一社会信用代码", "2010"),
    OTHER_CERTIFICATE("其他证件（对公）", "2999"),
    BUSINESS_LICENSE("营业执照", "2020"),
    FOREIGN_REGISTRATION_PROOF("境外合法注册成立的证明文件", "2710");

    private String label;
    private String value;

    EastOrgIdType(String label, String value) {
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

    public static String getLabelByValue(String value) {
        for (EastOrgIdType eastOrgIdType : EastOrgIdType.values()) {
            if (eastOrgIdType.getValue().equals(value)) {
                return eastOrgIdType.getLabel();
            }
        }
        return null;
    }
}
