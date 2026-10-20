/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:31
 *
 */

package com.yzj.jep.app.bus.web.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.bus.vo.FundsInvestmentVo;
import com.yzj.jep.app.fil.model.FilRelPsn;
import com.yzj.jep.app.fil.service.FilRelPsnService;
import com.yzj.jep.app.ins.model.TraCounterparty;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.service.BaseService;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.*;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.service.*;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;
import static com.yzj.jep.app.util.ConfidentialInfoUtil.otherIdEncrypt;


@Controller
@RequestMapping("/bus/relPsn")
public class RelPsnController extends CrudController<RelPsn> {
    private static Logger logger = LoggerFactory.getLogger(RelPsnController.class);

    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelFileService relFileService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private RelService relService;
    @Autowired
    private FilRelPsnService filRelPsnService;
    @Autowired
    private DictItemService dictItemService;

    @Autowired
    private AligMapService aligMapService;


    @Autowired
    private RelDictService relDictService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private RelPsnAndOrgOfProService relPsnAndOrgOfProService;

    @Autowired
    private RelProService relProService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private BaseService baseService;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private RelPsnHistoryService relPsnHistoryService;

    @Autowired
    private FundsInvestmentService fundsInvestmentService;

    @Autowired
    private RiskRatioService riskRatioService;
    @Autowired
    private RootOrgInfoService rootOrgInfoService;

    @Resource
    private TraFundsService traFundsService;
    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "关联方管理", description = "查询自然人列表数据")
    public String list(Model model) {

        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "bus/relPsn/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
//        model.addAttribute("mgerOrg", UserProfileUtils.getUserProfile().getUnitCode());
        return "bus/relPsnList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    @Log(module = "关联方管理", description = "自然人列表数据")
    protected void prepareQuery(Page<RelPsn> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        relPsnService.query(page, filters, orders);
    }



    @RequestMapping(value = "/affirmPsn/{pid}")
    public String ccc(@PathVariable String pid, Model model) {
        model.addAttribute("pid", pid);
        return "bus/psnConfirm";
    }

    /**
     * 确认按钮
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/affirm")
    @ResponseBody
    @Log(module = "关联方管理", description = "按钮查看权限后端查询")
    public Map<String, Object> affirm(String pid) {
        Map<String, Object> map = new HashMap<>();
        try {
            RelPsn relPsn = relPsnService.get(Integer.parseInt(pid));
            relPsn.setStatus("6");
            relPsnService.updateSelective(relPsn);
            List<Filter> tfilter = new Filter.Builder().eq("toPid", pid).eq("toType", "PSN").build();
            if (relService.exists(tfilter)) {
                for (Rel rl : relService.select(tfilter)) {
                    rl.setRelStatus("00");
                    relService.updateSelective(rl);
                }
            }
            map.put(WebCode.SUCCESS, true);
            map.put(WebCode.MSG, "操作成功！");
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            map.put(WebCode.SUCCESS, false);
            map.put(WebCode.MSG, "操作失败！");
        }
        return map;
    }

    /**
     * 为自然人列表页面做数据准备
     */
    @Override
    @Log(module = "关联方管理", description = "自然人列表数据")
    public Map<String, Object> query(Page<RelPsn> page, Model model, HttpServletRequest request) {
        UserProfile profile = UserProfileUtils.getUserProfile();
//        List<String> codes = unitService.selectUnitAllOrDeptAllCodes(profile.getUnitCode());
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
//        filters.add(Filter.le("createdTime", DateUtils.parseDate(createdTime)));
        RelPsn relPsn = new RelPsn();
        relPsn.setDelflag("1");
        for (Filter f : filters) {
            if ("name".equals(f.getProperty())) {
                relPsn.setName(f.getValue().toString());
            } else if ("status".equals(f.getProperty())) {
                relPsn.setStatus(f.getValue().toString());
            } else if ("identifyType".equals(f.getProperty())) {
                relPsn.setIdentifyType(f.getValue().toString());
            } else if ("identifyNumber".equals(f.getProperty())) {
                relPsn.setIdentifyNumber(f.getValue().toString());
            } else if ("domesticOrForeign".equals(f.getProperty())){
                relPsn.setDomesticOrForeign(f.getValue().toString());
            } else if ("effectRelation".equals(f.getProperty())){
                relPsn.setEffectRelation(f.getValue().toString());
            } else if ("transactionDep".equals(f.getProperty())){
                relPsn.setTransactionDep(f.getValue().toString());
            } else if ("operator".equals(f.getProperty())){
                relPsn.setOperator(f.getValue().toString());
            } else if ("createTime".equals(f.getProperty())){
                relPsn.setCreateTimeString(f.getValue().toString());
            } else if ("modifiedTime".equals(f.getProperty())){
                relPsn.setModifiedTimeString(f.getValue().toString());
            } else if ("isBlack".equals(f.getProperty())) {
                relPsn.setIsBlack(f.getValue().toString());
            } else if ("dataSource".equals(f.getProperty())) {
                relPsn.setDataSource(f.getValue().toString());
            }
//            else if ("mgerOrg".equals(f.getProperty())) {
//                relPsn.setMgerOrg(f.getValue().toString());
//            }
        }
        PageHelper.start(page);
        List<RelPsn> relPsns = relPsnService.selectPsnInformation(relPsn);
        relPsns.stream().forEach( e -> {
            if(e.getIdentifyType().equals("1")) {
                e.setIdentifyNumber(idEncrypt(e.getIdentifyNumber()));
            }else{
                e.setIdentifyNumber(otherIdEncrypt(e.getIdentifyNumber()));
            }
        });
        PageInfo pageInfo = new PageInfo(relPsns);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 新增页面
     * proOfPsnList
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    @Log(module = "关联方管理", description = "新增列表数据")
    public String add(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        model.addAttribute("opt", "add");
        RelPsn relPsn = new RelPsn();
        relPsn.setMgerOrg(unitCode);
        model.addAttribute("relPsn", relPsn);
        return "bus/relPsnAdd";
    }


    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    @Log(module = "关联方管理", description = "编辑列表数据")
    public String edit(@PathVariable Integer pid, Model model) {

        RelPsn relPsn = relPsnService.get(pid);
        if (relPsn.getMobile() != null) {
            relPsn.setMobile(new BigDecimal(relPsn.getMobile()).toString());
        }
        model.addAttribute("relPsn", relPsn);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "edit");
        return "bus/relPsnAddOrEdit";
    }


    /**
     * 编辑按钮查看权限后端查询
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/createPerisExist")
    @ResponseBody
    @Log(module = "关联方管理", description = "按钮查看权限后端查询")
    public Map<String, Boolean> createPerisExist(Integer pid) {
        Map<String, Boolean> map = new HashMap<>();
        RelPsn relPsn = relPsnService.get(pid);
        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
        if (userid.equals(relPsn.getAmendId())) {
            map.put(WebCode.SUCCESS, true);
        } else {
            map.put(WebCode.SUCCESS, false);

        }
        return map;
    }


    /**
     * 自然人归属的金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/proUpOfPsnList/{pid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "自然人归属产品列表数据")
    public Map<String, Object> proUpOfPsnList(@PathVariable String pid, Model model, Page<RelProVo> page) {
        List<RelProVo> relProVoList = relPsnAndOrgOfProService.selectRelProUpOfPsnInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relProVoList.size());
        int pages = (relProVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelProVo> relProVos = new ArrayList<RelProVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relProVoList.size()) {
                relProVos.add(relProVoList.get(i));
            }
        }
        page.setResult(relProVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 自然人关联下级金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/proDownOfPsnList/{pid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "自然人下级金融产品列表数据")
    public Map<String, Object> proDownOfPsnList(@PathVariable String pid, Model model, Page<RelProVo> page) {
        List<RelProVo> relProVoList = relPsnAndOrgOfProService.selectRelProDownOfPsnInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relProVoList.size());
        int pages = (relProVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelProVo> relProVos = new ArrayList<RelProVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relProVoList.size()) {
                relProVos.add(relProVoList.get(i));
            }
        }
        page.setResult(relProVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 自然人关联下级金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/addProUpOfPsn/{pid}")
    @Log(module = "关联方管理", description = "自然人下级金融产品列表数据")
    public String addProUpOfPsn(@PathVariable Integer pid, Model model) {
        model.addAttribute("opt", "add");
        // 自然人pid
        model.addAttribute("fromPid", pid);
        return "bus/proOfPsnAddOrEdit";
    }


    /**
     * 反显，查询金融产品信息
     */
    @RequestMapping(value = "/selectRelOrg")
    @ResponseBody
    @Log(module = "关联方管理", description = "金融产品反显列表数据")
    public List<RelOrg> selectRelPsn(String orgName, String identifyType, String uniscCode) {
        List<RelOrg> relOrgs = new ArrayList<>();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").nnl("aligCode").build();
        if (!StringUtils.isEmpty(orgName)) {
            filters.add(new Filter("orgName", orgName));
        }
        if (!StringUtils.isEmpty(identifyType)) {
            filters.add(new Filter("identifyType", identifyType));
        }
        if (!StringUtils.isEmpty(uniscCode)) {
            filters.add(new Filter("uniscCode", uniscCode));
        }
        relOrgs = relOrgService.select(filters);

        return relOrgs;
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    @Log(module = "关联方管理", description = "自然人查看列表数据")
    public String view(@PathVariable Integer pid, Model model) {
        RelPsn relPsn = relPsnService.get(pid);
       // List<RelPsn> relPsns = relPsnService.selectPsnInformation(relPsn);
        UserProfile profile = UserProfileUtils.getUserProfile();
        //证件号码脱敏，身份证保留前6后3位，其余保留前3后2位
        if (relPsn.getIdentifyType().equals("1")) {
            relPsn.setIdentifyNumber(idEncrypt(relPsn.getIdentifyNumber()));
        }else{
            relPsn.setIdentifyNumber(otherIdEncrypt(relPsn.getIdentifyNumber()));
        }
        //如果创建人id和登录id不同的话,进行下面的电话号码和证件号码规避
//        relPsn.setIdentifyNumber(new ConfidentialInfoUtil().desensitizedIdNumber(relPsn.getIdentifyNumber()));
//        relPsn.setMobile(new ConfidentialInfoUtil().hideAllPhoneNum(relPsn.getMobile()));
        model.addAttribute("relPsn", relPsn);
        model.addAttribute("opt", "view");
        model.addAttribute("op", "view");

        return "bus/relPsnAddOrEdit";
    }

    /**
     * 委托机构确认查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/confirmView/{pid}")
    public String confirmView(@PathVariable Integer pid, Model model) {

        RelPsn relPsn = relPsnService.get(pid);
        //给证件号码加密
        relPsn.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relPsn.getIdentifyNumber()));
        relPsn.setMobile(new ConfidentialInfoUtil().hideAllPhoneNum(relPsn.getMobile()));
        Map result = new HashMap();
        model.addAttribute("relPsn", relPsn);
        model.addAttribute("relPsn2", result);
        model.addAttribute("opt", "view");
        return "ex/psnForm";
    }

    /**
     * 我的工作台查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewForm/{pid}")
    @Log(module = "关联方管理", description = "自然人查看列表数据")
    public String viewForm(@PathVariable Integer pid, Model model) {

        RelPsn relPsn = relPsnService.get(pid);
        //给证件号码加密
        relPsn.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relPsn.getIdentifyNumber()));
        relPsn.setMobile(new ConfidentialInfoUtil().hideAllPhoneNum(relPsn.getMobile()));
        List<Filter> filters = new Filter.Builder().eq("flagPid", pid).build();
        List<FilRelPsn> list = filRelPsnService.select(filters);
//        if (list.size() >= 1) {
//            FilRelPsn filRelPsn = list.get(0);
//            RelPsn relPsnL = new RelPsn();
//            BeanUtils.copyProperties(filRelPsn, relPsnL);
//            //给证件号码加密
//            relPsnL.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relPsnL.getIdentifyNumber()));
//            relPsnL.setMobile(new ConfidentialInfoUtil().hideAllPhoneNum(relPsnL.getMobile()));
//            //实体类转json
//            JSONObject relPsn1 = (JSONObject) JSON.toJSON(relPsn);
//            JSONObject relPsn2 = (JSONObject) JSON.toJSON(relPsnL);
//            Map result = JsonUtil.compareJson(relPsn1, relPsn2, null);
//            model.addAttribute("relPsn", relPsn);
//            model.addAttribute("relPsn2", result);
//            model.addAttribute("opt", "view");
//        } else {

        Map result = new HashMap();
        model.addAttribute("relPsn", relPsn);
        model.addAttribute("relPsn2", result);
        model.addAttribute("opt", "view");
//        }

        return "ex/psnForm";
    }

    /**
     * 交易对手查询
     */
    @RequestMapping(value = "/relNameSelect")
    @ResponseBody
    public Map<String, Object> queryVo(Page<PsnAndOrgVo> page, Model model, HttpServletRequest request, String traSubName, String tranType) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        List<Filter> filterss = new ArrayList<>();
        if (filters.size() > 0) {
            Filter filter = filters.get(0);
            filterss = new Filter.Builder().like("orgName", filter.getValue()).build();
        }
        orders.add(new Order("modifiedTime"));
        filters.add(new Filter("delflag", "1"));
        filters.add(new Filter("status", "6"));
        filters.add(new Filter("effectRelation", "1"));
//        filters.add(new Filter("mgerOrg", traSubName));


        filterss.add(new Filter("delflag", "1"));
        filterss.add(new Filter("approvalStatus", "6"));
        filterss.add(new Filter("effectRelation", "1"));
//        filterss.add(new Filter("mgerOrg", traSubName));
        //获取法人或其他组织证件类型字典
        List<DictItem> orgDicItems = TraUtil.getDictItems("BUS-ORGIdentifyType");
        //获取自然人关联方证件类型字典
        List<DictItem> psnDicItems = TraUtil.getDictItems("BUS-IdentifyType");
        List<RelPsn> relPsns = relPsnService.select(filters);
        List<RelOrg> relOrgs = relOrgService.select(filterss);
        List<PsnAndOrgVo> psnAndOrgVos = new ArrayList<>();
        for (RelPsn a : relPsns) {
            PsnAndOrgVo b = new PsnAndOrgVo();
            b.setPid(a.getPid().toString());
            b.setType("PSN");
            b.setName(a.getName());
            b.setIdentifyNumber(a.getIdentifyNumber());
            b.setIdentifyType(a.getIdentifyType());
            b.setIdentifyTypes(TraUtil.getIdentifyType(psnDicItems, a.getIdentifyType()));
            b.setIdentifyTypePSN(a.getIdentifyType());
            /*BigDecimal tradeZeroAmount = new BigDecimal(0);
            BigDecimal currYearTotalTradeAmount = baseService.getCurrYearTotalTradeAmount(Integer.parseInt(b.getPid()), b.getType(), traSubName, AccumulatType.ZERO_NO.getHandler(), AccumulatType.COUNT_ALL.getHandler());
            if (currYearTotalTradeAmount.compareTo(BigDecimal.valueOf(0)) > 0) {
                tradeZeroAmount = baseService.getCurrYearTotalTradeAmount(Integer.parseInt(b.getPid()), b.getType(), traSubName, AccumulatType.ZERO_YES.getHandler(), AccumulatType.COUNT_PART.getHandler());
            }
            b.setTradeZeroAmount(tradeZeroAmount);
            b.setTotalTradeAmount(currYearTotalTradeAmount);*/
            psnAndOrgVos.add(b);
        }
        for (RelOrg a : relOrgs) {
            PsnAndOrgVo b = new PsnAndOrgVo();
            b.setPid(a.getPid().toString());
            b.setType("ORG");
            b.setName(a.getOrgName());
            b.setIdentifyNumber(a.getUniscCode());
            b.setIdentifyType(a.getIdentifyType());
            b.setIdentifyTypes(TraUtil.getIdentifyType(orgDicItems, a.getIdentifyType()));
            b.setIdentifyTypeORG(a.getIdentifyType());
            /*BigDecimal currYearTotalTradeAmount = new BigDecimal(0);
            if (tranType != null && "02".equals(tranType)) {//资金运用类
                currYearTotalTradeAmount = baseService.getCurrYearTotalTradeAmount(Integer.parseInt(b.getPid()), b.getType(), traSubName, tranType, AccumulatType.ZERO_NO.getHandler(), AccumulatType.COUNT_ALL.getHandler());
            } else {
                currYearTotalTradeAmount = baseService.getCurrYearTotalTradeAmount(Integer.parseInt(b.getPid()), b.getType(), traSubName, AccumulatType.ZERO_NO.getHandler(), AccumulatType.COUNT_ALL.getHandler());
            }
            b.setTotalTradeAmount(currYearTotalTradeAmount);
            BigDecimal tradeZeroAmount = new BigDecimal(0);
            if (currYearTotalTradeAmount.compareTo(BigDecimal.valueOf(0)) > 0) {
                tradeZeroAmount = baseService.getCurrYearTotalTradeAmount(Integer.parseInt(b.getPid()), b.getType(), traSubName, AccumulatType.ZERO_YES.getHandler(), AccumulatType.COUNT_PART.getHandler());
            }
            b.setTradeZeroAmount(tradeZeroAmount);*/
            psnAndOrgVos.add(b);
        }

        page.setTotal(psnAndOrgVos.size());
        int pages = (psnAndOrgVos.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<PsnAndOrgVo> psnAndOrgVoss = new ArrayList<PsnAndOrgVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < psnAndOrgVos.size()) {
                psnAndOrgVoss.add(psnAndOrgVos.get(i));
            }
        }
        page.setResult(psnAndOrgVoss);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 单个交易对手在途交易金额查询
     *
     * @param relId
     * @param traSubName
     * @param totolTradeAmount
     * @return
     */
    @RequestMapping(value = "/queryRelNameAmount")
    @ResponseBody
    public Map<String, Object> queryRelNameAmount(String relId, String traSubName, String totolTradeAmount) {
        Map<String, Object> map = new HashMap<>();
        String relName = null;
        BigDecimal totalTradeAmount = new BigDecimal(0);
        try {
            //查询关联关系自然人数据
            List<Filter> filters_psn = new Filter.Builder().build();
            filters_psn.add(new Filter("pid", relId));
            filters_psn.add(new Filter("delflag", "1"));
            filters_psn.add(new Filter("mgerOrg", traSubName));
            filters_psn.add(new Filter("status", "6"));
            List<RelPsn> relPsns = relPsnService.select(filters_psn);
            //查询关联关系机构
            List<Filter> filters_org = new Filter.Builder().build();
            filters_org.add(new Filter("pid", relId));
            filters_org.add(new Filter("delflag", "1"));
            filters_org.add(new Filter("mgerOrg", traSubName));
            filters_org.add(new Filter("approvalStatus", "6"));
            List<RelOrg> relOrgs = relOrgService.select(filters_org);
            //在途金额
            if (relPsns != null && relPsns.size() > 0) {
                RelPsn psn = relPsns.get(0);
                relName = psn.getName();
                BigDecimal psn_inRoadTotalTradeAmount = baseService.getInRoadTotalTradeAmount(Integer.valueOf(psn.getPid()),
                        "PSN", traSubName);
                totalTradeAmount = totalTradeAmount.add(psn_inRoadTotalTradeAmount);
            }
            if (relOrgs != null && relOrgs.size() > 0) {
                RelOrg org = relOrgs.get(0);
                relName = org.getOrgName();
                BigDecimal org_inRoadTotalTradeAmount = baseService.getInRoadTotalTradeAmount(Integer.valueOf(org.getPid()),
                        "ORG", traSubName);
                totalTradeAmount = totalTradeAmount.add(org_inRoadTotalTradeAmount);
            }
            List<Filter> filters = new Filter.Builder().eq("dictCode", "INS-subjectName").build();
            List<DictItem> dictItems = dictItemService.select(filters);
            Map traSubNames = new HashMap();
            for (DictItem dic : dictItems) {
                traSubNames.put(dic.getItemValue(), dic.getItemLabel());
            }
            //构造提示信息
            StringBuffer message = new StringBuffer();
            message.append("关联交易在途数据预警:交易主体:(").append(traSubNames.get(traSubName)).append("),交易对手:(").append(relName).append("),");
            message.append("累计交易金额:(").append(totolTradeAmount).append("万元),在途金额:(").append(totalTradeAmount).append("万元)");
            map.put(WebCode.SUCCESS, true);
            map.put(WebCode.MSG, message);
            map.put("amount", totalTradeAmount);
            map.put("inRoldAmount", message.toString());
        } catch (ServiceException e) {
            map.put(WebCode.SUCCESS, false);
            map.put(WebCode.MSG, "获取信息失败");
            logger.error("单个交易对手在途交易金额查询异常:{}", e);
        }
        return map;
    }

    /**
     * 保存
     *
     * @param relPsn
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @Log(module = "关联方管理", description = "自然人保存")
    public Map<String, Object> save(RelPsn relPsn, TraEnclosureVo traEnclosureVo) {

        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(relPsn);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        String regulatorOth1 = relPsn.getRegulatorOth1();
        //根据部门存储数据权限
        UserProfile profile = UserProfileUtils.getUserProfile();
        relPsn.setAmendId(profile.getStaffCode());
        relPsn.setAmendName(profile.getStaffName());
//        relPsn.setMgerOrg(profile.getUnitCode());
        relPsn.setOperator(profile.getStaffName());
        relPsn.setTransactionDep(profile.getDeptName());
        relPsn.setDelflag("1");
        RelPsnHistory relPsnHistory = new RelPsnHistory();
        BeanUtils.copyProperties(relPsn, relPsnHistory);
        relPsnHistory.setPid(null);
        relPsnHistory.setCreateTime(new Date());
        relPsnHistory.setDelflag("1");
        Boolean isExist = false;
        int index = 0;
        String relSerial = "";
        if (relPsn.getPid() != null && !relPsn.getPid().equals("")) {
            isExist = relPsnService.exists(relPsn.getPid());
        }
        try {
            if (isExist) {
                RelPsn updateHistroy = relPsnService.get(relPsn.getPid());
                BeanUtils.copyProperties(updateHistroy, relPsnHistory);
                relPsnHistory.setPid(null);
                relPsnHistory.setCreateTime(new Date());
                relPsnHistory.setDelflag("1");
                //查询此人是否存在有效的关联关系，存在改状态为待提交，不存在状态改完待补录
                List<Filter> ffilters = new Filter.Builder().eq("fromPid", relPsn.getPid().toString()).build();
                List<Filter> tfilters = new Filter.Builder().eq("toPid", relPsn.getPid().toString()).build();
                List<Rel> dbRels = relService.select(ffilters);
                List<Rel> tdbRels = relService.select(tfilters);
                if (CollectionUtils.isNotEmpty(dbRels) || CollectionUtils.isNotEmpty(tdbRels)) {
                    //待提交
//                    relPsn.setStatus("3");
                    new ConfirmUtil().relPsn(relPsn); //设置状态
                } else {
                    if (StringUtils.isEmpty(relPsn.getName()) || StringUtils.isEmpty(relPsn.getIdentifyType()) ||
                            StringUtils.isEmpty(relPsn.getIdentifyNumber()) || StringUtils.isEmpty(relPsn.getDomesticOrForeign())){
                        relPsn.setStatus("1");
                    }else {
                        relPsn.setStatus("6");//已归档
                    }
                }
                index = relPsnService.updateSelective(relPsn);
                relSerial = relPsnService.get(relPsn.getPid()).getRelSerial();
                if(index > 0){
                    // 更新关系表中的名称和证件号
                    List<Filter> fromFilters = new Filter.Builder().eq("fromPid", relPsn.getPid()).eq("delflag",Dict.SF_1).build();
                    List<Rel> fromlist = relService.select(fromFilters);
                    for (Rel rel:fromlist ) {
                        rel.setFromIdentifyNumber(relPsn.getIdentifyNumber());
                        rel.setFromMemo(relPsn.getName());
                        relService.updateSelective(rel);
                    }
                    List<Filter> toFilters = new Filter.Builder().eq("toPid", relPsn.getPid()).eq("delflag",Dict.SF_1).build();
                    List<Rel> tolist = relService.select(toFilters);
                    for (Rel rel:tolist) {
                        rel.setFromIdentifyNumber(relPsn.getIdentifyNumber());
                        rel.setFromMemo(relPsn.getName());
                        relService.updateSelective(rel);
                    }

                    relPsnHistory.setRecordType("2");
                    relPsnHistory.setSourceId(relPsn.getPid());
                    relPsnHistory.setStatus(relPsn.getStatus());
                    relPsnHistoryService.insertSelective(relPsnHistory);
                }
            } else {
                List<RelPsn> relPsnList = relPsnService.select(relPsn);
                if (relPsnList.size() > 0) {
                    relPsn = relPsnList.get(0);
                } else {
                    if (StringUtils.isEmpty(relPsn.getName()) || StringUtils.isEmpty(relPsn.getIdentifyType()) ||
                            StringUtils.isEmpty(relPsn.getIdentifyNumber()) || StringUtils.isEmpty(relPsn.getDomesticOrForeign())){
                        relPsn.setStatus("1");
                    }else {
                        relPsn.setStatus("6");//已归档
                    }
                    relPsn.setDelflag("1");// 逻辑删除标记
                    relPsn.setEffectRelation("0");
                    relPsn.setIsBlack("0");//是否为黑名单 “0”否
                    relPsn.setDataSource("1");//数据来源为手工录入
                    relPsn.setIsAssoParty("1");//是否为关联方 1否，0是
                    relPsn.setRelSerial(BatchNumberUtil.makeBatchNumber());
                    String maxBh = relPsnService.getMaxBh();
                    relPsn.setGlfbh(maxBh);
                    relPsn.setRelateNumber(maxBh);
                    String recordNumberNew = "";
                    // 新增时需要查询当前是否存在相应版本号的数据
                    List<Filter> isExistNum = new Filter.Builder().nnl("recordNumber").eq("delflag", "1").build();
                    List<RelPsn> psnList = relPsnService.select(isExistNum);

                    if(CollectionUtils.isNotEmpty(psnList)){
                        recordNumberNew = psnList.get(0).getRecordNumber();
                    }else {
                        // 查询当前最新版本号
                        List<Filter> filters = new Filter.Builder().nnl("recordNumber").build();
                        List<Order> orders = new Order.Builder().desc("createTime").build();
                        List<RelPsn> psnDayList = relPsnService.select(filters, orders);
                        boolean flag = CollectionUtils.isNotEmpty(psnDayList);
                        if(flag){
                            recordNumberNew = psnDayList.get(0).getRecordNumber();
                        }else {
                            recordNumberNew = IntegerUtils.recordNumber(flag, null);
                        }
                    }

                    relPsn.setRecordNumber(recordNumberNew);
                    index = relPsnService.insertSelective(relPsn);
                    if(index > 0){
                        RelPsn psn =  relPsnService.get(relPsn);
                        relPsnHistory.setRecordType("1");
                        relPsnHistory.setSourceId(psn.getPid());
                        relPsnHistory.setRecordNumber(recordNumberNew);
                        relPsnHistory.setStatus(relPsn.getStatus());
                        relPsnHistoryService.insertSelective(relPsnHistory);
                    }
                }
                relSerial = relPsn.getRelSerial();
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            String merge = relPsn.getMgerOrg();
            relFileService.updateRelSerial(docTableSelectItem, relSerial, merge);
            if (index != 0) {
                List<RelPsn> relPsn1 = relPsnService.select(relPsn);
                tips.put("pid", relPsn1.get(0).getPid());
                relPsnService.updateSelective(relPsnService.get(relPsn1.get(0).getPid()));
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
    @RequestMapping(value = "/remove/{pids}")
    @ResponseBody
    @Log(module = "关联方管理", description = "自然人删除")
    public Map<String, Object> remove(@PathVariable String pids) {

        Map<String, Object> tips = new HashMap<String, Object>();
        String[] pidStrArr = pids.split(",");
        for (String pidStr : pidStrArr) {
            int pid = Integer.parseInt(pidStr);
            RelPsn relPsn = relPsnService.get(pid);
            relPsn.setDelflag("0");
            relPsnService.updateSelective(relPsn);
            List<Filter> tfilters = new Filter.Builder().eq("toPid", pidStr).eq("toType", "PSN").eq("delflag", "1").build();
            boolean exists = relService.exists(tfilters);
            if (exists) {
                List<Rel> select = relService.select(tfilters);
                for (Rel rel : select) {
                    rel.setRelStatus("02");
                    relService.updateSelective(rel);
                }
            }
            // 删除数据时备份表数据备份
            RelPsnHistory relPsnHistory = new RelPsnHistory();
            BeanUtils.copyProperties(relPsn, relPsnHistory);
            relPsnHistory.setPid(null);
            relPsnHistory.setDelflag("1");
            relPsnHistory.setRecordType("3");
            relPsnHistory.setSourceId(pid);
            relPsnHistory.setCreateTime(new Date());
            relPsnHistoryService.insertSelective(relPsnHistory);
        }

        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, "删除成功");
        return tips;
    }

    /**
     * 加入/移出黑名单
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/addOrRemoveBlack/{pid}")
    @Log(module = "关联方管理", description = "自然人加入/移除黑名单")
    public String addBlack(@PathVariable Integer pid, Model model) {
        RelPsn relPsn = relPsnService.get(pid);
        model.addAttribute("relPsn", relPsn);
        if ("0".equals(relPsn.getIsBlack())) {
            model.addAttribute("addOrRemoveBlack", "加入黑名单原因");
        } else {
            model.addAttribute("addOrRemoveBlack", "移出黑名单原因");
        }
        return "bus/addOrRemoveBlack";
    }

    /**
     * 加入/移出黑名单
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/addOrRemoveBlack")
    @ResponseBody
    @Log(module = "关联方管理", description = "自然人加入/移除黑名单")
    public Map<String, Object> addBlack(RelPsn relPsn) {

        Map<String, Object> tips = new HashMap<String, Object>();
        RelPsn relPsn1 = relPsnService.get(relPsn.getPid());
        try {
            relPsn1.setStatus("4");
            relPsn1.setBlackReason(relPsn.getBlackReason());
            relPsnService.updateSelective(relPsn1);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "提交成功");
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "提交失败");
        }

        return tips;
    }

    /**
     * 提交审核所需数据
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/subAudit/{pid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "自然人提交")
    public JSONObject subAudit(@PathVariable Integer pid) {
        JSONObject result = new JSONObject();
        RelPsn relPsn = relPsnService.get(pid);
        result.put("relPsn", relPsn);
        return result;
    }

    /**
     * 解除关联关系
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/disconnection/{pid}")
    @Log(module = "关联方管理", description = "解除关联关系")
    public String disconnection(@PathVariable Integer pid, Model model) {
        model.addAttribute("toPid", pid);
        return "bus/disconnection";
    }

    /**
     * 解除关联关系
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/disconnection")
    @ResponseBody
    @Log(module = "关联方管理", description = "解除关联关系")
    public Map<String, Object> disconnection(RelPsnVo relPsnVo) {
        RelService relService = SpringContextHolder.getBean(RelService.class);
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            RelPsn relPsn = relPsnService.get(relPsnVo.getToPid());
            relPsn.setStatus("4");
            relPsnService.updateSelective(relPsn);
            Rel rel = new Rel();
            rel.setToPid(relPsnVo.getToPid().toString());
            List<Rel> relList = relService.select(rel);
            if (relList.size() > 0) {
                for (Rel rel1 : relList) {
                    rel1.setLiftReason(relPsnVo.getLiftReason());
                    relService.updateSelective(rel1);
                    relService.removeRel(rel1.getFromPid(), rel1.getToPid(), rel1.getFromType(), rel1.getToType());
                }
            } else {
                Rel rel1 = new Rel();
                rel1.setFromPid(relPsnVo.getToPid().toString());
                relList = relService.select(rel1);
                if (relList.size() > 0) {
                    for (Rel rel2 : relList) {
                        rel2.setLiftReason(relPsnVo.getLiftReason());
                        relService.updateSelective(rel2);
                        relService.removeRel(rel2.getFromPid(), rel2.getToPid(), rel2.getFromType(), rel2.getToType());
                    }
                }
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "提交成功");
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "提交失败");
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
    @Log(module = "关联方管理", description = "查重")
    public Map<String, Object> check(Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = relPsnService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 自然人关系信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/affiliateList/{pid}")
    @Log(module = "关联方管理", description = "关系自然人列表数据")
    public String affiliateView(@PathVariable String pid, int op, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("op", op);
//		model.addAttribute("opt", opt);
        return "bus/affiliateList";
    }

    /**
     * 自然人关系信息列表
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/affiliate/{pid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "关系自然人列表数据")
    public Map<String, Object> affiliate(@PathVariable String pid, Model model, Page<RelPsnVo> page) {
        List<RelPsnVo> relPsnVoList = relPsnService.selectRelPsnInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relPsnVoList.size());
        int pages = (relPsnVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelPsnVo> relPsnVos = new ArrayList<RelPsnVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relPsnVoList.size()) {
                relPsnVos.add(relPsnVoList.get(i));
            }
        }
        page.setResult(relPsnVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }


    /**
     * 自然人关系信息列表   下级关联人
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/affiliateDown/{pid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "关系自然人下级自然人列表数据")
    public Map<String, Object> affiliateDown(@PathVariable String pid, Model model, Page<RelPsnVo> page) {
        List<RelPsnVo> relPsnVoList = relPsnService.selectDownRelPsnInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relPsnVoList.size());
        int pages = (relPsnVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelPsnVo> relPsnVos = new ArrayList<RelPsnVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relPsnVoList.size()) {
                relPsnVos.add(relPsnVoList.get(i));
            }
        }
        page.setResult(relPsnVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 新增自然人关系信息页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/addAffiliate/{fromPid}")
    @Log(module = "关联方管理", description = "新增关系自然人列表数据")
    public String addAffiliate(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "add");
        // 自然人pid
        model.addAttribute("fromPid", fromPid);
        return "bus/psnUpAddOrEdit";
    }

    /**
     * 查看自然人关系信息页面
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/viewAffiliate/{pid}/{relId}/{fromPid}")
    @Log(module = "关联方管理", description = "查看关系自然人列表数据")
    public String viewAffiliate(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {

        RelPsn relPsn = relPsnService.get(pid);
        RelService relService = SpringContextHolder.getBean(RelService.class);
        Rel rel = relService.get(relId);
        RelPsnVo relPsnVo = new RelPsnVo();
        BeanUtils.copyProperties(relPsn, relPsnVo);
        relPsnVo.setRelStatement(rel.getRelStatement());
        relPsnVo.setPredicate(rel.getRelPredicate());
        relPsnVo.setEquityShare(rel.getEquityShare());
        relPsnVo.setRelCategory(rel.getRelCategory());
        relPsnVo.setRelCategorySub(rel.getRelCategorySub());
        relPsnVo.setIsValid(rel.getIsValid());
        relPsnVo.setActiveTimeString(rel.getActiveTimeString());
        relPsnVo.setInvalidTimeString(rel.getInvalidTimeString());
        relPsnVo.setShareholdingRatio(rel.getShareholdingRatio());
        relPsnVo.setPledgeRatio(rel.getPledgeRatio());
        relPsnVo.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relPsn.getIdentifyNumber()));
        relPsnVo.setMobile(new ConfidentialInfoUtil().hideAllPhoneNum(relPsn.getMobile()));
      /*  relPsnVo.setActiveTime(rel.getEffectiveDate());
        relPsnVo.setInvalidTime(rel.getIneffectiveDate());*/
        model.addAttribute("relPsnVo", relPsnVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "1");
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);
        model.addAttribute("fromPid", fromPid);

        return "bus/affiliateAddOrEdit";

    }

    /* 金融产品编辑所属自然人*/
    @RequestMapping(value = "/proeditPsn/{pid}/{relId}/{fromPid}")
    @Log(module = "关联方管理", description = "金融产品编辑所属自然人")
    public String proeditPsn(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {
//        shareholdingRatio
        RelPsn relPsn = relPsnService.get(pid);
        // RelService relService = SpringContextHolder.getBean(RelService.class);
        RelPsnAndOrgOfPro rel = relPsnAndOrgOfProService.get(relId);
        RelPsnVo relPsnVo = new RelPsnVo();
        BeanUtils.copyProperties(relPsn, relPsnVo);
        relPsnVo.setRelCategory(rel.getRelCategory());
        relPsnVo.setRelStatement(rel.getRelStatement());
//        relPsnVo.setEffectiveDate(rel.getEffectiveDate());
//        relPsnVo.setIneffectiveDate(rel.getIneffectiveDate());
        relPsnVo.setEquityShare(rel.getEquityShare());
        relPsnVo.setActiveTimeString(rel.getActiveTimeString());
        relPsnVo.setInvalidTimeString(rel.getInvalidTimeString());
        relPsnVo.setRelCategorySub(rel.getRelCategorySub());
        relPsnVo.setShareholdingRatio(rel.getShareholdingRatio());
        model.addAttribute("relPsnVo", relPsnVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "0");
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);
        model.addAttribute("fromPid", fromPid);
        model.addAttribute("parentPid", pid);

        return "bus/proPsnaffiliateAddOrEdit";
    }

    /* 金融产品查看所属自然人*/
    @RequestMapping(value = "/proviewPsn/{pid}/{relId}/{fromPid}")
    @Log(module = "关联方管理", description = "金融产品编辑所属自然人")
    public String proviewPsn(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {
//        shareholdingRatio
        RelPsn relPsn = relPsnService.get(pid);
        // RelService relService = SpringContextHolder.getBean(RelService.class);
        RelPsnAndOrgOfPro rel = relPsnAndOrgOfProService.get(relId);
        RelPsnVo relPsnVo = new RelPsnVo();
        BeanUtils.copyProperties(relPsn, relPsnVo);
        relPsnVo.setRelCategory(rel.getRelCategory());
        relPsnVo.setRelStatement(rel.getRelStatement());
//        relPsnVo.setEffectiveDate(rel.getEffectiveDate());
//        relPsnVo.setIneffectiveDate(rel.getIneffectiveDate());
        relPsnVo.setEquityShare(rel.getEquityShare());
        relPsnVo.setActiveTimeString(rel.getActiveTimeString());
        relPsnVo.setInvalidTimeString(rel.getInvalidTimeString());
        relPsnVo.setRelCategorySub(rel.getRelCategorySub());
        relPsnVo.setShareholdingRatio(rel.getShareholdingRatio());
        model.addAttribute("relPsnVo", relPsnVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "1");
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);
        model.addAttribute("fromPid", fromPid);
        model.addAttribute("parentPid", pid);

        return "bus/proPsnaffiliateAddOrEdit";
    }


    /**
     * 编辑自然人关系信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/editAffiliate/{pid}/{relId}/{fromPid}")
    @Log(module = "关联方管理", description = "编辑关系自然人数据")
    public String editAffiliate(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {

        RelPsn relPsn = relPsnService.get(pid);
        RelService relService = SpringContextHolder.getBean(RelService.class);
        Rel rel = relService.get(relId);
        RelPsnVo relPsnVo = new RelPsnVo();
        BeanUtils.copyProperties(relPsn, relPsnVo);
        relPsnVo.setRelCategory(rel.getRelCategory());
        relPsnVo.setRelCategorySub(rel.getRelCategorySub());
        relPsnVo.setRelStatement(rel.getRelStatement());
        relPsnVo.setPredicate(rel.getRelPredicate());
        relPsnVo.setEquityShare(rel.getEquityShare());
        relPsnVo.setRelCategory(rel.getRelCategory());
        relPsnVo.setRelCategorySub(rel.getRelCategorySub());
        relPsnVo.setIsValid(rel.getIsValid());
        relPsnVo.setActiveTimeString(rel.getActiveTimeString());
        relPsnVo.setInvalidTimeString(rel.getInvalidTimeString());
        relPsnVo.setShareholdingRatio(rel.getShareholdingRatio());
        relPsnVo.setPledgeRatio(rel.getPledgeRatio());
        model.addAttribute("relPsnVo", relPsnVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "0");
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);
        model.addAttribute("fromPid", fromPid);

        return "bus/affiliateAddOrEdit";
    }

    /**
     * 保存自然人关系信息
     *
     * @param relPsnVo
     * @return
     */
    @RequestMapping(value = "/saveAffiliate/{parentPid}/{fromPid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "保存关系自然人数数据")
    public Map<String, Object> saveAffiliate(@PathVariable String parentPid, @PathVariable String fromPid, RelPsnVo relPsnVo) throws ParseException {
        Map<String, Object> tips = new HashMap<String, Object>();
        //归属关联方
        RelPsn fromPsn = relPsnService.get(Integer.valueOf(parentPid));
        //新增关系to自然人
        RelPsn relPsn = relPsnService.get(Integer.valueOf(fromPid));
        //新增关联方根据上级定为码以及关系判断是否关联方,更新to定为码
        //更新关系表
        Rel rel = new Rel();
        rel.setPid(relPsnVo.getRelId());
        rel.setDelflag("1");
        //归属关联方pid
        rel.setFromPid(parentPid);
        //添加基本信息自然人的pid
        rel.setToPid(fromPid);
        Rel dbRel = relService.get(rel);
        rel.setRelCategory(relPsnVo.getRelCategory());
        rel.setRelCategorySub(relPsnVo.getRelCategorySub());
        BeanUtils.copyProperties(relPsnVo, rel);
        rel.setEquityShare(relPsnVo.getEquityShare());
        rel.setPledgeRatio(relPsnVo.getPledgeRatio());
        rel.setShareholdingRatio(relPsnVo.getShareholdingRatio());
        relPsnVo.setPredicate(rel.getRelPredicate());
        rel.setRelPredicate(relPsnVo.getPredicate());//关联关系判定
        rel.setDelflag("1");
        rel.setFromType("PSN");
        rel.setToType("PSN");
        rel.setShareholdingRatio(relPsnVo.getShareholdingRatio());
        new ConfirmUtil().relPsn(relPsn); //设置状态


        relPsnService.updateSelective(relPsn);
        //关系状态为待新增
        rel.setRelStatus("01");
        rel.setActiveTimeString(relPsnVo.getActiveTimeString());
        rel.setInvalidTimeString(relPsnVo.getInvalidTimeString());
        //如果查到两人间有相同关系，更新原先关系
        boolean isExist = false;
        if (dbRel != null) {
            isExist = true;
            rel.setPid(dbRel.getPid());
        }
        if (isExist) {
            relService.updateSelective(rel);
        } else {
            relService.insert(rel);
        }
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }

    /**
     * 删除
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/removeRelInformation/{pid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "删除自然人数据")
    public Map<String, Object> removeRelInformation(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {

            Rel rel1 = relService.get(pid);
            String toPid = rel1.getToPid();
            String toType = rel1.getToType();
            relService.deleteByPrimaryKey(pid);
            List<Filter> ffilters = new Filter.Builder().eq("toPid", toPid).eq("toType", toType).build();
            if (relService.exists(ffilters)) {
                RelPsn relPsn = relPsnService.get(Integer.parseInt(toPid));
                relPsn.setStatus("3");
                relPsnService.updateSelective(relPsn);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            } else {
                RelPsn relPsn = relPsnService.get(Integer.parseInt(toPid));
                relPsn.setStatus("1");
                relPsnService.updateSelective(relPsn);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }


    /**
     * 机构关系信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnRelOrgList/{pid}")
    @Log(module = "关联方管理", description = "关系机构列表数据")
    public String rightsList(@PathVariable String pid, int op, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("op", op);
//		model.addAttribute("opt", opt);
        return "bus/psnRelOrgList";
    }

    /**
     * 自然人关联金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/proOfPsnList/{pid}")
    @Log(module = "关联方管理", description = "金融产品列表数据")
    public String psnRelProList(@PathVariable String pid, int op, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("op", op);
//		model.addAttribute("opt", opt);
        return "bus/proOfPsnList";
    }

    /**
     * 保存自然人的金融产品关系信息
     *
     * @param relProVo
     * @return
     */
    @RequestMapping(value = "/saveProOfPsn/{parentPid}/{fromPid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "保存关系金融产品数据")
    public Map<String, Object> saveProOfPsn(@PathVariable String parentPid, @PathVariable String fromPid, RelProVo relProVo) throws Exception {
        Map<String, Object> tips = new HashMap<String, Object>();
        //归属关联方金融产品
        RelPro fromPro = relProService.get(Integer.valueOf(parentPid));
        //新增关系to自然人
        RelPsn relPsn = relPsnService.get(Integer.valueOf(fromPid));
//        BeanUtils.copyProperties(relPsnVo, relPsn);
        String fromAlig = fromPro.getAligCode();
        String chAligCode = relPsn.getAligCode();
        //新增关联方根据上级定为码以及关系判断是否关联方,更新to定为码
        if (StringUtils.isNotEmpty(fromAlig)) {
            //更新关系表
            RelPsnAndOrgOfPro relPsnAndOrgOfPro = new RelPsnAndOrgOfPro();
            relPsnAndOrgOfPro.setPid(relProVo.getRelId());
            relPsnAndOrgOfPro.setDelflag("1");
            //归属关联方pid
            relPsnAndOrgOfPro.setFromPid(parentPid);
            //添加基本信息自然人的pid
            relPsnAndOrgOfPro.setToPid(fromPid);
            relPsnAndOrgOfPro.setRelCategory(relProVo.getRelCategory());
            relPsnAndOrgOfPro.setRelCategorySub(relProVo.getRelCategorySub());
            relPsnAndOrgOfPro.setRelStatement(relProVo.getRelStatement());
            RelPsnAndOrgOfPro dbRelPsnAndOrgOfPro = relPsnAndOrgOfProService.get(relPsnAndOrgOfPro);
            //如果查到两人间有相同关系，更新原先关系
            boolean isExist = false;
            if (dbRelPsnAndOrgOfPro != null) {
                isExist = true;
                relPsnAndOrgOfPro.setPid(dbRelPsnAndOrgOfPro.getPid());
            }
            String[] aligs = fromAlig.split(",");
            for (String alig : aligs) {
                List<Filter> aligFilters = new Filter.Builder()
                        .eq("paAligCode", alig)
                        .eq("relCategoryVal", relProVo.getRelCategory())
                        .eq("assoTypeVal", relProVo.getRelCategorySub())
                        .build();
                AligMap aligMap = aligMapService.get(aligFilters);
                //to定为码不为空则为关联方，需要更新定为码
                if (aligMap != null) {
                    String toAligCode = aligMap.getChAligCode();
                    //判断to定为码是否已存在,确保不重复加
                    if (StringUtils.isNotEmpty(chAligCode)) {
                        String[] chAligCodes = chAligCode.split(",");
                        List<String> codeList = Arrays.asList(chAligCodes);
                        if (!codeList.contains(toAligCode)) {
                            relPsn.setAligCode(chAligCode + "," + toAligCode);
                        }
                    } else {
                        relPsn.setAligCode(toAligCode);
                    }
                    //关系设置定为码
                    relPsnAndOrgOfPro.setFromAligCode(alig);
                    relPsnAndOrgOfPro.setToAligCode(toAligCode);
                    relPsnAndOrgOfPro.setRegulator(aligMap.getCaliber());
                    relPsnAndOrgOfPro.setDelflag("1");
                    relPsnAndOrgOfPro.setFromType("PRO");
                    relPsnAndOrgOfPro.setToType("PSN");
                    relPsnAndOrgOfPro.setShareholdingRatio(relProVo.getShareholdingRatio());
                    //关联人设置口径
                    relPsnAndOrgOfPro.setRegulator(aligMap.getCaliber());
                    relPsnAndOrgOfPro.setRelStatus("3");
                    relPsnAndOrgOfPro.setIsValid(relProVo.getIsValid());
                    relPsnAndOrgOfPro.setEffectiveDate(relProVo.getEffectiveDate());
                    relPsnAndOrgOfPro.setIneffectiveDate(relProVo.getIneffectiveDate());
                    relPsnAndOrgOfPro.setRelStatement(relProVo.getRelStatement());
                } else {
                    //为null  代表to 不是关联方，仅仅维持关系即可
                    //关系设置定为码
                    relPsnAndOrgOfPro.setFromAligCode(alig);
                    relPsnAndOrgOfPro.setDelflag("1");
                    relPsnAndOrgOfPro.setFromType("PRO");
                    relPsnAndOrgOfPro.setToType("PSN");
                    relPsnAndOrgOfPro.setShareholdingRatio(relProVo.getShareholdingRatio());
                    relPsnAndOrgOfPro.setIsValid(relProVo.getIsValid());
                    relPsnAndOrgOfPro.setEffectiveDate(relProVo.getEffectiveDate());
                    relPsnAndOrgOfPro.setIneffectiveDate(relProVo.getIneffectiveDate());
                    relPsnAndOrgOfPro.setRelStatement(relProVo.getRelStatement());
                    //设置成待审批
                    relPsn.setStatus("3");
                }
                relPsnService.updateSelective(relPsn);
                //关系状态为待新增
                relPsnAndOrgOfPro.setRelStatus("01");
                if (isExist) {
                    relPsnAndOrgOfProService.updateSelective(relPsnAndOrgOfPro);
                } else {
                    relPsnAndOrgOfProService.insert(relPsnAndOrgOfPro);
//                    relPsnService.insertNeo4jProToPsn(fromPro, relPsn, relPsnAndOrgOfPro);
                }
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                break;
            }
        } else {
            //非关联方，返回不可添加提示
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "超过关联方关系判定范围");
        }
        return tips;
    }

    /**
     * 新增机构关系信息页面
     */
    @RequestMapping(value = "addPsnRelOrg/{orgid}")
    @Log(module = "关联方管理", description = "新增关系机构数据")
    public String addOrg(Model model, @PathVariable Integer orgid) {
        model.addAttribute("opt", "add");
        model.addAttribute("fromPid", orgid);
        return "bus/psnRelOrgAddOrEdit";
    }

    /**
     * 编辑机构关系信息页面
     */
    @RequestMapping(value = "editPsnToOrg/{pid}/{relId}/{fromPid}")
    @Log(module = "关联方管理", description = "编辑自然人->关系机构数据")
    public String editPsnToOrg(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {
        RelOrg relOrg = relOrgService.get(pid);
        RelService relService = SpringContextHolder.getBean(RelService.class);
        Rel rel = relService.get(relId);
        RelOrgVo relOrgVo = new RelOrgVo();
        BeanUtils.copyProperties(relOrg, relOrgVo);
        relOrgVo.setRelCategory(rel.getRelCategory());
        relOrgVo.setRelCategorySub(rel.getRelCategorySub());
        relOrgVo.setRelStatement(rel.getRelStatement());
        relOrgVo.setPredicate(rel.getRelPredicate());
        relOrgVo.setEquityShare(rel.getEquityShare());
        relOrgVo.setRelCategory(rel.getRelCategory());
        relOrgVo.setRelCategorySub(rel.getRelCategorySub());
        relOrgVo.setIsValid(rel.getIsValid());
        relOrgVo.setActiveTimeString(rel.getActiveTimeString());
        relOrgVo.setInvalidTimeString(rel.getInvalidTimeString());
        relOrgVo.setShareholdingRatio(rel.getShareholdingRatio());
        relOrgVo.setPledgeRatio(rel.getPledgeRatio());
        model.addAttribute("relOrg", relOrgVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("relId", relId);
        model.addAttribute("op", "0");
        model.addAttribute("fromPid", fromPid);

        return "bus/psnRelOrgAddOrView";
    }


    /**
     * 查看机构关系信息页面
     */
    @RequestMapping(value = "viewPsnRelOrg/{pid}/{relId}/{fromPid}")
    @Log(module = "关联方管理", description = "编辑关系机构数据")
    public String viewPsnRelOrg(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {
        RelOrg relOrg = relOrgService.get(pid);
        RelService relService = SpringContextHolder.getBean(RelService.class);
        Rel rel = relService.get(relId);
        RelOrgVo relOrgVo = new RelOrgVo();
        BeanUtils.copyProperties(relOrg, relOrgVo);
        relOrgVo.setRelStatement(rel.getRelStatement());
        relOrgVo.setPredicate(rel.getRelPredicate());
        relOrgVo.setEquityShare(rel.getEquityShare());
        relOrgVo.setRelCategory(rel.getRelCategory());
        relOrgVo.setRelCategorySub(rel.getRelCategorySub());
        relOrgVo.setIsValid(rel.getIsValid());
        relOrgVo.setActiveTimeString(rel.getActiveTimeString());
        relOrgVo.setInvalidTimeString(rel.getInvalidTimeString());
        relOrgVo.setShareholdingRatio(rel.getShareholdingRatio());
        relOrgVo.setPledgeRatio(rel.getPledgeRatio());
      /*  relPsnVo.setActiveTime(rel.getEffectiveDate());
        relPsnVo.setInvalidTime(rel.getIneffectiveDate());*/
        model.addAttribute("relOrg", relOrgVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("relId", relId);
        model.addAttribute("op", "1");
        model.addAttribute("fromPid", fromPid);

        return "bus/psnRelOrgAddOrView";
    }


    /**
     * 所属 机构关系信息列表
     *
     * @param orgid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnRelOrg/{orgid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "所属关系机构列表数据")
    public Map<String, Object> psnRelOrg(@PathVariable String orgid, Model model, Page<RelOrgVo> page) {
        Map<String, Object> result = new HashMap<String, Object>();
//        PageHelper.start(page);
        RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        List<RelOrgVo> tzOrgPsn = relOrgService.getTZOrgPsn(String.valueOf(orgid));
//        PageInfo pageInfo = new PageInfo(tzOrgPsn);
        page.setTotal(tzOrgPsn.size());
        int pages = (tzOrgPsn.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelOrgVo> relOrgVos = new ArrayList<RelOrgVo>();
//        List<RelPsnVo> relPsnVos = new ArrayList<RelPsnVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < tzOrgPsn.size()) {
                relOrgVos.add(tzOrgPsn.get(i));
            }
        }

        page.setResult(relOrgVos);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 下一级 机构关系信息列表
     *
     * @param orgid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnDownRelOrg/{orgid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "下级关系机构列表数据")
    public Map<String, Object> psnDownRelOrg(@PathVariable String orgid, Model model, Page<RelPsnVo> page) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.start(page);
        RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        List<RelOrgVo> tzOrgPsn = relOrgService.getDownTZOrgPsn(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(tzOrgPsn);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());

        return result;
    }

    /**
     * 编辑机构关系信息页面
     *
     * @param pid
     * @param model
     * @param fromPid
     * @param relId
     * @return
     */
    @RequestMapping(value = "/editPsnRelOrg/{pid}/{fromPid}/{relId}")
    @Log(module = "关联方管理", description = "编辑关系机构数据")
    public String editAll(@PathVariable Integer pid, Model model, @PathVariable Integer fromPid, @PathVariable Integer relId) {
        RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        RelService relService = SpringContextHolder.getBean(RelService.class);
        RelOrg relOrg = relOrgService.get(pid);
        RelOrgVo relOrgVo = new RelOrgVo();
        BeanUtils.copyProperties(relOrg, relOrgVo);
        Rel rel = relService.get(relId);
        if (rel.getShareholdingRatio() != null) {
            relOrgVo.setShareholdingRatio(rel.getShareholdingRatio());
        }
        relOrgVo.setRelCategory(rel.getRelCategory());
        if (pid.equals(rel.getFromPid())) {
            relOrgVo.setRelCategorySub(rel.getToName());
        } else {
            relOrgVo.setRelCategorySub(rel.getFromName());
        }
        model.addAttribute("relOrg", relOrgVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("fromPid", fromPid);
        model.addAttribute("relId", relId);
        model.addAttribute("paFromPid", pid);

        return "bus/psnRelOrgAddOrEdit";
    }

    /**
     * 金融產品保存关系机构页面
     *
     * @param relOrgVo
     * @return
     */
    @RequestMapping(value = "/ProsaveOrg/{paFromPid}/{fromPid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "金融產品保存关系机构数据")
    public Map<String, Object> ProsaveOrg(@PathVariable String paFromPid, @PathVariable String fromPid, RelOrgVo relOrgVo) {
        Map tips = new HashMap();

        //frompid  jingrong
        //归属关联方
        RelOrg fromOrg = relOrgService.get(Integer.valueOf(paFromPid));
        //新增关系to机构
        RelPro relPro = relProService.get(Integer.valueOf(fromPid));
        String fromAlig = fromOrg.getAligCode();
        String chAligCode = relPro.getAligCode();
        //新增关联方根据上级定为码以及关系判断是否关联方,更新to定为码
        if (StringUtils.isNotEmpty(fromAlig)) {
            //更新关系表
            Rel rel = new Rel();
            rel.setPid(relOrgVo.getRelId());
            rel.setDelflag("1");
            //归属关联方pid
            rel.setFromPid(paFromPid);
            //添加基本信息机构的pid
            rel.setToPid(fromPid);
            rel.setRelCategory(relOrgVo.getRelCategory());
            rel.setRelCategorySub(relOrgVo.getRelCategorySub());
            Rel dbRel = relService.get(rel);
            rel.setIsValid(relOrgVo.getIsValid());
            rel.setEffectiveDate(relOrgVo.getEffectiveDate());
            rel.setIneffectiveDate(relOrgVo.getIneffectiveDate());
            //如果查到两机构间有相同关系，更新原先关系
            boolean isExist = false;
            if (dbRel != null) {
                isExist = true;
                rel.setPid(dbRel.getPid());
            }
            String[] aligs = fromAlig.split(",");
            for (String alig : aligs) {
                List<Filter> aligFilters = new Filter.Builder()
                        .eq("paAligCode", alig)
                        .eq("relCategoryVal", relOrgVo.getRelCategory())
                        .eq("assoTypeVal", relOrgVo.getRelCategorySub())
                        .build();
                AligMap aligMap = aligMapService.get(aligFilters);
                //to定为码不为空则为关联方，需要更新定为码
                if (aligMap != null) {
                    String toAligCode = aligMap.getChAligCode();
                    //判断to定为码是否已存在,确保不重复加
                    if (StringUtils.isNotEmpty(chAligCode)) {
                        String[] chAligCodes = chAligCode.split(",");
                        List<String> codeList = Arrays.asList(chAligCodes);
                        if (!codeList.contains(toAligCode)) {
                            relPro.setAligCode(chAligCode + "," + toAligCode);
                        }
                    } else {
                        relPro.setAligCode(toAligCode);
                    }
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setToAligCode(toAligCode);
                    rel.setRegulator(aligMap.getCaliber());
                    rel.setDelflag("1");
                    rel.setFromType("ORG");
                    rel.setToType("PSN");
                    if (relOrgVo.getShareholdingProportion() != null) {
                        rel.setShareholdingRatio(Float.parseFloat(relOrgVo.getShareholdingProportion()));
                    }
                    //关联人设置口径
                    // relPro.setRegulatorOth1(aligMap.getCaliber());
                } else {
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setDelflag("1");
                    rel.setFromType("ORG");
                    rel.setToType("PSN");
                    if (relOrgVo.getShareholdingProportion() != null) {
                        rel.setShareholdingRatio(Float.parseFloat(relOrgVo.getShareholdingProportion()));
                    }
                }
                // relPro.setStatus("3");
                relProService.updateSelective(relPro);
                //关系状态为待新增
                rel.setRelStatus("01");
                if (isExist) {
                    relService.updateSelective(rel);
                } else {
                    relService.insert(rel);
                    //  relPsnService.insertNeo4jOrgToPsn(fromOrg,relPro,rel);
                }
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                break;
            }
        } else {
            //非关联方，返回不可添加提示
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "超过关联方关系判定范围");
        }
        return tips;
    }


    /**
     * 保存关系机构页面
     *
     * @param relOrgVo
     * @return
     */
    @RequestMapping(value = "/saveOrg/{paFromPid}/{fromPid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "保存关系机构数据")
    public Map<String, Object> saveOrg(@PathVariable String paFromPid, @PathVariable String fromPid, RelOrgVo relOrgVo) {
        Map tips = new HashMap();
        //归属关联方
        RelOrg fromOrg = relOrgService.get(Integer.valueOf(paFromPid));
        //新增关系to自然人
        RelPsn relPsn = relPsnService.get(Integer.valueOf(fromPid));
        //更新关系表
        Rel rel = new Rel();
        //归属关联方pid
        rel.setPid(relOrgVo.getRelId());
        rel.setFromPid(paFromPid);
        //添加基本信息机构的pid
        rel.setToPid(fromPid);
        Rel dbRel = relService.get(rel);
        rel.setRelCategory(relOrgVo.getRelCategory());
        rel.setRelCategorySub(relOrgVo.getRelCategorySub());
        rel.setRelPredicate(relOrgVo.getPredicate());//关联关系判定
        rel.setEquityShare(relOrgVo.getEquityShare());
        rel.setIsValid(relOrgVo.getIsValid());
        rel.setActiveTimeString(relOrgVo.getActiveTimeString());
        rel.setInvalidTimeString(relOrgVo.getInvalidTimeString());
        rel.setPledgeRatio(relOrgVo.getPledgeRatio());
        rel.setShareholdingRatio(relOrgVo.getShareholdingRatio());
        rel.setRelStatement(relOrgVo.getRelStatement());
        //如果查到两机构间有相同关系，更新原先关系
        boolean isExist = false;
        if (dbRel != null) {
            isExist = true;
            rel.setPid(dbRel.getPid());
        }
        rel.setDelflag("1");
        rel.setFromType("ORG");
        rel.setToType("PSN");
        if (relOrgVo.getShareholdingProportion() != null) {
            rel.setShareholdingRatio(Float.parseFloat(relOrgVo.getShareholdingProportion()));
        }
        new ConfirmUtil().relPsn(relPsn); //设置状态
        relPsnService.updateSelective(relPsn);
        //关系状态为待新增
        rel.setRelStatus("01");
        if (isExist) {
            relService.updateSelective(rel);
        } else {
            rel.setPid(null);
            relService.insert(rel);
        }
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }

    /**
     * @param flag
     * @return
     */
    @RequestMapping(value = "/exportHandler/{flag}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Log(module = "关联方管理", description = "查询报送列表数据")
    public void exportHandler(HttpServletRequest request, HttpServletResponse response, @PathVariable String flag) {
        Map<String, Object> tips = new HashMap<String, Object>();
        String filename = "";
        String filePath = "";
        Date date = new Date();
        if ("1".equals(flag)) {
            filename = "自然人导入模板" + "relPsn.xls";
            filePath = "D:/exportHandler/relPsn.xls";
        } else if ("2".equals(flag)) {
            filename = "机构导入模板" + "relOrg.xls";
            filePath = "D:/exportHandler/relOrg.xls";
        } else if ("3".equals(flag)) {
            filename = "疑似自然人导入模板" + "relPsnSuspect.xls";
            filePath = "D:/exportHandler/relPsnSuspect.xls";
        } else if ("4".equals(flag)) {
            filename = "疑似机构导入模板" + "relOrgSuspect.xls";
            filePath = "D:/exportHandler/relOrgSuspect.xls";
        }
        File file = new File(filePath);

        if (file.exists()) {
            OutputStream out = null;
            FileInputStream in = null;
            try {
                // 1.读取要下载的内容
                in = new FileInputStream(file);

                // 2. 告诉浏览器下载的方式以及一些设置
                // 解决文件名乱码问题，获取浏览器类型，转换对应文件名编码格式，IE要求文件名必须是utf-8, firefo要求是iso-8859-1编码
                String agent = request.getHeader("user-agent");
                if (agent.contains("FireFox")) {
                    filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
                } else {
                    filename = URLEncoder.encode(filename, "UTF-8");
                }
                // 设置下载文件的mineType，告诉浏览器下载文件类型
                String mineType = request.getServletContext().getMimeType(filename);
                response.setContentType(mineType);
                // 设置一个响应头，无论是否被浏览器解析，都下载
                response.setHeader("Content-disposition", "attachment; filename=" + filename);
                // 将要下载的文件内容通过输出流写到浏览器
                out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
//				tips.put("valid", "下载失败");
            } finally {

                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //反显，查询所属自然人信息
    @RequestMapping(value = "/selectRelPsn")
    @ResponseBody
    @Log(module = "关联方管理", description = "所属自然人反显")
    public List<RelPsn> selectRelPsn(String name, String identifyNumber) {
        List<RelPsn> relPsns = new ArrayList<RelPsn>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").eq("mgerOrg", unitCode).eq("status", "6").build();
        if (!StringUtils.isEmpty(name)) {
            filters.add(new Filter("name", name));
        }
        if (!StringUtils.isEmpty(identifyNumber)) {
            filters.add(new Filter("identifyNumber", identifyNumber));
        }
        relPsns = relPsnService.select(filters);


        return relPsns;
    }

    //反显，查询所属自然人信息
    @RequestMapping(value = "/selectIdentifyNumber")
    @ResponseBody
    @Log(module = "关联方管理", description = "所属自然人反显")
    public Boolean selectIdentifyNumber(String identifyNumber) {
        List<RelPsn> relPsns = new ArrayList<RelPsn>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").build();//.eq("mgerOrg", unitCode)
        if (!StringUtils.isEmpty(identifyNumber)) {
            filters.add(new Filter("identifyNumber", identifyNumber));
        }
        boolean exists = relPsnService.exists(filters);
        if (exists) {
            return true;
        } else {
            return false;
        }
    }

    //反显，查询金融产品信息
    @RequestMapping(value = "/selectRelProOfPsn")
    @ResponseBody
    @Log(module = "关联方管理", description = "金融产品反显")
    public List<RelPro> selectRelProOfPsn(String proName, String proProvider) {
        List<RelPro> relPros = new ArrayList<RelPro>();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").eq("approvalStatus", "6").build();
//        RelPro relPro = new RelPro();
        if (!StringUtils.isEmpty(proName)) {
            filters.add(new Filter("proName", proName));
//            relPsn.setName(name);
        }
        if (!StringUtils.isEmpty(proProvider)) {
            filters.add(new Filter("proProvider", proProvider));
        }
        relPros = relProService.select(filters);
        return relPros;
    }

    /*
     * 提交审批
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "提交审批")
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        RelPsn relPsn = relPsnService.get(pid);
        if ("3".equals(relPsn.getStatus()) || "5".equals(relPsn.getStatus())) {
            //4 代表审批中
            relPsn.setStatus("4");
            relPsnService.updateSelective(relPsn);
            tips.put("data", relPsn);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }

    /*
     * 任务下发taskSend
     * */
    @RequestMapping(value = "/relPsnSendCommand")
    @Log(module = "关联方管理", description = "任务下发")
    public String relPsnSendCommand(Model model) {

        return "bus/relPsnSend";
    }

    /*
     * 确认下发
     * */
    @RequestMapping(value = "/taskSend")
    @ResponseBody
    @Log(module = "关联方管理", description = "确认下发")
    public Map taskSend(String unitCode, String memo, String sendPsnId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //发放疑似自然人给指定机构补录
        List<Filter> filers = new Filter.Builder().eq("pid", sendPsnId).build();
        RelPsn relPsn = relPsnService.get(filers);
        //更换管库机构为下发机构
        relPsn.setMgerOrg(unitCode);
        //更新疑似自然人状态为待补录
        relPsn.setStatus("2");
        relPsn.setStatusComment(memo);
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
                    notifyService.createRemind(duty.getStaffCode(), "自然人[" + relPsn.getName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到自然人列表完成补录。", "自然人[" + relPsn.getName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到自然人列表完成补录。");
                }
                relPsnService.updateSelective(relPsn);
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
     * 单一查询页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/relSinQuery")
    @Log(module = "关联方管理", description = "单一查询表数据")
    public String relSinQuery(Model model) {
        //查询登陆人机构及下辖机构的信息
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        if ("0001".equals(unitCode) || "0001-ZZHK".equals(unitCode)) {
            //资产
            model.addAttribute("unitCode", unitCode);
            model.addAttribute("opt", "NoMH");
        } else {
            //管户机构
            model.addAttribute("unitCode", unitCode);
            model.addAttribute("opt", "MH");
        }
        return "bus/relSinQueList";
    }

    /**
     * 首页 关联方累计交易汇总
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/relAmtTotalQuery")
    @ResponseBody
    @Log(module = "首页", description = "关联方累计交易汇总")
    public List<Map<String, Object>> relSinQueryAmt(Page<PsnAndOrgVo> page, Model model, HttpServletRequest request) {
        /*
         * 1、查找交易对手表中的数据，根据清零标识为0、非重大标识、未删除的数据进行查找。
         * 2、对1的结果集循环调用清零后的累计金额计算方法；
         * 3、
         * */
        long s = System.currentTimeMillis();
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Map<String, Object>> result = null;
        BigDecimal baseAmtBD;//基数3000W
        BigDecimal baseDB;
        try {
            //获取当前的登录用户的信息
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            //获取主体id
            String userTenantId = userProfile.getTenantId();

            //重大关联交易基数
            String baseAmt = appConfigService.getConfigValue("major_base_amt");
            baseAmtBD = new BigDecimal(baseAmt).setScale(6, RoundingMode.HALF_UP);

            //获取上年末的净资产
            List<Filter> filters = new Filter.Builder().eq("quarter", 5).eq("delflag", "1").build();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            String lastYear = String.valueOf(cal.get(Calendar.YEAR));
            filters.add(new Filter("years", lastYear));
            List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filters);
            RootOrgInfo rootOrgInfo = rootOrgInfos.get(0);
            //净资产的1%
            BigDecimal netCaptial = rootOrgInfo.getNetCaptial().multiply(new BigDecimal("0.01")).setScale(6, RoundingMode.HALF_UP);
            //比较净资产的1%  与重大基数
            if (netCaptial.compareTo(baseAmtBD) < 0) {
                baseDB = baseAmtBD;
            } else {
                baseDB = netCaptial;
            }

            //获取累计清零后的金额结果集,前十
            int year = DateUtil.year(new Date());
            Map<String, Object> param = new HashMap<>();
            param.put("year", year);//当前年
            //param.put("traSubName", Commons.getTenantName());
            List<TraCounterparty> traCounterpartyList = baseService.sumAfterCleanAmount(param);

            if (null != traCounterpartyList) {
                int size = traCounterpartyList.size();
                if (size > 10) {
                    size = 10;
                }

                result = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    TraCounterparty traCounterparty = traCounterpartyList.get(i);
                    Map<String, Object> mapResult = new HashMap();
                    mapResult.put("amount", traCounterparty.getRelTradeAmount());
                    mapResult.put("percentAmount", baseDB);
                    mapResult.put("relname", traCounterparty.getRelName());
                    result.add(mapResult);
                }
            }

        } catch (Exception ex) {
            logger.error("失败");
        }
        System.out.println(System.currentTimeMillis() - s + "毫秒");
        return result;
    }


    @RequestMapping(value = "/relatedPartyTran")
    @ResponseBody
    @Log(module = "首页", description = "关联方累计交易汇总")
    public List<Map<String, Object>> relatedPartyTran(Page<PsnAndOrgVo> page, Model model, HttpServletRequest request) {
        /*
         * 查看本年度关联交易累计金额情况，累计金额需剔除统一交易协议与重大关联交易金额；重大额度根据基础数据填报计算重大额度，取三千万和上年度净资产两者较大值
         */


        List<Map<String, Object>> resultList = new ArrayList<>();
        //获取上年度资本金额
        String currentTime = DateUtils.formatCurrentDate();
        int year = DateUtil.parse(currentTime, DatePattern.NORM_DATE_PATTERN).year();
        //是否重大关联交易基数 3000W
        String baseAmt = appConfigService.getConfigValue("major_base_amt");
        String startTime =  year + "-01-01";
        String endTime = DateUtils.formatCurrentDate();
        List<Filter> filters = new Filter.Builder().eq("years", year -1 ).eq("quarter", 5).build();
        //上年度资本金额
        BigDecimal netCaptial = BigDecimal.ZERO;
        List<RootOrgInfo> orgInfoList = rootOrgInfoService.select(filters);
        if(CollUtil.isEmpty(orgInfoList)){
            logger.error("本年度基本数据没有维护，请到基础数据模块维护be年度基础数据！");
        }else {
            netCaptial = orgInfoList.get(0).getNetCaptial().divide(new BigDecimal("100"),4, RoundingMode.HALF_UP);
        }
        //获取三千万和上年度净资产两者较大值
        BigDecimal max = max(netCaptial,new BigDecimal(baseAmt));

        List<TraFunds> fundsList = traFundsService.getMaxRelatedTran(startTime,endTime);

        if(CollUtil.isNotEmpty(fundsList)){
            List<TraFunds> sortList = fundsList.stream().sorted(Comparator.comparing(TraFunds::getRelTradeAmount).reversed()).limit(10).collect(Collectors.toList());
            for (TraFunds traFund : sortList) {
                Map<String,Object> mapResult = new HashMap();
                mapResult.put("amount", traFund.getRelTradeAmount());
                mapResult.put("relName", traFund.getRelName());
                mapResult.put("percentAmount", max);
                resultList.add(mapResult);
            }
        }
        return resultList;
    }
    public BigDecimal max(BigDecimal a, BigDecimal b) {
        if (a.compareTo(b) > 0) {
            return a;
        }
        return b;
    }


    /**
     * 首页 业务类型限额汇总
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/investmentAmtQuery")
    @ResponseBody
    @Log(module = "首页", description = "资金运用类限额")
    public List<Map<String, Object>> InvestmentAmtQuery(Page<PsnAndOrgVo> page, Model model, HttpServletRequest request) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            Map map = new HashMap();
            BigDecimal multiply = BigDecimal.ZERO;
            //jsp中类型 001：股权 002：不动产 003：境外投资 004：金融产品
            map.put("investmentDesc", "00".concat(String.valueOf(i)));
            if (i != 3) {
                //业务类型 01：股权 02：不动产  04：金融产品
                map.put("investmentType", "0".concat(String.valueOf(i)));
            }
            FundsInvestmentVo vo = fundsInvestmentService.checkBalance();

            BigDecimal tradeAmountTotal = new BigDecimal(0);
            if (i == 1) {
                //multiply = vo.getUnlistedLimit();
                multiply = vo.getBalanceInvestmentLimit(); //限额
                 //tradeAmountTotal = vo.getUnlistedBalance();
                tradeAmountTotal = vo.getBalanceInvestment();
            } else if (i == 2) {
                multiply = vo.getRealEstateLimit();
                tradeAmountTotal = vo.getRealEstateBalance();
            } else if (i == 3) {
                map.put("isOverseasinvestment", "0");
                multiply = vo.getForeignLimit();
                tradeAmountTotal = vo.getForeignBalance();
            } else if (i == 4) {
                multiply = vo.getFinancialProducts();
               /* multiply = vo.getOtherFinanceLimit();*/
               if(ObjectUtil.isEmpty(vo.getInsuranceManagementBalance())){
                   vo.setInsuranceManagementBalance(BigDecimal.ZERO);
                }
                if(ObjectUtil.isEmpty(vo.getBankFinancialBalance())){
                    vo.setBankFinancialBalance(BigDecimal.ZERO);
                }
                if(ObjectUtil.isEmpty(vo.getOtherFinanceBalance())){
                    vo.setOtherFinanceBalance(BigDecimal.ZERO);
                }
                if(ObjectUtil.isEmpty(vo.getBankNegotiableSecuritiesBalance())){
                    vo.setBankNegotiableSecuritiesBalance(BigDecimal.ZERO);
                }
                if(ObjectUtil.isEmpty(vo.getTrustSchemeBalance())){
                    vo.setTrustSchemeBalance(BigDecimal.ZERO);
                }
                if(ObjectUtil.isEmpty(vo.getNegotiableSecuritiesSpecialPlanBalance())){
                    vo.setNegotiableSecuritiesSpecialPlanBalance(BigDecimal.ZERO);
                }
                if(ObjectUtil.isEmpty(vo.getInsuranceFundsSupportPlanBalance())){
                    vo.setInsuranceFundsSupportPlanBalance(BigDecimal.ZERO);
                }
                //tradeAmountTotal = vo.getOtherFinanceBalance();

                tradeAmountTotal = vo.getOtherInvestmentBalance().add(vo.getInsuranceManagementBalance()).add(vo.getBankFinancialBalance())
                        .add(vo.getBankNegotiableSecuritiesBalance()).add(vo.getTrustSchemeBalance()).add(vo.getNegotiableSecuritiesSpecialPlanBalance())
                        .add(vo.getInsuranceFundsSupportPlanBalance());
            }else if (i == 5) {
                Calendar date = Calendar.getInstance();
                String year = String.valueOf(date.get(Calendar.YEAR));
                List<Filter> filters = RequestUtils.buildFilters(request);
                filters.add(new Filter("years", Operator.EQ, year));
                filters.add(new Filter("quarter", Operator.EQ, 4));
                filters.add(new Filter("approvalStatus", Operator.EQ, "6"));
                List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filters);
                BigDecimal totalCapital;//总资产
                BigDecimal netCapital;//净资产
                totalCapital = rootOrgInfos.get(0).getTotalCapital();
                netCapital = rootOrgInfos.get(0).getNetCaptial();
                BigDecimal totalCapital1 = totalCapital.multiply(new BigDecimal("0.3"));
                int a = totalCapital1.compareTo(netCapital);
                if( a ==0 ){
                    multiply = totalCapital1;
                }else if(a== -1){
                    multiply = totalCapital1;
                } else if(a==1) {
                    multiply = netCapital;
                }
                //multiply = vo.getOtherFinanceLimit();
                tradeAmountTotal = vo.getAllInvestmentBalance();
            }
            map.put("percentAmount",multiply);   // 限额
            map.put("amount", tradeAmountTotal); // 累积金额
            resultList.add(map);
        }
        return resultList;
    }

    /**
     * 单一查询（自然人、机构）
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/relSinQuery1")
    @ResponseBody
    @Log(module = "关联方管理", description = "单一查询列表数据")
    public Map<String, Object> relSinQuery(Page<PsnAndOrgVo> page, Model model, HttpServletRequest request) {

        Map<String, Object> tips = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        PsnAndOrgVo psnAndOrgVo = new PsnAndOrgVo();

        for (Filter filter : filters) {
            if ("identifyNumber".equals(filter.getProperty())) {
                psnAndOrgVo.setIdentifyNumber(filter.getValue().toString());
            } else if ("name".equals(filter.getProperty())) {
                psnAndOrgVo.setName(filter.getValue().toString());
            } else if ("identifyTypePSN".equals(filter.getProperty())) {
                psnAndOrgVo.setIdentifyTypePSN(filter.getValue().toString());
            } else if ("identifyTypeORG".equals(filter.getProperty())) {
                psnAndOrgVo.setIdentifyTypeORG(filter.getValue().toString());
            } else if ("type".equals(filter.getProperty())) {
                psnAndOrgVo.setType(filter.getValue().toString());
            } else if ("traSubName".equals(filter.getProperty())) {
                psnAndOrgVo.setMgerOrg(filter.getValue().toString());
            }
        }

        //新建一个list集合，用以存储列表所要展示的内容
        List<PsnAndOrgVo> psnAndOrgVoList = new ArrayList<>();
        //类表初始化的时候，不展示数据

        if (psnAndOrgVo.getType().equals("psn")) {

            List<Filter> filtersPSN = new Filter.Builder().eq("status", "6").eq("delflag", "1").build();
            if (psnAndOrgVo.getIdentifyNumber() != null) {
                filtersPSN.add(new Filter("identifyNumber", Operator.EQ, psnAndOrgVo.getIdentifyNumber()));
            }
            if (psnAndOrgVo.getName() != null) {
                filtersPSN.add(new Filter("name", Operator.LIKE, psnAndOrgVo.getName()));
            }
            if (psnAndOrgVo.getMgerOrg() != null) {
                filtersPSN.add(new Filter("mgerOrg", Operator.EQ, psnAndOrgVo.getMgerOrg()));
            }
            if (psnAndOrgVo.getIdentifyTypePSN() != null) {
                filtersPSN.add(new Filter("identifyType", Operator.EQ, psnAndOrgVo.getIdentifyTypePSN()));
            }
            //查询自然人
            List<RelPsn> relPsns = relPsnService.select(filtersPSN);

            if (relPsns != null && relPsns.size() > 0) {
                for (RelPsn relPsn : relPsns) {
                    PsnAndOrgVo psnAndOrgVo1 = new PsnAndOrgVo();
                    psnAndOrgVo1.setPid(String.valueOf(relPsn.getPid()));
                    psnAndOrgVo1.setName(relPsn.getName());
                    psnAndOrgVo1.setDomesticOrForeign(relPsn.getDomesticOrForeign());
                    psnAndOrgVo1.setMgerOrg(relPsn.getMgerOrg());
                    psnAndOrgVo1.setIdentifyTypePSN(relPsn.getIdentifyType());
                    //隐藏关联人证件号
                    psnAndOrgVo1.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relPsn.getIdentifyNumber()));

                    psnAndOrgVo1.setCaliber(relPsn.getRegulatorOth1());
                    psnAndOrgVo1.setIsBlack(relPsn.getIsBlack());
                    psnAndOrgVoList.add(psnAndOrgVo1);
                }
            }

        } else if (psnAndOrgVo.getType().equals("org")) {

            List<Filter> filtersORG = new Filter.Builder().eq("delflag", "1").eq("approvalStatus", "6").build();
            if (psnAndOrgVo.getIdentifyNumber() != null) {
                filtersORG.add(new Filter("uniscCode", Operator.EQ, psnAndOrgVo.getIdentifyNumber()));
            }
            if (psnAndOrgVo.getName() != null) {
                filtersORG.add(new Filter("orgName", Operator.LIKE, psnAndOrgVo.getName()));
            }
            if (psnAndOrgVo.getIdentifyTypeORG() != null) {
                filtersORG.add(new Filter("identifyType", Operator.EQ, psnAndOrgVo.getIdentifyTypeORG()));
            }
            if (psnAndOrgVo.getMgerOrg() != null) {
                filtersORG.add(new Filter("mgerOrg", Operator.EQ, psnAndOrgVo.getMgerOrg()));
            }
            //查询关联组织
            List<RelOrg> relOrgs = relOrgService.select(filtersORG);

            if (relOrgs != null && relOrgs.size() > 0) {
                for (RelOrg relOrg : relOrgs) {
                    PsnAndOrgVo psnAndOrgVo1 = new PsnAndOrgVo();
                    psnAndOrgVo1.setPid(String.valueOf(relOrg.getPid()));
                    psnAndOrgVo1.setName(relOrg.getOrgName());
                    psnAndOrgVo1.setDomesticOrForeign(relOrg.getDomesticOrForeign());
                    psnAndOrgVo1.setMgerOrg(relOrg.getMgerOrg());
                    psnAndOrgVo1.setIdentifyTypeORG(relOrg.getIdentifyType());
                    psnAndOrgVo1.setIdentifyNumber(relOrg.getUniscCode());
                    psnAndOrgVo1.setCaliber(relOrg.getRegulatorOth1());
                    psnAndOrgVo1.setIsBlack(relOrg.getIsBlack());
                    psnAndOrgVoList.add(psnAndOrgVo1);
                }
            }

        }
        page.setTotal(psnAndOrgVoList.size());
        int pages = (psnAndOrgVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<PsnAndOrgVo> psnAndOrgVos = new ArrayList<PsnAndOrgVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < psnAndOrgVoList.size()) {
                psnAndOrgVos.add(psnAndOrgVoList.get(i));
            }
        }
        page.setResult(psnAndOrgVos);
        tips.put(RESULT_TOTAL, page.getTotal());
        tips.put(RESULT_ROWS, page.getResult());
        return tips;
    }

    /**
     * 批量查询页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/relBatQuery")
    @Log(module = "关联方管理", description = "批量查询列表数据")
    public String relBatQuery(Model model) {
//        System.out.println("11111");
        return "bus/relBatQueList";
    }


    /**
     * 批量查询（自然人，机构）
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/relBatQuery1")
    @ResponseBody
    @Log(module = "关联方管理", description = "批量查询列表数据")
    public Map<String, Object> relBatQuery1(Page<PsnAndOrgVo> page, Model model, HttpServletRequest request) {
        Map<String, Object> tips = new HashMap<>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        PsnAndOrgVo psnAndOrgVo = new PsnAndOrgVo();
        for (Filter f : filters) {
            if ("regulatorOth1".equals(f.getProperty())) {
                psnAndOrgVo.setRegulatorOth1(f.getValue().toString());
            } else if ("identifyNumber".equals(f.getProperty())) {
                psnAndOrgVo.setIdentifyNumber(f.getValue().toString());
            } else if ("relCategory".equals(f.getProperty())) {
                psnAndOrgVo.setRelCategory(f.getValue().toString());
            } else if ("relCategorySub".equals(f.getProperty())) {
                psnAndOrgVo.setRelCategorySub(f.getValue().toString());
            } else if ("isBlack".equals(f.getProperty())) {
                psnAndOrgVo.setIsBlack(f.getValue().toString());
            } else if ("memGroup".equals(f.getProperty())) {
                psnAndOrgVo.setMemGroup(f.getValue().toString());
            } else if ("groupName".equals(f.getProperty())) {
                psnAndOrgVo.setGroupName(f.getValue().toString());
            } else if ("stateOwned".equals(f.getProperty())) {
                psnAndOrgVo.setStateOwned(f.getValue().toString());
            }
        }

        //新建一个list集合，用以存储列表所要展示的内容
//        List<PsnAndOrgVo> psnAndOrgVoList = new ArrayList<>();

        //类表初始化的时候，不展示数据
        if (psnAndOrgVo.getRegulatorOth1() == null
                && psnAndOrgVo.getIdentifyNumber() == null
                && psnAndOrgVo.getMemGroup() == null
                && psnAndOrgVo.getGroupName() == null
                && psnAndOrgVo.getStateOwned() == null
                && psnAndOrgVo.getRelCategory() == null
                && psnAndOrgVo.getRelCategorySub() == null
                && psnAndOrgVo.getIsBlack() == null) {
            return tips;
        }
        if (psnAndOrgVo.getIdentifyNumber() != null) {
            // 自然人
            List<Filter> filterList = new Filter.Builder().eq("identifyNumber", psnAndOrgVo.getIdentifyNumber()).build();
            List<RelPsn> relPsns = relPsnService.select(filterList);
            if (!"null".equals(relPsns) || relPsns.size() > 0) {
                for (RelPsn relpsn : relPsns) {
                    psnAndOrgVo.setPid(String.valueOf(relpsn.getPid()));
                    psnAndOrgVo.setType("PSN");
                    PageHelper.start(page);
                    List<PsnAndOrgVo> psnAndOrgVos = relPsnService.selectBatQueMax(psnAndOrgVo);
                    PageInfo pageInfo = new PageInfo(psnAndOrgVos);
                    tips.put(RESULT_TOTAL, pageInfo.getTotal());
                    tips.put(RESULT_ROWS, pageInfo.getList());
                }
            } else {
                // 机构
                List<RelOrg> relOrgs = relOrgService.select(filterList);
                if (!"null".equals(relOrgs) || relOrgs.size() > 0) {
                    for (RelOrg relOrg : relOrgs) {
                        psnAndOrgVo.setPid(String.valueOf(relOrg.getPid()));
                        psnAndOrgVo.setType("ORG");
                        PageHelper.start(page);
                        List<PsnAndOrgVo> psnAndOrgVos = relPsnService.selectBatQueMax(psnAndOrgVo);
                        PageInfo pageInfo = new PageInfo(psnAndOrgVos);
                        tips.put(RESULT_TOTAL, pageInfo.getTotal());
                        tips.put(RESULT_ROWS, pageInfo.getList());
                    }
                }
            }

        } else {
            PageHelper.start(page);
            List<PsnAndOrgVo> psnAndOrgVos = relPsnService.selectBatQueMax(psnAndOrgVo);
            PageInfo pageInfo = new PageInfo(psnAndOrgVos);
            tips.put(RESULT_TOTAL, pageInfo.getTotal());
            tips.put(RESULT_ROWS, pageInfo.getList());
        }
        return tips;
    }


    /**
     * 根据父级关系查询子关系下拉选项
     *
     * @param relCategory
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryRelCategorySub")
    @ResponseBody
    @Log(module = "关联方管理", description = "父级关系和子级关系联动")
    public List<NameValuePair> getDictItems(String relCategory, String type) throws Exception {
        List<NameValuePair> items = new ArrayList<>();
        try {

            List<Filter> filters = new Filter.Builder().eq("dictCode", "BUS-Rel").build();
            List<DictItem> list = dictItemService.select(filters);
            String includes = "";
            //添加关联方为机构
            if ("p".equals(type)) {
                //亲属关系
                if ("1".equals(relCategory)) {
                    includes = "1,2,3,4,5";
                }//工作关系
                if ("2".equals(relCategory)) {
                    includes = "6,7,8,9,10,11,12";
                }//投资关系
                if ("3".equals(relCategory)) {
                    includes = "13,14,15,16,17,18,19,20,21,22,23,24";
                }//被投资关系
                if ("4".equals(relCategory)) {
                    includes = "25,26,27,28";
                }//	一致行动人/协议或依赖关系
                if ("5".equals(relCategory)) {
                    includes = "33";
                }
                if ("6".equals(relCategory)) {
                    includes = "34,35";
                }
            }//添加关联方为机构
            else if ("o".equals(type)) {
                //亲属关系
                if ("1".equals(relCategory)) {
                    includes = "1,2,3,4,5";
                }//工作关系
                if ("2".equals(relCategory)) {
                    includes = "6,7,8,9,10,11,12";
                }//投资关系
                if ("3".equals(relCategory)) {
                    includes = "13,14,15,16";
                }//被投资关系
                if ("4".equals(relCategory)) {
                    includes = "25,26,27,28";
                }//	一致行动人/协议或依赖关系
                if ("5".equals(relCategory)) {
                    includes = "33";
                }
                if ("6".equals(relCategory)) {
                    includes = "34,35";
                }
            }//添加关联方为产品
            else if ("r".equals(type)) {
                //亲属关系
                if ("1".equals(relCategory)) {
                    includes = "1,2,3,4,5";
                }//工作关系
                if ("2".equals(relCategory)) {
                    includes = "6,7,8,9,10,11,12";
                }//投资关系
                if ("3".equals(relCategory)) {
                    includes = "13,14,15,16,17,18,19,20,21,22,23,24";
                }//被投资关系
                if ("4".equals(relCategory)) {
                    includes = "25,26,27,28";
                }//	一致行动人/协议或依赖关系
                if ("5".equals(relCategory)) {
                    includes = "33";
                }
                if ("6".equals(relCategory)) {
                    includes = "34,35";
                }

            } else if ("s".equals(type)) {
                if ("4".equals(relCategory)) {
                    includes = "29,30,31,32";
                }
            }
            String[] includesArr = includes.split(",");
            List<String> includesList = Arrays.asList(includesArr);
            //过滤掉不要的字典项
            if (!org.springframework.util.CollectionUtils.isEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    DictItem item = list.get(i);
                    if (org.springframework.util.StringUtils.hasText(includes) && includesList.contains(item.getItemValue())) {
                        BasicNameValuePair nv = new BasicNameValuePair(item.getItemLabel(), item.getItemValue());
                        items.add(nv);
                    }
                }
            }

            return items;
        } catch (ServiceException se) {
            logger.error("系统出现异常：" + se.getMessage());
            throw se;
        }
    }



    /**
     * 关联方统计
     * 首页
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/psnAndOrgTotal")
    @ResponseBody
    public List<Map> psnAndOrgTotal(HttpServletRequest request) {
        List<Filter> filters = new Filter.Builder().eq("status", "6").eq("effectRelation","1").build();
        List<RelPsn> relPsn = relPsnService.select(filters);
        filters.clear();
        filters = new Filter.Builder().eq("approvalStatus", "6").eq("effectRelation","1").build();
        List<RelOrg> relOrg = relOrgService.select(filters);
        List<Map> resultList = new ArrayList<>();
        Map dMap = new HashMap();
        Map yMap = new HashMap();

        dMap.put("value", relPsn.size());
        dMap.put("name", "自然人");
        dMap.put("url","workflow/approve/todoList");
        resultList.add(dMap);
        yMap.put("value", relOrg.size());
        yMap.put("name", "法人或其他组织关联方");
        yMap.put("url","workflow/approve/doneList");
        resultList.add(yMap);
        return resultList;
    }

    /**
     * 统计分析-关联交易累计金额达到 （重大基准或者上年度末净资产的1%的最大值）的60%的数据
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "/relTotalQuery")
    @ResponseBody
    public Map<String, Object> relTotalQuery(Page<PsnAndOrgVo> page, HttpServletRequest request) {

        long s = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        // 基数3000W
        BigDecimal baseAmtBD;
        BigDecimal baseDb;
        BigDecimal rat;

        // 重大关联交易基数
        String baseAmt = appConfigService.getConfigValue("major_base_amt");
        baseAmtBD = new BigDecimal(baseAmt).setScale(6, RoundingMode.HALF_UP);

        // 获取上年末的净资产
        List<Filter> filters = new Filter.Builder().eq("quarter", 5).eq("delflag", "1").build();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        String lastYear = String.valueOf(cal.get(Calendar.YEAR));
        filters.add(new Filter("years", lastYear));
        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filters);
        RootOrgInfo rootOrgInfo = rootOrgInfos.get(0);
        // 净资产的1%
        BigDecimal netCaptial = rootOrgInfo.getNetCaptial().multiply(new BigDecimal("0.01")).setScale(4, RoundingMode.HALF_UP);
        // 比较净资产的1%  与重大基数
        if (netCaptial.compareTo(baseAmtBD) < 0) {
            baseDb = baseAmtBD;
        } else {
            baseDb = netCaptial;
        }
        // 获取实际比较数据的60%来作比较
        rat = baseDb.multiply(new BigDecimal("0.6")).setScale(4, RoundingMode.HALF_UP);

        // 获取累计未清零后的金额结果集
        int year = DateUtil.year(new Date());
        String startTime = year + "-01-01";
        String endTime = DateUtils.formatCurrentDate();
        // 新建一个list集合，用以存储列表所要展示的内容
        List<PsnAndOrgVo> psnAndOrgVos = new ArrayList<>();
        List<TraFunds> fundsList = traFundsService.getMaxRelatedTran(startTime, endTime);
        fundsList.stream().filter(traFunds -> traFunds.getRelTradeAmount().compareTo(rat) > 0).forEach(traFunds -> {
            String relType = traFunds.getRelType();
            String relNumber = traFunds.getRelNumber();
            BigDecimal tradeAmount = traFunds.getRelTradeAmount();
            PsnAndOrgVo psnAndOrgVo = new PsnAndOrgVo();
            // 关联方名称
            psnAndOrgVo.setName(traFunds.getRelName());
            // 关联方类别
            psnAndOrgVo.setType(relType);
            // 关联方证件号码
            psnAndOrgVo.setIdentifyNumber(relNumber);
            // 累计金额
            psnAndOrgVo.setTotalTradeAmount(tradeAmount);
            if (StringUtils.isNotEmpty(relType) && StringUtils.isNotEmpty(relNumber)) {
                String identifyType = relPsnService.getIdentifyType(relType, relNumber);
                // 关联方证件类型
                psnAndOrgVo.setIdentifyType(identifyType);
            }

            Double num1 = Double.valueOf(String.valueOf(tradeAmount));
            Double num2 = Double.valueOf(String.valueOf(baseDb));
            double res = num1 / num2;
            BigDecimal rate = new BigDecimal(String.valueOf(res)).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
            String strRate = String.valueOf(rate);
            psnAndOrgVo.setRattotalTradeAmount(strRate + "%");
            psnAndOrgVos.add(psnAndOrgVo);

        });

        if (!psnAndOrgVos.isEmpty()) {
            PageHelper.start(page);
            PageInfo pageInfo = new PageInfo(psnAndOrgVos);
            map.put(RESULT_TOTAL, pageInfo.getTotal());
            map.put(RESULT_ROWS, pageInfo.getList());
            return map;
        }

        System.out.println(System.currentTimeMillis() - s + "毫秒");
        return map;
    }

    @RequestMapping(value = "/recordNumber")
    @ResponseBody
    @Transactional
    public Map<String, Object> recordNumber() {
        Map<String, Object> tips = new HashMap<>();
        try {
            relPsnService.isAddRecordNumber(tips);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (RuntimeException e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, e.getMessage());
        }
        return tips;
    }
}
