package com.yzj.jep.app.sub.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class InsAllFundsVo {

    //年度
    private String Years;
    //总资产(上年末)
    private BigDecimal lastYearTotalAssets;
    //净资产(上年末)
    private BigDecimal lastYearNetWorth;
    //固定收益类资产投资余额
    private BigDecimal fixedProfitBalance;
    //上市权益类资产投资余额
    private BigDecimal listedInvestBalance;
    //未上市权益类资产投资余额
    private BigDecimal unlistedInvestBalance;
    //未上市权益类资产投资占总资产比例
    private String ratUnlistedInvestBalance;
    //不动产类资产投资余额
    private BigDecimal imvbsInvestBalance;
    //不动产类资产占总资产比例
    private String ratImvbsInvestBalance;
    //商业银行理财投资余额
    private BigDecimal comBankFinFund;
    //银行业金融机构信贷资产支持证券投资余额
    private BigDecimal bankCreditFund;
    //信托公司集合资金信托计划投资余额
    private BigDecimal trustFund;
    //证券公司专项资产管理计划投资余额
    private BigDecimal bondSpecialFund;
    //其他类金融资产投资余额
    private BigDecimal otherFund;
    //其他类金融资产投资-合计
    private BigDecimal otherFundAdd;
    //其他金融产品占总资产比例
    private BigDecimal ratOtherFund;
    //境外投资余额
    private BigDecimal abroadFund;
    //境外投资占总资产比例
    private String ratAbroadFund;
    //全部投资余额(含活期存款)
    private BigDecimal allInvetmenthq;
    //全部投资余额占总资产比例
    private String ratAllInvestmentTotal;
    //全部投资余额占净资产比例
    private String ratAllInvestmentWorth;
    //全部投资余额
    private BigDecimal allInvestmentBalance;
    //流动性资产投资余额
    private BigDecimal mobilityBalance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsAllFundsVo that = (InsAllFundsVo) o;
        return Objects.equals(Years, that.Years) && Objects.equals(lastYearTotalAssets, that.lastYearTotalAssets) && Objects.equals(lastYearNetWorth, that.lastYearNetWorth) && Objects.equals(fixedProfitBalance, that.fixedProfitBalance) && Objects.equals(listedInvestBalance, that.listedInvestBalance) && Objects.equals(unlistedInvestBalance, that.unlistedInvestBalance) && Objects.equals(ratUnlistedInvestBalance, that.ratUnlistedInvestBalance) && Objects.equals(imvbsInvestBalance, that.imvbsInvestBalance) && Objects.equals(ratImvbsInvestBalance, that.ratImvbsInvestBalance) && Objects.equals(comBankFinFund, that.comBankFinFund) && Objects.equals(bankCreditFund, that.bankCreditFund) && Objects.equals(trustFund, that.trustFund) && Objects.equals(bondSpecialFund, that.bondSpecialFund) && Objects.equals(otherFund, that.otherFund) && Objects.equals(otherFundAdd, that.otherFundAdd) && Objects.equals(ratOtherFund, that.ratOtherFund) && Objects.equals(abroadFund, that.abroadFund) && Objects.equals(ratAbroadFund, that.ratAbroadFund) && Objects.equals(allInvetmenthq, that.allInvetmenthq) && Objects.equals(ratAllInvestmentTotal, that.ratAllInvestmentTotal) && Objects.equals(ratAllInvestmentWorth, that.ratAllInvestmentWorth) && Objects.equals(allInvestmentBalance, that.allInvestmentBalance) && Objects.equals(mobilityBalance, that.mobilityBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Years, lastYearTotalAssets, lastYearNetWorth, fixedProfitBalance, listedInvestBalance, unlistedInvestBalance, ratUnlistedInvestBalance, imvbsInvestBalance, ratImvbsInvestBalance, comBankFinFund, bankCreditFund, trustFund, bondSpecialFund, otherFund, otherFundAdd, ratOtherFund, abroadFund, ratAbroadFund, allInvetmenthq, ratAllInvestmentTotal, ratAllInvestmentWorth, allInvestmentBalance, mobilityBalance);
    }

    @Override
    public String toString() {
        return "InsAllFundsVo{" +
                "Years='" + Years + '\'' +
                ", lastYearTotalAssets=" + lastYearTotalAssets +
                ", lastYearNetWorth=" + lastYearNetWorth +
                ", fixedProfitBalance=" + fixedProfitBalance +
                ", listedInvestBalance=" + listedInvestBalance +
                ", unlistedInvestBalance=" + unlistedInvestBalance +
                ", ratUnlistedInvestBalance='" + ratUnlistedInvestBalance + '\'' +
                ", imvbsInvestBalance=" + imvbsInvestBalance +
                ", ratImvbsInvestBalance='" + ratImvbsInvestBalance + '\'' +
                ", comBankFinFund=" + comBankFinFund +
                ", bankCreditFund=" + bankCreditFund +
                ", trustFund=" + trustFund +
                ", bondSpecialFund=" + bondSpecialFund +
                ", otherFund=" + otherFund +
                ", otherFundAdd=" + otherFundAdd +
                ", ratOtherFund=" + ratOtherFund +
                ", abroadFund=" + abroadFund +
                ", ratAbroadFund='" + ratAbroadFund + '\'' +
                ", allInvetmenthq=" + allInvetmenthq +
                ", ratAllInvestmentTotal='" + ratAllInvestmentTotal + '\'' +
                ", ratAllInvestmentWorth='" + ratAllInvestmentWorth + '\'' +
                ", allInvestmentBalance=" + allInvestmentBalance +
                ", mobilityBalance=" + mobilityBalance +
                '}';
    }

    public String getYears() {
        return Years;
    }

    public void setYears(String years) {
        Years = years;
    }

    public BigDecimal getLastYearTotalAssets() {
        return lastYearTotalAssets;
    }

    public void setLastYearTotalAssets(BigDecimal lastYearTotalAssets) {
        this.lastYearTotalAssets = lastYearTotalAssets;
    }

    public BigDecimal getLastYearNetWorth() {
        return lastYearNetWorth;
    }

    public void setLastYearNetWorth(BigDecimal lastYearNetWorth) {
        this.lastYearNetWorth = lastYearNetWorth;
    }

    public BigDecimal getFixedProfitBalance() {
        return fixedProfitBalance;
    }

    public void setFixedProfitBalance(BigDecimal fixedProfitBalance) {
        this.fixedProfitBalance = fixedProfitBalance;
    }

    public BigDecimal getListedInvestBalance() {
        return listedInvestBalance;
    }

    public void setListedInvestBalance(BigDecimal listedInvestBalance) {
        this.listedInvestBalance = listedInvestBalance;
    }

    public BigDecimal getUnlistedInvestBalance() {
        return unlistedInvestBalance;
    }

    public void setUnlistedInvestBalance(BigDecimal unlistedInvestBalance) {
        this.unlistedInvestBalance = unlistedInvestBalance;
    }

    public String getRatUnlistedInvestBalance() {
        return ratUnlistedInvestBalance;
    }

    public void setRatUnlistedInvestBalance(String ratUnlistedInvestBalance) {
        this.ratUnlistedInvestBalance = ratUnlistedInvestBalance;
    }

    public BigDecimal getImvbsInvestBalance() {
        return imvbsInvestBalance;
    }

    public void setImvbsInvestBalance(BigDecimal imvbsInvestBalance) {
        this.imvbsInvestBalance = imvbsInvestBalance;
    }

    public String getRatImvbsInvestBalance() {
        return ratImvbsInvestBalance;
    }

    public void setRatImvbsInvestBalance(String ratImvbsInvestBalance) {
        this.ratImvbsInvestBalance = ratImvbsInvestBalance;
    }

    public BigDecimal getComBankFinFund() {
        return comBankFinFund;
    }

    public void setComBankFinFund(BigDecimal comBankFinFund) {
        this.comBankFinFund = comBankFinFund;
    }

    public BigDecimal getBankCreditFund() {
        return bankCreditFund;
    }

    public void setBankCreditFund(BigDecimal bankCreditFund) {
        this.bankCreditFund = bankCreditFund;
    }

    public BigDecimal getTrustFund() {
        return trustFund;
    }

    public void setTrustFund(BigDecimal trustFund) {
        this.trustFund = trustFund;
    }

    public BigDecimal getBondSpecialFund() {
        return bondSpecialFund;
    }

    public void setBondSpecialFund(BigDecimal bondSpecialFund) {
        this.bondSpecialFund = bondSpecialFund;
    }

    public BigDecimal getOtherFund() {
        return otherFund;
    }

    public void setOtherFund(BigDecimal otherFund) {
        this.otherFund = otherFund;
    }

    public BigDecimal getOtherFundAdd() {
        return otherFundAdd;
    }

    public void setOtherFundAdd(BigDecimal otherFundAdd) {
        this.otherFundAdd = otherFundAdd;
    }

    public BigDecimal getRatOtherFund() {
        return ratOtherFund;
    }

    public void setRatOtherFund(BigDecimal ratOtherFund) {
        this.ratOtherFund = ratOtherFund;
    }

    public BigDecimal getAbroadFund() {
        return abroadFund;
    }

    public void setAbroadFund(BigDecimal abroadFund) {
        this.abroadFund = abroadFund;
    }

    public String getRatAbroadFund() {
        return ratAbroadFund;
    }

    public void setRatAbroadFund(String ratAbroadFund) {
        this.ratAbroadFund = ratAbroadFund;
    }

    public BigDecimal getAllInvetmenthq() {
        return allInvetmenthq;
    }

    public void setAllInvetmenthq(BigDecimal allInvetmenthq) {
        this.allInvetmenthq = allInvetmenthq;
    }

    public String getRatAllInvestmentTotal() {
        return ratAllInvestmentTotal;
    }

    public void setRatAllInvestmentTotal(String ratAllInvestmentTotal) {
        this.ratAllInvestmentTotal = ratAllInvestmentTotal;
    }

    public String getRatAllInvestmentWorth() {
        return ratAllInvestmentWorth;
    }

    public void setRatAllInvestmentWorth(String ratAllInvestmentWorth) {
        this.ratAllInvestmentWorth = ratAllInvestmentWorth;
    }

    public BigDecimal getAllInvestmentBalance() {
        return allInvestmentBalance;
    }

    public void setAllInvestmentBalance(BigDecimal allInvestmentBalance) {
        this.allInvestmentBalance = allInvestmentBalance;
    }

    public BigDecimal getMobilityBalance() {
        return mobilityBalance;
    }

    public void setMobilityBalance(BigDecimal mobilityBalance) {
        this.mobilityBalance = mobilityBalance;
    }
}
