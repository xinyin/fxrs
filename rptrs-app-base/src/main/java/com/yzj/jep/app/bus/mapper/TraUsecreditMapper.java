/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月27日 16:35:32
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.bus.model.TraUsecredit;

import java.math.BigDecimal;

@MyBatisMapper
public interface TraUsecreditMapper extends Mapper<TraUsecredit, Integer> {
    BigDecimal getTradeBalance(TraUsecredit traUsecredit);
}