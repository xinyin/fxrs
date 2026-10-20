/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年09月26日 10:09:25
 *
 */

package com.yzj.jep.app.workflow.web.controller;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IHisProcLog;
import com.yzj.jep.workflow.api.model.IProcDataLog;
import com.yzj.jep.workflow.api.model.IProcInstDataNotifyError;
import com.yzj.jep.workflow.api.model.IProcLog;
import com.yzj.jep.workflow.wf.model.ProcLog;

@Controller
@RequestMapping("/workflow/procLog")
public class ProcLogController extends CrudController<ProcLog> {
	private static Logger logger = LoggerFactory.getLogger(ProcLogController.class);

	@Autowired
	private WorkflowEngine workflowEngine;

	/**
	 * 获得操作记录
	 *
	 * @param
	 * @param procInstId
	 * @return
	 */
	@RequestMapping(value = "/runLog/{procInstId}")
	@ResponseBody
	public Map<String, Object> runLog(@PathVariable String procInstId) {
		Map<String, Object> tips = new HashMap<String, Object>();
		List<? extends IProcLog> logs = workflowEngine.getLogService().selectRunningProcLog(procInstId);
		StringBuilder sb = new StringBuilder();
		for (IProcLog log : logs) {
			sb.append(DateUtils.format(log.getLogTime(), DateUtils.DATE_TIME_FORMAT)).append(", ").append(log.getContent()).append("<br>");
		}
		tips.put(WebCode.SUCCESS, true);
		tips.put("logContent", sb.toString());
		return tips;
	}

	/**
	 * 获得历史操作记录
	 *
	 * @param
	 * @param procInstId
	 * @return
	 */

	@RequestMapping(value = "/hisLog/{procInstId}")
	@ResponseBody
	public Map<String, Object> hisLog(@PathVariable String procInstId) {
		Map<String, Object> tips = new HashMap<String, Object>();
		List<? extends IHisProcLog> logs = workflowEngine.getLogService().selectHistoryProcLog(procInstId);
		StringBuilder sb = new StringBuilder();
		String nbsp= "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>";
		sb.append("<table>");
		for (IHisProcLog log : logs) {
			sb.append("<tr>");
			String[] split = log.getContent().substring(0, log.getContent().indexOf(",办")).split(",");
			List<String> list = new ArrayList<>();
			for (String s : split) {
				String[] split1 = s.split(";");
				for (String s1 : split1) {
					String ss = nbsp+"<td>" + s1 + "</td>";
					list.add(ss);
				}
			}

			String join = StringUtils.join(list, "");

			sb.append("<td>").append(DateUtils.format(log.getLogTime(), DateUtils.DATE_TIME_FORMAT)).append("</td>").append(join).append("</tr>");
		}
		sb.append("</table>");

		tips.put("logContent", sb.toString());
		tips.put(WebCode.SUCCESS, true);
		return tips;
	}

	/**
	 * 获得历史操作记录
	 *
	 * @param model
	 * @param procInstId
	 * @return
	 */
	@RequestMapping(value = "/notifyDataErrorLog/{procInstId}")
	@ResponseBody
	public Map<String, Object> notifyDataErrorLog(@PathVariable String procInstId) {
		Map<String, Object> tips = new HashMap<String, Object>();
		List<? extends IProcInstDataNotifyError> logs = workflowEngine.getLogService().selectProcInstDataNotifyError(procInstId);
		StringBuilder sb = new StringBuilder();
		for (IProcInstDataNotifyError log : logs) {
			sb.append(DateUtils.format(log.getNotifyTime(), DateUtils.DATE_TIME_FORMAT)).append(", 通知次数：").append(log.getNotifyErrorTimes()).append(", 错误内容：").append(log.getErrorContent()).append("<br>");
		}
		tips.put("logContent", sb.toString());
		tips.put(WebCode.SUCCESS, true);
		return tips;
	}


	/**
	 * 获得历史操作记录
	 *
	 * @param model
	 * @param procInstId
	 * @return
	 */
	@RequestMapping(value = "/dataChangeLog/{procInstId}")
	@ResponseBody
	public Map<String, Object> dataChangeLog(@PathVariable String procInstId) {
		Map<String, Object> tips = new HashMap<String, Object>();
		List<? extends IProcDataLog> logs = workflowEngine.getLogService().selectProcDataLog(procInstId);
		tips.put("logContent", logs);
		tips.put(WebCode.SUCCESS, true);
		return tips;
	}


}