package com.yzj.jep.app.bus.vo;

import java.util.Date;

public class PersonRelationVo {

    private Integer pidForPsnTo; //自然人疑似关联方表主键(关系)

    private Integer pidForPsnFrom;//自然人疑似关联方表主键(源)

    private Integer pidForRel; //疑似关联关系表主键

    private String identifyType; //证件类型

    private String identifyNumber; //证件号码

    private String name; //姓名

    private String sex; //性别

    private String mgerOrg;//管护机构;

    private String race;//民族

    private String domesticOrForeign; //境内/境外

    private String maritalStatus; //婚姻状况

    private String birthdate; //出生日期

    private String mobile;//移动电话

    private String email;//邮箱

    private String relCategory; //亲属关系

    private String relCategorySub; //亲属关系子类型

    private String fromName;//from关系名称

    private String toName;//to关系名称
    private String relation; //
    private String relStatus;//关系状态
    private String residentialAddress;  //居住地址
    private String voteProportion;//表决权占比
    private Float shareholdingRatio;//持股比例

    private String predicate;//关联关系判定

    public Float getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(Float shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getVoteProportion() {
        return voteProportion;
    }

    public void setVoteProportion(String voteProportion) {
        this.voteProportion = voteProportion;
    }

    public String getRelStatus() {
        return relStatus;
    }

    public void setRelStatus(String relStatus) {
        this.relStatus = relStatus;
    }

    public Integer getPidForPsnTo() {
        return pidForPsnTo;
    }

    public void setPidForPsnTo(Integer pidForPsnTo) {
        this.pidForPsnTo = pidForPsnTo;
    }

    public Integer getPidForPsnFrom() {
        return pidForPsnFrom;
    }

    public void setPidForPsnFrom(Integer pidForPsnFrom) {
        this.pidForPsnFrom = pidForPsnFrom;
    }

    public Integer getPidForRel() {
        return pidForRel;
    }

    public void setPidForRel(Integer pidForRel) {
        this.pidForRel = pidForRel;
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

    public String getMgerOrg() {
        return mgerOrg;
    }

    public void setMgerOrg(String mgerOrg) {
        this.mgerOrg = mgerOrg;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getDomesticOrForeign() {
        return domesticOrForeign;
    }

    public void setDomesticOrForeign(String domesticOrForeign) {
        this.domesticOrForeign = domesticOrForeign;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    public String getRelCategory() {
        return relCategory;
    }

    public void setRelCategory(String relCategory) {
        this.relCategory = relCategory;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRelCategorySub() {
        return relCategorySub;
    }

    public void setRelCategorySub(String relCategorySub) {
        this.relCategorySub = relCategorySub;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }


    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }


    @Override
    public String toString() {
        return "PersonRelationVo{" +
                "pidForPsnTo=" + pidForPsnTo +
                ", pidForPsnFrom=" + pidForPsnFrom +
                ", pidForRel=" + pidForRel +
                ", identifyType='" + identifyType + '\'' +
                ", identifyNumber='" + identifyNumber + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", mgerOrg='" + mgerOrg + '\'' +
                ", race='" + race + '\'' +
                ", domesticOrForeign='" + domesticOrForeign + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", relCategory='" + relCategory + '\'' +
                ", relation='" + relation + '\'' +
                '}';
    }
}
