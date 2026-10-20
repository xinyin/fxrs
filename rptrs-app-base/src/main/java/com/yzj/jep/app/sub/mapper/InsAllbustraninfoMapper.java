/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:30
 *
 */

package com.yzj.jep.app.sub.mapper;


import com.yzj.jep.app.sub.model.InsAllbustraninfo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface InsAllbustraninfoMapper extends Mapper<InsAllbustraninfo, Integer> {
    List<Map<String,Object>> selectAllTrans(@Param("year") String year, @Param("subQuarter") String subQuarter, @Param("tid") String tid);
}