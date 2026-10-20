/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月31日 10:17:01
 *
 */

package com.yzj.jep.app.sub.web.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.InsBalanceService;
import com.yzj.jep.app.sub.service.InsInvestmentViewService;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.sub.service.InsInvestmentService;

@Controller
@RequestMapping("/sub/insInvestment")
public class InsInvestmentController extends CrudController<InsInvestment> {
	private static Logger	logger	= LoggerFactory.getLogger(InsInvestmentController.class);

	@Autowired
	private InsInvestmentService	insInvestmentService;

	@Autowired
	private InsInvestmentViewService insInvestmentViewService;

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
		
		return "sub/insInvestmentList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<InsInvestment> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
		
		insInvestmentService.query(page, filters, orders);
	}

	/**
	 * 新增报送文件
	 */
	@RequestMapping(value = "/addSubFile")
	@Log(module = "投资入股类",description = "新增报送文件列表")
	public String addSubFile(Model model) {
		UserProfile profile = UserProfileUtils.getUserProfile();
		String tenantId = subjectDate.get(profile.getTenantId());
		if (tenantId == null || tenantId ==""){
			model.addAttribute("subOrg", "");
		}else {
			model.addAttribute("subOrg", tenantId);
		}
		return "sub/investmentSubFile";
	}

	/**
	 * 查询所有满足条件的待报送文件
	 */
	@RequestMapping(value = "/querySubFile")
	@ResponseBody
	public Map<String,Object> querySubFile(Page<InsInvestment> page1,HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		List<Filter> filterss = new Filter.Builder().eq("status","3").build();
		filterss.add(new Filter("delflag", "1"));//表示没删除的数据
		PageHelper.start(page1);
		List<InsInvestment> insInvestment = insInvestmentService.select(filterss);
		PageHelper.end(page1,insInvestment);
		map.put(RESULT_TOTAL, page1.getTotal());
		map.put(RESULT_ROWS, page1.getResult());
		return map;
	}

	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "sub/insInvestmentEdit";
	}
	
	/**
	 * 编辑页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{pid}")
	public String edit(@PathVariable Integer pid, Model model){
		
		InsInvestment insInvestment = insInvestmentService.get(pid);
		model.addAttribute("insInvestment", insInvestment);
		model.addAttribute("opt", "edit");
		
		return "sub/insInvestmentEdit";
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
		return "sub/insInvestmentView";
	}

	/**
	 * 查看页面展示数据 viewHandler
	 */
	@RequestMapping(value = "/viewHandler/{pid}")
	@ResponseBody
	public Map<String,Object> viewHandler(Page<InsInvestmentView> page, HttpServletRequest request, @PathVariable Integer pid){
		Map<String,Object> result = new HashMap<>();
		List<Filter> filters = RequestUtils.buildFilters(request);
		//通过pid去sublog表中查询所选择的对应数据 获取他的流水号
		Sublog sublog = sublogService.get(pid);
		String investmentSeq = sublog.getBatchDecimal();
		filters.add(new Filter("investmentSeq",investmentSeq));
		List<Order> orders = RequestUtils.buildOrders(request);
		orders.add(new Order("modifiedTime", OrderType.DESC));
		PageHelper.start(page);
		List<InsInvestmentView> insInvestmentView =insInvestmentViewService.select(filters);
		PageHelper.end(page, insInvestmentView);
		result.put(RESULT_TOTAL, page.getTotal());
		result.put(RESULT_ROWS, page.getResult());
		return result;
	}

	/**
	 * 保存
	 * @param insInvestment
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(InsInvestment insInvestment) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = insInvestmentService.exists(insInvestment.getPid());
		try 
		{
			if(isExist)
			{
				
				insInvestmentService.update(insInvestment);
			}
			else
			{
				insInvestmentService.insert(insInvestment);
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
			filters.add(Filter.eq("investmentSeq", sublog.getBatchDecimal()));
			sublogService.deleteByPrimaryKey(pid);
			List<InsInvestmentView> insInvestmentViews = insInvestmentViewService.select(filters);
			for (InsInvestmentView insInvestmentView : insInvestmentViews) {
				InsInvestment insInvestment = insInvestmentService.get(insInvestmentView.getPortPid());
				insInvestment.setStatus("3");
				insInvestmentService.updateSelective(insInvestment);
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
		
		boolean exist = insInvestmentService.exists(pid);
		tips.put("valid", !exist);
		
		return tips;
	}
}