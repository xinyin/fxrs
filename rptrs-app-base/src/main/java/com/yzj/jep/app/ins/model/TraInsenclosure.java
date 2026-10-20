/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月22日 11:31:54
 *
 */

package com.yzj.jep.app.ins.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ins_tra_insenclosure")
public class TraInsenclosure extends BasePO {

    public static final String TABLE_ALIAS = "保险关联交易表-附件表";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_BUSINESS_TYPE = "业务类型";
    public static final String ALIAS_RELATED_NAME = "附件名称";
    public static final String ALIAS_ENCLOSURE_PID = "对应附件的pid";
    public static final String ALIAS_PATH = "附件路径";
    public static final String ALIAS_FILE_DESCRIPTION = "文件描述";
    public static final String ALIAS_FILE_TYPE = "文件类型";
    public static final String ALIAS_FILE_SIZE = "文件大小";
    public static final String ALIAS_TRANS_NUM = "交易流水";
    public static final String ALIAS_UPLOAD_TIME = "上传时间";
    public static final String ALIAS_UPLOAD_DEPT = "上传部门";
    public static final String ALIAS_UPLOAD_ORG = "上传机构";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "business_type", nullable = true, length = 21)
    private String businessType;

    @Column(name = "related_name", nullable = true, length = 100)
    private String relatedName;

    @Column(name = "enclosure_pid", nullable = true, length = 10)
    private String enclosurePid;

    @Column(name = "path", nullable = true, length = 100)
    private String path;

    @Column(name = "file_description", nullable = true, length = 256)
    private String fileDescription;

    @Column(name = "file_type", nullable = true, length = 16)
    private String fileType;

    @Column(name = "file_size", nullable = true, length = 32)
    private String fileSize;

    @Column(name = "trans_num", nullable = true, length = 32)
    private String transNum;

    @Column(name = "upload_time", nullable = true, length = 11)
    private String uploadTime;

    @Column(name = "upload_dept", nullable = true, length = 20)
    private String uploadDept;

    @Column(name = "upload_org", nullable = true, length = 20)
    private String uploadOrg;

    @Column(name = "upload_file_type", nullable = true, length = 20)
    private String uploadFileType;
    //columns END


    public String getUploadFileType() {
        return uploadFileType;
    }

    public void setUploadFileType(String uploadFileType) {
        this.uploadFileType = uploadFileType;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getBusinessType() {
        return this.businessType;
    }

    public void setBusinessType(String value) {
        this.businessType = value;
    }


    public String getRelatedName() {
        return this.relatedName;
    }

    public void setRelatedName(String value) {
        this.relatedName = value;
    }


    public String getEnclosurePid() {
        return this.enclosurePid;
    }

    public void setEnclosurePid(String value) {
        this.enclosurePid = value;
    }


    public String getPath() {
        return this.path;
    }

    public void setPath(String value) {
        this.path = value;
    }


    public String getFileDescription() {
        return this.fileDescription;
    }

    public void setFileDescription(String value) {
        this.fileDescription = value;
    }


    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String value) {
        this.fileType = value;
    }


    public String getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(String value) {
        this.fileSize = value;
    }


    public String getTransNum() {
        return this.transNum;
    }

    public void setTransNum(String value) {
        this.transNum = value;
    }


    public String getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(String value) {
        this.uploadTime = value;
    }


    public String getUploadDept() {
        return this.uploadDept;
    }

    public void setUploadDept(String value) {
        this.uploadDept = value;
    }


    public String getUploadOrg() {
        return this.uploadOrg;
    }

    public void setUploadOrg(String value) {
        this.uploadOrg = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("traInsenclosure[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("businessType = ").append(businessType).append(", \n")
                .append("relatedName = ").append(relatedName).append(", \n")
                .append("enclosurePid = ").append(enclosurePid).append(", \n")
                .append("path = ").append(path).append(", \n")
                .append("fileDescription = ").append(fileDescription).append(", \n")
                .append("fileType = ").append(fileType).append(", \n")
                .append("fileSize = ").append(fileSize).append(", \n")
                .append("transNum = ").append(transNum).append(", \n")
                .append("uploadTime = ").append(uploadTime).append(", \n")
                .append("uploadDept = ").append(uploadDept).append(", \n")
                .append("uploadOrg = ").append(uploadOrg).append(", \n")
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
        if (obj instanceof TraInsenclosure == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TraInsenclosure other = (TraInsenclosure) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

