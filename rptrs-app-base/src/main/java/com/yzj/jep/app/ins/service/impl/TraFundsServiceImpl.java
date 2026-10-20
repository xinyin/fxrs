/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:50
 *
 */

package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.mapper.TraFundsMapper;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.model.TraInsenclosure;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.ins.service.TraInsenclosureService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
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
@Transactional(rollbackFor = Exception.class)
public class TraFundsServiceImpl extends MyBatisCRUDService<TraFunds, Integer> implements TraFundsService {

    Logger logger = LoggerFactory.getLogger(TraFundsServiceImpl.class);

    @Autowired
    private TraInsenclosureService traInsenclosureService;

    @Autowired
    private TraFundsMapper traFundsMapper;

    @Override
    public Mapper<TraFunds, Integer> getMapper() {
        return traFundsMapper;
    }

    @Override
    public BigDecimal getCurrYearTotalTradeAmount(Map map) {
        return traFundsMapper.getCurrYearTotalTradeAmount(map);
    }

    @Override
    public Map getSumByTradeType(Map map) {
        return traFundsMapper.getSumByTradeType(map);
    }

    @Override
    public void approvalReturn(Integer pid, String memo, String backFlag) {
        TraFunds traFunds = new TraFunds();
        traFunds.setPid(pid);
        if(StrUtil.isNotBlank(backFlag)){
            traFunds.setStatus("2");
        }else {
            traFunds.setStatus("5");
        }
        traFunds.setStatusComment(memo);
        this.updateSelective(traFunds);
    }

    @Override
    public BigDecimal getSumTradeAmount(RelOrg relOrg) {
        return traFundsMapper.getSumTradeAmount(relOrg);
    }

    /**
     * 获取资金运用类交易的限额的金额
     * @param map
     * @return
     */
    @Override
    public BigDecimal getLimitTotalTradeAmount(Map map) {
        return traFundsMapper.getLimitTotalTradeAmount(map);
    }

    @Override
    public List<TraFunds> selectByYearAndQuarter(String subYear, String subQuarter, String subOrg) {

        List<TraFunds> traFundsList = traFundsMapper.selectByYearAndQuarter(subYear, subQuarter,subOrg);
        return traFundsList;
    }
    @Override
    public List<TraFunds> selectHisByYearAndQuarter(String subYear, String subQuarter, String subOrg) {

        List<TraFunds> traFundsList = traFundsMapper.selectHisByYearAndQuarter(subYear, subQuarter,subOrg);
        return traFundsList;
    }

    @Override
    public BigDecimal getInRoadTotalTradeAmount(Map map) {
        return traFundsMapper.getInRoadTotalTradeAmount(map);
    }

    @Override
    public void grandTradeZero(Map map) {
        traFundsMapper.grandTradeZero(map);
    }

    @Override
    public BigDecimal getAllSumTradeAmount() {
        return traFundsMapper.getAllSumTradeAmount();
    }

    @Override
    public List<QuarterListVo> getFundsService(Map<String, Object> map) {
        return traFundsMapper.getFundsService(map);
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
    public PageInfo<TraFunds> queryBatchSubmit(Integer pageNumber, Integer pageSize, String year, String quarter) {
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
        List<TraFunds> list= traFundsMapper.queryByConditions(param);
        return new PageInfo<>(list);
    }

    @Override
    public BigDecimal sumTradeAmountByProNum(String financialAssstsNum) {
        return traFundsMapper.sumTradeAmountByProNum(financialAssstsNum);
    }

    @Override
    public TraFunds traContractNo(String contractNo, String relNumber) {
        return traFundsMapper.traServiceNo(contractNo, relNumber);
    }

    @Override
    public Boolean updateByTradeAmount(Integer pid, Date contractDate, String relTrandeAmount) {
        return traFundsMapper.updateByTradeAmount(pid,contractDate,relTrandeAmount);
    }

    @Override
    public List<TraFunds> traContractNoList(String contractNo) {
        return traFundsMapper.traServiceList(contractNo);
    }

    @Override
    public List<TraFunds> getInit(String contractNo, String relNumber, String relName, String busType, String startTranDate, String endTranDate, String considerType, String majorTrade, String status, String createTimeString, String deptCode, String dataSources) {
        List<TraFunds> list = traFundsMapper.getInit(contractNo, relNumber, relName, busType, startTranDate, endTranDate, considerType, majorTrade, status, createTimeString, deptCode, dataSources);
        return list;
    }

    @Override
    public List<TraFunds> getTranNum(String transNum) {
        List<TraFunds> list = traFundsMapper.getTranNum(transNum);
        return list;
    }

    @Override
    public List<TraFunds> getMaxRelatedTran(String startTime, String endTime) {
        return traFundsMapper.getMaxRelatedTran(startTime, endTime);
    }

    @Override
    public boolean calibrationAccessories(String transNum) {

        boolean fal = true;
        String [] dictType = {"Y01","Y02","Y03","Y04","Y05","Y06"};

        for (String typeCode:dictType) {
            //判断该附件类型是否上传文件
            List<Filter> fileFilter = new Filter.Builder().eq("delflag", 1).build();
            fileFilter.add(new Filter("transNum", Operator.EQ, transNum));
            fileFilter.add(new Filter("uploadFileType", typeCode));
            //查询该文件类型的文件个数
            List<TraInsenclosure> select = traInsenclosureService.select(fileFilter);
            if(0 == select.size()){
                fal = false;
                break;
            }
        }
        return fal;
    }

    @Override
    public List<TraFunds> isContractNo(String contractNo) {
        return traFundsMapper.isServiceNo(contractNo);
    }

}
