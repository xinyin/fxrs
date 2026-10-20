package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.mapper.PersonViewMapper;
import com.yzj.jep.app.sub.mapper.ProductViewMapper;
import com.yzj.jep.app.sub.model.PersonView;
import com.yzj.jep.app.sub.model.ProductView;
import com.yzj.jep.app.sub.service.PersonViewService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yao
 */
@Service
@Transactional
public class PersonViewServiceImpl extends MyBatisCRUDService<PersonView,Integer> implements PersonViewService {
    Logger logger	= LoggerFactory.getLogger(PersonViewServiceImpl.class);

    @Autowired
    private PersonViewMapper personViewMapper;


    @Override
    public Mapper<PersonView, Integer> getMapper() {
        return personViewMapper;
    }
}
