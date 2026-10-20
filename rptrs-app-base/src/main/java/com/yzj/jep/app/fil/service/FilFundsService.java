/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:55:55
 *
 */

package com.yzj.jep.app.fil.service;


import com.yzj.jep.app.fil.model.FilFunds;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface FilFundsService extends CRUDService<FilFunds, Integer> {

    List<FilFunds> selectfilFundsByfalgPid(String flagPid);
}
