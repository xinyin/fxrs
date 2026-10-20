/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月31日 10:17:03
 *
 */

package com.yzj.jep.app.sub.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.InsServiceViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
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

import com.yzj.jep.app.sub.service.InsServiceService;

@Controller
@RequestMapping("/sub/insService")
public class InsServiceController extends CrudController<InsService> {
	private static Logger	logger	= LoggerFactory.getLogger(InsServiceController.class);

	@Autowired
	private InsServiceService	insServiceService;

	@Autowired
	private InsServiceViewService insServiceViewService;

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
		
		return "sub/insServiceList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<InsService> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
		
		insServiceService.query(page, filters, orders);
	}

	/**
	 * 新增报送文件
	 */
	@RequestMapping(value = "/addSubFile")
	public String addSubFile(Model model) {
		UserProfile profile = UserProfileUtils.getUserProfile();
		String tenantId = subjectDate.get(profile.getTenantId());
		if (tenantId == null || tenantId ==""){
			model.addAttribute("subOrg", "");
		}else {
			model.addAttribute("subOrg", tenantId);
		}
		return "sub/serviceSubFile";
	}

	/**
	 * 查询所有满足条件的待报送文件
	 */
	@RequestMapping(value = "/querySubFile")
	@ResponseBody
	public Map<String,Object> querySubFile(Page<InsService> page,HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		List<Filter> filterss = new Filter.Builder().eq("status","3").build();
		filterss.add(new Filter("delflag", "1"));//表示没删除的数据
		List<Order> orders = RequestUtils.buildOrders(request);
		orders.add(new Order("modifiedTime", OrderType.DESC));
		PageHelper.start(page);
		List<InsService> insServices = insServiceService.select(filterss);
		PageHelper.end(page,insServices);
		map.put(RESULT_TOTAL, page.getTotal());
		map.put(RESULT_ROWS, page.getResult());
		return map;
	}

	/**
	 * 查看页面展示数据 viewHandler
	 */
	@RequestMapping(value = "/viewHandler/{pid}")
	@ResponseBody
	public Map<String,Object> viewHandler(Page<InsServiceView> page, HttpServletRequest request, @PathVariable Integer pid){
		Map<String,Object> result = new HashMap<>();
		List<Filter> filters = RequestUtils.buildFilters(request);
		//通过pid去sublog表中查询所选择的对应数据 获取他的流水号
		Sublog sublog = sublogService.get(pid);
		String serviceSeq = sublog.getBatchDecimal();
		filters.add(new Filter("serviceSeq",serviceSeq));
		List<Order> orders = RequestUtils.buildOrders(request);
		orders.add(new Order("modifiedTime", OrderType.DESC));
		PageHelper.start(page);
		List<InsServiceView> serviceView =insServiceViewService.select(filters);
		PageHelper.end(page, serviceView);
		result.put(RESULT_TOTAL, page.getTotal());
		result.put(RESULT_ROWS, page.getResult());
		return result;
	}


	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "sub/insServiceEdit";
	}
	
	/**
	 * 编辑页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{pid}")
	public String edit(@PathVariable Integer pid, Model model){
		
		InsService insService = insServiceService.get(pid);
		model.addAttribute("insService", insService);
		model.addAttribute("opt", "edit");
		
		return "sub/insServiceEdit";
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
		return "sub/insServiceView";
	}
	
	/**
	 * 保存
	 * @param insService
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(InsService insService) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = insServiceService.exists(insService.getPid());
		try 
		{
			if(isExist)
			{
				
				insServiceService.update(insService);
			}
			else
			{
				insServiceService.insert(insService);
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
			filters.add(Filter.eq("serviceSeq", sublog.getBatchDecimal()));
			sublogService.deleteByPrimaryKey(pid);
			List<InsServiceView> insServiceViews = insServiceViewService.select(filters);
			for (InsServiceView insServiceView : insServiceViews) {
				InsService insService = insServiceService.get(insServiceView.getPortPid());
				insService.setStatus("3");
				insServiceService.updateSelective(insService);
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
		
		boolean exist = insServiceService.exists(pid);
		tips.put("valid", !exist);
		
		return tips;
	}
}