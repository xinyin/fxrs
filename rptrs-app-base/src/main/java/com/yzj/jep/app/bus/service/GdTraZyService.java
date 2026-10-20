/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月01日 15:10:59
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.GdTraZy;
import com.yzj.jep.orm.service.CRUDService;

public interface GdTraZyService extends CRUDService<GdTraZy, Integer> {
    /*Integer queryMaxID();*/
    void del(Integer pid);

    void approvalReturn(Integer pid);
}
