package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.mapper.EastZDGLJYMXViewMapper;
import com.yzj.jep.app.sub.model.EastZDGLJYMXView;
import com.yzj.jep.app.sub.service.EastZDGLJYMXViewService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EastZDGLJYMXViewServiceImpl extends MyBatisCRUDService<EastZDGLJYMXView, Integer> implements EastZDGLJYMXViewService {
    Logger logger = LoggerFactory.getLogger(EastZDGLJYMXViewServiceImpl.class);
    @Autowired
    EastZDGLJYMXViewMapper eastZDGLJYMXViewMapper;

    @Override
    public Mapper<EastZDGLJYMXView, Integer> getMapper() {
        return eastZDGLJYMXViewMapper;
    }
}
