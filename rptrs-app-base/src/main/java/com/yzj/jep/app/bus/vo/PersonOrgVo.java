package com.yzj.jep.app.bus.vo;

public class PersonOrgVo {

    private Integer pidForOrgTo; //机构疑似关联方表主键(关系)

    private Integer pidForOrgFrom;//机构疑似关联方表主键(源)

    private Integer pidForRel; //疑似关联关系表主键

    private String identifyType; //证件类型

    private String uniscCode; //证件号码

    private String orgName; //机构名称

    private String regAddress; //注册地

    private String domesticOrForeign; //境内/境外

    private String mgerOrg; //管户机构

    private String orgType; //企业类型

    private String businessScope; //营业范围

    private Float regCapital; //注册资本

    private String regCapitalCurrency; //注册资本币种代码

    private String legalPerson; //法定代表人

    private String status; //机构状态

    private String relCategory;//关联关系类别

    private String relCategorySub; //亲属关系子类型

    private String fromName;//from关系名称

    private String toName;//to关系名称

    private String dutyCode; //职位

    private Float shareholdingRatio; //持股比例或表决权

    public Integer getPidForOrgTo() {
        return pidForOrgTo;
    }

    public void setPidForOrgTo(Integer pidForOrgTo) {
        this.pidForOrgTo = pidForOrgTo;
    }

    public Integer getPidForOrgFrom() {
        return pidForOrgFrom;
    }

    public void setPidForOrgFrom(Integer pidForOrgFrom) {
        this.pidForOrgFrom = pidForOrgFrom;
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

    public String getUniscCode() {
        return uniscCode;
    }

    public void setUniscCode(String uniscCode) {
        this.uniscCode = uniscCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getDomesticOrForeign() {
        return domesticOrForeign;
    }

    public void setDomesticOrForeign(String domesticOrForeign) {
        this.domesticOrForeign = domesticOrForeign;
    }

    public String getMgerOrg() {
        return mgerOrg;
    }

    public void setMgerOrg(String mgerOrg) {
        this.mgerOrg = mgerOrg;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
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

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRelCategory() {
        return relCategory;
    }

    public void setRelCategory(String relCategory) {
        this.relCategory = relCategory;
    }

    public String getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
    }

    public Float getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(Float shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
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

    @Override
    public String toString() {
        return "PersonOrgVo{" +
                "pidForOrgTo=" + pidForOrgTo +
                ", pidForOrgFrom=" + pidForOrgFrom +
                ", pidForRel=" + pidForRel +
                ", identifyType='" + identifyType + '\'' +
                ", uniscCode='" + uniscCode + '\'' +
                ", orgName='" + orgName + '\'' +
                ", regAddress='" + regAddress + '\'' +
                ", domesticOrForeign='" + domesticOrForeign + '\'' +
                ", mgerOrg='" + mgerOrg + '\'' +
                ", orgType='" + orgType + '\'' +
                ", businessScope='" + businessScope + '\'' +
                ", regCapital=" + regCapital +
                ", regCapitalCurrency='" + regCapitalCurrency + '\'' +
                ", legalPerson='" + legalPerson + '\'' +
                ", status='" + status + '\'' +
                ", relCategory='" + relCategory + '\'' +
                ", dutyCode='" + dutyCode + '\'' +
                ", shareholdingRatio=" + shareholdingRatio +
                '}';
    }
}
