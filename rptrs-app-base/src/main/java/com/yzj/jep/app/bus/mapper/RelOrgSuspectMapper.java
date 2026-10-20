/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 14:26:10
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.bus.model.RelOrgSuspect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisMapper
public interface RelOrgSuspectMapper extends Mapper<RelOrgSuspect, Integer> {
    /*
     * 获取机构附属信息
     * */
    List<Object> queryOrgSubInfo(@Param(value = "pid") Integer pid);

    /*
     * 获取某张表下的  最大id  用于设置自增
     * */
    Integer getMaxId(@Param(value = "tableName") String tableName);

    Integer insertForMapper(RelOrgSuspect relOrgSuspect);
}