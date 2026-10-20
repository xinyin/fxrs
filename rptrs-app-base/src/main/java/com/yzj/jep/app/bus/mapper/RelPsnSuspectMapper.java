/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 14:26:15
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.bus.model.RelPsnSuspect;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface RelPsnSuspectMapper extends Mapper<RelPsnSuspect, Integer> {
    /*
     * 根据主键获取自然人附属信息
     * */
    List<Map<String, Object>> queryPsnRelation(@Param(value = "pid") Integer pid);

    /*
     * 根据主键获取自然人附属信息
     * */
    List<Map<String, Object>> queryPsnRelationDown(@Param(value = "pid") Integer pid);


    /*
     * 根据主键获取自然人权益信息(单位信息)
     * */
    List<Map<String, Object>> queryOrgCompany(@Param(value = "pid") Integer pid);

    /*
     * 返回主键的新增
     * */
    Integer insertForMapper(RelPsnSuspect relPsnSuspect);

}