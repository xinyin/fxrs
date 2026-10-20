package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.fil.mapper.FilAuthApplyMapper;
import com.yzj.jep.app.fil.model.FilAuthApply;
import com.yzj.jep.app.fil.service.FilAuthApplyService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @title: FilAuthApplyServiceImpl
 * @Author Whx
 * @Date: 2021/8/13 9:02
 * @Version 1.0
 */
@Service
@Transactional(readOnly = true)
public class FilAuthApplyServiceImpl extends MyBatisCRUDService<FilAuthApply, Integer> implements FilAuthApplyService {

    @Autowired
    private FilAuthApplyMapper filAuthApplyMapper;

    @Override
    public Mapper<FilAuthApply, Integer> getMapper() {
        return filAuthApplyMapper;
    }
}
