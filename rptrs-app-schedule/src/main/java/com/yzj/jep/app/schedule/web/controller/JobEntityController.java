/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年10月27日 14:57:01
 *
 */

package com.yzj.jep.app.schedule.web.controller;

import java.util.Date;
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

import com.yzj.jep.schedule.server.ServerScheduler;
import com.yzj.jep.schedule.server.code.JobState;
import com.yzj.jep.schedule.server.code.JobStatusDict;
import com.yzj.jep.schedule.server.model.JobEntity;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.schedule.server.service.JobEntityService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sch/jobEntity")
public class JobEntityController extends CrudController<JobEntity>{
	private static Logger	logger	= LoggerFactory.getLogger(JobEntityController.class);

	@Autowired
	private JobEntityService	jobEntityService;
	
	@Autowired
	private ServerScheduler 	serverScheduler;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "sch/jobEntityList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<JobEntity> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		jobEntityService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		model.addAttribute("currentDate", DateUtils.format(new Date(), DateUtils.DATE_FORMAT));
		return "sch/jobEntityEdit";
	}
	
	/**
	 * 编辑页面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{code}")
	public String edit(@PathVariable String code, Model model){
		
		JobEntity jobEntity = jobEntityService.get(code);
		model.addAttribute("jobEntity", jobEntity);
		model.addAttribute("opt", "edit");
		
		return "sch/jobEntityEdit";
	}
	
	/**
	 * 查看页面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{code}")
	public String view(@PathVariable String code, Model model) {
		
		JobEntity jobEntity = jobEntityService.get(code);
		model.addAttribute("jobEntity", jobEntity);
		
		return "sch/jobEntityView";
	}
	
	/**
	 * 保存
	 * @param jobEntity
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(JobEntity jobEntity) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = jobEntityService.exists(jobEntity.getCode());
		try 
		{
			if(isExist)
			{
				jobEntityService.update(jobEntity);
			}
			else
			{
				jobEntity.setStatus(JobStatusDict.NEW.getValue());
				jobEntityService.insert(jobEntity);
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
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/remove/{code}")
	@ResponseBody
	public Map<String, Object> remove(@PathVariable String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			jobEntityService.deleteByPrimaryKey(code);
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
	 * 启动任务
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/start/{code}")
	@ResponseBody
	public Map<String, Object> start(@PathVariable String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			JobEntity job = jobEntityService.get(code);
			if(JobState.PAUSED.name().equals(job.getStatus()))
			{
				serverScheduler.resume(code);
				tips.put(WebCode.SUCCESS, true);
				tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
			}
			else if(JobState.STOPPED.name().equals(job.getStatus())
					|| JobState.NEW.name().equals(job.getStatus()))
			{
				serverScheduler.schedule(code);
				tips.put(WebCode.SUCCESS, true);
				tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
			}
			else
			{
				tips.put(WebCode.SUCCESS, false);
				tips.put(WebCode.MSG, "任务状态非法");
			}
		} 
		catch (ServiceException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
		}
		
		return tips;
	}
	
	/**
	 * 暂停任务
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/pause/{code}")
	@ResponseBody
	public Map<String, Object> pause(@PathVariable String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			JobEntity job = jobEntityService.get(code);
			if(JobState.EXECUTING.name().equals(job.getStatus()))
			{
				serverScheduler.pause(code);
				tips.put(WebCode.SUCCESS, true);
				tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
			}
			else
			{
				tips.put(WebCode.SUCCESS, false);
				tips.put(WebCode.MSG, "任务状态非法");
			}
		} 
		catch (ServiceException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
		}
		
		return tips;
	}
	
	/**
	 * 停止任务
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/stop/{code}")
	@ResponseBody
	public Map<String, Object> stop(@PathVariable String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			JobEntity job = jobEntityService.get(code);
			if(JobState.EXECUTING.name().equals(job.getStatus())
				|| JobState.PAUSED.name().equals(job.getStatus()))
			{
				serverScheduler.unschedule(code);
				tips.put(WebCode.SUCCESS, true);
				tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
			}
			else
			{
				tips.put(WebCode.SUCCESS, false);
				tips.put(WebCode.MSG, "任务状态非法");
			}
		} 
		catch (ServiceException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
		}
		
		return tips;
	}
	/**
	 * 验证提交的code是否已经存在数据库中
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> check(String code){
		Map<String, Object> tips = new HashMap<String, Object>();
		
		boolean exist = jobEntityService.exists(code);
		tips.put("valid", !exist);
		
		return tips;
	}
}