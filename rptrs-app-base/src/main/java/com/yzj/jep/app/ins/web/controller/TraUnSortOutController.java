/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年07月19日 10:12:36
 *
 */

package com.yzj.jep.app.ins.web.controller;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.ins.model.UnSortOut;
import com.yzj.jep.app.ins.model.UnSortOutData;
import com.yzj.jep.app.ins.service.UnSortOutService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;


@Controller
@RequestMapping("/ins/traUnSortOut")
public class TraUnSortOutController extends CrudController<UnSortOut> {
	private static Logger	logger	= LoggerFactory.getLogger(TraUnSortOutController.class);


	@Autowired
	private UnSortOutService unSortOutService;
	/**
	 * 获取列表页面
	 * @return 列表页面
	 */
	@RequestMapping(value = "/list")
	public String list(Model model) {
		//用戶角色查詢
		String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
		String url = "ins/traUnSortOut/list";
		AuthorityUtil authorityUtil = new AuthorityUtil();
		String code = authorityUtil.buttonPerm(staffCode, url);
		model.addAttribute("code", code);
		//查询登陆人机构及下辖机构的信息
		UserProfile profile = UserProfileUtils.getUserProfile();
		String unitCode = profile.getUnitCode();
		if ("0001".equals(unitCode) || "0001-ZZHK".equals(unitCode)){
			//资产
			model.addAttribute("unitCode",unitCode);
			model.addAttribute("opt","NoMH");
		}else {
			//管户机构
			model.addAttribute("unitCode",unitCode);
			model.addAttribute("opt","MH");
		}

		return "ins/unSortOutList";
	}

	/**
	 * 为列表页面做数据准备
	 */
	@Override
	protected void prepareQuery(Page<UnSortOut> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

	unSortOutService.query(page, filters, orders);
		List<UnSortOut> list = page.getResult();
		list.stream().forEach( e -> {
			e.setRelNumber(idEncrypt(e.getRelNumber()));
		});
	}

	/**
	 * 跳转确认类型页面
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/add/{pids}")
	public String add(Model model,@PathVariable String pids){
		String[] StrPidArr = pids.split(",");
		model.addAttribute("pids",pids);
		model.addAttribute("total",StrPidArr.length);
		return "ins/unSortOutEdit";
	}





	/**
	 * 保存
	 * @param unSortOut
	 * @return
	 */
	@RequestMapping(value = "/save/{contractNo}")
	@ResponseBody
	public Map<String, Object> save(UnSortOutData unSortOut,@PathVariable String contractNo) {
		Map<String, Object> tips = new HashMap<>(16);
		try {
			List<Integer> parmList = new ArrayList<>();
			String[] StrPidArr = unSortOut.getPid().split(",");
			for (String s : StrPidArr) {
				parmList.add(Integer.valueOf(s));
			}
			List<Filter> filters = new ArrayList<>();
			filters.add(new Filter("pid", Operator.IN, parmList));
			List<UnSortOut> list= unSortOutService.select(filters);
			for (UnSortOut unSortOutList:list){
				if(ObjectUtil.isEmpty(unSortOutList.getRelId())){
					int relId = unSortOutService.getRelId(unSortOutList.getRelType(), unSortOutList.getRelNumber());
					if(relId <= 0){
						tips.put(WebCode.SUCCESS, false);
						tips.put(WebCode.MSG, "该交易的关联方不存在！");
						return tips;
					}
					unSortOutList.setRelId(relId);
				}
				unSortOutList.setTranType(unSortOut.getTradeType());
				unSortOutList.setContractNo(contractNo);
				unSortOutService.addTrad(unSortOutList,contractNo);
				unSortOutService.deleteByPrimaryKey(unSortOutList.getPid());
			}
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
		}catch (Exception e){
			logger.info("归类数据保存失败：{}", e);
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
		}
		return tips;
	}




	/**
	 * 删除
	 */
	@RequestMapping("/remove/{pids}")
	@ResponseBody
	public Map<String, Object> removeDoc(@PathVariable String pids) {
		Map<String, Object> tips = new HashMap<>();
		try {
			if (StringUtils.isEmpty(pids)) {
				tips.put(WebCode.FAILED, false);
				tips.put(WebCode.MSG, "未选择删除的数据");
				return tips;
			}
			String[] StrPidArr = pids.split(",");
			List<Integer> parmList = new ArrayList<>();
			for (String s : StrPidArr) {
				parmList.add(Integer.valueOf(s));
			}
			List<Filter> filters = new ArrayList<>();
			filters.add(new Filter("pid", Operator.IN, parmList));
			unSortOutService.deleteByFilter(filters);
			tips.put(WebCode.SUCCESS, true);
			tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
		} catch (Exception e) {
			tips.put(WebCode.SUCCESS, false);
			tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
		}
		return tips;
	}


}
