package com.yzj.jep.app.bus.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.service.ReturnTaskService;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.impl.engine.AbstractActiviti5WorkflowEngineAware;
import com.yzj.jep.workflow.impl.engine.Activiti5WorkflowEngine;
import com.yzj.jep.workflow.wf.model.ProcInst;
import com.yzj.jep.workflow.wf.model.ProcInstData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ReturnTaskServiceImpl extends AbstractActiviti5WorkflowEngineAware implements ReturnTaskService {
//    @Autowired
//    private WorkflowEngine workflowEngine;

    public ReturnTaskServiceImpl() {

    }

    public ReturnTaskServiceImpl(Activiti5WorkflowEngine workflowEngine) {
        super(workflowEngine);
    }

    @Override
    public Map<String, Object> getData(String taskId, String formData, String procInstId, String memo) {
        Map<String, Object> result = new HashMap<String, Object>();
        ProcInstData procInstData = this.workflowEngine.getProcInstDataService().get(procInstId);
        //数据
        JSONObject json = JSONObject.parseObject(procInstData.getFormData());
        ProcInst procInst = this.workflowEngine.getProcInstService().get(procInstId);
        //流程模板名称
        String code = procInst.getProcCode();
        result.put("code", code);
        result.put("data", json);
        return result;
    }
}
