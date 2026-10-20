/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:15:56
 *
 */

package com.yzj.jep.app.ins.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TraInterestVo {

    @Id
    @Column(name = "PID", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "REL_ID", nullable = true, length = 10)
    private Integer relId;

    @Column(name = "REL_TYPE", nullable = true, length = 12)
    private String relType;

    @Column(name = "REL_NAME", nullable = true, length = 20)
    private String relName;

    @Column(name = "REL_NUMBER", nullable = true, length = 100)
    private String relNumber;

    @Column(name = "TRADE_ORG", nullable = true, length = 100)
    private String tradeOrg;

    @Column(name = "TRAN_TYPE", nullable = true, length = 50)
    private String tranType;

    @Column(name = "BUS_TYPE", nullable = true, length = 50)
    private String busType;

    @Column(name = "DATA_SOURCES", nullable = true, length = 10)
    private String dataSources;

    @Column(name = "TRAN_DATE", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tranDate;

    @Column(name = "MAJOR_TRADE", nullable = true, length = 10)
    private String majorTrade;

    @Column(name = "CALIBER", nullable = true, length = 50)
    private String caliber;

    @Column(name = "STATUS", nullable = true, length = 10)
    private String status;

    @Column(name = "STATUS_COMMENT", nullable = true, length = 100)
    private String statusComment;

    @Column(name = "IS_DEAL_PRICE", nullable = true, length = 5)
    private String isDealPrice;

    @Column(name = "TOTOL_TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private String totolTradeAmount;

    @Column(name = "CONTRACT_NO", nullable = true, length = 50)
    private String contractNo;

    @Column(name = "TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private String tradeAmount;

    @Column(name = "BUS_START_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date busStartTime;

    @Column(name = "BUS_END_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date busEndTime;

    @Column(name = "TRADE_OCCUR", nullable = true, length = 100)
    private String tradeOccur;

    @Column(name = "TRADE_PRICE", nullable = true, length = 1000)
    private String tradePrice;

    @Column(name = "PRICING_POLICY", nullable = true, length = 1000)
    private String pricingPolicy;

    @Column(name = "TRADE_SUBJECT", nullable = true, length = 1000)
    private String tradeSubject;

    @Column(name = "REMARK", nullable = true, length = 1000)
    private String remark;

    @Column(name = "TRANS_NUM", nullable = true, length = 32)
    private String transNum;

    @Column(name = "IS_EFFECTIVE", nullable = true, length = 32)
    private String isEffective;

    @Column(name = "APPROVE_TRADE_AMOUNT", nullable = true, length = 32)
    private String approveTradeAmount;
    @Column(name = "product_code", nullable = true, length = 20)
    private String productCode;
    @Column(name = "product_name", nullable = true, length = 50)
    private String productName;

    @Column(name = "TRAN_NAME", nullable = true, length = 10)
    private String tranName;

    @Column(name = "TRAN_OPPONENT", nullable = true, length = 10)
    private String tranOpponent;

    @Column(name = "CERT_TYPE", nullable = true, length = 10)
    private String certType;

    @Column(name = "PRAC_TRADE_AMOUNT", nullable = true, length = 10)
    private String pracTradeAmount;

    public String getPracTradeAmount() {
        return pracTradeAmount;
    }

    public void setPracTradeAmount(String pracTradeAmount) {
        this.pracTradeAmount = pracTradeAmount;
    }

    @Column(name = "Archiving_Status")
    private String archivingStatus;//1：确认逐笔披露； 2：确认逐笔报告；  3：确认季度定期披露；4.确认年度定期披露；5：确认定期报告
    @Column(name = "report_Status")
    private String reportStatus;//1：确认逐笔报告；

    @Column(name = "quarter_Arc_Status")
    private String quarterArcStatus;//1：确认季度定期披露;

    @Column(name = "year_Arc_Status")
    private String yearArcStatus;//1.确认年度定期披露；

    @Column(name = "year_Rep_Status")
    private String yearRepStatus;//1：确认定期报告
    @Column(name = "REL_TRADE_AMOUNT")
    private BigDecimal relTradeAmount;
    private String relTradeAmountString;//关联交易金额

    private String isconsiderSituation;

    private String considerSituation;

    private String tradingDirection;

    // *********************************************************************************************************
    // 三期新增字段
    // 货币代码
    @Column(name = "currency_code", nullable = true, length = 100)
    private String currencyCode;

    // 交易金额（原币）
    @Column(name = "ORIGINAL_CURRENCY_AMOUNT", nullable = true, length = 10)
    private BigDecimal originalCurrencyAmount;

    private String originalCurrencyAmountString;

    // 资金流水编号
    @Column(name = "FUNDS_FLOW_NUMBER", nullable = true, length = 10)
    private String fundsFlowNumber;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getOriginalCurrencyAmount() {
        return originalCurrencyAmount;
    }

    public void setOriginalCurrencyAmount(BigDecimal originalCurrencyAmount) {
        this.originalCurrencyAmount = originalCurrencyAmount;
    }

    public String getOriginalCurrencyAmountString() {
        return originalCurrencyAmountString;
    }

    public void setOriginalCurrencyAmountString(String originalCurrencyAmountString) {
        this.originalCurrencyAmountString = originalCurrencyAmountString;
    }

    public String getFundsFlowNumber() {
        return fundsFlowNumber;
    }

    public void setFundsFlowNumber(String fundsFlowNumber) {
        this.fundsFlowNumber = fundsFlowNumber;
    }

    // *********************************************************************************************************

    public String getTradingDirection() {
        return tradingDirection;
    }

    public void setTradingDirection(String tradingDirection) {
        this.tradingDirection = tradingDirection;
    }

    public String getIsconsiderSituation() {
        return isconsiderSituation;
    }

    public void setIsconsiderSituation(String isconsiderSituation) {
        this.isconsiderSituation = isconsiderSituation;
    }

    public String getConsiderSituation() {
        return considerSituation;
    }

    public void setConsiderSituation(String considerSituation) {
        this.considerSituation = considerSituation;
    }

    public String getRelTradeAmountString() {
        return relTradeAmountString;
    }

    public void setRelTradeAmountString(String relTradeAmountString) {
        this.relTradeAmountString = relTradeAmountString;
    }

    public BigDecimal getRelTradeAmount() {
        return relTradeAmount;
    }

    public void setRelTradeAmount(BigDecimal relTradeAmount) {
        this.relTradeAmount = relTradeAmount;
    }

    @Column(name = "concentrated_approval")
    private String concentratedApproval;//是否是集中审议 0 是，1否

    @Column(name = "not_major_rel_number")
    private String notMajorRelNumber;//未清零的关联方relNumber

    @Column(name = "agreement_num", length = 32)
    private String agreementNum;//主协议编号
    @Column(name = "quarter_Rep_Status")
    private String quarterRepStatus;//1：确认季度定期报告

    public String getQuarterRepStatus() {
        return quarterRepStatus;
    }

    public void setQuarterRepStatus(String quarterRepStatus) {
        this.quarterRepStatus = quarterRepStatus;
    }

    public String getAgreementNum() {
        return agreementNum;
    }

    public void setAgreementNum(String agreementNum) {
        this.agreementNum = agreementNum;
    }

    public String getConcentratedApproval() {
        return concentratedApproval;
    }

    public void setConcentratedApproval(String concentratedApproval) {
        this.concentratedApproval = concentratedApproval;
    }

    public String getNotMajorRelNumber() {
        return notMajorRelNumber;
    }

    public void setNotMajorRelNumber(String notMajorRelNumber) {
        this.notMajorRelNumber = notMajorRelNumber;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getQuarterArcStatus() {
        return quarterArcStatus;
    }

    public void setQuarterArcStatus(String quarterArcStatus) {
        this.quarterArcStatus = quarterArcStatus;
    }

    public String getYearArcStatus() {
        return yearArcStatus;
    }

    public void setYearArcStatus(String yearArcStatus) {
        this.yearArcStatus = yearArcStatus;
    }

    public String getYearRepStatus() {
        return yearRepStatus;
    }

    public void setYearRepStatus(String yearRepStatus) {
        this.yearRepStatus = yearRepStatus;
    }

    public String getArchivingStatus() {
        return archivingStatus;
    }

    public void setArchivingStatus(String archivingStatus) {
        this.archivingStatus = archivingStatus;
    }


    @Transient
    private String tranDateString;

    @Transient
    private String busStartTimeString;

    @Transient
    private String busEndTimeString;

    @Transient
    private String traSubNameStr;
    @Transient
    private String relTypeStr;
    @Transient
    private String busTypeStr;

    public String getActualTotal() {
        return actualTotal;
    }

    public void setActualTotal(String actualTotal) {
        this.actualTotal = actualTotal;
    }

    @Transient
    private String majorTradeStr;
    @Transient
    private String statusStr;
    @Transient
    private String MajorTypeStr;

    /**
     * 交易对手（关联方）列表
     */
    @Transient
    private List<TraRelInfo> relInfoList;

    @Column(name = "rel_info_list_str", nullable = true, length = 1000)
    private String relInfoListStr;


    public List<TraRelInfo> getRelInfoList() {
        return relInfoList;
    }

    public void setRelInfoList(List<TraRelInfo> relInfoList) {
        this.relInfoList = relInfoList;
    }

    public String getRelInfoListStr() {
        return relInfoListStr;
    }

    public void setRelInfoListStr(String relInfoListStr) {
        this.relInfoListStr = relInfoListStr;
    }

    //归零操作统计金额
    @Transient
    private String tradeZeroAmount;

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    public String getTranOpponent() {
        return tranOpponent;
    }

    public void setTranOpponent(String tranOpponent) {
        this.tranOpponent = tranOpponent;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getTradeZeroAmount() {
        return tradeZeroAmount;
    }

    public void setTradeZeroAmount(String tradeZeroAmount) {
        this.tradeZeroAmount = tradeZeroAmount;
    }

    public String getBusTypeStr() {
        return busTypeStr;
    }

    public void setBusTypeStr(String busTypeStr) {
        this.busTypeStr = busTypeStr;
    }

    public String getMajorTypeStr() {
        return MajorTypeStr;
    }

    public void setMajorTypeStr(String majorTypeStr) {
        MajorTypeStr = majorTypeStr;
    }

    public String getTraSubNameStr() {
        return traSubNameStr;
    }

    public void setTraSubNameStr(String traSubNameStr) {
        this.traSubNameStr = traSubNameStr;
    }

    public String getRelTypeStr() {
        return relTypeStr;
    }

    public void setRelTypeStr(String relTypeStr) {
        this.relTypeStr = relTypeStr;
    }


    public String getMajorTradeStr() {
        return majorTradeStr;
    }

    public void setMajorTradeStr(String majorTradeStr) {
        this.majorTradeStr = majorTradeStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }


    @Column(name = "RULE_RESULT", nullable = true, length = 32)
    private String ruleResult;

    @Column(name = "is_HoldingSubsidiary", nullable = true, length = 20)
    private String isHoldingSubsidiary;

    @Column(name = "HoldingSubsidiary_name", nullable = true, length = 100)
    private String holdingSubsidiaryName;

    @Column(name = "HoldingSubsidiary_number", nullable = true, length = 10)
    private String holdingSubsidiaryNumber;

    @Column(name = "totalAssets", nullable = true, length = 100)
    private String totalAssets;

    @Column(name = "LAST_TRADE_PRICE", nullable = true, length = 10)
    private String lastTradePrice;

    @Column(name = "LAST_TRADE_TIME", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastTradeTime;

    @Column(name = "is_agreement", nullable = true, length = 6)
    private String isAgreement;

    @Column(name = "UNITF_TRADE_AGREMENT_INTEREST", nullable = true, length = 10)
    private String unitfTradeAgrmentInterest;

    @Column(name = "Unit_Tra_Agreement_Num", nullable = true, length = 50)
    private String unitTraAgreementNum;

    @Column(name = "Guarantee_conditions", nullable = true, length = 10)
    private String guaranteeConditions;

    @Column(name = "consider_type", nullable = true, length = 10)
    private String considerType;

    @Column(name = "TRADE_SUBJECT_ASSETS", nullable = true, length = 1000)
    private String tradeSubjectAssets;

    @Column(name = "Financial_Impact", nullable = true, length = 400)
    private String financialImpact;

    @Column(name = "Tra_sub_Name", nullable = true, length = 20)
    private String traSubName;

    @Column(name = "Major_Type", nullable = true, length = 10)
    private String majorType;

    @Column(name = "SUBJECT_SITUATION", nullable = true, length = 1000)
    private String subjectSituation;
    @Column(name = "HOLDINGSUBSIDIARY_TYPE", nullable = true, length = 20)
    private String holdingSubsidiaryType;
    @Column(name = "IS_CASHSUBSCRIPTION", nullable = true, length = 10)
    private String isCashSubscription;

    @Column(name = "Contract_Amount", nullable = true, precision = 50, scale = 0)
    private String contractAmount;

    @Column(name = "AMEND_ID", nullable = true, length = 10)
    private String amendId;

    @Column(name = "AMEND_NAME", nullable = true, length = 10)
    private String amendName;

    @Column(name = "actual_Total")
    private String actualTotal;
    @Column(name = "contract_Date")
    private Date contractDate;
    @Column(name = "contra_Total")
    private String contraTotal;
    @Column(name = "is_Agreement_Impl")
    private String isAgreementImpl;


    @Column(name = "tradeAgreement_Number")
    private String tradeAgreementNumber;

    @Column(name = "manager_org")
    private String managerOrg;


    public String getManagerOrg() {
        return managerOrg;
    }

    public void setManagerOrg(String managerOrg) {
        this.managerOrg = managerOrg;
    }

    //columns END
    @Transient
    private String lastTradeTimeString;

    public String getLastTradeTimeString() {
        if (lastTradeTime != null) {
            return DateUtils.formatDate(lastTradeTime);
        }
        return "";
    }

    //EXCEL 业务类型
    @Transient
    private String investmentType;

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    public void setLastTradeTimeString(String lastTradeTimeString) {
        this.lastTradeTimeString = lastTradeTimeString;
    }


    public String getAmendId() {
        return amendId;
    }

    public void setAmendId(String amendId) {
        this.amendId = amendId;
    }

    public String getAmendName() {
        return amendName;
    }

    public void setAmendName(String amendName) {
        this.amendName = amendName;
    }

    public String getIsCashSubscription() {
        return isCashSubscription;
    }

    public void setIsCashSubscription(String isCashSubscription) {
        this.isCashSubscription = isCashSubscription;
    }


    public String getHoldingSubsidiaryType() {
        return holdingSubsidiaryType;
    }

    public void setHoldingSubsidiaryType(String holdingSubsidiaryType) {
        this.holdingSubsidiaryType = holdingSubsidiaryType;
    }

    public String getSubjectSituation() {
        return subjectSituation;
    }

    public void setSubjectSituation(String subjectSituation) {
        this.subjectSituation = subjectSituation;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getRelId() {
        return this.relId;
    }

    public void setRelId(Integer value) {
        this.relId = value;
    }


    public String getRelType() {
        return this.relType;
    }

    public void setRelType(String value) {
        this.relType = value;
    }


    public String getRelName() {
        return this.relName;
    }

    public void setRelName(String value) {
        this.relName = value;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRelNumber() {
        return this.relNumber;
    }

    public void setRelNumber(String value) {
        this.relNumber = value;
    }


    public String getTradeOrg() {
        return this.tradeOrg;
    }

    public void setTradeOrg(String value) {
        this.tradeOrg = value;
    }


    public String getTranType() {
        return this.tranType;
    }

    public void setTranType(String value) {
        this.tranType = value;
    }


    public String getBusType() {
        return this.busType;
    }

    public void setBusType(String value) {
        this.busType = value;
    }


    public String getDataSources() {
        return this.dataSources;
    }

    public void setDataSources(String value) {
        this.dataSources = value;
    }


    public Date getTranDate() {
        return this.tranDate;
    }

    public void setTranDate(Date value) {
        this.tranDate = value;
    }


    public String getMajorTrade() {
        return this.majorTrade;
    }

    public void setMajorTrade(String value) {
        this.majorTrade = value;
    }


    public String getCaliber() {
        return this.caliber;
    }

    public void setCaliber(String value) {
        this.caliber = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public String getStatusComment() {
        return this.statusComment;
    }

    public void setStatusComment(String value) {
        this.statusComment = value;
    }

    public String getIsDealPrice() {
        return isDealPrice;
    }

    public void setIsDealPrice(String isDealPrice) {
        this.isDealPrice = isDealPrice;
    }

    public String getTotolTradeAmount() {
        return this.totolTradeAmount;
    }

    public void setTotolTradeAmount(String totolTradeAmount) {
        this.totolTradeAmount = totolTradeAmount;
    }


    public String getContractNo() {
        return this.contractNo;
    }

    public void setContractNo(String value) {
        this.contractNo = value;
    }


    public String getTradeAmount() {
        return this.tradeAmount;
    }

    public void setTradeAmount(String value) {
        this.tradeAmount = value;
    }


    public Date getBusStartTime() {
        return this.busStartTime;
    }

    public void setBusStartTime(Date value) {
        this.busStartTime = value;
    }


    public Date getBusEndTime() {
        return this.busEndTime;
    }

    public void setBusEndTime(Date value) {
        this.busEndTime = value;
    }


    public String getTradeOccur() {
        return this.tradeOccur;
    }

    public void setTradeOccur(String value) {
        this.tradeOccur = value;
    }


    public String getTradePrice() {
        return this.tradePrice;
    }

    public void setTradePrice(String value) {
        this.tradePrice = value;
    }


    public String getPricingPolicy() {
        return this.pricingPolicy;
    }

    public void setPricingPolicy(String value) {
        this.pricingPolicy = value;
    }


    public String getTradeSubject() {
        return this.tradeSubject;
    }

    public void setTradeSubject(String value) {
        this.tradeSubject = value;
    }


    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }


    public String getTransNum() {
        return this.transNum;
    }

    public void setTransNum(String value) {
        this.transNum = value;
    }

    public String getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(String isEffective) {
        this.isEffective = isEffective;
    }

    public String getApproveTradeAmount() {
        return approveTradeAmount;
    }

    public void setApproveTradeAmount(String approveTradeAmount) {
        this.approveTradeAmount = approveTradeAmount;
    }

    public String getUnitTraAgreementNum() {
        return unitTraAgreementNum;
    }

    public void setUnitTraAgreementNum(String unitTraAgreementNum) {
        this.unitTraAgreementNum = unitTraAgreementNum;
    }

    public String getTranDateString() {
        if (tranDate != null) {
            return DateUtils.formatDate(tranDate);
        }
        return "";
    }

    public void setTranDateString(String tranDateString) {
        if (tranDateString != null) {
            setTranDate(DateUtils.parseDate(tranDateString));
        }
    }

    public String getBusStartTimeString() {
        return busStartTimeString;
    }

    public void setBusStartTimeString(String busStartTimeString) {
        this.busStartTimeString = busStartTimeString;
    }

    public String getBusEndTimeString() {
        return busEndTimeString;
    }

    public String getConsiderType() {
        return considerType;
    }

    public void setConsiderType(String considerType) {
        this.considerType = considerType;
    }

    public void setBusEndTimeString(String busEndTimeString) {
        this.busEndTimeString = busEndTimeString;
    }

    public String getRuleResult() {
        return ruleResult;
    }

    public void setRuleResult(String ruleResult) {
        this.ruleResult = ruleResult;
    }

    public String getTradeSubjectAssets() {
        return tradeSubjectAssets;
    }

    public String getFinancialImpact() {
        return financialImpact;
    }

    public void setFinancialImpact(String financialImpact) {
        this.financialImpact = financialImpact;
    }

    public String getTraSubName() {
        return traSubName;
    }

    public void setTraSubName(String traSubName) {
        this.traSubName = traSubName;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }


    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public String getContraTotal() {
        return contraTotal;
    }

    public void setContraTotal(String contraTotal) {
        this.contraTotal = contraTotal;
    }

    public String getIsAgreementImpl() {
        return isAgreementImpl;
    }

    public void setIsAgreementImpl(String isAgreementImpl) {
        this.isAgreementImpl = isAgreementImpl;
    }

    public String getTradeAgreementNumber() {
        return tradeAgreementNumber;
    }

    public void setTradeAgreementNumber(String tradeAgreementNumber) {
        this.tradeAgreementNumber = tradeAgreementNumber;
    }

//    public String getLastTradeTimeString() {
//        return lastTradeTimeString;
//    }
//
//    public void setLastTradeTimeString(String lastTradeTimeString) {
//        this.lastTradeTimeString = lastTradeTimeString;
//    }

    public String getContractDateString() {
        if (contractDate != null) {
            return DateUtils.formatDate(contractDate);
        }
        return "";
    }

    public void setContractDateString(String contractDateString) {
        if (contractDateString != null) {
            setTranDate(DateUtils.parseDate(contractDateString));
            setContractDate(DateUtils.parseDate(contractDateString));
        }
    }


    @Override
    public String toString() {
        return "TraInterest{" +
                "pid=" + pid +
                ", relId=" + relId +
                ", relType='" + relType + '\'' +
                ", relName='" + relName + '\'' +
                ", relNumber='" + relNumber + '\'' +
                ", tradeOrg='" + tradeOrg + '\'' +
                ", tranType='" + tranType + '\'' +
                ", busType='" + busType + '\'' +
                ", dataSources='" + dataSources + '\'' +
                ", tranDate=" + tranDate +
                ", majorTrade='" + majorTrade + '\'' +
                ", caliber='" + caliber + '\'' +
                ", status='" + status + '\'' +
                ", statusComment='" + statusComment + '\'' +
                ", isDealPrice='" + isDealPrice + '\'' +
                ", totolTradeAmount=" + totolTradeAmount +
                ", contractNo='" + contractNo + '\'' +
                ", tradeAmount=" + tradeAmount +
                ", busStartTime=" + busStartTime +
                ", busEndTime=" + busEndTime +
                ", tradeOccur='" + tradeOccur + '\'' +
                ", tradePrice='" + tradePrice + '\'' +
                ", pricingPolicy='" + pricingPolicy + '\'' +
                ", tradeSubject='" + tradeSubject + '\'' +
                ", remark='" + remark + '\'' +
                ", transNum='" + transNum + '\'' +
                ", isEffective='" + isEffective + '\'' +
                ", approveTradeAmount='" + approveTradeAmount + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", tranDateString='" + tranDateString + '\'' +
                ", busStartTimeString='" + busStartTimeString + '\'' +
                ", busEndTimeString='" + busEndTimeString + '\'' +
                ", traSubNameStr='" + traSubNameStr + '\'' +
                ", relTypeStr='" + relTypeStr + '\'' +
                ", busTypeStr='" + busTypeStr + '\'' +
                ", majorTradeStr='" + majorTradeStr + '\'' +
                ", statusStr='" + statusStr + '\'' +
                ", MajorTypeStr='" + MajorTypeStr + '\'' +
                ", ruleResult='" + ruleResult + '\'' +
                ", isHoldingSubsidiary='" + isHoldingSubsidiary + '\'' +
                ", holdingSubsidiaryName='" + holdingSubsidiaryName + '\'' +
                ", holdingSubsidiaryNumber='" + holdingSubsidiaryNumber + '\'' +
                ", totalAssets='" + totalAssets + '\'' +
                ", lastTradePrice='" + lastTradePrice + '\'' +
                ", lastTradeTime=" + lastTradeTime +
                ", isAgreement='" + isAgreement + '\'' +
                ", unitfTradeAgrmentInterest='" + unitfTradeAgrmentInterest + '\'' +
                ", guaranteeConditions='" + guaranteeConditions + '\'' +
                ", considerType='" + considerType + '\'' +
                ", tradeSubjectAssets='" + tradeSubjectAssets + '\'' +
                ", financialImpact='" + financialImpact + '\'' +
                ", traSubName='" + traSubName + '\'' +
                ", majorType='" + majorType + '\'' +
                ", subjectSituation='" + subjectSituation + '\'' +
                ", holdingSubsidiaryType='" + holdingSubsidiaryType + '\'' +
                ", isCashSubscription='" + isCashSubscription + '\'' +
                ", contractAmount=" + contractAmount +
                ", amendId='" + amendId + '\'' +
                ", amendName='" + amendName + '\'' +
                '}';
    }

    public void setTradeSubjectAssets(String tradeSubjectAssets) {
        this.tradeSubjectAssets = tradeSubjectAssets;
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

    public String getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(String totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getLastTradePrice() {
        return lastTradePrice;
    }

    public void setLastTradePrice(String lastTradePrice) {
        this.lastTradePrice = lastTradePrice;
    }

    public Date getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(Date lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public String getIsAgreement() {
        return isAgreement;
    }

    public void setIsAgreement(String isAgreement) {
        this.isAgreement = isAgreement;
    }

    public String getUnitfTradeAgrmentInterest() {
        return unitfTradeAgrmentInterest;
    }

    public void setUnitfTradeAgrmentInterest(String unitfTradeAgrmentInterest) {
        this.unitfTradeAgrmentInterest = unitfTradeAgrmentInterest;
    }

    public String getGuaranteeConditions() {
        return guaranteeConditions;
    }

    public void setGuaranteeConditions(String guaranteeConditions) {
        this.guaranteeConditions = guaranteeConditions;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TraInterest == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraInterest other = (TraInterest) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }

    private String no;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}

