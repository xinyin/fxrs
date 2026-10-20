/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月20日 11:07:48
 *
 */

package com.yzj.jep.app.pub.mapper;

import com.yzj.jep.app.pub.model.PubQuarterVo;
import com.yzj.jep.app.pub.model.PubYearVo;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface PublogMapper extends Mapper<Publog, Integer> {

    List<PubYearVo> queryDate(@Param(value = "pubYear") String pubYear);

    BigDecimal selectTotal(@Param(value = "tranType") String tranType, @Param(value = "pubYear") String pubYear);

    BigDecimal selectAmt(@Param(value = "relName") String relName, @Param(value = "tranType") String tranType, @Param(value = "pubYear") String pubYear);

    List<PubQuarterVo> selectQuarter(@Param(value = "pubYear") String pubYear, @Param(value = "") String pubQuarter);

    BigDecimal selectLastAmt(@Param(value = "relName") String relName, @Param(value = "tranType") String tranType, @Param(value = "date") String date);

    Integer majorTradeCount(@Param(value = "relName") String relName, @Param(value = "tranType") String tranType, @Param(value = "pubYear") String pubYear);

    BigDecimal selectQuarterTotal(@Param(value = "tranType") String tranType, @Param(value = "pubYear") String pubYear, @Param(value = "pubQuarter") String pubQuarter);

    List<PubQuarterVo> queryQuarterDate(@Param(value = "pubQuarterge") String pubQuarterge, @Param(value = "pubQuarterle") String pubQuarterle);

    void del(@Param(value = "pid") Integer pid);

    Publog selectById(@Param(value = "pid") Integer pid);

    List<Publog> selectList(Publog publog);

    List<Publog> selectByConditions(@Param("param") Map<String, Object> param);
}