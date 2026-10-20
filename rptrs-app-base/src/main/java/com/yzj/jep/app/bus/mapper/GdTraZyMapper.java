/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月01日 15:10:59
 *
 */

package com.yzj.jep.app.bus.mapper;


import com.yzj.jep.app.bus.model.GdTraZy;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

@MyBatisMapper
public interface GdTraZyMapper extends Mapper<GdTraZy, Integer> {
    /*Integer queryMaxID();*/
    void del(Integer pid);
}