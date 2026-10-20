/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月10日 11:18:54
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import org.apache.ibatis.annotations.Param;

@MyBatisMapper
public interface RootOrgInfoMapper extends Mapper<RootOrgInfo, Integer> {


    RootOrgInfo selectById();

    RootOrgInfo selectRootInfo(@Param("year") Integer year, @Param("quarter") Integer quarter);
}