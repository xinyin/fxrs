/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年10月27日 14:57:02
 *
 */

package com.yzj.jep.app.schedule.web.controller;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.schedule.server.ServerScheduler;
import com.yzj.jep.schedule.server.model.JobInst;
import com.yzj.jep.schedule.server.service.JobInstService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sch/jobInst")
public class JobInstController extends CrudController<JobInst>{
	private static Logger	logger	= LoggerFactory.getLogger(JobInstController.class);

	@Autowired
	private JobInstService	jobInstService;
	
	@Autowired
	private ServerScheduler scheduler;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "sch/jobInstList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<JobInst> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		orders.add(new Order("startTime", OrderType.DESC));
		jobInstService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "sch/jobInstEdit";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model){
		
		JobInst jobInst = jobInstService.get(id);
		model.addAttribute("jobInst", jobInst);
		model.addAttribute("opt", "edit");
		
		return "sch/jobInstEdit";
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{id}")
	public String view(@PathVariable String id, Model model) {
		
		JobInst jobInst = jobInstService.get(id);
		model.addAttribute("jobInst", jobInst);
		
		return "sch/jobInstView";
	}
	
	/**
	 * 保存
	 * @param jobInst
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(JobInst jobInst) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = jobInstService.exists(jobInst.getId());
		try 
		{
			if(isExist)
			{
				jobInstService.insert(jobInst);
			}
			else
			{
				jobInstService.update(jobInst);
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
			jobInstService.deleteByPrimaryKey(id);
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
	 * 重做任务
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/redo/{id}")
	@ResponseBody
	public Map<String, Object> redo(@PathVariable String id){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			scheduler.scheduleJobInst(id);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
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
	 * 停止当前任务
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/stop/{id}")
	@ResponseBody
	public Map<String, Object> stop(@PathVariable String id){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
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
	 * 验证提交的id是否已经存在数据库中
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> check(String id){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		JobInst exist = jobInstService.get(id);
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
}