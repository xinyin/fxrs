package com.yzj.jep.app.sub.vo;

import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Objects;

public class PronInfoVo {

    //金融产品

    private Integer no;                        //金融产品序列
    private String productName;                //产品名称
    private String productType;                //产品分类
    private String productIssueNo;             //产品批准/注册/备案号
    private String productRegisterNo;          //产品代码
    private String registerOrg;                //产品登记机构
    private String productOwner;               //产品管理人全称
    private String productOwnerUniscCode;     //管理人统一社会信用代码
    private String productCustodian;          //产品托管人全称
    private String productCustodianUniscCode;  //托管人统一社会信用代码
    private String statusComment;             //校验结果

    //自然人

    private String name;  //姓名
    private String identifyType;  // 证件类型
    private String identifyNumber;  //证件号码
    /*//    private String 暂时没有定义字段;              //关联关系判定*/
    private String relCategory;  //关联关系类别
    private String relCategorySub;  //关系
    private String isValid;  //该关系是否有效
    private String effectiveDate;  //生效日期
    private String ineffectiveDate;  //失效日期
    private String relStatement;//关联关系说明

    //机构
    private String orgName;   //机构名称
    private String identifyTypeORG;  //证件类型
    private String uniscCode;  //证件号码
    /*//    private String 暂时没有定义字段;              //关联关系判定*/
    private String relCategoryORG;  //关联关系类别
    private String relCategorySubORG;  //关系
    private String isValidORG;  //该关系是否有效
    private String effectiveDateORG;  //生效日期
    private String ineffectiveDateORG;  //失效日期
    private String relStatementORG;//关联关系说明
    private String shareholdingRatioORG;//持股比例或表决权（当关联关系类别为投资或被投资时必填）


    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductIssueNo() {
        return productIssueNo;
    }

    public void setProductIssueNo(String productIssueNo) {
        this.productIssueNo = productIssueNo;
    }

    public String getProductRegisterNo() {
        return productRegisterNo;
    }

    public void setProductRegisterNo(String productRegisterNo) {
        this.productRegisterNo = productRegisterNo;
    }

    public String getRegisterOrg() {
        return registerOrg;
    }

    public void setRegisterOrg(String registerOrg) {
        this.registerOrg = registerOrg;
    }

    public String getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(String productOwner) {
        this.productOwner = productOwner;
    }

    public String getProductOwnerUniscCode() {
        return productOwnerUniscCode;
    }

    public void setProductOwnerUniscCode(String productOwnerUniscCode) {
        this.productOwnerUniscCode = productOwnerUniscCode;
    }

    public String getProductCustodian() {
        return productCustodian;
    }

    public void setProductCustodian(String productCustodian) {
        this.productCustodian = productCustodian;
    }

    public String getProductCustodianUniscCode() {
        return productCustodianUniscCode;
    }

    public void setProductCustodianUniscCode(String productCustodianUniscCode) {
        this.productCustodianUniscCode = productCustodianUniscCode;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getIneffectiveDate() {
        return ineffectiveDate;
    }

    public void setIneffectiveDate(String ineffectiveDate) {
        this.ineffectiveDate = ineffectiveDate;
    }

    public String getRelStatement() {
        return relStatement;
    }

    public void setRelStatement(String relStatement) {
        this.relStatement = relStatement;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIdentifyTypeORG() {
        return identifyTypeORG;
    }

    public void setIdentifyTypeORG(String identifyTypeORG) {
        this.identifyTypeORG = identifyTypeORG;
    }

    public String getUniscCode() {
        return uniscCode;
    }

    public void setUniscCode(String uniscCode) {
        this.uniscCode = uniscCode;
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

    public String getIsValidORG() {
        return isValidORG;
    }

    public void setIsValidORG(String isValidORG) {
        this.isValidORG = isValidORG;
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

    public String getShareholdingRatioORG() {
        return shareholdingRatioORG;
    }

    public void setShareholdingRatioORG(String shareholdingRatioORG) {
        this.shareholdingRatioORG = shareholdingRatioORG;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PronInfoVo that = (PronInfoVo) o;
        return Objects.equals(no, that.no) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(productType, that.productType) &&
                Objects.equals(productIssueNo, that.productIssueNo) &&
                Objects.equals(productRegisterNo, that.productRegisterNo) &&
                Objects.equals(registerOrg, that.registerOrg) &&
                Objects.equals(productOwner, that.productOwner) &&
                Objects.equals(productOwnerUniscCode, that.productOwnerUniscCode) &&
                Objects.equals(productCustodian, that.productCustodian) &&
                Objects.equals(productCustodianUniscCode, that.productCustodianUniscCode) &&
                Objects.equals(statusComment, that.statusComment) &&
                Objects.equals(name, that.name) &&
                Objects.equals(identifyType, that.identifyType) &&
                Objects.equals(identifyNumber, that.identifyNumber) &&
                Objects.equals(relCategory, that.relCategory) &&
                Objects.equals(relCategorySub, that.relCategorySub) &&
                Objects.equals(isValid, that.isValid) &&
                Objects.equals(effectiveDate, that.effectiveDate) &&
                Objects.equals(ineffectiveDate, that.ineffectiveDate) &&
                Objects.equals(relStatement, that.relStatement) &&
                Objects.equals(orgName, that.orgName) &&
                Objects.equals(identifyTypeORG, that.identifyTypeORG) &&
                Objects.equals(uniscCode, that.uniscCode) &&
                Objects.equals(relCategoryORG, that.relCategoryORG) &&
                Objects.equals(relCategorySubORG, that.relCategorySubORG) &&
                Objects.equals(isValidORG, that.isValidORG) &&
                Objects.equals(effectiveDateORG, that.effectiveDateORG) &&
                Objects.equals(ineffectiveDateORG, that.ineffectiveDateORG) &&
                Objects.equals(relStatementORG, that.relStatementORG) &&
                Objects.equals(shareholdingRatioORG, that.shareholdingRatioORG);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no, productName, productType, productIssueNo, productRegisterNo, registerOrg, productOwner, productOwnerUniscCode, productCustodian, productCustodianUniscCode, statusComment, name, identifyType, identifyNumber, relCategory, relCategorySub, isValid, effectiveDate, ineffectiveDate, relStatement, orgName, identifyTypeORG, uniscCode, relCategoryORG, relCategorySubORG, isValidORG, effectiveDateORG, ineffectiveDateORG, relStatementORG, shareholdingRatioORG);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PronInfoVo{");
        sb.append("no=").append(no);
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", productType='").append(productType).append('\'');
        sb.append(", productIssueNo='").append(productIssueNo).append('\'');
        sb.append(", productRegisterNo='").append(productRegisterNo).append('\'');
        sb.append(", registerOrg='").append(registerOrg).append('\'');
        sb.append(", productOwner='").append(productOwner).append('\'');
        sb.append(", productOwnerUniscCode='").append(productOwnerUniscCode).append('\'');
        sb.append(", productCustodian='").append(productCustodian).append('\'');
        sb.append(", productCustodianUniscCode='").append(productCustodianUniscCode).append('\'');
        sb.append(", statusComment='").append(statusComment).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", identifyType='").append(identifyType).append('\'');
        sb.append(", identifyNumber='").append(identifyNumber).append('\'');
        sb.append(", relCategory='").append(relCategory).append('\'');
        sb.append(", relCategorySub='").append(relCategorySub).append('\'');
        sb.append(", isValid='").append(isValid).append('\'');
        sb.append(", effectiveDate='").append(effectiveDate).append('\'');
        sb.append(", ineffectiveDate='").append(ineffectiveDate).append('\'');
        sb.append(", relStatement='").append(relStatement).append('\'');
        sb.append(", orgName='").append(orgName).append('\'');
        sb.append(", identifyTypeORG='").append(identifyTypeORG).append('\'');
        sb.append(", uniscCode='").append(uniscCode).append('\'');
        sb.append(", relCategoryORG='").append(relCategoryORG).append('\'');
        sb.append(", relCategorySubORG='").append(relCategorySubORG).append('\'');
        sb.append(", isValidORG='").append(isValidORG).append('\'');
        sb.append(", effectiveDateORG='").append(effectiveDateORG).append('\'');
        sb.append(", ineffectiveDateORG='").append(ineffectiveDateORG).append('\'');
        sb.append(", relStatementORG='").append(relStatementORG).append('\'');
        sb.append(", shareholdingRatioORG='").append(shareholdingRatioORG).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
