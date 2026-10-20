package com.yzj.jep.app.pub.model;


/**
 * @author LONG
 * @date 2020/7/20
 * @description
 */


public class PubYearVo {

    private String no; //序號
    private String pid;
    private String relType; //关联方类型
    private String relName;  //关联方名称
    private String relText;  //交易概述(交易类型 关联方名称 交易价格 交易金额 时间)
    private String tranType; //交易类型
    private String tranDate;//交易日期
    private String tradePrice;//交易价格
    private String pricingPolicy; //定价政策
    private String tradeSubject;//交易标的
    private String periodAmt;  //本期金额
    private String periodRatio;//本期同类占比
    private String lastAmt; //上期金额
    private String lastRatio;//上期同类占比
    private String total;  //合计
    private String tradeAmount;//交易金额
    private String majorTradecount;//发生重大关联交易笔数  MAJOR_TRADE
    private String majorTrade;//是否重大关联交易
    private String orgType;//企业类型
    private String uniscCode;//统一社会信用代码
    private String majorType;//是否重大关联


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

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTradeSubject() {
        return tradeSubject;
    }

    public void setTradeSubject(String tradeSubject) {
        this.tradeSubject = tradeSubject;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getRelText() {
        return relText;
    }

    public void setRelText(String relText) {
        this.relText = relText;
    }

    public String getPricingPolicy() {
        return pricingPolicy;
    }

    public void setPricingPolicy(String pricingPolicy) {
        this.pricingPolicy = pricingPolicy;
    }

    public String getPeriodAmt() {
        return periodAmt;
    }

    public void setPeriodAmt(String periodAmt) {
        this.periodAmt = periodAmt;
    }

    public String getPeriodRatio() {
        return periodRatio;
    }

    public void setPeriodRatio(String periodRatio) {
        this.periodRatio = periodRatio;
    }

    public String getLastAmt() {
        return lastAmt;
    }

    public void setLastAmt(String lastAmt) {
        this.lastAmt = lastAmt;
    }

    public String getLastRatio() {
        return lastRatio;
    }

    public void setLastRatio(String lastRatio) {
        this.lastRatio = lastRatio;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getMajorTradecount() {
        return majorTradecount;
    }

    public void setMajorTradecount(String majorTradecount) {
        this.majorTradecount = majorTradecount;
    }

    public String getMajorTrade() {
        return majorTrade;
    }

    public void setMajorTrade(String majorTrade) {
        this.majorTrade = majorTrade;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setUniscCode(String uniscCode) {
        this.uniscCode = uniscCode;
    }

    public String getUniscCode() {
        return uniscCode;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public String getMajorType() {
        return majorType;
    }
}