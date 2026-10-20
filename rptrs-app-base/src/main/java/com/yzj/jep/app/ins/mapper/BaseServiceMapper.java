

package com.yzj.jep.app.ins.mapper;

import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

import java.math.BigDecimal;
import java.util.Map;

@MyBatisMapper
public interface BaseServiceMapper extends Mapper<TraBusiness, Integer> {
    BigDecimal queryAgreementAmountSum(Map<String, Object> map);
}