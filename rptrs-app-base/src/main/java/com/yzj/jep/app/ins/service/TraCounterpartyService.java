/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:52
 *
 */

package com.yzj.jep.app.ins.service;

import com.yzj.jep.app.ins.model.TraCounterparty;
import com.yzj.jep.orm.service.CRUDService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TraCounterpartyService extends CRUDService<TraCounterparty, Integer> {

    /**
     * 对累计关联交易金额进行归零处理(新)
     *
     * @param map
     */
    void grandTradeZeroNew(Map map);

    /**
     * 根据交易编号更新交易对手状态
     */
    void updataCounterpartyStatusByTransNum(TraCounterparty tra);

    /**
     * 关联交易对手在途数据查询
     * @param map
     * @return
     */
    List<TraCounterparty> queryInRoadTransList(Map<String, Object> map);

    /**
     * 根据交易编号更新交易对手合同金额和合同日期
     */
    Boolean updataCounterpartyByTransNum(String transNum, String status,Date contractDate, String relTrandeAmount, BigDecimal bigDecimalOld);
}
