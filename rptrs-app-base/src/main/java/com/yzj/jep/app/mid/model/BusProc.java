/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年07月17日 13:42:50
 *
 */

package com.yzj.jep.app.mid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


@Entity
@Table(name = "mid_bus_proc")
public class BusProc extends BasePO {
	
	public static final String TABLE_ALIAS = "BusProc";
	public static final String ALIAS_PID = "主键";
	public static final String ALIAS_PROC_INST_ID = "流程实例id";
	public static final String ALIAS_FLAG_PID = "业务表id";
	public static final String ALIAS_FIL_PID = "备份表id";
	public static final String ALIAS_BUS_TYPE = "业务类型";
	public static final String ALIAS_FOCUS_APPROVE_PID = "集中审议pid";
	

	//可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
	//columns START

	@Id
	@Column(name = "pid", nullable = false, length = 10)
	private Integer pid;

	@Column(name = "proc_inst_id", nullable = true, length = 64)
	private String procInstId;

	@Column(name = "flag_pid", nullable = true, length = 11)
	private String flagPid;

	@Column(name = "fil_pid", nullable = true, length = 11)
	private String filPid;

	@Column(name = "bus_type", nullable = true, length = 10)
	private String busType;

	@Column(name = "focus_approve_pid", nullable = true, length = 500)
	private String focusApprovePid;
	//columns END


	public Integer getPid() {
		return this.pid;
	}
	
	public void setPid(Integer value) {
		this.pid = value;
	}
	

	public String getProcInstId() {
		return this.procInstId;
	}
	
	public void setProcInstId(String value) {
		this.procInstId = value;
	}
	

	public String getFlagPid() {
		return this.flagPid;
	}
	
	public void setFlagPid(String value) {
		this.flagPid = value;
	}
	

	public String getFilPid() {
		return this.filPid;
	}
	
	public void setFilPid(String value) {
		this.filPid = value;
	}
	

	public String getBusType() {
		return this.busType;
	}
	
	public void setBusType(String value) {
		this.busType = value;
	}
	

	public String getFocusApprovePid() {
		return this.focusApprovePid;
	}
	
	public void setFocusApprovePid(String value) {
		this.focusApprovePid = value;
	}
	

	@Override
	public String toString() {
		return new StringBuilder("busProc[\n")
						.append("pid = ").append(pid).append(", \n")
						.append("procInstId = ").append(procInstId).append(", \n")
						.append("flagPid = ").append(flagPid).append(", \n")
						.append("filPid = ").append(filPid).append(", \n")
						.append("busType = ").append(busType).append(", \n")
						.append("focusApprovePid = ").append(focusApprovePid).append(", \n")
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
		if(obj instanceof BusProc == false){ return false;}
		if(this == obj){ return true;}
		BusProc other = (BusProc)obj;
		return new EqualsBuilder()
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

