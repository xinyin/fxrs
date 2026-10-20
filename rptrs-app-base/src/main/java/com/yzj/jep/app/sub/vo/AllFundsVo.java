package com.yzj.jep.app.sub.vo;


import com.yzj.jep.app.sub.model.InsAllfunds;

import java.math.BigDecimal;

public class AllFundsVo extends InsAllfunds {
    private BigDecimal AllOtherFunds;

    public BigDecimal getAllOtherFunds() {
        return AllOtherFunds;
    }

    public void setAllOtherFunds(BigDecimal allOtherFunds) {
        AllOtherFunds = allOtherFunds;
    }
}
