/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年09月21日 21:53:15
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.api.code.PropFormEvent;
import com.yzj.jep.workflow.api.code.PropFormEventType;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IFormEvent;
import com.yzj.jep.workflow.wf.model.FormEvent;

/**
 * 表单管理 - 表单事件
 * @author yinke.hou
 * @time 2017年9月21日 下午9:54:24
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/workflow/formEvent")
public class FormEventController extends CrudController<FormEvent>{
	private static Logger	logger	= LoggerFactory.getLogger(FormEventController.class);

	@Autowired
	private WorkflowEngine		workflowEngine;

	/**
	 *  查询表单事件
	 * @param model
	 * @param request
	 * @param formCode
	 * @return
	 */
	@RequestMapping(value = {"/queryList"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> queryList(Model model, HttpServletRequest request, String formCode)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<? extends IFormEvent> list = workflowEngine.getFormService().selectFormEvent(formCode);
		
		result.put(RESULT_TOTAL, list.size());
		result.put(RESULT_ROWS, list);
		return result;
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model, String formCode){
		
		model.addAttribute("formCode", formCode);
		return "wf/formEventEdit";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model){
		
		IFormEvent formEvent = workflowEngine.getFormService().getFormEvent(id);
		model.addAttribute("formEvent", formEvent);
		model.addAttribute("formCode", formEvent.getFormCode());
		model.addAttribute("opt", "edit");
		
		return "wf/formEventEdit";
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{id}/{type}")
	public String view(@PathVariable String id, @PathVariable String type, Model model) {
		
		FormEvent formEvent = (FormEvent)workflowEngine.getFormService().getFormEvent(id);
		formEvent.setType(PropFormEventType.getLabel(formEvent.getType()));
		formEvent.setName(PropFormEvent.getLabel(formEvent.getName()));
		
		model.addAttribute("formEvent", formEvent);
		model.addAttribute("type", type);
		return "wf/formEventView";
	}
	
	/**
	 * 保存
	 * @param formEvent
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(FormEvent formEvent) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			if(StringUtils.isNotEmpty(formEvent.getId()))
			{
				workflowEngine.getFormService().updateFormEvent(formEvent);
			}
			else
			{
				workflowEngine.getFormService().insertFormEvent(formEvent);
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
			workflowEngine.getFormService().deleteFormEvent(id);
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
}