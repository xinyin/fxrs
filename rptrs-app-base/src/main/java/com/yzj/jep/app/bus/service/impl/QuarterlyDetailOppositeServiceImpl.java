/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:40
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.QuarterlyDetailOppositeMapper;
import com.yzj.jep.app.bus.service.QuarterlyDetailOppositeService;
import com.yzj.jep.app.bus.model.QuarterlyDetailOpposite;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class QuarterlyDetailOppositeServiceImpl extends MyBatisCRUDService<QuarterlyDetailOpposite, Integer> implements QuarterlyDetailOppositeService {

    Logger logger = LoggerFactory.getLogger(QuarterlyDetailOppositeServiceImpl.class);

    @Autowired
    private QuarterlyDetailOppositeMapper quarterlyDetailOppositeMapper;

    @Override
    public Mapper<QuarterlyDetailOpposite, Integer> getMapper() {
        return quarterlyDetailOppositeMapper;
    }

}