package com.yzj.beans;

import java.math.BigDecimal;

public class Report_alltra {
	String NAME;
	String number;
	BigDecimal ii;
	BigDecimal a;
	BigDecimal b;
	BigDecimal c;
	BigDecimal d;

	public Report_alltra() {
		super();
	}

	public Report_alltra(String nAME, String number, BigDecimal ii, BigDecimal a, BigDecimal b, BigDecimal c,
			BigDecimal d) {
		super();
		NAME = nAME;
		this.number = number;
		this.ii = ii;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public BigDecimal getIi() {
		return ii;
	}
	public void setIi(BigDecimal ii2) {
		this.ii = ii2;
	}
	public BigDecimal getA() {
		return a;
	}
	public void setA(BigDecimal a) {
		this.a = a;
	}
	public BigDecimal getB() {
		return b;
	}
	public void setB(BigDecimal b) {
		this.b = b;
	}
	public BigDecimal getC() {
		return c;
	}
	public void setC(BigDecimal c) {
		this.c = c;
	}
	public BigDecimal getD() {
		return d;
	}
	public void setD(BigDecimal d) {
		this.d = d;
	}
	@Override
	public String toString() {
		return "Report_alltra [NAME=" + NAME + ", number=" + number + ", ii=" + ii + ", a=" + a + ", b=" + b + ", c="
				+ c + ", d=" + d + "]";
	}
	
}
