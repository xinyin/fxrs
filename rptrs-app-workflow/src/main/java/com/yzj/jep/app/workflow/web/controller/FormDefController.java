/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年09月19日 09:57:13
 *
 */

package com.yzj.jep.app.workflow.web.controller;

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
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IFormDef;
import com.yzj.jep.workflow.wf.model.FormDef;

/**
 * 工作流 - 表单管理
 * @author yinke.hou
 * @time 2017年9月19日 上午9:58:53
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/workflow/formDef")
public class FormDefController extends CrudController<FormDef>{
	private static Logger	logger	= LoggerFactory.getLogger(FormDefController.class);

	@Autowired
	private WorkflowEngine workflowEngine;
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {

		return "wf/formDefList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<FormDef> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		orders.add(new Order("createTime", "asc"));
		workflowEngine.getFormService().queryFormDef(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "wf/formDefEdit";
	}
	
	/**
	 * 编辑页面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{code}")
	public String edit(@PathVariable String code, Model model){
		
		IFormDef formDef = workflowEngine.getFormService().getFormDef(code);
		model.addAttribute("formDef", formDef);
		model.addAttribute("opt", "edit");
		
		return "wf/formDefEdit";
	}
	
	/**
	 * 保存
	 * @param formDef
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(FormDef formDef) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = workflowEngine.getFormService().existsFormDef(formDef.getCode());
		try 
		{
			if(isExist)
			{
				workflowEngine.getFormService().updateFormDef(formDef);
			}
			else
			{
				workflowEngine.getFormService().insertFormDef(formDef);
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
			workflowEngine.getFormService().deleteFormDef(code);
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
	 * 表单预览
	 * @param model
	 * @param formCode
	 * @return
	 */
	@RequestMapping(value = "/preview")
	public String list(Model model, String formCode) {
		try 
		{
			//获取表单内容
			String content = workflowEngine.getFormService().renderForm(formCode);
		
			model.addAttribute("formCode", formCode);
			model.addAttribute("content", content);
		} 
		catch (WorkflowException e) 
		{
			logger.info(e.getMessage());
			model.addAttribute(WebCode.SUCCESS, false);
			model.addAttribute(WebCode.MSG, e.getMessage());
		}
		catch (ServiceException e) 
		{
			logger.info(e.getMessage());
			model.addAttribute(WebCode.SUCCESS, false);
			model.addAttribute(WebCode.MSG, e.getMessage());
		}
		return "wf/formDefPreview";
	}
	
	/**
	 * 启用表单
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/enable/{code}")
	@ResponseBody
	public Map<String, Object> enable(@PathVariable String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			workflowEngine.getFormService().enableFormDef(code);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
		} 
		catch (ServiceException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, e.getMessage());
		}
		
		return tips;
	}
	
	/**
	 * 表单停用
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/disable/{code}")
	@ResponseBody
	public Map<String, Object> disable(@PathVariable String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			workflowEngine.getFormService().diableFormDef(code);
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
	 * 验证提交的code是否已经存在数据库中
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> check(String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		boolean exist = workflowEngine.getFormService().existsFormDef(code);
		tips.put("valid", !exist);
		
		return tips;
	}
	
	/**
	 * 以特殊格式获取所有表单定义数据
	 * @return json格式返回
	 */
	@RequestMapping(value = "/selectAll")
	@ResponseBody
	public List<NameValuePair> selectAll(){
		
		List<? extends IFormDef> result = workflowEngine.getFormService().selectFormDef();
		
		return NVUtils.convert(result, result.size(), "code", "name");
	}
}