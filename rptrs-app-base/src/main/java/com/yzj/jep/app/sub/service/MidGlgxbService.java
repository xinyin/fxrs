/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2025 pactera.com. All rights reserved.
 *
 * Generated at 2025年01月23日 15:42:07
 *
 */

package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.MidGlgxb;
import com.yzj.jep.orm.service.CRUDService;

public interface MidGlgxbService extends CRUDService<MidGlgxb, String> {


    /**
     * East关联关系
     * @param batchNo
     */
    void createEastMidGlgxb(String batchNo);

}
