/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 15:45:49
 *
 */

package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sub_loan")
public class Loan extends BasePO{
	
	public static final String TABLE_ALIAS = "Loan";
	public static final String ALIAS_PID = "pid";
	public static final String ALIAS_RELATED_PARTY_NAME = "relatedPartyName";
	public static final String ALIAS_RELATED_PARTY_CERT_NO = "relatedPartyCertNo";
	public static final String ALIAS_RELATED_PARTY_TYPE = "relatedPartyType";
	public static final String ALIAS_CREDIT_NO = "creditNo";
	public static final String ALIAS_TRANS_CONTRACT_NO = "transContractNo";
	public static final String ALIAS_SIGN_DATE = "signDate";
	public static final String ALIAS_TRANS_START_DATE = "transStartDate";
	public static final String ALIAS_TRANS_END_DATE = "transEndDate";
	public static final String ALIAS_CREDIT_TYPE = "creditType";
	public static final String ALIAS_TRANS_PRICE_TYPE = "transPriceType";
	public static final String ALIAS_RATE_TYPE = "rateType";
	public static final String ALIAS_TRANS_PRICE = "transPrice";
	public static final String ALIAS_LPR = "lpr";
	public static final String ALIAS_SPREADS = "spreads";
	public static final String ALIAS_RATE_DISCOUNT = "rateDiscount";
	public static final String ALIAS_USED_FOR = "usedFor";
	public static final String ALIAS_PRODUCT_DESC = "productDesc";
	public static final String ALIAS_PRODUCT_NAME = "productName";
	public static final String ALIAS_PRODUCT_ISSUE_NO = "productIssueNo";
	public static final String ALIAS_PRODUCT_USED_FOR = "productUsedFor";
	public static final String ALIAS_CONTRACT_AMOUNT = "contractAmount";
	public static final String ALIAS_BALANCE = "balance";
	public static final String ALIAS_PRODUCT_RELEATED_AMOUNT = "productReleatedAmount";
	public static final String ALIAS_PRICING_MARK = "pricingMark";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_IS_INSIDER = "isInsider";
	public static final String ALIAS_STATUS = "status";
	public static final String ALIAS_STATUS_COMMENT = "statusComment";
	

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "related_party_name", nullable = false, length = 100)
	private String relatedPartyName;

	@Column(name = "related_party_cert_no", nullable = false, length = 50)
	private String relatedPartyCertNo;

	@Column(name = "related_party_type", nullable = false, length = 2)
	private String relatedPartyType;

	@Column(name = "credit_no", nullable = false, length = 100)
	private String creditNo;

	@Column(name = "trans_contract_no", nullable = false, length = 50)
	private String transContractNo;

	@Column(name = "sign_date", nullable = false, length = 19)
	@Temporal(TemporalType.DATE)
	private Date signDate;

	@Column(name = "trans_start_date", nullable = false, length = 19)
	@Temporal(TemporalType.DATE)
	private Date transStartDate;

	@Column(name = "trans_end_date", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date transEndDate;

	@Column(name = "credit_type", nullable = false, length = 10)
	private String creditType;

	@Column(name = "trans_price_type", nullable = false, length = 10)
	private String transPriceType;

	@Column(name = "rate_type", nullable = true, length = 10)
	private String rateType;

	@Column(name = "trans_price", nullable = true, precision = 65, scale = 0)
	private java.math.BigDecimal transPrice;

	@Column(name = "LPR", nullable = true, length = 100)
	private String lpr;

	@Column(name = "spreads", nullable = true, precision = 65, scale = 0)
	private java.math.BigDecimal spreads;

	@Column(name = "rate_discount", nullable = true, precision = 65, scale = 0)
	private java.math.BigDecimal rateDiscount;

	@Column(name = "used_for", nullable = true, length = 1000)
	private String usedFor;

	@Column(name = "product_desc", nullable = true, length = 1000)
	private String productDesc;

	@Column(name = "product_name", nullable = true, length = 100)
	private String productName;

	@Column(name = "product_issue_no", nullable = true, length = 100)
	private String productIssueNo;

	@Column(name = "product_used_for", nullable = true, length = 100)
	private String productUsedFor;

	@Column(name = "contract_amount", nullable = true, precision = 65, scale = 0)
	private java.math.BigDecimal contractAmount;

	@Column(name = "balance", nullable = true, precision = 65, scale = 0)
	private java.math.BigDecimal balance;

	@Column(name = "product_releated_amount", nullable = true, precision = 65, scale = 0)
	private java.math.BigDecimal productReleatedAmount;

	@Column(name = "pricing_mark", nullable = true, length = 1000)
	private String pricingMark;

	@Column(name = "remark", nullable = true, length = 1000)
	private String remark;

	@Column(name = "is_insider", nullable = true, length = 1)
	private String isInsider;

	@Column(name = "status", nullable = false, length = 10)
	private String status;

	@Column(name = "status_comment", nullable = true, length = 255)
	private String statusComment;
	//columns END


	public Integer getPid() {
		return this.pid;
	}
	
	public void setPid(Integer value) {
		this.pid = value;
	}
	

	public String getRelatedPartyName() {
		return this.relatedPartyName;
	}
	
	public void setRelatedPartyName(String value) {
		this.relatedPartyName = value;
	}
	

	public String getRelatedPartyCertNo() {
		return this.relatedPartyCertNo;
	}
	
	public void setRelatedPartyCertNo(String value) {
		this.relatedPartyCertNo = value;
	}
	

	public String getRelatedPartyType() {
		return this.relatedPartyType;
	}
	
	public void setRelatedPartyType(String value) {
		this.relatedPartyType = value;
	}
	

	public String getCreditNo() {
		return this.creditNo;
	}
	
	public void setCreditNo(String value) {
		this.creditNo = value;
	}
	

	public String getTransContractNo() {
		return this.transContractNo;
	}
	
	public void setTransContractNo(String value) {
		this.transContractNo = value;
	}
	

	public Date getSignDate() {
		return this.signDate;
	}
	
	public void setSignDate(Date value) {
		this.signDate = value;
	}
	

	public Date getTransStartDate() {
		return this.transStartDate;
	}
	
	public void setTransStartDate(Date value) {
		this.transStartDate = value;
	}
	

	public Date getTransEndDate() {
		return this.transEndDate;
	}
	
	public void setTransEndDate(Date value) {
		this.transEndDate = value;
	}
	

	public String getCreditType() {
		return this.creditType;
	}
	
	public void setCreditType(String value) {
		this.creditType = value;
	}
	

	public String getTransPriceType() {
		return this.transPriceType;
	}
	
	public void setTransPriceType(String value) {
		this.transPriceType = value;
	}
	

	public String getRateType() {
		return this.rateType;
	}
	
	public void setRateType(String value) {
		this.rateType = value;
	}
	

	public java.math.BigDecimal getTransPrice() {
		return this.transPrice;
	}
	
	public void setTransPrice(java.math.BigDecimal value) {
		this.transPrice = value;
	}
	

	public String getLpr() {
		return this.lpr;
	}
	
	public void setLpr(String value) {
		this.lpr = value;
	}
	

	public java.math.BigDecimal getSpreads() {
		return this.spreads;
	}
	
	public void setSpreads(java.math.BigDecimal value) {
		this.spreads = value;
	}
	

	public java.math.BigDecimal getRateDiscount() {
		return this.rateDiscount;
	}
	
	public void setRateDiscount(java.math.BigDecimal value) {
		this.rateDiscount = value;
	}
	

	public String getUsedFor() {
		return this.usedFor;
	}
	
	public void setUsedFor(String value) {
		this.usedFor = value;
	}
	

	public String getProductDesc() {
		return this.productDesc;
	}
	
	public void setProductDesc(String value) {
		this.productDesc = value;
	}
	

	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String value) {
		this.productName = value;
	}
	

	public String getProductIssueNo() {
		return this.productIssueNo;
	}
	
	public void setProductIssueNo(String value) {
		this.productIssueNo = value;
	}
	

	public String getProductUsedFor() {
		return this.productUsedFor;
	}
	
	public void setProductUsedFor(String value) {
		this.productUsedFor = value;
	}
	

	public java.math.BigDecimal getContractAmount() {
		return this.contractAmount;
	}
	
	public void setContractAmount(java.math.BigDecimal value) {
		this.contractAmount = value;
	}
	

	public java.math.BigDecimal getBalance() {
		return this.balance;
	}
	
	public void setBalance(java.math.BigDecimal value) {
		this.balance = value;
	}
	

	public java.math.BigDecimal getProductReleatedAmount() {
		return this.productReleatedAmount;
	}
	
	public void setProductReleatedAmount(java.math.BigDecimal value) {
		this.productReleatedAmount = value;
	}
	

	public String getPricingMark() {
		return this.pricingMark;
	}
	
	public void setPricingMark(String value) {
		this.pricingMark = value;
	}
	

	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String value) {
		this.remark = value;
	}
	

	public String getIsInsider() {
		return this.isInsider;
	}
	
	public void setIsInsider(String value) {
		this.isInsider = value;
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
		return new StringBuilder("loan[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("relatedPartyName = ").append(relatedPartyName).append(", \n")
						.append("relatedPartyCertNo = ").append(relatedPartyCertNo).append(", \n")
						.append("relatedPartyType = ").append(relatedPartyType).append(", \n")
						.append("creditNo = ").append(creditNo).append(", \n")
						.append("transContractNo = ").append(transContractNo).append(", \n")
						.append("signDate = ").append(signDate).append(", \n")
						.append("transStartDate = ").append(transStartDate).append(", \n")
						.append("transEndDate = ").append(transEndDate).append(", \n")
						.append("creditType = ").append(creditType).append(", \n")
						.append("transPriceType = ").append(transPriceType).append(", \n")
						.append("rateType = ").append(rateType).append(", \n")
						.append("transPrice = ").append(transPrice).append(", \n")
						.append("lpr = ").append(lpr).append(", \n")
						.append("spreads = ").append(spreads).append(", \n")
						.append("rateDiscount = ").append(rateDiscount).append(", \n")
						.append("usedFor = ").append(usedFor).append(", \n")
						.append("productDesc = ").append(productDesc).append(", \n")
						.append("productName = ").append(productName).append(", \n")
						.append("productIssueNo = ").append(productIssueNo).append(", \n")
						.append("productUsedFor = ").append(productUsedFor).append(", \n")
						.append("contractAmount = ").append(contractAmount).append(", \n")
						.append("balance = ").append(balance).append(", \n")
						.append("productReleatedAmount = ").append(productReleatedAmount).append(", \n")
						.append("pricingMark = ").append(pricingMark).append(", \n")
						.append("remark = ").append(remark).append(", \n")
						.append("isInsider = ").append(isInsider).append(", \n")
						.append("status = ").append(status).append(", \n")
						.append("statusComment = ").append(statusComment).append(", \n")
						.append("]")
						.toString();
	}
	
	@Override
    public int hashCode() {
		return new HashCodeBuilder()
			.append(getPid())
			.toHashCode();
	}
	
	@Override
    public boolean equals(Object obj) {
		if(obj instanceof Loan == false) {return false;}
		if(this == obj) {return true;}
		Loan other = (Loan)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

