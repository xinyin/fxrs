/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface RelOrgHistoryMapper extends Mapper<RelOrgHistory, Integer> {

    List<String> recordNumber();

    List<RelOrgHistory> queryRecordNumberData(@Param("createTime") String createTime);

    List<RelOrgHistory> queryRecordNumber(@Param("recordType")String recordType,@Param("recordNumber") String recordNumber);
}
