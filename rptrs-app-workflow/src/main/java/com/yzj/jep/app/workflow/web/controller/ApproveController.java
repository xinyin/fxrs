/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年09月19日 11:15:38
 *
 */

package com.yzj.jep.app.workflow.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.workflow.service.GetBusProcService;
import com.yzj.jep.app.workflow.service.PublicMethodService;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.api.code.NodeType;
import com.yzj.jep.workflow.api.code.RenderType;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IProcLog;
import com.yzj.jep.workflow.api.model.IProcNode;
import com.yzj.jep.workflow.api.model.ITaskInst;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import com.yzj.jep.workflow.impl.model.TaskInstImpl;
import com.yzj.jep.workflow.wf.model.HisProcInst;
import com.yzj.jep.workflow.wf.model.ProcInst;
import com.yzj.jep.workflow.wf.model.ProcInstData;
import com.yzj.jep.workflow.wf.service.ProcInstDataService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 流程任务
 *
 * @author ZY
 * @time 2017年9月29日 上午9:39:14
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/workflow/approve")
public class ApproveController extends CrudController<ProcInst> {
    Logger logger = LoggerFactory.getLogger(ApproveController.class);

    @Autowired
    private WorkflowEngine workflowEngine;

    @Autowired
    private GetBusProcService getBusProcService;

    @Autowired
    private StaffService staffService;
    @Autowired
    private  ProcInstDataService procInstDataService;
    @Autowired
    private PublicMethodService publicMethodService;

    @Value("${MOVE_IP}")
    public String moveIp;

    private static final HashMap<String,String> ACTION = new HashMap<>();

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<ProcInst> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            throw new ServiceException("user session may be expired.");
        }
        StaffImpl staff = new StaffImpl();
        BeanUtils.copyProperties(profile, staff);
        workflowEngine.getRuntimeService().queryTaskInst(page, staff, filters, orders);
        List<ProcInst> pList = new ArrayList<>();
        Object ob = page.getResult();
        List<TaskInstImpl> taskInstsLi = castList(ob, TaskInstImpl.class);
        for (TaskInstImpl t:taskInstsLi) {
            ProcInst procInst = new ProcInst();
            BeanUtils.copyProperties(t.getProcInst(),procInst );
            procInst.setTaskId(t.getTaskId());
            procInst.setTaskName(t.getTaskName());
            procInst.setTaskKey(t.getTaskKey());
            pList.add(procInst);
        }
        page.setResult(pList);
        workflowEngine.getRuntimeService().unionFormDataInProc(page);
        List<ProcInst> pLists = new ArrayList<>();
        for (ProcInst procInst : page.getResult()) {
            ProcInstData procInstData = procInstDataService.get(procInst.getId());
            JSONObject json = JSONObject.parseObject(procInstData.getFormData());
            if (null != json) {
                Map<String, String> data = publicMethodService.data(json.getInteger("pid"), json.getString("type"));
                procInst.setRelType(data.get("relType"));
                procInst.setBusinessType(data.get("businessType"));
                procInst.setRelNumber(data.get("relNumber"));
                procInst.setTransType(data.get("transType"));
                procInst.setRelName(data.get("relName"));
                procInst.setTransactionAmount(data.get("transactionAmount"));
            }
            pLists.add(procInst);
        }
        page.setResult(pLists);
    }
    public static <T> List<T> castList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
    /**
     * 获取待审批列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/todoList")
    public String todoList(Model model) {
        return "wf/taskTodoList";
    }

    /**
     * 获取待审批列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/process")
    public String process(Model model,HttpServletRequest request,String procInstId,String taskId,String staffCode) {
        StaffImpl staff = new StaffImpl();
        logger.info("-----------------------进入审批页面获取数据----------------------------"+staffCode+"-----"+procInstId+"-----"+taskId);
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            throw new ServiceException("user session may be expired.");
        }
        //获取请求头判断 移动端还是pc端
        String userAgent = request.getHeader("user-agent");
        if(null!=profile){
            BeanUtils.copyProperties(profile, staff);
        }
        if(null != staffCode){
            staff.setStaffCode(staffCode);
        }
        String object = JSON.toJSONString(staff);
        logger.info("-----------------------入参："+procInstId+"---"+taskId+"---"+staff.getStaffCode()+"-------------------------------");
        logger.info("-----------------------判断是移动端还是PC端----------------------------");
        logger.info("------------------------求头信息-----------------------------"+userAgent);
        if (userAgent.indexOf("Android") != -1 || userAgent.indexOf("iPhone") != -1 || userAgent.indexOf("iPad") != -1) {
            logger.info("-----------------------移动端进入----------------------------");
            model.addAttribute("procInstId", procInstId);
            model.addAttribute("taskId", taskId);
            model.addAttribute("staffCode", staffCode);
            model.addAttribute("url", moveIp);
            return "wf/dataJump";
        }else {   // 电脑
            logger.info("-----------------------PC端进入----------------------------");
            /*点击处理即签领任务*/
            logger.info("-----------------------点击处理即签领任务----------------------------");
            logger.info("-----------------------入参："+procInstId+"---"+taskId+"---"+staff.getStaffCode()+"-------------------------------");
            workflowEngine.getRuntimeService().claim(procInstId, taskId, staff);
            //把处理当成view去处理
            logger.info("-----------------------获取数据----------------------------");
            Map<String, Object> map = workflowEngine.getFormService().getFormContent(procInstId, taskId, RenderType.VIEW);
            if ((boolean) map.get("isBusiness")) {
                model.addAttribute("procInstId", procInstId);
                model.addAttribute("taskId", taskId);
                model.addAttribute("clientAdd", map.get("clientAdd"));
                model.addAttribute("token", map.get("token"));
                model.addAttribute("renderType", RenderType.EDIT);
                model.addAttribute("data", map.get("data"));
                model.addAttribute("isLast",map.get("isLast"));
                return "wf/taskBusinessProcess";
            } else {
                //获取表单内容
                String content = (String) map.get("content");
                model.addAttribute("content", content);
                model.addAttribute("procInstId", procInstId);
                model.addAttribute("taskId", taskId);
                return "wf/taskProcess";
            }
        }
    }

    //移动端访问接口，返回数据
    @RequestMapping(value = "/passOrReturn")
    @ResponseBody
    public JSONObject passOrReturn(HttpServletRequest request, String procInstId,String taskId,String staffCode){
        logger.info("-----------------------移动端访问数据接口入参》》procInstId："+procInstId+"taskId："+taskId
        +"staffCode："+staffCode+"--------------------------------------");
        Map<String, Object> map = new HashMap<>();
        List<Map<String,String>> li =new ArrayList<>();
        StaffImpl staff = new StaffImpl();
        Staff sss = staffService.get(staffCode);
        BeanUtils.copyProperties(sss, staff);
        /*点击处理即签领任务*/
        logger.info("-----------------------点击处理即签领任务----------------------------");
        logger.info("-----------------------入参："+procInstId+"---"+taskId+"---"+staff.getStaffCode()+"-------------------------------");
        try {
            workflowEngine.getRuntimeService().claim(procInstId, taskId, staff);
            /*获得当前业务流程*/
            //把处理当成view去处理
            logger.info("-----------------------根据流程实例ID获取数据----------------------------");
            map = workflowEngine.getFormService().getFormContent(procInstId, taskId, RenderType.EDIT);
            JSONObject json = JSONObject.parseObject((String)map.get("data"));
            /*
             * 判断是关联方还是关联交易
             * */
            if(null != json.getString("isRel1")){
                //关联方
                map.put("type","REL");
                /*
                 * 关联方相关参数判断
                 * */
                Integer pid = json.getInteger("pid");
                if("2".equals(json.getInteger("relType"))){
                    map.put("relType","PSN");
                }else{
                    map.put("relType","ORG");
                }
                if(null == json.getInteger("otherCountries")){
                    json.put("otherCountries","");
                }
                List<JSONObject> relLi = getBusProcService.selectRelByPid(pid,json.getString("tenantId"));
                if(null != json.get("identifyType")){
                    json.put("identifyTypeY",getBusProcService.selectItemByDictCode("identifyType",(String)json.get("identifyType")));
                }
                if(null != json.get("nationality")){
                    json.put("nationalityY",getBusProcService.selectItemByDictCode("nationality",(String)json.get("nationality")));
                }
                json.put("relData",relLi);
            }else{
                //关联交易
                map.put("type","TRA");

                /*
                 * 关联交易相关参数判断
                 * */
                if(null != json.get("pricingPolicy")){
                    json.put("pricingPolicyY",getBusProcService.selectItemByDictCode("pricingPolicy",(String)json.get("pricingPolicy")));
                }
                if(null != json.get("considerType")){
                    json.put("considerTypeY",getBusProcService.selectItemByDictCode("considerType",(String)json.get("considerType")));
                }
                if(null != json.get("businessType") && "02".equals(json.get("tranType"))){
                    json.put("businessTypeY",getBusProcService.selectItemByDictCode("businessTypeF",(String)json.get("businessType")));
                }else{
                    if(null != json.get("businessType")){
                        json.put("businessTypeY",getBusProcService.selectItemByDictCode("businessType",(String)json.get("businessType")));
                    }

                }
                if(null != json.get("tranType")){
                    json.put("tranTypeY",getBusProcService.selectItemByDictCode("tranType",(String)json.get("tranType")));
                }

                if(null != json.get("securitiesType")){
                    json.put("securitiesTypeY",getBusProcService.selectItemByDictCode("securitiesType",(String)json.get("securitiesType")));
                }
                if(null != json.get("unitfTradeAgrmentAbusiness")){
                    json.put("unitfTradeAgrmentAbusinessY",getBusProcService.selectItemByDictCode("unitfTradeAgrmentAbusiness",(String)json.get("unitfTradeAgrmentAbusiness")));
                }
                String tranTypec="04";
                String serviceTypec="05";
                if(null != json.get("busType") && tranTypec == json.get("tranType")){
                    json.put("busTypeY",getBusProcService.selectItemByDictCode("busType",(String)json.get("busType")));
                }else if(null != json.get("busType") && serviceTypec.equals(json.get("tranType"))){
                    json.put("busTypeY",getBusProcService.selectItemByDictCode("busTypeS",(String)json.get("busType")));
                }else{
                    json.put("busTypeY",getBusProcService.selectItemByDictCode("busTypeI",(String)json.get("busType")));
                }
                if(null != json.get("unitfTradeAgrmentInterest")){
                    json.put("unitfTradeAgrmentInterestY",getBusProcService.selectItemByDictCode("unitfTradeAgrmentInterest",(String)json.get("unitfTradeAgrmentInterest")));
                }
                if(null != json.get("investmentMethod")){
                    json.put("investmentMethodY",getBusProcService.selectItemByDictCode("investmentMethod",(String)json.get("investmentMethod")));
                }

                String url = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServerName();
                logger.info("------------------------输出当前url-------"+url+"--------------------");
                if(null != json.get("transNum")){li = getBusProcService.selectPathBytransNum((String)json.get("transNum"),url,(String)json.get("tenantId"));}
            }
            //日志信息获取
            logger.info("------------------------查询获取操作日志信息---------------------------");
            List<? extends IProcLog> logs = workflowEngine.getLogService().selectRunningProcLog(procInstId);
            List<Object> logli=new ArrayList<>();
            Set<String> se= new HashSet<>();
            for(IProcLog log : logs){
                Map<String, Object> tips1 = new HashMap<String, Object>();
                tips1.put("logTime", DateUtils.format(log.getLogTime(), DateUtils.DATE_TIME_FORMAT));
                tips1.put("taskName",log.getTaskName() != null ? log.getTaskName() : "-");
                tips1.put("staffName",log.getStaffName() != null ? log.getStaffName() : "-");
                tips1.put("staffCode",log.getStaffName() != null ? log.getStaffCode() : "-");
                tips1.put("action",ACTION.get(log.getAction()) != null ? ACTION.get(log.getAction()) : "-");
                tips1.put("memo",log.getMemo() != null ? log.getMemo() : "-");
                se.add(log.getStaffName() != null ? log.getStaffName() : "-");
                if(!"认领".equals(ACTION.get(log.getAction()))){
                    logli.add(tips1);
                }
            }
            map.put("procInstId",procInstId);
            map.put("taskId",taskId);
            map.put("code","200");
            map.put("filePaths",li);
            map.put("log",logli);
            map.put("json",json);
            String data=JSON.toJSONString(map);
            JSONObject jsonObject = JSONObject.parseObject(data);
            return jsonObject;
        }catch (Exception e){
            logger.info("---------------"+staff.getStaffCode()+"----------------------");
            logger.error("---------------点击处理即签领任务方法报错:----------------------"+e);
            map.put("msg","点击处理即签领任务方法报错");
            map.put("code","300");
            String data=JSON.toJSONString(map);
            JSONObject jsonObject = JSONObject.parseObject(data);
            return jsonObject;
        }

    }


    /**
     * 移动端审批通过
     *
     */
    @RequestMapping(value = "/processPass")
    @ResponseBody
    public Map<String, Object> processPass(String taskId,String formData, String procInstId, String memo,String staffCode) {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        Staff sss= staffService.get(staffCode);
        try {
            if (StringUtils.isNotBlank(memo)) {
                JSONObject jsonObject = JSON.parseObject(formData);
                jsonObject.put("memo",memo);
                formData = JSON.toJSONString(jsonObject);
            }
            if (null == staffCode) {
                throw new ServiceException("缺少审批人编号!");
            }
            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(sss, staff);
            workflowEngine.getRuntimeService().complete(procInstId, taskId, staff, memo, formData);
            data.put(WebCode.SUCCESS, true);
            data.put("code","200");
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put("code","300");
            data.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return data;
    }

    /*
     *
     * 移动端审批退回
     * */
    @RequestMapping(value = "/processReturn")
    @ResponseBody
    public Map<String, Object> processReturn(String taskId, String nodeKey, String procInstId, String memo,String fileUel,String staffCode) {
        Map<String, Object> data = new HashMap<String, Object>(16);
        Staff sss= staffService.get(staffCode);
        StaffImpl staff = new StaffImpl();
        try {
            if (null == staffCode) {
                throw new ServiceException("没有当前处理人ID");
            }
            BeanUtils.copyProperties(sss, staff);
            workflowEngine.getRuntimeService().reject(procInstId, taskId, nodeKey, staff, memo);
            data.put(WebCode.SUCCESS, true);
            data.put("code","200");
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (Exception e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put("code","300");
            data.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return data;
    }


    /**
     * 查询已经通过的节点
     *
     * @return
     */
    @RequestMapping(value = "/selectCompleteNode")
    @ResponseBody
    public List<NameValuePair> selectCompleteNode(String procInstId, String taskId) {
        List<IProcNode> list = new ArrayList<>();
        List<? extends IProcNode> result = workflowEngine.getRuntimeService().selectRejectableNodes(procInstId, taskId);
        for (IProcNode iProcNode : result) {
            if (NodeType.USERTASK.getValue().equals(iProcNode.getType())) {
                list.add(iProcNode);
            }
        }
        return NVUtils.convert(list, list.size(), "key", "name");
    }

    /**
     * 获取已审批列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/doneList")
    public String doneList(Model model) {
        return "wf/taskDoneList";
    }


    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryHis"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryHis(@SuppressWarnings("rawtypes") Page<HisProcInst> page, Model model, HttpServletRequest request) {
        List<HisProcInst> hisProcInstli = new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        UserProfile user = UserProfileUtils.getUserProfile();
        workflowEngine.getHistoryService().queryDoneProcInst(page, user.getStaffCode(), filters, orders);
        for (HisProcInst h : page.getResult()) {
            Map<String, String> data = publicMethodService.getData(h.getId(),h.getProcCode());
            h.setRelType(data.get("relType"));
            h.setBusinessType(data.get("businessType"));
            h.setRelNumber(data.get("relNumber"));
            h.setTransType(data.get("transType"));
            h.setRelName(data.get("relName"));
            h.setTransactionAmount(data.get("transactionAmount"));
            hisProcInstli.add(h);
        }
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
    @RequestMapping(value = {"/queryNoCandidate"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryNoCandidate(@SuppressWarnings("rawtypes") Page page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        workflowEngine.getRuntimeService().queryNoCandidateTaskInst(page, filters, orders);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }


    /**
     * 保存流程数据 不启动
     *
     * @param taskId
     * @param formData
     * @param procInstId
     * @param memo
     * @return
     */
    @RequestMapping(value = "/complete")
    @ResponseBody
    public Map<String, Object> complete(String taskId, String formData, String procInstId, String memo) {
        Map<String, Object> data = new HashMap<String, Object>();
        StaffImpl staffs = new StaffImpl();
        staffs.setStaffName(memo);
        boolean b = XssCheck.reflect(staffs);
        if (!b) {
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, "数据格式不规范，请检查!");
            return data;
        }
        try {
            try {
                formData = new String(Base64.decodeBase64(formData),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (StringUtils.isNotBlank(memo)) {
				JSONObject jsonObject = JSON.parseObject(formData);
				jsonObject.put("memo",memo);
				formData = JSON.toJSONString(jsonObject);
			}
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            }
            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);
            workflowEngine.getRuntimeService().complete(procInstId, taskId, staff,memo, formData);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return data;
    }


    /**
     * 保存流程数据 不启动
     * @param taskId
     * @param nodeKey
     * @param procInstId
     * @param memo
     * @return
     */
    @RequestMapping(value = "/reject")
    @ResponseBody
    public Map<String, Object> reject(String taskId, String nodeKey, String procInstId, String memo) {

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            }
            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);
            workflowEngine.getRuntimeService().reject(procInstId, taskId, nodeKey, staff, memo);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return data;
    }

    /**
     * 挂起操作
     *
     * @param procInstId
     * @return
     */
    @RequestMapping(value = "/suspend")
    @ResponseBody
    public Map<String, Object> suspend(String procInstId) {

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            }
            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);
            workflowEngine.getRuntimeService().suspend(procInstId, staff);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return data;
    }


    /**
     * 激活流程
     *
     * @param procInstId
     * @return
     */
    @RequestMapping(value = "/activate")
    @ResponseBody
    public Map<String, Object> activate(String procInstId) {

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            }
            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);
            workflowEngine.getRuntimeService().activate(procInstId, staff);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return data;
    }

    /**
     * 终止页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/terminatePage")
    public String terminatePage(String procInstId, Model model) {
        model.addAttribute("procInstId", procInstId);
        return "wf/procInstTerminate";
    }

    /**
     * 终止流程
     *
     * @param procInstId
     * @param memo
     * @return
     */
    @RequestMapping(value = "/terminate")
    @ResponseBody
    public Map<String, Object> terminate(String procInstId, String memo) {

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            }
            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);
            workflowEngine.getRuntimeService().terminate(procInstId, staff, memo);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return data;
    }

    /**
     * 转办页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/transferPage")
    public String transferPage(String procInstId, String taskId, Model model) {
        model.addAttribute("procInstId", procInstId);
        model.addAttribute("taskId", taskId);
        return "wf/procInstTransfer";
    }

    /**
     * 查询表字段
     *
     * @return 表字段
     */
    @RequestMapping(value = "/queryTaskList")
    @ResponseBody
    public List<NameValuePair> queryTaskList(String procInstId) {
        List<? extends ITaskInst> taskInstImpls = workflowEngine.getRuntimeService().selectTaskInst(procInstId);
        List<NameValuePair> nameValuePairs = NVUtils.convert(taskInstImpls, taskInstImpls.size(), "taskId", "taskName");
        return nameValuePairs;
    }

    /**
     * 指定操作人（转办）
     *
     * @param taskId
     * @param procInstId
     * @param memo
     * @return
     */
    @RequestMapping(value = "/transfer")
    @ResponseBody
    public Map<String, Object> transfer(String taskId, String procInstId, String assignee, String assigneeText, String memo) {

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            }
            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);
            StaffImpl assigneeStaff = new StaffImpl();
            assigneeStaff.setStaffCode(assignee);
            assigneeStaff.setStaffName(assigneeText);
            workflowEngine.getRuntimeService().transfer(procInstId, staff, taskId, assigneeStaff, memo);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return data;
    }

    /**
     * 跳转到指定页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/moveToPage")
    public String moveToPage(String procInstId, Model model) {
        model.addAttribute("procInstId", procInstId);
        return "wf/procInstMoveTo";
    }

    /**
     * 查询当前任务能跳转的节点
     *
     * @return
     */
    @RequestMapping(value = "/queryNodeList")
    @ResponseBody
    public List<NameValuePair> queryNodeList(String procInstId) {
        List<? extends IProcNode> taskInstImpls = workflowEngine.getRuntimeService().selectNoTaskNode(procInstId);
        List<NameValuePair> nameValuePairs = NVUtils.convert(taskInstImpls, taskInstImpls.size(), "key", "name");
        return nameValuePairs;
    }

    /**
     * 跳转到直接节点
     *
     * @param taskId
     * @param procInstId
     * @param memo
     * @return
     */
    @RequestMapping(value = "/moveTo")
    @ResponseBody
    public Map<String, Object> moveTo(String taskId, String procInstId, String nodeKey, String memo) {

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            }
            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);
            workflowEngine.getRuntimeService().moveTo(procInstId, taskId, staff, nodeKey, memo);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return data;
    }

    /**
     * 加签页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/insertTaskPage")
    public String insertTaskPage(String procInstId, String taskId, String flag, Model model) {
        model.addAttribute("procInstId", procInstId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("flag", flag);
        return "wf/procInstInsertTask";
    }


    /**
     * 在任务后面插入节点
     *
     * @param taskId
     * @param procInstId
     * @param memo
     * @return
     */
    @RequestMapping(value = "/insertTasksAfter")
    @ResponseBody
    public Map<String, Object> insertTasksAfter(String taskId, String procInstId, String assignee, String assigneeText, String memo) {

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            }
            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);
            if (StringUtils.isBlank(assignee)) {
                throw new ServiceException("user session may be expired.");
            }
            StaffImpl assigneeStaff = new StaffImpl();
            assigneeStaff.setStaffCode(assignee);
            assigneeStaff.setStaffName(assigneeText);
            workflowEngine.getRuntimeService().insertTasksAfter(procInstId, taskId, staff, assigneeStaff, memo);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return data;
    }


    /**
     * 在任务前面插入节点
     *
     * @param taskId
     * @param procInstId
     * @param memo
     * @return
     */
    @RequestMapping(value = "/insertTasksBefore")
    @ResponseBody
    public Map<String, Object> insertTasksBefore(String taskId, String procInstId, String assignee, String assigneeText, String memo) {

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            }
            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);
            StaffImpl assigneeStaff = new StaffImpl();
            assigneeStaff.setStaffCode(assignee);
            assigneeStaff.setStaffName(assigneeText);
            workflowEngine.getRuntimeService().insertTasksBefore(procInstId, taskId, staff, assigneeStaff, memo);
            data.put(WebCode.SUCCESS, true);
            data.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            data.put(WebCode.SUCCESS, false);
            data.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return data;
    }

    /**
     * 首页待办通知
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="/waitnotice")
    public List waitNotice(Model model){
        List list = new ArrayList();
        List<TaskInstImpl> result = null;
        SimpleDateFormat sdf = null;
        try{
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            }
            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);
            Page<TaskInstImpl> page = new Page<>();
            List<Order> orders = new Order.Builder().desc("createTime").build();
            List<Filter> filters = new Filter.Builder().eq("staffCode", staff.getStaffCode()).build();
            workflowEngine.getRuntimeService().queryTaskInst(page, staff, filters, orders);

            result = page.getResult();
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(CollectionUtils.isNotEmpty(result)){
                for(int i=0;i<result.size();i++){
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("procName",result.get(i).getProcInst().getProcName()+","+result.get(i).getTaskName());
                    map.put("procInstId",result.get(i).getProcInst().getId());
                    map.put("taskId",result.get(i).getTaskId());
                    map.put("createtime",sdf.format(result.get(i).getCreateTime()));
                    if(i < 5){
                        list.add(map);
                    }
                }
            }
        }catch (Exception e){
            logger.info("首页待办异常信息："+e.getMessage());
        }
        return list;
    }
}
