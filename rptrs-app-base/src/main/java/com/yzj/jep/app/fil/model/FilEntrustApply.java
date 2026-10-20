package com.yzj.jep.app.fil.model;

import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.*;
import java.util.Date;

/**
 * @title: FilEntrustApply
 * @Author Whx
 * @Date: 2021/8/13 9:53
 * @Version 1.0
 */
@Entity
@Table(name = "fil_sys_entrust_apply")
public class FilEntrustApply extends BasePO {

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

    @Column(name = "STATUS", nullable = true, length = 10)
    private Integer status;

    @Column(name = "PROCESS_DEFINITION_ID", nullable = true, length = 100)
    private String processDefinitionId;

    @Column(name = "REMARK", nullable = true, length = 1000)
    private String remark;

    @Column(name = "flag_pid", nullable = true, length = 64)
    private String flagPid;

    @Column(name = "fil_num", nullable = true, length = 64)
    private String filNum;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getTransferStaffCode() {
        return transferStaffCode;
    }

    public void setTransferStaffCode(String transferStaffCode) {
        this.transferStaffCode = transferStaffCode;
    }

    public String getTransferStaffName() {
        return transferStaffName;
    }

    public void setTransferStaffName(String transferStaffName) {
        this.transferStaffName = transferStaffName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTransferStaffNameNew() {
        return transferStaffNameNew;
    }

    public void setTransferStaffNameNew(String transferStaffNameNew) {
        this.transferStaffNameNew = transferStaffNameNew;
    }

    public String getTransferStaffCodeNew() {
        return transferStaffCodeNew;
    }

    public void setTransferStaffCodeNew(String transferStaffCodeNew) {
        this.transferStaffCodeNew = transferStaffCodeNew;
    }

    public String getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(String entrustType) {
        this.entrustType = entrustType;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getStaffDep() {
        return staffDep;
    }

    public void setStaffDep(String staffDep) {
        this.staffDep = staffDep;
    }

    public String getTransferStaffDep() {
        return transferStaffDep;
    }

    public void setTransferStaffDep(String transferStaffDep) {
        this.transferStaffDep = transferStaffDep;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFlagPid() {
        return flagPid;
    }

    public void setFlagPid(String flagPid) {
        this.flagPid = flagPid;
    }

    public String getFilNum() {
        return filNum;
    }

    public void setFilNum(String filNum) {
        this.filNum = filNum;
    }
}
