package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.app.sub.model.PersonView;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

/**
 * @author yao
 */
@MyBatisMapper
public interface PersonViewMapper extends Mapper<PersonView,Integer> {
}
