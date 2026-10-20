package com.yzj.jep.app.enums;

public enum SourceSys {

    SOURCE_SYS_TYPE_01("1","个险核心系统"),
    SOURCE_SYS_TYPE_02("2","团险核心系统"),
    SOURCE_SYS_TYPE_03("3","OA系统"),
    SOURCE_SYS_TYPE_04("4","费控系统"),
    SOURCE_SYS_TYPE_05("5","估值系统");



    private final String category;
    private final String categoryName;

    SourceSys(String category, String categoryName) {
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
