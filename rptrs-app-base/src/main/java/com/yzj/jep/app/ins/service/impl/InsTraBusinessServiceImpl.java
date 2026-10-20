/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:15:38
 *
 */

package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.mapper.InsTraBusinessMapper;
import com.yzj.jep.app.ins.model.InsTrans;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class InsTraBusinessServiceImpl extends MyBatisCRUDService<TraBusiness, Integer> implements TraBusinessService {

    Logger logger = LoggerFactory.getLogger(InsTraBusinessServiceImpl.class);

    @Autowired
    private InsTraBusinessMapper insTraBusinessMapper;

    @Override
    public Mapper<TraBusiness, Integer> getMapper() {
        return insTraBusinessMapper;
    }

    @Override
    public BigDecimal getCurrYearTotalTradeAmount(Map map) {
        return insTraBusinessMapper.getCurrYearTotalTradeAmount(map);
    }

    @Override
    public List<InsTrans> selectBusiness(String pid) {
        return insTraBusinessMapper.selectBusiness(pid);
    }

    @Override
    public BigDecimal getSumTradeAmount(RelOrg relOrg) {
        return insTraBusinessMapper.getSumTradeAmount(relOrg);
    }

    @Override
    public List<TraBusiness> selectByYearAndQuarter(String year, String quarter,String subOrg) {
        List<TraBusiness> traBusinessList = insTraBusinessMapper.selectByYearAndQuarter(year, quarter,subOrg);
        return traBusinessList;
    }

    @Override
    public List<TraBusiness> selectHisByYearAndQuarter(String year, String quarter,String subOrg) {
        List<TraBusiness> traBusinessList = insTraBusinessMapper.selectHisByYearAndQuarter(year, quarter,subOrg);
        return traBusinessList;
    }

    @Override
    public BigDecimal getInRoadTotalTradeAmount(Map map) {
        return insTraBusinessMapper.getInRoadTotalTradeAmount(map);
    }

    @Override
    public void grandTradeZero(Map map) {
        insTraBusinessMapper.grandTradeZero(map);
    }

    @Override
    public BigDecimal getAllSumTradeAmount() {
        return insTraBusinessMapper.getAllSumTradeAmount();
    }

    @Override
    public List<QuarterListVo> getBusiness(Map<String, Object> map) {
        return insTraBusinessMapper.getBusiness(map);
    }

    @Override
    public PageInfo<TraBusiness> queryBatchSubmit(Integer pageNumber, Integer pageSize, String year, String quarter) {
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
        List<TraBusiness> list= insTraBusinessMapper.queryByConditions(param);
        return new PageInfo<>(list);
    }

    @Override
    public List<TraBusiness> traServiceList(String contractNo) {
        return insTraBusinessMapper.traBusinessList(contractNo);
    }

    @Override
    public Boolean updateByTradeAmount(Integer pid, Date contractDate, String relTrandeAmount) {
        return insTraBusinessMapper.updateByTradeAmount(pid,contractDate,relTrandeAmount);
    }

    @Override
    public TraBusiness traBusinessNo(String contractNo, String relNumber) {
        return insTraBusinessMapper.traBusinessNo(contractNo, relNumber);
    }

    @Override
    public TraBusiness traBusinessByAmount(String contractNo) {
        return insTraBusinessMapper.traBusinessByAmount(contractNo);
    }
    @Override
    public List<TraBusiness> getInit(String contractNo, String relNumber, String relName, String busType, String startTranDate, String endTranDate, String considerType, String majorTrade, String status, String createTimeString, String deptCode, String dataSources) {
        List<TraBusiness> list = insTraBusinessMapper.getInit(contractNo, relNumber, relName, busType, startTranDate, endTranDate, considerType, majorTrade, status, createTimeString, deptCode, dataSources);
        return list;
    }

    @Override
    public List<TraBusiness> getTranNum(String transNum) {
        return insTraBusinessMapper.getTranNum(transNum);
    }

}