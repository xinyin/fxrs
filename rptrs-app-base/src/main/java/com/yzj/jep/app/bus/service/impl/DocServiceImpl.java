/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月20日 14:23:05
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.DocMapper;
import com.yzj.jep.app.bus.service.DocService;
import com.yzj.jep.app.bus.model.Doc;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class DocServiceImpl extends MyBatisCRUDService<Doc, Integer> implements DocService {

    Logger logger = LoggerFactory.getLogger(DocServiceImpl.class);

    @Autowired
    private DocMapper docMapper;

    @Override
    public Mapper<Doc, Integer> getMapper() {
        return docMapper;
    }


}