package com.yzj.jep.app.enums;

/**
 * @author whx
 */
public enum EastPsnIdType {

    RESIDENT_ID_CARD("居民身份证", "1010"),
    HOUSEHOLD_REGISTER("户口簿", "1040"),
    PASSPORT("护照", "1050"),
    MILITARY_OFFICER_CARD("中国人民解放军军官证", "1022"),
    OTHERS("其他证件（个人）", "1999");

    private String label;
    private String value;

    EastPsnIdType(String label, String value) {
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
        for (EastPsnIdType eastPsnIdType : EastPsnIdType.values()) {
            if (eastPsnIdType.getValue().equals(value)) {
                return eastPsnIdType.getLabel();
            }
        }
        return null;
    }
}
