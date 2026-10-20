/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 15:18:56
 *
 */

package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.app.sub.model.Organ;
import com.yzj.jep.app.sub.model.Person;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.sub.model.Transbalance;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@MyBatisMapper
public interface TransbalanceMapper extends Mapper<Transbalance, Integer>{
    public Person getIfExit(@Param("no") String no, @Param("startN") Date startN, @Param("endN") Date endN);
    public Organ getOrgIfExit(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public void deleteAll();
    public void insertAll(Transbalance transbalance);
}