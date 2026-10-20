/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:34
 *
 */

package com.yzj.jep.app.sub.service;


import com.yzj.jep.app.sub.model.InsAllfunds;
import com.yzj.jep.orm.service.CRUDService;

import java.util.Map;

public interface InsAllfundsService extends CRUDService<InsAllfunds, Integer> {

    void extractCredit(Map map);

}
