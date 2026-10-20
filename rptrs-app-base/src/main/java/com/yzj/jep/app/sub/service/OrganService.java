/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 09:48:44
 *
 */

package com.yzj.jep.app.sub.service;

import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.sub.model.Organ;

import java.util.Map;

public interface OrganService extends CRUDService<Organ, Integer>{

    void extracOrgan(Map map, String nowDate);
}
