/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2018 yzj.com. All rights reserved.
 *
 * Generated at 2018年02月24日 11:11:38
 *
 */

package com.yzj.jep.app.rule.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.rules.drools5.model.ScoreModel;
import com.yzj.jep.rules.drools5.service.ScoreModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.rules.drools5.model.ModelRuleScore;
import com.yzj.jep.rules.drools5.model.Rules;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.rules.drools5.service.ModelRuleScoreService;
import com.yzj.jep.rules.drools5.service.RulesService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/rul/modelRuleScore")
public class ModelRuleScoreController extends CrudController<ModelRuleScore>{
	private static Logger	logger	= LoggerFactory.getLogger(ModelRuleScoreController.class);

	@Autowired
	private ModelRuleScoreService	modelRuleScoreService;

	@Autowired
	private RulesService			rulesService;

	@Autowired
	private ScoreModelService scoreModelService;
	
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, String pkgId, String modelId) {
		
		model.addAttribute("pkgId", pkgId);
		model.addAttribute("modelId", modelId);
		return "rul/modelRuleScoreList";
	}
	
	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<ModelRuleScore> page, List<Filter> filters, List<Order> orders,HttpServletRequest request) {
		orders.add(new Order("minValue", OrderType.ASC));
		modelRuleScoreService.query(page, filters, orders);
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(Model model, String ruleId, String modelId){
		
		model.addAttribute("ruleId", ruleId);
		model.addAttribute("modelId", modelId);
		return "rul/modelRuleScoreEdit";
	}
	
	/**
	 * 编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model){
		
		ModelRuleScore modelRuleScore = modelRuleScoreService.get(id);
		model.addAttribute("modelRuleScore", modelRuleScore);
		model.addAttribute("opt", "edit");
		
		return "rul/modelRuleScoreEdit";
	}
	
	/**
	 * 查看页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view/{id}")
	public String view(@PathVariable String id, Model model) {
		
		ModelRuleScore modelRuleScore = modelRuleScoreService.get(id);
		model.addAttribute("modelRuleScore", modelRuleScore);
		
		return "rul/modelRuleScoreView";
	}
	
	/**
	 * 保存
	 * @param modelRuleScore
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(ModelRuleScore modelRuleScore) {
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		Boolean isExist = modelRuleScoreService.exists(modelRuleScore.getId());
		try 
		{
			if(isExist)
			{
				
				modelRuleScoreService.update(modelRuleScore);
			}
			else
			{
				Rules rules = rulesService.get(modelRuleScore.getRuleId());
				ScoreModel sm = scoreModelService.get(modelRuleScore.getModelId());
				modelRuleScore.setPkgId(sm.getRulPkgId());
				modelRuleScore.setRuleName(rules.getName());
				modelRuleScore.setRuleCode(rules.getCode());
				modelRuleScoreService.insert(modelRuleScore);
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
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/remove/{id}")
	@ResponseBody
	public Map<String, Object> remove(@PathVariable String id){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		try 
		{
			modelRuleScoreService.deleteByPrimaryKey(id);
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
	 * 验证提交的id是否已经存在数据库中
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> check(String id){
		
		Map<String, Object> tips = new HashMap<String, Object>();
		
		boolean exist = modelRuleScoreService.exists(id);
		tips.put("valid", !exist);
		
		return tips;
	}
}