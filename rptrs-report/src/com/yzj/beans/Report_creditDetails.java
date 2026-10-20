package com.yzj.beans;

import java.math.BigDecimal;

public class Report_creditDetails {
	String related_name;
	String ident_number;
	String important_trade;
	String internal_apply_no;
	String apply_over_time;
	String apply_time;
	BigDecimal credit_amount;
	String credit_plan;
	String remark;
	public String getRelated_name() {
		return related_name;
	}
	public void setRelated_name(String related_name) {
		this.related_name = related_name;
	}
	public String getIdent_number() {
		return ident_number;
	}
	public void setIdent_number(String ident_number) {
		this.ident_number = ident_number;
	}
	public String getImportant_trade() {
		return important_trade;
	}
	public void setImportant_trade(String important_trade) {
		this.important_trade = important_trade;
	}
	public String getInternal_apply_no() {
		return internal_apply_no;
	}
	public void setInternal_apply_no(String internal_apply_no) {
		this.internal_apply_no = internal_apply_no;
	}
	public String getApply_over_time() {
		return apply_over_time;
	}
	public void setApply_over_time(String apply_over_time) {
		this.apply_over_time = apply_over_time;
	}
	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public BigDecimal getCredit_amount() {
		return credit_amount;
	}
	public void setCredit_amount(BigDecimal credit_amount) {
		this.credit_amount = credit_amount;
	}
	public String getCredit_plan() {
		return credit_plan;
	}
	public void setCredit_plan(String credit_plan) {
		this.credit_plan = credit_plan;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Report_creditDetails(String related_name, String ident_number, String important_trade, String internal_apply_no,
			String apply_over_time, String apply_time, BigDecimal credit_amount, String credit_plan, String remark) {
		super();
		this.related_name = related_name;
		this.ident_number = ident_number;
		this.important_trade = important_trade;
		this.internal_apply_no = internal_apply_no;
		this.apply_over_time = apply_over_time;
		this.apply_time = apply_time;
		this.credit_amount = credit_amount;
		this.credit_plan = credit_plan;
		this.remark = remark;
	}
	public Report_creditDetails() {
		super();
	}
	@Override
	public String toString() {
		return "bus_tra_credit [related_name=" + related_name + ", ident_number=" + ident_number + ", important_trade="
				+ important_trade + ", internal_apply_no=" + internal_apply_no + ", apply_over_time=" + apply_over_time
				+ ", apply_time=" + apply_time + ", credit_amount=" + credit_amount + ", credit_plan=" + credit_plan
				+ ", remark=" + remark + "]";
	}
	
}
