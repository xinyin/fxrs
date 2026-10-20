/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月10日 18:04:45
 *
 */

package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.bus.model.RelPsnVo;
import com.yzj.jep.app.fil.mapper.FilRelPsnMapper;
import com.yzj.jep.app.fil.model.FilRelPsn;
import com.yzj.jep.app.fil.model.FilRelPsnVo;
import com.yzj.jep.app.fil.service.FilRelPsnService;
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
public class FilRelPsnServiceImpl extends MyBatisCRUDService<FilRelPsn, Integer> implements FilRelPsnService {

    Logger logger = LoggerFactory.getLogger(FilRelPsnServiceImpl.class);

    @Autowired
    private FilRelPsnMapper filRelPsnMapper;

    @Override
    public Mapper<FilRelPsn, Integer> getMapper() {
        return filRelPsnMapper;
    }

    @Override
    public List<FilRelPsnVo> selectRelPsnInformation(String pid) {
        return filRelPsnMapper.selectRelPsnInformation(pid);
    }

    @Override
    public List<FilRelPsnVo> selectDownRelPsnInformation(String pid) {
        return filRelPsnMapper.selectDownRelPsnInformation(pid);
    }

    @Override
    public List<FilRelPsn> selectFilRelPsnByfalgPid(String flagPid) {
        return filRelPsnMapper.selectFilRelPsnByfalgPid(flagPid);
    }


}