package com.yzj.jep.app.sub.enums;

public enum EastBatchEnum {

    RELATED("1","RELATED", "关联方信息"),
    PRODUCT("2","PRODUCT", "金融产品信息表"),
    RELATION("3","RELATION", "关联关系表"),
    FUNDS("4","FUNDS", "资金运用类关联交易协议信息表"),
    SERVICE("5","SERVICE", "服务类关联交易协议信息表"),
    INTEREST("6","INTEREST", "利益转移类关联交易协议信息表"),
    BUSINESS("7","BUSINESS", "保险业务和其他类关联交易协议信息表"),
    TRANSACTION("8","TRANSACTION", "关联交易明细表"),
    ALL_FUNDS("9","ALL_FUNDS", "资金运用关联交易统计表"),
    REL_FUNDS("10", "REL_FUNDS", "与单一关联方资金运用关联交易统计表"),
    ;


    String id;
    String type;
    String name;

    EastBatchEnum(String id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据给定的value查找对应的label
     *
     * @param value 给定的value，支持null
     * @return 对应的label，没有找到或给定的value为null都返回""空字符串
     */
    public static String getType(String value) {
        if (value != null) {
            for (EastBatchEnum s : EastBatchEnum.values()) {
                if (s.getId().equals(value)) {
                    return s.getType();
                }
            }
        }
        return "";
    }

    public static String getName(String value) {
        if (value != null) {
            for (EastBatchEnum s : EastBatchEnum.values()) {
                if (s.getId().equals(value)) {
                    return s.getName();
                }
            }
        }
        return "";
    }

}
