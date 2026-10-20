/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月28日 16:37:54
 *
 */

package com.yzj.jep.app.bus.mapper;


import com.yzj.jep.app.bus.model.TraCreditBzr;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

@MyBatisMapper
public interface TraCreditBzrMapper extends Mapper<TraCreditBzr, Integer> {

    Integer queryMaxID();
    /*void del(Integer creditId);*/
}