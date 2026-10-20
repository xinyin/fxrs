/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:37
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.bus.model.NaturalPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisMapper
public interface NaturalPersonMapper extends Mapper<NaturalPerson, Integer> {
    List<NaturalPerson> serachNaturalByPid(@Param(value = "pid") Integer pid);
}