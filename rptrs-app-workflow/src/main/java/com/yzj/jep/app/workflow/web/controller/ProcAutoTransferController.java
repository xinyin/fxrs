/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年12月04日 16:47:30
 *
 */

package com.yzj.jep.app.workflow.web.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.model.User;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.wf.model.ProcAutoTransfer;
import com.yzj.jep.workflow.wf.service.ProcAutoTransferService;

@Controller
@RequestMapping("/wf/procAutoTransfer")
public class ProcAutoTransferController extends CrudController<ProcAutoTransfer>{
	private static Logger	logger	= LoggerFactory.getLogger(ProcAutoTransferController.class);

	@Autowired
	private ProcAutoTransferService	procAutoTransferService;

	@Autowired
	private StaffService staffService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		UserProfile profile = UserProfileUtils.getUserProfile();
		if(profile == null)
		{
			throw new ServiceException("user session may be expired.");
		}
		model.addAttribute("staffCode", profile.getStaffCode());
		return "wf/procAutoTransferList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<ProcAutoTransfer> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		UserProfile profile = UserProfileUtils.getUserProfile();
		filters.add(new Filter("delflag", Operator.EQ, 1));
		filters.add(new Filter("staffCode", Operator.EQ, profile.getStaffCode()));
		orders.add(new Order("createTime", OrderType.DESC));
		procAutoTransferService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "wf/procAutoTransferEdit";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model){
		
		ProcAutoTransfer procAutoTransfer = procAutoTransferService.get(id);
		model.addAttribute("procAutoTransfer", procAutoTransfer);
		model.addAttribute("opt", "edit");
		
		return "wf/procAutoTransferEdit";
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{id}")
	public String view(@PathVariable String id, Model model) {
		
		ProcAutoTransfer procAutoTransfer = procAutoTransferService.get(id);
		model.addAttribute("procAutoTransfer", procAutoTransfer);
		
		return "wf/procAutoTransferView";
	}

	/**
	 * 保存
	 * @param procAutoTransfer
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(ProcAutoTransfer procAutoTransfer) {
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = procAutoTransferService.exists(procAutoTransfer.getId());
		Staff staff = staffService.get(procAutoTransfer.getStaffCode());
		try 
		{
			procAutoTransfer.setStaffCode(procAutoTransfer.getStaffCode());
			procAutoTransfer.setStaffName(staff.getStaffName());
			if(isExist)
			{
				
				procAutoTransferService.update(procAutoTransfer);
			}
			else
			{
				UserProfile profile = UserProfileUtils.getUserProfile();
				if(profile == null)
				{
					throw new ServiceException("user session may be expired.");
				}
				LocalDate localDate =LocalDate.now();
				Date date = procAutoTransfer.getStartTime();
				Instant instant = date.toInstant();
				ZoneId zoneId = ZoneId.systemDefault();
				LocalDate stDate = instant.atZone(zoneId).toLocalDate();
				if(stDate.isAfter(localDate)){
					procAutoTransfer.setStatus("0");
				}else{
					procAutoTransfer.setStatus("1");
				}
				procAutoTransferService.insert(procAutoTransfer);
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
			procAutoTransferService.deleteByPrimaryKey(id);
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
	public Map<String, Object> check(String id){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		boolean exist = procAutoTransferService.exists(id);
		tips.put("valid", !exist);
		
		return tips;
	}
	
	/**
	 * 验证提交的id是否已经存在数据库中
	 * @param procAutoTransfer
	 * @return
	 */
	@RequestMapping(value = "/isTransfered", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> isTransfered(ProcAutoTransfer procAutoTransfer){
		Map<String, Object> tips = new HashMap<String, Object>();
		
		List<Filter> filters1 = new Filter.Builder().eq("transferStaffCode", procAutoTransfer.getStaffCode())
				.ge("startTime", DateUtils.format(procAutoTransfer.getStartTime(), DateUtils.DATE_TIME_FORMAT))
				.le("startTime", DateUtils.format(procAutoTransfer.getEndTime(), DateUtils.DATE_TIME_FORMAT)).build();
		List<Filter> filters2 = new Filter.Builder().eq("transferStaffCode", procAutoTransfer.getStaffCode())
				.ge("endTime", DateUtils.format(procAutoTransfer.getStartTime(), DateUtils.DATE_TIME_FORMAT))
				.le("endTime", DateUtils.format(procAutoTransfer.getEndTime(), DateUtils.DATE_TIME_FORMAT))
				.build();
		if(StringUtils.isNotBlank(procAutoTransfer.getId())){
			Filter filter = new Filter("id", Operator.NE, procAutoTransfer.getId());
			filters1.add(filter);
			filters2.add(filter);
		}
		boolean exist1 = procAutoTransferService.exists(filters1);
		boolean exist2 = procAutoTransferService.exists(filters2);
		boolean flag = false;
		if(!(exist2||exist1)){
			flag = true;
		}
		tips.put("valid", flag);
		return tips;
	}

	/**
	 * 取消委托
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/cancel/{id}")
	@ResponseBody
	public Map<String, Object> cancel(@PathVariable String id){

		Map<String, Object> tips = new HashMap<String, Object>();

		try
		{
			ProcAutoTransfer procAutoTransfer = procAutoTransferService.get(id);
			procAutoTransfer.setStatus("3");
			procAutoTransferService.update(procAutoTransfer);
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
	 * 为列表页面做数据准备
	 */
	@RequestMapping(value = "/myprepareQuery")
	@ResponseBody
	protected void myprepareQuery(Page<ProcAutoTransfer> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {

		procAutoTransferService.query(page, filters, orders);
	}
}