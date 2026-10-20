/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:52
 *
 */

package com.yzj.jep.app.ins.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TraInvestmentVo{

    private Integer pid;

    private Integer relId;

    private String relType;

    private String relName;

    private String relNumber;

    private String tradeOrg;

    private String tranType;

    private String dataSources;

    private Date tranDate;

    private String majorTrade;

    private String caliber;

    private String status;

    private String statusComment;

    private String isDealPrice;

    private BigDecimal totolTradeAmount;

    private String contractNo;

    private BigDecimal tradeAmount;

    private Date busStartTime;

    private Date busEndTime;

    private String isCashSubscript;

    private String investmentType;

    private String approvalNum;

    private String securitiesType;

    private Date inTime;

    private String shareNum;

    private String shareRetio;

    private String tradePrice;

    private String pricingPolicy;

    private String tradeSubject;

    private String remark;
    //交易编号
    private String transNum;

    private String isEffective;

    private String approveTradeAmount;

    private String ruleResult;

    private String productCode;

    private String productName;

    private String considerType;

    private String isHoldingSubsidiary;

    private String holdingSubsidiaryName;

    private String holdingSubsidiaryNumber;

    private String holdingSubsidiaryType;

    private String yield_;

    private String variableshareratio;

    private String variableshareratioAfter;

    @Temporal(TemporalType.DATE)
    private Date effectiveTime;

    private String totalAssets;

    private String financialImpact;

    private String traSubName;

    private String majorType;

    private BigDecimal contractAmount;

    private String amendId;

    private String amendName;

    private String tranName;

    private String tranOpponent;

    private String certType;

    private String managerOrg;

    private String accuPracTradeAmount;

    private BigDecimal relTradeAmount;

    private String pracTradeAmount;

    private String archivingStatus ;//1：确认逐笔披露； 2：确认逐笔报告；  3：确认季度定期披露；4.确认年度定期披露；5：确认定期报告

    private String reportStatus;//1：确认逐笔报告；

    private String quarterArcStatus;//1：确认季度定期披露;

    private String yearArcStatus;//1.确认年度定期披露；

    private String yearRepStatus;//1：确认年度定期报告

    private String concentratedApproval;//是否是集中审议 0 是，1否

    private String notMajorRelNumber;//未清零的关联方relNumber

    private String quarterRepStatus;//1：确认季度定期报告

    public String getQuarterRepStatus() {
        return quarterRepStatus;
    }

    public void setQuarterRepStatus(String quarterRepStatus) {
        this.quarterRepStatus = quarterRepStatus;
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

    public String getManagerOrg() {
        return managerOrg;
    }

    public void setManagerOrg(String managerOrg) {
        this.managerOrg = managerOrg;
    }

    public String getAccuPracTradeAmount() {
        return accuPracTradeAmount;
    }

    public void setAccuPracTradeAmount(String accuPracTradeAmount) {
        this.accuPracTradeAmount = accuPracTradeAmount;
    }

    public BigDecimal getRelTradeAmount() {
        return relTradeAmount;
    }

    public void setRelTradeAmount(BigDecimal relTradeAmount) {
        this.relTradeAmount = relTradeAmount;
    }

    public String getPracTradeAmount() {
        return pracTradeAmount;
    }

    public void setPracTradeAmount(String pracTradeAmount) {
        this.pracTradeAmount = pracTradeAmount;
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
    private String traSubNameStr;
    @Transient
    private String instead;//导出数据investmentType在xls中无法显示数据，替换investmentType

    @Transient
    private String relTypeStr;
    //业务类型
    @Transient
    private String busTypeStr;
    //是否重大关联交易
    @Transient
    private String majorTypeStr;
    //状态
    @Transient
    private String statusStr;
    //归零操作统计金额
    @Transient
    private BigDecimal tradeZeroAmount;

    /**
     * 交易对手（关联方）列表
     */
    @Transient
    private List<TraRelInfo> relInfoList;

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

    public BigDecimal getTradeZeroAmount() {
        return tradeZeroAmount;
    }

    public void setTradeZeroAmount(BigDecimal tradeZeroAmount) {
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

    public String getTraSubNameStr() {
        return traSubNameStr;
    }

    public void setTraSubNameStr(String traSubNameStr) {
        this.traSubNameStr = traSubNameStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getMajorTypeStr() {
        return majorTypeStr;
    }

    public void setMajorTypeStr(String majorTypeStr) {
        this.majorTypeStr = majorTypeStr;
    }

    public String getBusTypeStr() {
        return busTypeStr;
    }

    public void setBusTypeStr(String busTypeStr) {
        this.busTypeStr = busTypeStr;
    }

    public String getRelTypeStr() {
        return relTypeStr;
    }

    public void setRelTypeStr(String relTypeStr) {
        this.relTypeStr = relTypeStr;
    }


    public String getInstead() {
        return instead;
    }

    public void setInstead(String instead) {
        this.instead = instead;
    }
//columns END

    @Transient
    private String tranDateString;
    @Transient
    private String effectiveTimeString;
    @Transient
    private String busStartTimeString;

    @Transient
    private String busEndTimeString;

    @Transient
    private String inTimeString;

    @Transient
    private String tradeAmountStr;

    public String getTradeAmountStr() {
        if(tradeAmount!=null){
            return getTradeAmount().toString();
        }else{
            return "";
        }
    }

    public void setTradeAmountStr(String tradeAmountStr) {
        if(tradeAmountStr!=null){
            setTradeAmount(new BigDecimal(tradeAmountStr));
        }
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

    public String getEffectiveTimeString() {
        return effectiveTimeString;
    }

    public void setEffectiveTimeString(String effectiveTimeString) {
        this.effectiveTimeString = effectiveTimeString;
    }
/*    public void setEffectiveTimeString(String effectiveTimeString) {
        if (effectiveTimeString != null) {
            setEffectiveTime(DateUtils.parseDate(effectiveTimeString));
        }
    }

    public String getEffectiveTimeString() {
        if (effectiveTime != null) {
            return DateUtils.formatDate(effectiveTime);
        }
        return "";
    }*/


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


    public Date getBusEndTime() {
        return this.busEndTime;
    }

    public void setBusEndTime(Date value) {
        this.busEndTime = value;
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

    public String getIsCashSubscript() {
        return isCashSubscript;
    }

    public void setIsCashSubscript(String isCashSubscript) {
        this.isCashSubscript = isCashSubscript;
    }

    public String getInvestmentType() {
        return this.investmentType;
    }

    public void setInvestmentType(String value) {
        this.investmentType = value;
    }


    public String getApprovalNum() {
        return this.approvalNum;
    }

    public void setApprovalNum(String value) {
        this.approvalNum = value;
    }


    public String getSecuritiesType() {
        return this.securitiesType;
    }

    public void setSecuritiesType(String value) {
        this.securitiesType = value;
    }


    public Date getInTime() {
        return this.inTime;
    }

    public void setInTime(Date value) {
        this.inTime = value;
    }

    public String getInTimeString() {
        if (inTime != null) {
            return DateUtils.formatDate(inTime);
        }
        return "";
    }

    public void setInTimeString(String value) {
        if (value != null) {
            setInTime(DateUtils.parseDate(value));
        }
    }


    public String getShareNum() {
        return this.shareNum;
    }

    public void setShareNum(String value) {
        this.shareNum = value;
    }


    public String getShareRetio() {
        return this.shareRetio;
    }

    public void setShareRetio(String value) {
        this.shareRetio = value;
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

    public String getConsiderType() {
        return considerType;
    }

    public void setConsiderType(String considerType) {
        this.considerType = considerType;
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

    public String getHoldingSubsidiaryType() {
        return holdingSubsidiaryType;
    }

    public void setHoldingSubsidiaryType(String holdingSubsidiaryType) {
        this.holdingSubsidiaryType = holdingSubsidiaryType;
    }

    public String getYield_() {
        return yield_;
    }

    public void setYield_(String yield_) {
        this.yield_ = yield_;
    }

    public String getVariableshareratio() {
        return variableshareratio;
    }

    public void setVariableshareratio(String variableshareratio) {
        this.variableshareratio = variableshareratio;
    }

    public String getVariableshareratioAfter() {
        return variableshareratioAfter;
    }

    public void setVariableshareratioAfter(String variableshareratioAfter) {
        this.variableshareratioAfter = variableshareratioAfter;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(String totalAssets) {
        this.totalAssets = totalAssets;
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

}

