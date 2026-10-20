/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年08月08日 08:54:29
 *
 */

package com.yzj.jep.app.sys.web.controller;

import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yzj.jep.app.sys.model.ApproveMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.yzj.jep.app.sys.model.ApproveMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yzj.jep.app.sys.service.ApproveMapService ;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/approveMap")
public class ApproveMapController extends CrudController<ApproveMap> {
    Logger logger = LoggerFactory.getLogger(ApproveMapController.class);

    @Autowired
    private ApproveMapService approveMapService;

    @Autowired
    private UnitService unitService;
    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/approveMapList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<ApproveMap> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        approveMapService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/approveMapEdit";
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

        ApproveMap approveMap = approveMapService.get(pid);
        model.addAttribute("approveMap", approveMap);
        model.addAttribute("opt", "edit");

        return "sys/approveMapEdit";
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

       ApproveMap approveMap = approveMapService.get(pid);//
        model.addAttribute("approveMap", approveMap);
        model.addAttribute("opt", "view");

        return "sys/approveMapEdit";
    }

    /**
     * 保存
     *
     * @param approveMap
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(ApproveMap approveMap) {

        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(approveMap);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }

        Boolean isExist = approveMapService.exists(approveMap.getPid());
        try {

            if (isExist) {
                approveMap.setDelflag("1");
                approveMap.setStaffCode_(approveMap.getStaffCode_().substring(0, approveMap.getStaffCode_().indexOf(",")));
                approveMap.setRoleCode_(approveMap.getRoleCode_().substring(0, approveMap.getRoleCode_().indexOf(",")));
                approveMapService.update(approveMap);
            } else {
             approveMap.setStaffCode_(approveMap.getStaffCode_().substring(0, approveMap.getStaffCode_().indexOf(",")));
             approveMap.setRoleCode_(approveMap.getRoleCode_().substring(0, approveMap.getRoleCode_().indexOf(",")));
             approveMap.setDelflag("1");
             approveMapService.insert(approveMap);
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

            approveMapService.deleteByPrimaryKey(pid);
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

        ApproveMap exist = approveMapService.get(pid);
        if (exist == null) {
            tips.put("valid", true);
        } else {
            tips.put("valid", false);
        }

        return tips;
    }
}
