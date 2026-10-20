package com.yzj.jep.app.pub.service.impl;

import com.yzj.jep.app.pub.mapper.PublogRegularMapper;
import com.yzj.jep.app.pub.model.PubFileTemplate;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.PublogRegularService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PublogRegularServiceImpl extends MyBatisCRUDService<PublogRegularVo, Integer>  implements PublogRegularService {

    @Autowired
    private PublogRegularMapper publogRegularMapper;

    public List<PublogRegularVo> getAll(){
        return publogRegularMapper.getAll();
    }
    @Override
    public Mapper<PublogRegularVo, Integer> getMapper() {
        return publogRegularMapper;
    }
}
