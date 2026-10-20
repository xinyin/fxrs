/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:52:13
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;


@Entity
@Table(name = "bus_natural_person_remove")
public class NaturalPersonRemove extends BasePO {

    public static final String TABLE_ALIAS = "自然人关联解除名单";
    public static final String ALIAS_PID = "自增长主键";
    public static final String ALIAS_REG_CODE = "登记编码";
    public static final String ALIAS_ORIGNAL_ORG = "源主体机构";
    public static final String ALIAS_ORIGNAL_ORG_NAME = "源主体银行名称";
    public static final String ALIAS_NAME = "姓名";
    public static final String ALIAS_SEX = "性別";
    public static final String ALIAS_NATIONALITY = "国别/地区";
    public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内/境外(1-境内，2-境外)";
    public static final String ALIAS_RACE = "民族";
    public static final String ALIAS_IDENTIFY_TYPE = "证件类型";
    public static final String ALIAS_IDENTIFY_NUMBER = "证件号码";
    public static final String ALIAS_BIRTHDATE = "出生日期";
    public static final String ALIAS_MOBILE = "移动电话";
    public static final String ALIAS_EMAIL = "邮箱";
    public static final String ALIAS_REPORT_VERSION = "报送版本号";
    public static final String ALIAS_VALID_STATUS = "是否有效 0-无效，1-有效";
    public static final String ALIAS_DUTY_CODE = "职务编码";
    public static final String ALIAS_MARITAL_STATUS = "婚姻状况";
    public static final String ALIAS_STATUS = "状态    未提交/已提交/未审批";
    public static final String ALIAS_IS_REPORTED = "是否被报送过（0-历史上从未报送过，1-已被报送过）";
    public static final String ALIAS_REMOVE_DATE = "计入关联解除时间";
    public static final String ALIAS_REMOVE_CAUSE = "计入关联解除原因";
    public static final String ALIAS_COMMIT_NAME = "提交人";
    public static final String ALIAS_COMMIT_DUTY_CODE = "提交人职位";
    public static final String ALIAS_CHECK_NAME = "复核人";
    public static final String ALIAS_CHECK_DUTY_CODE = "复核人职位";
    public static final String ALIAS_ACTIVE_TIME = "生效时间";
    public static final String ALIAS_INVALID_TIME = "失效时间";


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

    @Column(name = "name", nullable = true, length = 100)
    private String name;

    @Column(name = "sex", nullable = true, length = 10)
    private String sex;

    @Column(name = "nationality", nullable = true, length = 30)
    private String nationality;

    @Column(name = "domestic_or_foreign", nullable = true, length = 10)
    private String domesticOrForeign;

    @Column(name = "race", nullable = true, length = 20)
    private String race;

    @Column(name = "identify_type", nullable = true, length = 10)
    private String identifyType;

    @Column(name = "identify_number", nullable = true, length = 20)
    private String identifyNumber;

    @Column(name = "birthdate", nullable = true, length = 11)
    private String birthdate;

    @Column(name = "mobile", nullable = true, length = 11)
    private String mobile;

    @Column(name = "email", nullable = true, length = 32)
    private String email;

    @Column(name = "report_version", nullable = true, length = 10)
    private Integer reportVersion;

    @Column(name = "valid_status", nullable = true, length = 10)
    private String validStatus;

    @Column(name = "duty_code", nullable = true, length = 255)
    private String dutyCode;

    @Column(name = "marital_status", nullable = true, length = 32)
    private String maritalStatus;

    @Column(name = "status", nullable = true, length = 255)
    private String status;

    @Column(name = "is_reported", nullable = true, length = 10)
    private String isReported;

    @Column(name = "remove_date", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date removeDate;

    @Column(name = "remove_cause", nullable = true, length = 2000)
    private String removeCause;

    @Column(name = "commit_name", nullable = true, length = 500)
    private String commitName;

    @Column(name = "commit_duty_code", nullable = true, length = 11)
    private String commitDutyCode;

    @Column(name = "check_name", nullable = true, length = 500)
    private String checkName;

    @Column(name = "check_duty_code", nullable = true, length = 11)
    private String checkDutyCode;

    @Column(name = "active_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date activeTime;

    @Column(name = "invalid_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date invalidTime;
    //columns END


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


    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }


    public String getSex() {
        return this.sex;
    }

    public void setSex(String value) {
        this.sex = value;
    }


    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String value) {
        this.nationality = value;
    }


    public String getDomesticOrForeign() {
        return this.domesticOrForeign;
    }

    public void setDomesticOrForeign(String value) {
        this.domesticOrForeign = value;
    }


    public String getRace() {
        return this.race;
    }

    public void setRace(String value) {
        this.race = value;
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


    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(String value) {
        this.birthdate = value;
    }


    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String value) {
        this.mobile = value;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String value) {
        this.email = value;
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


    public String getDutyCode() {
        return this.dutyCode;
    }

    public void setDutyCode(String value) {
        this.dutyCode = value;
    }


    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(String value) {
        this.maritalStatus = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public String getIsReported() {
        return this.isReported;
    }

    public void setIsReported(String value) {
        this.isReported = value;
    }


    public Date getRemoveDate() {
        return this.removeDate;
    }

    public void setRemoveDate(Date value) {
        this.removeDate = value;
    }


    public String getRemoveCause() {
        return this.removeCause;
    }

    public void setRemoveCause(String value) {
        this.removeCause = value;
    }


    public String getCommitName() {
        return this.commitName;
    }

    public void setCommitName(String value) {
        this.commitName = value;
    }


    public String getCommitDutyCode() {
        return this.commitDutyCode;
    }

    public void setCommitDutyCode(String value) {
        this.commitDutyCode = value;
    }


    public String getCheckName() {
        return this.checkName;
    }

    public void setCheckName(String value) {
        this.checkName = value;
    }


    public String getCheckDutyCode() {
        return this.checkDutyCode;
    }

    public void setCheckDutyCode(String value) {
        this.checkDutyCode = value;
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


    @Override
    public String toString() {
        return new StringBuilder("naturalPersonRemove[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("regCode = ").append(regCode).append(", \n")
                .append("orignalOrg = ").append(orignalOrg).append(", \n")
                .append("orignalOrgName = ").append(orignalOrgName).append(", \n")
                .append("name = ").append(name).append(", \n")
                .append("sex = ").append(sex).append(", \n")
                .append("nationality = ").append(nationality).append(", \n")
                .append("domesticOrForeign = ").append(domesticOrForeign).append(", \n")
                .append("race = ").append(race).append(", \n")
                .append("identifyType = ").append(identifyType).append(", \n")
                .append("identifyNumber = ").append(identifyNumber).append(", \n")
                .append("birthdate = ").append(birthdate).append(", \n")
                .append("mobile = ").append(mobile).append(", \n")
                .append("email = ").append(email).append(", \n")
                .append("reportVersion = ").append(reportVersion).append(", \n")
                .append("validStatus = ").append(validStatus).append(", \n")
                .append("dutyCode = ").append(dutyCode).append(", \n")
                .append("maritalStatus = ").append(maritalStatus).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("isReported = ").append(isReported).append(", \n")
                .append("removeDate = ").append(removeDate).append(", \n")
                .append("removeCause = ").append(removeCause).append(", \n")
                .append("commitName = ").append(commitName).append(", \n")
                .append("commitDutyCode = ").append(commitDutyCode).append(", \n")
                .append("checkName = ").append(checkName).append(", \n")
                .append("checkDutyCode = ").append(checkDutyCode).append(", \n")
                .append("activeTime = ").append(activeTime).append(", \n")
                .append("invalidTime = ").append(invalidTime).append(", \n")
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
        if (obj instanceof NaturalPersonRemove == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        NaturalPersonRemove other = (NaturalPersonRemove) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

