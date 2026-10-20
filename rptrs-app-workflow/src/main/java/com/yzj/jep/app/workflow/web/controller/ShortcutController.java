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

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.security.token.InvalidTokenException;
import com.yzj.jep.core.security.token.Token;
import com.yzj.jep.core.security.token.TokenExpiredException;
import com.yzj.jep.core.security.token.TokenService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.mail.IEmailSendService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.api.code.RenderType;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import com.yzj.jep.workflow.wf.model.ProcInst;

/**
 * 工作流快捷操作controller
 * @author liuy
 *
 */
@Controller
@RequestMapping("/workflow/shortcut")
public class ShortcutController{
	Logger logger = LoggerFactory.getLogger(ShortcutController.class);
	/**
	 * 结果中的记录总数
	 */
	protected static final String RESULT_TOTAL = "total";
	
	/**
	 * 结果中的记录行数
	 */
	protected static final String RESULT_ROWS = "rows";
	

	@Autowired 
	private WorkflowEngine workflowEngine;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	IEmailSendService emailSendService;
	
	
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/approve")
	public String approve(Model model, String token, String staffCode,  String staffName) {
		try
		{
			//此处的逻辑为：获得当前的任务ID，查询当前任务是否已经被别人领取或者处理，
			//如果没有，就直接领取并完成，并返回处理结果
			//此处的逻辑为：将流程push到executor中执行，返回true
			Token t = tokenService.decode(token);
			String taskId = t.getId();
			String procInstId = (String) t.getProperty("procInstId");
			StaffImpl staff = new StaffImpl();
			staff.setStaffCode(staffCode);
			staff.setStaffName(staffName);
			Map<String, Object> tip = workflowEngine.getRuntimeService().process(taskId, procInstId, staff, true);
			model.addAttribute(WebCode.SUCCESS, tip.get(WebCode.SUCCESS));
			model.addAttribute(WebCode.MSG, tip.get(WebCode.MSG));
			model.addAttribute("procInstId", procInstId);
			model.addAttribute("taskId", taskId);
			return "wf/taskProcessResult";
		} catch (InvalidTokenException e) {
			model.addAttribute(WebCode.SUCCESS, false);
			model.addAttribute(WebCode.MSG, "无效的令牌。");
		}
		catch (TokenExpiredException e) {
			UserProfile user = UserProfileUtils.getUserProfile();
			if(user != null){
				model.addAttribute(WebCode.MSG, "令牌已过期，请进入我的审批页面进行审批。");
			}
			else
			{
				model.addAttribute(WebCode.MSG, "令牌已过期，请登录系统进行处理。");
			}
			model.addAttribute(WebCode.SUCCESS, false);
		}
		catch (SecurityException e) {
			model.addAttribute(WebCode.SUCCESS, false);
			model.addAttribute(WebCode.MSG, "系统错误：" + e.getMessage());
		}
		return "wf/workflowError";
	}
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/reject")
	public String reject(Model model, String token, String staffCode,  String staffName) {
		try
		{
			//此处的逻辑为：将流程push到executor中执行，返回true
			Token t = tokenService.decode(token);
			String taskId = t.getId();
			String procInstId = (String) t.getProperty("procInstId");
			StaffImpl staff = new StaffImpl();
			staff.setStaffCode(staffCode);
			staff.setStaffName(staffName);
			Map<String, Object> tip = workflowEngine.getRuntimeService().process(taskId, procInstId, staff, false);
			model.addAttribute(WebCode.SUCCESS, tip.get(WebCode.SUCCESS));
			model.addAttribute(WebCode.MSG, tip.get(WebCode.MSG));
			model.addAttribute("procInstId", procInstId);
			model.addAttribute("taskId", taskId);
			return "wf/taskProcessResult";
		} catch (InvalidTokenException e) {
			model.addAttribute(WebCode.SUCCESS, false);
			model.addAttribute(WebCode.MSG, "无效的令牌。");
		}
		catch (TokenExpiredException e) {
			model.addAttribute(WebCode.SUCCESS, false);
			UserProfile user = UserProfileUtils.getUserProfile();
			if(user != null){
				model.addAttribute(WebCode.MSG, "令牌已过期，请进入我的审批页面进行审批。");
			}
			else
			{
				model.addAttribute(WebCode.MSG, "令牌已过期，请登录系统进行处理。");
			}
		}
		catch (SecurityException e) {
			model.addAttribute(WebCode.SUCCESS, false);
			model.addAttribute(WebCode.MSG, "系统错误：" + e.getMessage());
		}
		return "wf/workflowError";
	}
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/view")
	public String view(Model model, String token) {
		try
		{
			//此处的逻辑为：获得当前的任务ID，查询当前任务数据
			Token t = tokenService.decode(token);
			String taskId = t.getId();
			String procInstId = (String) t.getProperty("procInstId");
			//获取表单内容
			String content = "表单已处理完毕。";
			try
			{
				/*获得当前业务流程*/
				Map<String, Object> map = workflowEngine.getFormService().getFormContent(procInstId, taskId, RenderType.EDIT);
				if((boolean) map.get("isBusiness")){
					model.addAttribute("procInstId", procInstId);
					model.addAttribute("taskId", taskId);
					model.addAttribute("clientAdd", map.get("clientAdd"));
					model.addAttribute("token", map.get("token"));
					model.addAttribute("data", map.get("data"));
					return "wf/taskTodoBusinessViewList";
				}
				else
				{
					//获取表单内容
					content = (String) map.get("content");
					model.addAttribute("content", content);
					model.addAttribute("procInstId", procInstId);
					model.addAttribute("taskId", taskId);
				}
			}
			catch (Exception e) {
			}
			model.addAttribute("content", content);
			model.addAttribute("procInstId", procInstId);
			model.addAttribute("taskId", taskId);
			return "wf/taskTodoViewList";
		} catch (InvalidTokenException e) {
			model.addAttribute(WebCode.SUCCESS, false);
			model.addAttribute(WebCode.MSG, "无效的令牌。");
		}
		catch (TokenExpiredException e) {
			model.addAttribute(WebCode.SUCCESS, false);
			UserProfile user = UserProfileUtils.getUserProfile();
			if(user != null){
				model.addAttribute(WebCode.MSG, "令牌已过期，请进入我的审批页面进行审批。");
			}
			else
			{
				model.addAttribute(WebCode.MSG, "令牌已过期，请登录系统进行处理。");
			}
		}
		catch (SecurityException e) {
			model.addAttribute(WebCode.SUCCESS, false);
			model.addAttribute(WebCode.MSG, "系统错误：" + e.getMessage());
		}
		return "wf/workflowError";
	}
	
	
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/viewData")
	@ResponseBody
	public  Map<String, Object>  viewData(Page<ProcInst> page, Model model, HttpServletRequest request, String procInstId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Filter> filters = RequestUtils.buildFilters(request);
		List<Order> orders = RequestUtils.buildOrders(request);
		orders.add(new Order("startTime", OrderType.DESC));
		filters.add(new Filter("id", procInstId));
		workflowEngine.getHistoryService().queryProcInst(page, filters, orders);
		result.put(RESULT_TOTAL, page.getTotal());
		result.put(RESULT_ROWS, page.getResult());
		return result;
	}
	/**
	 * 获取待审批列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/process")
	@ResponseBody
	public  Map<String, Object> process(Model model, String token) {
		Map<String, Object> data = new HashMap<String, Object>();
		try
		{
			//此处的逻辑为：调用一个直接处理的方法，
//			先查询这条数据是否已经被处理，如果已经被处理，就给出对应的提示
			Token t = tokenService.decode(token);
			String taskId = t.getId();
			String procInstId = (String) t.getProperty("procInstId");
			String staffCode = (String) t.getProperty("staffCode");
			String staffName = (String) t.getProperty("staffName");
			StaffImpl staff = new StaffImpl();
			staff.setStaffCode(staffCode);
			staff.setStaffName(staffName);
			Map<String, Object> tip = workflowEngine.getRuntimeService().process(taskId, procInstId, staff, false);
			return tip;
		} catch (InvalidTokenException e) {
			model.addAttribute(WebCode.SUCCESS, false);
			model.addAttribute(WebCode.MSG, "无效的令牌。");
		}
		catch (TokenExpiredException e) {
			model.addAttribute(WebCode.SUCCESS, false);
			model.addAttribute(WebCode.MSG, "令牌已过期，请登录系统进行处理。");
		}
		catch (SecurityException e) {
			model.addAttribute(WebCode.SUCCESS, false);
			model.addAttribute(WebCode.MSG, "系统错误：" + e.getMessage());
		}
		return data;
	}
}