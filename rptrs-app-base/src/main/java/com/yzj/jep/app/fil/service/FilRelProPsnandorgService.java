/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月17日 15:30:13
 *
 */

package com.yzj.jep.app.fil.service;

import com.yzj.jep.app.bus.model.RelProVo;
import com.yzj.jep.app.fil.model.FilRelProPsnandorg;
import com.yzj.jep.app.fil.model.FilRelProVo;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface FilRelProPsnandorgService extends CRUDService<FilRelProPsnandorg, Integer> {
    //获取自然人归属上级金融产品
    List<FilRelProVo> selectRelProUpOfPsnInformation(String pid);

    //获取自然人关联的下级金融产品
    List<FilRelProVo> selectRelProDownOfPsnInformation(String pid);
}
