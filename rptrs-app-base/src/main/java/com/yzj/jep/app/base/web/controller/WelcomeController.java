/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月12日 11:10:44
 *
 */

package com.yzj.jep.app.base.web.controller;

import com.yzj.jep.app.base.constant.IsStatusDict;
import com.yzj.jep.app.bus.model.DocFile;
import com.yzj.jep.app.bus.service.DocFileService;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.dto.StatisticsDto;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Role;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.wf.service.HisProcInstService;
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
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/base/welcome")
public class WelcomeController extends CrudController<TraCramcInfo> {
    private static Logger logger = LoggerFactory.getLogger(WelcomeController.class);

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
    private DutyService dutyService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private HisProcInstService hisProcInstService;
    @Autowired
    private DocFileService docFileService;


    /**
     * 首页的通知栏信息
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/queryNotices")
    @ResponseBody
    public List queryNotices(Model model) {
        List list = new ArrayList<>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String roleName = combineRoleName(profile.getStaffCode());
        List<Filter> filters = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("createTime", OrderType.DESC));
        List<DocFile> docFileList = docFileService.getLimitTwo();

        for (DocFile docFile : docFileList) {
            Map map = new HashMap();
            map.put("id", docFile.getPid());
            map.put("fileName", docFile.getFileName());
            //1:内部文件 2:外部文件
            if (1 == docFile.getUploadType()) {
                map.put("OuterOrInner", "docForInner");
                map.put("OOIType", 1);
            } else {
                map.put("OuterOrInner", "docForOutside");
                map.put("OOIType", 2);
            }
            map.put("createTime", new SimpleDateFormat("yyyy-MM-dd").format(docFile.getCreateTime()));
            list.add(map);
        }

        List list1 = new ArrayList<>();
        if (list.size() > 6) {
            for (int i = 0; i < 6; i++) {
                list1.add(list.get(i));
            }
        } else {
            list1.addAll(list);
        }
        return list1;
    }


    /***
     *
     * 首页待查阅交易查询
     *
     */
    @RequestMapping(value = "/Supplement1")
    @ResponseBody
    public List Supplement1(Model model) {
        List list = new ArrayList<>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String roleName = combineRoleName(profile.getStaffCode());
        List<Filter> filters = new ArrayList<>();
        if ((!"0001".equals(profile.getUnitCode()) && !"0001-ZZHK".equals(profile.getUnitCode()))) {
            //委托机构查待查阅
            filters = new Filter.Builder().eq("status", "9").build();
        }

        filters.add(new Filter("modifiedTime", Operator.NNL, null));
        filters.add(new Filter("traSubName", Operator.EQ, profile.getUnitCode()));
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        List<TraInvestment> trainvestments = traInvestmentService.select(filters);
        List<TraFunds> traFunds = traFundsService.select(filters);
        List<TraBusiness> traBusinesses = traBusinessService.select(filters);
        List<TraInterest> traInterests = traInterestService.select(filters);
        List<TraService> traServices = traServiceService.select(filters);
        List<TraOther> traOthers = traOtherService.select(filters);
        for (TraInvestment tra : trainvestments) {
            Map map = new HashMap();
            map.put("id", tra.getPid());
            map.put("modifiedTime", new SimpleDateFormat("yyyy-MM-dd").format(tra.getModifiedTime()));
            map.put("tranType", "traInvestment");
            map.put("tranTypeC", IdToMgerOrg(tra.getTraSubName()) + "与" + tra.getRelName() + "发生的投资入股类交易");
            list.add(map);
        }
        for (TraFunds tra : traFunds) {
            Map map = new HashMap();
            map.put("id", tra.getPid());
            map.put("modifiedTime", new SimpleDateFormat("yyyy-MM-dd").format(tra.getModifiedTime()));
            map.put("tranType", "traFunds");
            map.put("tranTypeC", IdToMgerOrg(tra.getTraSubName()) + "与" + tra.getRelName() + "发生的资金运用类交易");
            list.add(map);
        }
        for (TraBusiness tra : traBusinesses) {
            Map map = new HashMap();
            map.put("id", tra.getPid());
            map.put("modifiedTime", new SimpleDateFormat("yyyy-MM-dd").format(tra.getModifiedTime()));
            map.put("tranType", "traBusiness");
            map.put("tranTypeC", IdToMgerOrg(tra.getTraSubName()) + "与" + tra.getRelName() + "发生的保险业务类交易");
            list.add(map);
        }
        for (TraInterest tra : traInterests) {
            Map map = new HashMap();
            map.put("id", tra.getPid());
            map.put("modifiedTime", new SimpleDateFormat("yyyy-MM-dd").format(tra.getModifiedTime()));
            map.put("tranType", "traInterest");
            map.put("tranTypeC", IdToMgerOrg(tra.getTraSubName()) + "与" + tra.getRelName() + "发生的利益转移类交易");
            list.add(map);
        }
        for (TraService tra : traServices) {
            Map map = new HashMap();
            map.put("id", tra.getPid());
            map.put("modifiedTime", new SimpleDateFormat("yyyy-MM-dd").format(tra.getModifiedTime()));
            map.put("tranType", "traService");
            map.put("tranTypeC", IdToMgerOrg(tra.getTraSubName()) + "与" + tra.getRelName() + "发生的提供服务类交易");
            list.add(map);
        }
        for (TraOther tra : traOthers) {
            Map map = new HashMap();
            map.put("id", tra.getPid());
            map.put("modifiedTime", new SimpleDateFormat("yyyy-MM-dd").format(tra.getModifiedTime()));
            map.put("tranType", "traOther");
            map.put("tranTypeC", IdToMgerOrg(tra.getTraSubName()) + "与" + tra.getRelName() + "发生的其他类交易");
            list.add(map);
        }
        //排序 倒序
//		if (list != null && list .size() > 1) {
//			Collections.sort(list , new Comparator<Map<String, Object>>() {
//				@Override
//				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
//					Integer o1Value = Integer.valueOf(o1.get("modifiedTime").toString());
//					Integer o2Value = Integer.valueOf(o2.get("modifiedTime").toString());
//					return o2Value.compareTo(o1Value);
//				}
//			});
//		}
        List list1 = new ArrayList<>();
        if (list.size() > 6) {
            for (int i = 0; i < 6; i++) {
                list1.add(list.get(i));
            }
        } else {
            list1.addAll(list);
        }
        return list1;
    }

    /***
     *
     *
     * 首页审批统计
     *
     */
    @RequestMapping(value = "/approval/statistics")
    @ResponseBody
    public Map approvalStatistics(Model model) {
        List<String> subjectList = new ArrayList<>();
        List<Integer> adoptList = new ArrayList<>();
        List<Integer> allList = new ArrayList<>();
        Map map = new HashMap();
        try {
            List<StatisticsDto> total = hisProcInstService.selectAllHisProcInst();
            List<StatisticsDto> adoptTotal = hisProcInstService.selectHisProcInstToatal();
            //分公司总公司集合
            subjectList = total.stream().map(StatisticsDto -> {
                return StatisticsDto.getName();
            }).collect(Collectors.toList());
            //已审批总数
            adoptList = adoptTotal.stream().map(StatisticsDto -> {
                return StatisticsDto.getTotal();
            }).collect(Collectors.toList());
            //全部审批数据
            allList = total.stream().map(StatisticsDto -> {
                return StatisticsDto.getTotal();
            }).collect(Collectors.toList());
            map.put("subjectList", subjectList);
            map.put("adoptList", adoptList);
            map.put("allList", allList);
        } catch (Exception e) {
            logger.error("系统出现异常：" + e.getMessage());
            throw e;
        }
        return map;
    }


    /***
     *
     *
     * 首页关联交易统计
     *
     */
    @RequestMapping(value = "/statistics")
    @ResponseBody
    public List Statistics(Model model) {
        List list = new ArrayList<>();
        try {
            BigDecimal trainvestments = traInvestmentService.getAllSumTradeAmount();
            BigDecimal traFunds = traFundsService.getAllSumTradeAmount();
            BigDecimal traBusinesses = traBusinessService.getAllSumTradeAmount();
            BigDecimal traInterests = traInterestService.getAllSumTradeAmount();
            BigDecimal traServices = traServiceService.getAllSumTradeAmount();
            BigDecimal traOthers = traOtherService.getAllSumTradeAmount();
            list.add(traOthers);
            list.add(traServices);
            list.add(traInterests);
            list.add(traBusinesses);
            list.add(traFunds);
            list.add(trainvestments);
        } catch (Exception e) {
            logger.error("系统出现异常：" + e.getMessage());
        }
        return list;
    }

    public String IdToMgerOrg(String UscCode) {
        List<NameValuePair> items = new ArrayList<>();
        Map tips = new HashMap<String, String>();
        try {
            List<Filter> filters = new Filter.Builder().eq("dictCode", "INS-subjectName").build();
            List<DictItem> list = dictItemService.select(filters);

            //过滤掉不要的字典项
            if (!org.springframework.util.CollectionUtils.isEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    DictItem item = list.get(i);
                    if (org.springframework.util.StringUtils.hasText("") && "".contains(item.getItemValue())) {
                        continue;
                    }
                    BasicNameValuePair nv = new BasicNameValuePair(item.getItemLabel(), item.getItemValue());
                    items.add(nv);
                }
            }
            for (NameValuePair nameValuePair : items) {
                tips.put(nameValuePair.getValue(), nameValuePair.getName());
            }
            return (String) tips.get(UscCode);
        } catch (ServiceException se) {
            logger.error("系统出现异常：" + se.getMessage());
            throw se;
        }
    }


    /**
     * 组装该用户所拥有的角色名称
     *
     * @param staffCode 当前用户编码
     * @return 角色名称组
     */
    protected String combineRoleName(String staffCode) {
        String roleName = "";

        List<Role> rolelist = dutyService.getStaffRoleList(staffCode, null);
        if (CollectionUtils.isNotEmpty(rolelist)) {
            for (Role role : rolelist) {
                roleName += role.getRoleName() + ", ";
            }
            roleName = roleName.substring(0, roleName.length() - 2);
        }

        return roleName;
    }


}