/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:36
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.LegalPersonMapper;
import com.yzj.jep.app.bus.service.LegalPersonService;
import com.yzj.jep.app.bus.model.LegalPerson;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LegalPersonServiceImpl extends MyBatisCRUDService<LegalPerson, Integer> implements LegalPersonService {

    Logger logger = LoggerFactory.getLogger(LegalPersonServiceImpl.class);

    @Autowired
    private LegalPersonMapper legalPersonMapper;

    @Override
    public Mapper<LegalPerson, Integer> getMapper() {
        return legalPersonMapper;
    }

    @Override
    public List<LegalPerson> search(LegalPerson legalPerson) {
        return null;
    }
}