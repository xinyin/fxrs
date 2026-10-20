/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年10月27日 16:40:37
 *
 */

package com.yzj.jep.app.schedule.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.SchedulerException;
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
import com.yzj.jep.schedule.server.calendar.TimeRange;
import com.yzj.jep.schedule.server.model.CalendarDetail;
import com.yzj.jep.schedule.server.service.CalendarDetailService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

/**
 * 日历管理
 * @author yinke.hou
 * @time 2017年11月1日 上午10:02:04
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sch/calendarDetail")
public class CalendarDetailController extends CrudController<CalendarDetail>{
	private static Logger	logger	= LoggerFactory.getLogger(CalendarDetailController.class);

	@Autowired
	private CalendarDetailService	calendarDetailService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "sch/calendarDetailList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<CalendarDetail> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		calendarDetailService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "sch/calendarDetailEdit";
	}
	
	/**
	 * 编辑页面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{code}")
	public String edit(@PathVariable String code, Model model){
		
		CalendarDetail calendarDetail = calendarDetailService.get(code);
		model.addAttribute("calendarDetail", calendarDetail);
		model.addAttribute("opt", "edit");
		
		return "sch/calendarDetailEdit";
	}
	
	/**
	 * 查看页面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{code}")
	public String view(@PathVariable String code, Model model) {
		
		CalendarDetail calendarDetail = calendarDetailService.get(code);
		model.addAttribute("calendarDetail", calendarDetail);
		
		return "sch/calendarDetailView";
	}
	
	/**
	 * 设计日期包含或排除
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/set/{code}")
	public String set(@PathVariable String code, Model model){
		
		CalendarDetail calendarDetail = calendarDetailService.get(code);
		model.addAttribute("calendarDetail", calendarDetail);
		
		return "sch/calendarDetailSet";
	}
	
	/**
	 * 保存
	 * @param calendarDetail
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(CalendarDetail calendarDetail) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = calendarDetailService.exists(calendarDetail.getCode());
		try 
		{
			if(!isExist)
			{
				calendarDetailService.insertCalendarDetail(calendarDetail);
			}
			else
			{
				calendarDetailService.update(calendarDetail);
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
			calendarDetailService.deleteCalendarDetail(code);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
		} 
		catch (ServiceException | SchedulerException e) 
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
		
		CalendarDetail exist = calendarDetailService.get(code);
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
	 * 设置包含或者排除星期
	 * @param code
	 * @param index
	 * @param isWeek
	 * @return
	 */
	@RequestMapping(value = "/week/set")
	@ResponseBody
	public Map<String, Object> weekSet(String code, int index, boolean isWeek) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			calendarDetailService.setWeek(code, index, isWeek);
			
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
		}
		catch (ServiceException | SchedulerException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
		}
		return tips;
	}
	
	/**
	 * 获取星期
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/week/{code}")
	@ResponseBody
	public List<Integer> listWeek(@PathVariable String code) {

		return calendarDetailService.listWeek(code);
	}
	
	/**
	 * 新增排除时间设置
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/time/add/{code}")
	public String timeAdd(@PathVariable String code, Model model) {
		
		model.addAttribute("code", code);
		return "sch/calendarDetailTimeEdit";
	}
	
	/**
	 * 排除时间段设置
	 * @param code
	 * @param startHour
	 * @param startMinute
	 * @param endHour
	 * @param endMinute
	 * @return
	 */
	@RequestMapping(value = "/time/set")
	@ResponseBody
	public Map<String, Object> timeSet(String code, int startHour, int startMinute, int endHour, int endMinute) {
		Map<String, Object> tips = new HashMap<String, Object>();
		try 
		{
			calendarDetailService.setTime(code, startHour, startMinute, endHour, endMinute);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
		}
		catch (ServiceException | SchedulerException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
		}
		return tips;
	}
	
	/**
	 * 移除排除时间段设置
	 * @param code
	 * @param startHour
	 * @param startMinute
	 * @param endHour
	 * @param endMinute
	 * @return
	 */
	@RequestMapping(value = "/time/remove")
	@ResponseBody
	public Map<String, Object> removeTime(String code, int startHour, int startMinute, int endHour, int endMinute) {
		Map<String, Object> tips = new HashMap<String, Object>();
		try 
		{
			calendarDetailService.removeTime(code, startHour, startMinute, endHour, endMinute);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
		}
		catch (ServiceException | SchedulerException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
		}
		return tips;
	}
	
	/**
	 * 查询排除的时间段
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/time/{code}")
	@ResponseBody
	public List<TimeRange> listTime(@PathVariable String code) {

		return calendarDetailService.listTime(code);
	}
	
	/**
	 * 返回包含排除的天数
	 * @param code
	 * @param month
	 * @return
	 */
	@RequestMapping(value = "/days/{code}/{month}")
	@ResponseBody
	public Map<String, Object> days(@PathVariable String code, @PathVariable int month) {
		Map<String, Object> tips = new HashMap<String, Object>();
		try 
		{
			List<Integer> includes = calendarDetailService.includeDays(code, month);
			List<Integer> excludes = calendarDetailService.excludeDays(code, month);
			tips.put("includes", includes);
			tips.put("excludes", excludes);
			tips.put(WebCode.SUCCESS, true);
		}
		catch (ServiceException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
		}
		return tips;
	}

	/**
	 * 设置包含的日期
	 * @param code 日期编码
	 * @param date 日期
	 * @param isSelect true 移除  false 包含
	 * @return
	 */
	@RequestMapping(value = "/set/include/days")
	@ResponseBody
	public Map<String, Object> setIncludeDays(String code, String date, boolean isSelect) {
		Map<String, Object> tips = new HashMap<String, Object>();
		try 
		{
			calendarDetailService.setIncludeDays(code, date, isSelect);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
		}
		catch (ServiceException | SchedulerException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
		}
		return tips;
	}
	
	/**
	 * 设置排除的日期
	 * @param code 日期编码
	 * @param date 日期
	 * @param isSelect true 移除  false 排除
	 * @return
	 */
	@RequestMapping(value = "/set/exclude/days")
	@ResponseBody
	public Map<String, Object> setExcludeDays(String code, String date, boolean isSelect) {
		Map<String, Object> tips = new HashMap<String, Object>();
		try 
		{
			calendarDetailService.setExcludeDays(code, date, isSelect);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
		}
		catch (ServiceException | SchedulerException e) 
		{
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
		}
		return tips;
	}
	
	/**
	 * 查询可用日历
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/queryAll")
	@ResponseBody
	public List<NameValuePair> queryAll(Model model){
		List<CalendarDetail> result  = calendarDetailService.selectAll();
		List<NameValuePair> nameValuePairs = NVUtils.convert(result, result.size(), "code", "name");
		
		return nameValuePairs;
	}
}