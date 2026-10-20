/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月10日 18:04:43
 *
 */

package com.yzj.jep.app.fil.service;


import com.yzj.jep.app.fil.model.FilRelOrg;
import com.yzj.jep.app.fil.model.FilRelOrgVo;
import com.yzj.jep.app.fil.model.FilRelProVo;
import com.yzj.jep.app.fil.model.FilRelPsnVo;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface FilRelOrgService extends CRUDService<FilRelOrg, Integer> {
    List<FilRelOrgVo> getTZOrgPsn(String orgid);

    //自然人下级关联方
    List<FilRelOrgVo> getDownTZOrgPsn(String orgid);

    List<FilRelPsnVo> getOrgPsn(String orgid);

    List<FilRelPsnVo> getDownOrgPsn(String orgid);

    List<FilRelOrgVo> getOrgOrg(String orgid);

    List<FilRelOrgVo> getDownOrgOrg(String orgid);

    //查找机构下的所属金融产品
    List<FilRelProVo> getOrgUpPro(String orgid);

    //查找机构下的下级金融产品
    List<FilRelProVo> getOrgDownPro(String orgid);

    List<FilRelOrgVo> selecUpOrg(String valueOf);

    List<FilRelOrgVo> selecDownOrg(String valueOf);

    List<FilRelOrg> selectFilRelOrgByfalgPid(String flagPid);
}
