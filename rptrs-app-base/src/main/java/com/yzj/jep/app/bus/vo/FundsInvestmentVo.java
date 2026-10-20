package com.yzj.jep.app.bus.vo;

import com.yzj.jep.app.bus.model.MsgDeploy;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class FundsInvestmentVo implements Serializable {

    private Integer pid;  //主键

    private Date investmentBalanceTime; //投资余额统计时间

    private BigDecimal realEstateBalance; //不动资产投资余额

    private BigDecimal realEstateLimit; //不动资产投资限额

    private BigDecimal unlistedBalance; //未上市权益类资产投资余额

    private BigDecimal unlistedLimit; //未上市权益类资产投资限额

    private BigDecimal foreignBalance;//境外投资余额

    private BigDecimal foreignLimit;//境外投资限额

    private BigDecimal otherFinanceBalance;//其他类金融资产投资余额

    private BigDecimal otherFinanceLimit;//其他类金融资产投资限额

    private BigDecimal fixedIncomeBalance;//固定收益类资产投资余额

    private BigDecimal listedBalance;//上市权益类资产投资余额

    private BigDecimal insuranceManagementBalance;//保险资产管理产品投资余额

    private BigDecimal bankFinancialBalance;//商业银行理财产品投资余额

    private BigDecimal bankNegotiableSecuritiesBalance;//银行业金融机构信贷资产支持证券投资余额

    private BigDecimal trustSchemeBalance;//信托公司集合资金信托计划投资余额

    private BigDecimal negotiableSecuritiesSpecialPlanBalance;//证券公司专项资产管理计划投资余额

    private BigDecimal insuranceFundsSupportPlanBalance;//保险资管公司发行资产支持计划投资余额

    private BigDecimal otherInvestmentBalance;//其他投资余额

    private BigDecimal financialProducts;//金融产品投资限额


    private BigDecimal balanceInvestment;// 权益类资产投资余额

    private BigDecimal balanceInvestmentLimit;

    public BigDecimal getBalanceInvestment() {
        return balanceInvestment;
    }

    public void setBalanceInvestment(BigDecimal balanceInvestment) {
        this.balanceInvestment = balanceInvestment;
    }

    public BigDecimal getBalanceInvestmentLimit() {
        return balanceInvestmentLimit;
    }

    public void setBalanceInvestmentLimit(BigDecimal balanceInvestmentLimit) {
        this.balanceInvestmentLimit = balanceInvestmentLimit;
    }
    public BigDecimal getAllInvestmentBalance() {
        return allInvestmentBalance;
    }

    public void setAllInvestmentBalance(BigDecimal allInvestmentBalance) {
        this.allInvestmentBalance = allInvestmentBalance;
    }

    @Override
    public String toString() {
        return "FundsInvestmentVo{" +
                "pid=" + pid +
                ", investmentBalanceTime=" + investmentBalanceTime +
                ", realEstateBalance=" + realEstateBalance +
                ", realEstateLimit=" + realEstateLimit +
                ", unlistedBalance=" + unlistedBalance +
                ", unlistedLimit=" + unlistedLimit +
                ", foreignBalance=" + foreignBalance +
                ", foreignLimit=" + foreignLimit +
                ", otherFinanceBalance=" + otherFinanceBalance +
                ", otherFinanceLimit=" + otherFinanceLimit +
                ", fixedIncomeBalance=" + fixedIncomeBalance +
                ", listedBalance=" + listedBalance +
                ", insuranceManagementBalance=" + insuranceManagementBalance +
                ", bankFinancialBalance=" + bankFinancialBalance +
                ", bankNegotiableSecuritiesBalance=" + bankNegotiableSecuritiesBalance +
                ", trustSchemeBalance=" + trustSchemeBalance +
                ", negotiableSecuritiesSpecialPlanBalance=" + negotiableSecuritiesSpecialPlanBalance +
                ", insuranceFundsSupportPlanBalance=" + insuranceFundsSupportPlanBalance +
                ", otherInvestmentBalance=" + otherInvestmentBalance +
                ", allInvestmentBalance=" + allInvestmentBalance +
                ", page=" + page +
                ", pageSize=" + pageSize +
                ", startInvestmentBalanceTime='" + startInvestmentBalanceTime + '\'' +
                ", endInvestmentBalanceTime='" + endInvestmentBalanceTime + '\'' +
                ", years='" + years + '\'' +
                ", quarter='" + quarter + '\'' +
                '}';
    }

    private BigDecimal allInvestmentBalance;//全部投资余额


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Date getInvestmentBalanceTime() {
        return investmentBalanceTime;
    }

    public void setInvestmentBalanceTime(Date investmentBalanceTime) {
        this.investmentBalanceTime = investmentBalanceTime;
    }

    public BigDecimal getRealEstateBalance() {
        return realEstateBalance;
    }

    public void setRealEstateBalance(BigDecimal realEstateBalance) {
        this.realEstateBalance = realEstateBalance;
    }

    public BigDecimal getRealEstateLimit() {
        return realEstateLimit;
    }

    public void setRealEstateLimit(BigDecimal realEstateLimit) {
        this.realEstateLimit = realEstateLimit;
    }

    public BigDecimal getUnlistedBalance() {
        return unlistedBalance;
    }

    public void setUnlistedBalance(BigDecimal unlistedBalance) {
        this.unlistedBalance = unlistedBalance;
    }

    public BigDecimal getUnlistedLimit() {
        return unlistedLimit;
    }

    public void setUnlistedLimit(BigDecimal unlistedLimit) {
        this.unlistedLimit = unlistedLimit;
    }

    public BigDecimal getForeignBalance() {
        return foreignBalance;
    }

    public void setForeignBalance(BigDecimal foreignBalance) {
        this.foreignBalance = foreignBalance;
    }

    public BigDecimal getForeignLimit() {
        return foreignLimit;
    }

    public void setForeignLimit(BigDecimal foreignLimit) {
        this.foreignLimit = foreignLimit;
    }

    public BigDecimal getOtherFinanceBalance() {
        return otherFinanceBalance;
    }

    public void setOtherFinanceBalance(BigDecimal otherFinanceBalance) {
        this.otherFinanceBalance = otherFinanceBalance;
    }

    public BigDecimal getOtherFinanceLimit() {
        return otherFinanceLimit;
    }

    public void setOtherFinanceLimit(BigDecimal otherFinanceLimit) {
        this.otherFinanceLimit = otherFinanceLimit;
    }

    public BigDecimal getFixedIncomeBalance() {
        return fixedIncomeBalance;
    }

    public void setFixedIncomeBalance(BigDecimal fixedIncomeBalance) {
        this.fixedIncomeBalance = fixedIncomeBalance;
    }

    public BigDecimal getListedBalance() {
        return listedBalance;
    }

    public void setListedBalance(BigDecimal listedBalance) {
        this.listedBalance = listedBalance;
    }

    public BigDecimal getInsuranceManagementBalance() {
        return insuranceManagementBalance;
    }

    public void setInsuranceManagementBalance(BigDecimal insuranceManagementBalance) {
        this.insuranceManagementBalance = insuranceManagementBalance;
    }

    public BigDecimal getBankFinancialBalance() {
        return bankFinancialBalance;
    }

    public void setBankFinancialBalance(BigDecimal bankFinancialBalance) {
        this.bankFinancialBalance = bankFinancialBalance;
    }

    public BigDecimal getBankNegotiableSecuritiesBalance() {
        return bankNegotiableSecuritiesBalance;
    }

    public void setBankNegotiableSecuritiesBalance(BigDecimal bankNegotiableSecuritiesBalance) {
        this.bankNegotiableSecuritiesBalance = bankNegotiableSecuritiesBalance;
    }

    public BigDecimal getTrustSchemeBalance() {
        return trustSchemeBalance;
    }

    public void setTrustSchemeBalance(BigDecimal trustSchemeBalance) {
        this.trustSchemeBalance = trustSchemeBalance;
    }

    public BigDecimal getNegotiableSecuritiesSpecialPlanBalance() {
        return negotiableSecuritiesSpecialPlanBalance;
    }

    public void setNegotiableSecuritiesSpecialPlanBalance(BigDecimal negotiableSecuritiesSpecialPlanBalance) {
        this.negotiableSecuritiesSpecialPlanBalance = negotiableSecuritiesSpecialPlanBalance;
    }

    public BigDecimal getInsuranceFundsSupportPlanBalance() {
        return insuranceFundsSupportPlanBalance;
    }

    public void setInsuranceFundsSupportPlanBalance(BigDecimal insuranceFundsSupportPlanBalance) {
        this.insuranceFundsSupportPlanBalance = insuranceFundsSupportPlanBalance;
    }

    public BigDecimal getOtherInvestmentBalance() {
        return otherInvestmentBalance;
    }

    public void setOtherInvestmentBalance(BigDecimal otherInvestmentBalance) {
        this.otherInvestmentBalance = otherInvestmentBalance;
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof MsgDeploy == false) return false;
        if (this == obj) return true;
        MsgDeploy other = (MsgDeploy) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }

    public void setInvestmentBalanceTimeString(String value) {
        if (value != null) {
            setInvestmentBalanceTime(DateUtils.parseDate(value));
        }
    }

    public String getInvestmentBalanceTimeString() {
        if (Optional.ofNullable(investmentBalanceTime).isPresent()) {
            return DateUtils.formatDate(investmentBalanceTime);
        }
        return "";
    }

    private int page;

    private int pageSize;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private String startInvestmentBalanceTime;

    private String endInvestmentBalanceTime;

    public String getStartInvestmentBalanceTime() {
        return startInvestmentBalanceTime;
    }

    public void setStartInvestmentBalanceTime(String startInvestmentBalanceTime) {
        this.startInvestmentBalanceTime = startInvestmentBalanceTime;
    }

    public String getEndInvestmentBalanceTime() {
        return endInvestmentBalanceTime;
    }

    public void setEndInvestmentBalanceTime(String endInvestmentBalanceTime) {
        this.endInvestmentBalanceTime = endInvestmentBalanceTime;
    }
    private String years;
    private String quarter;

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public BigDecimal getFinancialProducts() {
        return financialProducts;
    }

    public void setFinancialProducts(BigDecimal financialProducts) {
        this.financialProducts = financialProducts;
    }
}
