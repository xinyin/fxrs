package com.yzj.jep.app.pub.mapper;

import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@MyBatisMapper
public interface PublogQuarterMapper extends Mapper<PublogRegularVo, Integer> {
    public List<PublogRegularVo> getQuarterList(@Param("startTime") String startTime,@Param("endTime") String endTime);
    public List<PublogRegularVo> getQuarterList1(@Param("startTime") String startTime,@Param("endTime") String endTime);
}
