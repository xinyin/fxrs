/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2018 yzj.com. All rights reserved.
 *
 * Generated at 2018年02月24日 11:11:38
 *
 */

package com.yzj.jep.app.rule.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.orm.entity.OrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.rules.drools5.model.Objects;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.rules.drools5.service.ObjectsService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

/**
 * 规则引擎 对象库
 * @author ke
 * @time 2018年2月26日 上午9:32:29
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/rul/objects")
public class ObjectsController extends CrudController<Objects>{
	private static Logger	logger	= LoggerFactory.getLogger(ObjectsController.class);

	@Autowired
	private ObjectsService	objectsService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "rul/objectsList";
	}

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/select")
	public String select(String ruleId,Model model) {
		model.addAttribute("ruleId",ruleId);
		return "rul/objectsSelect";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<Objects> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		orders.add(new Order("group", OrderType.ASC));
		orders.add(new Order("code", OrderType.ASC));
		objectsService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "rul/objectsEdit";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model){
		
		Objects objects = objectsService.get(id);
		model.addAttribute("objects", objects);
		model.addAttribute("opt", "edit");
		
		return "rul/objectsEdit";
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{id}")
	public String view(@PathVariable String id, Model model) {
		
		Objects objects = objectsService.get(id);
		model.addAttribute("objects", objects);
		
		return "rul/objectsView";
	}
	
	/**
	 * 保存
	 * @param objects
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(Objects objects) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = objectsService.exists(objects.getId());
		try 
		{
			if(isExist)
			{
				
				objectsService.update(objects);
			}
			else
			{
				objectsService.insert(objects);
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
			objectsService.deleteByPrimaryKey(id);
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
	 * 验证提交的Code是否已经存在数据库中
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> check(String code){
		Map<String, Object> tips = new HashMap<String, Object>();
		
		List<Objects> list = objectsService.select(new Filter.Builder().eq("code", code).build());
		if(list.size() > 0) {
            tips.put("valid", false);
        } else {
            tips.put("valid", true);
        }
		return tips;
	}





}