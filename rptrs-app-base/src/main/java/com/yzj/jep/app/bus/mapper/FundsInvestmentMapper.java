package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.FundsInvestment;
import com.yzj.jep.app.bus.vo.FundsInvestmentVo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisMapper
public interface FundsInvestmentMapper extends Mapper<FundsInvestment, Integer> {
    FundsInvestment queryFundsInvestment();
    public List<FundsInvestment> queryYearFunds(@Param("year") String year);
    public FundsInvestmentVo checkBalance();

    FundsInvestment selectFundsInvestment(@Param("quarterBeginDate") String quarterBeginDate,@Param("quarterEndDate")  String quarterEndDate);
}
