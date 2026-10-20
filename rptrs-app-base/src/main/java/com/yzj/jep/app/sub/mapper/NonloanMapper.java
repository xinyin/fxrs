/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 15:15:15
 *
 */

package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.sub.model.Nonloan;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@MyBatisMapper
public interface NonloanMapper extends Mapper<Nonloan, Integer>{
    public BigDecimal getTransAmcount(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getServiceAmcount(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getOtherAmcount(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public List<BigDecimal> getDayOfTransAmcount(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public List<BigDecimal> getDayOfServiceAmcount(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public List<BigDecimal> getDayOfOtherAmcount(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);

}