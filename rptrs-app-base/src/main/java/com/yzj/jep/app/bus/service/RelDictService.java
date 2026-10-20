/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月26日 18:23:04
 *
 */

package com.yzj.jep.app.bus.service;


import com.yzj.jep.app.bus.model.RelDict;
import com.yzj.jep.orm.service.CRUDService;

import java.util.Map;

public interface RelDictService extends CRUDService<RelDict, String> {
    RelDict selectDict(String relCategory, String relCategorySub);
}
