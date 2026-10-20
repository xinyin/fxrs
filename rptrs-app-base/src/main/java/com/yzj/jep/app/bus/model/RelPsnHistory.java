/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月22日 10:36:32
 *
 */

package com.yzj.jep.app.bus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "bus_rel_psn_history")
public class RelPsnHistory extends BasePO {

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

    @Column(name = "record_type", nullable = true, length = 2)
    private String recordType;


    @Column(name = "source_id", nullable = true, length = 32)
    private Integer sourceId;

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getOrignalOrg() {
        return orignalOrg;
    }

    public void setOrignalOrg(String orignalOrg) {
        this.orignalOrg = orignalOrg;
    }

    public String getOrignalOrgName() {
        return orignalOrgName;
    }

    public void setOrignalOrgName(String orignalOrgName) {
        this.orignalOrgName = orignalOrgName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDomesticOrForeign() {
        return domesticOrForeign;
    }

    public void setDomesticOrForeign(String domesticOrForeign) {
        this.domesticOrForeign = domesticOrForeign;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMgerOrg() {
        return mgerOrg;
    }

    public void setMgerOrg(String mgerOrg) {
        this.mgerOrg = mgerOrg;
    }

    public String getUpApprovalDepartment() {
        return upApprovalDepartment;
    }

    public void setUpApprovalDepartment(String upApprovalDepartment) {
        this.upApprovalDepartment = upApprovalDepartment;
    }

    public String getNextApprovalDepartment() {
        return nextApprovalDepartment;
    }

    public void setNextApprovalDepartment(String nextApprovalDepartment) {
        this.nextApprovalDepartment = nextApprovalDepartment;
    }

    public String getUpApprovalPeople() {
        return upApprovalPeople;
    }

    public void setUpApprovalPeople(String upApprovalPeople) {
        this.upApprovalPeople = upApprovalPeople;
    }

    public String getNextApprovalPeople() {
        return nextApprovalPeople;
    }

    public void setNextApprovalPeople(String nextApprovalPeople) {
        this.nextApprovalPeople = nextApprovalPeople;
    }

    public String getRegulatorYbj() {
        return regulatorYbj;
    }

    public void setRegulatorYbj(String regulatorYbj) {
        this.regulatorYbj = regulatorYbj;
    }

    public String getRegulatorZjh() {
        return regulatorZjh;
    }

    public void setRegulatorZjh(String regulatorZjh) {
        this.regulatorZjh = regulatorZjh;
    }

    public String getRegulatorCzb() {
        return regulatorCzb;
    }

    public void setRegulatorCzb(String regulatorCzb) {
        this.regulatorCzb = regulatorCzb;
    }

    public String getRegulatorOth1() {
        return regulatorOth1;
    }

    public void setRegulatorOth1(String regulatorOth1) {
        this.regulatorOth1 = regulatorOth1;
    }

    public String getRegulatorOth2() {
        return regulatorOth2;
    }

    public void setRegulatorOth2(String regulatorOth2) {
        this.regulatorOth2 = regulatorOth2;
    }

    public String getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(String isBlack) {
        this.isBlack = isBlack;
    }

    public String getBlackReason() {
        return blackReason;
    }

    public void setBlackReason(String blackReason) {
        this.blackReason = blackReason;
    }

    public String getIsShareholder() {
        return isShareholder;
    }

    public void setIsShareholder(String isShareholder) {
        this.isShareholder = isShareholder;
    }

    public String getShareholdersCode() {
        return shareholdersCode;
    }

    public void setShareholdersCode(String shareholdersCode) {
        this.shareholdersCode = shareholdersCode;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getInPrice() {
        return inPrice;
    }

    public void setInPrice(String inPrice) {
        this.inPrice = inPrice;
    }

    public String getHoleShareAmount() {
        return holeShareAmount;
    }

    public void setHoleShareAmount(String holeShareAmount) {
        this.holeShareAmount = holeShareAmount;
    }

    public String getShareholdingProportion() {
        return shareholdingProportion;
    }

    public void setShareholdingProportion(String shareholdingProportion) {
        this.shareholdingProportion = shareholdingProportion;
    }

    public String getFrozenShareAmount() {
        return frozenShareAmount;
    }

    public void setFrozenShareAmount(String frozenShareAmount) {
        this.frozenShareAmount = frozenShareAmount;
    }

    public String getPledgedShareAmount() {
        return pledgedShareAmount;
    }

    public void setPledgedShareAmount(String pledgedShareAmount) {
        this.pledgedShareAmount = pledgedShareAmount;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    public String getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getWorkUnits() {
        return workUnits;
    }

    public void setWorkUnits(String workUnits) {
        this.workUnits = workUnits;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getRelSerial() {
        return relSerial;
    }

    public void setRelSerial(String relSerial) {
        this.relSerial = relSerial;
    }

    public String getDjStatus() {
        return djStatus;
    }

    public void setDjStatus(String djStatus) {
        this.djStatus = djStatus;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getIsMainGd() {
        return isMainGd;
    }

    public void setIsMainGd(String isMainGd) {
        this.isMainGd = isMainGd;
    }

    public String getCapitalNumber() {
        return capitalNumber;
    }

    public void setCapitalNumber(String capitalNumber) {
        this.capitalNumber = capitalNumber;
    }

    public String getIsNoShareVote() {
        return isNoShareVote;
    }

    public void setIsNoShareVote(String isNoShareVote) {
        this.isNoShareVote = isNoShareVote;
    }

    public String getVoteProportion() {
        return voteProportion;
    }

    public void setVoteProportion(String voteProportion) {
        this.voteProportion = voteProportion;
    }

    public String getLimitVoteSituation() {
        return limitVoteSituation;
    }

    public void setLimitVoteSituation(String limitVoteSituation) {
        this.limitVoteSituation = limitVoteSituation;
    }

    public String getAligCode() {
        return aligCode;
    }

    public void setAligCode(String aligCode) {
        this.aligCode = aligCode;
    }

    public String getPledgeRatio() {
        return pledgeRatio;
    }

    public void setPledgeRatio(String pledgeRatio) {
        this.pledgeRatio = pledgeRatio;
    }

    public String getIsAssoParty() {
        return isAssoParty;
    }

    public void setIsAssoParty(String isAssoParty) {
        this.isAssoParty = isAssoParty;
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

    public String getEffectRelation() {
        return effectRelation;
    }

    public void setEffectRelation(String effectRelation) {
        this.effectRelation = effectRelation;
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

    public String getRelateNumber() {
        return relateNumber;
    }

    public void setRelateNumber(String relateNumber) {
        this.relateNumber = relateNumber;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String getDelflag() {
        return delflag;
    }

    @Override
    public void setDelflag(String delflag) {
        this.delflag = delflag;
    }

    public String getGlfbh() {
        return glfbh;
    }

    public void setGlfbh(String glfbh) {
        this.glfbh = glfbh;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
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
    public String toString() {
        final StringBuilder sb = new StringBuilder("RelPsn{");
        sb.append("pid=").append(pid);
        sb.append(", regCode='").append(regCode).append('\'');
        sb.append(", orignalOrg='").append(orignalOrg).append('\'');
        sb.append(", orignalOrgName='").append(orignalOrgName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", nationality='").append(nationality).append('\'');
        sb.append(", domesticOrForeign='").append(domesticOrForeign).append('\'');
        sb.append(", race='").append(race).append('\'');
        sb.append(", identifyType='").append(identifyType).append('\'');
        sb.append(", identifyNumber='").append(identifyNumber).append('\'');
        sb.append(", birthdate='").append(birthdate).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", dutyCode='").append(dutyCode).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", statusComment='").append(statusComment).append('\'');
        sb.append(", maritalStatus='").append(maritalStatus).append('\'');
        sb.append(", validStatus='").append(validStatus).append('\'');
        sb.append(", activeTime=").append(activeTime);
        sb.append(", invalidTime=").append(invalidTime);
        sb.append(", mgerOrg='").append(mgerOrg).append('\'');
        sb.append(", upApprovalDepartment='").append(upApprovalDepartment).append('\'');
        sb.append(", nextApprovalDepartment='").append(nextApprovalDepartment).append('\'');
        sb.append(", upApprovalPeople='").append(upApprovalPeople).append('\'');
        sb.append(", nextApprovalPeople='").append(nextApprovalPeople).append('\'');
        sb.append(", regulatorYbj='").append(regulatorYbj).append('\'');
        sb.append(", regulatorZjh='").append(regulatorZjh).append('\'');
        sb.append(", regulatorCzb='").append(regulatorCzb).append('\'');
        sb.append(", regulatorOth1='").append(regulatorOth1).append('\'');
        sb.append(", regulatorOth2='").append(regulatorOth2).append('\'');
        sb.append(", isBlack='").append(isBlack).append('\'');
        sb.append(", blackReason='").append(blackReason).append('\'');
        sb.append(", isShareholder='").append(isShareholder).append('\'');
        sb.append(", shareholdersCode='").append(shareholdersCode).append('\'');
        sb.append(", openBank='").append(openBank).append('\'');
        sb.append(", tradeAccount='").append(tradeAccount).append('\'');
        sb.append(", inTime='").append(inTime).append('\'');
        sb.append(", inPrice='").append(inPrice).append('\'');
        sb.append(", holeShareAmount='").append(holeShareAmount).append('\'');
        sb.append(", shareholdingProportion='").append(shareholdingProportion).append('\'');
        sb.append(", frozenShareAmount='").append(frozenShareAmount).append('\'');
        sb.append(", pledgedShareAmount='").append(pledgedShareAmount).append('\'');
        sb.append(", relType='").append(relType).append('\'');
        sb.append(", politicsStatus='").append(politicsStatus).append('\'');
        sb.append(", educationBackground='").append(educationBackground).append('\'');
        sb.append(", residentialAddress='").append(residentialAddress).append('\'');
        sb.append(", workUnits='").append(workUnits).append('\'');
        sb.append(", unitAddress='").append(unitAddress).append('\'');
        sb.append(", relSerial='").append(relSerial).append('\'');
        sb.append(", djStatus='").append(djStatus).append('\'');
        sb.append(", dataSource='").append(dataSource).append('\'');
        sb.append(", isMainGd='").append(isMainGd).append('\'');
        sb.append(", capitalNumber='").append(capitalNumber).append('\'');
        sb.append(", isNoShareVote='").append(isNoShareVote).append('\'');
        sb.append(", voteProportion='").append(voteProportion).append('\'');
        sb.append(", limitVoteSituation='").append(limitVoteSituation).append('\'');
        sb.append(", aligCode='").append(aligCode).append('\'');
        sb.append(", isIterate='").append(isIterate).append('\'');
        sb.append(", pledgeRatio='").append(pledgeRatio).append('\'');
        sb.append(", isAssoParty='").append(isAssoParty).append('\'');
        sb.append(", amendId='").append(amendId).append('\'');
        sb.append(", amendName='").append(amendName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RelPsnHistory == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelPsnHistory other = (RelPsnHistory) obj;
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
}

