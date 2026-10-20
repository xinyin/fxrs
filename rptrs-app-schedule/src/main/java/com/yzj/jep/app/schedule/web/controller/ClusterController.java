/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年10月27日 14:56:58
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

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.schedule.server.model.Cluster;
import com.yzj.jep.schedule.server.service.ClusterService;
import com.yzj.jep.schedule.server.service.NodeService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

/**
 * 集群管理
 * @author yinke.hou
 * @time 2017年10月28日 上午10:11:59
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sch/cluster")
public class ClusterController extends CrudController<Cluster>{
	private static Logger	logger	= LoggerFactory.getLogger(ClusterController.class);

	@Autowired
	private ClusterService	clusterService;
	
	@Autowired
	private NodeService		nodeService;
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "sch/clusterList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<Cluster> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		clusterService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "sch/clusterEdit";
	}
	
	/**
	 * 编辑页面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{code}")
	public String edit(@PathVariable String code, Model model){
		
		Cluster cluster = clusterService.get(code);
		model.addAttribute("cluster", cluster);
		model.addAttribute("opt", "edit");
		
		return "sch/clusterEdit";
	}
	
	/**
	 * 查看页面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{code}")
	public String view(@PathVariable String code, Model model) {
		
		Cluster cluster = clusterService.get(code);
		model.addAttribute("cluster", cluster);
		
		return "sch/clusterView";
	}
	
	/**
	 * 保存
	 * @param cluster
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(Cluster cluster) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = clusterService.exists(cluster.getCode());
		try 
		{
			if(isExist)
			{
				clusterService.update(cluster);
			}
			else
			{
				clusterService.insert(cluster);
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
			clusterService.deleteCluster(code);
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
	 * 验证提交的code是否已经存在数据库中
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> check(String code){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Cluster exist = clusterService.get(code);
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
	
	/**
	 * 查询全部数据
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/queryAll")
	@ResponseBody
	public List<NameValuePair> queryAll(Model model){
		List<Cluster> result = clusterService.selectAll();
		List<NameValuePair> nameValuePairs = NVUtils.convert(result, result.size(), "code", "name");
		
		return nameValuePairs;
	}
	
	/**
	 * 查询集群 并且查询启用的节点
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/queryClusterAndNodeAll")
	@ResponseBody
	public Map<String, Object> queryClusterAndNodeAll(Model model){
		Map<String, Object> tips = new HashMap<String, Object>();
		tips.put("clusters", clusterService.selectAll());
		tips.put("nodes", nodeService.selectNodeStateAll());
		return tips;
	}
}