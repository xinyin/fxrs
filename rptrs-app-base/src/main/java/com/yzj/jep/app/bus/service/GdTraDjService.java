/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月02日 14:05:03
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.bus.model.GdTraDj;

public interface GdTraDjService extends CRUDService<GdTraDj, Integer> {
    void approvalReturn(Integer pid);

}
