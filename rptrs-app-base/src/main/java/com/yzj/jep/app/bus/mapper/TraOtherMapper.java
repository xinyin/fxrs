/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:01:35
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.TraOther;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

import java.math.BigDecimal;

@MyBatisMapper
public interface TraOtherMapper extends Mapper<TraOther, Integer> {
    Integer queryMaxID();

    void updateIsDel(Integer pid);

    TraOther selectByID(Integer pid);

    void updateStatus(Integer pid);

    BigDecimal getBalance(TraOther traOther);

}