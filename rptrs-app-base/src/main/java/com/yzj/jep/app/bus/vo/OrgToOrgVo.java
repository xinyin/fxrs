package com.yzj.jep.app.bus.vo;

import java.util.Date;

public class OrgToOrgVo {
    /*
     * 机构信息
     * */
    private Integer pid;

    private String regCode;

    private String orignalOrg;

    private String orignalOrgName;

    private String orgName;

    private String orgType;

    private String domesticOrForeign;

    private String identifyType;

    private String uniscCode;

    private Float regCapital;

    private String regCapitalCurrency;

    private String businessScope;

    private String status;

    private String legalPerson;

    private String regAddress;

    private String validStatus;

    private Date activeTime;

    private Date invalidTime;

    private String mgerOrg;

    private String approvalStatus;

    private String statusComment;

    private String upApprovalDepartment;

    private String nextApprovalDepartment;

    private String upApprovalPeople;

    private String nextApprovalPeople;

    private String regulatorZjh;

    private String regulatorCzb;

    private String regulatorOth1;

    private String regulatorOth2;

    private String isBlack;
    //columns END
    private String relCategorySub;

    public String getRelCategorySub() {
        return relCategorySub;
    }

    public void setRelCategorySub(String relCategorySub) {
        this.relCategorySub = relCategorySub;
    }
    /*
     * 关系信息
     * */

    private Integer relPid;

    private String toPid;

    private String fromPid;

    private String relCategory;

    private String fromType;

    private String toType;

    private String fromMemo;

    private String toMemo;

    private String relation;

    private Float shareholdingRatio;

    private String toPidShareholders;

    private String isShow;

    private Integer level;

    private String liftReason;

    private String dutyCode;
    //columns ENDP


    public String getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getOrignalOrg() {
        return orignalOrg;
    }

    public void setOrignalOrg(String orignalOrg) {
        this.orignalOrg = orignalOrg;
    }

    public String getOrignalOrgName() {
        return orignalOrgName;
    }

    public void setOrignalOrgName(String orignalOrgName) {
        this.orignalOrgName = orignalOrgName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getDomesticOrForeign() {
        return domesticOrForeign;
    }

    public void setDomesticOrForeign(String domesticOrForeign) {
        this.domesticOrForeign = domesticOrForeign;
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    public String getUniscCode() {
        return uniscCode;
    }

    public void setUniscCode(String uniscCode) {
        this.uniscCode = uniscCode;
    }

    public Float getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(Float regCapital) {
        this.regCapital = regCapital;
    }

    public String getRegCapitalCurrency() {
        return regCapitalCurrency;
    }

    public void setRegCapitalCurrency(String regCapitalCurrency) {
        this.regCapitalCurrency = regCapitalCurrency;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
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

    public String getMgerOrg() {
        return mgerOrg;
    }

    public void setMgerOrg(String mgerOrg) {
        this.mgerOrg = mgerOrg;
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

    public String getUpApprovalDepartment() {
        return upApprovalDepartment;
    }

    public void setUpApprovalDepartment(String upApprovalDepartment) {
        this.upApprovalDepartment = upApprovalDepartment;
    }

    public String getNextApprovalDepartment() {
        return nextApprovalDepartment;
    }

    public void setNextApprovalDepartment(String nextApprovalDepartment) {
        this.nextApprovalDepartment = nextApprovalDepartment;
    }

    public String getUpApprovalPeople() {
        return upApprovalPeople;
    }

    public void setUpApprovalPeople(String upApprovalPeople) {
        this.upApprovalPeople = upApprovalPeople;
    }

    public String getNextApprovalPeople() {
        return nextApprovalPeople;
    }

    public void setNextApprovalPeople(String nextApprovalPeople) {
        this.nextApprovalPeople = nextApprovalPeople;
    }

    public String getRegulatorZjh() {
        return regulatorZjh;
    }

    public void setRegulatorZjh(String regulatorZjh) {
        this.regulatorZjh = regulatorZjh;
    }

    public String getRegulatorCzb() {
        return regulatorCzb;
    }

    public void setRegulatorCzb(String regulatorCzb) {
        this.regulatorCzb = regulatorCzb;
    }

    public String getRegulatorOth1() {
        return regulatorOth1;
    }

    public void setRegulatorOth1(String regulatorOth1) {
        this.regulatorOth1 = regulatorOth1;
    }

    public String getRegulatorOth2() {
        return regulatorOth2;
    }

    public void setRegulatorOth2(String regulatorOth2) {
        this.regulatorOth2 = regulatorOth2;
    }

    public String getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(String isBlack) {
        this.isBlack = isBlack;
    }

    public Integer getRelPid() {
        return relPid;
    }

    public void setRelPid(Integer relPid) {
        this.relPid = relPid;
    }

    public String getToPid() {
        return toPid;
    }

    public void setToPid(String toPid) {
        this.toPid = toPid;
    }

    public String getFromPid() {
        return fromPid;
    }

    public void setFromPid(String fromPid) {
        this.fromPid = fromPid;
    }

    public String getRelCategory() {
        return relCategory;
    }

    public void setRelCategory(String relCategory) {
        this.relCategory = relCategory;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getFromMemo() {
        return fromMemo;
    }

    public void setFromMemo(String fromMemo) {
        this.fromMemo = fromMemo;
    }

    public String getToMemo() {
        return toMemo;
    }

    public void setToMemo(String toMemo) {
        this.toMemo = toMemo;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Float getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(Float shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    public String getToPidShareholders() {
        return toPidShareholders;
    }

    public void setToPidShareholders(String toPidShareholders) {
        this.toPidShareholders = toPidShareholders;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLiftReason() {
        return liftReason;
    }

    public void setLiftReason(String liftReason) {
        this.liftReason = liftReason;
    }
}
