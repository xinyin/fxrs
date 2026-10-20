/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:31
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.RelHistoryMapper;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelHistory;
import com.yzj.jep.app.bus.service.RelHistoryService;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional(readOnly = false)
public class RelHistoryServiceImpl extends MyBatisCRUDService<RelHistory, Integer> implements RelHistoryService {

    @Autowired
    private RelHistoryMapper relHistoryMapper;

    @Override
    public Mapper<RelHistory, Integer> getMapper() {
        return relHistoryMapper;
    }

    @Override
    public void saveHistory(Rel rel, String type) {
        RelHistory relHistory = new RelHistory();
        BeanUtils.copyProperties(rel, relHistory);
        relHistory.setPid(null);
        relHistory.setRecordType(Integer.valueOf(type));
        relHistory.setSourceId(rel.getPid());
        relHistory.setDelflag(Dict.SF_1);
        relHistory.setCreateTime(new Date());
        this.insertSelective(relHistory);
    }

    @Override
    public List<String> recordNumber() {
        List<String> strings = relHistoryMapper.recordNumber();
        return strings;
    }

    @Override
    public List<RelHistory> queryRecordNumber(String recordType, String recordNumber) {
        String TD="%";
        if(TD.equals(recordNumber)){
            List<String> strings = this.recordNumber();
            if(strings.size()>0){
                recordNumber = strings.get(0);
            }else {
                recordNumber =null;
            }
        }
        if("5".equals(recordType)){
            recordType =null;
        }
        return relHistoryMapper.queryRecordNumber(recordType,recordNumber);
    }

    @Override
    public List<RelHistory> queryRecordNumberData(String createTime) {
        return relHistoryMapper.queryRecordNumberData(createTime);
    }
}
