/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月31日 10:17:01
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
@Table(name = "sub_ins_investment")
public class InsInvestment extends BasePO {

	public static final String TABLE_ALIAS = "InsInvestment";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_REL_ID = "关联方ID";
	public static final String ALIAS_REL_TYPE = "关联方类型";
	public static final String ALIAS_REL_NAME = "关联方名称";
	public static final String ALIAS_REL_NUMBER = "关联方证件号码";
	public static final String ALIAS_TRADE_ORG = "交易机构";
	public static final String ALIAS_TRAN_TYPE = "交易类型";
	public static final String ALIAS_DATA_SOURCES = "数据来源";
	public static final String ALIAS_TRAN_DATE = "合同签署日期";
	public static final String ALIAS_MAJOR_TRADE = "是否重大关联交易";
	public static final String ALIAS_CALIBER = "口径";
	public static final String ALIAS_STATUS = "前置流程状态";
	public static final String ALIAS_STATUS_COMMENT = "前置流程状态说明";
	public static final String ALIAS_TOTOL_TRADE_AMOUNT = "本年度与该关联方发生的关联交易累计金额（万元）";
	public static final String ALIAS_CONTRACT_NO = "合同编号";
	public static final String ALIAS_TRADE_AMOUNT = "交易金额（万元）";
	public static final String ALIAS_BUS_START_TIME = "业务起始日期";
	public static final String ALIAS_BUS_END_TIME = "业务终止日期";
	public static final String ALIAS_INVESTMENT_TYPE = "业务类型";
	public static final String ALIAS_APPROVAL_NUM = "行政审批文号";
	public static final String ALIAS_SECURITIES_TYPE = "证券类型";
	public static final String ALIAS_IN_TIME = "入股时间";
	public static final String ALIAS_SHARE_NUM = "股份数";
	public static final String ALIAS_SHARE_RETIO = "股份占比";
	public static final String ALIAS_TRADE_PRICE = "交易价格";
	public static final String ALIAS_PRICING_POLICY = "定价政策";
	public static final String ALIAS_TRADE_SUBJECT = "交易标的";
	public static final String ALIAS_REMARK = "其他事项说明";
	public static final String ALIAS_IS_EFFECTIVE = "是否生效";
	public static final String ALIAS_APPROVE_TRADE_AMOUNT = "审批金额";
	public static final String ALIAS_TRANS_NUM = "交易编号";
	public static final String ALIAS_IS_CASH_SUBSCRIBE = "是否现金认购";
	public static final String ALIAS_IS_DEAL_PRICE = "交易定价是否为国家规定";
	public static final String ALIAS_RULE_RESULT = "规则结果";
	public static final String ALIAS_PRODUCT_CODE = "产品代码（id）";
	public static final String ALIAS_PRODUCT_NAME = "产品名称";
	public static final String ALIAS_CONSIDER_TYPE = "审议类型(S0:可免予审议，S1:可集中审议，S2逐笔审议)";
	public static final String ALIAS_IS_HOLDING_SUBSIDIARY = "是否为控股子公司";
	public static final String ALIAS_HOLDING_SUBSIDIARY_NAME = "控股子公司名称";
	public static final String ALIAS_HOLDING_SUBSIDIARY_NUMBER = "控股子公司证件号码";
	public static final String ALIAS_YIELD = "收益率";
	public static final String ALIAS_VARIABLESHARERATIO = "变动股比或份额（%）";
	public static final String ALIAS_VARIABLESHARERATIO_AFTER = "变动后股比或份额（%）";
	public static final String ALIAS_EFFECTIVE_TIME = "生效时间";
	public static final String ALIAS_FINANCIAL_IMPACT = "对财务状况及经营成果的影响";
	public static final String ALIAS_MAJOR_TYPE = "达到重大关联交易的情形";
	public static final String ALIAS_CONTRACT_AMOUNT = "合同金额（万元）";
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

	@Column(name = "APPROVAL_NUM", nullable = true, length = 50)
	private String approvalNum;

	@Column(name = "SECURITIES_TYPE", nullable = true, length = 10)
	private String securitiesType;

	@Column(name = "IN_TIME", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date inTime;

	@Column(name = "SHARE_NUM", nullable = true, length = 50)
	private String shareNum;

	@Column(name = "SHARE_RETIO", nullable = true, length = 50)
	private String shareRetio;

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

	@Column(name = "IS_CASH_SUBSCRIBE", nullable = true, length = 10)
	private String isCashSubscribe;

	@Column(name = "IS_DEAL_PRICE", nullable = true, length = 5)
	private String isDealPrice;

	@Column(name = "RULE_RESULT", nullable = true, length = 100)
	private String ruleResult;

	@Column(name = "product_code", nullable = true, length = 20)
	private String productCode;

	@Column(name = "product_name", nullable = true, length = 50)
	private String productName;

	@Column(name = "consider_type", nullable = true, length = 10)
	private String considerType;

	@Column(name = "is_HoldingSubsidiary", nullable = true, length = 10)
	private String isHoldingSubsidiary;

	@Column(name = "HoldingSubsidiary_name", nullable = true, length = 20)
	private String holdingSubsidiaryName;

	@Column(name = "HoldingSubsidiary_number", nullable = true, length = 100)
	private String holdingSubsidiaryNumber;

	@Column(name = "Yield_", nullable = true, length = 10)
	private String yield_;

	@Column(name = "Variableshareratio", nullable = true, length = 10)
	private String variableshareratio;

	@Column(name = "Variableshareratio_after", nullable = true, length = 10)
	private String variableshareratioAfter;

	@Column(name = "EFFECTIVE_TIME", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date effectiveTime;

	@Column(name = "Financial_Impact", nullable = true, length = 400)
	private String financialImpact;

	@Column(name = "Major_Type", nullable = true, length = 10)
	private String majorType;

	@Column(name = "Contract_Amount", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal contractAmount;


	@Column(name = "AMEND_ID", nullable = true, length = 10)
	private String amendId;

	@Column(name = "AMEND_NAME", nullable = true, length = 10)
	private String amendName;

	@Column(name = "is_extract", nullable = true, length = 6)
	private String isExtract;

	@Column(name="rel_Trade_Amount")
	private BigDecimal relTradeAmount;

	@Column(name = "is_discovery", nullable = true, length = 6)
	private String isDiscovery;//披露状态

	@Column(name = "pub_id", length = 32)
	private String pubId;//披露文件标识
	//columns END


	public String getPubId() {
		return pubId;
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
	//columns END


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


	public String getApprovalNum() {
		return this.approvalNum;
	}

	public void setApprovalNum(String value) {
		this.approvalNum = value;
	}


	public String getSecuritiesType() {
		return this.securitiesType;
	}

	public void setSecuritiesType(String value) {
		this.securitiesType = value;
	}


	public Date getInTime() {
		return this.inTime;
	}

	public void setInTime(Date value) {
		this.inTime = value;
	}


	public String getShareNum() {
		return this.shareNum;
	}

	public void setShareNum(String value) {
		this.shareNum = value;
	}


	public String getShareRetio() {
		return this.shareRetio;
	}

	public void setShareRetio(String value) {
		this.shareRetio = value;
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


	public String getIsCashSubscribe() {
		return this.isCashSubscribe;
	}

	public void setIsCashSubscribe(String value) {
		this.isCashSubscribe = value;
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


	public String getConsiderType() {
		return this.considerType;
	}

	public void setConsiderType(String value) {
		this.considerType = value;
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


	/*public String getYield() {
		return this.yield_;
	}

	public void setYield(String value) {
		this.yield_ = value;
	}*/

	public String getYield_() {
		return yield_;
	}

	public void setYield_(String yield_) {
		this.yield_ = yield_;
	}

	public String getVariableshareratio() {
		return this.variableshareratio;
	}

	public void setVariableshareratio(String value) {
		this.variableshareratio = value;
	}


	public String getVariableshareratioAfter() {
		return this.variableshareratioAfter;
	}

	public void setVariableshareratioAfter(String value) {
		this.variableshareratioAfter = value;
	}


	public Date getEffectiveTime() {
		return this.effectiveTime;
	}

	public void setEffectiveTime(Date value) {
		this.effectiveTime = value;
	}


	public String getFinancialImpact() {
		return this.financialImpact;
	}

	public void setFinancialImpact(String value) {
		this.financialImpact = value;
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
	private String effectiveTimeSub;

	@Transient
	private String securitiesTypeSub;

	public String getSecuritiesTypeSub() {
		if (securitiesType != null) {
			int i = Integer.valueOf(securitiesType) + 1;
			return String.valueOf(i);
		} else {
			return "";
		}
	}

	public String getRelTypeSub() {
		if ("ORG".equals(relType)) {
			return "1";
		}
		return "2";
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
		return isHoldingSubsidiary.equals("1") ? "0" : "1" ;
	}

	public String getEffectiveTimeSub() {
		if (effectiveTime != null) {
			return DateUtils.formatDateShort(effectiveTime);
		}
		return "";
	}

	public String toString() {
		return new StringBuilder("insInvestment[\n")
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
				.append("approvalNum = ").append(approvalNum).append(", \n")
				.append("securitiesType = ").append(securitiesType).append(", \n")
				.append("inTime = ").append(inTime).append(", \n")
				.append("shareNum = ").append(shareNum).append(", \n")
				.append("shareRetio = ").append(shareRetio).append(", \n")
				.append("tradePrice = ").append(tradePrice).append(", \n")
				.append("pricingPolicy = ").append(pricingPolicy).append(", \n")
				.append("tradeSubject = ").append(tradeSubject).append(", \n")
				.append("remark = ").append(remark).append(", \n")
				.append("isEffective = ").append(isEffective).append(", \n")
				.append("approveTradeAmount = ").append(approveTradeAmount).append(", \n")
				.append("transNum = ").append(transNum).append(", \n")
				.append("isCashSubscribe = ").append(isCashSubscribe).append(", \n")
				.append("isDealPrice = ").append(isDealPrice).append(", \n")
				.append("ruleResult = ").append(ruleResult).append(", \n")
				.append("productCode = ").append(productCode).append(", \n")
				.append("productName = ").append(productName).append(", \n")
				.append("considerType = ").append(considerType).append(", \n")
				.append("isHoldingSubsidiary = ").append(isHoldingSubsidiary).append(", \n")
				.append("holdingSubsidiaryName = ").append(holdingSubsidiaryName).append(", \n")
				.append("holdingSubsidiaryNumber = ").append(holdingSubsidiaryNumber).append(", \n")
				.append("yield_ = ").append(yield_).append(", \n")
				.append("variableshareratio = ").append(variableshareratio).append(", \n")
				.append("variableshareratioAfter = ").append(variableshareratioAfter).append(", \n")
				.append("effectiveTime = ").append(effectiveTime).append(", \n")
				.append("financialImpact = ").append(financialImpact).append(", \n")
				.append("majorType = ").append(majorType).append(", \n")
				.append("contractAmount = ").append(contractAmount).append(", \n")
				.append("]")
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getPid())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof InsInvestment == false) return false;
		if(this == obj) return true;
		InsInvestment other = (InsInvestment)obj;
		return new EqualsBuilder()
				.append(getPid(),other.getPid())
				.isEquals();
	}
}

