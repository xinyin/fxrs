/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:36
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.bus.model.LegalPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisMapper
public interface LegalPersonMapper extends Mapper<LegalPerson, Integer> {
    List<LegalPerson> search(@Param(value = "legalPerson") LegalPerson legalPerson);
}