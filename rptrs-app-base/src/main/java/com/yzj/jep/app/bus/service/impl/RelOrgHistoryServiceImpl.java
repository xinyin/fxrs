/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.mapper.RelOrgHistoryMapper;
import com.yzj.jep.app.bus.mapper.RelOrgMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgHistoryService;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.bus.vo.Neo4jRelVo;
import com.yzj.jep.app.sys.web.controller.XlsieController;
import com.yzj.jep.app.util.Neo4jUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service(value = "RelOrgHistoryServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class RelOrgHistoryServiceImpl extends MyBatisCRUDService<RelOrgHistory, Integer> implements RelOrgHistoryService {

    Logger logger = LoggerFactory.getLogger(RelOrgHistoryServiceImpl.class);

    @Autowired
    private RelOrgHistoryMapper relOrgHistoryMapper;

    @Override
    public Mapper<RelOrgHistory, Integer> getMapper() {
        return relOrgHistoryMapper;
    }

    @Override
    public List<RelOrgHistory> queryRecordNumberData(String createTime) {
        return relOrgHistoryMapper.queryRecordNumberData(createTime);
    }

    @Override
    public List<RelOrgHistory> queryRecordNumber(String recordType, String recordNumber) {
        String TD="%";
        if(TD.equals(recordNumber)){
            List<String> strings = relOrgHistoryMapper.recordNumber();
            if(strings.size()>0){
                recordNumber = strings.get(0);
            }else {
                recordNumber =null;
            }
        }
        if("5".equals(recordType)){
            recordType =null;
        }
        return relOrgHistoryMapper.queryRecordNumber(recordType,recordNumber);
    }

    @Override
    public List<String> recordNumber() {
        return relOrgHistoryMapper.recordNumber();
    }
}
