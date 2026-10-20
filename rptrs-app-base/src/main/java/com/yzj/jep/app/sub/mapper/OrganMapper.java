/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 09:48:44
 *
 */

package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.app.sub.model.Person;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.sub.model.Organ;

import java.util.List;

@MyBatisMapper
public interface OrganMapper extends Mapper<Organ, Integer>{
    List<Organ> getAllOrg();
}