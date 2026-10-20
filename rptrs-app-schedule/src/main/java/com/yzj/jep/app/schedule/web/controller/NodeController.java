/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年10月27日 14:57:04
 *
 */

package com.yzj.jep.app.schedule.web.controller;

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

import com.yzj.jep.schedule.server.model.Cluster;
import com.yzj.jep.schedule.server.model.Node;
import com.yzj.jep.core.code.Status;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.schedule.server.service.ClusterService;
import com.yzj.jep.schedule.server.service.NodeService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

/**
 * 节点管理
 * @author yinke.hou
 * @time 2017年10月28日 上午11:15:46
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sch/node")
public class NodeController extends CrudController<Node>{
	private static Logger	logger	= LoggerFactory.getLogger(NodeController.class);

	@Autowired
	private NodeService		nodeService;
	
	@Autowired
	private ClusterService	clusterService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "sch/nodeList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<Node> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		nodeService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "sch/nodeEdit";
	}
	
	/**
	 * 编辑页面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{code}")
	public String edit(@PathVariable String code, Model model){
		
		Node node = nodeService.get(code);
		model.addAttribute("node", node);
		model.addAttribute("opt", "edit");
		
		return "sch/nodeEdit";
	}
	
	/**
	 * 查看页面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{code}")
	public String view(@PathVariable String code, Model model) {
		
		Node node = nodeService.get(code);
		Cluster cluster = clusterService.get(node.getClusterCode());
		node.setClusterCode(cluster.getName());
		model.addAttribute("node", node);
		
		return "sch/nodeView";
	}
	
	/**
	 * 保存
	 * @param node
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(Node node) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = nodeService.exists(node.getCode());
		try 
		{
			if(isExist)
			{
				nodeService.update(node);
			}
			else
			{
				node.setStatus(Status.DRAFT.getValue());
				nodeService.insert(node);
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
			nodeService.deleteByPrimaryKey(code);
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
	 * 启用节点
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/enable/{code}")
	@ResponseBody
	public Map<String, Object> enable(@PathVariable String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			nodeService.enableNode(code);
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
	 * 停用节点
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/disable/{code}")
	@ResponseBody
	public Map<String, Object> disable(@PathVariable String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			nodeService.disableNode(code);
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
		
		Node exist = nodeService.get(code);
		if(exist == null)
		{
			tips.put("valid", true);
		}
		else
		{
			tips.put("valid", false);
		}
		
		return tips;
	}
}