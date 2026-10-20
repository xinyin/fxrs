/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月20日 11:49:15
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.DocFileMapper;
import com.yzj.jep.app.bus.service.DocFileService;
import com.yzj.jep.app.bus.model.DocFile;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DocFileServiceImpl extends MyBatisCRUDService<DocFile, Integer> implements DocFileService {

    Logger logger = LoggerFactory.getLogger(DocFileServiceImpl.class);

    @Autowired
    private DocFileMapper docFileMapper;

    @Override
    public Mapper<DocFile, Integer> getMapper() {
        return docFileMapper;
    }

    @Override
    public List<DocFile> getLimitTwo() {

        return docFileMapper.getLimitTwo();
    }
}