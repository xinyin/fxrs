/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月19日 14:20:05
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.GdPfInfoVo;
import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.bus.model.GdPfInfo;

import java.util.List;

public interface GdPfInfoService extends CRUDService<GdPfInfo, Integer> {
    void createNewPfInfo(GdPfInfo gdPfInfo);

    void changePfInfo(List<GdPfInfo> gdPfInfos);

    List<GdPfInfoVo> queryGdPfInfo(GdPfInfoVo gdPfInfoVo);

}
