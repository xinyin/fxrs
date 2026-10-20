package com.yzj.jep.app.sub.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;
import java.util.Optional;

/**
 * @author yao
 */
@Entity
@Table(name = "sub_ins_interest_view")
public class InsInterestView extends BasePO {

	public static final String TABLE_ALIAS = "InsInterestView";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_INTEREST_SEQ = "利益转移流水号";
	public static final String ALIAS_REL_ID = "关联方ID";
	public static final String ALIAS_REL_TYPE = "关联方类型";
	public static final String ALIAS_REL_NAME = "关联方名称";
	public static final String ALIAS_REL_NUMBER = "关联方证件号码";
	public static final String ALIAS_TRADE_ORG = "交易机构";
	public static final String ALIAS_TRAN_TYPE = "交易类型";
	public static final String ALIAS_BUS_TYPE = "业务类型";
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
	public static final String ALIAS_TRADE_OCCUR = "交易发生原因及合同、证明文件等";
	public static final String ALIAS_TRADE_PRICE = "交易价格";
	public static final String ALIAS_PRICING_POLICY = "定价政策";
	public static final String ALIAS_TRADE_SUBJECT = "交易标的";
	public static final String ALIAS_REMARK = "其他事项说明";
	public static final String ALIAS_TRANS_NUM = "交易编号";
	public static final String ALIAS_IS_EFFECTIVE = "是否生效";
	public static final String ALIAS_APPROVE_TRADE_AMOUNT = "审批金额";
	public static final String ALIAS_OBJECT_ASSET_CONDITION = "标的资产情况";
	public static final String ALIAS_LAST_BARGAIN_PRICE = "前次交易价格";
	public static final String ALIAS_LAST_BARGAIN_TIME = "前次交易时间";
	public static final String ALIAS_OBJECT_CONDITION = "标的情况";
	public static final String ALIAS_GUARANTEE_CONDITION = "担保情况";
	public static final String ALIAS_IS_TRADE_AGREEMENT = "是否统为统一交易协议";
	public static final String ALIAS_IS_DEAL_PRICE = "交易定价是否为国家规定";
	public static final String ALIAS_RULE_RESULT = "规则结果";
	public static final String ALIAS_PRODUCT_CODE = "产品id";
	public static final String ALIAS_PRODUCT_NAME = "产品名称";
	public static final String ALIAS_IS_HOLDING_SUBSIDIARY = "是否为控股子公司";
	public static final String ALIAS_HOLDING_SUBSIDIARY_NAME = "控股子公司名称";
	public static final String ALIAS_HOLDING_SUBSIDIARY_NUMBER = "控股子公司证件号码";
	public static final String ALIAS_TOTAL_ASSETS = "关联方总资产";
	public static final String ALIAS_LAST_TRADE_PRICE = "前次交易价格";
	public static final String ALIAS_LAST_TRADE_TIME = "前次交易日期";
	public static final String ALIAS_GUARANTEE_CONDITIONS = "担保条件";
	public static final String ALIAS_TRADE_SUBJECT_ASSETS = "标的资产情况";
	public static final String ALIAS_FINANCIAL_IMPACT = "对财务状况及经营成果的影响";
	public static final String ALIAS_MAJOR_TYPE = "达到重大关联交易的情形";
	public static final String ALIAS_CONTRACT_AMOUNT = "合同金额（万元）";
	public static final String ALIAS_SUBJECT_SITUATION = "标的情况";
	public static final String PORT_PID="需要报送的文件对应的pid";


	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "PID", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "interest_seq", nullable = false, length = 64)
	private String interestSeq;

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

	@Column(name = "BUS_TYPE", nullable = true, length = 50)
	private String busType;

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

	@Column(name = "TRADE_OCCUR", nullable = true, length = 100)
	private String tradeOccur;

	@Column(name = "TRADE_PRICE", nullable = true, length = 1000)
	private String tradePrice;

	@Column(name = "PRICING_POLICY", nullable = true, length = 1000)
	private String pricingPolicy;

	@Column(name = "TRADE_SUBJECT", nullable = true, length = 1000)
	private String tradeSubject;

	@Column(name = "REMARK", nullable = true, length = 1000)
	private String remark;

	@Column(name = "TRANS_NUM", nullable = true, length = 32)
	private String transNum;

	@Column(name = "IS_EFFECTIVE", nullable = true, length = 1)
	private String isEffective;

	@Column(name = "APPROVE_TRADE_AMOUNT", nullable = true, precision = 10, scale = 0)
	private Long approveTradeAmount;

	@Column(name = "object_asset_condition", nullable = true, length = 128)
	private String objectAssetCondition;

	@Column(name = "last_bargain_price", nullable = true, precision = 10, scale = 0)
	private Long lastBargainPrice;

	@Column(name = "last_bargain_time", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date lastBargainTime;

	@Column(name = "object_condition", nullable = true, length = 128)
	private String objectCondition;

	@Column(name = "guarantee_condition", nullable = true, length = 128)
	private String guaranteeCondition;

	@Column(name = "is_agreement", nullable = true, length = 6)
	private String isAgreement;

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

	@Column(name = "totalAssets", nullable = true, length = 100)
	private String totalAssets;

	@Column(name = "LAST_TRADE_PRICE", nullable = true, length = 100)
	private String lastTradePrice;

	@Column(name = "LAST_TRADE_TIME", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date lastTradeTime;

	@Column(name = "Guarantee_conditions", nullable = true, length = 10)
	private String guaranteeConditions;

	@Column(name = "TRADE_SUBJECT_ASSETS", nullable = true, length = 1000)
	private String tradeSubjectAssets;

	@Column(name = "Financial_Impact", nullable = true, length = 400)
	private String financialImpact;

	@Column(name = "Major_Type", nullable = true, length = 10)
	private String majorType;

	@Column(name = "Contract_Amount", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal contractAmount;

	@Column(name = "SUBJECT_SITUATION", nullable = true, length = 255)
	private String subjectSituation;
	@Column(name="PORT_PID")
	private Integer portPid;

	@Column(name = "single_related_trantype", nullable = true, length = 1)
	private String singleRelatedTranType;
	@Column(name = "TRAN_NAME", nullable = true, length = 10)
	private String tranName;

	@Column(name = "Trading_direction", nullable = true, length = 6)
	private String tradingDirection;

	//columns END


	public String getSingleRelatedTranType() {
		return singleRelatedTranType;
	}

	public void setSingleRelatedTranType(String singleRelatedTranType) {
		this.singleRelatedTranType = singleRelatedTranType;
	}

	public String getTranName() {
		return tranName;
	}

	public void setTranName(String tranName) {
		this.tranName = tranName;
	}

	public String getTradingDirection() {
		return tradingDirection;
	}

	public void setTradingDirection(String tradingDirection) {
		this.tradingDirection = tradingDirection;
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


	public String getInterestSeq() {
		return this.interestSeq;
	}

	public void setInterestSeq(String value) {
		this.interestSeq = value;
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


	public String getBusType() {
		return this.busType;
	}

	public void setBusType(String value) {
		this.busType = value;
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


	public String getTradeOccur() {
		return this.tradeOccur;
	}

	public void setTradeOccur(String value) {
		this.tradeOccur = value;
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


	public String getTransNum() {
		return this.transNum;
	}

	public void setTransNum(String value) {
		this.transNum = value;
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


	public String getObjectAssetCondition() {
		return this.objectAssetCondition;
	}

	public void setObjectAssetCondition(String value) {
		this.objectAssetCondition = value;
	}


	public Long getLastBargainPrice() {
		return this.lastBargainPrice;
	}

	public void setLastBargainPrice(Long value) {
		this.lastBargainPrice = value;
	}


	public Date getLastBargainTime() {
		return this.lastBargainTime;
	}

	public void setLastBargainTime(Date value) {
		this.lastBargainTime = value;
	}


	public String getObjectCondition() {
		return this.objectCondition;
	}

	public void setObjectCondition(String value) {
		this.objectCondition = value;
	}


	public String getGuaranteeCondition() {
		return this.guaranteeCondition;
	}

	public void setGuaranteeCondition(String value) {
		this.guaranteeCondition = value;
	}


	public String getIsAgreement() {
		return this.isAgreement;
	}

	public void setIsAgreement(String value) {
		this.isAgreement = value;
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


	public String getTotalAssets() {
		return this.totalAssets;
	}

	public void setTotalAssets(String value) {
		this.totalAssets = value;
	}


	public String getLastTradePrice() {
		return this.lastTradePrice;
	}

	public void setLastTradePrice(String value) {
		this.lastTradePrice = value;
	}


	public Date getLastTradeTime() {
		return this.lastTradeTime;
	}

	public void setLastTradeTime(Date value) {
		this.lastTradeTime = value;
	}


	public String getGuaranteeConditions() {
		return this.guaranteeConditions;
	}

	public void setGuaranteeConditions(String value) {
		this.guaranteeConditions = value;
	}


	public String getTradeSubjectAssets() {
		return this.tradeSubjectAssets;
	}

	public void setTradeSubjectAssets(String value) {
		this.tradeSubjectAssets = value;
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


	public String getSubjectSituation() {
		return this.subjectSituation;
	}

	public void setSubjectSituation(String value) {
		this.subjectSituation = value;
	}

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



	public String toString() {
		return new StringBuilder("insInterestView[\n")
				.append("pid = ").append(pid).append(", \n")
				.append("interestSeq = ").append(interestSeq).append(", \n")
				.append("relId = ").append(relId).append(", \n")
				.append("relType = ").append(relType).append(", \n")
				.append("relName = ").append(relName).append(", \n")
				.append("relNumber = ").append(relNumber).append(", \n")
				.append("tradeOrg = ").append(tradeOrg).append(", \n")
				.append("tranType = ").append(tranType).append(", \n")
				.append("busType = ").append(busType).append(", \n")
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
				.append("tradeOccur = ").append(tradeOccur).append(", \n")
				.append("tradePrice = ").append(tradePrice).append(", \n")
				.append("pricingPolicy = ").append(pricingPolicy).append(", \n")
				.append("tradeSubject = ").append(tradeSubject).append(", \n")
				.append("remark = ").append(remark).append(", \n")
				.append("transNum = ").append(transNum).append(", \n")
				.append("isEffective = ").append(isEffective).append(", \n")
				.append("approveTradeAmount = ").append(approveTradeAmount).append(", \n")
				.append("objectAssetCondition = ").append(objectAssetCondition).append(", \n")
				.append("lastBargainPrice = ").append(lastBargainPrice).append(", \n")
				.append("lastBargainTime = ").append(lastBargainTime).append(", \n")
				.append("objectCondition = ").append(objectCondition).append(", \n")
				.append("guaranteeCondition = ").append(guaranteeCondition).append(", \n")
				.append("isAgreement = ").append(isAgreement).append(", \n")
				.append("isDealPrice = ").append(isDealPrice).append(", \n")
				.append("ruleResult = ").append(ruleResult).append(", \n")
				.append("productCode = ").append(productCode).append(", \n")
				.append("productName = ").append(productName).append(", \n")
				.append("isHoldingSubsidiary = ").append(isHoldingSubsidiary).append(", \n")
				.append("holdingSubsidiaryName = ").append(holdingSubsidiaryName).append(", \n")
				.append("holdingSubsidiaryNumber = ").append(holdingSubsidiaryNumber).append(", \n")
				.append("totalAssets = ").append(totalAssets).append(", \n")
				.append("lastTradePrice = ").append(lastTradePrice).append(", \n")
				.append("lastTradeTime = ").append(lastTradeTime).append(", \n")
				.append("guaranteeConditions = ").append(guaranteeConditions).append(", \n")
				.append("tradeSubjectAssets = ").append(tradeSubjectAssets).append(", \n")
				.append("financialImpact = ").append(financialImpact).append(", \n")
				.append("majorType = ").append(majorType).append(", \n")
				.append("contractAmount = ").append(contractAmount).append(", \n")
				.append("subjectSituation = ").append(subjectSituation).append(", \n")
				.append("]")
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getPid())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof InsInterestView == false) return false;
		if(this == obj) return true;
		InsInterestView other = (InsInterestView)obj;
		return new EqualsBuilder()
				.append(getPid(),other.getPid())
				.isEquals();
	}
}

