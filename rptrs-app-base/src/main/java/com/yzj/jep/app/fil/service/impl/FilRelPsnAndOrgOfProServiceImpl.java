/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:27
 *
 */

package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.bus.mapper.DelRelMapper;
import com.yzj.jep.app.bus.mapper.RelOrgMapper;
import com.yzj.jep.app.bus.mapper.RelPsnAndOrgOfProMapper;
import com.yzj.jep.app.bus.mapper.RelPsnMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.bus.service.RelPsnAndOrgOfProService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.vo.Neo4jRelVo;
import com.yzj.jep.app.fil.mapper.FilRelPsnAndOrgOfProMapper;
import com.yzj.jep.app.fil.model.FilRelProVo;
import com.yzj.jep.app.fil.model.FilRelPsnAndOrgOfPro;
import com.yzj.jep.app.fil.service.FilRelPsnAndOrgOfProService;
import com.yzj.jep.app.util.Neo4jUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class FilRelPsnAndOrgOfProServiceImpl extends MyBatisCRUDService<FilRelPsnAndOrgOfPro, Integer> implements FilRelPsnAndOrgOfProService {

    @Autowired
    private FilRelPsnAndOrgOfProMapper filRelPsnAndOrgOfProMapper;

    @Override
    public Mapper<FilRelPsnAndOrgOfPro, Integer> getMapper() {
        return null;
    }

    @Override
    public List<FilRelProVo> selectRelProUpOfOrgInformation(String pid) {
        return filRelPsnAndOrgOfProMapper.selectRelProUpOfOrgInformation(pid);
    }

    @Override
    public List<FilRelProVo> selectRelProDownOfOrgInformation(String pid) {
        return filRelPsnAndOrgOfProMapper.selectRelProDownOfOrgInformation(pid);
    }


}