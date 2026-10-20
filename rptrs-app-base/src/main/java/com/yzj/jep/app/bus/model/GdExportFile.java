/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月07日 11:54:12
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
@Table(name = "bus_gd_export_file")
public class GdExportFile extends BasePO {

    public static final String TABLE_ALIAS = "股东导出文件记录表";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_NAME = "文件名称";
    public static final String ALIAS_FILE_CREATE_TIME = "文件生成时间";
    public static final String ALIAS_STATUS = "审批状态";
    public static final String ALIAS_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_BATCH_NUMBER = "数据批次";
    public static final String ALIAS_URL = "文件路径";
    public static final String ALIAS_EXPORT_USER_ID = "导出人ID";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @Column(name = "file_create_time", nullable = true, length = 11)
    private String fileCreateTime;

    @Column(name = "status", nullable = true, length = 10)
    private String status;

    @Column(name = "status_comment", nullable = true, length = 200)
    private String statusComment;

    @Column(name = "batch_number", nullable = true, length = 20)
    private String batchNumber;

    @Column(name = "url", nullable = true, length = 100)
    private String url;

    @Column(name = "export_user_id", nullable = true, length = 20)
    private String exportUserId;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }


    public String getFileCreateTime() {
        return this.fileCreateTime;
    }

    public void setFileCreateTime(String value) {
        this.fileCreateTime = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public String getStatusComment() {
        return this.statusComment;
    }

    public void setStatusComment(String value) {
        this.statusComment = value;
    }


    public String getBatchNumber() {
        return this.batchNumber;
    }

    public void setBatchNumber(String value) {
        this.batchNumber = value;
    }


    public String getUrl() {
        return this.url;
    }

    public void setUrl(String value) {
        this.url = value;
    }


    public String getExporeUserId() {
        return this.exportUserId;
    }

    public void setExporeUserId(String value) {
        this.exportUserId = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("gdExportFile[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("name = ").append(name).append(", \n")
                .append("fileCreateTime = ").append(fileCreateTime).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("batchNumber = ").append(batchNumber).append(", \n")
                .append("url = ").append(url).append(", \n")
                .append("exportUserId = ").append(exportUserId).append(", \n")
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
        if (obj instanceof GdExportFile == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GdExportFile other = (GdExportFile) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

