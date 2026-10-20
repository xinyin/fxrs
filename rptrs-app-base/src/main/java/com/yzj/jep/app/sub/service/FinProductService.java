/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 10:12:05
 *
 */

package com.yzj.jep.app.sub.service;

import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.sub.model.FinProduct;

import java.util.Map;

public interface FinProductService extends CRUDService<FinProduct, Integer>{

    void extractFinProduct(Map map, String nowDate);
}
