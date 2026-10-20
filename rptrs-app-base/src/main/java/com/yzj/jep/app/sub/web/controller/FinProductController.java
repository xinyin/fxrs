/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 10:12:05
 *
 */

package com.yzj.jep.app.sub.web.controller;

import com.yzj.jep.app.sub.model.FinProduct;
import com.yzj.jep.app.sub.model.PersonView;
import com.yzj.jep.app.sub.model.ProductView;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.FinProductService;
import com.yzj.jep.app.sub.service.ProductViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
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

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sub/finProduct")
public class FinProductController extends CrudController<FinProduct>{
	private static Logger	logger	= LoggerFactory.getLogger(FinProductController.class);

	@Autowired
	private FinProductService	finProductService;

	@Autowired
	private SublogService sublogService;

	@Autowired
	private ProductViewService productViewService;

	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		
		return "sub/finProductList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<FinProduct> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		
		finProductService.query(page, filters, orders);
	}

	/**
	 * 为新增报送文件页面准备数据
	 */
	@RequestMapping(value = "/queryAddProduct")
	@ResponseBody
	public Map<String,Object> queryAdd(Page<FinProduct> page, Model model, HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		List<Filter> filters = RequestUtils.buildFilters(request);
		filters.add(Filter.eq("status","3"));
		PageHelper.start(page);
		List<FinProduct> finProducts = finProductService.select(filters);
		PageHelper.end(page,finProducts);
		result.put(RESULT_TOTAL, page.getTotal());
		result.put(RESULT_ROWS, page.getResult());
		return result;
	}

//	/**
//	 * 查看页面
//	 * @param pid
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/proView/{pid}")
//	public String proView(@PathVariable Integer pid, Model model) {
//
//		Sublog sublog = sublogService.get(pid);
//		//格式化规则
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		//格式化成yyyy-MM-dd格式的时间字符串
//		String strDate= sdf.format(sublog.getMaxPercentInNetAssets() );
//		model.addAttribute("date",strDate);
//		model.addAttribute("mgerOrg","中再资产管理股份有限公司");
//		model.addAttribute("sublog", sublog);
//		model.addAttribute("pid", pid);
//
//		return "sub/SubTraView";
//	}

	/**
	 * 查看金融产品详情
	 */
	@RequestMapping(value = "ProView/{pid}")
	@ResponseBody
	public Map<String,Object> ProView(@PathVariable Integer pid,Page<PersonView> page){
		Map<String,Object> result = new HashMap<String,Object>();
		//拿到所选sublo数据
		Sublog sublog = sublogService.get(pid);
		//根据所选sublog数据得流水号到productview查找此报送文件的所有得自然人档案数据
		List<Filter> filter = new Filter.Builder().eq("subSeq", sublog.getBatchDecimal()).build();
		List<ProductView> productViews = productViewService.select(filter);
		PageHelper.end(page,productViews);
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
		
		return "sub/finProductEdit";
	}
	
	/**
	 * 编辑页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{pid}")
	public String edit(@PathVariable Integer pid, Model model){
		
		FinProduct finProduct = finProductService.get(pid);
		model.addAttribute("finProduct", finProduct);
		model.addAttribute("opt", "edit");
		
		return "sub/finProductEdit";
	}
	
	/**
	 * 查看页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{pid}")
	public String view(@PathVariable Integer pid, Model model) {
		
		FinProduct finProduct = finProductService.get(pid);
		model.addAttribute("finProduct", finProduct);
		
		return "sub/finProductView";
	}
	
	/**
	 * 保存
	 * @param finProduct
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(FinProduct finProduct) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = finProductService.exists(finProduct.getPid());
		try 
		{
			if(isExist)
			{
				
				finProductService.update(finProduct);
			}
			else
			{
				finProductService.insert(finProduct);
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
			finProductService.deleteByPrimaryKey(pid);
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
		
		boolean exist = finProductService.exists(pid);
		tips.put("valid", !exist);
		
		return tips;
	}
}