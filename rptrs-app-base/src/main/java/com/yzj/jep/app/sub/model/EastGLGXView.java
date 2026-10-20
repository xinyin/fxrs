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
@Table(name = "east_glgxb_view")
public class EastGLGXView extends BasePO {

	public static final String TABLE_ALIAS = "EastGlgxbView";
	public static final String ALIAS_RELID = "关联east_glgxb的ID";
	public static final String ALIAS_PCH = "批次号";
	public static final String ALIAS_BUSSEDATE = "采集日期";
	public static final String ALIAS_GLFMC_GLFZJJDMC = "关联方名称/关联方中间节点名称";
	public static final String ALIAS_GLFDM_GLFZJJDDM = "关联方代码/关联方中间节点代码";
	public static final String ALIAS_GLF_GLFZJJDLX = "关联方/关联方中间节点类型";
	public static final String ALIAS_QYCGLFMC_GLFZJJDMC = "前一层关联方名称/关联方中间节点名称";
	public static final String ALIAS_QYCGLFDM_GLFZJJDDM = "前一层关联方代码/关联方中间节点代码";
	public static final String ALIAS_QYCGLF_GLFZJJDLX = "前一层关联方/关联方中间节点类型";
	public static final String ALIAS_YQYCDGLGX = "与前一层的关联关系";
	public static final String ALIAS_YQYCZJSFYQYFE = "与前一层之间是否有权益份额";
	public static final String ALIAS_CYBL = "持有比例";
	public static final String ALIAS_ZYGQBL = "质押股权比例";
	public static final String ALIAS_GLGXSM = "关联关系说明";
	public static final String ALIAS_GXYXBZ = "关系有效标志";
	public static final String ALIAS_SXRQ = "生效日期";
	public static final String ALIAS_SXRQ1 = "失效日期";
	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "ID", nullable = false, length = 11)
	private Integer id;

	@Column(name = "RELID", nullable = true, length = 11)
	private Integer relid;

	@Column(name = "PCH", nullable = true, length = 100)
	private String pch;

	@Column(name = "BUSSEDATE", nullable = true, length = 8)
	private String bussedate;

	@Column(name = "GLFMC_GLFZJJDMC", nullable = true, length = 200)
	private String glfmcGlfzjjdmc;

	@Column(name = "GLFDM_GLFZJJDDM", nullable = true, length = 100)
	private String glfdmGlfzjjddm;

	@Column(name = "GLF_GLFZJJDLX", nullable = true, length = 40)
	private String glfGlfzjjdlx;

	@Column(name = "QYCGLFMC_GLFZJJDMC", nullable = true, length = 200)
	private String qycglfmcGlfzjjdmc;

	@Column(name = "QYCGLFDM_GLFZJJDDM", nullable = true, length = 100)
	private String qycglfdmGlfzjjddm;

	@Column(name = "QYCGLF_GLFZJJDLX", nullable = true, length = 40)
	private String qycglfGlfzjjdlx;

	@Column(name = "YQYCDGLGX", nullable = true, length = 50)
	private String yqycdglgx;

	@Column(name = "YQYCZJSFYQYFE", nullable = true, length = 30)
	private String yqyczjsfyqyfe;

	@Column(name = "CYBL", nullable = true, length = 22)
	private String cybl;

	@Column(name = "ZYGQBL", nullable = true, length = 22)
	private String zygqbl;

	@Column(name = "GLGXSM", nullable = true, length = 4000)
	private String glgxsm;

	@Column(name = "GXYXBZ", nullable = true, length = 10)
	private String gxyxbz;

	@Column(name = "SXRQ", nullable = true, length = 8)
	private String sxrq;

	@Column(name = "SXRQ1", nullable = true, length = 8)
	private String sxrq1;

	@Column(name = "sub_org", nullable = true, length = 65)
	private String subOrg;

	@Column(name = "sub_num", nullable = true, length = 65)
	private String subNum;

	@Column(name = "khbh", nullable = true, length = 65)
	private String khbh;

	@Column(name = "khmc", nullable = true, length = 65)
	private String khmc;

	@Column(name = "khsbm", nullable = true, length = 65)
	private String khsbm;

	@Column(name = "khlb", nullable = true, length = 65)
	private String khlb;

	@Column(name = "glfbh", nullable = true, length = 20)
	private String glfbh;

	//columns END


	public String getSubOrg() {
		return subOrg;
	}

	public void setSubOrg(String subOrg) {
		this.subOrg = subOrg;
	}

	public String getSubNum() {
		return subNum;
	}

	public void setSubNum(String subNum) {
		this.subNum = subNum;
	}

	public String getKhbh() {
		return khbh;
	}

	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}

	public String getKhmc() {
		return khmc;
	}

	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}

	public String getKhsbm() {
		return khsbm;
	}

	public void setKhsbm(String khsbm) {
		this.khsbm = khsbm;
	}

	public String getKhlb() {
		return khlb;
	}

	public void setKhlb(String khlb) {
		this.khlb = khlb;
	}

	public String getGlfbh() {
		return glfbh;
	}

	public void setGlfbh(String glfbh) {
		this.glfbh = glfbh;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRelid() {
		return relid;
	}

	public void setRelid(Integer relid) {
		this.relid = relid;
	}

	public String getPch() {
		return pch;
	}

	public void setPch(String pch) {
		this.pch = pch;
	}

	public String getBussedate() {
		return bussedate;
	}

	public void setBussedate(String bussedate) {
		this.bussedate = bussedate;
	}

	public String getGlfmcGlfzjjdmc() {
		return glfmcGlfzjjdmc;
	}

	public void setGlfmcGlfzjjdmc(String glfmcGlfzjjdmc) {
		this.glfmcGlfzjjdmc = glfmcGlfzjjdmc;
	}

	public String getGlfdmGlfzjjddm() {
		return glfdmGlfzjjddm;
	}

	public void setGlfdmGlfzjjddm(String glfdmGlfzjjddm) {
		this.glfdmGlfzjjddm = glfdmGlfzjjddm;
	}

	public String getGlfGlfzjjdlx() {
		return glfGlfzjjdlx;
	}

	public void setGlfGlfzjjdlx(String glfGlfzjjdlx) {
		this.glfGlfzjjdlx = glfGlfzjjdlx;
	}

	public String getQycglfmcGlfzjjdmc() {
		return qycglfmcGlfzjjdmc;
	}

	public void setQycglfmcGlfzjjdmc(String qycglfmcGlfzjjdmc) {
		this.qycglfmcGlfzjjdmc = qycglfmcGlfzjjdmc;
	}

	public String getQycglfdmGlfzjjddm() {
		return qycglfdmGlfzjjddm;
	}

	public void setQycglfdmGlfzjjddm(String qycglfdmGlfzjjddm) {
		this.qycglfdmGlfzjjddm = qycglfdmGlfzjjddm;
	}

	public String getQycglfGlfzjjdlx() {
		return qycglfGlfzjjdlx;
	}

	public void setQycglfGlfzjjdlx(String qycglfGlfzjjdlx) {
		this.qycglfGlfzjjdlx = qycglfGlfzjjdlx;
	}

	public String getYqycdglgx() {
		return yqycdglgx;
	}

	public void setYqycdglgx(String yqycdglgx) {
		this.yqycdglgx = yqycdglgx;
	}

	public String getYqyczjsfyqyfe() {
		return yqyczjsfyqyfe;
	}

	public void setYqyczjsfyqyfe(String yqyczjsfyqyfe) {
		this.yqyczjsfyqyfe = yqyczjsfyqyfe;
	}

	public String getCybl() {
		return cybl;
	}

	public void setCybl(String cybl) {
		this.cybl = cybl;
	}

	public String getZygqbl() {
		return zygqbl;
	}

	public void setZygqbl(String zygqbl) {
		this.zygqbl = zygqbl;
	}

	public String getGlgxsm() {
		return glgxsm;
	}

	public void setGlgxsm(String glgxsm) {
		this.glgxsm = glgxsm;
	}

	public String getGxyxbz() {
		return gxyxbz;
	}

	public void setGxyxbz(String gxyxbz) {
		this.gxyxbz = gxyxbz;
	}

	public String getSxrq() {
		return sxrq;
	}

	public void setSxrq(String sxrq) {
		this.sxrq = sxrq;
	}

	public String getSxrq1() {
		return sxrq1;
	}

	public void setSxrq1(String sxrq1) {
		this.sxrq1 = sxrq1;
	}

	@Override
	public String toString() {
		return "EastGLGXView{" +
				"id=" + id +
				", relid=" + relid +
				", pch='" + pch + '\'' +
				", bussedate='" + bussedate + '\'' +
				", glfmcGlfzjjdmc='" + glfmcGlfzjjdmc + '\'' +
				", glfdmGlfzjjddm='" + glfdmGlfzjjddm + '\'' +
				", glfGlfzjjdlx='" + glfGlfzjjdlx + '\'' +
				", qycglfmcGlfzjjdmc='" + qycglfmcGlfzjjdmc + '\'' +
				", qycglfdmGlfzjjddm='" + qycglfdmGlfzjjddm + '\'' +
				", qycglfGlfzjjdlx='" + qycglfGlfzjjdlx + '\'' +
				", yqycdglgx='" + yqycdglgx + '\'' +
				", yqyczjsfyqyfe='" + yqyczjsfyqyfe + '\'' +
				", cybl='" + cybl + '\'' +
				", zygqbl='" + zygqbl + '\'' +
				", glgxsm='" + glgxsm + '\'' +
				", gxyxbz='" + gxyxbz + '\'' +
				", sxrq='" + sxrq + '\'' +
				", sxrq1='" + sxrq1 + '\'' +
				'}';
	}
}

