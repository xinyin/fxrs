
package com.yzj.jep.app.bus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sys_auth_apply")
public class AuthApply extends BasePO {
	
	public static final String TABLE_ALIAS = "权限申请实例表";
	public static final String ALIAS_ID = "pid";
	public static final String ALIAS_APPLICANT = "applicant";
	public static final String ALIAS_DEPARTMENT = "department";
	public static final String ALIAS_POST = "post";
	public static final String ALIAS_ROLE = "role";
	public static final String ALIAS_STATUS = "status";
	public static final String ALIAS_CREATE_TIME = "createTime";

	@Id
	@Column(name = "pid", nullable = false, length = 11)
	private Integer pid;

	@Column(name = "APPLICANT", nullable = true, length = 100)
	private String applicant;

	@Column(name = "DEPARTMENT", nullable = false, length = 100)
	private String department;

	@Column(name = "CREATED_TIME_")
	@Temporal(TemporalType.DATE)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date createTime;

	@Column(name = "STATUS", nullable = true, length = 2)
	private String status;

	@Column(name = "POST", nullable = true, length = 100)
	private String post;

	@Column(name = "ROLE", nullable = true, length = 100)
	private String role;


	@Column(name = "REMARK", nullable = true, length = 1000)
	private String  remark;


	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return new StringBuilder("AuthApply[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("applicant = ").append(applicant).append(", \n")
						.append("department = ").append(department).append(", \n")
						.append("post = ").append(post).append(", \n")
						.append("role = ").append(role).append(", \n")
						.append("status = ").append(status).append(", \n")
						.append("remark = ").append(remark).append(", \n")
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
		if(obj instanceof AuthApply == false) {return false;}
		if(this == obj) {return true;}
		AuthApply other = (AuthApply)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

