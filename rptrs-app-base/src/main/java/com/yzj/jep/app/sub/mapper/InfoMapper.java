package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.sub.model.Info;

import java.math.BigDecimal;
@MyBatisMapper
public interface InfoMapper extends Mapper<Info,Integer>{
    public BigDecimal getCaptial();
}
