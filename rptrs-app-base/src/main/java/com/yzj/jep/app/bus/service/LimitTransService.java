/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月03日 09:44:42
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.bus.model.LimitTrans;

public interface LimitTransService extends CRUDService<LimitTrans, Integer> {

    /**
     * @param pid  product的pid
     * @param rate 额度使用率
     * @return
     */
    public String warnUse(Integer pid, Integer rate);
}
