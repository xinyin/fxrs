/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:52
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
import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.model.TraEnclosureVo;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.enums.CompanyType;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sub.model.Bus_rel_list;
import com.yzj.jep.app.sub.service.EffectiveRelatedPartyService;
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
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;
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
 * 投资入股类
 */
@Controller
@RequestMapping("/ins/traInvestment")
public class TraInvestmentController extends CrudController<TraInvestment> {
    private static Logger logger = LoggerFactory.getLogger(TraInvestmentController.class);

    @Autowired
    private TraInvestmentService traInvestmentService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private TraInsenclosureService traInsenclosureService;

    @Autowired
    private BaseService baseService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private RuleCheckService ruleCheckService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ApproveAssignerService approveAssignerService;

    @Autowired
    private TraCounterpartyService traCounterpartyService;

    @Autowired
    WorkflowOperation workflowOperation;

    @Autowired
    private EffectiveRelatedPartyService effectiveRelatedPartyMapper;




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
    @Log(module = "投资入股类", description = "查询列表")
    public String list(Model model) {
        //用户角色查询
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "ins/traInvestment/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "ins/traInvestmentList";
    }

    /**
     * 跳转到集中审议页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/batchSendList")
    public String batchSendList(Model model) {
        return "ins/batchSendTraInvestmenList";
    }

    /**
     * 集中审议类型数据列表查询
     *
     * @return
     */
    @RequestMapping("/batchSubmit")
    @ResponseBody
    public Map<String, Object> batchSubmitList(Integer pageNumber, Integer pageSize, String year, String quarter) {
        Map<String, Object> result = new HashMap<>();
        PageInfo<TraInvestment> pageInfo = traInvestmentService.queryByConditions(pageNumber, pageSize, year, quarter);
        result.put("total", pageInfo.getTotal());
        result.put("rows", pageInfo.getList());
        return result;
    }

    /**
     * 集中审议类型数据列表查询
     *
     * @return
     */
    @RequestMapping("/batchConditionSubmit")
    @ResponseBody
    public Map<String, Object> batchConditionSubmit(String year, String quarter) {
        Map<String, Object> result = new HashMap<>();
        PageInfo<TraInvestment> pageInfo = traInvestmentService.queryByConditions(1, Integer.MAX_VALUE, year, quarter);
        List<TraInvestment> list = pageInfo.getList();
        if (CollectionUtil.isEmpty(list)) {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "无数据");
            return result;
        }
        //封装pids
        List<String> pidList = new ArrayList<>();
        for (TraInvestment traInvestment : list) {
            //修改审批状态
            TraInvestment investment = new TraInvestment();
            investment.setPid(traInvestment.getPid());
            investment.setStatus("4");
            investment.setConcentratedApproval("0");
            investment.setConsiderType("2");
            traInvestmentService.updateSelective(investment);
            pidList.add(String.valueOf(traInvestment.getPid()));
            String relInfoListStr = traInvestment.getRelInfoListStr();
            if (StrUtil.isNotBlank(relInfoListStr)) {
                List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
                traInvestment.setRelInfoList(traRelInfos);
                traInvestment.setRelInfoListStr(null);
            }
        }
        Map<String, Object> formData = new HashMap<>();
        formData.put("pids", CollectionUtil.join(pidList, ","));
        formData.put("data", list);
        formData.put("tranType", Dict.TRAN_TYPE_01);
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

    @RequestMapping("/queryByPids/{pids}")
    @ResponseBody
    public Map<String, Object> queryByPids(Page<TraInvestment> page, @PathVariable List<Integer> pids) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        traInvestmentService.query(page, filters);
        result.put(WebCode.SUCCESS, true);
        result.put("total", page.getTotal());
        result.put("rows", page.getResult());
        return result;
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraInvestment> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(new Order("modifiedTime", OrderType.DESC));
        filters.add(new Filter("delflag", "1"));
        //不查询有实际交易金额的数据
        //filters.add(new Filter("pracTradeAmount",Operator.NNL,""));
        //不查询状态为待提交的数据
        //filters.add(new Filter("status",Operator.NE,"3"));
        //且审议类型为一般审议
        //filters.add(new Filter("considerType","2"));
        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
        String unitCode = profile.getUnitCode();
        Date date1 = DateUtils.parseDate(filters.get(0).getProperty());
        Commons.common(request, filters);
        //处理交易截止日期modifiedTime时间，往后加一天
        List<Filter> handleFilters = new ArrayList<>();
        for (int i = 0; i < filters.size(); i++) {
            if (filters.get(i).getProperty().equals("startTranDate")) {
                if (filters.get(i).getOperator().name().equals("GE")) {
                    Date startTranDate = DateUtils.parseDate(filters.get(i).getValue().toString());
                    String format = DateUtils.format(startTranDate, "yyyy-MM-dd");
                    handleFilters.add(new Filter("tranDate", Operator.GE, format));
                    filters.remove(i);
                }
            }

            if (filters.get(i).getProperty().equals("endTranDate")) {
                if (filters.get(i).getOperator().name().equals("LE")) {
                    Date endTranDate = DateUtils.parseDate(filters.get(i).getValue().toString());
                    String format = DateUtils.format(endTranDate, "yyyy-MM-dd");
                    handleFilters.add(new Filter("tranDate", Operator.LE, format));
                    filters.remove(i);
                }
            }


        }
        //查询登陆人机构及下辖机构的信息
        /*AuthorityUtil authorityUtil = new AuthorityUtil();
        filters = authorityUtil.getFilterByRole(filters);*/
        List<String> codes = unitService.selectUnitAllOrDeptAllCodes(profile.getUnitCode());
        List<String> mgerOrgs = codes.stream().distinct().collect(Collectors.toList());
        //filters.add(new Filter("tradeOrg", Operator.IN, mgerOrgs));
        //防止有缓存
        handleFilters.addAll(filters);
        Constants.conditions.clear();
        Constants.conditions.put("filters", handleFilters);
        Page<TraInvestment> query = traInvestmentService.query(page, handleFilters, orders);
        List<TraInvestment> list = page.getResult();
        list.stream().forEach( e -> {
            e.setRelNumber(idEncrypt(e.getRelNumber()));
        });
        page.setResult(list);
    }

    /**
     * 新增页面
     * fil
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {
        //生成交易编号
        TraInvestment traInvestment = new TraInvestment();
        traInvestment.setTransNum(BatchNumberUtil.makeBatchNumber());
        //默认国家定价现金交易为否
      /*  traInvestment.setIsDealPrice(Dict.SF_0);
        traInvestment.setIsCashSubscript(Dict.SF_0);*/
        traInvestment.setTradeAmount(new BigDecimal(0));
        traInvestment.setRelInfoListStr("[]");
        traInvestment.setIsHoldingSubsidiary(Dict.SF_0);
        //添加交易主体名称
        traInvestment.setTraSubName(Commons.getTenantName());
        model.addAttribute("traInvestment", traInvestment);
        return "ins/traInvestmentEdit";
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

        TraInvestment traInvestment = traInvestmentService.get(pid);
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traInvestment.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traInvestment && counterpartyList != null && counterpartyList.size() > 0) {
            traInvestment.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        model.addAttribute("traInvestment", traInvestment);
        model.addAttribute("opt", "edit");
        return "ins/traInvestmentEdit";
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
        TraInvestment traInvestment = traInvestmentService.get(pid);
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traInvestment.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traInvestment && counterpartyList != null && counterpartyList.size() > 0) {
            traInvestment.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        model.addAttribute("traInvestment", traInvestment);
        model.addAttribute("opt", "view");
        return "ins/traInvestmentEdit";
    }

    /**
     * 保存
     *
     * @param traInvestment
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(TraInvestment traInvestment, TraEnclosureVo traEnclosureVo, String proName) {

        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        //校验是否已经存在该笔交易
        TraInvestment existTraInvestment = traInvestmentService.get(traInvestment.getPid());
        //获取关联交易对手方信息列表，并赋值给交易主题信息
        String relInfoStr = traInvestment.getRelInfoListStr();
        JSONArray relArray = JSONArray.parseArray(relInfoStr);
        List<TraRelInfo> relList = relArray.toJavaList(TraRelInfo.class);
        if (relList.size() == 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请添加交易对手信息");
            return tips;
        }

        try {
            String transNum = "";
            traInvestment.setDelflag("1");//正常状态
            traInvestment.setTranType(Dict.TRAN_TYPE_01);
            traInvestment.setDataSources("1");//手工录入
            traInvestment.setTradeOrg(profile.getUnitCode());
            traInvestment.setAmendId(profile.getStaffCode());
            traInvestment.setAmendName(profile.getStaffName());
            traInvestment.setRelInfoList(relList);

            if (existTraInvestment != null) {
                //待确认修改为已归档,待确认码值对应的为2
                if (Dict.APPLY_STATUS_2.equals(traInvestment.getStatus())) {
                    TraInvestment traInvestmentForUpdate = new TraInvestment();
                    //判断实际交易金额是否为空
                    if(null != traInvestmentForUpdate.getPracTradeAmount() && null != traInvestmentForUpdate.getTranDate()){
                        traInvestmentForUpdate.setStatus("6");
                    }else if(null == traInvestmentForUpdate.getTranDate()){
                        traInvestmentForUpdate.setStatus("8");
                    }else if(null != traInvestmentForUpdate.getTranDate() && null == traInvestmentForUpdate.getPracTradeAmount()){
                        traInvestmentForUpdate.setStatus("7");
                    }
                    traInvestmentForUpdate.setPid(traInvestment.getPid());
                    traInvestmentService.updateSelective(traInvestmentForUpdate);
                    //交易流水号
                    transNum = existTraInvestment.getTransNum();
                    //把所有交易对手状态都改为已归档
                    List<Filter> filterList = new ArrayList<>();
                    filterList.add(new Filter("transNum", transNum));
                    TraCounterparty traCounterparty = new TraCounterparty();
                    traCounterparty.setStatus("8");
                    traCounterpartyService.update(traCounterparty, new String[]{"status"}, filterList);
                    //更新文件信息
                    updateTraInsenclosure(traEnclosureVo, transNum);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, "确认成功");
                    return tips;
                }
                //检查重大交易
                checkMajorTrade(traInvestment, relList);
                //检查审议类型
                checkConsiderType(traInvestment);

                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traInvestment.setRelId(r.getRelId());
                traInvestment.setRelType(r.getRelType());
                traInvestment.setRelName(r.getRelName());
                traInvestment.setRelNumber(r.getRelNumber());
                traInvestment.setTranOpponent(r.getTranOpponent());
                traInvestment.setCertType(r.getCertType());
                traInvestment.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traInvestment.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                //更新成操作人(针对第三方数据)
/*                traInvestment.setCreateById(profile.getStaffCode());
                traInvestment.setCreateByName(profile.getStaffName());*/
                traInvestment.setAmendId(profile.getStaffCode());
                traInvestment.setAmendName(profile.getStaffName());
                traInvestmentService.updateSelective(traInvestment);
                transNum = existTraInvestment.getTransNum();

                //更新关联交易-交易对手信息表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traInvestment.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traInvestment.getPracTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traInvestment.getStatus());
                    traCounterparty.setTranDate(traInvestment.getTranDate());
                    traCounterparty.setTraSubName(traInvestment.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());

                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());
                    traCounterpartyService.updateSelective(traCounterparty);
                }

            } else {
                //检查重大交易
                checkMajorTrade(traInvestment, relList);
                //检查审议类型
                checkConsiderType(traInvestment);

                //设置交易号
                transNum = traInvestment.getTransNum();
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traInvestment.setRelId(r.getRelId());
                traInvestment.setRelType(r.getRelType());
                traInvestment.setRelName(r.getRelName());
                traInvestment.setRelNumber(r.getRelNumber());
                traInvestment.setTranOpponent(r.getTranOpponent());
                traInvestment.setCertType(r.getCertType());
                traInvestment.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traInvestment.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traInvestmentService.insertSelective(traInvestment);
                //保存交易对手到新表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traInvestment.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traInvestment.getPracTradeAmount());
                    traCounterparty.setTradeAmount(tra.getTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traInvestment.getStatus());
                    traCounterparty.setTranDate(traInvestment.getTranDate());
                    traCounterparty.setTraSubName(traInvestment.getTraSubName());
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

    //签定页面的保存
    @RequestMapping(value = "/saveSign")
    @ResponseBody
    public Map<String, Object> saveSign(TraInvestment traInvestment, TraEnclosureVo traEnclosureVo, String proName) {

        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        //校验是否已经存在该笔交易
        TraInvestment existTraInvestment = traInvestmentService.get(traInvestment.getPid());
        //获取关联交易对手方信息列表，并赋值给交易主题信息
        String relInfoStr = traInvestment.getRelInfoListStr();
        JSONArray relArray = JSONArray.parseArray(relInfoStr);
        List<TraRelInfo> relList = relArray.toJavaList(TraRelInfo.class);
        if (relList.size() == 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请添加交易对手信息");
            return tips;
        }

        try {
            String transNum = "";
            traInvestment.setDelflag("1");//正常状态
            traInvestment.setTranType(Dict.TRAN_TYPE_01);
            traInvestment.setDataSources("1");//手工录入
            traInvestment.setTradeOrg(profile.getUnitCode());
            traInvestment.setAmendId(profile.getStaffCode());
            traInvestment.setAmendName(profile.getStaffName());
            traInvestment.setRelInfoList(relList);

            if (existTraInvestment != null) {
                //待确认修改为已归档,待确认码值对应的为2
                if ("8".equals(traInvestment.getStatus())) {
                    //判断实际交易金额是否为空，如果不为空修改为已归档
                    TraInvestment traInvestmentForUpdate = new TraInvestment();
                    traInvestmentForUpdate.setPid(traInvestment.getPid());
                    if(traInvestment.getPracTradeAmount()  == null) {
                        traInvestmentForUpdate.setTranDate(traInvestment.getTranDate());
                        traInvestmentForUpdate.setStatus("7");
                    }else{
                        traInvestmentForUpdate.setTranDate(traInvestment.getTranDate());
                        traInvestmentForUpdate.setStatus("6");
                    }
                    traInvestmentService.updateSelective(traInvestmentForUpdate);
                    //交易流水号
                    transNum = existTraInvestment.getTransNum();
                    //把所有交易对手状态都改为已归档
                    List<Filter> filterList = new ArrayList<>();
                    filterList.add(new Filter("transNum", transNum));
                    TraCounterparty traCounterparty = new TraCounterparty();
                    if(traInvestment.getPracTradeAmount()  == null) {
                        traCounterparty.setTranDate(traInvestment.getTranDate());
                        traCounterparty.setStatus("7");
                    }else{
                        traCounterparty.setTranDate(traInvestment.getTranDate());
                        traCounterparty.setStatus("6");
                    }
                   // traCounterparty.setStatus("8");
                    traCounterpartyService.update(traCounterparty, new String[]{"status"}, filterList);
                    //更新文件信息
                    updateTraInsenclosure(traEnclosureVo, transNum);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, "签定成功");
                    return tips;
                }
                //检查重大交易
                checkMajorTrade(traInvestment, relList);
                //检查审议类型
                checkConsiderType(traInvestment);

                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traInvestment.setRelId(r.getRelId());
                traInvestment.setRelType(r.getRelType());
                traInvestment.setRelName(r.getRelName());
                traInvestment.setRelNumber(r.getRelNumber());
                traInvestment.setTranOpponent(r.getTranOpponent());
                traInvestment.setCertType(r.getCertType());
                traInvestment.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traInvestment.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traInvestmentService.updateSelective(traInvestment);
                transNum = existTraInvestment.getTransNum();

                //更新关联交易-交易对手信息表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traInvestment.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traInvestment.getPracTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traInvestment.getStatus());
                    traCounterparty.setTranDate(traInvestment.getTranDate());
                    traCounterparty.setTraSubName(traInvestment.getTraSubName());
                    traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());

                    traCounterparty.setMajorTrade(tra.getMajorTrade());
                    traCounterparty.setMajorType(tra.getMajorType());
                    traCounterpartyService.updateSelective(traCounterparty);
                }

            } else {
                //检查重大交易
                checkMajorTrade(traInvestment, relList);
                //检查审议类型
                checkConsiderType(traInvestment);

                //设置交易号
                transNum = traInvestment.getTransNum();
                /**
                 * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
                 */
                TraRelInfo r = relList.get(0);
                traInvestment.setRelId(r.getRelId());
                traInvestment.setRelType(r.getRelType());
                traInvestment.setRelName(r.getRelName());
                traInvestment.setRelNumber(r.getRelNumber());
                traInvestment.setTranOpponent(r.getTranOpponent());
                traInvestment.setCertType(r.getCertType());
                traInvestment.setTradeAmount(r.getTradeAmount());
                //把交易对手查的年度累计未清理金额赋值给 累计实际交易金额
                traInvestment.setAccuPracTradeAmount(r.getTotalNotCleanTradeAmount());
                traInvestmentService.insertSelective(traInvestment);
                //保存交易对手到新表
                for (TraRelInfo tra : relList) {
                    TraCounterparty traCounterparty = new TraCounterparty();
                    BeanUtils.copyProperties(tra, traCounterparty);
                    traCounterparty.setTransNum(transNum);
                    traCounterparty.setModifiedById(profile.getStaffCode());
                    traCounterparty.setModifiedByName(profile.getStaffName());
                    traCounterparty.setModifiedTime(new Date());
                    traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
                    traCounterparty.setRelTradeAmount(traInvestment.getRelTradeAmount());
                    traCounterparty.setPracTradeAmount(traInvestment.getPracTradeAmount());
                    traCounterparty.setTradeAmount(tra.getTradeAmount());
                    traCounterparty.setTotalAssets(tra.getTotalAssets());
                    traCounterparty.setCertType(tra.getCertType());
                    traCounterparty.setStatus(traInvestment.getStatus());
                    traCounterparty.setTranDate(traInvestment.getTranDate());
                    traCounterparty.setTraSubName(traInvestment.getTraSubName());
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
            TraInvestment traInvestment = traInvestmentService.get(pid);
            traInvestment.setDelflag("0");
            traInvestmentService.updateSelective(traInvestment);
            //同时把交易对手表数据状态改为删除
            TraCounterparty tra = new TraCounterparty();
            tra.setTransNum(traInvestment.getTransNum());
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
     * 查阅页面跳转
     */
    @RequestMapping(value = "/toConfirm/{pid}")
    public String toConfirm(@PathVariable Integer pid, Model model) {
        TraInvestment traInvestment = traInvestmentService.get(pid);
        //确认归档后设置状态为以归档
//        traInvestment.setStatus("6");
//        traInvestmentService.updateSelective(traInvestment);
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traInvestment.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traInvestment && counterpartyList != null && counterpartyList.size() > 0) {
            traInvestment.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        model.addAttribute("traInvestment", traInvestment);
        model.addAttribute("opt", "view");
        model.addAttribute("file", "file");
        model.addAttribute("isConfirm", true);
        return "ins/traInvestmentEdit";
    }

    @RequestMapping(value = "/sign/{pid}")
    public String sign(@PathVariable Integer pid, Model model) {
        TraInvestment traInvestment = traInvestmentService.get(pid);
        //确认归档后设置状态为以归档
//        traInvestment.setStatus("6");
//        traInvestmentService.updateSelective(traInvestment);
        //获取交易对手列表信息
        List<Filter> filterList = new ArrayList<>();
        filterList.add(new Filter("transNum", traInvestment.getTransNum()));
        List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
        if (null != traInvestment && counterpartyList != null && counterpartyList.size() > 0) {
            traInvestment.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
        }
        model.addAttribute("traInvestment", traInvestment);
        model.addAttribute("opt", "view");
        model.addAttribute("isConfirm", true);
        return "ins/traInvestmentSign";
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

        boolean exist = traInvestmentService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 反显，查询所属关联方信息（旧）
     */
    @RequestMapping(value = "/selectRelPsn")
    @ResponseBody
    public Map selectRelPsn(String relType, String identifyNumber) {
        Map result = new HashMap();
        result.put("code", 1);
        RelPsn relPsn = new RelPsn();
        RelOrg relOrg = new RelOrg();
        TraInvestment traInvestment = new TraInvestment();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").nnl("aligCode").build();
        if ("PSN".equals(relType)) {
            if (!StringUtils.isEmpty(identifyNumber)) {
                filters.add(new Filter("identifyNumber", identifyNumber));
            }
            relPsn = relPsnService.get(filters);
            if (relPsn != null) {
                result.put("code", 0);
                traInvestment.setRelId(relPsn.getPid());
                traInvestment.setRelName(relPsn.getName());
                traInvestment.setTotolTradeAmount(new BigDecimal(100));
                traInvestment.setMajorTrade("0");
                traInvestment.setCaliber(relPsn.getRegulatorOth1());
                result.put("tra", traInvestment);
            }
        } else {
            if (!StringUtils.isEmpty(identifyNumber)) {
                filters.add(new Filter("uniscCode", identifyNumber));
            }
            relOrg = relOrgService.get(filters);
            if (relOrg != null) {
                result.put("code", 0);
                traInvestment.setRelId(relOrg.getPid());
                traInvestment.setRelName(relOrg.getOrgName());
                traInvestment.setTotolTradeAmount(new BigDecimal(100));
                traInvestment.setMajorTrade("0");
                traInvestment.setCaliber(relOrg.getRegulatorOth1());
                result.put("tra", traInvestment);
            }
        }

        return result;
    }

    /**
     * 反显，查询所属关联方信息
     */
    @RequestMapping(value = "/selectRel")
    @ResponseBody
    public Map selectRel(String relName, String identifyNumber) {
        Map result = new HashMap();
        result = baseService.getRelInfo(identifyNumber, relName);
        return result;
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
        TraInvestment traInvestment = traInvestmentService.get(filers);
        //更换管库机构为下发机构
        traInvestment.setTradeOrg(unitCode);
        //更新疑似自然人状态为待补录
        traInvestment.setStatus("2");
        traInvestment.setStatusComment(memo);
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
                    notifyService.createRemind(duty.getStaffCode(), "投资入股类交易[" + traInvestment.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到关联交易投资入股类列表完成补录。", "投资入股类交易[" + traInvestment.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到关联交易投资入股类列表完成补录。");
                }
                traInvestmentService.update(traInvestment);
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


    /**
     * 审批页面跳转
     */
    @RequestMapping(value = "/submit/{pid}")
    public String submit(@PathVariable Integer pid, Model model) {
        TraInvestment traInvestment = traInvestmentService.get(pid);
        model.addAttribute("traInvestment", traInvestment);
        model.addAttribute("opt", "view");
        return "ins/traInvestmentApprove";
    }

    /**
     * 判断是否重大关联交易
     *
     * @return
     */
    @RequestMapping(value = "/GreatCheck")
    @ResponseBody
    public Map<String, Object> isGreatCheck(String relTradeAmount, String relInfoListStr) {
        HashMap<String, Object> result = Maps.newHashMap();
        Map ruleResult = null;
        String isGreate = "0";
        String majorType = "0";//0标识不重大
        String notMajorRelNumber = "";
        try {
            BigDecimal bigRelTradeAmount = StringUtils.isEmpty(relTradeAmount) ? null : new BigDecimal(relTradeAmount).setScale(6, BigDecimal.ROUND_HALF_UP);
            ruleResult = InsUtil.GreatCheckNew(Dict.TRAN_TYPE_01, bigRelTradeAmount, relInfoListStr);

            if (null != ruleResult) {
                InsTrans insTrans = (InsTrans) ruleResult.get("insTrans");
                if (StringUtils.isNotEmpty(insTrans.getMajorTrade())) {
                    isGreate = insTrans.getMajorTrade();
                }
                if (StringUtils.isNotEmpty(insTrans.getMajorType())) {
                    majorType = insTrans.getMajorType();
                }
                if (ruleResult.get("notMajorRelNumber") != null) {
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


    @RequestMapping(value = "/traFundsCheck")
    @ResponseBody
    public Map<String, Object> tradeCheck(TraInvestment traFunds) {
        Map<String, Object> result = new HashMap();
//        StringBuffer sb_msg = new StringBuffer();
//        StringBuffer sb_msg2 = new StringBuffer();
//        StringBuffer sb_code = new StringBuffer();
//        Map params = InsUtil.initRuleData(traFunds.getRelNumber(), traFunds.getRelId() + "", traFunds.getRelType(), traFunds.getTradeAmount() + "", "TraInvestment", null, traFunds.getInvestmentType());
//        List<Map<String, String>> ruleResult = ruleCheckService.ComPlianceCheck("01", params);
//        if (ruleResult != null && ruleResult.size() > 0) {
//            if (ruleResult.size() == 1 && "999999".equals(ruleResult.get(0).get("code"))) { //规则执行出错， 不影响流程
//                result.put(WebCode.SUCCESS, true);
//            } else {
//                for (int i = 0; i < ruleResult.size(); i++) {
//                    if (org.apache.commons.lang3.StringUtils.isNotBlank(ruleResult.get(i).get("code")) && org.apache.commons.lang3.StringUtils.isNotBlank(ruleResult.get(i).get("msg"))) {
//                        String code = ruleResult.get(i).get("code");
//                        String msg = ruleResult.get(i).get("msg");
//                        sb_code.append(code + "|");
//                        sb_msg2.append(msg + "|");
//                        if (i < ruleResult.size() - 1) {
//                            sb_msg.append("[" + msg + "]，");
//                        } else {
//                            sb_msg.append("[" + msg + "]");
//                        }
//                    }
//                }
//                result.put(WebCode.SUCCESS, false);
//                result.put("code", sb_msg2.toString());
//                result.put("msg", "合规检查异常：【" + sb_msg.toString() + "】，您是否继续提交保存？");
//            }
//
//        } else {
        result.put(WebCode.SUCCESS, true);
//        }


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
//        String businessType = traService.getBusType();
//        BigDecimal tradeAmount = traService.getTradeAmount();
        Map<String, Object> tips = Maps.newHashMap();
//        Map<String, Object> quotaParam = InsUtil.initQuotaParam("01", businessType, tradeAmount);
//        if (quotaParam != null) {
//            Map<String, String> resultMap = ruleCheckService.quotaCheck(quotaParam);
//            if (resultMap != null) {
//                tips.put(WebCode.SUCCESS, false);
//                tips.put(WebCode.MSG, resultMap.get("msg"));
//                return tips;
//            }
//        }
        tips.put(WebCode.SUCCESS, true);
        return tips;
    }

    /**
     * 内联树
     *
     * @return
     */
    @RequestMapping(value = "/proNameListTree")
    public String proNameListTree() {
        return "bus/limitEditTree";
    }

    /**
     * 选择交易对手名称
     */
    @RequestMapping(value = "/relNameSelect/{traSubName}/{tranType}")
    public String relNameSelect(Model model, @PathVariable String traSubName, @PathVariable String tranType) {
        String string = traSubName;
        model.addAttribute("traSubName", string);
        model.addAttribute("tranType", tranType);
        return "ins/RelNameSelect";
    }

    /**
     * 判断选择的产品是否在用信交易类型下
     *
     * @param unitCodes
     * @return
     */
    @RequestMapping("/selectbuinessL")
    @ResponseBody
    public Map<String, Object> selectDept(String unitCodes) {
        System.out.println(unitCodes);
        Map<String, Object> map = new HashMap<String, Object>();
        String[] split = unitCodes.split("-");
        String level = split[0];
        Integer pid = Integer.parseInt(split[1]);
        Integer paProId = productService.get(pid).getPaProId();


        //反显交易类型和业务类型
        if ("0".equals(level)) {
            map.put(WebCode.SUCCESS, false);
            map.put(WebCode.MSG, "操作失败，请选择一个产品或者业务类型！！！");
        } else if ("1".equals(level)) {
            Integer proId = Integer.parseInt(productService.get(productService.get(pid).getPaProId()).getProId());

            //判断选择的产品是否属于投资入股类
            if (proId != 1) {
                map.put(WebCode.SUCCESS, false);
                map.put(WebCode.MSG, "操作失败，请选择投资入股模块下的产品或者业务类型！！！");
            } else {
                map.put("tradesType", proId.toString());
                map.put("investmentType", pid.toString());
                map.put(WebCode.SUCCESS, true);

            }
        } else if ("2".equals(level)) {
            Integer proId = Integer.parseInt(productService.get(productService.get(productService.get(pid).getPaProId()).getPaProId()).getProId());
            if (proId != 1) {
                map.put(WebCode.SUCCESS, false);
                map.put(WebCode.MSG, "操作失败，请选择投资入股模块下的产品或者业务类型！！！");
            } else {
                map.put("tradesType", proId);
                map.put("investmentType", productService.get(pid).getPaProId().toString());
                map.put(WebCode.SUCCESS, true);
            }
        }
        return map;
    }

    /**
     * 查询交易类型
     *
     * @return
     */
    @RequestMapping(value = "/getBuinessTypeItems")
    @ResponseBody
    public List<NameValuePair> getBuinessTypeItems() {

        List<Filter> filters = new Filter.Builder().eq("busLevel", 1).eq("delflag", "1").build();
    /*    filters.add(new Filter("delflag", Operator.EQ, "1"));
        filters.add(new Filter("preLimit", Operator.NNL, "0"));*/
        List<Product> list = productService.select(filters);
        List<NameValuePair> nameValuePairs = NVUtils.convert(list, list.size() + 1, "pid", "proName");
//		nameValuePairs.add(0, new BasicNameValuePair("N/A",""));
        return nameValuePairs;

    }

    /**
     * 更新关联交易-附件表
     *
     * @param traEnclosureVo
     * @param transNum
     */
    public void updateTraInsenclosure(TraEnclosureVo traEnclosureVo, String transNum) {
        List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
        traInsenclosureService.updateTransNum(docTableSelectItem, transNum);
    }

    /*
     * 提交审批
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> send(@PathVariable Integer pid, Model model) throws Exception {
        List<TraFunds> lli = new ArrayList<>();
        Map<String, Object> tips = new HashMap<String, Object>();
        /*List<String> li= Arrays.asList(pid.split(","));*/
        //获取当前登录用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        //String staffCode = profile.getStaffCode();
        String procInstId = null;

        StaffImpl staff = new StaffImpl();
        BeanUtils.copyProperties(profile, staff);

        TraInvestment investment = traInvestmentService.get(pid);
        //根据交易编号更新交易对手表状态改为审批中
        TraCounterparty tra = new TraCounterparty();
        tra.setStatus(Dict.APPLY_STATUS_4);
        tra.setTransNum(investment.getTransNum());
        traCounterpartyService.updataCounterpartyStatusByTransNum(tra);
        String relInfoListStr = investment.getRelInfoListStr();
        if (StrUtil.isNotBlank(relInfoListStr)) {
            //设置关联人信息
            List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
            investment.setRelInfoList(traRelInfos);
            //解析json时会报错，此处设置为空
            investment.setRelInfoListStr(null);
        }
        com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSONObject.parseObject(JSONObject.toJSONString(investment));
        if (org.apache.commons.lang.StringUtils.isNotBlank(investment.getProcInstId())) {
            procInstId = investment.getProcInstId();
        }
        data.put("startStaffCode", staff.getStaffCode());
        data.put("tenantId", profile.getTenantId());
        data.put("TransactionTtype","investment");
        data.put("type","investment");
        //判断员工所属机构
        Boolean StaffType = this.companyType(profile.getUnitCode());
        String procDefCode =null;
        String considerType = investment.getConsiderType();
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
            if ("2".equals(considerType)){
                data.put("WorkflowType", "YBGLJY");
                procDefCode = "FXBDX_YBGLJY";
            }else {
                //总公司审批流 重大统一交易审批流
                data.put("WorkflowType", "ZDTI");
                procDefCode = "FXBDX_ZDTY";
            }
        }
        data.put("procDefCode", procDefCode);
        try {
            workflowOperation.sendSubmit(pid, procDefCode, staff, null, data.toJSONString(), "investment", procInstId);
        } catch (Exception e) {
            logger.info("流程启动失败，失败数据类型:投资入股类---" + "pid" + pid, e);
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
    private void checkConsiderType(TraInvestment traInvestment) {
        //校验交易 审议状态，1免于审议， 2一般审议， 3逐笔审议
        boolean isEmpty = false;
        String considerType = traInvestment.getConsiderType();
        if(considerType.equals("3")){
            if (
                    StringUtils.isEmpty(traInvestment.getInvestmentType()) ||//业务类型
                            //     traInvestment.getTranDate() == null ||//合同签署日期
                            traInvestment.getRelTradeAmount() == null ||//关联交易金额
                  /*          StringUtils.isEmpty(traInvestment.getIsCashSubscript()) ||//现金认购
                            StringUtils.isEmpty(traInvestment.getIsDealPrice()) ||//交易定价是否为国家规定*/
                                 StringUtils.isEmpty(traInvestment.getTradeSubject()) ||//交易标的
                            StringUtils.isEmpty(traInvestment.getTradePrice()) ||//交易价格
                            StringUtils.isEmpty(traInvestment.getPricingPolicy()) ||//定价政策
                            //    StringUtils.isEmpty(traInvestment.getRemark())||//其他事项说明
                            StringUtils.isEmpty(traInvestment.getContractNo()) ||//合同编号 逐笔必填
                            traInvestment.getBusStartTime() == null ||//业务起始日期 逐笔必填
                            traInvestment.getYield_() == null ||//收益率 逐笔必填
                            traInvestment.getShareNum() == null ||//股份张数 逐笔必填
                            StringUtils.isEmpty(traInvestment.getFinancialImpact())//对财务状况及经营成果的影响 逐笔必填
            ) {//所有类型都必填的，缺失即为待补录
                isEmpty = true;
            }
            else {
                if ("01".equals(traInvestment.getInvestmentType())) {//当业务类型为投资入股或增资，变动股比占比，变动后股比，生效时间必填
                    if (traInvestment.getEffectiveTimeString() == null ||//生效时间 当业务类型为1的逐笔时必填
                            StringUtils.isEmpty(traInvestment.getApprovalNum()) ||//行政审批文号 当业务类型为1、2、3时逐笔必填
                            traInvestment.getVariableshareratio() == null ||//变动股比或份额占比 当业务类型为1时逐笔必填
                            traInvestment.getVariableshareratioAfter() == null) {//变动后股比或份额占比 当业务类型为1时逐笔必填
                        isEmpty = true;
                    }
                } else if ("02".equals(traInvestment.getInvestmentType()) || "03".equals(traInvestment.getInvestmentType())) {
                    if (StringUtils.isEmpty(traInvestment.getApprovalNum())) {//行政审批文号 但业务类型为1、2、3时逐笔必填
                        isEmpty = true;
                    }
                } else if ("04".equals(traInvestment.getInvestmentType())) {
                    if (StringUtils.isEmpty(traInvestment.getSecuritiesType())) {//证券类型 当业务类型为4时逐笔必填
                        isEmpty = true;
                    }
                }
            }
            if (isEmpty) {
                traInvestment.setStatus("1");
            } else {

                traInvestment.setStatus("3");

            }

        }else{


            if( StringUtils.isEmpty(traInvestment.getInvestmentType()) ||   //业务类型
                    traInvestment.getRelTradeAmount() == null ||          //关联交易金额
                    StringUtils.isEmpty(traInvestment.getTradePrice()) ||//交易价格
                    StringUtils.isEmpty(traInvestment.getPricingPolicy()) ||//定价政策
                    StringUtils.isEmpty(traInvestment.getTradeSubject())   //交易标的
              /*   ||StringUtils.isEmpty(traInvestment.getIsCashSubscript()) ||//现金认购
                    StringUtils.isEmpty(traInvestment.getIsDealPrice())  //交易定价是否为国家规定*/
            ){
                isEmpty = true;
            }

            if(isEmpty){
                traInvestment.setStatus("1");
            }else{
                if (Dict.CONSIDER_TYPE_MYSY.equals(traInvestment.getConsiderType())) {
                    //判断实际交易金额是否为空
                    if(null != traInvestment.getPracTradeAmount() && null != traInvestment.getTranDate()){
                        traInvestment.setStatus("6");
                    }else if(null == traInvestment.getTranDate()){
                        traInvestment.setStatus("8");
                    }else if(null != traInvestment.getTranDate() && null == traInvestment.getPracTradeAmount()){
                        traInvestment.setStatus("7");
                    }
                }else{
                    traInvestment.setStatus("3");

                }
            }

        }

    }

    /**
     * 判断重大情况并且处理保存信息
     *
     * @param traInvestment
     * @param relList
     */
    private void checkMajorTrade(TraInvestment traInvestment, List<TraRelInfo> relList) {
        for (TraRelInfo tra : relList) {
            tra.setMajorTrade("0");
            tra.setMajorType("0");
            if (Dict.SF_1.equals(traInvestment.getMajorTrade())) {
                if (Dict.MAJOR_TYPE_DB.equals(traInvestment.getMajorType())) {
                    tra.setMajorTrade(Dict.SF_1);
                    tra.setMajorType(Dict.MAJOR_TYPE_DB);
                } else if (Dict.MAJOR_TYPE_LJ.equals(traInvestment.getMajorType())) {
                    tra.setMajorTrade(Dict.SF_1);
                    tra.setMajorType(Dict.MAJOR_TYPE_LJ);
                }
            }
        }
    }

    /**
     * 编辑按钮查看权限后端查询
     * @param pid
     * @return
     */
    @RequestMapping(value = "/createPerisExist")
    @ResponseBody
    @Log(module = "关联交易管理", description = "按钮查看权限后端查询")
    public Map<String, Boolean> createPerisExist(Integer pid) {
        Map<String, Boolean> map = new HashMap<>();
        TraInvestment traInvestment = traInvestmentService.get(pid);
        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
        if ("System".equals(traInvestment.getAmendName())) {
            map.put(WebCode.SUCCESS, true);
            return map;
        }
        if (userid.equals(traInvestment.getAmendId())) {
            map.put(WebCode.SUCCESS, true);
        } else {
            map.put(WebCode.SUCCESS, false);

        }
        return map;
    }

}