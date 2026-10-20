package com.yzj.jep.app.pub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "pub_file_template")
public class PubFileTemplate extends BasePO {
    /**
     * 主键
     */

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    /**
     * 模板文件名称
     */
    @Column(name = "TEMP_FILE_NAME", nullable = false, length = 200)
    private String tempFileName;

    /**
     * 模板文件路径
     */
    @Column(name = "TEMP_FILE_URL", nullable = false, length = 200)
    private String tempFileUrl;

    /**
     * 模板文件用途
     */
    @Column(name = "TEMP_FILE_TEPE", nullable = false, length = 10)
    private String tempFileTepe;

    /**
     * 模板文件版本号
     */
    @Column(name = "TEMP_FILE_VERSION", nullable = false, length = 10)
    private String tempFileVersion;

    /**
     * 模板上传机构
     */
    @Column(name = "TEMP_UPLOAD_ORG", nullable = false, length = 64)
    private String tempUploadOrg;

    /**
     * 模板上传机构名称
     */
    @Column(name = "TEMP_UPLOAD_ORGNAME", nullable = false, length = 200)
    private String tempUploadOrgname;

    /**
     * 模板上传部门
     */
    @Column(name = "TEMP_UPLOAD_DEP", nullable = false, length = 64)
    private String tempUploadDep;

    /**
     * 模板上传部门名称
     */
    @Column(name = "TEMP_UPLOAD_DEPNAME", nullable = false, length = 200)
    private String tempUploadDepname;

    /**
     * 文件大小
     */
    @Column(name = "file_size", nullable = false, length = 64)
    private Double fileSize;

    /**
     * 文件类型
     */
    @Column(name = "file_type", nullable = false, length = 20)
    private String fileType;

    @Column(name = "CREATED_TIME_", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column(name = "CREATED_BY_NAME_", nullable = false, length = 64)
    private String createByName;

    @Transient
    private String createTimeStr;


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTempFileName() {
        return tempFileName;
    }

    public void setTempFileName(String tempFileName) {
        this.tempFileName = tempFileName;
    }

    public String getTempFileUrl() {
        return tempFileUrl;
    }

    public void setTempFileUrl(String tempFileUrl) {
        this.tempFileUrl = tempFileUrl;
    }

    public String getTempFileTepe() {
        return tempFileTepe;
    }

    public void setTempFileTepe(String tempFileTepe) {
        this.tempFileTepe = tempFileTepe;
    }

    public String getTempFileVersion() {
        return tempFileVersion;
    }

    public void setTempFileVersion(String tempFileVersion) {
        this.tempFileVersion = tempFileVersion;
    }

    public String getTempUploadOrg() {
        return tempUploadOrg;
    }

    public void setTempUploadOrg(String tempUploadOrg) {
        this.tempUploadOrg = tempUploadOrg;
    }

    public String getTempUploadOrgname() {
        return tempUploadOrgname;
    }

    public void setTempUploadOrgname(String tempUploadOrgname) {
        this.tempUploadOrgname = tempUploadOrgname;
    }

    public String getTempUploadDep() {
        return tempUploadDep;
    }

    public void setTempUploadDep(String tempUploadDep) {
        this.tempUploadDep = tempUploadDep;
    }

    public String getTempUploadDepname() {
        return tempUploadDepname;
    }

    public void setTempUploadDepname(String tempUploadDepname) {
        this.tempUploadDepname = tempUploadDepname;
    }

    public Double getFileSize() {
        return fileSize;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getCreateTimeStr() {
        if (Optional.ofNullable(createTime).isPresent()) {
            return DateUtils.formatDate(createTime);
        }
        return "";
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PubFileTemplate == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        PubFileTemplate other = (PubFileTemplate) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }

    @Override
    public String toString() {
        return "PubFileTemplate{" +
                "pid=" + pid +
                ", tempFileName='" + tempFileName + '\'' +
                ", tempFileUrl='" + tempFileUrl + '\'' +
                ", tempFileTepe='" + tempFileTepe + '\'' +
                ", tempFileVersion='" + tempFileVersion + '\'' +
                ", tempUploadOrg='" + tempUploadOrg + '\'' +
                ", tempUploadOrgname='" + tempUploadOrgname + '\'' +
                ", tempUploadDep='" + tempUploadDep + '\'' +
                ", tempUploadDepname='" + tempUploadDepname + '\'' +
                ", fileSize=" + fileSize +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}