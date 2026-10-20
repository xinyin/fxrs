package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.app.sub.model.MidBusProc;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.workflow.wf.model.HisProcLog;
import com.yzj.jep.workflow.wf.model.ProcAutoTransfer;
import com.yzj.jep.workflow.wf.model.ProcInstData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title: ProcessMapper
 * @Author Whx
 * @Date: 2021/7/15 10:37
 * @Version 1.0
 */
@MyBatisMapper
public interface ProcessQueryMapper {

    List<HisProcLog> queryTaskImplByTaskId(@Param("taskId") String taskId, @Param("action") String action, @Param("nextTaskId") String nextTaskId);

    List<HisProcLog> queryHisProcLogByInsId(@Param("processInstantId") String processInstantId, @Param("action") String action, @Param("userid") String userid);

    ProcInstData queryProcInstDataByprocInstId(String procInstId);

    List<MidBusProc> queryMidData(String procInsId);

    List<HisProcLog> queryNextStafIsNotUser(@Param("processInstantId") String processInstantId, @Param("staffCode") String staffCode);

    List<ProcAutoTransfer> queryTrans(@Param("staffCode") String staffCode, @Param("logTimeString") String logTimeString);
}
