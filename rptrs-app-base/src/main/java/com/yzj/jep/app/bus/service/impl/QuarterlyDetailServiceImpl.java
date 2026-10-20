/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:39
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.QuarterlyDetailMapper;
import com.yzj.jep.app.bus.service.QuarterlyDetailService;
import com.yzj.jep.app.bus.model.QuarterlyDetail;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class QuarterlyDetailServiceImpl extends MyBatisCRUDService<QuarterlyDetail, Integer> implements QuarterlyDetailService {

    Logger logger = LoggerFactory.getLogger(QuarterlyDetailServiceImpl.class);

    @Autowired
    private QuarterlyDetailMapper quarterlyDetailMapper;

    @Override
    public Mapper<QuarterlyDetail, Integer> getMapper() {
        return quarterlyDetailMapper;
    }

}