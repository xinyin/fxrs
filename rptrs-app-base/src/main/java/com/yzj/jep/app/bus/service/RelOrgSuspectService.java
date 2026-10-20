/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 14:26:10
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;
import java.util.Map;

public interface RelOrgSuspectService extends CRUDService<RelOrgSuspect, Integer> {
    /*
     * 获取机构附属信息
     * */
    List<RelPsnSuspect> queryOrgSubInfo(Integer pid);

    /*
     * 获取某张表下的  最大id  用于设置自增
     * */
    Integer getMaxId(String tableName);

    /*
     * 将疑似关联方  转变为关联方
     * */
    Map<String, Object> replaceOrg(Integer pid, RelOrg relOrg, List<Rel> rels);
}
