package com.yzj.jep.app.fil.model;


import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author duanliqiang
 */

public class RelTransactions {

    /** 关联方类型 */
    private String relType;
    /** 关联方证件类型 */
    private String certType;
    /** 关联方证件号码 */
    private String relNumber;
    /** 关联方名称 */
    private String relName;
    /** 合同编号 */
    private String contractNo;
    /** 关联交易金额 */
    private java.math.BigDecimal relTrandeAmount;
    /** 关联交易类型 */
    private String tranType;
    /** 是否为统一交易协议 */
    private String isTradeAgreement;
    /** 定价政策 */
    private String pricingPolicy;
    /** 关联交易唯一编码 */
    private String sourceSysCode;
    /** 来源系统 */
    private String sourceSysName;
    /** 来源模块 */
    private String sourceSysModule;
    /** 收付费标记（0：收费，1：付费） */
    private String paymentMark;
    /** 统一交易协议类型 */
    private String tradeAgreementType;
    /** 部门 */
    private String deptName;
    /** 合同签署日期 */
    private Date contractDate;
    /** 交易日期 */
    private Date tranDate;
    private String effectOrPay;

    //三期新增字段
    //资金流水编号
    private String tranAmountTranRecordNumber;
    //货币代码
    private String currencyType;
    //交易金额（原币）
    private BigDecimal originalCurrency;
    //保单号
    private String  policyNo;
    //团个性质
    private String  groupNature;
    //
    private String busStartTime;
    //
    private String busEndTime;

    public String getTranAmountTranRecordNumber() {
        return tranAmountTranRecordNumber;
    }

    public void setTranAmountTranRecordNumber(String tranAmountTranRecordNumber) {
        this.tranAmountTranRecordNumber = tranAmountTranRecordNumber;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(BigDecimal originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getGroupNature() {
        return groupNature;
    }

    public void setGroupNature(String groupNature) {
        this.groupNature = groupNature;
    }

    public String getBusStartTime() {
        return busStartTime;
    }

    public void setBusStartTime(String busStartTime) {
        this.busStartTime = busStartTime;
    }

    public String getBusEndTime() {
        return busEndTime;
    }

    public void setBusEndTime(String busEndTime) {
        this.busEndTime = busEndTime;
    }

    public String getEffectOrPay() {
        return effectOrPay;
    }

    public void setEffectOrPay(String effectOrPay) {
        this.effectOrPay = effectOrPay;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getRelNumber() {
        return relNumber;
    }

    public void setRelNumber(String relNumber) {
        this.relNumber = relNumber;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public BigDecimal getRelTrandeAmount() {
        return relTrandeAmount;
    }

    public void setRelTrandeAmount(BigDecimal relTrandeAmount) {
        this.relTrandeAmount = relTrandeAmount;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getIsTradeAgreement() {
        return isTradeAgreement;
    }

    public void setIsTradeAgreement(String isTradeAgreement) {
        this.isTradeAgreement = isTradeAgreement;
    }

    public String getPricingPolicy() {
        return pricingPolicy;
    }

    public void setPricingPolicy(String pricingPolicy) {
        this.pricingPolicy = pricingPolicy;
    }

    public String getSourceSysCode() {
        return sourceSysCode;
    }

    public void setSourceSysCode(String sourceSysCode) {
        this.sourceSysCode = sourceSysCode;
    }

    public String getSourceSysName() {
        return sourceSysName;
    }

    public void setSourceSysName(String sourceSysName) {
        this.sourceSysName = sourceSysName;
    }

    public String getSourceSysModule() {
        return sourceSysModule;
    }

    public void setSourceSysModule(String sourceSysModule) {
        this.sourceSysModule = sourceSysModule;
    }

    public String getPaymentMark() {
        return paymentMark;
    }

    public void setPaymentMark(String paymentMark) {
        this.paymentMark = paymentMark;
    }

    public String getTradeAgreementType() {
        return tradeAgreementType;
    }

    public void setTradeAgreementType(String tradeAgreementType) {
        this.tradeAgreementType = tradeAgreementType;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    @Override
    public String toString() {
        return "RelTransactions{" +
                "relType='" + relType + '\'' +
                ", certType='" + certType + '\'' +
                ", relNumber='" + relNumber + '\'' +
                ", relName='" + relName + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", relTrandeAmount=" + relTrandeAmount +
                ", tranType='" + tranType + '\'' +
                ", isTradeAgreement='" + isTradeAgreement + '\'' +
                ", pricingPolicy='" + pricingPolicy + '\'' +
                ", sourceSysCode='" + sourceSysCode + '\'' +
                ", sourceSysName='" + sourceSysName + '\'' +
                ", sourceSysModule='" + sourceSysModule + '\'' +
                ", paymentMark='" + paymentMark + '\'' +
                ", tradeAgreementType='" + tradeAgreementType + '\'' +
                ", deptName='" + deptName + '\'' +
                ", contractDate=" + contractDate +
                '}';
    }
}
