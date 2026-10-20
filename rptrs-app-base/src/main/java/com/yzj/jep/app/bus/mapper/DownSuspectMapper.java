package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.suspect.DownSuspect;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@MyBatisMapper
public interface DownSuspectMapper {
    /*
     * 获取自然人的下级信息
     * */
    List<DownSuspect> getDownPsnInfo(Integer pid);
}
