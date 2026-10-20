/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月03日 17:22:32
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.MeetingManageMapper;
import com.yzj.jep.app.bus.service.MeetingManageService;
import com.yzj.jep.app.bus.model.MeetingManage;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class MeetingManageServiceImpl extends MyBatisCRUDService<MeetingManage, Integer> implements MeetingManageService {

    Logger logger = LoggerFactory.getLogger(MeetingManageServiceImpl.class);

    @Autowired
    private MeetingManageMapper meetingManageMapper;

    @Override
    public Mapper<MeetingManage, Integer> getMapper() {
        return meetingManageMapper;
    }

    @Override
    public int selectByname(String meetName) {
        int pid = meetingManageMapper.selectByname(meetName);
        return pid;
    }
}