/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月02日 14:05:03
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.GdTraDjMapper;
import com.yzj.jep.app.bus.service.GdTraDjService;
import com.yzj.jep.app.bus.model.GdTraDj;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional()
public class GdTraDjServiceImpl extends MyBatisCRUDService<GdTraDj, Integer> implements GdTraDjService {

    Logger logger = LoggerFactory.getLogger(GdTraDjServiceImpl.class);

    @Autowired
    private GdTraDjMapper gdTraDjMapper;

    @Override
    public Mapper<GdTraDj, Integer> getMapper() {
        return gdTraDjMapper;
    }

    @Override
    public void approvalReturn(Integer pid) {
        GdTraDj gdTraDj = new GdTraDj();
        gdTraDj.setStatus("5");
        gdTraDj.setPid(pid);
        gdTraDjMapper.updateSelective(gdTraDj);
    }
}