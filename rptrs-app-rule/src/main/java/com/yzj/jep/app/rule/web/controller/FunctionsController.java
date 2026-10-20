/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2018 yzj.com. All rights reserved.
 *
 * Generated at 2018年02月24日 11:11:38
 *
 */

package com.yzj.jep.app.rule.web.controller;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.rules.drools5.model.Functions;
import com.yzj.jep.rules.drools5.service.FunctionsService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rul/functions")
public class FunctionsController extends CrudController<Functions>{
	private static Logger	logger	= LoggerFactory.getLogger(FunctionsController.class);

	@Autowired
	private FunctionsService	functionsService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "rul/functionsList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<Functions> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		functionsService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "rul/functionsEdit";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model){
		
		Functions functions = functionsService.get(id);
		model.addAttribute("functions", functions);
		model.addAttribute("opt", "edit");
		
		return "rul/functionsEdit";
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{id}")
	public String view(@PathVariable String id, Model model) {
		
		Functions functions = functionsService.get(id);
		model.addAttribute("functions", functions);
		
		return "rul/functionsView";
	}
	
	/**
	 * 保存
	 * @param functions
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(Functions functions) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = functionsService.exists(functions.getId());
		try 
		{
			if(isExist)
			{
				
				functionsService.update(functions);
			}
			else
			{
				functionsService.insert(functions);
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
			functionsService.deleteByPrimaryKey(id);
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
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public List<NameValuePair> queryList(Model model){
		
		List<Functions> rules = functionsService.selectAll();
		List<NameValuePair> nameValuePairs = NVUtils.convert(rules, rules.size(), "id", "name");
		
		return nameValuePairs;
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
		
		List<Functions> list = functionsService.select(new Filter.Builder().eq("code", code).build());
		if(list.size() > 0) {
            tips.put("valid", false);
        } else {
            tips.put("valid", true);
        }
		
		return tips;
	}
	
	
	@RequestMapping(value = "/selectFuns", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> selectFuns(String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		List<Map<String,Object>> list = new ArrayList<>();
		try {
			Functions functions = functionsService.get(new Filter.Builder().eq("code", code).build());
			if(functions!=null){
				String content = functions.getContent();
				String paramStr = content.substring(content.indexOf("(")+1,content.indexOf(")"));
				if("".equals(paramStr)){
					tips.put("paramTotal",0);
					tips.put("params", list);
				}else{
					String[] params = paramStr.split(",");
					tips.put("paramTotal", params.length);
					for (String par : params) {
						Map<String, Object> typeMap = new HashMap<String, Object>();
						String paramType = par.substring(0, par.indexOf(" "));
						typeMap.put("paramType", paramType);
						list.add(typeMap);
					}
					tips.put("params", list);
				}
				
			}
			tips.put(WebCode.SUCCESS,true);
			return tips;
		} catch (Exception e) {
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG,WebCode.FAULURE_OPERATE);
			return tips;
		}
		
	}
}