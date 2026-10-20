/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2018 yzj.com. All rights reserved.
 *
 * Generated at 2018年02月24日 11:11:39
 *
 */

package com.yzj.jep.app.rule.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.rules.drools5.model.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.rules.drools5.model.RuleObjectMap;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.rules.drools5.service.RuleObjectMapService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/rul/ruleObjectMap")
public class RuleObjectMapController extends CrudController<RuleObjectMap>{
	private static Logger	logger	= LoggerFactory.getLogger(RuleObjectMapController.class);

	@Autowired
	private RuleObjectMapService	ruleObjectMapService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "rul/ruleObjectMapList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<RuleObjectMap> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		ruleObjectMapService.query(page, filters, orders);
	}

	/**
	 * 分页查询
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/findObjectsByRuleId"}, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public Map<String, Object> findObjectsByRuleId(Page<Objects> page, Model model, HttpServletRequest request)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		String ruleId = (String) request.getParameter("ruleId");
		page =  ruleObjectMapService.findObjectsByRuleId(page,ruleId);
		result.put(RESULT_TOTAL, page.getTotal());
		result.put(RESULT_ROWS, page.getResult());
		return result;
	}

	@RequestMapping("/findObjectsByPkgId")
	@ResponseBody
	public List<Objects> findObjectByPkgId(String pkgId)
	{
		return ruleObjectMapService.findObjectsByPkgId(pkgId);
	}


	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "rul/ruleObjectMapEdit";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model){
		
		RuleObjectMap ruleObjectMap = ruleObjectMapService.get(id);
		model.addAttribute("ruleObjectMap", ruleObjectMap);
		model.addAttribute("opt", "edit");
		
		return "rul/ruleObjectMapEdit";
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{id}")
	public String view(@PathVariable String id, Model model) {
		
		RuleObjectMap ruleObjectMap = ruleObjectMapService.get(id);
		model.addAttribute("ruleObjectMap", ruleObjectMap);
		
		return "rul/ruleObjectMapView";
	}


	/**
	 * 批量插入关联的对象
	 * @param ruleId 规则ID
	 * @param objIds 对象ID
	 * @return
	 */
	@RequestMapping(value = "/batchInsert")
	@ResponseBody
	public Map<String, Object> batchInsert(String ruleId,String objIds) {

		Map<String, Object> tips = new HashMap<String, Object>();
		try
		{
			ruleObjectMapService.batchInsert(ruleId,objIds);
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
	 * 保存
	 * @param ruleObjectMap
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(RuleObjectMap ruleObjectMap) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = ruleObjectMapService.exists(ruleObjectMap.getId());
		try 
		{
			if(isExist)
			{
				
				ruleObjectMapService.update(ruleObjectMap);
			}
			else
			{
				ruleObjectMapService.insert(ruleObjectMap);
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
			ruleObjectMapService.deleteByPrimaryKey(id);
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
		
		boolean exist = ruleObjectMapService.exists(id);
		tips.put("valid", !exist);
		
		return tips;
	}
}