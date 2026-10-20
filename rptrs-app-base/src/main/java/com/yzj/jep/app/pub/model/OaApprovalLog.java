/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年12月25日 15:19:35
 *
 */

package com.yzj.jep.app.pub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

@Entity
@Table(name = "oa_approval_log")
public class OaApprovalLog extends BasePO{
	
	public static final String TABLE_ALIAS = "OA系统审批意见表";
	public static final String ALIAS_PID = "自增主键";
	public static final String ALIAS_APPROVAL_ID = "审批ID";
	public static final String ALIAS_OPINION_SERIALNO = "意见序号";
	public static final String ALIAS_OPINION_ID = "意见ID";
	public static final String ALIAS_OPINION_INFO = "意见详细信息";
	public static final String ALIAS_OPINION_USER = "意见填写人";
	public static final String ALIAS_OPINION_TIME = "意见填写日期";
	

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "PID", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "APPROVAL_ID_", nullable = true, length = 64)
	private String approvalId;

	@Column(name = "OPINION_SERIALNO_", nullable = true, length = 20)
	private String opinionSerialno;

	@Column(name = "OPINION_ID_", nullable = true, length = 100)
	private String opinionId;

	@Column(name = "OPINION_INFO_", nullable = true, length = 1024)
	private String opinionInfo;

	@Column(name = "OPINION_USER_", nullable = true, length = 100)
	private String opinionUser;

	@Column(name = "OPINION_TIME_", nullable = true, length = 100)
	private String opinionTime;
	//columns END


	public Integer getPid() {
		return this.pid;
	}
	
	public void setPid(Integer value) {
		this.pid = value;
	}
	

	public String getApprovalId() {
		return this.approvalId;
	}
	
	public void setApprovalId(String value) {
		this.approvalId = value;
	}
	

	public String getOpinionSerialno() {
		return this.opinionSerialno;
	}
	
	public void setOpinionSerialno(String value) {
		this.opinionSerialno = value;
	}
	

	public String getOpinionId() {
		return this.opinionId;
	}
	
	public void setOpinionId(String value) {
		this.opinionId = value;
	}
	

	public String getOpinionInfo() {
		return this.opinionInfo;
	}
	
	public void setOpinionInfo(String value) {
		this.opinionInfo = value;
	}
	

	public String getOpinionUser() {
		return this.opinionUser;
	}
	
	public void setOpinionUser(String value) {
		this.opinionUser = value;
	}
	

	public String getOpinionTime() {
		return this.opinionTime;
	}
	
	public void setOpinionTime(String value) {
		this.opinionTime = value;
	}
	

	public String toString() {
		return new StringBuilder("oaApprovalLog[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("approvalId = ").append(approvalId).append(", \n")
						.append("opinionSerialno = ").append(opinionSerialno).append(", \n")
						.append("opinionId = ").append(opinionId).append(", \n")
						.append("opinionInfo = ").append(opinionInfo).append(", \n")
						.append("opinionUser = ").append(opinionUser).append(", \n")
						.append("opinionTime = ").append(opinionTime).append(", \n")
						.append("]")
						.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof OaApprovalLog == false) return false;
		if(this == obj) return true;
		OaApprovalLog other = (OaApprovalLog)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

