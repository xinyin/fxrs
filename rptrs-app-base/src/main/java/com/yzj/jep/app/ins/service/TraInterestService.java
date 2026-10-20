/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:15:56
 *
 */

package com.yzj.jep.app.ins.service;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.ins.model.TraInterest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TraInterestService extends CRUDService<TraInterest, Integer> {
    /**
     * 计算交易主体与关联方的累积交易金额
     * 剔除 单笔重大的交易以及合并交易中的累积重大（即同一个合同号对应多个交易）
     * @param map
     * @return
     */
    BigDecimal getCurrYearTotalTradeAmount(Map map);

    BigDecimal getSumTradeAmount(RelOrg relOrg);

    List<TraInterest> selectByYearAndQuarter(String subYear, String subQuarter, String subOrg);

    List<TraInterest> selectHisByYearAndQuarter(String subYear, String subQuarter, String subOrg);

    BigDecimal getInRoadTotalTradeAmount(Map map);

    /**
     * 对累计关联交易金额进行归零处理
     *
     * @param map
     */
    void grandTradeZero(Map map);

    BigDecimal getAllSumTradeAmount();

    List<QuarterListVo> getInterest(Map<String,Object> map);

    PageInfo<TraInterest> queryBatchSubmit(Integer pageNumber, Integer pageSize, String year, String quarter);

    TraInterest traContractNo(String contractNo, String relNumber);

    Boolean updateByTradeAmount(Integer pid, Date contractDate, String relTrandeAmount);

    List<TraInterest> traContractNoList(String contractNo);

    TraInterest traInterestByAmount(String contractNo);

    TraInterest traBusinessByAmount(String contractNo, String contractAmount);
    List<TraInterest> getInit(String contractNo, String relNumber, String relName, String busType, String status, String considerType, String majorTrade, String startTranDate, String endTranDate, String createTimeString , String deptCode, String dataSources);

    List<TraInterest> getTranNum(String transNum);
}
