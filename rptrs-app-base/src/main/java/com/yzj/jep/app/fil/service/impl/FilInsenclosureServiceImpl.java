/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月31日 15:57:15
 *
 */

package com.yzj.jep.app.fil.service.impl;


import com.yzj.jep.app.fil.mapper.FilInsenclosureMapper;
import com.yzj.jep.app.fil.model.FilInsenclosure;
import com.yzj.jep.app.fil.service.FilInsenclosureService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class FilInsenclosureServiceImpl extends MyBatisCRUDService<FilInsenclosure, Integer> implements FilInsenclosureService {

    Logger logger = LoggerFactory.getLogger(FilInsenclosureServiceImpl.class);

    @Autowired
    private FilInsenclosureMapper filInsenclosureMapper;

    @Override
    public Mapper<FilInsenclosure, Integer> getMapper() {
        return filInsenclosureMapper;
    }

}