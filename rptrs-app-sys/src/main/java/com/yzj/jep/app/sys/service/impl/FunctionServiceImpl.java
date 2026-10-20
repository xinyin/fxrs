/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月09日 19:59:12
 *
 */

package com.yzj.jep.app.sys.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.sys.mapper.FunctionMapper;
import com.yzj.jep.app.sys.service.FunctionService;
import com.yzj.jep.app.sys.model.Function;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class FunctionServiceImpl extends MyBatisCRUDService<Function, Integer> implements FunctionService {

    Logger logger = LoggerFactory.getLogger(FunctionServiceImpl.class);

    @Autowired
    private FunctionMapper functionMapper;

    @Override
    public Mapper<Function, Integer> getMapper() {

        return functionMapper;
    }

}