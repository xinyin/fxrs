package com.yzj.jep.app.enums;

public enum CompanyType {


    YLINS("FX00001_1","复星保德信总部"),
    CLIENT00("FX00001_2_6","上海业务总部"),
    CLIENT01("FX00001_2_1","北京分公司"),
    CLIENT02("FX00001_2_2","四川分公司"),
    CLIENT03("FX00001_2_3","山东分公司"),
    CLIENT04("FX00001_2_4","江苏分公司"),
    CLIENT05("FX00001_2_5","河南分公司");

    private final String category;
    private final String categoryName;

    CompanyType(String category, String categoryName) {
        this.category = category;
        this.categoryName = categoryName;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryName() {
        return categoryName;
    }




}
