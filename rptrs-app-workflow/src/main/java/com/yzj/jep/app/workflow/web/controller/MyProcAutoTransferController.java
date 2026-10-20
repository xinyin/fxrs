/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年12月04日 16:47:30
 *
 */

package com.yzj.jep.app.workflow.web.controller;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.wf.model.ProcAutoTransfer;
import com.yzj.jep.workflow.wf.service.ProcAutoTransferService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wf/myprepareQuery")
public class MyProcAutoTransferController extends CrudController<ProcAutoTransfer>{
	private static Logger	logger	= LoggerFactory.getLogger(MyProcAutoTransferController.class);

	@Autowired
	private ProcAutoTransferService	procAutoTransferService;


	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<ProcAutoTransfer> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		UserProfile profile = UserProfileUtils.getUserProfile();
		filters.add(new Filter("delflag", Operator.EQ, 1));
		filters.add(new Filter("transferStaffCode", Operator.EQ, profile.getStaffCode()));
		orders.add(new Order("createTime", OrderType.DESC));
		procAutoTransferService.query(page, filters, orders);
	}
	

}