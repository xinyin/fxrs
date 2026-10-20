package com.yzj.jep.app.ins.model;


/**
 * @author LONG
 * @date 2020/7/1
 * @description
 */

public class InsTrans {
    private String relId;
    private String relType;
    private String relName;
    private String relNumber;
    private String tranType;
    private String tradeOrg;
    private String tradeAmount;
    private String majorTrade;
    private String majorType;

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
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

    public String getRelNumber() {
        return relNumber;
    }

    public void setRelNumber(String relNumber) {
        this.relNumber = relNumber;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getTradeOrg() {
        return tradeOrg;
    }

    public void setTradeOrg(String tradeOrg) {
        this.tradeOrg = tradeOrg;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getMajorTrade() {
        return majorTrade;
    }

    public void setMajorTrade(String majorTrade) {
        this.majorTrade = majorTrade;
    }

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }
}