
package com.yzj.jep.app.sub.web.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.core.factory.FaultFactory;
import com.fPaaS1.core.factory.MessageFactory;
import com.fPaaS1.core.utils.Constants;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.fPaas.client.HttpConstans;
import com.yzj.jep.app.pub.model.OaApprovalLog;
import com.yzj.jep.app.pub.model.PubOaApprovalLogVo;
import com.yzj.jep.app.pub.service.OaApprovalLogService;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.util.EastSubTypeEnum;
import com.yzj.jep.app.util.ZipUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/sub/sublog")
public class SublogController extends CrudController<Sublog> {
    private static Logger logger = LoggerFactory.getLogger(SublogController.class);

    @Autowired
    private SublogService sublogService;

    @Autowired
    private RdsMiddatalogService rdsMiddatalogService;

    @Autowired
    private ProductViewService productViewService;

    @Autowired
    private TemplateDetailsService templateDetailsService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private InsAllbustraninfoService insAllbustraninfoService;

    @Autowired
    private IFPaaS1Handler OAContractGatewayClientHandler;

    @Autowired
    private OaApprovalLogService oaApprovalLogService;
    @Autowired
    private FinProductService finProductService;
    @Autowired
    private PersonViewService personViewService;
    @Autowired
    private PersonService personService;
    @Autowired
    private OrganService organService;
    @Autowired
    private OrganViewService organViewService;
    @Autowired
    private RelatedRelationService relatedRelationService;
    @Autowired
    RelatedRelationViewService relatedRelationViewService;
    @Autowired
    private InsInvestmentService insInvestmentService;
    @Autowired
    private InsInvestmentViewService insInvestmentViewService;
    @Autowired
    private InsFundsService insFundsService;
    @Autowired
    private InsFundsViewService insFundsViewService;
    @Autowired
    private InsBusinessService insBusinessService;
    @Autowired
    private InsBusinessViewService insBusinessViewService;
    @Autowired
    private InsInterestService insInterestService;
    @Autowired
    private InsInterestViewService insInterestViewService;
    @Autowired
    private InsServiceService insServiceService;
    @Autowired
    private InsServiceViewService insServiceViewService;
    @Autowired
    private InsOtherService insOtherService;
    @Autowired
    private InsOtherViewService insOtherViewService;
    @Autowired
    private InsAllbustraninfoViewService insAllbustraninfoViewService;
    @Autowired
    private InsAllfundsService insAllfundsService;
    @Autowired
    private InsAllfundsViewService insAllfundsViewService;
    @Autowired
    private InsRelfundsService insRelfundsService;
    @Autowired
    private InsRelfundsViewService insRelfundsViewService;
    @Autowired
    private Configs configs;
    private static final Map<String, String> subjectDate = new HashMap<>();

    static {

        /*
         * 根据不同的主体来添加不同的后缀
         * */
        subjectDate.put("FX", "复星保德信人寿保险有限公司");
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "报送管理", description = "列表数据查询页面")
    public String list(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "sub/sublog/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        String tenantId = profile.getTenantId();
        if (tenantId == null || tenantId == "") {
            model.addAttribute("subOrg", "");
        } else {
            model.addAttribute("subOrg", tenantId);
        }
        return "sub/SubTraList";
    }

    /**
     * 新增金融产品报送文件
     */
    @RequestMapping(value = "/addSubPro")
    @Log(module = "金融产品报送", description = "新增报送文件列表")
    public String addSubPro(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || tenantId == "") {
            model.addAttribute("subOrg", "");
        } else {
            model.addAttribute("subOrg", tenantId);
        }
        return "sub/addSubPro";
    }

    /**
     * 投资入股类报送获取列表页面 traInvestmentList
     */
    @RequestMapping(value = "/traInvestmentList")
    @Log(module = "单笔报送管理", description = "投资入股类列表数据查询页面")
    public String traInvestmentList(Model model) {
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "sub/sublog/traInvestmentList";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "sub/traInvestmentList";
    }


    /**
     * 投资入股类报送-确认报送
     */
    @RequestMapping(value = "/traInvestmentSubmission/{pid}")
    public String traInvestmentSubmission(@PathVariable Integer pid, Model model) {

        Sublog sublog = sublogService.get(pid);
        model.addAttribute("sublog", sublog);
        model.addAttribute("pid", pid);

        return "sub/traInvestmentSubmission";
    }


    /**
     * 投资入股类-报送日期-确定
     */
    @RequestMapping(value = "/saveTraInvestmentSubmission")
    @ResponseBody
    @Log(module = "报送管理", description = "保存数据页面")
    public Map<String, Object> saveTraInvestmentSubmission(Sublog sublog) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = sublogService.exists(sublog.getPid());
        try {
            if (isExist) {
                //修改报送状态为-已报送
                sublog.setStatus("02");
                sublogService.update(sublog);
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
     * 资金运用类报送获取列表页面 traFundsList
     */
    @RequestMapping(value = "/traFundsList")
    @Log(module = "单笔报送管理", description = "资金运用类列表数据查询页面")
    public String traFundsList(Model model) {
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "sub/sublog/traFundsList";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);

        return "sub/traFundsList";
    }


    /**
     * 资金运用类报送-确认报送
     */
    @RequestMapping(value = "/traFundsSubmission/{pid}")
    public String traFundsSubmission(@PathVariable Integer pid, Model model) {

        Sublog sublog = sublogService.get(pid);
        model.addAttribute("sublog", sublog);
        model.addAttribute("pid", pid);

        return "sub/traFundsSubmission";
    }


    /**
     * 资金运用类-报送日期-确定
     */
    @RequestMapping(value = "/saveTraFundsSubmission")
    @ResponseBody
    @Log(module = "报送管理", description = "保存数据页面")
    public Map<String, Object> saveTraFundsSubmission(Sublog sublog) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = sublogService.exists(sublog.getPid());
        try {
            if (isExist) {
                //修改报送状态为-已报送
                sublog.setStatus("02");
                sublogService.update(sublog);
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
     * 保险业务类报送获取列表页面 traBusinessList
     */
    @RequestMapping(value = "/traBusinessList")
    @Log(module = "单笔报送管理", description = "保险业务类列表数据查询页面")
    public String traBusinessList(Model model) {
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "sub/sublog/traBusinessList";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "sub/traBusinessList";
    }


    /**
     * 保险业务类报送-确认报送
     */
    @RequestMapping(value = "/traBusinessSubmission/{pid}")
    public String traBusinessSubmission(@PathVariable Integer pid, Model model) {

        Sublog sublog = sublogService.get(pid);
        model.addAttribute("sublog", sublog);
        model.addAttribute("pid", pid);

        return "sub/traBusinessSubmission";
    }


    /**
     * 保险业务类-报送日期-确定
     */
    @RequestMapping(value = "/saveTraBusinessSubmission")
    @ResponseBody
    @Log(module = "报送管理", description = "保存数据页面")
    public Map<String, Object> saveTraBusinessSubmission(Sublog sublog) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = sublogService.exists(sublog.getPid());
        try {
            if (isExist) {
                //修改报送状态为-已报送
                sublog.setStatus("02");
                sublogService.update(sublog);
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
     * 利益转移类报送获取列表页面 traInterestList
     */
    @RequestMapping(value = "/traInterestList")
    public String traInterestList(Model model) {
//        UserProfile profile = UserProfileUtils.getUserProfile();
//        String tenantId = profile.getTenantId();
//        if (tenantId == null || tenantId ==""){
//            model.addAttribute("subOrg", "");
//        }else {
//            model.addAttribute("subOrg", tenantId);
//        }
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "sub/sublog/traInterestList";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "sub/traInterestList";
    }

    /**
     * 利益转移类报送-确认报送
     */
    @RequestMapping(value = "/traInterestSubmission/{pid}")
    public String traInterestSubmission(@PathVariable Integer pid, Model model) {

        Sublog sublog = sublogService.get(pid);
        model.addAttribute("sublog", sublog);
        model.addAttribute("pid", pid);

        return "sub/traInterestSubmission";
    }


    /**
     * 利益转移类-报送日期-确定
     */
    @RequestMapping(value = "/saveTraInterestSubmission")
    @ResponseBody
    @Log(module = "报送管理", description = "保存数据页面")
    public Map<String, Object> saveTraInterestSubmission(Sublog sublog) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = sublogService.exists(sublog.getPid());
        try {
            if (isExist) {
                //修改报送状态为-已报送
                sublog.setStatus("02");
                sublogService.update(sublog);
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
     * 提供服务类报送获取列表页面 traServiceList
     */
    @RequestMapping(value = "/traServiceList")
    public String traServiceList(Model model) {
//        UserProfile profile = UserProfileUtils.getUserProfile();
//        String tenantId = profile.getTenantId();
//        if (tenantId == null || tenantId ==""){
//            model.addAttribute("subOrg", "");
//        }else {
//            model.addAttribute("subOrg", tenantId);
//        }
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "sub/sublog/traServiceList";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "sub/traServiceList";
    }


    /**
     * 提供服务类报送-确认报送
     */
    @RequestMapping(value = "/traServiceSubmission/{pid}")
    public String traServiceSubmission(@PathVariable Integer pid, Model model) {

        Sublog sublog = sublogService.get(pid);
        model.addAttribute("sublog", sublog);
        model.addAttribute("pid", pid);

        return "sub/traServiceSubmission";
    }


    /**
     * 提供服务类-报送日期-确定
     */
    @RequestMapping(value = "/saveTraServiceSubmission")
    @ResponseBody
    @Log(module = "报送管理", description = "保存数据页面")
    public Map<String, Object> saveTraServiceSubmission(Sublog sublog) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = sublogService.exists(sublog.getPid());
        try {
            if (isExist) {
                //修改报送状态为-已报送
                sublog.setStatus("02");
                sublogService.update(sublog);
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
     * 其他类报送获取列表页面 traOtherList
     */
    @RequestMapping(value = "/traOtherList")
    public String traOtherList(Model model) {
//        UserProfile profile = UserProfileUtils.getUserProfile();
//        String tenantId = profile.getTenantId();
//        if (tenantId == null || tenantId ==""){
//            model.addAttribute("subOrg", "");
//        }else {
//            model.addAttribute("subOrg", tenantId);
//        }
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "sub/sublog/traOtherList";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "sub/traOtherList";
    }


    /**
     * 其他类报送-确认报送
     */
    @RequestMapping(value = "/traOtherSubmission/{pid}")
    public String traOtherSubmission(@PathVariable Integer pid, Model model) {

        Sublog sublog = sublogService.get(pid);
        model.addAttribute("sublog", sublog);
        model.addAttribute("pid", pid);

        return "sub/traOtherSubmission";
    }


    /**
     * 其他类-报送日期-确定
     */
    @RequestMapping(value = "/saveTraOtherSubmission")
    @ResponseBody
    @Log(module = "报送管理", description = "保存数据页面")
    public Map<String, Object> saveTraOtherSubmission(Sublog sublog) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = sublogService.exists(sublog.getPid());
        try {
            if (isExist) {
                //修改报送状态为-已报送
                sublog.setStatus("02");
                sublogService.update(sublog);
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
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Sublog> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        sublogService.query(page, filters, orders);
    }

    /**
     * 单笔关联交易报送为列表页面做数据准备
     */
    @RequestMapping(value = {"/singleDeal/{type}"}, method = {RequestMethod.GET})
    @ResponseBody
    @Log(module = "报送管理", description = "{type}查询报送列表数据")
    public Map<String, Object> singleDeal(@PathVariable String type, Page<Rel> page, Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        if ("01".equals(type)) {  //投资入股类
            filters.add(new Filter("subType", "01"));
        } else if ("02".equals(type)) {
            filters.add(new Filter("subType", "02"));
        } else if ("03".equals(type)) {
            filters.add(new Filter("subType", "03"));
        } else if ("04".equals(type)) {
            filters.add(new Filter("subType", "04"));
        } else if ("05".equals(type)) {
            filters.add(new Filter("subType", "05"));
        } else if ("06".equals(type)) { //关联交易明细表（季报）
            filters.add(new Filter("subType", "06"));
        } else if ("07".equals(type)) {
            filters.add(new Filter("subType", "07"));
        } else if ("08".equals(type)) {
            filters.add(new Filter("subType", "08"));
        } else if ("09".equals(type)) {//其他类
            filters.add(new Filter("subType", "09"));
        }
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("modifiedTime", OrderType.DESC));
        PageHelper.start(page);
        List<Sublog> subLogs = sublogService.select(filters, orders);
        PageInfo pageInfo = new PageInfo(subLogs);
        map.put(RESULT_TOTAL, pageInfo.getTotal());
        map.put(RESULT_ROWS, pageInfo.getList());
        return map;
    }

    /**
     * 新增报送文件
     */
    @RequestMapping(value = "/addSubFile")
    @Log(module = "投资入股类", description = "新增报送文件列表")
    public String addSubFile(Model model) {

        return "sub/addSubFile";
    }


    /**
     * 查询报送列表数据
     *
     * @param type
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/query/{type}"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryVo(@PathVariable String type, Page<Rel> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        switch (type) {
            case "organ":
                filters.add(new Filter("subType", "0"));
                break;
            case "person":
                filters.add(new Filter("subType", "1"));
                break;
            case "rel":
                filters.add(new Filter("subType", "2"));
                break;
            case "pro":
                filters.add(new Filter("subType", "3"));
                break;
            case "credit":
                filters.add(new Filter("subType", "4"));
                break;
            case "loan":
                filters.add(new Filter("subType", "5"));
                break;
            case "noLoan":
                filters.add(new Filter("subType", "6"));
                break;
            case "balance":
                filters.add(new Filter("subType", "7"));
                break;
            default:
        }
        List<Order> orders = RequestUtils.buildOrders(request);
//        orders.add(new Order("modifiedTime"));
        orders.add(new Order("modifiedTime", OrderType.DESC));
        PageHelper.start(page);
        List<Sublog> subLogs = sublogService.select(filters, orders);
        PageHelper.end(page, subLogs);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 为金融产品新增报送文件页面准备数据
     */
    @RequestMapping(value = "/queryAdd")
    @ResponseBody
    public Map<String, Object> queryAdd(Page<InsAllbustraninfo> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        //设置查询条件 报送文件状态为待报送
        List<Filter> filters = new Filter.Builder().eq("status", "3").build();
//        if(!"".equals(subOrg) || subOrg!=null){
//            filters.add(new Filter("tenantId", Operator.EQ,subOrg));
//        }
        List<Order> orders = RequestUtils.buildOrders(request);
        List<InsAllbustraninfo> insAllbustraninfoList = insAllbustraninfoService.select(filters);
        for (InsAllbustraninfo insAllbustraninfo : insAllbustraninfoList) {
            insAllbustraninfo.setReportStatus("已报送");
        }
        PageHelper.end(page, insAllbustraninfoList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 通过报送类型查询模板pid  然后通过模板id去生成报送文件
     */
    @RequestMapping(value = "/template/{subType}/{subOrg}")
    @ResponseBody
    @Log(module = "报送管理", description = "subType通过报送类型查询模板pid  然后通过模板id去生成报送文件")
    public Map<String, Object> subProFile(Model model, @PathVariable String subType, @PathVariable String subOrg, HttpServletRequest request) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            List<Filter> filters = RequestUtils.buildFilters(request);
            List<Filter> list = new ArrayList<Filter>();
            switch (subType) {
                case "organ":
                    filters.add(new Filter("subType", "0"));

                    break;
                case "person":
                    filters.add(new Filter("subType", "1"));
                    break;
                case "relatedRelation":
                    filters.add(new Filter("subType", "2"));
                    break;
                case "pro":
                    filters.add(new Filter("subType", "3"));
                    break;
                case "credit":
                    filters.add(new Filter("subType", "4"));
                    break;
                case "loan":
                    filters.add(new Filter("subType", "5"));
                    break;
                case "noLoan":
                    filters.add(new Filter("subType", "6"));
                    break;
                case "balance":
                    filters.add(new Filter("subType", "7"));
                    break;
                default:
            }
            Template template = templateService.get(filters);
            if (template != null) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

                boolean flag = templateDetailsService.createSubmitFile(template.getPid(), list, template.getTemplateName() + sf.format(new Date()), template.getSubType());

                if (flag) {
                    logger.info("报送文件数据已生成");
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, WebCode.FAILURE_SUCCESS_FILE);
                } else {
                    logger.info("暂无报送数据生成");
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, WebCode.FAILURE_NODATA_FILE);
                }
            }
        } catch (Exception e) {
            logger.error("生成报送文件异常：", e);
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_FAILED_FILE);
        }
        return tips;
    }

    /**
     * 通过报送类型查询模板pid  然后通过模板id去生成单笔报送文件
     */
    @RequestMapping(value = "/singleFile/{subType}")
    @ResponseBody
    @Log(module = "报送管理", description = "subType通过报送类型查询模板pid  然后通过模板id去生成报送文件")
    public Map<String, Object> singleFile(Model model, @PathVariable String subType, HttpServletRequest request) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            List<Filter> filters = RequestUtils.buildFilters(request);
            List<Filter> list = new ArrayList<Filter>();
            switch (subType) {
                case "investment":
                    filters.add(new Filter("subType", "01"));
                    break;
                case "funds":
                    filters.add(new Filter("subType", "02"));
                    break;
                case "business":
                    filters.add(new Filter("subType", "03"));
                    break;
                case "interest":
                    filters.add(new Filter("subType", "04"));
                    break;
                case "service":
                    filters.add(new Filter("subType", "05"));
                    break;
                case "other":
                    filters.add(new Filter("subType", "09"));
                    break;
                default:
            }
            Template template = templateService.get(filters);
            if (template != null) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

                boolean flag = templateDetailsService.createSingleSubFile(template.getPid(), list, template.getTemplateName() + sf.format(new Date()), template.getSubType());
                if (flag) {
                    logger.info("报送文件数据已生成");
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, WebCode.FAILURE_SUCCESS_FILE);
                } else {
                    logger.info("暂无报送数据生成");
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, WebCode.FAILURE_NODATA_FILE);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_FAILED_FILE);
        }
        return tips;
    }

    /**
     * 通过报送类型查询模板pid  然后通过模板id去生成关联交易季报报送文件
     */
    @RequestMapping(value = "/quaterFile")
    @ResponseBody
    public Map<String, Object> quaterFile(Model model, String subType, String subYear, String subQuarter, HttpServletRequest request) {
        Map<String, Object> tips = Maps.newHashMap();
        Map<String, Object> date = getDate(new Date());
        if (subYear == null && subQuarter == null) {
            subYear = (String) date.get("year");
            subQuarter = (String) date.get("quarter");
        } else if (subQuarter == null) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "请先选择年度与季度");
            return tips;
        }
        try {
            List<Filter> filters = RequestUtils.buildFilters(request);
            List<Filter> list = new ArrayList<Filter>();
            switch (subType) {
                case "allbustraninfo":
                    filters.add(new Filter("subType", "06"));
                    break;
                case "allfunds":
                    filters.add(new Filter("subType", "07"));
                    break;
                case "relfunds":
                    filters.add(new Filter("subType", "08"));
                    break;
                default:
            }
            Template template = templateService.get(filters);
            if (template != null) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                boolean flag = templateDetailsService.createQuarterSubFile(template.getPid(), list, template.getTemplateName() + sf.format(new Date()), subYear, subQuarter, template.getSubType(), request);
                if (flag) {
                    logger.info("报送文件数据已生成");
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, WebCode.FAILURE_SUCCESS_FILE);
                } else {
                    logger.info("暂无报送数据生成");
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, WebCode.FAILURE_NODATA_FILE);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_FAILED_FILE);
        }
        return tips;
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
        int mouth = DateUtil.month(nowDate);
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
        map.put("year", years+"");
        map.put("quarter", quarter);
        return map;
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
            List<Sublog> sublogs = sublogService.select(filters);
            for (Sublog s : sublogs) {
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
        Sublog sublog = sublogService.get(pid);
        String OAApprovaId = sublog.getApprovalId();
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
                sublog.setStatus("02");
                sublogService.update(sublog);
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
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sub/sublogEdit";
    }

    /**
     * 新增自然人报送页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/addSubPsn")
    public String addSubPsn(Model model) {

        return "sub/addSubPsn";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    @Log(module = "报送管理", description = "编辑数据页面")
    public String edit(@PathVariable Integer pid, Model model) {

        Sublog sublog = sublogService.get(pid);
        model.addAttribute("sublog", sublog);
        model.addAttribute("opt", "edit");

        return "sub/sublogEdit";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    @Log(module = "报送管理", description = "查看数据页面")
    public String view(@PathVariable Integer pid, Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = subjectDate.get(profile.getTenantId());
        Sublog sublog = sublogService.get(pid);
        //格式化规则
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //格式化成yyyy-MM-dd格式的时间字符串
        String strDate = sdf.format(sublog.getMaxPercentInNetAssets());
        model.addAttribute("subOrg", tenantId);
        model.addAttribute("date", strDate);
        model.addAttribute("sublog", sublog);
        return "sub/SubTraView";
    }

    /**
     * 查看金融产品报送详情
     */
    @RequestMapping(value = "ProductView/{pid}")
    @ResponseBody
    @Log(module = "金融产品报送", description = "查看金融产品报送详情")
    public Map<String, Object> ProductView(@PathVariable Integer pid, Page<ProductView> page) {
        Map<String, Object> result = new HashMap<String, Object>();
        //拿到所选sublo数据
        Sublog sublog = sublogService.get(pid);
        //根据所选sublog数据得流水号到productview查找此报送文件的所有金融产品得数据
        List<Filter> filter = new Filter.Builder().eq("subSeq", sublog.getBatchDecimal()).build();
        List<ProductView> productViews = productViewService.select(filter);
        PageHelper.end(page, productViews);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 提交审批
     */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    @Log(module = "报送管理", description = "审批流程")
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Sublog sublog = sublogService.get(pid);
        if ("01".equals(sublog.getStatus()) || "04".equals(sublog.getStatus())) {
            //02 代表审批中 01待提交 03审批通过  04审批退回
            sublog.setPid(pid);
            sublog.setStatus("02");
            sublogService.updateSelective(sublog);
            tips.put("data", sublog);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }

    /**
     * 投资入股类提交审批
     */
    /*@RequestMapping(value = "/sendIns/{pid}")
    @ResponseBody
    public Map<String, Object> sendIns(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Sublog sublog = sublogService.get(pid);
        if ("01".equals(sublog.getStatus()) || "03".equals(sublog.getStatus())) {
            //4 代表审批中
            sublog.setStatus("02");
            sublogService.updateSelective(sublog);
            tips.put("data", sublog);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }*/

    /**
     * 保存
     *
     * @param sublog
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @Log(module = "报送管理", description = "保存数据页面")
    public Map<String, Object> save(Sublog sublog) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = sublogService.exists(sublog.getPid());
        try {
            if (isExist) {

                sublogService.update(sublog);
            } else {
                sublogService.insert(sublog);
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
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            Sublog sublog = sublogService.get(pid);
            List<Filter> filters = new ArrayList<>();
            filters.add(Filter.eq("subSeq", sublog.getBatchDecimal()));
            sublogService.deleteByPrimaryKey(pid);
            List<ProductView> productViews = productViewService.select(filters);
            for (ProductView productView : productViews) {
                FinProduct finProduct = finProductService.get(productView.getProtPid());
                finProduct.setStatus("3");
                finProductService.updateSelective(finProduct);
            }
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

        boolean exist = sublogService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 关联交易季报
     *
     * @return 关联交易明细表
     */
    @RequestMapping(value = "/SubTraList")
    @Log(module = "关联交易明细表", description = "列表数据展示")
    public String subTraList(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || tenantId == "") {
            model.addAttribute("subOrg", "");
        } else {
            model.addAttribute("subOrg", tenantId);
        }
        return "sub/SubTraList";
    }

    /**
     * 关联交易季报
     *
     * @return 关联交易明细表新增
     */
    @RequestMapping(value = "/addSubTra")
    @Log(module = "关联交易明细表", description = "新增报送文件列表")
    public String addSubTra(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = subjectDate.get(profile.getTenantId());
        if (tenantId == null || tenantId == "") {
            model.addAttribute("subOrg", "");
        } else {
            model.addAttribute("subOrg", tenantId);
        }
        return "sub/addSubTra";
    }

    /**
     * 关联交易季报
     *
     * @return 资金运用关联交易统计表
     */
    @RequestMapping(value = "/SubTraFundsList")
    @Log(module = "关联交易明细表", description = "列表数据展示")
    public String subTraFundsList(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || tenantId == "") {
            model.addAttribute("subOrg", "");
        } else {
            model.addAttribute("subOrg", tenantId);
        }
        return "sub/SubTraFundsList";
    }

    /**
     * 关联交易季报
     *
     * @return 资金运用关联交易统计表新增
     */
    @RequestMapping(value = "/addSubTraFunds")
    @Log(module = "资金运用关联交易统计表", description = "新增报送文件列表")
    public String addSubTraFunds(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || tenantId == "") {
            model.addAttribute("subOrg", "");
        } else {
            model.addAttribute("subOrg", tenantId);
        }
        return "sub/addSubTraFunds";
    }

    /**
     * 关联交易季报
     *
     * @return 与单一法人主体资金运用关联交易统计表
     */
    @RequestMapping(value = "/SubLegTraFundsList")
    @Log(module = "关联交易明细表", description = "列表数据展示")
    public String subLegTraFundsList(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || tenantId == "") {
            model.addAttribute("subOrg", "");
        } else {
            model.addAttribute("subOrg", tenantId);
        }
        return "sub/SubLegTraFundsList";
    }

    /**
     * 关联交易季报
     *
     * @return 与单一法人主体资金运用关联交易统计表新增
     */
    @RequestMapping(value = "/addSubLegTraFun")
    @Log(module = "关联交易明细表", description = "新增报送文件列表")
    public String addSubLegTraFun(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || tenantId == "") {
            model.addAttribute("subOrg", "");
        } else {
            model.addAttribute("subOrg", tenantId);
        }
        return "sub/addSubLegTraFunds";
    }

    /**
     * 金融产品报送-确认报送
     */
    @RequestMapping(value = "/submission/{pid}")
    public String submission(@PathVariable Integer pid, Model model) {

        Sublog sublog = sublogService.get(pid);
        model.addAttribute("sublog", sublog);
        model.addAttribute("pid", pid);

        return "sub/submission";
    }


    /**
     * 报送日期-确定
     */
    @RequestMapping(value = "/saveSubmission/{pid}/{formDate}/{subType}")
    @ResponseBody
    @Log(module = "报送管理", description = "保存数据页面")
    public Map<String, Object> saveSubmission(@PathVariable Integer pid, @PathVariable String formDate, @PathVariable String subType) {
        Map<String, Object> tips = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Sublog sublog = sublogService.get(pid);
        try {
            if (sublog != null) {
                //修改报送状态为-已报送
                sublog.setStatus("02");
                sublog.setSubmissionDate(sdf.parse(formDate));
                sublogService.update(sublog);
   /*             List<Filter> filter = new Filter.Builder().eq("subSeq", sublog.getBatchDecimal()).build();
                switch (subType){
                    case "pro":
                        List<ProductView> productViews = productViewService.select(filter);
                        for(ProductView p:productViews){
                            FinProduct finProduct=new FinProduct();
                            finProduct.setPid(p.getProtPid());
                            finProductService.updateSelective(finProduct);
                        }
                        break;
                    case "relation":
                        List<RelatedRelationView> relatedRelationViews= relatedRelationViewService.select(filter);
                        for(RelatedRelationView r:relatedRelationViews){
                            RelatedRelation relatedRelation=new RelatedRelation();
                            relatedRelation.setPid(r.getProdPid());
                            relatedRelation.setStatus("4");
                            relatedRelationService.updateSelective(relatedRelation);
                        }
                    case "investment" ://投资入股类
                        List<InsInvestmentView> insInvestmentViews= insInvestmentViewService.select(filter);
                        for(InsInvestmentView i :insInvestmentViews){
                            InsInvestment insInvestment=new InsInvestment();
                            insInvestment.setPid(i.getPortPid());
                            insInvestment.setStatus("4");
                            insInvestmentService.updateSelective(insInvestment);
                        }
                        break;
                    case "funds"://资金运用类
                        List<InsFundsView> insFundsViews= insFundsViewService.select(filter);
                        for(InsFundsView i :insFundsViews){
                            InsFunds insFunds=new InsFunds();
                            insFunds.setPid(i.getPortPid());
                            insFunds.setStatus("4");
                            insFundsService.updateSelective(insFunds);
                        }
                        break;
                    case "business"://保险业务类
                        List<InsBusinessView> insBusinessesViews=insBusinessViewService.select(filter);
                        for(InsBusinessView i : insBusinessesViews){
                            InsBusiness insBusiness=new InsBusiness();
                            insBusiness.setPid(i.getPortPid());
                            insBusiness.setStatus("4");
                            insBusinessService.updateSelective(insBusiness);
                        }
                        break;
                    case "interest"://利益转移类
                        List<InsInterestView> insInterestViews=insInterestViewService.select(filter);
                        for(InsInterestView i : insInterestViews){
                            InsInterest insInterest=new InsInterest();
                            insInterest.setPid(i.getPortPid());
                            insInterest.setStatus("4");
                            insInterestService.updateSelective(insInterest);
                        }
                        break;
                    case "service"://提供货物或服务类
                        List<InsServiceView> insServiceViews=insServiceViewService.select(filter);
                        for(InsServiceView i : insServiceViews){
                            InsService insService=new InsService();
                            insService.setPid(i.getPortPid());
                            insService.setStatus("4");
                            insServiceService.updateSelective(insService);
                        }
                        break;
                    case "other"://其他类
                        List<InsOtherView> insOtherViews=insOtherViewService.select(filter);
                        for(InsOtherView i : insOtherViews){
                            InsOther insOther=new InsOther();
                            insOther.setPid(i.getPortPid());
                            insOther.setStatus("4");
                            insOtherService.updateSelective(insOther);
                        }
                        break;
                    case "allBustran"://关联交易明细
                        List<InsAllbustraninfoView> allbustraninfoViews=insAllbustraninfoViewService.select(filter);
                        for(InsAllbustraninfoView i:allbustraninfoViews){
                            InsAllbustraninfo insAllbustraninfo=new InsAllbustraninfo();
                            insAllbustraninfo.setPid(i.getPortPid());
                            insAllbustraninfo.setStatus("4");
                            insAllbustraninfoService.updateSelective(insAllbustraninfo);
                        }
                    case "allFunds"://资金运用关联交易统计表
                        List<InsAllfundsView> insAllfundsViews=insAllfundsViewService.select(filter);
                        for(InsAllfundsView i:insAllfundsViews){
                            InsAllfunds insAllfunds=new InsAllfunds();
                            insAllfunds.setPid(i.getPortPid());
                            insAllfunds.setStatus("4");
                            insAllfundsService.updateSelective(insAllfunds);
                        }
                    case "relFunds"://单一法人资金运用关联交易统计表
                        List<InsRelfundsView> insRelfundsViews=insRelfundsViewService.select(filter);
                        for(InsRelfundsView i:insRelfundsViews){
                            InsRelfunds insRelFunds=new InsRelfunds();
                            insRelFunds.setPid(i.getPortPid());
                            insRelFunds.setStatus("4");
                            insRelfundsService.updateSelective(insRelFunds);
                    }

                }*/
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        } catch (ParseException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "输入日期格式错误");
        }
        return tips;
    }

    /**
     * 查询EAST报送列表数据
     *
     * @param type
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryEast/{type}"}, method = {RequestMethod.GET})
    @ResponseBody
    @Log(module = "EAST报送", description = "{type}查询报送列表数据")
    public Map<String, Object> queryEASTVo(@PathVariable String type, Page<Rel> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        filters.add(new Filter("subType", type));
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("modifiedTime", OrderType.DESC));
        PageHelper.start(page);
        List<Sublog> subLogs = sublogService.select(filters, orders);
        PageHelper.end(page, subLogs);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        String[] eastTypes = new String[]{type};

        rdsMiddatalogService.createEastBatch(eastTypes);
        return result;
    }

    @RequestMapping(value = "/download/{pid}")
    @ResponseBody
    public File download(@PathVariable String pid, HttpServletResponse response, HttpServletRequest request) {
        File file = null;
        List<File> files = new ArrayList<>();
        List<Filter> filterForDoc = null;
        // String zipPath = configs.getReportZip();
        String zipPath = "D:\\home\\rptrs\\upload";
        BufferedInputStream bis = null;
        ByteArrayOutputStream bbos = null;
        BufferedOutputStream bos = null;
        try {
            filterForDoc = new Filter.Builder().eq("pid", pid).build();
            Sublog sublog = sublogService.get(Integer.valueOf(pid));
            String zipName = sublog.getFileName();
            files.add(new File("D:" + sublog.getFileUrl() + ".xls"));
            files.add(new File("D:" + sublog.getFileUrl() + ".xml"));
            File[] files1 = files.toArray(new File[0]);
            String zipPathAll = zipPath + "/" + zipName + ".zip";
            String zipName11 = zipName + ".zip";
            ZipUtil.zip(zipPath, zipName, files1);
            file = new File(zipPathAll);
            long fileLength = file.length();
            //1.获取要下载的文件的绝对路径
            String realPath = zipPathAll;
            //2.获取要下载的文件名
            String fileName = realPath.substring(realPath.lastIndexOf(File.separator) + 1);
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            zipName11 = URLEncoder.encode(zipName11, "utf-8");
            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(zipName11.getBytes()));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(new FileInputStream(realPath));
            bbos = new ByteArrayOutputStream();
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bbos.write(buff, 0, bytesRead);
            }
            byte[] outBody = bbos.toByteArray();
            bos.write(outBody, 0, outBody.length);
        } catch (Exception e) {
            logger.error("下载失败：" + e.getMessage());
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                logger.error("关闭IO流缓冲失败：" + e.getMessage());
            }
        }
        return file;
    }


}
