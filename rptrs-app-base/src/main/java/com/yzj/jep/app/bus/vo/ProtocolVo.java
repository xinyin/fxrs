package com.yzj.jep.app.bus.vo;

import com.yzj.jep.commons.DateUtils;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @title: ProtocolVo
 * @Author Whx
 * @Date: 2021/7/16 16:10
 * @Version 1.0
 * 统一执行协议返回视图
 */
public class ProtocolVo implements Serializable {
    /**主键*/
    private String pid;
    /**交易标的*/
    private String tradeSubject;
    /**交易名称*/
    private String transactionName;
    /**统一协议编号*/
    private String protocolCode;
    /**关联方ID*/
    private String relId;
    /**关联方类型*/
    private String relType;
    /**关联方名称*/
    private String relName;
    /**关联方证件号码*/
    private String relNumber;
    /**关联方ID*/
    private String tradeOrg;
    /**交易类型*/
    private String tranType;
    /**业务类型*/
    private String busType;
    /**关联方ID*/
    private String tranDate;

    private String tranDateStr;
    /**关联方ID*/
    private String majorTrade;
    /**合同编号*/
    private String contractNo;
    /**交易金额*/
    private String tradeAmount;
    /**本年度与该关联方发生的关联交易累计金额（万元）*/
    private String totolTradeAmount;
    /**交易价格*/
    private String tradePrice;
    /**定价政策*/
    private String pricingPolicy;
    /**是否为控股子公司*/
    private String isHoldingSubsidiary;
    /**控股子公司名称*/
    private String holdingSubsidiaryName;
    /**控股子公司证件号码*/
    private String holdingSubsidiaryNumber;
    /**项目/协议名称*/
    private String contractName;
    /**实际交易金额*/

    private String pracTradeAmount;
    /**统一协议编号*/
    private String agreementNum;
    /**关联交易金额*/

    @Column(name = "REL_TRADE_AMOUNT")
    private String relTradeAmount;
    /**合同日期*/
    private String contractDate;
    private String contractDateStr;
//    String contractDate = protocolVo.getContractDate();
//    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//    protocolVo.setContractDate(f.format(contractDate));
    /**合同金额*/

    private String contractAmount;

    /**统一交易协议执行情况*/
    private String isAgreementImpl;


    private String rat;

    private String year;

    private String createdTime;

    private String agreementImpl;

    private String tranName;

    private BigDecimal accounted;

    public String getContractDateStr() {
        if (contractDate != null) {
            return DateUtils.formatDate(DateUtils.parseDate(contractDate));
        }
        return "";
    }

    public void setContractDateStr(String contractDateStr) {
        if (contractDateStr != null) {
            setContractDate(contractDateStr);
        }
    }


    public String getTranDateStr() {
        if (tranDate != null) {
            return DateUtils.formatDate(DateUtils.parseDate(tranDate));
        }
        return "";
    }

    public void setTranDateStr(String tranDateStr) {
        if (tranDateStr != null) {
            setContractDate(tranDateStr);
        }
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRat() {
        return rat;
    }

    public void setRat(String rat) {
        this.rat = rat;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getTotolTradeAmount() {
        return totolTradeAmount;
    }

    public void setTotolTradeAmount(String totolTradeAmount) {
        this.totolTradeAmount = totolTradeAmount;
    }

    public String getRelTradeAmount() {
        return relTradeAmount;
    }

    public void setRelTradeAmount(String relTradeAmount) {
        this.relTradeAmount = relTradeAmount;
    }

    public String getAgreementNum() {
        return agreementNum;
    }

    public void setAgreementNum(String agreementNum) {
        this.agreementNum = agreementNum;
    }

    public String getPracTradeAmount() {
        return pracTradeAmount;
    }

    public void setPracTradeAmount(String pracTradeAmount) {
        this.pracTradeAmount = pracTradeAmount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTradeSubject() {
        return tradeSubject;
    }

    public void setTradeSubject(String tradeSubject) {
        this.tradeSubject = tradeSubject;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
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

    public String getRelNumber() {
        return relNumber;
    }

    public void setRelNumber(String relNumber) {
        this.relNumber = relNumber;
    }

    public String getTradeOrg() {
        return tradeOrg;
    }

    public void setTradeOrg(String tradeOrg) {
        this.tradeOrg = tradeOrg;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getMajorTrade() {
        return majorTrade;
    }

    public void setMajorTrade(String majorTrade) {
        this.majorTrade = majorTrade;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getPricingPolicy() {
        return pricingPolicy;
    }

    public void setPricingPolicy(String pricingPolicy) {
        this.pricingPolicy = pricingPolicy;
    }

    public String getIsHoldingSubsidiary() {
        return isHoldingSubsidiary;
    }

    public void setIsHoldingSubsidiary(String isHoldingSubsidiary) {
        this.isHoldingSubsidiary = isHoldingSubsidiary;
    }

    public String getHoldingSubsidiaryName() {
        return holdingSubsidiaryName;
    }

    public void setHoldingSubsidiaryName(String holdingSubsidiaryName) {
        this.holdingSubsidiaryName = holdingSubsidiaryName;
    }

    public String getHoldingSubsidiaryNumber() {
        return holdingSubsidiaryNumber;
    }

    public void setHoldingSubsidiaryNumber(String holdingSubsidiaryNumber) {
        this.holdingSubsidiaryNumber = holdingSubsidiaryNumber;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getAgreementImpl() {
        return agreementImpl;
    }

    public void setAgreementImpl(String agreementImpl) {
        this.agreementImpl = agreementImpl;
    }

    public String getIsAgreementImpl() {
        return isAgreementImpl;
    }

    public void setIsAgreementImpl(String isAgreementImpl) {
        this.isAgreementImpl = isAgreementImpl;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    public BigDecimal getAccounted() {
        return accounted;
    }

    public void setAccounted(BigDecimal accounted) {
        this.accounted = accounted;
    }
}
