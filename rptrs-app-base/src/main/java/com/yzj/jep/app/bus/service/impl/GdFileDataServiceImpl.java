/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 18:33:42
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.GdFileDataMapper;
import com.yzj.jep.app.bus.service.GdFileDataService;
import com.yzj.jep.app.bus.model.GdFileData;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import java.util.List;

@Service
@Transactional()
public class GdFileDataServiceImpl extends MyBatisCRUDService<GdFileData, Integer> implements GdFileDataService {

    Logger logger = LoggerFactory.getLogger(GdFileDataServiceImpl.class);

    @Autowired
    private GdFileDataMapper gdFileDataMapper;

    @Override
    public Mapper<GdFileData, Integer> getMapper() {
        return gdFileDataMapper;
    }

    @Override
    public List<GdFileData> getAllGdInfo(GdFileData gdFileData) {
        return gdFileDataMapper.getAllGdInfo(gdFileData);
    }

    @Override
    public void newFileBatch(GdFileData gdFileData) {
        gdFileDataMapper.newFileBatch(gdFileData);
    }
}