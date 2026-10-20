/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年07月14日 10:43:08
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.time.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.service.UserProfileService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.util.ConfidentialInfoUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.sys.model.User;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.wf.model.ProcAutoTransfer;
import com.yzj.jep.workflow.wf.service.ProcAutoTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yzj.jep.app.bus.service.EntrustApplyService;


@Controller
@RequestMapping("/sys/entrustApply")
public class EntrustApplyController extends CrudController<EntrustApply> {
	private static Logger	logger	= LoggerFactory.getLogger(EntrustApplyController.class);

	@Autowired
	private EntrustApplyService	entrustApplyService;

	@Autowired
	private ProcAutoTransferService procAutoTransferService;

	@Autowired
	private UserProfileService userProfileService;
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "bus/entrustApplyList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<EntrustApply> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
		UserProfile profile = UserProfileUtils.getUserProfile();
		filters.add(new Filter("staffCode",Operator.EQ,profile.getStaffCode()));
		orders.add(new Order("createTime", OrderType.DESC));
		entrustApplyService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		UserProfile profile = UserProfileUtils.getUserProfile();
		EntrustApply entrustApply = new EntrustApply();
		entrustApply.setStaffName(profile.getStaffName());
		entrustApply.setStaffCode(profile.getStaffCode());
		entrustApply.setStaffDep(profile.getDeptName());
		model.addAttribute("entrustApply",entrustApply);
		return "bus/entrustApplyEdit";
	}
	
	/**
	 * 编辑页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{pid}")
	public String edit(@PathVariable Integer pid, Model model){
		
		EntrustApply entrustApply = entrustApplyService.get(pid);
		model.addAttribute("entrustApply", entrustApply);
		model.addAttribute("opt", "edit");
		
		return "bus/entrustApplyEdit";
	}
	
	/**
	 * 查看页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{pid}")
	public String view(@PathVariable Integer pid, Model model) {
		
		EntrustApply entrustApply = entrustApplyService.get(pid);
		model.addAttribute("entrustApply", entrustApply);
		model.addAttribute("opt", "view");
		return "bus/entrustApplyEdit";
	}
	
	/**
	 * 保存
	 * @param entrustApply
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(EntrustApply entrustApply) {
		Map<String, Object> tips = new HashMap<String, Object>();
		try {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(entrustApply.getEndTime().getTime()), ZoneId.systemDefault());;
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		EntrustApply entrust = entrustApplyService.get(entrustApply.getPid());
		AuthorityUtil authorityUtil = new AuthorityUtil();
		//判断是新增委托还是变更委托
		if("1".equals(entrustApply.getEntrustType())){
			//变更委托查询新委托人信息
			User user = new User();
			user.setUserId(entrustApply.getTransferStaffCodeNew());
			UserProfile userDetail = userProfileService.getUserProfileByUser(user, null);
			entrustApply.setTransferStaffCode(entrustApply.getTransferStaffCodeNew());
			entrustApply.setStaffDep(entrustApply.getTransferStaffDep());
			entrustApply.setTransferStaffDep(userDetail.getDeptName());
			entrustApply.setTransferStaffNameNew(userDetail.getStaffName());
		}else {
			entrustApply.setEndTime(Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant()));
		}
		//未提交审批前判断是修改还是新增
		if(null != entrust) {
			if(5==entrustApply.getStatus()){
				entrustApply.setStatus(3);
			}
			entrustApplyService.updateSelective(entrustApply);
		} else {
			//只有经办人和审核人角色才会需要提交审批
			boolean type = authorityUtil.getRoleType();
			if(type){
				//部门领导新增通过的委托申请
				entrustApply.setStatus(6);
				entrustApplyService.insertSelective(entrustApply);
				//新增委托任务
				ProcAutoTransfer procAutoTransfer = new ProcAutoTransfer();
				LocalDate localDate =LocalDate.now();
				Date date = entrustApply.getStartTime();
				Instant instant = date.toInstant();
				ZoneId zoneId = ZoneId.systemDefault();
				LocalDate stDate = instant.atZone(zoneId).toLocalDate();
				if(stDate.isAfter(localDate)){
					procAutoTransfer.setStatus("0");
				}else{
					procAutoTransfer.setStatus("1");
				}
					procAutoTransfer.setStaffName(entrustApply.getStaffName());
					procAutoTransfer.setStaffCode(entrustApply.getStaffCode());
					procAutoTransfer.setStartTime(entrustApply.getStartTime());
					procAutoTransfer.setStaffDep(entrustApply.getStaffDep());
					procAutoTransfer.setEndTime(entrustApply.getEndTime());
					procAutoTransfer.setTransferStaffDep(entrustApply.getTransferStaffDep());
					procAutoTransfer.setApproveStatus("6");
					procAutoTransfer.setDelflag("1");
					procAutoTransfer.setCreateTime(new Date());
					//新增委托
					if("0".equals(entrustApply.getEntrustType())){
						procAutoTransfer.setTransferStaffName(entrustApply.getTransferStaffName());
						procAutoTransfer.setTransferStaffCode(entrustApply.getTransferStaffCode());
					}else {
						//变更委托
						//1:更具任务编号先取消原来的委托任务
						ProcAutoTransfer pro = procAutoTransferService.get(entrustApply.getTaskNumber());
						pro.setStatus("3");
						procAutoTransferService.update(pro);
						//2：添加新的委托任务
						procAutoTransfer.setTransferStaffName(entrustApply.getTransferStaffNameNew());
						procAutoTransfer.setTransferStaffCode(entrustApply.getTransferStaffCodeNew());
						}
						procAutoTransfer.setId(UUID.randomUUID().toString());
						procAutoTransferService.insertSelective(procAutoTransfer);
						tips.put(WebCode.SUCCESS, true);
						tips.put(WebCode.MSG, "您的委托申请不需要提交审批流程，已保存成功并创建委托任务，请在委托任务进行查看。");
						return tips;
				}else {
					//经办人或审核人新增待提交委托申请
					entrustApply.setStatus(3);
				    entrustApplyService.insertSelective(entrustApply);
			}
			}
			
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
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
			entrustApplyService.deleteByPrimaryKey(pid);
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
		
		boolean exist = entrustApplyService.exists(pid);
		tips.put("valid", !exist);
		
		return tips;
	}

	/*
	 * 提交审批
	 * */
	@RequestMapping(value = "/send/{pid}")
	@ResponseBody
	public Map<String, Object> send(@PathVariable String pid, Model model) {
		Map<String, Object> tips = new HashMap<String, Object>();
		EntrustApply entrustApply = entrustApplyService.get(Integer.valueOf(pid));
		if (3 == entrustApply.getStatus()) {
			//4 代表审批中
			entrustApply.setStatus(4);
			entrustApplyService.updateSelective(entrustApply);
			tips.put("data", entrustApply);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
		} else {
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, "状态不符，提交失败！");
		}
		return tips;
	}

}