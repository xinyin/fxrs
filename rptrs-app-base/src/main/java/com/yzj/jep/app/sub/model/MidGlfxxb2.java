/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2025 pactera.com. All rights reserved.
 *
 * Generated at 2025年01月23日 14:55:45
 *
 */

package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "mid_glfxxb_2")
public class MidGlfxxb2 extends BasePO {

	public static final String TABLE_ALIAS = "MidGlfxxb2";
	public static final String ALIAS_SERIALNO = "流水号";
	public static final String ALIAS_BATCHNO = "原始数据批次号";
	public static final String ALIAS_BUSSNO = "业务号";
	public static final String ALIAS_MAKEDATE = "申请日期";
	public static final String ALIAS_BUSSDATE = "标识日期";
	public static final String ALIAS_DATASRC = "数据来源";
	public static final String ALIAS_OTHERSIGN = "状态位标签";
	public static final String ALIAS_REMARK = "备注说明";
	public static final String ALIAS_BXJGDM = "保险机构代码";
	public static final String ALIAS_BXJGMC = "保险机构名称";
	public static final String ALIAS_GLFDM = "关联方代码";
	public static final String ALIAS_GLFMC = "关联方名称";
	public static final String ALIAS_JNWXZ = "境内外性质";
	public static final String ALIAS_GLFLX = "关联方类型";
	public static final String ALIAS_ZJLX = "证件类型";
	public static final String ALIAS_ZJLXZWMC = "证件类型中文名称";
	public static final String ALIAS_GLFZJHM = "关联方证件号码";
	public static final String ALIAS_JJXZHLX = "经济性质或类型";
	public static final String ALIAS_ZYYWHJYFW = "主营业务或经营范围";
	public static final String ALIAS_FDDBRXM = "法定代表人姓名";
	public static final String ALIAS_ZCD = "注册地";
	public static final String ALIAS_ZCZB = "注册资本";
	public static final String ALIAS_ZCZBBZ = "注册资本币种";
	public static final String ALIAS_YXBZ = "有效标志";
	public static final String ALIAS_CJRQ = "采集日期";
	public static final String ALIAS_JGXQDM = "监管辖区代码";


	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "SERIALNO", nullable = false, length = 60)
	private String serialno;

	@Column(name = "BATCHNO", nullable = true, length = 60)
	private String batchno;

	@Column(name = "BUSSNO", nullable = true, length = 600)
	private String bussno;

	@Column(name = "MAKEDATE", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date makedate;

	@Column(name = "BUSSDATE", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date bussdate;

	@Column(name = "DATASRC", nullable = true, length = 2)
	private String datasrc;

	@Column(name = "OTHERSIGN", nullable = true, length = 6)
	private String othersign;

	@Column(name = "REMARK", nullable = true, length = 150)
	private String remark;

	@Column(name = "BXJGDM", nullable = true, length = 45)
	private String bxjgdm;

	@Column(name = "BXJGMC", nullable = true, length = 600)
	private String bxjgmc;

	@Column(name = "GLFDM", nullable = true, length = 600)
	private String glfdm;

	@Column(name = "GLFMC", nullable = true, length = 600)
	private String glfmc;

	@Column(name = "JNWXZ", nullable = true, length = 30)
	private String jnwxz;

	@Column(name = "GLFLX", nullable = true, length = 6)
	private String glflx;

	@Column(name = "ZJLX", nullable = true, length = 12)
	private String zjlx;

	@Column(name = "ZJLXZWMC", nullable = true, length = 150)
	private String zjlxzwmc;

	@Column(name = "GLFZJHM", nullable = true, length = 300)
	private String glfzjhm;

	@Column(name = "JJXZHLX", nullable = true, length = 4000)
	private String jjxzhlx;

	@Column(name = "ZYYWHJYFW", nullable = true, length = 4000)
	private String zyywhjyfw;

	@Column(name = "FDDBRXM", nullable = true, length = 600)
	private String fddbrxm;

	@Column(name = "ZCD", nullable = true, length = 765)
	private String zcd;

	@Column(name = "ZCZB", nullable = true, precision = 22, scale = 0)
	private java.math.BigDecimal zczb;

	@Column(name = "ZCZBBZ", nullable = true, length = 9)
	private String zczbbz;

	@Column(name = "YXBZ", nullable = true, length = 3)
	private Long yxbz;

	@Column(name = "CJRQ", nullable = true, length = 10)
	@Temporal(TemporalType.DATE)
	private Date cjrq;

	@Column(name = "JGXQDM", nullable = true, length = 300)
	private String jgxqdm;
	//columns END


	public String getSerialno() {
		return this.serialno;
	}

	public void setSerialno(String value) {
		this.serialno = value;
	}


	public String getBatchno() {
		return this.batchno;
	}

	public void setBatchno(String value) {
		this.batchno = value;
	}


	public String getBussno() {
		return this.bussno;
	}

	public void setBussno(String value) {
		this.bussno = value;
	}


	public Date getMakedate() {
		return this.makedate;
	}

	public void setMakedate(Date value) {
		this.makedate = value;
	}


	public Date getBussdate() {
		return this.bussdate;
	}

	public void setBussdate(Date value) {
		this.bussdate = value;
	}


	public String getDatasrc() {
		return this.datasrc;
	}

	public void setDatasrc(String value) {
		this.datasrc = value;
	}


	public String getOthersign() {
		return this.othersign;
	}

	public void setOthersign(String value) {
		this.othersign = value;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String value) {
		this.remark = value;
	}


	public String getBxjgdm() {
		return this.bxjgdm;
	}

	public void setBxjgdm(String value) {
		this.bxjgdm = value;
	}


	public String getBxjgmc() {
		return this.bxjgmc;
	}

	public void setBxjgmc(String value) {
		this.bxjgmc = value;
	}


	public String getGlfdm() {
		return this.glfdm;
	}

	public void setGlfdm(String value) {
		this.glfdm = value;
	}


	public String getGlfmc() {
		return this.glfmc;
	}

	public void setGlfmc(String value) {
		this.glfmc = value;
	}


	public String getJnwxz() {
		return this.jnwxz;
	}

	public void setJnwxz(String value) {
		this.jnwxz = value;
	}


	public String getGlflx() {
		return this.glflx;
	}

	public void setGlflx(String value) {
		this.glflx = value;
	}


	public String getZjlx() {
		return this.zjlx;
	}

	public void setZjlx(String value) {
		this.zjlx = value;
	}


	public String getZjlxzwmc() {
		return this.zjlxzwmc;
	}

	public void setZjlxzwmc(String value) {
		this.zjlxzwmc = value;
	}


	public String getGlfzjhm() {
		return this.glfzjhm;
	}

	public void setGlfzjhm(String value) {
		this.glfzjhm = value;
	}


	public String getJjxzhlx() {
		return this.jjxzhlx;
	}

	public void setJjxzhlx(String value) {
		this.jjxzhlx = value;
	}


	public String getZyywhjyfw() {
		return this.zyywhjyfw;
	}

	public void setZyywhjyfw(String value) {
		this.zyywhjyfw = value;
	}


	public String getFddbrxm() {
		return this.fddbrxm;
	}

	public void setFddbrxm(String value) {
		this.fddbrxm = value;
	}


	public String getZcd() {
		return this.zcd;
	}

	public void setZcd(String value) {
		this.zcd = value;
	}


	public java.math.BigDecimal getZczb() {
		return this.zczb;
	}

	public void setZczb(java.math.BigDecimal value) {
		this.zczb = value;
	}


	public String getZczbbz() {
		return this.zczbbz;
	}

	public void setZczbbz(String value) {
		this.zczbbz = value;
	}


	public Long getYxbz() {
		return this.yxbz;
	}

	public void setYxbz(Long value) {
		this.yxbz = value;
	}


	public Date getCjrq() {
		return this.cjrq;
	}

	public void setCjrq(Date value) {
		this.cjrq = value;
	}


	public String getJgxqdm() {
		return this.jgxqdm;
	}

	public void setJgxqdm(String value) {
		this.jgxqdm = value;
	}


	public String toString() {
		return new StringBuilder("midGlfxxb[\n")
						.append("serialno = ").append(serialno).append(", \n")
						.append("batchno = ").append(batchno).append(", \n")
						.append("bussno = ").append(bussno).append(", \n")
						.append("makedate = ").append(makedate).append(", \n")
						.append("bussdate = ").append(bussdate).append(", \n")
						.append("datasrc = ").append(datasrc).append(", \n")
						.append("othersign = ").append(othersign).append(", \n")
						.append("remark = ").append(remark).append(", \n")
						.append("bxjgdm = ").append(bxjgdm).append(", \n")
						.append("bxjgmc = ").append(bxjgmc).append(", \n")
						.append("glfdm = ").append(glfdm).append(", \n")
						.append("glfmc = ").append(glfmc).append(", \n")
						.append("jnwxz = ").append(jnwxz).append(", \n")
						.append("glflx = ").append(glflx).append(", \n")
						.append("zjlx = ").append(zjlx).append(", \n")
						.append("zjlxzwmc = ").append(zjlxzwmc).append(", \n")
						.append("glfzjhm = ").append(glfzjhm).append(", \n")
						.append("jjxzhlx = ").append(jjxzhlx).append(", \n")
						.append("zyywhjyfw = ").append(zyywhjyfw).append(", \n")
						.append("fddbrxm = ").append(fddbrxm).append(", \n")
						.append("zcd = ").append(zcd).append(", \n")
						.append("zczb = ").append(zczb).append(", \n")
						.append("zczbbz = ").append(zczbbz).append(", \n")
						.append("yxbz = ").append(yxbz).append(", \n")
						.append("cjrq = ").append(cjrq).append(", \n")
						.append("jgxqdm = ").append(jgxqdm).append(", \n")
						.append("]")
						.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getSerialno())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof MidGlfxxb2 == false) return false;
		if(this == obj) return true;
		MidGlfxxb2 other = (MidGlfxxb2)obj;
		return new EqualsBuilder()
			.append(getSerialno(),other.getSerialno())
			.isEquals();
	}
}

