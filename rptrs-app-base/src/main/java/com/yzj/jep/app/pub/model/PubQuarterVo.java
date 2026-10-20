package com.yzj.jep.app.pub.model;


/**
 * @author LONG
 * @date 2020/8/4
 * @description
 */


public class PubQuarterVo {

    private String no;//序号
    private String pid;
    private String tranYear; //交易时间  年度+季度
    private String tranQuarter;
    private String relName; //交易对象  关联方名称
    private String relStatement;  //关联关系说明
    private String tranType;  //交易类型
    private String tradeSubject; //交易概述  交易标的
    private String tradePrice; //交易价格
    private String isDealPrice; //交易定价是否为国家规定
    private String tradeAmount;  //交易金额
    private String total;  //合计
    private String remark;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTranYear() {
        return tranYear;
    }

    public void setTranYear(String tranYear) {
        this.tranYear = tranYear;
    }

    public String getTranQuarter() {
        return tranQuarter;
    }

    public void setTranQuarter(String tranQuarter) {
        this.tranQuarter = tranQuarter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getRelStatement() {
        return relStatement;
    }

    public void setRelStatement(String relStatement) {
        this.relStatement = relStatement;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getTradeSubject() {
        return tradeSubject;
    }

    public void setTradeSubject(String tradeSubject) {
        this.tradeSubject = tradeSubject;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getIsDealPrice() {
        return isDealPrice;
    }

    public void setIsDealPrice(String isDealPrice) {
        this.isDealPrice = isDealPrice;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}