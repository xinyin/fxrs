/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月16日 17:10:19
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;


@Entity
@Table(name = "bus_natural_inviolable_rights")
public class NaturalInviolableRights extends BasePO {

    public static final String TABLE_ALIAS = "NaturalInviolableRights";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_NATURAL_PID = "所属自然人pid";
    public static final String ALIAS_NATURAL_REG_CODE = "所属自然人登记编码";
    public static final String ALIAS_COMPANY_NAME = "单位名称";
    public static final String ALIAS_POSITION = "职位";
    public static final String ALIAS_INVESTEE = "被投资方";
    public static final String ALIAS_HOLDER_SCALE = "持股比例";
    public static final String ALIAS_ACTIVE_TIME = "生效时间";
    public static final String ALIAS_INVALID_TIME = "失效时间";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "natural_pid", nullable = false, length = 10)
    private Integer naturalPid;

    @Column(name = "natural_reg_code", nullable = false, length = 32)
    private String naturalRegCode;

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    @Column(name = "position", nullable = false, length = 128)
    private String position;

    @Column(name = "investee", nullable = false, length = 128)
    private String investee;

    @Column(name = "holder_scale", nullable = false, precision = 2, scale = 0)
    private Integer holderScale;

    @Column(name = "active_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date activeTime;

    @Column(name = "invalid_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date invalidTime;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getNaturalPid() {
        return this.naturalPid;
    }

    public void setNaturalPid(Integer value) {
        this.naturalPid = value;
    }


    public String getNaturalRegCode() {
        return this.naturalRegCode;
    }

    public void setNaturalRegCode(String value) {
        this.naturalRegCode = value;
    }


    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String value) {
        this.companyName = value;
    }


    public String getPosition() {
        return this.position;
    }

    public void setPosition(String value) {
        this.position = value;
    }


    public String getInvestee() {
        return this.investee;
    }

    public void setInvestee(String value) {
        this.investee = value;
    }


    public Integer getHolderScale() {
        return this.holderScale;
    }

    public void setHolderScale(Integer value) {
        this.holderScale = value;
    }


    public Date getActiveTime() {
        return this.activeTime;
    }

    public void setActiveTime(Date value) {
        this.activeTime = value;
    }


    public Date getInvalidTime() {
        return this.invalidTime;
    }

    public void setInvalidTime(Date value) {
        this.invalidTime = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("naturalInviolableRights[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("naturalPid = ").append(naturalPid).append(", \n")
                .append("naturalRegCode = ").append(naturalRegCode).append(", \n")
                .append("companyName = ").append(companyName).append(", \n")
                .append("position = ").append(position).append(", \n")
                .append("investee = ").append(investee).append(", \n")
                .append("holderScale = ").append(holderScale).append(", \n")
                .append("activeTime = ").append(activeTime).append(", \n")
                .append("invalidTime = ").append(invalidTime).append(", \n")
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
        if (obj instanceof NaturalInviolableRights == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        NaturalInviolableRights other = (NaturalInviolableRights) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

