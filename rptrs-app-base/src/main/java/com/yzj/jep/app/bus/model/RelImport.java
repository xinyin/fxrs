package com.yzj.jep.app.bus.model;


import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bus_rel_import")
public class RelImport extends BasePO {
    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;
    @Column(name = "to_pid", nullable = true, length = 32)
    private String toPid;
    @Column(name = "to_name", nullable = true, length = 16)
    private String toName;
    @Column(name = "to_type", nullable = true, length = 16)
    private String toType;
    @Column(name = "to_identify_number", nullable = true, length = 20)
    private String toIdentifyNumber;
    @Column(name = "from_pid", nullable = true, length = 20)
    private String fromPid;
    @Column(name = "from_name", nullable = true, length = 16)
    private String fromName;
    @Column(name = "from_type", nullable = true, length = 16)
    private String fromType;
    @Column(name = "from_identify_number", nullable = true, length = 20)
    private String fromIdentifyNumber;
    @Column(name = "rel_category", nullable = true, length = 16)
    private String relCategory;
    @Column(name = "rel_psn_org", nullable = true, length = 20)
    private String relPsnOrg;
    @Column(name = "equity_share", nullable = true, length = 10)
    private String equityShare;
    @Column(name = "shareholding_ratio", nullable = true, length = 16)
    private Float shareholdingRatio;
    @Column(name = "pledge_ratio", nullable = true, length = 10)
    private String pledgeRatio;
    @Column(name = "isValid", nullable = true, length = 1)
    private String isValid;
    @Column(name = "active_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date activeTime;
    @Column(name = "invalid_time", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date invalidTime;
    @Column(name = "relStatement", nullable = true, length = 1000)
    private String relStatement;


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getToPid() {
        return toPid;
    }

    public void setToPid(String toPid) {
        this.toPid = toPid;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getToIdentifyNumber() {
        return toIdentifyNumber;
    }

    public void setToIdentifyNumber(String toIdentifyNumber) {
        this.toIdentifyNumber = toIdentifyNumber;
    }

    public String getFromPid() {
        return fromPid;
    }

    public void setFromPid(String fromPid) {
        this.fromPid = fromPid;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getFromIdentifyNumber() {
        return fromIdentifyNumber;
    }

    public void setFromIdentifyNumber(String fromIdentifyNumber) {
        this.fromIdentifyNumber = fromIdentifyNumber;
    }

    public String getRelCategory() {
        return relCategory;
    }

    public void setRelCategory(String relCategory) {
        this.relCategory = relCategory;
    }

    public String getRelPsnOrg() {
        return relPsnOrg;
    }

    public void setRelPsnOrg(String relPsnOrg) {
        this.relPsnOrg = relPsnOrg;
    }

    public String getEquityShare() {
        return equityShare;
    }

    public void setEquityShare(String equityShare) {
        this.equityShare = equityShare;
    }

    public Float getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(Float shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    public String getPledgeRatio() {
        return pledgeRatio;
    }

    public void setPledgeRatio(String pledgeRatio) {
        this.pledgeRatio = pledgeRatio;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getRelStatement() {
        return relStatement;
    }

    public void setRelStatement(String relStatement) {
        this.relStatement = relStatement;
    }
}
