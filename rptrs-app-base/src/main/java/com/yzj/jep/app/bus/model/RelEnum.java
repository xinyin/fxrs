package com.yzj.jep.app.bus.model;

public enum RelEnum {
    /**
     * 控股
     */
    KG("kg", "ikg"),
    /**
     * 被控股
     */
    BKG("bkg", "ikg"),
    /**
     * 操控
     */
    CK("ck", "ick"),
    /**
     * 被操控
     */
    BCK("bck", "ick");

    private String isName;
    private String name;

    public String getIsName() {
        return isName;
    }

    public void setIsName(String isName) {
        this.isName = isName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    RelEnum(String isName, String name) {
        this.isName = isName;
        this.name = name;
    }
}
