/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:55:57
 *
 */

package com.yzj.jep.app.fil.mapper;


import com.yzj.jep.app.fil.model.FilInterest;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

import java.util.List;

@MyBatisMapper
public interface FilInterestMapper extends Mapper<FilInterest, Integer> {
    List<FilInterest> selectfilInterestByfalgPid(String flagPid);
}