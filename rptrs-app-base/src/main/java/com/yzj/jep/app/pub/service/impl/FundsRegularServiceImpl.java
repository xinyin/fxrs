package com.yzj.jep.app.pub.service.impl;

import com.yzj.jep.app.pub.mapper.FundsRegularMapper;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.FundsRegularService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FundsRegularServiceImpl extends MyBatisCRUDService<PublogRegularVo, Integer> implements FundsRegularService {
    @Autowired
    private FundsRegularMapper fundsRegularMapper;

    public List<PublogRegularVo> getAll(){
        return fundsRegularMapper.getAll();
    }
    public List<PublogRegularVo> getConsiderTypeOne(){
        return fundsRegularMapper.getConsiderTypeOne();
    }
    @Override
    public Mapper<PublogRegularVo, Integer> getMapper() {
        return fundsRegularMapper;
    }
}
