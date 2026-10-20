/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月16日 13:38:15
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;


@Entity
@Table(name = "bus_submission_file")
public class SubmissionFile extends BasePO {

    public static final String TABLE_ALIAS = "报送文件";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_FILE_TYPE = "报送文件类型";
    public static final String ALIAS_FILE_URL = "文件路径";
    public static final String ALIAS_FILE_NAME = "文件名称";
    public static final String ALIAS_SHOW_STATUS = "显示状态";
    public static final String ALIAS_APPROVE_STATUS = "审批状态";
    public static final String ALIAS_SUBMISSION_STATUS = "报送状态";
    public static final String ALIAS_TIME_ATTRIBUTE = "时间属性             ";
    public static final String ALIAS_REP_BATCH_NO = "报送批次号             ";
    public static final String ALIAS_REP_TIME = "发起报送时间             ";
    public static final String ALIAS_REP_PERSON = "报送人             ";
    public static final String ALIAS_REVIEWER = "复核人             ";
    public static final String ALIAS_REP_INFOR_FEEDBACK = "报送信息反馈             ";
    public static final String ALIAS_CORPORATE_BODY = "法人机构";
    public static final String ALIAS_REP_ORG = "报送机构";
    public static final String ALIAS_BUILD_FILE_TYPE = "生成文件类型(1、关联方信息2、重大关联交易3、季报4、年报5、月报6、G157、行内制度)";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "file_type", nullable = true, length = 0)
    private String fileType;

    @Column(name = "file_url", nullable = true, length = 200)
    private String fileUrl;

    @Column(name = "file_name", nullable = true, length = 20)
    private String fileName;

    @Column(name = "show_status", nullable = true, length = 10)
    private String showStatus;

    @Column(name = "approve_status", nullable = true, length = 10)
    private String approveStatus;

    @Column(name = "submission_status", nullable = true, length = 10)
    private String submissionStatus;

    @Column(name = "time_attribute", nullable = true, length = 20)
    private String timeAttribute;

    @Column(name = "rep_batch_no", nullable = true, length = 100)
    private String repBatchNo;

    @Column(name = "rep_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date repTime;

    @Column(name = "rep_person", nullable = true, length = 25)
    private String repPerson;

    @Column(name = "reviewer", nullable = true, length = 25)
    private String reviewer;

    @Column(name = "rep_infor_feedback", nullable = true, length = 255)
    private String repInforFeedback;

    @Column(name = "corporate_body", nullable = true, length = 100)
    private String corporateBody;

    @Column(name = "rep_org", nullable = true, length = 100)
    private String repOrg;

    @Column(name = "build_file_type", nullable = true, length = 11)
    private String buildFileType;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String value) {
        this.fileType = value;
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


    public String getShowStatus() {
        return this.showStatus;
    }

    public void setShowStatus(String value) {
        this.showStatus = value;
    }


    public String getApproveStatus() {
        return this.approveStatus;
    }

    public void setApproveStatus(String value) {
        this.approveStatus = value;
    }


    public String getSubmissionStatus() {
        return this.submissionStatus;
    }

    public void setSubmissionStatus(String value) {
        this.submissionStatus = value;
    }


    public String getTimeAttribute() {
        return this.timeAttribute;
    }

    public void setTimeAttribute(String value) {
        this.timeAttribute = value;
    }


    public String getRepBatchNo() {
        return this.repBatchNo;
    }

    public void setRepBatchNo(String value) {
        this.repBatchNo = value;
    }


    public Date getRepTime() {
        return this.repTime;
    }

    public void setRepTime(Date value) {
        this.repTime = value;
    }


    public String getRepPerson() {
        return this.repPerson;
    }

    public void setRepPerson(String value) {
        this.repPerson = value;
    }


    public String getReviewer() {
        return this.reviewer;
    }

    public void setReviewer(String value) {
        this.reviewer = value;
    }


    public String getRepInforFeedback() {
        return this.repInforFeedback;
    }

    public void setRepInforFeedback(String value) {
        this.repInforFeedback = value;
    }


    public String getCorporateBody() {
        return this.corporateBody;
    }

    public void setCorporateBody(String value) {
        this.corporateBody = value;
    }


    public String getRepOrg() {
        return this.repOrg;
    }

    public void setRepOrg(String value) {
        this.repOrg = value;
    }


    public String getBuildFileType() {
        return this.buildFileType;
    }

    public void setBuildFileType(String value) {
        this.buildFileType = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("submissionFile[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("fileType = ").append(fileType).append(", \n")
                .append("fileUrl = ").append(fileUrl).append(", \n")
                .append("fileName = ").append(fileName).append(", \n")
                .append("showStatus = ").append(showStatus).append(", \n")
                .append("approveStatus = ").append(approveStatus).append(", \n")
                .append("submissionStatus = ").append(submissionStatus).append(", \n")
                .append("timeAttribute = ").append(timeAttribute).append(", \n")
                .append("repBatchNo = ").append(repBatchNo).append(", \n")
                .append("repTime = ").append(repTime).append(", \n")
                .append("repPerson = ").append(repPerson).append(", \n")
                .append("reviewer = ").append(reviewer).append(", \n")
                .append("repInforFeedback = ").append(repInforFeedback).append(", \n")
                .append("corporateBody = ").append(corporateBody).append(", \n")
                .append("repOrg = ").append(repOrg).append(", \n")
                .append("buildFileType = ").append(buildFileType).append(", \n")
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
        if (obj instanceof SubmissionFile == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SubmissionFile other = (SubmissionFile) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

