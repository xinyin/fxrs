/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:16:10
 *
 */

package com.yzj.jep.app.ins.mapper;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
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
public interface InsTraServiceMapper extends Mapper<TraService, Integer> {
    BigDecimal getCurrYearTotalTradeAmount(Map map);

    BigDecimal getSumTradeAmount(RelOrg relOrg);

    //@Select("SELECT * from ins_tra_service WHERE YEAR(TRAN_DATE)=#{year} and QUARTER(TRAN_DATE)=#{quarter} and Tra_sub_Name=#{subOrg}  and DELFLAG_=1")
    List<TraService> selectByYearAndQuarter(@Param("year") String year, @Param("quarter") String quarter, @Param("subOrg") String subOrg);
    List<TraService> selectHisByYearAndQuarter(@Param("year") String year, @Param("quarter") String quarter, @Param("subOrg") String subOrg);

    BigDecimal getInRoadTotalTradeAmount(Map map);

    /**
     * 对累计关联交易金额进行归零处理
     *
     * @param map
     */
    void grandTradeZero(Map map);
    BigDecimal getAllSumTradeAmount();
    List<QuarterListVo> getService(Map map);

    List<TraService> queryByConditions(@Param("param") Map<String, Object> param);

    TraService traServiceNo(@Param("contractNo")String contractNo, @Param("relNumber") String relNumber);

    List<TraService> traServiceList(@Param("contractNo")String contractNo);

    Boolean updateByTradeAmount(@Param("pid")Integer pid, @Param("contractDate")Date contractDate, @Param("relTrandeAmount")String relTrandeAmount);

    TraService traServiceByAmount(@Param("contractNo")String contractNo,@Param("contractAmount") String contractAmount);
    List<TraService> getInit(@Param("contractNo")String contractNo,@Param("relNumber")String relNumber,@Param("relName")String relName,@Param("busType")String busType,@Param("status")String status,@Param("considerType")String considerType,@Param("majorTrade")String majorTrade,@Param("startTranDate")String startTranDate,@Param("endTranDate")String endTranDate,@Param("createTimeString")String createTimeString,@Param("deptCode")String deptCode,@Param("dataSources")String dataSources);

    List<TraService> getTranNum(@Param("transNum") String transNum);
}
