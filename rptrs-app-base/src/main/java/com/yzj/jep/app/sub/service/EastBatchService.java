/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月05日 10:57:27
 *
 */

package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.EastBatch;
import com.yzj.jep.orm.service.CRUDService;

import java.util.Map;

public interface EastBatchService extends CRUDService<EastBatch, Integer>{

    Map<String, Object> pushEast(Integer id);

    Map<String, Object> distribute(String ids);
}
