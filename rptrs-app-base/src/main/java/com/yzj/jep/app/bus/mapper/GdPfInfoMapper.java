/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月19日 14:20:05
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.GdPfInfoVo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.bus.model.GdPfInfo;

import java.util.List;

@MyBatisMapper
public interface GdPfInfoMapper extends Mapper<GdPfInfo, Integer> {
    void createNewPfInfo(GdPfInfo gdPfInfo);

    void changePfInfo(GdPfInfo gdPfInfo);

    List<GdPfInfoVo> queryGdPfInfo(GdPfInfoVo gdPfInfoVo);
}