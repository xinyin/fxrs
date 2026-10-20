package com.yzj.jep.app.pub.mapper;

import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisMapper
public interface PublogYearReportMapper extends Mapper<PublogRegularVo, Integer> {
    public List<PublogRegularVo> getYearReportList(@Param("startTime") String startTime,@Param("endTime") String endTime);
    public List<PublogRegularVo> getGroupByTranTypeAndRelNameAndBusType(@Param("tranType") String tranType,@Param("startTime") String startTime,@Param("endTime") String endTime);
    public List<PublogRegularVo> getGroupByRelName(@Param("startTime") String startTime,@Param("endTime") String endTime);
    public List<PublogRegularVo> getOrderByPracTradeAmount(@Param("year") String year);
    public List<PublogRegularVo> getTranType(@Param("year") String year);
    public List<PublogRegularVo> getQuarterReportList(@Param("startTime") String startTime,@Param("endTime") String endTime);
}
