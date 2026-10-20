package com.yzj.beans;

import java.math.BigDecimal;

public class Report_groupSum {
	BigDecimal sum;

	public Report_groupSum() {
		super();
	}

	public Report_groupSum(BigDecimal sum) {
		super();
		this.sum = sum;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	@Override
	public String toString() {
		return "Report_groupSum [sum=" + sum + "]";
	}
	
}
