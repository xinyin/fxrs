/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月30日 15:47:12
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
@Table(name = "bus_gd_tra_zr")
public class GdTraZr extends BasePO {

    public static final String TABLE_ALIAS = "GdTraZr";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_FROM_SHAREHOLDERS_CODE = "转让人股东代码";
    public static final String ALIAS_FROM_SHAREHOLDERS_NAME = "转让人股东名称";
    public static final String ALIAS_OPERATION_AMOUNT = "操作股数（股）";
    public static final String ALIAS_OPERATION_PRICE = "转让单价（元/股）";
    public static final String ALIAS_AFTER_FROM_AMOUNT = "变更后转让人持股数量（股）";
    public static final String ALIAS_AFTER_FROM_PROPORTION = "变更后转让人持股数比例（%)";
    public static final String ALIAS_BEFORE_FROM_AMOUNT = "变更前转让人持股数量（股）";
    public static final String ALIAS_BEFORE_FROM_PROPORTION = "变更前转让人持股占比（%）";
    public static final String ALIAS_AFTER_TO_AMOUNT = "变更后受让人持股数量（股）";
    public static final String ALIAS_AFTER_TO_PROPORTION = "变更后受让人持股数比例（%）";
    public static final String ALIAS_BEFORE_TO_AMOUNT = "变更前受让人持股数量（股）";
    public static final String ALIAS_BEFORE_TO_PROPORTION = "变更前受让人持股数比例（%）";
    public static final String ALIAS_TO_SHAREHOLDERS_CODE = "受让人股东代码";
    public static final String ALIAS_TO_SHAREHOLDERS_NAME = "受让人股东名称";
    public static final String ALIAS_STATUS = "审批状态";
    public static final String ALIAS_OPERATION_TYPE = "操作类型";
    public static final String ALIAS_CHANGE_TIME = "股权变更时间";
    public static final String ALIAS_TRA_SERIAL = "交易流水号";
    public static final String ALIAS_FROM_SHAREHOLDERS_TYPE = "转让人股东类型";
    public static final String ALIAS_TO_SHAREHOLDERS_TYPE = "受让人股东类型";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "from_shareholders_code", nullable = true, length = 50)
    private String fromShareholdersCode;

    @Column(name = "from_shareholders_name", nullable = true, length = 500)
    private String fromShareholdersName;

    @Column(name = "operation_amount", nullable = true, length = 50)
    private String operationAmount;

    @Column(name = "operation_price", nullable = true, length = 20)
    private String operationPrice;

    @Column(name = "after_from_amount", nullable = true, length = 50)
    private String afterFromAmount;

    @Column(name = "after_from_proportion", nullable = true, length = 20)
    private String afterFromProportion;

    @Column(name = "before_from_amount", nullable = true, length = 50)
    private String beforeFromAmount;

    @Column(name = "before_from_proportion", nullable = true, length = 20)
    private String beforeFromProportion;

    @Column(name = "after_to_amount", nullable = true, length = 50)
    private String afterToAmount;

    @Column(name = "after_to_proportion", nullable = true, length = 20)
    private String afterToProportion;

    @Column(name = "before_to_amount", nullable = true, length = 50)
    private String beforeToAmount;

    @Column(name = "before_to_proportion", nullable = true, length = 20)
    private String beforeToProportion;

    @Column(name = "to_shareholders_code", nullable = true, length = 50)
    private String toShareholdersCode;

    @Column(name = "to_shareholders_name", nullable = true, length = 500)
    private String toShareholdersName;

    @Column(name = "status", nullable = true, length = 255)
    private String status;

    @Column(name = "operation_type", nullable = true, length = 10)
    private String operationType;

    @Column(name = "change_time", nullable = true, length = 11)
    private String changeTime;

    @Column(name = "tra_serial", nullable = true, length = 32)
    private String traSerial;

    @Column(name = "from_shareholders_type", nullable = true, length = 10)
    private String fromShareholdersType;

    @Column(name = "to_shareholders_type", nullable = true, length = 10)
    private String toShareholdersType;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getFromShareholdersCode() {
        return this.fromShareholdersCode;
    }

    public void setFromShareholdersCode(String value) {
        this.fromShareholdersCode = value;
    }


    public String getFromShareholdersName() {
        return this.fromShareholdersName;
    }

    public void setFromShareholdersName(String value) {
        this.fromShareholdersName = value;
    }


    public String getOperationAmount() {
        return this.operationAmount;
    }

    public void setOperationAmount(String value) {
        this.operationAmount = value;
    }


    public String getOperationPrice() {
        return this.operationPrice;
    }

    public void setOperationPrice(String value) {
        this.operationPrice = value;
    }


    public String getAfterFromAmount() {
        return this.afterFromAmount;
    }

    public void setAfterFromAmount(String value) {
        this.afterFromAmount = value;
    }


    public String getAfterFromProportion() {
        return this.afterFromProportion;
    }

    public void setAfterFromProportion(String value) {
        this.afterFromProportion = value;
    }


    public String getBeforeFromAmount() {
        return this.beforeFromAmount;
    }

    public void setBeforeFromAmount(String value) {
        this.beforeFromAmount = value;
    }


    public String getBeforeFromProportion() {
        return this.beforeFromProportion;
    }

    public void setBeforeFromProportion(String value) {
        this.beforeFromProportion = value;
    }


    public String getAfterToAmount() {
        return this.afterToAmount;
    }

    public void setAfterToAmount(String value) {
        this.afterToAmount = value;
    }


    public String getAfterToProportion() {
        return this.afterToProportion;
    }

    public void setAfterToProportion(String value) {
        this.afterToProportion = value;
    }


    public String getBeforeToAmount() {
        return this.beforeToAmount;
    }

    public void setBeforeToAmount(String value) {
        this.beforeToAmount = value;
    }


    public String getBeforeToProportion() {
        return this.beforeToProportion;
    }

    public void setBeforeToProportion(String value) {
        this.beforeToProportion = value;
    }


    public String getToShareholdersCode() {
        return this.toShareholdersCode;
    }

    public void setToShareholdersCode(String value) {
        this.toShareholdersCode = value;
    }


    public String getToShareholdersName() {
        return this.toShareholdersName;
    }

    public void setToShareholdersName(String value) {
        this.toShareholdersName = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public String getOperationType() {
        return this.operationType;
    }

    public void setOperationType(String value) {
        this.operationType = value;
    }


    public String getChangeTime() {
        return this.changeTime;
    }

    public void setChangeTime(String value) {
        this.changeTime = value;
    }


    public String getTraSerial() {
        return this.traSerial;
    }

    public void setTraSerial(String value) {
        this.traSerial = value;
    }


    public String getFromShareholdersType() {
        return this.fromShareholdersType;
    }

    public void setFromShareholdersType(String value) {
        this.fromShareholdersType = value;
    }


    public String getToShareholdersType() {
        return this.toShareholdersType;
    }

    public void setToShareholdersType(String value) {
        this.toShareholdersType = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("gdTraZr[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("fromShareholdersCode = ").append(fromShareholdersCode).append(", \n")
                .append("fromShareholdersName = ").append(fromShareholdersName).append(", \n")
                .append("operationAmount = ").append(operationAmount).append(", \n")
                .append("operationPrice = ").append(operationPrice).append(", \n")
                .append("afterFromAmount = ").append(afterFromAmount).append(", \n")
                .append("afterFromProportion = ").append(afterFromProportion).append(", \n")
                .append("beforeFromAmount = ").append(beforeFromAmount).append(", \n")
                .append("beforeFromProportion = ").append(beforeFromProportion).append(", \n")
                .append("afterToAmount = ").append(afterToAmount).append(", \n")
                .append("afterToProportion = ").append(afterToProportion).append(", \n")
                .append("beforeToAmount = ").append(beforeToAmount).append(", \n")
                .append("beforeToProportion = ").append(beforeToProportion).append(", \n")
                .append("toShareholdersCode = ").append(toShareholdersCode).append(", \n")
                .append("toShareholdersName = ").append(toShareholdersName).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("operationType = ").append(operationType).append(", \n")
                .append("changeTime = ").append(changeTime).append(", \n")
                .append("traSerial = ").append(traSerial).append(", \n")
                .append("fromShareholdersType = ").append(fromShareholdersType).append(", \n")
                .append("toShareholdersType = ").append(toShareholdersType).append(", \n")
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
        if (obj instanceof GdTraZr == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GdTraZr other = (GdTraZr) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

