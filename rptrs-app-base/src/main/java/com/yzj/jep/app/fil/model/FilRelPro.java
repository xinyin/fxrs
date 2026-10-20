/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月10日 18:04:44
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
@Table(name = "fil_rel_pro")
public class FilRelPro extends BasePO {

    public static final String TABLE_ALIAS = "RelPro";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_FIL_NUM = "备案编号";
    public static final String ALIAS_FLAG_PID = "备案标识pid";
    public static final String ALIAS_PRO_CODE = "产品代码";
    public static final String ALIAS_PRO_NAME = "产品名称";
    public static final String ALIAS_PRO_TYPE = "产品分类";
    public static final String ALIAS_PRO_PROVIDER = "产品批准";
    public static final String ALIAS_PRO_REG_ORG = "产品登记机构";
    public static final String ALIAS_AD_PID = "产品管理人pid";
    public static final String ALIAS_AD_NAME = "产品管理人全程";
    public static final String ALIAS_AD_CREDIT_CODE = "管理人统一社会信用代码";
    public static final String ALIAS_CARE_PID = "产品托管人pid";
    public static final String ALIAS_CARE_NAME = "产品托管人全称";
    public static final String ALIAS_CARE_CREDIT_CODE = "托管人统一社会信用代码";
    public static final String ALIAS_DATA_SOURCE = "dataSource";
    public static final String ALIAS_APPROVAL_STATUS = "approvalStatus";
    public static final String ALIAS_STATUS_COMMENT = "statusComment";
    public static final String ALIAS_ALIG_CODE = "aligCode";
    public static final String ALIAS_IS_ITERATE = "是否需要遍历  0 否 1 是";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "fil_num", nullable = true, length = 64)
    private String filNum;

    @Column(name = "flag_pid", nullable = true, length = 64)
    private String flagPid;

    @Column(name = "pro_code", nullable = true, length = 100)
    private String proCode;

    @Column(name = "pro_name", nullable = true, length = 100)
    private String proName;

    @Column(name = "pro_type", nullable = true, length = 100)
    private String proType;

    @Column(name = "pro_provider", nullable = true, length = 100)
    private String proProvider;

    @Column(name = "pro_reg_org", nullable = true, length = 100)
    private String proRegOrg;

    @Column(name = "ad_pid", nullable = true, length = 100)
    private String adPid;

    @Column(name = "ad_name", nullable = true, length = 100)
    private String adName;

    @Column(name = "ad_credit_code", nullable = true, length = 100)
    private String adCreditCode;

    @Column(name = "care_pid", nullable = true, length = 100)
    private String carePid;

    @Column(name = "care_name", nullable = true, length = 100)
    private String careName;

    @Column(name = "care_credit_code", nullable = true, length = 100)
    private String careCreditCode;

    @Column(name = "data_source", nullable = true, length = 64)
    private String dataSource;

    @Column(name = "approval_status", nullable = true, length = 255)
    private String approvalStatus;

    @Column(name = "status_comment", nullable = true, length = 255)
    private String statusComment;

    @Column(name = "alig_code", nullable = true, length = 100)
    private String aligCode;

    @Column(name = "is_Iterate", nullable = true, length = 1)
    private String isIterate;
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

    public String getProCode() {
        return this.proCode;
    }

    public void setProCode(String value) {
        this.proCode = value;
    }


    public String getProName() {
        return this.proName;
    }

    public void setProName(String value) {
        this.proName = value;
    }


    public String getProType() {
        return this.proType;
    }

    public void setProType(String value) {
        this.proType = value;
    }


    public String getProProvider() {
        return this.proProvider;
    }

    public void setProProvider(String value) {
        this.proProvider = value;
    }


    public String getProRegOrg() {
        return this.proRegOrg;
    }

    public void setProRegOrg(String value) {
        this.proRegOrg = value;
    }


    public String getAdPid() {
        return this.adPid;
    }

    public void setAdPid(String value) {
        this.adPid = value;
    }


    public String getAdName() {
        return this.adName;
    }

    public void setAdName(String value) {
        this.adName = value;
    }


    public String getAdCreditCode() {
        return this.adCreditCode;
    }

    public void setAdCreditCode(String value) {
        this.adCreditCode = value;
    }


    public String getCarePid() {
        return this.carePid;
    }

    public void setCarePid(String value) {
        this.carePid = value;
    }


    public String getCareName() {
        return this.careName;
    }

    public void setCareName(String value) {
        this.careName = value;
    }


    public String getCareCreditCode() {
        return this.careCreditCode;
    }

    public void setCareCreditCode(String value) {
        this.careCreditCode = value;
    }


    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String value) {
        this.dataSource = value;
    }


    public String getApprovalStatus() {
        return this.approvalStatus;
    }

    public void setApprovalStatus(String value) {
        this.approvalStatus = value;
    }


    public String getStatusComment() {
        return this.statusComment;
    }

    public void setStatusComment(String value) {
        this.statusComment = value;
    }


    public String getAligCode() {
        return this.aligCode;
    }

    public void setAligCode(String value) {
        this.aligCode = value;
    }


    public String getIsIterate() {
        return this.isIterate;
    }

    public void setIsIterate(String value) {
        this.isIterate = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("relPro[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("filNum = ").append(filNum).append(", \n")
                .append("proCode = ").append(proCode).append(", \n")
                .append("proName = ").append(proName).append(", \n")
                .append("proType = ").append(proType).append(", \n")
                .append("proProvider = ").append(proProvider).append(", \n")
                .append("proRegOrg = ").append(proRegOrg).append(", \n")
                .append("adPid = ").append(adPid).append(", \n")
                .append("adName = ").append(adName).append(", \n")
                .append("adCreditCode = ").append(adCreditCode).append(", \n")
                .append("carePid = ").append(carePid).append(", \n")
                .append("careName = ").append(careName).append(", \n")
                .append("careCreditCode = ").append(careCreditCode).append(", \n")
                .append("dataSource = ").append(dataSource).append(", \n")
                .append("approvalStatus = ").append(approvalStatus).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("aligCode = ").append(aligCode).append(", \n")
                .append("isIterate = ").append(isIterate).append(", \n")
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
        if (obj instanceof FilRelPro == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        FilRelPro other = (FilRelPro) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

