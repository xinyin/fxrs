package com.yzj.jep.app.bus.model;

import java.util.Date;

public class RelProVo {
    private Integer pid;
    private Integer relId;

    private String proCode;

    private String proName;

    private String proType;

    private String proProvider;

    private String proRegOrg;

    private String adPid;

    private String adName;

    private String adCreditCode;

    private String carePid;

    private String careName;

    private String careCreditCode;

    private String dataSource;

    private String approvalStatus;

    private String statusComment;
    private String equityShare;

    private String aligCode;
    private String relCategory;
    private String relCategorySub;
    private Float shareholdingRatio;
    private String isValid;
    private String relStatus;
    private String ineffectiveDate;
    private String effectiveDate;
    private String relStatement;
    private String activeTimeString;// 生效日期
    private String invalidTimeString;// 失效日期

    private Date activeTime;// 生效日期
    private Date invalidTime;// 失效日期
    private String relPredicate;

    private String holdings;

    public String getHoldings() {
        return holdings;
    }

    public void setHoldings(String holdings) {
        this.holdings = holdings;
    }

    public String getRelPredicate() {
        return relPredicate;
    }

    public void setRelPredicate(String relPredicate) {
        this.relPredicate = relPredicate;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getEquityShare() {
        return equityShare;
    }

    public void setEquityShare(String equityShare) {
        this.equityShare = equityShare;
    }

    public String getActiveTimeString() {
        return activeTimeString;
    }

    public void setActiveTimeString(String activeTimeString) {
        this.activeTimeString = activeTimeString;
    }

    public String getInvalidTimeString() {
        return invalidTimeString;
    }

    public void setInvalidTimeString(String invalidTimeString) {
        this.invalidTimeString = invalidTimeString;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getRelId() {
        return relId;
    }

    public void setRelId(Integer relId) {
        this.relId = relId;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getProProvider() {
        return proProvider;
    }

    public void setProProvider(String proProvider) {
        this.proProvider = proProvider;
    }

    public String getProRegOrg() {
        return proRegOrg;
    }

    public void setProRegOrg(String proRegOrg) {
        this.proRegOrg = proRegOrg;
    }

    public String getAdPid() {
        return adPid;
    }

    public void setAdPid(String adPid) {
        this.adPid = adPid;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdCreditCode() {
        return adCreditCode;
    }

    public void setAdCreditCode(String adCreditCode) {
        this.adCreditCode = adCreditCode;
    }

    public String getCarePid() {
        return carePid;
    }

    public void setCarePid(String carePid) {
        this.carePid = carePid;
    }

    public String getCareName() {
        return careName;
    }

    public void setCareName(String careName) {
        this.careName = careName;
    }

    public String getCareCreditCode() {
        return careCreditCode;
    }

    public void setCareCreditCode(String careCreditCode) {
        this.careCreditCode = careCreditCode;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    public String getAligCode() {
        return aligCode;
    }

    public void setAligCode(String aligCode) {
        this.aligCode = aligCode;
    }

    public String getRelCategory() {
        return relCategory;
    }

    public void setRelCategory(String relCategory) {
        this.relCategory = relCategory;
    }

    public String getRelCategorySub() {
        return relCategorySub;
    }

    public void setRelCategorySub(String relCategorySub) {
        this.relCategorySub = relCategorySub;
    }

    public Float getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(Float shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getRelStatus() {
        return relStatus;
    }

    public void setRelStatus(String relStatus) {
        this.relStatus = relStatus;
    }

    public String getIneffectiveDate() {
        return ineffectiveDate;
    }

    public void setIneffectiveDate(String ineffectiveDate) {
        this.ineffectiveDate = ineffectiveDate;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getRelStatement() {
        return relStatement;
    }

    public void setRelStatement(String relStatement) {
        this.relStatement = relStatement;
    }
}
