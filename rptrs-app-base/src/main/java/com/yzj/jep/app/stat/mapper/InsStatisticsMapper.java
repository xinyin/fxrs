package com.yzj.jep.app.stat.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.workflow.wf.model.HisProcInst;
import com.yzj.jep.workflow.wf.model.HisProcLog;
import com.yzj.jep.workflow.wf.model.ProcInstData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @title: InsStatisticsMapper
 * @Author Whx
 * @Date: 2021/7/12 17:00
 * @Version 1.0
 */
@MyBatisMapper
public interface InsStatisticsMapper {
    List<HisProcInst> queryInsByConditions(@Param("param") Map<String, Object> param);

    ProcInstData queryInsDataByProcInsId(String procId);

    List<Map> queryActTaskByInsId(String processInstantId);

    List<HisProcLog> queryProcLogByInsId(String processInstantId);

    List<Map<String, Object>> sumIns(@Param("param") Map<String, Object> param);
}
