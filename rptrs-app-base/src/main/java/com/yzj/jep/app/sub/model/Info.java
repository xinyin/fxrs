package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;

import java.math.BigDecimal;

public class Info extends BasePO {
    private BigDecimal net_captial;

    public Info() {
    }

    public Info(BigDecimal net_captial) {
        this.net_captial = net_captial;
    }

    public BigDecimal getNet_captial() {
        return net_captial;
    }

    public void setNet_captial(BigDecimal net_captial) {
        this.net_captial = net_captial;
    }

    @Override
    public String toString() {
        return "captial{" +
                "net_captial=" + net_captial +
                '}';
    }
}
