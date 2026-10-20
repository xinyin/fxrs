package com.yzj.jep.app.bus.model;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "bus_funds_investment_balance")
public class FundsInvestment extends BasePO {

    @Id
    @Column(name = "PID", nullable = false, length = 10)
    private Integer pid;  //主键

    @Column(name = "investment_balance_time", nullable = false, length = 19)
    @Temporal(TemporalType.DATE)
    private Date investmentBalanceTime; //投资余额统计时间

    @Column(name = "real_estate_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal realEstateBalance; //不动资产投资余额

    @Column(name = "real_estate_limit", nullable = true, precision = 50, scale = 6)
    private BigDecimal realEstateLimit; //不动资产投资限额

    @Column(name = "unlisted_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal unlistedBalance; //未上市权益类资产投资余额

    @Column(name = "unlisted_limit", nullable = true, precision = 50, scale = 6)
    private BigDecimal unlistedLimit; //未上市权益类资产投资限额

    @Column(name = "foreign_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal foreignBalance;//境外投资余额

    @Column(name = "foreign_limit", nullable = true, precision = 50, scale = 6)
    private BigDecimal foreignLimit;//境外投资限额

    @Column(name = "other_finance_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal otherFinanceBalance;//其他类金融资产投资余额

    @Column(name = "other_finance_limit", nullable = true, precision = 50, scale = 6)
    private BigDecimal otherFinanceLimit;//其他类金融资产投资限额

    @Column(name = "fixed_income_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal fixedIncomeBalance;//固定收益类资产投资余额

    @Column(name = "listed_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal listedBalance;//上市权益类资产投资余额

    @Column(name = "insurance_management_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal insuranceManagementBalance;//保险资产管理产品投资余额

    @Column(name = "bank_financial_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal bankFinancialBalance;//商业银行理财产品投资余额

    @Column(name = "bank_negotiable_securities_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal bankNegotiableSecuritiesBalance;//银行业金融机构信贷资产支持证券投资余额

    @Column(name = "trust_scheme_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal trustSchemeBalance;//信托公司集合资金信托计划投资余额

    @Column(name = "negotiable_securities_special_plan_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal negotiableSecuritiesSpecialPlanBalance;//证券公司专项资产管理计划投资余额

    @Column(name = "insurance_funds_support_plan_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal insuranceFundsSupportPlanBalance;//保险资管公司发行资产支持计划投资余额

    @Column(name = "other_investment_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal otherInvestmentBalance;//其他投资余额

    @Column(name = "status", nullable = true, length = 8)
    private String status;//状态
    //全部投资余额
    @Column(name = "all_investment_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal allInvestmentBalance;
    //流动性资产投资余额
    @Column(name = "mobility_balance", nullable = true, precision = 50, scale = 6)
    private BigDecimal mobilityBalance;

    // 权益类资产投资余额
    @Column(name = "balance_investment", nullable = true, precision = 50, scale = 6)
    private BigDecimal balanceInvestment;
    // 权益类资产投资限额
    @Column(name = "balance_investment_limit", nullable = true, precision = 50, scale = 6)
    private BigDecimal balanceInvestmentLimit;
    // 金融产品投资限额
    @Column(name = "financial_products", nullable = true, precision = 50, scale = 6)
    private BigDecimal financialProducts;
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

    public BigDecimal getFinancialProducts() {
        return financialProducts;
    }

    public void setFinancialProducts(BigDecimal financialProducts) {
        this.financialProducts = financialProducts;
    }

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

//    public String getCREATED_BY_ID_() {
//        return CREATED_BY_ID_;
//    }
//
//    public void setCREATED_BY_ID_(String CREATED_BY_ID_) {
//        this.CREATED_BY_ID_ = CREATED_BY_ID_;
//    }

    @Override
    public String toString() {
        return "FundsInvestment{" +
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
                ", status='" + status + '\'' +
                ", allInvestmentBalance=" + allInvestmentBalance +
                ", mobilityBalance=" + mobilityBalance +
                ", balanceInvestment=" + balanceInvestment +
                '}';
    }


//    @Id
//    @Column(name = "CREATED_BY_ID_", nullable = false, length = 64)
//    private String CREATED_BY_ID_;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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




}
