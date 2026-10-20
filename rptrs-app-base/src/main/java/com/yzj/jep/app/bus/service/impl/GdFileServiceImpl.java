/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月03日 17:27:56
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.GdFileMapper;
import com.yzj.jep.app.bus.service.GdFileService;
import com.yzj.jep.app.bus.model.GdFile;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional()
public class GdFileServiceImpl extends MyBatisCRUDService<GdFile, Integer> implements GdFileService {

    Logger logger = LoggerFactory.getLogger(GdFileServiceImpl.class);

    @Autowired
    private GdFileMapper gdFileMapper;

    @Override
    public Mapper<GdFile, Integer> getMapper() {
        return gdFileMapper;
    }

}