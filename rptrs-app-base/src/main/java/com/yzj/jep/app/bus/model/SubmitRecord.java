/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月30日 17:26:33
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
@Table(name = "bus_submit_record")
public class SubmitRecord extends BasePO {

    public static final String TABLE_ALIAS = "报送记录表";
    public static final String ALIAS_PID = "自增长主键";
    public static final String ALIAS_BATCH_NO = "批次号";
    public static final String ALIAS_FROM_PID = "数据所指pid";
    public static final String ALIAS_SUBMIT_TYPE = "报送类型";
    public static final String ALIAS_IS_SUCCESS = "校验结果";
    public static final String ALIAS_FAIL_CAUSE = "校验信息";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "batch_no", nullable = true, length = 20)
    private String batchNo;

    @Column(name = "from_pid", nullable = true, length = 11)
    private String fromPid;

    @Column(name = "submit_type", nullable = true, length = 10)
    private String submitType;

    @Column(name = "is_success", nullable = true, length = 10)
    private String isSuccess;

    @Column(name = "fail_cause", nullable = true, length = 2000)
    private String failCause;
    //columns END
    @Column(name = "from_type", nullable = true, length = 255)
    private String fromType;
    //columns END

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getBatchNo() {
        return this.batchNo;
    }

    public void setBatchNo(String value) {
        this.batchNo = value;
    }


    public String getFromPid() {
        return this.fromPid;
    }

    public void setFromPid(String value) {
        this.fromPid = value;
    }


    public String getSubmitType() {
        return this.submitType;
    }

    public void setSubmitType(String value) {
        this.submitType = value;
    }


    public String getIsSuccess() {
        return this.isSuccess;
    }

    public void setIsSuccess(String value) {
        this.isSuccess = value;
    }


    public String getFailCause() {
        return this.failCause;
    }

    public void setFailCause(String value) {
        this.failCause = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("submitRecord[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("batchNo = ").append(batchNo).append(", \n")
                .append("fromPid = ").append(fromPid).append(", \n")
                .append("submitType = ").append(submitType).append(", \n")
                .append("isSuccess = ").append(isSuccess).append(", \n")
                .append("failCause = ").append(failCause).append(", \n")
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
        if (obj instanceof SubmitRecord == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SubmitRecord other = (SubmitRecord) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

