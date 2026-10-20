/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年09月19日 11:15:38
 *
 */

package com.yzj.jep.app.workflow.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IStaff;
import com.yzj.jep.workflow.wf.model.HisProcInst;

/**
 * 流程实例表
 * @author yinke.hou
 * @time 2017年9月25日 下午5:29:14
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/workflow/monitor")
public class MonitorController extends CrudController<HisProcInst>{
	Logger logger = LoggerFactory.getLogger(MonitorController.class);

	@Autowired 
	private WorkflowEngine workflowEngine;
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		return "wf/monitorList";
	}
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/doneList")
	public String doneList(Model model) {
		return "wf/monitorDoneList";
	}
	
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/noCandidateList")
	public String noCandidateList(Model model) {
		return "wf/monitorNoCandidateList";
	}
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/longTimeList")
	public String longTimeList(Model model) {
		return "wf/monitorLongTimeList";
	}
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/notifyDataErrorList")
	public String notifyDataErrorList(Model model) {
		return "wf/monitorNotifyDataErrorList";
	}
	
	/**
	 * 获得历史操作记录
	 * @param model
	 * @param procInstId
	 * @return
	 */
	@RequestMapping(value = "/candidates/{procInstId}/{taskId}")
	@ResponseBody
	public Map<String, Object> candidates(@PathVariable String procInstId, @PathVariable String taskId) {
		Map<String, Object> tips = new HashMap<String, Object>();
		List<IStaff> candidates = workflowEngine.getRuntimeService().selectCandidate(procInstId, taskId);
		
		StringBuilder sb = new StringBuilder();
		if(!CollectionUtils.isEmpty(candidates)){
			sb.append("该任务下一步处理人[");
			int flag = 0;
			for(IStaff staff : candidates){
				sb.append(staff.getStaffName());
				if(flag > 0){
					sb.append(",");
				}
				
				flag++;
			}
		}
		else
		{
			sb.append("该任务下一步处理人为空，请及时处理。");
		}
		tips.put("candidates", sb.toString());
		tips.put(WebCode.SUCCESS, true);
		return tips;
	}
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/monitorCenter")
	public String monitorCenter(Model model) {
		return "wf/monitorCenter";
	}
	
}