package com.yzj.beans;

import java.sql.Timestamp;
import java.util.Date;

import com.ibm.icu.math.BigDecimal;

public class Report_investment {
	String REL_NAME;
	String REL_NUMBER;
	String MAJOR_TRADE;
	String CONTRACT_NO;
	Date TRAN_DATE;
	Date BUS_START_TIME;
	Date BUS_END_TIME;
	String INVESTMENT_TYPE;
	BigDecimal APPROVAL_NUM;
	String SECURITIES_TYPE;
	Timestamp IN_TIME;
	String SHARE_NUM;
	String SHARE_RETIO;
	BigDecimal TRADE_AMOUNT;
	String TRADE_PRICE;
	String TRADE_SUBJECT;
	String PRICING_POLICY;
	String REMARK;
	public Report_investment() {
		super();
	}


	public Report_investment(String rEL_NAME, String rEL_NUMBER, String mAJOR_TRADE, String cONTRACT_NO, Date tRAN_DATE,
			Date bUS_START_TIME, Date bUS_END_TIME, String iNVESTMENT_TYPE, BigDecimal aPPROVAL_NUM,
			String sECURITIES_TYPE, Timestamp iN_TIME, String sHARE_NUM, String sHARE_RETIO, BigDecimal tRADE_AMOUNT,
			String tRADE_PRICE, String tRADE_SUBJECT, String pRICING_POLICY, String rEMARK) {
		super();
		REL_NAME = rEL_NAME;
		REL_NUMBER = rEL_NUMBER;
		MAJOR_TRADE = mAJOR_TRADE;
		CONTRACT_NO = cONTRACT_NO;
		TRAN_DATE = tRAN_DATE;
		BUS_START_TIME = bUS_START_TIME;
		BUS_END_TIME = bUS_END_TIME;
		INVESTMENT_TYPE = iNVESTMENT_TYPE;
		APPROVAL_NUM = aPPROVAL_NUM;
		SECURITIES_TYPE = sECURITIES_TYPE;
		IN_TIME = iN_TIME;
		SHARE_NUM = sHARE_NUM;
		SHARE_RETIO = sHARE_RETIO;
		TRADE_AMOUNT = tRADE_AMOUNT;
		TRADE_PRICE = tRADE_PRICE;
		TRADE_SUBJECT = tRADE_SUBJECT;
		PRICING_POLICY = pRICING_POLICY;
		REMARK = rEMARK;
	}
	public String getREL_NAME() {
		return REL_NAME;
	}
	public void setREL_NAME(String rEL_NAME) {
		REL_NAME = rEL_NAME;
	}
	public String getREL_NUMBER() {
		return REL_NUMBER;
	}
	public void setREL_NUMBER(String rEL_NUMBER) {
		REL_NUMBER = rEL_NUMBER;
	}
	public String getMAJOR_TRADE() {
		return MAJOR_TRADE;
	}
	public void setMAJOR_TRADE(String mAJOR_TRADE) {
		MAJOR_TRADE = mAJOR_TRADE;
	}
	public String getCONTRACT_NO() {
		return CONTRACT_NO;
	}
	public void setCONTRACT_NO(String cONTRACT_NO) {
		CONTRACT_NO = cONTRACT_NO;
	}
	public Date getTRAN_DATE() {
		return TRAN_DATE;
	}
	public void setTRAN_DATE(Date tRAN_DATE) {
		TRAN_DATE = tRAN_DATE;
	}
	public Date getBUS_START_TIME() {
		return BUS_START_TIME;
	}
	public void setBUS_START_TIME(Date bUS_START_TIME) {
		BUS_START_TIME = bUS_START_TIME;
	}
	public Date getBUS_END_TIME() {
		return BUS_END_TIME;
	}
	public void setBUS_END_TIME(Date bUS_END_TIME) {
		BUS_END_TIME = bUS_END_TIME;
	}
	public String getINVESTMENT_TYPE() {
		return INVESTMENT_TYPE;
	}
	public void setINVESTMENT_TYPE(String iNVESTMENT_TYPE) {
		INVESTMENT_TYPE = iNVESTMENT_TYPE;
	}
	public BigDecimal getAPPROVAL_NUM() {
		return APPROVAL_NUM;
	}
	public void setAPPROVAL_NUM(BigDecimal aPPROVAL_NUM) {
		APPROVAL_NUM = aPPROVAL_NUM;
	}
	public String getSECURITIES_TYPE() {
		return SECURITIES_TYPE;
	}
	public void setSECURITIES_TYPE(String sECURITIES_TYPE) {
		SECURITIES_TYPE = sECURITIES_TYPE;
	}
	public Timestamp getIN_TIME() {
		return IN_TIME;
	}
	public void setIN_TIME(Timestamp iN_TIME) {
		IN_TIME = iN_TIME;
	}
	public String getSHARE_NUM() {
		return SHARE_NUM;
	}
	public void setSHARE_NUM(String sHARE_NUM) {
		SHARE_NUM = sHARE_NUM;
	}
	public String getSHARE_RETIO() {
		return SHARE_RETIO;
	}
	public void setSHARE_RETIO(String sHARE_RETIO) {
		SHARE_RETIO = sHARE_RETIO;
	}
	public BigDecimal getTRADE_AMOUNT() {
		return TRADE_AMOUNT;
	}
	public void setTRADE_AMOUNT(BigDecimal tRADE_AMOUNT) {
		TRADE_AMOUNT = tRADE_AMOUNT;
	}
	public String getTRADE_PRICE() {
		return TRADE_PRICE;
	}
	public void setTRADE_PRICE(String tRADE_PRICE) {
		TRADE_PRICE = tRADE_PRICE;
	}
	public String getTRADE_SUBJECT() {
		return TRADE_SUBJECT;
	}
	public void setTRADE_SUBJECT(String tRADE_SUBJECT) {
		TRADE_SUBJECT = tRADE_SUBJECT;
	}
	public String getPRICING_POLICY() {
		return PRICING_POLICY;
	}
	public void setPRICING_POLICY(String pRICING_POLICY) {
		PRICING_POLICY = pRICING_POLICY;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	@Override
	public String toString() {
		return "Report_investment [REL_NAME=" + REL_NAME + ", REL_NUMBER=" + REL_NUMBER + ", MAJOR_TRADE=" + MAJOR_TRADE
				+ ", CONTRACT_NO=" + CONTRACT_NO + ", TRAN_DATE=" + TRAN_DATE + ", BUS_START_TIME=" + BUS_START_TIME
				+ ", BUS_END_TIME=" + BUS_END_TIME + ", INVESTMENT_TYPE=" + INVESTMENT_TYPE + ", APPROVAL_NUM="
				+ APPROVAL_NUM + ", SECURITIES_TYPE=" + SECURITIES_TYPE + ", IN_TIME=" + IN_TIME + ", SHARE_NUM="
				+ SHARE_NUM + ", SHARE_RETIO=" + SHARE_RETIO + ", TRADE_AMOUNT=" + TRADE_AMOUNT + ", TRADE_PRICE="
				+ TRADE_PRICE + ", TRADE_SUBJECT=" + TRADE_SUBJECT + ", PRICING_POLICY=" + PRICING_POLICY + ", REMARK="
				+ REMARK + "]";
	}
	
	
}
