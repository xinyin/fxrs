package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.Chklog;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;

import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface ChklogMapper {

    List<Integer> selectbyisinsderbylevela();

    List<Integer> selectbyisinsderbytyped();

    List<Integer> selectbyisinsderbylevelf();

    List<Integer> selectbyisinsderbytypei();

}
