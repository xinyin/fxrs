/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年08月08日 08:54:29
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
import com.yzj.jep.sys.model.AttachmentType;
import com.yzj.jep.sys.service.AttachmentTypeService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/attachmentType")
public class AttachmentTypeController extends CrudController<AttachmentType> {
    Logger logger = LoggerFactory.getLogger(AttachmentTypeController.class);

    @Autowired
    private AttachmentTypeService attachmentTypeService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/attachmentTypeList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<AttachmentType> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        attachmentTypeService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/attachmentTypeEdit";
    }

    /**
     * 编辑页面
     *
     * @param attachTypeCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{attachTypeCode}")
    public String edit(@PathVariable String attachTypeCode, Model model) {

        AttachmentType attachmentType = attachmentTypeService.get(attachTypeCode);
        model.addAttribute("attachmentType", attachmentType);
        model.addAttribute("opt", "edit");

        return "sys/attachmentTypeEdit";
    }

    /**
     * 查看页面
     *
     * @param attachTypeCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{attachTypeCode}")
    public String view(@PathVariable String attachTypeCode, Model model) {

        AttachmentType attachmentType = attachmentTypeService.get(attachTypeCode);
        model.addAttribute("attachmentType", attachmentType);

        return "sys/attachmentTypeView";
    }

    /**
     * 保存
     *
     * @param attachmentType
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(AttachmentType attachmentType) {

        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(attachmentType);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        Boolean isExist = attachmentTypeService.exists(attachmentType.getAttachTypeCode());
        try {
            if (isExist) {
                attachmentTypeService.update(attachmentType);
            } else {
                attachmentTypeService.insert(attachmentType);
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
     * @param attachTypeCode
     * @return
     */
    @RequestMapping(value = "/remove/{attachTypeCode}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String attachTypeCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            attachmentTypeService.deleteByPrimaryKey(attachTypeCode);
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
     * 验证提交的attachTypeCode是否已经存在数据库中
     *
     * @param attachTypeCode
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String attachTypeCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        AttachmentType exist = attachmentTypeService.get(attachTypeCode);
        if (exist == null) {
            tips.put("valid", true);
        } else {
            tips.put("valid", false);
        }

        return tips;
    }
}
