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
import com.yzj.jep.app.sub.model.EastGLGX;
import com.yzj.jep.app.sub.model.EastGLGXView;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.EastGLGXService;
import com.yzj.jep.app.sub.service.EastGLGXViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sub.service.TemplateDetailsService;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
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
@RequestMapping("/east/glgx")
public class EastGLGXController extends CrudController<EastGLGX> {
    private static Logger logger = LoggerFactory.getLogger(EastGLGXController.class);

    @Autowired
    private EastGLGXService eastGlgxService;
    @Autowired
    private EastGLGXViewService eastGlgxViewService;
    @Autowired
    private SublogService sublogService;
    @Autowired
    private TemplateDetailsService templateDetailsService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sub/eastGLGXList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<EastGLGX> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        eastGlgxService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sub/eastGLGXAdd";
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

        EastGLGX eastGlgxb = eastGlgxService.get(id);
        model.addAttribute("eastGlgxb", eastGlgxb);
        model.addAttribute("opt", "edit");

        return "sub/eastGLGXEdit";
    }

    /**
     * 查看页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        Sublog sublog = sublogService.get(id);
        model.addAttribute("dateTime", DateUtils.formatDateTime(sublog.getCreateTime()));
        model.addAttribute("pid", sublog.getPid());
        return "sub/eastGLGXView";
    }

    @RequestMapping(value = "/viewDetail")
    @ResponseBody
    public Map<String, Object> viewDetail(Integer pid, Page<EastGLGXView> page) {
        Map<String, Object> result = new HashMap<>();
        Sublog sublog = sublogService.get(pid);
        if (sublog != null) {
            List<Filter> filters = new ArrayList<>();
            filters.add(Filter.eq("pch", sublog.getBatchDecimal()));
            Page<EastGLGXView> query = eastGlgxViewService.query(page, filters);
            result.put(RESULT_TOTAL, query.getTotal());
            result.put(RESULT_ROWS, query.getResult());
        }
        return result;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/remove/{id}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer id) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            Sublog sublog=sublogService.get(id);
            List<Filter> filters=new ArrayList<>();
            filters.add(Filter.eq("pch",sublog.getBatchDecimal()));
            //删除sublog中的数据
            sublogService.deleteByPrimaryKey(id);
            eastGlgxViewService.deleteByFilter(filters);
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
        try {
            /**
             * 调用模板类将数据备份到对应的view表与sublog表中，返回一个boolean值用来判断是否成功
             */
            List<Filter> filters = new ArrayList<>();
            //boolean flag = templateDetailsService.addEastView(filters,subType,"","");
            boolean flag=false;
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
    public Map<String, Object> querySubFile(Page<EastGLGX> page, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        filters.add(Filter.eq("delflag", "1"));//表示没删除的数据
        filters.add(Filter.eq("eastStatus", 1));
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("modifiedTime", OrderType.DESC));
        PageHelper.start(page);
        List<EastGLGX> relPars = eastGlgxService.select(filters);
        PageHelper.end(page, relPars);
        map.put(RESULT_TOTAL, page.getTotal());
        map.put(RESULT_ROWS, page.getResult());
        return map;
    }
}