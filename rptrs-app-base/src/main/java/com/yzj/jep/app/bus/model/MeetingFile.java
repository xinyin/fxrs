/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 16:13:55
 *
 */

package com.yzj.jep.app.bus.model;

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
@Table(name = "bus_meeting_file")
public class MeetingFile extends BasePO {

    public static final String TABLE_ALIAS = "MeetingFile";
    public static final String ALIAS_PID = "pid";
    public static final String ALIAS_FILE_URL = "文件路径";
    public static final String ALIAS_MEETING_PID = "会议id";
    public static final String ALIAS_FILE_NAME = "文件名称";
    public static final String ALIAS_FILE_TYPE = "文件类型";
    public static final String ALIAS_APPROVE_STATUS = "审批状态";
    public static final String ALIAS_FILE_SIZE = "文件大小";
    public static final String ALIAS_UPLOAD_AGENCY = "上传机构";
    public static final String ALIAS_UPLOAD_DEPARTMENT = "上传部门";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "PID", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "FILE_URL", nullable = true, length = 200)
    private String fileUrl;

    @Column(name = "MEETING_PID", nullable = true, length = 10)
    private Integer meetingPid;

    @Column(name = "FILE_NAME", nullable = true, length = 200)
    private String fileName;

    @Column(name = "FILE_TYPE", nullable = true, length = 20)
    private String fileType;

    @Column(name = "APPROVE_STATUS", nullable = true, length = 11)
    private String approveStatus;

    @Column(name = "file_size", nullable = true, length = 64)
    private String fileSize;

    @Column(name = "upload_agency", nullable = true, length = 64)
    private String uploadAgency;

    @Column(name = "upload_department", nullable = true, length = 64)
    private String uploadDepartment;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String value) {
        this.fileUrl = value;
    }


    public Integer getMeetingPid() {
        return this.meetingPid;
    }

    public void setMeetingPid(Integer value) {
        this.meetingPid = value;
    }


    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String value) {
        this.fileName = value;
    }


    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String value) {
        this.fileType = value;
    }


    public String getApproveStatus() {
        return this.approveStatus;
    }

    public void setApproveStatus(String value) {
        this.approveStatus = value;
    }


    public String getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(String value) {
        this.fileSize = value;
    }


    public String getUploadAgency() {
        return this.uploadAgency;
    }

    public void setUploadAgency(String value) {
        this.uploadAgency = value;
    }


    public String getUploadDepartment() {
        return this.uploadDepartment;
    }

    public void setUploadDepartment(String value) {
        this.uploadDepartment = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("meetingFile[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("fileUrl = ").append(fileUrl).append(", \n")
                .append("meetingPid = ").append(meetingPid).append(", \n")
                .append("fileName = ").append(fileName).append(", \n")
                .append("fileType = ").append(fileType).append(", \n")
                .append("approveStatus = ").append(approveStatus).append(", \n")
                .append("fileSize = ").append(fileSize).append(", \n")
                .append("uploadAgency = ").append(uploadAgency).append(", \n")
                .append("uploadDepartment = ").append(uploadDepartment).append(", \n")
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
        if (obj instanceof MeetingFile == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        MeetingFile other = (MeetingFile) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

