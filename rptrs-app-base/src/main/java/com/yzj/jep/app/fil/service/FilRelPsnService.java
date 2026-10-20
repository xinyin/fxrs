/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月10日 18:04:45
 *
 */

package com.yzj.jep.app.fil.service;

import com.yzj.jep.app.bus.model.RelPsnVo;
import com.yzj.jep.app.fil.model.FilRelPsn;
import com.yzj.jep.app.fil.model.FilRelPsnVo;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface FilRelPsnService extends CRUDService<FilRelPsn, Integer> {
    List<FilRelPsnVo> selectRelPsnInformation(String pid);

    List<FilRelPsnVo> selectDownRelPsnInformation(String pid);

    List<FilRelPsn> selectFilRelPsnByfalgPid(String falgPid);

}
