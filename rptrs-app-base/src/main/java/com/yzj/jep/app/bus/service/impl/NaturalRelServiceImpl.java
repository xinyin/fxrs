/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月16日 16:45:59
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.NaturalRelMapper;
import com.yzj.jep.app.bus.model.NaturalRel;
import com.yzj.jep.app.bus.service.NaturalRelService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class NaturalRelServiceImpl extends MyBatisCRUDService<NaturalRel, Integer> implements NaturalRelService {

    Logger logger = LoggerFactory.getLogger(NaturalRelServiceImpl.class);

    @Autowired
    private NaturalRelMapper naturalRelMapper;

    @Override
    public Mapper<NaturalRel, Integer> getMapper() {
        return naturalRelMapper;
    }

}