/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年09月19日 11:15:38
 *
 */

package com.yzj.jep.app.workflow.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.service.UserProfileService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.workflow.service.PublicMethodService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.security.token.Token;
import com.yzj.jep.core.security.token.TokenService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.User;
import com.yzj.jep.sys.service.AttachmentService;
import com.yzj.jep.sys.service.UserService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ProcInstStatus;
import com.yzj.jep.workflow.api.code.RenderType;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IHisProcInst;
import com.yzj.jep.workflow.api.model.IProcDef;
import com.yzj.jep.workflow.api.model.IProcInst;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.api.notify.event.NotifyEventBuilder;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import com.yzj.jep.workflow.impl.model.TaskInstImpl;
import com.yzj.jep.workflow.wf.model.HisProcInst;
import com.yzj.jep.workflow.wf.model.ProcInst;
import com.yzj.jep.workflow.wf.model.ProcInstData;
import com.yzj.jep.workflow.wf.service.ProcInstDataService;
import org.activiti.engine.task.Task;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程实例表
 *
 * @author yinke.hou
 * @time 2017年9月25日 下午5:29:14
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/workflow/process")
public class ProcessController extends CrudController<ProcInst> {
    Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @Autowired
    private WorkflowEngine workflowEngine;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private ProcInstDataService procInstDataService;

    @Autowired
    private PublicMethodService publicMethodService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/existProcDef")
    @ResponseBody
    public Map<String, Object> existProcDef(Model model, String token, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> data = new HashMap<String, Object>();
        Token t = tokenService.decode(token);
        String procDefCode = t.getId();

        UserProfile profile = UserProfileUtils.getUserProfile();

        /**
         * 如果为空就说明是没有登录。
         */
        if (profile == null) {
            String userName = t.getUsername();
            String password = t.getPassword();
            User user = userService.get(userName);
            if (user != null && password.equals(user.getPassword())) {
                /*获得当前业务流程*/
                IProcDef procdef = workflowEngine.getProcessService().getLastProcDef(procDefCode);
                if (procdef == null) {
                    data.put(WebCode.SUCCESS, false);
                    data.put(WebCode.MSG, "业务流程编码不存在，请检查是否拼写正确。");
                } else {
                    data.put(WebCode.SUCCESS, true);
                }
            } else {
                data.put(WebCode.SUCCESS, false);
                data.put(WebCode.MSG, "用户名和密码不匹配，请检查。");
            }
        } else {
            /*获得当前业务流程*/
            IProcDef procdef = workflowEngine.getProcessService().getLastProcDef(procDefCode);
            if (procdef == null) {
                data.put(WebCode.SUCCESS, false);
                data.put(WebCode.MSG, "业务流程编码不存在，请检查是否拼写正确。");
            } else {
                data.put(WebCode.SUCCESS, true);
            }
        }
        return data;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryData"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryData(Page<ProcInst> page, Model model, HttpServletRequest request, String passToken) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (StringUtils.isNotEmpty(passToken) && profile == null) {
            model.addAttribute("passToken", userService.processToken(passToken).get(UserService.PASS_TOKEN));
            Map<String, Object> result = new HashMap<String, Object>();
            List<Filter> filters = RequestUtils.buildFilters(request);
            List<Order> orders = RequestUtils.buildOrders(request);
            prepareQuery(page, filters, orders, request);
            result.put(RESULT_TOTAL, page.getTotal());
            result.put(RESULT_ROWS, page.getResult());
            return result;
        } else {
            Map<String, Object> result = new HashMap<String, Object>();
            List<Filter> filters = RequestUtils.buildFilters(request);
            List<Order> orders = RequestUtils.buildOrders(request);
            prepareQuery(page, filters, orders, request);
            result.put(RESULT_TOTAL, page.getTotal());
            result.put(RESULT_ROWS, page.getResult());
            return result;
        }

    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/edit")
    public String edit(Model model, String procDefCode, String oldData, String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String data = "";
        if (StringUtils.isNotEmpty(oldData) && (!"null".equals(oldData))) {
            data = new String(Base64.decodeBase64(oldData));
        }

        UserProfile profile = UserProfileUtils.getUserProfile();
        if (StringUtils.isNotEmpty(token) && profile == null) {
            model.addAttribute("passToken", userService.processToken(token).get(UserService.PASS_TOKEN));
        }
        /*获得当前业务流程*/
        Map<String, Object> map = workflowEngine.getFormService().getFormContent(procDefCode, data);
        if ((boolean) map.get("isBusiness")) {
            model.addAttribute("oldData", oldData);
            model.addAttribute("procDefCode", procDefCode);
            model.addAttribute("clientAdd", map.get("clientAdd"));
            model.addAttribute("token", map.get("token"));
            return "wf/procInstBusinessEdit";
        } else {
            //获取表单内容
            String content = (String) map.get("content");

            model.addAttribute("oldData", oldData);
            model.addAttribute("procDefCode", procDefCode);
            model.addAttribute("content", content);
            return "wf/procInstEdit";
        }
    }

    /**
     * 保存流程数据 不启动
     *
     * @param procDefCode
     * @param oldData
     * @param newData
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(String passToken, String procDefCode, String oldData, String newData, String uploadSessions, String procInstId, String status) {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            newData = new String(Base64.decodeBase64(newData));
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                User user = (User) userService.processToken(passToken).get(UserService.CURRENT_USER);
                profile = userProfileService.getUserProfileByUser(user, null);
            }

            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);

            if (StringUtils.isNotBlank(procInstId)) {
                if (ProcInstStatus.REJECT.getValue().equals(status)) {
                    workflowEngine.getRuntimeService().updateFormData(procInstId, newData, staff);
                } else {
                    workflowEngine.getFormService().updateFormData(procInstId, newData);
                }
            } else {
                //判断当前修改前的数据是否存在
                if (StringUtils.isNotEmpty(oldData)) {
                    oldData = new String(Base64.decodeBase64(oldData));
                }

                IProcInst procInst = workflowEngine.getRuntimeService().newProcess(procDefCode, staff, oldData, newData);

                /*附件关联*/
                if (StringUtils.isNotBlank(uploadSessions)) {
                    associateAttachment(procInst, uploadSessions);
                }
            }


            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return data;
    }

    /**
     * 启动一支业务流程，
     *
     * @param procInstId 流程实例ID
     * @return
     */
    @RequestMapping(value = "/submit")
    @ResponseBody
    public Map<String, Object> submit(String procInstId, String passToken) {

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                User user = (User) userService.processToken(passToken).get(UserService.CURRENT_USER);
                profile = userProfileService.getUserProfileByUser(user, null);
            }

            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);

            workflowEngine.getRuntimeService().startProcess(procInstId, staff);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return data;
    }

    /**
     * 保存并启动一条业务流程，
     *
     * @param procInstId 流程实例ID
     * @return
     */
    @RequestMapping(value = "/saveAndStart")
    @ResponseBody
    public Map<String, Object> saveAndStart(String passToken, String procDefCode, String oldData, String newData, String uploadSessions, String procInstId) {

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            try {
                newData = new String(Base64.decodeBase64(newData), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (StringUtils.isNotBlank(procInstId)) {
                UserProfile profile = UserProfileUtils.getUserProfile();
                if (profile == null) {
                    User user = (User) userService.processToken(passToken).get(UserService.CURRENT_USER);
                    profile = userProfileService.getUserProfileByUser(user, null);
                }

                StaffImpl staff = new StaffImpl();
                BeanUtils.copyProperties(profile, staff);
                workflowEngine.getRuntimeService().startProcess(procInstId, newData, staff);

                try {
                    //设置提醒邮件
                    IProcInst procInst = workflowEngine.getRuntimeService().getProcInst(procInstId);
                    List<? extends ITaskInst> iTaskInsts = workflowEngine.getRuntimeService().selectTaskInst(procInstId);
                    ITaskInst iTaskInst = iTaskInsts.get(0);
                    TaskInstImpl taskInst = createTaskInstance(procInst, iTaskInst);
                    workflowEngine.getNotifyDispatcher().dispatchNotify(NotifyEventBuilder.createTaskEndEvent(staff, taskInst));
                } catch (WorkflowException e) {
                    logger.info(e.getMessage());
                }

            } else {
                //判断当前修改前的数据是否存在
                if (StringUtils.isNotEmpty(oldData)) {
                    oldData = new String(Base64.decodeBase64(oldData));
                }

                UserProfile profile = UserProfileUtils.getUserProfile();
                if (profile == null) {
                    User user = (User) userService.processToken(passToken).get(UserService.CURRENT_USER);
                    profile = userProfileService.getUserProfileByUser(user, null);
                }

                StaffImpl staff = new StaffImpl();
                BeanUtils.copyProperties(profile, staff);

                IProcInst procInst = workflowEngine.getRuntimeService().startProcess(procDefCode, staff, oldData, newData);

                try {
                    //设置提醒邮件
                    String procId = procInst.getId();
                    //发起流程给页面返回procId
                    data.put("procId", procId);
                    List<? extends ITaskInst> iTaskInsts = workflowEngine.getRuntimeService().selectTaskInst(procId);
                    ITaskInst iTaskInst = iTaskInsts.get(0);
                    TaskInstImpl taskInst = createTaskInstance(procInst, iTaskInst);
                    workflowEngine.getNotifyDispatcher().dispatchNotify(NotifyEventBuilder.createTaskEndEvent(staff, taskInst));
                } catch (WorkflowException e) {
                    logger.info(e.getMessage());
                }

                /*附件关联*/
                if (StringUtils.isNotBlank(uploadSessions)) {
                    associateAttachment(procInst, uploadSessions);
                }

            }
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return data;
    }

    /**
     * 保存流程数据 不启动
     *
     * @param procInstId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> delete(String procInstId) {

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            workflowEngine.getRuntimeService().deleteProcess(procInstId);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return data;
    }

    /**
     * 关联附件
     *
     * @param procInst
     * @param uploadSessions
     */
    private void associateAttachment(IProcInst procInst, String uploadSessions) {
        String sessions = new String(Base64.decodeBase64(uploadSessions));
        String[] sessionArray = sessions.split(",");
        for (String str : sessionArray) {
            String[] array = str.split(":");
            String propCode = array[0];
            String session = array[1];
            String entityId = procInst.getId() + "-" + propCode;
            attachmentService.associate(entityId, session);
        }
    }


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/draftList")
    public String draftList(Model model, String passToken) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            Map<String, Object> map = userService.processToken(passToken);
            User user = (User) map.get(UserService.CURRENT_USER);
            profile = userProfileService.getUserProfileByUser(user, null);
            model.addAttribute("passToken", map.get(UserService.PASS_TOKEN));
        }
        model.addAttribute("userCode", profile.getStaffCode());

        return "wf/procInstDraftList";
    }


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/runingList")
    public String runList(Model model, String passToken) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            Map<String, Object> map = userService.processToken(passToken);
            User user = (User) map.get(UserService.CURRENT_USER);
            profile = userProfileService.getUserProfileByUser(user, null);
            model.addAttribute("passToken", map.get(UserService.PASS_TOKEN));
        }
        model.addAttribute("userCode", profile.getStaffCode());
        return "wf/procInstRunList";
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/completeList")
    public String completeList(Model model) {
        UserProfile user = UserProfileUtils.getUserProfile();
        model.addAttribute("userCode", user.getStaffCode());
        return "wf/procInstCompleteList";
    }


    /**
     * 获取列表页面 编辑业务数据
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/editDraftProcInst")
    public String editDraftProcInst(Model model, String procInstId, String status, String passToken) {

        /*获得当前业务流程*/
        Map<String, Object> map = workflowEngine.getFormService().getFormContent(procInstId, null, RenderType.EDIT);
        if (StringUtils.isNotEmpty(passToken)) {
            model.addAttribute("passToken", userService.processToken(passToken).get(UserService.PASS_TOKEN));
        }
        if ((boolean) map.get("isBusiness")) {
            model.addAttribute("procInstId", procInstId);
            model.addAttribute("status", status);
            model.addAttribute("clientAdd", map.get("clientAdd"));
            model.addAttribute("token", map.get("token"));
            return "wf/procInstBusinessEdit";
        } else {
            //获取表单内容
            String content = (String) map.get("content");

            model.addAttribute("content", content);
            model.addAttribute("procInstId", procInstId);
            model.addAttribute("status", status);
            return "wf/procInstEdit";
        }
    }


    /**
     * 获取列表页面  查看未审批完成的业务数据
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/view")
    public String view(Model model, String procInstId, String option, String passToken) {

        if (StringUtils.isNotBlank(passToken)) {
            /*获得当前业务流程*/
            option = option + "?passToken=" + userService.processToken(passToken).get(UserService.PASS_TOKEN);
        }
        Map<String, Object> map = workflowEngine.getFormService().getFormContent(procInstId, null, RenderType.VIEW);
        if ((boolean) map.get("isBusiness")) {
            model.addAttribute("procInstId", procInstId);
            model.addAttribute("option", option);
            model.addAttribute("clientAdd", map.get("clientAdd"));
            model.addAttribute("token", map.get("token"));
            return "wf/procInstBusinessView";
        } else {
            //获取表单内容
            String content = (String) map.get("content");
            model.addAttribute("content", content);
            model.addAttribute("option", option);
            model.addAttribute("procInstId", procInstId);
            return "wf/procInstView";
        }

    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<ProcInst> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        workflowEngine.getRuntimeService().queryProcInst(page, filters, orders);
        //将FormData放入procInst对象中
        workflowEngine.getRuntimeService().unionFormDataInProc(page);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryHistory"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryHistory(Page<HisProcInst> page, Model model, HttpServletRequest request, String passToken) {
        List<HisProcInst> hisProcInstli = new ArrayList<>();
        if (StringUtils.isNotEmpty(passToken)) {
            model.addAttribute("passToken", userService.processToken(passToken).get(UserService.PASS_TOKEN));
            Map<String, Object> result = new HashMap<String, Object>();
            List<Filter> filters = RequestUtils.buildFilters(request);
            List<Order> orders = RequestUtils.buildOrders(request);
            orders.add(new Order("startTime", OrderType.DESC));
            workflowEngine.getHistoryService().queryProcInst(page, filters, orders);
            workflowEngine.getHistoryService().unionFormDataHisInProc(page);
            result.put(RESULT_TOTAL, page.getTotal());
            result.put(RESULT_ROWS, page.getResult());
            return result;
        } else {
            Map<String, Object> result = new HashMap<String, Object>();
            List<Filter> filters = RequestUtils.buildFilters(request);
            List<Order> orders = RequestUtils.buildOrders(request);
            orders.add(new Order("startTime", OrderType.DESC));
            workflowEngine.getHistoryService().queryProcInst(page, filters, orders);
            workflowEngine.getHistoryService().unionFormDataHisInProc(page);
            /**
             * 遍历集合将需要的业务数据存到新的集合中，在返现到页面
             * processType 流程类型
             * businessType 业务类型（自然人，投资入股等）
             * glfName 关联方名称
             * transactionAmount 关联交易金额（疑似关联方不存在交易金额）
             */
            for (HisProcInst h : page.getResult()) {
                if (h.getStatus().equals("30")) {
                    ProcInstData procInstData = procInstDataService.get(h.getId());
                    JSONObject json = JSONObject.parseObject(procInstData.getFormData());
                    if (null != json) {
                        Map<String, String> data = publicMethodService.data(json.getInteger("pid"), json.getString("type"));
                        h.setRelType(data.get("relType"));
                        h.setBusinessType(data.get("businessType"));
                        h.setRelNumber(data.get("relNumber"));
                        h.setTransType(data.get("transType"));
                        h.setRelName(data.get("relName"));
                        h.setTransactionAmount(data.get("transactionAmount"));
                    }
                }
                hisProcInstli.add(h);
            }
            result.put(RESULT_TOTAL, page.getTotal());
            //result.put(RESULT_ROWS, page.getResult());
            result.put(RESULT_ROWS, hisProcInstli);
            return result;
        }

    }

    /**
     * 分页查询  我的流程-已审批
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryHis"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryHis(Page<HisProcInst> page, Model model, HttpServletRequest request) {
        List<HisProcInst> hisProcInstli = new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("startTime", OrderType.DESC));
        workflowEngine.getHistoryService().queryProcInst(page, filters, orders);
//        workflowEngine.getHistoryService().unionFormDataHisInProc(page);
        /**
         * 遍历集合将需要的业务数据存到新的集合中，在返现到页面
         * processType 流程类型
         * businessType 业务类型（自然人，投资入股等）
         * glfName 关联方名称
         * transactionAmount 关联交易金额（疑似关联方不存在交易金额）
         */


        for (HisProcInst h : page.getResult()) {
            /*ProcInstData procInstData = procInstDataService.get(h.getId());
            if (null != procInstData) {
                JSONObject json = JSONObject.parseObject(procInstData.getFormData());*/
                if (h.getStatus().equals("20") || h.getStatus().equals("40")) {

                    Map<String, String> data = publicMethodService.getData(h.getId(), h.getProcCode());
                    h.setRelType(data.get("relType"));
                    h.setBusinessType(data.get("businessType"));
                    h.setRelNumber(data.get("relNumber"));
                    h.setTransType(data.get("transType"));
                    h.setRelName(data.get("relName"));
                    h.setTransactionAmount(data.get("transactionAmount"));


                /*if (null != json) {
                    Map<String, String> data = publicMethodService.data(json.getInteger("pid"), json.getString("type"));
                    h.setRelType(data.get("relType"));
                    h.setBusinessType(data.get("businessType"));
                    h.setRelNumber(data.get("relNumber"));
                    h.setTransType(data.get("transType"));
                    h.setRelName(data.get("relName"));
                    h.setTransactionAmount(data.get("transactionAmount"));
                }*/
                }
            hisProcInstli.add(h);
        }
        result.put(RESULT_TOTAL, page.getTotal());
        //result.put(RESULT_ROWS, page.getResult());
        result.put(RESULT_ROWS, hisProcInstli);
        return result;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryLongTime"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryLongTime(Page<HisProcInst> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("startTime", OrderType.DESC));
        workflowEngine.getHistoryService().queryLongTimeProcInst(page, filters, orders);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryNotifyDataError"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryNotifyDataError(Page<HisProcInst> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("startTime", OrderType.DESC));
        workflowEngine.getHistoryService().queryNotifyDataError(page, filters, orders);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 分页查询
     *
     * @param procDefCode
     * @return
     */
    @RequestMapping(value = {"/cleanNotifyByTest"})
    @ResponseBody
    public Map<String, Object> cleanNotifyByTest(String procDefCode) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<IHisProcInst> hisProcInsts = workflowEngine.getHistoryService().queryHisProcInst(procDefCode, ProcInstStatus.COMPLETE.getValue());
        for (IHisProcInst hisProcInst : hisProcInsts) {
            workflowEngine.getRuntimeService().archiveProcInst(hisProcInst.getId());
        }
        result.put(WebCode.SUCCESS, true);
        result.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        return result;
    }

    /**
     * 流程监控
     *
     * @param model
     * @param passToken
     * @return
     */
    @RequestMapping(value = "/monitor")
    public String monitor(Model model, String passToken) {

        return "wf/procInstMonitorList";
    }

    /**
     * 组装taskInst
     *
     * @param procInst
     * @param task
     * @return
     */
    private TaskInstImpl createTaskInstance(IProcInst procInst, ITaskInst task) {
        TaskInstImpl taskInst = new TaskInstImpl(procInst);
        taskInst.setAssignee(task.getAssignee());
        taskInst.setCreateTime(task.getCreateTime());
        taskInst.setTaskId(task.getId());
        taskInst.setTaskKey(task.getTaskKey());
        taskInst.setTaskName(task.getTaskName());
        return taskInst;
    }

    @RequestMapping("/queryProcInstTaskId")
    @ResponseBody
    public String queryProcInstTaskId(String processInstantId) {
        String taskId = workflowEngine.getRuntimeService().queryTaskId(processInstantId);
        return taskId;
    }

} 	