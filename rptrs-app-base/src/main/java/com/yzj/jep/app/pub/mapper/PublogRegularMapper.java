package com.yzj.jep.app.pub.mapper;

import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

import java.util.List;

@MyBatisMapper
public interface PublogRegularMapper  extends Mapper<PublogRegularVo, Integer> {
    public List<PublogRegularVo> getAll();
}
