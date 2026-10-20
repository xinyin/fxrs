package com.yzj.jep.app.bus.vo;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import com.yzj.jep.workflow.api.model.IProcInst;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Transient;
import java.util.Date;


public class ProcInstVo extends BasePO implements IProcInst {


    private String id;

    private String processInstantId;

    private String bizId;

    private String procId;

    private String procCode;

    private String procName;

    private Integer procVersion;

    private String moduleCode;

    private String moduleName;

    private String templateCode;

    private String templateName;

    private String processDefId;

    private String processDefKey;

    private String processDefName;

    private String formCode;

    private String formName;

    private String taskId;

    private String taskKey;

    private String taskName;

    private String starterCode;

    private String starterName;

    private String starterUnitCode;

    private String starterUnitName;

    private String starterDeptCode;

    private String starterDeptName;

    private String skipSameApprover;


    private Date startTime;


    private Date endTime;


    private Date processTime;

    private Long duration;

    private String status;

    private String notifyStatus;

    private Integer notifyRetryTimes;

    private String tenantId;

    private String starterUnitHierarchy;

    private String handleStatus;

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getProcessInstantId() {
        return this.processInstantId;
    }

    public void setProcessInstantId(String value) {
        this.processInstantId = value;
    }

    public String getBizId() {
        return this.bizId;
    }

    public void setBizId(String value) {
        this.bizId = value;
    }

    public String getProcId() {
        return this.procId;
    }

    public void setProcId(String value) {
        this.procId = value;
    }

    public String getProcCode() {
        return this.procCode;
    }

    public void setProcCode(String value) {
        this.procCode = value;
    }

    public String getProcName() {
        return this.procName;
    }

    public void setProcName(String value) {
        this.procName = value;
    }

    public Integer getProcVersion() {
        return this.procVersion;
    }

    public void setProcVersion(Integer value) {
        this.procVersion = value;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String value) {
        this.moduleCode = value;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String value) {
        this.moduleName = value;
    }

    public String getTemplateCode() {
        return this.templateCode;
    }

    public void setTemplateCode(String value) {
        this.templateCode = value;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String value) {
        this.templateName = value;
    }

    public String getProcessDefId() {
        return this.processDefId;
    }

    public void setProcessDefId(String value) {
        this.processDefId = value;
    }

    public String getProcessDefKey() {
        return this.processDefKey;
    }

    public void setProcessDefKey(String value) {
        this.processDefKey = value;
    }

    public String getProcessDefName() {
        return this.processDefName;
    }

    public void setProcessDefName(String value) {
        this.processDefName = value;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String value) {
        this.formCode = value;
    }

    public String getFormName() {
        return this.formName;
    }

    public void setFormName(String value) {
        this.formName = value;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String value) {
        this.taskId = value;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String value) {
        this.taskKey = value;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String value) {
        this.taskName = value;
    }

    public String getStarterCode() {
        return this.starterCode;
    }

    public void setStarterCode(String value) {
        this.starterCode = value;
    }

    public String getStarterName() {
        return this.starterName;
    }

    public void setStarterName(String value) {
        this.starterName = value;
    }

    public String getStarterUnitCode() {
        return this.starterUnitCode;
    }

    public void setStarterUnitCode(String value) {
        this.starterUnitCode = value;
    }

    public String getStarterUnitName() {
        return this.starterUnitName;
    }

    public void setStarterUnitName(String value) {
        this.starterUnitName = value;
    }

    public String getStarterDeptCode() {
        return this.starterDeptCode;
    }

    public void setStarterDeptCode(String value) {
        this.starterDeptCode = value;
    }

    public String getStarterDeptName() {
        return this.starterDeptName;
    }

    public void setStarterDeptName(String value) {
        this.starterDeptName = value;
    }

    @Transient
    public String getStartTimeString() {
        return DateUtils.format(this.getStartTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public void setStartTimeString(String value) {
        this.setStartTime(DateUtils.parse(value, "yyyy-MM-dd HH:mm:ss"));
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date value) {
        this.startTime = value;
    }

    @Transient
    public String getEndTimeString() {
        return DateUtils.format(this.getEndTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public void setEndTimeString(String value) {
        this.setEndTime(DateUtils.parse(value, "yyyy-MM-dd HH:mm:ss"));
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date value) {
        this.endTime = value;
    }

    @Transient
    public String getProcessTimeString() {
        return DateUtils.format(this.getProcessTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public void setProcessTimeString(String value) {
        this.setProcessTime(DateUtils.parse(value, "yyyy-MM-dd HH:mm:ss"));
    }

    public String getSkipSameApprover() {
        return this.skipSameApprover;
    }

    public void setSkipSameApprover(String skipSameApprover) {
        this.skipSameApprover = skipSameApprover;
    }

    public Date getProcessTime() {
        return this.processTime;
    }

    public void setProcessTime(Date value) {
        this.processTime = value;
    }

    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long value) {
        this.duration = value;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public String getNotifyStatus() {
        return this.notifyStatus;
    }

    public void setNotifyStatus(String value) {
        this.notifyStatus = value;
    }

    public Integer getNotifyRetryTimes() {
        return this.notifyRetryTimes;
    }

    public void setNotifyRetryTimes(Integer value) {
        this.notifyRetryTimes = value;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String value) {
        this.tenantId = value;
    }

    public String getStarterUnitHierarchy() {
        return this.starterUnitHierarchy;
    }

    public void setStarterUnitHierarchy(String value) {
        this.starterUnitHierarchy = value;
    }

    public String toString() {
        return "procInst[\n" + "id = " + this.id + ", \n" + "processInstantId = " + this.processInstantId + ", \n" + "bizId = " + this.bizId + ", \n" + "procId = " + this.procId + ", \n" + "procCode = " + this.procCode + ", \n" + "procName = " + this.procName + ", \n" + "procVersion = " + this.procVersion + ", \n" + "moduleCode = " + this.moduleCode + ", \n" + "moduleName = " + this.moduleName + ", \n" + "templateCode = " + this.templateCode + ", \n" + "templateName = " + this.templateName + ", \n" + "processDefId = " + this.processDefId + ", \n" + "processDefKey = " + this.processDefKey + ", \n" + "processDefName = " + this.processDefName + ", \n" + "formCode = " + this.formCode + ", \n" + "formName = " + this.formName + ", \n" + "taskId = " + this.taskId + ", \n" + "taskKey = " + this.taskKey + ", \n" + "taskName = " + this.taskName + ", \n" + "starterCode = " + this.starterCode + ", \n" + "starterName = " + this.starterName + ", \n" + "starterUnitCode = " + this.starterUnitCode + ", \n" + "starterUnitName = " + this.starterUnitName + ", \n" + "starterDeptCode = " + this.starterDeptCode + ", \n" + "starterDeptName = " + this.starterDeptName + ", \n" + "startTime = " + this.startTime + ", \n" + "endTime = " + this.endTime + ", \n" + "processTime = " + this.processTime + ", \n" + "skipSameApprover = " + this.skipSameApprover + ", \n" + "duration = " + this.duration + ", \n" + "status = " + this.status + ", \n" + "notifyStatus = " + this.notifyStatus + ", \n" + "notifyRetryTimes = " + this.notifyRetryTimes + ", \n" + "tenantId = " + this.tenantId + ", \n" + "starterUnitHierarchy = " + this.starterUnitHierarchy + ", \n" + "]";
    }

    public int hashCode() {
        return (new HashCodeBuilder()).append(this.getId()).toHashCode();
    }


    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }
}
