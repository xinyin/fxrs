/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:15:38
 *
 */

package com.yzj.jep.app.ins.mapper;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.model.InsTrans;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface InsTraBusinessMapper extends Mapper<TraBusiness, Integer> {
    BigDecimal getCurrYearTotalTradeAmount(Map map);

    List<InsTrans> selectBusiness(@Param(value = "pid") String pid);

    BigDecimal getSumTradeAmount(RelOrg relOrg);

//    @Select("SELECT * from ins_tra_business WHERE YEAR(TRAN_DATE)=#{year} and QUARTER(TRAN_DATE)=#{quarter} and Tra_sub_Name=#{subOrg}  and DELFLAG_=1")
//    List<TraBusiness>  selectByYearAndQuarter(@Param("year") String year,@Param("quarter") String quarter,@Param("subOrg") String subOrg);


    List<TraBusiness>  selectByYearAndQuarter(@Param("year") String year,@Param("quarter") String quarter,@Param("subOrg") String subOrg);
    List<TraBusiness>  selectHisByYearAndQuarter(@Param("year") String year,@Param("quarter") String quarter,@Param("subOrg") String subOrg);

    BigDecimal getInRoadTotalTradeAmount(Map map);

    /**
     * 对累计关联交易金额进行归零处理
     *
     * @param map
     */
    void grandTradeZero(Map map);

    BigDecimal getAllSumTradeAmount();

    List<QuarterListVo> getBusiness(Map map);

    List<TraBusiness> queryByConditions(@Param("param") Map<String, Object> param);

    Boolean updateByTradeAmount(@Param("pid")Integer pid, @Param("contractDate") Date contractDate, @Param("relTrandeAmount")String relTrandeAmount);

   TraBusiness traBusinessNo(@Param("contractNo") String contractNo,@Param("relNumber") String relNumber);

   List<TraBusiness> traBusinessList(@Param("contractNo")String contractNo);

    TraBusiness traBusinessByAmount(@Param("contractNo")String contractNo);


    List<TraBusiness> getInit(@Param("contractNo")String contractNo, @Param("relNumber")String relNumber, @Param("relName")String relName, @Param("busType")String busType, @Param("status")String status, @Param("considerType")String considerType, @Param("majorTrade")String majorTrade, @Param("startTranDate")String startTranDate, @Param("endTranDate")String endTranDate, @Param("createTimeString")String createTimeString, @Param("deptCode")String deptCode, @Param("dataSources")String dataSources);

    List<TraBusiness> getTranNum(String transNum);
}