package com.yzj.jep.app.bus.model;

import com.google.common.base.Objects;

/**
 * 根据证件号码去反显的分类
 *
 */


public class ForPidEntity {
    private Integer pid;
    private String name;   //名称
    private String number;  //证件号码
    private String numPSNType;  //证件类型
    private String numORGType;  //证件类型

    private String domesticOrForeign;  //境内外
    private String legalRepresentative;  //法定代表人    机构
    private String orgStatus;    //机构状态     机构
    private String proType;    //产品分类    金融产品



    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumPSNType() {
        return numPSNType;
    }

    public void setNumPSNType(String numPSNType) {
        this.numPSNType = numPSNType;
    }

    public String getNumORGType() {
        return numORGType;
    }

    public void setNumORGType(String numORGType) {
        this.numORGType = numORGType;
    }

    public String getDomesticOrForeign() {
        return domesticOrForeign;
    }

    public void setDomesticOrForeign(String domesticOrForeign) {
        this.domesticOrForeign = domesticOrForeign;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ForPidEntity{");
        sb.append("pid=").append(pid);
        sb.append(", name='").append(name).append('\'');
        sb.append(", number='").append(number).append('\'');
        sb.append(", numPSNType='").append(numPSNType).append('\'');
        sb.append(", numORGType='").append(numORGType).append('\'');
        sb.append(", domesticOrForeign='").append(domesticOrForeign).append('\'');
        sb.append(", legalRepresentative='").append(legalRepresentative).append('\'');
        sb.append(", orgStatus='").append(orgStatus).append('\'');
        sb.append(", proType='").append(proType).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForPidEntity that = (ForPidEntity) o;
        return Objects.equal(pid, that.pid) &&
                Objects.equal(name, that.name) &&
                Objects.equal(number, that.number) &&
                Objects.equal(numPSNType, that.numPSNType) &&
                Objects.equal(numORGType, that.numORGType) &&
                Objects.equal(domesticOrForeign, that.domesticOrForeign) &&
                Objects.equal(legalRepresentative, that.legalRepresentative) &&
                Objects.equal(orgStatus, that.orgStatus) &&
                Objects.equal(proType, that.proType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pid, name, number, numPSNType, numORGType, domesticOrForeign, legalRepresentative, orgStatus, proType);
    }
}
