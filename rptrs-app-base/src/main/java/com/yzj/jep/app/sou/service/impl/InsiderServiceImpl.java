/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月07日 20:40:20
 *
 */

package com.yzj.jep.app.sou.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.sou.mapper.InsiderMapper;
import com.yzj.jep.app.sou.service.InsiderService;
import com.yzj.jep.app.sou.model.Insider;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class InsiderServiceImpl extends MyBatisCRUDService<Insider, Integer> implements InsiderService {

    Logger logger = LoggerFactory.getLogger(InsiderServiceImpl.class);

    @Autowired
    private InsiderMapper insiderMapper;

    @Override
    public Mapper<Insider, Integer> getMapper() {
        return insiderMapper;
    }

}