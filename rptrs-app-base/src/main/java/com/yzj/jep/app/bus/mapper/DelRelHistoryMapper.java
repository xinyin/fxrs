package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.DelRelHistory;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ClassName:DelRelHistoryMapper
 * Package:com.yzj.jep.app.bus.mapper
 * Description:
 *
 * @date:2021/6/23 11:46
 * @author:
 */

@MyBatisMapper
public interface DelRelHistoryMapper extends Mapper<DelRelHistory, Integer> {
    DelRelHistory selectByRelId(@Param("relPid") Integer relPid);
}
