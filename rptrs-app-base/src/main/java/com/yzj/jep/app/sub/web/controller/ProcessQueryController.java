package com.yzj.jep.app.sub.web.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.fil.service.*;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.model.TraOther;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.ins.service.TraOtherService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.sub.service.ProcessQueryService;
import com.yzj.jep.app.sub.vo.HisProcInstVo;
import com.yzj.jep.app.sub.vo.TaskInstVo;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.service.UserProfileService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.service.UserService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import com.yzj.jep.workflow.wf.model.HisProcInst;
import com.yzj.jep.workflow.wf.model.ProcInst;
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
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ProcessQueryController
 * @Author Whx
 * @Date: 2021/7/15 21:21
 * @Version 1.0
 * 我的流程查询 UnifiedProtocolController
 */
@Controller
@RequestMapping("/sub/process")
public class ProcessQueryController {

    private static Logger logger = LoggerFactory.getLogger(ProcessQueryController.class);

    private static final String RESULT_TOTAL = "total";
    private static final String RESULT_ROWS = "rows";

    @Autowired
    private WorkflowEngine workflowEngine;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ProcessQueryService processQueryService;
    @Autowired
    TraInvestmentService traInvestmentService;
    @Autowired
    TraServiceService traServiceService;
    @Autowired
    TraInterestService traInterestService;
    @Autowired
    TraFundsService traFundsService;
    @Autowired
    FilInvestmentService filInvestmentService;
    @Autowired
    FilServiceService filServiceService;
    @Autowired
    FilInterestService filInterestService;
    @Autowired
    FilFundsService filFundsService;
    @Autowired
    TraOtherService traOtherService;
    @Autowired
    TraBusinessService traBusinessService;
    @Autowired
    ProductService productService;
    @Autowired
    RootOrgInfoService rootOrgInfoService;
    @Autowired
    RelService relService;
    @Autowired
    private FilBusinessService filBusinessService;
    @Autowired
    private FilRelProService relProService;
    @Autowired
    private FilRootOrgInfoService filRootOrgInfoService;
    @Autowired
    private FilRelService filRelService;
    @Autowired
    private RelPsnSuspectService relPsnSuspectService;
    @Autowired
    private RelOrgSuspectService relOrgSuspectService;
    @Autowired
    private FilOtherService filOtherService;
    @Autowired
    private FilAuthApplyService filAuthApplyService;
    @Autowired
    private FilEntrustApplyService filEntrustApplyService;
    @Autowired
    private TraCounterpartyService traCounterpartyService;

    @Autowired
    RelPsnService relPsnService;

    @Resource
    private RelProService proService;
    @Resource
    private AuthApplyService authApplyService;
    @Resource
    private EntrustApplyService entrustApplyService;

    private static final Map<String, String> subjectName = new HashMap<>();

    static {

        /*
         * 根据不同的主体来添加不同的后缀
         * */
        subjectName.put("JT", "JT_");
        subjectName.put("SX", "SX_");
        subjectName.put("CX", "CX_");
        subjectName.put("XB", "XB_");
        subjectName.put("ZG", "ZG_");
    }

    private static final Map<String, String> subjectProcesses = new HashMap<>();
    /**
     * 我的流程，审批中、已审批
     *
     * @param page
     * @param model
     * @param request
     * @param passToken
     * @return
     */
    @RequestMapping(value = {"/queryHistory"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryHistory(Page<HisProcInst> page, Model model, HttpServletRequest request, String passToken) {
        if (StringUtils.isNotEmpty(passToken)) {
            model.addAttribute("passToken", userService.processToken(passToken).get(UserService.PASS_TOKEN));
            Map<String, Object> result = new HashMap<String, Object>();
            List<Filter> filters = RequestUtils.buildFilters(request);
            List<Order> orders = RequestUtils.buildOrders(request);
            orders.add(new Order("modifiedTime", OrderType.DESC));
            orders.add(new Order("startTime", OrderType.DESC));
            workflowEngine.getHistoryService().queryProcInst(page, filters, orders);
            workflowEngine.getHistoryService().unionFormDataHisInProc(page);
            result.put(RESULT_TOTAL, page.getTotal());
            List<HisProcInstVo> list = processQueryService.queryTrustDetail(page.getResult());
            result.put(RESULT_ROWS, list);
            return result;
        } else {
            Map<String, Object> result = new HashMap<String, Object>();
            List<Filter> filters = RequestUtils.buildFilters(request);
            List<Order> orders = RequestUtils.buildOrders(request);
            orders.add(new Order("modifiedTime", OrderType.DESC));
            orders.add(new Order("startTime", OrderType.DESC));
            workflowEngine.getHistoryService().queryProcInst(page, filters, orders);
            workflowEngine.getHistoryService().unionFormDataHisInProc(page);
            result.put(RESULT_TOTAL, page.getTotal());
            List<HisProcInstVo> list = processQueryService.queryTrustDetail(page.getResult());
            result.put(RESULT_ROWS, list);
            return result;
        }


    }

    /**
     * 我的流程 已审批
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryHis"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryHis(@SuppressWarnings("rawtypes") Page<HisProcInst> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        UserProfile user = UserProfileUtils.getUserProfile();
        workflowEngine.getHistoryService().queryDoneProcInst(page, user.getStaffCode(), filters, orders);
        result.put(RESULT_TOTAL, page.getTotal());
        List<HisProcInstVo> list = processQueryService.queryTrustDetail(page.getResult());
        result.put(RESULT_ROWS, list);
        return result;
    }

    @RequestMapping(value = {"/queryTodo"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryTodo(@SuppressWarnings("rawtypes") Page<ProcInst> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            throw new ServiceException("user session may be expired.");
        }
        StaffImpl staff = new StaffImpl();
        BeanUtils.copyProperties(profile, staff);
        workflowEngine.getRuntimeService().queryTaskInst(page, staff, filters, orders);
        result.put("total", page.getTotal());
        //封装task任务详情
        List<TaskInstVo> list = processQueryService.queryTaskType(page.getResult());
        result.put("rows", list);
        return result;
    }
    @RequestMapping("/queryData")
    @ResponseBody
    public ModelAndView queryData(String procInsId, String procCode, ModelAndView model) {
        List<Filter> filters = new Filter.Builder().eq("procInstId", procInsId).build();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String procDefCode = null;
        procDefCode = subjectName.get(profile.getTenantId());
        if (procCode.equals(procDefCode+"YSGLFSPLC")){
            RelPsnSuspect psnSuspect = relPsnSuspectService.get(filters);
            RelOrgSuspect relOrgSuspect = relOrgSuspectService.get(filters);
            if (psnSuspect!=null){
                model.addObject("relPsnSuspect", psnSuspect);
                model.setViewName("ex/suspectRelPsnformHandleView");
            }else if (relOrgSuspect!=null){
                model.addObject("relOrgSuspect", relOrgSuspect);
                model.setViewName("ex/relOrgSuspectformHandleView");
            }else {
                logger.debug("没有查到数据！");
            }
            //zhd
        }else if (procCode.equals("FXBDX_GLGXBGBA")){
            Rel rel = relService.get(filters);
            if (rel!=null){
                model.addObject("rel", rel);
                model.setViewName("bus/relationView");
            }else {
                logger.debug("没有查到数据！");
            }

        }else if (procCode.equals("FXBDX_ZDTY") || procCode.equals("FXBDX_YBGLJY_Company")
                || procCode.equals("FXBDX_YBGLJY") || procCode.equals("FXBDX_ZDTY_BranchOffice")){
            TraInvestment traInvestment = traInvestmentService.get(filters);
            TraFunds traFunds = traFundsService.get(filters);
            TraBusiness traBusiness = traBusinessService.get(filters);
            TraInterest traInterest = traInterestService.get(filters);
            TraService traService = traServiceService.get(filters);
            TraOther traOther = traOtherService.get(filters);
            if (traInvestment!=null){
                //获取交易对手列表信息
                List<Filter> filterList = new ArrayList<>();
                filterList.add(new Filter("transNum", traInvestment.getTransNum()));
                List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
                if (counterpartyList != null && counterpartyList.size() > 0) {
                    traInvestment.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
                }
                if("PSN".equals(traInvestment.getRelType())){
                    //查询关联方证件类型
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(traInvestment.getRelNumber())){
                        RelPsn relPsn = relPsnService.selectByIdentifyNumber(traInvestment.getRelNumber());
                        if(ObjectUtil.isNotEmpty(relPsn)){
                            if("1".equals(relPsn.getIdentifyType())){
                                traInvestment.setRelNumber(idEncrypt(traInvestment.getRelNumber()));
                            }else{
                                traInvestment.setRelNumber(otherIdEncrypt(traInvestment.getRelNumber()));
                            }
                        }
                    }
                }
                model.addObject("traInvestment",traInvestment );
                model.addObject("opt","view" );
                model.setViewName("ins/traInvestmentEdit");
            }else if (traFunds!=null){
                //获取交易对手列表信息
                List<Filter> filterList = new ArrayList<>();
                filterList.add(new Filter("transNum", traFunds.getTransNum()));
                List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
                if (counterpartyList != null && counterpartyList.size() > 0) {
                    traFunds.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
                }
                if("PSN".equals(traFunds.getRelType())){
                    //查询关联方证件类型
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(traFunds.getRelNumber())){
                        RelPsn relPsn = relPsnService.selectByIdentifyNumber(traFunds.getRelNumber());
                        if(ObjectUtil.isNotEmpty(relPsn)){
                            if("1".equals(relPsn.getIdentifyType())){
                                traFunds.setRelNumber(idEncrypt(traFunds.getRelNumber()));
                            }else{
                                traFunds.setRelNumber(otherIdEncrypt(traFunds.getRelNumber()));
                            }
                        }
                    }
                }
                model.addObject("traFunds",traFunds);
                model.addObject("opt","view" );
                model.setViewName("ins/traFundsEdit");
            }else if (traBusiness!=null){
                //获取交易对手列表信息
                List<Filter> filterList = new ArrayList<>();
                filterList.add(new Filter("transNum", traBusiness.getTransNum()));
                List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
                if (counterpartyList != null && counterpartyList.size() > 0) {
                    traBusiness.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
                }
                if("PSN".equals(traBusiness.getRelType())){
                    //查询关联方证件类型
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(traBusiness.getRelNumber())){
                        RelPsn relPsn = relPsnService.selectByIdentifyNumber(traBusiness.getRelNumber());
                        if(ObjectUtil.isNotEmpty(relPsn)){
                            if("1".equals(relPsn.getIdentifyType())){
                                traBusiness.setRelNumber(idEncrypt(traBusiness.getRelNumber()));
                            }else{
                                traBusiness.setRelNumber(otherIdEncrypt(traBusiness.getRelNumber()));
                            }
                        }
                    }
                }
                model.addObject("traBusiness",traBusiness);
                model.addObject("opt","view" );
                model.setViewName("ins/traBusinessEdit");
            }else if (traInterest!=null){
                //获取交易对手列表信息
                List<Filter> filterList = new ArrayList<>();
                filterList.add(new Filter("transNum", traInterest.getTransNum()));
                List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
                if (counterpartyList != null && counterpartyList.size() > 0) {
                    traInterest.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
                }

                if("PSN".equals(traInterest.getRelType())){
                    //查询关联方证件类型
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(traInterest.getRelNumber())){
                        RelPsn relPsn = relPsnService.selectByIdentifyNumber(traInterest.getRelNumber());
                        if(ObjectUtil.isNotEmpty(relPsn)){
                            if("1".equals(relPsn.getIdentifyType())){
                                traInterest.setRelNumber(idEncrypt(traInterest.getRelNumber()));
                            }else{
                                traInterest.setRelNumber(otherIdEncrypt(traInterest.getRelNumber()));
                            }
                        }
                    }
                }
                model.addObject("traInterest",traInterest);
                model.addObject("opt","view" );
                model.setViewName("ins/traInterestEdit");
            }else if (traService!=null){
                //获取交易对手列表信息
                List<Filter> filterList = new ArrayList<>();
                filterList.add(new Filter("transNum", traService.getTransNum()));
                List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
                if (counterpartyList != null && counterpartyList.size() > 0) {
                    traService.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
                }
                if("PSN".equals(traService.getRelType())){
                    //查询关联方证件类型
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(traService.getRelNumber())){
                        RelPsn relPsn = relPsnService.selectByIdentifyNumber(traService.getRelNumber());
                        if(ObjectUtil.isNotEmpty(relPsn)){
                            if("1".equals(relPsn.getIdentifyType())){
                                traService.setRelNumber(idEncrypt(traService.getRelNumber()));
                            }else{
                                traService.setRelNumber(otherIdEncrypt(traService.getRelNumber()));
                            }
                        }
                    }
                }
                model.addObject("traService",traService);
                model.addObject("opt","view" );
                model.setViewName("ins/traServiceEdit");
            }else if (traOther!=null){
                //获取交易对手列表信息
                List<Filter> filterList = new ArrayList<>();
                filterList.add(new Filter("transNum", traOther.getTransNum()));
                List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
                if (counterpartyList != null && counterpartyList.size() > 0) {
                    traOther.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
                }
                if("PSN".equals(traOther.getRelType())){
                    //查询关联方证件类型
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(traOther.getRelNumber())){
                        RelPsn relPsn = relPsnService.selectByIdentifyNumber(traOther.getRelNumber());
                        if(ObjectUtil.isNotEmpty(relPsn)){
                            if("1".equals(relPsn.getIdentifyType())){
                                traOther.setRelNumber(idEncrypt(traOther.getRelNumber()));
                            }else{
                                traOther.setRelNumber(otherIdEncrypt(traOther.getRelNumber()));
                            }
                        }
                    }
                }
                model.addObject("traOther",traOther);
                model.addObject("opt","view" );
                model.setViewName("ins/traOtherEdit");
            }else {
                logger.debug("没有查到数据！");
            }
        }
        return model;
    }
    /**
     * @param procInsId 流程实例ID
     * @param procCode  流程编码ID
     * @return
     */
    /*@RequestMapping("/queryFormData")
    @ResponseBody
    public ModelAndView queryFormData(String procInsId, String procCode, String fillNumber, ModelAndView model) {
        //查询中间表
        List<MidBusProc> list = processQueryService.queryMidData(procInsId);
        if (CollectionUtil.isEmpty(list)) {
            throw new RuntimeException("暂无备份");
        }
        //根据流程编码判断业务流程
        if ("zybr_relGeneral".equals(procCode)
                || "zybr_relGreat".equals(procCode)
                || "zyzbyjybrel".equals(procCode)
                || "zyzbyizdrel".equals(procCode)
                || "zyzbejybrel".equals(procCode)
                || "zyzbejzdrel".equals(procCode)) {
            //关联交易审批流程
            return processRelTran(list, model, fillNumber);

        } else if ("ZZ_Pro001".equals(procCode)) {
            //产品审批流程
            if (list.size() == 1) {
                //单条审批
                MidBusProc busProc = list.get(0);
                FilRelPro filRelPro = relProService.get(Integer.valueOf(busProc.getFilPid()));
                RelPro relPro = new RelPro();
                BeanUtil.copyProperties(filRelPro, relPro);
                model.addObject("relPro", relPro);
                model.setViewName("ex/proForm");
            } else {
                //多条审批
                StringBuilder sb = new StringBuilder("");
                for (MidBusProc midBusProc : list) {
                    sb.append(midBusProc.getFilPid()).append(",");
                }
                model.addObject("pids", sb.toString());
                model.setViewName("ex/filBatchProForm");
            }
        } else if ("ZZ_Org001".equals(procCode)) {
            //机构审批流程
            MidBusProc midBusProc = list.get(0);
            FilRootOrgInfo filRootOrgInfo = filRootOrgInfoService.get(Integer.valueOf(midBusProc.getFilPid()));
            RootOrgInfo orgInfo = new RootOrgInfo();
            BeanUtil.copyProperties(filRootOrgInfo, orgInfo);
            model.addObject("rootOrgInfo", orgInfo);
            model.setViewName("ex/rootOrgInfoForm");
        } else if ("zyRel003".equals(procCode)) {
            //关联关系审批流程
            if (list.size() == 1) {
                MidBusProc midBusProc = list.get(0);
                FilRel filRel = filRelService.get(Integer.valueOf(midBusProc.getFilPid()));
                Rel rel = new Rel();
                BeanUtil.copyProperties(filRel, rel);
                model.addObject("rel", rel);
                model.setViewName("ex/relForm");
            } else {
//                model.addObject("pids", focusApprovePid);
                //多条审批
                StringBuilder sb = new StringBuilder("");
                for (MidBusProc midBusProc : list) {
                    sb.append(midBusProc.getFilPid()).append(",");
                }
                model.addObject("pids", sb.toString());
                model.setViewName("ex/filRelTableForm");
            }
        } else if ("ZZ_AuthApproval".equals(procCode)) {
            //权限审批流程
            //单笔
            MidBusProc midBusProc = list.get(0);
            String filPid = midBusProc.getFilPid();
            FilAuthApply filAuthApply = filAuthApplyService.get(Integer.valueOf(filPid));
            AuthApply authApply = new AuthApply();
            BeanUtil.copyProperties(filAuthApply, authApply);
            model.addObject("authApply", authApply);
            model.addObject("opt", "view");
            //不展示内容
            model.addObject("op", 1);
            model.setViewName("ex/qxForm1");
        } else if ("ZZ_taskDelegation".equals(procCode)) {
            //任务委托申请
            //单笔
            MidBusProc midBusProc = list.get(0);
            String filPid = midBusProc.getFilPid();
            FilEntrustApply filEntrustApply = filEntrustApplyService.get(Integer.valueOf(filPid));
            EntrustApply entrustApply = new EntrustApply();
            BeanUtil.copyProperties(filEntrustApply, entrustApply);
            if ("0".equals(entrustApply.getEntrustType())) {
                entrustApply.setEntrustType("新增委托");
            } else {
                entrustApply.setEntrustType("变更委托");
            }
            model.addObject("entrustApply", entrustApply);
            model.addObject("opt", "view");
            //不展示内容
            model.addObject("op", 1);
            model.setViewName("ex/entrustForm");
        }
        return model;
    }*/

    /**
     * @param list
     * @param model
     * @param fillNumber
     * @return
     */
    /*private ModelAndView processRelTran(List<MidBusProc> list, ModelAndView model, String fillNumber) {
        String substring = fillNumber.substring(0, 1);
        if ("1".equals(substring)) {
            if (list.size() == 1) {
                //单笔交易
//                TraInvestment traInterest = traInvestmentService.get(Integer.valueOf(flagPid));
                MidBusProc midBusProc = list.get(0);
                FilInvestment filInvestment = filInvestmentService.get(Integer.valueOf(midBusProc.getFilPid()));
                TraInvestment traInvestment = new TraInvestment();
                BeanUtil.copyProperties(filInvestment, traInvestment);
                model.addObject("traInvestment", traInvestment);
                model.setViewName("ex/traInvestmentApprove");
            } else {
                //集中审议
                StringBuilder sb = new StringBuilder("");
                for (MidBusProc midBusProc : list) {
                    sb.append(midBusProc.getFilPid()).append(",");
                }
                model.addObject("pids", sb.toString());
                model.setViewName("ex/filTraInvestmentBatchApprove");
            }
        } else if ("2".equals(substring)) {
            if (list.size() == 1) {
                //单笔交易
                MidBusProc midBusProc = list.get(0);
                FilFunds filFunds = filFundsService.get(Integer.valueOf(midBusProc.getFilPid()));
                TraFunds traFunds = new TraFunds();
                BeanUtil.copyProperties(filFunds, traFunds);
//                TraFunds traFunds = traFundsService.get(Integer.valueOf(flagPid));
                model.addObject("traFunds", traFunds);
                model.setViewName("ex/traFundsSubmit");
            } else {
                //集中审议
                StringBuilder sb = new StringBuilder("");
                for (MidBusProc midBusProc : list) {
                    sb.append(midBusProc.getFilPid()).append(",");
                }
                model.addObject("pids", sb.toString());
                model.setViewName("ex/filTraFundsBatchSubmit");
            }
        } else if ("3".equals(substring)) {
            if (list.size() == 1) {
                //单笔交易
                MidBusProc midBusProc = list.get(0);
                FilBusiness filBusiness = filBusinessService.get(Integer.valueOf(midBusProc.getFilPid()));
                TraBusiness traBusiness = new TraBusiness();
                BeanUtil.copyProperties(filBusiness, traBusiness);
//                TraBusiness traBusiness = traBusinessService.get(Integer.valueOf(flagPid));
                model.addObject("traBusiness", traBusiness);
                model.setViewName("ex/traBusinessForm");
            } else {
                //集中审议
                StringBuilder sb = new StringBuilder("");
                for (MidBusProc midBusProc : list) {
                    sb.append(midBusProc.getFilPid()).append(",");
                }
                model.addObject("pids", sb.toString());
                model.setViewName("ex/filTraFundsBatchSubmit");
            }

        } else if ("4".equals(substring)) {
            if (list.size() == 1) {
                //单笔交易
                MidBusProc midBusProc = list.get(0);
                FilInterest filInterest = filInterestService.get(Integer.valueOf(midBusProc.getFilPid()));
                TraInterest traInterest = new TraInterest();
                BeanUtil.copyProperties(filInterest, traInterest);
//                TraInterest traInterest = traInterestService.get(Integer.valueOf(flagPid));
                model.addObject("traInterest", traInterest);
                model.setViewName("ex/traInterestApprove");
            } else {
                //集中审议
                StringBuilder sb = new StringBuilder("");
                for (MidBusProc midBusProc : list) {
                    sb.append(midBusProc.getFilPid()).append(",");
                }
                model.addObject("pids", sb.toString());
                model.setViewName("ex/filTraInterestBatchApprove");
            }
        } else if ("5".equals(substring)) {
            if (list.size() == 1) {
                //单笔交易
                MidBusProc midBusProc = list.get(0);
                FilBusiness filBusiness = filBusinessService.get(Integer.valueOf(midBusProc.getFilPid()));
                TraService traService = new TraService();
                BeanUtil.copyProperties(filBusiness, traService);
//                TraService traService = traServiceService.get(Integer.valueOf(flagPid));
                model.addObject("traService", traService);
                model.setViewName("ex/traServiceApprove");
            } else {
                //集中审议
                StringBuilder sb = new StringBuilder("");
                for (MidBusProc midBusProc : list) {
                    sb.append(midBusProc.getFilPid()).append(",");
                }
                model.addObject("pids", sb.toString());
                model.setViewName("ex/filTraServiceBatchApprove");
            }
        } else if ("6".equals(substring)) {
            if (list.size() == 1) {
                //单笔交易
                MidBusProc midBusProc = list.get(0);
                FilOther filOther = filOtherService.get(Integer.valueOf(midBusProc.getFilPid()));
                TraOther traOther = new TraOther();
                BeanUtil.copyProperties(filOther, traOther);
//                TraOther traOther = traOtherService.get(Integer.valueOf(flagPid));
                model.addObject("traOther", traOther);
                model.setViewName("ex/traOtherApprove");
            } else {
                //集中审议
                StringBuilder sb = new StringBuilder("");
                for (MidBusProc midBusProc : list) {
                    sb.append(midBusProc.getFilPid()).append(",");
                }
                model.addObject("pids", sb.toString());
                model.setViewName("ex/filTraOtherBatchApprove");
            }
        }
        return model;
    }*/


    /**
     * @param procInsId 流程实例ID
     * @param procCode  流程编码ID
     * @return          视图对象
     */
    @RequestMapping("/queryFormData")
    @ResponseBody
    public ModelAndView queryFormData(String procInsId, String procCode, String fillNumber, ModelAndView model) {
        //根据流程编码判断业务流程
        //根据流程编码判断业务流程
        if ("zybr_relGeneral".equals(procCode)
                || "zybr_relGreat".equals(procCode)
                || "zyzbyjybrel".equals(procCode)
                || "zyzbyizdrel".equals(procCode)
                || "zyzbejybrel".equals(procCode)
                || "zyzbejzdrel".equals(procCode)) {
            //关联交易审批流程
            return processRelTran(procInsId, model, fillNumber);

        } else if ("ZZ_Pro001".equals(procCode)) {
            //关联关系审批流程
            List<Filter> filter = new Filter.Builder().eq("procInstId", procInsId).build();
            List<RelPro> list = proService.select(filter);
            if (list.size() == 1) {
                model.addObject("rel", list.get(0));
                model.setViewName("ex/proForm");
            } else {
                //多条审批
                StringBuilder sb = new StringBuilder("");
                for (RelPro r : list) {
                    sb.append(r.getPid()).append(",");
                }
                model.addObject("pids", sb.toString());
                model.setViewName("ex/filBatchProForm");
            }
        } else if ("ZZ_Org001".equals(procCode)) {

            //关联关系审批流程
            List<Filter> filter = new Filter.Builder().eq("procInstId", procInsId).build();
            List<RootOrgInfo> list = rootOrgInfoService.select(filter);
            model.addObject("rootOrgInfo", list.get(0));
            model.setViewName("ex/rootOrgInfoForm");
        } else if ("zyRel003".equals(procCode)) {
            List<Filter> filter = new Filter.Builder().eq("procInstId", procInsId).build();
            List<Rel> list = relService.select(filter);
            //关联关系审批流程
            if (list.size() == 1) {
                model.addObject("rel", list.get(0));
                model.setViewName("ex/relForm");
            } else {
                //多条审批
                StringBuilder sb = new StringBuilder("");
                for (Rel rel : list) {
                    sb.append(rel.getPid()).append(",");
                }
                model.addObject("pids", sb.toString());
                model.setViewName("ex/filRelTableForm");
            }
        } else if ("ZZ_AuthApproval".equals(procCode)) {
            List<Filter> filter = new Filter.Builder().eq("procInstId", procInsId).build();
            List<AuthApply> list = authApplyService.select(filter);
            //权限审批流程
            //单笔
            model.addObject("authApply", list.get(0));
            model.addObject("opt", "view");
            //不展示内容
            model.addObject("op", 1);
            model.setViewName("ex/qxForm1");
        } else if ("ZZ_taskDelegation".equals(procCode)) {
            List<Filter> filter = new Filter.Builder().eq("procInstId", procInsId).build();
            List<EntrustApply> list = entrustApplyService.select(filter);
            //任务委托申请
            //单笔
            EntrustApply entrustApply = list.get(0);
            if ("0".equals(entrustApply.getEntrustType())) {
                entrustApply.setEntrustType("新增委托");
            } else {
                entrustApply.setEntrustType("变更委托");
            }
            model.addObject("entrustApply", entrustApply);
            model.addObject("opt", "view");
            //不展示内容
            model.addObject("op", 1);
            model.setViewName("ex/entrustForm");
        }
        return model;
    }


    /**
     * @param model      视图对象
     * @param fillNumber 编号
     * @return           视图对象
     */
    private ModelAndView processRelTran(String procInsId, ModelAndView model, String fillNumber) {
        String substring = fillNumber.substring(0, 1);
        List<Filter> filter = new Filter.Builder().eq("procInstId", procInsId).build();
        switch (substring) {
            case "1": {
                List<TraInvestment> list = traInvestmentService.select(filter);
                if (list.size() == 1) {
                    model.addObject("traInvestment", list.get(0));
                    model.setViewName("ex/traInvestmentApprove");
                } else {
                    //集中审议
                    StringBuilder sb = new StringBuilder("");
                    for (TraInvestment traInvestment : list) {
                        sb.append(traInvestment.getPid()).append(",");
                    }
                    model.addObject("pids", sb.toString());
                    model.setViewName("ex/filTraInvestmentBatchApprove");
                }
                break;
            }
            case "2": {
                List<TraFunds> list = traFundsService.select(filter);
                if (list.size() == 1) {
                    //单笔交易
                    model.addObject("traFunds", list.get(0));
                    model.setViewName("ex/traFundsSubmit");
                } else {
                    //集中审议
                    StringBuilder sb = new StringBuilder("");
                    for (TraFunds traFunds : list) {
                        sb.append(traFunds.getPid()).append(",");
                    }
                    model.addObject("pids", sb.toString());
                    model.setViewName("ex/filTraFundsBatchSubmit");
                }
                break;
            }
            case "3": {
                List<TraBusiness> list = traBusinessService.select(filter);
                if (list.size() == 1) {
                    //单笔交易
                    model.addObject("traBusiness", list.get(0));
                    model.setViewName("ex/traBusinessForm");
                } else {
                    //集中审议
                    StringBuilder sb = new StringBuilder("");
                    for (TraBusiness traBusiness : list) {
                        sb.append(traBusiness.getPid()).append(",");
                    }
                    model.addObject("pids", sb.toString());
                    model.setViewName("ex/filTraFundsBatchSubmit");
                }

                break;
            }
            case "4": {
                List<TraInterest> list = traInterestService.select(filter);
                if (list.size() == 1) {
                    //单笔交易
                    model.addObject("traInterest", list.get(0));
                    model.setViewName("ex/traInterestApprove");
                } else {
                    //集中审议
                    StringBuilder sb = new StringBuilder("");
                    for (TraInterest traInterest : list) {
                        sb.append(traInterest.getPid()).append(",");
                    }
                    model.addObject("pids", sb.toString());
                    model.setViewName("ex/filTraInterestBatchApprove");
                }
                break;
            }
            case "5": {
                List<TraService> list = traServiceService.select(filter);
                if (list.size() == 1) {
                    //单笔交易
                    model.addObject("traService", list.get(0));
                    model.setViewName("ex/traServiceApprove");
                } else {
                    //集中审议
                    StringBuilder sb = new StringBuilder("");
                    for (TraService traService : list) {
                        sb.append(traService.getPid()).append(",");
                    }
                    model.addObject("pids", sb.toString());
                    model.setViewName("ex/filTraServiceBatchApprove");
                }
                break;
            }
            case "6": {
                List<TraOther> list = traOtherService.select(filter);
                if (list.size() == 1) {
                    //单笔交易
                    model.addObject("traOther", list.get(0));
                    model.setViewName("ex/traOtherApprove");
                } else {
                    //集中审议
                    StringBuilder sb = new StringBuilder("");
                    for (TraOther traOther : list) {
                        sb.append(traOther.getPid()).append(",");
                    }
                    model.addObject("pids", sb.toString());
                    model.setViewName("ex/filTraOtherBatchApprove");
                }
                break;
            }
            default:
        }
        return model;
    }

    @RequestMapping("/hasData")
    @ResponseBody
    public Map<String, Object> hasData(String procInsId, String procCode, String fillNumber) {
        Map<String, Object> result = new HashMap<>();
        boolean flag;
        boolean fundsFlag;
        boolean serverFlag;
        boolean interestFlag;
        boolean businessFlag;
        boolean investmentFlag;
        boolean otherFlag;

        List<Filter> filter = new Filter.Builder().eq("procInstId", procInsId).build();
        if ("ZZ_Pro001".equals(procCode)) {
            flag = proService.exists(filter);
        } else if ("ZZ_Org001".equals(procCode)) {
            flag = rootOrgInfoService.exists(filter);
        } else if ("zyRel003".equals(procCode)) {
            flag = relService.exists(filter);
        } else if ("ZZ_AuthApproval".equals(procCode)) {
            flag = authApplyService.exists(filter);
        } else if ("ZZ_taskDelegation".equals(procCode)) {
            flag = entrustApplyService.exists(filter);
        } else {
            fundsFlag = traFundsService.exists(filter);
            serverFlag = traServiceService.exists(filter);
            interestFlag = traInterestService.exists(filter);
            businessFlag = traBusinessService.exists(filter);
            investmentFlag = traInvestmentService.exists(filter);
            otherFlag = traOtherService.exists(filter);
            flag = fundsFlag || serverFlag || interestFlag || businessFlag || investmentFlag || otherFlag;
        }
        if (flag) {
            result.put(WebCode.SUCCESS, true);
        } else {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "暂无备份");
        }
        return result;
    }

    //身份证前六后四脱敏
    public static String idEncrypt(String id) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(id) || (id.length() < 10)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{6})\\w(?=\\w{4})", "*");
    }
    //自然人非身份证脱敏
    public static String otherIdEncrypt(String id) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{2})", "*");
    }

}
