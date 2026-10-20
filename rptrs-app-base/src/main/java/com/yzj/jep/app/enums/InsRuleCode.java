package com.yzj.jep.app.enums;

/**
 * @author tw
 * @date 2020/7/3 11:08
 * @remark 保险类关联交易规则返回码 以及提示语
 */
public enum InsRuleCode {
    //	未上市权益类投资余额不超过上季末总资产 15%
    C901("C901", "该关联方未上市权益类投资余额超过上季末总资产15%"),
    //不动产类投资余额不超过上季末总资产15%
    C902("C902", "该关联方不动产类投资余额超过上季末总资产15%"),
    //	其他金融资产投资余额不超过上季末总资产12.5%
    C903("C903", "该关联方其他金融资产投资余额超过上季末总资产12.5%"),
    //关联方的全部投资余额不超过保险公司上一年度末总资产15%
    C904("C904", "该关联方的全部投资余额超过保险公司上一年度末总资产15%"),
    //境外投资余额不超过上季末总资产 7.5%
    C905("C905", "该关联方的境外投资余额超过上季末总资产7.5%"),
    //	持股5%以上股东质押股权不超过持有保险公司股权总数50%
    C906("C906", "该关联方质押股权超过持有保险公司股权总数50%"),
    //	全部关联方的投资余额，合计不得超过保险公司上一年度末总资产的30%与上一年度末净资产二者中金额较低者
    C907("C907", "该关联方的全部投资余额超过保险公司（上一年度末总资产的30%/上一年度末净资产）"),
    //重大关联交易的
    C908("C908", "该交易为重大关联交易"),

    //重大关联交易的
    C909("C909", "金融产品的份额超过该产品发行总额的60%"),

    //超过预计额度
    C910("C910", "超过预计额度");

    String code;
    String desc;

    private InsRuleCode(String code, String desc) {
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
