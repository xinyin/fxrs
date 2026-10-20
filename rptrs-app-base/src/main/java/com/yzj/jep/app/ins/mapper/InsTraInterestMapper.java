/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:15:56
 *
 */

package com.yzj.jep.app.ins.mapper;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface InsTraInterestMapper extends Mapper<TraInterest, Integer> {
    BigDecimal getCurrYearTotalTradeAmount(Map map);

    BigDecimal getSumTradeAmount(RelOrg relOrg);


   // @Select("SELECT * from ins_tra_interest WHERE YEAR(TRAN_DATE)=#{year} and QUARTER(TRAN_DATE)=#{quarter} and Tra_sub_Name=#{subOrg}  and DELFLAG_=1")
    List<TraInterest> selectByYearAndQuarter(@Param("year") String year, @Param("quarter") String quarter, @Param("subOrg") String subOrg);
    List<TraInterest> selectHisByYearAndQuarter(@Param("year") String year, @Param("quarter") String quarter, @Param("subOrg") String subOrg);

    BigDecimal getInRoadTotalTradeAmount(Map map);

    /**
     * 对累计关联交易金额进行归零处理
     *
     * @param map
     */
    void grandTradeZero(Map map);

    BigDecimal getAllSumTradeAmount();

    List<QuarterListVo> getInterest(Map map);

    List<TraInterest> queryByConditions(@Param("param") Map<String, Object> param);


    TraInterest traTraInterestNo(@Param("contractNo") String contractNo, @Param("relNumber") String relNumber);

    List<TraInterest> traTraInterestList(@Param("contractNo")String contractNo);

    Boolean updateByTradeAmount(@Param("pid")Integer pid, @Param("contractDate") Date contractDate, @Param("relTrandeAmount")String relTrandeAmount);

    TraInterest traTraInteresByAmount(@Param("contractNo")String contractNo);

   TraInterest insTraInteresByAmount(@Param("contractNo")String contractNo, @Param("contractAmount")String contractAmount);
    List<TraInterest> getInit(@Param("contractNo")String contractNo,@Param("relNumber")String relNumber,@Param("relName")String relName,@Param("busType")String busType,@Param("status")String status,@Param("considerType")String considerType,@Param("majorTrade")String majorTrade,@Param("startTranDate")String startTranDate,@Param("endTranDate")String endTranDate,@Param("createTimeString")String createTimeString,@Param("deptCode")String deptCode,@Param("dataSources")String dataSources);

    List<TraInterest> getTranNum(String transNum);
}
