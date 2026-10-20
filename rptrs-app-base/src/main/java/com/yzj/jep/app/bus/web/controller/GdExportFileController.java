/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 18:33:39
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.GdExportFile;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.GdExportFileService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/gdExportFile")
public class GdExportFileController extends CrudController<GdExportFile> {
    private static Logger logger = LoggerFactory.getLogger(GdExportFileController.class);

    @Autowired
    private GdExportFileService gdExportFileService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/gdExportFileList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<GdExportFile> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        String staffCode = userProfile.getStaffCode();
        filters.add(new Filter("exportUserId", Operator.EQ, staffCode));
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        gdExportFileService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/gdExportFileEdit";
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

        GdExportFile gdExportFile = gdExportFileService.get(pid);
        model.addAttribute("gdExportFile", gdExportFile);
        model.addAttribute("opt", "edit");

        return "bus/gdExportFileEdit";
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

        GdExportFile gdExportFile = gdExportFileService.get(pid);
        model.addAttribute("gdExportFile", gdExportFile);

        return "bus/gdExportFileView";
    }

    /**
     * 保存
     *
     * @param gdExportFile
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(GdExportFile gdExportFile) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = gdExportFileService.exists(gdExportFile.getPid());
        try {
            if (isExist) {

                gdExportFileService.update(gdExportFile);
            } else {
                gdExportFileService.insert(gdExportFile);
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
            GdExportFile gdExportFile = new GdExportFile();
            gdExportFile.setPid(pid);
            gdExportFile.setDelflag("0");
            gdExportFileService.updateSelective(gdExportFile);
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

        boolean exist = gdExportFileService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 提交页面跳转
     */
    @RequestMapping(value = "/submit/{pid}")
    public String submit(Model model, @PathVariable Integer pid) {
        model.addAttribute("pid", pid);
        return "bus/gdExportFileSubmit";
    }

    /**
     * 审批数据查询
     */
    @RequestMapping(value = "/submitInfo/{pid}")
    @ResponseBody
    public Map<String, Object> submitInfo(Page page, Model model, HttpServletRequest request, @PathVariable Integer pid) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        GdExportFile gdExportFile = new GdExportFile();
        gdExportFile.setPid(pid);
        PageHelper.start(page);
        List<GdExportFile> gdTraPfs = gdExportFileService.select(gdExportFile);
        PageInfo<GdExportFile> pageInfo = new PageInfo<>(gdTraPfs);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 更改提交审批状态
     */
    @RequestMapping(value = "/changeApplyStatus/{pid}")
    @ResponseBody
    public Map<String, Object> changeApplyStatus(@PathVariable Integer pid) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            GdExportFile gdExportFile = new GdExportFile();
            gdExportFile.setStatus("4");
            gdExportFile.setPid(pid);
            gdExportFileService.updateSelective(gdExportFile);
            tips.put(WebCode.SUCCESS, true);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 文件详情页面
     */
    @RequestMapping(value = "/fileData/{batchNumber}")
    public String fileData(Model model, @PathVariable String batchNumber) {
        model.addAttribute("batchNumber", batchNumber);
        return "ex/gdFileDataSubmit";
    }
}