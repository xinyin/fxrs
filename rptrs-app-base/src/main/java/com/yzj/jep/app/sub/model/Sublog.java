/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月05日 10:57:27
 *
 */

package com.yzj.jep.app.sub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "sub_sublog")
public class Sublog extends BasePO {

    public static final String TABLE_ALIAS = "Sublog";
    public static final String ALIAS_PID = "pid";
    public static final String ALIAS_BATCH_DECIMAL = "batchDecimal";
    public static final String ALIAS_FILE_NAME = "fileName";
    public static final String ALIAS_SUB_TYPE = "subType";
    public static final String ALIAS_SUB_YEAR = "subYear";
    public static final String ALIAS_SUB_QUARTER = "subQuarter";
    public static final String ALIAS_MAX_PERCENT_IN_NET_ASSETS = "maxPercentInNetAssets";
    public static final String ALIAS_DOWN_AMT = "downAmt";
    public static final String ALIAS_STATUS = "状态";
    public static final String ALIAS_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_DOWN_STATUS = "下载状态";
    public static final String ALIAS_SUB_ORG = "报送机构";
    public static final String ALIAS_OPERATOR_NAME = "操作员";
    public static final String ALIAS_APPROVALID = "审批id";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "batch_decimal", nullable = false, length = 100)
    private String batchDecimal;

    @Column(name = "file_name", nullable = false, length = 100)
    private String fileName;

    @Column(name = "sub_type", nullable = false, length = 10)
    private String subType;

    @Column(name = "sub_year", nullable = false, length = 10)
    private String subYear;

    @Column(name = "sub_quarter", nullable = false, length = 10)
    private String subQuarter;

    @Column(name = "max_percentIn_net_assets", nullable = false, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maxPercentInNetAssets;

    @Column(name = "down_amt", nullable = true, precision = 65, scale = 0)
    private Integer downAmt;

    //报送状态
    @Column(name = "status", nullable = true, precision = 65, scale = 0)
    private String status;

    @Column(name = "status_comment", nullable = true, precision = 65, scale = 0)
    private String statusComment;

    //下载状态
    @Column(name = "down_status", nullable = true, precision = 65, scale = 0)
    private String downStatus;

    @Column(name = "sub_org", nullable = true, precision = 65, scale = 0)
    private String subOrg;

    @Column(name = "operator_name", nullable = true, precision = 65, scale = 0)
    private String operatorName;

    @Column(name = "approval_id", nullable = true, precision = 65, scale = 0)
    private String approvalId;

    /**
     * 推送日期
     */
    @Column(name = "SUBMISSION_DATE", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date submissionDate;

    @Column(name = "rel_id", nullable = true, length = 11)
    private int relId;

    @Column(name = "tran_type", nullable = true, length = 11)
    private String tranType;

    @Column(name = "file_url", nullable = true, length = 128)
    private String fileUrl;

    //columns END operator_name


    public void setSubmissionDateString(String value){
        if (value != null){
            setSubmissionDate(DateUtils.parseDate(value));
        }
    }

    public String getSubmissionDateString(){
        if (Optional.ofNullable(submissionDate).isPresent()) {
            return DateUtils.formatDate(submissionDate);
        }
        return "";
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getBatchDecimal() {
        return this.batchDecimal;
    }

    public void setBatchDecimal(String value) {
        this.batchDecimal = value;
    }


    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String value) {
        this.fileName = value;
    }


    public String getSubType() {
        return this.subType;
    }

    public void setSubType(String value) {
        this.subType = value;
    }


    public String getSubYear() {
        return this.subYear;
    }

    public void setSubYear(String value) {
        this.subYear = value;
    }


    public String getSubQuarter() {
        return this.subQuarter;
    }

    public void setSubQuarter(String value) {
        this.subQuarter = value;
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    public String getDownStatus() {
        return downStatus;
    }

    public void setDownStatus(String downStatus) {
        this.downStatus = downStatus;
    }

    public String getSubOrg() {
        return subOrg;
    }

    public void setSubOrg(String subOrg) {
        this.subOrg = subOrg;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public int getRelId() {
        return relId;
    }

    public void setRelId(int relId) {
        this.relId = relId;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "Sublog{" +
                "pid=" + pid +
                ", batchDecimal='" + batchDecimal + '\'' +
                ", fileName='" + fileName + '\'' +
                ", subType='" + subType + '\'' +
                ", subYear='" + subYear + '\'' +
                ", subQuarter='" + subQuarter + '\'' +
                ", maxPercentInNetAssets=" + maxPercentInNetAssets +
                ", downAmt=" + downAmt +
                ", status='" + status + '\'' +
                ", statusComment='" + statusComment + '\'' +
                ", downStatus='" + downStatus + '\'' +
                ", subOrg='" + subOrg + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", approvalId='" + approvalId + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sublog == false) {return false;}
        if (this == obj) {return true;}
        Sublog other = (Sublog) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

