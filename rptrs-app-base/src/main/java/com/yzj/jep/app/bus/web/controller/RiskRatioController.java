/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年07月06日 16:15:35
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollUtil;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.RiskRatio;

import com.yzj.jep.app.bus.service.RiskRatioService;


@Controller
@RequestMapping("/bus/riskRatio")
public class RiskRatioController extends CrudController<RiskRatio> {
	private static Logger	logger	= LoggerFactory.getLogger(RiskRatioController.class);

	@Autowired
	private RiskRatioService	riskRatioService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {

		return "bus/riskRatioList";
	}

	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<RiskRatio> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

		riskRatioService.query(page, filters, orders);
	}

	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		return "bus/riskRatioAdd";
	}

	/**
	 * 查询个数
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/count")
	@ResponseBody
	public Map<String, Object> count(){
		Map<String, Object> tips = new HashMap<String, Object>();
		try
		{
			List<Filter> filters = new ArrayList<>();
			filters.add(new Filter("delflag", "1"));
			List<RiskRatio> ratioList = riskRatioService.select(filters);
			if(CollUtil.isEmpty(ratioList)){
				tips.put(WebCode.SUCCESS, true);
				tips.put("count",ratioList.size());
			}else {
				tips.put(WebCode.SUCCESS, false);
				tips.put(WebCode.MSG, "新增失败，最多只能有一条预警信息");
			}
		}
		catch (ServiceException e)
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, "新增失败，最多只能有一条预警信息");
		}

		return tips;
	}

	/**
	 * 编辑页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{pid}")
	public String edit(@PathVariable Integer pid, Model model){

		RiskRatio riskRatio = riskRatioService.get(pid);
		model.addAttribute("riskRatio", riskRatio);
		model.addAttribute("opt", "edit");

		return "bus/riskRatioEdit";
	}

	/**
	 * 查看页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{pid}")
	public String view(@PathVariable Integer pid, Model model) {

		RiskRatio riskRatio = riskRatioService.get(pid);
		model.addAttribute("riskRatio", riskRatio);

		return "bus/riskRatioView";
	}



	/**
	 * 保存
	 * @param riskRatio
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(RiskRatio riskRatio) {

		Map<String, Object> tips = new HashMap<String, Object>();
		boolean b = XssCheck.reflect(riskRatio);
		if (!b) {
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, "数据格式不规范，请检查!");
			return tips;
		}
		Boolean isExist = riskRatioService.exists(riskRatio.getPid());
		try
		{
			if(isExist)
			{

				riskRatioService.update(riskRatio);
			}
			else
			{
				riskRatioService.insert(riskRatio);
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
			riskRatioService.deleteByPrimaryKey(pid);
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

		boolean exist = riskRatioService.exists(pid);
		tips.put("valid", !exist);

		return tips;
	}
}
