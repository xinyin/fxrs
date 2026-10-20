/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月20日 11:07:48
 *
 */

package com.yzj.jep.app.pub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "pub_publog")
public class Publog extends BasePO {

    public static final String TABLE_ALIAS = "Publog";
    public static final String ALIAS_PID = "pid";
    public static final String ALIAS_PUB_FILE_NAME = "文件名称";
    public static final String ALIAS_PUB_SUBJECT = "更新部门";
    public static final String ALIAS_PUB_YEAR = "pubYear";
    public static final String ALIAS_PUB_QUARTER = "pubQuarter";
    public static final String ALIAS_MAX_PERCENT_IN_NET_ASSETS = "maxPercentInNetAssets";
    public static final String ALIAS_DOWN_AMT = "下载次数";
    public static final String ALIAS_STATUS = "状态";
    public static final String ALIAS_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_DOWN_STATUS = "下载状态";
    public static final String ALIAS_APPROVALID = "序号";
    public static final String ALIAS_PUB_TYPE="文件类型";



    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "pub_file_name", nullable = false, length = 100)
    private String pubFileName;

    @Column(name = "pub_subject", nullable = false, length = 50)
    private String pubSubject;

    @Column(name = "pub_year", nullable = true, length = 10)
    private String pubYear;

    @Column(name = "pub_quarter", nullable = true, length = 10)
    private String pubQuarter;

    @Column(name = "max_percentIn_net_assets", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date maxPercentInNetAssets;

    @Column(name = "down_amt", nullable = true, length = 10)
    private Integer downAmt;

    @Column(name = "status", nullable = true, length = 10)
    private String status;

    @Column(name = "status_comment", nullable = true, length = 64)
    private String statusComment;

    @Column(name = "down_status", nullable = true, length = 10)
    private String downStatus;

    @Column(name = "pub_version", nullable = true, length = 50)
    private String pubVersion;

    @Column(name = "approval_id", nullable = true, length = 50)
    private String approvalId;

    @Column(name = "pub_type", nullable = true, length = 10)
    private String pubType;

    @Column(name = "rel_id", nullable = true, length = 11)
    private int relId;

    @Column(name = "file_url", nullable = true, length = 255)
    private String fileUrl;

    @Column(name = "tran_type", nullable = true, length = 11)
    private String tranType;

    //归档日期
    @Column(name = "ARCH_DATE", nullable = true, length = 19)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date archDate;

    @Column(name = "pub_id", length = 32)
    private String pubId;//披露文件标识
    //columns END


    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }
    //columns END

    public void setArchDateString(String value){
        if (value != null){
            setArchDate(DateUtils.parseDate(value));
        }
    }

    public String getArchDateString(){
        if (Optional.ofNullable(archDate).isPresent()) {
            return DateUtils.formatDate(archDate);
        }
        return "";
    }

    public Date getArchDate() {
        return archDate;
    }

    public void setArchDate(Date archDate) {
        this.archDate = archDate;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getPubFileName() {
        return this.pubFileName;
    }

    public void setPubFileName(String value) {
        this.pubFileName = value;
    }


    public String getPubSubject() {
        return this.pubSubject;
    }

    public void setPubSubject(String value) {
        this.pubSubject = value;
    }


    public String getPubYear() {
        return this.pubYear;
    }

    public void setPubYear(String value) {
        this.pubYear = value;
    }


    public String getPubQuarter() {
        return this.pubQuarter;
    }

    public void setPubQuarter(String value) {
        this.pubQuarter = value;
    }


    public Date getMaxPercentInNetAssets() {
        return this.maxPercentInNetAssets;
    }

    public void setMaxPercentInNetAssets(Date value) {
        this.maxPercentInNetAssets = value;
    }


    public Integer getDownAmt() {
        return this.downAmt;
    }

    public void setDownAmt(Integer value) {
        this.downAmt = value;
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


    public String getDownStatus() {
        return this.downStatus;
    }

    public void setDownStatus(String value) {
        this.downStatus = value;
    }

    public String getPubVersion() {
        return pubVersion;
    }

    public void setPubVersion(String pubVersion) {
        this.pubVersion = pubVersion;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getPubType() {
        return pubType;
    }

    public void setPubType(String pubType) {
        this.pubType = pubType;
    }

    public int getRelId() {
        return relId;
    }

    public void setRelId(int relId) {
        this.relId = relId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Publog{");
        sb.append("pid=").append(pid);
        sb.append(", pubFileName='").append(pubFileName).append('\'');
        sb.append(", pubSubject='").append(pubSubject).append('\'');
        sb.append(", pubYear='").append(pubYear).append('\'');
        sb.append(", pubQuarter='").append(pubQuarter).append('\'');
        sb.append(", maxPercentInNetAssets=").append(maxPercentInNetAssets);
        sb.append(", downAmt=").append(downAmt);
        sb.append(", archDate=").append(archDate);
        sb.append(", status='").append(status).append('\'');
        sb.append(", statusComment='").append(statusComment).append('\'');
        sb.append(", downStatus='").append(downStatus).append('\'');
        sb.append(", pubVersion='").append(pubVersion).append('\'');
        sb.append(", approvalId='").append(approvalId).append('\'');
        sb.append(", pubType='").append(pubType).append('\'');
        sb.append(", relId='").append(relId).append('\'');
        sb.append(", fileUrl='").append(fileUrl).append('\'');
        sb.append(", tranType='").append(tranType).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Publog == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Publog other = (Publog) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

