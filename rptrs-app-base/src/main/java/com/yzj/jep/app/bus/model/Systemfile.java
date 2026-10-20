/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月19日 16:17:00
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

import java.util.Date;

@Entity
@Table(name = "bus_systemfile")
public class Systemfile extends BasePO {

    public static final String TABLE_ALIAS = "制度文件";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_SYSTEM_TYPE = "制度类型";
    public static final String ALIAS_FILE_URL = "文件路径";
    public static final String ALIAS_FILE_NAME = "文件名称";
    public static final String ALIAS_FILE_TYPE = "文件类型";
    public static final String ALIAS_FILE_SIZE = "文件大小";
    public static final String ALIAS_FILE_VERSION = "版本号";
    public static final String ALIAS_IS_DEL = "是否删除";
    public static final String ALIAS_APPROVE_STATUS = "审批状态";
    public static final String ALIAS_ENABLE_STATUS = "时效性";
    public static final String ALIAS_EFFECTIVE_TIME = "生效时间";
    public static final String ALIAS_SYS_NAME = "制度名称";
    public static final String ALIAS_DISPATCH_DEP = "发文部门";
    public static final String ALIAS_UPLOAD_ORG = "上传机构";
    public static final String ALIAS_UPLOAD_DEP = "上传部门";
    public static final String ALIAS_EXPIRY_DATE = "失效时间";
    public static final String ALIAS_SYS_ISSUE_ORG = "制度颁布机构部";
    public static final String ALIAS_ARTICLE_NUMBER = "文号";
    public static final String ALIAS_RELE_OUT_RULE = "关联外规";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "system_type", nullable = true, length = 20)
    private String systemType;

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

    @Column(name = "is_del", nullable = true, length = 10)
    private Integer isDel;

    @Column(name = "approve_status", nullable = true, length = 10)
    private Integer approveStatus;

    @Column(name = "enable_status", nullable = true, length = 10)
    private Integer enableStatus;

    @Column(name = "effective_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date effectiveTime;

    @Column(name = "sys_name", nullable = true, length = 500)
    private String sysName;

    @Column(name = "dispatch_dep", nullable = true, length = 200)
    private String dispatchDep;

    @Column(name = "upload_org", nullable = true, length = 200)
    private String uploadOrg;

    @Column(name = "upload_dep", nullable = true, length = 200)
    private String uploadDep;

    @Column(name = "expiry_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date expiryDate;

    @Column(name = "sys_issue_org", nullable = true, length = 200)
    private String sysIssueOrg;

    @Column(name = "article_number", nullable = true, length = 200)
    private String articleNumber;

    @Column(name = "rele_out_rule", nullable = true, length = 200)
    private String releOutRule;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getSystemType() {
        return this.systemType;
    }

    public void setSystemType(String value) {
        this.systemType = value;
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


    public Integer getIsDel() {
        return this.isDel;
    }

    public void setIsDel(Integer value) {
        this.isDel = value;
    }


    public Integer getApproveStatus() {
        return this.approveStatus;
    }

    public void setApproveStatus(Integer value) {
        this.approveStatus = value;
    }


    public Integer getEnableStatus() {
        return this.enableStatus;
    }

    public void setEnableStatus(Integer value) {
        this.enableStatus = value;
    }


    public Date getEffectiveTime() {
        return this.effectiveTime;
    }

    public void setEffectiveTime(Date value) {
        this.effectiveTime = value;
    }


    public String getSysName() {
        return this.sysName;
    }

    public void setSysName(String value) {
        this.sysName = value;
    }


    public String getDispatchDep() {
        return this.dispatchDep;
    }

    public void setDispatchDep(String value) {
        this.dispatchDep = value;
    }


    public String getUploadOrg() {
        return this.uploadOrg;
    }

    public void setUploadOrg(String value) {
        this.uploadOrg = value;
    }


    public String getUploadDep() {
        return this.uploadDep;
    }

    public void setUploadDep(String value) {
        this.uploadDep = value;
    }


    public Date getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(Date value) {
        this.expiryDate = value;
    }


    public String getSysIssueOrg() {
        return this.sysIssueOrg;
    }

    public void setSysIssueOrg(String value) {
        this.sysIssueOrg = value;
    }


    public String getArticleNumber() {
        return this.articleNumber;
    }

    public void setArticleNumber(String value) {
        this.articleNumber = value;
    }


    public String getReleOutRule() {
        return this.releOutRule;
    }

    public void setReleOutRule(String value) {
        this.releOutRule = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("systemfile[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("systemType = ").append(systemType).append(", \n")
                .append("fileUrl = ").append(fileUrl).append(", \n")
                .append("fileName = ").append(fileName).append(", \n")
                .append("fileType = ").append(fileType).append(", \n")
                .append("fileSize = ").append(fileSize).append(", \n")
                .append("fileVersion = ").append(fileVersion).append(", \n")
                .append("isDel = ").append(isDel).append(", \n")
                .append("approveStatus = ").append(approveStatus).append(", \n")
                .append("enableStatus = ").append(enableStatus).append(", \n")
                .append("effectiveTime = ").append(effectiveTime).append(", \n")
                .append("sysName = ").append(sysName).append(", \n")
                .append("dispatchDep = ").append(dispatchDep).append(", \n")
                .append("uploadOrg = ").append(uploadOrg).append(", \n")
                .append("uploadDep = ").append(uploadDep).append(", \n")
                .append("expiryDate = ").append(expiryDate).append(", \n")
                .append("sysIssueOrg = ").append(sysIssueOrg).append(", \n")
                .append("articleNumber = ").append(articleNumber).append(", \n")
                .append("releOutRule = ").append(releOutRule).append(", \n")
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
        if (obj instanceof Systemfile == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Systemfile other = (Systemfile) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

