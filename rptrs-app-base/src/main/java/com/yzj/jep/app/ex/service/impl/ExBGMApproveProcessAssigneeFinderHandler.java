package com.yzj.jep.app.ex.service.impl;

import java.util.HashSet;
import java.util.Set;

//import com.yzj.jep.app.pm.model.Project;
//import com.yzj.jep.app.pm.model.Projectstaff;
//import com.yzj.jep.app.pm.service.ProjectService;
//import com.yzj.jep.app.pm.service.ProjectstaffService;
//import com.yzj.jep.app.pub.model.Depbizsupport;
//import com.yzj.jep.app.pub.model.PubStaff;
//import com.yzj.jep.app.pub.service.DepbizsupportService;
//import com.yzj.jep.app.pub.service.PubStaffService;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.impl.assign.ProcessAssigneeFinderHandler;
import com.yzj.jep.workflow.impl.assign.annotation.AssigneeFinderHandler;

//@AssigneeFinderHandler(value = "zxManager", name = "直线经理")
public class ExBGMApproveProcessAssigneeFinderHandler implements ProcessAssigneeFinderHandler {

//	PubStaffService pubStaffService;
//	DepbizsupportService depbizsupportService;

    @Override
    public Set<String> find(ITaskInst pevTask, ITaskInst task, String data) throws WorkflowException {
//			pubStaffService = SpringContextHolder.getBean(PubStaffService.class);
//			depbizsupportService = SpringContextHolder.getBean(DepbizsupportService.class);

        Set<String> set = new HashSet<String>();
        JSONObject json = JSONObject.parseObject(data);
        String nextApprovalPeople = (String) json.get("nextApprovalPeople");
			/*String staffId = pevTask.getStarterCode();
			JSONObject json = JSONObject.parseObject(data);
//			String eid = UserProfileUtils.getUserProfile().getUnitCode();
			String eid = json.getString("eid");
			PubStaff staff  = new PubStaff();
			staff.setEid(eid);
			staff.setStaffId(staffId);
			PubStaff pubStaff = pubStaffService.get(staff);
			String depid = pubStaff.getDepId();
			if(depid != null) {
				List<Filter> filters = new Filter.Builder().eq("eid", eid).eq("depId", depid.substring(0, 4).trim()).eq("bizSupType","HEAD").build();
				List<Depbizsupport> depbizsupports = depbizsupportService.select(filters);
				if(depbizsupports != null && depbizsupports.size()>0) {
					Depbizsupport depbizsupport =  depbizsupports.get(0);
					set.add(depbizsupport.getStaffId());
				}
			}*/
        set.add(nextApprovalPeople);
        return set;
    }

}
