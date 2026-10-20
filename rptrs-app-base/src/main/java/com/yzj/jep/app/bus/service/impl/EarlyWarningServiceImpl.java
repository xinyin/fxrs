package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.EarlyWarningMapper;
import com.yzj.jep.app.bus.model.EarlyWarning;
import com.yzj.jep.app.bus.service.EarlyWarningService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EarlyWarningServiceImpl extends MyBatisCRUDService<EarlyWarning,Integer> implements EarlyWarningService {
    @Autowired
    private EarlyWarningMapper earlyWarningMapper;
    @Override
    public Mapper<EarlyWarning, Integer> getMapper() {
        return earlyWarningMapper;
    }
}
