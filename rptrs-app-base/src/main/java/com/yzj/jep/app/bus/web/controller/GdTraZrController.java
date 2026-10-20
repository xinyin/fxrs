/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月01日 11:59:29
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.GdTraUtil;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/gdTraZr")
public class GdTraZrController extends CrudController<GdTraZr> {
    private static Logger logger = LoggerFactory.getLogger(GdTraZrController.class);

    @Autowired
    private GdTraZrService gdTraZrService;

    @Autowired
    private GdFileService gdFileService;

    /**
     * 获取转让列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/gdTraZrList";
    }

    /**
     * 获取继承列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/Jclist")
    public String jcList(Model model) {

        return "bus/gdTraJcList";
    }

    /**
     * 获取赠与列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/giftList")
    public String giftList(Model model) {

        return "bus/gdTraGiftList";
    }

    @RequestMapping(value = {"/queryJc"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryJc(Page<GdTraZr> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        filters.add(new Filter("operationType", Operator.EQ, "1"));
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        gdTraZrService.query(page, filters, orders);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    @RequestMapping(value = {"/queryGift"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryGift(Page<GdTraZr> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        filters.add(new Filter("operationType", Operator.EQ, "2"));
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        gdTraZrService.query(page, filters, orders);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }


    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<GdTraZr> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("operationType", Operator.EQ, "0"));
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        gdTraZrService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/gdTraZrEdit";
    }

    /**
     * 新增继承页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/addJc")
    public String addJc(Model model) {

        return "bus/gdTraJcEdit";
    }

    /**
     * 新增赠与页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/addGift")
    public String addGift(Model model) {

        return "bus/gdTraGiftEdit";
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

        GdTraZr gdTraZr = gdTraZrService.get(pid);
        model.addAttribute("gdTraZr", gdTraZr);
        model.addAttribute("opt", "edit");
        return "bus/gdTraZrEdit";
    }

    /**
     * 编辑继承页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/editJc/{pid}")
    public String editJc(@PathVariable Integer pid, Model model) {

        GdTraZr gdTraZr = gdTraZrService.get(pid);
        model.addAttribute("gdTraZr", gdTraZr);
        model.addAttribute("opt", "edit");
        return "bus/gdTraJcEdit";
    }

    /**
     * 编辑继承页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/editGift/{pid}")
    public String editGift(@PathVariable Integer pid, Model model) {

        GdTraZr gdTraZr = gdTraZrService.get(pid);
        model.addAttribute("gdTraZr", gdTraZr);
        model.addAttribute("opt", "edit");
        return "bus/gdTraGiftEdit";
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

        GdTraZr gdTraZr = gdTraZrService.get(pid);
        model.addAttribute("gdTraZr", gdTraZr);
        String operationType = gdTraZr.getOperationType();
        if ("0".equals(operationType)) {
            return "bus/gdTraZrView";
        }
        if ("1".equals(operationType)) {
            return "bus/gdTraJcView";
        }

        return "bus/gdTraGiftView";
    }

    /**
     * 保存
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/save/{operationType}")
    @ResponseBody
    @Transactional
    public Map<String, Object> save(GdTraZr gdTraZr, GdUploadFileVo gdUploadFileVo, @PathVariable String operationType) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            String fromShareholdersCode = gdTraZr.getFromShareholdersCode();
            String fromShareholdersName = gdTraZr.getFromShareholdersName();
            String fromShareholdersType = gdTraZr.getFromShareholdersType();
            String operationAmount = gdTraZr.getOperationAmount();
            boolean b = GdTraUtil.checkUseAmount(fromShareholdersType, fromShareholdersCode, fromShareholdersName, operationAmount);
            if (!b) {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, "可用股数不足");
                return tips;
            }

            String traSerial = null;
            if (gdTraZr.getPid() != null) {
                Integer pid = gdTraZr.getPid();
                gdTraZrService.updateSelective(gdTraZr);
                gdTraZr.setStatus("3");
                traSerial = gdTraZrService.get(pid).getTraSerial();
            } else {
                gdTraZr.setStatus("3");
                gdTraZr.setDelflag("1");
                gdTraZr.setTraSerial(BatchNumberUtil.makeBatchNumber());
                gdTraZr.setOperationType(operationType);
                traSerial = gdTraZr.getTraSerial();
                gdTraZrService.insert(gdTraZr);
            }
            List<Integer> docTableSelectItem = gdUploadFileVo.getDocTableSelectItem();
            for (Integer pid : docTableSelectItem) {
                GdFile gdFile = new GdFile();
                gdFile.setPid(pid);
                gdFile.setTraSerial(traSerial);
                gdFileService.updateSelective(gdFile);
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
            GdTraZr gdTraZr = new GdTraZr();
            gdTraZr.setPid(pid);
            gdTraZr.setDelflag("0");
            gdTraZrService.updateSelective(gdTraZr);
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

        boolean exist = gdTraZrService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 根据股东代码查询股东信息
     */
    @RequestMapping(value = "/getShareholder")
    @ResponseBody
    public Map<String, Object> getShareholder(String code) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            tips = GdTraUtil.getShareholder(code);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 变更后股数占比
     *
     * @param amount
     * @return
     */
    @RequestMapping(value = "/getProportion")
    @ResponseBody
    public Map<String, Object> getProportion(String amount) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            String[] split = amount.split(",");
            Double fromProportion = GdTraUtil.getProportion(split[0]);
            Double toProportion = GdTraUtil.getProportion(split[1]);
            tips.put(WebCode.SUCCESS, true);
            tips.put("fromProportion", fromProportion);
            tips.put("toProportion", toProportion);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 更改审批状态
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/changeApplyStatus/{pid}")
    @ResponseBody
    public Map<String, Object> changeApplyStatus(@PathVariable Integer pid) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            GdTraZr gdTraZr = new GdTraZr();
            gdTraZr.setPid(pid);
            gdTraZr.setStatus("4");
            gdTraZrService.updateSelective(gdTraZr);
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
     * 提交页面跳转
     *
     * @param model
     * @param operationType
     * @param pid
     * @return
     */
    @RequestMapping(value = "/submit/{pid}/{operationType}")
    public String submit(Model model, @PathVariable String operationType, @PathVariable Integer pid) {
        GdTraZr gdTraZr = gdTraZrService.get(pid);
        model.addAttribute("gdTraZr", gdTraZr);
        if ("0".equals(operationType)) {
            return "bus/gdTraZrSubmit";
        }
        if ("1".equals(operationType)) {
            return "bus/gdTraJcSubmit";
        }
        return "bus/gdTraGiftSubmit";

    }

}