package com.yzj.jep.app.pub.service.impl;

import com.yzj.jep.app.pub.mapper.PublogYearMapper;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.PublogYearService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PublogYearServiceImpl extends MyBatisCRUDService<PublogRegularVo, Integer> implements PublogYearService {
    @Autowired
    private PublogYearMapper publogYearMapper;

    @Override
    public Mapper<PublogRegularVo, Integer> getMapper() {
        return publogYearMapper;
    }

    public List<PublogRegularVo> getYearList(Map<String,String> map){
        return publogYearMapper.getYearList(map.get("year"),map.get("startTime"),map.get("endTime"));
    }
}
