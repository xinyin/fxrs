package com.yzj.beans;

import java.math.BigDecimal;

public class Report_tra {

	String NAME;
	String ident_number;
	
	BigDecimal loan;
	BigDecimal bondInvestment;
	BigDecimal investment;
	BigDecimal otherinnercredit;
	BigDecimal irrevocablecommitments;
	BigDecimal nonbreakevenfinancial;
	BigDecimal otheroutcredit;
	
	BigDecimal asset_tran_amount;
	BigDecimal provide_tran_amount;
	BigDecimal other_tran_amount;
	BigDecimal depanddet;
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getIdent_number() {
		return ident_number;
	}
	public void setIdent_number(String ident_number) {
		this.ident_number = ident_number;
	}
	public BigDecimal getLoan() {
		return loan;
	}
	public void setLoan(BigDecimal loan) {
		this.loan = loan;
	}
	public BigDecimal getBondInvestment() {
		return bondInvestment;
	}
	public void setBondInvestment(BigDecimal bondInvestment) {
		this.bondInvestment = bondInvestment;
	}
	public BigDecimal getInvestment() {
		return investment;
	}
	public void setInvestment(BigDecimal investment) {
		this.investment = investment;
	}
	public BigDecimal getOtherinnercredit() {
		return otherinnercredit;
	}
	public void setOtherinnercredit(BigDecimal otherinnercredit) {
		this.otherinnercredit = otherinnercredit;
	}
	public BigDecimal getIrrevocablecommitments() {
		return irrevocablecommitments;
	}
	public void setIrrevocablecommitments(BigDecimal irrevocablecommitments) {
		this.irrevocablecommitments = irrevocablecommitments;
	}
	public BigDecimal getNonbreakevenfinancial() {
		return nonbreakevenfinancial;
	}
	public void setNonbreakevenfinancial(BigDecimal nonbreakevenfinancial) {
		this.nonbreakevenfinancial = nonbreakevenfinancial;
	}
	public BigDecimal getOtheroutcredit() {
		return otheroutcredit;
	}
	public void setOtheroutcredit(BigDecimal otheroutcredit) {
		this.otheroutcredit = otheroutcredit;
	}
	public BigDecimal getAsset_tran_amount() {
		return asset_tran_amount;
	}
	public void setAsset_tran_amount(BigDecimal asset_tran_amount) {
		this.asset_tran_amount = asset_tran_amount;
	}
	public BigDecimal getProvide_tran_amount() {
		return provide_tran_amount;
	}
	public void setProvide_tran_amount(BigDecimal provide_tran_amount) {
		this.provide_tran_amount = provide_tran_amount;
	}
	public BigDecimal getOther_tran_amount() {
		return other_tran_amount;
	}
	public void setOther_tran_amount(BigDecimal other_tran_amount) {
		this.other_tran_amount = other_tran_amount;
	}
	public BigDecimal getDepanddet() {
		return depanddet;
	}
	public void setDepanddet(BigDecimal depanddet) {
		this.depanddet = depanddet;
	}
	public Report_tra(String nAME, String ident_number, BigDecimal loan, BigDecimal bondInvestment,
			BigDecimal investment, BigDecimal otherinnercredit, BigDecimal irrevocablecommitments,
			BigDecimal nonbreakevenfinancial, BigDecimal otheroutcredit, BigDecimal asset_tran_amount,
			BigDecimal provide_tran_amount, BigDecimal other_tran_amount, BigDecimal depanddet) {
		super();
		NAME = nAME;
		this.ident_number = ident_number;
		this.loan = loan;
		this.bondInvestment = bondInvestment;
		this.investment = investment;
		this.otherinnercredit = otherinnercredit;
		this.irrevocablecommitments = irrevocablecommitments;
		this.nonbreakevenfinancial = nonbreakevenfinancial;
		this.otheroutcredit = otheroutcredit;
		this.asset_tran_amount = asset_tran_amount;
		this.provide_tran_amount = provide_tran_amount;
		this.other_tran_amount = other_tran_amount;
		this.depanddet = depanddet;
	}
	public Report_tra() {
		super();
	}
	@Override
	public String toString() {
		return "Report_tra [NAME=" + NAME + ", ident_number=" + ident_number + ", loan=" + loan + ", bondInvestment="
				+ bondInvestment + ", investment=" + investment + ", otherinnercredit=" + otherinnercredit
				+ ", irrevocablecommitments=" + irrevocablecommitments + ", nonbreakevenfinancial="
				+ nonbreakevenfinancial + ", otheroutcredit=" + otheroutcredit + ", asset_tran_amount="
				+ asset_tran_amount + ", provide_tran_amount=" + provide_tran_amount + ", other_tran_amount="
				+ other_tran_amount + ", depanddet=" + depanddet + "]";
	}
	
	
	
	
	

}
