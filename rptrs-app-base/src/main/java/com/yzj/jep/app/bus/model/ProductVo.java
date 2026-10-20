package com.yzj.jep.app.bus.model;


/**
 * @author LONG
 * @date 2020/8/20
 * @description
 */

public class ProductVo {

    private String no;
    private String tradeType;
    private String tranOrg;
    private String businessType;
    private String proName;
    private String pasLimit;
    private Integer limitRate;
    private String preLimit;
    private String remLimit;
    private String warnLeve;
    private String status;
    private String statusComment;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTranOrg() {
        return tranOrg;
    }

    public void setTranOrg(String tranOrg) {
        this.tranOrg = tranOrg;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getPasLimit() {
        return pasLimit;
    }

    public void setPasLimit(String pasLimit) {
        this.pasLimit = pasLimit;
    }

    public Integer getLimitRate() {
        return limitRate;
    }

    public void setLimitRate(Integer limitRate) {
        this.limitRate = limitRate;
    }

    public String getPreLimit() {
        return preLimit;
    }

    public void setPreLimit(String preLimit) {
        this.preLimit = preLimit;
    }

    public String getRemLimit() {
        return remLimit;
    }

    public void setRemLimit(String remLimit) {
        this.remLimit = remLimit;
    }

    public String getWarnLeve() {
        return warnLeve;
    }

    public void setWarnLeve(String warnLeve) {
        this.warnLeve = warnLeve;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }
}