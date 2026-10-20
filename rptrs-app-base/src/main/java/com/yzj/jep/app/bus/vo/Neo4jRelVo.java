package com.yzj.jep.app.bus.vo;

import java.math.BigDecimal;

public class Neo4jRelVo {
    private Integer fromPid;//本级id
    private Integer toPid;//上一级id
    private String fromName;//本级名称
    private String toName;//上一级名称
    private String fromType;//本级类型（psn-人，org-机构，pro-产品）
    private String toType;//上一级类型（psn-人，org-机构，pro-产品）
    private String fromIdentifyType;//from证件类型
    private String toIdentifyType;//to证件类型
    private String fromIdentifyNumber;//from证件号码
    private String toIdentifyNumber;//to证件号码
    private String relCategory;//一级关系
    private String relCategorySub;//二级关系
    private int fromIsRelParty; //是否关联方
    private int fromIsInsider; //是否内部人
    private int fromIsShareholder; //是否股东
    private int toIsRelParty; //是否关联方
    private int toIsInsider; //是否内部人
    private int toIsShareholder; //是否股东
    private String relWithprevious;//与前一层的关联关系
    private String fromTenantId; //from租户类型
    private String toTenantId; //to租户类型
    /**
     * 持股比例
     */
    private BigDecimal shareholdingRatio;

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

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getFromIdentifyType() {
        return fromIdentifyType;
    }

    public void setFromIdentifyType(String fromIdentifyType) {
        this.fromIdentifyType = fromIdentifyType;
    }

    public String getToIdentifyType() {
        return toIdentifyType;
    }

    public void setToIdentifyType(String toIdentifyType) {
        this.toIdentifyType = toIdentifyType;
    }

    public String getFromIdentifyNumber() {
        return fromIdentifyNumber;
    }

    public void setFromIdentifyNumber(String fromIdentifyNumber) {
        this.fromIdentifyNumber = fromIdentifyNumber;
    }

    public String getToIdentifyNumber() {
        return toIdentifyNumber;
    }

    public void setToIdentifyNumber(String toIdentifyNumber) {
        this.toIdentifyNumber = toIdentifyNumber;
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

    public int getFromIsRelParty() {
        return fromIsRelParty;
    }

    public void setFromIsRelParty(int fromIsRelParty) {
        this.fromIsRelParty = fromIsRelParty;
    }

    public int getFromIsInsider() {
        return fromIsInsider;
    }

    public void setFromIsInsider(int fromIsInsider) {
        this.fromIsInsider = fromIsInsider;
    }

    public int getFromIsShareholder() {
        return fromIsShareholder;
    }

    public void setFromIsShareholder(int fromIsShareholder) {
        this.fromIsShareholder = fromIsShareholder;
    }

    public int getToIsRelParty() {
        return toIsRelParty;
    }

    public void setToIsRelParty(int toIsRelParty) {
        this.toIsRelParty = toIsRelParty;
    }

    public int getToIsInsider() {
        return toIsInsider;
    }

    public void setToIsInsider(int toIsInsider) {
        this.toIsInsider = toIsInsider;
    }

    public int getToIsShareholder() {
        return toIsShareholder;
    }

    public void setToIsShareholder(int toIsShareholder) {
        this.toIsShareholder = toIsShareholder;
    }

    public String getRelWithprevious() {
        return relWithprevious;
    }

    public void setRelWithprevious(String relWithprevious) {
        this.relWithprevious = relWithprevious;
    }

    public BigDecimal getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(BigDecimal shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    public String getFromTenantId() {
        return fromTenantId;
    }

    public void setFromTenantId(String fromTenantId) {
        this.fromTenantId = fromTenantId;
    }

    public String getToTenantId() {
        return toTenantId;
    }

    public void setToTenantId(String toTenantId) {
        this.toTenantId = toTenantId;
    }
}
