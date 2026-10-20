package com.yzj.jep.app.enums;

import com.yzj.jep.core.code.FixedDict;

@FixedDict(
        label = "getLabel",
        value = "getValue"
)
public enum InsUnit {
    YLINS("总部", "10"),
    YLINS07("分公司", "20");


    private String label;
    private String value;

    private InsUnit(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getLabel(String value) {
        if (value != null) {
            InsUnit[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                InsUnit s = var1[var3];
                if (s.getValue().equals(value)) {
                    return s.getLabel();
                }
            }
        }

        return "";
    }
}
