/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月13日 15:41:26
 *
 */

package com.yzj.jep.app.sub.web.controller;

import com.google.common.collect.Maps;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.model.UnSortOut;
import com.yzj.jep.app.pub.model.PubYearVo;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.app.sub.service.impl.EastExportCallBackImpl;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.JdbcUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.dash.utils.WidgetUtils;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.drools.core.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * EAST报送关联方信息表操作类
 *
 * @author apple
 */
@Controller
@RequestMapping("/east/glfxx")
public class EastGLFXXController extends CrudController<EastGLFXX> {
    private static Logger logger = LoggerFactory.getLogger(EastGLFXXController.class);
    @Autowired
    private EastGLFXXService eastGlfxxService;
    @Autowired
    private TemplateDetailsService templateDetailsService;
    @Autowired
    private EastExportCallBackImpl eastExportCallBack;

    @Autowired
    private EastGLFXXViewService eastGLFXXViewService;
    @Autowired
    private SublogService sublogService;

    @Autowired
    private EastGLGXService eastGlgxService;

    /**
     * 获取列表页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model) {
        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "east/glfxx/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "sub/eastGLFXXList";

    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sub/eastGLFXXAdd";
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public String view(@PathVariable String id, Model model) {
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("batchDecimal", Operator.EQ, id));
        Sublog subLogs = sublogService.get(filters);
        String pattern = "yyyy年MM月dd日";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String time = "";
        if (null != subLogs.getSubmissionDate()) {
            time = simpleDateFormat.format(subLogs.getSubmissionDate());
        }
        model.addAttribute("time", time);
        model.addAttribute("id", id);
        return "sub/eastGLFXXView";
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/viewDtail/list")
    @ResponseBody
    public Map<String, Object> viewDtail(Page<EastGLFXXView> page, HttpServletRequest request, String id) {
        List<Filter> filters = RequestUtils.buildFilters(request);
        Map<String, Object> map = new HashMap<>();

        filters.add(new Filter("pch", Operator.EQ, id));
        Page<EastGLFXXView> query = eastGLFXXViewService.query(page, filters);
        map.put(RESULT_TOTAL, query.getTotal());
        map.put(RESULT_ROWS, query.getResult());
        return map;
    }

    /**
     * 关联方信息 生成报送文件
     */
    @RequestMapping(value = "/createSubRelParFile/{subType}")
    @ResponseBody
    @Log(module = "east报送", description = "关联方信息生成报送文件")
    public Map<String, Object> createSubRelParFile(@PathVariable String subType) {
        //用来存储返回结果
        Map<String, Object> tips = Maps.newHashMap();
        //用来存储获取文件名的条件
        Map<String, Object> map = Maps.newHashMap();
        map.put("subType", subType);
        try {
            templateDetailsService.creatExcel("EastExportCallBackImpl", eastExportCallBack.getExportFilename(map), map);
        } catch (Exception e) {
            logger.info("生成报送文件失败");
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_FAILED_FILE);
            e.printStackTrace();
            return tips;
        }
        try {
            map.put("subType", subType);
            List<Filter> filters = new ArrayList<>();
            filters.add(Filter.eq("eastStatus", 1));
            filters.add(Filter.eq("delflag", "1"));
            /**
             * 调用模板类将数据备份到对应的view表与sublog表中，返回一个boolean值用来判断是否成功
             */
            boolean flag = templateDetailsService.addEastView(filters, eastExportCallBack.getExportFilename(map), subType);
            if (flag) {
                logger.info("报送文件数据已生成");
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.FAILURE_SUCCESS_FILE);
            } else {
                logger.info("暂无报送数据生成或系统异常");
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.FAILURE_NODATA_FILE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            tips.put(WebCode.FAILED, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_FAILED_FILE);
        }
        return tips;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/remove/{id}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer id) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            Sublog sublog = sublogService.get(id);
            List<Filter> filters = new ArrayList<>();
            filters.add(Filter.eq("pch", sublog.getBatchDecimal()));
            //删除sublog中的数据
            sublogService.deleteByPrimaryKey(id);
            List<EastGLFXXView> select = eastGLFXXViewService.select(filters);
            for (EastGLFXXView eastGLFXXView : select) {
                EastGLFXX glfxx = eastGlfxxService.get(eastGLFXXView.getRelid());
                glfxx.setEastStatus(1);
                glfxx.setDelflag("1");
                eastGlfxxService.updateSelective(glfxx);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }
    /**
     * 查询所有满足条件的待报送文件
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryFile")
    @ResponseBody
    public Map<String, Object> queryFile(Page<EastGLFXX> page, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Filter> filters = RequestUtils.buildFilters(request);
            //表示没删除的数据
            filters.add(Filter.eq("delflag", "1"));
            filters.add(Filter.eq("eastStatus", 1));
            List<Order> orders = RequestUtils.buildOrders(request);
            orders.add(new Order("modifiedTime", OrderType.DESC));
            PageHelper.start(page);
            List<EastGLFXX> relPars = eastGlfxxService.select(filters);
            PageHelper.end(page, relPars);
            map.put(RESULT_TOTAL, page.getTotal());
            map.put(RESULT_ROWS, page.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}