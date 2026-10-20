package com.yzj.jep.app.ins.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @title: MajorTraCheckMapper
 * @Author Whx
 * @Date: 2021/8/18 15:44
 * @Version 1.0
 */
@MyBatisMapper
public interface MajorTraCheckMapper {
    BigDecimal sumAmount(@Param("param") Map<String, Object> param);

    BigDecimal sumNotClearAmount(@Param("param") Map<String, Object> param);

    BigDecimal sumCompleteApprove(@Param("param")Map<String, Object> param);

    Integer hasUnassortedBus(@Param("param") Map<String, Object> relNumber);

    BigDecimal sumPracTradeAmount(@Param("param") Map<String, Object> param);

    BigDecimal sumRelTradeAmount(@Param("param") Map<String, Object> param);
}
