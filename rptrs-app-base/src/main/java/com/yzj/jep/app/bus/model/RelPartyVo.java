package com.yzj.jep.app.bus.model;

import java.util.Objects;

public class RelPartyVo {
    //交易主体名称
    String traSubName;
    //关联方类别 （PSN-自然人，ORG-法人或机构组织）
    String relType;
    //关联方名称
    String relName;
    //关联方证件类型
    String certType;
    //关联方证件号码
    String relNumber;
    //是否为关联方（0:否，1:是）
    String isRelParty;

    public String getTraSubName() {
        return traSubName;
    }

    public void setTraSubName(String traSubName) {
        this.traSubName = traSubName;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getRelNumber() {
        return relNumber;
    }

    public void setRelNumber(String relNumber) {
        this.relNumber = relNumber;
    }

    public String getIsRelParty() {
        return isRelParty;
    }

    public void setIsRelParty(String isRelParty) {
        this.isRelParty = isRelParty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelPartyVo)) return false;
        RelPartyVo that = (RelPartyVo) o;
        return Objects.equals(getRelType(), that.getRelType()) &&
                Objects.equals(getRelName(), that.getRelName()) &&
                Objects.equals(getCertType(), that.getCertType()) &&
                Objects.equals(getRelNumber(), that.getRelNumber()) &&
                Objects.equals(getTraSubName(), that.getTraSubName()) &&
                Objects.equals(getIsRelParty(), that.getIsRelParty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRelType(), getRelName(), getCertType(), getRelNumber(), getTraSubName(), getIsRelParty());
    }

    @Override
    public String toString() {
        return "RelPartyVo{" +
                "relType='" + relType + '\'' +
                ", relName='" + relName + '\'' +
                ", certType='" + certType + '\'' +
                ", relNumber='" + relNumber + '\'' +
                ", traSubName='" + traSubName + '\'' +
                ", isRelParty='" + isRelParty + '\'' +
                '}';
    }
}
