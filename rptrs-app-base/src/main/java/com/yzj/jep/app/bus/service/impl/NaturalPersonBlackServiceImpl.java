/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:52:10
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

import com.yzj.jep.app.bus.mapper.NaturalPersonBlackMapper;
import com.yzj.jep.app.bus.service.NaturalPersonBlackService;
import com.yzj.jep.app.bus.model.NaturalPersonBlack;

@Service
@Transactional(readOnly = true)
public class NaturalPersonBlackServiceImpl extends MyBatisCRUDService<NaturalPersonBlack, Integer> implements NaturalPersonBlackService {

    Logger logger = LoggerFactory.getLogger(NaturalPersonBlackServiceImpl.class);

    @Autowired
    private NaturalPersonBlackMapper naturalPersonBlackMapper;

    @Override
    public Mapper<NaturalPersonBlack, Integer> getMapper() {
        return naturalPersonBlackMapper;
    }

}