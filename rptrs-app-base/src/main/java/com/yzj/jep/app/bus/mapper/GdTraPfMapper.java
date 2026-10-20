/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月02日 16:21:56
 *
 */

package com.yzj.jep.app.bus.mapper;


import com.yzj.jep.app.bus.model.GdTraPf;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@MyBatisMapper
public interface GdTraPfMapper extends Mapper<GdTraPf, Integer> {
    /*Integer queryMaxID();*/
    /*void updateStatus(Integer pid);*/
    void del(Integer pid);

    Integer changeCreateTime(@Param(value = "createTime") Date createTime, @Param(value = "year") String year);

    List<GdTraPf> selectGdTraPfExprot(@Param(value = "year") String year);
}