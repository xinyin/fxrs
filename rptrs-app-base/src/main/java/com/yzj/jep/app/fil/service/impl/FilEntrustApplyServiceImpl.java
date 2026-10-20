package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.fil.mapper.FilEntrustApplyMapper;
import com.yzj.jep.app.fil.model.FilEntrustApply;
import com.yzj.jep.app.fil.service.FilEntrustApplyService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @title: FilEntrustApplyServiceImpl
 * @Author Whx
 * @Date: 2021/8/13 9:57
 * @Version 1.0
 */
@Service
@Transactional(readOnly = true)
public class FilEntrustApplyServiceImpl extends MyBatisCRUDService<FilEntrustApply, Integer> implements FilEntrustApplyService {

    @Autowired
    private FilEntrustApplyMapper filEntrustApplyMapper;

    @Override
    public Mapper<FilEntrustApply, Integer> getMapper() {
        return filEntrustApplyMapper;
    }
}
