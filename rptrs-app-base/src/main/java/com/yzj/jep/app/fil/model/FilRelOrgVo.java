package com.yzj.jep.app.fil.model;

public class FilRelOrgVo {
    private Integer pid;
    private Integer orgId;
    private Integer relId;
    private String orgName;
    private String orgType;
    private String domesticOrForeign;
    private String identifyType;
    private String uniscCode;
    private Float regCapital;
    private String regCapitalCurrency;
    private String businessScope;
    private String legalPerson;
    private String legalIdentifyType;
    private String legalIdentifyNumber;
    private String regAddress;
    private String mgerOrg;
    private Float shareholdingRatio;
    private String regulatorOth1;
    private String status;
    private String relCategory;
    private String relCategorySub;
    private String blackReason;
    private String isBlack;
    private String fromPid;
    private String regulatorYbj;
    private String regulatorZjh;
    private String regulatorCzb;
    private String opt;
    private String relSerial;
    private String isShareholder;
    private String isMainGd;
    private String memGroup;
    private String groupName;
    private String shareholdersCode;//股权证编号
    private String capitalNumber;//股东代码（账户号）
    private String openBank;//交易账户开户行
    private String tradeAccount;//交易账户账号
    private String inTime;//入股时间
    private String inPrice;//入股价格
    private String holeShareAmount;//持股数量（股）
    private String shareholdingProportion;//持股占比
    private String frozenShare;//总冻结股数（股）
    private String pledgedShare;//总质押股数（股）
    private String isNoShareVote;//是否具有非持股表决权
    private String limitVoteSituation;//限制表决权情况
    private String voteProportion;//表决权占比
    private String relStatus;
    private String regulator;

    private String isValid;
    private String relStatement;
    private String effectiveDate;
    private String ineffectiveDate;
    private String predicate;//关联关系判定

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

    public String getRelStatus() {
        return relStatus;
    }

    public void setRelStatus(String relStatus) {
        this.relStatus = relStatus;
    }

    public String getRegulator() {
        return regulator;
    }

    public void setRegulator(String regulator) {
        this.regulator = regulator;
    }

    public String getIsMainGd() {
        return isMainGd;
    }

    public void setIsMainGd(String isMainGd) {
        this.isMainGd = isMainGd;
    }

    public String getIsShareholder() {
        return isShareholder;
    }

    public void setIsShareholder(String isShareholder) {
        this.isShareholder = isShareholder;
    }

    public String getRelSerial() {
        return relSerial;
    }

    public void setRelSerial(String relSerial) {
        this.relSerial = relSerial;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getDomesticOrForeign() {
        return domesticOrForeign;
    }

    public void setDomesticOrForeign(String domesticOrForeign) {
        this.domesticOrForeign = domesticOrForeign;
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

    public Float getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(Float regCapital) {
        this.regCapital = regCapital;
    }

    public String getRegCapitalCurrency() {
        return regCapitalCurrency;
    }

    public void setRegCapitalCurrency(String regCapitalCurrency) {
        this.regCapitalCurrency = regCapitalCurrency;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalIdentifyType() {
        return legalIdentifyType;
    }

    public void setLegalIdentifyType(String legalIdentifyType) {
        this.legalIdentifyType = legalIdentifyType;
    }

    public String getLegalIdentifyNumber() {
        return legalIdentifyNumber;
    }

    public void setLegalIdentifyNumber(String legalIdentifyNumber) {
        this.legalIdentifyNumber = legalIdentifyNumber;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getMgerOrg() {
        return mgerOrg;
    }

    public void setMgerOrg(String mgerOrg) {
        this.mgerOrg = mgerOrg;
    }

    public Float getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(Float shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    public String getRegulatorOth1() {
        return regulatorOth1;
    }

    public void setRegulatorOth1(String regulatorOth1) {
        this.regulatorOth1 = regulatorOth1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getRelId() {
        return relId;
    }

    public void setRelId(Integer relId) {
        this.relId = relId;
    }

    public String getRelCategory() {
        return relCategory;
    }

    public void setRelCategory(String relCategory) {
        this.relCategory = relCategory;
    }

    public String getRelCategorySub() {
        return relCategorySub;
    }

    public void setRelCategorySub(String relCategorySub) {
        this.relCategorySub = relCategorySub;
    }

    public String getBlackReason() {
        return blackReason;
    }

    public void setBlackReason(String blackReason) {
        this.blackReason = blackReason;
    }

    public String getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(String isBlack) {
        this.isBlack = isBlack;
    }

    public String getFromPid() {
        return fromPid;
    }

    public void setFromPid(String fromPid) {
        this.fromPid = fromPid;
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

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getShareholdersCode() {
        return shareholdersCode;
    }

    public void setShareholdersCode(String shareholdersCode) {
        this.shareholdersCode = shareholdersCode;
    }

    public String getCapitalNumber() {
        return capitalNumber;
    }

    public void setCapitalNumber(String capitalNumber) {
        this.capitalNumber = capitalNumber;
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

    public String getFrozenShare() {
        return frozenShare;
    }

    public void setFrozenShare(String frozenShare) {
        this.frozenShare = frozenShare;
    }

    public String getPledgedShare() {
        return pledgedShare;
    }

    public void setPledgedShare(String pledgedShare) {
        this.pledgedShare = pledgedShare;
    }

    public String getIsNoShareVote() {
        return isNoShareVote;
    }

    public void setIsNoShareVote(String isNoShareVote) {
        this.isNoShareVote = isNoShareVote;
    }

    public String getLimitVoteSituation() {
        return limitVoteSituation;
    }

    public void setLimitVoteSituation(String limitVoteSituation) {
        this.limitVoteSituation = limitVoteSituation;
    }

    public String getVoteProportion() {
        return voteProportion;
    }

    public void setVoteProportion(String voteProportion) {
        this.voteProportion = voteProportion;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getRelStatement() {
        return relStatement;
    }

    public void setRelStatement(String relStatement) {
        this.relStatement = relStatement;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getIneffectiveDate() {
        return ineffectiveDate;
    }

    public void setIneffectiveDate(String ineffectiveDate) {
        this.ineffectiveDate = ineffectiveDate;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }
}
