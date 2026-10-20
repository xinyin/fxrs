package com.yzj.jep.app.bus.vo;

import java.util.Date;

public class OrgToPsnVo {

    private Integer pid;

    private String regCode;

    private String orignalOrg;

    private String orignalOrgName;

    private String name;

    private String sex;

    private String nationality;

    private String domesticOrForeign;

    private String race;

    private String identifyType;

    private String identifyNumber;

    private String birthdate;

    private String mobile;

    private String email;

    private String status;

    private String statusComment;

    private String maritalStatus;

    private String validStatus;

    private Date activeTime;

    private Date invalidTime;

    private String mgerOrg;

    private String upApprovalDepartment;

    private String nextApprovalDepartment;

    private String upApprovalPeople;

    private String nextApprovalPeople;

    private String regulatorYbj;

    private String regulatorZjh;

    private String regulatorCzb;

    private String regulatorOth1;

    private String regulatorOth2;

    private String isBlack;

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

    private String shareholdingProportion;

    public String getShareholdingProportion() {
        return shareholdingProportion;
    }

    public void setShareholdingProportion(String shareholdingProportion) {
        this.shareholdingProportion = shareholdingProportion;
    }

    private String toPidShareholders;

    private String isShow;

    private Integer level;

    private String liftReason;

    private String dutyCode;
    //columns ENDP

    private String relCategorySub;
    private String residentialAddress;

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getRelCategorySub() {
        return relCategorySub;
    }

    public void setRelCategorySub(String relCategorySub) {
        this.relCategorySub = relCategorySub;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDomesticOrForeign() {
        return domesticOrForeign;
    }

    public void setDomesticOrForeign(String domesticOrForeign) {
        this.domesticOrForeign = domesticOrForeign;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    public String getRegulatorYbj() {
        return regulatorYbj;
    }

    public void setRegulatorYbj(String regulatorYbj) {
        this.regulatorYbj = regulatorYbj;
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

    public String getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
    }
}
