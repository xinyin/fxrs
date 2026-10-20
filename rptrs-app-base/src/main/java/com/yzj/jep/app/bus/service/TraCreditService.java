/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月22日 16:09:52
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.TraCredit;
import com.yzj.jep.orm.service.CRUDService;

import java.math.BigDecimal;

public interface TraCreditService extends CRUDService<TraCredit, Integer> {
    TraCredit selectByID(Integer pid);

    void updateStatus(Integer pid);

    Integer queryMaxID();

    void del(Integer pid);

    BigDecimal getCreditBalance(TraCredit traCredit);

    void approvalReturn(Integer pid);

    BigDecimal getBankDeposit(TraCredit traCredit);

    BigDecimal getNationalDebt(TraCredit traCredit);

    BigDecimal getSecuritDeposit(TraCredit traCredit);
}
