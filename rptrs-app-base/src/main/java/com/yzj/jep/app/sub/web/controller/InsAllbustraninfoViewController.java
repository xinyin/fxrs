/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:32
 *
 */

package com.yzj.jep.app.sub.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sub.model.InsAllbustraninfoView;
import com.yzj.jep.app.sub.service.InsAllbustraninfoViewService;
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
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sub/insAllbustraninfoView")
public class InsAllbustraninfoViewController extends CrudController<InsAllbustraninfoView>{
	private static Logger	logger	= LoggerFactory.getLogger(InsAllbustraninfoViewController.class);

	@Autowired
	private InsAllbustraninfoViewService insAllbustraninfoViewService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "sub/insAllbustraninfoViewList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<InsAllbustraninfoView> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		insAllbustraninfoViewService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "sub/insAllbustraninfoViewEdit";
	}
	
	/**
	 * 编辑页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{pid}")
	public String edit(@PathVariable Integer pid, Model model){
		
		InsAllbustraninfoView insAllbustraninfoView = insAllbustraninfoViewService.get(pid);
		model.addAttribute("insAllbustraninfoView", insAllbustraninfoView);
		model.addAttribute("opt", "edit");
		
		return "sub/insAllbustraninfoViewEdit";
	}
	
	/**
	 * 查看页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{pid}")
	public String view(@PathVariable Integer pid, Model model) {
		
		InsAllbustraninfoView insAllbustraninfoView = insAllbustraninfoViewService.get(pid);
		model.addAttribute("insAllbustraninfoView", insAllbustraninfoView);
		
		return "sub/insAllbustraninfoViewView";
	}
	
	/**
	 * 保存
	 * @param insAllbustraninfoView
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(InsAllbustraninfoView insAllbustraninfoView) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = insAllbustraninfoViewService.exists(insAllbustraninfoView.getPid());
		try 
		{
			if(isExist)
			{
				
				insAllbustraninfoViewService.update(insAllbustraninfoView);
			}
			else
			{
				insAllbustraninfoViewService.insert(insAllbustraninfoView);
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
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/remove/{pid}")
	@ResponseBody
	public Map<String, Object> remove(@PathVariable Integer pid){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			insAllbustraninfoViewService.deleteByPrimaryKey(pid);
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
	 * 验证提交的pid是否已经存在数据库中
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> check(Integer pid){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		boolean exist = insAllbustraninfoViewService.exists(pid);
		tips.put("valid", !exist);
		
		return tips;
	}
}