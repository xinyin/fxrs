/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:01:38
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.TraService;
import com.yzj.jep.orm.service.CRUDService;

import java.math.BigDecimal;
import java.util.List;

public interface TraServiceService extends CRUDService<TraService, Integer> {

    Integer queryMaxID();

    void updateIsDel(Integer pid);

    TraService selectByID(Integer pid);

    void updateStatus(Integer pid);

    List<String> selStaffSH();

    List<String> selStaffFH();

    BigDecimal getBalance(TraService traService);

    void approvalReturn(Integer pid);
}
