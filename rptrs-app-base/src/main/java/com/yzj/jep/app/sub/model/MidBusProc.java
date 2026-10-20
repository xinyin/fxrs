package com.yzj.jep.app.sub.model;

import java.io.Serializable;

/**
 * @title: MidBusProc
 * @Author Whx
 * @Date: 2021/7/29 15:30
 * @Version 1.0
 * 流程审批中间表
 */
public class MidBusProc implements Serializable {

    private static final long serialVersionUID = 742514231035222778L;
    /**
     * 主键
     */
    private Integer pid;
    /**
     * 流程实例id
     */
    private String procInstId;
    /**
     * 业务表id
     */
    private String flagPid;
    /**
     * 备份表id
     */
    private String filPid;
    /**
     * 业务类型
     */
    private String busType;
    /**
     * 集中审议pid
     */
    private String focusApprovePid;


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getFlagPid() {
        return flagPid;
    }

    public void setFlagPid(String flagPid) {
        this.flagPid = flagPid;
    }

    public String getFilPid() {
        return filPid;
    }

    public void setFilPid(String filPid) {
        this.filPid = filPid;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getFocusApprovePid() {
        return focusApprovePid;
    }

    public void setFocusApprovePid(String focusApprovePid) {
        this.focusApprovePid = focusApprovePid;
    }

}
