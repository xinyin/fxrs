
/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年07月05日 15:57:31
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.service.UserProfileService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
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
import com.yzj.jep.sys.model.User;
import com.yzj.jep.sys.service.UserService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/user")
public class UserController extends CrudController<User> {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserService userService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/userList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<User> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        userService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/userEdit";
    }

    /**
     * 编辑页面
     *
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{userId}")
    public String edit(@PathVariable String userId, Model model) {

        User user = userService.get(userId);
        model.addAttribute("user", user);
        model.addAttribute("opt", "edit");

        return "sys/userEdit";
    }

    /**
     * 查看页面
     *
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{userId}")
    public String view(@PathVariable String userId, Model model) {

        User user = userService.get(userId);
        model.addAttribute("user", user);

        return "sys/userView";
    }

    /**
     * 保存
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(User user) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = userService.exists(user.getUserId());
        try {
            if (isExist) {
                userService.update(user);
            } else {
                userService.insert(user);
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
     * @param userId
     * @return
     */
    @RequestMapping(value = "/remove/{userId}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String userId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            userService.deleteByPrimaryKey(userId);
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
     * 验证提交的userId是否已经存在数据库中
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String userId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        User exist = userService.get(userId);
        if (exist == null) {
            tips.put("valid", true);
        } else {
            tips.put("valid", false);
        }

        return tips;
    }

    //============================修改密码配置============================

    /**
     * 修改密码页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/changePassword")
    public String changePassword(Model model, HttpServletRequest request) {
        return "sys/sysChangePassword";
    }

    /**
     * 修改密码配置
     *
     * @param configs
     * @return
     */
    @RequestMapping(value = "/changePWD")
    @ResponseBody
    public Map<String, Object> changePWD(String password, HttpServletRequest request) {

        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        tips.put("success", userProfileService.changePWD(userProfile, password));
        return tips;
    }
}