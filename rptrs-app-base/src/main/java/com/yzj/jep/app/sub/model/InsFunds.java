/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月31日 10:16:57
 *
 */

package com.yzj.jep.app.sub.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "sub_ins_funds")
public class InsFunds extends BasePO {

	public static final String TABLE_ALIAS = "InsFunds";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_REL_ID = "关联方ID";
	public static final String ALIAS_REL_TYPE = "关联方类型";
	public static final String ALIAS_REL_NAME = "关联方名称";
	public static final String ALIAS_REL_NUMBER = "关联方证件号码";
	public static final String ALIAS_TRADE_ORG = "交易机构";
	public static final String ALIAS_TRAN_TYPE = "交易类型";
	public static final String ALIAS_DATA_SOURCES = "数据来源";
	public static final String ALIAS_TRAN_DATE = "交易日期";
	public static final String ALIAS_MAJOR_TRADE = "是否重大关联交易";
	public static final String ALIAS_CALIBER = "口径";
	public static final String ALIAS_STATUS = "前置流程状态";
	public static final String ALIAS_STATUS_COMMENT = "前置流程状态说明";
	public static final String ALIAS_TOTOL_TRADE_AMOUNT = "本年度与该关联方发生的关联交易累计金额（万元）";
	public static final String ALIAS_CONTRACT_NO = "合同编号";
	public static final String ALIAS_TRADE_AMOUNT = "交易金额（万元）";
	public static final String ALIAS_BUS_START_TIME = "业务起始日期";
	public static final String ALIAS_BUS_END_TIME = "业务终止日期";
	public static final String ALIAS_INVESTMENT_TYPE = "投资类别";
	public static final String ALIAS_INVESTMENT_METHOD = "投资方式";
	public static final String ALIAS_INVESTED_ENTERPRISES = "被投资企业情况";
	public static final String ALIAS_EQUITY_LAST_TRADE_PRICE = "未上市权益类_前次交易价格";
	public static final String ALIAS_IM_LAST_TRADE_PRICE = "不动产类_前次交易价格";
	public static final String ALIAS_IM_SUBJECT_MATTER = "不动产类_标的情况";
	public static final String ALIAS_OUT_SUBJECT_MATTER = "境外投资_标的情况";
	public static final String ALIAS_SHARE_DEBT_RATIO = "股债比";
	public static final String ALIAS_FINANCIAL_ASSSTS_TYPE = "金融资产类别";
	public static final String ALIAS_FINANCIAL_ASSSTS_NUM = "金融资产编号";
	public static final String ALIAS_FINANCIAL_PRO_SCALE = "金融产品发行规模";
	public static final String ALIAS_FINANCIAL_PRO_RATIO = "购买金融产品份额占比（%）";
	public static final String ALIAS_UNDERLYING_ASSET = "底层资产";
	public static final String ALIAS_UNDER_IS_REL = "底层资产是否为关联方";
	public static final String ALIAS_UNDER_INVOLVE_REL = "底层基础资产是否涉及控股股东或控股股东的关联方";
	public static final String ALIAS_UNDER_REL = "底层资产关联关系";
	public static final String ALIAS_INVESTED_ENTERPRISES_NAME = "被投企业名称";
	public static final String ALIAS_BUS_MODEL = "业务模式";
	public static final String ALIAS_EARNINGS_EXPECTAIONS = "盈利预期";
	public static final String ALIAS_INVESTMENT_RATIO = "投资比例（%）";
	public static final String ALIAS_INVESTMENT_ALLOCATION = "投资收益分配情况";
	public static final String ALIAS_LOSS_SHARE = "损失分担情况";
	public static final String ALIAS_TRADE_PRICE = "交易价格";
	public static final String ALIAS_PRICING_POLICY = "定价政策";
	public static final String ALIAS_TRADE_SUBJECT = "交易标的";
	public static final String ALIAS_REMARK = "其他事项说明";
	public static final String ALIAS_IS_EFFECTIVE = "是否生效";
	public static final String ALIAS_APPROVE_TRADE_AMOUNT = "审批金额";
	public static final String ALIAS_TRANS_NUM = "交易编号";
	public static final String ALIAS_DEPOSIT_TERMS = "存款期限";
	public static final String ALIAS_IS_DEAL_PRICE = "交易定价是否为国家规定";
	public static final String ALIAS_RULE_RESULT = "规则结果";
	public static final String ALIAS_PRODUCT_CODE = "产品id";
	public static final String ALIAS_PRODUCT_NAME = "产品名称";
	public static final String ALIAS_IS_HOLDING_SUBSIDIARY = "是否为控股子公司";
	public static final String ALIAS_HOLDING_SUBSIDIARY_NAME = "控股子公司名称";
	public static final String ALIAS_HOLDING_SUBSIDIARY_NUMBER = "控股子公司证件号码";
	public static final String ALIAS_IS_OVERSEASINVESTMENT = "是否境外投资";
	public static final String ALIAS_FINANCIAL_IMPACT = "对财务状况及经营成果的影响";
	public static final String ALIAS_TRA_SUB_NAME = "交易主体名称";
	public static final String ALIAS_MAJOR_TYPE = "达到重大关联交易的情形";
	public static final String ALIAS_CONTRACT_AMOUNT = "合同金额（万元）";
	public static final String ALIAS_HOLDING_SUBSIDIARY_TYPE = "控股公司证件类型";
	public static final String ALIAS_IS_CASH_SUBSCRIPTION = "是否现金认购";
	public static final String ALIAS_FINANCIAL_ASSSTS_NAME = "金融产品名称";
	public static final String ALIAS_STOCKRIGHT_TYPE = "股权分类";
	public static final String ALIAS_AMEND_ID = "操作人id";
	public static final String ALIAS_AMEND_NAME = "操作人姓名";

	public static final String 	REL_TRADE_AMOUNT="关联交易金额";


	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "PID", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "REL_ID", nullable = true, length = 10)
	private Integer relId;

	@Column(name = "REL_TYPE", nullable = true, length = 12)
	private String relType;

	@Column(name = "REL_NAME", nullable = true, length = 20)
	private String relName;

	@Column(name = "REL_NUMBER", nullable = true, length = 100)
	private String relNumber;

	@Column(name = "TRADE_ORG", nullable = true, length = 100)
	private String tradeOrg;

	@Column(name = "TRAN_TYPE", nullable = true, length = 50)
	private String tranType;

	@Column(name = "DATA_SOURCES", nullable = true, length = 10)
	private String dataSources;

	@Column(name = "TRAN_DATE", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date tranDate;

	@Column(name = "MAJOR_TRADE", nullable = true, length = 10)
	private String majorTrade;

	@Column(name = "CALIBER", nullable = true, length = 50)
	private String caliber;

	@Column(name = "STATUS", nullable = true, length = 10)
	private String status;

	@Column(name = "STATUS_COMMENT", nullable = true, length = 100)
	private String statusComment;

	@Column(name = "TOTOL_TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal totolTradeAmount;

	@Column(name = "CONTRACT_NO", nullable = true, length = 50)
	private String contractNo;

	@Column(name = "TRADE_AMOUNT", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal tradeAmount;

	@Column(name = "BUS_START_TIME", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date busStartTime;

	@Column(name = "BUS_END_TIME", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date busEndTime;

	@Column(name = "INVESTMENT_TYPE", nullable = true, length = 10)
	private String investmentType;

	@Column(name = "INVESTMENT_METHOD", nullable = true, length = 10)
	private String investmentMethod;

	@Column(name = "INVESTED_ENTERPRISES", nullable = true, length = 500)
	private String investedEnterprises;

	@Column(name = "EQUITY_LAST_TRADE_PRICE", nullable = true,  length = 100)
	private String equityLastTradePrice;

	@Column(name = "IM_LAST_TRADE_PRICE", nullable = true,  length = 100)
	private String imLastTradePrice;

	@Column(name = "IM_SUBJECT_MATTER", nullable = true, length = 100)
	private String imSubjectMatter;

	@Column(name = "OUT_SUBJECT_MATTER", nullable = true, length = 100)
	private String outSubjectMatter;

	@Column(name = "SHARE_DEBT_RATIO", nullable = true, length = 50)
	private String shareDebtRatio;

	@Column(name = "FINANCIAL_ASSSTS_TYPE", nullable = true, length = 50)
	private String financialAssstsType;

	@Column(name = "FINANCIAL_ASSSTS_NUM", nullable = true, length = 50)
	private String financialAssstsNum;

	@Column(name = "FINANCIAL_PRO_SCALE", nullable = true, length = 50)
	private String financialProScale;

	@Column(name = "FINANCIAL_PRO_RATIO", nullable = true, length = 50)
	private String financialProRatio;

	@Column(name = "UNDERLYING_ASSET", nullable = true, length = 50)
	private String underlyingAsset;

	@Column(name = "UNDER_IS_REL", nullable = true, length = 10)
	private String underIsRel;

	@Column(name = "UNDER_INVOLVE_REL", nullable = true, length = 10)
	private String underInvolveRel;

	@Column(name = "UNDER_REL", nullable = true, length = 50)
	private String underRel;

	@Column(name = "INVESTED_ENTERPRISES_NAME", nullable = true, length = 50)
	private String investedEnterprisesName;

	@Column(name = "BUS_MODEL", nullable = true, length = 50)
	private String busModel;

	@Column(name = "EARNINGS_EXPECTAIONS", nullable = true, length = 50)
	private String earningsExpectaions;

	@Column(name = "INVESTMENT_RATIO", nullable = true, length = 50)
	private String investmentRatio;

	@Column(name = "INVESTMENT_ALLOCATION", nullable = true, length = 200)
	private String investmentAllocation;

	@Column(name = "LOSS_SHARE", nullable = true, length = 50)
	private String lossShare;

	@Column(name = "TRADE_PRICE", nullable = true, length = 1000)
	private String tradePrice;

	@Column(name = "PRICING_POLICY", nullable = true, length = 1000)
	private String pricingPolicy;

	@Column(name = "TRADE_SUBJECT", nullable = true, length = 1000)
	private String tradeSubject;

	@Column(name = "REMARK", nullable = true, length = 1000)
	private String remark;

	@Column(name = "IS_EFFECTIVE", nullable = true, length = 1)
	private String isEffective;

	@Column(name = "APPROVE_TRADE_AMOUNT", nullable = true, precision = 10, scale = 0)
	private Long approveTradeAmount;

	@Column(name = "TRANS_NUM", nullable = true, length = 32)
	private String transNum;

	@Column(name = "DEPOSIT_TERMS", nullable = true, length = 10)
	private String depositTerms;

	@Column(name = "IS_DEAL_PRICE", nullable = true, length = 5)
	private String isDealPrice;

	@Column(name = "RULE_RESULT", nullable = true, length = 100)
	private String ruleResult;

	@Column(name = "product_code", nullable = true, length = 20)
	private String productCode;

	@Column(name = "product_name", nullable = true, length = 50)
	private String productName;

	@Column(name = "is_HoldingSubsidiary", nullable = true, length = 10)
	private String isHoldingSubsidiary;

	@Column(name = "HoldingSubsidiary_name", nullable = true, length = 20)
	private String holdingSubsidiaryName;

	@Column(name = "HoldingSubsidiary_number", nullable = true, length = 100)
	private String holdingSubsidiaryNumber;

	@Column(name = "is_Overseasinvestment", nullable = true, length = 10)
	private String isOverseasinvestment;

	@Column(name = "Financial_Impact", nullable = true, length = 400)
	private String financialImpact;

	@Column(name = "Tra_sub_Name", nullable = true, length = 20)
	private String traSubName;

	@Column(name = "Major_Type", nullable = true, length = 10)
	private String majorType;

	@Column(name = "Contract_Amount", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal contractAmount;

	@Column(name = "HoldingSubsidiary_type", nullable = true, length = 10)
	private String holdingSubsidiaryType;

	@Column(name = "is_CashSubscription", nullable = true, length = 10)
	private String isCashSubscription;

	@Column(name = "FINANCIAL_ASSSTS_NAME", nullable = true, length = 50)
	private String financialAssstsName;

	@Column(name = "stockright_type", nullable = true, length = 10)
	private String stockrightType;


	@Column(name = "AMEND_ID", nullable = true, length = 10)
	private String amendId;

	@Column(name = "AMEND_NAME", nullable = true, length = 10)
	private String amendName;

	@Column(name = "is_extract", nullable = true, length = 6)
	private String isExtract;

	@Column(name = "is_discovery", nullable = true, length = 6)
	private String isDiscovery;//披露状态

	@Column(name="rel_Trade_Amount" ,nullable = true,length = 50)
	private BigDecimal relTradeAmount;

	@Column(name = "pub_id", length = 32)
	private String pubId;//披露文件标识
	//columns END
	/**
	 * 业务类型
	 */
	@Column(name = "bus_type", nullable = true, length = 10)
	private String busType;
	/**
	 * 交易概述
	 */
	@Column(name = "TRAN_NAME", nullable = true, length = 10)
	private String tranName;

	/** 单笔关联交易类型 */
	@Column(name = "single_related_trantype", nullable = true, length = 1)
	private String singleRelatedTranType;
	public String getPubId() {
		return pubId;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getTranName() {
		return tranName;
	}

	public void setTranName(String tranName) {
		this.tranName = tranName;
	}

	public String getSingleRelatedTranType() {
		return singleRelatedTranType;
	}

	public void setSingleRelatedTranType(String singleRelatedTranType) {
		this.singleRelatedTranType = singleRelatedTranType;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getIsDiscovery() {
		return isDiscovery;
	}

	public void setIsDiscovery(String isDiscovery) {
		this.isDiscovery = isDiscovery;
	}

	public BigDecimal getRelTradeAmount() {
		return relTradeAmount;
	}

	public void setRelTradeAmount(BigDecimal relTradeAmount) {
		this.relTradeAmount = relTradeAmount;
	}

	public String getIsExtract() {
		return isExtract;
	}

	public void setIsExtract(String isExtract) {
		this.isExtract = isExtract;
	}

	public String getAmendId() {
		return amendId;
	}

	public void setAmendId(String amendId) {
		this.amendId = amendId;
	}

	public String getAmendName() {
		return amendName;
	}

	public void setAmendName(String amendName) {
		this.amendName = amendName;
	}

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer value) {
		this.pid = value;
	}


	public Integer getRelId() {
		return this.relId;
	}

	public void setRelId(Integer value) {
		this.relId = value;
	}


	public String getRelType() {
		return this.relType;
	}

	public void setRelType(String value) {
		this.relType = value;
	}


	public String getRelName() {
		return this.relName;
	}

	public void setRelName(String value) {
		this.relName = value;
	}


	public String getRelNumber() {
		return this.relNumber;
	}

	public void setRelNumber(String value) {
		this.relNumber = value;
	}


	public String getTradeOrg() {
		return this.tradeOrg;
	}

	public void setTradeOrg(String value) {
		this.tradeOrg = value;
	}


	public String getTranType() {
		return this.tranType;
	}

	public void setTranType(String value) {
		this.tranType = value;
	}


	public String getDataSources() {
		return this.dataSources;
	}

	public void setDataSources(String value) {
		this.dataSources = value;
	}


	public Date getTranDate() {
		return this.tranDate;
	}

	public void setTranDate(Date value) {
		this.tranDate = value;
	}


	public String getMajorTrade() {
		return this.majorTrade;
	}

	public void setMajorTrade(String value) {
		this.majorTrade = value;
	}


	public String getCaliber() {
		return this.caliber;
	}

	public void setCaliber(String value) {
		this.caliber = value;
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


	public java.math.BigDecimal getTotolTradeAmount() {
		return this.totolTradeAmount;
	}

	public void setTotolTradeAmount(java.math.BigDecimal value) {
		this.totolTradeAmount = value;
	}


	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String value) {
		this.contractNo = value;
	}


	public java.math.BigDecimal getTradeAmount() {
		return this.tradeAmount;
	}

	public void setTradeAmount(java.math.BigDecimal value) {
		this.tradeAmount = value;
	}


	public Date getBusStartTime() {
		return this.busStartTime;
	}

	public void setBusStartTime(Date value) {
		this.busStartTime = value;
	}


	public Date getBusEndTime() {
		return this.busEndTime;
	}

	public void setBusEndTime(Date value) {
		this.busEndTime = value;
	}


	public String getInvestmentType() {
		return this.investmentType;
	}

	public void setInvestmentType(String value) {
		this.investmentType = value;
	}


	public String getInvestmentMethod() {
		return this.investmentMethod;
	}

	public void setInvestmentMethod(String value) {
		this.investmentMethod = value;
	}


	public String getInvestedEnterprises() {
		return this.investedEnterprises;
	}

	public void setInvestedEnterprises(String value) {
		this.investedEnterprises = value;
	}


	public String getEquityLastTradePrice() {
		return this.equityLastTradePrice;
	}

	public void setEquityLastTradePrice(String value) {
		this.equityLastTradePrice = value;
	}


	public String getImLastTradePrice() {
		return this.imLastTradePrice;
	}

	public void setImLastTradePrice(String value) {
		this.imLastTradePrice = value;
	}


	public String getImSubjectMatter() {
		return this.imSubjectMatter;
	}

	public void setImSubjectMatter(String value) {
		this.imSubjectMatter = value;
	}


	public String getOutSubjectMatter() {
		return this.outSubjectMatter;
	}

	public void setOutSubjectMatter(String value) {
		this.outSubjectMatter = value;
	}


	public String getShareDebtRatio() {
		return this.shareDebtRatio;
	}

	public void setShareDebtRatio(String value) {
		this.shareDebtRatio = value;
	}


	public String getFinancialAssstsType() {
		return this.financialAssstsType;
	}

	public void setFinancialAssstsType(String value) {
		this.financialAssstsType = value;
	}


	public String getFinancialAssstsNum() {
		return this.financialAssstsNum;
	}

	public void setFinancialAssstsNum(String value) {
		this.financialAssstsNum = value;
	}


	public String getFinancialProScale() {
		return this.financialProScale;
	}

	public void setFinancialProScale(String value) {
		this.financialProScale = value;
	}


	public String getFinancialProRatio() {
		return this.financialProRatio;
	}

	public void setFinancialProRatio(String value) {
		this.financialProRatio = value;
	}


	public String getUnderlyingAsset() {
		return this.underlyingAsset;
	}

	public void setUnderlyingAsset(String value) {
		this.underlyingAsset = value;
	}


	public String getUnderIsRel() {
		return this.underIsRel;
	}

	public void setUnderIsRel(String value) {
		this.underIsRel = value;
	}


	public String getUnderInvolveRel() {
		return this.underInvolveRel;
	}

	public void setUnderInvolveRel(String value) {
		this.underInvolveRel = value;
	}


	public String getUnderRel() {
		return this.underRel;
	}

	public void setUnderRel(String value) {
		this.underRel = value;
	}


	public String getInvestedEnterprisesName() {
		return this.investedEnterprisesName;
	}

	public void setInvestedEnterprisesName(String value) {
		this.investedEnterprisesName = value;
	}


	public String getBusModel() {
		return this.busModel;
	}

	public void setBusModel(String value) {
		this.busModel = value;
	}


	public String getEarningsExpectaions() {
		return this.earningsExpectaions;
	}

	public void setEarningsExpectaions(String value) {
		this.earningsExpectaions = value;
	}


	public String getInvestmentRatio() {
		return this.investmentRatio;
	}

	public void setInvestmentRatio(String value) {
		this.investmentRatio = value;
	}


	public String getInvestmentAllocation() {
		return this.investmentAllocation;
	}

	public void setInvestmentAllocation(String value) {
		this.investmentAllocation = value;
	}


	public String getLossShare() {
		return this.lossShare;
	}

	public void setLossShare(String value) {
		this.lossShare = value;
	}


	public String getTradePrice() {
		return this.tradePrice;
	}

	public void setTradePrice(String value) {
		this.tradePrice = value;
	}


	public String getPricingPolicy() {
		return this.pricingPolicy;
	}

	public void setPricingPolicy(String value) {
		this.pricingPolicy = value;
	}


	public String getTradeSubject() {
		return this.tradeSubject;
	}

	public void setTradeSubject(String value) {
		this.tradeSubject = value;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String value) {
		this.remark = value;
	}


	public String getIsEffective() {
		return this.isEffective;
	}

	public void setIsEffective(String value) {
		this.isEffective = value;
	}


	public Long getApproveTradeAmount() {
		return this.approveTradeAmount;
	}

	public void setApproveTradeAmount(Long value) {
		this.approveTradeAmount = value;
	}


	public String getTransNum() {
		return this.transNum;
	}

	public void setTransNum(String value) {
		this.transNum = value;
	}


	public String getDepositTerms() {
		return this.depositTerms;
	}

	public void setDepositTerms(String value) {
		this.depositTerms = value;
	}


	public String getIsDealPrice() {
		return this.isDealPrice;
	}

	public void setIsDealPrice(String value) {
		this.isDealPrice = value;
	}


	public String getRuleResult() {
		return this.ruleResult;
	}

	public void setRuleResult(String value) {
		this.ruleResult = value;
	}


	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String value) {
		this.productCode = value;
	}


	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String value) {
		this.productName = value;
	}


	public String getIsHoldingSubsidiary() {
		return this.isHoldingSubsidiary;
	}

	public void setIsHoldingSubsidiary(String value) {
		this.isHoldingSubsidiary = value;
	}


	public String getHoldingSubsidiaryName() {
		return this.holdingSubsidiaryName;
	}

	public void setHoldingSubsidiaryName(String value) {
		this.holdingSubsidiaryName = value;
	}


	public String getHoldingSubsidiaryNumber() {
		return this.holdingSubsidiaryNumber;
	}

	public void setHoldingSubsidiaryNumber(String value) {
		this.holdingSubsidiaryNumber = value;
	}


	public String getIsOverseasinvestment() {
		return this.isOverseasinvestment;
	}

	public void setIsOverseasinvestment(String value) {
		this.isOverseasinvestment = value;
	}


	public String getFinancialImpact() {
		return this.financialImpact;
	}

	public void setFinancialImpact(String value) {
		this.financialImpact = value;
	}


	public String getTraSubName() {
		return this.traSubName;
	}

	public void setTraSubName(String value) {
		this.traSubName = value;
	}


	public String getMajorType() {
		return this.majorType;
	}

	public void setMajorType(String value) {
		this.majorType = value;
	}


	public java.math.BigDecimal getContractAmount() {
		return this.contractAmount;
	}

	public void setContractAmount(java.math.BigDecimal value) {
		this.contractAmount = value;
	}


	public String getHoldingSubsidiaryType() {
		return this.holdingSubsidiaryType;
	}

	public void setHoldingSubsidiaryType(String value) {
		this.holdingSubsidiaryType = value;
	}


	public String getIsCashSubscription() {
		return this.isCashSubscription;
	}

	public void setIsCashSubscription(String value) {
		this.isCashSubscription = value;
	}


	public String getFinancialAssstsName() {
		return this.financialAssstsName;
	}

	public void setFinancialAssstsName(String value) {
		this.financialAssstsName = value;
	}


	public String getStockrightType() {
		return this.stockrightType;
	}

	public void setStockrightType(String value) {
		this.stockrightType = value;
	}



	//报送文件需要的映射
	@Transient
	private String relTypeSub;
	@Transient
	private String tranDateSub;
	@Transient
	private String busStartTimeSub;
	@Transient
	private String busEndTimeSub;
	@Transient
	private String tranTypeSub;
	@Transient
	private String IsHoldingSubsidiarySub;
	@Transient
	private String isOverseasinvestmentSub;
	@Transient
	private String underIsRelSub;
	@Transient
	private String underInvolveRelSub;

	@Transient
	private String investmentMethodSub;

	public String getInvestmentMethodSub() {
		switch (investmentMethod) {
			case "01":
				return "1";
			case "02":
				return "2";
			default:
				return "";
		}
	}
	public String getUnderIsRelSub() {
		return "1".equals(underIsRel) ? "1" : "2" ;
	}
	public String getUnderInvolveRelSub() {
		return "1".equals(underInvolveRel) ? "1" : "2" ;
	}


	public String getRelTypeSub() {
		if ("ORG".equals(relType)){
			return "1";
		} else if ("PSN".equals(relType)) {
			return "2";
		}
		return "3";
	}

	public String getTranDateSub() {
		if (tranDate != null) {
			return DateUtils.formatDateShort(tranDate);
		}
		return "";
	}

	public String getBusStartTimeSub() {
		if (busStartTime != null) {
			return DateUtils.formatDateShort(busStartTime);
		}
		return "";
	}

	public String getBusEndTimeSub() {
		if (busEndTime != null) {
			return DateUtils.formatDateShort(busEndTime);
		}
		return "";
	}

	public String getTranTypeSub() {
		switch (tranType){
			case "01" :
				return "1";
			case "02" :
				return "2";
			case "03" :
				return "3";
			case "04" :
				return "4";
			case "05" :
				return "5";
			case "06" :
				return "6";
			default :
				return null;
		}
	}

	public String getIsHoldingSubsidiarySub() {
		return "1".equals(isHoldingSubsidiary) ? "0" : "1" ;
	}

	public String getIsOverseasinvestmentSub() {
		return "1".equals(isOverseasinvestment) ? "0" : "1" ;
	}




	public String toString() {
		return new StringBuilder("insFunds[\n")
				.append("pid = ").append(pid).append(", \n")
				.append("relId = ").append(relId).append(", \n")
				.append("relType = ").append(relType).append(", \n")
				.append("relName = ").append(relName).append(", \n")
				.append("relNumber = ").append(relNumber).append(", \n")
				.append("tradeOrg = ").append(tradeOrg).append(", \n")
				.append("tranType = ").append(tranType).append(", \n")
				.append("dataSources = ").append(dataSources).append(", \n")
				.append("tranDate = ").append(tranDate).append(", \n")
				.append("majorTrade = ").append(majorTrade).append(", \n")
				.append("caliber = ").append(caliber).append(", \n")
				.append("status = ").append(status).append(", \n")
				.append("statusComment = ").append(statusComment).append(", \n")
				.append("totolTradeAmount = ").append(totolTradeAmount).append(", \n")
				.append("contractNo = ").append(contractNo).append(", \n")
				.append("tradeAmount = ").append(tradeAmount).append(", \n")
				.append("busStartTime = ").append(busStartTime).append(", \n")
				.append("busEndTime = ").append(busEndTime).append(", \n")
				.append("investmentType = ").append(investmentType).append(", \n")
				.append("investmentMethod = ").append(investmentMethod).append(", \n")
				.append("investedEnterprises = ").append(investedEnterprises).append(", \n")
				.append("equityLastTradePrice = ").append(equityLastTradePrice).append(", \n")
				.append("imLastTradePrice = ").append(imLastTradePrice).append(", \n")
				.append("imSubjectMatter = ").append(imSubjectMatter).append(", \n")
				.append("outSubjectMatter = ").append(outSubjectMatter).append(", \n")
				.append("shareDebtRatio = ").append(shareDebtRatio).append(", \n")
				.append("financialAssstsType = ").append(financialAssstsType).append(", \n")
				.append("financialAssstsNum = ").append(financialAssstsNum).append(", \n")
				.append("financialProScale = ").append(financialProScale).append(", \n")
				.append("financialProRatio = ").append(financialProRatio).append(", \n")
				.append("underlyingAsset = ").append(underlyingAsset).append(", \n")
				.append("underIsRel = ").append(underIsRel).append(", \n")
				.append("underInvolveRel = ").append(underInvolveRel).append(", \n")
				.append("underRel = ").append(underRel).append(", \n")
				.append("investedEnterprisesName = ").append(investedEnterprisesName).append(", \n")
				.append("busModel = ").append(busModel).append(", \n")
				.append("earningsExpectaions = ").append(earningsExpectaions).append(", \n")
				.append("investmentRatio = ").append(investmentRatio).append(", \n")
				.append("investmentAllocation = ").append(investmentAllocation).append(", \n")
				.append("lossShare = ").append(lossShare).append(", \n")
				.append("tradePrice = ").append(tradePrice).append(", \n")
				.append("pricingPolicy = ").append(pricingPolicy).append(", \n")
				.append("tradeSubject = ").append(tradeSubject).append(", \n")
				.append("remark = ").append(remark).append(", \n")
				.append("isEffective = ").append(isEffective).append(", \n")
				.append("approveTradeAmount = ").append(approveTradeAmount).append(", \n")
				.append("transNum = ").append(transNum).append(", \n")
				.append("depositTerms = ").append(depositTerms).append(", \n")
				.append("isDealPrice = ").append(isDealPrice).append(", \n")
				.append("ruleResult = ").append(ruleResult).append(", \n")
				.append("productCode = ").append(productCode).append(", \n")
				.append("productName = ").append(productName).append(", \n")
				.append("isHoldingSubsidiary = ").append(isHoldingSubsidiary).append(", \n")
				.append("holdingSubsidiaryName = ").append(holdingSubsidiaryName).append(", \n")
				.append("holdingSubsidiaryNumber = ").append(holdingSubsidiaryNumber).append(", \n")
				.append("isOverseasinvestment = ").append(isOverseasinvestment).append(", \n")
				.append("financialImpact = ").append(financialImpact).append(", \n")
				.append("traSubName = ").append(traSubName).append(", \n")
				.append("majorType = ").append(majorType).append(", \n")
				.append("contractAmount = ").append(contractAmount).append(", \n")
				.append("holdingSubsidiaryType = ").append(holdingSubsidiaryType).append(", \n")
				.append("isCashSubscription = ").append(isCashSubscription).append(", \n")
				.append("financialAssstsName = ").append(financialAssstsName).append(", \n")
				.append("stockrightType = ").append(stockrightType).append(", \n")
				.append("busType = ").append(busType).append(", \n")
				.append("tranName = ").append(tranName).append(", \n")
				.append("singleRelatedTranType = ").append(singleRelatedTranType).append(", \n")
				.append("]")
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getPid())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof InsFunds == false) return false;
		if(this == obj) return true;
		InsFunds other = (InsFunds)obj;
		return new EqualsBuilder()
				.append(getPid(),other.getPid())
				.isEquals();
	}
}

