package com.yzj.jep.app.bus.vo;

public class OrgInfoVo {
    private String no;
    private String orgName;
    private String identifyType;
    private String uniscCode;
    private String domesticOrForeign;
    private String mgerOrg;
    private String regulatorYbj;
    private String dataSource;
    private String status;
    private String statusComment;
    private String isBlack;
    private String regAddress;
    private String legalPerson;
    private String legalIdentifytype;
    private String legalIdentifyNumber;
    private String approvalStatus;
    private String orgType;
    private String businessScope;
    private String regCapital;
    private String regCapitalCurrency;
    private String groupName;
    private String memGroup;
    private String isShareholder;
    private String isMainGd;
    private String capitalNumber;
    private String shareholdersCode;
    private String openBank;
    private String tradeAccount;
    private String inTime;
    private String inPrice;
    private String holeShareAmount;
    private String shareholdingProportion;
    private String frozenShareAmount;
    private String pledgedShareAmount;
    private String isNoShareVote;
    private String voteProportion;
    private String limitVoteSituation;
    private String pledgeRatio;
    //关系自然人
    private String psnName;
    private String psnIdentifyType;
    private String psnIdentifyNumber;
    private String psnDomesticOrForeign;
    private String psnResidentialAddress;
    private String psnRelCategory;
    private String psnRelCategorySub;
    private String psnValidStatus;
    private String psnActiveTime;
    private String psnInvalidTime;
    private String psnEquityShare;
    private String psnShareholdingRatio;
    private String psnPledgeRatio;
    private String psnRelStatement;
    //关系机构
    private String orgOrgName;
    private String orgIdentifyType;
    private String orgUniscCode;
    private String orgDomesticOrForeign;
    private String orgRegAddress;
    private String orgStatus;
    private String orgLegalPerson;
    private String orgLegalIdentifytype;
    private String orgLegalIdentifyNumber;
    private String orgRelCategory;
    private String orgRelCategorySub;
    private String orgShareholdingRatio;
    private String orgvalidStatus;
    private String orgActiveTime;
    private String orgInvalidTime;
    private String orgEquityShare;
    private String orgPledgeRatio;
    private String orgRelStatement;
    private String orgNature;//机构性质
    //关系产品
    private String proName;
    private String proProvider;
    private String proType;
    private String proRelCategory;
    private String proRelCategorySub;
    private String provalidStatus;
    private String proActiveTime;
    private String proInvalidTime;
    private String proEquityShare;
    private String proShareholdingRatio;
    private String proRelStatement;

    private String operator;
    private String transactionDep;
    private String recordType;
    private String modifiedTime;
    private String isSubsidiary;

    //2021.11.18 关联方编号
    private  String relateNumber;

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

    public String getIsSubsidiary() {
        return isSubsidiary;
    }

    public void setIsSubsidiary(String isSubsidiary) {
        this.isSubsidiary = isSubsidiary;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getOrgNature() {
        return orgNature;
    }

    public void setOrgNature(String orgNature) {
        this.orgNature = orgNature;
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

    public String getPledgeRatio() {
        return pledgeRatio;
    }

    public void setPledgeRatio(String pledgeRatio) {
        this.pledgeRatio = pledgeRatio;
    }

    public String getPsnValidStatus() {
        return psnValidStatus;
    }

    public void setPsnValidStatus(String psnValidStatus) {
        this.psnValidStatus = psnValidStatus;
    }

    public String getPsnActiveTime() {
        return psnActiveTime;
    }

    public void setPsnActiveTime(String psnActiveTime) {
        this.psnActiveTime = psnActiveTime;
    }

    public String getPsnInvalidTime() {
        return psnInvalidTime;
    }

    public void setPsnInvalidTime(String psnInvalidTime) {
        this.psnInvalidTime = psnInvalidTime;
    }

    public String getPsnEquityShare() {
        return psnEquityShare;
    }

    public void setPsnEquityShare(String psnEquityShare) {
        this.psnEquityShare = psnEquityShare;
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

    public String getPsnRelStatement() {
        return psnRelStatement;
    }

    public void setPsnRelStatement(String psnRelStatement) {
        this.psnRelStatement = psnRelStatement;
    }

    public String getOrgvalidStatus() {
        return orgvalidStatus;
    }

    public void setOrgvalidStatus(String orgvalidStatus) {
        this.orgvalidStatus = orgvalidStatus;
    }

    public String getOrgActiveTime() {
        return orgActiveTime;
    }

    public void setOrgActiveTime(String orgActiveTime) {
        this.orgActiveTime = orgActiveTime;
    }

    public String getOrgInvalidTime() {
        return orgInvalidTime;
    }

    public void setOrgInvalidTime(String orgInvalidTime) {
        this.orgInvalidTime = orgInvalidTime;
    }

    public String getOrgEquityShare() {
        return orgEquityShare;
    }

    public void setOrgEquityShare(String orgEquityShare) {
        this.orgEquityShare = orgEquityShare;
    }

    public String getOrgPledgeRatio() {
        return orgPledgeRatio;
    }

    public void setOrgPledgeRatio(String orgPledgeRatio) {
        this.orgPledgeRatio = orgPledgeRatio;
    }

    public String getOrgRelStatement() {
        return orgRelStatement;
    }

    public void setOrgRelStatement(String orgRelStatement) {
        this.orgRelStatement = orgRelStatement;
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

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
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

    public String getProvalidStatus() {
        return provalidStatus;
    }

    public void setProvalidStatus(String provalidStatus) {
        this.provalidStatus = provalidStatus;
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

    public String getProEquityShare() {
        return proEquityShare;
    }

    public void setProEquityShare(String proEquityShare) {
        this.proEquityShare = proEquityShare;
    }

    public String getProShareholdingRatio() {
        return proShareholdingRatio;
    }

    public void setProShareholdingRatio(String proShareholdingRatio) {
        this.proShareholdingRatio = proShareholdingRatio;
    }

    public String getProRelStatement() {
        return proRelStatement;
    }

    public void setProRelStatement(String proRelStatement) {
        this.proRelStatement = proRelStatement;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    public String getUniscCode() {
        return uniscCode;
    }

    public void setUniscCode(String uniscCode) {
        this.uniscCode = uniscCode;
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

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalIdentifytype() {
        return legalIdentifytype;
    }

    public void setLegalIdentifytype(String legalIdentifytype) {
        this.legalIdentifytype = legalIdentifytype;
    }

    public String getLegalIdentifyNumber() {
        return legalIdentifyNumber;
    }

    public void setLegalIdentifyNumber(String legalIdentifyNumber) {
        this.legalIdentifyNumber = legalIdentifyNumber;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital;
    }

    public String getRegCapitalCurrency() {
        return regCapitalCurrency;
    }

    public void setRegCapitalCurrency(String regCapitalCurrency) {
        this.regCapitalCurrency = regCapitalCurrency;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMemGroup() {
        return memGroup;
    }

    public void setMemGroup(String memGroup) {
        this.memGroup = memGroup;
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

    public String getOrgOrgName() {
        return orgOrgName;
    }

    public void setOrgOrgName(String orgOrgName) {
        this.orgOrgName = orgOrgName;
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

    public String getRelateNumber() {
        return relateNumber;
    }

    public void setRelateNumber(String relateNumber) {
        this.relateNumber = relateNumber;
    }
}
