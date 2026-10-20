/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月16日 13:38:15
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.SubmissionFile;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.entity.PermitInfo;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface SubmissionFileMapper extends Mapper<SubmissionFile, Integer> {

    List<SubmissionFile> queryByCon(Map map);

    SubmissionFile queryByID(Integer pids);
}


