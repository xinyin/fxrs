/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:35
 *
 */

package com.yzj.jep.app.sub.web.controller;

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

import com.yzj.jep.app.sub.model.InsAllfundsView;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.sub.service.InsAllfundsViewService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sub/insAllfundsView")
public class InsAllfundsViewController extends CrudController<InsAllfundsView>{
	private static Logger	logger	= LoggerFactory.getLogger(InsAllfundsViewController.class);

	@Autowired
	private InsAllfundsViewService	insAllfundsViewService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "sub/insAllfundsViewList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<InsAllfundsView> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		insAllfundsViewService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "sub/insAllfundsViewEdit";
	}
	
	/**
	 * 编辑页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{pid}")
	public String edit(@PathVariable Integer pid, Model model){
		
		InsAllfundsView insAllfundsView = insAllfundsViewService.get(pid);
		model.addAttribute("insAllfundsView", insAllfundsView);
		model.addAttribute("opt", "edit");
		
		return "sub/insAllfundsViewEdit";
	}
	
	/**
	 * 查看页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{pid}")
	public String view(@PathVariable Integer pid, Model model) {
		
		InsAllfundsView insAllfundsView = insAllfundsViewService.get(pid);
		model.addAttribute("insAllfundsView", insAllfundsView);
		
		return "sub/insAllfundsViewView";
	}
	
	/**
	 * 保存
	 * @param insAllfundsView
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(InsAllfundsView insAllfundsView) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = insAllfundsViewService.exists(insAllfundsView.getPid());
		try 
		{
			if(isExist)
			{
				
				insAllfundsViewService.update(insAllfundsView);
			}
			else
			{
				insAllfundsViewService.insert(insAllfundsView);
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
			insAllfundsViewService.deleteByPrimaryKey(pid);
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
		
		boolean exist = insAllfundsViewService.exists(pid);
		tips.put("valid", !exist);
		
		return tips;
	}
}