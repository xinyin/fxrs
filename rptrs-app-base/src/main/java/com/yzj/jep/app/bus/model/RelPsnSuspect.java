/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月22日 10:15:05
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.*;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;


@Entity
@Table(name = "bus_rel_psn_suspect")
public class RelPsnSuspect extends BasePO {

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
    public static final String ALIAS_PROCESS_TYPE = "流程类型";
    public static final String ALIAS_DUTY_CODE = "职位";
    public static final String ALIAS_STATUS = "审批状态    未提交/已提交/未审批";
    public static final String ALIAS_COGNIZA_REASON = "cognizaReason";
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
    public static final String ALIAS_IS_SUSPECT = "isSuspect";
    public static final String ALIAS_IS_BLACK = "是否黑名单（0|否1|是）";
    public static final String ALIAS_REL_SERIAL = "relSerial";
    public static final String ALIAS_BLACK_REASON = "加入黑名单原因";
    public static final String ALIAS_IS_SHAREHOLDER = "是否为股东";
    public static final String ALIAS_SHAREHOLDERS_CODE = "股东代码";
    public static final String ALIAS_LEGAL_IDENTIFY_TYPE = "股东证件类型";
    public static final String ALIAS_LEGAL_IDENTIFY_NUMBER = "股东证件号码";
    public static final String ALIAS_STATE_OWNED = "是否国有企业";
    public static final String ALIAS_OPEN_BANK = "账户关联银行账户开户行";
    public static final String ALIAS_TRADE_ACCOUNT = "关联银行户账号";
    public static final String ALIAS_HOLE_SHARE_AMOUNT = "持股数量";
    public static final String ALIAS_SHAREHOLDING_PROPORTION = "持股占比";
    public static final String ALIAS_FROZEN_SHARE = "总冻结股数（股）";
    public static final String ALIAS_PLEDGED_SHARE = "总质押股数（股）";
    public static final String ALIAS_REL_TYPE = "关联方类型";
    public static final String ALIAS_POLITICS_STATUS = "政治面貌";
    public static final String ALIAS_EDUCATION_BACKGROUND = "学历";
    public static final String ALIAS_RESIDENTIAL_ADDRESS = "居住地址";
    public static final String ALIAS_WORK_UNITS = "工作单位";
    public static final String ALIAS_UNIT_ADDRESS = "单位地址";
    public static final String ALIAS_IN_TIME = "入股时间";
    public static final String ALIAS_IN_PRICE = "入股价格";
    public static final String ALIAS_DATA_SOURCE = "dataSource";
    public static final String ALIAS_IS_MAIN_GD = "isMainGd";
    public static final String ALIAS_CAPITAL_NUMBER = "capitalNumber";
    public static final String ALIAS_IS_NO_SHARE_VOTE = "isNoShareVote";
    public static final String ALIAS_VOTE_PROPORTION = "voteProportion";
    public static final String ALIAS_LIMIT_VOTE_SITUATION = "limitVoteSituation";


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

    @Column(name = "identify_type", nullable = true, length = 30)
    private String identifyType;

    @Column(name = "identify_number", nullable = true, length = 30)
    private String identifyNumber;

    @Column(name = "birthdate", nullable = true, length = 11)
    private String birthdate;

    @Column(name = "mobile", nullable = true, length = 11)
    private String mobile;

    @Column(name = "email", nullable = true, length = 32)
    private String email;

    @Column(name = "process_type", nullable = true, length = 20)
    private String processType;

    @Column(name = "duty_code", nullable = true, length = 255)
    private String dutyCode;

    @Column(name = "status", nullable = true, length = 255)
    private String status;

    @Column(name = "cogniza_reason", nullable = true, length = 255)
    private String cognizaReason;

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
    @Temporal(TemporalType.DATE)
    private Date invalidTime;

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

    @Column(name = "is_suspect", nullable = true, length = 4)
    private String isSuspect;

    @Column(name = "is_black", nullable = true, length = 4)
    private String isBlack;

    @Column(name = "rel_serial", nullable = true, length = 64)
    private String relSerial;

    @Column(name = "black_reason", nullable = true, length = 1024)
    private String blackReason;

    @Column(name = "is_shareholder", nullable = true, length = 16)
    private String isShareholder;

    @Column(name = "shareholders_code", nullable = true, length = 100)
    private String shareholdersCode;

    @Column(name = "legal_identify_type", nullable = true, length = 10)
    private String legalIdentifyType;

    @Column(name = "legal_identify_number", nullable = true, length = 20)
    private String legalIdentifyNumber;

    @Column(name = "state_owned", nullable = true, length = 10)
    private String stateOwned;

    @Column(name = "open_bank", nullable = true, length = 100)
    private String openBank;

    @Column(name = "trade_account", nullable = true, length = 50)
    private String tradeAccount;

    @Column(name = "hole_share_amount", nullable = true, length = 50)
    private String holeShareAmount;

    @Column(name = "shareholding_proportion", nullable = true, length = 10)
    private String shareholdingProportion;

    @Column(name = "frozen_share", nullable = true, length = 50)
    private String frozenShare;

    @Column(name = "pledged_share", nullable = true, length = 50)
    private String pledgedShare;

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

    @Column(name = "in_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date inTime;

    @Column(name = "in_price", nullable = true, length = 20)
    private String inPrice;

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
    //columns END
    @Column(name = "frozen_share_amount", nullable = true, length = 20)
    private String frozenShareAmount;

    @Column(name = "pledged_share_amount", nullable = true, length = 20)
    private String pledgedShareAmount;

    @Column(name = "alig_code", nullable = true, length = 100)
    private String aligCode;

    @Column(name = "is_assoparty", nullable = true, length = 10)
    private String isAssoParty;
    @Column(name = "proc_inst_id", nullable = true, length = 10)
    private String procInstId;

    @Column(name="DELFLAG_",nullable = true,length = 10)
    private String deflag;

    public String getDeflag() {
        return deflag;
    }

    public void setDeflag(String deflag) {
        this.deflag = deflag;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getIsAssoParty() {
        return isAssoParty;
    }

    public void setIsAssoParty(String isAssoParty) {
        this.isAssoParty = isAssoParty;
    }

    public String getAligCode() {
        return aligCode;
    }

    public void setAligCode(String aligCode) {
        this.aligCode = aligCode;
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


    public String getProcessType() {
        return this.processType;
    }

    public void setProcessType(String value) {
        this.processType = value;
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


    public String getCognizaReason() {
        return this.cognizaReason;
    }

    public void setCognizaReason(String value) {
        this.cognizaReason = value;
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


    public String getIsSuspect() {
        return this.isSuspect;
    }

    public void setIsSuspect(String value) {
        this.isSuspect = value;
    }


    public String getIsBlack() {
        return this.isBlack;
    }

    public void setIsBlack(String value) {
        this.isBlack = value;
    }


    public String getRelSerial() {
        return this.relSerial;
    }

    public void setRelSerial(String value) {
        this.relSerial = value;
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


    public String getLegalIdentifyType() {
        return this.legalIdentifyType;
    }

    public void setLegalIdentifyType(String value) {
        this.legalIdentifyType = value;
    }


    public String getLegalIdentifyNumber() {
        return this.legalIdentifyNumber;
    }

    public void setLegalIdentifyNumber(String value) {
        this.legalIdentifyNumber = value;
    }


    public String getStateOwned() {
        return this.stateOwned;
    }

    public void setStateOwned(String value) {
        this.stateOwned = value;
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


    public String getFrozenShare() {
        return this.frozenShare;
    }

    public void setFrozenShare(String value) {
        this.frozenShare = value;
    }


    public String getPledgedShare() {
        return this.pledgedShare;
    }

    public void setPledgedShare(String value) {
        this.pledgedShare = value;
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


    public Date getInTime() {
        return this.inTime;
    }

    public void setInTime(Date value) {
        this.inTime = value;
    }


    public String getInPrice() {
        return this.inPrice;
    }

    public void setInPrice(String value) {
        this.inPrice = value;
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


    @Override
    public String toString() {
        return new StringBuilder("relPsnSuspect[\n")
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
                .append("processType = ").append(processType).append(", \n")
                .append("dutyCode = ").append(dutyCode).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("cognizaReason = ").append(cognizaReason).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("maritalStatus = ").append(maritalStatus).append(", \n")
                .append("validStatus = ").append(validStatus).append(", \n")
                .append("activeTime = ").append(activeTime).append(", \n")
                .append("invalidTime = ").append(invalidTime).append(", \n")
                .append("mgerOrg = ").append(mgerOrg).append(", \n")
                .append("upApprovalDepartment = ").append(upApprovalDepartment).append(", \n")
                .append("nextApprovalDepartment = ").append(nextApprovalDepartment).append(", \n")
                .append("upApprovalPeople = ").append(upApprovalPeople).append(", \n")
                .append("nextApprovalPeople = ").append(nextApprovalPeople).append(", \n")
                .append("regulatorYbj = ").append(regulatorYbj).append(", \n")
                .append("regulatorZjh = ").append(regulatorZjh).append(", \n")
                .append("regulatorCzb = ").append(regulatorCzb).append(", \n")
                .append("regulatorOth1 = ").append(regulatorOth1).append(", \n")
                .append("regulatorOth2 = ").append(regulatorOth2).append(", \n")
                .append("isSuspect = ").append(isSuspect).append(", \n")
                .append("isBlack = ").append(isBlack).append(", \n")
                .append("relSerial = ").append(relSerial).append(", \n")
                .append("blackReason = ").append(blackReason).append(", \n")
                .append("isShareholder = ").append(isShareholder).append(", \n")
                .append("shareholdersCode = ").append(shareholdersCode).append(", \n")
                .append("legalIdentifyType = ").append(legalIdentifyType).append(", \n")
                .append("legalIdentifyNumber = ").append(legalIdentifyNumber).append(", \n")
                .append("stateOwned = ").append(stateOwned).append(", \n")
                .append("openBank = ").append(openBank).append(", \n")
                .append("tradeAccount = ").append(tradeAccount).append(", \n")
                .append("holeShareAmount = ").append(holeShareAmount).append(", \n")
                .append("shareholdingProportion = ").append(shareholdingProportion).append(", \n")
                .append("frozenShare = ").append(frozenShare).append(", \n")
                .append("pledgedShare = ").append(pledgedShare).append(", \n")
                .append("relType = ").append(relType).append(", \n")
                .append("politicsStatus = ").append(politicsStatus).append(", \n")
                .append("educationBackground = ").append(educationBackground).append(", \n")
                .append("residentialAddress = ").append(residentialAddress).append(", \n")
                .append("workUnits = ").append(workUnits).append(", \n")
                .append("unitAddress = ").append(unitAddress).append(", \n")
                .append("inTime = ").append(inTime).append(", \n")
                .append("inPrice = ").append(inPrice).append(", \n")
                .append("dataSource = ").append(dataSource).append(", \n")
                .append("isMainGd = ").append(isMainGd).append(", \n")
                .append("capitalNumber = ").append(capitalNumber).append(", \n")
                .append("isNoShareVote = ").append(isNoShareVote).append(", \n")
                .append("voteProportion = ").append(voteProportion).append(", \n")
                .append("limitVoteSituation = ").append(limitVoteSituation).append(", \n")
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
        if (obj instanceof RelPsnSuspect == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelPsnSuspect other = (RelPsnSuspect) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

