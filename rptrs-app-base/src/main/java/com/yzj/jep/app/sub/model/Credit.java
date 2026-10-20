/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 15:44:32
 *
 */

package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sub_credit")
public class Credit extends BasePO{
	
	public static final String TABLE_ALIAS = "Credit";
	public static final String ALIAS_PID = "pid";
	public static final String ALIAS_TRANS_NUM = "transNum";
	public static final String ALIAS_RELATED_PARTY_NAME = "relatedPartyName";
	public static final String ALIAS_RELATED_PARTY_CERT_NO = "relatedPartyCertNo";
	public static final String ALIAS_RELATED_PARTY_TYPE = "relatedPartyType";
	public static final String ALIAS_IS_DECLARED = "isDeclared";
	public static final String ALIAS_CREDIT_NO = "creditNo";
	public static final String ALIAS_CREDIT_CONFIRM_DATE = "creditConfirmDate";
	public static final String ALIAS_CREDIT_LIMIT = "creditLimit";
	public static final String ALIAS_FOREIGN_CURR_TO_RMB = "foreignCurrToRmb";
	public static final String ALIAS_CREDIT_ITEMS = "creditItems";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_STATUS = "status";
	public static final String ALIAS_STATUS_COMMENT = "statusComment";
	public static final String ALIAS_MANAGE_ORG = "manageOrg";
	public static final String ALIAS_IDENTIFY_TYPE = "identifyType";
	public static final String ALIAS_IDENTIFY_NUMBER = "identifyNumber";
	public static final String ALIAS_TRAN_TYPE = "tranType";
	public static final String ALIAS_TRAN_SCOPE = "tranScope";
	public static final String ALIAS_CONTRACT_NO = "contractNo";
	public static final String ALIAS_CONTRACT_SIGN_DATE = "contractSignDate";
	public static final String ALIAS_VALLDITY_CONTRACT = "valldityContract";
	public static final String ALIAS_CREDIT_STATUS = "creditStatus";
	public static final String ALIAS_TRAN_DATE = "tranDate";
	public static final String ALIAS_TRAN_PRICE = "tranPrice";
	public static final String ALIAS_BZ = "bz";
	public static final String ALIAS_VALIDITY_CONTRACT = "validityContract";
	public static final String ALIAS_CREDIT_AMOUNT = "creditAmount";
	public static final String ALIAS_VALIDITY_BALANCE = "validityBalance";
	public static final String ALIAS_CREDIT_START_DATE = "creditStartDate";
	public static final String ALIAS_CREDIT_END_DATE = "creditEndDate";
	public static final String ALIAS_GUARA_MODE = "guaraMode";
	public static final String ALIAS_COLLATERAL = "collateral";
	public static final String ALIAS_GUARANTOR = "guarantor";
	public static final String ALIAS_BZR_IDENTIFY_TYPE = "bzrIdentifyType";
	public static final String ALIAS_BZR_IDENT_NUMBER = "bzrIdentNumber";
	public static final String ALIAS_FIVE_LEVEL = "fiveLevel";
	public static final String ALIAS_SECURIT_DEPOSIT = "securitDeposit";
	public static final String ALIAS_MARGIN_RATIO = "marginRatio";
	public static final String ALIAS_BANK_DEPOSIT = "bankDeposit";
	public static final String ALIAS_NATIONAL_DEBT = "nationalDebt";
	public static final String ALIAS_BUSINESS_TYPE = "businessType";
	public static final String ALIAS_PRICING_STRATEGY = "pricingStrategy";
	public static final String ALIAS_IS_INSIDER = "isInsider";
	public static final String ALIAS_IMPORTANT_TRADE = "importantTrade";
	public static final String ALIAS_PRODUCT_CODE = "productCode";
	public static final String ALIAS_INTERNAL_APPLY_NO = "internalApplyNo";
	public static final String ALIAS_RATE = "rate";
	public static final String ALIAS_APPLY_TIME = "applyTime";
	public static final String ALIAS_APPLY_OVER_TIME = "applyOverTime";
	public static final String ALIAS_REGULATOR = "regulator";
	public static final String ALIAS_CREDIT_PLAN = "creditPlan";
	public static final String ALIAS_CREDIT_STATUS_COMMENT = "creditStatusComment";
	public static final String ALIAS_SHAREHOLDING_PROPORTION = "shareholdingProportion";
	

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "TRANS_NUM", nullable = true, length = 100)
	private String transNum;

	@Column(name = "related_party_name", nullable = false, length = 100)
	private String relatedPartyName;

	@Column(name = "related_party_certNo", nullable = false, length = 50)
	private String relatedPartyCertNo;

	@Column(name = "related_party_type", nullable = false, length = 2)
	private String relatedPartyType;

	@Column(name = "Is_declared", nullable = false, length = 2)
	private String isDeclared;

	@Column(name = "credit_no", nullable = false, length = 100)
	private String creditNo;

	@Column(name = "credit_confirm_date", nullable = false, length = 19)
	@Temporal(TemporalType.DATE)
	private Date creditConfirmDate;

	@Column(name = "credit_limit", nullable = false, precision = 65, scale = 2)
	private java.math.BigDecimal creditLimit;

	@Column(name = "foreign_curr_toRMB", nullable = true, precision = 65, scale = 0)
	private java.math.BigDecimal foreignCurrToRmb;

	@Column(name = "credit_items", nullable = false, length = 1000)
	private String creditItems;

	@Column(name = "remark", nullable = true, length = 1000)
	private String remark;

	@Column(name = "status", nullable = false, length = 10)
	private String status;

	@Column(name = "status_comment", nullable = true, length = 255)
	private String statusComment;

	@Column(name = "MANAGE_ORG", nullable = true, length = 100)
	private String manageOrg;

	@Column(name = "IDENTIFY_TYPE", nullable = true, length = 100)
	private String identifyType;

	@Column(name = "IDENTIFY_NUMBER", nullable = true, length = 100)
	private String identifyNumber;

	@Column(name = "TRAN_TYPE", nullable = true, length = 50)
	private String tranType;

	@Column(name = "tran_scope", nullable = true, length = 100)
	private String tranScope;

	@Column(name = "contract_no", nullable = true, length = 50)
	private String contractNo;

	@Column(name = "contract_sign_date", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date contractSignDate;

	@Column(name = "VALLDITY_CONTRACT", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date valldityContract;

	@Column(name = "credit_status", nullable = true, length = 20)
	private String creditStatus;

	@Column(name = "tran_date", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date tranDate;

	@Column(name = "tran_price", nullable = true, length = 16)
	private String tranPrice;

	@Column(name = "bz", nullable = true, length = 16)
	private String bz;

	@Column(name = "VALIDITY_CONTRACT", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date validityContract;

	@Column(name = "CREDIT_AMOUNT", nullable = true, precision = 10, scale = 0)
	private Long creditAmount;

	@Column(name = "VALIDITY_BALANCE", nullable = true, precision = 10, scale = 0)
	private Long validityBalance;

	@Column(name = "CREDIT_START_DATE", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date creditStartDate;

	@Column(name = "CREDIT_END_DATE", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date creditEndDate;

	@Column(name = "GUARA_MODE", nullable = true, length = 100)
	private String guaraMode;

	@Column(name = "COLLATERAL", nullable = true, length = 100)
	private String collateral;

	@Column(name = "GUARANTOR", nullable = true, length = 100)
	private String guarantor;

	@Column(name = "BZR_IDENTIFY_TYPE", nullable = true, length = 100)
	private String bzrIdentifyType;

	@Column(name = "BZR_IDENT_NUMBER", nullable = true, length = 100)
	private String bzrIdentNumber;

	@Column(name = "FIVE_LEVEL", nullable = true, length = 100)
	private String fiveLevel;

	@Column(name = "SECURIT_DEPOSIT", nullable = true, precision = 65, scale = 0)
	private java.math.BigDecimal securitDeposit;

	@Column(name = "MARGIN_RATIO", nullable = true, length = 100)
	private Float marginRatio;

	@Column(name = "bank_deposit", nullable = true, length = 100)
	private String bankDeposit;

	@Column(name = "national_debt", nullable = true, length = 100)
	private String nationalDebt;

	@Column(name = "BUSINESS_TYPE", nullable = true, length = 21)
	private String businessType;

	@Column(name = "pricing_strategy", nullable = true, length = 1024)
	private String pricingStrategy;

	@Column(name = "is_insider", nullable = true, length = 255)
	private String isInsider;

	@Column(name = "important_trade", nullable = true, length = 255)
	private String importantTrade;

	@Column(name = "product_code", nullable = true, length = 20)
	private String productCode;

	@Column(name = "internal_apply_no", nullable = true, length = 20)
	private String internalApplyNo;

	@Column(name = "rate", nullable = true, length = 10)
	private String rate;

	@Column(name = "apply_time", nullable = true, length = 10)
	private String applyTime;

	@Column(name = "apply_over_time", nullable = true, length = 11)
	private String applyOverTime;

	@Column(name = "regulator", nullable = true, length = 20)
	private String regulator;

	@Column(name = "credit_plan", nullable = true, length = 100)
	private String creditPlan;

	@Column(name = "credit_status_comment", nullable = true, length = 32)
	private String creditStatusComment;

	@Column(name = "shareholding_proportion", nullable = true, length = 10)
	private String shareholdingProportion;
	//columns END


	public Integer getPid() {
		return this.pid;
	}
	
	public void setPid(Integer value) {
		this.pid = value;
	}
	

	public String getTransNum() {
		return this.transNum;
	}
	
	public void setTransNum(String value) {
		this.transNum = value;
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
	

	public String getCreditNo() {
		return this.creditNo;
	}
	
	public void setCreditNo(String value) {
		this.creditNo = value;
	}
	

	public Date getCreditConfirmDate() {
		return this.creditConfirmDate;
	}
	
	public void setCreditConfirmDate(Date value) {
		this.creditConfirmDate = value;
	}
	

	public java.math.BigDecimal getCreditLimit() {
		return this.creditLimit;
	}
	
	public void setCreditLimit(java.math.BigDecimal value) {
		this.creditLimit = value;
	}
	

	public java.math.BigDecimal getForeignCurrToRmb() {
		return this.foreignCurrToRmb;
	}
	
	public void setForeignCurrToRmb(java.math.BigDecimal value) {
		this.foreignCurrToRmb = value;
	}
	

	public String getCreditItems() {
		return this.creditItems;
	}
	
	public void setCreditItems(String value) {
		this.creditItems = value;
	}
	

	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String value) {
		this.remark = value;
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
	

	public String getManageOrg() {
		return this.manageOrg;
	}
	
	public void setManageOrg(String value) {
		this.manageOrg = value;
	}
	

	public String getIdentifyType() {
		return this.identifyType;
	}
	
	public void setIdentifyType(String value) {
		this.identifyType = value;
	}
	

	public String getIdentifyNumber() {
		return this.identifyNumber;
	}
	
	public void setIdentifyNumber(String value) {
		this.identifyNumber = value;
	}
	

	public String getTranType() {
		return this.tranType;
	}
	
	public void setTranType(String value) {
		this.tranType = value;
	}
	

	public String getTranScope() {
		return this.tranScope;
	}
	
	public void setTranScope(String value) {
		this.tranScope = value;
	}
	

	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String value) {
		this.contractNo = value;
	}
	

	public Date getContractSignDate() {
		return this.contractSignDate;
	}
	
	public void setContractSignDate(Date value) {
		this.contractSignDate = value;
	}
	

	public Date getValldityContract() {
		return this.valldityContract;
	}
	
	public void setValldityContract(Date value) {
		this.valldityContract = value;
	}
	

	public String getCreditStatus() {
		return this.creditStatus;
	}
	
	public void setCreditStatus(String value) {
		this.creditStatus = value;
	}
	

	public Date getTranDate() {
		return this.tranDate;
	}
	
	public void setTranDate(Date value) {
		this.tranDate = value;
	}
	

	public String getTranPrice() {
		return this.tranPrice;
	}
	
	public void setTranPrice(String value) {
		this.tranPrice = value;
	}
	

	public String getBz() {
		return this.bz;
	}
	
	public void setBz(String value) {
		this.bz = value;
	}
	

	public Date getValidityContract() {
		return this.validityContract;
	}
	
	public void setValidityContract(Date value) {
		this.validityContract = value;
	}
	

	public Long getCreditAmount() {
		return this.creditAmount;
	}
	
	public void setCreditAmount(Long value) {
		this.creditAmount = value;
	}
	

	public Long getValidityBalance() {
		return this.validityBalance;
	}
	
	public void setValidityBalance(Long value) {
		this.validityBalance = value;
	}
	

	public Date getCreditStartDate() {
		return this.creditStartDate;
	}
	
	public void setCreditStartDate(Date value) {
		this.creditStartDate = value;
	}
	

	public Date getCreditEndDate() {
		return this.creditEndDate;
	}
	
	public void setCreditEndDate(Date value) {
		this.creditEndDate = value;
	}
	

	public String getGuaraMode() {
		return this.guaraMode;
	}
	
	public void setGuaraMode(String value) {
		this.guaraMode = value;
	}
	

	public String getCollateral() {
		return this.collateral;
	}
	
	public void setCollateral(String value) {
		this.collateral = value;
	}
	

	public String getGuarantor() {
		return this.guarantor;
	}
	
	public void setGuarantor(String value) {
		this.guarantor = value;
	}
	

	public String getBzrIdentifyType() {
		return this.bzrIdentifyType;
	}
	
	public void setBzrIdentifyType(String value) {
		this.bzrIdentifyType = value;
	}
	

	public String getBzrIdentNumber() {
		return this.bzrIdentNumber;
	}
	
	public void setBzrIdentNumber(String value) {
		this.bzrIdentNumber = value;
	}
	

	public String getFiveLevel() {
		return this.fiveLevel;
	}
	
	public void setFiveLevel(String value) {
		this.fiveLevel = value;
	}
	

	public java.math.BigDecimal getSecuritDeposit() {
		return this.securitDeposit;
	}
	
	public void setSecuritDeposit(java.math.BigDecimal value) {
		this.securitDeposit = value;
	}
	

	public Float getMarginRatio() {
		return this.marginRatio;
	}
	
	public void setMarginRatio(Float value) {
		this.marginRatio = value;
	}
	

	public String getBankDeposit() {
		return this.bankDeposit;
	}
	
	public void setBankDeposit(String value) {
		this.bankDeposit = value;
	}
	

	public String getNationalDebt() {
		return this.nationalDebt;
	}
	
	public void setNationalDebt(String value) {
		this.nationalDebt = value;
	}
	

	public String getBusinessType() {
		return this.businessType;
	}
	
	public void setBusinessType(String value) {
		this.businessType = value;
	}
	

	public String getPricingStrategy() {
		return this.pricingStrategy;
	}
	
	public void setPricingStrategy(String value) {
		this.pricingStrategy = value;
	}
	

	public String getIsInsider() {
		return this.isInsider;
	}
	
	public void setIsInsider(String value) {
		this.isInsider = value;
	}
	

	public String getImportantTrade() {
		return this.importantTrade;
	}
	
	public void setImportantTrade(String value) {
		this.importantTrade = value;
	}
	

	public String getProductCode() {
		return this.productCode;
	}
	
	public void setProductCode(String value) {
		this.productCode = value;
	}
	

	public String getInternalApplyNo() {
		return this.internalApplyNo;
	}
	
	public void setInternalApplyNo(String value) {
		this.internalApplyNo = value;
	}
	

	public String getRate() {
		return this.rate;
	}
	
	public void setRate(String value) {
		this.rate = value;
	}
	

	public String getApplyTime() {
		return this.applyTime;
	}
	
	public void setApplyTime(String value) {
		this.applyTime = value;
	}
	

	public String getApplyOverTime() {
		return this.applyOverTime;
	}
	
	public void setApplyOverTime(String value) {
		this.applyOverTime = value;
	}
	

	public String getRegulator() {
		return this.regulator;
	}
	
	public void setRegulator(String value) {
		this.regulator = value;
	}
	

	public String getCreditPlan() {
		return this.creditPlan;
	}
	
	public void setCreditPlan(String value) {
		this.creditPlan = value;
	}
	

	public String getCreditStatusComment() {
		return this.creditStatusComment;
	}
	
	public void setCreditStatusComment(String value) {
		this.creditStatusComment = value;
	}
	

	public String getShareholdingProportion() {
		return this.shareholdingProportion;
	}
	
	public void setShareholdingProportion(String value) {
		this.shareholdingProportion = value;
	}
	

	@Override
    public String toString() {
		return new StringBuilder("credit[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("transNum = ").append(transNum).append(", \n")
						.append("relatedPartyName = ").append(relatedPartyName).append(", \n")
						.append("relatedPartyCertNo = ").append(relatedPartyCertNo).append(", \n")
						.append("relatedPartyType = ").append(relatedPartyType).append(", \n")
						.append("isDeclared = ").append(isDeclared).append(", \n")
						.append("creditNo = ").append(creditNo).append(", \n")
						.append("creditConfirmDate = ").append(creditConfirmDate).append(", \n")
						.append("creditLimit = ").append(creditLimit).append(", \n")
						.append("foreignCurrToRmb = ").append(foreignCurrToRmb).append(", \n")
						.append("creditItems = ").append(creditItems).append(", \n")
						.append("remark = ").append(remark).append(", \n")
						.append("status = ").append(status).append(", \n")
						.append("statusComment = ").append(statusComment).append(", \n")
						.append("manageOrg = ").append(manageOrg).append(", \n")
						.append("identifyType = ").append(identifyType).append(", \n")
						.append("identifyNumber = ").append(identifyNumber).append(", \n")
						.append("tranType = ").append(tranType).append(", \n")
						.append("tranScope = ").append(tranScope).append(", \n")
						.append("contractNo = ").append(contractNo).append(", \n")
						.append("contractSignDate = ").append(contractSignDate).append(", \n")
						.append("valldityContract = ").append(valldityContract).append(", \n")
						.append("creditStatus = ").append(creditStatus).append(", \n")
						.append("tranDate = ").append(tranDate).append(", \n")
						.append("tranPrice = ").append(tranPrice).append(", \n")
						.append("bz = ").append(bz).append(", \n")
						.append("validityContract = ").append(validityContract).append(", \n")
						.append("creditAmount = ").append(creditAmount).append(", \n")
						.append("validityBalance = ").append(validityBalance).append(", \n")
						.append("creditStartDate = ").append(creditStartDate).append(", \n")
						.append("creditEndDate = ").append(creditEndDate).append(", \n")
						.append("guaraMode = ").append(guaraMode).append(", \n")
						.append("collateral = ").append(collateral).append(", \n")
						.append("guarantor = ").append(guarantor).append(", \n")
						.append("bzrIdentifyType = ").append(bzrIdentifyType).append(", \n")
						.append("bzrIdentNumber = ").append(bzrIdentNumber).append(", \n")
						.append("fiveLevel = ").append(fiveLevel).append(", \n")
						.append("securitDeposit = ").append(securitDeposit).append(", \n")
						.append("marginRatio = ").append(marginRatio).append(", \n")
						.append("bankDeposit = ").append(bankDeposit).append(", \n")
						.append("nationalDebt = ").append(nationalDebt).append(", \n")
						.append("businessType = ").append(businessType).append(", \n")
						.append("pricingStrategy = ").append(pricingStrategy).append(", \n")
						.append("isInsider = ").append(isInsider).append(", \n")
						.append("importantTrade = ").append(importantTrade).append(", \n")
						.append("productCode = ").append(productCode).append(", \n")
						.append("internalApplyNo = ").append(internalApplyNo).append(", \n")
						.append("rate = ").append(rate).append(", \n")
						.append("applyTime = ").append(applyTime).append(", \n")
						.append("applyOverTime = ").append(applyOverTime).append(", \n")
						.append("regulator = ").append(regulator).append(", \n")
						.append("creditPlan = ").append(creditPlan).append(", \n")
						.append("creditStatusComment = ").append(creditStatusComment).append(", \n")
						.append("shareholdingProportion = ").append(shareholdingProportion).append(", \n")
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
		if(obj instanceof Credit == false) {return false;}
		if(this == obj) {return true;}
		Credit other = (Credit)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

