/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年09月18日 21:41:52
 *
 */

package com.yzj.jep.app.workflow.web.controller;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONArray;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.sys.service.DictService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;
import com.yzj.jep.workflow.api.code.PropFormType;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IFormDef;
import com.yzj.jep.workflow.api.model.IFormProp;
import com.yzj.jep.workflow.wf.model.FormProp;

/**
 * 工作流 - 表单属性管理
 * @author yinke.hou
 * @time 2017年9月19日 下午10:28:13
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/workflow/formProp")
public class FormPropController extends CrudController<FormProp>{
	private static Logger	logger	= LoggerFactory.getLogger(FormPropController.class);
	
	@Autowired 
	private WorkflowEngine	 	workflowEngine;
	
	@Autowired
	DictService					dictService;
	
	/**
	 * 表单设计界面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, String formCode) {
		IFormDef iFormDef = workflowEngine.getFormService().getFormDef(formCode);
		model.addAttribute("formDef", iFormDef);
		if(PropFormType.CUSTOM.getValue().equals(iFormDef.getFormType())){
			List<? extends IFormProp> list = workflowEngine.getFormService().selectFormProp(formCode);
			JSONArray props = new JSONArray();
			props.addAll(list);
			model.addAttribute("formProp", props.toJSONString());
			return "wf/formPropBusinessList";
		}
		else
		{
			return "wf/formPropList";
		}
		
	}
	
	/**
	 * 表单设计查看
	 * @param model
	 * @param formCode
	 * @return
	 */
	@RequestMapping(value = "/designView")
	public String designList(Model model, String formCode) {
		IFormDef iFormDef = workflowEngine.getFormService().getFormDef(formCode);
		model.addAttribute("formDef", iFormDef);
		if(PropFormType.CUSTOM.getValue().equals(iFormDef.getFormType())){
			return "wf/formDesignBusinessView";
		}
		else
		{
			return "wf/formDesignView";
		}
		
	}
	
	/**
	 *  查询表单属性
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
		
		List<? extends IFormProp> list = workflowEngine.getFormService().selectFormProp(formCode);
		
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
	public String add(Model model, String formCode, String formType){
		model.addAttribute("formCode", formCode);
		if(PropFormType.CUSTOM.getValue().equals(formType)){
			return "wf/formPropBusinessEdit";
		}
		else
		{
			return "wf/formPropEdit";
		}
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{formCode}/{propCode}")
	public String edit(@PathVariable String formCode, @PathVariable String propCode, Model model, String formType){
		
		IFormProp formProp = workflowEngine.getFormService().getFormProp(formCode, propCode);
		model.addAttribute("formProp", formProp);
		model.addAttribute("formCode", formCode);
		model.addAttribute("opt", "edit");
		if(PropFormType.CUSTOM.getValue().equals(formType)){
			return "wf/formPropBusinessEdit";
		}
		else
		{
			return "wf/formPropEdit";
		}
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{formCode}/{propCode}/{type}")
	public String view(@PathVariable String formCode, @PathVariable String propCode, @PathVariable String type, Model model, String formType) {
		
		FormProp formProp = (FormProp)workflowEngine.getFormService().getFormProp(formCode, propCode);
		model.addAttribute("inputType", formProp.getInputType());
		model.addAttribute("formProp", formProp);
		model.addAttribute("type", type);
		
		if(PropFormType.CUSTOM.getValue().equals(formType)){
			return "wf/formPropBusinessView";
		}
		else
		{
			return "wf/formPropView";
		}
	}

	/**
	 * 保存
	 * @param formProp
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(FormProp formProp) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = workflowEngine.getFormService().existsFormProp(formProp.getFormCode(), formProp.getPropCode());
		try 
		{
			if(isExist)
			{
				workflowEngine.getFormService().updateFormProp(formProp);
			}
			else
			{
				workflowEngine.getFormService().insertFormProp(formProp);
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
	 * 删除表单属性
	 * @param formCode
	 * @param propCode
	 * @return
	 */
	@RequestMapping(value = "/remove/{formCode}/{propCode}")
	@ResponseBody
	public Map<String, Object> remove(@PathVariable String formCode, @PathVariable String propCode){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			workflowEngine.getFormService().deleteFormProp(formCode, propCode);
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
	public Map<String, Object> check(String formCode, String propCode){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		boolean exist = workflowEngine.getFormService().existsFormProp(formCode, propCode);
		tips.put("valid", !exist);
		
		return tips;
	}
	
	/**
	 * 验证提交的id是否已经存在数据库中
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryDict", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<NameValuePair> queryDict(String dictType, String source){
		List<NameValuePair> list = new ArrayList<>();
		
		list.add(new BasicNameValuePair("测试1", "A"));
		list.add(new BasicNameValuePair("测试2", "B"));
		list.add(new BasicNameValuePair("测试3", "C"));
		
		return list;
	}
	
	/**
	 * 向上移动属性
	 * @param formCode
	 * @param propCode
	 * @param propOrder
	 * @return
	 */
	@RequestMapping(value = "/formPropUp")
	@ResponseBody
	public Map<String, Object> formPropUp(String formCode, String propCode, int propOrder){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		try 
		{
			workflowEngine.getFormService().formPropUp(formCode, propCode, propOrder);
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
	 * 表单属性下移
	 * @param formCode
	 * @param propCode
	 * @param propOrder
	 * @return
	 */
	@RequestMapping(value = "/formPropDown")
	@ResponseBody
	public Map<String, Object> formPropDown(String formCode, String propCode, int propOrder){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			workflowEngine.getFormService().formPropDown(formCode, propCode, propOrder);
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
	 * 查询当前表单属性
	 * @param formCode
	 * @return
	 */
	@RequestMapping(value = "/queryFormProp")
	@ResponseBody
	public List<NameValuePair> queryStdRole(String formCode){
		List<? extends IFormProp> result = workflowEngine.getFormService().selectFormProp(formCode);
		
		List<NameValuePair> nameValuePairs = NVUtils.convert(result, result.size(), "propCode", "propName");
		return nameValuePairs;
	}
	
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewDetails")
	public String viewDetails(String instId, String propCode, Model model) {
		/*查询对应的数据变动日志*/
		model.addAttribute("content", workflowEngine.getLogService().getProcDataLog(instId, propCode));
		return "wf/formPropDataChangeLog";
	}
	
	/**
	 * 保存
	 * @param formProp
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/saveProps")
	@ResponseBody
	public Map<String, Object> saveProps(String formCode, String formProps) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		
		try 
		{
			JSONArray props = new JSONArray();
			List<? extends FormProp> formPropList = props.parseArray(formProps, FormProp.class);
			workflowEngine.getFormService().saveProps(formCode, formPropList);
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
	 *  查询表单属性
	 * @param model
	 * @param request
	 * @param formCode
	 * @return
	 */
	@RequestMapping(value = {"/existsFormProp"})
	@ResponseBody
	public Map<String, Object> existsFormProp(Model model, String formCode)
	{
		Map<String, Object> tips = new HashMap<String, Object>();
		try 
		{
			boolean exists = workflowEngine.getFormService().existsFormProp(formCode);
			tips.put("exists", exists);
		}
		catch (ServiceException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, e.getMessage());
		}
		return tips;
	}
}