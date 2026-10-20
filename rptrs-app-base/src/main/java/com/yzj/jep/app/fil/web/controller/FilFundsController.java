/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:55:55
 *
 */

package com.yzj.jep.app.fil.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.fil.model.FilFunds;
import com.yzj.jep.app.fil.service.FilFundsService;
import com.yzj.jep.app.util.JsonUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
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


@Controller
@RequestMapping("/ins/filFunds")
public class FilFundsController extends CrudController<FilFunds> {
    private static Logger logger = LoggerFactory.getLogger(FilFundsController.class);

    @Autowired
    private FilFundsService filFundsService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "fil/ins/filFundsList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<FilFunds> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(new Order("pid", OrderType.DESC));
        //列表按pid排序 因为备案管理系统没有修改数据路径

        filFundsService.query(page, filters, orders);
    }


    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model) {

        FilFunds filFunds = filFundsService.get(pid);
        model.addAttribute("traFunds", filFunds);
        model.addAttribute("wt", "1");

        return "fil/ins/filFundsView";
    }


    /**
     * 判断是否存在备案号
     *
     * @param taskid
     * @return
     */
    @RequestMapping(value = "/viewA/{taskid}")
    @ResponseBody
    public Map<String, Object> viewexits(@PathVariable String taskid) {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<Filter> filters = new Filter.Builder().eq("filNum", taskid).build();
        boolean exists = filFundsService.exists(filters);
        if (exists) {
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);

        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "该条数据审批未通过！");
        }
        return tips;
    }

    /**
     * 我的工作台查看页面
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewB/{taskid}")
    public String viewbeian(@PathVariable String taskid, String wt, Model model) {
        List<Filter> filters = new Filter.Builder().eq("filNum", taskid).build();
        FilFunds filFunds = filFundsService.get(filters);
        model.addAttribute("filFunds", filFunds);
        model.addAttribute("wt", wt);

        return "fil/ins/filFundsView";
    }


    /**
     * 删除
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/remove/{pid}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            filFundsService.deleteByPrimaryKey(pid);
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
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = filFundsService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    @RequestMapping(value = "/compared/{pid}")
    public String compared(@PathVariable String pid, Model model) {
//		String[] pids = pid.split(",");
//		FilFunds relPsn1 = filFundsService.get(Integer.valueOf(pids[0]));
//		FilFunds relPsn2 = filFundsService.get(Integer.valueOf(pids[1]));
        //根据pid查出选出所选数据
        FilFunds filFunds = filFundsService.get(Integer.valueOf(pid));
        //根据查出对象flagPid属性查出同一自然人/机构得备案记录
        List<FilFunds> filFundsList = filFundsService.selectfilFundsByfalgPid(filFunds.getFlagPid());
        //取出所选数据得上一条数据
        int index = filFundsList.indexOf(filFunds);
        if (index >= 1) {
            FilFunds filFunds1 = filFundsList.get(index - 1);
            //实体类转json
            JSONObject psn1 = (JSONObject) JSON.toJSON(filFunds);
            JSONObject psn2 = (JSONObject) JSON.toJSON(filFunds1);
            System.out.println("PSN1" + psn1 + "PSN2" + psn2);
            Map result = JsonUtil.compareJson(psn1, psn2, null);
            model.addAttribute("filFunds", psn1);
            model.addAttribute("filFunds2", result);
            model.addAttribute("opt", "edit");
            model.addAttribute("op", "1");
            model.addAttribute("wt", "1");
            return "fil/ins/filFundsView";
        } else {
            JSONObject psn1 = (JSONObject) JSON.toJSON(filFunds);
            Map result = JsonUtil.compareJson(psn1, psn1, null);
            model.addAttribute("filFunds", psn1);
            model.addAttribute("filFunds2", result);
            model.addAttribute("opt", "edit");
            model.addAttribute("op", "1");
            model.addAttribute("wt", "1");
            return "fil/ins/filFundsView";
        }
    }

    /**
     * 多条查询
     *
     * @param pids
     * @return
     */
    @RequestMapping("/queryPidsByConditions/{pids}")
    @ResponseBody
    public Map<String, Object> queryPidsByConditions(Page<FilFunds> page, @PathVariable List<Integer> pids) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        filFundsService.query(page, filters);
        result.put(WebCode.SUCCESS, true);
        result.put("total", page.getTotal());
        result.put("rows", page.getResult());
        return result;
    }
}