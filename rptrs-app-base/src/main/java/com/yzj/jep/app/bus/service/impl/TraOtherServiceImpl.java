/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:01:35
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.TraOther;
import com.yzj.jep.app.bus.service.TraOtherService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.TraOtherMapper;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class TraOtherServiceImpl extends MyBatisCRUDService<TraOther, Integer> implements TraOtherService {

    Logger logger = LoggerFactory.getLogger(TraOtherServiceImpl.class);

    @Autowired
    private TraOtherMapper traOtherMapper;

    @Autowired
    private DictItemService dictItemService;

    @Override
    public Mapper<TraOther, Integer> getMapper() {
        return traOtherMapper;
    }

    @Override
    public Integer queryMaxID() {
        return traOtherMapper.queryMaxID();
    }

    @Override
    public void updateIsDel(Integer pid) {
        traOtherMapper.updateIsDel(pid);
    }

    @Override
    public TraOther selectByID(Integer pid) {
        return traOtherMapper.selectByID(pid);
    }

    @Override
    public void updateStatus(Integer pid) {
        traOtherMapper.updateStatus(pid);
    }

    @Override
    public String getFormat(String dict, String value) {
        DictItem dictItem = new DictItem();
        String name = null;
        dictItem.setDictCode(dict);
        dictItem.setItemValue(value);
        List<DictItem> select = dictItemService.select(dictItem);
        name = select.get(0).getItemLabel();
        return name;
    }

    @Override
    public BigDecimal getBalance(TraOther traOther) {
        return traOtherMapper.getBalance(traOther);
    }

    @Override
    public void approvalReturn(Integer pid) {
        TraOther traOther = new TraOther();
        traOther.setStatus("5");
        traOther.setPid(pid);
        traOtherMapper.updateSelective(traOther);
    }
}