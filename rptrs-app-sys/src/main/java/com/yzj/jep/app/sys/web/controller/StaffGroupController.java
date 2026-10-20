/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年06月29日 09:58:43
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sys.util.XssCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.StaffGroup;
import com.yzj.jep.sys.service.StaffGroupService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

@Controller
@RequestMapping("/sys/staffGroup")
public class StaffGroupController extends CrudController<StaffGroup> {
    private static Logger logger = LoggerFactory.getLogger(StaffGroupController.class);

    @Autowired
    private StaffGroupService staffGroupService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/staffGroupList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<StaffGroup> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        staffGroupService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/staffGroupEdit";
    }

    /**
     * 编辑页面
     *
     * @param staffGroupCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{staffGroupCode}")
    public String edit(@PathVariable String staffGroupCode, Model model) {

        StaffGroup staffGroup = staffGroupService.get(staffGroupCode);
        model.addAttribute("staffGroup", staffGroup);
        model.addAttribute("opt", "edit");

        return "sys/staffGroupEdit";
    }

    /**
     * 查看页面
     *
     * @param staffGroupCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{staffGroupCode}")
    public String view(@PathVariable String staffGroupCode, Model model) {

        StaffGroup staffGroup = staffGroupService.get(staffGroupCode);
        model.addAttribute("staffGroup", staffGroup);

        return "sys/staffGroupView";
    }

    /**
     * 保存
     *
     * @param staffGroup
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(StaffGroup staffGroup) {

        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(staffGroup);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        Boolean isExist = staffGroupService.exists(staffGroup.getStaffGroupCode());
        try {
            if (isExist) {
                staffGroupService.update(staffGroup);
            } else {
                staffGroupService.insert(staffGroup);
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
     * @param staffGroupCode
     * @return
     */
    @RequestMapping(value = "/remove/{staffGroupCode}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String staffGroupCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            staffGroupService.deleteStaffGroup(staffGroupCode);
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
     * 验证提交的staffGroupCode是否已经存在数据库中
     *
     * @param staffGroupCode
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String staffGroupCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        StaffGroup exist = staffGroupService.get(staffGroupCode);
        if (exist == null) {
            tips.put("valid", true);
        } else {
            tips.put("valid", false);
        }

        return tips;
    }

    /**
     * 以特殊格式获取所有用户组类型
     *
     * @return json格式返回
     */
    @RequestMapping(value = "/queryAll")
    @ResponseBody
    public List<NameValuePair> queryAll() {

        List<StaffGroup> result = staffGroupService.selectAll();

        return NVUtils.convert(result, result.size(), "staffGroupCode", "staffGroupName");
    }
}
