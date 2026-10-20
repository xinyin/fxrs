/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:52:08
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.LegalPersonRemoveMapper;
import com.yzj.jep.app.bus.service.LegalPersonRemoveService;
import com.yzj.jep.app.bus.model.LegalPersonRemove;

@Service
@Transactional(readOnly = true)
public class
LegalPersonRemoveServiceImpl extends MyBatisCRUDService<LegalPersonRemove, Integer> implements LegalPersonRemoveService {

    Logger logger = LoggerFactory.getLogger(LegalPersonRemoveServiceImpl.class);

    @Autowired
    private LegalPersonRemoveMapper legalPersonRemoveMapper;

    @Override
    public Mapper<LegalPersonRemove, Integer> getMapper() {
        return legalPersonRemoveMapper;
    }

}