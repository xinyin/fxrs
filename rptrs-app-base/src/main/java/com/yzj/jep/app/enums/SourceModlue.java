package com.yzj.jep.app.enums;

public enum SourceModlue {

    SOURCE_MODLUE_01("1","新保"),
    SOURCE_MODLUE_02("2","保全"),
    SOURCE_MODLUE_03("3","理赔"),
    SOURCE_MODLUE_04("4","续期"),
    SOURCE_MODLUE_05("5","续保"),
    SOURCE_MODLUE_06("6","再保");


    private final String category;
    private final String categoryName;

    SourceModlue(String category, String categoryName) {
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
