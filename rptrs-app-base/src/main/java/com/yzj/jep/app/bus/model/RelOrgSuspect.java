/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月22日 10:15:03
 *
 */

package com.yzj.jep.app.bus.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "bus_rel_org_suspect")
public class RelOrgSuspect extends BasePO {

    public static final String TABLE_ALIAS = "RelOrgSuspect";
    public static final String ALIAS_PID = "自增主键";
    public static final String ALIAS_REG_CODE = "登记编码";
    public static final String ALIAS_ORIGNAL_ORG = "源主体银行机构";
    public static final String ALIAS_ORIGNAL_ORG_NAME = "源主体银行机构名称";
    public static final String ALIAS_ORG_NAME = "企业名称";
    public static final String ALIAS_ORG_TYPE = "企业类型";
    public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内外标识(1-境内，2-境外)";
    public static final String ALIAS_IDENTIFY_TYPE = "证件类型";
    public static final String ALIAS_UNISC_CODE = "社会统一信用代码";
    public static final String ALIAS_REG_CAPITAL = "注册资本(万元)";
    public static final String ALIAS_REG_CAPITAL_CURRENCY = "注册资本币种代码";
    public static final String ALIAS_BUSINESS_SCOPE = "营业范围";
    public static final String ALIAS_STATUS = "机构状态（1-正常，2-停业）";
    public static final String ALIAS_LEGAL_PERSON = "法定代表人";
    public static final String ALIAS_REG_ADDRESS = "注册地";
    public static final String ALIAS_VALID_STATUS = "是否有效 0-无效，1-有效";
    public static final String ALIAS_ACTIVE_TIME = "生效时间";
    public static final String ALIAS_INVALID_TIME = "失效时间";
    public static final String ALIAS_MGER_ORG = "管户机构(默认是总行)";
    public static final String ALIAS_APPROVAL_STATUS = "审批状态    未提交/已提交/未审批";
    public static final String ALIAS_COGNIZA_REASON = "cognizaReason";
    public static final String ALIAS_STATUS_COMMENT = "审批状态说明";
    public static final String ALIAS_UP_APPROVAL_DEPARTMENT = "上一级审批部门";
    public static final String ALIAS_NEXT_APPROVAL_DEPARTMENT = "下一级审批部门";
    public static final String ALIAS_UP_APPROVAL_PEOPLE = "上一级审批人";
    public static final String ALIAS_NEXT_APPROVAL_PEOPLE = "下一级审批人";
    public static final String ALIAS_REGULATOR_ZJH = "证监会口径（0|否1|是）";
    public static final String ALIAS_REGULATOR_CZB = "财政部口径（0|否1|是）";
    public static final String ALIAS_REGULATOR_OTH1 = "其他口径1（0|否1|是）";
    public static final String ALIAS_REGULATOR_OTH2 = "其他口径2（0|否1|是）";
    public static final String ALIAS_IS_SUSPECT = "是否是关联方";
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

    @Column(name = "org_name", nullable = true, length = 500)
    private String orgName;

    @Column(name = "org_type", nullable = true, length = 100)
    private String orgType;

    @Column(name = "domestic_or_foreign", nullable = true, length = 10)
    private String domesticOrForeign;

    @Column(name = "identify_type", nullable = true, length = 30)
    private String identifyType;

    @Column(name = "unisc_code", nullable = true, length = 30)
    private String uniscCode;

    @Column(name = "reg_capital", nullable = true, length = 10)
    private Float regCapital;

    @Column(name = "reg_capital_currency", nullable = true, length = 10)
    private String regCapitalCurrency;

    @Column(name = "business_scope", nullable = true, length = 2000)
    private String businessScope;

    @Column(name = "statu", nullable = true, length = 10)
    private String status;

    @Column(name = "legal_person", nullable = true, length = 100)
    private String legalPerson;

    @Column(name = "reg_address", nullable = true, length = 100)
    private String regAddress;

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

    @Column(name = "approval_status", nullable = true, length = 255)
    private String approvalStatus;

    @Column(name = "cogniza_reason", nullable = true, length = 255)
    private String cognizaReason;

    @Column(name = "status_comment", nullable = true, length = 255)
    private String statusComment;

    @Column(name = "up_approval_department", nullable = true, length = 50)
    private String upApprovalDepartment;

    @Column(name = "next_approval_department", nullable = true, length = 50)
    private String nextApprovalDepartment;

    @Column(name = "up_approval_people", nullable = true, length = 50)
    private String upApprovalPeople;

    @Column(name = "next_approval_people", nullable = true, length = 50)
    private String nextApprovalPeople;

    @Column(name = "regulator_zjh", nullable = true, length = 50)
    private String regulatorZjh;

    @Column(name = "regulator_czb", nullable = true, length = 50)
    private String regulatorCzb;

    @Column(name = "regulator_ybj", nullable = true, length = 50)
    private String regulatorYbj;

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

    @Column(name = "mem_group", nullable = true, length = 20)
    private String memGroup;

    @Column(name = "group_name", nullable = true, length = 10)
    private String groupName;
    @Column(name = "in_time", nullable = true, length = 11)
    private String inTime;

    @Column(name = "in_price", nullable = true, length = 20)
    private String inPrice;

    @Column(name = "is_assoParty", nullable = true, length = 10)
    private String isAssoParty;

    //是否子公司
    @Column(name = "is_subsidiary",nullable = true,length = 50)
    private String isSubsidiary;

    //机构性质
    @Column(name = "ORG_NATURE", nullable = true, length = 50)
    private String orgNature;
    @Column(name = "proc_inst_id", nullable = true, length = 10)
    private String procInstId;
    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getIsSubsidiary() {
        return isSubsidiary;
    }

    public void setIsSubsidiary(String isSubsidiary) {
        this.isSubsidiary = isSubsidiary;
    }

    public String getRegulatorYbj() {
        return regulatorYbj;
    }

    public void setRegulatorYbj(String regulatorYbj) {
        this.regulatorYbj = regulatorYbj;
    }

    public String getIsAssoParty() {
        return isAssoParty;
    }

    public void setIsAssoParty(String isAssoParty) {
        this.isAssoParty = isAssoParty;
    }

    public String getOrgNature() {
        return orgNature;
    }

    public void setOrgNature(String orgNature) {
        this.orgNature = orgNature;
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

    public String getMemGroup() {
        return memGroup;
    }

    public void setMemGroup(String memGroup) {
        this.memGroup = memGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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


    public String getIdentifyType() {
        return this.identifyType;
    }

    public void setIdentifyType(String value) {
        this.identifyType = value;
    }


    public String getUniscCode() {
        return this.uniscCode;
    }

    public void setUniscCode(String value) {
        this.uniscCode = value;
    }


    public Float getRegCapital() {
        return this.regCapital;
    }

    public void setRegCapital(Float value) {
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


    public String getApprovalStatus() {
        return this.approvalStatus;
    }

    public void setApprovalStatus(String value) {
        this.approvalStatus = value;
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
        return new StringBuilder("relOrgSuspect[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("regCode = ").append(regCode).append(", \n")
                .append("orignalOrg = ").append(orignalOrg).append(", \n")
                .append("orignalOrgName = ").append(orignalOrgName).append(", \n")
                .append("orgName = ").append(orgName).append(", \n")
                .append("orgType = ").append(orgType).append(", \n")
                .append("domesticOrForeign = ").append(domesticOrForeign).append(", \n")
                .append("identifyType = ").append(identifyType).append(", \n")
                .append("uniscCode = ").append(uniscCode).append(", \n")
                .append("regCapital = ").append(regCapital).append(", \n")
                .append("regCapitalCurrency = ").append(regCapitalCurrency).append(", \n")
                .append("businessScope = ").append(businessScope).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("legalPerson = ").append(legalPerson).append(", \n")
                .append("regAddress = ").append(regAddress).append(", \n")
                .append("validStatus = ").append(validStatus).append(", \n")
                .append("activeTime = ").append(activeTime).append(", \n")
                .append("invalidTime = ").append(invalidTime).append(", \n")
                .append("mgerOrg = ").append(mgerOrg).append(", \n")
                .append("approvalStatus = ").append(approvalStatus).append(", \n")
                .append("cognizaReason = ").append(cognizaReason).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("upApprovalDepartment = ").append(upApprovalDepartment).append(", \n")
                .append("nextApprovalDepartment = ").append(nextApprovalDepartment).append(", \n")
                .append("upApprovalPeople = ").append(upApprovalPeople).append(", \n")
                .append("nextApprovalPeople = ").append(nextApprovalPeople).append(", \n")
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
        if (obj instanceof RelOrgSuspect == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RelOrgSuspect other = (RelOrgSuspect) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }

}

