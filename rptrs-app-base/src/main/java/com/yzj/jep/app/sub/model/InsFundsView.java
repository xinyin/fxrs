/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年09月07日 15:06:12
 *
 */

package com.yzj.jep.app.sub.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "sub_ins_funds_view")
public class InsFundsView extends BasePO {

	public static final String TABLE_ALIAS = "InsFundsView";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_FUNDS_SEQ = "资金运用类流水号";
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
	public static final String ALIAS_REGISTER = "产品批准/注册/备案号";
	public static final String ALIAS_INVEST_PRODUC_RATE = "投资金融产品份额占比(%)";
	public static final String ALIAS_IS_UNDERLY_ASSET_REL = "底层资产是否涉及关联方";
	public static final String ALIAS_UNDERLY_ASSET_DESCRIBE = "底层资产具体描述";
	public static final String ALIAS_INVESTMENT_PROPORTION = "投资比例(%)";
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


	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "PID", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "FUNDS_SEQ", nullable = false, length = 64)
	private String fundsSeq;

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

	@Column(name = "IM_SUBJECT_MATTER", nullable = true, length =  100)
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

	@Column(name = "INVESTMENT_ALLOCATION", nullable = true, length = 50)
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

	@Column(name = "REGISTER", nullable = true, length = 64)
	private String register;

	@Column(name = "invest_produc_rate", nullable = true, length = 32)
	private String investProducRate;

	@Column(name = "is_underly_asset_rel", nullable = true, length = 4)
	private String isUnderlyAssetRel;

	@Column(name = "underly_asset_describe", nullable = true, length = 128)
	private String underlyAssetDescribe;

	@Column(name = "investment_proportion", nullable = true, length = 128)
	private String investmentProportion;

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

	@Column(name="port_pid")
	private Integer portPid;
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

	@Transient
	private String tranDateString;

	@Transient
	private  String busStartTimeString;
	@Transient
	private  String busEndTimeString;

	public String getTranDateString() {
		if (Optional.ofNullable(tranDate).isPresent()) {
			return DateUtils.formatDate(tranDate);
		}
		return "";
	}
	public void setTranDateString(String value) {
	}


	public String getBusStartTimeString() {
		if (Optional.ofNullable(busStartTime).isPresent()) {
			return DateUtils.formatDate(busStartTime);
		}
		return "";
	}

	public String getBusEndTimeString() {
		if (Optional.ofNullable(busEndTime).isPresent()) {
			return DateUtils.formatDate(busEndTime);
		}
		return "";
	}

	public void setBusStartTimeString(String busStartTimeString) {
		this.busStartTimeString = busStartTimeString;
	}

	public void setBusEndTimeString(String busEndTimeString) {
		this.busEndTimeString = busEndTimeString;
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

	public Integer getPortPid() {
		return portPid;
	}

	public void setPortPid(Integer portPid) {
		this.portPid = portPid;
	}

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer value) {
		this.pid = value;
	}


	public String getFundsSeq() {
		return this.fundsSeq;
	}

	public void setFundsSeq(String value) {
		this.fundsSeq = value;
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


	public String getRegister() {
		return this.register;
	}

	public void setRegister(String value) {
		this.register = value;
	}


	public String getInvestProducRate() {
		return this.investProducRate;
	}

	public void setInvestProducRate(String value) {
		this.investProducRate = value;
	}


	public String getIsUnderlyAssetRel() {
		return this.isUnderlyAssetRel;
	}

	public void setIsUnderlyAssetRel(String value) {
		this.isUnderlyAssetRel = value;
	}


	public String getUnderlyAssetDescribe() {
		return this.underlyAssetDescribe;
	}

	public void setUnderlyAssetDescribe(String value) {
		this.underlyAssetDescribe = value;
	}


	public String getInvestmentProportion() {
		return this.investmentProportion;
	}

	public void setInvestmentProportion(String value) {
		this.investmentProportion = value;
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


	@Override
	public String toString() {
		return "InsFundsView{" +
				"pid=" + pid +
				", fundsSeq='" + fundsSeq + '\'' +
				", relId=" + relId +
				", relType='" + relType + '\'' +
				", relName='" + relName + '\'' +
				", relNumber='" + relNumber + '\'' +
				", tradeOrg='" + tradeOrg + '\'' +
				", tranType='" + tranType + '\'' +
				", dataSources='" + dataSources + '\'' +
				", tranDate=" + tranDate +
				", majorTrade='" + majorTrade + '\'' +
				", caliber='" + caliber + '\'' +
				", status='" + status + '\'' +
				", statusComment='" + statusComment + '\'' +
				", totolTradeAmount=" + totolTradeAmount +
				", contractNo='" + contractNo + '\'' +
				", tradeAmount=" + tradeAmount +
				", busStartTime=" + busStartTime +
				", busEndTime=" + busEndTime +
				", investmentType='" + investmentType + '\'' +
				", investmentMethod='" + investmentMethod + '\'' +
				", investedEnterprises='" + investedEnterprises + '\'' +
				", equityLastTradePrice='" + equityLastTradePrice + '\'' +
				", imLastTradePrice='" + imLastTradePrice + '\'' +
				", imSubjectMatter='" + imSubjectMatter + '\'' +
				", outSubjectMatter='" + outSubjectMatter + '\'' +
				", shareDebtRatio='" + shareDebtRatio + '\'' +
				", financialAssstsType='" + financialAssstsType + '\'' +
				", financialAssstsNum='" + financialAssstsNum + '\'' +
				", financialProScale='" + financialProScale + '\'' +
				", financialProRatio='" + financialProRatio + '\'' +
				", underlyingAsset='" + underlyingAsset + '\'' +
				", underIsRel='" + underIsRel + '\'' +
				", underInvolveRel='" + underInvolveRel + '\'' +
				", underRel='" + underRel + '\'' +
				", investedEnterprisesName='" + investedEnterprisesName + '\'' +
				", busModel='" + busModel + '\'' +
				", earningsExpectaions='" + earningsExpectaions + '\'' +
				", investmentRatio='" + investmentRatio + '\'' +
				", investmentAllocation='" + investmentAllocation + '\'' +
				", lossShare='" + lossShare + '\'' +
				", tradePrice='" + tradePrice + '\'' +
				", pricingPolicy='" + pricingPolicy + '\'' +
				", tradeSubject='" + tradeSubject + '\'' +
				", remark='" + remark + '\'' +
				", isEffective='" + isEffective + '\'' +
				", approveTradeAmount=" + approveTradeAmount +
				", transNum='" + transNum + '\'' +
				", register='" + register + '\'' +
				", investProducRate='" + investProducRate + '\'' +
				", isUnderlyAssetRel='" + isUnderlyAssetRel + '\'' +
				", underlyAssetDescribe='" + underlyAssetDescribe + '\'' +
				", investmentProportion='" + investmentProportion + '\'' +
				", depositTerms='" + depositTerms + '\'' +
				", isDealPrice='" + isDealPrice + '\'' +
				", ruleResult='" + ruleResult + '\'' +
				", productCode='" + productCode + '\'' +
				", productName='" + productName + '\'' +
				", isHoldingSubsidiary='" + isHoldingSubsidiary + '\'' +
				", holdingSubsidiaryName='" + holdingSubsidiaryName + '\'' +
				", holdingSubsidiaryNumber='" + holdingSubsidiaryNumber + '\'' +
				", isOverseasinvestment='" + isOverseasinvestment + '\'' +
				", financialImpact='" + financialImpact + '\'' +
				", traSubName='" + traSubName + '\'' +
				", majorType='" + majorType + '\'' +
				", contractAmount=" + contractAmount +
				", holdingSubsidiaryType='" + holdingSubsidiaryType + '\'' +
				", isCashSubscription='" + isCashSubscription + '\'' +
				", financialAssstsName='" + financialAssstsName + '\'' +
				", stockrightType='" + stockrightType + '\'' +
				", portPid=" + portPid +
				", busType='" + busType + '\'' +
				", tranName='" + tranName + '\'' +
				", singleRelatedTranType='" + singleRelatedTranType + '\'' +
				'}';
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getPid())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof InsFundsView == false) return false;
		if(this == obj) return true;
		InsFundsView other = (InsFundsView)obj;
		return new EqualsBuilder()
				.append(getPid(),other.getPid())
				.isEquals();
	}
}

