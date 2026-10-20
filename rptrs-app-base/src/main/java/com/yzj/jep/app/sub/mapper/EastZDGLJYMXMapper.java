package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.app.sub.model.EastZDGLJYMX;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

@MyBatisMapper
public interface EastZDGLJYMXMapper extends Mapper<EastZDGLJYMX,Integer> {
    EastZDGLJYMX getTransNum();
}
