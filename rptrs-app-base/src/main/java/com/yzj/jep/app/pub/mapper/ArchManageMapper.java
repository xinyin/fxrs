/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月25日 15:19:35
 *
 */

package com.yzj.jep.app.pub.mapper;

import com.yzj.jep.app.pub.model.ArchManageVo;
import com.yzj.jep.app.pub.model.OaApprovalLog;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

import java.util.List;

/**
 * @author wll
 */
@MyBatisMapper
public interface ArchManageMapper extends Mapper<OaApprovalLog, Integer>{

    List<ArchManageVo> selectArchList();
}