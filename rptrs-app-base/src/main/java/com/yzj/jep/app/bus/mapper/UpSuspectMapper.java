package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.suspect.UpSuspect;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisMapper
public interface UpSuspectMapper {
    /*
     * 获取自然人的上级信息
     * */
    List<UpSuspect> getUpPsnInfo(@Param(value = "pid") Integer pid);


    /*
     * 获取自然人的上级信息
     * */
    List<UpSuspect> getUpOrgInfo(@Param(value = "pid") Integer pid);

    //获取关联方信息  pid应该是自身
    List<UpSuspect> getUpInfoOnOrg(@Param(value = "pid") Integer pid);

    //获取关联方信息  pid应该是自身
    List<UpSuspect> getUpInfoOnPsn(@Param(value = "pid") Integer pid);
}
