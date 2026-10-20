package com.yzj.jep.app.pub.service.impl;

import com.yzj.jep.app.pub.mapper.PubFileTemplateMapper;
import com.yzj.jep.app.pub.model.PubFileTemplate;
import com.yzj.jep.app.pub.service.PubFileTemplateService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PubFileTemplateServiceImpl extends MyBatisCRUDService<PubFileTemplate, Integer> implements PubFileTemplateService {
    Logger logger = LoggerFactory.getLogger(PubFileTemplateServiceImpl.class);
    @Resource
    private PubFileTemplateMapper pubFileTemplateMapper;

    @Override
    public Mapper<PubFileTemplate, Integer> getMapper() {
        return pubFileTemplateMapper;
    }

    @Override
    public PubFileTemplate selectById(int pid) {
        return pubFileTemplateMapper.selectByPrimaryKey(pid);
    }
}
