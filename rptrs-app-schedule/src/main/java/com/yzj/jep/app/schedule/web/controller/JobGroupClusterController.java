/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年10月27日 14:56:59
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

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.schedule.server.model.JobGroupCluster;
import com.yzj.jep.schedule.server.service.JobGroupClusterService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

/**
 * 类型集群管理
 * @author yinke.hou
 * @time 2017年10月31日 上午9:08:52
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sch/jobGroupCluster")
public class JobGroupClusterController extends CrudController<JobGroupCluster>{
	private static Logger	logger	= LoggerFactory.getLogger(JobGroupClusterController.class);

	@Autowired
	private JobGroupClusterService	groupClusterService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "sch/jobGroupClusterList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<JobGroupCluster> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		groupClusterService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "sch/jobGroupClusterEdit";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model){
		
		JobGroupCluster groupCluster = groupClusterService.get(id);
		model.addAttribute("groupCluster", groupCluster);
		model.addAttribute("opt", "edit");
		
		return "sch/jobGroupClusterEdit";
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{id}")
	public String view(@PathVariable String id, Model model) {
		
		JobGroupCluster groupCluster = groupClusterService.get(id);
		model.addAttribute("groupCluster", groupCluster);
		
		return "sch/jobGroupClusterView";
	}
	
	/**
	 * 保存
	 * @param groupCluster
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(JobGroupCluster groupCluster) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = groupClusterService.exists(groupCluster.getId());
		try 
		{
			if(isExist)
			{
				groupClusterService.insert(groupCluster);
			}
			else
			{
				groupClusterService.update(groupCluster);
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
	 * 关联类型集群
	 * @param groupCode
	 * @param clusterCode
	 * @return
	 */
	@RequestMapping(value = "/saveAndCluster")
	@ResponseBody
	public Map<String, Object> saveAndCluster(String groupCode, String clusterCode) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			groupClusterService.saveAndCluster(groupCode, clusterCode);
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
			groupClusterService.deleteByPrimaryKey(id);
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
		
		JobGroupCluster exist = groupClusterService.get(id);
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
	 * 查询全部数据
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/selectAll")
	@ResponseBody
	public List<JobGroupCluster> selectAll(Model model, String groupCode){
		
		List<JobGroupCluster> result = groupClusterService.select(new Filter.Builder().eq("groupCode", groupCode).build());
		
		return result;
	}
}