/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月08日 19:41:07
 *
 */

package com.yzj.jep.app.ins.service;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.model.TraOther;
import com.yzj.jep.orm.service.CRUDService;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TraOtherService extends CRUDService<TraOther, Integer>{

    /**
     * 计算交易主体与关联方的累积交易金额
     * 剔除 单笔重大的交易以及合并交易中的累积重大（即同一个合同号对应多个交易）
     * @param map
     * @return
     */
    BigDecimal getCurrYearTotalTradeAmount(Map map);

    List<TraOther> selectByYearAndQuarter(String subYear, String subQuarter, String subOrg);
    List<TraOther> selectHisByYearAndQuarter(String subYear, String subQuarter, String subOrg);

    BigDecimal getInRoadTotalTradeAmount(Map map);

    /**
     * 对累计关联交易金额进行归零处理
     *
     * @param map
     */
    void grandTradeZero(Map map);

    BigDecimal getAllSumTradeAmount();


    List<QuarterListVo> getOther(Map<String,Object> map);

    PageInfo<TraOther> queryBatchSubmit(Integer pageNumber, Integer pageSize, String year, String quarter);

    TraOther traContractNo(String contractNo);

    Boolean updateByTradeAmount(Integer pid, Date contractDate, String relTrandeAmount);

    List<TraOther> traContractNoList(String contractNo);
}
