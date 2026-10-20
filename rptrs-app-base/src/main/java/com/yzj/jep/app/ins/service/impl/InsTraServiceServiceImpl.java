/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:16:10
 *
 */

package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.mapper.InsTraServiceMapper;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InsTraServiceServiceImpl extends MyBatisCRUDService<TraService, Integer> implements TraServiceService {

    Logger logger = LoggerFactory.getLogger(InsTraServiceServiceImpl.class);

    @Autowired
    private InsTraServiceMapper insTraServiceMapper;

    @Override
    public Mapper<TraService, Integer> getMapper() {
        return insTraServiceMapper;
    }

    @Override
    public BigDecimal getCurrYearTotalTradeAmount(Map map) {
        return insTraServiceMapper.getCurrYearTotalTradeAmount(map);
    }

    @Override
    public Map getSumByTradeType(Map tradeType) {
        return null;
    }

    @Override
    public void approveReturn(Integer pid, String memo, String backFlag) {
        TraService traService = new TraService();
        traService.setPid(pid);
        if(StrUtil.isNotBlank(backFlag)){
            traService.setStatus("2");
        }else {
            traService.setStatus("5");
        }
        traService.setStatusComment(memo);
        this.updateSelective(traService);
    }

    @Override
    public BigDecimal getSumTradeAmount(RelOrg relOrg) {
        return insTraServiceMapper.getSumTradeAmount(relOrg);
    }

    @Override
    public List<TraService> selectByYearAndQuarter(String subYear, String subQuarter, String subOrg) {
        return insTraServiceMapper.selectByYearAndQuarter(subYear,subQuarter,subOrg);
    }
    @Override
    public List<TraService> selectHisByYearAndQuarter(String subYear, String subQuarter, String subOrg) {
        return insTraServiceMapper.selectHisByYearAndQuarter(subYear,subQuarter,subOrg);
    }

    @Override
    public BigDecimal getInRoadTotalTradeAmount(Map map) {
        return insTraServiceMapper.getInRoadTotalTradeAmount(map);
    }

    @Override
    public void grandTradeZero(Map map) {
        insTraServiceMapper.grandTradeZero(map);
    }

    @Override
    public BigDecimal getAllSumTradeAmount() {
        return insTraServiceMapper.getAllSumTradeAmount();
    }

    @Override
    public List<QuarterListVo> getService(Map<String, Object> map) {
        return insTraServiceMapper.getService(map);
    }

    @Override
    public PageInfo<TraService> queryBatchSubmit(Integer pageNumber, Integer pageSize, String year, String quarter) {
        PageHelper.startPage(pageNumber,pageSize);
        //构造查询条件
        Map<String,Object> param = new HashMap<>();
        if(StrUtil.isNotBlank(year)){
            param.put("year",year.concat("%"));
        }else {
            Integer yearInt = DateUtil.year(new Date());
            param.put("year",String.valueOf(yearInt).concat("%"));
        }
        if(StrUtil.isNotBlank(quarter)){
            String begin=null;
            String center=null;
            String end=null;
            if("1".equals(quarter)){
                begin = "%".concat("-01-").concat("%");
                center = "%".concat("-02-").concat("%");
                end = "%".concat("-03-").concat("%");
            }else if("2".equals(quarter)){
                begin = "%".concat("-04-").concat("%");
                center = "%".concat("-05-").concat("%");
                end = "%".concat("-06-").concat("%");
            }else if("3".equals(quarter)){
                begin = "%".concat("-07-").concat("%");
                center = "%".concat("-08-").concat("%");
                end = "%".concat("-09-").concat("%");
            }else {
                begin = "%".concat("-10-").concat("%");
                center = "%".concat("-11-").concat("%");
                end = "%".concat("-12-").concat("%");
            }
            param.put("begin",begin);
            param.put("center",center);
            param.put("end",end);
        }
        List<TraService> list= insTraServiceMapper.queryByConditions(param);
        return new PageInfo<>(list);
    }

    @Override
    public TraService traServiceNo(String contractNo, String relNumber) {
        return insTraServiceMapper.traServiceNo(contractNo, relNumber);
    }

    @Override
    public List<TraService> traServiceList(String contractNo) {
        return insTraServiceMapper.traServiceList(contractNo);
    }

    @Override
    public Boolean updateByTradeAmount(Integer pid, Date contractDate, String relTrandeAmount) {
        return insTraServiceMapper.updateByTradeAmount(pid,contractDate,relTrandeAmount);
    }

    @Override
    public TraService traServiceByAmount(String contractNo,String contractAmount) {
        return insTraServiceMapper.traServiceByAmount(contractNo, contractAmount);
    }
    @Override
    public List<TraService> getInit(String contractNo, String relNumber, String relName, String busType, String status, String considerType, String majorTrade, String startTranDate, String endTranDate,  String createTimeString ,String deptCode, String dataSources) {
        List<TraService> list = insTraServiceMapper.getInit(contractNo, relNumber, relName, busType, status,considerType, majorTrade,startTranDate,endTranDate,createTimeString,deptCode,dataSources);
        return list;
    }

    @Override
    public List<TraService> getTranNum(String transNum) {
        return insTraServiceMapper.getTranNum(transNum);
    }

}
