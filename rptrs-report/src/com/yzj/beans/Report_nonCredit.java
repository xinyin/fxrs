package com.yzj.beans;

import java.math.BigDecimal;

public class Report_nonCredit {
	private String related_name;
	private String ident_number;
	private String important_trade;
	private String contract_no;
	private String contract_sign_date;
	private String business_start_time;
	private String business_end_time;
	private String tran_type;
	private String business_type;
	private BigDecimal tran_amount;
	private String tran_price;
	private String tran_target;
	private String pricing_strategy;
	private String remark;
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
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getContract_sign_date() {
		return contract_sign_date;
	}
	public void setContract_sign_date(String contract_sign_date) {
		this.contract_sign_date = contract_sign_date;
	}
	public String getBusiness_start_time() {
		return business_start_time;
	}
	public void setBusiness_start_time(String business_start_time) {
		this.business_start_time = business_start_time;
	}
	public String getBusiness_end_time() {
		return business_end_time;
	}
	public void setBusiness_end_time(String business_end_time) {
		this.business_end_time = business_end_time;
	}
	public String getTran_type() {
		return tran_type;
	}
	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}
	public String getBusiness_type() {
		return business_type;
	}
	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}
	public BigDecimal getTran_amount() {
		return tran_amount;
	}
	public void setTran_amount(BigDecimal tran_amount) {
		this.tran_amount = tran_amount;
	}
	public String getTran_price() {
		return tran_price;
	}
	public void setTran_price(String tran_price) {
		this.tran_price = tran_price;
	}
	public String getTran_target() {
		return tran_target;
	}
	public void setTran_target(String tran_target) {
		this.tran_target = tran_target;
	}
	public String getPricing_strategy() {
		return pricing_strategy;
	}
	public void setPricing_strategy(String pricing_strategy) {
		this.pricing_strategy = pricing_strategy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Report_nonCredit(String related_name, String ident_number, String important_trade, String contract_no,
			String contract_sign_date, String business_start_time, String business_end_time, String tran_type,
			String business_type, BigDecimal tran_amount, String tran_price, String tran_target,
			String pricing_strategy, String remark) {
		super();
		this.related_name = related_name;
		this.ident_number = ident_number;
		this.important_trade = important_trade;
		this.contract_no = contract_no;
		this.contract_sign_date = contract_sign_date;
		this.business_start_time = business_start_time;
		this.business_end_time = business_end_time;
		this.tran_type = tran_type;
		this.business_type = business_type;
		this.tran_amount = tran_amount;
		this.tran_price = tran_price;
		this.tran_target = tran_target;
		this.pricing_strategy = pricing_strategy;
		this.remark = remark;
	}
	public Report_nonCredit() {
		super();
	}
	@Override
	public String toString() {
		return "Report_nonCredit [related_name=" + related_name + ", ident_number=" + ident_number
				+ ", important_trade=" + important_trade + ", contract_no=" + contract_no + ", contract_sign_date="
				+ contract_sign_date + ", business_start_time=" + business_start_time + ", business_end_time="
				+ business_end_time + ", tran_type=" + tran_type + ", business_type=" + business_type + ", tran_amount="
				+ tran_amount + ", tran_price=" + tran_price + ", tran_target=" + tran_target + ", pricing_strategy="
				+ pricing_strategy + ", remark=" + remark + "]";
	}
	
}
