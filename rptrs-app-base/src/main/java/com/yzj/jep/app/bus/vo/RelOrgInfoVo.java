package com.yzj.jep.app.bus.vo;

public class RelOrgInfoVo {
    private String no;                      //序号
    private String orgName;                 //机构名称
    private String identifyType;            //证件类型
    private String uniscCode;               //证件号码
    private String domesticOrForeign;       //机构境外
    private String regAddress;              //注册地
    private String legalPerson;             //法定代表人
    private String legalIdentifytype;       //待定义字段/法定代表人证件类型
    private String legalIdentifyNumber;     //法定代表人身份证号
    private String status;                  //机构状态
    private String orgType;                 //企业类型
    private String businessScope;           //营业范围
    private String regCapital;              //注册资本
    private String regCapitalCurrency;      //注册资本币种代码
    private String groupName;               //所属集团
    private String memGroup;                //所属集团证件号码
    private String isShareholder;           //是否为本行股东
    private String isMainGd;                //是否为本行重要股东
    private String capitalNumber;           //股权证编号
    private String shareholdersCode;        //股东账号
    private String openBank;                //交易账户开户行
    private String tradeAccount;            //交易账户账号
    private String inTime;                  //入股时间
    private String inPrice;                 //入股价格
    private String holeShareAmount;         //持股数量
    private String shareholdingProportion;  //持股占比
    //2020-12-13新增
    private String pledgeRatio;//质押股权比例
    private String frozenShare;             //总冻结股数
    private String pledgedShare;            //总质押股数
    private String isNoShareVote;           //是否具有非持股表决权
    private String voteProportion;
    private String limitVoteSituation;      //限制表决权情况
    private String psnName;                 //自然人姓名
    private String psnIdentifyType;         //自然人证件类型
    private String psnIdentifyNumber;       //自然人证件号码
    private String psnPredicate;            //自然人关联关系判定
    private String psnDomesticOrForeign;    //自然人境内境外
    private String psnValidStatus;          //自然人关系是否生效
    private String psnActiveTime;           //自然人关系生效日期
    private String psnInvalidTime;          //自然人关系失效日期
    private String psnRelStatement;         //自然人关联关系说明
    private String psnShareholdingRatio;     //自然人持股比例和表决权
    //2020-12-13新增
    private String psnEquityShare;//与前一层是否有权益份额
    private String psnPledgeRatio;//质押股权比例
    private String orgEquityShare;//与前一层是否有权益份额
    private String orgPledgeRatio;//质押股权比例
    private String proEquityShare;//与前一层是否有权益份额
    public String orgNature;//机构性质

    private String relSort;                 //关联关系类别
    private String relation;                //关系
    private String orgOrgName;              //机构名称
    private String orgIdentifyType;         //证件类型
    private String orgUniscCode;            //证件号码
    private String orgDomesticOrForeign;    //境内境外
    private String orgRegAddress;           //注册地址
    private String orgStatus;               //机构状态
    private String orgLegalPerson;          //法定代表人
    private String orgLegalIdentifytype;    //法定代表人证件类型
    private String orgLegalIdentifyNumber;  //法定代表人证件号码
    private String orgPredicate;            //自然人关联关系判定
    private String orgRelSort;              //关联关系类别
    private String orgRelation;             //机构关联关系
    private String orgShareholdingRatio;     //关系机构持股比例或表决权
    private String orgValidStatus;          //关系机构关系是否有效
    private String orgActiveTime;           //关系机构关系生效日期
    private String orgInvalidTime;          //关系机构关系失效日期
    private String orgRelStatement;         //关系机构关联关系说明
    private String proName;                 //产品名称
    private String proIssueNo;              //产品批准注册/备案号
    private String proRelSort;              //产品关联关系类别
    private String proRelation;             //产品关联关系
    private String proShareholdingRatio;     //产品持股比例或表决权
    private String proValidStatus;          //产品关系是否有效
    private String proActiveTime;           //产品关系生效日期
    private String proInvalidTime;          //产品关系失效日期
    private String proRelStatement;         //产品关联关系说明
    private String IsSubsidiary;               //是否控股子公司
    private String modifiedTime;               //更新日期

    public String getIsSubsidiary() {
        return IsSubsidiary;
    }

    public void setIsSubsidiary(String isSubsidiary) {
        IsSubsidiary = isSubsidiary;
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

    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital;
    }

    public String getPsnShareholdingRatio() {
        return psnShareholdingRatio;
    }

    public void setPsnShareholdingRatio(String psnShareholdingRatio) {
        this.psnShareholdingRatio = psnShareholdingRatio;
    }

    public String getOrgShareholdingRatio() {
        return orgShareholdingRatio;
    }

    public void setOrgShareholdingRatio(String orgShareholdingRatio) {
        this.orgShareholdingRatio = orgShareholdingRatio;
    }

    public String getProShareholdingRatio() {
        return proShareholdingRatio;
    }

    public void setProShareholdingRatio(String proShareholdingRatio) {
        this.proShareholdingRatio = proShareholdingRatio;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRelSort() {
        return relSort;
    }

    public void setRelSort(String relSort) {
        this.relSort = relSort;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
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

    public String getOrgRelSort() {
        return orgRelSort;
    }

    public void setOrgRelSort(String orgRelSort) {
        this.orgRelSort = orgRelSort;
    }

    public String getOrgRelation() {
        return orgRelation;
    }

    public void setOrgRelation(String orgRelation) {
        this.orgRelation = orgRelation;
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

    public String getPsnRelStatement() {
        return psnRelStatement;
    }

    public void setPsnRelStatement(String psnRelStatement) {
        this.psnRelStatement = psnRelStatement;
    }

    public String getOrgValidStatus() {
        return orgValidStatus;
    }

    public void setOrgValidStatus(String orgValidStatus) {
        this.orgValidStatus = orgValidStatus;
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

    public String getProIssueNo() {
        return proIssueNo;
    }

    public void setProIssueNo(String proIssueNo) {
        this.proIssueNo = proIssueNo;
    }

    public String getProRelSort() {
        return proRelSort;
    }

    public void setProRelSort(String proRelSort) {
        this.proRelSort = proRelSort;
    }

    public String getProRelation() {
        return proRelation;
    }

    public void setProRelation(String proRelation) {
        this.proRelation = proRelation;
    }

    public String getProValidStatus() {
        return proValidStatus;
    }

    public void setProValidStatus(String proValidStatus) {
        this.proValidStatus = proValidStatus;
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

    public String getPledgeRatio() {
        return pledgeRatio;
    }

    public void setPledgeRatio(String pledgeRatio) {
        this.pledgeRatio = pledgeRatio;
    }

    public String getPsnEquityShare() {
        return psnEquityShare;
    }

    public void setPsnEquityShare(String psnEquityShare) {
        this.psnEquityShare = psnEquityShare;
    }

    public String getPsnPledgeRatio() {
        return psnPledgeRatio;
    }

    public void setPsnPledgeRatio(String psnPledgeRatio) {
        this.psnPledgeRatio = psnPledgeRatio;
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

    public String getProEquityShare() {
        return proEquityShare;
    }

    public void setProEquityShare(String proEquityShare) {
        this.proEquityShare = proEquityShare;
    }
}
