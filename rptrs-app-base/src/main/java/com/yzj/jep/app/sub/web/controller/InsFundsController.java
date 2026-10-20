/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月31日 10:16:57
 *
 */

package com.yzj.jep.app.sub.web.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.InsBalanceService;
import com.yzj.jep.app.sub.service.InsFundsViewService;
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

import com.yzj.jep.app.sub.service.InsFundsService;

@Controller
@RequestMapping("/sub/insFunds")
public class InsFundsController extends CrudController<InsFunds> {
	private static Logger	logger	= LoggerFactory.getLogger(InsFundsController.class);

	@Autowired
	private InsFundsService	insFundsService;

	@Autowired
	private InsFundsViewService insFundsViewService;

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
		
		return "sub/insFundsList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<InsFunds> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
		
		insFundsService.query(page, filters, orders);
	}

	/**
	 * 新增报送文件
	 */
	@RequestMapping(value = "/addSubFile")
	@Log(module = "资金运用类",description = "新增报送文件列表")
	public String addSubFile(Model model){
		UserProfile profile = UserProfileUtils.getUserProfile();
		String tenantId = subjectDate.get(profile.getTenantId());
		if (tenantId == null || tenantId ==""){
			model.addAttribute("subOrg", "");
		}else {
			model.addAttribute("subOrg", tenantId);
		}
		return "sub/fundsSubFile";
	}

	/**
	 * 查询所有满足条件的待报送文件
	 */
	@RequestMapping(value = "/querySubFile")
	@ResponseBody
	public Map<String,Object> querySubFile(Page<InsFunds> page1, HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		List<Filter> filters = RequestUtils.buildFilters(request);
		filters.add(new Filter("status", "3"));  //表示待报
		List<Order> orders = RequestUtils.buildOrders(request);
		orders.add(new Order("modifiedTime", OrderType.DESC));
		PageHelper.start(page1);
		List<InsFunds> insFunds = insFundsService.select(filters,orders);
		PageHelper.end(page1,insFunds);
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
		
		return "sub/insFundsEdit";
	}
	
	/**
	 * 编辑页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{pid}")
	public String edit(@PathVariable Integer pid, Model model){
		
		InsFunds insFunds = insFundsService.get(pid);
		model.addAttribute("insFunds", insFunds);
		model.addAttribute("opt", "edit");
		
		return "sub/insFundsEdit";
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
		return "sub/insFundsView";
	}

	/**
	 * 查看页面展示数据 viewHandler
	 */
	@RequestMapping(value = "/viewHandler/{pid}")
	@ResponseBody
	public Map<String,Object> viewHandler(Page<InsFundsView> page, HttpServletRequest request, @PathVariable Integer pid){
		Map<String,Object> result = new HashMap<>();
		List<Filter> filters = RequestUtils.buildFilters(request);
		//通过pid去sublog表中查询所选择的对应数据 获取他的流水号
		Sublog sublog = sublogService.get(pid);
		String fundsSeq = sublog.getBatchDecimal();
		filters.add(new Filter("fundsSeq",fundsSeq));
		List<Order> orders = RequestUtils.buildOrders(request);
		orders.add(new Order("modifiedTime", OrderType.DESC));
		PageHelper.start(page);
		List<InsFundsView> insFundsView =insFundsViewService.select(filters);
		PageHelper.end(page, insFundsView);
		result.put(RESULT_TOTAL, page.getTotal());
		result.put(RESULT_ROWS, page.getResult());
		return result;
	}

	/**
	 * 保存
	 * @param insFunds
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(InsFunds insFunds) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = insFundsService.exists(insFunds.getPid());
		try 
		{
			if(isExist)
			{
				
				insFundsService.update(insFunds);
			}
			else
			{
				insFundsService.insert(insFunds);
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
			filters.add(Filter.eq("fundsSeq", sublog.getBatchDecimal()));
			sublogService.deleteByPrimaryKey(pid);
			List<InsFundsView> insFundsViews = insFundsViewService.select(filters);
			for (InsFundsView insFundsView : insFundsViews) {
				InsFunds insFunds = insFundsService.get(insFundsView.getPortPid());
				insFunds.setStatus("3");
				insFundsService.updateSelective(insFunds);
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
		
		boolean exist = insFundsService.exists(pid);
		tips.put("valid", !exist);
		
		return tips;
	}
}