package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.app.sub.model.MidGljymxb;
import com.yzj.jep.app.sub.model.MidZjyylgljyxyxxb;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@MyBatisMapper
public interface MidGljymxbMapper extends Mapper<MidGljymxb,Integer> {

    List<MidGljymxb> selectTraDetails(@Param("startTime") String startTime,@Param("endTime") String endTime);
}




