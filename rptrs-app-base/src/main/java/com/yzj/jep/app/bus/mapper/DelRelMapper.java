/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月22日 11:51:25
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.bus.model.DelRel;
import org.apache.ibatis.annotations.Param;

@MyBatisMapper
public interface DelRelMapper extends Mapper<DelRel, Integer> {
    DelRel selectByRelId(@Param("relPid") Integer relPid);
}