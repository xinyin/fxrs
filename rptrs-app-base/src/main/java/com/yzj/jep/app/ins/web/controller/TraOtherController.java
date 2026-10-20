/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月08日 19:41:07
 *
 */

package com.yzj.jep.app.ins.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.TraEnclosureVo;
import com.yzj.jep.app.enums.CompanyType;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.Constants;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.app.util.InsUtil;
import com.yzj.jep.app.wf.service.WorkflowOperation;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.NotifyService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;

/**
 * 关联交易其他类
 */

@Controller
@RequestMapping("/ins/traOther")
public class TraOtherController extends CrudController<TraOther> {
    private static Logger logger = LoggerFactory.getLogger(TraOtherController.class);

    @Autowired
    private TraOtherService traOtherService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private TraInsenclosureService traInsenclosureService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private RuleCheckService ruleCheckService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private ApproveAssignerService approveAssignerService;
    @Autowired
    private TraCounterpartyService traCounterpartyService;

    @Autowired
    WorkflowOperation workflowOperation;



    @ModelAttribute
    public void defaultMethod(Model model) {
        model.addAttribute("flag", "flag");
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "其他类", description = "查询列表")
    public String list(Model model) {
        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "ins/traOther/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        //查询登陆人机构及下辖机构的信息
      /*  UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
            //管户机构
            model.addAttribute("unitCode", unitCode);
            model.addAttribute("opt", "MH");*/
        return "ins/traOtherList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraOther> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(new Order("modifiedTime", OrderType.DESC));
        filters.add(new Filter("delflag", "1"));
        //查询登陆人机构及下辖机构的信息
        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
        String unitCode = profile.getUnitCode();
        List<String> codes = unitService.selectUnitAllOrDeptAllCodes(profile.getUnitCode());
        List<String> mgerOrgs = codes.stream().distinct().collect(Collectors.toList());
        /*AuthorityUtil authorityUtil = new AuthorityUtil();
        filters = authorityUtil.getFilterByRole(filters);*/
        Commons.common(request, filters);
        //处理交易截止日期modifiedTime时间，往后加一天
        List<Filter> handleFilters = new ArrayList<>();
        for (int i = 0; i < filters.size(); i++) {
            if (filters.get(i).getProperty().equals("startTranDate")) {
                if (filters.get(i).getOperator().name().equals("GE")) {
                    Date startTranDate = DateUtils.parseDate(filters.get(i).getValue().toString());
                    String format = DateUtils.format(startTranDate, "yyyy-MM-dd");
                    handleFilters.add(new Filter("contractDate", Operator.GE, format));
                    filters.remove(i);
                }
            }

            if (filters.get(i).getProperty().equals("endTranDate")) {
                if (filters.get(i).getOperator().name().equals("LE")) {
                    Date endTranDate = DateUtils.parseDate(filters.get(i).getValue().toString());
                    String format = DateUtils.format(endTranDate, "yyyy-MM-dd");
                    handleFilters.add(new Filter("contractDate", Operator.LE, format));
                    filters.remove(i);
                }
            }


        }
        //filters.add(new Filter("tradeOrg", Operator.IN, mgerOrgs));
        handleFilters.addAll(filters);
        //filters.add(new Filter("tradeOrg", Operator.IN, mgerOrgs));
        //防止有缓存
        Constants.conditions.clear();
        Constants.conditions.put("filters", handleFilters);
        traOtherService.query(page, handleFilters, orders);
        List<TraOther> list = page.getResult();
        list.stream().forEach( e -> {
            e.setRelNumber(idEncrypt(e.getRelNumber()));
        });
        page.setResult(list);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {
        //生成交易编号
        TraOther traOther = new TraOther();
        traOther.setTransNum(BatchNumberUtil.makeBatchNumber());
/*        //默认国家定价现金交易为否
        traOther.setIsDealPrice(Dict.SF_0);
        traOther.setIsCashSubscribe(Dict.SF_0);*/
        traOther.setTradeAmount(new BigDecimal(0));
        traOther.setRelInfoListStr("[]");
        traOther.setIsHoldingSubsidiary(Dict.SF_0);
        //添加交易主体名称
        traOther.setTraSubName(Commons.getTenantName());
        model.addAttribute("traOther", traOther);
       /* String unitCode = UserProfileUtils.getUserProfile().getUnitCode();
        Unit unit=unitService.get(unitCode);
        model.addAttribute("unitName", unit.getUnitName());
        model.addAttribute("unitCode", unit.getUnitCode());*/
        return "ins/traOtherEdit";
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
        TraOther traOther = traOtherService.get(pid);
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traOther.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traOther && counterpartyList != null && counterpartyList.size() > 0) {
            traOther.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        model.addAttribute("traOther", traOther);
        model.addAttribute("opt", "edit");

        return "ins/traOtherEdit";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model, HttpServletRequest request) {
        Commons.common2(request, model);
        TraOther traOther = traOtherService.get(pid);
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traOther.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traOther && counterpartyList != null && counterpartyList.size() > 0) {
            traOther.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        model.addAttribute("traOther", traOther);
        model.addAttribute("opt", "view");
        //待查阅数据
        if ("9".equals(traOther.getStatus())) {
            model.addAttribute("isConfirm", true);
            confirm(pid);
        }
        return "ins/traOtherEdit";
    }

    /**
     * 保存
     *
     * @param traOther
     * @return
     */
    @RequestMapping(value = "/saveSign")
    @ResponseBody
    public Map<String, Object> save1(TraOther traOther, TraEnclosureVo traEnclosureVo) {

        Map<String, Object> tips = new HashMap<String, Object>();
        //校验是否已经存在该笔交易
        TraOther existTraOthers = traOtherService.get(traOther.getPid());
        UserProfile profile = UserProfileUtils.getUserProfile();
        //交易对手信息
        String relInfoStr = traOther.getRelInfoListStr();
        JSONArray relArray = JSONArray.parseArray(relInfoStr);
        List<TraRelInfo> relList = relArray.toJavaList(TraRelInfo.class);
        if (relList.size() == 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请添加交易对手信息");
            return tips;
        }

        try {
            //交易流水号
            String transNum = "";
            traOther.setDelflag("1");
            traOther.setTranType(Dict.TRAN_TYPE_06);
            traOther.setDataSources("1");//手工录入
            traOther.setTradeOrg(profile.getUnitCode());
            traOther.setAmendId(profile.getStaffCode());
            traOther.setAmendName(profile.getStaffName());
            traOther.setRelInfoList(relList);
            traOther.setTranDate(traOther.getContractDate());
            //判断交易信息已存在就更新，不存在就新建
            if (existTraOthers != null) {
                //待确认状态直接修改为已归档
                if ("8".equals(traOther.getStatus())) {
                    //修改为已归档
                    TraOther traOtherForUpdate = new TraOther();
                    traOtherForUpdate.setPid(traOther.getPid());
                    if(traOther.getPracTradeAmount() != null) {
                        traOtherForUpdate.setStatus("6");
                        traOtherForUpdate.setContractDate(traOther.getContractDate());
                    }else{
                        traOtherForUpdate.setStatus("7");
                        traOtherForUpdate.setContractDate(traOther.getContractDate());
                    }
                    traOtherService.updateSelective(traOtherForUpdate);
                    //交易流水号
                    transNum = existTraOthers.getTransNum();
                    //把所有交易对手状态都改为已归档
                    List<Filter> filterList = new ArrayList<>();
                    filterList.add(new Filter("transNum", transNum));
                    TraCounterparty traCounterparty = new TraCounterparty();
                    if(traOther.getPracTradeAmount() != null) {
                        traCounterparty.setStatus("6");
                        traCounterparty.setTranDate(traOther.getContractDate());
                    }else {
                        traCounterparty.setStatus("7");
                        traCounterparty.setTranDate(traOther.getContractDate());
                    }
                    traCounterpartyService.update(traCounterparty, new String[]{"status"}, filterList);
                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, "签定成功");
                    return tips;
                }
                //校验审议类型
                checkConsiderType(traOther);
                //处理重大交易情况
                checkMajorTrade(traOther, relList);

                TraRelInfo r = relList.get(0);
                traOther.setRelId(r.getRelId());
                traOther.setRelType(r.getRelType());
                traOther.setRelName(r.getRelName());
                traOther.setRelNumber(r.getRelNumber());
                traOther.setTranOpponent(r.getTranOpponent());
                traOther.setCertType(r.getCertType());
                traOther.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traOther.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traOtherService.updateSelective(traOther);
                transNum = traOtherService.get(traOther.getPid()).getTransNum();
                //更新关联交易-交易对手信息表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traOther.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traOther.getPracTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traOther.getStatus());
                    traCounterparty.setTranDate(traOther.getContractDate());
                    traCounterparty.setTraSubName(traOther.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());

                    traCounterpartyService.updateSelective(traCounterparty);
                }

            } else {
                //校验审议类型
                checkConsiderType(traOther);
                //处理重大交易情况
                checkMajorTrade(traOther, relList);
                transNum = traOther.getTransNum();
                traOther.setModifiedTime(new Date());
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traOther.setRelId(r.getRelId());
                traOther.setRelType(r.getRelType());
                traOther.setRelName(r.getRelName());
                traOther.setRelNumber(r.getRelNumber());
                traOther.setTranOpponent(r.getTranOpponent());
                traOther.setCertType(r.getCertType());
                traOther.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traOther.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traOtherService.insertSelective(traOther);
                //保存交易对手到新表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traOther.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traOther.getPracTradeAmount());
                    traCounterparty.setTradeAmount(tra.getTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traOther.getStatus());
                    traCounterparty.setTranDate(traOther.getContractDate());
                    traCounterparty.setTraSubName(traOther.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());
                    traCounterpartyService.insertSelective(traCounterparty);
                }
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(TraOther traOther, TraEnclosureVo traEnclosureVo) {

        Map<String, Object> tips = new HashMap<String, Object>();
        //校验是否已经存在该笔交易
        TraOther existTraOthers = traOtherService.get(traOther.getPid());
        UserProfile profile = UserProfileUtils.getUserProfile();
        //交易对手信息
        String relInfoStr = traOther.getRelInfoListStr();
        JSONArray relArray = JSONArray.parseArray(relInfoStr);
        List<TraRelInfo> relList = relArray.toJavaList(TraRelInfo.class);
        if (relList.size() == 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请添加交易对手信息");
            return tips;
        }

        try {
            //交易流水号
            String transNum = "";
            traOther.setDelflag("1");
            traOther.setTranType(Dict.TRAN_TYPE_06);
            traOther.setDataSources("1");//手工录入
            traOther.setTradeOrg(profile.getUnitCode());
            traOther.setAmendId(profile.getStaffCode());
            traOther.setAmendName(profile.getStaffName());
            traOther.setRelInfoList(relList);
            traOther.setTranDate(traOther.getContractDate());
            //判断交易信息已存在就更新，不存在就新建
            if (existTraOthers != null) {
                //待确认状态直接修改为已归档
                if (Dict.APPLY_STATUS_2.equals(traOther.getStatus())) {
                    TraOther traOtherForUpdate = new TraOther();
                    //判断实际交易金额是否为空
                    if(null != traOtherForUpdate.getPracTradeAmount() && null != traOtherForUpdate.getTranDate()){
                        traOtherForUpdate.setStatus("6");
                    }else if(null == traOtherForUpdate.getTranDate()){
                        traOtherForUpdate.setStatus("8");
                    }else if(null != traOtherForUpdate.getTranDate() && null == traOtherForUpdate.getPracTradeAmount()){
                        traOtherForUpdate.setStatus("7");
                    }
                    traOtherForUpdate.setPid(traOther.getPid());
                    traOtherService.updateSelective(traOtherForUpdate);
                    //交易流水号
                    transNum = existTraOthers.getTransNum();
                    //把所有交易对手状态都改为已归档
                    List<Filter> filterList = new ArrayList<>();
                    filterList.add(new Filter("transNum", transNum));
                    TraCounterparty traCounterparty = new TraCounterparty();
                    traCounterparty.setStatus("8");
                    traCounterpartyService.update(traCounterparty, new String[]{"status"}, filterList);
                    //更新文件信息
                    List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
                    traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, "确认成功");
                    return tips;
                }
                //校验审议类型
                checkConsiderType(traOther);
                //处理重大交易情况
                checkMajorTrade(traOther, relList);

                TraRelInfo r = relList.get(0);
                traOther.setRelId(r.getRelId());
                traOther.setRelType(r.getRelType());
                traOther.setRelName(r.getRelName());
                traOther.setRelNumber(r.getRelNumber());
                traOther.setTranOpponent(r.getTranOpponent());
                traOther.setCertType(r.getCertType());
                traOther.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traOther.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                //更新成操作人(针对第三方数据)
                traOther.setAmendId(profile.getStaffCode());
                traOther.setAmendName(profile.getStaffName());
/*                traOther.setCreateById(profile.getStaffCode());
                traOther.setCreateByName(profile.getStaffName());*/
                traOtherService.updateSelective(traOther);
                transNum = traOtherService.get(traOther.getPid()).getTransNum();
                //更新关联交易-交易对手信息表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traOther.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traOther.getPracTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traOther.getStatus());
                    traCounterparty.setTranDate(traOther.getContractDate());
                    traCounterparty.setTraSubName(traOther.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());

                    traCounterpartyService.updateSelective(traCounterparty);
                }

            } else {
                //校验审议类型
                checkConsiderType(traOther);
                //处理重大交易情况
                checkMajorTrade(traOther, relList);
                transNum = traOther.getTransNum();
                traOther.setModifiedTime(new Date());
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traOther.setRelId(r.getRelId());
                traOther.setRelType(r.getRelType());
                traOther.setRelName(r.getRelName());
                traOther.setRelNumber(r.getRelNumber());
                traOther.setTranOpponent(r.getTranOpponent());
                traOther.setCertType(r.getCertType());
                traOther.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traOther.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traOtherService.insertSelective(traOther);
                //保存交易对手到新表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traOther.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traOther.getPracTradeAmount());
                    traCounterparty.setTradeAmount(tra.getTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traOther.getStatus());
                    traCounterparty.setTranDate(traOther.getContractDate());
                    traCounterparty.setTraSubName(traOther.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());
                    traCounterpartyService.insertSelective(traCounterparty);
                }
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }



    @RequestMapping(value = "/sendNotice")
    @ResponseBody
    public Map<String, Object> sendNotice(String traSubName, String relName, String
            tranType, String contractNo, String contractAmount) {
        Map<String, Object> message = new HashMap<String, Object>();
        try {
            //发送通知
            InsUtil.sendNotice(traSubName, relName, tranType,
                    contractNo, contractAmount);
            message.put(WebCode.SUCCESS, true);
            message.put(WebCode.MSG, "发送通知邮件成功");
        } catch (Exception e) {
            message.put(WebCode.SUCCESS, false);
            message.put(WebCode.MSG, "发送邮件失败");
        }
        return message;
    }

    /**
     * 查阅页面跳转
     */
    @RequestMapping(value = "/toConfirm/{pid}")
    public String toConfirm(@PathVariable Integer pid, Model model) {
        TraOther traOther = traOtherService.get(pid);
        //确认归档后设置状态为以归档
//        traOther.setStatus("6");
//        traOtherService.updateSelective(traOther);
        model.addAttribute("traOther", traOther);
        model.addAttribute("opt", "view");
        model.addAttribute("file", "file");
        model.addAttribute("isConfirm", true);
        return "ins/traOtherEdit";
    }


    @RequestMapping(value = "/sign/{pid}")
    public String toConfirm1(@PathVariable Integer pid, Model model) {
        TraOther traOther = traOtherService.get(pid);
        //确认归档后设置状态为以归档
//        traOther.setStatus("6");
//        traOtherService.updateSelective(traOther);
        model.addAttribute("traOther", traOther);
        model.addAttribute("opt", "view");
        model.addAttribute("isConfirm", true);
        return "ins/traOtherSign";
    }



    /**
     * 确认管户机构数据
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/confirm/{pid}")
    @ResponseBody
    public Map<String, Object> confirm(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            TraOther traOther = traOtherService.get(pid);
            traOther.setStatus("9");
            traOtherService.updateSelective(traOther);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
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
            TraOther traOther = traOtherService.get(pid);
            traOther.setDelflag("0");
            traOtherService.updateSelective(traOther);
            //同时把交易对手表数据状态改为删除
            TraCounterparty tra = new TraCounterparty();
            tra.setTransNum(traOther.getTransNum());
            tra.setStatus("0");
            traCounterpartyService.updataCounterpartyStatusByTransNum(tra);
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

        boolean exist = traOtherService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 选择下一节点审批人
     */
    @RequestMapping(value = "/Approved")
    public String ApprovedPsn(Model model, Boolean mult) {
        model.addAttribute("mult", false);
        return "ins/ApprovedPsn";
    }

    /**
     * 任务下发
     */
    @RequestMapping(value = "/sendCommand")
    public String relPsnSendCommand(Model model) {

        return "ins/traSend";
    }

    /**
     * 确认下发
     */
    @RequestMapping(value = "/taskSend")
    @ResponseBody
    public Map taskSend(String unitCode, String memo, String sendId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //发放疑似自然人给指定机构补录
        List<Filter> filers = new Filter.Builder().eq("pid", sendId).build();
        TraOther traOther = traOtherService.get(filers);
        //更换管库机构为下发机构
        traOther.setTradeOrg(unitCode);
        //更新疑似自然人状态为待补录
        traOther.setStatus("2");
        traOther.setStatusComment(memo);
        try {
            //查出指定机构角色为业务岗的人并通知补录
            List<Duty> dutys = dutyService.select(new Filter.Builder()
                    .like("positionCode", "120@" + unitCode)
                    .like("dutySource", DutySource.ROLE.toString())
                    .build());
            if (CollectionUtils.isNotEmpty(dutys)) {
                for (Duty duty : dutys) {
                    //通知补录人
                    /*创建提醒*/
                    notifyService.createRemind(duty.getStaffCode(), "其他类[" + traOther.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到关联交易其他类列表完成补录。", "其他类交易[" + traOther.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到关联交易其他类列表完成补录。");
                }
                traOtherService.update(traOther);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_TASKSEND);
            } else {
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, "该机构未设业务岗");
            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

//    /*
//     * 提交审批
//     * */
//    @RequestMapping(value = "/send/{pid}")
//    @ResponseBody
//    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
//        Map<String, Object> tips = new HashMap<String, Object>();
//        TraOther traOther = traOtherService.get(pid);
//        if ("3".equals(traOther.getStatus()) || "5".equals(traOther.getStatus())) {
//            //4 代表审批中
//            TraOther traOtherUpdate = new TraOther();
//            traOtherUpdate.setPid(pid);
//            traOtherUpdate.setStatus("4");
//            traOtherService.updateSelective(traOtherUpdate);
//            traOther.setStatus("4");
//            String relInfoListStr = traOther.getRelInfoListStr();
//            if (StrUtil.isNotBlank(relInfoListStr)) {
//                List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
//                traOther.setRelInfoList(traRelInfos);
//                traOther.setRelInfoListStr(null);
//            }
//            UserProfile profile = UserProfileUtils.getUserProfile();
//            String unitCode = profile.getUnitCode();
//            String deptCode = profile.getDeptCode();
//            //保存发起人的机构信息
//            tips.put("unitCode", unitCode);
//            tips.put("deptCode", deptCode);
//            tips.put("data", traOther);
//            String deptName = profile.getDeptName();
//            tips.put("deptName", deptName);
//            //获取对应总部门的部门编号
//            List<String> hdDeptCodeList = approveAssignerService.queryDeptCodeByDeptName(deptName, "8600");
//            tips.put("hdDeptCode", CollectionUtil.isNotEmpty(hdDeptCodeList) ? hdDeptCodeList.get(0) : null);
//            //是否是二级部门
//            Boolean isSecond = approveAssignerService.isHdSecondDept(deptCode);
//            tips.put("isSecond", isSecond);
//            tips.put(WebCode.SUCCESS, true);
//            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
//        } else {
//            tips.put(WebCode.SUCCESS, false);
//            tips.put(WebCode.MSG, "状态不符，提交失败！");
//        }
//        return tips;
//    }

    /**
     * 审批页面跳转
     */
    @RequestMapping(value = "/submit/{pid}")
    public String submit(@PathVariable Integer pid, Model model) {
        TraOther traOther = traOtherService.get(pid);
        model.addAttribute("traOther", traOther);
        model.addAttribute("opt", "view");
        return "ins/traOtherApprove";
    }

    /**
     * 判断是否重大关联交易
     * <p>
     * <p>
     * 关联交易发起部门录入关联交易相关信息后，系统根据模型对比该笔交易金额和重大关联交易
     * 金额限制，自动识别出为一般关联交易还是重大关联交易。随后流转至秘书处法律合规专岗，由专
     * 岗人员对关联交易情况做基本了解后，引导发起部门继续后续审批流程。
     * 集团的关联交易分为重大关联交易和一般关联交易。
     * 重大关联交易是指集团或其控股子公司与一个关联方之间单笔或年度累计交易金额达到 3000
     * 万元以上，且占集团上一年度末经审计的净资产的 1%以上的交易；保险业务类和提供货物、服务
     * 或财务资助，可签署统一交易协议，统一交易协议应按照重大关联交易进行内部管控。
     * 一个年度内集团或其控股子公司与一个关联方的累计交易金额达到前款标准后，其后发生的关
     * 联交易，如再次累计达到前款标准，则应当重新认定为重大关联交易。
     * 集团与多个关联方在同一笔交易中的金额，应合并计算进行认定。
     * 一般关联交易是指除重大关联交易以外的其他关联交易。
     *
     * @param
     * @return
     */

    @RequestMapping(value = "/GreatCheck")
    @ResponseBody
    public Map<String, Object> isGreatCheck(String relTradeAmount, String pracTradeAmount, String relInfoListStr) {
        HashMap<String, Object> result = Maps.newHashMap();
        //调用规则service
        Map ruleResult = null;
        String isGreate = "0";
        String majorType = "0";//0标识不重大
        String notMajorRelNumber ="";
        try {
            BigDecimal bigRelTradeAmount = com.yzj.jep.commons.StringUtils.isEmpty(relTradeAmount) ? null : new BigDecimal(relTradeAmount).setScale(6, BigDecimal.ROUND_HALF_UP);
            ruleResult = InsUtil.GreatCheckNew(Dict.TRAN_TYPE_06, bigRelTradeAmount, relInfoListStr);

            if (null != ruleResult) {
                InsTrans insTrans = (InsTrans) ruleResult.get("insTrans");
                if (StringUtils.isNotEmpty(insTrans.getMajorTrade())) {
                    isGreate = insTrans.getMajorTrade();
                }
                if (StringUtils.isNotEmpty(insTrans.getMajorType())) {
                    majorType = insTrans.getMajorType();
                }
                if (ruleResult.get("notMajorRelNumber")!=null) {
                    notMajorRelNumber = (String) ruleResult.get("notMajorRelNumber");
                }
            }

            result.put("isGreatTrade", isGreate);
            result.put("majorType", majorType);
            result.put("notMajorRelNumber", notMajorRelNumber);
            result.put(WebCode.SUCCESS, true);
        } catch (Exception e) {
            logger.info("调用重大关联交易规则失败。。。 错误信息{}", e);
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "调用重大关联交易规则失败。。。");
        }
        return result;
    }

    @RequestMapping(value = "/traOtherCheck")
    @ResponseBody
    public Map<String, Object> tradeCheck(TraOther obj) {
        Map<String, Object> result = new HashMap();
//		StringBuffer sb_msg = new StringBuffer();
//		StringBuffer sb_msg2 = new StringBuffer();
//		StringBuffer sb_code = new StringBuffer();
//		Map params = InsUtil.initRuleData(obj.getRelNumber(), obj.getRelId() + "", obj.getRelType(), obj.getTradeAmount() + "", "06", null, obj.getBusType());
//		List<Map<String, String>> ruleResult = ruleCheckService.ComPlianceCheck("06", params);
//		if (ruleResult != null && ruleResult.size() > 0) {
//			if (ruleResult.size() == 1 && "999999".equals(ruleResult.get(0).get("code"))) { //规则执行出错， 不影响流程
//				result.put(WebCode.SUCCESS, true);
//			} else if (ruleResult.size() == 1 && "C910".equals(ruleResult.get(0).get("code"))) {
//				result.put(WebCode.SUCCESS, true);
//				result.put("msg", ruleResult.get(0).get("msg"));
//			} else {
//				for (int i = 0; i < ruleResult.size(); i++) {
//					if (org.apache.commons.lang.StringUtils.isNotBlank(ruleResult.get(i).get("code")) && org.apache.commons.lang.StringUtils.isNotBlank(ruleResult.get(i).get("msg"))) {
//						String code = ruleResult.get(i).get("code");
//						String msg = ruleResult.get(i).get("msg");
//						sb_code.append(code + "|");
//						sb_msg2.append(msg + "|");
//						if (i < ruleResult.size() - 1) {
//							sb_msg.append("[" + msg + "]，");
//						} else {
//							sb_msg.append("[" + msg + "]");
//						}
//					}
//				}
//				result.put(WebCode.SUCCESS, false);
//				result.put("code", sb_msg2.toString());
//				result.put("msg", "合规检查异常：【" + sb_msg.toString() + "】，您是否继续提交保存？");
//			}
//		} else {
        result.put(WebCode.SUCCESS, true);
//		}
        //        if(null != ruleResult){ //合规校验结果
//            String code = ruleResult.get("code");
//            String msg = ruleResult.get("msg");
//            if(StringUtils.isNotBlank(code)){//合规校验错误码不为空 出发合规校验规则，返回前端提示语
//                result.put(WebCode.SUCCESS, false);
//                result.put(WebCode.MSG,"合规校验失败：【"+msg+"】，确定要保存吗？");
//                return result;
//            }
//        }
        return result;
    }

    @RequestMapping("/quotaCheck")
    @ResponseBody
    public Map<String, Object> quotaCheck(TraService traService) {
//		String businessType = traService.getBusType();
//		BigDecimal tradeAmount = traService.getTradeAmount();
        Map<String, Object> tips = Maps.newHashMap();
//		Map<String, Object> quotaParam = InsUtil.initQuotaParam("05", businessType, tradeAmount);
//		if (quotaParam != null) {
//			Map<String, String> resultMap = ruleCheckService.quotaCheck(quotaParam);
//			if (resultMap != null) {
//				tips.put(WebCode.SUCCESS, false);
//				tips.put(WebCode.MSG, resultMap.get("msg"));
//				return tips;
//			}
//		}
        tips.put(WebCode.SUCCESS, true);
        return tips;
    }

    /**
     * 跳转到集中审议列表页面
     *
     * @return
     */
    @RequestMapping("/batchSendList")
    public String batchSendList() {
        return "ins/traOtherBatchList";
    }

    /**
     * 查询集中审议符合条件的数据
     *
     * @param pageNumber
     * @param pageSize
     * @param year
     * @param quarter
     * @return
     */
    @RequestMapping("/queryBatchSubmit")
    @ResponseBody
    public Map<String, Object> queryBatchSubmit(Integer pageNumber, Integer pageSize, String year, String quarter) {
        Map<String, Object> result = new HashMap<>();
        PageInfo<TraOther> pageInfo = traOtherService.queryBatchSubmit(pageNumber, pageSize, year, quarter);
        result.put("total", pageInfo.getTotal());
        result.put("rows", pageInfo.getList());
        return result;
    }

    @RequestMapping("/queryPidsByConditions/{pids}")
    @ResponseBody
    public Map<String, Object> queryPidsByConditions(Page<TraOther> page, @PathVariable List<Integer> pids) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        traOtherService.query(page, filters);
        result.put("total", page.getTotal());
        result.put("rows", page.getResult());
        return result;
    }

    /**
     * 查询集中审议符合条件的数据
     *
     * @param year
     * @param quarter
     * @return
     */
    @RequestMapping("/batchConditionSubmit")
    @ResponseBody
    public Map<String, Object> batchConditionSubmit(String year, String quarter) {
        Map<String, Object> result = new HashMap<>();
        PageInfo<TraOther> pageInfo = traOtherService.queryBatchSubmit(1, Integer.MAX_VALUE, year, quarter);
        List<TraOther> list = pageInfo.getList();
        if (CollectionUtil.isEmpty(list)) {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "无数据");
            return result;
        }
        //修改审批状态
        List<String> pids = new ArrayList<>();
        for (TraOther traother : list) {
            TraOther other = new TraOther();
            Integer pid = traother.getPid();
            other.setPid(pid);
            other.setStatus("4");
            traOtherService.updateSelective(other);
            pids.add(String.valueOf(pid));
            String relInfoListStr = traother.getRelInfoListStr();
            if (StrUtil.isNotBlank(relInfoListStr)) {
                List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
                traother.setRelInfoList(traRelInfos);
                traother.setRelInfoListStr(null);
            }
        }
        Map<String, Object> formData = new HashMap<>();
        formData.put("pids", CollectionUtil.join(pids, ","));
        formData.put("data", list);
        formData.put("tranType", Dict.TRAN_TYPE_06);
        result.put("data", formData);
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        String deptCode = profile.getDeptCode();
        //保存发起人的机构信息
        result.put("unitCode", unitCode);
        result.put("deptCode", deptCode);
        String deptName = profile.getDeptName();
        result.put("deptName", deptName);
        //获取对应总部门的部门编号
        List<String> hdDeptCodeList = approveAssignerService.queryDeptCodeByDeptName(deptName, "8600");
        result.put("hdDeptCode", CollectionUtil.isNotEmpty(hdDeptCodeList) ? hdDeptCodeList.get(0) : null);
        //是否是二级部门
        Boolean isSecond = approveAssignerService.isHdSecondDept(deptCode);
        result.put("isSecond", isSecond);
        result.put(WebCode.SUCCESS, true);
        return result;
    }

    /*
     * 提交审批（集中）
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> sendjz(@PathVariable Integer pid, Model model) throws Exception {
        List<TraFunds> lli = new ArrayList<>();
        Map<String, Object> tips = new HashMap<String, Object>();
        /*List<String> li= Arrays.asList(pid.split(","));*/
        //获取当前登录用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        //String staffCode = profile.getStaffCode();
        String procInstId = null;

        StaffImpl staff = new StaffImpl();
        BeanUtils.copyProperties(profile, staff);

        TraOther other = traOtherService.get(pid);

        String relInfoListStr = other.getRelInfoListStr();
        if (StrUtil.isNotBlank(relInfoListStr)) {
            //设置关联人信息
            List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
            other.setRelInfoList(traRelInfos);
            //解析json时会报错，此处设置为空
            other.setRelInfoListStr(null);
        }
        com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSONObject.parseObject(JSONObject.toJSONString(other));
        if (org.apache.commons.lang3.StringUtils.isNotBlank(other.getProcInstId())) {
            procInstId = other.getProcInstId();
        }
        data.put("type", "other");
        data.put("TransactionTtype", "other");
        data.put("startStaffCode", staff.getStaffCode());
        data.put("tenantId", profile.getTenantId());
        String procDefCode = null;
        data.put("procDefCode", procDefCode);
            //判断员工所属机构
            Boolean StaffType = this.companyType(profile.getUnitCode());
            String considerType = other.getConsiderType();
            //分公司审批
            if (StaffType){
                if ("3".equals(considerType)){
                    // 分公司审批流 重大统一交易审批流
                    data.put("WorkflowType", "ZDTI_BranchOffice");
                    procDefCode = "FXBDX_ZDTY_BranchOffice";
                }else {
                    // 分公司审批流 一般交易审批流
                    data.put("WorkflowType", "YBGLJY_BranchOffice");
                    procDefCode = "FXBDX_YBGLJY_Company";
                }

            }else {
                // 总公司审批流 一般交易审批流
                if ("2".equals(considerType)) {
                    data.put("WorkflowType", "YBGLJY");
                    procDefCode = "FXBDX_YBGLJY";
                } else {
                    //总公司审批流 重大统一交易审批流
                    data.put("WorkflowType", "ZDTI");
                    procDefCode = "FXBDX_ZDTY";
                }
            }
        try {
            workflowOperation.sendSubmit(pid, procDefCode, staff, null, data.toJSONString(), "other", procInstId);
        } catch (Exception e) {
            logger.info("流程启动失败，失败数据类型:其他类---" + "pid" + pid, e);
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
            return tips;
        }


        tips.put("data", lli);
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }

    public Boolean companyType(String unitCode){
        Boolean type = false;
        if (unitCode.equals(CompanyType.CLIENT00.getCategory())){
            type=true;
        }else if (unitCode.equals(CompanyType.CLIENT01.getCategory())){
            type=true;
        }else if (unitCode.equals(CompanyType.CLIENT02.getCategory())){
            type=true;
        }else if (unitCode.equals(CompanyType.CLIENT03.getCategory())){
            type=true;
        }else if (unitCode.equals(CompanyType.CLIENT04.getCategory())){
            type=true;
        }else if (unitCode.equals(CompanyType.CLIENT05.getCategory())){
            type=true;
            //总公司
        }else if (unitCode.equals(CompanyType.YLINS.getCategory())){
            type=false;
        }
        return type;
    }


    /**
     * 校验审议类型，通过审议类型归类交易状态
     */
    private void  checkConsiderType(TraOther traOther) {
        Map<String, Object> map = new HashMap<>();
        //校验交易 审议状态，1免于审议， 2一般审议， 3逐笔审议
        boolean isEmpty = false;
        String considerType = traOther.getConsiderType();
        if (considerType.equals("3")) {
            if (
                    StringUtils.isEmpty(traOther.getBusType()) || //业务类型
                            StringUtils.isEmpty(traOther.getTradePrice()) || //交易价格
                            StringUtils.isEmpty(traOther.getTradeSubject()) || //交易标的
                            StringUtils.isEmpty(traOther.getPricingPolicy()) || //定价政策
                            StringUtils.isEmpty(traOther.getRelTradeAmount().toString()) ||  //关联交易金额
                            StringUtils.isEmpty(traOther.getContractNo()) ||
                            StringUtils.isEmpty(traOther.getTradeSubject()) || //交易标的
                            traOther.getBusStartTime() == null || //业务起始日期
                            StringUtils.isEmpty(traOther.getFinancialImpact()) //对财务状况及经营成果的影响
            ) {
                //所有类型都必填的，缺失即为待补录
                isEmpty = true;
            } else {
                //发起方是否为控股子公司
                if ("1".equals(traOther.getIsHoldingSubsidiary()) && (StringUtils.isEmpty(traOther.getHoldingSubsidiaryName()) ||
                        StringUtils.isEmpty(traOther.getHoldingSubsidiaryNumber()))) {
                    //待补录
                    isEmpty = true;
                }

            }
            if (isEmpty) {
                traOther.setStatus("1");
            } else {

                traOther.setStatus("3");

            }
        }else {
            if( com.yzj.jep.commons.StringUtils.isEmpty(traOther.getBusType()) ||   //业务类型
                    traOther.getRelTradeAmount() == null ||          //关联交易金额
                    com.yzj.jep.commons.StringUtils.isEmpty(traOther.getTradePrice()) ||//交易价格
                    com.yzj.jep.commons.StringUtils.isEmpty(traOther.getPricingPolicy()) ||//定价政策
                    com.yzj.jep.commons.StringUtils.isEmpty(traOther.getTradeSubject())//交易标的
            ){
                isEmpty = true;
            }else {
                //发起方是否为控股子公司
                if ("1".equals(traOther.getIsHoldingSubsidiary()) && (StringUtils.isEmpty(traOther.getHoldingSubsidiaryName()) ||
                        StringUtils.isEmpty(traOther.getHoldingSubsidiaryNumber()))) {
                    //待补录
                    isEmpty = true;
                }

            }



            if (isEmpty) {
                traOther.setStatus("1");
            } else {
                if (Dict.CONSIDER_TYPE_MYSY.equals(traOther.getConsiderType())) {
                    //判断实际交易金额是否为空
                    if(null != traOther.getPracTradeAmount() && null != traOther.getTranDate()){
                        traOther.setStatus("6");
                    }else if(null == traOther.getTranDate()){
                        traOther.setStatus("8");
                    }else if(null != traOther.getTranDate() && null == traOther.getPracTradeAmount()){
                        traOther.setStatus("7");
                    }
                } else {
                    traOther.setStatus("3");
                }
            }


        }
    }

    /**
     * 判断重大情况并且处理保存信息
     *
     * @param traOther
     * @param relList
     */
    private void checkMajorTrade(TraOther traOther, List<TraRelInfo> relList) {
        for (TraRelInfo tra : relList) {
            tra.setMajorTrade("0");
            tra.setMajorType("0");
            if (Dict.SF_1.equals(traOther.getMajorTrade())) {
                if (Dict.MAJOR_TYPE_DB.equals(traOther.getMajorType())) {
                    tra.setMajorTrade(Dict.SF_1);
                    tra.setMajorType(Dict.MAJOR_TYPE_DB);
                } else if (Dict.MAJOR_TYPE_LJ.equals(traOther.getMajorType())) {
                    tra.setMajorTrade(Dict.SF_1);
                    tra.setMajorType(Dict.MAJOR_TYPE_LJ);
                }
            }
        }
    }

    /**
     * 编辑按钮查看权限后端查询
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/createPerisExist")
    @ResponseBody
    @Log(module = "关联交易管理", description = "按钮查看权限后端查询")
    public Map<String, Boolean> createPerisExist(Integer pid) {
        Map<String, Boolean> map = new HashMap<>();
        TraOther traOther = traOtherService.get(pid);
        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
        if ("System".equals(traOther.getAmendName())) {
            map.put(WebCode.SUCCESS, true);
            return map;
        }
        if (userid.equals(traOther.getAmendId())) {
            map.put(WebCode.SUCCESS, true);
        } else {
            map.put(WebCode.SUCCESS, false);

        }
        return map;
    }

}