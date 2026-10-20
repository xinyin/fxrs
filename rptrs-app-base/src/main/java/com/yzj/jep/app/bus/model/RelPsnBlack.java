/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月24日 16:43:06
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
@Table(name = "bus_rel_psn_black")
public class RelPsnBlack extends BasePO {

    public static final String TABLE_ALIAS = "RelPsnBlack";
    public static final String ALIAS_PID = "自增长主键";
    public static final String ALIAS_PER_ID = "自然人id";
    public static final String ALIAS_BLANK_TYPE = "黑名单类型";
    public static final String ALIAS_LIMIT_TYPE = "limitType";
    public static final String ALIAS_LIMIT_REASON = "限制原因";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "per_id", nullable = true, length = 10)
    private Integer perId;

    @Column(name = "blank_type", nullable = true, length = 16)
    private String blankType;

    @Column(name = "limit_type", nullable = true, length = 32)
    private String limitType;

    @Column(name = "limit_reason", nullable = true, length = 255)
    private String limitReason;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getPerId() {
        return this.perId;
    }

    public void setPerId(Integer value) {
        this.perId = value;
    }


    public String getBlankType() {
        return this.blankType;
    }

    public void setBlankType(String value) {
        this.blankType = value;
    }


    public String getLimitType() {
        return this.limitType;
    }

    public void setLimitType(String value) {
        this.limitType = value;
    }


    public String getLimitReason() {
        return this.limitReason;
    }

    public void setLimitReason(String value) {
        this.limitReason = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("relPsnBlack[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("perId = ").append(perId).append(", \n")
                .append("blankType = ").append(blankType).append(", \n")
                .append("limitType = ").append(limitType).append(", \n")
                .append("limitReason = ").append(limitReason).append(", \n")
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
        if (obj instanceof RelPsnBlack == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelPsnBlack other = (RelPsnBlack) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

