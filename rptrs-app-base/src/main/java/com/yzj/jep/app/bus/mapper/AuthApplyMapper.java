/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年09月16日 20:03:29
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.AuthApply;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;


import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface AuthApplyMapper extends Mapper<AuthApply, Integer>{
    List<AuthApply>  find(Map<String, Object> emap);
}