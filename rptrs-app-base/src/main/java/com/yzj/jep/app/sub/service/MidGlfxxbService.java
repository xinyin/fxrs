/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2025 pactera.com. All rights reserved.
 *
 * Generated at 2025年01月23日 14:55:45
 *
 */

package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.MidGlfxxb;
import com.yzj.jep.orm.service.CRUDService;

public interface MidGlfxxbService extends CRUDService<MidGlfxxb, String> {


    /**
     * East关联方
     * @param batchNo
     */
    void createEastMidGlfxxb(String batchNo);

}
