/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:52
 *
 */

package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.mapper.TraInvestmentMapper;
import com.yzj.jep.app.ins.model.QuarterlyVo;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.service.TraInvestmentService;
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
@Transactional
public class TraInvestmentServiceImpl extends MyBatisCRUDService<TraInvestment, Integer> implements TraInvestmentService {

    Logger logger = LoggerFactory.getLogger(TraInvestmentServiceImpl.class);

    @Autowired
    private TraInvestmentMapper traInvestmentMapper;

    @Override
    public Mapper<TraInvestment, Integer> getMapper() {
        return traInvestmentMapper;
    }

    @Override
    public BigDecimal getCurrYearTotalTradeAmount(Map map) {
        return traInvestmentMapper.getCurrYearTotalTradeAmount(map);
    }

    @Override
    public void approveReturn(Integer pid, String memo, String backFlag) {
        TraInvestment traInvestment = new TraInvestment();
        traInvestment.setPid(pid);
        if(StrUtil.isNotBlank(backFlag)){
            traInvestment.setStatus("2");
        }else {
            traInvestment.setStatus("5");
        }
        traInvestment.setStatusComment(memo);
        this.updateSelective(traInvestment);
    }

    @Override
    public BigDecimal getSumTradeAmount(RelOrg relOrg) {
        return traInvestmentMapper.getSumTradeAmount(relOrg);
    }

    @Override
    public List<TraInvestment> selectByYearAndQuarter(String subYear, String subQuarter, String subOrg) {
        return traInvestmentMapper.selectByYearAndQuarter(subYear,subQuarter,subOrg);
    }
    @Override
    public List<TraInvestment> selectHisByYearAndQuarter(String subYear, String subQuarter, String subOrg) {
        return traInvestmentMapper.selectHisByYearAndQuarter(subYear,subQuarter,subOrg);
    }

    @Override
    public BigDecimal getInRoadTotalTradeAmount(Map map) {
        return traInvestmentMapper.getInRoadTotalTradeAmount(map);
    }

    @Override
    public void grandTradeZero(Map map) {
        traInvestmentMapper.grandTradeZero(map);
    }

    @Override
    public BigDecimal getAllSumTradeAmount() {
        return traInvestmentMapper.getAllSumTradeAmount();
    }

    @Override
    public List<QuarterListVo> getInvestment(Map<String, Object> map) {
        return traInvestmentMapper.getInvestment(map);
    }

    /**
     * 集中审议条件查询
     * @param pageNumber
     * @param pageSize
     * @param year
     * @param quarter
     * @return
     */
    @Override
    public PageInfo<TraInvestment> queryByConditions(Integer pageNumber, Integer pageSize, String year, String quarter) {
        PageHelper.startPage(pageNumber,pageSize);
        //构造查询条件
        Map<String,Object> param = new HashMap<>();
        if(StrUtil.isNotBlank(year)){
            param.put("year",year.concat("%"));
        }else {
            int yearInt = DateUtil.year(new Date());
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
        List<TraInvestment> list= traInvestmentMapper.queryByConditions(param);
        return new PageInfo<>(list);
    }

    @Override
    public TraInvestment traContractNo(String contractNo) {
        return traInvestmentMapper.traContractNo(contractNo);
    }

    @Override
    public List<TraInvestment> traContractNoList(String contractNo) {
        return traInvestmentMapper.traContractList(contractNo);
    }

    @Override
    public Boolean updateByTradeAmount(Integer pid, Date contractDate, String relTrandeAmount) {
        return traInvestmentMapper.updateByTradeAmount(pid,contractDate,relTrandeAmount);
    }




}