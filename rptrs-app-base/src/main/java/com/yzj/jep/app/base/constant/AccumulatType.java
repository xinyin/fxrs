package com.yzj.jep.app.base.constant;

/**
 * 累计金额类型
 */
public enum AccumulatType {

    ZERO_YES("GROUND_ZERO_YES", "归零操作", "0"),
    ZERO_NO("GROUND_ZERO_NO", "不归零操作", "1"),
    COUNT_ALL("COUNT_ALL", "计算全部累计", "2"),
    COUNT_PART("COUNT_PART", "计算部分累计", "3");

    private String code;
    private String name;
    private String handler;

    AccumulatType(String code, String name, String handler) {
        this.code = code;
        this.name = name;
        this.handler = handler;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(Integer weight) {
        this.handler = handler;
    }

}
