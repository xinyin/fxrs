/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:52
 *
 */

package com.yzj.jep.app.ins.mapper;

import com.yzj.jep.app.ins.model.TraCounterparty;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface TraCounterpartyMapper extends Mapper<TraCounterparty, Integer> {


    /**
     * 对累计关联交易金额进行归零处理(新)
     *
     * @param map
     */
    void grandTradeZeroNew(Map map);

    /**
     * 根据交易编号更新交易对手状态
     * @param tra
     */
    void updataCounterpartyStatusByTransNum(TraCounterparty tra);

    /**
     * 关联交易对手在途数据查询
     * @param param
     * @return
     */
    List<TraCounterparty> queryInRoadTransList(@Param("param") Map<String, Object> param);

    Boolean updataCounterpartyByTransNum(@Param("transNum")String transNum, @Param("Status")String Status,@Param("contractDate") Date contractDate, @Param("relTrandeAmount")String relTrandeAmount,@Param("bigDecimalOld")BigDecimal bigDecimalOld);

    List<TraCounterparty> sumAfterCleanAmount(@Param("param") Map<String, Object> param);
}