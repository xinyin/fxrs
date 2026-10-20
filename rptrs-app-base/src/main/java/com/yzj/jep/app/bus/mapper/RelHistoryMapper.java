/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.RelHistory;
import com.yzj.jep.app.bus.model.RelOrgHistory;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisMapper
public interface RelHistoryMapper extends Mapper<RelHistory, Integer> {
    /**
     * 获取所有的版本号
     * @return
     */
    List<String> recordNumber();

    List<RelHistory> queryRecordNumber(@Param("recordType") String recordType,@Param("recordNumber") String recordNumber);

    List<RelHistory> queryRecordNumberData(@Param("createTime") String createTime);

}
