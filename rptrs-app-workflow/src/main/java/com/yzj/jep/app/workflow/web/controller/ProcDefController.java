/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年09月19日 11:15:38
 *
 */

package com.yzj.jep.app.workflow.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.code.Status;
import com.yzj.jep.core.code.VersionStatus;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.code.InsUnitHierarchy;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;
import com.yzj.jep.workflow.api.code.NodeType;
import com.yzj.jep.workflow.api.code.NotifyEvent;
import com.yzj.jep.workflow.api.code.UnitScope;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IProcDef;
import com.yzj.jep.workflow.api.model.IProcNode;
import com.yzj.jep.workflow.api.model.IProcVarMap;
import com.yzj.jep.workflow.impl.assign.ProcessAssignFindHandlerRegistry;
import com.yzj.jep.workflow.impl.util.UrlUtil;
import com.yzj.jep.workflow.wf.model.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/workflow/procDef")
public class ProcDefController extends CrudController<ProcDef>{
	Logger logger = LoggerFactory.getLogger(ProcDefController.class);

	@Autowired 
	private WorkflowEngine workflowEngine;
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "wf/procDefList";
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		model.addAttribute("procVersion", 1);
		return "wf/procDefEdit";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(String code, int procVersion, Model model){
		
		IProcDef procDef = workflowEngine.getProcessService().getProcDef(code, procVersion);
		
		model.addAttribute("procDef", procDef);
		model.addAttribute("procVersion", procVersion);
		model.addAttribute("opt", "edit");
		
		return "wf/procDefEdit";
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view")
	public String view(String code, int procVersion, Model model) {
		
		ProcDef procDef = (ProcDef)workflowEngine.getProcessService().getProcDef(code, procVersion);
		procDef.setProcVersionStatus(VersionStatus.getLabel(procDef.getProcVersionStatus()));
		procDef.setStatus(Status.getLabel(procDef.getStatus()));
		model.addAttribute("procDef", procDef);
		
		return "wf/procDefView";
	}
	
	/**
	 * 复制
	 * @return 复制页面
	 */
	@RequestMapping(value = "/copyDlg")
	public String copyDlg(Model model) {
		
		return "wf/procDefCopy";
	}
	
	/**
	 * 设计
	 * @return 设计页面
	 */
	@RequestMapping(value = "/designPage")
	public String designPage(String code, int procVersion, Model model) {
		
		ProcDef procDef = (ProcDef)workflowEngine.getProcessService().getProcDef(code, procVersion);
		procDef.setProcVersionStatus(VersionStatus.getLabel(procDef.getProcVersionStatus()));
		procDef.setStatus(Status.getLabel(procDef.getStatus()));
		model.addAttribute("procDef", procDef);
		
	/*	Map<String, Object> result =  this.getProcNotify(procDef.getCode(), procDef.getProcVersion(), NotifyEvent.PROCESSEND.getValue());
		model.addAttribute("procNotify", result.get("procNotify"));*/
		
		return "wf/procDefDesign";
	}
	
	/**
	 * 节点授权选择岗位页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authRoleDlg")
	public String authRoleDlg(Model model) {
		
		return "wf/procAuthRoleDlg";
	}

	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<ProcDef> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		workflowEngine.getProcessService().queryProcDef(page, filters, orders);
	}
	
	/**
	 * 保存
	 * @param procDef
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(ProcDef procDef) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		try 
		{
			boolean isTestPass =  workflowEngine.getProcessService().workflowShakeTesk(UrlUtil.mergeUrl(UrlUtil.URL_HEAD, procDef.getClientUrl() + ":" + procDef.getClientPort(), procDef.getClientContext()));
			if(isTestPass){
				boolean isExist = workflowEngine.getProcessService().existsProcDef(procDef.getCode(), procDef.getProcVersion());
				if(isExist)
				{
					procDef.setProcVersion(procDef.getProcVersion() + 1);
					workflowEngine.getProcessService().updateProcDef(procDef);
				}
				else
				{

					procDef.setStatus(Status.DRAFT.getValue());
					procDef.setCreateTime(new Date());
					procDef.setProcVersionStatus(VersionStatus.D.getValue());
					workflowEngine.getProcessService().insertProcDef(procDef);
				}
				
				tips.put(WebCode.SUCCESS, true);
				tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
			}
			else
			{
				tips.put(WebCode.SUCCESS, false);
				tips.put(WebCode.MSG, "客户端访问地址有误，请检查。");
			}
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
			workflowEngine.getProcessService().deleteProcDef(id);
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
	 * 验证流程编码是否已存在
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> check(String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		boolean isExist = workflowEngine.getProcessService().existsProcDef(code, null);
		tips.put("valid", !isExist);
		
		return tips;
	}
	
	/**
	 * 复制流程
	 * @param code
	 * @param ProcVersion
	 * @return
	 */
	@RequestMapping(value = "/copy")
	@ResponseBody
	public Map<String, Object> copy(String code, int version, String newCode, String newName){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			workflowEngine.getProcessService().copyProcDef(code, version, newCode, newName);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_COPY);
		} 
		catch (ServiceException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_COPY);
		}
		
		return tips;
	}
	
	/**
	 * 启用流程
	 * @param code
	 * @param ProcVersion
	 * @return
	 */
	@RequestMapping(value = "/enable")
	@ResponseBody
	public Map<String, Object> enable(String code, int procVersion){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			workflowEngine.getProcessService().enableProcDef(code, procVersion);
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
	 * 停用流程
	 * @param code
	 * @param ProcVersion
	 * @return
	 */
	@RequestMapping(value = "/disable")
	@ResponseBody
	public Map<String, Object> disable(String code, int procVersion){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			workflowEngine.getProcessService().disableProcDef(code, procVersion);
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
	 * 升级流程
	 * @param code
	 * @param version
	 * @return
	 */
	@RequestMapping(value = "/newProcDef")
	@ResponseBody
	public Map<String, Object> newProcDef(String code, int version){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			workflowEngine.getProcessService().newProcDef(code, version);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_NEW);
		} 
		catch (ServiceException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_NEW);
		}
		
		return tips;
	}
	
	@RequestMapping(value = {"/getNodeProcAuth"})
	@ResponseBody
	public Map<String, Object> getNodeProcAuth(String code, int version, String nodeKey)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		ProcAuth procAuth = (ProcAuth) workflowEngine.getProcessService().getNodeProcAuth(code, version, nodeKey);
		result.put("procAuth", procAuth);
		
		return result;
	}
	
	@RequestMapping(value = "/updateNodeProcAuth")
	@ResponseBody
	public Map<String, Object> updateNodeProcAuth(ProcAuth procAuth){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			workflowEngine.getProcessService().updateNodeProcAuth(procAuth);
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
	 * 查询节点的表单属性
	 * @param code
	 * @param version
	 * @param nodeKey
	 * @return
	 */
	@RequestMapping(value = {"/getNodeProcFormCfg"})
	@ResponseBody
	public Map<String, Object> getNodeProcFormCfg(String code, int version, String nodeKey)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(nodeKey))
		{
			ProcFormCfg procFormCfg = (ProcFormCfg) workflowEngine.getProcessService().getNodeProcFormCfg(code, version, nodeKey);
			result.put("procFormCfg", procFormCfg);
			
			String formCfg = procFormCfg.getFormConfig();
			JSONArray jsonArray = JSONArray.parseArray(formCfg);
			
			result.put(RESULT_TOTAL, jsonArray.size());
			result.put(RESULT_ROWS, jsonArray);
		}
		else
		{
			result.put(RESULT_TOTAL, 0);
			result.put(RESULT_ROWS, null);
		}
		return result;
	}
	
	@RequestMapping(value = "/updateProcFormCfg")
	@ResponseBody
	public Map<String, Object> updateNodeProcFormCfg(ProcFormCfg procFormCfg){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			workflowEngine.getProcessService().updateNodeProcFormCfg(procFormCfg);
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
	 * 查询业务流程的变量配置信息
	 * @param code
	 * @param version
	 * @return
	 */
	@RequestMapping(value = {"/selectProcVarMap"})
	@ResponseBody
	public Map<String, Object> selectProcVarMap(String code, int version)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<? extends IProcVarMap> procVarMapList = workflowEngine.getProcessService().selectProcVarMap(code, version);
		
		result.put(RESULT_TOTAL, procVarMapList.size());
		result.put(RESULT_ROWS, procVarMapList);
		
		return result;
	}
	
	@RequestMapping(value = "/updateProcVarMap")
	@ResponseBody
	public Map<String, Object> updateProcVarMap(String varMapJson){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			List<ProcVarMap> procVarMapList = JSONArray.parseArray(varMapJson, ProcVarMap.class);
			workflowEngine.getProcessService().updateProcVarMap(procVarMapList);
			
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
	
	@RequestMapping(value = {"/getProcNotify"})
	@ResponseBody
	public Map<String, Object> getProcNotify(String code, int version, String notifyEvent)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		ProcNotify procNotify = (ProcNotify) workflowEngine.getProcessService().getProcNotify(code, version, NotifyEvent.valueOf(notifyEvent));
		result.put("procNotify", procNotify);
		
		return result;
	}
	
	/**
	 * 更新流程通知事件
	 * @param notify
	 * @return
	 */
	@RequestMapping(value = "/updateProcNotify")
	@ResponseBody
	public Map<String, Object> updateProcNotify(ProcNotify notify){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			workflowEngine.getProcessService().updateProcNotify(notify);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_COPY);
		} 
		catch (ServiceException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_COPY);
		}
		
		return tips;
	}
	
	/**
	 * 查询节点，以特殊格式返回
	 * @return
	 */
	@RequestMapping(value = "/selectNodeKey")
	@ResponseBody
	public List<NameValuePair> selectNodeKey(String code){
		List<IProcNode> list = new ArrayList<>();
		List<? extends IProcNode> result = workflowEngine.getRepositoryService().selectProcNodes(code);
		for (IProcNode iProcNode : result) 
		{
			if(NodeType.USERTASK.getValue().equals(iProcNode.getType()))
			{
				list.add(iProcNode);
			}
		}
		
		return NVUtils.convert(list, list.size(), "key", "name");
	}
	
	/**
	 * 测试用，返回所有已启用的当前版本的流程模板
	 * @return
	 */
	@RequestMapping(value = "/selectCurrent")
	@ResponseBody
	public List<NameValuePair> selectCurrent(){
		
		List<? extends IProcDef> result = workflowEngine.getProcessService().selectCurrentList();
		
		return NVUtils.convert(result, result.size(), "code", "name");
	}
	
	
	/**
	 * 节点授权选择岗位页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/workflowTestSubmit")
	public String workflowTestSubmit(Model model) {
		
		return "wf/workflowTestSubmit";
	}
	
	@RequestMapping(value = "/selectUnitScop")
	@ResponseBody
	public  List<NameValuePair> selectUnitScop(){
		List<String> hierarchys = workflowEngine.getProcessService().selectExistedHierarchy();
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		/*因为要去掉最后一层，所以循环时最后一个数据不取*/
		for(int i = 0, length = hierarchys.size(); i < length  ; i++){
			nameValuePairs.add(new BasicNameValuePair(InsUnitHierarchy.getLabel(hierarchys.get(i)), hierarchys.get(i)));
		}
		
		for(UnitScope scope : UnitScope.values()){
			nameValuePairs.add(new BasicNameValuePair(scope.getLabel(), scope.getValue()));
		}
		
		return nameValuePairs;
		
	}
	
	
	@RequestMapping(value = "/selectBizAuthHandler")
	@ResponseBody
	public  List<NameValuePair> selectBizAuthHandler(){
		
		Map<String, String> handlerNames = ProcessAssignFindHandlerRegistry.getAllHandlerNames();
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		
		if(CollectionUtils.isNotEmpty(handlerNames)){
			for(String key : handlerNames.keySet()){
				nameValuePairs.add(new BasicNameValuePair(handlerNames.get(key), key));
			}
		}
		return nameValuePairs;
		
	}
}