package com.yzj.beans;

import java.math.BigDecimal;

public class Report_statistical {
    //保险业务类
    private BigDecimal businessCount;
    //资金运用类
    private BigDecimal fundsCount;
    //利益转移类
    private BigDecimal interestCount;
    //投资入股类
    private BigDecimal investmentCount;
    //提供服务类
    private BigDecimal serviceCount;
    //其他类
    private BigDecimal otherCount;

    public BigDecimal getBusinessCount() {
        return businessCount;
    }

    public void setBusinessCount(BigDecimal businessCount) {
        this.businessCount = businessCount;
    }

    public BigDecimal getFundsCount() {
        return fundsCount;
    }

    public void setFundsCount(BigDecimal fundsCount) {
        this.fundsCount = fundsCount;
    }

    public BigDecimal getInterestCount() {
        return interestCount;
    }

    public void setInterestCount(BigDecimal interestCount) {
        this.interestCount = interestCount;
    }

    public BigDecimal getInvestmentCount() {
        return investmentCount;
    }

    public void setInvestmentCount(BigDecimal investmentCount) {
        this.investmentCount = investmentCount;
    }

    public BigDecimal getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(BigDecimal serviceCount) {
        this.serviceCount = serviceCount;
    }

    public BigDecimal getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(BigDecimal otherCount) {
        this.otherCount = otherCount;
    }
}
