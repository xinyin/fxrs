package com.yzj.jep.app.pub.service;

import com.yzj.jep.app.pub.model.PublogRegularVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PublogYearReportService {
    public List<PublogRegularVo> getYearReportList(Map<String,String> map);
    public List<PublogRegularVo> getGroupByTranTypeAndRelNameAndBusType(String tranType,Map<String,String> map);
    public List<PublogRegularVo> getGroupByRelName(Map<String,String> map);
    public List<PublogRegularVo> getOrderByPracTradeAmount(String year);
    public List<PublogRegularVo> getTranType(String year);
    public List<PublogRegularVo> getQuarterReportList(Map<String,String> map);
}
