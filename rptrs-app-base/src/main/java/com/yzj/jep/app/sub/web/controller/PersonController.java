/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 09:48:47
 *
 */

package com.yzj.jep.app.sub.web.controller;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.sub.mapper.PersonMapper;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.PersonService;
import com.yzj.jep.app.sub.service.PersonViewService;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sub/person")
public class PersonController extends CrudController<Person>{
	private static Logger	logger	= LoggerFactory.getLogger(PersonController.class);

	@Autowired
	private PersonService	personService;

    @Autowired
    private SublogService sublogService;

    @Autowired
	private PersonViewService personViewService;
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

		return "sub/personList";
	}

	/**
	 * 获取报送列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/sublist")
	public String sublist(Model model) {
		UserProfile profile = UserProfileUtils.getUserProfile();
		String tenantId = profile.getTenantId();
		String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
		String url = "sub/person/sublist";
		AuthorityUtil authorityUtil = new AuthorityUtil();
		String code = authorityUtil.buttonPerm(staffCode, url);
		model.addAttribute("code", code);
		if (tenantId == null || tenantId ==""){
			model.addAttribute("subOrg", "");
		}else {
			model.addAttribute("subOrg", tenantId);
		}
		return "sub/personSubList";
	}

	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<Person> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {

		personService.query(page, filters, orders);
	}



	/**
	 * 为新增报送文件页面准备数据
	 */
	@RequestMapping(value = "/queryAddPerson")
	@ResponseBody
	public Map<String,Object> queryAddPerson( Page<Person> page, Model model, HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		//设置查询条件 报送文件状态为待报送
		List<Filter> filterss = new Filter.Builder().eq("status","3").build();
		PageHelper.start(page);
		List<Person> persons = personService.select(filterss);
		PageHelper.end(page,persons);
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

		return "sub/personEdit";
	}

	/**
	 * 新增报送文件
	 */
	@RequestMapping(value = "/addSubFile")
	@Log(module = "自然人档案报送",description = "新增报送文件列表")
	public String addSubFile(Model model){
		UserProfile profile = UserProfileUtils.getUserProfile();
		String tenantId = subjectDate.get(profile.getTenantId());
		if (tenantId == null || tenantId ==""){
			model.addAttribute("subOrg", "");
		}else {
			model.addAttribute("subOrg", tenantId);
		}
		return "sub/addSubPsn";
	}

	/**
	 * 编辑页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{pid}")
	public String edit(@PathVariable Integer pid, Model model){

		Person person = personService.get(pid);
		model.addAttribute("person", person);
		model.addAttribute("opt", "edit");

		return "sub/personEdit";
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
		return "sub/personView";
	}

	/**
	 * 查看自然人关联方档案详情
	 */
	@RequestMapping(value = "ProductView/{pid}")
	@ResponseBody
	public Map<String,Object> ProductView(@PathVariable Integer pid,Page<PersonView> page){
		Map<String,Object> result = new HashMap<String,Object>();
		//拿到所选sublo数据
		Sublog sublog = sublogService.get(pid);
		//根据所选sublog数据得流水号到productview查找此报送文件的所有得自然人档案数据
		List<Filter> filter = new Filter.Builder().eq("subSeq", sublog.getBatchDecimal()).build();
		PageHelper.start(page);
		List<PersonView> personViews = personViewService.select(filter);
		PageHelper.end(page,personViews);
		result.put(RESULT_TOTAL, page.getTotal());
		result.put(RESULT_ROWS, page.getResult());
		return result;
	}


	
	/**
	 * 保存
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(Person person) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = personService.exists(person.getPid());
		try 
		{
			if(isExist)
			{
				
				personService.update(person);
			}
			else
			{
				personService.insert(person);
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
			filters.add(Filter.eq("subSeq", sublog.getBatchDecimal()));
			sublogService.deleteByPrimaryKey(pid);
			List<PersonView> personViews = personViewService.select(filters);
			for (PersonView personView : personViews) {
				Person person = personService.get(personView.getProtPid());
				person.setStatus("3");
				personService.updateSelective(person);
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
		
		boolean exist = personService.exists(pid);
		tips.put("valid", !exist);
		
		return tips;
	}


	/**
	 * 自然人档案报送-确认报送
	 */
	@RequestMapping(value = "/submission/{pid}")
	public String submission(@PathVariable Integer pid,Model model) {

		Sublog sublog = sublogService.get(pid);
		model.addAttribute("sublog", sublog);
		model.addAttribute("pid", pid);

		return "sub/personSubmission";
	}


	/**
	 * 报送日期-确定
	 */
	@RequestMapping(value = "/savePersonSubmission")
	@ResponseBody
	@Log(module = "报送管理",description = "保存数据页面")
	public Map<String, Object> savePersonSubmission(Sublog sublog) {
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
}