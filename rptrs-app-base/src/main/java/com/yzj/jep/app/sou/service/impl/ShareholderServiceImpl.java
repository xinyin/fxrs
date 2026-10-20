/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月07日 20:40:22
 *
 */

package com.yzj.jep.app.sou.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.sou.mapper.ShareholderMapper;
import com.yzj.jep.app.sou.service.ShareholderService;
import com.yzj.jep.app.sou.model.Shareholder;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class ShareholderServiceImpl extends MyBatisCRUDService<Shareholder, Integer> implements ShareholderService {

    Logger logger = LoggerFactory.getLogger(ShareholderServiceImpl.class);

    @Autowired
    private ShareholderMapper shareholderMapper;

    @Override
    public Mapper<Shareholder, Integer> getMapper() {
        return shareholderMapper;
    }

}