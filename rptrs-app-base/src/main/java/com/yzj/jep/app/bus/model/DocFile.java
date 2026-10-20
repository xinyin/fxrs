/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月20日 11:49:15
 *
 */

package com.yzj.jep.app.bus.model;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bus_doc_file")
public class DocFile extends BasePO {

    public static final String TABLE_ALIAS = "制度文件";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_FILE_URL = "文件路径";
    public static final String ALIAS_FILE_NAME = "文件名称";
    public static final String ALIAS_FILE_TYPE = "文件类型";
    public static final String ALIAS_FILE_SIZE = "文件大小";
    public static final String ALIAS_FILE_VERSION = "版本号";
    public static final String ALIAS_APPROVE_STATUS = "审批状态";
    public static final String ALIAS_DOC_PID = "制度pid";
    public static final String TEMP_FILE_VERSION = "模板文件版本号";
    public static final String TEMP_UPLOAD_ORG = "模板上传机构";
    public static final String TEMP_UPLOAD_ORGNAME = "模板上传机构名称";
    public static final String TEMP_UPLOAD_DEP = "模板板上传部门";
    public static final String TEMP_UPLOAD_DEPNAME = "模板板上传部门名称";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 20)
    private Integer pid;

    @Column(name = "file_url", nullable = true, length = 200)
    private String fileUrl;

    @Column(name = "file_name", nullable = true, length = 200)
    private String fileName;

    @Column(name = "file_type", nullable = true, length = 20)
    private String fileType;

    @Column(name = "file_size", nullable = true, length = 64)
    private Double fileSize;

    @Column(name = "file_version", nullable = true, length = 10)
    private String fileVersion;

    @Column(name = "approve_status", nullable = true, length = 10)
    private Integer approveStatus;

    @Column(name = "doc_pid", nullable = false, length = 20)
    private Integer docPid;

    @Column(name = "upload_org_name", nullable = false, length = 200)
    private String uploadOrgName;

    @Column(name = "upload_dept_name", nullable = false, length = 200)
    private String uploadDeptName;

    @Column(name = "upload_type", nullable = false, length = 4)
    private int uploadType;

    @Column(name = "upload_name", nullable = false, length = 100)
    private String uploadName;

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public int getUploadType() {
        return uploadType;
    }

    public void setUploadType(int uploadType) {
        this.uploadType = uploadType;
    }

    public String getUploadOrgName() {
        return uploadOrgName;
    }

    public void setUploadOrgName(String uploadOrgName) {
        this.uploadOrgName = uploadOrgName;
    }

    public String getUploadDeptName() {
        return uploadDeptName;
    }

    public void setUploadDeptName(String uploadDeptName) {
        this.uploadDeptName = uploadDeptName;
    }

    //columns END
    @Transient
    private Date createTimeString;

    public String getActiveTimeString() {
        if (createTimeString != null) {
            return DateUtils.formatDate(createTimeString);
        }
        return "";
    }

    public void setActiveTimeString(String createTimeString) {
        if (createTimeString != null) {
            setCreateTime(DateUtils.parseDate(createTimeString));
        }
    }


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


    public Double getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Double value) {
        this.fileSize = value;
    }


    public String getFileVersion() {
        return this.fileVersion;
    }

    public void setFileVersion(String value) {
        this.fileVersion = value;
    }


    public Integer getApproveStatus() {
        return this.approveStatus;
    }

    public void setApproveStatus(Integer value) {
        this.approveStatus = value;
    }


    public Integer getDocPid() {
        return this.docPid;
    }

    public void setDocPid(Integer value) {
        this.docPid = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("docFile[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("fileUrl = ").append(fileUrl).append(", \n")
                .append("fileName = ").append(fileName).append(", \n")
                .append("fileType = ").append(fileType).append(", \n")
                .append("fileSize = ").append(fileSize).append(", \n")
                .append("fileVersion = ").append(fileVersion).append(", \n")
                .append("approveStatus = ").append(approveStatus).append(", \n")
                .append("upload_org_name = ").append(uploadOrgName).append(", \n")
                .append("upload_dept_name = ").append(uploadDeptName).append(", \n")
                .append("docPid = ").append(docPid).append(", \n")
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
        if (obj instanceof DocFile == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        DocFile other = (DocFile) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

