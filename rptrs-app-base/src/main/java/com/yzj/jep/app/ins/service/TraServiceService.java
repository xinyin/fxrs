/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:16:10
 *
 */

package com.yzj.jep.app.ins.service;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.ins.model.TraService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TraServiceService extends CRUDService<TraService, Integer> {
    /**
     * 计算交易主体与关联方的累积交易金额
     * 剔除 单笔重大的交易以及合并交易中的累积重大（即同一个合同号对应多个交易）
     * @param map
     * @return
     */
    BigDecimal getCurrYearTotalTradeAmount(Map map);

    Map getSumByTradeType(Map tradeType);

    void approveReturn(Integer pid, String memo, String backFlag);

    BigDecimal getSumTradeAmount(RelOrg relOrg);

    List<TraService> selectByYearAndQuarter(String subYear, String subQuarter, String subOrg);
    List<TraService> selectHisByYearAndQuarter(String subYear, String subQuarter, String subOrg);

    BigDecimal getInRoadTotalTradeAmount(Map map);

    /**
     * 对累计关联交易金额进行归零处理
     *
     * @param map
     */
    void grandTradeZero(Map map);

    BigDecimal getAllSumTradeAmount();

    List<QuarterListVo> getService(Map<String,Object> map);

    PageInfo<TraService> queryBatchSubmit(Integer pageNumber, Integer pageSize, String year, String quarter);

    TraService traServiceNo(String contractNo, String relNumber);

    List<TraService> traServiceList(String contractNo);

    Boolean updateByTradeAmount(Integer pid, Date contractDate, String relTrandeAmount);

    TraService traServiceByAmount(String contractNo,String contractAmount);
    List<TraService> getInit(String contractNo, String relNumber, String relName, String busType, String status, String considerType, String majorTrade, String startTranDate, String endTranDate, String createTimeString ,String deptCode, String dataSources);

    List<TraService> getTranNum(String transNum);
}
