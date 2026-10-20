package com.yzj.jep.app.sub.vo;

import java.math.BigDecimal;

public class captial {
    private BigDecimal net_captial;

    public captial() {
    }

    public captial(BigDecimal net_captial) {
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
