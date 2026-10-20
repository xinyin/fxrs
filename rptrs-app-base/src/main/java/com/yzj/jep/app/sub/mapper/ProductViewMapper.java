package com.yzj.jep.app.sub.mapper;


import com.yzj.jep.app.sub.model.ProductView;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

@MyBatisMapper
public interface ProductViewMapper extends Mapper<ProductView,Integer> {
}
