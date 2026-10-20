/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年10月12日 16:01:44
 *
 */

package com.yzj.jep.app.sys.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.code.YesNo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.code.NotifyConfigResult;
import com.yzj.jep.sys.code.NotifyConfigType;
import com.yzj.jep.sys.code.NotifyStatus;
import com.yzj.jep.sys.model.Attachment;
import com.yzj.jep.sys.model.Notify;
import com.yzj.jep.sys.model.NotifyConfig;
import com.yzj.jep.sys.service.*;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/notify")
public class NotifyController extends CrudController<Notify> {
    Logger logger = LoggerFactory.getLogger(NotifyController.class);

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private StaffNotifyService staffNotifyService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private NotifyConfigService notifyConfigService;

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Notify> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        notifyService.query(page, filters, orders);
    }

    /**
     * 公告列表页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/announceList")
    public String announceList(String folderType, Model model) {

        UserProfile staff = UserProfileUtils.getUserProfile();
        long draftUnsend = notifyService.count(
                new Filter.Builder().eq("sender", staff.getStaffCode()).
                        eq("status", NotifyStatus.DRAFT.getValue()).eq("type", NotifyConfigType.ANNOUNCE.getValue()).build());
        model.addAttribute("draftUnsend", draftUnsend);
        model.addAttribute("folderType", folderType);

        return "sys/notifyAnnounceList";
    }

    /**
     * 公告编辑页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/announce")
    public String announce(String id, String folderType, Model model) {

        Notify notify = notifyService.get(id);
        model.addAttribute("notify", notify);
        model.addAttribute("folderType", folderType);

        return "sys/notifyAnnounce";
    }

    /**
     * 保存公告
     *
     * @param notify
     * @param receiver
     * @return
     */
    @RequestMapping(value = "/saveAnnounce")
    @ResponseBody
    public Map<String, Object> saveAnnounce(Notify notify) {
        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            /*设置发件人，及基础信息*/
            UserProfile staff = UserProfileUtils.getUserProfile();
            notify.setSender(staff.getStaffCode());
            notify.setSenderName(staff.getStaffName());
            notify.setSendTime(new Date());
            notify.setStatus(NotifyStatus.SEND.getValue());
            notify.setType(NotifyConfigType.ANNOUNCE.getValue());
            long i = attachmentService.count(new Filter.Builder().eq("entityId", notify.getId()).build());
            if (i > 0) {
                notify.setIsUpload(YesNo.YES.getValue());
            } else {
                notify.setIsUpload(YesNo.NO.getValue());
            }

            /*获取收件人*/

            String staffCodes = this.getAnnounceReceiver(notify.getReceiver());
            notify.setReceiver(staffCodes);
            notify.setReceiverName(staffService.getNameByCode(notify.getReceiver()));

            notifyService.createAnnounce(notify);

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SEND);
            tips.put("id", notify.getId());
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SEND);
        }
        return tips;
    }

    @RequestMapping(value = "/saveAnnounceDraft")
    @ResponseBody
    public Map<String, Object> saveAnnounceDraft(Notify notify) {

        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = notifyService.exists(notify.getId());
        try {
            UserProfile staff = UserProfileUtils.getUserProfile();
            notify.setSender(staff.getStaffCode());
            notify.setSenderName(staff.getStaffName());
            notify.setSendTime(new Date());
            notify.setStatus(NotifyStatus.DRAFT.getValue());
            notify.setType(NotifyConfigType.ANNOUNCE.getValue());
            notify.setIsUpload(YesNo.NO.getValue());
            notify.setReceiver(this.getAnnounceReceiver(notify.getReceiver()));
            notify.setReceiverName(staffService.getNameByCode(notify.getReceiver()));

            if (StringUtils.isEmpty(notify.getSubject())) {
                notify.setSubject("");
            }

            if (isExist) {
                long i = attachmentService.count(new Filter.Builder().eq("entityId", notify.getId()).build());
                if (i > 0) {
                    notify.setIsUpload(YesNo.YES.getValue());
                }
                notifyService.update(notify);
            } else {
                notifyService.insert(notify);
            }

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE_DRAFT);
            tips.put("id", notify.getId());
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE_DRAFT);
        }
        return tips;
    }

    /*获取发公告的收件人*/
    private String getAnnounceReceiver(String receiver) {

        String staffCodes = "";
        if (StringUtils.isNotEmpty(receiver)) {
            if ("all".equals(receiver)) {
                staffCodes = staffService.selectAllStaffCode();
            } else {
                JSONObject obj = JSONObject.parseObject(receiver);
                String units = (String) obj.get("unitList");
                String roles = (String) obj.get("roleList");
                String staffs = (String) obj.get("staffList");
                staffCodes = staffService.selectStaffByUnitsAndRolesAndStaffs(units, roles, staffs);
            }
        }
        return staffCodes;
    }

    /**
     * 私信编辑页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/msg")
    public String msg(String id, String folderType, Model model) {

        Notify notify = notifyService.get(id);
        if (notify != null && NotifyStatus.SEND.getValue().equals(notify.getStatus())) //转发
        {
            notify.setSubject("转发：" + notify.getSubject());
            String tmp = "<p><b>发件人：</b>" + notify.getSenderName() + "</p>";
            tmp += "<p><b>收件人：</b>" + this.getReceiver(id) + "</p>";
            tmp += "<p><b>时间：</b>" + notify.getSendTimeString() + "</p>";
            tmp += "<p><b>主题：</b>" + notify.getSubject() + "</p><br>";
            notify.setContent(tmp + notify.getContent());
            notify.setReceiver("");
            notify.setReceiverName("");
            model.addAttribute("opt", "forward");

            if (YesNo.YES.getValue().equals(notify.getIsUpload())) {
                List<Attachment> list = attachmentService.copy(id, "NOTIFY");
                if (list != null && list.size() > 0) {
                    model.addAttribute("newEntityId", list.get(0).getEntityId());
                    model.addAttribute("uploadSession", list.get(0).getUploadSession());
                }
            }
        } else {
            model.addAttribute("opt", "draft");
        }

        model.addAttribute("notify", notify);
        model.addAttribute("folderType", folderType);

        return "sys/notifyMessage";
    }

    /**
     * 根据通知主键获取所有的收件人
     *
     * @param notifyId
     * @return
     */
    private String getReceiver(String notifyId) {
        return staffNotifyService.getReceiver(notifyId);
    }

    /**
     * 保存私信
     *
     * @param notify
     * @return
     */
    @RequestMapping(value = "/saveMessage")
    @ResponseBody
    public Map<String, Object> saveMessage(Notify notify) {
        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            /*设置发件人，及基础信息*/
            UserProfile staff = UserProfileUtils.getUserProfile();
            notify.setSender(staff.getStaffCode());
            notify.setSenderName(staff.getStaffName());
            notify.setSendTime(new Date());
            notify.setStatus(NotifyStatus.SEND.getValue());
            notify.setType(NotifyConfigType.MESSAGE.getValue());
            long i = attachmentService.count(new Filter.Builder().eq("entityId", notify.getId()).build());
            if (i > 0) {
                notify.setIsUpload(YesNo.YES.getValue());
            } else {
                notify.setIsUpload(YesNo.NO.getValue());
            }

            /*设置收件人*/
            Map<String, String> remind = this.getStaffMsgNotifyConfig(notify.getReceiver(), notify.getSender());
            notify.setReceiverName(staffService.getNameByCode(notify.getReceiver()));

            notifyService.createMessage(notify, remind);

            /*设置消息提示*/
            String refuseRecMsg = remind.get("refuseRecMsgName");
            String blacklist = remind.get("blacklistName");
            if (StringUtils.isNotEmpty(remind.get("newReceiver")) &&
                    (StringUtils.isNotEmpty(refuseRecMsg) || StringUtils.isNotEmpty(blacklist))) {
                tips.put("msg", "私信发送部分成功，用户：" + refuseRecMsg + " " + blacklist + "拒绝接收私信！");
            } else if (StringUtils.isEmpty(remind.get("newReceiver")) &&
                    (StringUtils.isNotEmpty(blacklist) || StringUtils.isNotEmpty(blacklist))) {
                tips.put("msg", "私信发送成功，用户接收私信失败！用户：" + refuseRecMsg + " " + blacklist + "拒绝接收私信！");
            } else {
                tips.put(WebCode.MSG, WebCode.SUCCESS_SEND);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put("id", notify.getId());
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SEND);
        }
        return tips;
    }

    /**
     * 保存私信草稿
     *
     * @param notify
     * @return
     */
    @RequestMapping(value = "/saveMessageDraft")
    @ResponseBody
    public Map<String, Object> saveMessageDraft(Notify notify) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = notifyService.exists(notify.getId());
        try {
            /*设置发件人*/
            UserProfile staff = UserProfileUtils.getUserProfile();
            notify.setSender(staff.getStaffCode());
            notify.setSenderName(staff.getStaffName());
            notify.setSendTime(new Date());
            notify.setStatus(NotifyStatus.DRAFT.getValue());
            notify.setType(NotifyConfigType.MESSAGE.getValue());
            notify.setIsUpload(YesNo.NO.getValue());
            notify.setReceiverName(staffService.getNameByCode(notify.getReceiver()));

            if (StringUtils.isEmpty(notify.getSubject())) {
                notify.setSubject("");
            }

            if (isExist) //草稿状态下发送
            {
                long i = attachmentService.count(new Filter.Builder().eq("entityId", notify.getId()).build());
                if (i > 0) {
                    notify.setIsUpload(YesNo.YES.getValue());
                }
                notifyService.update(notify);
            } else {
                notifyService.insert(notify);
            }

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE_DRAFT);
            tips.put("id", notify.getId());
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE_DRAFT);
        }
        return tips;
    }

    /**
     * 获取发送者的通知配置值:<br>
     * 1.发送者是否在收件人的黑名单中<br>
     * 2.收件人是否设置拒绝接收私信
     *
     * @param receiver
     * @param sender
     * @return
     */
    private Map<String, String> getStaffMsgNotifyConfig(String receiver, String sender) {
        Map<String, String> result = new HashMap<String, String>();

        String newReceiver = "";
        String refuseRecMsg = "";
        String blacklist = "";

        String[] receiverArr = receiver.split(",");
        for (String staffCode : receiverArr) {
            /*获取收件人的配置信息*/
            NotifyConfig tmp = new NotifyConfig();
            tmp.setStaffCode(staffCode);
            NotifyConfig staffNotifyConfig = notifyConfigService.get(tmp);

            if (staffNotifyConfig != null) {
                /*收件人的配置信息中为接收私信，或没有黑名单、或没有将发件人设置为黑名单内*/
                if (!NotifyConfigResult.REFUSE.getValue().equals(staffNotifyConfig.getIsReceiveMsg())
                        && (staffNotifyConfig.getBlacklist() == null
                        || staffNotifyConfig.getBlacklist().indexOf(sender) == -1)) {
                    newReceiver += staffCode + ",";
                } else if (NotifyConfigResult.REFUSE.getValue().equals(staffNotifyConfig.getIsReceiveMsg())) //拒绝接收私信
                {
                    refuseRecMsg += staffCode + ",";
                } else if (staffNotifyConfig.getBlacklist() != null
                        && staffNotifyConfig.getBlacklist().indexOf(sender) != -1) //在黑名单列表中
                {
                    blacklist += staffCode + ",";
                }
            } else { //用户未配置，默认接收
                newReceiver += staffCode + ",";
            }
        }

        /*获取发件人是配置信息*/
        NotifyConfig senderConfig = new NotifyConfig();
        senderConfig.setStaffCode(sender);
        NotifyConfig staffNotifyConfig = notifyConfigService.get(senderConfig);
        if (staffNotifyConfig != null) {
            result.put("remindCfg", staffNotifyConfig.getIsReceiveRemind());
            result.put("messageCfg", staffNotifyConfig.getIsReceiveMsg());
        } else {
            result.put("remindCfg", NotifyConfigResult.RECEIVE.getValue());
            result.put("messageCfg", NotifyConfigResult.RECEIVE.getValue());
        }

        result.put("newReceiver", newReceiver);
        result.put("refuseRecMsgName", staffService.getNameByCode(refuseRecMsg));
        result.put("blacklistName", staffService.getNameByCode(blacklist));

        return result;
    }

    /**
     * 查看页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public String view(@PathVariable String id, String folderType, String path, Model model) {

        Notify notify = notifyService.get(id);
        model.addAttribute("notify", notify);
        model.addAttribute("folderType", folderType);
        model.addAttribute("path", path);

        return "sys/notifyView";
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteFlag")
    @ResponseBody
    public Map<String, Object> deleteFlag(String ids) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            notifyService.deleteFlag(ids);
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
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Map<String, Object> remove(String ids) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            notifyService.remove(ids);
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
     * 查询用户的发件箱、草稿箱数据
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryNotify")
    @ResponseBody
    public Map<String, Object> queryNotify(Page<Notify> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("sendTime", OrderType.DESC));

        List<Filter> filters = RequestUtils.buildFilters(request);
        UserProfile staff = UserProfileUtils.getUserProfile();
        filters.add(new Filter("sender", staff.getStaffCode()));

        notifyService.queryNotify(page, filters, orders);

        result.put("pageNumber", page.getPageNumber());
        result.put("pages", page.getPages());
        result.put("pageSize", page.getPageSize());
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 计算不同类型的通知未读数量
     *
     * @return
     */
    @RequestMapping(value = "/countNotifyByType")
    @ResponseBody
    public Map<String, Object> countNotifyByType() {
        Map<String, Object> result = new HashMap<String, Object>();
        long remind = 0L;
        long message = 0L;

        UserProfile staff = UserProfileUtils.getUserProfile();
        if (staff != null) {
            NotifyConfig senderConfig = new NotifyConfig();
            senderConfig.setStaffCode(staff.getStaffCode());
            NotifyConfig staffNotifyConfig = notifyConfigService.get(senderConfig);
            if (staffNotifyConfig != null) {
                /*用户配置为接收消息提示*/
                if (NotifyConfigResult.RECEIVE.getValue().equals(staffNotifyConfig.getIsReceiveRemind())) {
                    remind = notifyService.countNotifyByType(staff.getStaffCode(), NotifyConfigType.REMIND.getValue());
                }
                if (NotifyConfigResult.RECEIVE.getValue().equals(staffNotifyConfig.getIsReceiveMsg())) {
                    message = notifyService.countNotifyByType(staff.getStaffCode(), NotifyConfigType.MESSAGE.getValue());
                }
            } else {
                remind = notifyService.countNotifyByType(staff.getStaffCode(), NotifyConfigType.REMIND.getValue());
                message = notifyService.countNotifyByType(staff.getStaffCode(), NotifyConfigType.MESSAGE.getValue());
            }

            result.put("message", message);
            result.put("remind", remind);

            long announce = notifyService.countNotifyByType(staff.getStaffCode(), NotifyConfigType.ANNOUNCE.getValue());
            result.put("announce", announce);

            long todo = notifyService.countNotifyByType(staff.getStaffCode(), NotifyConfigType.TODO.getValue());
            result.put("todo", todo);
            result.put("login", YesNo.YES.getValue());
        } else {
            result.put("login", YesNo.NO.getValue());
        }
        return result;
    }

    /**
     * 预览
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/preview")
    public String preview(String receiver, Model model) {

        //List<Attachment> list = attachmentService.select(null, uploadSession, "NOTIFY");

        if (StringUtils.isNotEmpty(receiver)) {
            String staffCode = this.getAnnounceReceiver(receiver);
            model.addAttribute("receiver", staffService.getNameByCode(staffCode));
        }
        UserProfile staff = UserProfileUtils.getUserProfile();
        model.addAttribute("sender", staff.getStaffName());

        return "sys/notifyPreview";
    }

    /**
     * 保存私信草稿
     *
     * @param notify
     * @return
     */
    @RequestMapping(value = "/updateStatusOfUpload")
    @ResponseBody
    public Map<String, Object> updateStatusOfUpload(Notify notify) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            notifyService.updateSelective(notify);

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_UPDATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_UPDATE);
        }
        return tips;
    }

}