/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月18日 16:19:09
 *
 */

package com.yzj.jep.app.sub.service;


import com.yzj.jep.app.sub.model.InsOther;
import com.yzj.jep.orm.service.CRUDService;

import java.util.Map;

public interface InsOtherService extends CRUDService<InsOther, Integer> {

    void extractInsOther(Map map, String nowDate);
}
