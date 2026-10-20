/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月13日 15:41:26
 *
 */

package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.EastGLFXX;
import com.yzj.jep.app.sub.model.RdsMiddatalog2;
import com.yzj.jep.orm.service.CRUDService;

public interface RdsMiddatalogService extends CRUDService<RdsMiddatalog2, Integer> {

    void createEastBatch(String[] eastTypes);

    String createBatchNo();
}
