package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.app.sub.model.EastGljyhzbView;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisMapper
public interface EastGljyhzbViewMapper extends Mapper<EastGljyhzbView,Integer> {

    List<EastGljyhzbView> queryOne(@Param("id") String id);
}
