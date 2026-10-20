/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:31
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.RelPsnHistoryMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelPsnHistoryService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class RelPsnHistoryServiceImpl extends MyBatisCRUDService<RelPsnHistory, Integer> implements RelPsnHistoryService {

    @Autowired
    private RelPsnHistoryMapper relPsnHistoryMapper;

    @Override
    public Mapper<RelPsnHistory, Integer> getMapper() {
        return relPsnHistoryMapper;
    }


    /**
     * 根据时点日期查询出所有的数据
     *
     * @param createTime
     * @return
     */
    @Override
    public List<RelPsnHistory> queryRecordNumberData(String createTime) {
        return relPsnHistoryMapper.queryRecordNumberData(createTime);
    }

    @Override
    public List<String> recordNumber() {
        return relPsnHistoryMapper.recordNumber();
    }

    @Override
    public List<RelPsnHistory> queryRecordNumber(String recordType, String recordNumber) {
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
        return relPsnHistoryMapper.queryRecordNumber(recordType,recordNumber);
    }
}
