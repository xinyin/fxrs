/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 18:33:42
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.GdExportFile;
import com.yzj.jep.app.bus.service.GdExportFileService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.commons.DateUtils;
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

import com.yzj.jep.app.bus.model.GdFileData;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.GdFileDataService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/gdFileData")
public class GdFileDataController extends CrudController<GdFileData> {
    private static Logger logger = LoggerFactory.getLogger(GdFileDataController.class);

    @Autowired
    private GdFileDataService gdFileDataService;

    @Autowired
    private GdExportFileService gdExportFileService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/gdFileDataList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<GdFileData> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        gdFileDataService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/gdFileDataEdit";
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
        GdFileData gdFileData = gdFileDataService.get(pid);

        model.addAttribute("gdFileData", gdFileData);
        model.addAttribute("opt", "edit");

        return "bus/gdFileDataEdit";
    }

    /**
     * 查看页面
     *
     * @param batchNumber
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{batchNumber}")
    public String view(@PathVariable String batchNumber, Model model) {

        model.addAttribute("batchNumber", batchNumber);

        return "bus/gdFileDataView";
    }

    /**
     * 保存
     *
     * @param gdFileData
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(GdFileData gdFileData) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = gdFileDataService.exists(gdFileData.getPid());
        try {
            if (isExist) {

                gdFileDataService.update(gdFileData);
            } else {
                gdFileDataService.insert(gdFileData);
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
            gdFileDataService.deleteByPrimaryKey(pid);
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

        boolean exist = gdFileDataService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    @RequestMapping(value = "/getAllGdInfo")
    @ResponseBody
    public Map<String, Object> getAllGdInfo(Page page, Model model, HttpServletRequest request, String batchNumber) {

        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        GdFileData gdFileData = new GdFileData();
        for (Filter filter : filters) {
            if ("shareholdersCode".equals(filter.getProperty())) {
                gdFileData.setShareholdersCode(filter.getValue().toString());
            } else if ("name".equals(filter.getProperty())) {
                gdFileData.setName(filter.getValue().toString());
            } else if ("domesticOrForeign".equals(filter.getProperty())) {
                gdFileData.setDomesticOrForeign(filter.getValue().toString());
            } else if ("shareholdersType".equals(filter.getProperty())) {
                gdFileData.setShareholdersType(filter.getValue().toString());
            } else if ("stateOwned".equals(filter.getProperty())) {
                gdFileData.setStateOwned(filter.getValue().toString());
            } else if ("status".equals(filter.getProperty())) {
                gdFileData.setStatus(filter.getValue().toString());
            }
        }
        if (batchNumber != null) {
            if ("%".equals(gdFileData.getShareholdersType())) {
                gdFileData.setShareholdersType(null);
            }
            if ("%".equals(gdFileData.getStatus())) {
                gdFileData.setStatus(null);
            }
            if ("%".equals(gdFileData.getDomesticOrForeign())) {
                gdFileData.setDomesticOrForeign(null);
            }
            gdFileData.setBatchNumber(batchNumber);
            PageHelper.start(page);
            List<GdFileData> gdFileDataList = gdFileDataService.select(gdFileData);
            PageInfo<GdFileData> pageInfo = new PageInfo<>(gdFileDataList);
            result.put(RESULT_TOTAL, pageInfo.getTotal());
            result.put(RESULT_ROWS, pageInfo.getList());
            return result;
        }
        PageHelper.start(page);
        List<GdFileData> gdFileDataList = gdFileDataService.getAllGdInfo(gdFileData);
        PageInfo<GdFileData> pageInfo = new PageInfo<>(gdFileDataList);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 生成导出数据和批次
     */
    @RequestMapping(value = "/newFileBatch")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> newFileBatch(GdFileData gdFileData, String fileName) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            String staffCode = userProfile.getStaffCode();
            GdExportFile gdExportFile = new GdExportFile();
            String batchNumber = BatchNumberUtil.makeBatchNumber();
            gdExportFile.setBatchNumber(batchNumber);
            gdExportFile.setExporeUserId(staffCode);
            gdExportFile.setFileCreateTime(DateUtils.format(new Date(), "yyyy-MM-dd"));
            gdExportFile.setStatus("3");
            gdExportFile.setDelflag("1");
            gdExportFile.setName(fileName);
            gdExportFileService.insert(gdExportFile);
            gdFileData.setBatchNumber(batchNumber);
            gdFileDataService.newFileBatch(gdFileData);
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