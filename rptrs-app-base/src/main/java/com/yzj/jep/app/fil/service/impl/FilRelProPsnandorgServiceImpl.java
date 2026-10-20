/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月17日 15:30:13
 *
 */

package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.bus.model.RelProVo;
import com.yzj.jep.app.fil.mapper.FilRelProPsnandorgMapper;
import com.yzj.jep.app.fil.model.FilRelProPsnandorg;
import com.yzj.jep.app.fil.model.FilRelProVo;
import com.yzj.jep.app.fil.service.FilRelProPsnandorgService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class FilRelProPsnandorgServiceImpl extends MyBatisCRUDService<FilRelProPsnandorg, Integer> implements FilRelProPsnandorgService {

    Logger logger = LoggerFactory.getLogger(FilRelProPsnandorgServiceImpl.class);

    @Autowired
    private FilRelProPsnandorgMapper filRelProPsnandorgMapper;

    @Override
    public Mapper<FilRelProPsnandorg, Integer> getMapper() {
        return filRelProPsnandorgMapper;
    }

    //获取自然人归属上级金融产品
    @Override
    public List<FilRelProVo> selectRelProUpOfPsnInformation(String pid) {
        return filRelProPsnandorgMapper.selectRelProUpOfPsnInformation(pid);
    }

    //获取自然人关联的下级金融产品
    @Override
    public List<FilRelProVo> selectRelProDownOfPsnInformation(String pid) {
        return filRelProPsnandorgMapper.selectRelProDownOfPsnInformation(pid);
    }

}