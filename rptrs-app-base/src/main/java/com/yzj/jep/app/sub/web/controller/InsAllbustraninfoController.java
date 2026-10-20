/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:30
 *
 */

package com.yzj.jep.app.sub.web.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.InsAllbustraninfoService;
import com.yzj.jep.app.sub.service.InsAllbustraninfoViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/sub/insAllbustraninfo")
public class InsAllbustraninfoController extends CrudController<InsAllbustraninfo> {
	private static Logger	logger	= LoggerFactory.getLogger(InsAllbustraninfoController.class);

	@Autowired
	private InsAllbustraninfoService insAllbustraninfoService;

	@Autowired
	private SublogService sublogService;
	@Autowired
	private DictItemService dictItemService;

	@Autowired
	private InsAllbustraninfoViewService insAllbustraninfoViewService;
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
		String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
		String url = "sub/insAllbustraninfo/list";
		AuthorityUtil authorityUtil = new AuthorityUtil();
		String code = authorityUtil.buttonPerm(staffCode, url);
		model.addAttribute("code", code);


		return "sub/traAllbustraninfolist";
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
		return "sub/allbustraninfoSubFile";
	}

	/**
	 * 查询所有满足条件的待报送文件
	 */
	@RequestMapping(value = "/querySubFile" ,method = {RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> querySubFile(Page<InsAllbustraninfo> page, HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		List<Filter> filterss = RequestUtils.buildFilters(request);
		filterss.add(new Filter("status","3"));
		filterss.add(new Filter("delflag", "1"));//表示没删除的数据
		PageHelper.start(page);
		List<InsAllbustraninfo> insAllbustraninfos = insAllbustraninfoService.select(filterss);
		addData(insAllbustraninfos);
		PageHelper.end(page,insAllbustraninfos);
		map.put(RESULT_TOTAL, page.getTotal());
		map.put(RESULT_ROWS, page.getResult());
		return map;
	}
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<InsAllbustraninfo> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
		
		insAllbustraninfoService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model){
		
		return "sub/insAllbustraninfoEdit";
	}
	
	/**
	 * 编辑页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{pid}")
	public String edit(@PathVariable Integer pid, Model model){
		
		InsAllbustraninfo insAllbustraninfo = insAllbustraninfoService.get(pid);
		model.addAttribute("insAllbustraninfo", insAllbustraninfo);
		model.addAttribute("opt", "edit");
		
		return "sub/insAllbustraninfoEdit";
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    //格式化规则
		String strDate= sdf.format(sublog.getMaxPercentInNetAssets() ); //格式化成yyyy-MM-dd格式的时间字符串
		model.addAttribute("date",strDate);
		model.addAttribute("sublog", sublog);
		model.addAttribute("subOrg",tenantId);

		return "sub/insAllbustraninfoView";
	}
	@RequestMapping(value = "/viewHandler/{pid}")
	@ResponseBody
	public Map<String,Object> viewHandler(Page<InsAllbustraninfoView> page, HttpServletRequest request, @PathVariable Integer pid){
		Map<String,Object> result = new HashMap<>();
		List<Filter> filters = RequestUtils.buildFilters(request);
		//通过pid去sublog表中查询所选择的对应数据 获取他的流水号
		Sublog sublog = sublogService.get(pid);
		String businessSeq = sublog.getBatchDecimal();
		filters.add(new Filter("allbustraninfoSeq",businessSeq));
		List<Order> orders = RequestUtils.buildOrders(request);
		orders.add(new Order("modifiedTime", OrderType.DESC));
		PageHelper.start(page);
		List<InsAllbustraninfoView> insAllbustraninfoViews = insAllbustraninfoViewService.select(filters);
		addDataView(insAllbustraninfoViews);
		PageHelper.end(page, insAllbustraninfoViews);
		result.put(RESULT_TOTAL, page.getTotal());
		result.put(RESULT_ROWS, page.getResult());
		return result;
	}
	
	/**
	 * 保存
	 * @param insAllbustraninfo
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(InsAllbustraninfo insAllbustraninfo) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = insAllbustraninfoService.exists(insAllbustraninfo.getPid());
		try 
		{
			if(isExist)
			{
				
				insAllbustraninfoService.update(insAllbustraninfo);
			}
			else
			{
				insAllbustraninfoService.insert(insAllbustraninfo);
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
			filters.add(Filter.eq("allbustraninfoSeq", sublog.getBatchDecimal()));
			sublogService.deleteByPrimaryKey(pid);
			List<InsAllbustraninfoView> insAllbustraninfoViews = insAllbustraninfoViewService.select(filters);
			for (InsAllbustraninfoView insAllbustraninfoView : insAllbustraninfoViews) {
				InsAllbustraninfo insAllbustraninfo = insAllbustraninfoService.get(insAllbustraninfoView.getPortPid());
				insAllbustraninfo.setStatus("3");
				insAllbustraninfoService.updateSelective(insAllbustraninfo);
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
		
		boolean exist = insAllbustraninfoService.exists(pid);
		tips.put("valid", !exist);
		
		return tips;
	}

	/**关联交易明细表-确认报送
	 */
	@RequestMapping(value = "/relatedTransInfoSubmission/{pid}")
	public String traOtherSubmission(@PathVariable Integer pid,Model model) {

		Sublog sublog = sublogService.get(pid);
		model.addAttribute("sublog", sublog);
		model.addAttribute("pid", pid);

		return "sub/relatedTransInfoSubmission";
	}


	/**
	 * 关联交易明细表-报送日期-确定
	 */
	@RequestMapping(value = "/saveTransInfoSubmission")
	@ResponseBody
	@Log(module = "报送管理",description = "保存数据页面")
	public Map<String, Object> saveTransInfoSubmission(Sublog sublog) {
		Map<String, Object> tips = new HashMap<String, Object>();
		Boolean isExist = sublogService.exists(sublog.getPid());
		try {
			if (isExist) {
				//修改报送状态为-已报送
				sublog.setStatus("02");
				sublogService.update(sublog);
			}
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
		} catch (ServiceException e) {
			logger.info(e.getMessage());
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
		}
		return tips;
	}
	/*
	查询字典序
	 */
	private String selectByValue(String value, String simpleName) {
		List<NameValuePair> items = new ArrayList<>();
		try {
			List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
			List<DictItem> list = dictItemService.select(filters);
			//过滤掉不要的字典项
			if (!CollectionUtils.isEmpty(list)) {
				for (DictItem dictItem : list) {
					if (dictItem.getItemValue().equals(value)) {
						return dictItem.getItemLabel();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 为关联季报表页面准备数据
	 * @param list
	 */
	private void addData(List<InsAllbustraninfo> list) {
		try {
			for (InsAllbustraninfo allbustraninfo : list) {
				String byValue = null;
				String tradingDirection = null;
				switch (allbustraninfo.getTranType()) {
					case "1":
						byValue = selectByValue(allbustraninfo.getBusType(), "INS-FundsType");
						break;
					case "2":
						byValue = selectByValue(allbustraninfo.getBusType(), "INS-ServiceType");
						tradingDirection = selectByValue(allbustraninfo.getTradingDirection(), "INS-Tra-Direction");
						break;
					case "3":
						byValue = selectByValue(allbustraninfo.getBusType(), "INT-BusinessTypes");
						tradingDirection = selectByValue(allbustraninfo.getTradingDirection(), "INS-Tra-Directions");
						break;
					case "4":
						byValue = selectByValue(allbustraninfo.getBusType(), "INT-InsBusinessType");
						break;
					default:
						byValue = null;
				}
				allbustraninfo.setTradingDirection(tradingDirection);
				allbustraninfo.setBusType(byValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为关联交易季报表查看页面准备数据
	 * @param list
	 */
	private void addDataView(List<InsAllbustraninfoView> list)
	{
		try {
			for (InsAllbustraninfoView allbustraninfo : list) {
				String byValue = null;
				String tradingDirection = null;
				switch (allbustraninfo.getTranType()) {
					case "1":
						byValue = selectByValue(allbustraninfo.getBusType(), "INS-FundsType");
						break;
					case "2":
						byValue = selectByValue(allbustraninfo.getBusType(), "INS-ServiceType");
						tradingDirection = selectByValue(allbustraninfo.getTradingDirection(), "INS-Tra-Direction");
						break;
					case "3":
						byValue = selectByValue(allbustraninfo.getBusType(), "INT-BusinessTypes");
						tradingDirection = selectByValue(allbustraninfo.getTradingDirection(), "INS-Tra-Directions");
						break;
					case "4":
						byValue = selectByValue(allbustraninfo.getBusType(), "INT-InsBusinessType");
						break;
					default:
						byValue = null;
				}
				allbustraninfo.setTradingDirection(tradingDirection);
				allbustraninfo.setBusType(byValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}