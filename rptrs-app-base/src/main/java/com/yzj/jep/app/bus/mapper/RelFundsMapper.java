package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@MyBatisMapper
public interface RelFundsMapper extends Mapper<RelFunds, Integer> {
    BigDecimal sumRelTraBalance();

    BigDecimal sumAmountByRelId(@Param("relId") String relId,@Param("relType") String relType);
    public List<RelFunds> queryYearFunds(@Param("year") String year);

    List<RelFunds> checkExistRelated(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("relNumber") String relNumber);

    RelFunds selectRelFunds(@Param("quarterBeginDate") String quarterBeginDate, @Param("quarterEndDate") String quarterEndDate);
}
