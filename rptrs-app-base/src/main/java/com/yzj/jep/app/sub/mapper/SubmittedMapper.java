package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.vo.SubmittedVo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

import java.util.List;

@MyBatisMapper
public interface SubmittedMapper extends Mapper<SubmittedVo, Integer> {
    public List<SubmittedVo> getAll();
}
