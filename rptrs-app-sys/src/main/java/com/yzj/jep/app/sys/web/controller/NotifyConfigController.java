/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年10月17日 14:13:56
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.code.NotifyConfigResult;
import com.yzj.jep.sys.model.NotifyConfig;
import com.yzj.jep.sys.service.NotifyConfigService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/notifyConfig")
public class NotifyConfigController extends CrudController<NotifyConfig> {
    Logger logger = LoggerFactory.getLogger(NotifyConfigController.class);

    @Autowired
    private NotifyConfigService notifyConfigService;

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<NotifyConfig> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        notifyConfigService.query(page, filters, orders);
    }

    /**
     * 系统默认配置页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/configDefault")
    public String configDefault(Model model) {

        NotifyConfig notifyConfig = notifyConfigService.get(WebCode.NOTIFY_CONFIG_ID);
        model.addAttribute("notifyConfig", notifyConfig);

        return "sys/notifyConfigDefault";
    }

    /**
     * 消息配置页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/config")
    public String config(Model model) {

        UserProfile staff = UserProfileUtils.getUserProfile();
        NotifyConfig notifyConfig = getNotifyConfigId(staff);
        model.addAttribute("notifyConfig", notifyConfig);
        model.addAttribute("staff", staff);

        return "sys/notifyConfig";
    }

    /**
     * 获取当前登录用户的通知配置
     *
     * @return
     */
    private NotifyConfig getNotifyConfigId(UserProfile staff) {

        NotifyConfig tmp = new NotifyConfig();
        tmp.setStaffCode(staff.getStaffCode());

        NotifyConfig config = notifyConfigService.get(tmp);
        if (config != null) {
            /*只更新过黑名单，未配置通知。获取用户的黑名单配置*/
            if (config.getIsReceiveSms() == null && config.getBlacklist() != null) {
                NotifyConfig result = notifyConfigService.get(WebCode.NOTIFY_CONFIG_ID);
                result.setBlacklist(config.getBlacklist());
                result.setBlacklistText(config.getBlacklistText());
                return result;
            } else {
                return config;
            }
        }
        return notifyConfigService.get(WebCode.NOTIFY_CONFIG_ID);
    }

    /**
     * 保存默认配置
     *
     * @param notifyConfig
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(NotifyConfig notifyConfig) {

        Map<String, Object> tips = new HashMap<String, Object>();

        notifyConfig.setId(WebCode.NOTIFY_CONFIG_ID);

        Boolean isExist = notifyConfigService.exists(notifyConfig.getId());
        try {
            if (isExist) {
                notifyConfigService.updateSelective(notifyConfig);
            } else {
                notifyConfig.setIsReceiveEmail(NotifyConfigResult.REFUSE.getValue());
                notifyConfig.setIsReceiveSms(NotifyConfigResult.REFUSE.getValue());
                notifyConfig.setIsReceiveMsg(NotifyConfigResult.RECEIVE.getValue());
                notifyConfig.setIsReceiveRemind(NotifyConfigResult.RECEIVE.getValue());

                notifyConfigService.insert(notifyConfig);
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
     * 保存自定义配置
     *
     * @param notifyConfig
     * @return
     */
    @RequestMapping(value = "/saveConfig")
    @ResponseBody
    public Map<String, Object> saveConfig(NotifyConfig notifyConfig) {

        Map<String, Object> tips = new HashMap<String, Object>();

        UserProfile staff = UserProfileUtils.getUserProfile();
        notifyConfig.setStaffCode(staff.getStaffCode());
        notifyConfig.setStaffName(staff.getStaffName());

        if (WebCode.NOTIFY_CONFIG_ID.equals(notifyConfig.getId())) {
            notifyConfig.setId(null);
        }

        Boolean isExist = notifyConfigService.exists(notifyConfig.getId());
        try {
            if (isExist) {
                notifyConfigService.update(notifyConfig);
            } else {
                notifyConfigService.insert(notifyConfig);
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

    @RequestMapping(value = "/getBlacklistByStaffCode")
    @ResponseBody
    public Map<String, Object> getBlacklistByStaffCode(String blackList) {
        Map<String, Object> result = new HashMap<String, Object>();

        NotifyConfig notifyConfig = new NotifyConfig();
        UserProfile staff = UserProfileUtils.getUserProfile();
        notifyConfig.setStaffCode(staff.getStaffCode());
        notifyConfig.setStaffName(staff.getStaffName());

        NotifyConfig exist = notifyConfigService.get(notifyConfig);
        if (exist != null) {
            String blacklistOld = exist.getBlacklist();
            if (StringUtils.isNotEmpty(blacklistOld) && blacklistOld.indexOf(blackList) != -1) {
                result.put("isExist", true);
            } else {
                result.put("isExist", false);
            }
        }
        return result;
    }

    /**
     * 加入黑名单
     *
     * @param blacklist
     * @param blacklistName
     * @return
     */
    @RequestMapping(value = "/addBlacklist")
    @ResponseBody
    public Map<String, Object> addBlacklist(String blacklist, String blacklistName) {

        Map<String, Object> tips = new HashMap<String, Object>();

        NotifyConfig notifyConfig = new NotifyConfig();
        UserProfile staff = UserProfileUtils.getUserProfile();
        notifyConfig.setStaffCode(staff.getStaffCode());
        notifyConfig.setStaffName(staff.getStaffName());

        NotifyConfig exist = notifyConfigService.get(notifyConfig);
        try {
            if (exist != null) {
                String blacklistOld = exist.getBlacklist();
                String blacklistNameOld = exist.getBlacklistText();
                exist.setBlacklist(blacklist + "," + blacklistOld);
                exist.setBlacklistText(blacklistName + "," + blacklistNameOld);
                notifyConfigService.update(exist);
            } else {
                notifyConfig.setBlacklist(blacklist);
                notifyConfig.setBlacklistText(blacklistName);
                notifyConfig.setStaffPhone(staff.getMobile());
                notifyConfig.setStaffEmail(staff.getEmail());
                notifyConfigService.insert(notifyConfig);
            }

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_ADD_BLT);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_ADD_BLT);
        }
        return tips;
    }

}