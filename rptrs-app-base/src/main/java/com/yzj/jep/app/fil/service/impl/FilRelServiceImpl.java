/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月10日 18:04:42
 *
 */

package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.fil.mapper.FilRelMapper;
import com.yzj.jep.app.fil.model.FilRel;
import com.yzj.jep.app.fil.service.FilRelService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class FilRelServiceImpl extends MyBatisCRUDService<FilRel, Integer> implements FilRelService {

    Logger logger = LoggerFactory.getLogger(FilRelServiceImpl.class);

    @Autowired
    private FilRelMapper filRelMapper;

    @Override
    public Mapper<FilRel, Integer> getMapper() {
        return filRelMapper;
    }

}