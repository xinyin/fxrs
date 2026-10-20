package com.yzj.jep.app.pub.service.impl;

import com.yzj.jep.app.pub.mapper.FundsReportMapper;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.FundsReportService;
import com.yzj.jep.app.pub.service.PublogReportService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FundsReportServiceImpl extends MyBatisCRUDService<PublogRegularVo, Integer> implements FundsReportService {
    @Autowired
    private FundsReportMapper fundsReportMapper;

    public List<PublogRegularVo> getAll(){
        return fundsReportMapper.getAll();

    }
    @Override
    public Mapper<PublogRegularVo, Integer> getMapper() {
        return fundsReportMapper;
    }

}
