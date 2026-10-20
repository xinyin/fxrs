/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月30日 17:26:31
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


import java.util.Date;

@Entity
@Table(name = "bus_submit_batch")
public class DocSubmit extends BasePO {

    public static final String TABLE_ALIAS = "报送批次表";
    public static final String ALIAS_PID = "自增长主键";
    public static final String ALIAS_FILE_NAME = "文件名称";
    public static final String ALIAS_FILE_SUFFIX = "文件后缀";
    public static final String ALIAS_SUBMIT_ORG = "报送机构";
    public static final String ALIAS_YEAR = "年度";
    public static final String ALIAS_QUARTER = "季度";
    public static final String ALIAS_MONTH = "月份";
    public static final String ALIAS_START_DATE = "开始时间";
    public static final String ALIAS_END_DATE = "结束时间";
    public static final String ALIAS_DOWNLOAD_COUNT = "下载次数";
    public static final String ALIAS_GENERATE_TIME = "文件生成时间";
    public static final String ALIAS_SUBMIT_TYPE = "报送类型";
    public static final String ALIAS_FILE_PATH = "报送文件路径";
    public static final String ALIAS_BATCH_NO = "批次号";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "file_name", nullable = true, length = 200)
    private String fileName;

    @Column(name = "file_suffix", nullable = true, length = 10)
    private String fileSuffix;

    @Column(name = "submit_org", nullable = true, length = 20)
    private String submitOrg;

    @Column(name = "year", nullable = true, length = 10)
    private String year;

    @Column(name = "quarter", nullable = true, length = 10)
    private String quarter;

    @Column(name = "month", nullable = true, length = 10)
    private String month;

    @Column(name = "start_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "download_count", nullable = true, length = 10)
    private Integer downloadCount;

    @Column(name = "generate_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date generateTime;

    @Column(name = "submit_type", nullable = true, length = 10)
    private String submitType;

    @Column(name = "file_path", nullable = true, length = 200)
    private String filePath;

    @Column(name = "batch_no", nullable = true, length = 20)
    private String batchNo;
    //columns END
    @Column(name = "is_success", nullable = true, length = 10)
    private String isSuccess;

    @Column(name = "fail_cause", nullable = true, length = 2000)
    private String failCause;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getFailCause() {
        return failCause;
    }

    public void setFailCause(String failCause) {
        this.failCause = failCause;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String value) {
        this.fileName = value;
    }


    public String getFileSuffix() {
        return this.fileSuffix;
    }

    public void setFileSuffix(String value) {
        this.fileSuffix = value;
    }


    public String getSubmitOrg() {
        return this.submitOrg;
    }

    public void setSubmitOrg(String value) {
        this.submitOrg = value;
    }


    public String getYear() {
        return this.year;
    }

    public void setYear(String value) {
        this.year = value;
    }


    public String getQuarter() {
        return this.quarter;
    }

    public void setQuarter(String value) {
        this.quarter = value;
    }


    public String getMonth() {
        return this.month;
    }

    public void setMonth(String value) {
        this.month = value;
    }


    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date value) {
        this.startDate = value;
    }


    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date value) {
        this.endDate = value;
    }


    public Integer getDownloadCount() {
        return this.downloadCount;
    }

    public void setDownloadCount(Integer value) {
        this.downloadCount = value;
    }


    public Date getGenerateTime() {
        return this.generateTime;
    }

    public void setGenerateTime(Date value) {
        this.generateTime = value;
    }


    public String getSubmitType() {
        return this.submitType;
    }

    public void setSubmitType(String value) {
        this.submitType = value;
    }


    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String value) {
        this.filePath = value;
    }


    public String getBatchNo() {
        return this.batchNo;
    }

    public void setBatchNo(String value) {
        this.batchNo = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("submitBatch[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("fileName = ").append(fileName).append(", \n")
                .append("fileSuffix = ").append(fileSuffix).append(", \n")
                .append("submitOrg = ").append(submitOrg).append(", \n")
                .append("year = ").append(year).append(", \n")
                .append("quarter = ").append(quarter).append(", \n")
                .append("month = ").append(month).append(", \n")
                .append("startDate = ").append(startDate).append(", \n")
                .append("endDate = ").append(endDate).append(", \n")
                .append("downloadCount = ").append(downloadCount).append(", \n")
                .append("generateTime = ").append(generateTime).append(", \n")
                .append("submitType = ").append(submitType).append(", \n")
                .append("filePath = ").append(filePath).append(", \n")
                .append("batchNo = ").append(batchNo).append(", \n")
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
        if (obj instanceof DocSubmit == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        DocSubmit other = (DocSubmit) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

