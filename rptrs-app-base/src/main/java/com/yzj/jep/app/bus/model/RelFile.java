/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月08日 18:30:22
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


@Entity
@Table(name = "bus_rel_file")
public class RelFile extends BasePO {

    public static final String TABLE_ALIAS = "RelFile";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_FILE_URL = "文件路径";
    public static final String ALIAS_FILE_NAME = "文件名称";
    public static final String ALIAS_FILE_TYPE = "文件类型";
    public static final String ALIAS_FILE_VERSION = "版本号";
    public static final String ALIAS_APPROVE_STATUS = "审批状态";
    public static final String ALIAS_FILE_SIZE = "文件大小";
    public static final String ALIAS_REL_SERIAL = "交易流水号";

    public static final String ALIAS_UPLOAD_AGENCY = "上传机构";
    public static final String ALIAS_UPLOAD_DEPARTMENT = "上传部门";

    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "PID", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "FILE_URL", nullable = true, length = 200)
    private String fileUrl;

    @Column(name = "FILE_NAME", nullable = true, length = 200)
    private String fileName;

    @Column(name = "FILE_TYPE", nullable = true, length = 20)
    private String fileType;

    @Column(name = "FILE_VERSION", nullable = true, length = 10)
    private String fileVersion;

    @Column(name = "APPROVE_STATUS", nullable = true, length = 11)
    private String approveStatus;

    @Column(name = "file_size", nullable = true, length = 64)
    private String fileSize;

    @Column(name = "rel_serial", nullable = true, length = 32)
    private String relSerial;

    @Column(name = "upload_agency", nullable = true, length = 64)
    private String uploadAgency;

    @Column(name = "upload_department", nullable = true, length = 32)
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


    public String getFileVersion() {
        return this.fileVersion;
    }

    public void setFileVersion(String value) {
        this.fileVersion = value;
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


    public String getRelSerial() {
        return this.relSerial;
    }

    public void setRelSerial(String value) {
        this.relSerial = value;
    }

    public String getUploadAgency() {
        return uploadAgency;
    }

    public void setUploadAgency(String uploadAgency) {
        this.uploadAgency = uploadAgency;
    }

    public String getUploadDepartment() {
        return uploadDepartment;
    }

    public void setUploadDepartment(String uploadDepartment) {
        this.uploadDepartment = uploadDepartment;
    }

    @Override
    public String toString() {
        return new StringBuilder("relFile[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("fileUrl = ").append(fileUrl).append(", \n")
                .append("fileName = ").append(fileName).append(", \n")
                .append("fileType = ").append(fileType).append(", \n")
                .append("fileVersion = ").append(fileVersion).append(", \n")
                .append("approveStatus = ").append(approveStatus).append(", \n")
                .append("fileSize = ").append(fileSize).append(", \n")
                .append("relSerial = ").append(relSerial).append(", \n")
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
        if (obj instanceof RelFile == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelFile other = (RelFile) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

