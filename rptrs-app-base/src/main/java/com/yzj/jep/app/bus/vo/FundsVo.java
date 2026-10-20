package com.yzj.jep.app.bus.vo;

import java.math.BigDecimal;

public class FundsVo {
    //投资余额
    private BigDecimal balance;
    //投资限额
    private BigDecimal limit;
    //名称
    private String relName;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public String getName() {
        return relName;
    }

    public void setName(String name) {
        this.relName = name;
    }
}
