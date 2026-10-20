package com.yzj.jep.app.bus.vo;

import javax.persistence.Column;
import java.util.Date;

public class PsnInfoVo {
    private String no;//自然人序号
    private String name;//自然人姓名
    private String identifyType;//证件类型
    private String identifyNumber;//证件号码
    private String domesticOrForeign;//境内/境外
    private String mgerOrg;//管户机构
    private String regulatorYbj;//银保监口径（0|否1|是）
    private String dataSource;//数据来源
    private String status;//审批状态
    private String statusComment;//审批状态说明
    private String isBlack;//是否黑名单（0|否1|是）
    private String sex;//自然人性别
    private String race;//民族编号
    private String politicsStatus;//政治面貌
    private String maritalStatus;//婚姻状况
    private String educationBackground;//学历编码
    private String mobile;//移动电话
    private String email;//邮箱
    private String residentialAddress;//居住地址
    private String birthdate;//出生日期
    private String isShareholder;//是否为本行股东
    private String isMainGd;//是否为本行主要股东
    private String capitalNumber;//股权证编号
    private String shareholdersCode;//股东账号
    private String openBank;//交易账号开户行
    private String tradeAccount;//交易账户号
    private String inTime;//入股时间
    private String inPrice;//入股价格
    private String holeShareAmount;//持股数量
    private String shareholdingProportion;//是否具有非持股表决权
    private String frozenShareAmount;//表决权占比
    private String pledgedShareAmount;//限制表决情况
    private String unitAddress;

    private String isNoShareVote;////是否具有非持股表决权
    private String voteProportion;//表决权占比
    private String limitVoteSituation;//限制表决情况
    //2020-12-10新增
    private String shareholdingRatio;//持股比例
    private String pledgeRatio;//质押股权比例
    private String workUnits;//工作单位
    private String unitsAddress;//单位地址


    private String psnName;//关系自然人姓名
    private String psnIdentifyType;//关系自然人证件类型
    private String psnIdentifyNumber;//关系自然人证件号码
    private String psnDomesticOrForeign;//境内外标识
    private String psnResidentialAddress;//居住地址
    private String psnRelCategory;//自然人关联关系类别
    private String psnRelCategorySub;//自然人关联-关系
    private String psnEffectiveDate;//生效日期
    private String psnIneffectiveDate;//失效日期
    private String psnRelComment; //关系说明
    private String psnPredicate; //关系自然人关系判定
    private String isValid;//是否有效

    private String modifiedTime;//更新日期
    private Date createTime;//创建日期
    private String effectRelation;//是否存在有效关系

    private Date orgModifiedTime;//更新日期-机构
    private Date orgCreateTime;//创建日期-机构
    private String orgEffectRelation;//是否存在有效关系-机构

    //2020-12-10新增
    private String psnShareholdingRatio;//持股比例
    private String psnPledgeRatio;//质押股权比例
    private String psnIsEquityShare;//与前一层是否有权益份额

    private String orgName;//关系机构名称
    private String orgIdentifyType;//关系机构证件类型
    private String orgUniscCode;//关系机构证件编号
    private String orgDomesticOrForeign;//境内外标识
    private String orgRegAddress;//注册地
    private String orgStatus;//?机构状态（1-正常，2-停业）
    private String orgLegalPerson;//法定代表人
    private String orgLegalIdentifytype;//法定人证件类型
    private String orgLegalIdentifyNumber;//法定人证件号码
    private String orgRelCategory;//关联关系类别
    private String orgRelCategorySub;//关系
    private String orgShareholdingRatio;//?持股比例
    private String orgEffectiveDate;//生效时间
    private String orgIneffectiveDate;//失效日期
    private String orgRelComment; //关系说明
    private String orgPredicate; //关系机构关系判定
    private String orgisValid;//关系是否有效
    //2020-12-10新增
    private String orgPledgeRatio;//质押股权比例
    private String orgIsEquityShare;//与前一层是否有权益份额

    private String proName;//产品名称
    private String proProvider;//产品批准/注册备案号
    private String proIsValid;//关联关系是否有效
    private String proRelCategory;//关联关系类别
    private String proRelCategorySub;//关系
    //2020-12-10新增
    private String proPledgeRatio;//质押股权比例
    //7.15新增
    private Float proShareholdingRatio;     //产品持股比例或表决权
    private String proActiveTime;           //产品关系生效日期
    private String proInvalidTime;          //产品关系失效日期
    private String proRelStatement;         //产品关联关系说明
    //7.15新增
    private String operator;
    private String transactionDep;
    private String recordType;

    //2021.11.18新增
    private String relateNumber; //关联方编号

    private String recordNumber;

    private String createTimeString;
    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public Date getOrgModifiedTime() {
        return orgModifiedTime;
    }

    public void setOrgModifiedTime(Date orgModifiedTime) {
        this.orgModifiedTime = orgModifiedTime;
    }

    public Date getOrgCreateTime() {
        return orgCreateTime;
    }

    public void setOrgCreateTime(Date orgCreateTime) {
        this.orgCreateTime = orgCreateTime;
    }

    public String getOrgEffectRelation() {
        return orgEffectRelation;
    }

    public void setOrgEffectRelation(String orgEffectRelation) {
        this.orgEffectRelation = orgEffectRelation;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEffectRelation() {
        return effectRelation;
    }

    public void setEffectRelation(String effectRelation) {
        this.effectRelation = effectRelation;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTransactionDep() {
        return transactionDep;
    }

    public void setTransactionDep(String transactionDep) {
        this.transactionDep = transactionDep;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public Float getProShareholdingRatio() {
        return proShareholdingRatio;
    }

    public void setProShareholdingRatio(Float proShareholdingRatio) {
        this.proShareholdingRatio = proShareholdingRatio;
    }

    public String getProActiveTime() {
        return proActiveTime;
    }

    public void setProActiveTime(String proActiveTime) {
        this.proActiveTime = proActiveTime;
    }

    public String getProInvalidTime() {
        return proInvalidTime;
    }

    public void setProInvalidTime(String proInvalidTime) {
        this.proInvalidTime = proInvalidTime;
    }

    public String getProRelStatement() {
        return proRelStatement;
    }

    public void setProRelStatement(String proRelStatement) {
        this.proRelStatement = proRelStatement;
    }

    public String getPsnIneffectiveDate() {
        return psnIneffectiveDate;
    }

    public void setPsnIneffectiveDate(String psnIneffectiveDate) {
        this.psnIneffectiveDate = psnIneffectiveDate;
    }

    public String getPsnEffectiveDate() {
        return psnEffectiveDate;
    }

    public void setPsnEffectiveDate(String psnEffectiveDate) {
        this.psnEffectiveDate = psnEffectiveDate;
    }

    public String getPsnRelComment() {
        return psnRelComment;
    }

    public void setPsnRelComment(String psnRelComment) {
        this.psnRelComment = psnRelComment;
    }

    public String getOrgRelComment() {
        return orgRelComment;
    }

    public void setOrgRelComment(String orgRelComment) {
        this.orgRelComment = orgRelComment;
    }

    public String getPsnPredicate() {
        return psnPredicate;
    }

    public void setPsnPredicate(String psnPredicate) {
        this.psnPredicate = psnPredicate;
    }

    public String getOrgPredicate() {
        return orgPredicate;
    }

    public void setOrgPredicate(String orgPredicate) {
        this.orgPredicate = orgPredicate;
    }

    public String getOrgisValid() {
        return orgisValid;
    }

    public void setOrgisValid(String orgisValid) {
        this.orgisValid = orgisValid;
    }


    public String getOrgEffectiveDate() {
        return orgEffectiveDate;
    }

    public void setOrgEffectiveDate(String orgEffectiveDate) {
        this.orgEffectiveDate = orgEffectiveDate;
    }

    public String getOrgIneffectiveDate() {
        return orgIneffectiveDate;
    }

    public void setOrgIneffectiveDate(String orgIneffectiveDate) {
        this.orgIneffectiveDate = orgIneffectiveDate;
    }

    public String getProIsValid() {
        return proIsValid;
    }

    public void setProIsValid(String proIsValid) {
        this.proIsValid = proIsValid;
    }

    public String getProRelCategory() {
        return proRelCategory;
    }

    public void setProRelCategory(String proRelCategory) {
        this.proRelCategory = proRelCategory;
    }

    public String getProRelCategorySub() {
        return proRelCategorySub;
    }

    public void setProRelCategorySub(String proRelCategorySub) {
        this.proRelCategorySub = proRelCategorySub;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProProvider() {
        return proProvider;
    }

    public void setProProvider(String proProvider) {
        this.proProvider = proProvider;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDomesticOrForeign() {
        return domesticOrForeign;
    }

    public void setDomesticOrForeign(String domesticOrForeign) {
        this.domesticOrForeign = domesticOrForeign;
    }

    public String getMgerOrg() {
        return mgerOrg;
    }

    public void setMgerOrg(String mgerOrg) {
        this.mgerOrg = mgerOrg;
    }

    public String getRegulatorYbj() {
        return regulatorYbj;
    }

    public void setRegulatorYbj(String regulatorYbj) {
        this.regulatorYbj = regulatorYbj;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
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

    public String getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(String isBlack) {
        this.isBlack = isBlack;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
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

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getIsShareholder() {
        return isShareholder;
    }

    public void setIsShareholder(String isShareholder) {
        this.isShareholder = isShareholder;
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

    public String getPsnName() {
        return psnName;
    }

    public void setPsnName(String psnName) {
        this.psnName = psnName;
    }

    public String getPsnIdentifyType() {
        return psnIdentifyType;
    }

    public void setPsnIdentifyType(String psnIdentifyType) {
        this.psnIdentifyType = psnIdentifyType;
    }

    public String getPsnIdentifyNumber() {
        return psnIdentifyNumber;
    }

    public void setPsnIdentifyNumber(String psnIdentifyNumber) {
        this.psnIdentifyNumber = psnIdentifyNumber;
    }

    public String getPsnDomesticOrForeign() {
        return psnDomesticOrForeign;
    }

    public void setPsnDomesticOrForeign(String psnDomesticOrForeign) {
        this.psnDomesticOrForeign = psnDomesticOrForeign;
    }

    public String getPsnResidentialAddress() {
        return psnResidentialAddress;
    }

    public void setPsnResidentialAddress(String psnResidentialAddress) {
        this.psnResidentialAddress = psnResidentialAddress;
    }

    public String getPsnRelCategory() {
        return psnRelCategory;
    }

    public void setPsnRelCategory(String psnRelCategory) {
        this.psnRelCategory = psnRelCategory;
    }

    public String getPsnRelCategorySub() {
        return psnRelCategorySub;
    }

    public void setPsnRelCategorySub(String psnRelCategorySub) {
        this.psnRelCategorySub = psnRelCategorySub;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgIdentifyType() {
        return orgIdentifyType;
    }

    public void setOrgIdentifyType(String orgIdentifyType) {
        this.orgIdentifyType = orgIdentifyType;
    }

    public String getOrgUniscCode() {
        return orgUniscCode;
    }

    public void setOrgUniscCode(String orgUniscCode) {
        this.orgUniscCode = orgUniscCode;
    }

    public String getOrgDomesticOrForeign() {
        return orgDomesticOrForeign;
    }

    public void setOrgDomesticOrForeign(String orgDomesticOrForeign) {
        this.orgDomesticOrForeign = orgDomesticOrForeign;
    }

    public String getOrgRegAddress() {
        return orgRegAddress;
    }

    public void setOrgRegAddress(String orgRegAddress) {
        this.orgRegAddress = orgRegAddress;
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus;
    }

    public String getOrgLegalPerson() {
        return orgLegalPerson;
    }

    public void setOrgLegalPerson(String orgLegalPerson) {
        this.orgLegalPerson = orgLegalPerson;
    }

    public String getOrgLegalIdentifytype() {
        return orgLegalIdentifytype;
    }

    public void setOrgLegalIdentifytype(String orgLegalIdentifytype) {
        this.orgLegalIdentifytype = orgLegalIdentifytype;
    }

    public String getOrgLegalIdentifyNumber() {
        return orgLegalIdentifyNumber;
    }

    public void setOrgLegalIdentifyNumber(String orgLegalIdentifyNumber) {
        this.orgLegalIdentifyNumber = orgLegalIdentifyNumber;
    }

    public String getOrgRelCategory() {
        return orgRelCategory;
    }

    public void setOrgRelCategory(String orgRelCategory) {
        this.orgRelCategory = orgRelCategory;
    }

    public String getOrgRelCategorySub() {
        return orgRelCategorySub;
    }

    public void setOrgRelCategorySub(String orgRelCategorySub) {
        this.orgRelCategorySub = orgRelCategorySub;
    }

    public String getOrgShareholdingRatio() {
        return orgShareholdingRatio;
    }

    public void setOrgShareholdingRatio(String orgShareholdingRatio) {
        this.orgShareholdingRatio = orgShareholdingRatio;
    }

    public String getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(String shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    public String getPledgeRatio() {
        return pledgeRatio;
    }

    public void setPledgeRatio(String pledgeRatio) {
        this.pledgeRatio = pledgeRatio;
    }

    public String getPsnShareholdingRatio() {
        return psnShareholdingRatio;
    }

    public void setPsnShareholdingRatio(String psnShareholdingRatio) {
        this.psnShareholdingRatio = psnShareholdingRatio;
    }

    public String getPsnPledgeRatio() {
        return psnPledgeRatio;
    }

    public void setPsnPledgeRatio(String psnPledgeRatio) {
        this.psnPledgeRatio = psnPledgeRatio;
    }

    public String getOrgPledgeRatio() {
        return orgPledgeRatio;
    }

    public void setOrgPledgeRatio(String orgPledgeRatio) {
        this.orgPledgeRatio = orgPledgeRatio;
    }

    public String getProPledgeRatio() {
        return proPledgeRatio;
    }

    public void setProPledgeRatio(String proPledgeRatio) {
        this.proPledgeRatio = proPledgeRatio;
    }

    public String getPsnIsEquityShare() {
        return psnIsEquityShare;
    }

    public void setPsnIsEquityShare(String psnIsEquityShare) {
        this.psnIsEquityShare = psnIsEquityShare;
    }

    public String getOrgIsEquityShare() {
        return orgIsEquityShare;
    }

    public void setOrgIsEquityShare(String orgIsEquityShare) {
        this.orgIsEquityShare = orgIsEquityShare;
    }

    public String getWorkUnits() {
        return workUnits;
    }

    public void setWorkUnits(String workUnits) {
        this.workUnits = workUnits;
    }

    public String getUnitsAddress() {
        return unitsAddress;
    }

    public void setUnitsAddress(String unitsAddress) {
        this.unitsAddress = unitsAddress;
    }

    public String getRelateNumber() {
        return relateNumber;
    }

    public void setRelateNumber(String relateNumber) {
        this.relateNumber = relateNumber;
    }
}
