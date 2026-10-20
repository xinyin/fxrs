/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 14:15:38
 *
 */

package com.yzj.jep.app.ins.service;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.model.InsTrans;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.ins.model.TraBusiness;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TraBusinessService extends CRUDService<TraBusiness, Integer> {
    /**
     * 计算交易主体与关联方的累积交易金额
     * 剔除 单笔重大的交易以及合并交易中的累积重大（即同一个合同号对应多个交易）
     * @param map
     * @return
     */
    BigDecimal getCurrYearTotalTradeAmount(Map map);

    List<InsTrans> selectBusiness(String pid);

    BigDecimal getSumTradeAmount(RelOrg relOrg);

    List<TraBusiness>  selectByYearAndQuarter(String year,String quarter,String subOrg);

    List<TraBusiness>  selectHisByYearAndQuarter(String year,String quarter,String subOrg);

    BigDecimal getInRoadTotalTradeAmount(Map map);

    /**
     * 对累计关联交易金额进行归零处理
     *
     * @param map
     */
    void grandTradeZero(Map map);

    BigDecimal getAllSumTradeAmount();

    List<QuarterListVo> getBusiness(Map<String,Object> map);

    PageInfo<TraBusiness> queryBatchSubmit(Integer pageNumber, Integer pageSize, String year, String quarter);

    List<TraBusiness> traServiceList(String contractNo);

    Boolean updateByTradeAmount(Integer pid, Date contractDate, String relTrandeAmount);

    TraBusiness traBusinessNo(String contractNo, String relNumber);

    TraBusiness traBusinessByAmount(String contractNo);

    List<TraBusiness> getInit(String contractNo, String relNumber, String relName, String busType, String startTranDate, String endTranDate, String considerType, String majorTrade, String status, String createTimeString, String deptCode, String dataSources);

    List<TraBusiness> getTranNum(String transNum);
}
