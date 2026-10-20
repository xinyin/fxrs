/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年12月25日 15:19:35
 *
 */

package com.yzj.jep.app.pub.web.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.pub.model.OaApprovalLog;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.pub.service.OaApprovalLogService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/pub/oaApprovalLog")
public class OaApprovalLogController extends CrudController<OaApprovalLog>{
	private static Logger	logger	= LoggerFactory.getLogger(OaApprovalLogController.class);

	@Autowired
	private OaApprovalLogService	oaApprovalLogService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "pub/oaApprovalLogList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<OaApprovalLog> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		//从过滤器中取出关于审批id得查询条件
		List<Filter> filterList = new ArrayList<>();
		for (Filter f:filters) {
			if ("approvalId".equals(f.getProperty())){
				filterList.add(f);
			}
		}
		if (filterList.size() == 0) {
			filterList.add(new Filter("approvalId","查不到"));
		}
			oaApprovalLogService.query(page, filterList, orders);

	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "pub/oaApprovalLogEdit";
	}
	
	/**
	 * 编辑页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{pid}")
	public String edit(@PathVariable Integer pid, Model model){
		
		OaApprovalLog oaApprovalLog = oaApprovalLogService.get(pid);
		model.addAttribute("oaApprovalLog", oaApprovalLog);
		model.addAttribute("opt", "edit");
		
		return "pub/oaApprovalLogEdit";
	}
	
	/**
	 * 查看页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{pid}")
	public String view(@PathVariable Integer pid, Model model) {
		
		OaApprovalLog oaApprovalLog = oaApprovalLogService.get(pid);
		model.addAttribute("oaApprovalLog", oaApprovalLog);
		
		return "pub/oaApprovalLogView";
	}
	
	/**
	 * 保存
	 * @param oaApprovalLog
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(OaApprovalLog oaApprovalLog) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = oaApprovalLogService.exists(oaApprovalLog.getPid());
		try 
		{
			if(isExist)
			{
				
				oaApprovalLogService.update(oaApprovalLog);
			}
			else
			{
				oaApprovalLogService.insert(oaApprovalLog);
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
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/remove/{pid}")
	@ResponseBody
	public Map<String, Object> remove(@PathVariable Integer pid){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			oaApprovalLogService.deleteByPrimaryKey(pid);
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
	 * 验证提交的pid是否已经存在数据库中
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> check(Integer pid){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		boolean exist = oaApprovalLogService.exists(pid);
		tips.put("valid", !exist);
		
		return tips;
	}
}