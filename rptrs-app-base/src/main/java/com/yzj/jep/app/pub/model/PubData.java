package com.yzj.jep.app.pub.model;

import java.math.BigDecimal;
import java.util.Date;

public class PubData {
    private Integer pid;
    private String relName; //关联方名称
    private String relType;//关联方类别
    private String relNumber;//关联方证件号
    private String relComment; //关联关系说明
    private String tranType;//交易类型
    private String tranDate ;//交易日期
    private String IsAgreement;//统一交易协议
    private String isAgreementImpl;//统一交易协议执行情况
    private String majorTrade;//是否重大 0：是，1：否
    private String majorType;//达到重大关联交易的情形
    private BigDecimal totlTradeAmount;// 本年度与该关联方发生的关联交易累计金额（万元）
    private BigDecimal tradeAmount;//关联交易金额（实际交易金额）
    private String pricingPolicy;//定价政策
    private String relStatement;//关联说明
    private String busStartDate;//业务开始日期
    private String tradePrice;//交易价格
    private String financialImpact;//对财务状况及经营成果的影响
    private String tradeSubject;//交易标的
    private String isDiscovery;//是否披露
    private String busType;//业务类型
    private String remark;//其他事项说明

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getRelNumber() {
        return relNumber;
    }

    public void setRelNumber(String relNumber) {
        this.relNumber = relNumber;
    }

    public String getRelComment() {
        return relComment;
    }

    public void setRelComment(String relComment) {
        this.relComment = relComment;
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

    public String getIsAgreement() {
        return IsAgreement;
    }

    public void setIsAgreement(String isAgreement) {
        IsAgreement = isAgreement;
    }

    public String getIsAgreementImpl() {
        return isAgreementImpl;
    }

    public void setIsAgreementImpl(String isAgreementImpl) {
        this.isAgreementImpl = isAgreementImpl;
    }

    public String getMajorTrade() {
        return majorTrade;
    }

    public void setMajorTrade(String majorTrade) {
        this.majorTrade = majorTrade;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public BigDecimal getTotlTradeAmount() {
        return totlTradeAmount;
    }

    public void setTotlTradeAmount(BigDecimal totlTradeAmount) {
        this.totlTradeAmount = totlTradeAmount;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getPricingPolicy() {
        return pricingPolicy;
    }

    public void setPricingPolicy(String pricingPolicy) {
        this.pricingPolicy = pricingPolicy;
    }

    public String getRelStatement() {
        return relStatement;
    }

    public void setRelStatement(String relStatement) {
        this.relStatement = relStatement;
    }

    public String getBusStartDate() {
        return busStartDate;
    }

    public void setBusStartDate(String busStartDate) {
        this.busStartDate = busStartDate;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getFinancialImpact() {
        return financialImpact;
    }

    public void setFinancialImpact(String financialImpact) {
        this.financialImpact = financialImpact;
    }

    public String getTradeSubject() {
        return tradeSubject;
    }

    public void setTradeSubject(String tradeSubject) {
        this.tradeSubject = tradeSubject;
    }

    public String getIsDiscovery() {
        return isDiscovery;
    }

    public void setIsDiscovery(String isDiscovery) {
        this.isDiscovery = isDiscovery;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
