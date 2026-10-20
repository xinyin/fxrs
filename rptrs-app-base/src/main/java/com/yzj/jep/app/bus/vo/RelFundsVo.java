package com.yzj.jep.app.bus.vo;

import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.commons.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class RelFundsVo extends RelFunds {
    private Integer pid;  //主键

    private Date investmentBalanceTime; //投资余额统计时间

    private String relType; //关联方类型

    private String relIdentifyNumber; //关联方证件号

    private String relIdentifyType; //关联方证件类型


    private String relName; //关联方名称
    private BigDecimal fundsInvestmentBalance; //资金运用投资余额
    private BigDecimal relTotalAssets; //关联方总资产
    //占总资产比例(为该法人投资余额占中邮保险上年末总资产的比例。计算公式为：该法人的投资余额/中邮上年末总资产*100%。)
    private BigDecimal allInvestProForTotal;
    //占法人主体总资产比例(该法人的投资余额/该法人上年末总资产*100%)
    private BigDecimal allInvestProForOrgTotal;

    //序号
    private String no;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public BigDecimal getAllInvestProForTotal() {
        return allInvestProForTotal;
    }

    public void setAllInvestProForTotal(BigDecimal allInvestProForTotal) {
        this.allInvestProForTotal = allInvestProForTotal;
    }

    public BigDecimal getAllInvestProForOrgTotal() {
        return allInvestProForOrgTotal;
    }

    public void setAllInvestProForOrgTotal(BigDecimal allInvestProForOrgTotal) {
        this.allInvestProForOrgTotal = allInvestProForOrgTotal;
    }

    private String status; private String startInvestmentBalanceTime;

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
    public Date getInvestmentBalanceTime() {
        return investmentBalanceTime;
    }

    @Override
    public void setInvestmentBalanceTime(Date investmentBalanceTime) {
        this.investmentBalanceTime = investmentBalanceTime;
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


}
