/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年07月14日 10:43:08
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;


@Entity
@Table(name = "sys_entrust_apply")
public class EntrustApply extends BasePO {
	
	public static final String TABLE_ALIAS = "EntrustApply";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_STAFF_CODE = "用户编码";
	public static final String ALIAS_STAFF_NAME = "用户名称";
	public static final String ALIAS_TRANSFER_STAFF_CODE = "转办用户编码";
	public static final String ALIAS_TRANSFER_STAFF_NAME = "转办用户名称";
	public static final String ALIAS_START_TIME = "开始时间";
	public static final String ALIAS_END_TIME = "结束时间";
	public static final String ALIAS_TRANSFER_STAFF_NAME_NEW = "新被委托人编码";
	public static final String ALIAS_TRANSFER_STAFF_CODE_NEW = "新被委托人名称";
	public static final String ALIAS_ENTRUST_TYPE = "委托类型";
	public static final String ALIAS_TASK_NUMBER = "任务编号";
	public static final String ALIAS_STAFF_DEP = "委托人部门";
	public static final String ALIAS_TRANSFER_STAFF_DEP = "被委托人部门";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_PROCESS_DEFINITION_ID = "processDefinitionId";
	public static final String ALIAS_REMARK = "remark";
	

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "STAFF_CODE_", nullable = true, length = 64)
	private String staffCode;

	@Column(name = "STAFF_NAME_", nullable = true, length = 128)
	private String staffName;

	@Column(name = "TRANSFER_STAFF_CODE_", nullable = true, length = 64)
	private String transferStaffCode;

	@Column(name = "TRANSFER_STAFF_NAME_", nullable = true, length = 128)
	private String transferStaffName;

	@Column(name = "START_TIME_", nullable = true, length = 10)
	@Temporal(TemporalType.DATE)
	private Date startTime;

	@Column(name = "END_TIME_", nullable = true, length = 10)
	@Temporal(TemporalType.DATE)
	private Date endTime;

	@Column(name = "TRANSFER_STAFF_NAME_NEW", nullable = true, length = 64)
	private String transferStaffNameNew;

	@Column(name = "TRANSFER_STAFF_CODE_NEW", nullable = true, length = 128)
	private String transferStaffCodeNew;

	@Column(name = "ENTRUST_TYPE", nullable = true, length = 2)
	private String entrustType;

	@Column(name = "TASK_NUMBER", nullable = true, length = 64)
	private String taskNumber;

	@Column(name = "STAFF_DEP", nullable = true, length = 64)
	private String staffDep;

	@Column(name = "TRANSFER_STAFF_DEP", nullable = true, length = 64)
	private String transferStaffDep;

	@Column(name = "TRANSFER_STAFF_DEP_NEW", nullable = true, length = 64)
	private String transferStaffDepNew;

	@Column(name = "STATUS", nullable = true, length = 10)
	private Integer status;

	@Column(name = "PROCESS_DEFINITION_ID", nullable = true, length = 100)
	private String processDefinitionId;

	@Column(name = "REMARK", nullable = true, length = 1000)
	private String remark;
	//columns END


	public String getTransferStaffDepNew() {
		return transferStaffDepNew;
	}

	public void setTransferStaffDepNew(String transferStaffDepNew) {
		this.transferStaffDepNew = transferStaffDepNew;
	}

	public Integer getPid() {
		return this.pid;
	}
	
	public void setPid(Integer value) {
		this.pid = value;
	}
	

	public String getStaffCode() {
		return this.staffCode;
	}
	
	public void setStaffCode(String value) {
		this.staffCode = value;
	}
	

	public String getStaffName() {
		return this.staffName;
	}
	
	public void setStaffName(String value) {
		this.staffName = value;
	}
	

	public String getTransferStaffCode() {
		return this.transferStaffCode;
	}
	
	public void setTransferStaffCode(String value) {
		this.transferStaffCode = value;
	}
	

	public String getTransferStaffName() {
		return this.transferStaffName;
	}
	
	public void setTransferStaffName(String value) {
		this.transferStaffName = value;
	}
	

	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date value) {
		this.startTime = value;
	}
	

	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date value) {
		this.endTime = value;
	}
	

	public String getTransferStaffNameNew() {
		return this.transferStaffNameNew;
	}
	
	public void setTransferStaffNameNew(String value) {
		this.transferStaffNameNew = value;
	}
	

	public String getTransferStaffCodeNew() {
		return this.transferStaffCodeNew;
	}
	
	public void setTransferStaffCodeNew(String value) {
		this.transferStaffCodeNew = value;
	}
	

	public String getEntrustType() {
		return this.entrustType;
	}
	
	public void setEntrustType(String value) {
		this.entrustType = value;
	}
	

	public String getTaskNumber() {
		return this.taskNumber;
	}
	
	public void setTaskNumber(String value) {
		this.taskNumber = value;
	}
	

	public String getStaffDep() {
		return this.staffDep;
	}
	
	public void setStaffDep(String value) {
		this.staffDep = value;
	}
	

	public String getTransferStaffDep() {
		return this.transferStaffDep;
	}
	
	public void setTransferStaffDep(String value) {
		this.transferStaffDep = value;
	}
	

	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	

	public String getProcessDefinitionId() {
		return this.processDefinitionId;
	}
	
	public void setProcessDefinitionId(String value) {
		this.processDefinitionId = value;
	}
	

	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String value) {
		this.remark = value;
	}
	@Transient
	public String getStartTimeString() {
		return DateUtils.format(getStartTime(), DateUtils.DATE_FORMAT);
	}
	public void setStartTimeString(String value) {
		setStartTime(DateUtils.parse(value, DateUtils.DATE_FORMAT));
	}

	@Transient
	public String getEndTimeString() {
		return DateUtils.format(getEndTime(), DateUtils.DATE_FORMAT);
	}
	public void setEndTimeString(String value) {
		setEndTime(DateUtils.parse(value, DateUtils.DATE_FORMAT));
	}

	@Override
	@Transient
	public String getCreateTimeString(){
		return DateUtils.format(getCreateTime(), DateUtils.DATE_TIME_FORMAT);
	}
	@Override
	public void setCreateTimeString(String value) {
		setCreateTime(DateUtils.parse(value, DateUtils.DATE_TIME_FORMAT));
	}


	@Override
	public String toString() {
		return new StringBuilder("entrustApply[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("staffCode = ").append(staffCode).append(", \n")
						.append("staffName = ").append(staffName).append(", \n")
						.append("transferStaffCode = ").append(transferStaffCode).append(", \n")
						.append("transferStaffName = ").append(transferStaffName).append(", \n")
						.append("startTime = ").append(startTime).append(", \n")
						.append("endTime = ").append(endTime).append(", \n")
						.append("transferStaffNameNew = ").append(transferStaffNameNew).append(", \n")
						.append("transferStaffCodeNew = ").append(transferStaffCodeNew).append(", \n")
						.append("entrustType = ").append(entrustType).append(", \n")
						.append("taskNumber = ").append(taskNumber).append(", \n")
						.append("staffDep = ").append(staffDep).append(", \n")
						.append("transferStaffDep = ").append(transferStaffDep).append(", \n")
						.append("status = ").append(status).append(", \n")
						.append("processDefinitionId = ").append(processDefinitionId).append(", \n")
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
		if(obj instanceof EntrustApply == false) return false;
		if(this == obj) return true;
		EntrustApply other = (EntrustApply)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

