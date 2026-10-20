/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月03日 17:22:32
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

import java.util.Date;

@Entity
@Table(name = "bus_meeting_manage")
public class MeetingManage extends BasePO {

    public static final String TABLE_ALIAS = "MeetingManage";
    public static final String ALIAS_FILEPID = "filepid";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_MEET_NAME = "会议名称";
    public static final String ALIAS_MEET_DEPART = "会议发起部门";
    public static final String ALIAS_MEET_INITIATOR_ID = "会议发起人编号";
    public static final String ALIAS_MEET_INITIATOR_NAME = "会议发起人名称";
    public static final String ALIAS_MEET_DATE = "会议发起日期";
    public static final String ALIAS_CONFEREE = "参会人员";
    public static final String ALIAS_MEET_DETAILS = "会议内容";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Column(name = "filepid", nullable = true, length = 10)
    private Integer filepid;

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "meet_name", nullable = true, length = 20)
    private String meetName;

    @Column(name = "meet_depart", nullable = false, length = 10)
    private String meetDepart;

    @Column(name = "meet_initiator_id", nullable = false, length = 20)
    private String meetInitiatorId;

    @Column(name = "meet_initiator_name", nullable = false, length = 10)
    private String meetInitiatorName;

    @Column(name = "meet_date", nullable = false, length = 19)
    @Temporal(TemporalType.DATE)
    private Date meetDate;

    @Column(name = "conferee", nullable = false, length = 100)
    private String conferee;

    @Column(name = "meet_details", nullable = true, length = 10)
    private String meetDetails;
    //columns END


    public Integer getFilepid() {
        return this.filepid;
    }

    public void setFilepid(Integer value) {
        this.filepid = value;
    }


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getMeetName() {
        return this.meetName;
    }

    public void setMeetName(String value) {
        this.meetName = value;
    }


    public String getMeetDepart() {
        return this.meetDepart;
    }

    public void setMeetDepart(String value) {
        this.meetDepart = value;
    }


    public String getMeetInitiatorId() {
        return this.meetInitiatorId;
    }

    public void setMeetInitiatorId(String value) {
        this.meetInitiatorId = value;
    }


    public String getMeetInitiatorName() {
        return this.meetInitiatorName;
    }

    public void setMeetInitiatorName(String value) {
        this.meetInitiatorName = value;
    }


    public Date getMeetDate() {
        return this.meetDate;
    }

    public void setMeetDate(Date value) {
        this.meetDate = value;
    }


    public String getConferee() {
        return this.conferee;
    }

    public void setConferee(String value) {
        this.conferee = value;
    }


    public String getMeetDetails() {
        return this.meetDetails;
    }

    public void setMeetDetails(String value) {
        this.meetDetails = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("meetingManage[\n")
                .append("filepid = ").append(filepid).append(", \n")
                .append("pid = ").append(pid).append(", \n")
                .append("meetName = ").append(meetName).append(", \n")
                .append("meetDepart = ").append(meetDepart).append(", \n")
                .append("meetInitiatorId = ").append(meetInitiatorId).append(", \n")
                .append("meetInitiatorName = ").append(meetInitiatorName).append(", \n")
                .append("meetDate = ").append(meetDate).append(", \n")
                .append("conferee = ").append(conferee).append(", \n")
                .append("meetDetails = ").append(meetDetails).append(", \n")
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
        if (obj instanceof MeetingManage == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        MeetingManage other = (MeetingManage) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

