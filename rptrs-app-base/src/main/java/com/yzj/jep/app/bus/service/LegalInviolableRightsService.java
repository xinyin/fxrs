/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月16日 16:30:53
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.LegalInviolableRights;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface LegalInviolableRightsService extends CRUDService<LegalInviolableRights, Integer> {
    /*
     * 根据机构id  查找权益信息
     * */
    List<LegalInviolableRights> searchByPid(Integer pid);

}
