/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月10日 18:04:44
 *
 */

package com.yzj.jep.app.fil.service;


import com.yzj.jep.app.bus.model.RelProVo;
import com.yzj.jep.app.fil.model.FilRelPro;
import com.yzj.jep.app.fil.model.FilRelProVo;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface FilRelProService extends CRUDService<FilRelPro, Integer> {
    List<FilRelProVo> selectRelProOrg(String pid);

    List<FilRelProVo> selectDownRelProOrg(String pid);

}
