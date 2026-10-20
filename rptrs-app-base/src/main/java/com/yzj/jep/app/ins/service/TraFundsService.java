/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:50
 *
 */

package com.yzj.jep.app.ins.service;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.ins.model.TraFunds;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TraFundsService extends CRUDService<TraFunds, Integer> {

    /**
     * 计算交易主体与关联方的累积交易金额
     * 剔除 单笔重大的交易以及合并交易中的累积重大（即同一个合同号对应多个交易）
     * @param map
     * @return
     */
    BigDecimal getCurrYearTotalTradeAmount(Map map);

    Map getSumByTradeType(Map map);

    void approvalReturn(Integer pid, String memo, String backFlag);

    BigDecimal getSumTradeAmount(RelOrg relOrg);

    /**
     * 获取资金运用类交易的限额的金额
     * @param map
     * @return
     */
    BigDecimal getLimitTotalTradeAmount(Map map);

    List<TraFunds> selectByYearAndQuarter(String subYear, String subQuarter, String subOrg);
    List<TraFunds> selectHisByYearAndQuarter(String subYear, String subQuarter, String subOrg);

    BigDecimal getInRoadTotalTradeAmount(Map map);

    /**
     * 对累计关联交易金额进行归零处理
     *
     * @param map
     */
    void grandTradeZero(Map map);

    BigDecimal getAllSumTradeAmount();

    List<QuarterListVo> getFundsService(Map<String,Object> map);

    PageInfo<TraFunds> queryBatchSubmit(Integer pageNumber, Integer pageSize, String year, String quarter);

    BigDecimal sumTradeAmountByProNum(String financialAssstsNum);

    TraFunds traContractNo(String contractNo, String relNumber);

    Boolean updateByTradeAmount(Integer pid, Date contractDate, String relTrandeAmount);

    List<TraFunds> traContractNoList(String contractNo);

    List<TraFunds> getInit(String contractNo, String relNumber, String relName, String busType, String startTranDate, String endTranDate, String considerType, String majorTrade, String status, String createTimeString, String deptCode,String dataSources);

    List<TraFunds> getTranNum(String transNum);

    List<TraFunds> getMaxRelatedTran(String startTime, String endTime);

    /**
     * 校验必传附件是否上传
     * @return
     */
    boolean calibrationAccessories(String transNum);

    List<TraFunds> isContractNo(String contractNo);
}
