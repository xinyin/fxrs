/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 11:07:46
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
@Table(name = "bus_gd_tra_dj")
public class GdTraDj extends BasePO {

    public static final String TABLE_ALIAS = "GdTraDj";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_SHAREHOLDERS_CODE = "股东代码";
    public static final String ALIAS_SHAREHOLDERS_NAME = "股东名称";
    public static final String ALIAS_SHAREHOLDERS_TYPE = "股东类型";
    public static final String ALIAS_STATE_OWNED = "是否国有企业";
    public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内境外";
    public static final String ALIAS_HOLE_SHARE_AMOUNT = "总持有股数";
    public static final String ALIAS_FROZEN_SHARE_AMOUNT = "总冻结股数";
    public static final String ALIAS_OPERATION_TYPE = "操作类型";
    public static final String ALIAS_OPERATION_AMOUNT = "操作股数";
    public static final String ALIAS_EFFECTIVE_TIME = "冻结生效时间";
    public static final String ALIAS_FAILURE_TIME = "冻结失效时间";
    public static final String ALIAS_FROZEN_ORG = "冻结机构";
    public static final String ALIAS_FROZEN_SERIAL = "冻结流水号";
    public static final String ALIAS_CONTINUE_FROZEN_TIME = "续冻失效时间";
    public static final String ALIAS_FROZEN_REASON = "冻结原因";
    public static final String ALIAS_CONTINUE_FROZEN_REASON = "续冻原因";
    public static final String ALIAS_UNFROZEN_TIME = "解冻时间";
    public static final String ALIAS_SUBMIT_TIME = "提交审批时间";
    public static final String ALIAS_STATUS = "审批状态";
    public static final String ALIAS_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_OPERATOR = "经办人";
    public static final String ALIAS_APPROVER = "审批人";
    public static final String ALIAS_UNFROZEN_REASON = "解冻说明";
    public static final String ALIAS_UNFROZEN_AMOUNT = "已冻结股数";
    public static final String ALIAS_CURR_FROZEN_AMOUNT = "解冻当前冻结股数";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "shareholders_code", nullable = true, length = 50)
    private String shareholdersCode;

    @Column(name = "shareholders_name", nullable = true, length = 100)
    private String shareholdersName;

    @Column(name = "shareholders_type", nullable = true, length = 10)
    private String shareholdersType;

    @Column(name = "state_owned", nullable = true, length = 50)
    private String stateOwned;

    @Column(name = "domestic_or_foreign", nullable = true, length = 10)
    private String domesticOrForeign;

    @Column(name = "hole_share_amount", nullable = true, length = 50)
    private String holeShareAmount;

    @Column(name = "frozen_share_amount", nullable = true, length = 50)
    private String frozenShareAmount;

    @Column(name = "operation_type", nullable = true, length = 10)
    private String operationType;

    @Column(name = "operation_amount", nullable = true, length = 50)
    private String operationAmount;

    @Column(name = "effective_time", nullable = true, length = 11)
    private String effectiveTime;

    @Column(name = "failure_time", nullable = true, length = 11)
    private String failureTime;

    @Column(name = "frozen_org", nullable = true, length = 50)
    private String frozenOrg;

    @Column(name = "frozen_serial", nullable = true, length = 32)
    private String frozenSerial;

    @Column(name = "continue_frozen_time", nullable = true, length = 11)
    private String continueFrozenTime;

    @Column(name = "frozen_reason", nullable = true, length = 255)
    private String frozenReason;

    @Column(name = "continue_frozen_reason", nullable = true, length = 255)
    private String continueFrozenReason;

    @Column(name = "unfrozen_time", nullable = true, length = 11)
    private String unfrozenTime;

    @Column(name = "submit_time", nullable = true, length = 11)
    private String submitTime;

    @Column(name = "status", nullable = true, length = 255)
    private String status;

    @Column(name = "status_comment", nullable = true, length = 255)
    private String statusComment;

    @Column(name = "operator", nullable = true, length = 50)
    private String operator;

    @Column(name = "approver", nullable = true, length = 50)
    private String approver;

    @Column(name = "unfrozen_reason", nullable = true, length = 255)
    private String unfrozenReason;

    @Column(name = "unfrozen_amount", nullable = true, length = 50)
    private String unfrozenAmount;

    @Column(name = "curr_frozen_amount", nullable = true, length = 50)
    private String currFrozenAmount;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getShareholdersCode() {
        return this.shareholdersCode;
    }

    public void setShareholdersCode(String value) {
        this.shareholdersCode = value;
    }


    public String getShareholdersName() {
        return this.shareholdersName;
    }

    public void setShareholdersName(String value) {
        this.shareholdersName = value;
    }


    public String getShareholdersType() {
        return this.shareholdersType;
    }

    public void setShareholdersType(String value) {
        this.shareholdersType = value;
    }


    public String getStateOwned() {
        return this.stateOwned;
    }

    public void setStateOwned(String value) {
        this.stateOwned = value;
    }


    public String getDomesticOrForeign() {
        return this.domesticOrForeign;
    }

    public void setDomesticOrForeign(String value) {
        this.domesticOrForeign = value;
    }


    public String getHoleShareAmount() {
        return this.holeShareAmount;
    }

    public void setHoleShareAmount(String value) {
        this.holeShareAmount = value;
    }


    public String getFrozenShareAmount() {
        return this.frozenShareAmount;
    }

    public void setFrozenShareAmount(String value) {
        this.frozenShareAmount = value;
    }


    public String getOperationType() {
        return this.operationType;
    }

    public void setOperationType(String value) {
        this.operationType = value;
    }


    public String getOperationAmount() {
        return this.operationAmount;
    }

    public void setOperationAmount(String value) {
        this.operationAmount = value;
    }


    public String getEffectiveTime() {
        return this.effectiveTime;
    }

    public void setEffectiveTime(String value) {
        this.effectiveTime = value;
    }


    public String getFailureTime() {
        return this.failureTime;
    }

    public void setFailureTime(String value) {
        this.failureTime = value;
    }


    public String getFrozenOrg() {
        return this.frozenOrg;
    }

    public void setFrozenOrg(String value) {
        this.frozenOrg = value;
    }


    public String getFrozenSerial() {
        return this.frozenSerial;
    }

    public void setFrozenSerial(String value) {
        this.frozenSerial = value;
    }


    public String getContinueFrozenTime() {
        return this.continueFrozenTime;
    }

    public void setContinueFrozenTime(String value) {
        this.continueFrozenTime = value;
    }


    public String getFrozenReason() {
        return this.frozenReason;
    }

    public void setFrozenReason(String value) {
        this.frozenReason = value;
    }


    public String getContinueFrozenReason() {
        return this.continueFrozenReason;
    }

    public void setContinueFrozenReason(String value) {
        this.continueFrozenReason = value;
    }


    public String getUnfrozenTime() {
        return this.unfrozenTime;
    }

    public void setUnfrozenTime(String value) {
        this.unfrozenTime = value;
    }


    public String getSubmitTime() {
        return this.submitTime;
    }

    public void setSubmitTime(String value) {
        this.submitTime = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public String getStatusComment() {
        return this.statusComment;
    }

    public void setStatusComment(String value) {
        this.statusComment = value;
    }


    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String value) {
        this.operator = value;
    }


    public String getApprover() {
        return this.approver;
    }

    public void setApprover(String value) {
        this.approver = value;
    }


    public String getUnfrozenReason() {
        return this.unfrozenReason;
    }

    public void setUnfrozenReason(String value) {
        this.unfrozenReason = value;
    }


    public String getUnfrozenAmount() {
        return this.unfrozenAmount;
    }

    public void setUnfrozenAmount(String value) {
        this.unfrozenAmount = value;
    }


    public String getCurrFrozenAmount() {
        return this.currFrozenAmount;
    }

    public void setCurrFrozenAmount(String value) {
        this.currFrozenAmount = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("gdTraDj[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("shareholdersCode = ").append(shareholdersCode).append(", \n")
                .append("shareholdersName = ").append(shareholdersName).append(", \n")
                .append("shareholdersType = ").append(shareholdersType).append(", \n")
                .append("stateOwned = ").append(stateOwned).append(", \n")
                .append("domesticOrForeign = ").append(domesticOrForeign).append(", \n")
                .append("holeShareAmount = ").append(holeShareAmount).append(", \n")
                .append("frozenShareAmount = ").append(frozenShareAmount).append(", \n")
                .append("operationType = ").append(operationType).append(", \n")
                .append("operationAmount = ").append(operationAmount).append(", \n")
                .append("effectiveTime = ").append(effectiveTime).append(", \n")
                .append("failureTime = ").append(failureTime).append(", \n")
                .append("frozenOrg = ").append(frozenOrg).append(", \n")
                .append("frozenSerial = ").append(frozenSerial).append(", \n")
                .append("continueFrozenTime = ").append(continueFrozenTime).append(", \n")
                .append("frozenReason = ").append(frozenReason).append(", \n")
                .append("continueFrozenReason = ").append(continueFrozenReason).append(", \n")
                .append("unfrozenTime = ").append(unfrozenTime).append(", \n")
                .append("submitTime = ").append(submitTime).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("operator = ").append(operator).append(", \n")
                .append("approver = ").append(approver).append(", \n")
                .append("unfrozenReason = ").append(unfrozenReason).append(", \n")
                .append("unfrozenAmount = ").append(unfrozenAmount).append(", \n")
                .append("currFrozenAmount = ").append(currFrozenAmount).append(", \n")
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
        if (obj instanceof GdTraDj == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GdTraDj other = (GdTraDj) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

