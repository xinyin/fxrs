/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月18日 16:19:11
 *
 */

package com.yzj.jep.app.sys.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;


@Entity
@Table(name = "sys_approve_map")
public class ApproveMap extends BasePO {
	
	public static final String TABLE_ALIAS = "InsOtherView";
	public static final String ALIAS_PID = "主键";



	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "PID", nullable = false, length = 10)
	private Integer pid;

	/**
	 * 用户编码
	 */
	private String staffCode_;
	/**
	 * 用户名称
	 */
	private String staffName_;
	/**
	 * 所属部门
	 */
	private String deptCode_;
	/**
	 * 所属部门
	 */
	private String deptName_;
	/**
	 * 角色编码
	 */
	private String roleCode_;
	/**
	 * 角色名称
	 */
	private String roleName_;
	/**
	 * 标准角色编码
	 */
	private String roleStdCode_;



	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getStaffCode_() {
		return staffCode_;
	}

	public void setStaffCode_(String staffCode_) {
		this.staffCode_ = staffCode_;
	}

	public String getStaffName_() {
		return staffName_;
	}

	public void setStaffName_(String staffName_) {
		this.staffName_ = staffName_;
	}

	public String getDeptCode_() {
		return deptCode_;
	}

	public void setDeptCode_(String deptCode_) {
		this.deptCode_ = deptCode_;
	}

	public String getDeptName_() {
		return deptName_;
	}

	public void setDeptName_(String deptName_) {
		this.deptName_ = deptName_;
	}

	public String getRoleCode_() {
		return roleCode_;
	}

	public void setRoleCode_(String roleCode_) {
		this.roleCode_ = roleCode_;
	}

	public String getRoleName_() {
		return roleName_;
	}

	public void setRoleName_(String roleName_) {
		this.roleName_ = roleName_;
	}

	public String getRoleStdCode_() {
		return roleStdCode_;
	}

	public void setRoleStdCode_(String roleStdCode_) {
		this.roleStdCode_ = roleStdCode_;
	}

	@Override
	public String toString() {
		return new StringBuilder("templateDetails[\n")
				.append("pid = ").append(pid).append(", \n")
				.append("]")
				.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getPid())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ApproveMap == false) {return false;}
		if (this == obj) {return true;}
		ApproveMap other = (ApproveMap) obj;
		return new EqualsBuilder()
				.append(getPid(), other.getPid())
				.isEquals();
	}
}

