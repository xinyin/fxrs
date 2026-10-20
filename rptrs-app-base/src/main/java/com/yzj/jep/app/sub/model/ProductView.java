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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author yao
 */
@Entity
@Table(name = "sub_product_view")
public class ProductView extends BasePO{

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
	public static final String ALIAS_SUB_SEQ = "流水号";


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

	@Column(name = "SUB_SEQ",nullable = false, length = 18)
	private String subSeq;

	@Column(name = "prot_pid")
	private Integer protPid;

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

	public Integer getProtPid() {
		return protPid;
	}

	public void setProtPid(Integer protPid) {
		this.protPid = protPid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
		if (o == null || getClass() != o.getClass()) {
            return false;
        }
		ProductView that = (ProductView) o;
		return Objects.equals(pid, that.pid) &&
				Objects.equals(productName, that.productName) &&
				Objects.equals(productRegisterNo, that.productRegisterNo) &&
				Objects.equals(productType, that.productType) &&
				Objects.equals(productIssueNo, that.productIssueNo) &&
				Objects.equals(registerOrg, that.registerOrg) &&
				Objects.equals(productOwner, that.productOwner) &&
				Objects.equals(productOwnerUniscCode, that.productOwnerUniscCode) &&
				Objects.equals(productCustodian, that.productCustodian) &&
				Objects.equals(productCustodianUniscCode, that.productCustodianUniscCode) &&
				Objects.equals(subSeq, that.subSeq);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pid, productName, productRegisterNo, productType, productIssueNo, registerOrg, productOwner, productOwnerUniscCode, productCustodian, productCustodianUniscCode, subSeq);
	}

	@Override
	public String toString() {
		return "ProductView{" +
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
				", subSeq='" + subSeq + '\'' +
				", protPid=" + protPid +
				", assetDesc='" + assetDesc + '\'' +
				", investors='" + investors + '\'' +
				'}';
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductRegisterNo() {
		return productRegisterNo;
	}

	public void setProductRegisterNo(String productRegisterNo) {
		this.productRegisterNo = productRegisterNo;
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

	public String getSubSeq() {
		return subSeq;
	}

	public void setSubSeq(String subSeq) {
		this.subSeq = subSeq;
	}
}

