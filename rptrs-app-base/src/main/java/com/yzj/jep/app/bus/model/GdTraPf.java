/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月02日 16:21:56
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
@Table(name = "bus_gd_tra_pf")
public class GdTraPf extends BasePO {

    public static final String TABLE_ALIAS = "GdTraPf";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_NAME = "派发方案名称";
    public static final String ALIAS_YEAR = "派发年份";
    public static final String ALIAS_CREATE_TIME = "文件生成时间";
    public static final String ALIAS_TOTAL_SHARE = "当前总股本";
    public static final String ALIAS_ALLOCATION_AMOUNT = "股利分配额";
    public static final String ALIAS_ALLOCATION_PROPORTION = "股利分配比例";
    public static final String ALIAS_CASH_BONUS = "现金分红";
    public static final String ALIAS_BONUS_ISSUE = "派送红股";
    public static final String ALIAS_STATUS = "审批状态";
    public static final String ALIAS_ROUTE = "路径";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @Column(name = "year", nullable = true, length = 10)
    private String year;

    @Column(name = "create_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    @Column(name = "total_share", nullable = true, length = 50)
    private String totalShare;

    @Column(name = "allocation_amount", nullable = true, length = 50)
    private String allocationAmount;

    @Column(name = "allocation_proportion", nullable = true, length = 10)
    private String allocationProportion;

    @Column(name = "cash_bonus", nullable = true, length = 50)
    private String cashBonus;

    @Column(name = "bonus_issue", nullable = true, length = 50)
    private String bonusIssue;

    @Column(name = "status", nullable = true, length = 255)
    private String status;

    @Column(name = "route", nullable = true, length = 100)
    private String route;
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


    public String getYear() {
        return this.year;
    }

    public void setYear(String value) {
        this.year = value;
    }


    @Override
    public Date getCreateTime() {
        return this.createTime;
    }

    @Override
    public void setCreateTime(Date value) {
        this.createTime = value;
    }


    public String getTotalShare() {
        return this.totalShare;
    }

    public void setTotalShare(String value) {
        this.totalShare = value;
    }


    public String getAllocationAmount() {
        return this.allocationAmount;
    }

    public void setAllocationAmount(String value) {
        this.allocationAmount = value;
    }


    public String getAllocationProportion() {
        return this.allocationProportion;
    }

    public void setAllocationProportion(String value) {
        this.allocationProportion = value;
    }


    public String getCashBonus() {
        return this.cashBonus;
    }

    public void setCashBonus(String value) {
        this.cashBonus = value;
    }


    public String getBonusIssue() {
        return this.bonusIssue;
    }

    public void setBonusIssue(String value) {
        this.bonusIssue = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public String getRoute() {
        return this.route;
    }

    public void setRoute(String value) {
        this.route = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("gdTraPf[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("name = ").append(name).append(", \n")
                .append("year = ").append(year).append(", \n")
                .append("createTime = ").append(createTime).append(", \n")
                .append("totalShare = ").append(totalShare).append(", \n")
                .append("allocationAmount = ").append(allocationAmount).append(", \n")
                .append("allocationProportion = ").append(allocationProportion).append(", \n")
                .append("cashBonus = ").append(cashBonus).append(", \n")
                .append("bonusIssue = ").append(bonusIssue).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("route = ").append(route).append(", \n")
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
        if (obj instanceof GdTraPf == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GdTraPf other = (GdTraPf) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

