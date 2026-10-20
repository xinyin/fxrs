package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.RelImportMapper;
import com.yzj.jep.app.bus.model.RelImport;
import com.yzj.jep.app.bus.service.RelImportService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RelImportServiceImpl extends MyBatisCRUDService<RelImport, Integer> implements RelImportService {
    @Autowired
    private RelImportMapper relImportMapper;

    @Override
    public Mapper<RelImport, Integer> getMapper() {
        return relImportMapper;
    }

    public void clearTable(){
        relImportMapper.clearTable();
    }
    public int selectCountByToIdentifyNumber(String identifyNumber){
        return relImportMapper.selectCountByToIdentifyNumber(identifyNumber);
    }

}
