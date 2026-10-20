package com.yzj.jep.app.fil.model;

public class FilRelPsnVo {
    private Integer pid;
    private String filNum;//备案编号
    private Integer relId;
    private Integer orgId;
    private String name;//姓名
    private String sex;//性别
    private String domesticOrForeign;//境内外标识
    private String race;//民族
    private String identifyType;//证件类型
    private String identifyNumber;//证件号码
    private String birthdate;//出生日期
    private String mobile;//移动电话
    private String email;//邮箱
    private String mgerOrg;//管户机构
    private Float shareholdingRatio;
    private String regulatorOth1;
    private String maritalStatus;//婚姻状况
    private String dutyCode;
    private String predicate;//关联关系判定
    private String relCategory;
    private String relCategorySub;//关系
    private String isValid;//是否有效
    private String relStatement;//关联关系说明
    private String effectiveDate;//生效日期

    private String ineffectiveDate;//失效日期

    private String relation;
    private Integer fromPid;
    private Integer toPid;
    private String regulatorYbj;
    private String regulatorZjh;
    private String regulatorCzb;
    private String fromName;
    private String toName;
    private String liftReason;

    private String isShareholder;//是否为股东
    private String isMainGd;     //是否为主要股东
    private String shareholdersCode;//股权证编号
    private String capitalNumber;//股东账号
    private String openBank;     //交易账户开户行
    private String tradeAccount;//交易账户账号
    private String inTime;      //入股时间
    private String inPrice;     //入股价格
    private String holeShareAmount;//持股数量
    private String shareholdingProportion;//持股占比
    private String frozenShareAmount;//总冻结股数（股）
    private String pledgedShareAmount;//总质押股数（股）
    private String relSerial;   //流水号
    private String isNoShareVote;//是否具有非持股表决权
    private String voteProportion;//表决权占比
    private String limitVoteSituation;//限制表决权情况
    private String politicsStatus;//政治面貌
    private String educationBackground;//学历
    private String residentialAddress;//居住地址
    private String workUnits;//工作单位
    private String unitAddress;//单位地址
    private String relStatus;
    private String regulator;

    public String getFilNum() {
        return filNum;
    }

    public void setFilNum(String filNum) {
        this.filNum = filNum;
    }

    public String getRegulator() {
        return regulator;
    }

    public void setRegulator(String regulator) {
        this.regulator = regulator;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getRelStatus() {
        return relStatus;
    }

    public void setRelStatus(String relStatus) {
        this.relStatus = relStatus;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getRelId() {
        return relId;
    }

    public void setRelId(Integer relId) {
        this.relId = relId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
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

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Integer getFromPid() {
        return fromPid;
    }

    public void setFromPid(Integer fromPid) {
        this.fromPid = fromPid;
    }

    public Integer getToPid() {
        return toPid;
    }

    public void setToPid(Integer toPid) {
        this.toPid = toPid;
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

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getLiftReason() {
        return liftReason;
    }

    public void setLiftReason(String liftReason) {
        this.liftReason = liftReason;
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

    public String getRelSerial() {
        return relSerial;
    }

    public void setRelSerial(String relSerial) {
        this.relSerial = relSerial;
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
