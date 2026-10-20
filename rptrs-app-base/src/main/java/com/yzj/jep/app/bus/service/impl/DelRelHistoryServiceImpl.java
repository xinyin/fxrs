package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.DelRelHistoryMapper;
import com.yzj.jep.app.bus.model.DelRelHistory;
import com.yzj.jep.app.bus.service.DelRelHistoryService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DelRelHistoryServiceImpl extends MyBatisCRUDService<DelRelHistory, Integer> implements DelRelHistoryService {
    Logger logger = LoggerFactory.getLogger(DelRelHistoryServiceImpl.class);

    @Autowired
    private DelRelHistoryMapper delRelHistoryMapper;

    @Override
    public Mapper<DelRelHistory, Integer> getMapper() {
        return delRelHistoryMapper;
    }

    @Override
    public boolean selectByRelId(Integer relPid) {
        DelRelHistory delRelHistory = delRelHistoryMapper.selectByRelId(relPid);
        if (delRelHistory == null) {
            return true;
        }
        return false;
    }
}
