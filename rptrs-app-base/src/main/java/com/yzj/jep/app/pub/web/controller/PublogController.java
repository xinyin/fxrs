/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月20日 11:07:48
 *
 */

package com.yzj.jep.app.pub.web.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.core.factory.FaultFactory;
import com.fPaaS1.core.factory.MessageFactory;
import com.fPaaS1.core.utils.Constants;
import com.google.common.collect.Maps;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.FundsInvestmentService;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.fPaas.client.HttpConstans;
import com.yzj.jep.app.ins.constants.TranTypeConstant;
import com.yzj.jep.app.ins.mapper.TraInvestmentMapper;
import com.yzj.jep.app.ins.model.QuarterlyVo;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.ins.web.controller.Commons;
import com.yzj.jep.app.pub.client.FileOnLineEditClient;
import com.yzj.jep.app.pub.model.*;
import com.yzj.jep.app.pub.service.OaApprovalLogService;
import com.yzj.jep.app.pub.service.PubFileTemplateService;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.app.sub.util.DownFileUtils;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.*;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.core.utils.xlsio.transformer.XlsTransformer4Merge;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.commons.DateUtils.*;

@Controller
@RequestMapping("/pub/publog")
public class PublogController extends CrudController<Publog> {
    private static Logger logger = LoggerFactory.getLogger(PublogController.class);


    @Value("${file.path.disclose}")
    public String url;

    @Autowired
    private InsInvestmentService insInvestmentService;
    @Autowired
    private InsFundsService insFundsService;
    @Autowired
    private InsBusinessService insBusinessService;
    @Autowired
    private InsInterestService insInterestService;
    @Autowired
    private InsServiceService insServiceService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private InsOtherService insOtherService;
    @Autowired
    private PublogService publogService;
    @Autowired
    private InsAllbustraninfoService insAllbustraninfoService;
    @Autowired
    private static Configs configs;
    @Autowired
    private static ExcelToPdf excelToPdf;

    @Autowired
    private DownFileUtils downFileUtils;

    @Autowired
    private IFPaaS1Handler OAContractGatewayClientHandler;

    @Autowired
    private OaApprovalLogService oaApprovalLogService;

    @Autowired
    private TraOtherService traOtherService;

    @Autowired
    private PubFileTemplateService pubFileTemplateService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private TraInvestmentService traInvestmentService;

    @Autowired
    private TraBusinessService traBusinessService;

    @Autowired
    private TraFundsService traFundsService;

    @Autowired
    private TraInterestService traInterestService;

    @Autowired
    private TraServiceService traServiceService;

    @Autowired
    private FundsInvestmentService fundsInvestmentService;

    @Autowired
    private TraInvestmentMapper  traInvestmentMapper;


    @Autowired
    private FileOnLineEditClient fileOnLineEditClient;
    @Autowired
    private RelService relService;
    @Value("/home/rptrs/template")
    private String disclosePath;
    @Autowired
    private StaffService staffService;

    /**
     * 进入年度关联交易列表页
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/lists")
    @Log(module = "进入年度关联交易列表页", description = "查询进入年度关联交易列表")
    public String lists(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            model.addAttribute("pubSubject", "");
        } else {
            model.addAttribute("pubSubject", tenantId);
        }
        return "pub/publogtimeList";
    }


    @RequestMapping(value = "/list1")
    @Log(module = "进入控制委员会报告汇总列表页", description = "查询进入控制委员会报告汇总列表")
    public String list1(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            model.addAttribute("pubSubject", "");
        } else {
            model.addAttribute("pubSubject", tenantId);
        }
        return "pub/pubControlList";
    }


    /**
     * 进入季度关联交易列表页
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "进入季度关联交易列表页", description = "查询进入季度关联交易列表")
    public String list(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            model.addAttribute("pubSubject", "");
        } else {
            model.addAttribute("pubSubject", tenantId);
        }
        return "pub/publogList";
    }

    /**
     * 进入季度关联交易查询展示页面
     *
     * @return 查询展示页面
     */
    @RequestMapping(value = {"/queryQuarter"},  method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryQuarter(String quarter, String year, Page<QuarterlyVo> page, HttpServletRequest request,String startTranDate, String endTranDate) {

        Map<String, Object> result = new HashMap<>();
        //构造查询条件
        Map<String, Object> param = new HashMap<>();
        //处理查询条件
        StringBuilder stringBuilder = new StringBuilder();
        if (CharSequenceUtil.isNotBlank(year)) {
            stringBuilder.append(year);
            param.put("year", stringBuilder.toString().concat("%"));
        }
        //合同签署日期
        if(CharSequenceUtil.isNotBlank(startTranDate)){
            stringBuilder.append(startTranDate);
        }
        if(CharSequenceUtil.isNotBlank(endTranDate)){
            stringBuilder.append(endTranDate);
        }
        param.put("startTranDate", startTranDate);
        param.put("endTranDate", endTranDate);


        if (CharSequenceUtil.isNotBlank(year)) {
            stringBuilder.append(year);
            param.put("year", stringBuilder.toString().concat("%"));
        }


        //季度
        if (CharSequenceUtil.isNotBlank(quarter)) {
            int ofYear = DateUtil.year(new Date());
            if (CharSequenceUtil.isBlank(year)) {
                stringBuilder.append(ofYear);
            }
            String beginQuarter = null;
            String endQuarter = null;
            //季度endQuarter拼接最大时间
            if ("1".equals(quarter)) {
                beginQuarter = stringBuilder.toString().concat("-01-01");
                endQuarter = stringBuilder.toString().concat("-04-01");
            } else if ("2".equals(quarter)) {
                beginQuarter = stringBuilder.toString().concat("-04-01");
                endQuarter = stringBuilder.toString().concat("-07-01");
            } else if ("3".equals(quarter)) {
                beginQuarter = stringBuilder.toString().concat("-07-01");
                endQuarter = stringBuilder.toString().concat("-10-01");
            } else {
                beginQuarter = stringBuilder.toString().concat("-10-01");
                //年加一
                int addYear = ofYear + 1;
                endQuarter = addYear + "-01-01";
            }
            param.put("beginQuarter", beginQuarter);
            param.put("endQuarter", endQuarter);
        }
        List<TraInvestment> list = traInvestmentMapper.getByInt(param);
        List<QuarterlyVo> quarterlyVos = new ArrayList<>();
        if(!CollUtil.isEmpty(list)){
            quarterlyVos = QueryQuarterCommon.queryQuarterTranData(list);
        }

        page.setResult(quarterlyVos);
        page.setTotal(quarterlyVos.size());
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }








    @RequestMapping(value = "/listFunds")
    @ResponseBody
    public void listFunds(Page<TraFunds> page, HttpServletRequest request, HttpServletResponse response) {

        List<Filter> filters = RequestUtils.buildFilters(request);

        Map<String, Object> result = new HashMap<>();
//        ?traSubName=0001,0001-ZZHK&majorTrade=0
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        String unitCode = userProfile.getUnitCode();
        String userid = userProfile.getUserid();
        AuthorityUtil authorityUtil = new AuthorityUtil();
        filters = authorityUtil.getFilterByRole(filters);
        Commons.common(request, filters);
        filters.add(Filter.eq("delflag", "1"));
//        orders.add(Order.desc("modifiedTime"));
        //处理交易截止日期modifiedTime时间，往后加一天
        List<Filter> handleFilters = new ArrayList<>();
        for (int i = 0; i < filters.size(); i++) {
            if (filters.get(i).getProperty().equals("modifiedTime")) {
                if (filters.get(i).getOperator().name().equals("LT")) {
                    Date date = DateUtils.parseDate(filters.get(i).getValue().toString());
                    long l = date.getTime() + 24 * 3600 * 1000 * 1;
                    String format = DateUtils.format(l, "yyyy-MM-dd");
                    handleFilters.add(new Filter("modifiedTime", Operator.LT, format));
                    filters.remove(i);
                }
            }
            if (filters.get(i).getProperty().equals("majorTrade")) {
                if (filters.get(i).getValue().equals("0")) {
                    handleFilters.add(new Filter("majorTrade", "0"));
                    handleFilters.add(Filter.ge("contractAmount", 500));
                    if (unitCode.contains("0001")) {
                        handleFilters.add(new Filter("amendId", userid));
                        handleFilters.add(new Filter("traSubName", Operator.LIKE, "0001"));
                    } else {
                        handleFilters.add(new Filter("traSubName", Operator.LIKE, "ZZ_Delegation"));
                    }
                }
            }
        }
//        防止有缓存
        handleFilters.addAll(filters);
        com.yzj.jep.app.sys.util.Constants.conditions.clear();
        com.yzj.jep.app.sys.util.Constants.conditions.put("filters", handleFilters);
        List<TraFunds> traFundsList1 = traFundsService.select(handleFilters);
        List<TraFunds> traFundsList2 = traFundsService.select(handleFilters);

        traFundsList1.addAll(traFundsList2);


        page.setTotal(traFundsList1.size());
        int pages = (traFundsList1.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<TraFunds> traFundsArrayList = new ArrayList<TraFunds>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < traFundsList1.size()) {
                traFundsArrayList.add(traFundsList1.get(i));
            }
        }

        page.setResult(traFundsArrayList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());


        try {
            response.getWriter().print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }


//        Page<TraFunds> query = traFundsService.query(page, handleFilters, orders);
//
//        return "pub/publogList";
    }

    /**
     * 定期披露数据查询
     */
    @ResponseBody
    @RequestMapping("/queryTraVo")
    @Log(module = "披露管理", description = "查询生成季度披露列表")
    public Map<String, Object> querytraVo(Page<PubQuarterVo> page, Model model, HttpServletRequest request, HttpServletResponse response) {
        List<Filter> filters = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        filters = RequestUtils.buildFilters(request);
        Map<String, Object> result = new HashMap<String, Object>();
        filters.add(Filter.eq("delflag", "1"));
        filters.add(Filter.eq("isDiscovery", "0"));
        orders.add(new Order("modifiedTime", OrderType.DESC));
        List<PubQuarterVo> quarterVos = new ArrayList<>();
        int i = 1;
        List<InsAllbustraninfo> insAllbustraninfos = null;
        try {
            insAllbustraninfos = insAllbustraninfoService.select(filters, orders);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        for (InsAllbustraninfo insAllbustraninfo : insAllbustraninfos) {
            PubQuarterVo pubQuarterVo = new PubQuarterVo();
            pubQuarterVo.setNo(String.valueOf(i++)); //序号
            pubQuarterVo.setPid(String.valueOf(insAllbustraninfo.getPid()));  //pid
            pubQuarterVo.setTranYear(insAllbustraninfo.getSubYear()); //年度
            pubQuarterVo.setTranQuarter(insAllbustraninfo.getSubQuarter()); //季度
            pubQuarterVo.setRelName(insAllbustraninfo.getRelName());  //交易对手
            pubQuarterVo.setTranType(insAllbustraninfo.getTranType());   //交易类型
            pubQuarterVo.setTradeAmount(String.valueOf(insAllbustraninfo.getTradeAmount()));//交易金额
            pubQuarterVo.setRemark(insAllbustraninfo.getRemark());
            quarterVos.add(pubQuarterVo);
        }
        page.setResult(quarterVos);
        page.setTotal(quarterVos.size());
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;

    }


    @RequestMapping(value = "/disclosure/queryList")
    @Log(module = "披露管理", description = "查询生成披露文件列表")
    @ResponseBody
    public Map<String, Object> QueryList(Page<PubYearVo> page, Model model, HttpServletRequest request, List<Filter> filters, List<Order> orders) {


        Map<String, Object> map = new HashMap<>();
        orders.add(new Order("modifiedTime", OrderType.DESC));
        filters.add(new Filter("delflag", "1"));
        //查询登陆人机构及下辖机构的信息
        UserProfile profile = UserProfileUtils.getUserProfile();
        AuthorityUtil authorityUtil = new AuthorityUtil();
        filters = authorityUtil.getFilterByRole(filters);
        List<String> codes = unitService.selectUnitAllOrDeptAllCodes(profile.getUnitCode());
        List<String> mgerOrgs = codes.stream().distinct().collect(Collectors.toList());
        filters.add(new Filter("tradeOrg", Operator.IN, mgerOrgs));


        Page<TraInvestment> traInvestmentPage = new Page<>();
        traInvestmentPage.setTotal(page.getTotal());
        traInvestmentPage.setPageSize(page.getPageSize());

        List<TraInvestment> traInvestmentList = traInvestmentService.select(filters, orders);
//        traInvestmentService.query(page, filters, orders);
//        page.setResult(traInvestmentList);
        map.put(RESULT_TOTAL, traInvestmentPage.getTotal());
        map.put(RESULT_ROWS, traInvestmentList);


        return map;
    }

    @RequestMapping(value = "/disclosure/selectPubfile")
    @Log(module = "披露管理", description = "查询披露模板文件列表")
    @ResponseBody
    public Map<String, Object> DisclosureQueryList(Model model) {

        Map<String, Object> result = new HashMap<>();
        List<PubFileTemplate> pubFileTemplateList = pubFileTemplateService.selectAll();

        result.put("pubFileTemplateList", pubFileTemplateList);

        return result;
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Publog> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(Filter.eq("delflag", "1"));
        String pubType = request.getParameter("pubType");
        filters.add(Filter.eq("pubType", pubType));
        orders.add(Order.desc("maxPercentInNetAssets"));

        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
        String unitCode = profile.getUnitCode();

        if (unitCode.contains("0001")) {
            filters.add(new Filter("createByName", userid));
        } else {
            List<Filter> uFilters = new Filter.Builder().like("deptCode", profile.getDeptCode()).build();
            List<Staff> select = staffService.select(uFilters);
            List<String> list = new ArrayList<>();
            for (Staff s : select) {
                String deptName = s.getDeptName();
                list.add(deptName);
            }
            filters.add(new Filter("pubSubject", Operator.NE, list));
        }

        publogService.query(page, filters, orders);

    }

    /**
     * 逐笔披露点击新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {
        /*UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();*/
        return "pub/publogAdd";
    }

    /**
     * 定期新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/addtime")
    public String addtime(Model model) {

        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || tenantId == "") {
            model.addAttribute("subOrg", "");
        } else {
            model.addAttribute("subOrg", tenantId);
        }

        return "pub/publogtimeAdd";
    }

    @ResponseBody
    @RequestMapping("/queryData")
    public Map<String, Object> queryData(Page<PubData> page, Model model, HttpServletRequest request) {
        Map<String, Object> tips = new HashMap<>();
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("isDiscovery", "0"));
        List<PubData> pubDataList = new LinkedList<>();
        //投资入股类
        List<InsInvestment> insInvestments = null;
        try {
            insInvestments = insInvestmentService.select(filters);
        } catch (ServiceException e) {
            logger.error("投资入股类数据错误");
            e.printStackTrace();
        }
        if (insInvestments != null && insInvestments.size() != 0) {
            for (InsInvestment insInvestment : insInvestments) {
                PubData pubData = new PubData();
                pubData.setPid(insInvestment.getPid());
                pubData.setRelName(insInvestment.getRelName());//关联方名称
                pubData.setRelType(insInvestment.getRelType());//关联方类别
                pubData.setRelNumber(insInvestment.getRelNumber());//关联方证件号码
                pubData.setTranType(insInvestment.getTranType());//交易类别
                pubData.setTradeAmount(insInvestment.getTradeAmount());//关联方金额
                pubData.setIsAgreement("0");//是否是统一交易协议
                pubData.setTradeSubject(insInvestment.getTradeSubject());//交易标的
                pubDataList.add(pubData);
            }
        }
        //资金运用类
        List<InsFunds> fundsList = null;
        try {
            fundsList = insFundsService.select(filters);
        } catch (ServiceException e) {
            logger.error("资金运用类数据错误");
            e.printStackTrace();
        }
        if (fundsList != null && fundsList.size() != 0) {
            for (InsFunds insFunds : fundsList) {
                PubData pubData = new PubData();
                pubData.setPid(insFunds.getPid());
                pubData.setRelName(insFunds.getRelName());//关联方名称
                pubData.setRelType(insFunds.getRelType());//关联方类别
                pubData.setRelNumber(insFunds.getRelNumber());//关联方证件号码
                pubData.setTranType(insFunds.getTranType());//交易类别
                pubData.setTradeAmount(insFunds.getTradeAmount());//关联方金额
                pubData.setIsAgreement("0");//是否是统一交易协议
                pubData.setTradeSubject(insFunds.getTradeSubject());//交易标的
                pubDataList.add(pubData);
            }
        }
        //保险业务类
        List<InsBusiness> insBusinessList = null;
        try {
            insBusinessList = insBusinessService.select(filters);
        } catch (ServiceException e) {
            logger.error("保险业务类数据错误");
            e.printStackTrace();
        }
        if (insBusinessList != null && insBusinessList.size() != 0) {
            for (InsBusiness business : insBusinessList) {
                PubData pubData = new PubData();
                pubData.setPid(business.getPid());
                pubData.setRelName(business.getRelName());//关联方名称
                pubData.setRelType(business.getRelType());//关联方类别
                pubData.setRelNumber(business.getRelNumber());//关联方证件号码
                pubData.setTranType(business.getTranType());//交易类别
                pubData.setTradeAmount(business.getTradeAmount());//关联方金额
                pubData.setIsAgreement(business.getIsAgreement());//是否是统一交易协议
                pubData.setTradeSubject(business.getTradeSubject());//交易标的
                pubDataList.add(pubData);
            }
        }
        //利益转移类
        List<InsInterest> insInterestList = null;
        try {
            insInterestList = insInterestService.select(filters);
        } catch (ServiceException e) {
            logger.error("利益转移类数据错误");
            e.printStackTrace();
        }
        if (insInterestList != null && insInterestList.size() != 0) {
            for (InsInterest insInterest : insInterestList) {
                PubData pubData = new PubData();
                pubData.setPid(insInterest.getPid());
                pubData.setRelName(insInterest.getRelName());//关联方名称
                pubData.setRelType(insInterest.getRelType());//关联方类别
                pubData.setRelNumber(insInterest.getRelNumber());//关联方证件号码
                pubData.setTranType(insInterest.getTranType());//交易类别
                pubData.setTradeAmount(insInterest.getTradeAmount());//关联方金额
                pubData.setIsAgreement(insInterest.getIsAgreement());//是否是统一交易协议
                pubData.setTradeSubject(insInterest.getTradeSubject());//交易标的
                pubDataList.add(pubData);
            }
        }
        //提供货物类
        List<InsService> insServiceList = null;
        try {
            insServiceList = insServiceService.select(filters);
        } catch (ServiceException e) {
            logger.error("提供货物类数据错误");
            e.printStackTrace();
        }
        if (insServiceList != null && insServiceList.size() != 0) {
            for (InsService insService : insServiceList) {
                PubData pubData = new PubData();
                pubData.setPid(insService.getPid());
                pubData.setRelName(insService.getRelName());//关联方名称
                pubData.setRelType(insService.getRelType());//关联方类别
                pubData.setRelNumber(insService.getRelNumber());//关联方证件号码
                pubData.setTranType(insService.getTranType());//交易类别
                pubData.setTradeAmount(insService.getTradeAmount());//关联方金额
                pubData.setIsAgreement(insService.getIsAgreement());//是否是统一交易协议
                pubData.setTradeSubject(insService.getTradeSubject());//交易标的
                pubDataList.add(pubData);
            }
        }
        //其他类
        List<InsOther> insOtherList = null;
        try {
            insOtherList = insOtherService.select(filters);
        } catch (ServiceException e) {
            logger.error("其他类数据错误");
            e.printStackTrace();
        }
        if (insOtherList != null && insOtherList.size() != 0) {
            for (InsOther insOther : insOtherList) {
                PubData pubData = new PubData();
                pubData.setPid(insOther.getPid());
                pubData.setRelName(insOther.getRelName());//关联方名称
                pubData.setRelType(insOther.getRelType());//关联方类别
                pubData.setRelNumber(insOther.getRelNumber());//关联方证件号码
                pubData.setTranType(insOther.getTranType());//交易类别
                pubData.setTradeAmount(insOther.getTradeAmount());//关联方金额
                pubData.setIsAgreement("0");//是否是统一交易协议
                pubData.setTradeSubject(insOther.getTradeSubject());//交易标的
                pubDataList.add(pubData);
            }

        }
        page.setResult(pubDataList);
        page.setTotal(pubDataList.size());
        tips.put(RESULT_TOTAL, page.getTotal());
        tips.put(RESULT_ROWS, page.getResult());
        return tips;
    }

    /**
     * 生成披露文件页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/pubFileAdd")
    @Log(module = "披露年度信息新增", description = "跳转生成披露文件页面")
    public String pubFileAdd(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            model.addAttribute("pubSubject", "");
        } else {
            model.addAttribute("pubSubject", tenantId);
        }
        return "pub/publogAdd";
    }

    /**
     * 生成xls表格
     *
     * @param bean
     * @param id
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/exportXls/{bean}/{id}", method = {RequestMethod.POST})
    @Log(module = "披露管理中的披露年度信息", description = "生成披露年度报表")
    @ResponseBody
    public Map<String, Object> exportXls(@PathVariable String bean, @PathVariable String id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //参数为空 则不处理
        if (StringUtils.isNotBlank(bean) && StringUtils.isNotBlank(id)) {
            //获取bean
            XlsExportCallback eCallback = (XlsExportCallback) SpringContextHolder.getBean(bean);
            try {
                //请求参数上下文
                Map parameters = new HashMap();
                parameters.put("id", id);//添加请求中的路径参数
                Enumeration e = (Enumeration) request.getParameterNames();
                while (e.hasMoreElements()) {
                    String parameterName = (String) e.nextElement();
                    if (StringUtils.isNotBlank(parameterName)) {
                        parameters.put(parameterName, request.getParameter(parameterName));
                    }
                }

                String templateName = eCallback.getExportTemplate();
                String filename = eCallback.getExportFilename(parameters);
                if (StringUtils.isBlank(filename)) {
                    filename = templateName.substring(templateName.lastIndexOf("/") + 1);
                }
                //调用业务方法,组装导出XLS报表所需数据
                Map context = eCallback.executeExportCallback(parameters);
                if (context.size() <= 0 || context.isEmpty()) {
                    logger.info("披露年度信息未查询到数据！");
                    map.put(WebCode.SUCCESS, true);
                    map.put(WebCode.MSG, "披露年度未查询到数据");
//					context = eCallback.executeExportCallback(request);
                } else {
                    configs = SpringContextHolder.getBean(Configs.class);
                    String filePath = configs.getFilePathDown();
                    filePath = filePath + "/" + filename;
                    OutputStream out = new FileOutputStream(new File(filePath));
//				OutputStream out = new FileOutputStream(new File("F:\\credit\\" + filename  ));
                    XlsTransformer4Merge transformer = new XlsTransformer4Merge();
                    transformer.setBeanId(bean);
                    transformer.transformXLS(templateName, context, out);
                    if (null != context) {
                        context.clear();
                    }
                    if (null != parameters) {
                        parameters.clear();
                    }
                    logger.info("披露年度信息文件生成成功！");
                    map.put(WebCode.SUCCESS, true);
                    map.put(WebCode.MSG, WebCode.FAILURE_SUCCESS_PUBFILE);
                }
            } catch (Exception e) {
                logger.info("披露年度信息文件生成失败！");
                e.printStackTrace();
                logger.info(e.getMessage());
                map.put(WebCode.SUCCESS, false);
                map.put(WebCode.MSG, WebCode.FAILURE_FAILED_PUBFILE);
            }
        }
        return map;
    }

    @RequestMapping(value = "/exportXlsQuarter/{bean}/{data}", method = {RequestMethod.POST})
    @Log(module = "披露管理中的披露季度合并信息", description = "生成披露季度合并报表")
    @ResponseBody
    public Map<String, Object> exportXlsQuarter(@PathVariable String bean, @PathVariable String data, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //参数为空 则不处理
        if (StringUtils.isNotBlank(bean) && StringUtils.isNotBlank(data)) {
            //获取bean
            XlsExportCallback eCallback = (XlsExportCallback) SpringContextHolder.getBean(bean);
            try {
                //请求参数上下文
                Map parameters = new HashMap();
                parameters.put("data", data);//添加请求中的路径参数
                Enumeration e = (Enumeration) request.getParameterNames();
                while (e.hasMoreElements()) {
                    String parameterName = (String) e.nextElement();
                    if (StringUtils.isNotBlank(parameterName)) {
                        parameters.put(parameterName, request.getParameter(parameterName));
                    }
                }

                String templateName = eCallback.getExportTemplate();
                String filename = eCallback.getExportFilename(parameters);
                if (StringUtils.isBlank(filename)) {
                    filename = templateName.substring(templateName.lastIndexOf("/") + 1);
                }
                //调用业务方法,组装导出XLS报表所需数据
                Map context = eCallback.executeExportCallback(parameters);
                if (context == null) {
                    context = eCallback.executeExportCallback(request);
                }
                configs = SpringContextHolder.getBean(Configs.class);
                String filePath = configs.getFilePathDown();
                OutputStream out = new FileOutputStream(new File(filePath + filename));
//				OutputStream out = new FileOutputStream(new File("F:\\credit\\" + filename  ));
                XlsTransformer4Merge transformer = new XlsTransformer4Merge();
                transformer.setBeanId(bean);
                transformer.transformXLS(templateName, context, out);

                if (null != context) {
                    context.clear();
                }
                if (null != parameters) {
                    parameters.clear();
                }
                logger.info("披露季度合并信息文件生成成功！");
                map.put(WebCode.SUCCESS, true);
                map.put(WebCode.MSG, WebCode.FAILURE_SUCCESS_PUBFILE);
            } catch (Exception e) {
                logger.info("披露季度合并信息文件生成失败！");
                e.printStackTrace();
                logger.info(e.getMessage());
                map.put(WebCode.SUCCESS, false);
                map.put(WebCode.MSG, WebCode.FAILURE_FAILED_PUBFILE);
            }
        }
        return map;
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

        Publog publog = publogService.get(pid);
        model.addAttribute("publog", publog);
        model.addAttribute("opt", "edit");

        return "pub/publogEdit";
    }

    /**
     * 查看详情
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model) {

        Publog publog = publogService.get(pid);
        model.addAttribute("publog", publog);

        return "pub/publogView";
    }

    //PDF文件预览
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    @Log(module = "披露年度信息", description = "查看披露文件 PDF预览")
    public void pdfStreamHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = "";
        String pdfFileName = "";
        String pdfPath = "";
        String pidStr = request.getParameter("pid");
        if (com.yzj.jep.commons.StringUtils.isEmpty(pidStr)) {
            return;
        }
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.EQ, Integer.valueOf(pidStr)));
        List<Publog> publogs = publogService.select(filters);
        logger.info("获取配置文件中的路径！");
        configs = SpringContextHolder.getBean(Configs.class);
        String filePath = configs.getFilePathDown();
        logger.info("获取配置文件中的路径为：" + filePath);
//		String filePath="F:\\credit\\";
        if (publogs != null && publogs.size() > 0) {
            fileName = publogs.get(0).getPubFileName();
            logger.info("通过pid获取对一个的文件名称：" + fileName);
            if (fileName != null && fileName.endsWith(".xls")) {
                pdfFileName = fileName.replace(".xls", ".pdf");
            } else {
                logger.info("文件不存在，或文件不是以.xls结尾的！");
                return;
            }
            pdfPath = filePath + File.separator + pdfFileName;
            filePath = filePath + File.separator + fileName;
            logger.info("生成pdf文件路径：" + pdfPath + ",生成xls文件路径：" + filePath);
        }
        ExcelToPdf.excelToPdf(filePath, pdfPath);
        File file = new File(pdfPath);
        if (file.exists()) {
            byte[] data = null;
            try {
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(pdfFileName, "UTF-8"));
                FileInputStream input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
                logger.info("要查看的pdf文件不存在");
                System.out.println("pdf文件处理异常：" + e);
            }

        } else {
            return;
        }
    }

    /**
     * 保存
     *
     * @param publog
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(Publog publog) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = publogService.exists(publog.getPid());
        try {
            if (isExist) {

                publogService.update(publog);
            } else {
                publogService.insert(publog);
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
    @Log(module = "披露年度信息", description = "删除相关pid对应的信息")
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            logger.info("披露文件删除开始。。。");
            Publog publog = publogService.get(pid);
            revocatory(publog.getTranType(),publog.getPubId());
            publogService.deleteByPrimaryKey(pid);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            logger.info("披露文件删除结束。。。");
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }

    /**
     * 删除文件，数据反显
     *
     * @param tranType
     * @param eq
     */
    public void revocatory(String tranType, String eq) {
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("pubId", eq));
        switch (tranType) {
            case "01":
                InsInvestment insInvestment = insInvestmentService.get(filters);
                insInvestment.setIsDiscovery("0");
                insInvestmentService.updateSelective(insInvestment);
                break;
            case "02":
                InsFunds insFunds = insFundsService.get(filters);
                insFunds.setIsDiscovery("0");
                insFundsService.updateSelective(insFunds);
                break;
            case "03":
                InsBusiness insBusiness = insBusinessService.get(filters);
                insBusiness.setIsDiscovery("0");
                insBusinessService.updateSelective(insBusiness);
                break;
            case "04":
                InsInterest insInterest = insInterestService.get(filters);
                insInterest.setIsDiscovery("0");
                insInterestService.updateSelective(insInterest);
                break;
            case "05":
                InsService insService = insServiceService.get(filters);
                insService.setIsDiscovery("0");
                insServiceService.updateSelective(insService);
                break;
            case "06":
                InsOther insOther = insOtherService.get(filters);
                insOther.setIsDiscovery("0");
                insOtherService.updateSelective(insOther);
                break;
            case "09":
                List<InsAllbustraninfo> insAllbustraninfos = insAllbustraninfoService.select(filters);
                for (InsAllbustraninfo insAllbustraninfo : insAllbustraninfos) {
                    insAllbustraninfo.setIsDiscovery("0");
                    insAllbustraninfoService.updateSelective(insAllbustraninfo);
                }
                break;

        }
    }

    /**
     * 获取审批状态
     *
     * @return
     */
    @RequestMapping(value = "/getApprovalLog")
    @ResponseBody
    @Log(module = "获取OA审批意见", description = "获取pid对应的OA审批意见")
    public Map<String, Object> getApprovalLog() {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("status", Operator.EQ, "03"));
        try {
            List<Publog> publogs = publogService.select(filters);
            for (Publog s : publogs) {
                Map<String, Object> map = getApprovalLogUtil(s.getPid());
                if (null != map && !(Boolean) map.get(WebCode.SUCCESS)) {
                    return map;
                }
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (Exception e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return tips;
    }

    public Map<String, Object> getApprovalLogUtil(Integer pid) throws Exception {
        Map<String, Object> tips = new HashMap<String, Object>();
        Message msg = null;
        Message resmsg = null;
        Map<String, Object> bodys = new HashMap<String, Object>();
        Map<String, Object> headers = new HashMap<String, Object>();
        List<PubOaApprovalLogVo> pubOaApprovalLogVoList;
        Publog publog = publogService.get(pid);
        String OAApprovaId = publog.getApprovalId();
        if (null == OAApprovaId || "".equals(OAApprovaId)) {
            return null;
        }
        List<Filter> filters = new Filter.Builder().eq("approvalId", OAApprovaId).build();
        //以推送数据从oa获取审批记录，以报送数据直接从日志表中获取审批记录
        if (!oaApprovalLogService.exists(filters)) {
            logger.info("获取OA审批意见开始。。。");
            //全局唯一流水号
            String transSeqNo =
                    "99" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "0"
                            + String.format("%09d", new Random().nextInt(999999999));
            bodys.put("id", OAApprovaId);
            bodys.put("postType", HttpConstans.GET_WITH_PARM);
            msg = MessageFactory.create(transSeqNo, MessageFactory.createSimpleMessageInstance(),
                    MessageFactory.createSimpleMessage(headers, bodys),
                    FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));
            resmsg = (Message) OAContractGatewayClientHandler.handle(msg);
            Map<String, Object> map = (Map<String, Object>) resmsg.getTarget().getBodys();
            String resultCode = (String) map.get("msgCode");
            if ("0000".equals(resultCode)) {
                Object data = map.get("data");//返回的审批意见
                String str = data.toString();
                pubOaApprovalLogVoList = JSONArray.parseArray(str, PubOaApprovalLogVo.class);
                for (PubOaApprovalLogVo pubOaLog : pubOaApprovalLogVoList) {
                    OaApprovalLog oaApprovalLog = new OaApprovalLog();
                    oaApprovalLog.setOpinionSerialno(pubOaLog.getSerialno().toString());
                    oaApprovalLog.setOpinionId(pubOaLog.getId());
                    oaApprovalLog.setOpinionInfo(pubOaLog.getTitle());
                    oaApprovalLog.setOpinionUser(pubOaLog.getUserName());
                    oaApprovalLog.setOpinionTime(pubOaLog.getCreatTime());
                    oaApprovalLog.setApprovalId(OAApprovaId);
                    oaApprovalLogService.insert(oaApprovalLog);
                }
                publog.setStatus("02");
                publogService.update(publog);
            } else if ("1001".equals(resultCode)) {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.REQUEST_MISSING_PARAMETER);
            } else if ("1002".equals(resultCode)) {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.REQUEST_MISSING_NOAPPROVAL);
            } else if ("1003".equals(resultCode)) {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.REQUEST_MISSING_ANALYSIS);
            } else if ("1004".equals(resultCode)) {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.REQUEST_MISSING_MOREFILE);
            } else if ("1005".equals(resultCode)) {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.REQUEST_MISSING_UNKNOWN);
            }
            logger.info("获取OA审批意见结束。。。");
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

        boolean exist = publogService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /*
     * 提交审批
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Publog publog = publogService.get(pid);
        if ("01".equals(publog.getStatus()) || "04".equals(publog.getStatus())) {
            //4 代表审批中
            publog.setStatus("02");
            publogService.update(publog);
            tips.put("data", publog);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }

    /*
     * 提交审批
     * */
    @RequestMapping(value = "/toConfirm/{pid}/{archDate}")
    @ResponseBody
    public Map<String, Object> toConfirm(@PathVariable Integer pid, @PathVariable Date archDate, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Publog publog = publogService.get(pid);
        if ("0".equals(publog.getStatus())) {
            publog.setStatus("1");//已披露
            publog.setArchDate(archDate);
            publogService.update(publog);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "确认成功");
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "披露失败");
        }
        return tips;
    }


    /**
     * 披露管理文件下载
     *
     * @param pid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/subDownFile/{pid}")
    @ResponseBody
    @Log(module = "披露年度信息", description = "下载生成的披露年度文件信息")
    public Map<String, Object> subDownFile(@PathVariable Integer pid, HttpServletRequest request, HttpServletResponse response) {
        logger.info("下载生成的披露年度文件信息开始。。。：");
        Map<String, Object> tipes = Maps.newHashMap();
        int count = 0;
        String pubFileName = null;
        //通过pid查询publog记录表  获取下载文件的名称和下载次数
        Publog publog = publogService.get(pid);
        if (publog != null) {
            count = publog.getDownAmt();
            pubFileName = publog.getPubFileName();
        }
        //下载服务器的路径  后期在publog记录表中添加保存路径字段
        configs = SpringContextHolder.getBean(Configs.class);
        String filePath = configs.getFilePathDown();
        String serverUrl = filePath + File.separator + pubFileName;
//		String serverUrl = "/home/rptrs/subFile/" + pubFileName ;
//		String serverUrl = "F:\\credit\\"+pubFileName;
        //浏览器下载
        boolean aaa = DownFileUtils.downServerFile(serverUrl, pubFileName, request, response);
        if (aaa) {
            logger.info("下载文件成功！");
            publog.setPid(Integer.valueOf(pid));
            publog.setDownStatus("0"); //如果下载成功 更新为已下载  0-是  1-否
            publog.setDownAmt(count + 1);//下载成功 下载次数加1
            publogService.updateSelective(publog);
            tipes.put(WebCode.SUCCESS, true);
            tipes.put(WebCode.MSG, WebCode.DOWN_SUCCESS_FILE);
        } else {
            logger.info("下载文件失败！");
            tipes.put(WebCode.SUCCESS, false);
            tipes.put(WebCode.MSG, WebCode.DOWN_FAILED_FILE);
        }
        return tipes;
    }

    public String selectByValue(String value, String simpleName) {
        List<NameValuePair> items = new ArrayList<>();
        try {
            List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
            List<DictItem> list = dictItemService.select(filters);
            //过滤掉不要的字典项
            if (!org.springframework.util.CollectionUtils.isEmpty(list)) {
                for (DictItem dictItem : list) {
                    if (dictItem.getItemValue().equals(value)) {
                        return dictItem.getItemLabel();
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取季度报送数据的年度与季度
     *
     * @param nowDate
     * @return
     */
    public Map<String, Object> getDate(Date nowDate) {
        HashMap<String, Object> map = Maps.newHashMap();
        int years = DateUtil.year(nowDate);
        int mouth = DateUtil.quarter(nowDate);
        String quarter = null;
        switch (mouth) {
            case 1:
            case 2:
            case 3:
                quarter = "4";
                years = years - 1;
                //第一季度报送上一年的第四季度
                break;
            case 4:
            case 5:
            case 6:
                quarter = "1";
                //第二季度报送第一季度
                break;
            case 7:
            case 8:
            case 9:
                quarter = "2";
                //第三季度报送第二季度
                break;
            case 10:
            case 11:
            case 12:
                quarter = "3";
                //第四季度报送第三季度
                break;
        }
        map.put("year", years + "");
        map.put("quarter", quarter);
        return map;
    }

    /**
     * 生成季度披露文件
     *
     * @param model
     * @param subType
     * @param subYear
     * @param subQuarter
     * @param subOrg
     * @param fileName
     * @param filePid
     * @param request
     * @return
     */
    @RequestMapping(value = "/quaterFile")
    @ResponseBody
    public Map<String, Object> quaterFile(Model model, String subType, String subYear, String subQuarter, String subOrg, String fileName, Integer filePid, HttpServletRequest request) {
        Map<String, Object> tips = Maps.newHashMap();
        //获取当前登录用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        //主体全称
        String tranName = Commons.getTenantNameByTenantId(profile.getTenantId());
        String eq = null;
        try {
            PubFileTemplate pubFileTemplate = pubFileTemplateService.selectById(filePid);
            //模板文件地址
            String inputUrl = pubFileTemplate.getTempFileUrl();
            //拿文件后缀 String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String suffix = inputUrl.substring(inputUrl.lastIndexOf("."));
            //新生产的模板文件
            String outputUrl = disclosePath + "/" + fileName + suffix;
            File dest = new File(outputUrl);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }

            /*
             * 测试部分开始 wanghui
             *
             * */

            //需要进行文本替换的信息
            /*Map<String, Object> data = new HashMap<String, Object>();
            data.put("${date}", "2018-03-06");
            data.put("${name}", "东方明珠");

            //需要进行动态生成的信息
            List<Object> mapList = new ArrayList<Object>();

            //第一个动态生成的数据列表
            List<String[]> list01 = new ArrayList<String[]>();
            list01.add(new String[]{"A", "11111111111", "22", "22"});
            list01.add(new String[]{"A", "22222222222", "33", "22"});
            list01.add(new String[]{"B", "33333333333", "44", "22"});
            list01.add(new String[]{"C", "44444444444", "55", "22"});

            //第二个动态生成的数据列表
            List<String[]> list02 = new ArrayList<String[]>();
            list02.add(new String[]{"01", "11111111111", "221", "ss23ad", "a22sdsaf", "2w32wqwd"});
            list02.add(new String[]{"02", "11111111111", "222", "ssewad", "asd43saf", "2wwq21wd"});
            list02.add(new String[]{"03", "11111111111", "223", "ssead", "as4dsaf", "2ww3qwd"});
            list02.add(new String[]{"04", "11111111111", "224", "sssdxad", "asdsaf", "2wwq12wd"});
            list02.add(new String[]{"05", "11111111111", "225", "ssaxcd", "asds23af", "2ww34qwd"});
            list02.add(new String[]{"06", "11111111111", "226", "ssgfdad", "asd232saf", "2wwrwqwd"});
            list02.add(new String[]{"07", "11111111111", "227", "sdsvsad", "asdsaf", "2www22qwd"});

            mapList.add(list01);
            mapList.add(list02);

            //需要动态改变表格的位置；第一个表格的位置为0
            int[] placeList = {0, 1};

            Boolean flag = WorderToNewWordUtils.changWord(inputUrl, outputUrl, data, mapList, placeList);*/

            /*
             * 测试部分结束 wanghui
             *
             * */

            Map<String, Object> testMap = new HashMap<>();
            List<Map<String, Object>> tranDescList = new ArrayList();//新增的段落信息
            List<Object> testList = new ArrayList<>();//需要插入的表格信息集合
            List<String[]> list = new ArrayList<String[]>();

            List<Filter> filters = new ArrayList<>();
            filters = RequestUtils.buildFilters(request);
            filters.add(Filter.eq("delflag", "1"));
            filters.add(Filter.eq("isDiscovery", "0"));

            List<InsAllbustraninfo> insAllbustraninfos = insAllbustraninfoService.select(filters);
            if ("".equals(eq) || eq == null) {
                //生成流水号
                eq = BatchNumberUtil.makeBatchNumber();
            }
            testMap.put("transOrg", tranName);

            testMap.put("title", subYear + "年" + subQuarter + "季度关联交易分类合并信息披露公告");
            testMap.put("date", "报告期间：" + subYear + "年 " + subQuarter + "季度");
            testMap.put("content", "根据银保监会《保险公司关联交易管理办法》（银保监发〔2019〕35号）及相关规定，现将" + tranName + "（以下简称“本公司”）" + subYear + " 年第" + subQuarter + "季度需分类合并披露的关联交易信息及统一交易协议执行情况披露如下：");
            testMap.put("report", "报告人：" + tranName);
            int no = 1;
            for (InsAllbustraninfo insAllbustraninfo : insAllbustraninfos) {

                StringBuilder stringBuilder = new StringBuilder();
                String num = String.valueOf(no++);//序号
                String relName = insAllbustraninfo.getRelName();//关联方
                List<Filter> filterOrgPsn = new ArrayList<>();
                String queryRelation = null;
                if (insAllbustraninfo.getRelType().equals("ORG")) {
                    filterOrgPsn.add(Filter.eq("orgName", relName));
                    filterOrgPsn.add(Filter.eq("uniscCode", insAllbustraninfo.getRelNumber()));
                    RelOrg relOrg = relOrgService.get(filterOrgPsn);
                    queryRelation = queryRelation(stringBuilder, relOrg, tranName);//关联关系说明
                } else {
                    filterOrgPsn.add(Filter.eq("name", relName));
                    filterOrgPsn.add(Filter.eq("identifyNumber", insAllbustraninfo.getRelNumber()));
                    RelPsn psn = relPsnService.get(filterOrgPsn);
                    queryRelation = queryPsnRelation(stringBuilder, psn, tranName);
                }
                String transType = selectByValue(insAllbustraninfo.getTranType(), "INS-tranType");//交易类型
                String byValue = null;  //业务类型
                switch (insAllbustraninfo.getTranType()) {
                    case "01":
                        byValue = selectByValue(insAllbustraninfo.getInvestmentType(), "INS-InvestmentType");
                        break;
                    case "02":
                        byValue = selectByValue(insAllbustraninfo.getInvestmentType(), "INS-FundsType");
                        break;
                    case "03":
                        byValue = selectByValue(insAllbustraninfo.getInvestmentType(), "INT-InsBusinessType");
                        break;
                    case "04":
                        byValue = selectByValue(insAllbustraninfo.getInvestmentType(), "INT-BusinessType");
                        break;
                    case "05":
                        byValue = selectByValue(insAllbustraninfo.getInvestmentType(), "INS-ServiceType");
                        break;
                    case "06":
                        byValue = selectByValue(insAllbustraninfo.getInvestmentType(), "INS-otherType");
                        break;
                    default:
                        byValue = null;
                }

                String tradeAmount = String.valueOf(insAllbustraninfo.getTradeAmount().setScale(2, BigDecimal.ROUND_HALF_UP));//交易金额
                String[] strings = {num, relName, queryRelation, transType, byValue, tradeAmount};
                list.add(strings);
            }
            testList.add(list);
            //统一交易协议情况下
            List<Map<String, Object>> allbustraninfos = insAllbustraninfoService.selectAllTrans(subYear, subQuarter, profile.getTenantId());
            for (Map<String, Object> mapall : allbustraninfos) {
                Map<String, Object> map = new HashMap();
                int year = getYear((Date) mapall.get("busStartTime"));
                int month = getMonth((Date) mapall.get("busStartTime"));
                int day = getDay((Date) mapall.get("busStartTime"));
                String s = appendTransDesc(year, month, day, String.valueOf(mapall.get("relName")), subQuarter, String.valueOf(mapall.get("toltAmount")));
                map.put("tranDescList", s);
                tranDescList.add(map);
            }
            int[] placeList = {0, 1};

            Boolean changWord = WorderToNewWordUtils.changWord(inputUrl, outputUrl, testMap, testList, placeList, tranDescList);

            if (changWord) {
                try {
                    setPubStu(insAllbustraninfos, eq);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //查询
                Publog publog = new Publog();
                //新增
                publog.setPubFileName(fileName + suffix);
                publog.setPubSubject(UserProfileUtils.getUserProfile().getDeptName());
                publog.setCreateByName(UserProfileUtils.getUserProfile().getStaffName());//操作员
                publog.setMaxPercentInNetAssets(new Date());
                publog.setDownAmt(0);
                publog.setPubId(eq);
                publog.setTenantId(profile.getTenantId());
                publog.setStatus("0");
                publog.setDownStatus("1");
                publog.setTranType(subType);
                publog.setDelflag("1");
                publog.setPubType("2");//披露类型
                publog.setFileUrl(outputUrl);
                publog.setPubYear(subYear);
                publog.setPubQuarter(subQuarter);

                int i = publogService.insert(publog);

                if (i > 0) {
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, WebCode.FAILURE_SUCCESS_PUBFILE);
                    return tips;
                }

            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        tips.put(WebCode.SUCCESS, false);
        tips.put(WebCode.MSG, WebCode.FAILURE_FAILED_PUBFILE);

        return tips;
    }

    /**
     * 更新季报表披露状态
     *
     * @param allbustraninfos
     */
    public void setPubStu(List<InsAllbustraninfo> allbustraninfos, String eq) {
        for (InsAllbustraninfo allbustraninfo : allbustraninfos) {
            allbustraninfo.setIsDiscovery("1");
            allbustraninfo.setPubId(eq);
            insAllbustraninfoService.updateSelective(allbustraninfo);
        }
    }

    /**
     * 统一交易协议情况下
     *
     * @param year
     * @param month
     * @param day
     * @param relName
     * @param subQuarter
     * @param tradeAmount
     * @return
     */
    public String appendTransDesc(int year, int month, int day, String relName, String subQuarter, String tradeAmount) {
        StringBuilder appendStr = new StringBuilder("");
        appendStr.append(year)
                .append("年")
                .append("与").append(relName).append("签订了《xx合同》，自")
                .append(year)
                .append("年")
                .append(month)
                .append("月")
                .append(day)
                .append("日起生效。根据该合同相关费用计提规则，本公司")
                .append(year)
                .append("年")
                .append(subQuarter)
                .append("季度当期发生xx费共计")
                .append(tradeAmount).append("万元。");
        return appendStr.toString();
    }

    /**
     * 关联关系说明
     * ORG
     *
     * @param stringBuilder
     * @param relOrg
     * @param orgName
     * @return
     */
    public String queryRelation(StringBuilder stringBuilder, RelOrg relOrg, String orgName) {
        List<Filter> filters = null;
        List<String> list = relService.selectByFormPid(relOrg.getPid().toString());//获取上层机构id
        if (list.size() == 0 || list == null) {
            return stringBuilder.toString();
        }

        for (String s : list) {
            RelPsn psn = new RelPsn();
            RelOrg org = relOrgService.get(Integer.valueOf(s));//获取上层机构
            if (org == null) {
                psn = relPsnService.get(Integer.valueOf(s));
                filters = new Filter.Builder().eq("fromIdentifyNumber", relOrg.getUniscCode()).eq("toIdentifyNumber", psn.getIdentifyNumber()).build();
            } else {
                filters = new Filter.Builder().eq("fromIdentifyNumber", relOrg.getUniscCode()).eq("toIdentifyNumber", org.getUniscCode()).build();
            }
            List<Rel> rels = relService.select(filters);//获取上层机构与本层关系
            if (rels.size() == 0 || rels == null) {
                continue;
            }
            for (Rel rel : rels) {
//                String orgMap = String.valueOf(relPsnOrgMap.get(rel.getRelPsnOrg()));
                if (org != null) {
                    if (org.getOrgName().equals(orgName)) {
                        stringBuilder.append(rel.getRelStatement() + "。");
                        return stringBuilder.toString();
                    } else {
                        stringBuilder.append(rel.getRelStatement() + "；");
                        queryRelation(stringBuilder, org, orgName);
                    }
                } else {
                    stringBuilder.append(rel.getRelStatement() + "；");
                    stringBuilder.append("\r\n");
                    queryPsnRelation(stringBuilder, psn, orgName);
                }
            }
        }
        return stringBuilder.toString();

    }

    /**
     * 关联关系说明
     * PSN
     *
     * @param stringBuilder
     * @param relPsn
     * @param orgName
     * @return
     */
    public String queryPsnRelation(StringBuilder stringBuilder, RelPsn relPsn, String orgName) {
        List<Filter> filters = null;
        List<String> list = relService.selectByFormPid(relPsn.getPid().toString());//获取上层机构id
        if (list.size() == 0 || list == null) {
            return stringBuilder.toString();
        }
        for (String s : list) {
            RelOrg relOrg = new RelOrg();
            RelPsn psn = relPsnService.get(Integer.valueOf(s));//获取上层机构
            if (psn == null) {
                relOrg = relOrgService.get(Integer.valueOf(s));
                filters = new Filter.Builder().eq("fromIdentifyNumber", relPsn.getIdentifyNumber()).eq("toIdentifyNumber", relOrg.getUniscCode()).build();
            } else {
                filters = new Filter.Builder().eq("fromIdentifyNumber", relPsn.getIdentifyNumber()).eq("toIdentifyNumber", psn.getIdentifyNumber()).build();
            }
            List<Rel> rels = relService.select(filters);//获取上层机构与本层关系
            if (rels.size() == 0 || rels == null) {
                continue;
            }
            for (Rel rel : rels) {
                if (psn != null) {
                    stringBuilder.append(rel.getRelStatement() + "；");
                    queryPsnRelation(stringBuilder, psn, orgName);
                } else {
                    if (relOrg.getOrgName().equals(orgName)) {
                        stringBuilder.append(rel.getRelStatement() + "。");
                        return stringBuilder.toString();
                    } else {
                        stringBuilder.append(rel.getRelStatement() + "；");
                        queryRelation(stringBuilder, relOrg, orgName);
                    }
                }
            }
        }
        return stringBuilder.toString();

    }

    /**
     * 文件下载
     *
     * @param pid
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public File download(@RequestParam("pid") int pid, HttpServletResponse response, HttpServletRequest request) {
        Publog publog = publogService.selectById(pid);
        File file = new File(publog.getFileUrl());
        if (file == null || !file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        OutputStream out = null;
        try {
            String suffix = publog.getFileUrl().substring(publog.getFileUrl().lastIndexOf("."));
            String filename = publog.getPubFileName();
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes(), "ISO8859-1"));
            out = response.getOutputStream();
            out.write(FileUtils.readFileToByteArray(file));
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 在线编辑页面
     *
     * @param id
     * @param mode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editOnLine")
    public Map<String, Object> editOnLine(Integer id, String mode, String type) {
        Map<String, Object> result = new HashMap<>();
        if (id == null) {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "在线编辑页面打开失败!");
        }
        String message = fileOnLineEditClient.openEditPage(mode, String.valueOf(id), type);
        if (message != null) {
            result.put(WebCode.SUCCESS, true);
            result.put(WebCode.MSG, message);
        } else {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "在线编辑页面打开失败!");
        }
        return result;
    }

}