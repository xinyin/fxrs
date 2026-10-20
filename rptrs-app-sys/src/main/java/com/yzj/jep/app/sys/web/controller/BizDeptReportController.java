/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年08月07日 08:53:02
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.BizDeptReport;
import com.yzj.jep.sys.model.BizDeptType;
import com.yzj.jep.sys.service.BizDeptReportService;
import com.yzj.jep.sys.service.BizDeptTypeService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

@Controller
@RequestMapping("/sys/bizDeptReport")
public class BizDeptReportController extends CrudController<BizDeptReport> {
    Logger logger = LoggerFactory.getLogger(BizDeptReportController.class);

    @Autowired
    private BizDeptReportService bizDeptReportService;

    @Autowired
    private BizDeptTypeService bizDeptTypeService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/bizDeptReportList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<BizDeptReport> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        bizDeptReportService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/bizDeptReportEdit";
    }

    /**
     * 编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable String id, Model model) {

        BizDeptReport bizDeptReport = bizDeptReportService.get(id);
        model.addAttribute("bizDeptReport", bizDeptReport);
        model.addAttribute("opt", "edit");

        return "sys/bizDeptReportEdit";
    }

    /**
     * 查看页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public String view(@PathVariable String id, Model model) {

        BizDeptReport bizDeptReport = bizDeptReportService.get(id);
        model.addAttribute("bizDeptReport", bizDeptReport);

        return "sys/bizDeptReportView";
    }

    /**
     * 保存
     *
     * @param bizDeptReport
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(BizDeptReport bizDeptReport) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = bizDeptReportService.exists(bizDeptReport.getId());
        try {
            if (isExist) {
                bizDeptReportService.update(bizDeptReport);
            } else {
                bizDeptReportService.insert(bizDeptReport);
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

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/remove/{id}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String id) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            bizDeptReportService.deleteByPrimaryKey(id);
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
     * 验证提交的id是否已经存在数据库中
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String id) {

        Map<String, Object> tips = new HashMap<String, Object>();

        BizDeptReport exist = bizDeptReportService.get(id);
        if (exist == null) {
            tips.put("valid", true);
        } else {
            tips.put("valid", false);
        }

        return tips;
    }


    /**
     * 验证提交的上报部门和上报业务是否已经存在数据库中（相同的部门和相同的业务不能重复）
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/checkRepeat", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> checkRepeat(String deptCode, String bizCode) {

        Map<String, Object> tips = new HashMap<String, Object>();
        /*查询当前菜单的兄弟菜单中是否存在公共菜单*/
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("deptCode", Operator.EQ, deptCode));
        filters.add(new Filter("bizCode", Operator.EQ, bizCode));
        List<BizDeptReport> exist = bizDeptReportService.select(filters);
        if (CollectionUtils.isEmpty(exist)) {
            tips.put("valid", true);
        } else {
            tips.put("valid", false);
        }

        return tips;
    }

    /**
     * 根据传入的部门编码deptCode，查询其关联的业务类型，并增加参数ALL到业务类型数据中
     *
     * @param deptCode 部门编码
     * @return 相关业务类型与ALL
     */
    @RequestMapping(value = "/queryBizType")
    @ResponseBody
    public List<NameValuePair> queryBizType(String deptCode) {
        List<Filter> filters = new Filter.Builder().eq("deptCode", deptCode).build();
        List<BizDeptType> result = bizDeptTypeService.select(filters);
        List<NameValuePair> nameValuePairs = NVUtils.convert(result, result.size(), "bizTypeCode", "bizTypeName");
        nameValuePairs.add(0, new BasicNameValuePair("全部", "ALL"));
        return nameValuePairs;
    }
}