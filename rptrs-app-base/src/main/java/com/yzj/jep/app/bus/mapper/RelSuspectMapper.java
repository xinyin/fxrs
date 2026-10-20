/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 14:26:18
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.bus.model.RelSuspect;

@MyBatisMapper
public interface RelSuspectMapper extends Mapper<RelSuspect, Integer> {

    /*
     * 返回主键的新增
     * */
    Integer insertForMapper(RelSuspect relSuspect);
}