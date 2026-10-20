package com.yzj.jep.app.bus.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TraSubmitVo {
    private String name; //机构名称
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd hh:mm:ss")
    private Date validityContract;
    private String fromPid;
    private String manageOrg;
    private String identifyType;
    private String identNumber;
    private String tranType;
    private String businessType;
    private String relatedTranType;
    private String tranAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getValidityContract() {
        return validityContract;
    }

    public void setValidityContract(Date validityContract) {
        this.validityContract = validityContract;
    }

    public String getFromPid() {
        return fromPid;
    }

    public void setFromPid(String fromPid) {
        this.fromPid = fromPid;
    }

    public String getManageOrg() {
        return manageOrg;
    }

    public void setManageOrg(String manageOrg) {
        this.manageOrg = manageOrg;
    }

    public String getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    public String getIdentNumber() {
        return identNumber;
    }

    public void setIdentNumber(String identNumber) {
        this.identNumber = identNumber;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getRelatedTranType() {
        return relatedTranType;
    }

    public void setRelatedTranType(String relatedTranType) {
        this.relatedTranType = relatedTranType;
    }

    public String getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(String tranAmount) {
        this.tranAmount = tranAmount;
    }
}
