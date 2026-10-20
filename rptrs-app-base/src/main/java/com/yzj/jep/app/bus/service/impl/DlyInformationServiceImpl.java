package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.DlyInformationMapper;
import com.yzj.jep.app.bus.model.DlyInformation;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.service.DlyInformationService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DlyInformationServiceImpl extends MyBatisCRUDService<DlyInformation, Integer>  implements DlyInformationService {
    @Autowired
    private DlyInformationMapper dlyInformationMapper;

//    查询页面信息
    @Override
    public List<DlyInformation> selectList(DlyInformation dlyInformation){
        List<DlyInformation> list=new ArrayList<>();
        list= dlyInformationMapper.selectList(dlyInformation);
        return  list;
    }
    @Override
    public Mapper<DlyInformation, Integer> getMapper() {
        return dlyInformationMapper;
    }


}
