package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.service.TraServiceService;
import com.yzj.jep.app.ins.service.ApproveAssignerService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.impl.assign.ProcessAssigneeFinderHandler;
import com.yzj.jep.workflow.impl.assign.annotation.AssigneeFinderHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//关联关系关联交易非会签节点进行人员筛选处理。

//@AssigneeFinderHandler(value = "YSGLFAll", name = "疑似关联方自定义")
public class YSGLFProcessAssigneeFinderHandler implements ProcessAssigneeFinderHandler {

    TraServiceService traServiceService;

    UnitService unitService;

    private ApproveAssignerService approveAssignerService;

    private String topDeptCode;

    private static final HashMap<String,String> nodeKeyToRoleIdMap = new HashMap<>();

    static{
        nodeKeyToRoleIdMap.put("usertask2","103");//合规法律部负责人
    }

    @Override
    public Set<String> find(ITaskInst pevTask, ITaskInst task, String data) throws WorkflowException {
        approveAssignerService = SpringContextHolder.getBean(ApproveAssignerService.class);
        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        unitService = SpringContextHolder.getBean(UnitService.class);

        Set<String> set = new HashSet<String>();
        JSONObject json = JSONObject.parseObject(data);
        //获取发起人部门和机构编码
        String  unitCode = pevTask.getStarterUnitCode();
        String  deptCode = pevTask.getStarterDeptCode();

        String nodeKey  = task.getTaskKey();

        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd(nodeKeyToRoleIdMap.get(nodeKey),json.getString("tenantId"));

        for (Staff s:staffList) {
            set.add(s.getStaffCode());
        }

        return set;
    }
}
