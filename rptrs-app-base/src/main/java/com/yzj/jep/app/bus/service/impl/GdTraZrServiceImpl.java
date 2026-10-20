/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月01日 11:59:29
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.GdTraZrMapper;
import com.yzj.jep.app.bus.service.GdTraZrService;
import com.yzj.jep.app.bus.model.GdTraZr;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional()
public class GdTraZrServiceImpl extends MyBatisCRUDService<GdTraZr, Integer> implements GdTraZrService {

    Logger logger = LoggerFactory.getLogger(GdTraZrServiceImpl.class);

    @Autowired
    private GdTraZrMapper gdTraZrMapper;

    @Override
    public Mapper<GdTraZr, Integer> getMapper() {
        return gdTraZrMapper;
    }

    @Override
    public void approvalReturn(Integer pid) {
        GdTraZr gdTraZr = new GdTraZr();
        gdTraZr.setStatus("5");
        gdTraZr.setPid(pid);
        gdTraZrMapper.updateSelective(gdTraZr);
    }
}