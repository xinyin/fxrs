package com.yzj.jep.app.enums;

import com.yzj.jep.commons.StringUtils;

import java.util.Arrays;

public enum DataSource {

    SOURCE_SYS_TYPE_1("0","1","系统录入"),
    SOURCE_SYS_TYPE_2("1","2","个险核心系统"),
    SOURCE_SYS_TYPE_3("2","3","团险核心系统"),
    SOURCE_SYS_TYPE_4("3","4","OA系统"),
    SOURCE_SYS_TYPE_5("4","5","费控系统"),
    SOURCE_SYS_TYPE_6("5","6","估值系统");



    private final String category;
    private final String sysCode;
    private final String categoryName;

    DataSource(String category, String sysCode, String categoryName) {
        this.category = category;
        this.sysCode = sysCode;
        this.categoryName = categoryName;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getSysCode() {
        return sysCode;
    }
    /**
     * 根据category获取sysCode值
     */
    public static String getCode(String categoryStr){
        if(StringUtils.isEmpty(categoryStr)) {
            return null;
        }
        return Arrays.stream(values())
                .filter(it -> it.category.equals(categoryStr.trim()))
                .findFirst()
                .map(v -> v.sysCode)
                .orElse(null);
    }

}
