/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:41
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.QuarterlyLegalMapper;
import com.yzj.jep.app.bus.service.QuarterlyLegalService;
import com.yzj.jep.app.bus.model.QuarterlyLegal;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class QuarterlyLegalServiceImpl extends MyBatisCRUDService<QuarterlyLegal, Integer> implements QuarterlyLegalService {

    Logger logger = LoggerFactory.getLogger(QuarterlyLegalServiceImpl.class);

    @Autowired
    private QuarterlyLegalMapper quarterlyLegalMapper;

    @Override
    public Mapper<QuarterlyLegal, Integer> getMapper() {
        return quarterlyLegalMapper;
    }

}