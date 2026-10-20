/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 10:02:38
 *
 */

package com.yzj.jep.app.bus.model;

import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name = "bus_warn_deploy")
public class WarnDeploy extends BasePO {

    public static final String TABLE_ALIAS = "预警配置表";
    public static final String ALIAS_PID = "pid";
    public static final String ALIAS_WARN_LEVEL = "预警级别";
    public static final String ALIAS_TRADE_TYPE = "交易类型";
    public static final String ALIAS_BUSINESS_TYPE = "业务类型";
    public static final String ALIAS_BUS_WAY_ID = "通知方式";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "warn_level", nullable = true, length = 1)
    private String warnLevel;

    @Column(name = "trade_type", nullable = true, length = 10)
    private Integer tradeType;

    @Column(name = "business_type", nullable = true, length = 10)
    private Integer businessType;

    @Column(name = "bus_way_id", nullable = true, length = 10)
    private String wayId;
    //columns END


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(String warnLevel) {
        this.warnLevel = warnLevel;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getWayId() {
        return wayId;
    }

    public void setWayId(String wayId) {
        this.wayId = wayId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WarnDeploy that = (WarnDeploy) o;
        return Objects.equals(pid, that.pid) &&
                Objects.equals(warnLevel, that.warnLevel) &&
                Objects.equals(tradeType, that.tradeType) &&
                Objects.equals(businessType, that.businessType) &&
                Objects.equals(wayId, that.wayId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, warnLevel, tradeType, businessType, wayId);
    }

    @Override
    public String toString() {
        return "WarnDeploy{" +
                "pid=" + pid +
                ", warnLevel='" + warnLevel + '\'' +
                ", tradeType=" + tradeType +
                ", businessType=" + businessType +
                ", wayId=" + wayId +
                '}';
    }
}

