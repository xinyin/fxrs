/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月13日 15:41:26
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.mapper.EastGLGXViewMapper;
import com.yzj.jep.app.sub.model.EastGLGXView;
import com.yzj.jep.app.sub.service.EastGLGXViewService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EastGLGXViewServiceImpl extends MyBatisCRUDService<EastGLGXView, Integer> implements EastGLGXViewService {

    Logger logger = LoggerFactory.getLogger(EastGLGXViewServiceImpl.class);
    @Autowired
    private EastGLGXViewMapper eastGLGXViewMapper;

    @Override
    public Mapper<EastGLGXView, Integer> getMapper() {
        return eastGLGXViewMapper;
    }
}