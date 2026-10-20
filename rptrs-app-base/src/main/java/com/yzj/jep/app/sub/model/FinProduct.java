/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 10:12:05
 *
 */

package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "sub_product")
public class FinProduct extends BasePO {

    public static final String TABLE_ALIAS = "FinProduct";
    public static final String ALIAS_PID = "pid";
    public static final String ALIAS_PRODUCT_NAME = "产品全称";
    public static final String ALIAS_PRODUCT_REGISTER_NO = "产品代码";
    public static final String ALIAS_PRODUCT_TYPE = "产品分类";
    public static final String ALIAS_PRODUCT_ISSUE_NO = "产品批准/注册/备案号";
    public static final String ALIAS_REGISTER_ORG = "产品登记机构";
    public static final String ALIAS_PRODUCT_OWNER = "产品管理人全称";
    public static final String ALIAS_PRODUCT_OWNER_UNISC_CODE = "管理人统一社会信用代码";
    public static final String ALIAS_PRODUCT_CUSTODIAN = "产品托管人全称";
    public static final String ALIAS_PRODUCT_CUSTODIAN_UNISC_CODE = "托管人统一社会信用代码";
    public static final String ALIAS_STATUS = "前置流程状态";
    public static final String ALIAS_STATUS_COMMENT = "前置流程状态说明";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "product_register_no", nullable = false, length = 10)
    private String productRegisterNo;

    @Column(name = "product_type", nullable = false, length = 10)
    private String productType;

    @Column(name = "productIssue_no", nullable = false, length = 100)
    private String productIssueNo;

    @Column(name = "register_org", nullable = true, length = 100)
    private String registerOrg;

    @Column(name = "product_owner", nullable = false, length = 100)
    private String productOwner;

    @Column(name = "product_owner_unisc_code", nullable = false, length = 18)
    private String productOwnerUniscCode;

    @Column(name = "product_custodian", nullable = true, length = 100)
    private String productCustodian;

    @Column(name = "product_custodian_unisc_code", nullable = true, length = 18)
    private String productCustodianUniscCode;

    @Column(name = "status", nullable = false, length = 255)
    private String status;

    @Column(name = "status_comment", nullable = false, length = 255)
    private String statusComment;
    //columns END
    @Transient
    private String productTypeSub;

    /** 底层资产 */
    @Column(name = "asset_desc", nullable = true, length = 2000)
    private String assetDesc;
    /** 投资者 */
    @Column(name = "investors", nullable = true, length = 2000)
    private String investors;

    public String getAssetDesc() {
        return assetDesc;
    }

    public void setAssetDesc(String assetDesc) {
        this.assetDesc = assetDesc;
    }

    public String getInvestors() {
        return investors;
    }

    public void setInvestors(String investors) {
        this.investors = investors;
    }

    public String getProductTypeSub() {
        if (Integer.valueOf(productType) < 10) {
            return "0" + productType;
        }else {
            return productType;
        }
    }


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String value) {
        this.productName = value;
    }


    public String getProductRegisterNo() {
        return this.productRegisterNo;
    }

    public void setProductRegisterNo(String value) {
        this.productRegisterNo = value;
    }


    public String getProductType() {
        return this.productType;
    }

    public void setProductType(String value) {
        this.productType = value;
    }


    public String getProductIssueNo() {
        return this.productIssueNo;
    }

    public void setProductIssueNo(String value) {
        this.productIssueNo = value;
    }


    public String getRegisterOrg() {
        return this.registerOrg;
    }

    public void setRegisterOrg(String value) {
        this.registerOrg = value;
    }


    public String getProductOwner() {
        return this.productOwner;
    }

    public void setProductOwner(String value) {
        this.productOwner = value;
    }


    public String getProductOwnerUniscCode() {
        return this.productOwnerUniscCode;
    }

    public void setProductOwnerUniscCode(String value) {
        this.productOwnerUniscCode = value;
    }


    public String getProductCustodian() {
        return this.productCustodian;
    }

    public void setProductCustodian(String value) {
        this.productCustodian = value;
    }


    public String getProductCustodianUniscCode() {
        return this.productCustodianUniscCode;
    }

    public void setProductCustodianUniscCode(String value) {
        this.productCustodianUniscCode = value;
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

    @Override
    public String toString() {
        return "FinProduct{" +
                "pid=" + pid +
                ", productName='" + productName + '\'' +
                ", productRegisterNo='" + productRegisterNo + '\'' +
                ", productType='" + productType + '\'' +
                ", productIssueNo='" + productIssueNo + '\'' +
                ", registerOrg='" + registerOrg + '\'' +
                ", productOwner='" + productOwner + '\'' +
                ", productOwnerUniscCode='" + productOwnerUniscCode + '\'' +
                ", productCustodian='" + productCustodian + '\'' +
                ", productCustodianUniscCode='" + productCustodianUniscCode + '\'' +
                ", status='" + status + '\'' +
                ", statusComment='" + statusComment + '\'' +
                ", productTypeSub='" + productTypeSub + '\'' +
                ", assetDesc='" + assetDesc + '\'' +
                ", investors='" + investors + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FinProduct == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        FinProduct other = (FinProduct) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

