/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月08日 19:00:45
 *
 */

package com.yzj.jep.app.bus.model;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bus_rel_pro_psnandorg")
public class RelPsnAndOrgOfPro extends BasePO {

    public static final String TABLE_ALIAS = "RelPsnAndOrgOfPro";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_TO_PID = "关联关系pid";
    public static final String ALIAS_FROM_PID = "源主体pid";
    public static final String ALIAS_REL_CATEGORY = "关系类型";
    public static final String ALIAS_REL_CATEGORY_SUB = "关系子类型";
    public static final String ALIAS_FROM_TYPE = "源主体类型  PSN|个人 ORG|机构";
    public static final String ALIAS_TO_TYPE = "关联关系主体类型   PSN|个人 ORG|机构";
    public static final String ALIAS_FROM_MEMO = "源主体显示名称";
    public static final String ALIAS_TO_MEMO = "关联关系主体显示名称";
    public static final String ALIAS_REL_SORT = "关系类别";
    public static final String ALIAS_POSITION = "职位";
    public static final String ALIAS_RELATION = "亲属关系";
    public static final String ALIAS_SHAREHOLDING_STRAND = "持股数量";
    public static final String ALIAS_SHAREHOLDING_RATIO = "持股比例";
    public static final String ALIAS_TO_PID_SHAREHOLDERS = "是否关联关系主体股东    0-否 1-是";
    public static final String ALIAS_IS_SHOW = "是否显示";
    public static final String ALIAS_LEVEL = "关系层级";
    public static final String ALIAS_FROM_NAME = "from关系名称";
    public static final String ALIAS_TO_NAME = "to关系名称";
    public static final String ALIAS_REL_TYPE = "关联关系类型 0-股东 1-关联方";
    public static final String ALIAS_LIFT_REASON = "解除原因";
    public static final String ALIAS_IS_VALID = "是否有效 1-有效 0-无效";
    public static final String ALIAS_REL_STATEMENT = "关联关系说明";
    public static final String ALIAS_EFFECTIVE_DATE = "生效日期";
    public static final String ALIAS_INEFFECTIVE_DATE = "失效日期";
    public static final String ALIAS_ACTIVE_TIME = "生效时间";
    public static final String ALIAS_INVALID_TIME = "失效时间";


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

    @Column(name = "rel_category_sub", nullable = true, length = 30)
    private String relCategorySub;

    @Column(name = "from_type", nullable = true, length = 16)
    private String fromType;

    @Column(name = "to_type", nullable = true, length = 16)
    private String toType;

    @Column(name = "suspect_type", nullable = true, length = 16)
    private String suspectType;

    @Column(name = "suspect_pid", nullable = true, length = 20)
    private String suspectPid;

    @Column(name = "from_memo", nullable = true, length = 16)
    private String fromMemo;

    @Column(name = "rel_status", nullable = true, length = 10)
    private String relStatus;


    @Column(name = "to_memo", nullable = true, length = 16)
    private String toMemo;

    @Column(name = "rel_sort", nullable = true, length = 16)
    private String relSort;

    @Column(name = "position", nullable = true, length = 30)
    private String position;

    @Column(name = "relation", nullable = true, length = 8)
    private String relation;

    @Column(name = "shareholding_strand", nullable = true, length = 20)
    private String shareholdingStrand;

    @Column(name = "shareholding_ratio", nullable = true, length = 16)
    private Float shareholdingRatio;

    @Column(name = "to_pid_shareholders", nullable = true, length = 4)
    private String toPidShareholders;

    @Column(name = "is_show", nullable = true, length = 4)
    private String isShow;

    @Column(name = "REL_LEVEL", nullable = true, length = 10)
    private Integer level;

    @Column(name = "from_name", nullable = true, length = 16)
    private String fromName;

    @Column(name = "to_name", nullable = true, length = 16)
    private String toName;

    @Column(name = "rel_type", nullable = true, length = 1)
    private String relType;

    @Column(name = "lift_reason", nullable = true, length = 512)
    private String liftReason;

    @Column(name = "regulator", nullable = true, length = 16)
    private String regulator;

    @Column(name = "from_alig_code", nullable = true, length = 10)
    private String fromAligCode;

    @Column(name = "to_alig_code", nullable = true, length = 10)
    private String toAligCode;

    @Column(name = "pa_rel_id", nullable = true, length = 10)
    private String paRelId;

    @Column(name = "isValid", nullable = true, length = 1)
    private String isValid;

    @Column(name = "relStatement", nullable = true, length = 255)
    private String relStatement;

    @Column(name = "effectiveDate", nullable = true, length = 12)
    private String effectiveDate;

    @Column(name = "ineffectiveDate", nullable = true, length = 12)
    private String ineffectiveDate;
    //columns END
    @Column(name = "active_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date activeTime;

    @Column(name = "invalid_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date invalidTime;

    @Column(name = "equity_share", nullable = true, length = 10)
    private String equityShare;

    @Transient
    private String activeTimeString;

    @Transient
    private String invalidTimeString;

    public String getActiveTimeString() {
        if (activeTime != null) {
            return DateUtils.formatDate(activeTime);
        }
        return "";
    }

    public void setActiveTimeString(String activeTimeString) {
        if (activeTimeString != null) {
            setActiveTime(DateUtils.parseDate(activeTimeString));
        }
    }

    public String getInvalidTimeString() {
        if (invalidTime != null) {
            return DateUtils.formatDate(invalidTime);
        }
        return "";
    }

    public void setInvalidTimeString(String invalidTimeString) {
        if (invalidTimeString != null) {
            setInvalidTime(DateUtils.parseDate(invalidTimeString));
        }
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getRelStatement() {
        return relStatement;
    }

    public void setRelStatement(String relStatement) {
        this.relStatement = relStatement;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getIneffectiveDate() {
        return ineffectiveDate;
    }

    public void setIneffectiveDate(String ineffectiveDate) {
        this.ineffectiveDate = ineffectiveDate;
    }

    public String getRelStatus() {
        return relStatus;
    }

    public void setRelStatus(String relStatus) {
        this.relStatus = relStatus;
    }

    public String getSuspectType() {
        return suspectType;
    }

    public void setSuspectType(String suspectType) {
        this.suspectType = suspectType;
    }

    public String getSuspectPid() {
        return suspectPid;
    }

    public void setSuspectPid(String suspectPid) {
        this.suspectPid = suspectPid;
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


    public String getRelCategorySub() {
        return this.relCategorySub;
    }

    public void setRelCategorySub(String value) {
        this.relCategorySub = value;
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


    public String getRelSort() {
        return this.relSort;
    }

    public void setRelSort(String value) {
        this.relSort = value;
    }


    public String getPosition() {
        return this.position;
    }

    public void setPosition(String value) {
        this.position = value;
    }


    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String value) {
        this.relation = value;
    }


    public String getShareholdingStrand() {
        return this.shareholdingStrand;
    }

    public void setShareholdingStrand(String value) {
        this.shareholdingStrand = value;
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


    public String getFromName() {
        return this.fromName;
    }

    public void setFromName(String value) {
        this.fromName = value;
    }


    public String getToName() {
        return this.toName;
    }

    public void setToName(String value) {
        this.toName = value;
    }


    public String getRelType() {
        return this.relType;
    }

    public void setRelType(String value) {
        this.relType = value;
    }


    public String getLiftReason() {
        return this.liftReason;
    }

    public void setLiftReason(String value) {
        this.liftReason = value;
    }

    public String getRegulator() {
        return regulator;
    }

    public void setRegulator(String regulator) {
        this.regulator = regulator;
    }

    public String getFromAligCode() {
        return fromAligCode;
    }

    public void setFromAligCode(String fromAligCode) {
        this.fromAligCode = fromAligCode;
    }

    public String getToAligCode() {
        return toAligCode;
    }

    public void setToAligCode(String toAligCode) {
        this.toAligCode = toAligCode;
    }

    public String getPaRelId() {
        return paRelId;
    }

    public void setPaRelId(String paRelId) {
        this.paRelId = paRelId;
    }

    public String getEquityShare() {
        return equityShare;
    }

    public void setEquityShare(String equityShare) {
        this.equityShare = equityShare;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RelPsnAndOrgOfPro{");
        sb.append("pid=").append(pid);
        sb.append(", toPid='").append(toPid).append('\'');
        sb.append(", fromPid='").append(fromPid).append('\'');
        sb.append(", relCategory='").append(relCategory).append('\'');
        sb.append(", relCategorySub='").append(relCategorySub).append('\'');
        sb.append(", fromType='").append(fromType).append('\'');
        sb.append(", toType='").append(toType).append('\'');
        sb.append(", suspectType='").append(suspectType).append('\'');
        sb.append(", suspectPid='").append(suspectPid).append('\'');
        sb.append(", fromMemo='").append(fromMemo).append('\'');
        sb.append(", relStatus='").append(relStatus).append('\'');
        sb.append(", toMemo='").append(toMemo).append('\'');
        sb.append(", relSort='").append(relSort).append('\'');
        sb.append(", position='").append(position).append('\'');
        sb.append(", relation='").append(relation).append('\'');
        sb.append(", shareholdingStrand='").append(shareholdingStrand).append('\'');
        sb.append(", shareholdingRatio=").append(shareholdingRatio);
        sb.append(", toPidShareholders='").append(toPidShareholders).append('\'');
        sb.append(", isShow='").append(isShow).append('\'');
        sb.append(", level=").append(level);
        sb.append(", fromName='").append(fromName).append('\'');
        sb.append(", toName='").append(toName).append('\'');
        sb.append(", relType='").append(relType).append('\'');
        sb.append(", liftReason='").append(liftReason).append('\'');
        sb.append(", regulator='").append(regulator).append('\'');
        sb.append(", fromAligCode='").append(fromAligCode).append('\'');
        sb.append(", toAligCode='").append(toAligCode).append('\'');
        sb.append(", paRelId='").append(paRelId).append('\'');
        sb.append(", isValid='").append(isValid).append('\'');
        sb.append(", relStatement='").append(relStatement).append('\'');
        sb.append(", effectiveDate='").append(effectiveDate).append('\'');
        sb.append(", ineffectiveDate='").append(ineffectiveDate).append('\'');
        sb.append(", activeTime=").append(activeTime);
        sb.append(", invalidTime=").append(invalidTime);
        sb.append(", equityShare='").append(equityShare).append('\'');
        sb.append(", activeTimeString='").append(activeTimeString).append('\'');
        sb.append(", invalidTimeString='").append(invalidTimeString).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RelPsnAndOrgOfPro == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelPsnAndOrgOfPro other = (RelPsnAndOrgOfPro) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

