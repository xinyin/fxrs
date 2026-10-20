package com.yzj.jep.app.ins.model;

import java.math.BigDecimal;

public class QuarterlyVo {




    private String tranType;

    private int  number;

    private BigDecimal amount;

    @Override
    public String toString() {
        return "QuarterlyVo{" +
                "tranType='" + tranType + '\'' +
                ", number=" + number +
                ", amount=" + amount +
                ", proprotion='" + proprotion + '\'' +
                '}';
    }

    private  String proprotion;

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getProprotion() {
        return proprotion;
    }

    public void setProprotion(String proprotion) {
        this.proprotion = proprotion;
    }


}
