package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.mapper.ProductViewMapper;
import com.yzj.jep.app.sub.model.ProductView;
import com.yzj.jep.app.sub.service.ProductViewService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductVIewServiceImpl extends MyBatisCRUDService<ProductView, Integer> implements ProductViewService   {

    Logger logger	= LoggerFactory.getLogger(ProductVIewServiceImpl.class);

    @Autowired
    private ProductViewMapper productViewMapper;

    @Override
    public Mapper<ProductView, Integer> getMapper() {
        return productViewMapper;
    }
}
