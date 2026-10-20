/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月17日 10:14:29
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.LegalPersonService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.app.bus.model.LegalInviolableRights;
import com.yzj.jep.app.bus.service.LegalInviolableRightsService;

@Controller
@RequestMapping("/bus/legalInviolableRights")
public class LegalInviolableRightsController extends CrudController<LegalInviolableRights> {
    private static Logger logger = LoggerFactory.getLogger(LegalInviolableRightsController.class);

    @Autowired
    private LegalInviolableRightsService legalInviolableRightsService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/legalInviolableRightsList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<LegalInviolableRights> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        legalInviolableRightsService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/legalInviolableRightsEdit";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, Model model) {

        LegalInviolableRights legalInviolableRights = legalInviolableRightsService.get(pid);
        model.addAttribute("legalInviolableRights", legalInviolableRights);
        model.addAttribute("opt", "edit");

        return "bus/legalInviolableRightsEdit";
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

        LegalInviolableRights legalInviolableRights = legalInviolableRightsService.get(pid);
        model.addAttribute("legalInviolableRights", legalInviolableRights);

        return "bus/legalInviolableRightsView";
    }

    /**
     * 保存
     *
     * @param legalInviolableRights
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(LegalInviolableRights legalInviolableRights) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = legalInviolableRightsService.exists(legalInviolableRights.getPid());
        try {
            if (isExist) {

                legalInviolableRightsService.update(legalInviolableRights);
            } else {
                legalInviolableRightsService.insert(legalInviolableRights);
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
     * @param pid
     * @return
     */
    @RequestMapping(value = "/remove/{pid}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            legalInviolableRightsService.deleteByPrimaryKey(pid);
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

        boolean exist = legalInviolableRightsService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }
}