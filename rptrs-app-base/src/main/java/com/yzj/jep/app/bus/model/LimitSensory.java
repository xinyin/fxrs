/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月03日 11:00:21
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

@Entity
@Table(name = "bus_limit_sensory")
public class LimitSensory extends BasePO {

    public static final String TABLE_ALIAS = "LimitSensory";
    public static final String ALIAS_PID = "pid";
    public static final String ALIAS_SENSORY_INDEX = "阈值指标";
    public static final String ALIAS_TRADE_TYPE = "交易类型";
    public static final String ALIAS_BUSINESS_TYPE = "业务类型";
    public static final String ALIAS_BUS_LEVE = "业务级别";
    public static final String ALIAS_BUS_LEVE_NAME = "业务级别名称";
    public static final String ALIAS_PRE_LIMIT = "预计额度";
    public static final String ALIAS_LIMIT_RATE = "额度使用率";
    public static final String ALIAS_WARN_THRESHOLD1L = "一级预警阈值";
    public static final String ALIAS_WARN_THRESHOLD2L = "二级预警阈值";
    public static final String ALIAS_WARN_THRESHOLD3L = "三级预警阈值";
    public static final String ALIAS_IS_ACTIVE = "是否有效";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "sensory_index", nullable = true, length = 50)
    private String sensoryIndex;

    @Column(name = "tradeType", nullable = true, length = 20)
    private String tradeType;

    @Column(name = "businessType", nullable = true, length = 20)
    private String businessType;

    @Column(name = "bus_leve", nullable = true, length = 10)
    private String busLeve;

    @Column(name = "bus_leve_name", nullable = true, length = 20)
    private String busLeveName;

    @Column(name = "pre_limit", nullable = true, length = 64)
    private String preLimit;

    @Column(name = "limit_rate", nullable = true, length = 64)
    private String limitRate;

    @Column(name = "warn_threshold_1l", nullable = true, length = 10)
    private Integer warnThreshold1l;

    @Column(name = "warn_threshold_2l", nullable = true, length = 10)
    private Integer warnThreshold2l;

    @Column(name = "warn_threshold_3l", nullable = true, length = 10)
    private Integer warnThreshold3l;

    @Column(name = "is_active", nullable = true, length = 10)
    private String isActive;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getSensoryIndex() {
        return this.sensoryIndex;
    }

    public void setSensoryIndex(String value) {
        this.sensoryIndex = value;
    }


    public String getTradeType() {
        return this.tradeType;
    }

    public void setTradeType(String value) {
        this.tradeType = value;
    }


    public String getBusinessType() {
        return this.businessType;
    }

    public void setBusinessType(String value) {
        this.businessType = value;
    }


    public String getBusLeve() {
        return this.busLeve;
    }

    public void setBusLeve(String value) {
        this.busLeve = value;
    }


    public String getBusLeveName() {
        return this.busLeveName;
    }

    public void setBusLeveName(String value) {
        this.busLeveName = value;
    }


    public String getPreLimit() {
        return this.preLimit;
    }

    public void setPreLimit(String value) {
        this.preLimit = value;
    }


    public String getLimitRate() {
        return this.limitRate;
    }

    public void setLimitRate(String value) {
        this.limitRate = value;
    }


    public Integer getWarnThreshold1l() {
        return this.warnThreshold1l;
    }

    public void setWarnThreshold1l(Integer value) {
        this.warnThreshold1l = value;
    }


    public Integer getWarnThreshold2l() {
        return this.warnThreshold2l;
    }

    public void setWarnThreshold2l(Integer value) {
        this.warnThreshold2l = value;
    }


    public Integer getWarnThreshold3l() {
        return this.warnThreshold3l;
    }

    public void setWarnThreshold3l(Integer value) {
        this.warnThreshold3l = value;
    }


    public String getIsActive() {
        return this.isActive;
    }

    public void setIsActive(String value) {
        this.isActive = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("limitSensory[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("sensoryIndex = ").append(sensoryIndex).append(", \n")
                .append("tradeType = ").append(tradeType).append(", \n")
                .append("businessType = ").append(businessType).append(", \n")
                .append("busLeve = ").append(busLeve).append(", \n")
                .append("busLeveName = ").append(busLeveName).append(", \n")
                .append("preLimit = ").append(preLimit).append(", \n")
                .append("limitRate = ").append(limitRate).append(", \n")
                .append("warnThreshold1l = ").append(warnThreshold1l).append(", \n")
                .append("warnThreshold2l = ").append(warnThreshold2l).append(", \n")
                .append("warnThreshold3l = ").append(warnThreshold3l).append(", \n")
                .append("isActive = ").append(isActive).append(", \n")
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
        if (obj instanceof LimitSensory == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        LimitSensory other = (LimitSensory) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}
