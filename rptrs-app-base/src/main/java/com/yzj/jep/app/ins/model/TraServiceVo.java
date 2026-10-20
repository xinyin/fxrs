/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:16:10
 *
 */

package com.yzj.jep.app.ins.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class TraServiceVo extends BasePO {



    private Integer pid;


    private Integer relId;


    private String relType;


    private String relName;


    private String relNumber;


    private String tradeOrg;

    @Column(name = "TRAN_TYPE", nullable = true, length = 50)
    private String tranType;

    @Column(name = "BUS_TYPE", nullable = true, length = 50)
    private String busType;

    @Column(name = "DATA_SOURCES", nullable = true, length = 10)
    private String dataSources;

    private Date tranDate;

    @Column(name = "MAJOR_TRADE", nullable = true, length = 10)
    private String majorTrade;

    @Column(name = "CALIBER", nullable = true, length = 50)
    private String caliber;

    @Column(name = "STATUS", nullable = true, length = 10)
    private String status;

    private String unitfTradeAgrmentAbusiness;

    @Column(name = "STATUS_COMMENT", nullable = true, length = 100)
    private String statusComment;

    @Column(name = "IS_DEAL_PRICE", nullable = true, length = 5)
    private String isDealPrice;

    @Column(name = "TOTOL_TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private BigDecimal totolTradeAmount;

    @Column(name = "CONTRACT_NO", nullable = true, length = 50)
    private String contractNo;
    @Column(name = "IS_CASH_SUBSCRIBE", nullable = true, length = 10)
    private String iscashsubscribe;
    @Column(name = "TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
    private BigDecimal tradeAmount;


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

    @Column(name = "RULE_RESULT", nullable = true, length = 32)
    private String ruleResult;
    @Column(name = "PRODUCT_CODE", nullable = true, length = 20)
    private String productCode;
    @Column(name = "PRODUCT_NAME", nullable = true, length = 50)
    private String productName;
    //是否为控股子公司
    @Column(name = "is_HoldingSubsidiary", nullable = true, length = 20)
    private String isHoldingSubsidiary;

    @Column(name = "HoldingSubsidiary_name", nullable = true, length = 100)
    private String holdingSubsidiaryName;

    @Column(name = "HoldingSubsidiary_number", nullable = true, length = 10)
    private String holdingSubsidiaryNumber;

    @Column(name = "totalAssets", nullable = true, length = 100)
    private String totalAssets;

    @Column(name = "AMEND_ID", nullable = true, length = 10)
    private String amendId;

    @Column(name = "AMEND_NAME", nullable = true, length = 10)
    private String amendName;


    //    @Column(name = "IN_TIME", nullable = true, length = 19)
//    @Temporal(TemporalType.DATE)
//    private Date inTime;
    @Column(name = "is_agreement", nullable = true, length = 6)
    private String isAgreement;

    @Column(name = "consider_type", nullable = true, length = 10)
    private String considerType;

    @Column(name = "Trading_direction", nullable = true, length = 6)
    private String tradingDirection;

    @Column(name = "trade_agreement_number", nullable = true, length = 100)
    private String tradeAgreementNumber;

    @Column(name = "Financial_Impact", nullable = true, length = 400)
    private String financialImpact;

    @Column(name = "Tra_sub_Name", nullable = true, length = 20)
    private String traSubName;

    @Column(name = "Major_Type", nullable = true, length = 10)
    private String majorType;

    @Column(name = "Contract_Amount", nullable = true, precision = 50, scale = 0)
    private BigDecimal contractAmount;


    @Column(name = "HoldingSubsidiary_TYPE", nullable = true, length = 100)
    private String holdingSubsidiaryTYPE;

//    @Column(name = "LastSeasonAssets", nullable = true, length = 50)
//    private String lastSeasonAssets;

    //columns END
    @Column(name = "TRAN_NAME", nullable = true, length = 10)
    private String tranName;

    @Column(name = "TRAN_OPPONENT", nullable = true, length = 10)
    private String tranOpponent;

    @Column(name = "CERT_TYPE", nullable = true, length = 10)
    private String certType;

    @Column(name="actual_total")
    private BigDecimal actualTotal;

    @Column(name="contract_Date")
    private Date contractDate;

    @Column(name="contra_Total")
    private BigDecimal contraTotal;


    @Column(name="is_Agreement_Impl")
    private String isAgreementImpl;
    @Column(name = "PRAC_TRADE_AMOUNT", nullable = true, length = 10)
    private String pracTradeAmount;

    public String getPracTradeAmount() {
        return pracTradeAmount;
    }

    public void setPracTradeAmount(String pracTradeAmount) {
        this.pracTradeAmount = pracTradeAmount;
    }

    @Column(name="Archiving_Status")
    private String archivingStatus ;//1：确认逐笔披露； 2：确认逐笔报告；  3：确认季度定期披露；4.确认年度定期披露；5：确认定期报告
    @Column(name="report_Status")
    private String reportStatus;//1：确认逐笔报告；

    @Column(name="quarter_Arc_Status")
    private String quarterArcStatus;//1：确认季度定期披露;

    @Column(name="year_Arc_Status")
    private String yearArcStatus;//1.确认年度定期披露；

    @Column(name="year_Rep_Status")
    private String yearRepStatus;//1：确认定期报告
    @Column(name = "REL_TRADE_AMOUNT")
    private String relTradeAmount;
    @Column(name = "ACCU_PRAC_TRADE_AMOUNT")
    private BigDecimal accuPracTradeAmount;

    public String getRelTradeAmount() {
        return relTradeAmount;
    }

    public void setRelTradeAmount(String relTradeAmount) {
        this.relTradeAmount = relTradeAmount;
    }

    public BigDecimal getAccuPracTradeAmount() {
        return accuPracTradeAmount;
    }

    public void setAccuPracTradeAmount(BigDecimal accuPracTradeAmount) {
        this.accuPracTradeAmount = accuPracTradeAmount;
    }

    @Column(name="concentrated_approval")
    private String concentratedApproval;//是否是集中审议 0 是，1否

    @Column(name="not_major_rel_number")
    private String notMajorRelNumber;//未清零的关联方relNumber

    @Column(name="agreement_num", length = 32)
    private String agreementNum;//主协议编号
    @Column(name="quarter_Rep_Status")
    private String quarterRepStatus;//1：确认季度定期报告
    //是否统一交易协议
    @Column(name = "is_trade_agreement",length = 6)
    private String isTradeAgreement;

    @Column(name = "proc_inst_id", nullable = true, length = 10)
    private String procInstId;

    @Column(name = "effectOrPay", nullable = true, length = 10)
    private String effectOrPay;




    /* 复星保险二期需求改动 */
    @Column(name = "IS_ACCUMULATED", nullable = true, length = 10)
    private String isAccumulated;
    /** 单笔关联交易类型 */
    @Column(name = "single_related_trantype", nullable = true, length = 1)
    private String singleRelatedTranType;
    /** 是否需逐笔报告 */
    @Column(name = "each_report", nullable = true, length = 30)
    private String eachReport;
    /** 是否需逐笔披露 */
    @Column(name = "each_disclosure", nullable = true, length = 30)
    private String eachDisclosure;
    /**  是否需要补录关联方信息 */
    @Column(name = "is_collect_related", nullable = true, length = 1)
    private String isCollectRelated;
    /** 是否可免于审议 */
    @Column(name = "is_consider_situation", nullable = true, length = 50)
    private String isconsiderSituation;
    /** 免于审议情形 */
    @Column(name = "consider_situation", nullable = true, length = 50)
    private String considerSituation;

    // 统一交易协议类型
    @Column(name = "trade_agreement_type", nullable = true, length = 1)
    private String tradeAgreementType;

    // 部门
    @Column(name = "dept_name", nullable = true, length = 100)
    private String deptName;

    public String getSingleRelatedTranType() {
        return singleRelatedTranType;
    }

    public void setSingleRelatedTranType(String singleRelatedTranType) {
        this.singleRelatedTranType = singleRelatedTranType;
    }

    public String getEachReport() {
        return eachReport;
    }

    public void setEachReport(String eachReport) {
        this.eachReport = eachReport;
    }

    public String getEachDisclosure() {
        return eachDisclosure;
    }

    public void setEachDisclosure(String eachDisclosure) {
        this.eachDisclosure = eachDisclosure;
    }

    public String getIsCollectRelated() {
        return isCollectRelated;
    }

    public void setIsCollectRelated(String isCollectRelated) {
        this.isCollectRelated = isCollectRelated;
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

//columns END


    public String getIsAccumulated() {
        return isAccumulated;
    }

    public void setIsAccumulated(String isAccumulated) {
        this.isAccumulated = isAccumulated;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }
    public String getIsTradeAgreement() {
        return isTradeAgreement;
    }

    public void setIsTradeAgreement(String isTradeAgreement) {
        this.isTradeAgreement = isTradeAgreement;
    }

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

    /**
     * 交易对手（关联方）列表
     */
    @Transient
    private List<TraRelInfo> relInfoList;

    @Column(name = "rel_info_list_str", nullable = true, length = 1000)
    private String relInfoListStr;
    @Column(name="manager_org")
    private String managerOrg;

    public String getManagerOrg() {
        return managerOrg;
    }

    public void setManagerOrg(String managerOrg) {
        this.managerOrg = managerOrg;
    }

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
    @Transient
    private String majorTradeStr;
    @Transient
    private String statusStr;
    @Transient
    private String majorTypeStr;
    //归零操作统计金额
    @Transient
    private java.math.BigDecimal tradeZeroAmount;
    //EXCEL 业务类型
    @Transient
    private String investmentType;

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    public java.math.BigDecimal getTradeZeroAmount() {
        return tradeZeroAmount;
    }

    public void setTradeZeroAmount(java.math.BigDecimal tradeZeroAmount) {
        this.tradeZeroAmount = tradeZeroAmount;
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

    public String getBusTypeStr() {
        return busTypeStr;
    }

    public void setBusTypeStr(String busTypeStr) {
        this.busTypeStr = busTypeStr;
    }

    public String getMajorTypeStr() {
        return majorTypeStr;
    }

    public void setMajorTypeStr(String majorTypeStr) {
        this.majorTypeStr = majorTypeStr;
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

    public String getBusTypStr() {
        return busTypeStr;
    }

    public void setBusTypStr(String busTypStr) {
        this.busTypeStr = busTypStr;
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




    @Column(name = "payment_id", nullable = true, length = 10)
    private String paymentId;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getSourceModule() {
        return sourceModule;
    }

    public void setSourceModule(String sourceModule) {
        this.sourceModule = sourceModule;
    }

    @Column(name = "scene", nullable = true, length = 10)
    private String scene;

    @Column(name = "transaction_name", nullable = true, length = 100)
    private String transactionName;

    @Column(name = "transaction_description", nullable = true, length = 1000)
    private String transactionDescription;
    @Column(name = "unique_code", nullable = true, length = 100)
    private String uniqueCode;

    @Column(name = "source_system", nullable = true, length = 60)
    private String sourceSystem;

    @Column(name = "source_module", nullable = true, length = 60)
    private String sourceModule;


    //协议交易标志
    @Column(name = "PROTOCOL_TRANSACTION_SYMBOL", nullable = true, length = 100)
    private String protocolTransactionSymbol;

    //合同状态
    @Column(name = "CONTRACT_STATUS", nullable = true, length = 100)
    private String contractStatus;

    // 数据版本
    @Column(name = "data_version", nullable = true, length = 64)
    private String dataVersion;

    @Column(name = "FUNDS_FLOW_NUMBER", nullable = true, length = 100)
    private String fundsFlowNumber;

    public String getFundsFlowNumber() {
        return fundsFlowNumber;
    }

    public void setFundsFlowNumber(String fundsFlowNumber) {
        this.fundsFlowNumber = fundsFlowNumber;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getProtocolTransactionSymbol() {
        return protocolTransactionSymbol;
    }

    public void setProtocolTransactionSymbol(String protocolTransactionSymbol) {
        this.protocolTransactionSymbol = protocolTransactionSymbol;
    }

//    @Transient
//    private String inTimeString;


    public String getTranDateString() {
        if (tranDate != null) {
            return DateUtils.formatDate(tranDate);
        }
        return "";
    }

    public void setTranDateString(String value) {
        if (value != null) {
            setTranDate(DateUtils.parseDate(value));
        }
    }

    public String getBusStartTimeString() {
        if (busStartTime != null) {
            return DateUtils.formatDate(busStartTime);
        }
        return "";
    }

    public void setBusStartTimeString(String value) {
        if (value != null) {
            setBusStartTime(DateUtils.parseDate(value));
        }
    }

    public String getBusEndTimeString() {
        if (busEndTime != null) {
            return DateUtils.formatDate(busEndTime);
        }
        return "";
    }

    public void setBusEndTimeString(String value) {
        if (value != null) {
            setBusEndTime(DateUtils.parseDate(value));
        }
    }

//    public String getInTimeString() {
//        if (inTime != null) {
//            return DateUtils.formatDate(inTime);
//        }
//        return "";
//    }
//
//    public void setInTimeString(String value) {
//        if (value != null) {
//            setInTime(DateUtils.parseDate(value));
//        }
//    }
//
//    public Date getInTime() {
//        return this.inTime;
//    }
//
//    public void setInTime(Date value) {
//        this.inTime = value;
//    }

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

    public String getIscashsubscribe() {
        return iscashsubscribe;
    }

    public void setIscashsubscribe(String iscashsubscribe) {
        this.iscashsubscribe = iscashsubscribe;
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

    public String getUnitfTradeAgrmentAbusiness() {
        return unitfTradeAgrmentAbusiness;
    }

    public void setUnitfTradeAgrmentAbusiness(String unitfTradeAgrmentAbusiness) {
        this.unitfTradeAgrmentAbusiness = unitfTradeAgrmentAbusiness;
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

    public BigDecimal getTotolTradeAmount() {
        return this.totolTradeAmount;
    }

    public void setTotolTradeAmount(BigDecimal value) {
        this.totolTradeAmount = value;
    }

    public String getContractNo() {
        return this.contractNo;
    }

    public void setContractNo(String value) {
        this.contractNo = value;
    }


    public BigDecimal getTradeAmount() {
        return this.tradeAmount;
    }

    public void setTradeAmount(BigDecimal value) {
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
    @Transient
    private String creater;

    public String getCreater() {
        if ("System".equals(getAmendName())){
            return "其它系统";
        }else{
            creater = amendId+"-"+amendName;
            return creater;
        }
    }

    public void setCreater(String creater) {
        this.creater = creater;
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

    public String getRuleResult() {
        return ruleResult;
    }

    public void setRuleResult(String ruleResult) {
        this.ruleResult = ruleResult;
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

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
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


    public String getConsiderType() {
        return considerType;
    }

    public void setConsiderType(String considerType) {
        this.considerType = considerType;
    }

    public String getTradingDirection() {
        return tradingDirection;
    }

    public void setTradingDirection(String tradingDirection) {
        this.tradingDirection = tradingDirection;
    }

    public String getTradeAgreementNumber() {
        return tradeAgreementNumber;
    }

    public void setTradeAgreementNumber(String tradeAgreementNumber) {
        this.tradeAgreementNumber = tradeAgreementNumber;
    }

    public String getHoldingSubsidiaryTYPE() {
        return holdingSubsidiaryTYPE;
    }

    public void setHoldingSubsidiaryTYPE(String holdingSubsidiaryTYPE) {
        this.holdingSubsidiaryTYPE = holdingSubsidiaryTYPE;
    }

    public String getIsAgreement() {
        return isAgreement;
    }

    public void setIsAgreement(String isAgreement) {
        this.isAgreement = isAgreement;
    }

    public String getIsAgreementImpl() {
        return isAgreementImpl;
    }

    public void setIsAgreementImpl(String isAgreementImpl) {
        this.isAgreementImpl = isAgreementImpl;
    }

    public BigDecimal getActualTotal() {
        return actualTotal;
    }

    public void setActualTotal(BigDecimal actualTotal) {
        this.actualTotal = actualTotal;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public String getEffectOrPay() {
        return effectOrPay;
    }

    public void setEffectOrPay(String effectOrPay) {
        this.effectOrPay = effectOrPay;
    }


    public BigDecimal getContraTotal() {
        return contraTotal;
    }
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

    public void setContraTotal(BigDecimal contraTotal) {
        this.contraTotal = contraTotal;
    }
    //    public String getLastSeasonAssets() {
//        return lastSeasonAssets;
//    }
//
//    public void setLastSeasonAssets(String lastSeasonAssets) {
//        this.lastSeasonAssets = lastSeasonAssets;
//    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TraService == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraService other = (TraService) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }

    @Override
    public String toString() {
        return "TraService{" +
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
                ", unitfTradeAgrmentAbusiness='" + unitfTradeAgrmentAbusiness + '\'' +
                ", statusComment='" + statusComment + '\'' +
                ", isDealPrice='" + isDealPrice + '\'' +
                ", totolTradeAmount=" + totolTradeAmount +
                ", contractNo='" + contractNo + '\'' +
                ", iscashsubscribe='" + iscashsubscribe + '\'' +
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
                ", ruleResult='" + ruleResult + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", isHoldingSubsidiary='" + isHoldingSubsidiary + '\'' +
                ", holdingSubsidiaryName='" + holdingSubsidiaryName + '\'' +
                ", holdingSubsidiaryNumber='" + holdingSubsidiaryNumber + '\'' +
                ", totalAssets='" + totalAssets + '\'' +
                ", amendId='" + amendId + '\'' +
                ", amendName='" + amendName + '\'' +
                ", isAgreement='" + isAgreement + '\'' +
                ", considerType='" + considerType + '\'' +
                ", tradingDirection='" + tradingDirection + '\'' +
                ", tradeAgreementNumber='" + tradeAgreementNumber + '\'' +
                ", financialImpact='" + financialImpact + '\'' +
                ", traSubName='" + traSubName + '\'' +
                ", majorType='" + majorType + '\'' +
                ", contractAmount=" + contractAmount +
                ", holdingSubsidiaryTYPE='" + holdingSubsidiaryTYPE + '\'' +
                ", tranName='" + tranName + '\'' +
                ", tranOpponent='" + tranOpponent + '\'' +
                ", certType='" + certType + '\'' +
                ", actualTotal=" + actualTotal +
                ", contractDate=" + contractDate +
                ", contraTotal=" + contraTotal +
                ", isAgreementImpl='" + isAgreementImpl + '\'' +
                ", pracTradeAmount='" + pracTradeAmount + '\'' +
                ", archivingStatus='" + archivingStatus + '\'' +
                ", reportStatus='" + reportStatus + '\'' +
                ", quarterArcStatus='" + quarterArcStatus + '\'' +
                ", yearArcStatus='" + yearArcStatus + '\'' +
                ", yearRepStatus='" + yearRepStatus + '\'' +
                ", relTradeAmount=" + relTradeAmount +
                ", accuPracTradeAmount=" + accuPracTradeAmount +
                ", concentratedApproval='" + concentratedApproval + '\'' +
                ", notMajorRelNumber='" + notMajorRelNumber + '\'' +
                ", agreementNum='" + agreementNum + '\'' +
                ", quarterRepStatus='" + quarterRepStatus + '\'' +
                ", isTradeAgreement='" + isTradeAgreement + '\'' +
                ", procInstId='" + procInstId + '\'' +
                ", effectOrPay='" + effectOrPay + '\'' +
                ", isAccumulated='" + isAccumulated + '\'' +
                ", singleRelatedTranType='" + singleRelatedTranType + '\'' +
                ", eachReport='" + eachReport + '\'' +
                ", eachDisclosure='" + eachDisclosure + '\'' +
                ", isCollectRelated='" + isCollectRelated + '\'' +
                ", isconsiderSituation='" + isconsiderSituation + '\'' +
                ", considerSituation='" + considerSituation + '\'' +
                ", tradeAgreementType='" + tradeAgreementType + '\'' +
                ", deptName='" + deptName + '\'' +
                ", relInfoList=" + relInfoList +
                ", relInfoListStr='" + relInfoListStr + '\'' +
                ", managerOrg='" + managerOrg + '\'' +
                ", tranDateString='" + tranDateString + '\'' +
                ", busStartTimeString='" + busStartTimeString + '\'' +
                ", busEndTimeString='" + busEndTimeString + '\'' +
                ", traSubNameStr='" + traSubNameStr + '\'' +
                ", relTypeStr='" + relTypeStr + '\'' +
                ", busTypeStr='" + busTypeStr + '\'' +
                ", majorTradeStr='" + majorTradeStr + '\'' +
                ", statusStr='" + statusStr + '\'' +
                ", majorTypeStr='" + majorTypeStr + '\'' +
                ", tradeZeroAmount=" + tradeZeroAmount +
                ", investmentType='" + investmentType + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", scene='" + scene + '\'' +
                ", transactionName='" + transactionName + '\'' +
                ", transactionDescription='" + transactionDescription + '\'' +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", sourceSystem='" + sourceSystem + '\'' +
                ", sourceModule='" + sourceModule + '\'' +
                ", creater='" + creater + '\'' +
                '}';
    }
}

