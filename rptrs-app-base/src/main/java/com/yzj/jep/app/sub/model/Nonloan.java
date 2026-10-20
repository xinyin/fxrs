/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 15:49:06
 *
 */

package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sub_nonloan")
public class Nonloan extends BasePO{
	
	public static final String TABLE_ALIAS = "Nonloan";
	public static final String ALIAS_PID = "pid";
	public static final String ALIAS_RELATED_PARTY_NAME = "relatedPartyName";
	public static final String ALIAS_RELATED_PARTY_CERT_NO = "relatedPartyCertNo";
	public static final String ALIAS_RELATED_PARTY_TYPE = "relatedPartyType";
	public static final String ALIAS_IS_DECLARED = "isDeclared";
	public static final String ALIAS_CONTRACT_NO = "contractNo";
	public static final String ALIAS_SIGN_DATE = "signDate";
	public static final String ALIAS_TRANS_START_DATE = "transStartDate";
	public static final String ALIAS_TRANS_END_DATE = "transEndDate";
	public static final String ALIAS_TRANS_TYPE = "transType";
	public static final String ALIAS_BUZ_TYPE = "buzType";
	public static final String ALIAS_TRANS_AMOUNT = "transAmount";
	public static final String ALIAS_TRANS_PRICE = "transPrice";
	public static final String ALIAS_TRANS_SUBJECT = "transSubject";
	public static final String ALIAS_PRICING_MARK = "pricingMark";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_PRODUCT_CODE = "productCode";
	public static final String ALIAS_RATE = "rate";
	public static final String ALIAS_REGULATOR = "regulator";
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

	@Column(name = "related_party_cert_no", nullable = false, length = 10)
	private String relatedPartyCertNo;

	@Column(name = "related_party_type", nullable = false, length = 2)
	private String relatedPartyType;

	@Column(name = "is_declared", nullable = false, length = 2)
	private String isDeclared;

	@Column(name = "contract_no", nullable = false, length = 50)
	private String contractNo;

	@Column(name = "sign_date", nullable = false, length = 19)
	@Temporal(TemporalType.DATE)
	private Date signDate;

	@Column(name = "trans_start_date", nullable = false, length = 19)
	@Temporal(TemporalType.DATE)
	private Date transStartDate;

	@Column(name = "trans_end_date", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date transEndDate;

	@Column(name = "trans_type", nullable = false, length = 10)
	private String transType;

	@Column(name = "buz_type", nullable = false, length = 10)
	private String buzType;

	@Column(name = "trans_amount", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal transAmount;

	@Column(name = "trans_price", nullable = false, precision = 65, scale = 0)
	private java.math.BigDecimal transPrice;

	@Column(name = "trans_subject", nullable = true, length = 1000)
	private String transSubject;

	@Column(name = "pricing_mark", nullable = false, length = 1000)
	private String pricingMark;

	@Column(name = "remark", nullable = true, length = 1000)
	private String remark;

	@Column(name = "product_code", nullable = true, length = 20)
	private String productCode;

	@Column(name = "rate", nullable = true, length = 10)
	private String rate;

	@Column(name = "regulator", nullable = true, length = 20)
	private String regulator;

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
	

	public String getIsDeclared() {
		return this.isDeclared;
	}
	
	public void setIsDeclared(String value) {
		this.isDeclared = value;
	}
	

	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String value) {
		this.contractNo = value;
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
	

	public String getTransType() {
		return this.transType;
	}
	
	public void setTransType(String value) {
		this.transType = value;
	}
	

	public String getBuzType() {
		return this.buzType;
	}
	
	public void setBuzType(String value) {
		this.buzType = value;
	}
	

	public java.math.BigDecimal getTransAmount() {
		return this.transAmount;
	}
	
	public void setTransAmount(java.math.BigDecimal value) {
		this.transAmount = value;
	}
	

	public java.math.BigDecimal getTransPrice() {
		return this.transPrice;
	}
	
	public void setTransPrice(java.math.BigDecimal value) {
		this.transPrice = value;
	}
	

	public String getTransSubject() {
		return this.transSubject;
	}
	
	public void setTransSubject(String value) {
		this.transSubject = value;
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
	

	public String getProductCode() {
		return this.productCode;
	}
	
	public void setProductCode(String value) {
		this.productCode = value;
	}
	

	public String getRate() {
		return this.rate;
	}
	
	public void setRate(String value) {
		this.rate = value;
	}
	

	public String getRegulator() {
		return this.regulator;
	}
	
	public void setRegulator(String value) {
		this.regulator = value;
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
		return new StringBuilder("nonloan[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("relatedPartyName = ").append(relatedPartyName).append(", \n")
						.append("relatedPartyCertNo = ").append(relatedPartyCertNo).append(", \n")
						.append("relatedPartyType = ").append(relatedPartyType).append(", \n")
						.append("isDeclared = ").append(isDeclared).append(", \n")
						.append("contractNo = ").append(contractNo).append(", \n")
						.append("signDate = ").append(signDate).append(", \n")
						.append("transStartDate = ").append(transStartDate).append(", \n")
						.append("transEndDate = ").append(transEndDate).append(", \n")
						.append("transType = ").append(transType).append(", \n")
						.append("buzType = ").append(buzType).append(", \n")
						.append("transAmount = ").append(transAmount).append(", \n")
						.append("transPrice = ").append(transPrice).append(", \n")
						.append("transSubject = ").append(transSubject).append(", \n")
						.append("pricingMark = ").append(pricingMark).append(", \n")
						.append("remark = ").append(remark).append(", \n")
						.append("productCode = ").append(productCode).append(", \n")
						.append("rate = ").append(rate).append(", \n")
						.append("regulator = ").append(regulator).append(", \n")
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
		if(obj instanceof Nonloan == false) {return false;}
		if(this == obj) {return true;}
		Nonloan other = (Nonloan)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

