package com.yzj.beans;

import java.util.Date;

public class Report_group {
	Date TRAN_DATE;
	String REL_NAME;
	String relStatement;
	String TRAN_TYPE;
	String REMARK;
	String TRADE_AMOUNT;
	public Report_group() {
		super();
	}
	public Report_group(Date tRAN_DATE, String rEL_NAME, String relStatement, String tRAN_TYPE, String rEMARK,
			String tRADE_AMOUNT) {
		super();
		TRAN_DATE = tRAN_DATE;
		REL_NAME = rEL_NAME;
		this.relStatement = relStatement;
		TRAN_TYPE = tRAN_TYPE;
		REMARK = rEMARK;
		TRADE_AMOUNT = tRADE_AMOUNT;
	}
	public Date getTRAN_DATE() {
		return TRAN_DATE;
	}
	public void setTRAN_DATE(Date tRAN_DATE) {
		TRAN_DATE = tRAN_DATE;
	}
	public String getREL_NAME() {
		return REL_NAME;
	}
	public void setREL_NAME(String rEL_NAME) {
		REL_NAME = rEL_NAME;
	}
	public String getRelStatement() {
		return relStatement;
	}
	public void setRelStatement(String relStatement) {
		this.relStatement = relStatement;
	}
	public String getTRAN_TYPE() {
		return TRAN_TYPE;
	}
	public void setTRAN_TYPE(String tRAN_TYPE) {
		TRAN_TYPE = tRAN_TYPE;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getTRADE_AMOUNT() {
		return TRADE_AMOUNT;
	}
	public void setTRADE_AMOUNT(String tRADE_AMOUNT) {
		TRADE_AMOUNT = tRADE_AMOUNT;
	}
	@Override
	public String toString() {
		return "Report_group [TRAN_DATE=" + TRAN_DATE + ", REL_NAME=" + REL_NAME + ", relStatement=" + relStatement
				+ ", TRAN_TYPE=" + TRAN_TYPE + ", REMARK=" + REMARK + ", TRADE_AMOUNT=" + TRADE_AMOUNT + "]";
	}
	
}
