/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.service;


import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelOrgVo;
import com.yzj.jep.app.bus.model.RelPsnVo;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface InsiderOrgService extends CRUDService<RelOrg, Integer> {
    List<RelOrg> getOrgPsn(String orgid);

    List<RelOrg> getTZOrgPsn(String orgid);

    List<RelOrg> queryOrgList();

    List<RelOrg> selectList(RelOrg relOrg);

    RelOrg getGdInfo(String code);

    List<RelOrg> selectListNew(RelOrg relOrg);
}
