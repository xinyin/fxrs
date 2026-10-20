/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年06月28日 16:09:20
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.bus.model.MsgDeploy;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@MyBatisMapper
public interface MsgDeployMapper extends Mapper<MsgDeploy, Integer>{
    public List<MsgDeploy> selectReleaseMsg();
    public List<MsgDeploy> selectReleaseNews();
}