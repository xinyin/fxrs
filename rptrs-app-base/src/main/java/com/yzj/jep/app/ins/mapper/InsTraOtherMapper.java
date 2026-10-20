/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月08日 19:41:07
 *
 */

package com.yzj.jep.app.ins.mapper;


import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.model.TraOther;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


@MyBatisMapper
public interface InsTraOtherMapper extends Mapper<TraOther, Integer>{

    BigDecimal getCurrYearTotalTradeAmount(Map map);


    //@Select("SELECT * from ins_tra_other WHERE YEAR(TRAN_DATE)=#{year} and QUARTER(TRAN_DATE)=#{quarter} and Tra_sub_Name=#{subOrg}  and DELFLAG_=1")
    List<TraOther> selectByYearAndQuarter(@Param("year") String year, @Param("quarter") String quarter, @Param("subOrg") String subOrg);
    List<TraOther> selectHisByYearAndQuarter(@Param("year") String year, @Param("quarter") String quarter, @Param("subOrg") String subOrg);

    BigDecimal getInRoadTotalTradeAmount(Map map);

    /**
     * 对累计关联交易金额进行归零处理
     *
     * @param map
     */
    void grandTradeZero(Map map);
    BigDecimal getAllSumTradeAmount();


    List<QuarterListVo> getOther(Map map);

    List<TraOther> queryByConditions(@Param("param") Map<String, Object> param);

    Boolean updateByTradeAmount(@Param("pid")Integer pid, @Param("contractDate") Date contractDate, @Param("relTrandeAmount")String relTrandeAmount);
    TraOther traOtherNo(String contractNo);
    List<TraOther> traOtherList(@Param("contractNo")String contractNo);

}