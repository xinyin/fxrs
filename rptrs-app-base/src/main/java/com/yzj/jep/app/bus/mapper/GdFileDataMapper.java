/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 18:33:42
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.bus.model.GdFileData;

import java.util.List;

@MyBatisMapper
public interface GdFileDataMapper extends Mapper<GdFileData, Integer> {
    List<GdFileData> getAllGdInfo(GdFileData gdFileData);

    void newFileBatch(GdFileData gdFileData);
}