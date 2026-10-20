package com.yzj.beans;

import java.math.BigDecimal;
import java.util.Date;

public class Report_business {
	String REL_NAME;
	String REL_NUMBER;
	String MAJOR_TRADE;
	String CONTRACT_NO;
	Date TRAN_DATE;
	Date BUS_START_TIME;
	Date BUS_END_TIME;
	String BUS_TYPE;
	String TRADE_OCCUR;
	BigDecimal TRADE_AMOUNT;
	String TRADE_PRICE;
	String TRADE_SUBJECT;
	String PRICING_POLICY;
	String REMARK;
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
	public String getBUS_TYPE() {
		return BUS_TYPE;
	}
	public void setBUS_TYPE(String bUS_TYPE) {
		BUS_TYPE = bUS_TYPE;
	}
	public String getTRADE_OCCUR() {
		return TRADE_OCCUR;
	}
	public void setTRADE_OCCUR(String tRADE_OCCUR) {
		TRADE_OCCUR = tRADE_OCCUR;
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
	public Report_business(String rEL_NAME, String rEL_NUMBER, String mAJOR_TRADE, String cONTRACT_NO, Date tRAN_DATE,
			Date bUS_START_TIME, Date bUS_END_TIME, String bUS_TYPE, String tRADE_OCCUR, BigDecimal tRADE_AMOUNT,
			String tRADE_PRICE, String tRADE_SUBJECT, String pRICING_POLICY, String rEMARK) {
		super();
		REL_NAME = rEL_NAME;
		REL_NUMBER = rEL_NUMBER;
		MAJOR_TRADE = mAJOR_TRADE;
		CONTRACT_NO = cONTRACT_NO;
		TRAN_DATE = tRAN_DATE;
		BUS_START_TIME = bUS_START_TIME;
		BUS_END_TIME = bUS_END_TIME;
		BUS_TYPE = bUS_TYPE;
		TRADE_OCCUR = tRADE_OCCUR;
		TRADE_AMOUNT = tRADE_AMOUNT;
		TRADE_PRICE = tRADE_PRICE;
		TRADE_SUBJECT = tRADE_SUBJECT;
		PRICING_POLICY = pRICING_POLICY;
		REMARK = rEMARK;
	}
	public Report_business() {
		super();
	}
	@Override
	public String toString() {
		return "report_business [REL_NAME=" + REL_NAME + ", REL_NUMBER=" + REL_NUMBER + ", MAJOR_TRADE=" + MAJOR_TRADE
				+ ", CONTRACT_NO=" + CONTRACT_NO + ", TRAN_DATE=" + TRAN_DATE + ", BUS_START_TIME=" + BUS_START_TIME
				+ ", BUS_END_TIME=" + BUS_END_TIME + ", BUS_TYPE=" + BUS_TYPE + ", TRADE_OCCUR=" + TRADE_OCCUR
				+ ", TRADE_AMOUNT=" + TRADE_AMOUNT + ", TRADE_PRICE=" + TRADE_PRICE + ", TRADE_SUBJECT=" + TRADE_SUBJECT
				+ ", PRICING_POLICY=" + PRICING_POLICY + ", REMARK=" + REMARK + "]";
	}
	
}
