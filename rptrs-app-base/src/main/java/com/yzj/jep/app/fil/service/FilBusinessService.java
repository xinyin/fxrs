/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:55:54
 *
 */

package com.yzj.jep.app.fil.service;


import com.yzj.jep.app.fil.model.FilBusiness;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface FilBusinessService extends CRUDService<FilBusiness, Integer> {

    List<FilBusiness> selectFilBusinessByfalgPid(String flagPid);
}
