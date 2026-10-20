/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月02日 14:16:57
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


import java.util.Date;

@Entity
@Table(name = "bus_termreminder")
public class TermReminder extends BasePO {

    public static final String TABLE_ALIAS = "Termreminder";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_TO_PID = "关联关系pid";
    public static final String ALIAS_FROM_PID = "源主体pid";
    public static final String ALIAS_REL_CATEGORY = "关系类型";
    public static final String ALIAS_REL_CATEGORY_SUB = "关系子类型";
    public static final String ALIAS_FROM_TYPE = "源主体类型  PSN|个人 ORG|机构";
    public static final String ALIAS_TO_TYPE = "关联关系主体类型   PSN|个人 ORG|机构";
    public static final String ALIAS_FROM_MEMO = "源主体显示名称";
    public static final String ALIAS_TO_MEMO = "关联关系主体显示名称";
    public static final String ALIAS_REGULATOR = "统计口径";
    public static final String ALIAS_REL_SORT = "关系类别";
    public static final String ALIAS_RELATION = "亲属关系";
    public static final String ALIAS_SHAREHOLDING_STRAND = "持股数量";
    public static final String ALIAS_SHAREHOLDING_RATIO = "持股比例";
    public static final String ALIAS_TO_PID_SHAREHOLDERS = "是否关联关系主体股东    0-否 1-是";
    public static final String ALIAS_IS_SHOW = "是否显示";
    public static final String ALIAS_IS_VALID = "isValid";
    public static final String ALIAS_FROM_NAME = "from关系名称";
    public static final String ALIAS_TO_NAME = "to关系名称";
    public static final String ALIAS_FROM_ALIG_CODE = "from定位码";
    public static final String ALIAS_TO_ALIG_CODE = "to定位码";
    public static final String ALIAS_PA_REL_ID = "父级关系id";
    public static final String ALIAS_REL_TYPE = "关联关系类型 0-股东 1-关联方";
    public static final String ALIAS_REL_STATUS = "00 已归档  01 新增  02删除  03归档-删除";
    public static final String ALIAS_REL_PREDICATE = "关联关系判定";
    public static final String ALIAS_REL_NAME = "关联方名称";
    public static final String ALIAS_REL_DATA_SOURCE = "数据来源";
    public static final String ALIAS_IDENTIFY_TYPE = "关联方证件类型";
    public static final String ALIAS_IDENTIFY_NUMBER = "关联方证件号码";
    public static final String ALIAS_WORK_HOURS = "任期时长";
    public static final String ALIAS_REMIND_TIME = "提醒时间";
    public static final String ALIAS_IS_ALREADY_REMIND = "是否已提醒 0-未提醒 1-已提醒";
    public static final String ALIAS_IS_NEED_REMIND = "是否需要要提醒 0-不需要 1-需要";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "rel_pid", nullable = false, length = 10)
    private Integer relPid;

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

    @Column(name = "from_memo", nullable = true, length = 16)
    private String fromMemo;

    @Column(name = "to_memo", nullable = true, length = 16)
    private String toMemo;

    @Column(name = "regulator", nullable = true, length = 16)
    private String regulator;

    @Column(name = "rel_sort", nullable = true, length = 16)
    private String relSort;

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

    @Column(name = "isValid", nullable = true, length = 1)
    private String isValid;

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

    @Column(name = "rel_name", nullable = true, length = 16)
    private String relName;

    @Column(name = "rel_dataSource", nullable = true, length = 20)
    private String relDataSource;

    @Column(name = "identify_type", nullable = true, length = 50)
    private String identifyType;

    @Column(name = "identify_number", nullable = true, length = 20)
    private String identifyNumber;

    @Column(name = "work_hours", nullable = true, length = 10)
    private String workHours;

    @Column(name = "remind_time", nullable = true, length = 19)
    private String remindTime;

    @Column(name = "IS_ALREADY_REMIND", nullable = true, length = 1)
    private String isAlreadyRemind;

    @Column(name = "IS_NEED_REMIND", nullable = true, length = 1)
    private String isNeedRemind;

    @Column(name = "MODIFIED_TIME_", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date modifiedTime;

    @Column(name = "DELFLAG_", nullable = true, length = 1)
    private String delflag;

    @Column(name = "mger_org", nullable = true, length = 50)
    private String mgerOrg;


//columns END


    public Integer getRelPid() {
        return relPid;
    }

    public void setRelPid(Integer relPid) {
        this.relPid = relPid;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    @Override
    public Date getModifiedTime() {
        return modifiedTime;
    }

    @Override
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String getDelflag() {
        return delflag;
    }

    @Override
    public void setDelflag(String delflag) {
        this.delflag = delflag;
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


    public String getIsValid() {
        return this.isValid;
    }

    public void setIsValid(String value) {
        this.isValid = value;
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


    public String getRelName() {
        return this.relName;
    }

    public void setRelName(String value) {
        this.relName = value;
    }


    public String getRelDataSource() {
        return this.relDataSource;
    }

    public void setRelDataSource(String value) {
        this.relDataSource = value;
    }


    public String getIdentifyType() {
        return this.identifyType;
    }

    public void setIdentifyType(String value) {
        this.identifyType = value;
    }


    public String getIdentifyNumber() {
        return this.identifyNumber;
    }

    public void setIdentifyNumber(String value) {
        this.identifyNumber = value;
    }


    public String getWorkHours() {
        return this.workHours;
    }

    public void setWorkHours(String value) {
        this.workHours = value;
    }

    public String getIsAlreadyRemind() {
        return this.isAlreadyRemind;
    }

    public void setIsAlreadyRemind(String value) {
        this.isAlreadyRemind = value;
    }


    public String getIsNeedRemind() {
        return this.isNeedRemind;
    }

    public void setIsNeedRemind(String value) {
        this.isNeedRemind = value;
    }

    public String getMgerOrg() {
        return mgerOrg;
    }

    public void setMgerOrg(String mgerOrg) {
        this.mgerOrg = mgerOrg;
    }

    @Override
    public String toString() {
        return "TermReminder{" +
                "pid=" + pid +
                ", relPid=" + relPid +
                ", toPid='" + toPid + '\'' +
                ", fromPid='" + fromPid + '\'' +
                ", relCategory='" + relCategory + '\'' +
                ", relCategorySub='" + relCategorySub + '\'' +
                ", fromType='" + fromType + '\'' +
                ", toType='" + toType + '\'' +
                ", fromMemo='" + fromMemo + '\'' +
                ", toMemo='" + toMemo + '\'' +
                ", regulator='" + regulator + '\'' +
                ", relSort='" + relSort + '\'' +
                ", relation='" + relation + '\'' +
                ", shareholdingStrand='" + shareholdingStrand + '\'' +
                ", shareholdingRatio=" + shareholdingRatio +
                ", toPidShareholders='" + toPidShareholders + '\'' +
                ", isShow='" + isShow + '\'' +
                ", isValid='" + isValid + '\'' +
                ", fromName='" + fromName + '\'' +
                ", toName='" + toName + '\'' +
                ", fromAligCode='" + fromAligCode + '\'' +
                ", toAligCode='" + toAligCode + '\'' +
                ", paRelId=" + paRelId +
                ", relType='" + relType + '\'' +
                ", relStatus='" + relStatus + '\'' +
                ", relPredicate='" + relPredicate + '\'' +
                ", relName='" + relName + '\'' +
                ", relDataSource='" + relDataSource + '\'' +
                ", identifyType='" + identifyType + '\'' +
                ", identifyNumber='" + identifyNumber + '\'' +
                ", workHours='" + workHours + '\'' +
                ", remindTime='" + remindTime + '\'' +
                ", isAlreadyRemind='" + isAlreadyRemind + '\'' +
                ", isNeedRemind='" + isNeedRemind + '\'' +
                ", modifiedTime=" + modifiedTime +
                ", delflag='" + delflag + '\'' +
                ", mgerOrg='" + mgerOrg + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TermReminder == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TermReminder other = (TermReminder) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}
