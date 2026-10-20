/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2025 pactera.com. All rights reserved.
 *
 * Generated at 2025年01月23日 15:42:07
 *
 */

package com.yzj.jep.app.sub.model;

import cn.hutool.core.date.DateUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "mid_glgxb_2")
public class MidGlgxb2 extends BasePO {

	public static final String TABLE_ALIAS = "MidGlgxb2";
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
	public static final String ALIAS_GLFMCGLFZJJDMC = "关联方名称/关联方中间节点名称";
	public static final String ALIAS_GLFDMGLFZJJDDM = "关联方代码/关联方中间节点代码";
	public static final String ALIAS_GLFGLFZJJDLX = "关联方/关联方中间节点类型";
	public static final String ALIAS_QYCGLFMCGLFZJJDMC = "前一层关联方名称/关联方中间节点名称";
	public static final String ALIAS_QYCGLFDMGLFZJJDDM = "前一层关联方代码/关联方中间节点代码";
	public static final String ALIAS_QYCGLFGLFZJJDLX = "前一层关联方/关联方中间节点类型";
	public static final String ALIAS_YQYCDGLGX = "与前一层的关联关系";
	public static final String ALIAS_YQYCZJSFYQYFE = "与前一层之间是否有权益份额";
	public static final String ALIAS_CYBL = "持有比例";
	public static final String ALIAS_ZYGQBL = "质押股权比例";
	public static final String ALIAS_GLGXSM = "关联关系说明";
	public static final String ALIAS_GXYXBZ = "关系有效标志";
	public static final String ALIAS_SXRQ1 = "生效日期";
	public static final String ALIAS_SXRQ2 = "失效日期";
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

	@Column(name = "GLFMCGLFZJJDMC", nullable = true, length = 600)
	private String glfmcglfzjjdmc;

	@Column(name = "GLFDMGLFZJJDDM", nullable = true, length = 600)
	private String glfdmglfzjjddm;

	@Column(name = "GLFGLFZJJDLX", nullable = true, length = 60)
	private String glfglfzjjdlx;

	@Column(name = "QYCGLFMCGLFZJJDMC", nullable = true, length = 600)
	private String qycglfmcglfzjjdmc;

	@Column(name = "QYCGLFDMGLFZJJDDM", nullable = true, length = 600)
	private String qycglfdmglfzjjddm;

	@Column(name = "QYCGLFGLFZJJDLX", nullable = true, length = 60)
	private String qycglfglfzjjdlx;

	@Column(name = "YQYCDGLGX", nullable = true, length = 150)
	private String yqycdglgx;

	@Column(name = "YQYCZJSFYQYFE", nullable = true, length = 60)
	private String yqyczjsfyqyfe;

	@Column(name = "CYBL", nullable = true, precision = 22, scale = 0)
	private java.math.BigDecimal cybl;

	@Column(name = "ZYGQBL", nullable = true, precision = 22, scale = 0)
	private java.math.BigDecimal zygqbl;

	@Column(name = "GLGXSM", nullable = true, length = 4000)
	private String glgxsm;

	@Column(name = "GXYXBZ", nullable = true, length = 3)
	private Long gxyxbz;

	@Column(name = "SXRQ1", nullable = true, length = 10)
	@Temporal(TemporalType.DATE)
	private Date sxrq1;

	@Column(name = "SXRQ2", nullable = true, length = 10)
	@Temporal(TemporalType.DATE)
	private Date sxrq2;

	@Column(name = "CJRQ", nullable = true, length = 10)
	@Temporal(TemporalType.DATE)
	private Date cjrq;

	@Column(name = "JGXQDM", nullable = true, length = 300)
	private String jgxqdm;
	//columns END


	@Transient
	private String makedateStr;

	@Transient
	private String bussdateStr;

	@Transient
	private String cjrqStr;

	@Transient
	private String sxrq1Str;

	public String getSxrq1Str() {
		if (sxrq1 != null) {
			return DateUtil.format(this.sxrq1, "yyyy-MM-dd");
		}
		return "";
	}

	public void setSxrq1Str(String sxrq1Str) {
		if (sxrq1Str != null) {
			setSxrq1(DateUtils.parseDate(sxrq1Str));
		}
	}

	public String getSxrq2Str() {
		if (sxrq2 != null) {
			return DateUtil.format(this.sxrq2, "yyyy-MM-dd");
		}
		return "";
	}

	public void setSxrq2Str(String sxrq2Str) {
		if (sxrq2Str != null) {
			setSxrq2(DateUtils.parseDate(sxrq2Str));
		}
	}

	@Transient
	private String sxrq2Str;

	public String getMakedateStr() {
		if (makedate != null) {
			return DateUtil.format(this.makedate, "yyyy-MM-dd");
		}
		return "";
	}

	public void setMakedateStr(String makedateStr) {
		if (makedateStr != null) {
			setMakedate(DateUtils.parseDate(makedateStr));
		}
	}


	public String getBussdateStr() {
		if (bussdate != null) {
			return DateUtil.format(this.bussdate, "yyyy-MM-dd");
		}
		return "";
	}

	public void setBussdateStr(String bussdateStr) {
		if (bussdateStr != null) {
			setBussdate(DateUtils.parseDate(bussdateStr));
		}
	}

	public String getCjrqStr() {
		if (cjrq != null) {
			return DateUtil.format(this.cjrq, "yyyy-MM-dd");
		}
		return "";
	}

	public void setCjrqStr(String cjrqStr) {
		if (cjrqStr != null) {
			setCjrq(DateUtils.parseDate(cjrqStr));
		}
	}


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


	public String getGlfmcglfzjjdmc() {
		return this.glfmcglfzjjdmc;
	}

	public void setGlfmcglfzjjdmc(String value) {
		this.glfmcglfzjjdmc = value;
	}


	public String getGlfdmglfzjjddm() {
		return this.glfdmglfzjjddm;
	}

	public void setGlfdmglfzjjddm(String value) {
		this.glfdmglfzjjddm = value;
	}


	public String getGlfglfzjjdlx() {
		return this.glfglfzjjdlx;
	}

	public void setGlfglfzjjdlx(String value) {
		this.glfglfzjjdlx = value;
	}


	public String getQycglfmcglfzjjdmc() {
		return this.qycglfmcglfzjjdmc;
	}

	public void setQycglfmcglfzjjdmc(String value) {
		this.qycglfmcglfzjjdmc = value;
	}


	public String getQycglfdmglfzjjddm() {
		return this.qycglfdmglfzjjddm;
	}

	public void setQycglfdmglfzjjddm(String value) {
		this.qycglfdmglfzjjddm = value;
	}


	public String getQycglfglfzjjdlx() {
		return this.qycglfglfzjjdlx;
	}

	public void setQycglfglfzjjdlx(String value) {
		this.qycglfglfzjjdlx = value;
	}


	public String getYqycdglgx() {
		return this.yqycdglgx;
	}

	public void setYqycdglgx(String value) {
		this.yqycdglgx = value;
	}


	public String getYqyczjsfyqyfe() {
		return this.yqyczjsfyqyfe;
	}

	public void setYqyczjsfyqyfe(String value) {
		this.yqyczjsfyqyfe = value;
	}


	public java.math.BigDecimal getCybl() {
		return this.cybl;
	}

	public void setCybl(java.math.BigDecimal value) {
		this.cybl = value;
	}


	public java.math.BigDecimal getZygqbl() {
		return this.zygqbl;
	}

	public void setZygqbl(java.math.BigDecimal value) {
		this.zygqbl = value;
	}


	public String getGlgxsm() {
		return this.glgxsm;
	}

	public void setGlgxsm(String value) {
		this.glgxsm = value;
	}


	public Long getGxyxbz() {
		return this.gxyxbz;
	}

	public void setGxyxbz(Long value) {
		this.gxyxbz = value;
	}


	public Date getSxrq1() {
		return this.sxrq1;
	}

	public void setSxrq1(Date value) {
		this.sxrq1 = value;
	}


	public Date getSxrq2() {
		return this.sxrq2;
	}

	public void setSxrq2(Date value) {
		this.sxrq2 = value;
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
		return new StringBuilder("glgxb2[\n")
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
						.append("glfmcglfzjjdmc = ").append(glfmcglfzjjdmc).append(", \n")
						.append("glfdmglfzjjddm = ").append(glfdmglfzjjddm).append(", \n")
						.append("glfglfzjjdlx = ").append(glfglfzjjdlx).append(", \n")
						.append("qycglfmcglfzjjdmc = ").append(qycglfmcglfzjjdmc).append(", \n")
						.append("qycglfdmglfzjjddm = ").append(qycglfdmglfzjjddm).append(", \n")
						.append("qycglfglfzjjdlx = ").append(qycglfglfzjjdlx).append(", \n")
						.append("yqycdglgx = ").append(yqycdglgx).append(", \n")
						.append("yqyczjsfyqyfe = ").append(yqyczjsfyqyfe).append(", \n")
						.append("cybl = ").append(cybl).append(", \n")
						.append("zygqbl = ").append(zygqbl).append(", \n")
						.append("glgxsm = ").append(glgxsm).append(", \n")
						.append("gxyxbz = ").append(gxyxbz).append(", \n")
						.append("sxrq1 = ").append(sxrq1).append(", \n")
						.append("sxrq2 = ").append(sxrq2).append(", \n")
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
		if(obj instanceof MidGlgxb2 == false) return false;
		if(this == obj) return true;
		MidGlgxb2 other = (MidGlgxb2)obj;
		return new EqualsBuilder()
			.append(getSerialno(),other.getSerialno())
			.isEquals();
	}
}

