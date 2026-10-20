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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.rules.drools5.model.PkgRuleMap;
import com.yzj.jep.rules.drools5.model.Rules;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.rules.drools5.service.PkgRuleMapService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/rul/pkgRuleMap")
public class PkgRuleMapController extends CrudController<PkgRuleMap>{
	private static Logger	logger	= LoggerFactory.getLogger(PkgRuleMapController.class);

	@Autowired
	private PkgRuleMapService	pkgRuleMapService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		 
		return "rul/pkgRuleMapList";
	}


	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<PkgRuleMap> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		pkgRuleMapService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "rul/pkgRuleMapEdit";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model){
		
		PkgRuleMap pkgRuleMap = pkgRuleMapService.get(id);
		model.addAttribute("pkgRuleMap", pkgRuleMap);
		model.addAttribute("opt", "edit");
		
		return "rul/pkgRuleMapEdit";
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{id}")
	public String view(@PathVariable String id, Model model) {
		
		PkgRuleMap pkgRuleMap = pkgRuleMapService.get(id);
		model.addAttribute("pkgRuleMap", pkgRuleMap);
		
		return "rul/pkgRuleMapView";
	}
	
	/**
	 * 保存
	 * @param pkgRuleMap
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(PkgRuleMap pkgRuleMap) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = pkgRuleMapService.exists(pkgRuleMap.getId());
		try 
		{
			if(isExist)
			{
				
				pkgRuleMapService.update(pkgRuleMap);
			}
			else
			{
				pkgRuleMapService.insert(pkgRuleMap);
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
	 * 保存
	 * @param packagesId 包ID
	 * @param  rules 规则ID，逗号分割
	 * @return
	 */
	@RequestMapping(value = "/batchInsert")
	@ResponseBody
	public Map<String, Object> batchInsert(String packagesId, String rules) {

		Map<String, Object> tips = new HashMap<String, Object>();
		try
		{
			pkgRuleMapService.batchInsert(packagesId,rules);
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
			pkgRuleMapService.deleteByPrimaryKey(id);
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
	 * 查询全部函数
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/queryRuleList")
	@ResponseBody
	public Map<String, Object> queryList(Model model, String pkgId){
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<Rules> rules = pkgRuleMapService.queryRules(pkgId);
		result.put(RESULT_TOTAL, rules.size());
		result.put(RESULT_ROWS, rules);
		return result;
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
		
		boolean exist = pkgRuleMapService.exists(id);
		tips.put("valid", !exist);
		
		return tips;
	}
}