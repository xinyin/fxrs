/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年10月27日 14:57:03
 *
 */

package com.yzj.jep.app.schedule.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.schedule.server.model.JobLog;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.schedule.server.service.JobLogService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sch/jobLog")
public class JobLogController extends CrudController<JobLog>{
	private static Logger	logger	= LoggerFactory.getLogger(JobLogController.class);

	@Autowired
	private JobLogService	jobLogService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "sch/jobLogList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<JobLog> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		jobLogService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "sch/jobLogEdit";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model){
		
		JobLog jobLog = jobLogService.get(id);
		model.addAttribute("jobLog", jobLog);
		model.addAttribute("opt", "edit");
		
		return "sch/jobLogEdit";
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{id}")
	public String view(@PathVariable String id, Model model) {
		
		JobLog jobLog = jobLogService.get(id);
		model.addAttribute("jobLog", jobLog);
		
		return "sch/jobLogView";
	}
	
	/**
	 * 保存
	 * @param jobLog
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(JobLog jobLog) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = jobLogService.exists(jobLog.getId());
		try 
		{
			if(isExist)
			{
				jobLogService.insert(jobLog);
			}
			else
			{
				jobLogService.update(jobLog);
			}
			
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
		}
		catch (ServiceException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
		}
		return tips;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/remove/{id}")
	@ResponseBody
	public Map<String, Object> remove(@PathVariable String id){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			jobLogService.deleteByPrimaryKey(id);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
		} 
		catch (ServiceException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
		}
		
		return tips;
	}
	
	/**
	 * 验证提交的id是否已经存在数据库中
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> check(String id){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		JobLog exist = jobLogService.get(id);
		if(exist == null)
		{
			tips.put("valid", true);
		}
		else
		{
			tips.put("valid", false);
		}
		
		return tips;
	}
	
	/**
	 * 根据任务实例id查询日志
	 * @param instId
	 * @return
	 */
	@RequestMapping(value = "/queryByinstId", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<JobLog> queryByinstId(String instId){
		
		List<JobLog> jobLogs= jobLogService.select(new Filter.Builder().eq("jobInstId", instId).build(), new Order.Builder().asc("logTime").build());
		 
		return jobLogs;
	}
}