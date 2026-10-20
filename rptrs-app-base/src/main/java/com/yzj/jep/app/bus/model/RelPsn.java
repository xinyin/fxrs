/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月22日 10:36:32
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "bus_rel_psn")
public class RelPsn extends BasePO {

    public static final String TABLE_ALIAS = "关联关系自然人表";
    public static final String ALIAS_PID = "自增长主键";
    public static final String ALIAS_REG_CODE = "登记编码";
    public static final String ALIAS_ORIGNAL_ORG = "源主体银行机构";
    public static final String ALIAS_ORIGNAL_ORG_NAME = "源主体银行机构名称";
    public static final String ALIAS_NAME = "姓名";
    public static final String ALIAS_SEX = "性別";
    public static final String ALIAS_NATIONALITY = "国别/地区";
    public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内外标识";
    public static final String ALIAS_RACE = "民族";
    public static final String ALIAS_IDENTIFY_TYPE = "证件类型";
    public static final String ALIAS_IDENTIFY_NUMBER = "证件号码";
    public static final String ALIAS_BIRTHDATE = "出生日期";
    public static final String ALIAS_MOBILE = "移动电话";
    public static final String ALIAS_EMAIL = "邮箱";
    public static final String ALIAS_DUTY_CODE = "职位";
    public static final String ALIAS_STATUS = "审批状态    未提交/已提交/未审批";
    public static final String ALIAS_STATUS_COMMENT = "审批状态说明";
    public static final String ALIAS_MARITAL_STATUS = "婚姻状况";
    public static final String ALIAS_VALID_STATUS = "是否有效   0|无效1|有效";
    public static final String ALIAS_ACTIVE_TIME = "生效时间";
    public static final String ALIAS_INVALID_TIME = "失效时间";
    public static final String ALIAS_MGER_ORG = "管户机构(默认是总行)";
    public static final String ALIAS_UP_APPROVAL_DEPARTMENT = "上一级审批部门";
    public static final String ALIAS_NEXT_APPROVAL_DEPARTMENT = "下一级审批部门";
    public static final String ALIAS_UP_APPROVAL_PEOPLE = "上一级审批人";
    public static final String ALIAS_NEXT_APPROVAL_PEOPLE = "下一级审批人";
    public static final String ALIAS_REGULATOR_YBJ = "银保监口径  0|否 1|是";
    public static final String ALIAS_REGULATOR_ZJH = "证监会口径（0|否1|是）";
    public static final String ALIAS_REGULATOR_CZB = "财政部口径（0|否1|是）";
    public static final String ALIAS_REGULATOR_OTH1 = "其他口径1（0|否1|是）";
    public static final String ALIAS_REGULATOR_OTH2 = "其他口径2（0|否1|是）";
    public static final String ALIAS_IS_BLACK = "是否黑名单（0|否1|是）";
    public static final String ALIAS_BLACK_REASON = "加入/移出黑名单原因";
    public static final String ALIAS_IS_SHAREHOLDER = "是否为股东";
    public static final String ALIAS_SHAREHOLDERS_CODE = "股东代码（账户号）";
    public static final String ALIAS_OPEN_BANK = "交易账户开户行";
    public static final String ALIAS_TRADE_ACCOUNT = "交易账户账号";
    public static final String ALIAS_IN_TIME = "入股时间";
    public static final String ALIAS_IN_PRICE = "入股价格";
    public static final String ALIAS_HOLE_SHARE_AMOUNT = "持股数量（股）";
    public static final String ALIAS_SHAREHOLDING_PROPORTION = "持股占比";
    public static final String ALIAS_FROZEN_SHARE_AMOUNT = "总冻结股数（股）";
    public static final String ALIAS_PLEDGED_SHARE_AMOUNT = "总质押股数（股）";
    public static final String ALIAS_REL_TYPE = "关联方类型 ( 0-股东 1-关联方 2-股东&关联方）";
    public static final String ALIAS_POLITICS_STATUS = "政治面貌";
    public static final String ALIAS_EDUCATION_BACKGROUND = "学历";
    public static final String ALIAS_RESIDENTIAL_ADDRESS = "居住地址";
    public static final String ALIAS_WORK_UNITS = "工作单位";
    public static final String ALIAS_UNIT_ADDRESS = "单位地址";
    public static final String ALIAS_REL_SERIAL = "流水号";
    public static final String ALIAS_DJ_STATUS = "冻结状态";
    public static final String ALIAS_DATA_SOURCE = "dataSource";
    public static final String ALIAS_IS_MAIN_GD = "isMainGd";
    public static final String ALIAS_CAPITAL_NUMBER = "capitalNumber";
    public static final String ALIAS_IS_NO_SHARE_VOTE = "isNoShareVote";
    public static final String ALIAS_VOTE_PROPORTION = "voteProportion";
    public static final String ALIAS_LIMIT_VOTE_SITUATION = "limitVoteSituation";
    public static final String ALIAS_IS_ITERATE = "是否需要遍历更新相关定位码：0不需要，1需要";
    public static final String ALIAS_PLEDGE_RATIO = "质押股权比例";
    public static final String ALIAS_IS_ASSOPARTY = "是否关联方 1否，0是";

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

    @Column(name = "name_", nullable = true, length = 100)
    private String name;

    @Column(name = "sex", nullable = true, length = 10)
    private String sex;

    @Column(name = "nationality", nullable = true, length = 30)
    private String nationality;

    @Column(name = "domestic_or_foreign", nullable = true, length = 10)
    private String domesticOrForeign;

    @Column(name = "race", nullable = true, length = 20)
    private String race;

    @Column(name = "identify_type", nullable = true, length = 50)
    private String identifyType;

    @Column(name = "identify_number", nullable = true, length = 20)
    private String identifyNumber;

    @Column(name = "birthdate", nullable = true, length = 11)
    private String birthdate;

    @Column(name = "mobile", nullable = true, length = 20)
    private String mobile;

    @Column(name = "email", nullable = true, length = 32)
    private String email;

    @Column(name = "duty_code", nullable = true, length = 255)
    private String dutyCode;

    @Column(name = "status", nullable = true, length = 255)
    private String status;

    @Column(name = "status_comment", nullable = true, length = 255)
    private String statusComment;

    @Column(name = "marital_status", nullable = true, length = 32)
    private String maritalStatus;

    @Column(name = "valid_status", nullable = true, length = 10)
    private String validStatus;


    @Column(name = "active_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date activeTime;


    @Column(name = "invalid_time", nullable = true, length = 19)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date invalidTime;

    @Column(name = "CREATED_TIME_", nullable = true, length = 6)
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date createTime;

//    @Column(name = "MODIFIED_TIME_", nullable = true, length = 0)
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
//    @Temporal(TemporalType.DATE)
//    private Date modifiedTime;

    @Column(name = "mger_org", nullable = true, length = 50)
    private String mgerOrg;

    @Column(name = "up_approval_department", nullable = true, length = 50)
    private String upApprovalDepartment;

    @Column(name = "next_approval_department", nullable = true, length = 50)
    private String nextApprovalDepartment;

    @Column(name = "up_approval_people", nullable = true, length = 50)
    private String upApprovalPeople;

    @Column(name = "next_approval_people", nullable = true, length = 50)
    private String nextApprovalPeople;

    @Column(name = "regulator_ybj", nullable = true, length = 50)
    private String regulatorYbj;

    @Column(name = "regulator_zjh", nullable = true, length = 50)
    private String regulatorZjh;

    @Column(name = "regulator_czb", nullable = true, length = 50)
    private String regulatorCzb;

    @Column(name = "regulator_oth1", nullable = true, length = 50)
    private String regulatorOth1;

    @Column(name = "regulator_oth2", nullable = true, length = 50)
    private String regulatorOth2;

    @Column(name = "is_black", nullable = true, length = 4)
    private String isBlack;

    @Column(name = "black_reason", nullable = true, length = 1024)
    private String blackReason;

    @Column(name = "is_shareholder", nullable = true, length = 16)
    private String isShareholder;

    @Column(name = "shareholders_code", nullable = true, length = 100)
    private String shareholdersCode;

    @Column(name = "open_bank", nullable = true, length = 100)
    private String openBank;

    @Column(name = "trade_account", nullable = true, length = 50)
    private String tradeAccount;

    @Column(name = "in_time", nullable = true, length = 11)
    private String inTime;

    @Column(name = "in_price", nullable = true, length = 20)
    private String inPrice;

    @Column(name = "hole_share_amount", nullable = true, length = 50)
    private String holeShareAmount;

    @Column(name = "shareholding_proportion", nullable = true, length = 10)
    private String shareholdingProportion;

    @Column(name = "frozen_share_amount", nullable = true, length = 20)
    private String frozenShareAmount;

    @Column(name = "pledged_share_amount", nullable = true, length = 20)
    private String pledgedShareAmount;

    @Column(name = "rel_type", nullable = true, length = 20)
    private String relType;

    @Column(name = "politics_status", nullable = true, length = 64)
    private String politicsStatus;

    @Column(name = "education_background", nullable = true, length = 64)
    private String educationBackground;

    @Column(name = "residential_address", nullable = true, length = 64)
    private String residentialAddress;

    @Column(name = "work_units", nullable = true, length = 64)
    private String workUnits;

    @Column(name = "unit_address", nullable = true, length = 64)
    private String unitAddress;

    @Column(name = "rel_serial", nullable = true, length = 64)
    private String relSerial;

    @Column(name = "dj_status", nullable = true, length = 1)
    private String djStatus;

    @Column(name = "data_source", nullable = true, length = 64)
    private String dataSource;

    @Column(name = "is_main_gd", nullable = true, length = 16)
    private String isMainGd;

    @Column(name = "capital_number", nullable = true, length = 64)
    private String capitalNumber;

    @Column(name = "is_no_share_vote", nullable = true, length = 16)
    private String isNoShareVote;

    @Column(name = "vote_proportion", nullable = true, length = 20)
    private String voteProportion;

    @Column(name = "limit_vote_situation", nullable = true, length = 200)
    private String limitVoteSituation;

    @Column(name = "alig_code", nullable = true, length = 100)
    private String aligCode;

    @Column(name = "is_Iterate", nullable = true, length = 1)
    private String isIterate;
    //columns END

    @Column(name = "pledge_ratio", nullable = true, length = 10)
    private String pledgeRatio;

    @Column(name = "is_assoparty", nullable = true, length = 1)
    private String isAssoParty;

    @Column(name = "AMEND_ID", nullable = true, length = 50)
    private String amendId;

    @Column(name = "AMEND_NAME", nullable = true, length = 50)
    private String amendName;

    @Column(name = "effect_relation", nullable = true, length = 6)
    private String effectRelation;

    //经办部门
    @Column(name = "TRANSACTION_DEP", nullable = true, length = 50)
    private String transactionDep;

    @Column(name = "is_change",nullable = true,length = 1)
    private String isChange;

    public String getIsChange() {
        return isChange;
    }

    public void setIsChange(String isChange) {
        this.isChange = isChange;
    }

    //经办人
    @Column(name = "OPERATOR", nullable = true, length = 100)
    private String operator;

    //关联方编号
    @Column(name = "relate_number", nullable = true, length = 20)
    private String relateNumber;

    //交易主体
    @Column(name ="TENANT_ID_",length = 20)
    private String tenantId;
    //删除标记
    @Column(name ="DELFLAG_")
    private String delflag;


    @Column(name = "GLFBH", nullable = true, length = 65)
    private String glfbh;


    @Column(name = "record_number")
    private String recordNumber;

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getRelateNumber() {
        return relateNumber;
    }

    public void setRelateNumber(String relateNumber) {
        this.relateNumber = relateNumber;
    }
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

    public String getIsAssoParty() {
        return isAssoParty;
    }

    public void setIsAssoParty(String isAssoParty) {
        this.isAssoParty = isAssoParty;
    }

    public String getPledgeRatio() {
        return pledgeRatio;
    }

    public void setPledgeRatio(String pledgeRatio) {
        this.pledgeRatio = pledgeRatio;
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


    public String getDutyCode() {
        return this.dutyCode;
    }

    public void setDutyCode(String value) {
        this.dutyCode = value;
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


    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(String value) {
        this.maritalStatus = value;
    }


    public String getValidStatus() {
        return this.validStatus;
    }

    public void setValidStatus(String value) {
        this.validStatus = value;
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


    public String getMgerOrg() {
        return this.mgerOrg;
    }

    public void setMgerOrg(String value) {
        this.mgerOrg = value;
    }


    public String getUpApprovalDepartment() {
        return this.upApprovalDepartment;
    }

    public void setUpApprovalDepartment(String value) {
        this.upApprovalDepartment = value;
    }


    public String getNextApprovalDepartment() {
        return this.nextApprovalDepartment;
    }

    public void setNextApprovalDepartment(String value) {
        this.nextApprovalDepartment = value;
    }


    public String getUpApprovalPeople() {
        return this.upApprovalPeople;
    }

    public void setUpApprovalPeople(String value) {
        this.upApprovalPeople = value;
    }


    public String getNextApprovalPeople() {
        return this.nextApprovalPeople;
    }

    public void setNextApprovalPeople(String value) {
        this.nextApprovalPeople = value;
    }


    public String getRegulatorYbj() {
        return this.regulatorYbj;
    }

    public void setRegulatorYbj(String value) {
        this.regulatorYbj = value;
    }


    public String getRegulatorZjh() {
        return this.regulatorZjh;
    }

    public void setRegulatorZjh(String value) {
        this.regulatorZjh = value;
    }


    public String getRegulatorCzb() {
        return this.regulatorCzb;
    }

    public void setRegulatorCzb(String value) {
        this.regulatorCzb = value;
    }


    public String getRegulatorOth1() {
        return this.regulatorOth1;
    }

    public void setRegulatorOth1(String value) {
        this.regulatorOth1 = value;
    }


    public String getRegulatorOth2() {
        return this.regulatorOth2;
    }

    public void setRegulatorOth2(String value) {
        this.regulatorOth2 = value;
    }


    public String getIsBlack() {
        return this.isBlack;
    }

    public void setIsBlack(String value) {
        this.isBlack = value;
    }


    public String getBlackReason() {
        return this.blackReason;
    }

    public void setBlackReason(String value) {
        this.blackReason = value;
    }


    public String getIsShareholder() {
        return this.isShareholder;
    }

    public void setIsShareholder(String value) {
        this.isShareholder = value;
    }


    public String getShareholdersCode() {
        return this.shareholdersCode;
    }

    public void setShareholdersCode(String value) {
        this.shareholdersCode = value;
    }


    public String getOpenBank() {
        return this.openBank;
    }

    public void setOpenBank(String value) {
        this.openBank = value;
    }


    public String getTradeAccount() {
        return this.tradeAccount;
    }

    public void setTradeAccount(String value) {
        this.tradeAccount = value;
    }


    public String getInTime() {
        return this.inTime;
    }

    public void setInTime(String value) {
        this.inTime = value;
    }


    public String getInPrice() {
        return this.inPrice;
    }

    public void setInPrice(String value) {
        this.inPrice = value;
    }


    public String getHoleShareAmount() {
        return this.holeShareAmount;
    }

    public void setHoleShareAmount(String value) {
        this.holeShareAmount = value;
    }


    public String getShareholdingProportion() {
        return this.shareholdingProportion;
    }

    public void setShareholdingProportion(String value) {
        this.shareholdingProportion = value;
    }


    public String getFrozenShareAmount() {
        return this.frozenShareAmount;
    }

    public void setFrozenShareAmount(String value) {
        this.frozenShareAmount = value;
    }


    public String getPledgedShareAmount() {
        return this.pledgedShareAmount;
    }

    public void setPledgedShareAmount(String value) {
        this.pledgedShareAmount = value;
    }


    public String getRelType() {
        return this.relType;
    }

    public void setRelType(String value) {
        this.relType = value;
    }


    public String getPoliticsStatus() {
        return this.politicsStatus;
    }

    public void setPoliticsStatus(String value) {
        this.politicsStatus = value;
    }


    public String getEducationBackground() {
        return this.educationBackground;
    }

    public void setEducationBackground(String value) {
        this.educationBackground = value;
    }


    public String getResidentialAddress() {
        return this.residentialAddress;
    }

    public void setResidentialAddress(String value) {
        this.residentialAddress = value;
    }


    public String getWorkUnits() {
        return this.workUnits;
    }

    public void setWorkUnits(String value) {
        this.workUnits = value;
    }


    public String getUnitAddress() {
        return this.unitAddress;
    }

    public void setUnitAddress(String value) {
        this.unitAddress = value;
    }


    public String getRelSerial() {
        return this.relSerial;
    }

    public void setRelSerial(String value) {
        this.relSerial = value;
    }


    public String getDjStatus() {
        return this.djStatus;
    }

    public void setDjStatus(String value) {
        this.djStatus = value;
    }


    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String value) {
        this.dataSource = value;
    }


    public String getIsMainGd() {
        return this.isMainGd;
    }

    public void setIsMainGd(String value) {
        this.isMainGd = value;
    }


    public String getCapitalNumber() {
        return this.capitalNumber;
    }

    public void setCapitalNumber(String value) {
        this.capitalNumber = value;
    }


    public String getIsNoShareVote() {
        return this.isNoShareVote;
    }

    public void setIsNoShareVote(String value) {
        this.isNoShareVote = value;
    }


    public String getVoteProportion() {
        return this.voteProportion;
    }

    public void setVoteProportion(String value) {
        this.voteProportion = value;
    }


    public String getLimitVoteSituation() {
        return this.limitVoteSituation;
    }

    public void setLimitVoteSituation(String value) {
        this.limitVoteSituation = value;
    }

    public String getAligCode() {
        return aligCode;
    }

    public void setAligCode(String aligCode) {
        this.aligCode = aligCode;
    }

    public String getEffectRelation() {
        return effectRelation;
    }

    public void setEffectRelation(String effectRelation) {
        this.effectRelation = effectRelation;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date value) {
        this.createTime = value;
    }

    public String getGlfbh() {
        return glfbh;
    }

    public void setGlfbh(String glfbh) {
        this.glfbh = glfbh;
    }

    @Override
    public String toString() {
        return "RelPsn{" +
                "pid=" + pid +
                ", regCode='" + regCode + '\'' +
                ", orignalOrg='" + orignalOrg + '\'' +
                ", orignalOrgName='" + orignalOrgName + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", nationality='" + nationality + '\'' +
                ", domesticOrForeign='" + domesticOrForeign + '\'' +
                ", race='" + race + '\'' +
                ", identifyType='" + identifyType + '\'' +
                ", identifyNumber='" + identifyNumber + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", dutyCode='" + dutyCode + '\'' +
                ", status='" + status + '\'' +
                ", statusComment='" + statusComment + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", validStatus='" + validStatus + '\'' +
                ", activeTime=" + activeTime +
                ", invalidTime=" + invalidTime +
                ", createTime=" + createTime +
                ", mgerOrg='" + mgerOrg + '\'' +
                ", upApprovalDepartment='" + upApprovalDepartment + '\'' +
                ", nextApprovalDepartment='" + nextApprovalDepartment + '\'' +
                ", upApprovalPeople='" + upApprovalPeople + '\'' +
                ", nextApprovalPeople='" + nextApprovalPeople + '\'' +
                ", regulatorYbj='" + regulatorYbj + '\'' +
                ", regulatorZjh='" + regulatorZjh + '\'' +
                ", regulatorCzb='" + regulatorCzb + '\'' +
                ", regulatorOth1='" + regulatorOth1 + '\'' +
                ", regulatorOth2='" + regulatorOth2 + '\'' +
                ", isBlack='" + isBlack + '\'' +
                ", blackReason='" + blackReason + '\'' +
                ", isShareholder='" + isShareholder + '\'' +
                ", shareholdersCode='" + shareholdersCode + '\'' +
                ", openBank='" + openBank + '\'' +
                ", tradeAccount='" + tradeAccount + '\'' +
                ", inTime='" + inTime + '\'' +
                ", inPrice='" + inPrice + '\'' +
                ", holeShareAmount='" + holeShareAmount + '\'' +
                ", shareholdingProportion='" + shareholdingProportion + '\'' +
                ", frozenShareAmount='" + frozenShareAmount + '\'' +
                ", pledgedShareAmount='" + pledgedShareAmount + '\'' +
                ", relType='" + relType + '\'' +
                ", politicsStatus='" + politicsStatus + '\'' +
                ", educationBackground='" + educationBackground + '\'' +
                ", residentialAddress='" + residentialAddress + '\'' +
                ", workUnits='" + workUnits + '\'' +
                ", unitAddress='" + unitAddress + '\'' +
                ", relSerial='" + relSerial + '\'' +
                ", djStatus='" + djStatus + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", isMainGd='" + isMainGd + '\'' +
                ", capitalNumber='" + capitalNumber + '\'' +
                ", isNoShareVote='" + isNoShareVote + '\'' +
                ", voteProportion='" + voteProportion + '\'' +
                ", limitVoteSituation='" + limitVoteSituation + '\'' +
                ", aligCode='" + aligCode + '\'' +
                ", isIterate='" + isIterate + '\'' +
                ", pledgeRatio='" + pledgeRatio + '\'' +
                ", isAssoParty='" + isAssoParty + '\'' +
                ", amendId='" + amendId + '\'' +
                ", amendName='" + amendName + '\'' +
                ", effectRelation='" + effectRelation + '\'' +
                ", transactionDep='" + transactionDep + '\'' +
                ", isChange='" + isChange + '\'' +
                ", operator='" + operator + '\'' +
                ", relateNumber='" + relateNumber + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", delflag='" + delflag + '\'' +
                ", glfbh='" + glfbh + '\'' +
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
        if (obj instanceof RelPsn == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelPsn other = (RelPsn) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }

    public String getIsIterate() {
        return isIterate;
    }

    public void setIsIterate(String isIterate) {
        this.isIterate = isIterate;
    }

    @Override
    public void setCreateTimeString(String value){
        if (value != null){
            setCreateTime(DateUtils.parseDate(value));
        }
    }

    @Override
    public String getCreateTimeString(){
        if (Optional.ofNullable(createTime).isPresent()) {
            return DateUtils.formatDate(createTime);
        }
        return "";
    }

    @Override
    public void setModifiedTimeString(String modifiedTimeString){
        if (modifiedTimeString != null){
            setModifiedTime(DateUtils.parseDate(modifiedTimeString));
        }
    }

//    public String setModifiedTimeString(){
//        return modifiedTime != null ? DateUtils.formatDate(modifiedTime):"";
//    }
}

