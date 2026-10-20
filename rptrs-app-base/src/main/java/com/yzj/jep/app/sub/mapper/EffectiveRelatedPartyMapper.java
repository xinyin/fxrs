package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.app.sub.model.Bus_rel_list;
import com.yzj.jep.app.sub.model.Bus_rel_list_hi;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

/**
 * 有效关联方
 */
@MyBatisMapper
public interface EffectiveRelatedPartyMapper {

    Boolean insertBusRelList(Bus_rel_list busRelList);

    Boolean insertBusRelListHi(Bus_rel_list_hi busRelList);

    Boolean truncateBybusRellist();
}
