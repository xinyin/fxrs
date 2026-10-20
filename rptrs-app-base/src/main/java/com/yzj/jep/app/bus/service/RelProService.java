/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月25日 14:22:10
 *
 */

package com.yzj.jep.app.bus.service;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface RelProService extends CRUDService<RelPro, Integer> {

    List<RelProVo> selectRelProOrg(String pid);

    List<RelProVo> selectDownRelProOrg(String pid);

    void updateIsIterateByPid(Integer pid, Integer isIterate);

    void insertNeo4jOrgToPsn(RelOrg relFromOrg, RelPro relToPro, RelPsnAndOrgOfPro rel);

    public void returnProTask(JSONObject json, String memo);

    void insertNeo4jPsnToPro(RelPsn fromPsn, RelPro toPro, Rel rel);

    void insertNeo4jOrgToPro(RelOrg fromOrg, RelPro toPro, Rel rel);


    List<RelProVo> selectProByRelToPid(String pid);

    List<RelProVo> selectProByRelFromPid(String rootId);

}
