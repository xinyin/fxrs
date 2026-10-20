package com.yzj.jep.app.sub.vo;

import com.yzj.jep.workflow.api.model.IProcInst;
import com.yzj.jep.workflow.impl.model.TaskInstImpl;

/**
 * @title: TaskInstVo
 * @Author Whx
 * @Date: 2021/7/16 10:44
 * @Version 1.0
 */
public class TaskInstVo extends TaskInstImpl {
    public TaskInstVo(IProcInst procInst) {
        super(procInst);
    }

    /**审批概述*/
    private String summarize;

    /**任务类型  自有任务，委托任务*/
    private String taskType;

    /**委托人编码*/
    private String trustCode;

    /**委托人名称*/
    private String trustName;

    public String getSummarize() {
        return summarize;
    }

    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTrustCode() {
        return trustCode;
    }

    public void setTrustCode(String trustCode) {
        this.trustCode = trustCode;
    }

    public String getTrustName() {
        return trustName;
    }

    public void setTrustName(String trustName) {
        this.trustName = trustName;
    }
}
