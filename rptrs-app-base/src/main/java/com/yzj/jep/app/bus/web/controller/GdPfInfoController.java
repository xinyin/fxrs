/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月19日 14:20:05
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.GdPfInfoVo;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.GdPfInfo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.GdPfInfoService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/gdPfInfo")
public class GdPfInfoController extends CrudController<GdPfInfo> {
    private static Logger logger = LoggerFactory.getLogger(GdPfInfoController.class);

    @Autowired
    private GdPfInfoService gdPfInfoService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/gdPfInfoList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<GdPfInfo> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        gdPfInfoService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/gdPfInfoEdit";
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

        GdPfInfo gdPfInfo = gdPfInfoService.get(pid);
        model.addAttribute("gdPfInfo", gdPfInfo);
        model.addAttribute("opt", "edit");

        return "bus/gdPfInfoEdit";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model) {

        GdPfInfo gdPfInfo = gdPfInfoService.get(pid);
        model.addAttribute("gdPfInfo", gdPfInfo);

        return "bus/gdPfInfoView";
    }

    /**
     * 保存
     *
     * @param gdPfInfo
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(GdPfInfo gdPfInfo) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = gdPfInfoService.exists(gdPfInfo.getPid());
        try {
            if (isExist) {

                gdPfInfoService.update(gdPfInfo);
            } else {
                gdPfInfoService.insert(gdPfInfo);
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
            gdPfInfoService.deleteByPrimaryKey(pid);
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

        boolean exist = gdPfInfoService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 创建新的派发方案
     */
    @RequestMapping(value = "/create")
    @ResponseBody
    @Transactional
    public Map<String, Object> create(GdPfInfo gdPfInfo) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            gdPfInfoService.insert(gdPfInfo);
            gdPfInfoService.createNewPfInfo(gdPfInfo);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 修改派发方案
     */
    @RequestMapping(value = "/changePfInfo")
    @ResponseBody
    @Transactional
    public Map<String, Object> changePfInfo(List<GdPfInfo> gdPfInfos) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            gdPfInfoService.changePfInfo(gdPfInfos);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }

        return tips;
    }

    @RequestMapping(value = "/queryGdPfInfo")
    @ResponseBody
    public Map<String, Object> queryGdPfInfo(Page page, Model model, HttpServletRequest request, Integer pfPid) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        GdPfInfoVo gdPfInfoVo = new GdPfInfoVo();
        gdPfInfoVo.setPfPid(pfPid);
        for (Filter filter : filters) {
            if ("shareholdCode".equals(filter.getProperty())) {
                gdPfInfoVo.setShareholdCode(filter.getValue().toString());
            } else if ("name".equals(filter.getProperty())) {
                gdPfInfoVo.setName(filter.getValue().toString());
            } else if ("domesticOrForeign".equals(filter.getProperty())) {
                gdPfInfoVo.setDomesticOrForeign(filter.getValue().toString());
            }
        }
        PageHelper.start(page);
        List<GdPfInfoVo> gdPfInfoVos = gdPfInfoService.queryGdPfInfo(gdPfInfoVo);
        PageInfo<GdPfInfoVo> pageInfo = new PageInfo<>(gdPfInfoVos);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    @RequestMapping(value = "/editPf")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> edit(String data) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            List<GdPfInfo> gdPfInfos = JSONArray.parseArray(data).toJavaList(GdPfInfo.class);
            gdPfInfos.forEach(gdPfInfo -> gdPfInfoService.updateSelective(gdPfInfo));
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }

        return tips;
    }

}