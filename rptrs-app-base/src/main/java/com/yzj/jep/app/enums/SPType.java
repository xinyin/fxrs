package com.yzj.jep.app.enums;

public enum SPType {
    //报送-金融产品
    B_01("B_01", "3"),
    //报送-自然人关联方
    B_02("B_02", "1"),
    //报送-法人或其他组织
    B_03("B_03", "0"),
    //报送-关联关系图谱
    B_04("B_04", "2"),
    //报送-单笔关联交易-投资入股类
    B_05("B_05", "01"),
    //报送-单笔关联交易-资金运用类
    B_06("B_06", "02"),
    //报送-单笔关联交易-保险业务类
    B_07("B_07", "03"),
    //报送-单笔关联交易-利益转移类
    B_08("B_08", "04"),
    //报送-单笔关联交易-提供货物类或服务类
    B_09("B_09", "05"),
    //报送-季报-关联交易明细表
    B_10("B_10", "06"),
    //报送-季报-资金运用关联交易统计表
    B_11("B_11", "07"),
    //报送-季报-与单一法人资金运用关联交易统计表
    B_12("B_12", "08"),


    //逐笔披露-资金运用信息披露
    P_01("P_01","1"),
    //逐笔披露-重大关联交易披露
    P_02("P_02","2"),
    //逐笔披露-统一交易协议披露
    P_03("P_03","3"),
    //定期披露-关联交易季度明细
    P_04("P_04","4");

    String code;
    String desc;

    private SPType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
