/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月02日 14:16:57
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.TermReminder;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@MyBatisMapper
public interface TermReminderMapper extends Mapper<TermReminder, Integer> {

    //查询自然人关系
    List<TermReminder> selectRelPsnInfo();

    //查询机构关系
    List<TermReminder> selectRelOrgInfo();

    //批量插入数据
    int insertList(TermReminder termReminder);
}