package com.yzj.jep.app.ins.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @title: RelTradeInterfaceMapper
 */
@MyBatisMapper
public interface RelTradeInterfaceMapper {
    Map queryTableMap(@Param("param") Map<String, Object> param);

    /**
     * 更新数据库表
     * @param param
     */
    void updateTableStatus(@Param("param") Map<String, Object> param);
}
