/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 14:26:18
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

@Entity
@Table(name = "bus_rel_suspect")
public class RelSuspect extends BasePO {

    public static final String TABLE_ALIAS = "RelSuspect";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_TO_PID = "关联关系pid";
    public static final String ALIAS_FROM_PID = "源主体pid";
    public static final String ALIAS_REL_CATEGORY = "关系类型";
    public static final String ALIAS_REL_CATEGORY_SUB = "关系子类型";
    public static final String ALIAS_TO_NAME = "to关系名称";
    public static final String ALIAS_FROM_NAME = "from关系名称";
    public static final String ALIAS_FROM_TYPE = "源主体类型  PSN|个人 ORG|机构";
    public static final String ALIAS_TO_TYPE = "关联关系主体类型   PSN|个人 ORG|机构";
    public static final String ALIAS_FROM_MEMO = "源主体显示名称";
    public static final String ALIAS_TO_MEMO = "关联关系主体显示名称";
    public static final String ALIAS_RELATION = "亲属关系";
    public static final String ALIAS_SHAREHOLDING_RATIO = "持股比例";
    public static final String ALIAS_TO_PID_SHAREHOLDERS = "是否关联关系主体股东    0-否 1-是";
    public static final String ALIAS_IS_SHOW = "是否显示";
    public static final String ALIAS_LEVEL = "关系层级";
    public static final String ALIAS_LIFT_REASON = "解除原因";
    public static final String ALIAS_DUTY_CODE = "职位编码";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "to_pid", nullable = true, length = 32)
    private String toPid;

    @Column(name = "from_pid", nullable = true, length = 20)
    private String fromPid;

    @Column(name = "rel_category", nullable = true, length = 16)
    private String relCategory;

    @Column(name = "rel_category_sub", nullable = true, length = 16)
    private String relCategorySub;

    @Column(name = "to_name", nullable = true, length = 32)
    private String toName;

    @Column(name = "from_name", nullable = true, length = 20)
    private String fromName;

    @Column(name = "from_type", nullable = true, length = 16)
    private String fromType;

    @Column(name = "to_type", nullable = true, length = 16)
    private String toType;

    @Column(name = "from_memo", nullable = true, length = 16)
    private String fromMemo;

    @Column(name = "to_memo", nullable = true, length = 16)
    private String toMemo;

    @Column(name = "relation", nullable = true, length = 8)
    private String relation;

    @Column(name = "shareholding_ratio", nullable = true, length = 16)
    private Float shareholdingRatio;

    @Column(name = "to_pid_shareholders", nullable = true, length = 4)
    private String toPidShareholders;

    @Column(name = "is_show", nullable = true, length = 4)
    private String isShow;

    @Column(name = "level", nullable = true, length = 10)
    private Integer level;

    @Column(name = "lift_reason", nullable = true, length = 512)
    private String liftReason;

    @Column(name = "duty_code", nullable = true, length = 100)
    private String dutyCode;
    //columns END

    public String getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getToPid() {
        return this.toPid;
    }

    public void setToPid(String value) {
        this.toPid = value;
    }


    public String getFromPid() {
        return this.fromPid;
    }

    public void setFromPid(String value) {
        this.fromPid = value;
    }


    public String getRelCategory() {
        return this.relCategory;
    }

    public void setRelCategory(String value) {
        this.relCategory = value;
    }


    public String getFromType() {
        return this.fromType;
    }

    public void setFromType(String value) {
        this.fromType = value;
    }


    public String getToType() {
        return this.toType;
    }

    public void setToType(String value) {
        this.toType = value;
    }


    public String getFromMemo() {
        return this.fromMemo;
    }

    public void setFromMemo(String value) {
        this.fromMemo = value;
    }


    public String getToMemo() {
        return this.toMemo;
    }

    public void setToMemo(String value) {
        this.toMemo = value;
    }


    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String value) {
        this.relation = value;
    }


    public Float getShareholdingRatio() {
        return this.shareholdingRatio;
    }

    public void setShareholdingRatio(Float value) {
        this.shareholdingRatio = value;
    }


    public String getToPidShareholders() {
        return this.toPidShareholders;
    }

    public void setToPidShareholders(String value) {
        this.toPidShareholders = value;
    }


    public String getIsShow() {
        return this.isShow;
    }

    public void setIsShow(String value) {
        this.isShow = value;
    }


    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer value) {
        this.level = value;
    }


    public String getLiftReason() {
        return this.liftReason;
    }

    public void setLiftReason(String value) {
        this.liftReason = value;
    }

    public String getRelCategorySub() {
        return relCategorySub;
    }

    public void setRelCategorySub(String relCategorySub) {
        this.relCategorySub = relCategorySub;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    @Override
    public String toString() {
        return new StringBuilder("relSuspect[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("toPid = ").append(toPid).append(", \n")
                .append("fromPid = ").append(fromPid).append(", \n")
                .append("relCategory = ").append(relCategory).append(", \n")
                .append("fromType = ").append(fromType).append(", \n")
                .append("toType = ").append(toType).append(", \n")
                .append("fromMemo = ").append(fromMemo).append(", \n")
                .append("toMemo = ").append(toMemo).append(", \n")
                .append("relation = ").append(relation).append(", \n")
                .append("shareholdingRatio = ").append(shareholdingRatio).append(", \n")
                .append("toPidShareholders = ").append(toPidShareholders).append(", \n")
                .append("isShow = ").append(isShow).append(", \n")
                .append("level = ").append(level).append(", \n")
                .append("liftReason = ").append(liftReason).append(", \n")
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
        if (obj instanceof RelSuspect == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelSuspect other = (RelSuspect) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

