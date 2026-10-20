/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:32
 *
 */

package com.yzj.jep.app.sub.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;


@Entity
@Table(name = "sub_ins_allbustraninfo_view")
public class InsAllbustraninfoView extends BasePO {

	public static final String TABLE_ALIAS = "InsAllbustraninfoView";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_REL_TYPE = "关联方类型";
	public static final String ALIAS_REL_NAME = "关联方名称";
	public static final String ALIAS_REL_NUMBER = "关联方证件号码";
	public static final String ALIAS_TRAN_TYPE = "交易类型";
	public static final String ALIAS_INVESTMENT_TYPE = "业务类型";
	public static final String ALIAS_CONTRACT_NO = "合同编号";
	public static final String ALIAS_TRADE_AMOUNT = "交易金额（万元）";
	public static final String ALIAS_BUS_START_TIME = "业务起始日期";
	public static final String ALIAS_STATUS = "数据校验结果";
	public static final String ALIAS_REPORT_STATUS = "报送文件状态";
	public static final String ALIAS_TRADE_SUBJECT = "交易标的";
	public static final String ALIAS_TRADE_PRICE = "交易价格";
	public static final String ALIAS_PRICING_POLICY = "定价政策";
	public static final String ALIAS_IS_HOLDING_SUBSIDIARY = "是否控股子公司交易";
	public static final String ALIAS_HOLDING_SUBSIDIARY_NAME = "控股子公司全称";
	public static final String ALIAS_HOLDING_SUBSIDIARY_NUMBER = "控股子公司有效证件号码";
	public static final String ALIAS_REMARK = "其他事项说明";
	public static final String ALIAS_TRAN_DATE = "合同签署日期";
	public static final String ALIAS_IS_OVERSEASINVESTMENT = "是否境外投资";
	public static final String ALIAS_ALLBUSTRANINFO_SEQ = "关联流水号";
	public static final String PORT_PID="报送对应文件pid";


	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "PID", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "REL_TYPE", nullable = true, length = 100)
	private String relType;

	@Column(name = "REL_NAME", nullable = true, length = 100)
	private String relName;

	@Column(name = "REL_NUMBER", nullable = true, length = 100)
	private String relNumber;

	@Column(name = "TRAN_TYPE", nullable = true, length = 100)
	private String tranType;

	@Column(name = "INVESTMENT_TYPE", nullable = true, length = 100)
	private String investmentType;

	@Column(name = "CONTRACT_NO", nullable = true, length = 100)
	private String contractNo;

	@Column(name = "TRADE_AMOUNT", nullable = true, precision = 64, scale = 2)
	private java.math.BigDecimal tradeAmount;

	@Column(name = "BUS_START_TIME", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date busStartTime;

	@Column(name = "STATUS", nullable = true, length = 100)
	private String status;

	@Column(name = "REPORT_STATUS", nullable = true, length = 100)
	private String reportStatus;

	@Column(name = "TRADE_SUBJECT", nullable = true, length = 100)
	private String tradeSubject;

	@Column(name = "TRADE_PRICE", nullable = true, length = 300)
	private String tradePrice;

	@Column(name = "PRICING_POLICY", nullable = true, length = 255)
	private String pricingPolicy;

	@Column(name = "is_HoldingSubsidiary", nullable = true, length = 255)
	private String isHoldingSubsidiary;

	@Column(name = "HoldingSubsidiary_name", nullable = true, length = 255)
	private String holdingSubsidiaryName;

	@Column(name = "HoldingSubsidiary_number", nullable = true, length = 50)
	private String holdingSubsidiaryNumber;

	@Column(name = "REMARK", nullable = true, length = 255)
	private String remark;

	@Column(name = "TRAN_DATE", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date tranDate;

	@Column(name = "is_Overseasinvestment", nullable = true, length = 10)
	private String isOverseasinvestment;

	@Column(name = "FINANCIAL_ASSSTS_NAME", nullable = true, length = 10)
	private String financialAssstsName;

	@Column(name = "FINANCIAL_PRO_SCALE", nullable = true, length = 50)
	private String financialProScale;

	@Column(name = "UNDER_INVOLVE_REL", nullable = true, length = 10)
	private String underInvolveRel;

	@Column(name = "FINANCIAL_PRO_RATIO", nullable = true, length = 50)
	private String financialProRatio;

	@Column(name = "allbustraninfo_seq", nullable = true, length = 64)
	private String allbustraninfoSeq;

	@Column(name="port_pid")
	private Integer portPid;
	@Column(name = "Trading_direction", length = 32)
	private String tradingDirection;// 交易方向
	@Column(name = "BUS_TYPE", nullable = true, length = 50)
	private String busType; //业务类型
	//columns END
	@Transient
	private String investmentTypeStr;

	//金融资产编号
	@Column(name = "FINANCIAL_ASSSTS_NUM", nullable = true, length = 50)
	private String financialAssstsNum;

	@Column(name = "is_agreement", nullable = true, length = 8)
	private String isAgreement;//是否是统一交易


	@Transient
	private String tranDateStr;

	public String getTranDateStr() {
		return tranDateStr;
	}

	public void setTranDateStr(String tranDateStr) {
		this.tranDateStr = tranDateStr;
	}

	public String getIsAgreement() {
		return isAgreement;
	}

	public void setIsAgreement(String isAgreement) {
		this.isAgreement = isAgreement;
	}

	public String getFinancialAssstsNum() {
		return financialAssstsNum;
	}

	public void setFinancialAssstsNum(String financialAssstsNum) {
		this.financialAssstsNum = financialAssstsNum;
	}
	public String getTradingDirection() {
		return tradingDirection;
	}

	public void setTradingDirection(String tradingDirection) {
		this.tradingDirection = tradingDirection;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getInvestmentTypeStr() {
		return investmentTypeStr;
	}

	public void setInvestmentTypeStr(String investmentTypeStr) {
		this.investmentTypeStr = investmentTypeStr;
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


	public String getRelType() {
		return this.relType;
	}

	public void setRelType(String value) {
		this.relType = value;
	}

	public String getFinancialAssstsName() {
		return financialAssstsName;
	}

	public void setFinancialAssstsName(String financialAssstsName) {
		this.financialAssstsName = financialAssstsName;
	}

	public String getFinancialProScale() {
		return financialProScale;
	}

	public void setFinancialProScale(String financialProScale) {
		this.financialProScale = financialProScale;
	}

	public String getUnderInvolveRel() {
		return underInvolveRel;
	}

	public void setUnderInvolveRel(String underInvolveRel) {
		this.underInvolveRel = underInvolveRel;
	}

	public String getFinancialProRatio() {
		return financialProRatio;
	}

	public void setFinancialProRatio(String financialProRatio) {
		this.financialProRatio = financialProRatio;
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


	public String getTranType() {
		return this.tranType;
	}

	public void setTranType(String value) {
		this.tranType = value;
	}


	public String getInvestmentType() {
		return this.investmentType;
	}

	public void setInvestmentType(String value) {
		this.investmentType = value;
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


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}


	public String getReportStatus() {
		return this.reportStatus;
	}

	public void setReportStatus(String value) {
		this.reportStatus = value;
	}


	public String getTradeSubject() {
		return this.tradeSubject;
	}

	public void setTradeSubject(String value) {
		this.tradeSubject = value;
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


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String value) {
		this.remark = value;
	}


	public Date getTranDate() {
		return this.tranDate;
	}

	public void setTranDate(Date value) {
		this.tranDate = value;
	}


	public String getIsOverseasinvestment() {
		return this.isOverseasinvestment;
	}

	public void setIsOverseasinvestment(String value) {
		this.isOverseasinvestment = value;
	}


	public String getAllbustraninfoSeq() {
		return this.allbustraninfoSeq;
	}

	public void setAllbustraninfoSeq(String value) {
		this.allbustraninfoSeq = value;
	}


	public String toString() {
		return new StringBuilder("insAllbustraninfoView[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("relType = ").append(relType).append(", \n")
						.append("relName = ").append(relName).append(", \n")
						.append("relNumber = ").append(relNumber).append(", \n")
						.append("tranType = ").append(tranType).append(", \n")
						.append("investmentType = ").append(investmentType).append(", \n")
						.append("contractNo = ").append(contractNo).append(", \n")
						.append("tradeAmount = ").append(tradeAmount).append(", \n")
						.append("busStartTime = ").append(busStartTime).append(", \n")
						.append("status = ").append(status).append(", \n")
						.append("reportStatus = ").append(reportStatus).append(", \n")
						.append("tradeSubject = ").append(tradeSubject).append(", \n")
						.append("tradePrice = ").append(tradePrice).append(", \n")
						.append("pricingPolicy = ").append(pricingPolicy).append(", \n")
						.append("isHoldingSubsidiary = ").append(isHoldingSubsidiary).append(", \n")
						.append("holdingSubsidiaryName = ").append(holdingSubsidiaryName).append(", \n")
						.append("holdingSubsidiaryNumber = ").append(holdingSubsidiaryNumber).append(", \n")
						.append("remark = ").append(remark).append(", \n")
						.append("tranDate = ").append(tranDate).append(", \n")
						.append("isOverseasinvestment = ").append(isOverseasinvestment).append(", \n")
						.append("allbustraninfoSeq = ").append(allbustraninfoSeq).append(", \n")
						.append("]")
						.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPid())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof InsAllbustraninfoView == false) return false;
		if(this == obj) return true;
		InsAllbustraninfoView other = (InsAllbustraninfoView)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

