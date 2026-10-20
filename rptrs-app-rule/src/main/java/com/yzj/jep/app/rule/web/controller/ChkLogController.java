/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月26日 20:59:28
 *
 */

package com.yzj.jep.app.rule.web.controller;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.rules.drools5.model.ChkLog;
import com.yzj.jep.rules.drools5.service.ChkLogService;
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
@RequestMapping("/rul/chkLog")
public class ChkLogController extends CrudController<ChkLog> {
	private static Logger	logger	= LoggerFactory.getLogger(ChkLogController.class);

	@Autowired
	private ChkLogService chkLogService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "rul/chkLogList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<ChkLog> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
		String bus_code ="";
		String tra_code ="";
		for (int i=0;i<filters.size();i++){
			if("busCode".equals(filters.get(i).getProperty())){
				bus_code = filters.get(i).getValue().toString();
				if("".equals(bus_code) || "%".equals(bus_code)){
					filters.remove(i);
				}
			}
			if("traCode".equals(filters.get(i).getProperty())){
				tra_code = filters.get(i).getValue().toString();
				if( ("".equals(tra_code) || "%".equals(tra_code))){
					filters.remove(i);
				}
			}
		}
	/*	if(bus_code.equals("") || bus_code.equals("%")){
			filters.add(new Filter("busCode","is null"));
		}
		if(tra_code.equals("") || tra_code.equals("%")){
			filters.add(new Filter("traCode","is null"));
		}*/
		chkLogService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "bus/chkLogCopyEdit";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable Integer id, Model model){
		
		ChkLog chkLog = chkLogService.get(id);
		model.addAttribute("chkLogCopy", chkLog);
		model.addAttribute("opt", "edit");
		
		return "bus/chkLogCopyEdit";
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{id}")
	public String view(@PathVariable Integer id, Model model) {
		
		ChkLog chkLogCopy = chkLogService.get(id);
		model.addAttribute("chkLogCopy", chkLogCopy);
		
		return "bus/chkLogCopyView";
	}
	
	/**
	 * 保存
	 * @param chkLogCopy
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(ChkLog chkLogCopy) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = chkLogService.exists(chkLogCopy.getId());
		try 
		{
			if(isExist)
			{
				
				chkLogService.update(chkLogCopy);
			}
			else
			{
				chkLogService.insert(chkLogCopy);
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
	public Map<String, Object> remove(@PathVariable Integer id){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			chkLogService.deleteByPrimaryKey(id);
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
	public Map<String, Object> check(Integer id){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		boolean exist = chkLogService.exists(id);
		tips.put("valid", !exist);
		
		return tips;
	}
}