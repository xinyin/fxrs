package com.yzj.jep.app.enums;

/**
 * @author whx
 */
public enum RelApplyStatus {

    ARCHIVED("已归档", "6"),
    PENDING_ARCHIVAL("待归档", "7");

    private String label;
    private String value;

    RelApplyStatus(String label, String value) {
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
        for (RelApplyStatus eastOrgIdType : RelApplyStatus.values()) {
            if (eastOrgIdType.getValue().equals(value)) {
                return eastOrgIdType.getLabel();
            }
        }
        return null;
    }
}
