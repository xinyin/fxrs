/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:50
 *
 */

package com.yzj.jep.app.ins.mapper;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface TraFundsMapper extends Mapper<TraFunds, Integer> {
    BigDecimal getCurrYearTotalTradeAmount(Map map);

    Map getSumByTradeType(Map map);

    BigDecimal getSumTradeAmount(RelOrg relOrg);

    /**
     * 获取资金运用类交易的限额的金额
     * @param map
     * @return
     */
    BigDecimal getLimitTotalTradeAmount(Map map);


    //@Select("SELECT * from ins_tra_funds WHERE YEAR(TRAN_DATE)=#{year} and QUARTER(TRAN_DATE)=#{quarter} and Tra_sub_Name=#{subOrg}  and DELFLAG_=1")
    List<TraFunds> selectByYearAndQuarter(@Param("year") String year, @Param("quarter") String quarter, @Param("subOrg") String subOrg);
    List<TraFunds> selectHisByYearAndQuarter(@Param("year") String year, @Param("quarter") String quarter, @Param("subOrg") String subOrg);

    BigDecimal getInRoadTotalTradeAmount(Map map);

    /**
     * 对累计关联交易金额进行归零处理
     *
     * @param map
     */
    void grandTradeZero(Map map);

    BigDecimal getAllSumTradeAmount();

    List<QuarterListVo> getFundsService(Map map);

    List<TraFunds> queryByConditions(@Param("param") Map<String, Object> param);

    BigDecimal sumTradeAmountByProNum(String financialAssstsNum);

    Boolean updateByTradeAmount(@Param("pid")Integer pid, @Param("contractDate") Date contractDate, @Param("relTrandeAmount")String relTrandeAmount);

    TraFunds traServiceNo(@Param("contractNo") String contractNo, @Param("relNumber") String relNumber);

    List<TraFunds> traServiceList(@Param("contractNo")String contractNo);

    List<TraFunds> getInit(@Param("contractNo") String contractNo, @Param("relNumber") String relNumber, @Param("relName") String relName, @Param("busType") String busType,
                           @Param("startTranDate") String startTranDate, @Param("endTranDate") String endTranDate,
                           @Param("considerType") String considerType, @Param("majorTrade") String majorTrade,
                           @Param("status") String status, @Param("createTimeString") String createTimeString,
                           @Param("deptCode") String deptCode,@Param("dataSources") String dataSources);

    List<TraFunds> getTranNum(@Param("transNum") String transNum);

    List<TraFunds> getMaxRelatedTran(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<TraFunds> isServiceNo(@Param("contractNo") String contractNo);
}