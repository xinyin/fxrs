/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 16:13:55
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.yzj.jep.app.bus.mapper.MeetingFileMapper;
import com.yzj.jep.app.bus.service.MeetingFileService;
import com.yzj.jep.app.bus.model.MeetingFile;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class MeetingFileServiceImpl extends MyBatisCRUDService<MeetingFile, Integer> implements MeetingFileService {

    Logger logger = LoggerFactory.getLogger(MeetingFileServiceImpl.class);

    @Autowired
    private MeetingFileMapper meetingFileMapper;


    @Override
    public Mapper<MeetingFile, Integer> getMapper() {

        return meetingFileMapper;
    }

    @Override
    public MeetingFile selectByPid(Integer filepid) {
        MeetingFile meetingFile = meetingFileMapper.selectByPrimaryKey(filepid);

        return meetingFile;
    }
}