/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 18:33:39
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.GdExportFileMapper;
import com.yzj.jep.app.bus.service.GdExportFileService;
import com.yzj.jep.app.bus.model.GdExportFile;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional()
public class GdExportFileServiceImpl extends MyBatisCRUDService<GdExportFile, Integer> implements GdExportFileService {

    Logger logger = LoggerFactory.getLogger(GdExportFileServiceImpl.class);

    @Autowired
    private GdExportFileMapper gdExportFileMapper;

    @Override
    public Mapper<GdExportFile, Integer> getMapper() {
        return gdExportFileMapper;
    }

    @Override
    public void approvalReturn(Integer pid) {
        GdExportFile gdExportFile = new GdExportFile();
        gdExportFile.setStatus("5");
        gdExportFile.setPid(pid);
        gdExportFileMapper.updateSelective(gdExportFile);
    }
}