/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月02日 14:16:57
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.TermReminder;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;
import java.util.Map;

public interface TermReminderService extends CRUDService<TermReminder, Integer> {

    //查询自然人关系
    List<TermReminder> selectRelPsnInfo();

    //查询机构关系
    List<TermReminder> selectRelOrgInfo();

    //批量修改并插入数据
    void insertList();

    //查询新表筛选数据
    void selectTermReminder(Map map);
}
