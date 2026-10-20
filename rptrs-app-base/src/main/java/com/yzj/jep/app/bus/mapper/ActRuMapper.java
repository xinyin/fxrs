
/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:31
 *
 */

package com.yzj.jep.app.bus.mapper;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import org.apache.ibatis.annotations.Param;

@MyBatisMapper
public interface ActRuMapper {

    /*
     * 删除流程任务
     * */
    int deleteRuTask(@Param("taskId")String taskId);
    int deleteRuIdentitylink(@Param("taskId")String taskId);
    /*
     *删除多于任务
     */
   /* int deleteRuTaskForProcInstId(@Param("procInstId")String taskId);
    int deleteRuIdentitylinkForProcInstId(@Param("procInstId")String taskId);*/
}