package com.yzj.jep.app.pub.service.impl;

import com.yzj.jep.app.pub.mapper.PublogYearReportMapper;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.PublogYearReportService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PublogYearReportServiceImpl  extends MyBatisCRUDService<PublogRegularVo, Integer> implements PublogYearReportService {
    @Autowired
    private PublogYearReportMapper publogYearReportMapper;

    @Override
    public Mapper<PublogRegularVo, Integer> getMapper() {
        return publogYearReportMapper;
    }

    public List<PublogRegularVo> getYearReportList(Map<String,String> map){
        return publogYearReportMapper.getYearReportList(map.get("startTime"),map.get("endTime"));
    }
    public List<PublogRegularVo> getGroupByTranTypeAndRelNameAndBusType(String tranType,Map<String,String>map){
        return  publogYearReportMapper.getGroupByTranTypeAndRelNameAndBusType(tranType,map.get("startTime"),map.get("endTime"));
    }
    public List<PublogRegularVo> getGroupByRelName(Map<String,String>map){
        return  publogYearReportMapper.getGroupByRelName(map.get("startTime"),map.get("endTime"));
    }
    public List<PublogRegularVo> getOrderByPracTradeAmount(String year){
        return  publogYearReportMapper.getOrderByPracTradeAmount(year);
    }
    public List<PublogRegularVo> getTranType(String year){
        return  publogYearReportMapper.getTranType(year);
    }

    public List<PublogRegularVo> getQuarterReportList(Map<String,String> map){
        return publogYearReportMapper.getQuarterReportList(map.get("startTime"),map.get("endTime"));
    }
}
