package com.yzj.jep.app.bus.vo;

import com.yzj.jep.app.bus.model.FundsInvestment;
import com.yzj.jep.commons.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

//保险公司资金运用关联交易季度统计表
public class FundsInvestmentStaVo extends FundsInvestment {

    private BigDecimal totalCapital;//总资产(上年末)
    private Long netCaptial;//净资产(上年末)
    private BigDecimal unlistedPro;//未上市权益类资产占总资产比例
    private BigDecimal realEstatePro;//不动产类资产占总资产比例
    private BigDecimal otherTotal;//其他金融资产-合计
    private BigDecimal otherTotalPro;//其他金融资产占总资产比例
    private BigDecimal foreignPro;//境外投资占总资产比例
    private BigDecimal allBalance;//全部投资余额（含活期存款）
    private BigDecimal allInvestProForTotal;//全部投资余额占总资产比例
    private BigDecimal allInvestProForNetAsset;//全部投资余额占净资产比例
    private BigDecimal allInvestmentBalance;
    private BigDecimal mobilityBalance;
    private Date investmentBalanceTime; //投资余额统计时间

    //序号
    private String no;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @Override
    public Date getInvestmentBalanceTime() {
        return investmentBalanceTime;
    }

    @Override
    public void setInvestmentBalanceTime(Date investmentBalanceTime) {
        this.investmentBalanceTime = investmentBalanceTime;
    }

    public BigDecimal getTotalCapital() {
        return totalCapital;
    }

    public void setTotalCapital(BigDecimal totalCapital) {
        this.totalCapital = totalCapital;
    }

    public Long getNetCaptial() {
        return netCaptial;
    }

    public void setNetCaptial(Long netCaptial) {
        this.netCaptial = netCaptial;
    }

    public BigDecimal getUnlistedPro() {
        return unlistedPro;
    }

    public void setUnlistedPro(BigDecimal unlistedPro) {
        this.unlistedPro = unlistedPro;
    }

    public BigDecimal getRealEstatePro() {
        return realEstatePro;
    }

    public void setRealEstatePro(BigDecimal realEstatePro) {
        this.realEstatePro = realEstatePro;
    }

    public BigDecimal getOtherTotal() {
        return otherTotal;
    }

    public void setOtherTotal(BigDecimal otherTotal) {
        this.otherTotal = otherTotal;
    }

    public BigDecimal getOtherTotalPro() {
        return otherTotalPro;
    }

    public void setOtherTotalPro(BigDecimal otherTotalPro) {
        this.otherTotalPro = otherTotalPro;
    }

    public BigDecimal getForeignPro() {
        return foreignPro;
    }

    public void setForeignPro(BigDecimal foreignPro) {
        this.foreignPro = foreignPro;
    }

    public BigDecimal getAllBalance() {
        return allBalance;
    }

    public void setAllBalance(BigDecimal allBalance) {
        this.allBalance = allBalance;
    }

    public BigDecimal getAllInvestProForTotal() {
        return allInvestProForTotal;
    }

    public void setAllInvestProForTotal(BigDecimal allInvestProForTotal) {
        this.allInvestProForTotal = allInvestProForTotal;
    }

    public BigDecimal getAllInvestProForNetAsset() {
        return allInvestProForNetAsset;
    }

    public void setAllInvestProForNetAsset(BigDecimal allInvestProForNetAsset) {
        this.allInvestProForNetAsset = allInvestProForNetAsset;
    }

    @Override
    public void setInvestmentBalanceTimeString(String value) {
        if (value != null) {
            setInvestmentBalanceTime(DateUtils.parseDate(value));
        }
    }

    @Override
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

    @Override
    public BigDecimal getAllInvestmentBalance() {
        return allInvestmentBalance;
    }

    @Override
    public void setAllInvestmentBalance(BigDecimal allInvestmentBalance) {
        this.allInvestmentBalance = allInvestmentBalance;
    }

    @Override
    public BigDecimal getMobilityBalance() {
        return mobilityBalance;
    }

    @Override
    public void setMobilityBalance(BigDecimal mobilityBalance) {
        this.mobilityBalance = mobilityBalance;
    }
}
