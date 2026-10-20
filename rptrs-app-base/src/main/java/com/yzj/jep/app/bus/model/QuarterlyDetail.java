/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:39
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

import java.util.Date;

@Entity
@Table(name = "bus_quarterly_detail")
public class QuarterlyDetail extends BasePO {

    public static final String TABLE_ALIAS = "关联交易季报-关联交易明细表";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_REPORT_ID = "关联交易季报主键";
    public static final String ALIAS_TRANS_NUM = "交易编码";
    public static final String ALIAS_TRANS_TIME = "交易时间";
    public static final String ALIAS_TRANS_TYPE = "交易类型";
    public static final String ALIAS_TRANS_FUND = "交易金额";
    public static final String ALIAS_TRANS_TARGET = "交易标的";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "report_id", nullable = true, length = 10)
    private Integer reportId;

    @Column(name = "trans_num", nullable = true, length = 50)
    private String transNum;

    @Column(name = "trans_time", nullable = true, length = 10)
    @Temporal(TemporalType.DATE)
    private Date transTime;

    @Column(name = "trans_type", nullable = true, length = 20)
    private String transType;

    @Column(name = "trans_fund", nullable = true, precision = 10, scale = 0)
    private Long transFund;

    @Column(name = "trans_target", nullable = true, length = 100)
    private String transTarget;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getReportId() {
        return this.reportId;
    }

    public void setReportId(Integer value) {
        this.reportId = value;
    }


    public String getTransNum() {
        return this.transNum;
    }

    public void setTransNum(String value) {
        this.transNum = value;
    }


    public Date getTransTime() {
        return this.transTime;
    }

    public void setTransTime(Date value) {
        this.transTime = value;
    }


    public String getTransType() {
        return this.transType;
    }

    public void setTransType(String value) {
        this.transType = value;
    }


    public Long getTransFund() {
        return this.transFund;
    }

    public void setTransFund(Long value) {
        this.transFund = value;
    }


    public String getTransTarget() {
        return this.transTarget;
    }

    public void setTransTarget(String value) {
        this.transTarget = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("quarterlyDetail[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("reportId = ").append(reportId).append(", \n")
                .append("transNum = ").append(transNum).append(", \n")
                .append("transTime = ").append(transTime).append(", \n")
                .append("transType = ").append(transType).append(", \n")
                .append("transFund = ").append(transFund).append(", \n")
                .append("transTarget = ").append(transTarget).append(", \n")
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
        if (obj instanceof QuarterlyDetail == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        QuarterlyDetail other = (QuarterlyDetail) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

