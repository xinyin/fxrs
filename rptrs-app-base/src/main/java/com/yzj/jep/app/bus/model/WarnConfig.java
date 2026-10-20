/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 10:02:38
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
@Table(name = "bus_warn_config")
public class WarnConfig extends BasePO {

    public static final String TABLE_ALIAS = "内部交易额度预警规则";
    public static final String ALIAS_PID = "pid";
    public static final String ALIAS_WARNING_INDEX = "预警指标";
    public static final String ALIAS_1LWARN_THRESHOLD = "一级预警阈值";
    public static final String ALIAS_2LWARN_THRESHOLD = "二级预警阈值";
    public static final String ALIAS_3LWARN_THRESHOLD = "三级预警阈值";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "warning_index", nullable = true, length = 1)
    private String warningIndex;

    @Column(name = "1Lwarn_threshold", nullable = true, length = 10)
    private Integer warnThreshold1l;

    @Column(name = "2Lwarn_threshold", nullable = true, length = 10)
    private Integer warnThreshold2l;

    @Column(name = "3Lwarn_threshold", nullable = true, length = 10)
    private Integer warnThreshold3l;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getWarningIndex() {
        return this.warningIndex;
    }

    public void setWarningIndex(String value) {
        this.warningIndex = value;
    }

    public Integer getWarnThreshold1l() {
        return warnThreshold1l;
    }

    public void setWarnThreshold1l(Integer warnThreshold1l) {
        this.warnThreshold1l = warnThreshold1l;
    }

    public Integer getWarnThreshold2l() {
        return warnThreshold2l;
    }

    public void setWarnThreshold2l(Integer warnThreshold2l) {
        this.warnThreshold2l = warnThreshold2l;
    }

    public Integer getWarnThreshold3l() {
        return warnThreshold3l;
    }

    public void setWarnThreshold3l(Integer warnThreshold3l) {
        this.warnThreshold3l = warnThreshold3l;
    }

    @Override
    public String toString() {
        return new StringBuilder("warnConfig[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("warningIndex = ").append(warningIndex).append(", \n")
                .append("warnThreshold11 = ").append(warnThreshold1l).append(", \n")
                .append("2lwarnThreshold = ").append(warnThreshold2l).append(", \n")
                .append("3lwarnThreshold = ").append(warnThreshold3l).append(", \n")
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
        if (obj instanceof WarnConfig == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        WarnConfig other = (WarnConfig) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

