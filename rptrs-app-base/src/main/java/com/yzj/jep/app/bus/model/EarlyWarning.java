/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 10:02:38
 *
 */

package com.yzj.jep.app.bus.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "bus_early_warning")
public class EarlyWarning extends BasePO {

    public static final String TABLE_ALIAS = "预警信息管理";
    public static final String ALIAS_PID = "pid";
    public static final String ALIAS_EVENT_TYPE = "事件类型";
    public static final String ALIAS_EARLYWARNING_EVENT = "预警事件";
    public static final String ALIAS_EARLYWARNING_LEVEL = "预警级别";
    public static final String ALIAS_LWARN_THRESHOLD1 = "预警阈值1";
    public static final String ALIAS_LWARN_THRESHOLD2 = "预警阈值2";
    public static final String ALIAS_EVENT_DESCRIPTION = "事件描述";
    public static final String ALIAS_ISBLOCKING_PROCESS = "是否阻断流程";
    public static final String ALIAS_EARLY_WARNING_MODE = "预警方式";
    public static final String ALIAS_EVENT_STATUS = "事件状态";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "EVENT_TYPE", nullable = true, length = 1)
    private String eventType;

    @Column(name = "EARLYWARNING_EVENT", nullable = true, length = 10)
    private String earlyWarningEvent;

    @Column(name = "EARLYWARNING_LEVEL", nullable = true, length = 10)
    private String earlyWarningLevel;

    @Column(name = "LWARN_THRESHOLD1", nullable = true, length = 10)
    private String warnThreshold1;
    @Column(name = "LWARN_THRESHOLD2", nullable = true, length = 10)
    private String warnThreshold2;
    @Column(name = "EVENT_DESCRIPTION", nullable = true, length = 10)
    private String eventDescription;
    @Column(name = "ISBLOCKING_PROCESS", nullable = true, length = 10)
    private String isBlockingProcess;
    @Column(name = "EARLY_WARNING_MODE", nullable = true, length = 10)
    private String earlyWarningMode;
    @Column(name = "EVENT_STATUS", nullable = true, length = 10)
    private String eventStatus;
    @Column(name = "LWARN_THRESHOLDSUB", nullable = true, length = 10)
    private String warnThresholdSub;
    //columns END


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEarlyWarningEvent() {
        return earlyWarningEvent;
    }

    public void setEarlyWarningEvent(String earlyWarningEvent) {
        this.earlyWarningEvent = earlyWarningEvent;
    }

    public String getEarlyWarningLevel() {
        return earlyWarningLevel;
    }

    public void setEarlyWarningLevel(String earlyWarningLevel) {
        this.earlyWarningLevel = earlyWarningLevel;
    }

    public String getWarnThreshold1() {
        return warnThreshold1;
    }

    public void setWarnThreshold1(String warnThreshold1) {
        this.warnThreshold1 = warnThreshold1;
    }

    public String getWarnThreshold2() {
        return warnThreshold2;
    }

    public void setWarnThreshold2(String warnThreshold2) {
        this.warnThreshold2 = warnThreshold2;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getIsBlockingProcess() {
        return isBlockingProcess;
    }

    public void setIsBlockingProcess(String isBlockingProcess) {
        this.isBlockingProcess = isBlockingProcess;
    }

    public String getEarlyWarningMode() {
        return earlyWarningMode;
    }

    public void setEarlyWarningMode(String earlyWarningMode) {
        this.earlyWarningMode = earlyWarningMode;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getWarnThresholdSub() {
        return warnThresholdSub;
    }

    public void setWarnThresholdSub(String warnThresholdSub) {
        this.warnThresholdSub = warnThresholdSub;
    }

    @Override
    public String toString() {
        return "EarlyWarning{" +
                "pid=" + pid +
                ", eventType='" + eventType + '\'' +
                ", earlyWarningEvent='" + earlyWarningEvent + '\'' +
                ", earlyWarningLevel='" + earlyWarningLevel + '\'' +
                ", warnThreshold1='" + warnThreshold1 + '\'' +
                ", warnThreshold2='" + warnThreshold2 + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", isBlockingProcess='" + isBlockingProcess + '\'' +
                ", earlyWarningMode='" + earlyWarningMode + '\'' +
                ", eventStatus='" + eventStatus + '\'' +
                ", warnThresholdSub='" + warnThresholdSub + '\'' +
                '}';
    }
}

