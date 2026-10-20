/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月01日 15:10:59
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.GdTraZyMapper;
import com.yzj.jep.app.bus.service.GdTraZyService;
import com.yzj.jep.app.bus.model.GdTraZy;


@Service
@Transactional(readOnly = false)
public class GdTraZyServiceImpl extends MyBatisCRUDService<GdTraZy, Integer> implements GdTraZyService {

    Logger logger = LoggerFactory.getLogger(GdTraZyServiceImpl.class);

    @Autowired
    private GdTraZyMapper gdTraZyMapper;

    @Override
    public Mapper<GdTraZy, Integer> getMapper() {
        return gdTraZyMapper;
    }

    @Override
    public void del(Integer pid) {
        gdTraZyMapper.del(pid);
    }

    @Override
    public void approvalReturn(Integer pid) {
        GdTraZy gdTraZy = new GdTraZy();
        gdTraZy.setStatus("5");
        gdTraZy.setPid(pid);
        gdTraZyMapper.updateSelective(gdTraZy);
    }

}