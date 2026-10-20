package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.app.sub.model.EastGljyhzb;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

import java.util.List;

@MyBatisMapper
public interface EastGLJYHZMapper extends Mapper<EastGljyhzb,Integer> {


   List<EastGljyhzb> queryAll();



}
