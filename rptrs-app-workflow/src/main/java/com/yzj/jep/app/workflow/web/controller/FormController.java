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

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.api.model.IFormEvent;
import com.yzj.jep.workflow.api.model.IFormProp;
import com.yzj.jep.workflow.api.service.IFormService;
import com.yzj.jep.workflow.wf.model.FormDef;

/**
 * 工作流 - 表单管理
 * @author yinke.hou
 * @time 2017年9月19日 上午9:58:53
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/workflow/form")
public class FormController extends CrudController<FormDef>{
	private static Logger	logger	= LoggerFactory.getLogger(FormController.class);

	@Autowired
	private IFormService formService;
	
	/**
	 * 测试页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		String data = "{\"code\": \"hahahahaha\"}";
		String data64 = new String(Base64.encodeBase64(data.getBytes()));
		
		String formDefCode = "form1";
		String content = formService.renderForm(formDefCode);
		model.addAttribute("formDefCode", formDefCode);
		model.addAttribute("formData", data64);
		model.addAttribute("content", content);
		return "wf/formTest";
	}
	
	/**
	 * 前端插件加载表单元数据
	 * 1. 加载表单属性，用于默认值的处理
	 * 2. 加载表单事件，在页面处理事件
	 * @param formDefCode
	 * @param procDefCode
	 * @param procInstId
	 * @param oldData
	 * @return
	 */
	@RequestMapping(value = "/metadata")
	@ResponseBody
	public Map<String, Object> metadata(String formDefCode, String procDefCode, String procInstId, String oldData, String taskId) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		try
		{
			//将所有属性的列表加载到前端，用于数据加载。要根据传入的code不同，调用不同的加载方法
			List<? extends IFormProp> props = null;
			List<? extends IFormEvent> events = null;
			
			if(StringUtils.isNotBlank(formDefCode))
			{
				props = formService.selectFormDefFormProp(formDefCode);
				events = formService.selectFormDefFormEvent(formDefCode);
			}
			else if(StringUtils.isNotBlank(procDefCode))
			{
				if(StringUtils.isNotBlank(oldData))
				{
					oldData = new String(Base64.decodeBase64(oldData));
				}
				props = formService.selectProcDefFormProp(procDefCode, oldData);
				events = formService.selectProcDefFormEvent(procDefCode);
			}
			else if(StringUtils.isNotBlank(procInstId))
			{
				props = formService.selectProcInstFormProp(procInstId, taskId);
				events = formService.selectProcInstFormEvent(procInstId);
			}
			data.put(WebCode.SUCCESS, true);
			data.put("props", props);
			data.put("events", events);
			
		} catch(Exception ex)
		{
			data.put(WebCode.SUCCESS, false);
		}
		
		return data;
	}
	
	
	
	
	
}