/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月17日 14:46:19
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;

@Entity
@Table(name = "bus_legal_inviolable_rights")
public class LegalInviolableRights extends BasePO {

    public static final String TABLE_ALIAS = "LegalInviolableRights";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_LEGAL_PID = "所属机构pid";
    public static final String ALIAS_LEGAL_REG_CODE = "所属机构编码";
    public static final String ALIAS_NAME = "名称";
    public static final String ALIAS_IS_SHARE = "权益类型  0代表股东 1代表被投资方";
    public static final String ALIAS_HOLDER_SCALE = "持股比例";
    public static final String ALIAS_ACTIVE_TIME = "生效时间";
    public static final String ALIAS_INVALID_TIME = "失效时间";
    public static final String ALIAS_COMMENT = "注释";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "legal_pid", nullable = false, length = 10)
    private Integer legalPid;

    @Column(name = "legal_reg_code", nullable = false, length = 32)
    private String legalRegCode;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "is_share", nullable = false, length = 10)
    private String isShare;

    @Column(name = "holder_scale", nullable = false, precision = 2, scale = 0)
    private Integer holderScale;

    @Column(name = "active_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date activeTime;

    @Column(name = "invalid_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date invalidTime;

    @Column(name = "comment", nullable = true, length = 500)
    private String comment;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public Integer getLegalPid() {
        return this.legalPid;
    }

    public void setLegalPid(Integer value) {
        this.legalPid = value;
    }


    public String getLegalRegCode() {
        return this.legalRegCode;
    }

    public void setLegalRegCode(String value) {
        this.legalRegCode = value;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }


    public String getIsShare() {
        return this.isShare;
    }

    public void setIsShare(String value) {
        this.isShare = value;
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


    public String getComment() {
        return this.comment;
    }

    public void setComment(String value) {
        this.comment = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("legalInviolableRights[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("legalPid = ").append(legalPid).append(", \n")
                .append("legalRegCode = ").append(legalRegCode).append(", \n")
                .append("name = ").append(name).append(", \n")
                .append("isShare = ").append(isShare).append(", \n")
                .append("holderScale = ").append(holderScale).append(", \n")
                .append("activeTime = ").append(activeTime).append(", \n")
                .append("invalidTime = ").append(invalidTime).append(", \n")
                .append("comment = ").append(comment).append(", \n")
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
        if (obj instanceof LegalInviolableRights == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        LegalInviolableRights other = (LegalInviolableRights) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

