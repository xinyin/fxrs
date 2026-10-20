/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:52:13
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

import com.yzj.jep.app.bus.mapper.NaturalPersonRemoveMapper;
import com.yzj.jep.app.bus.service.NaturalPersonRemoveService;
import com.yzj.jep.app.bus.model.NaturalPersonRemove;

@Service
@Transactional(readOnly = true)
public class NaturalPersonRemoveServiceImpl extends MyBatisCRUDService<NaturalPersonRemove, Integer> implements NaturalPersonRemoveService {

    Logger logger = LoggerFactory.getLogger(NaturalPersonRemoveServiceImpl.class);

    @Autowired
    private NaturalPersonRemoveMapper naturalPersonRemoveMapper;

    @Override
    public Mapper<NaturalPersonRemove, Integer> getMapper() {
        return naturalPersonRemoveMapper;
    }

}