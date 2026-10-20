/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:35
 *
 */

package com.yzj.jep.app.sub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.math.BigDecimal;


@Entity
@Table(name = "sub_ins_allfunds_view")
public class InsAllfundsView extends BasePO {
	
	public static final String TABLE_ALIAS = "InsAllfundsView";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_FIXED_PROFIT_BALANCE = "固定收益类资产投资余额";
	public static final String ALIAS_LISTED_INVEST_BALANCE = "上市权益类资产投资余额（万元";
	public static final String ALIAS_UNLISTED_INVEST_BALANCE = "未上市权益类资产投资余额（万元）";
	public static final String ALIAS_INVEST_AMOUNT = "权益类资产投资限额";
	public static final String ALIAS_IMVBS_INVEST_BALANCE = "不动产类资产投资余额（万元）";
	public static final String ALIAS_IMVBS_INVEST_AMOUNT = "不动产类资产投资限额";
	public static final String ALIAS_ABROAD_FUND = "境外投资余额（万元）";
	public static final String ALIAS_ABROAD_AMOUNT = "境外投资限额";
	public static final String ALIAS_INS_MANAGE_PRO_FUND = "保险资产管理产品";
	public static final String ALIAS_COM_BANK_FIN_FUND = "商业银行理财";
	public static final String ALIAS_BANK_CREDIT_FUND = "银行业金融机构信贷资产支持证券";
	public static final String ALIAS_TRUST_FUND = "信托公司集合资金";
	public static final String ALIAS_BOND_SPECIAL_FUND = "证券公司专项资产管理计划";
	public static final String ALIAS_INS_COMPANY_PLAN_FUND = "保险资管公司发行资产支持计划";
	public static final String ALIAS_OTHER_FUND = "其他金融资产投资余额";
	public static final String ALIAS_OTHER_TOTAL_FUND = "金融资产投资限额";
	public static final String ALIAS_OTHER_TOTAL_AMOUNT = "其他资产";
	public static final String ALIAS_ALLFUNDS_SEQ = "关联流水号";
	public static final String ALIAS_ALLTOTALFUND = "全部投资余额-投资关联方";
	public static final String PORT_PID="报送文件对应pid";

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "PID", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "FixedProfitBalance", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal fixedProfitBalance;

	@Column(name = "ListedInvestBalance", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal listedInvestBalance;

	@Column(name = "UnlistedInvestBalance", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal unlistedInvestBalance;

	@Column(name = "InvestAmount", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal investAmount;

	@Column(name = "ImvbsInvestBalance", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal imvbsInvestBalance;

	@Column(name = "ImvbsInvestAmount", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal imvbsInvestAmount;

	@Column(name = "AbroadFund", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal abroadFund;

	@Column(name = "AbroadAmount", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal abroadAmount;

	@Column(name = "InsManageProFund", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal insManageProFund;

	@Column(name = "ComBankFinFund", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal comBankFinFund;
	@Column(name = "current_asset_investment_balance", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal currentAssetinvestmentBalance;
	@Column(name = "BankCreditFund", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal bankCreditFund;

	@Column(name = "TrustFund", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal trustFund;

	@Column(name = "BondSpecialFund", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal bondSpecialFund;

	@Column(name = "InsCompanyPlanFund", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal insCompanyPlanFund;

	@Column(name = "OtherFund", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal otherFund;

	@Column(name = "OtherTotalFund", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal otherTotalFund;

	@Column(name = "OtherTotalAmount", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal otherTotalAmount;

	@Column(name = "AllTotalFund", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal allTotalFund;

	@Column(name = "allfunds_seq", nullable = true, length = 64)
	private String allfundsSeq;

	@Column(name="port_pid")
	private Integer portPid;

	//全部投资余额
	@Column(name = "AllTotalFund", nullable = true, precision = 50, scale = 6)
	private BigDecimal allInvestmentBalance;
	//流动性资产投资余额
	@Column(name = "current_asset_investment_balance", nullable = true, precision = 50, scale = 6)
	private BigDecimal mobilityBalance;

	//columns END


	public BigDecimal getAllInvestmentBalance() {
		return allInvestmentBalance;
	}

	public void setAllInvestmentBalance(BigDecimal allInvestmentBalance) {
		this.allInvestmentBalance = allInvestmentBalance;
	}

	public BigDecimal getMobilityBalance() {
		return mobilityBalance;
	}

	public void setMobilityBalance(BigDecimal mobilityBalance) {
		this.mobilityBalance = mobilityBalance;
	}

	public BigDecimal getCurrentAssetinvestmentBalance() {
		return currentAssetinvestmentBalance;
	}

	public void setCurrentAssetinvestmentBalance(BigDecimal currentAssetinvestmentBalance) {
		this.currentAssetinvestmentBalance = currentAssetinvestmentBalance;
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
	

	public java.math.BigDecimal getFixedProfitBalance() {
		return this.fixedProfitBalance;
	}
	
	public void setFixedProfitBalance(java.math.BigDecimal value) {
		this.fixedProfitBalance = value;
	}
	

	public java.math.BigDecimal getListedInvestBalance() {
		return this.listedInvestBalance;
	}
	
	public void setListedInvestBalance(java.math.BigDecimal value) {
		this.listedInvestBalance = value;
	}
	

	public java.math.BigDecimal getUnlistedInvestBalance() {
		return this.unlistedInvestBalance;
	}
	
	public void setUnlistedInvestBalance(java.math.BigDecimal value) {
		this.unlistedInvestBalance = value;
	}
	

	public java.math.BigDecimal getInvestAmount() {
		return this.investAmount;
	}
	
	public void setInvestAmount(java.math.BigDecimal value) {
		this.investAmount = value;
	}
	

	public java.math.BigDecimal getImvbsInvestBalance() {
		return this.imvbsInvestBalance;
	}
	
	public void setImvbsInvestBalance(java.math.BigDecimal value) {
		this.imvbsInvestBalance = value;
	}
	

	public java.math.BigDecimal getImvbsInvestAmount() {
		return this.imvbsInvestAmount;
	}
	
	public void setImvbsInvestAmount(java.math.BigDecimal value) {
		this.imvbsInvestAmount = value;
	}
	

	public java.math.BigDecimal getAbroadFund() {
		return this.abroadFund;
	}
	
	public void setAbroadFund(java.math.BigDecimal value) {
		this.abroadFund = value;
	}
	

	public java.math.BigDecimal getAbroadAmount() {
		return this.abroadAmount;
	}
	
	public void setAbroadAmount(java.math.BigDecimal value) {
		this.abroadAmount = value;
	}
	

	public java.math.BigDecimal getInsManageProFund() {
		return this.insManageProFund;
	}
	
	public void setInsManageProFund(java.math.BigDecimal value) {
		this.insManageProFund = value;
	}
	

	public java.math.BigDecimal getComBankFinFund() {
		return this.comBankFinFund;
	}
	
	public void setComBankFinFund(java.math.BigDecimal value) {
		this.comBankFinFund = value;
	}
	

	public java.math.BigDecimal getBankCreditFund() {
		return this.bankCreditFund;
	}
	
	public void setBankCreditFund(java.math.BigDecimal value) {
		this.bankCreditFund = value;
	}
	

	public java.math.BigDecimal getTrustFund() {
		return this.trustFund;
	}
	
	public void setTrustFund(java.math.BigDecimal value) {
		this.trustFund = value;
	}
	

	public java.math.BigDecimal getBondSpecialFund() {
		return this.bondSpecialFund;
	}
	
	public void setBondSpecialFund(java.math.BigDecimal value) {
		this.bondSpecialFund = value;
	}
	

	public java.math.BigDecimal getInsCompanyPlanFund() {
		return this.insCompanyPlanFund;
	}
	
	public void setInsCompanyPlanFund(java.math.BigDecimal value) {
		this.insCompanyPlanFund = value;
	}
	

	public java.math.BigDecimal getOtherFund() {
		return this.otherFund;
	}
	
	public void setOtherFund(java.math.BigDecimal value) {
		this.otherFund = value;
	}
	

	public java.math.BigDecimal getOtherTotalFund() {
		return this.otherTotalFund;
	}
	
	public void setOtherTotalFund(java.math.BigDecimal value) {
		this.otherTotalFund = value;
	}
	

	public java.math.BigDecimal getOtherTotalAmount() {
		return this.otherTotalAmount;
	}
	
	public void setOtherTotalAmount(java.math.BigDecimal value) {
		this.otherTotalAmount = value;
	}
	

	public String getAllfundsSeq() {
		return this.allfundsSeq;
	}
	
	public void setAllfundsSeq(String value) {
		this.allfundsSeq = value;
	}

	public BigDecimal getAllTotalFund() {
		return allTotalFund;
	}

	public void setAllTotalFund(BigDecimal allTotalFund) {
		this.allTotalFund = allTotalFund;
	}

	public String toString() {
		return new StringBuilder("insAllfundsView[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("fixedProfitBalance = ").append(fixedProfitBalance).append(", \n")
						.append("listedInvestBalance = ").append(listedInvestBalance).append(", \n")
						.append("unlistedInvestBalance = ").append(unlistedInvestBalance).append(", \n")
						.append("investAmount = ").append(investAmount).append(", \n")
						.append("imvbsInvestBalance = ").append(imvbsInvestBalance).append(", \n")
						.append("imvbsInvestAmount = ").append(imvbsInvestAmount).append(", \n")
						.append("abroadFund = ").append(abroadFund).append(", \n")
						.append("abroadAmount = ").append(abroadAmount).append(", \n")
						.append("insManageProFund = ").append(insManageProFund).append(", \n")
						.append("comBankFinFund = ").append(comBankFinFund).append(", \n")
						.append("bankCreditFund = ").append(bankCreditFund).append(", \n")
						.append("trustFund = ").append(trustFund).append(", \n")
						.append("bondSpecialFund = ").append(bondSpecialFund).append(", \n")
						.append("insCompanyPlanFund = ").append(insCompanyPlanFund).append(", \n")
						.append("otherFund = ").append(otherFund).append(", \n")
						.append("otherTotalFund = ").append(otherTotalFund).append(", \n")
						.append("allTotalFund = ").append(allTotalFund).append(", \n")
						.append("otherTotalAmount = ").append(otherTotalAmount).append(", \n")
						.append("allfundsSeq = ").append(allfundsSeq).append(", \n")
						.append("]")
						.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof InsAllfundsView == false) return false;
		if(this == obj) return true;
		InsAllfundsView other = (InsAllfundsView)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

