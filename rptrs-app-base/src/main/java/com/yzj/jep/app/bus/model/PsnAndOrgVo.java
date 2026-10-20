package com.yzj.jep.app.bus.model;

import java.math.BigDecimal;

public class PsnAndOrgVo {

    private String pid;
    private Integer psnId;
    private Integer orgId;

    private String name_;    //姓名

    private String identifyTypePSN;    //自然人证件类型
    private String identifyTypeORG;    //机构证件类型
    private String identifyType;    //证件类型
    private String identifyTypes;    //证件类型中文
    private String identifyNumber;    //证件号码

    private String relCategory;    //关联关系类别

    private String relCategorySub;    //关系

    public String getDomesticOrForeign() {
        return domesticOrForeign;
    }

    public void setDomesticOrForeign(String domesticOrForeign) {
        this.domesticOrForeign = domesticOrForeign;
    }

    private String domesticOrForeign;    //境内外标识

    private String mgerOrg;    //管户机构

    private String caliber;    //口径

    public String getRegulatorOth1() {
        return regulatorOth1;
    }

    public void setRegulatorOth1(String regulatorOth1) {
        this.regulatorOth1 = regulatorOth1;
    }

    private String regulatorOth1; // 口径

    private String isBlack;    //是否黑名单

    private String fromPid;

    private String toPid;

    private String memGroup; //集团号

    private String groupName; //集团名

    private String stateOwned;//是否国有企业

    private String fromRel;//归属关联主体

    private java.math.BigDecimal totalTradeAmount;//关联交易用到得本年度与该关联方累计金额

    private String rattotalTradeAmount;

    private BigDecimal ratDoubleTradeAmount;

    private java.math.BigDecimal tradeZeroAmount;

    public java.math.BigDecimal getTradeZeroAmount() {
        return tradeZeroAmount;
    }

    public void setTradeZeroAmount(java.math.BigDecimal tradeZeroAmount) {
        this.tradeZeroAmount = tradeZeroAmount;
    }

    public String getIdentifyTypePSN() {
        return identifyTypePSN;
    }

    public void setIdentifyTypePSN(String identifyTypePSN) {
        this.identifyTypePSN = identifyTypePSN;
    }

    public String getIdentifyTypeORG() {
        return identifyTypeORG;
    }

    public void setIdentifyTypeORG(String identifyTypeORG) {
        this.identifyTypeORG = identifyTypeORG;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type; // 关联方类型


    public java.math.BigDecimal getTotalTradeAmount() {
        return totalTradeAmount;
    }

    public void setTotalTradeAmount(java.math.BigDecimal totalTradeAmount) {
        this.totalTradeAmount = totalTradeAmount;
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

    public String getStateOwned() {
        return stateOwned;
    }

    public void setStateOwned(String stateOwned) {
        this.stateOwned = stateOwned;
    }

    public String getFromRel() {
        return fromRel;
    }

    public void setFromRel(String fromRel) {
        this.fromRel = fromRel;
    }


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getPsnId() {
        return psnId;
    }

    public void setPsnId(Integer psnId) {
        this.psnId = psnId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name_;
    }

    public void setName(String name) {
        this.name_ = name;
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


    public String getMgerOrg() {
        return mgerOrg;
    }

    public void setMgerOrg(String mgerOrg) {
        this.mgerOrg = mgerOrg;
    }

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = caliber;
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

    public String getToPid() {
        return toPid;
    }

    public void setToPid(String toPid) {
        this.toPid = toPid;
    }

    public String getRattotalTradeAmount() {
        return rattotalTradeAmount;
    }

    public void setRattotalTradeAmount(String rattotalTradeAmount) {
        this.rattotalTradeAmount = rattotalTradeAmount;
    }

    public BigDecimal getRatDoubleTradeAmount() {
        return ratDoubleTradeAmount;
    }

    public void setRatDoubleTradeAmount(BigDecimal ratDoubleTradeAmount) {
        this.ratDoubleTradeAmount = ratDoubleTradeAmount;
    }

    public String getIdentifyTypes() {
        return identifyTypes;
    }

    public void setIdentifyTypes(String identifyTypes) {
        this.identifyTypes = identifyTypes;
    }
}
