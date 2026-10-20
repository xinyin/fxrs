/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 14:21:39
 *
 */

package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.app.sub.model.Credit;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@MyBatisMapper
public interface CreditMapper extends Mapper<Credit, Integer>{
    public String getType(String no);
    public BigDecimal getDepanddet(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public List<BigDecimal> getDayOfDepanddet(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);


}