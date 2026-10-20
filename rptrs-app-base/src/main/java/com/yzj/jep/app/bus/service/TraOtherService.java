/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:01:35
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.TraOther;
import com.yzj.jep.orm.service.CRUDService;

import java.math.BigDecimal;

public interface TraOtherService extends CRUDService<TraOther, Integer> {
    Integer queryMaxID();

    void updateIsDel(Integer pid);

    TraOther selectByID(Integer pid);

    void updateStatus(Integer pid);

    String getFormat(String dict, String value);

    BigDecimal getBalance(TraOther traOther);

    void approvalReturn(Integer pid);
}
