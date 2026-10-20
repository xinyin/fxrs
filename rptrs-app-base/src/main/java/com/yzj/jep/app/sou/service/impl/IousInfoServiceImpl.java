/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月07日 20:40:21
 *
 */

package com.yzj.jep.app.sou.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.sou.mapper.IousInfoMapper;
import com.yzj.jep.app.sou.service.IousInfoService;
import com.yzj.jep.app.sou.model.IousInfo;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class IousInfoServiceImpl extends MyBatisCRUDService<IousInfo, Integer> implements IousInfoService {

    Logger logger = LoggerFactory.getLogger(IousInfoServiceImpl.class);

    @Autowired
    private IousInfoMapper iousInfoMapper;

    @Override
    public Mapper<IousInfo, Integer> getMapper() {
        return iousInfoMapper;
    }

}