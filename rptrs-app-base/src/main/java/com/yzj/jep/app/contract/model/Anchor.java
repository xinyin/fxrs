package com.yzj.jep.app.contract.model;

import java.util.List;
import java.util.Objects;

public class Anchor {
    //合同主体类别（1-自然人，2-法人或机构组织）
     String contSubType;
     //Y	合同主体（公司）名称
     String contSubName;
     //合同主体证件类型(字典参考：证件类型_ uscType)
     String uscType;
     //合同主体证件号码（统一社会信用代码）
     String uscCode;
     //锚点类型（M0:资产，M1:委托方）
     String anchorType;
     //当前锚点的关联方信息集
     List<AnchorRel> relList;
     //是否超过监管上限比例（0:否，1:是），当anchorType为M0时（属于资产），该项必填
     String isOverrun;
     //超监管比例原因简述，当isOverrun为1时（超过监管比例），该项必填
     String overrunInf;
    //关联交易类别（0:一般关联交易，1:重大关联交易），当其所在锚点的anchorType（锚点类型）为M0时（属于资产），该项必填
    String relTranCategory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anchor anchor = (Anchor) o;
        return Objects.equals(contSubType, anchor.contSubType) &&
                Objects.equals(contSubName, anchor.contSubName) &&
                Objects.equals(uscType, anchor.uscType) &&
                Objects.equals(uscCode, anchor.uscCode) &&
                Objects.equals(anchorType, anchor.anchorType) &&
                Objects.equals(relList, anchor.relList) &&
                Objects.equals(isOverrun, anchor.isOverrun) &&
                Objects.equals(overrunInf, anchor.overrunInf) &&
                Objects.equals(relTranCategory, anchor.relTranCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contSubType, contSubName, uscType, uscCode, anchorType, relList, isOverrun, overrunInf, relTranCategory);
    }

    @Override
    public String toString() {
        return "Anchor{" +
                "contSubType='" + contSubType + '\'' +
                ", contSubName='" + contSubName + '\'' +
                ", uscType='" + uscType + '\'' +
                ", uscCode='" + uscCode + '\'' +
                ", anchorType='" + anchorType + '\'' +
                ", relList=" + relList +
                ", isOverrun='" + isOverrun + '\'' +
                ", overrunInf='" + overrunInf + '\'' +
                ", relTranCategory='" + relTranCategory + '\'' +
                '}';
    }

    public String getIsOverrun() {
        return isOverrun;
    }

    public void setIsOverrun(String isOverrun) {
        this.isOverrun = isOverrun;
    }

    public String getOverrunInf() {
        return overrunInf;
    }

    public void setOverrunInf(String overrunInf) {
        this.overrunInf = overrunInf;
    }

    public String getRelTranCategory() {
        return relTranCategory;
    }

    public void setRelTranCategory(String relTranCategory) {
        this.relTranCategory = relTranCategory;
    }

    public String getContSubType() {
        return contSubType;
    }

    public void setContSubType(String contSubType) {
        this.contSubType = contSubType;
    }

    public String getContSubName() {
        return contSubName;
    }

    public void setContSubName(String contSubName) {
        this.contSubName = contSubName;
    }

    public String getUscType() {
        return uscType;
    }

    public void setUscType(String uscType) {
        this.uscType = uscType;
    }

    public String getUscCode() {
        return uscCode;
    }

    public void setUscCode(String uscCode) {
        this.uscCode = uscCode;
    }

    public String getAnchorType() {
        return anchorType;
    }

    public void setAnchorType(String anchorType) {
        this.anchorType = anchorType;
    }

    public List<AnchorRel> getRelList() {
        return relList;
    }

    public void setRelList(List<AnchorRel> relList) {
        this.relList = relList;
    }



}
