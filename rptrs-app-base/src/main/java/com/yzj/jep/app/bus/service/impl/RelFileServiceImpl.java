/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月08日 18:30:22
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.RelFileMapper;
import com.yzj.jep.app.bus.model.RelFile;
import com.yzj.jep.app.bus.service.RelFileService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = false)
public class RelFileServiceImpl extends MyBatisCRUDService<RelFile, Integer> implements RelFileService {

    Logger logger = LoggerFactory.getLogger(RelFileServiceImpl.class);

    @Autowired
    private RelFileMapper relFileMapper;

    @Override
    public Mapper<RelFile, Integer> getMapper() {
        return relFileMapper;
    }

    @Override
    public void updateRelSerial(List<Integer> docTableSelectItem, String relSerial, String merge) {
        if (docTableSelectItem != null) {
            for (Integer pid : docTableSelectItem) {
                RelFile relFile = new RelFile();
                relFile.setPid(pid);
                relFile.setRelSerial(relSerial);
                relFile.setUploadAgency(merge);
                updateSelective(relFile);
            }
        }
    }
}