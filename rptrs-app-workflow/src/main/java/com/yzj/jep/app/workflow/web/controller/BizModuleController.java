/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年09月19日 08:37:26
 *
 */

package com.yzj.jep.app.workflow.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.orm.entity.Treeable;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.entity.TreeNode;
import com.yzj.jep.web.util.NVUtils;
import com.yzj.jep.web.util.TreeUtils;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IBizModule;
import com.yzj.jep.workflow.wf.model.BizModule;

@Controller
@RequestMapping("/workflow/bizModule")
public class BizModuleController extends CrudController<BizModule>{
	Logger logger = LoggerFactory.getLogger(BizModuleController.class);
	
	@Autowired 
	private WorkflowEngine workflowEngine;
	
	/**
	 * 页面跳转到bizModuleTree
	 * 
	 * @param model
	 * @return 树显示页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		return "wf/bizModuleTree";
	}
	
	/**
	 * 页面跳转到bizModuleDlg
	 * 
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "/dlg")
	public String dlg(String code, Model model) {
		
		model.addAttribute("code", code);
		
		return "wf/bizModuleDlg";
	}

	/**
	 * 移除业务模块表节点 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/removeNode/{code}")
	@ResponseBody
	public Map<String, Object> removeNode(@PathVariable String code) {
		Map<String, Object> tips = new HashMap<String, Object>();
		
		/*查询当前节点是否有子节点*/
		List<? extends IBizModule> leafNodes = workflowEngine.getProcessService().selectBizModule(code);
		boolean exist = workflowEngine.getProcessService().existProcDefByModelcode(code);
		/*总数大于0，提示先删除子节点，反之进行删除*/
		if(leafNodes != null && leafNodes.size() > 0)
		{
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.DELETE_SUBNODE_FIRST);
		}else if(exist)
		{
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.BIZ_MODEL_NOT_DEL);
		}
		else
		{
			workflowEngine.getProcessService().deleteBizModule(code);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
		}
		return tips;
	}
	
	/**
	 * 新增节点
	 * @param bizModule
	 * @return
	 */
	@RequestMapping(value = "/updateNode")
	@ResponseBody
	public Map<String, Object> updateNode(BizModule bizModule) {
		Map<String, Object> tips = new HashMap<String, Object>();
		try
		{
			/*判断当前对象是否存在, 不存在就添加*/
			boolean isExist = workflowEngine.getProcessService().existsBizModule(bizModule.getCode());
			if(isExist)
			{
				workflowEngine.getProcessService().updateBizModule(bizModule);
			}
			else
			{
				workflowEngine.getProcessService().insertBizModule(bizModule);
			}
			
			tips.put(Treeable.TREE_ID, bizModule.getCode());
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
		}
		catch(Exception ex)
		{
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
		}
		return tips;
	}
	
	/**
	 * 初始化加载数据
	 * @param parentId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/treeNode")
	@ResponseBody
	public List<TreeNode> treeNode(String parentId, Model model, HttpServletRequest request) {
		List<? extends IBizModule> list = null;
		if(StringUtils.isNotBlank(parentId))
		{
			list = workflowEngine.getProcessService().selectBizModule(parentId);
		}
		else
		{
			list = workflowEngine.getProcessService().selectBizModule();
		}
		
		String[] columns = new String[]{
			"code",
			"name",
			"remark",
			"parentId",
		};
		
		List<TreeNode> nodes = TreeUtils.parse((List<BizModule>)list, columns);
		return nodes;
	}
	

	/**
	 * 验证模块编码是否已存在
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> check(String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		boolean isExist = workflowEngine.getProcessService().existsBizModule(code);
		tips.put("valid", !isExist);
		
		return tips;
	}
	
	/**
	 * 以特殊格式获取所有模块数据
	 * @return json格式返回
	 */
	@RequestMapping(value = "/queryAll")
	@ResponseBody
	public List<NameValuePair> queryAll(){
		
		List<? extends IBizModule> result = workflowEngine.getProcessService().selectBizModule();
		
		return NVUtils.convert(result, result.size(), "code", "name");
	}
	
}