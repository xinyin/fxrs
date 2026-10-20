package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.MidBusProc;
import com.yzj.jep.app.sub.vo.HisProcInstVo;
import com.yzj.jep.app.sub.vo.TaskInstVo;
import com.yzj.jep.workflow.wf.model.HisProcInst;
import com.yzj.jep.workflow.wf.model.ProcInst;

import java.util.List;

/**
 * @title: ProcessService
 * @Author Whx
 * @Date: 2021/7/15 10:35
 * @Version 1.0
 */
public interface ProcessQueryService {
    List<HisProcInstVo> queryTrustDetail(List<HisProcInst> result);

    List<TaskInstVo> queryTaskType(List<ProcInst> result);

    List<MidBusProc> queryMidData(String procInsId);
}
