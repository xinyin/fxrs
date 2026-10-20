package com.yzj.jep.app.design.mapper;

import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

@MyBatisMapper
public interface EquityStructureChartMapper extends Mapper<Rel, String> {

    public String selectAll(String pid);

}
