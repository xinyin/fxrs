package com.yzj.jep.app.bus.model;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author yls
 * @since 2020/9/23
 */
public class RelProOutVo {

    Integer pid;//序号

    //基本信息

    String proName;//产品名称
    String proType;//产品分类
    String proProvider;//产品批准
    String proCode;//产品代码
    String proRegOrg;//产品登记机构
    String adName;//产品管理人全称
    String adCreditCode;//管理人统一社会信用代码
    String careName;//产品托管人全称;
    String careCreditCode;//托管人统一社会信用代码
    String approvalStatus;//状态
    String statusComment;//状态说明
    String mgerName;//管护机构

    //自然人

    String identifyNumber;//证件号码
    String identifyType;//证件类型
    String name;//关联方名称
    String domesticOrForeign;//境内外标识
    String relCategory;//关联关系类别
    String relCategorySub;//关系
    String shareholdingRatio;//持股比例或表决权
    String isValid;//该关系是否有效
    String effectiveDate;//生效日期
    String ineffectiveDate;//失效日期
    String relStatement;//关联关系说明

    //机构
    String uniscCode;//证件号码
    String identifyTypeORG;//证件类型ORG             与自然人的证件类型区分
    String orgName;//机构名称
    String relCategoryORG;//关联关系类别             与自然人关联关系类别取分
    String relCategorySubORG;//关系                与自然人区分
    String shareholdingRatioORG;//持股比例或表决权   与自然人区别
    String isValidORG;//该关系是否有效               与自然人区别
    String effectiveDateORG;//生效时间                与自然人区分
    String ineffectiveDateORG;//失效时间              与自然人区分
    String relStatementORG;// 关联关系说明         与自然人区分
    String domesticOrForeignOrg;//境内外
    String legalPerson;//法定代表人
    String approvalStatusOrg;//机构状态
    String equityShare;//是否存在权益份额

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getApprovalStatusOrg() {
        return approvalStatusOrg;
    }

    public void setApprovalStatusOrg(String approvalStatusOrg) {
        this.approvalStatusOrg = approvalStatusOrg;
    }

    public String getEquityShare() {
        return equityShare;
    }

    public void setEquityShare(String equityShare) {
        this.equityShare = equityShare;
    }

    public String getDomesticOrForeignOrg() {
        return domesticOrForeignOrg;
    }

    public void setDomesticOrForeignOrg(String domesticOrForeignOrg) {
        this.domesticOrForeignOrg = domesticOrForeignOrg;
    }

    public String getMgerName() {
        return mgerName;
    }

    public void setMgerName(String mgerName) {
        this.mgerName = mgerName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProRegOrg() {
        return proRegOrg;
    }

    public void setProRegOrg(String proRegOrg) {
        this.proRegOrg = proRegOrg;
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

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public String getDomesticOrForeign() {
        return domesticOrForeign;
    }

    public void setDomesticOrForeign(String domesticOrForeign) {
        this.domesticOrForeign = domesticOrForeign;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(String shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public String getIneffectiveDate() {
        return ineffectiveDate;
    }

    public String getRelStatement() {
        return relStatement;
    }

    public void setRelStatement(String relStatement) {
        this.relStatement = relStatement;
    }

    public String getUniscCode() {
        return uniscCode;
    }

    public void setUniscCode(String uniscCode) {
        this.uniscCode = uniscCode;
    }

    public String getIdentifyTypeORG() {
        return identifyTypeORG;
    }

    public void setIdentifyTypeORG(String identifyTypeORG) {
        this.identifyTypeORG = identifyTypeORG;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRelCategoryORG() {
        return relCategoryORG;
    }

    public void setRelCategoryORG(String relCategoryORG) {
        this.relCategoryORG = relCategoryORG;
    }

    public String getRelCategorySubORG() {
        return relCategorySubORG;
    }

    public void setRelCategorySubORG(String relCategorySubORG) {
        this.relCategorySubORG = relCategorySubORG;
    }

    public String getShareholdingRatioORG() {
        return shareholdingRatioORG;
    }

    public void setShareholdingRatioORG(String shareholdingRatioORG) {
        this.shareholdingRatioORG = shareholdingRatioORG;
    }

    public String getIsValidORG() {
        return isValidORG;
    }

    public void setIsValidORG(String isValidORG) {
        this.isValidORG = isValidORG;
    }


    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setIneffectiveDate(String ineffectiveDate) {
        this.ineffectiveDate = ineffectiveDate;
    }

    public String getEffectiveDateORG() {
        return effectiveDateORG;
    }

    public void setEffectiveDateORG(String effectiveDateORG) {
        this.effectiveDateORG = effectiveDateORG;
    }

    public String getIneffectiveDateORG() {
        return ineffectiveDateORG;
    }

    public void setIneffectiveDateORG(String ineffectiveDateORG) {
        this.ineffectiveDateORG = ineffectiveDateORG;
    }

    public String getRelStatementORG() {
        return relStatementORG;
    }

    public void setRelStatementORG(String relStatementORG) {
        this.relStatementORG = relStatementORG;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RelProOutVo{");
        sb.append("pid=").append(pid);
        sb.append(", proName='").append(proName).append('\'');
        sb.append(", proType='").append(proType).append('\'');
        sb.append(", proProvider='").append(proProvider).append('\'');
        sb.append(", proCode='").append(proCode).append('\'');
        sb.append(", proRegOrg='").append(proRegOrg).append('\'');
        sb.append(", adName='").append(adName).append('\'');
        sb.append(", adCreditCode='").append(adCreditCode).append('\'');
        sb.append(", careName='").append(careName).append('\'');
        sb.append(", careCreditCode='").append(careCreditCode).append('\'');
        sb.append(", approvalStatus='").append(approvalStatus).append('\'');
        sb.append(", statusComment='").append(statusComment).append('\'');
        sb.append(", identifyNumber='").append(identifyNumber).append('\'');
        sb.append(", identifyType='").append(identifyType).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", relCategory='").append(relCategory).append('\'');
        sb.append(", relCategorySub='").append(relCategorySub).append('\'');
        sb.append(", shareholdingRatio='").append(shareholdingRatio).append('\'');
        sb.append(", isValid='").append(isValid).append('\'');
        sb.append(", effectiveDate=").append(effectiveDate);
        sb.append(", ineffectiveDate=").append(ineffectiveDate);
        sb.append(", relStatement='").append(relStatement).append('\'');
        sb.append(", uniscCode='").append(uniscCode).append('\'');
        sb.append(", identifyTypeORG='").append(identifyTypeORG).append('\'');
        sb.append(", orgName='").append(orgName).append('\'');
        sb.append(", relCategoryORG='").append(relCategoryORG).append('\'');
        sb.append(", relCategorySubORG='").append(relCategorySubORG).append('\'');
        sb.append(", shareholdingRatioORG='").append(shareholdingRatioORG).append('\'');
        sb.append(", isValidORG='").append(isValidORG).append('\'');
        sb.append(", effectiveDateORG=").append(effectiveDateORG);
        sb.append(", ineffectiveDateORG=").append(ineffectiveDateORG);
        sb.append(", relStatementORG='").append(relStatementORG).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
