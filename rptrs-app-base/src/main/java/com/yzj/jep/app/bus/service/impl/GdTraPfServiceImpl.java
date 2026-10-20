/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月02日 16:21:56
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.GdTraPfMapper;
import com.yzj.jep.app.bus.service.GdTraPfService;
import com.yzj.jep.app.bus.model.GdTraPf;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class GdTraPfServiceImpl extends MyBatisCRUDService<GdTraPf, Integer> implements GdTraPfService {

    Logger logger = LoggerFactory.getLogger(GdTraPfServiceImpl.class);

    @Autowired
    private GdTraPfMapper gdTraPfMapper;

    @Override
    public Mapper<GdTraPf, Integer> getMapper() {
        return gdTraPfMapper;
    }

    /*@Override
    public Integer queryMaxID() {

        return gdTraPfMapper.queryMaxID();
    }
*/
	/*@Override
	public void updateStatus(Integer pid) {
		gdTraPfMapper.updateStatus(pid);
	}*/
    @Override
    public void del(Integer pid) {

        gdTraPfMapper.del(pid);
    }

    @Override
    public void approvalReturn(Integer pid) {
        GdTraPf gdTraPf = new GdTraPf();
        gdTraPf.setStatus("5");
        gdTraPf.setPid(pid);
        gdTraPfMapper.updateSelective(gdTraPf);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer changeCreateTime(Date createTime, String year) {
        return gdTraPfMapper.changeCreateTime(createTime, year);
    }

    @Override
    public List<GdTraPf> selectGdTraPfExprot(String year) {
        return gdTraPfMapper.selectGdTraPfExprot(year);
    }
}