package com.yzj.jep.app.enums;

/**
 * @author zyk
 * 用来记录交易类型对应的数据库中的id
 * @create 2020-08-12 16:53
 */
public enum TradeTypeCode {

    //授信类
    TRACREDIT("投资入股类", "1"),
    //用信类
    TRAUSECREDIT("资金运用类", "2"),
    //资产转移类
    TRAASSET("保险业务类", "3"),
    //提供服务类
    PROVIDESERVICES("提供服务类", "5"),
    //其他类
    OTHERTYPE("其他类", "6"),

    INSENCLOSURE("利益转移类", "4");
    String name;
    String id;

    //为了方便使用时的判断，id使用String类型
    private TradeTypeCode(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
