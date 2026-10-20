/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月13日 15:41:26
 *
 */

package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="east_gljyhzb")
public class EastGljyhzb extends BasePO {
	
	public static final String TABLE_ALIAS = "EastGljyhzb";
	public static final String ALIAS_BUSSEDATE = "采集日期";
	public static final String ALIAS_GLFBH = "关联方编号";
	public static final String ALIAS_GLFMC = "关联方名称";
	public static final String ALIAS_GLJYYWLX = "关联交易业务类型";
	public static final String ALIAS_GLJYJE = "关联交易金额";
	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "ID", nullable = false, length = 11)
	private Integer id;

	@Column(name = "BUSSEDATE", nullable = true, length = 8)
	private String bussedate;

	@Column(name = "GLFBH", nullable = true, length = 100)
	private String glfbh;

	@Column(name = "GLFMC", nullable = true, length = 200)
	private String glfmc;

	@Column(name = "GLJYYWLX", nullable = true, length = 50)
	private String gljyywlx;

	@Column(name = "GLJYJE", nullable = true, length = 22)
	private BigDecimal gljyje;

	@Column(name = "EAST_STATUS_", nullable = true, length = 4)
	private Integer eastStatus;

	@Column(name = "BXJGDM", nullable = true, length = 65)
	private String bxjgdm;

	@Column(name = "BXJGMC", nullable = true, length = 10)
	private String bxjgmc;

	@Column(name = "KHBH", nullable = true, length = 65)
	private String khbh;

	@Column(name = "TJZQ", nullable = true, length = 65)
	private String tjzq;
//
//	@Column(name = "khsbm", nullable = true, length = 65)
//	private String khsbm;
//
//	@Column(name = "khlb", nullable = true, length = 65)
//	private String khlb;
	@Column(name = "LSH", nullable = true, length = 20)
	private String lsh;
	@Column(name = "GLFLB", nullable = true, length = 20)
	private String glflb;
	//columns END


	public String getTjzq() {
		return tjzq;
	}

	public void setTjzq(String tjzq) {
		this.tjzq = tjzq;
	}

	public String getGlflb() {
		return glflb;
	}

	public void setGlflb(String glflb) {
		this.glflb = glflb;
	}

	public String getBxjgdm() {
		return bxjgdm;
	}

	public void setBxjgdm(String bxjgdm) {
		this.bxjgdm = bxjgdm;
	}

	public String getBxjgmc() {
		return bxjgmc;
	}

	public void setBxjgmc(String bxjgmc) {
		this.bxjgmc = bxjgmc;
	}

	public String getKhbh() {
		return khbh;
	}

	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}

	public String getLsh() {
		return lsh;
	}

	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer value) {
		this.id = value;
	}

	public Integer getEastStatus() {
		return eastStatus;
	}

	public void setEastStatus(Integer eastStatus) {
		this.eastStatus = eastStatus;
	}

	public String getBussedate() {
		return bussedate;
	}

	public void setBussedate(String bussedate) {
		this.bussedate = bussedate;
	}

	public String getGlfbh() {
		return glfbh;
	}

	public void setGlfbh(String glfbh) {
		this.glfbh = glfbh;
	}

	public String getGlfmc() {
		return glfmc;
	}

	public void setGlfmc(String glfmc) {
		this.glfmc = glfmc;
	}

	public String getGljyywlx() {
		return gljyywlx;
	}

	public void setGljyywlx(String gljyywlx) {
		this.gljyywlx = gljyywlx;
	}

	public BigDecimal getGljyje() {
		return gljyje;
	}

	public void setGljyje(BigDecimal gljyje) {
		this.gljyje = gljyje;
	}

	@Override
	public String toString() {
		return "EastGljyhzb{" +
				"id=" + id +
				", bussedate='" + bussedate + '\'' +
				", glfbh='" + glfbh + '\'' +
				", glfmc='" + glfmc + '\'' +
				", gljyywlx='" + gljyywlx + '\'' +
				", gljyje='" + gljyje + '\'' +
				", eastStatus='" + eastStatus + '\'' +
				'}';
	}
}

