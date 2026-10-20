/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月10日 11:18:54
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.bus.model.RootOrgInfo;

public interface RootOrgInfoService extends CRUDService<RootOrgInfo, Integer> {

    RootOrgInfo selectByid();

    RootOrgInfo selectRootInfo(Integer year, Integer quarter);
}
