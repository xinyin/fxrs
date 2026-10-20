/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月10日 18:04:44
 *
 */

package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.fil.mapper.FilRelProMapper;
import com.yzj.jep.app.fil.model.FilRelPro;
import com.yzj.jep.app.fil.model.FilRelProVo;
import com.yzj.jep.app.fil.service.FilRelProService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class FilRelProServiceImpl extends MyBatisCRUDService<FilRelPro, Integer> implements FilRelProService {

    Logger logger = LoggerFactory.getLogger(FilRelProServiceImpl.class);

    @Autowired
    private FilRelProMapper filRelProMapper;

    @Override
    public Mapper<FilRelPro, Integer> getMapper() {
        return filRelProMapper;
    }

    @Override
    public List<FilRelProVo> selectRelProOrg(String pid) {
        return filRelProMapper.selectRelProOrg(pid);
    }

    @Override
    public List<FilRelProVo> selectDownRelProOrg(String pid) {
        return filRelProMapper.selectDownRelProOrg(pid);
    }
}