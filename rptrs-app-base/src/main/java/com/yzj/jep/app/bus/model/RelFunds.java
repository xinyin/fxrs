package com.yzj.jep.app.bus.model;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name="bus_rel_funds")
public class RelFunds extends BasePO {
    @Id
    @Column(name = "PID", nullable = false, length = 10)
    private Integer pid;  //主键

    @Column(name = "investment_balance_time", nullable = false, length = 19)
    @Temporal(TemporalType.DATE)
    private Date investmentBalanceTime; //投资余额统计时间

    @Column(name="rel_name" , nullable = true )
    private String relName; //关联方名称

    @Column(name="rel_type" , nullable = true )
    private String relType; //关联方类型

    @Column(name="rel_identify_number" , nullable = true)
    private String relIdentifyNumber; //关联方证件号

    @Column(name="rel_identify_type" , nullable = true)
    private String relIdentifyType; //关联方证件类型

    @Column(name="funds_investment_balance" , nullable = true , precision = 50, scale = 6)
    private BigDecimal fundsInvestmentBalance; //资金运用投资余额

    @Column(name="rel_total_assets" , nullable = true , precision = 50, scale = 6)
    private BigDecimal relTotalAssets; //关联方总资产

    @Column(name="status" , nullable = true)
    private String status;

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

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getRelIdentifyNumber() {
        return relIdentifyNumber;
    }

    public void setRelIdentifyNumber(String relIdentifyNumber) {
        this.relIdentifyNumber = relIdentifyNumber;
    }

    public String getRelIdentifyType() {
        return relIdentifyType;
    }

    public void setRelIdentifyType(String relIdentifyType) {
        this.relIdentifyType = relIdentifyType;
    }

    public BigDecimal getFundsInvestmentBalance() {
        return fundsInvestmentBalance;
    }

    public void setFundsInvestmentBalance(BigDecimal fundsInvestmentBalance) {
        this.fundsInvestmentBalance = fundsInvestmentBalance;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public BigDecimal getRelTotalAssets() {
        return relTotalAssets;
    }

    public void setRelTotalAssets( BigDecimal relTotalAssets) {
        this.relTotalAssets = relTotalAssets;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
