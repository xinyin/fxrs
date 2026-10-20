package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.EarlyWarning;
import com.yzj.jep.app.bus.model.WarnConfig;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
@MyBatisMapper
public interface EarlyWarningMapper extends Mapper<EarlyWarning, Integer> {
}
