/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月20日 11:53:28
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.DocRelMapper;
import com.yzj.jep.app.bus.service.DocRelService;
import com.yzj.jep.app.bus.model.DocRel;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class DocRelServiceImpl extends MyBatisCRUDService<DocRel, Integer> implements DocRelService {

    Logger logger = LoggerFactory.getLogger(DocRelServiceImpl.class);

    @Autowired
    private DocRelMapper docRelMapper;

    @Override
    public Mapper<DocRel, Integer> getMapper() {
        return docRelMapper;
    }

}