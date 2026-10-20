/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月05日 17:39:20
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
@Table(name = "bus_gd_tra_zy")
public class GdTraZy extends BasePO {

    public static final String TABLE_ALIAS = "GdTraZy";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_SHAREHOLDERS_CODE = "股东代码";
    public static final String ALIAS_SHAREHOLDERS_NAME = "股东名称";
    public static final String ALIAS_SHAREHOLDERS_TYPE = "股东类型";
    public static final String ALIAS_STATE_OWNED = "是否国有企业";
    public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内境外";
    public static final String ALIAS_HOLE_SHARE_AMOUNT = "总持有股数";
    public static final String ALIAS_PLEDGED_SHARE_AMOUNT = "总质押股数";
    public static final String ALIAS_OPERATION_TYPE = "操作类型";
    public static final String ALIAS_OPERATION_AMOUNT = "操作股数";
    public static final String ALIAS_PLEDGED_CONTRACT = "质押合同号";
    public static final String ALIAS_EFFECTIVE_TIME = "质押生效时间";
    public static final String ALIAS_UNPLEDGED_TIME = "解押时间";
    public static final String ALIAS_SUBMIT_TIME = "提交审批时间";
    public static final String ALIAS_STATUS = "审批状态";
    public static final String ALIAS_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_OPERATOR = "经办人";
    public static final String ALIAS_APPROVER = "审批人";
    public static final String ALIAS_PLEDGED_AMOUNT = "质押融资额";
    public static final String ALIAS_NAME = "质权人名称";
    public static final String ALIAS_IDENTIFY_TYPE = "质权人证件类型";
    public static final String ALIAS_IDENTIFY_NUMBER = "质权人证件号码";
    public static final String ALIAS_UNPLEDGED_COMMENT = "解押说明";
    public static final String ALIAS_UNPLEDGED_AMOUNT = "已解押股数";
    public static final String ALIAS_CURR_PLEDGED_AMOUNT = "解压当前质押股数";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "shareholders_code", nullable = true, length = 50)
    private String shareholdersCode;

    @Column(name = "shareholders_name", nullable = true, length = 500)
    private String shareholdersName;

    @Column(name = "shareholders_type", nullable = true, length = 10)
    private String shareholdersType;

    @Column(name = "state_owned", nullable = true, length = 10)
    private String stateOwned;

    @Column(name = "domestic_or_foreign", nullable = true, length = 10)
    private String domesticOrForeign;

    @Column(name = "hole_share_amount", nullable = true, length = 50)
    private String holeShareAmount;

    @Column(name = "pledged_share_amount", nullable = true, length = 50)
    private String pledgedShareAmount;

    @Column(name = "operation_type", nullable = true, length = 10)
    private String operationType;

    @Column(name = "operation_amount", nullable = true, length = 50)
    private String operationAmount;

    @Column(name = "pledged_contract", nullable = true, length = 50)
    private String pledgedContract;

    @Column(name = "effective_time", nullable = true, length = 11)
    private String effectiveTime;

    @Column(name = "unpledged_time", nullable = true, length = 11)
    private String unpledgedTime;

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

    @Column(name = "pledged_amount", nullable = true, length = 50)
    private String pledgedAmount;

    @Column(name = "name", nullable = true, length = 500)
    private String name;

    @Column(name = "identify_type", nullable = true, length = 10)
    private String identifyType;

    @Column(name = "identify_number", nullable = true, length = 20)
    private String identifyNumber;

    @Column(name = "unpledged_comment", nullable = true, length = 255)
    private String unpledgedComment;

    @Column(name = "unpledged_amount", nullable = true, length = 50)
    private String unpledgedAmount;

    @Column(name = "curr_pledged_amount", nullable = true, length = 50)
    private String currPledgedAmount;
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


    public String getPledgedShareAmount() {
        return this.pledgedShareAmount;
    }

    public void setPledgedShareAmount(String value) {
        this.pledgedShareAmount = value;
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


    public String getPledgedContract() {
        return this.pledgedContract;
    }

    public void setPledgedContract(String value) {
        this.pledgedContract = value;
    }


    public String getEffectiveTime() {
        return this.effectiveTime;
    }

    public void setEffectiveTime(String value) {
        this.effectiveTime = value;
    }


    public String getUnpledgedTime() {
        return this.unpledgedTime;
    }

    public void setUnpledgedTime(String value) {
        this.unpledgedTime = value;
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


    public String getPledgedAmount() {
        return this.pledgedAmount;
    }

    public void setPledgedAmount(String value) {
        this.pledgedAmount = value;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
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


    public String getUnpledgedComment() {
        return this.unpledgedComment;
    }

    public void setUnpledgedComment(String value) {
        this.unpledgedComment = value;
    }


    public String getUnpledgedAmount() {
        return this.unpledgedAmount;
    }

    public void setUnpledgedAmount(String value) {
        this.unpledgedAmount = value;
    }


    public String getCurrPledgedAmount() {
        return this.currPledgedAmount;
    }

    public void setCurrPledgedAmount(String value) {
        this.currPledgedAmount = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("gdTraZy[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("shareholdersCode = ").append(shareholdersCode).append(", \n")
                .append("shareholdersName = ").append(shareholdersName).append(", \n")
                .append("shareholdersType = ").append(shareholdersType).append(", \n")
                .append("stateOwned = ").append(stateOwned).append(", \n")
                .append("domesticOrForeign = ").append(domesticOrForeign).append(", \n")
                .append("holeShareAmount = ").append(holeShareAmount).append(", \n")
                .append("pledgedShareAmount = ").append(pledgedShareAmount).append(", \n")
                .append("operationType = ").append(operationType).append(", \n")
                .append("operationAmount = ").append(operationAmount).append(", \n")
                .append("pledgedContract = ").append(pledgedContract).append(", \n")
                .append("effectiveTime = ").append(effectiveTime).append(", \n")
                .append("unpledgedTime = ").append(unpledgedTime).append(", \n")
                .append("submitTime = ").append(submitTime).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("operator = ").append(operator).append(", \n")
                .append("approver = ").append(approver).append(", \n")
                .append("pledgedAmount = ").append(pledgedAmount).append(", \n")
                .append("name = ").append(name).append(", \n")
                .append("identifyType = ").append(identifyType).append(", \n")
                .append("identifyNumber = ").append(identifyNumber).append(", \n")
                .append("unpledgedComment = ").append(unpledgedComment).append(", \n")
                .append("unpledgedAmount = ").append(unpledgedAmount).append(", \n")
                .append("currPledgedAmount = ").append(currPledgedAmount).append(", \n")
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
        if (obj instanceof GdTraZy == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GdTraZy other = (GdTraZy) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

