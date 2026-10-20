/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 15:15:15
 *
 */

package com.yzj.jep.app.sub.service;

import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.sub.model.Nonloan;

import java.util.Map;

public interface NonloanService extends CRUDService<Nonloan, Integer>{

    void extractNoLoan(Map map, String nowDate);
}
