/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月31日 10:16:54
 *
 */

package com.yzj.jep.app.sub.service;
import com.yzj.jep.app.sub.model.InsBusiness;
import com.yzj.jep.orm.service.CRUDService;

import java.util.Map;

public interface InsBusinessService extends CRUDService<InsBusiness, Integer> {

    void extractInsBusiness(Map map, String nowDate);
}
