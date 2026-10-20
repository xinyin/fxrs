/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:01:28
 *
 */

package com.yzj.jep.app.bus.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "bus_asset_transfer")
public class AssetTransfer extends BasePO {

    public static final String TABLE_ALIAS = "交易管理类型——资产转移类\r\n";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_TRANS_NUM = "交易编号             ";
    public static final String ALIAS_RELATED_NAME = "关联方名称";
    public static final String ALIAS_HOUSE_MANAGE_ORG = "管户机构";
    public static final String ALIAS_IDENT_NUMBER = "证件号码";
    public static final String ALIAS_TRAN_TYPE = "交易类型";
    public static final String ALIAS_APPROVAL_TIME = "审批时间 ";
    public static final String ALIAS_VALIDITY_CONTRACT = "合同有效期";
    public static final String ALIAS_STATUS = "状态";
    public static final String ALIAS_TRAN_TARGET = "交易标的";
    public static final String ALIAS_TRAN_AMOUNT = "交易金额";
    public static final String ALIAS_TRAN_DATE = "交易日期";
    public static final String ALIAS_TRAN_INFO = "交易情况";
    public static final String ALIAS_APPLICA_SUB_TIME = "申请报送时间";
    public static final String ALIAS_RELATED_TRAN_TYPE = "关联交易类别";
    public static final String ALIAS_BUSINESS_TYPE = "业务类型             ";


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

    @Column(name = "tran_target", nullable = true, length = 100)
    private String tranTarget;

    @Column(name = "tran_amount", nullable = true, precision = 10, scale = 0)
    private Long tranAmount;

    @Column(name = "tran_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date tranDate;

    @Column(name = "tran_info", nullable = true, length = 100)
    private String tranInfo;

    @Column(name = "applica_sub_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date applicaSubTime;

    @Column(name = "related_tran_type", nullable = false, length = 20)
    private String relatedTranType;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Column(name = "business_type", nullable = false, length = 21)
    private String businessType;


    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    @Column(name = "is_del", nullable = false, length = 11)
    private Integer isDel;
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


    public String getTranTarget() {
        return this.tranTarget;
    }

    public void setTranTarget(String value) {
        this.tranTarget = value;
    }


    public Long getTranAmount() {
        return this.tranAmount;
    }

    public void setTranAmount(Long value) {
        this.tranAmount = value;
    }


    public Date getTranDate() {
        return this.tranDate;
    }

    public void setTranDate(Date value) {
        this.tranDate = value;
    }


    public String getTranInfo() {
        return this.tranInfo;
    }

    public void setTranInfo(String value) {
        this.tranInfo = value;
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
        return new StringBuilder("assetTransfer[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("transNum = ").append(transNum).append(", \n")
                .append("relatedName = ").append(relatedName).append(", \n")
                .append("houseManageOrg = ").append(houseManageOrg).append(", \n")
                .append("identNumber = ").append(identNumber).append(", \n")
                .append("tranType = ").append(tranType).append(", \n")
                .append("approvalTime = ").append(approvalTime).append(", \n")
                .append("validityContract = ").append(validityContract).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("tranTarget = ").append(tranTarget).append(", \n")
                .append("tranAmount = ").append(tranAmount).append(", \n")
                .append("tranDate = ").append(tranDate).append(", \n")
                .append("tranInfo = ").append(tranInfo).append(", \n")
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
        if (obj instanceof AssetTransfer == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        AssetTransfer other = (AssetTransfer) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

