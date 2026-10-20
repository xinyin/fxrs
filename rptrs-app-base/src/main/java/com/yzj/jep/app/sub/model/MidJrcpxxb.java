/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月13日 15:41:26
 *
 */

package com.yzj.jep.app.sub.model;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mid_jrcpxxb_2_middle")
public class MidJrcpxxb extends BasePO {

	public static final String TABLE_ALIAS = "MidJrcpxxb";

	//columns START

	@Id
	@Column(name = "SERIALNO", nullable = false, length = 60)
	private String serialno;

	@Column(name = "BATCHNO", nullable = false, length = 60)
	private String batchno;

	@Column(name = "BUSSNO", nullable = false, length = 600)
	private String bussno;

	@Column(name = "MAKEDATE", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date makedate;

	@Transient
	private String makedateStr;

	@Column(name = "BUSSDATE", nullable = true, length = 19)
	@Temporal(TemporalType.DATE)
	private Date bussdate;

	@Transient
	private String bussdateStr;

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

	@Column(name = "JRCPBH", nullable = true, length = 600)
	private String jrcpbh;

	@Column(name = "JRCPQC", nullable = true, length = 600)
	private String jrcpqc;

	@Column(name = "CPPZZCBAH", nullable = true, length = 600)
	private String cppzzcbah;

	@Column(name = "CPDM", nullable = true, length = 600)
	private String cpdm;

	@Column(name = "CPFL", nullable = true, length = 150)
	private String cpfl;

	@Column(name = "CPDJJG", nullable = true, length = 600)
	private String cpdjjg;

	@Column(name = "CPGLRQC", nullable = true, length = 600)
	private String cpglrqc;

	@Column(name = "GLRTYSHXYDM", nullable = true, length = 300)
	private String glrtyshxydm;

	@Column(name = "CPTGRQC", nullable = true, length = 600)
	private String cptgrqc;

	@Column(name = "TGRTYSHXYDM", nullable = true, length = 300)
	private String tgrtyshxydm;

	@Column(name = "DCZC", nullable = true, length = 4000)
	private String dczc;

	@Column(name = "TZZ", nullable = true, length = 4000)
	private String tzz;

	@Column(name = "CJRQ", nullable = true, length = 10)
	@Temporal(TemporalType.DATE)
	private Date cjrq;

	@Transient
	private String cjrqStr;

	@Column(name = "JGXQDM", nullable = true, length = 300)
	private String jgxqdm;


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

	//columns END

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getBussno() {
		return bussno;
	}

	public void setBussno(String bussno) {
		this.bussno = bussno;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Date getBussdate() {
		return bussdate;
	}

	public void setBussdate(Date bussdate) {
		this.bussdate = bussdate;
	}

	public String getDatasrc() {
		return datasrc;
	}

	public void setDatasrc(String datasrc) {
		this.datasrc = datasrc;
	}

	public String getOthersign() {
		return othersign;
	}

	public void setOthersign(String othersign) {
		this.othersign = othersign;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getJrcpbh() {
		return jrcpbh;
	}

	public void setJrcpbh(String jrcpbh) {
		this.jrcpbh = jrcpbh;
	}

	public String getJrcpqc() {
		return jrcpqc;
	}

	public void setJrcpqc(String jrcpqc) {
		this.jrcpqc = jrcpqc;
	}

	public String getCppzzcbah() {
		return cppzzcbah;
	}

	public void setCppzzcbah(String cppzzcbah) {
		this.cppzzcbah = cppzzcbah;
	}

	public String getCpdm() {
		return cpdm;
	}

	public void setCpdm(String cpdm) {
		this.cpdm = cpdm;
	}

	public String getCpfl() {
		return cpfl;
	}

	public void setCpfl(String cpfl) {
		this.cpfl = cpfl;
	}

	public String getCpdjjg() {
		return cpdjjg;
	}

	public void setCpdjjg(String cpdjjg) {
		this.cpdjjg = cpdjjg;
	}

	public String getCpglrqc() {
		return cpglrqc;
	}

	public void setCpglrqc(String cpglrqc) {
		this.cpglrqc = cpglrqc;
	}

	public String getGlrtyshxydm() {
		return glrtyshxydm;
	}

	public void setGlrtyshxydm(String glrtyshxydm) {
		this.glrtyshxydm = glrtyshxydm;
	}

	public String getCptgrqc() {
		return cptgrqc;
	}

	public void setCptgrqc(String cptgrqc) {
		this.cptgrqc = cptgrqc;
	}

	public String getTgrtyshxydm() {
		return tgrtyshxydm;
	}

	public void setTgrtyshxydm(String tgrtyshxydm) {
		this.tgrtyshxydm = tgrtyshxydm;
	}

	public String getDczc() {
		return dczc;
	}

	public void setDczc(String dczc) {
		this.dczc = dczc;
	}

	public String getTzz() {
		return tzz;
	}

	public void setTzz(String tzz) {
		this.tzz = tzz;
	}

	public Date getCjrq() {
		return cjrq;
	}

	public void setCjrq(Date cjrq) {
		this.cjrq = cjrq;
	}

	public String getJgxqdm() {
		return jgxqdm;
	}

	public void setJgxqdm(String jgxqdm) {
		this.jgxqdm = jgxqdm;
	}
}

