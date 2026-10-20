/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月29日 14:02:55
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.DocSubmit;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.DocSubmitMapper;
import com.yzj.jep.app.bus.service.DocSubmitService;

@Service
@Transactional(readOnly = true)
public class DocSubmitServiceImpl extends MyBatisCRUDService<DocSubmit, Integer> implements DocSubmitService {

    Logger logger = LoggerFactory.getLogger(DocSubmitServiceImpl.class);

    @Autowired
    private DocSubmitMapper docSubmitMapper;

    @Override
    public Mapper<DocSubmit, Integer> getMapper() {
        return docSubmitMapper;
    }

}