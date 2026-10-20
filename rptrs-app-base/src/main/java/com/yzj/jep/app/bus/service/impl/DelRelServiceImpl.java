/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月22日 11:51:25
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.DelRelMapper;
import com.yzj.jep.app.bus.model.DelRel;
import com.yzj.jep.app.bus.service.DelRelService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DelRelServiceImpl extends MyBatisCRUDService<DelRel, Integer> implements DelRelService {

    Logger logger = LoggerFactory.getLogger(DelRelServiceImpl.class);

    @Autowired
    private DelRelMapper delRelMapper;

    @Override
    public Mapper<DelRel, Integer> getMapper() {
        return delRelMapper;
    }

    @Override
    public boolean selectByRelId(Integer relPid) {
        DelRel delRel = delRelMapper.selectByRelId(relPid);
        if (delRel == null) {
            return true;
        }
        return false;
    }
}