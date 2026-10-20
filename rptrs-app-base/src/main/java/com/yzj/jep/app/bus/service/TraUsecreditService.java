/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月27日 16:35:32
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.bus.model.TraUsecredit;

import java.math.BigDecimal;

public interface TraUsecreditService extends CRUDService<TraUsecredit, Integer> {
    void approvalReturn(Integer pid, String memo);

    BigDecimal getTradeBalance(TraUsecredit traUsecredit);

}
