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

@Entity
@Table(name = "east_glfxxb")
public class EastGLFXX extends BasePO {
	
	public static final String TABLE_ALIAS = "EastGlfxxb";
	public static final String ALIAS_BUSSEDATE = "采集日期";
	public static final String ALIAS_BXJGDM = "保险机构代码";
	public static final String ALIAS_BXJGMC = "保险机构名称";
	public static final String ALIAS_GLFBH = "关联方编号";
	public static final String ALIAS_GLFMC = "关联方名称";
	public static final String ALIAS_JNWXZ = "境内外性质";
	public static final String ALIAS_GLFLB = "关联方类别";
	public static final String ALIAS_JGZJLX = "机构证件类型";
	public static final String ALIAS_GRZJLX = "个人证件类型";
	public static final String ALIAS_GLFSBM = "关联方识别码";
	public static final String ALIAS_YXBZ = "关联有效标识";
	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "ID", nullable = false, length = 10)
	private Integer id;

	@Column(name = "BUSSEDATE", nullable = true, length = 8)
	private String bussedate;

	@Column(name = "BXJGDM", nullable = true, length = 6)
	private String bxjgdm;

	@Column(name = "BXJGMC", nullable = true, length = 200)
	private String bxjgmc;

	@Column(name = "GLFBH", nullable = true, length = 100)
	private String glfbh;

	@Column(name = "GLFMC", nullable = true, length = 200)
	private String glfmc;

	@Column(name = "JNWXZ", nullable = true, length = 20)
	private String jnwxz;

	@Column(name = "GLFLB", nullable = true, length = 30)
	private String glflb;

	@Column(name = "JGZJLX", nullable = true, length = 80)
	private String jgzjlx;

	@Column(name = "GRZJLX", nullable = true, length = 80)
	private String grzjlx;

	@Column(name = "GLFSBM", nullable = true, length = 100)
	private String glfsbm;

	@Column(name = "YXBZ", nullable = true, length = 20)
	private String yxbz;

	@Column(name = "EAST_STATUS", nullable = true, length = 4)
	private Integer eastStatus;
	@Column(name = "QTGLGXSM", nullable = true, length = 20)
	private String qtglgxsm;

	@Column(name = "GXLX", nullable = true, length = 4)
	private String gxlx;

	//columns END

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

	public String getJnwxz() {
		return jnwxz;
	}

	public void setJnwxz(String jnwxz) {
		this.jnwxz = jnwxz;
	}

	public String getGlflb() {
		return glflb;
	}

	public void setGlflb(String glflb) {
		this.glflb = glflb;
	}

	public String getJgzjlx() {
		return jgzjlx;
	}

	public void setJgzjlx(String jgzjlx) {
		this.jgzjlx = jgzjlx;
	}

	public String getGrzjlx() {
		return grzjlx;
	}

	public void setGrzjlx(String grzjlx) {
		this.grzjlx = grzjlx;
	}

	public String getGlfsbm() {
		return glfsbm;
	}

	public void setGlfsbm(String glfsbm) {
		this.glfsbm = glfsbm;
	}

	public String getYxbz() {
		return yxbz;
	}

	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}

	public String getQtglgxsm() {
		return qtglgxsm;
	}

	public void setQtglgxsm(String qtglgxsm) {
		this.qtglgxsm = qtglgxsm;
	}

	public String getGxlx() {
		return gxlx;
	}

	public void setGxlx(String gxlx) {
		this.gxlx = gxlx;
	}
}

