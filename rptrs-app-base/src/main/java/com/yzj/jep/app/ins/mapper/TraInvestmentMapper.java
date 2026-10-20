/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:52
 *
 */

package com.yzj.jep.app.ins.mapper;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.model.QuarterlyVo;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface TraInvestmentMapper extends Mapper<TraInvestment, Integer> {
    BigDecimal getCurrYearTotalTradeAmount(Map map);

    BigDecimal getSumTradeAmount(RelOrg relOrg);

    //@Select("SELECT * from ins_tra_investment WHERE YEAR(TRAN_DATE)=#{year} and QUARTER(TRAN_DATE)=#{quarter} and Tra_sub_Name=#{subOrg}  and DELFLAG_=1")
    List<TraInvestment> selectByYearAndQuarter(@Param("year") String year, @Param("quarter") String quarter, @Param("subOrg") String subOrg);
    List<TraInvestment> selectHisByYearAndQuarter(@Param("year") String year, @Param("quarter") String quarter, @Param("subOrg") String subOrg);

    BigDecimal getInRoadTotalTradeAmount(Map map);

    /**
     * 对累计关联交易金额进行归零处理
     *
     * @param map
     */
    void grandTradeZero(Map map);

    BigDecimal getAllSumTradeAmount();
    List<QuarterListVo> getInvestment(Map map);

    List<TraInvestment> queryByConditions(@Param("param") Map<String, Object> param);

    TraInvestment traContractNo(@Param("contractNo") String contractNo);

    List<TraInvestment> traContractList(@Param("contractNo") String contractNo);

    Boolean updateByTradeAmount(@Param("pid")Integer pid, @Param("contractDate") Date contractDate, @Param("relTrandeAmount")String relTrandeAmount);

    List<TraInvestment> getByInt(@Param("param") Map<String, Object> param);

}