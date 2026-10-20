package com.yzj.jep.app.bus.web.controller;


import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.mapper.Report_annualMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.bus.service.Report_annualService;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.bus.service.impl.Report_annualServiceImpl;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.model.TraOther;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.ins.web.controller.Commons;
import com.yzj.jep.app.sub.model.InsAllbustraninfo;
import com.yzj.jep.app.sub.model.InsAllfunds;
import com.yzj.jep.app.sub.model.InsInterest;
import com.yzj.jep.app.sub.service.InsAllfundsService;
import com.yzj.jep.app.sub.service.InsRelfundsService;
import com.yzj.jep.app.sub.vo.InsAllFundsVo;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.rules.drools5.model.Objects;
import com.yzj.jep.sys.model.AppConfig;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ObjectInput;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/report/design")
public class ReportDesignController extends CrudController<Objects> {
    private static Logger logger = LoggerFactory.getLogger(com.yzj.jep.app.ex.web.controller.WFEngineController.class);

    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private TraFundsService traFundsService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private TraInvestmentService traInvestmentService;
    @Autowired
    private TraServiceService traServiceService;
    @Autowired
    private TraOtherService traOtherService;

    @Autowired
    DictItemService dictItemService;

    @Autowired
    private RootOrgInfoService rootOrgInfoService;

    @Autowired
    private InsAllfundsService insAllfundsService;

    @Autowired
    private AppConfigService appConfigService;
    @Resource
    private Report_annualService report_annualService;

    @Resource
    private RelService relService;

    @RequestMapping(value = "/A1list")
    public String A1list(Model model) {
        return "rpt/atest";
    }

    //报表---授信银保监会
    @RequestMapping(value = "/CreditYBJ")
    public String creditYBJ(Model model) {
        return "rpt/creditYBJ";
    }

    //报表---单笔关联交易-其他查询
    @RequestMapping(value = "/singleOther")
    public String singleOther(Model model) {

        return "rpt/singleOther";
    }

    //报表---单笔关联交易-其他
    @RequestMapping(value = "/singleOtherView")
    public String singleOtherView(String startTime, String endTime, String manage_org, String related_tran_type, Model model) {
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("manage_org", manage_org);
        model.addAttribute("related_tran_type", related_tran_type);
        return "rpt/singleOtherView";
    }

    //报表---单笔关联交易-资产转移类查询
    @RequestMapping(value = "/singleAsset")
    public String singleAsset(Model model) {
        return "rpt/singleAsset";
    }

    //报表---单笔关联交易-资产转移类
    @RequestMapping(value = "/singleAssetView")
    public String singleAssetView(String startTime, String endTime, String manage_org, String related_tran_type, Model model) {
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("manage_org", manage_org);
        model.addAttribute("related_tran_type", related_tran_type);
        return "rpt/singleAssetView";
    }

    //报表---单笔关联交易-提供服务类查询
    @RequestMapping(value = "/singleService")
    public String singleService(Model model) {
        return "rpt/singleService";
    }

    //报表---单笔关联交易-提供服务类
    @RequestMapping(value = "/singleServiceView")
    public String singleServiceView(Model model) {
        return "rpt/singleServiceView";
    }

    //报表---单笔关联交易-授信类查询
    @RequestMapping(value = "/singleCredit")
    public String singleCredit(Model model) {

        return "rpt/singleCredit";
    }

    //报表---单笔关联交易-授信类
    @RequestMapping(value = "/singleCreditView")
    public String singleCreditView(String startTime, String endTime, String manage_org, String related_tran_type, Model model) {
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("manage_org", manage_org);
        model.addAttribute("related_tran_type", related_tran_type);
        return "rpt/singleCreditView";
    }


    //综合授信报表查询
    @RequestMapping(value = "/comCreditGran")
    public String comCreditGran(Model model) {
        return "rpt/comCreditGran";
    }

    //综合授信报表
    @RequestMapping(value = "/comCreditGranView")
    public String comCreditGranView(String startTime, String endTime, String manage_org, Model model) {
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("manage_org", manage_org);
        return "rpt/comCreditGranView";
    }

    //季度关联交易情况表查询
    @RequestMapping(value = "/tra")
    public String tra(Model model) {
        String code = UserProfileUtils.getUserProfile().getUnitCode();
        model.addAttribute("code", code);
        //model.addAttribute("year","2020");
        //查询登陆人机构及下辖机构的信息
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        if ("0001".equals(unitCode) || "0001-ZZHK".equals(unitCode)) {
            //资产
            model.addAttribute("unitCode", unitCode);
            model.addAttribute("opt1", "NoMH");
        } else {
            //管户机构
            model.addAttribute("unitCode", unitCode);
            model.addAttribute("opt1", "MH");
        }
        return "rpt/tra";
    }

    //季度关联交易情况表查询
    @RequestMapping(value = "/yearTra")
    public String yearTra(Model model) {
        String code = UserProfileUtils.getUserProfile().getUnitCode();
        model.addAttribute("code", code);
        //model.addAttribute("year","2020");
        return "rpt/yearTra";
    }

    @RequestMapping(value = "/getAnnual/{year1}/{quarter1}")
    @ResponseBody
    public Map<String, Object> getRelTranTable(@PathVariable String year1, @PathVariable String quarter1,
                                               Page<ReportAnnual> page, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<ReportAnnual> list = new ArrayList<>();
        String[] years = request.getParameterValues(year1);
        String[] quarters = request.getParameterValues(quarter1);
        String year = (years == null || years[0] == "") ? null : years[0];
        String quarter = quarters == null ? null : quarters[0];
        String[] traSubNames = request.getParameterValues("traSubName");
        String traSubName = traSubNames == null ? null : traSubNames[0];
        //String code = UserProfileUtils.getUserProfile().getUnitCode();
        ReportAnnual businessAnnual = report_annualService.selectBusiness(year, quarter, traSubName);
        ReportAnnual fundsAnnual = report_annualService.selectFunds(year, quarter, traSubName);
        ReportAnnual interestAnnual = report_annualService.selectInterest(year, quarter, traSubName);
        ReportAnnual investmentAnnual = report_annualService.selectInvestment(year, quarter, traSubName);
        ReportAnnual otherAnnual = report_annualService.selectOther(year, quarter, traSubName);
        ReportAnnual serviceAnnual = report_annualService.selectService(year, quarter, traSubName);
        list.add(businessAnnual);
        list.add(fundsAnnual);
        list.add(interestAnnual);
        list.add(investmentAnnual);
        list.add(otherAnnual);
        list.add(serviceAnnual);
        /*PageHelper.start(page);
        PageHelper.end(page, list);
        page.setTotal(6);
        result.put("rows", page.getResult());
        result.put("total", page.getTotal());*/

        page.setTotal(list.size());
        int pages = (list.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<ReportAnnual> list1 = new ArrayList<>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < list.size()) {
                list1.add(list.get(i));
            }
        }
        page.setResult(list1);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    //季度关联交易情况表
    @RequestMapping(value = "/traView")
    public String traView(String startTime, String endTime, String manage_org, Model model) {
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("manage_org", manage_org);
        return "rpt/traView";
    }

    //报表---内部交易信息统计表查询
    @RequestMapping(value = "/tranInfo")
    public String tranInfo(Model model) {

        return "rpt/tranInfo";
    }

    //报表---内部交易信息统计表
    @RequestMapping(value = "/tranInfoView")
    public String tranInfoView(String startTime, String endTime, String manage_org, Model model) {
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("manage_org", manage_org);
        return "rpt/tranInfoView";
    }

    //报表---内部交易信息统计明细表查询
    @RequestMapping(value = "/tranInfoDetails")
    public String tranInfoDetails(Model model) {

        return "rpt/tranInfoDetails";
    }

    //    //报表---内部交易信息统计明细表查询测试
//    @RequestMapping(value = "/tranInfoDetailsgxr")
//    public String tranInfoDetailsgxr(Model model){
//
//        return "rpt/tranInfoDetailsgxr";
//    }
    //报表---内部交易信息统计明细表
    @RequestMapping(value = "/tranInfoDetailsView")
    public String tranInfoDetailsView(String relatedName, String identifyType, String identNumber, String startTime, String endTime, String businessType, String manage_org, Model model) {
        model.addAttribute("relatedName", relatedName);
        model.addAttribute("identifyType", identifyType);
        model.addAttribute("identNumber", identNumber);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("businessType", businessType);
        model.addAttribute("manage_org", manage_org);
        return "rpt/tranInfoDetailsView";
    }

    //报表---G15最大十家关联方交易关联交易情况表查询
    @RequestMapping(value = "relatedTranG15")
    public String relatedTranG15(Model model) {
        return "rpt/relatedTranG15";
    }

    //报表---G15最大十家关联方交易关联交易情况表
    @RequestMapping(value = "relatedTranG15View")
    public String relatedTranG15View(String startTime, String endTime, String manage_org, Model model) {
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("manage_org", manage_org);
        return "rpt/relatedTranG15View";
    }

    //报表---非授信类关联交易明细表查询
    @RequestMapping(value = "nonCredit")
    public String nonCredit(Model model) {

        return "rpt/nonCredit";
    }

    //报表---非授信类关联交易明细表
    @RequestMapping(value = "nonCreditView")
    public String nonCreditView(String startTime, String endTime, String manage_org, Model model) {
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("manage_org", manage_org);
        return "rpt/nonCreditView";
    }

    //报表---授信类关联交易明细表查询
    @RequestMapping(value = "creditDetails")
    public String creditDetails(Model model) {

        return "rpt/creditDetails";
    }

    //报表---授信类关联交易明细表
    @RequestMapping(value = "creditDetailsView")
    public String creditDetailsView(String startTime, String endTime, String manage_org, Model model) {
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("manage_org", manage_org);
        return "rpt/creditDetailsView";
    }

    //报表---授信-用信情况明细表查询
    @RequestMapping(value = "creditApplication")
    public String creditApplication(Model model) {

        return "rpt/creditApplication";
    }

    //报表---授信-用信情况明细表
    @RequestMapping(value = "creditApplicationView")
    public String creditApplicationView(String startTime, String endTime, String manage_org, Model model) {
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("manage_org", manage_org);
        return "rpt/creditApplicationView";
    }

    //报表---与所有关联方关联交易余额表查询
    @RequestMapping(value = "allBusTranInfo")
    public String allBusTranInfo(Model model) {

        return "rpt/allBusTranInfo";
    }

    //报表---与所有关联方关联交易余额表
    @RequestMapping(value = "allBusTranInfoView")
    public String allBusTranInfoView(String startTime, String endTime, String manage_org, Model model) {
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("manage_org", manage_org);
        return "rpt/allBusTranInfoView";
    }

    //报表---测试用报表
    @RequestMapping(value = "test")
    public String test(Model model) {

        return "rpt/test";
    }

    //关联交易表(保险)-投资入股类
    @RequestMapping(value = "/insTraInvestment")
    public String insTraInvestment(Model model) {
        return "rpt/insTraInvestment";
    }

    //关联交易表-资金运用类
    @RequestMapping(value = "/insTraFunds")
    public String insTraFunds(Model model) {
        return "rpt/insTraFunds";
    }

    //关联交易表-保险业务类
    @RequestMapping(value = "/insTraBusiness")
    public String insTraBusiness(Model model) {
        return "rpt/insTraBusiness";
    }

    //关联交易表-利益转移类
    @RequestMapping(value = "/insTraInterest")
    public String insTraInterest(Model model) {
        return "rpt/insTraInterest";
    }

    //关联交易表-提供服务类
    @RequestMapping(value = "/insTraService")
    public String insTraService(Model model) {
        return "rpt/insTraService";
    }

    //关联交易表(保险)-与所有关联方关联交易余额表
    @RequestMapping(value = "/insTraAll")
    public String insTraAll(Model model) {
        return "rpt/insTraAll";
    }

    //大家保险集团有限责任公司关联交易明细表
    @RequestMapping(value = "/insTraGroup")
    public String insTraGroup(Model model) {
        return "rpt/insTraGroup";
    }


    //季度统计详情跳转
    @RequestMapping(value = "/trainfoJump/{year}/{quarter}/{type}/{traSubName}")
    public String trainfoJump(Model model, @PathVariable String year, @PathVariable String quarter, @PathVariable String type, @PathVariable String traSubName) {
        model.addAttribute("years", year);
        model.addAttribute("quarter", quarter);
        model.addAttribute("unitCode", traSubName);
        if ("ALL".equals(quarter)) {
            model.addAttribute("quarter", "%");
        }
        model.addAttribute("traType", type);

        return "rpt/traInfo";
    }

    //季度关联交易明细表
    @RequestMapping(value = "/traInfoList")
    public String trainfoList(Model model) {
        Date date = new Date();
        Integer year = date.getYear();
        model.addAttribute("year", year.toString());
        model.addAttribute("quarter", "%");
        model.addAttribute("traType", "%");
        model.addAttribute("opt", "list");
        //查询登陆人机构及下辖机构的信息
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        if ("0001".equals(unitCode) || "0001-ZZHK".equals(unitCode)) {
            //资产
            model.addAttribute("unitCode", unitCode);
            model.addAttribute("opt1", "NoMH");
        } else {
            //管户机构
            model.addAttribute("unitCode", unitCode);
            model.addAttribute("opt1", "MH");
        }
        return "rpt/traInfo";
    }

    //季度关联交易明细数据查询
    @RequestMapping(value = "traInfo")
    @ResponseBody
    public Map<String, Object> traInfo(Page<ReportAnnual> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        // String code = UserProfileUtils.getUserProfile().getUnitCode();
        List<Order> orders = RequestUtils.buildOrders(request);
        String[] years = request.getParameterValues("year");
        String[] quarters = request.getParameterValues("quarter");
        String[] considerTypes = request.getParameterValues("considerType");
        String[] tranTypes = request.getParameterValues("tranType");
        String[] yearss = request.getParameterValues("years");
        String[] traSubNames = request.getParameterValues("traSubName");
        String year = (years == null || years[0] == "") ? null : years[0];
        String trayear = (yearss == null || yearss[0] == "") ? null : yearss[0];
        String quarter = quarters == null ? null : quarters[0];
        String tranType = quarters == null ? null : tranTypes[0];
        String considerType = considerTypes == null ? null : considerTypes[0];
        String traSubName = traSubNames == null ? null : traSubNames[0];
        //初始话交易对象
        List<TraInvestment> traInvestments = new ArrayList<>();
        List<TraFunds> traFunds = new ArrayList<>();
        List<TraBusiness> traBusinesses = new ArrayList<>();
        List<TraInterest> traInterests = new ArrayList<>();
        List<TraService> traServices = new ArrayList<>();
        List<TraOther> traOthers = new ArrayList<>();
        //默认年份
        if (null == year && null == trayear) {
            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR) + "";
        } else if (null == year && null != trayear) {
            year = trayear;
        }
        String[] list = yearQuarter(year, quarter);
        //查询条件构建
        List<Filter> filters = new ArrayList<>();
        //没删除的数据
        filters.add(new Filter("delflag", "1"));
        filters.add(new Filter("traSubName", traSubName));
        if (!"%".equals(considerType)) {
            filters.add(new Filter("considerType", Operator.EQ, considerType));
        }
        //中再(港再)资产只查询已归档的
        if ("0001".equals(traSubName) || "0001-ZZHK".equals(traSubName)) {
            filters.add(new Filter("status", "6"));
        } else {
            filters.add(new Filter("status", Operator.IN, new String[]{"8", "9"}));
        }
        //季度未选查询全年
        if ("%".equals(quarter)) {
            filters.add(new Filter("tranDate", Operator.LIKE, year));
        } else {
            filters.add(new Filter("tranDate", Operator.BETWEEN, list));
        }
        if (!"%".equals(tranType)) {
            if ("01".equals(tranType)) {
                traInvestments = traInvestmentService.select(filters);
            }
            if ("02".equals(tranType)) {
                traFunds = traFundsService.select(filters);
            }
            if ("03".equals(tranType)) {
                traBusinesses = traBusinessService.select(filters);
            }
            if ("04".equals(tranType)) {
                traInterests = traInterestService.select(filters);
            }
            if ("05".equals(tranType)) {
                traServices = traServiceService.select(filters);
            }
            if ("06".equals(tranType)) {
                traOthers = traOtherService.select(filters);
            }
        } else {
            traInvestments = traInvestmentService.select(filters);
            traFunds = traFundsService.select(filters);
            traBusinesses = traBusinessService.select(filters);
            traInterests = traInterestService.select(filters);
            traServices = traServiceService.select(filters);
            traOthers = traOtherService.select(filters);
        }
        //把查询到得数据添加到vo对象中
        List<ReportAnnual> traInfo = getTraInfo(traInvestments, traFunds, traBusinesses, traInterests, traServices, traOthers);

        page.setTotal(traInfo.size());
        int pages = (traInfo.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<ReportAnnual> psnAndOrgVoss = new ArrayList<ReportAnnual>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < traInfo.size()) {
                psnAndOrgVoss.add(traInfo.get(i));
            }
        }
        page.setResult(psnAndOrgVoss);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    //返回季度开始时间和结束时间
    public String[] yearQuarter(String year, String quarter) {
        StringBuffer staTIme = new StringBuffer();
        StringBuffer endTIme = new StringBuffer();
        if ("%".equals(quarter)) {
            staTIme = staTIme.append(year).append("-01-01");
            endTIme = endTIme.append(year).append("-12-31");
            String[] list = {staTIme.toString(), endTIme.toString()};
            return list;
        }
        switch (quarter) {
            case "1":
                staTIme = staTIme.append(year).append("-01-01");
                endTIme = endTIme.append(year).append("-03-31");
                break;
            case "2":
                staTIme = staTIme.append(year).append("-01-01");
                endTIme = endTIme.append(year).append("-06-31");
                break;
            case "3":
                staTIme = staTIme.append(year).append("-01-01");
                endTIme = endTIme.append(year).append("-09-31");
                break;
            case "4":
                staTIme = staTIme.append(year).append("-01-01");
                endTIme = endTIme.append(year).append("-12-31");
                break;
        }
        String[] list = {staTIme.toString(), endTIme.toString()};

        return list;
    }

    public List<ReportAnnual> getTraInfo(List<TraInvestment> traInvestments, List<TraFunds> traFunds, List<TraBusiness> traBusinesses,
                                         List<TraInterest> traInterests, List<TraService> traServices, List<TraOther> traOthers) {
        List<ReportAnnual> reportAnnuals = new ArrayList<>();

        for (TraInvestment tra : traInvestments) {
            ReportAnnual reportAnnual = new ReportAnnual();
            BeanUtils.copyProperties(tra,reportAnnual);
            reportAnnuals.add(reportAnnual);
        }
        for (TraFunds tra : traFunds) {
            ReportAnnual reportAnnual = new ReportAnnual();
            BeanUtils.copyProperties(tra,reportAnnual);
            reportAnnuals.add(reportAnnual);
       }
        for (TraBusiness tra : traBusinesses) {
            ReportAnnual reportAnnual = new ReportAnnual();
            BeanUtils.copyProperties(tra,reportAnnual);
            reportAnnuals.add(reportAnnual);
        }
        for (TraInterest tra : traInterests) {
            ReportAnnual reportAnnual = new ReportAnnual();
            BeanUtils.copyProperties(tra,reportAnnual);
            reportAnnuals.add(reportAnnual);
        }
        for (TraService tra : traServices) {
            ReportAnnual reportAnnual = new ReportAnnual();
            BeanUtils.copyProperties(tra, reportAnnual);
            reportAnnuals.add(reportAnnual);
        }
        for (TraOther tra : traOthers) {
            ReportAnnual reportAnnual = new ReportAnnual();
            BeanUtils.copyProperties(tra, reportAnnual);
            reportAnnuals.add(reportAnnual);
        }

        return reportAnnuals;
    }


    /**
     * 查看页面
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{transNum}")
    public String view(@PathVariable String transNum, Model model, HttpServletRequest request) {

        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("transNum", transNum));
        List<TraInvestment> traInvestments = traInvestmentService.select(filters);
        List<TraFunds> traFunds = traFundsService.select(filters);
        List<TraBusiness> traBusinesses = traBusinessService.select(filters);
        List<TraInterest> traInterests = traInterestService.select(filters);
        List<TraService> traServices = traServiceService.select(filters);
        List<TraOther> traOthers = traOtherService.select(filters);

        for (TraInvestment tra : traInvestments) {
            model.addAttribute("traInvestment", tra);
            model.addAttribute("opt", "view");
            return "rpt/infoTraInvestment";
        }
        for (TraFunds tra : traFunds) {
            model.addAttribute("traFunds", tra);
            model.addAttribute("opt", "view");
            return "rpt/infoTraFunds";
        }
        for (TraBusiness tra : traBusinesses) {
            model.addAttribute("traBusiness", tra);
            model.addAttribute("opt", "view");
            return "rpt/infoTraBusiness";
        }
        for (TraInterest tra : traInterests) {
            model.addAttribute("traInterest", tra);
            model.addAttribute("opt", "view");
            return "rpt/infoTraInterest";
        }
        for (TraService tra : traServices) {
            model.addAttribute("traService", tra);
            model.addAttribute("opt", "view");
            return "rpt/infoTraService";
        }

        for (TraOther tra : traOthers) {
            model.addAttribute("traOther", tra);
            model.addAttribute("opt", "view");
            return "rpt/infoTraOther";
        }
        return "rpt/tra";
    }

    //统计分析——季度明细表页面
    @RequestMapping(value = "/quarterList")
    public String quarterList(Model model) {
        Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR);
        model.addAttribute("year", year.toString());
        model.addAttribute("quarter", "%");
        model.addAttribute("traType", "%");
        model.addAttribute("opt", "list");
        String code = UserProfileUtils.getUserProfile().getUnitCode();
        model.addAttribute("code", code);
        return "rpt/quarterList";
    }

    //统计分析——季度明细表查询
    @RequestMapping(value = "/getQuarterList")
    @ResponseBody
    public Map<String, Object> getQuarterList(Page<QuarterListVo> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        String[] years = request.getParameterValues("year");
        String[] quarters = request.getParameterValues("quarter");
        String[] startDates = request.getParameterValues("startDate");
        String[] endDates = request.getParameterValues("endDate");
        String year = (years == null || years[0] == "") ? null : years[0];
        String quarter = quarters == null ? null : quarters[0];
        String staTime = startDates[0];
        String endTime = endDates[0];
        //初始化交易对象
        List<QuarterListVo> traInvestments = new ArrayList<>();
        List<QuarterListVo> traFunds = new ArrayList<>();
        List<QuarterListVo> traBusinesses = new ArrayList<>();
        List<QuarterListVo> traInterests = new ArrayList<>();
        List<QuarterListVo> traServices = new ArrayList<>();
        List<QuarterListVo> traOthers = new ArrayList<>();
        //默认年份
        if (StringUtils.isEmpty(staTime) || StringUtils.isEmpty(endTime)) {
            if (year == null || year.trim().equals("")) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR) + "";
            }
            String[] list = yearQuarter(year, quarter);
            staTime = list[0];
            endTime = list[1];
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("staTime", staTime);
        map.put("endTime", endTime);
        traInvestments = traInvestmentService.getInvestment(map);
        traFunds = traFundsService.getFundsService(map);
        traBusinesses = traBusinessService.getBusiness(map);
        traInterests = traInterestService.getInterest(map);
        traServices = traServiceService.getService(map);
        traOthers = traOtherService.getOther(map);
//        }
        //把查询到得数据添加到vo对象中
        List<QuarterListVo> quarterInfo = new ArrayList<>();
        quarterInfo.addAll(traInvestments);
        quarterInfo.addAll(traFunds);
        quarterInfo.addAll(traBusinesses);
        quarterInfo.addAll(traInterests);
        quarterInfo.addAll(traServices);
        quarterInfo.addAll(traOthers);
        BigDecimal totalMoney = quarterInfo.stream().map(QuarterListVo::getContractAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        page.setTotal(quarterInfo.size());
        int pages = (quarterInfo.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<QuarterListVo> quarterListVoList = new ArrayList<QuarterListVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < quarterInfo.size()) {
                quarterListVoList.add(quarterInfo.get(i));
            }
        }
        //分页展示所查数据
        for (QuarterListVo vo : quarterListVoList) {
            StringBuffer sb = new StringBuffer();
            List<Rel> rel = relService.queryRelForOrgAndPsn(vo.getRelId());
            for (Rel r : rel) {
                sb.append(r.getRelStatement());
                sb.append(",");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.lastIndexOf(","));
                vo.setRelStatement(sb.toString());
            }
            if ("01".equals(vo.getTranType())) {
                vo.setTranOverView(selectByValue(vo.getTranOverView(), "INS-InvestmentType"));
            }
            if ("02".equals(vo.getTranType())) {
                vo.setTranOverView(selectByValue(vo.getTranOverView(), "INS-FundsType"));
            }
            if ("03".equals(vo.getTranType())) {
                vo.setTranOverView(selectByValue(vo.getTranOverView(), "INS-BusinessType"));
            }
            if ("04".equals(vo.getTranType())) {
                vo.setTranOverView(selectByValue(vo.getTranOverView(), "INS-InterestType"));
            }
            if ("05".equals(vo.getTranType())) {
                vo.setTranOverView(selectByValue(vo.getTranOverView(), "INS-ServiceType"));
            }
            if ("06".equals(vo.getTranType())) {
                vo.setTranOverView(selectByValue(vo.getTranOverView(), "INS-otherType"));
            }
            vo.setTotalAmount(totalMoney.toString());
            vo.setYear(year);
        }
        page.setResult(quarterListVoList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    //统计分析——季度明细表页面
    @RequestMapping(value = "/quarterListByManagerOrg")
    public String quarterListByManagerOrg(Model model) {
        Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR);
        model.addAttribute("year", year.toString());
        model.addAttribute("quarter", "%");
        model.addAttribute("traType", "%");
        model.addAttribute("opt", "list");
        String code = UserProfileUtils.getUserProfile().getUnitCode();
        model.addAttribute("code", code);
        return "rpt/quarterList2";
    }

    //统计分析——季度明细表查询
    @RequestMapping(value = "/getQuarterListByManagerOrg")
    @ResponseBody
    public Map<String, Object> getQuarterListByManagerOrg(Page<QuarterListVo> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        String[] years = request.getParameterValues("year");
        String[] quarters = request.getParameterValues("quarter");
        String[] startDates = request.getParameterValues("startDate");
        String[] endDates = request.getParameterValues("endDate");
        String year = (years == null || years[0] == "") ? null : years[0];
        String quarter = quarters == null ? null : quarters[0];
        String staTime = startDates[0];
        String endTime = endDates[0];
        //初始化交易对象
        List<QuarterListVo> traInvestments = new ArrayList<>();
        List<QuarterListVo> traFunds = new ArrayList<>();
        List<QuarterListVo> traBusinesses = new ArrayList<>();
        List<QuarterListVo> traInterests = new ArrayList<>();
        List<QuarterListVo> traServices = new ArrayList<>();
        List<QuarterListVo> traOthers = new ArrayList<>();
        //默认年份
        if (StringUtils.isEmpty(staTime) || StringUtils.isEmpty(endTime)) {
            if (year == null || year.trim().equals("")) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR) + "";
            }
            String[] list = yearQuarter(year, quarter);
            staTime = list[0];
            endTime = list[1];
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("staTime", staTime);
        map.put("endTime", endTime);
        map.put("managerOrg", "总部");
        traInvestments = traInvestmentService.getInvestment(map);
        traFunds = traFundsService.getFundsService(map);
        traBusinesses = traBusinessService.getBusiness(map);
        traInterests = traInterestService.getInterest(map);
        traServices = traServiceService.getService(map);
        traOthers = traOtherService.getOther(map);
//        }
        //把查询到得数据添加到vo对象中
        List<QuarterListVo> quarterInfo = new ArrayList<>();
        quarterInfo.addAll(traInvestments);
        quarterInfo.addAll(traFunds);
        quarterInfo.addAll(traBusinesses);
        quarterInfo.addAll(traInterests);
        quarterInfo.addAll(traServices);
        quarterInfo.addAll(traOthers);
        BigDecimal totalMoney = quarterInfo.stream().map(QuarterListVo::getContractAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        page.setTotal(quarterInfo.size());
        int pages = (quarterInfo.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<QuarterListVo> quarterListVoList = new ArrayList<QuarterListVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < quarterInfo.size()) {
                quarterListVoList.add(quarterInfo.get(i));
            }
        }
        //分页展示所查数据
        for (QuarterListVo vo : quarterListVoList) {
            StringBuffer sb = new StringBuffer();
            List<Rel> rel = relService.queryRelForOrgAndPsn(vo.getRelId());
            for (Rel r : rel) {
                sb.append(r.getRelStatement());
                sb.append(",");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.lastIndexOf(","));
                vo.setRelStatement(sb.toString());
            }
            if ("01".equals(vo.getTranType())) {
                vo.setTranOverView(selectByValue(vo.getTranOverView(), "INS-InvestmentType"));
            }
            if ("02".equals(vo.getTranType())) {
                vo.setTranOverView(selectByValue(vo.getTranOverView(), "INS-FundsType"));
            }
            if ("03".equals(vo.getTranType())) {
                vo.setTranOverView(selectByValue(vo.getTranOverView(), "INS-BusinessType"));
            }
            if ("04".equals(vo.getTranType())) {
                vo.setTranOverView(selectByValue(vo.getTranOverView(), "INS-InterestType"));
            }
            if ("05".equals(vo.getTranType())) {
                vo.setTranOverView(selectByValue(vo.getTranOverView(), "INS-ServiceType"));
            }
            if ("06".equals(vo.getTranType())) {
                vo.setTranOverView(selectByValue(vo.getTranOverView(), "INS-otherType"));
            }
            vo.setTotalAmount(totalMoney.toString());
            vo.setYear(year);
        }
        page.setResult(quarterListVoList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    public String selectByValue(String value, String simpleName) {
        List<Filter> filters = new ArrayList<>();
        String result = "";
        try {
            filters.clear();
            filters.add(new Filter("dictCode", Operator.EQ, simpleName));
            filters.add(new Filter("itemValue", Operator.EQ, value));
            DictItem dictItem = dictItemService.get(filters);
            result = dictItem.getItemLabel();
        } catch (Exception e) {
        }
        return result;
    }

    //统计分析——季度明细表查询
    @RequestMapping(value = "/getQuarterAll/{parameters}")
    @ResponseBody
    public Map<String, Object> getQuarterAll(@PathVariable Map parameters) {
        Map<String, Object> result = new HashMap<String, Object>();
        String data = (String) parameters.get("parameters");
        String[] param = data.split(",");
        String year = "%".equals(param[0]) ? null : param[0];
        String quarter = param[1];
        String staTime = "";
        String endTime = "";
        //初始化交易对象
        List<QuarterListVo> traInvestments = new ArrayList<>();
        List<QuarterListVo> traFunds = new ArrayList<>();
        List<QuarterListVo> traBusinesses = new ArrayList<>();
        List<QuarterListVo> traInterests = new ArrayList<>();
        List<QuarterListVo> traServices = new ArrayList<>();
        List<QuarterListVo> traOthers = new ArrayList<>();
        //默认年份
        if (year == null || year.trim().equals("")) {
            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR) + "";
        }
        String[] list = yearQuarter(year, quarter);
        staTime = list[0];
        endTime = list[1];
        Map<String, Object> map = new HashMap<>(16);
        map.put("staTime", staTime);
        map.put("endTime", endTime);
        traInvestments = traInvestmentService.getInvestment(map);
        traFunds = traFundsService.getFundsService(map);
        traBusinesses = traBusinessService.getBusiness(map);
        traInterests = traInterestService.getInterest(map);
        traServices = traServiceService.getService(map);
        traOthers = traOtherService.getOther(map);
//        }
        //把查询到得数据添加到vo对象中
        List<QuarterListVo> quarterInfo = new ArrayList<>();
        quarterInfo.addAll(traInvestments);
        quarterInfo.addAll(traFunds);
        quarterInfo.addAll(traBusinesses);
        quarterInfo.addAll(traInterests);
        quarterInfo.addAll(traServices);
        quarterInfo.addAll(traOthers);
        BigDecimal totalMoney = quarterInfo.stream().map(QuarterListVo::getContractAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        QuarterListVo vo = new QuarterListVo();
        vo.setTraSubName("复星保险");
        vo.setTotalAmount(totalMoney.toString());
        vo.setQuartertTitle(getQuarter(quarter));
        vo.setYear(year);
        result.put("quarterListVo", vo);
        return result;
    }

    /**
     * @param quarter
     * @return
     * @desc 季度转换
     */
    private String getQuarter(String quarter) {
        switch (quarter) {
            case "%":
                return "全季度";
            case "1":
                return "第一季度";
            case "2":
                return "第二季度";
            case "3":
                return "第三季度";
            default:
                return null;
        }
    }


    /**
     * 重大关联交易统计分析
     */
    @RequestMapping(value = "/majorRelTranAnalysis")
    public String metaAnalysisClient(Model model) {
        return "rpt/majorRelTranAnalysis";
    }

    /**
     * 统一交易执行情况
     */
    @RequestMapping(value = "/isAgreementImpl")
    public String isAgreementImpl(Model model) {
        return "rpt/isAgreementImpl";
    }

    /**
     * 资金运用类分析统计
     */
    @RequestMapping(value = "/fundsAnalysis")
    public String   fundsAnalysis(Model model) {
        return "rpt/fundsAnalysis";
    }

    /**
     * 资金运用类分析统计
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryData")
    @ResponseBody
    public Map<String, Object> queryData(Page<InsAllFundsVo> page, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Filter> filtersOrg = RequestUtils.buildFilters(request);
        String year = request.getParameter("year");
        filters.add(new Filter("status", "3"));  //表示待报送
        filtersOrg.add(new Filter("status", "3"));  //表示待报送
        filtersOrg.add(new Filter("delflag", "1"));//表示没删除的数据
        filters.add(new Filter("delflag", "1"));//表示没删除的数据
        if (year != null && !year.equals("")) {

            filters.add(new Filter("subYear", year));
            filtersOrg.add(new Filter("year", year));
        }
        AuthorityUtil authorityUtil = new AuthorityUtil();
        //filters = authorityUtil.getFilterByRole(filters);
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("modifiedTime", OrderType.DESC));
        PageHelper.start(page);
        List<InsAllfunds> insInterests = insAllfundsService.select(filters);
        filtersOrg.add(new Filter("quarter","4"));
        List<InsAllFundsVo> insAllFundsVoList = new ArrayList<>();
        for (InsAllfunds insInterest : insInterests) {
            InsAllFundsVo insAllFundsVo = new InsAllFundsVo();
            insAllFundsVo.setYears(insInterest.getSubYear());
            insAllFundsVo.setLastYearTotalAssets(null);
            insAllFundsVo.setLastYearNetWorth(null);
            insAllFundsVo.setFixedProfitBalance(insInterest.getFixedProfitBalance());
            insAllFundsVo.setListedInvestBalance(insInterest.getListedInvestBalance());
            insAllFundsVo.setUnlistedInvestBalance(insInterest.getUnlistedInvestBalance());
            insAllFundsVo.setRatUnlistedInvestBalance(null);
            insAllFundsVo.setImvbsInvestBalance(insInterest.getImvbsInvestBalance());
            insAllFundsVo.setRatImvbsInvestBalance(null);
            insAllFundsVo.setComBankFinFund(insInterest.getComBankFinFund());
            insAllFundsVo.setBankCreditFund(insInterest.getBankCreditFund());
            insAllFundsVo.setTrustFund(insInterest.getTrustFund());
            insAllFundsVo.setBondSpecialFund(insInterest.getBondSpecialFund());
            insAllFundsVo.setOtherFund(insInterest.getOtherFund());
            insAllFundsVo.setOtherFundAdd(null);
            insAllFundsVo.setRatOtherFund(null);
            insAllFundsVo.setAbroadFund(insInterest.getAbroadFund());
            insAllFundsVo.setRatAbroadFund(null);
            insAllFundsVo.setAllInvetmenthq(null);
            insAllFundsVo.setRatAllInvestmentTotal(null);
            insAllFundsVo.setRatAllInvestmentWorth(null);
            insAllFundsVo.setAllInvestmentBalance(insInterest.getAllInvestmentBalance());
            insAllFundsVo.setMobilityBalance(insInterest.getMobilityBalance());
            insAllFundsVoList.add(insAllFundsVo);
        }
        PageHelper.end(page, insAllFundsVoList);
        map.put(RESULT_TOTAL, page.getTotal());
        map.put(RESULT_ROWS, page.getResult());
        return map;
    }


}