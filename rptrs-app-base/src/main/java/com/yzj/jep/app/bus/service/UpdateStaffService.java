/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月08日 18:29:09
 *
 */

package com.yzj.jep.app.bus.service;


import com.yzj.jep.app.bus.model.DeptEntitys;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;


public interface UpdateStaffService extends CRUDService<DeptEntitys, Integer> {


    public List<DeptEntitys> getStaff();


}
