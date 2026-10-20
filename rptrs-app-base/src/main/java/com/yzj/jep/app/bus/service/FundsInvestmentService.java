package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.FundsInvestment;
import com.yzj.jep.app.bus.vo.FundsInvestmentStaVo;
import com.yzj.jep.app.bus.vo.FundsInvestmentVo;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface FundsInvestmentService extends CRUDService<FundsInvestment, Integer> {
    public FundsInvestmentVo queryList(String quarter, String investmentBalanceTime);
    public FundsInvestmentStaVo queryStaList(String quarter, String investmentBalanceTime);
    public List<FundsInvestment> queryYearFunds(String year);
    FundsInvestment queryFundsInvestment();
    public FundsInvestmentVo checkBalance();

    FundsInvestment selectFundsInvestment(String quarterBeginDate, String quarterEndDate);
}
