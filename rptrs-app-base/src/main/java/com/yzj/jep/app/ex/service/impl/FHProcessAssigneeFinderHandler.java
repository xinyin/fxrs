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

//@AssigneeFinderHandler(value = "glgxsp", name = "非会签关联关系以及关联交易")
public class FHProcessAssigneeFinderHandler implements ProcessAssigneeFinderHandler {

    TraServiceService traServiceService;

    UnitService unitService;

    private ApproveAssignerService approveAssignerService;

    private String topDeptCode;

    private static final HashMap<String,String> nodeKeyToRoleIdMap = new HashMap<>();

    static{
        nodeKeyToRoleIdMap.put("usertask2","101");//负责人
        nodeKeyToRoleIdMap.put("usertask3","102");//合规法律部成员
        nodeKeyToRoleIdMap.put("usertask4","103");//合规法律部负责人
        nodeKeyToRoleIdMap.put("usertask6","105");//关管办负责人
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

        String nodeKey  = task.getTaskKey();//取当前节点

        List<Staff> staffList = approveAssignerService.selectStaffByRoleStd(nodeKeyToRoleIdMap.get(nodeKey),json.getString("tenantId"));
        if("usertask2".equals(nodeKey)){
            getTopDept(deptCode);
            if(null != topDeptCode){
                deptCode = topDeptCode;
            }
        }
        for (Staff s :staffList){
            if("usertask2".equals(nodeKey)){
                getTopDept(s.getDeptCode());
                if(null != topDeptCode){
                    s.setDeptCode(topDeptCode);
                }
                //根据部门筛选
                if(deptCode.equals(s.getDeptCode())){
                    set.add(s.getStaffCode());
                }
            }else{
                //if(unitCode.equals(s.getUnitCode())){
                    set.add(s.getStaffCode());
                //}
            }
        }


        return set;
    }
    private void getTopDept(String deptCode){
        unitService = SpringContextHolder.getBean(UnitService.class);
        Unit unit = unitService.get(deptCode);
        if (!"UNIT".equals(unit.getKind())){
            topDeptCode = deptCode;
            getTopDept(unit.getParentId());
        }
    }
}
