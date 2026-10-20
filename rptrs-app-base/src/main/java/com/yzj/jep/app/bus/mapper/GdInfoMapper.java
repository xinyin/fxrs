package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.GdInfoVo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

import java.util.List;

@MyBatisMapper
public interface GdInfoMapper {
    List<GdInfoVo> getAllGd(GdInfoVo gdInfoVo);
}
