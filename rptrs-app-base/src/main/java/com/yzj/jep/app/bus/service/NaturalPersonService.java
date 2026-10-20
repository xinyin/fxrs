/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:37
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.bus.model.NaturalPerson;

import java.util.List;

public interface NaturalPersonService extends CRUDService<NaturalPerson, Integer> {
    List<NaturalPerson> serachNaturalByPid(Integer pid);
}
