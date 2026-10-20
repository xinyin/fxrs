/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月10日 11:18:54
 *
 */

package com.yzj.jep.app.bus.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.util.ObtainFundsLimit;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("/bus/rootOrgInfo")
public class RootOrgInfoController extends CrudController<RootOrgInfo> {
    private static Logger logger = LoggerFactory.getLogger(RootOrgInfoController.class);

    @Autowired
    private RootOrgInfoService rootOrgInfoService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        Integer pid = 1;
        RootOrgInfo rootOrgInfo = rootOrgInfoService.get(pid);
        model.addAttribute("rootOrgInfo", rootOrgInfo);
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "bus/rootOrgInfo/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);

//        return "bus/rootOrgInfoEdit";
        return "rootorginfo/rootOrgInfoList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RootOrgInfo> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        rootOrgInfoService.query(page, filters, orders);
    }

    @Override
    @Log(module = "关联关系管理", description = "关联关系维护")
    public Map<String, Object> query(Page<RootOrgInfo> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<Filter> filters = RequestUtils.buildFilters(request);
            List<Order> orders = RequestUtils.buildOrders(request);
            filters.add(new Filter("delflag", "1"));
            orders.add(new Order("years", OrderType.DESC));
            orders.add(new Order("quarter", OrderType.DESC));
            PageHelper.start(page);
            List<RootOrgInfo> rootOrgInfoList = rootOrgInfoService.select(filters, orders);
            for (RootOrgInfo orgInfo : rootOrgInfoList) {
                DecimalFormat df2 = new DecimalFormat("#.00");
                orgInfo.setTotalCapitalStr(df2.format(orgInfo.getTotalCapital()));
                orgInfo.setNetCaptialStr(df2.format(orgInfo.getNetCaptial()));
            }
            PageInfo pageInfo = new PageInfo(rootOrgInfoList);
            result.put(RESULT_TOTAL, pageInfo.getTotal());
            result.put(RESULT_ROWS, pageInfo.getList());
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

//        return "bus/rootOrgInfoEdit";
        return "rootorginfo/rootOrgInfoEdit";
    }

    /**
     * 编辑页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(Model model, @PathVariable Integer pid) {

        RootOrgInfo rootOrgInfo = rootOrgInfoService.get(pid);
        model.addAttribute("rootOrgInfo", rootOrgInfo);
        model.addAttribute("opt", "view");

        return "rootorginfo/rootOrgInfoEdit";
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

        RootOrgInfo rootOrgInfo = rootOrgInfoService.get(pid);
        if(5 == rootOrgInfo.getQuarter()){
            model.addAttribute("rootOrgInfo", rootOrgInfo);
            model.addAttribute("view", "view");
            return "rootorginfo/rootOrgInfoYearsView";
        }
        Map<String,BigDecimal> result = ObtainFundsLimit.getLimitRule(rootOrgInfo.getYears(), rootOrgInfo.getQuarter());
        BigDecimal limit01 = result.get("limit01");
        BigDecimal limit02 = result.get("limit02");
        BigDecimal limit03 = result.get("limit03");
        BigDecimal limit04 = result.get("limit04");
        BigDecimal limit05 = result.get("limit05");
        BigDecimal limit06 = result.get("limit06");
        BigDecimal limit07 = result.get("limit07");

        model.addAttribute("rootOrgInfo", rootOrgInfo);
        model.addAttribute("view", "view");
        model.addAttribute("limit01", limit01);
        model.addAttribute("limit02", limit02);
        model.addAttribute("limit03", limit03);
        model.addAttribute("limit04", limit04);
        model.addAttribute("limit05", limit05);
        model.addAttribute("limit06", limit06);
        model.addAttribute("limit07", limit07);
        return "rootorginfo/rootOrgInfoView";
    }

    /**
     * 保存
     *
     * @param rootOrgInfo
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RootOrgInfo rootOrgInfo) {

        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(rootOrgInfo);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        /**判空*/
        int year = DateUtil.year(new Date());
        /**如果年份不为今年，权益类资产投资上限比例（%）不能为空*/
        if (Integer.valueOf(rootOrgInfo.getYears()) != year) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "必须字段不能为空！");
        }
        DecimalFormat df2 = new DecimalFormat("#.000000");
        rootOrgInfo.setTotalCapital(new BigDecimal(df2.format(rootOrgInfo.getTotalCapital())));
        rootOrgInfo.setNetCaptial(new BigDecimal(df2.format(rootOrgInfo.getNetCaptial())));
        rootOrgInfo.setRelOrgId(1);
        Boolean isExist = rootOrgInfoService.exists(rootOrgInfo.getPid());
        try {
            if (isExist) {
                rootOrgInfo.setApprovalStatus("6");
                rootOrgInfoService.updateSelective(rootOrgInfo);
            } else {
                /**根据时间和季度查询，如果存在，就替换之前的数据*/
                List<Filter> filters = new ArrayList<>();
                filters.add(new Filter("delflag", Operator.EQ, "1"));
                filters.add(new Filter("years", Operator.EQ, rootOrgInfo.getYears()));
                filters.add(new Filter("quarter", Operator.EQ, rootOrgInfo.getQuarter()));
                List<RootOrgInfo> rootOrgInfoList = rootOrgInfoService.select(filters);
                if (CollectionUtil.isNotEmpty(rootOrgInfoList)) {
                    if (rootOrgInfo.getQuarter() != 5) {
                        tips.put(WebCode.SUCCESS, false);
                        tips.put(WebCode.MSG, rootOrgInfo.getYears() + "年度" + rootOrgInfo.getQuarter() + "季度数据已存在！");
                        return tips;
                    } else {
                        tips.put(WebCode.SUCCESS, false);
                        tips.put(WebCode.MSG, rootOrgInfo.getYears() + "年度" + "本年度数据已存在！");
                        return tips;
                    }
                } else {
                    rootOrgInfo.setApprovalStatus("6");
                    rootOrgInfoService.insertSelective(rootOrgInfo);
                }
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
     * 提交审批
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        RootOrgInfo rootOrgInfo = rootOrgInfoService.get(pid);
        if ("3".equals(rootOrgInfo.getApprovalStatus())) {
            //4 代表审批中
            rootOrgInfo.setApprovalStatus("4");
            rootOrgInfoService.updateSelective(rootOrgInfo);
            tips.put("data", rootOrgInfo);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
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
            rootOrgInfoService.deleteByPrimaryKey(pid);
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

        boolean exist = rootOrgInfoService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    @RequestMapping(value = "/count/{holeShareAmount}")
    @ResponseBody
    public Map<String, Object> count(@PathVariable String holeShareAmount, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        RootOrgInfo rootOrgInfo = rootOrgInfoService.get(1);
        String fen = "";
        if (StringUtils.isEmpty(holeShareAmount)) {
            model.addAttribute("holeShareAmount", 0);
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "持股数填写不正确");
        }

        Long total = Long.parseLong(rootOrgInfo.getTotalShareAmount());
        Long hold = Long.parseLong(holeShareAmount);//转换为Int类型
        double percent = (double) hold / total;
        DecimalFormat format = new DecimalFormat("0.0000%");
        String holeShare = format.format(percent);
//		String holeShare= result+"%";
        tips.put(WebCode.SUCCESS, true);
        tips.put("shareholdingProportion", holeShare);
        return tips;
    }
}
