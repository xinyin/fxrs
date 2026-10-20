/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月17日 14:46:21
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;

@Entity
@Table(name = "bus_legal_person")
public class LegalPerson extends BasePO {

    public static final String TABLE_ALIAS = "法人";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_REG_CODE = "登记编码";
    public static final String ALIAS_ORIGNAL_ORG = "源主体银行或保险机构";
    public static final String ALIAS_ORIGNAL_ORG_NAME = "源主体银行或保险机构名称";
    public static final String ALIAS_ORG_NAME = "企业名称";
    public static final String ALIAS_ORG_TYPE = "企业类型";
    public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内/境外(1-境内，2-境外)";
    public static final String ALIAS_UNISC_CODE = "社会统一信用代码";
    public static final String ALIAS_REG_CAPITAL = "注册资本(万元)";
    public static final String ALIAS_REG_CAPITAL_CURRENCY = "注册资本币种代码";
    public static final String ALIAS_BUSINESS_SCOPE = "营业范围";
    public static final String ALIAS_STATUS = "机构状态（1-正常，2-停业）";
    public static final String ALIAS_LEGAL_PERSON = "法定代表人";
    public static final String ALIAS_REG_ADDRESS = "注册地";
    public static final String ALIAS_REPORT_VERSION = "报送版本号";
    public static final String ALIAS_VALID_STATUS = "是否有效 0-无效，1-有效";
    public static final String ALIAS_IS_REPORTED = "是否被报送过（0-历史上从未报送过，1-已被报送过）";
    public static final String ALIAS_ACTIVE_TIME = "生效时间";
    public static final String ALIAS_INVALID_TIME = "失效时间";
    public static final String ALIAS_BODY_TYPE = "机构类型";
    public static final String ALIAS_IDENTIFY_TYPE = "证件类型";
    public static final String ALIAS_IDENTIFY_NUMBER = "证件代码";
    public static final String ALIAS_COMMIT_STATUS = "状态";

    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "reg_code", nullable = true, length = 32)
    private String regCode;

    @Column(name = "orignal_org", nullable = true, length = 20)
    private String orignalOrg;

    @Column(name = "orignal_org_name", nullable = true, length = 100)
    private String orignalOrgName;

    @Column(name = "org_name", nullable = true, length = 500)
    private String orgName;

    @Column(name = "org_type", nullable = true, length = 100)
    private String orgType;

    @Column(name = "domestic_or_foreign", nullable = true, length = 10)
    private String domesticOrForeign;

    @Column(name = "unisc_code", nullable = true, length = 20)
    private String uniscCode;

    @Column(name = "reg_capital", nullable = true, precision = 10, scale = 0)
    private Long regCapital;

    @Column(name = "reg_capital_currency", nullable = true, length = 10)
    private String regCapitalCurrency;

    @Column(name = "business_scope", nullable = true, length = 2000)
    private String businessScope;

    @Column(name = "status", nullable = true, length = 10)
    private String status;

    @Column(name = "legal_person", nullable = true, length = 100)
    private String legalPerson;

    @Column(name = "reg_address", nullable = true, length = 100)
    private String regAddress;

    @Column(name = "report_version", nullable = true, length = 10)
    private Integer reportVersion;

    @Column(name = "valid_status", nullable = true, length = 10)
    private String validStatus;

    @Column(name = "is_reported", nullable = true, length = 10)
    private String isReported;

    @Column(name = "active_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date activeTime;

    @Column(name = "invalid_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date invalidTime;

    @Column(name = "body_type", nullable = true, length = 32)
    private String bodyType;

    @Column(name = "identify_type", nullable = true, length = 10)
    private String identifyType;

    @Column(name = "identify_number", nullable = true, length = 20)
    private String identifyNumber;
    //columns END
    @Column(name = "commit_status", nullable = true, length = 255)
    private String commitStatus;

    public String getCommitStatus() {
        return commitStatus;
    }

    public void setCommitStatus(String commitStatus) {
        this.commitStatus = commitStatus;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getRegCode() {
        return this.regCode;
    }

    public void setRegCode(String value) {
        this.regCode = value;
    }


    public String getOrignalOrg() {
        return this.orignalOrg;
    }

    public void setOrignalOrg(String value) {
        this.orignalOrg = value;
    }


    public String getOrignalOrgName() {
        return this.orignalOrgName;
    }

    public void setOrignalOrgName(String value) {
        this.orignalOrgName = value;
    }


    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String value) {
        this.orgName = value;
    }


    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String value) {
        this.orgType = value;
    }


    public String getDomesticOrForeign() {
        return this.domesticOrForeign;
    }

    public void setDomesticOrForeign(String value) {
        this.domesticOrForeign = value;
    }


    public String getUniscCode() {
        return this.uniscCode;
    }

    public void setUniscCode(String value) {
        this.uniscCode = value;
    }


    public Long getRegCapital() {
        return this.regCapital;
    }

    public void setRegCapital(Long value) {
        this.regCapital = value;
    }


    public String getRegCapitalCurrency() {
        return this.regCapitalCurrency;
    }

    public void setRegCapitalCurrency(String value) {
        this.regCapitalCurrency = value;
    }


    public String getBusinessScope() {
        return this.businessScope;
    }

    public void setBusinessScope(String value) {
        this.businessScope = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public String getLegalPerson() {
        return this.legalPerson;
    }

    public void setLegalPerson(String value) {
        this.legalPerson = value;
    }


    public String getRegAddress() {
        return this.regAddress;
    }

    public void setRegAddress(String value) {
        this.regAddress = value;
    }


    public Integer getReportVersion() {
        return this.reportVersion;
    }

    public void setReportVersion(Integer value) {
        this.reportVersion = value;
    }


    public String getValidStatus() {
        return this.validStatus;
    }

    public void setValidStatus(String value) {
        this.validStatus = value;
    }


    public String getIsReported() {
        return this.isReported;
    }

    public void setIsReported(String value) {
        this.isReported = value;
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


    public String getBodyType() {
        return this.bodyType;
    }

    public void setBodyType(String value) {
        this.bodyType = value;
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


    @Override
    public String toString() {
        return new StringBuilder("legalPerson[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("regCode = ").append(regCode).append(", \n")
                .append("orignalOrg = ").append(orignalOrg).append(", \n")
                .append("orignalOrgName = ").append(orignalOrgName).append(", \n")
                .append("orgName = ").append(orgName).append(", \n")
                .append("orgType = ").append(orgType).append(", \n")
                .append("domesticOrForeign = ").append(domesticOrForeign).append(", \n")
                .append("uniscCode = ").append(uniscCode).append(", \n")
                .append("regCapital = ").append(regCapital).append(", \n")
                .append("regCapitalCurrency = ").append(regCapitalCurrency).append(", \n")
                .append("businessScope = ").append(businessScope).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("legalPerson = ").append(legalPerson).append(", \n")
                .append("regAddress = ").append(regAddress).append(", \n")
                .append("reportVersion = ").append(reportVersion).append(", \n")
                .append("validStatus = ").append(validStatus).append(", \n")
                .append("isReported = ").append(isReported).append(", \n")
                .append("activeTime = ").append(activeTime).append(", \n")
                .append("invalidTime = ").append(invalidTime).append(", \n")
                .append("bodyType = ").append(bodyType).append(", \n")
                .append("identifyType = ").append(identifyType).append(", \n")
                .append("identifyNumber = ").append(identifyNumber).append(", \n")
                .append("commitStatus = ").append(commitStatus).append(", \n")
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
        if (obj instanceof LegalPerson == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        LegalPerson other = (LegalPerson) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

