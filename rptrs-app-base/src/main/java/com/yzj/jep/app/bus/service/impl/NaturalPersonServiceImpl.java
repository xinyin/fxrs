/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:37
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.NaturalPersonMapper;
import com.yzj.jep.app.bus.service.NaturalPersonService;
import com.yzj.jep.app.bus.model.NaturalPerson;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import java.util.List;

@Service
@Transactional
public class NaturalPersonServiceImpl extends MyBatisCRUDService<NaturalPerson, Integer> implements NaturalPersonService {

    Logger logger = LoggerFactory.getLogger(NaturalPersonServiceImpl.class);

    @Autowired
    private NaturalPersonMapper naturalPersonMapper;

    @Override
    public Mapper<NaturalPerson, Integer> getMapper() {
        return naturalPersonMapper;
    }

    @Override
    public List<NaturalPerson> serachNaturalByPid(Integer pid) {
        return naturalPersonMapper.serachNaturalByPid(pid);
    }
}