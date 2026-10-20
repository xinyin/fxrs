/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月16日 13:38:15
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

import com.yzj.jep.app.bus.mapper.SubmissionFileMapper;
import com.yzj.jep.app.bus.service.SubmissionFileService;
import com.yzj.jep.app.bus.model.SubmissionFile;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class SubmissionFileServiceImpl extends MyBatisCRUDService<SubmissionFile, Integer> implements SubmissionFileService {

    Logger logger = LoggerFactory.getLogger(SubmissionFileServiceImpl.class);

    @Autowired
    private SubmissionFileMapper submissionFileMapper;

    @Override
    public Mapper<SubmissionFile, Integer> getMapper() {
        return submissionFileMapper;
    }


    @Override
    public List<SubmissionFile> queryByCon(Map map) {
        List<SubmissionFile> list = submissionFileMapper.queryByCon(map);
        return list;
    }

    @Override
    public SubmissionFile queryByID(Integer pids) {
        return submissionFileMapper.queryByID(pids);
    }
}