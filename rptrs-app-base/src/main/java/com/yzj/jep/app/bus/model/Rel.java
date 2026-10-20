package com.yzj.jep.app.bus.model;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bus_rel")
public class Rel extends BasePO {
    public static final String TABLE_ALIAS = "关联方关系表";
    public static final String ALIAS_PID = "自增主键";
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
    public static final String ALIAS_IS_VALID = "是否有效";
    public static final String ALIAS_REL_STATEMENT = "关联关系说明";
    public static final String ALIAS_EFFECTIVE_DATE = "实体生效时间";
    public static final String ALIAS_INEFFECTIVE_DATE = "实体失效时间";
    public static final String ALIAS_ACTIVE_TIME = "生效时间";
    public static final String ALIAS_INVALID_TIME = "失效时间";
    public static final String ALIAS_PLEDGE_RATIO = "质押股权比例";
    public static final String ALIAS_EQUITY_SHARE = "与前一层是否有权益份额 是，对前一层持有 是，被前一层持有 否";
    public static final String ALIAS_FAILURE_STATUS = "确认失效状态 1 待确认 2 已确认";
    public static final String ALIAS_ITEM_DESCRIPTION = "事项说明 0新增 1编辑";

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

    @Column(name = "level_", nullable = true, length = 10)
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

    @Column(name = "relStatement", nullable = true, length = 1000)
    private String relStatement;

    @Column(name = "effectiveDate", nullable = true, length = 22)
    private String effectiveDate;

    @Column(name = "ineffectiveDate", nullable = true, length = 22)
    private String ineffectiveDate;

    @Column(name = "active_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date activeTime;

    @Column(name = "invalid_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date invalidTime;

    @Column(name = "pledge_ratio", nullable = true, length = 10)
    private String pledgeRatio;

    @Column(name = "equity_share", nullable = true, length = 10)
    private String equityShare;

    //与前一层的关系
    @Column(name = "rel_psn_org", nullable = true, length = 20)
    private String relPsnOrg;
    //确认失效的状态
    @Column(name = "failure_status",nullable = true,length = 20)
    private String failureStatus;



    @Column(name = "item_description",nullable = true,length = 20)
    private String itemDescription;

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @Transient
    private String shipString;

    @Transient
    private String describeString;

    //经办部门
    @Column(name = "TRANSACTION_DEP", nullable = true, length = 50)
    private String transactionDep;

    //经办人
    @Column(name = "OPERATOR", nullable = true, length = 100)
    private String operator;


    @Column(name = "from_identify_number", nullable = true, length = 20)
    private String fromIdentifyNumber;

    @Column(name = "to_identify_number", nullable = true, length = 20)
    private String toIdentifyNumber;
    //关联关系审批状态：1、待补录；2、待提交；3、审批中；4、审批退回；5、已归档
    @Column(name = "approval_status", nullable = true, length = 20)
    private String approvalStatus;

    //关系类型（2021.8.9）
    @Column(name = "relation_type", nullable = true, length = 30)
    private String relationType;

    @Column(name = "proc_inst_id", nullable = true, length = 10)
    private String procInstId;


    @Column(name = "record_number")
    private String recordNumber;

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }
    //columns END

    @Transient
    private String activeTimeString;

    @Transient
    private String invalidTimeString;

    public String getTransactionDep() {
        return transactionDep;
    }

    public void setTransactionDep(String transactionDep) {
        this.transactionDep = transactionDep;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getShipString() {
        return shipString;
    }

    public void setShipString(String shipString) {
        this.shipString = shipString;
    }

    public String getDescribeString() {
        return describeString;
    }

    public void setDescribeString(String describeString) {
        this.describeString = describeString;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

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


    public String getPledgeRatio() {
        return this.pledgeRatio;
    }

    public void setPledgeRatio(String value) {
        this.pledgeRatio = value;
    }


    public String getEquityShare() {
        return this.equityShare;
    }

    public void setEquityShare(String value) {
        this.equityShare = value;
    }


    public String getFromIdentifyNumber() {
        return this.fromIdentifyNumber;
    }

    public void setFromIdentifyNumber(String value) {
        this.fromIdentifyNumber = value;
    }


    public String getToIdentifyNumber() {
        return this.toIdentifyNumber;
    }

    public void setToIdentifyNumber(String value) {
        this.toIdentifyNumber = value;
    }


    public String getRelPsnOrg() {
        return this.relPsnOrg;
    }

    public void setRelPsnOrg(String value) {
        this.relPsnOrg = value;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getFailureStatus() {
        return failureStatus;
    }

    public void setFailureStatus(String failureStatus) {
        this.failureStatus = failureStatus;
    }

    @Override
    public String toString() {
        return "Rel{" +
                "pid=" + pid +
                ", toPid='" + toPid + '\'' +
                ", fromPid='" + fromPid + '\'' +
                ", relCategory='" + relCategory + '\'' +
                ", relCategorySub='" + relCategorySub + '\'' +
                ", fromType='" + fromType + '\'' +
                ", toType='" + toType + '\'' +
                ", suspectType='" + suspectType + '\'' +
                ", suspectPid='" + suspectPid + '\'' +
                ", fromMemo='" + fromMemo + '\'' +
                ", toMemo='" + toMemo + '\'' +
                ", regulator='" + regulator + '\'' +
                ", relSort='" + relSort + '\'' +
                ", position='" + position + '\'' +
                ", relation='" + relation + '\'' +
                ", shareholdingStrand='" + shareholdingStrand + '\'' +
                ", shareholdingRatio=" + shareholdingRatio +
                ", toPidShareholders='" + toPidShareholders + '\'' +
                ", isShow='" + isShow + '\'' +
                ", level=" + level +
                ", fromName='" + fromName + '\'' +
                ", toName='" + toName + '\'' +
                ", fromAligCode='" + fromAligCode + '\'' +
                ", toAligCode='" + toAligCode + '\'' +
                ", paRelId=" + paRelId +
                ", relType='" + relType + '\'' +
                ", relStatus='" + relStatus + '\'' +
                ", relPredicate='" + relPredicate + '\'' +
                ", liftReason='" + liftReason + '\'' +
                ", isValid='" + isValid + '\'' +
                ", relStatement='" + relStatement + '\'' +
                ", transactionDep='" + transactionDep + '\'' +
                ", operator='" + operator + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", ineffectiveDate='" + ineffectiveDate + '\'' +
                ", activeTime=" + activeTime +
                ", invalidTime=" + invalidTime +
                ", pledgeRatio='" + pledgeRatio + '\'' +
                ", equityShare='" + equityShare + '\'' +
                ", activeTimeString='" + activeTimeString + '\'' +
                ", invalidTimeString='" + invalidTimeString + '\'' +
                ", fromIdentifyNumber='" + fromIdentifyNumber + '\'' +
                ", toIdentifyNumber='" + toIdentifyNumber + '\'' +
                ", relPsnOrg='" + relPsnOrg + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", shipString='" + shipString + '\'' +
                ", describeString='" + describeString + '\'' +
                ", relationType='" + relationType + '\'' +
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
        if (obj instanceof Rel == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Rel other = (Rel) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}
