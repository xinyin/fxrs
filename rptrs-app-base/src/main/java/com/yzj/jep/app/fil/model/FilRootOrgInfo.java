/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年08月03日 17:04:30
 *
 */

package com.yzj.jep.app.fil.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "fil_root_org_info")
public class FilRootOrgInfo extends BasePO {
	
	public static final String TABLE_ALIAS = "备案基础信息表";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_FIL_NUM = "备案编号";
	public static final String ALIAS_REL_ORG_ID = "关联机构主键";
	public static final String ALIAS_TOTAL_SHARE_AMOUNT = "总股数";
	public static final String ALIAS_NET_CAPTIAL = "资本净额";
	public static final String ALIAS_TOTAL_CAPITAL = "总资产";
	public static final String ALIAS_FROZEN_SHARE_AMOUNT = "冻结股数(股)";
	public static final String ALIAS_PLEDGED_SHARE_AMOUNT = "质押股数(股)";
	public static final String ALIAS_END_LAST_QUARTER = "上季末总资产";
	public static final String ALIAS_END_LAST_YEAR = "上年末总资产";
	public static final String ALIAS_NET_CAPTIAL_LAST_YEAR = "上年末净资产";
	public static final String ALIAS_NET_CAPTIAL_LAST_QUARTER = "上季末净资产";
	public static final String ALIAS_QUARTER_COMPRE_SOLVENCY_RATE = "季度综合偿付能力充足率";
	public static final String ALIAS_YEARS = "年度";
	public static final String ALIAS_QUARTER = "季度";
	public static final String ALIAS_APPROVAL_STATUS = "审批状态";
	

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "fil_num", nullable = true, length = 64)
	private String filNum;

	@Column(name = "rel_org_id", nullable = false, length = 10)
	private Integer relOrgId;

	@Column(name = "total_share_amount", nullable = true, length = 100)
	private String totalShareAmount;

	@Column(name = "net_captial", nullable = true, precision = 20, scale = 0)
	private java.math.BigDecimal netCaptial;

	@Column(name = "total_capital", nullable = true, precision = 50, scale = 0)
	private java.math.BigDecimal totalCapital;

	@Column(name = "frozen_share_amount", nullable = true, length = 50)
	private String frozenShareAmount;

	@Column(name = "pledged_share_amount", nullable = true, length = 50)
	private String pledgedShareAmount;

	@Column(name = "end_last_quarter", nullable = true, length = 100)
	private String endLastQuarter;

	@Column(name = "end_last_year", nullable = true, length = 100)
	private String endLastYear;

	@Column(name = "net_captial_last_year", nullable = true, length = 50)
	private String netCaptialLastYear;

	@Column(name = "net_captial_last_quarter", nullable = true, length = 50)
	private String netCaptialLastQuarter;

	@Column(name = "quarter_compre_solvency_rate", nullable = true, length = 50)
	private String quarterCompreSolvencyRate;

	@Column(name = "years", nullable = true, length = 10)
	private Integer years;

	@Column(name = "quarter", nullable = true, length = 10)
	private Integer quarter;

	@Column(name = "approval_status", nullable = true, length = 2)
	private String approvalStatus;
	//columns END


	public Integer getPid() {
		return this.pid;
	}
	
	public void setPid(Integer value) {
		this.pid = value;
	}
	

	public String getFilNum() {
		return this.filNum;
	}
	
	public void setFilNum(String value) {
		this.filNum = value;
	}
	

	public Integer getRelOrgId() {
		return this.relOrgId;
	}
	
	public void setRelOrgId(Integer value) {
		this.relOrgId = value;
	}
	

	public String getTotalShareAmount() {
		return this.totalShareAmount;
	}
	
	public void setTotalShareAmount(String value) {
		this.totalShareAmount = value;
	}
	

	public java.math.BigDecimal getNetCaptial() {
		return this.netCaptial;
	}
	
	public void setNetCaptial(java.math.BigDecimal value) {
		this.netCaptial = value;
	}
	

	public java.math.BigDecimal getTotalCapital() {
		return this.totalCapital;
	}
	
	public void setTotalCapital(java.math.BigDecimal value) {
		this.totalCapital = value;
	}
	

	public String getFrozenShareAmount() {
		return this.frozenShareAmount;
	}
	
	public void setFrozenShareAmount(String value) {
		this.frozenShareAmount = value;
	}
	

	public String getPledgedShareAmount() {
		return this.pledgedShareAmount;
	}
	
	public void setPledgedShareAmount(String value) {
		this.pledgedShareAmount = value;
	}
	

	public String getEndLastQuarter() {
		return this.endLastQuarter;
	}
	
	public void setEndLastQuarter(String value) {
		this.endLastQuarter = value;
	}
	

	public String getEndLastYear() {
		return this.endLastYear;
	}
	
	public void setEndLastYear(String value) {
		this.endLastYear = value;
	}
	

	public String getNetCaptialLastYear() {
		return this.netCaptialLastYear;
	}
	
	public void setNetCaptialLastYear(String value) {
		this.netCaptialLastYear = value;
	}
	

	public String getNetCaptialLastQuarter() {
		return this.netCaptialLastQuarter;
	}
	
	public void setNetCaptialLastQuarter(String value) {
		this.netCaptialLastQuarter = value;
	}
	

	public String getQuarterCompreSolvencyRate() {
		return this.quarterCompreSolvencyRate;
	}
	
	public void setQuarterCompreSolvencyRate(String value) {
		this.quarterCompreSolvencyRate = value;
	}
	

	public Integer getYears() {
		return this.years;
	}
	
	public void setYears(Integer value) {
		this.years = value;
	}
	

	public Integer getQuarter() {
		return this.quarter;
	}
	
	public void setQuarter(Integer value) {
		this.quarter = value;
	}
	

	public String getApprovalStatus() {
		return this.approvalStatus;
	}
	
	public void setApprovalStatus(String value) {
		this.approvalStatus = value;
	}
	

	public String toString() {
		return new StringBuilder("rootOrgInfo[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("filNum = ").append(filNum).append(", \n")
						.append("relOrgId = ").append(relOrgId).append(", \n")
						.append("totalShareAmount = ").append(totalShareAmount).append(", \n")
						.append("netCaptial = ").append(netCaptial).append(", \n")
						.append("totalCapital = ").append(totalCapital).append(", \n")
						.append("frozenShareAmount = ").append(frozenShareAmount).append(", \n")
						.append("pledgedShareAmount = ").append(pledgedShareAmount).append(", \n")
						.append("endLastQuarter = ").append(endLastQuarter).append(", \n")
						.append("endLastYear = ").append(endLastYear).append(", \n")
						.append("netCaptialLastYear = ").append(netCaptialLastYear).append(", \n")
						.append("netCaptialLastQuarter = ").append(netCaptialLastQuarter).append(", \n")
						.append("quarterCompreSolvencyRate = ").append(quarterCompreSolvencyRate).append(", \n")
						.append("years = ").append(years).append(", \n")
						.append("quarter = ").append(quarter).append(", \n")
						.append("approvalStatus = ").append(approvalStatus).append(", \n")
						.append("]")
						.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof FilRootOrgInfo == false) return false;
		if(this == obj) return true;
		FilRootOrgInfo other = (FilRootOrgInfo)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

