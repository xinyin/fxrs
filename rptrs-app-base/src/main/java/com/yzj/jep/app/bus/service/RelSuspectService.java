/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 14:26:18
 *
 */

package com.yzj.jep.app.bus.service;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.bus.model.RelSuspect;

public interface RelSuspectService extends CRUDService<RelSuspect, Integer> {

    void removeRel(String fromId, String toId, String fromType, String toType);

    void returnSuspectTask(JSONObject json, String memo);
}
