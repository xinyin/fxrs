package com.yzj.jep.app.pub.service.impl;

import com.yzj.jep.app.pub.mapper.PublogReportMapper;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.PublogReportService;
import com.yzj.jep.app.sub.mapper.SubmittedMapper;
import com.yzj.jep.app.sub.vo.SubmittedVo;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PublogReportServiceImpl extends MyBatisCRUDService<PublogRegularVo, Integer> implements PublogReportService {
    @Autowired
    private PublogReportMapper publogReportMapper;

    public List<PublogRegularVo> getAll(){
        return publogReportMapper.getAll();

    }
    @Override
    public Mapper<PublogRegularVo, Integer> getMapper() {
        return publogReportMapper;
    }
}
