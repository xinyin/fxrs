package com.yzj.jep.app.bus.model;

import org.springframework.stereotype.Component;

@Component
public class OrgSuspect {
    //关联关系自增pid
    private Integer pid;
    private String orgName;
    private String name;
    private String identifyType;
    private String identifyNumber;
    private String uniscCode;
    private Float shareholdingRatio;
    private String dutyCode;
    private String sex;
    private String relation;
    //源主体pid
    private Integer fromPid;
    //关联主体pid
    private Integer toPid;
    private String relCategory;
    private String fromMemo;
    private String toMemo;
    private String relCategorySub;

    public String getRelCategorySub() {
        return relCategorySub;
    }

    public void setRelCategorySub(String relCategorySub) {
        this.relCategorySub = relCategorySub;
    }

    public String getRelCategory() {
        return relCategory;
    }

    public void setRelCategory(String relCategory) {
        this.relCategory = relCategory;
    }

    public String getFromMemo() {
        return fromMemo;
    }

    public void setFromMemo(String fromMemo) {
        this.fromMemo = fromMemo;
    }

    public String getToMemo() {
        return toMemo;
    }

    public void setToMemo(String toMemo) {
        this.toMemo = toMemo;
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

    public String getUniscCode() {
        return uniscCode;
    }

    public void setUniscCode(String uniscCode) {
        this.uniscCode = uniscCode;
    }

    public Float getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(Float shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    public String getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
