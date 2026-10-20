package com.yzj.jep.app.enums;

/**
 * @author tw
 * @date 2020/6/11 20:14
 * @remark
 */
public enum RuleCode {
    CODE_01("CODE_01", ""),
    CODE_02("CODE_02", ""),
    CODE_03("CODE_03", ""),
    CODE_04("CODE_04", "该关联方的授信余额已达上限，禁止交易"),
    CODE_05("CODE_05", "该关联方的授信余额已达上限，禁止交易"),
    CODE_06("CODE_06", "所有关联方的授信余额已达上限，禁止交易"),
    CODE_07("CODE_07", "该笔交易已构成本行一般关联交易，请及时备案处理"),
    CODE_08("CODE_08", "该笔交易达到我行重大关联交易标准，需提交董事会审批"),
    CODE_09("CODE_09", "该产品交易额已达上限，禁止交易"),
    CODE_10("CODE_10", "内部人交易金额大于内部人额度，禁止交易");

    String code;
    String desc;

    private RuleCode(String code, String desc) {
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
