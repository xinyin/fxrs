package com.yzj.jep.app.bus.vo;

import com.yzj.jep.orm.entity.Treeable;

import java.io.Serializable;

public class TreeNodeVo implements Treeable {

    private Integer pid;

    private String name;

    private String type;

    private String relCategory;

    private String relCategorySub;

    private Float shareholdingRatio;

    private String shareholdingStrand;

    private String pidAndType;

    private String nameAndType;

    private Integer relPid;

    private String shareholdersCode;

    private String identifyType;

    private String identifyNumber;


    @Override
    public Integer getPid() {
        return pid;
    }

    @Override
    public String getTitle() {
        return this.getName();
    }

    @Override
    public boolean isParent() {
        return true;
    }

    @Override
    public boolean isChkDisabled() {
        return false;
    }

    @Override
    public boolean isNocheck() {
        return false;
    }

    @Override
    public boolean isDrag() {
        return false;
    }

    @Override
    public boolean isDrop() {
        return false;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public Serializable getId() {
        return this.getPidAndType();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Float getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(Float shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    public String getPidAndType() {
        return pidAndType;
    }

    public void setPidAndType(String pidAndType) {
        this.pidAndType = pidAndType;
    }

    public String getNameAndType() {
        return nameAndType;
    }

    public void setNameAndType(String nameAndType) {
        this.nameAndType = nameAndType;
    }

    public String getShareholdingStrand() {
        return shareholdingStrand;
    }

    public void setShareholdingStrand(String shareholdingStrand) {
        this.shareholdingStrand = shareholdingStrand;
    }

    public Integer getRelPid() {
        return relPid;
    }

    public void setRelPid(Integer relPid) {
        this.relPid = relPid;
    }

    public String getShareholdersCode() {
        return shareholdersCode;
    }

    public void setShareholdersCode(String shareholdersCode) {
        this.shareholdersCode = shareholdersCode;
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

    @Override
    public String toString() {
        return "TreeNodeVo{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", relCategory='" + relCategory + '\'' +
                ", relCategorySub='" + relCategorySub + '\'' +
                ", shareholdingRatio=" + shareholdingRatio +
                ", shareholdingStrand='" + shareholdingStrand + '\'' +
                ", pidAndType='" + pidAndType + '\'' +
                ", nameAndType='" + nameAndType + '\'' +
                ", relPid=" + relPid +
                ", shareholdersCode='" + shareholdersCode + '\'' +
                ", identifyType='" + identifyType + '\'' +
                ", identifyNumber='" + identifyNumber + '\'' +
                '}';
    }
}
