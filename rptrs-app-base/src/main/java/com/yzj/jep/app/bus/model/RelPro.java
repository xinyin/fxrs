/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月25日 14:22:10
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;

@Entity
@Table(name = "bus_rel_pro")
public class RelPro extends BasePO {

    public static final String TABLE_ALIAS = "关联关系金融产品表";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_PRO_CODE = "产品代码";
    public static final String ALIAS_PRO_NAME = "产品名称";
    public static final String ALIAS_PRO_TYPE = "产品分类";
    public static final String ALIAS_PRO_PROVIDER = "产品批准";
    public static final String ALIAS_PRO_REG_ORG = "产品登记机构";
    public static final String ALIAS_AD_PID = "产品管理人pid";
    public static final String ALIAS_AD_NAME = "产品管理人全称";
    public static final String ALIAS_AD_CREDIT_CODE = "管理人统一社会信用代码";
    public static final String ALIAS_CARE_PID = "产品托管人pid";
    public static final String ALIAS_CARE_NAME = "产品托管人全称";
    public static final String ALIAS_CARE_CREDIT_CODE = "托管人统一社会信用代码";
    public static final String ALIAS_PRO_DATA_SOURCE = "数据源";
    public static final String ALIAS_PRO_APPROVAL_STATUS = "审批状态";
    public static final String ALIAS_PRO_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_PRO_ALIG_CODE = "定位码";
    public static final String ALIAS_IS_ITERATE = "是否需要遍历更新相关定位码：0不需要，1需要";
    public static final String ALIAS_ACTIVE_TIME = "生效时间";
    public static final String ALIAS_INVALID_TIME = "失效时间";
    public static final String ALIAS_MGER_ORG = "管户机构";



    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

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

    @Column(name = "data_source", nullable = true, length = 100)
    private String dataSource;

    @Column(name = "approval_status", nullable = true, length = 100)
    private String approvalStatus;

    @Column(name = "status_comment", nullable = true, length = 100)
    private String statusComment;

    @Column(name = "alig_code", nullable = true, length = 100)
    private String aligCode;
    @Column(name = "is_Iterate", nullable = true, length = 1)
    private String isIterate;
    @Column(name = "mger_org", nullable = true, length = 100)
    private String mgerOrg;
    //columns END

    @Column(name = "active_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date activeTime;


    @Column(name = "invalid_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date invalidTime;

    @Column(name = "AMEND_ID", nullable = true, length = 50)
    private String amendId;

    //操作人
    @Column(name = "AMEND_NAME", nullable = true, length = 50)
    private String amendName;

    //是否存在有效关系
    @Column(name = "effect_relation", nullable = true, length = 6)
    private String effectRelation;

    /** 底层资产 */
    @Column(name = "asset_desc", nullable = true, length = 2000)
    private String assetDesc;
    /** 投资者 */
    @Column(name = "investors", nullable = true, length = 2000)
    private String investors;

    public String getAmendId() {
        return amendId;
    }

    public void setAmendId(String amendId) {
        this.amendId = amendId;
    }

    public String getAmendName() {
        return amendName;
    }

    public void setAmendName(String amendName) {
        this.amendName = amendName;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    public String getAligCode() {
        return aligCode;
    }

    public void setAligCode(String aligCode) {
        this.aligCode = aligCode;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
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

    public String getIsIterate() {
        return isIterate;
    }

    public void setIsIterate(String isIterate) {
        this.isIterate = isIterate;
    }

    public Date getActiveTime() {
        return this.activeTime;
    }

    public void setActiveTime(Date value) {
        this.activeTime = value;
    }

    public String getMgerOrg() {
        return mgerOrg;
    }

    public void setMgerOrg(String mgerOrg) {
        this.mgerOrg = mgerOrg;
    }

    public Date getInvalidTime() {
        return this.invalidTime;
    }

    public void setInvalidTime(Date value) {
        this.invalidTime = value;
    }

    public String getEffectRelation() {
        return effectRelation;
    }

    public void setEffectRelation(String effectRelation) {
        this.effectRelation = effectRelation;
    }

    public String getAssetDesc() {
        return assetDesc;
    }

    public void setAssetDesc(String assetDesc) {
        this.assetDesc = assetDesc;
    }

    public String getInvestors() {
        return investors;
    }

    public void setInvestors(String investors) {
        this.investors = investors;
    }

    @Override
    public String toString() {
        return "RelPro{" +
                "pid=" + pid +
                ", proCode='" + proCode + '\'' +
                ", proName='" + proName + '\'' +
                ", proType='" + proType + '\'' +
                ", proProvider='" + proProvider + '\'' +
                ", proRegOrg='" + proRegOrg + '\'' +
                ", adPid='" + adPid + '\'' +
                ", adName='" + adName + '\'' +
                ", adCreditCode='" + adCreditCode + '\'' +
                ", carePid='" + carePid + '\'' +
                ", careName='" + careName + '\'' +
                ", careCreditCode='" + careCreditCode + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", statusComment='" + statusComment + '\'' +
                ", aligCode='" + aligCode + '\'' +
                ", isIterate='" + isIterate + '\'' +
                ", mgerOrg='" + mgerOrg + '\'' +
                ", activeTime=" + activeTime +
                ", invalidTime=" + invalidTime +
                ", amendId='" + amendId + '\'' +
                ", amendName='" + amendName + '\'' +
                ", effectRelation='" + effectRelation + '\'' +
                ", assetDesc='" + assetDesc + '\'' +
                ", investors='" + investors + '\'' +
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
        if (obj instanceof RelPro == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelPro other = (RelPro) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

