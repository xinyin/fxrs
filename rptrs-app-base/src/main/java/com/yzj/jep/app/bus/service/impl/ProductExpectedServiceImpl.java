/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月20日 09:41:48
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.ProductExpectedMapper;
import com.yzj.jep.app.bus.model.ProductExpected;
import com.yzj.jep.app.bus.service.ProductExpectedService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class ProductExpectedServiceImpl extends MyBatisCRUDService<ProductExpected, Integer> implements ProductExpectedService {

    Logger logger = LoggerFactory.getLogger(ProductExpectedServiceImpl.class);

    @Autowired
    private ProductExpectedMapper productExpectedMapper;

    @Override
    public Mapper<ProductExpected, Integer> getMapper() {
        return productExpectedMapper;
    }

}