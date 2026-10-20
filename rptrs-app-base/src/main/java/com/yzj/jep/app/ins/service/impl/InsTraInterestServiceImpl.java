/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:15:56
 *
 */

package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.mapper.InsTraInterestMapper;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.TraInterestService;
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
public class InsTraInterestServiceImpl extends MyBatisCRUDService<TraInterest, Integer> implements TraInterestService {

    Logger logger = LoggerFactory.getLogger(InsTraInterestServiceImpl.class);

    @Autowired
    private InsTraInterestMapper insTraInterestMapper;

    @Override
    public Mapper<TraInterest, Integer> getMapper() {
        return insTraInterestMapper;
    }

    @Override
    public BigDecimal getCurrYearTotalTradeAmount(Map map) {
        return insTraInterestMapper.getCurrYearTotalTradeAmount(map);
    }

    @Override
    public BigDecimal getSumTradeAmount(RelOrg relOrg) {
        return insTraInterestMapper.getSumTradeAmount(relOrg);
    }

    @Override
    public List<TraInterest> selectByYearAndQuarter(String subYear, String subQuarter, String subOrg) {
        return insTraInterestMapper.selectByYearAndQuarter(subYear,subQuarter,subOrg);
    }
    @Override
    public List<TraInterest> selectHisByYearAndQuarter(String subYear, String subQuarter, String subOrg) {
        return insTraInterestMapper.selectHisByYearAndQuarter(subYear,subQuarter,subOrg);
    }

    @Override
    public BigDecimal getInRoadTotalTradeAmount(Map map) {
        return insTraInterestMapper.getInRoadTotalTradeAmount(map);
    }

    @Override
    public void grandTradeZero(Map map) {
        insTraInterestMapper.grandTradeZero(map);
    }

    @Override
    public BigDecimal getAllSumTradeAmount() {
        return insTraInterestMapper.getAllSumTradeAmount();
    }

    @Override
    public List<QuarterListVo> getInterest(Map<String, Object> map) {
        return insTraInterestMapper.getInterest(map);
    }

    @Override
    public PageInfo<TraInterest> queryBatchSubmit(Integer pageNumber, Integer pageSize, String year, String quarter) {
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
        List<TraInterest> list= insTraInterestMapper.queryByConditions(param);
        return new PageInfo<>(list);
    }

    @Override
    public TraInterest traContractNo(String contractNo, String relNumber) {
        return insTraInterestMapper.traTraInterestNo(contractNo, relNumber);
    }

    @Override
    public Boolean updateByTradeAmount(Integer pid, Date contractDate, String relTrandeAmount) {
        return insTraInterestMapper.updateByTradeAmount(pid,contractDate,relTrandeAmount);
    }

    @Override
    public List<TraInterest> traContractNoList(String contractNo) {
        return insTraInterestMapper.traTraInterestList(contractNo);
    }

    @Override
    public TraInterest traInterestByAmount(String contractNo) {
        return insTraInterestMapper.traTraInteresByAmount(contractNo);
    }

    @Override
    public TraInterest traBusinessByAmount(String contractNo, String contractAmount) {
        return insTraInterestMapper.insTraInteresByAmount(contractNo,contractAmount);
    }
    @Override
    public List<TraInterest> getInit(String contractNo, String relNumber, String relName, String busType, String status, String considerType, String majorTrade, String startTranDate, String endTranDate, String createTimeString , String deptCode, String dataSources) {
        List<TraInterest> list = insTraInterestMapper.getInit(contractNo, relNumber, relName, busType, status,considerType, majorTrade,startTranDate,endTranDate,createTimeString,deptCode,dataSources);
        return list;
    }

    @Override
    public List<TraInterest> getTranNum(String transNum) {
        return insTraInterestMapper.getTranNum(transNum);
    }
}
