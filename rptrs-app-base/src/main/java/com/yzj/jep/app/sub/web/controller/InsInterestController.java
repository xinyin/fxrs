/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月31日 10:16:59
 *
 */

package com.yzj.jep.app.sub.web.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.InsInterestViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.service.TenantService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.sub.service.InsInterestService;

@Controller
@RequestMapping("/sub/insInterest")
public class InsInterestController extends CrudController<InsInterest> {
	private static Logger	logger	= LoggerFactory.getLogger(InsInterestController.class);

	@Autowired
	private InsInterestService	insInterestService;

	@Autowired
	private InsInterestViewService insInterestViewService;

	@Autowired
	private SublogService sublogService;

	@Autowired
	private TenantService tenantService;
	private static final Map<String, String> subjectDate = new HashMap<>();

	static {

		/*
		 * 根据不同的主体来添加不同的后缀
		 * */
		subjectDate.put("FX", "复星保德信人寿保险有限公司");
	}
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "sub/insInterestList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<InsInterest> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
		
		insInterestService.query(page, filters, orders);
	}

	/**
	 * 新增报送文件
	 */
	@RequestMapping(value = "/addSubFile")
	public String addSubFile(Model model){
		UserProfile profile = UserProfileUtils.getUserProfile();
		String tenantId = subjectDate.get(profile.getTenantId());
		if (tenantId == null || tenantId ==""){
			model.addAttribute("subOrg", "");
		}else {
			model.addAttribute("subOrg", tenantId);
		}
		return "sub/interestSubFile";
	}

	/**
	 * 查询所有满足条件的待报送文件
	 */
	@RequestMapping(value = "/querySubFile")
	@ResponseBody
	public Map<String,Object> querySubFile(Page<InsInterest> page, HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		List<Filter> filters = RequestUtils.buildFilters(request);
		filters.add(new Filter("status", "3"));  //表示待报送
		filters.add(new Filter("delflag", "1"));//表示没删除的数据
		AuthorityUtil authorityUtil = new AuthorityUtil();
		//filters = authorityUtil.getFilterByRole(filters);
		List<Order> orders = RequestUtils.buildOrders(request);
		orders.add(new Order("modifiedTime", OrderType.DESC));
		PageHelper.start(page);
		List<InsInterest> insInterests = insInterestService.select(filters);
		PageHelper.end(page, insInterests);
		map.put(RESULT_TOTAL, page.getTotal());
		map.put(RESULT_ROWS, page.getResult());
		return map;
	}

	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "sub/insInterestEdit";
	}
	
	/**
	 * 编辑页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{pid}")
	public String edit(@PathVariable Integer pid, Model model){
		
		InsInterest insInterest = insInterestService.get(pid);
		model.addAttribute("insInterest", insInterest);
		model.addAttribute("opt", "edit");
		
		return "sub/insInterestEdit";
	}
	
	/**
	 * 查看页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{pid}")
	public String view(@PathVariable Integer pid, Model model) {

		UserProfile profile = UserProfileUtils.getUserProfile();
		Sublog sublog = sublogService.get(pid);
		String tenantId = subjectDate.get(profile.getTenantId());
		//格式化规则
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//格式化成yyyy-MM-dd格式的时间字符串
		String strDate= sdf.format(sublog.getMaxPercentInNetAssets() );
		model.addAttribute("date",strDate);
		model.addAttribute("sublog", sublog);
		model.addAttribute("subOrg", tenantId);
		return "sub/insInterestView";
	}

	/**
	 * 查看页面展示数据 viewHandler
	 */
	@RequestMapping(value = "/viewHandler/{pid}")
	@ResponseBody
	public Map<String,Object> viewHandler(Page<InsInterestView> page, HttpServletRequest request, @PathVariable Integer pid){
		Map<String,Object> result = new HashMap<>();
		List<Filter> filters = RequestUtils.buildFilters(request);
		//通过pid去sublog表中查询所选择的对应数据 获取他的流水号
		Sublog sublog = sublogService.get(pid);
		String interestSeq = sublog.getBatchDecimal();
		filters.add(new Filter("interestSeq",interestSeq));
		List<Order> orders = RequestUtils.buildOrders(request);
		orders.add(new Order("modifiedTime", OrderType.DESC));
		PageHelper.start(page);
		List<InsInterestView> insInterestView =insInterestViewService.select(filters);
		PageHelper.end(page, insInterestView);
		result.put(RESULT_TOTAL, page.getTotal());
		result.put(RESULT_ROWS, page.getResult());
		return result;
	}


	/**
	 * 保存
	 * @param insInterest
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(InsInterest insInterest) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = insInterestService.exists(insInterest.getPid());
		try 
		{
			if(isExist)
			{
				
				insInterestService.update(insInterest);
			}
			else
			{
				insInterestService.insert(insInterest);
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

		try {
			Sublog sublog = sublogService.get(pid);
			List<Filter> filters = new ArrayList<>();
			filters.add(Filter.eq("interestSeq", sublog.getBatchDecimal()));
			sublogService.deleteByPrimaryKey(pid);
			List<InsInterestView> insInterestViews = insInterestViewService.select(filters);
			for (InsInterestView insInterestView : insInterestViews) {
				InsInterest insInterest = insInterestService.get(insInterestView.getPortPid());
				insInterest.setStatus("3");
				insInterestService.updateSelective(insInterest);
			}
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
		} catch (ServiceException e) {
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
		
		boolean exist = insInterestService.exists(pid);
		tips.put("valid", !exist);
		
		return tips;
	}
}