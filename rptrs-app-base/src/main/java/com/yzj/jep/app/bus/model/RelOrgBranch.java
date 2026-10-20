/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年07月13日 13:55:31
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "bus_rel_org_branch")
public class RelOrgBranch extends BasePO {
	
	public static final String TABLE_ALIAS = "关联关系机构分公司表";
	public static final String ALIAS_PID = "自增主键";
	public static final String ALIAS_PARENT_IDENTIFY_TYPE = "上级机构证件类型";
	public static final String ALIAS_PARENT_UNISC_CODE = "上级机构证件号码";
	public static final String ALIAS_ORG_NAME = "企业名称";
	public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内外标识(1-境内，2-境外)";
	public static final String ALIAS_IDENTIFY_TYPE = "证件类型";
	public static final String ALIAS_UNISC_CODE = "社会统一信用代码";
	public static final String ALIAS_STATUS = "机构状态（1-正常，2-停业）";
	public static final String ALIAS_VALID_STATUS = "是否有效 0-无效，1-有效";
	public static final String ALIAS_AMEND_ID = "操作人id";
	public static final String ALIAS_AMEND_NAME = "操作人";
	

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "parent_identify_type", nullable = true, length = 64)
	private String parentIdentifyType;

	@Column(name = "parent_unisc_code", nullable = true, length = 64)
	private String parentUniscCode;

	@Column(name = "org_name", nullable = true, length = 500)
	private String orgName;

	@Column(name = "domestic_or_foreign", nullable = true, length = 10)
	private String domesticOrForeign;

	@Column(name = "identify_type", nullable = true, length = 64)
	private String identifyType;

	@Column(name = "unisc_code", nullable = true, length = 64)
	private String uniscCode;

	@Column(name = "status", nullable = true, length = 10)
	private String status;

	@Column(name = "valid_status", nullable = true, length = 10)
	private String validStatus;

	@Column(name = "AMEND_ID", nullable = true, length = 50)
	private String amendId;

	@Column(name = "AMEND_NAME", nullable = true, length = 50)
	private String amendName;
	//columns END


	public Integer getPid() {
		return this.pid;
	}
	
	public void setPid(Integer value) {
		this.pid = value;
	}
	

	public String getParentIdentifyType() {
		return this.parentIdentifyType;
	}
	
	public void setParentIdentifyType(String value) {
		this.parentIdentifyType = value;
	}
	

	public String getParentUniscCode() {
		return this.parentUniscCode;
	}
	
	public void setParentUniscCode(String value) {
		this.parentUniscCode = value;
	}
	

	public String getOrgName() {
		return this.orgName;
	}
	
	public void setOrgName(String value) {
		this.orgName = value;
	}
	

	public String getDomesticOrForeign() {
		return this.domesticOrForeign;
	}
	
	public void setDomesticOrForeign(String value) {
		this.domesticOrForeign = value;
	}
	

	public String getIdentifyType() {
		return this.identifyType;
	}
	
	public void setIdentifyType(String value) {
		this.identifyType = value;
	}
	

	public String getUniscCode() {
		return this.uniscCode;
	}
	
	public void setUniscCode(String value) {
		this.uniscCode = value;
	}
	

	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String value) {
		this.status = value;
	}
	

	public String getValidStatus() {
		return this.validStatus;
	}
	
	public void setValidStatus(String value) {
		this.validStatus = value;
	}
	

	public String getAmendId() {
		return this.amendId;
	}
	
	public void setAmendId(String value) {
		this.amendId = value;
	}
	

	public String getAmendName() {
		return this.amendName;
	}
	
	public void setAmendName(String value) {
		this.amendName = value;
	}
	

	public String toString() {
		return new StringBuilder("relOrgBranch[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("parentIdentifyType = ").append(parentIdentifyType).append(", \n")
						.append("parentUniscCode = ").append(parentUniscCode).append(", \n")
						.append("orgName = ").append(orgName).append(", \n")
						.append("domesticOrForeign = ").append(domesticOrForeign).append(", \n")
						.append("identifyType = ").append(identifyType).append(", \n")
						.append("uniscCode = ").append(uniscCode).append(", \n")
						.append("status = ").append(status).append(", \n")
						.append("validStatus = ").append(validStatus).append(", \n")
						.append("amendId = ").append(amendId).append(", \n")
						.append("amendName = ").append(amendName).append(", \n")
						.append("]")
						.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RelOrgBranch == false) return false;
		if(this == obj) return true;
		RelOrgBranch other = (RelOrgBranch)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

