/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月13日 15:41:26
 *
 */

package com.yzj.jep.app.sub.web.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.sub.model.EastGljyhzbView;
import com.yzj.jep.app.sub.model.EastZDGLJYMX;
import com.yzj.jep.app.sub.model.EastZDGLJYMXView;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.EastZDGLJYMXService;
import com.yzj.jep.app.sub.service.EastZDGLJYMXViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sub.service.TemplateDetailsService;
import com.yzj.jep.app.sub.service.impl.EastExportCallBackImpl;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/east/zdgljymx")
public class EastZDGLJYMXController extends CrudController<EastZDGLJYMX> {
    private static Logger logger = LoggerFactory.getLogger(EastZDGLJYMXController.class);
    @Autowired
    private EastZDGLJYMXService eastZDGLJYMXService;
    @Autowired
    private TemplateDetailsService templateDetailsService;
    @Autowired
    private SublogService sublogService;
    @Autowired
    private EastZDGLJYMXViewService eastZDGLJYMXViewService;
    @Autowired
    private EastExportCallBackImpl eastExportCallBack;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "east/zdgljymx/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "sub/eastZDGLJYMXList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<EastZDGLJYMX> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        eastZDGLJYMXService.query(page, filters, orders);

    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sub/eastZDGLJYMXAdd";
    }

    /**
     * 编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        return "";
    }

    /**
     * 查看页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public String view(@PathVariable String id, Model model) {
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("batchDecimal", Operator.EQ,id));
        Sublog subLogs = sublogService.get(filters);
        String pattern = "yyyy年MM月dd日";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String time ="";
        if(null != subLogs.getSubmissionDate()){
            time = simpleDateFormat.format(subLogs.getSubmissionDate());
        }
        model.addAttribute("time",time);
        model.addAttribute("id",id);
        return "sub/eastZDGLJYMXView";
    }

    @RequestMapping(value = "/viewDetail")
    @ResponseBody
    public Map<String, Object> viewDetail(HttpServletRequest request,String id, Page<EastZDGLJYMXView> page) {
        Map<String, Object> result = Maps.newHashMap();
        List<Filter> filters = RequestUtils.buildFilters(request);
        filters.add(Filter.eq("pch", id));
        Page<EastZDGLJYMXView> query = eastZDGLJYMXViewService.query(page, filters);
        result.put(RESULT_TOTAL, query.getTotal());
        result.put(RESULT_ROWS, query.getResult());
        return result;
    }

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
            //删除对应的view表中的数据
            List<EastZDGLJYMXView> select = eastZDGLJYMXViewService.select(filters);
            for (EastZDGLJYMXView eastZDGLJYMXView : select) {
                EastZDGLJYMX zdgljymx = eastZDGLJYMXService.get(eastZDGLJYMXView.getRelid());
                zdgljymx.setEastStatus(1);
                zdgljymx.setDelflag("1");
                eastZDGLJYMXService.updateSelective(zdgljymx);
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
            templateDetailsService.creatExcel("EastExportCallBackImpl",eastExportCallBack.getExportFilename(map),map);
        } catch (Exception e) {
            logger.info("生成报送文件失败");
            tips.put(WebCode.SUCCESS,false);
            tips.put(WebCode.MSG,WebCode.FAILURE_FAILED_FILE);
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
                logger.info("暂无报送数据生成");
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.FAILURE_NODATA_FILE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_FAILED_FILE);
        }
        return tips;
    }

    /**
     * 查询所有满足条件的待报送文件
     */
    @RequestMapping(value = "/querySubFile")
    @ResponseBody
    public Map<String, Object> querySubFile(Page<EastZDGLJYMX> page, HttpServletRequest request) {
        Map<String, Object> map = Maps.newHashMap();
        List<Filter> filters = RequestUtils.buildFilters(request);
        filters.add(Filter.eq("delflag", "1"));
        filters.add(Filter.eq("eastStatus", 1));
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("modifiedTime", OrderType.DESC));
        PageHelper.start(page);
        List<EastZDGLJYMX> relPars = eastZDGLJYMXService.select(filters, orders);
        PageInfo<EastZDGLJYMX> pageInfo = new PageInfo<>(relPars);
        map.put(RESULT_TOTAL, pageInfo.getTotal());
        map.put(RESULT_ROWS, pageInfo.getList());
        return map;
    }
}