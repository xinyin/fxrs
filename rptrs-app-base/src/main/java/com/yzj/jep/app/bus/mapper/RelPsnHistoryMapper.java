
/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:31
 *
 */

package com.yzj.jep.app.bus.mapper;


import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface RelPsnHistoryMapper extends Mapper<RelPsnHistory, Integer> {

    List<RelPsnHistory> queryRecordNumberData(@Param("createTime") String createTime);

    List<String> recordNumber();

    List<RelPsnHistory> queryRecordNumber(@Param("recordType")String recordType,@Param("recordNumber") String recordNumber);
}
