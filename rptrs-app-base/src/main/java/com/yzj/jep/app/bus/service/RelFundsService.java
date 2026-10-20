package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.app.bus.vo.RelFundsVo;
import com.yzj.jep.orm.service.CRUDService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface RelFundsService extends CRUDService<RelFunds, Integer> {
    public RelFundsVo queryList(String investmentBalanceTime, String quarter);

    BigDecimal sumRelTraBalance();

    BigDecimal sumAmountByRelId(String relId,String relType);
    public List<RelFunds> queryYearFunds(String year);

    abstract boolean checkExistRelated(String startTime, String endTime, String relNumber);

    RelFunds selectRelFunds(String quarterBeginDate, String quarterEndDate);
}
