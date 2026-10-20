/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月10日 18:04:42
 *
 */

package com.yzj.jep.app.fil.model;

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
@Table(name = "fil_rel")
public class FilRel extends BasePO {

    public static final String TABLE_ALIAS = "Rel";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_FIL_NUM = "备案编号";
    public static final String ALIAS_FLAG_PID = "备案标识pid";
    public static final String ALIAS_TO_PID = "关联关系pid";
    public static final String ALIAS_FROM_PID = "源主体pid";
    public static final String ALIAS_REL_CATEGORY = "关系类型";
    public static final String ALIAS_REL_CATEGORY_SUB = "关系子类型";
    public static final String ALIAS_FROM_TYPE = "源主体类型  PSN|个人 ORG|机构";
    public static final String ALIAS_TO_TYPE = "关联关系主体类型   PSN|个人 ORG|机构";
    public static final String ALIAS_SUSPECT_TYPE = "疑似关联类型";
    public static final String ALIAS_SUSPECT_PID = "疑似关联方pid";
    public static final String ALIAS_FROM_MEMO = "源主体显示名称";
    public static final String ALIAS_TO_MEMO = "关联关系主体显示名称";
    public static final String ALIAS_REGULATOR = "统计口径";
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
    public static final String ALIAS_FROM_ALIG_CODE = "from定位码";
    public static final String ALIAS_TO_ALIG_CODE = "to定位码";
    public static final String ALIAS_PA_REL_ID = "父级关系id";
    public static final String ALIAS_REL_TYPE = "关联关系类型 0-股东 1-关联方";
    public static final String ALIAS_REL_STATUS = "00 已归档  01 新增  02删除  03归档-删除";
    public static final String ALIAS_REL_PREDICATE = "关联关系判定";
    public static final String ALIAS_LIFT_REASON = "解除原因";
    public static final String ALIAS_IS_VALID = "isValid";
    public static final String ALIAS_REL_STATEMENT = "relStatement";
    public static final String ALIAS_EFFECTIVE_DATE = "effectiveDate";
    public static final String ALIAS_INEFFECTIVE_DATE = "ineffectiveDate";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "fil_num", nullable = true, length = 64)
    private String filNum;

    @Column(name = "flag_pid", nullable = true, length = 64)
    private String flagPid;

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

    @Column(name = "to_memo", nullable = true, length = 16)
    private String toMemo;

    @Column(name = "regulator", nullable = true, length = 16)
    private String regulator;

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

    @Column(name = "level", nullable = true, length = 10)
    private Integer level;

    @Column(name = "from_name", nullable = true, length = 16)
    private String fromName;

    @Column(name = "to_name", nullable = true, length = 16)
    private String toName;

    @Column(name = "from_alig_code", nullable = true, length = 10)
    private String fromAligCode;

    @Column(name = "to_alig_code", nullable = true, length = 10)
    private String toAligCode;

    @Column(name = "pa_rel_id", nullable = true, length = 10)
    private Integer paRelId;

    @Column(name = "rel_type", nullable = true, length = 1)
    private String relType;

    @Column(name = "rel_status", nullable = true, length = 10)
    private String relStatus;

    @Column(name = "rel_predicate", nullable = true, length = 255)
    private String relPredicate;

    @Column(name = "lift_reason", nullable = true, length = 512)
    private String liftReason;

    @Column(name = "isValid", nullable = true, length = 1)
    private String isValid;

    @Column(name = "relStatement", nullable = true, length = 255)
    private String relStatement;

    @Column(name = "effectiveDate", nullable = true, length = 12)
    private String effectiveDate;

    @Column(name = "ineffectiveDate", nullable = true, length = 12)
    private String ineffectiveDate;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getFilNum() {
        return this.filNum;
    }

    public void setFilNum(String value) {
        this.filNum = value;
    }

    public String getFlagPid() {
        return flagPid;
    }

    public void setFlagPid(String flagPid) {
        this.flagPid = flagPid;
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


    public String getSuspectType() {
        return this.suspectType;
    }

    public void setSuspectType(String value) {
        this.suspectType = value;
    }


    public String getSuspectPid() {
        return this.suspectPid;
    }

    public void setSuspectPid(String value) {
        this.suspectPid = value;
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


    public String getRegulator() {
        return this.regulator;
    }

    public void setRegulator(String value) {
        this.regulator = value;
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


    public String getFromAligCode() {
        return this.fromAligCode;
    }

    public void setFromAligCode(String value) {
        this.fromAligCode = value;
    }


    public String getToAligCode() {
        return this.toAligCode;
    }

    public void setToAligCode(String value) {
        this.toAligCode = value;
    }


    public Integer getPaRelId() {
        return this.paRelId;
    }

    public void setPaRelId(Integer value) {
        this.paRelId = value;
    }


    public String getRelType() {
        return this.relType;
    }

    public void setRelType(String value) {
        this.relType = value;
    }


    public String getRelStatus() {
        return this.relStatus;
    }

    public void setRelStatus(String value) {
        this.relStatus = value;
    }


    public String getRelPredicate() {
        return this.relPredicate;
    }

    public void setRelPredicate(String value) {
        this.relPredicate = value;
    }


    public String getLiftReason() {
        return this.liftReason;
    }

    public void setLiftReason(String value) {
        this.liftReason = value;
    }


    public String getIsValid() {
        return this.isValid;
    }

    public void setIsValid(String value) {
        this.isValid = value;
    }


    public String getRelStatement() {
        return this.relStatement;
    }

    public void setRelStatement(String value) {
        this.relStatement = value;
    }


    public String getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(String value) {
        this.effectiveDate = value;
    }


    public String getIneffectiveDate() {
        return this.ineffectiveDate;
    }

    public void setIneffectiveDate(String value) {
        this.ineffectiveDate = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("rel[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("filNum = ").append(filNum).append(", \n")
                .append("toPid = ").append(toPid).append(", \n")
                .append("fromPid = ").append(fromPid).append(", \n")
                .append("relCategory = ").append(relCategory).append(", \n")
                .append("relCategorySub = ").append(relCategorySub).append(", \n")
                .append("fromType = ").append(fromType).append(", \n")
                .append("toType = ").append(toType).append(", \n")
                .append("suspectType = ").append(suspectType).append(", \n")
                .append("suspectPid = ").append(suspectPid).append(", \n")
                .append("fromMemo = ").append(fromMemo).append(", \n")
                .append("toMemo = ").append(toMemo).append(", \n")
                .append("regulator = ").append(regulator).append(", \n")
                .append("relSort = ").append(relSort).append(", \n")
                .append("position = ").append(position).append(", \n")
                .append("relation = ").append(relation).append(", \n")
                .append("shareholdingStrand = ").append(shareholdingStrand).append(", \n")
                .append("shareholdingRatio = ").append(shareholdingRatio).append(", \n")
                .append("toPidShareholders = ").append(toPidShareholders).append(", \n")
                .append("isShow = ").append(isShow).append(", \n")
                .append("level = ").append(level).append(", \n")
                .append("fromName = ").append(fromName).append(", \n")
                .append("toName = ").append(toName).append(", \n")
                .append("fromAligCode = ").append(fromAligCode).append(", \n")
                .append("toAligCode = ").append(toAligCode).append(", \n")
                .append("paRelId = ").append(paRelId).append(", \n")
                .append("relType = ").append(relType).append(", \n")
                .append("relStatus = ").append(relStatus).append(", \n")
                .append("relPredicate = ").append(relPredicate).append(", \n")
                .append("liftReason = ").append(liftReason).append(", \n")
                .append("isValid = ").append(isValid).append(", \n")
                .append("relStatement = ").append(relStatement).append(", \n")
                .append("effectiveDate = ").append(effectiveDate).append(", \n")
                .append("ineffectiveDate = ").append(ineffectiveDate).append(", \n")
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
        if (obj instanceof FilRel == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        FilRel other = (FilRel) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

