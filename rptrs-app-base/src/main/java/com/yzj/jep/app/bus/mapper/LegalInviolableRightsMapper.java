/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月16日 16:30:53
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.LegalInviolableRights;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisMapper
public interface LegalInviolableRightsMapper extends Mapper<LegalInviolableRights, Integer> {
    List<LegalInviolableRights> searchByPid(@Param(value = "pid") Integer pid);
}