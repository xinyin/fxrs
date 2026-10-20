package com.yzj.jep.app.bus.service;

import com.yzj.jep.workflow.api.engine.WorkflowEngineAware;
import com.yzj.jep.workflow.api.model.IStaff;
import com.yzj.jep.workflow.wf.model.ProcInst;

import java.util.List;

public interface ApprovalProcessService extends WorkflowEngineAware {
    public List<ProcInst> getProInst(IStaff staff);
}
