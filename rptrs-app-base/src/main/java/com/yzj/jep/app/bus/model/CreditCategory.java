/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:01:31
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;


@Entity
@Table(name = "bus_credit_category")
public class CreditCategory extends BasePO {

    public static final String TABLE_ALIAS = "交易管理类型——授信类";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_TRANS_NUM = "交易编号             ";
    public static final String ALIAS_RELATED_NAME = "关联方名称";
    public static final String ALIAS_HOUSE_MANAGE_ORG = "管户机构";
    public static final String ALIAS_IDENT_NUMBER = "证件号码";
    public static final String ALIAS_TRAN_TYPE = "交易类型";
    public static final String ALIAS_APPROVAL_TIME = "审批时间 ";
    public static final String ALIAS_VALIDITY_CONTRACT = "合同有效期";
    public static final String ALIAS_STATUS = "状态";
    public static final String ALIAS_AMOUNT_CREDIT = "授信金额";
    public static final String ALIAS_CREDIT_START_DATE = "授信起始日期";
    public static final String ALIAS_CREDIT_END_DATE = "授信到期日";
    public static final String ALIAS_GUARA_MODE = "担保方式";
    public static final String ALIAS_COLLATERAL = "担保物情况";
    public static final String ALIAS_GUARANTOR = "保证人情况";
    public static final String ALIAS_FIVE_LEVEL = "五级分类";
    public static final String ALIAS_SECURITY_DEPOSIT = "保证金情况";
    public static final String ALIAS_APPLICA_SUB_TIME = "申请报送时间";
    public static final String ALIAS_RELATED_TRAN_TYPE = "关联交易类别";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "trans_num", nullable = true, length = 100)
    private String transNum;

    @Column(name = "related_name", nullable = true, length = 20)
    private String relatedName;

    @Column(name = "house_manage_org", nullable = true, length = 100)
    private String houseManageOrg;

    @Column(name = "ident_number", nullable = true, length = 100)
    private String identNumber;

    @Column(name = "tran_type", nullable = true, length = 10)
    private String tranType;

    @Column(name = "approval_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date approvalTime;

    @Column(name = "validity_contract", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date validityContract;

    @Column(name = "status", nullable = true, length = 20)
    private String status;

    @Column(name = "amount_credit", nullable = true, precision = 10, scale = 0)
    private Long amountCredit;

    @Column(name = "credit_start_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date creditStartDate;

    @Column(name = "credit_end_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date creditEndDate;

    @Column(name = "guara_mode", nullable = true, length = 100)
    private String guaraMode;

    @Column(name = "collateral", nullable = true, length = 100)
    private String collateral;

    @Column(name = "guarantor", nullable = true, length = 100)
    private String guarantor;

    @Column(name = "five_level", nullable = true, length = 100)
    private String fiveLevel;

    @Column(name = "security_deposit", nullable = true, length = 100)
    private String securityDeposit;

    @Column(name = "applica_sub_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date applicaSubTime;

    @Column(name = "related_tran_type", nullable = true, length = 20)
    private String relatedTranType;

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    @Column(name = "is_del", nullable = false, length = 11)
    private Integer isDel;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Column(name = "business_type", nullable = false, length = 21)
    private String businessType;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getTransNum() {
        return this.transNum;
    }

    public void setTransNum(String value) {
        this.transNum = value;
    }


    public String getRelatedName() {
        return this.relatedName;
    }

    public void setRelatedName(String value) {
        this.relatedName = value;
    }


    public String getHouseManageOrg() {
        return this.houseManageOrg;
    }

    public void setHouseManageOrg(String value) {
        this.houseManageOrg = value;
    }


    public String getIdentNumber() {
        return this.identNumber;
    }

    public void setIdentNumber(String value) {
        this.identNumber = value;
    }


    public String getTranType() {
        return this.tranType;
    }

    public void setTranType(String value) {
        this.tranType = value;
    }


    public Date getApprovalTime() {
        return this.approvalTime;
    }

    public void setApprovalTime(Date value) {
        this.approvalTime = value;
    }


    public Date getValidityContract() {
        return this.validityContract;
    }

    public void setValidityContract(Date value) {
        this.validityContract = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public Long getAmountCredit() {
        return this.amountCredit;
    }

    public void setAmountCredit(Long value) {
        this.amountCredit = value;
    }


    public Date getCreditStartDate() {
        return this.creditStartDate;
    }

    public void setCreditStartDate(Date value) {
        this.creditStartDate = value;
    }


    public Date getCreditEndDate() {
        return this.creditEndDate;
    }

    public void setCreditEndDate(Date value) {
        this.creditEndDate = value;
    }


    public String getGuaraMode() {
        return this.guaraMode;
    }

    public void setGuaraMode(String value) {
        this.guaraMode = value;
    }


    public String getCollateral() {
        return this.collateral;
    }

    public void setCollateral(String value) {
        this.collateral = value;
    }


    public String getGuarantor() {
        return this.guarantor;
    }

    public void setGuarantor(String value) {
        this.guarantor = value;
    }


    public String getFiveLevel() {
        return this.fiveLevel;
    }

    public void setFiveLevel(String value) {
        this.fiveLevel = value;
    }


    public String getSecurityDeposit() {
        return this.securityDeposit;
    }

    public void setSecurityDeposit(String value) {
        this.securityDeposit = value;
    }


    public Date getApplicaSubTime() {
        return this.applicaSubTime;
    }

    public void setApplicaSubTime(Date value) {
        this.applicaSubTime = value;
    }


    public String getRelatedTranType() {
        return this.relatedTranType;
    }

    public void setRelatedTranType(String value) {
        this.relatedTranType = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("creditCategory[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("transNum = ").append(transNum).append(", \n")
                .append("relatedName = ").append(relatedName).append(", \n")
                .append("houseManageOrg = ").append(houseManageOrg).append(", \n")
                .append("identNumber = ").append(identNumber).append(", \n")
                .append("tranType = ").append(tranType).append(", \n")
                .append("approvalTime = ").append(approvalTime).append(", \n")
                .append("validityContract = ").append(validityContract).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("amountCredit = ").append(amountCredit).append(", \n")
                .append("creditStartDate = ").append(creditStartDate).append(", \n")
                .append("creditEndDate = ").append(creditEndDate).append(", \n")
                .append("guaraMode = ").append(guaraMode).append(", \n")
                .append("collateral = ").append(collateral).append(", \n")
                .append("guarantor = ").append(guarantor).append(", \n")
                .append("fiveLevel = ").append(fiveLevel).append(", \n")
                .append("securityDeposit = ").append(securityDeposit).append(", \n")
                .append("applicaSubTime = ").append(applicaSubTime).append(", \n")
                .append("relatedTranType = ").append(relatedTranType).append(", \n")
                .append("businessType = ").append(businessType).append(", \n")
                .append("isDel = ").append(isDel).append(", \n")
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
        if (obj instanceof CreditCategory == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        CreditCategory other = (CreditCategory) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

