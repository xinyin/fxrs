package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.mapper.EastGljyhzbViewMapper;
import com.yzj.jep.app.sub.model.EastGljyhzbView;
import com.yzj.jep.app.sub.service.EastGLJYHZViewService;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EastGLJYHZViewServiceImpl extends MyBatisCRUDService<EastGljyhzbView,Integer> implements EastGLJYHZViewService {
    @Autowired
    EastGljyhzbViewMapper eastGljyhzbViewMapper;
    Logger logger = LoggerFactory.getLogger(EastGLJYHZViewServiceImpl.class);
    @Override
    public Mapper<EastGljyhzbView, Integer> getMapper() {
        return eastGljyhzbViewMapper;
    }


    @Override
    public List<EastGljyhzbView> queryOne(String id) {

        List<EastGljyhzbView> queryOne = eastGljyhzbViewMapper.queryOne(id);
        return queryOne;
    }
}
