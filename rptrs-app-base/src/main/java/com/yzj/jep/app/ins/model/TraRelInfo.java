/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:52
 *
 */

package com.yzj.jep.app.ins.model;

import javax.persistence.Column;
import java.math.BigDecimal;

public class TraRelInfo {

    private Integer pid;

    private Integer traId;

    private Integer relId;

    private String relType;

    private String relName;

    private String relNumber;

    private BigDecimal totolTradeAmount;

    private BigDecimal tradeAmount;

    private BigDecimal assetsLastQuarter;

    private String businessType;

    private String tranOpponent;

    private String certType;

    private String totalassetsLateLast;
    //是否是重大
    private String majorTrade;
    //重大类型，1单笔重大，2累计重大
    private String majorType;
    //关联方总资产
    private BigDecimal totalAssets;
    //年度未清零累计总交易金额
    private BigDecimal totalNotCleanTradeAmount;
    //交易主体
    private String tenantId;
    //交易主体名称
    private String traSubName;
    //当前关联方是否造成重大
    private String thisIsMajor;  // 1 是 0 否
    //是否达到重大的80%
    private String majorTradeCritical;//1是 0否

    public String getTraSubName() {
        return traSubName;
    }

    public void setTraSubName(String traSubName) {
        this.traSubName = traSubName;
    }

    public String getThisIsMajor() {
        return thisIsMajor;
    }

    public void setThisIsMajor(String thisIsMajor) {
        this.thisIsMajor = thisIsMajor;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public BigDecimal getTotalNotCleanTradeAmount() {
        return totalNotCleanTradeAmount;
    }

    public void setTotalNotCleanTradeAmount(BigDecimal totalNotCleanTradeAmount) {
        this.totalNotCleanTradeAmount = totalNotCleanTradeAmount;
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
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

    public String getTotalassetsLateLast() {
        return totalassetsLateLast;
    }

    public void setTotalassetsLateLast(String totalassetsLateLast) {
        this.totalassetsLateLast = totalassetsLateLast;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getTranOpponent() {
        return tranOpponent;
    }

    public void setTranOpponent(String tranOpponent) {
        this.tranOpponent = tranOpponent;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getTraId() {
        return traId;
    }

    public void setTraId(Integer traId) {
        this.traId = traId;
    }

    public Integer getRelId() {
        return relId;
    }

    public void setRelId(Integer relId) {
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

    public BigDecimal getTotolTradeAmount() {
        return totolTradeAmount;
    }

    public void setTotolTradeAmount(BigDecimal totolTradeAmount) {
        this.totolTradeAmount = totolTradeAmount;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public BigDecimal getAssetsLastQuarter() {
        return assetsLastQuarter;
    }

    public void setAssetsLastQuarter(BigDecimal assetsLastQuarter) {
        this.assetsLastQuarter = assetsLastQuarter;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMajorTradeCritical() {
        return majorTradeCritical;
    }

    public void setMajorTradeCritical(String majorTradeCritical) {
        this.majorTradeCritical = majorTradeCritical;
    }
}

