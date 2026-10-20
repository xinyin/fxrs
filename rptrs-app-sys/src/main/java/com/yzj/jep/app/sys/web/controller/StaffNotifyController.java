/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年10月16日 09:38:25
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.code.YesNo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.code.NotifyConfigType;
import com.yzj.jep.sys.code.NotifyStatus;
import com.yzj.jep.sys.code.StaffNotifyStatus;
import com.yzj.jep.sys.dto.StaffNotifyDto;
import com.yzj.jep.sys.model.Notify;
import com.yzj.jep.sys.model.StaffNotify;
import com.yzj.jep.sys.service.NotifyService;
import com.yzj.jep.sys.service.StaffNotifyService;
import com.yzj.jep.sys.util.NotifyContentParser;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;

@Controller
@RequestMapping("/sys/staffNotify")
public class StaffNotifyController extends CrudController<StaffNotify> {
    Logger logger = LoggerFactory.getLogger(StaffNotifyController.class);

    @Autowired
    private StaffNotifyService staffNotifyService;

    @Autowired
    private NotifyService notifyService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model, String type, String folderType) {

        UserProfile staff = UserProfileUtils.getUserProfile();

        long inboxUnread = staffNotifyService.count(
                new Filter.Builder().eq("staffCode", staff.getStaffCode()).
                        eq("read", YesNo.NO.getValue()).eq("status", StaffNotifyStatus.INBOX.getValue()).build());
        model.addAttribute("inboxUnread", inboxUnread);

        long draftUnsend = notifyService.count(
                new Filter.Builder().eq("sender", staff.getStaffCode())
                        .eq("status", NotifyStatus.DRAFT.getValue()).eq("type", NotifyConfigType.MESSAGE.getValue()).build());
        model.addAttribute("draftUnsend", draftUnsend);

        model.addAttribute("type", type);
        model.addAttribute("folderType", folderType); //消息查看记录后返回标记。

        return "sys/staffNotifyList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<StaffNotify> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        staffNotifyService.query(page, filters, orders);
    }

    /**
     * 查看页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public String view(@PathVariable String id, String folderType, Model model) {

        UserProfile staff = UserProfileUtils.getUserProfile();
        StaffNotify staffNotify = staffNotifyService.get(id);
        if (staffNotify != null) {
            if (YesNo.NO.getValue().equals(staffNotify.getRead())) {
                staffNotify.setRead(YesNo.YES.getValue());
                staffNotifyService.update(staffNotify);
            }

            Notify notify = notifyService.get(staffNotify.getNotifyId());
            if (notify != null && NotifyConfigType.TODO.getValue().equals(notify.getType())) {
                String content = notify.getContent();
                Map<String, String> params = new HashMap<String, String>();
                params.put("staffCode", staff.getStaffCode());
                params.put("staffName", staff.getStaffName());
                content = NotifyContentParser.parseDollar(content, params);
                notify.setContent(content);
            }
            model.addAttribute("notify", notify);
        }
        model.addAttribute("folderType", folderType);
        model.addAttribute("staffNotifyId", id);
        return "sys/staffNotifyView";
    }

    /**
     * 查询某个用户的所有消息
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryNotifyByStaffCode")
    @ResponseBody
    public Map<String, Object> queryNotifyByStaffCode(Page<StaffNotifyDto> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("createTime", OrderType.DESC));

        List<Filter> filters = RequestUtils.buildFilters(request);
        UserProfile staff = UserProfileUtils.getUserProfile();
        filters.add(new Filter("staffCode", staff.getStaffCode()));

        staffNotifyService.queryNotifyByStaffCode(page, filters, orders);

        result.put("pageNumber", page.getPageNumber());
        result.put("pages", page.getPages());
        result.put("pageSize", page.getPageSize());
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 标记为已读
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/markAsRead")
    @ResponseBody
    public Map<String, Object> markAsRead(String ids) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            staffNotifyService.markAsRead(ids);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_MARK);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_MARK);
        }

        return tips;
    }

    /**
     * 全部标记为已读
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/markAllAsRead")
    @ResponseBody
    public Map<String, Object> markAllAsRead() {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            staffNotifyService.markAllAsRead();
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_MARK);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_MARK);
        }

        return tips;
    }

    /**
     * 标记为未读
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/markAsUnread")
    @ResponseBody
    public Map<String, Object> markAsUnread(String ids) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            staffNotifyService.markAsUnread(ids);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_MARK);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_MARK);
        }

        return tips;
    }

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteFlag")
    @ResponseBody
    public Map<String, Object> deleteFlag(String ids) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            staffNotifyService.deleteFlag(ids);
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
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteCompletely")
    @ResponseBody
    public Map<String, Object> remove(String ids) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            staffNotifyService.deleteCompletely(ids);
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
     * 恢复
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/recovery")
    @ResponseBody
    public Map<String, Object> recovery(String ids) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            staffNotifyService.recovery(ids);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_RECOVERY);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_RECOVERY);
        }

        return tips;
    }

    @RequestMapping(value = "/synch")
    @ResponseBody
    public Map<String, Object> synch() {
        Map<String, Object> result = new HashMap<String, Object>();

        UserProfile staff = UserProfileUtils.getUserProfile();

        long inboxUnread = staffNotifyService.count(
                new Filter.Builder().eq("staffCode", staff.getStaffCode()).
                        eq("read", YesNo.NO.getValue()).eq("status", StaffNotifyStatus.INBOX.getValue()).build());
        result.put("inboxUnread", inboxUnread);

        long draftUnsend = notifyService.count(
                new Filter.Builder().eq("sender", staff.getStaffCode())
                        .eq("status", NotifyStatus.DRAFT.getValue()).eq("type", NotifyConfigType.MESSAGE.getValue()).build());
        result.put("draftUnsend", draftUnsend);

        return result;
    }
}