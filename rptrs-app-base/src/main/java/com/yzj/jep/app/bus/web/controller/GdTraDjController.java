/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月02日 14:05:03
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.GdFile;
import com.yzj.jep.app.bus.model.GdUploadFileVo;
import com.yzj.jep.app.bus.service.GdFileService;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.GdTraUtil;
import com.yzj.jep.orm.entity.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.GdTraDj;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.GdTraDjService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/gdTraDj")
public class GdTraDjController extends CrudController<GdTraDj> {
    private static Logger logger = LoggerFactory.getLogger(GdTraDjController.class);

    @Autowired
    private GdTraDjService gdTraDjService;

    @Autowired
    private GdFileService gdFileService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/gdTraDjList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<GdTraDj> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        gdTraDjService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/gdTraDjEdit";
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/addJd/{pid}")
    public String addJd(Model model, @PathVariable Integer pid) {
        GdTraDj gdTraDj = gdTraDjService.get(pid);
        String operationAmount = gdTraDj.getOperationAmount();
        gdTraDj.setCurrFrozenAmount(operationAmount);
        gdTraDj.setOperationAmount(null);
        gdTraDj.setPid(null);
        Map<String, Object> shareholderInfo = getShareholderInfo(gdTraDj.getShareholdersCode(), null);
        String frozenShareAmount = (String) shareholderInfo.get("frozenShareAmount");
        gdTraDj.setFrozenShareAmount(frozenShareAmount);
        model.addAttribute("gdTraDj", gdTraDj);
        model.addAttribute("operationAmount", operationAmount);
        return "bus/gdTraJdEdit";
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

        GdTraDj gdTraDj = gdTraDjService.get(pid);
        model.addAttribute("gdTraDj", gdTraDj);
        model.addAttribute("opt", "edit");

        return "bus/gdTraDjEdit";
    }

    /**
     * 编辑解冻页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/editJd/{pid}")
    public String editJd(@PathVariable Integer pid, Model model) {

        GdTraDj gdTraDj = gdTraDjService.get(pid);
        String frozenSerial = gdTraDj.getFrozenSerial();
        GdTraDj traDj = new GdTraDj();
        traDj.setFrozenSerial(frozenSerial);
        traDj.setOperationType("1");
        List<GdTraDj> select = gdTraDjService.select(traDj);
        model.addAttribute("gdTraDj", gdTraDj);
        model.addAttribute("opt", "edit");
        model.addAttribute("operationAmount", select.get(0).getOperationAmount());

        return "bus/gdTraJdEdit";
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

        GdTraDj gdTraDj = gdTraDjService.get(pid);
        model.addAttribute("gdTraDj", gdTraDj);
        String operationType = gdTraDj.getOperationType();
        if ("0".equals(operationType)) {
            return "bus/gdTraDjView";
        }
        return "bus/gdTraJdView";
    }

    /**
     * 保存
     *
     * @param gdTraDj
     * @param gdUploadFileVo
     * @param operationType
     * @return
     */
    @RequestMapping(value = "/save/{operationType}")
    @ResponseBody
    public Map<String, Object> save(GdTraDj gdTraDj, GdUploadFileVo gdUploadFileVo, @PathVariable String operationType) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Map<String, Object> shareholder = GdTraUtil.getShareholder(gdTraDj.getShareholdersCode());
        if (!(boolean) shareholder.get("success")) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "不是股东");
            return tips;
        }
        Boolean isExist = gdTraDjService.exists(gdTraDj.getPid());
        try {
            String shareholdersType = gdTraDj.getShareholdersType();
            String shareholdersCode = gdTraDj.getShareholdersCode();
            String shareholdersName = gdTraDj.getShareholdersName();
            String operationAmount1 = gdTraDj.getOperationAmount();
            if ("0".equals(operationType)) {
                boolean b = GdTraUtil.checkUseAmount(shareholdersType, shareholdersCode, shareholdersName, operationAmount1);
                if (!b) {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "可用股数不足");
                    return tips;
                }
            }
            if ("1".equals(operationType)) {
                GdTraDj traDj = new GdTraDj();
                traDj.setFrozenSerial(gdTraDj.getFrozenSerial());
                traDj.setOperationType("0");
                traDj.setDelflag("1");
                GdTraDj gdTraDj1 = gdTraDjService.get(traDj);
                Integer unfrozenAmount = gdTraDj1.getUnfrozenAmount() == null ? 0 : Integer.parseInt(gdTraDj1.getUnfrozenAmount());
                Integer operationAmount = gdTraDj1.getOperationAmount() == null ? 0 : Integer.parseInt(gdTraDj1.getOperationAmount());
                if (Integer.parseInt(gdTraDj.getOperationAmount()) > (operationAmount - unfrozenAmount)) {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "超过剩余冻结股数");
                    return tips;
                }
            }
            String traSerial = null;
            if (isExist) {

                gdTraDjService.updateSelective(gdTraDj);
                traSerial = gdTraDjService.get(gdTraDj.getPid()).getFrozenSerial();
            } else {
                gdTraDj.setDelflag("1");
                gdTraDj.setOperationType(operationType);
                gdTraDj.setStatus("3");
                if ("0".equals(operationType)) {
                    gdTraDj.setFrozenSerial(BatchNumberUtil.makeBatchNumber());
                }
                if (gdTraDj.getStateOwned() == null || "".equals(gdTraDj.getStateOwned())) {
                    gdTraDj.setStateOwned("");
                }
                if ("1".equals(operationType)) {
                    String frozenSerial = gdTraDj.getFrozenSerial();
                    GdTraDj gdTraDj1 = new GdTraDj();
                    gdTraDj1.setFrozenSerial(frozenSerial);
                    gdTraDj1.setOperationType("0");
                    GdTraDj traDj = gdTraDjService.get(gdTraDj1);
                    gdTraDj.setCurrFrozenAmount(traDj.getOperationAmount());
                }
                traSerial = gdTraDj.getFrozenSerial();
                gdTraDjService.insert(gdTraDj);
            }
            for (Integer pid : gdUploadFileVo.getDocTableSelectItem()) {
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
            GdTraDj gdTraDj = new GdTraDj();
            gdTraDj.setDelflag("0");
            gdTraDj.setPid(pid);
            gdTraDjService.updateSelective(gdTraDj);
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

        boolean exist = gdTraDjService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 提交审批页面
     */
    @RequestMapping(value = "/submit/{operationType}/{pid}")
    public String submit(Model model, @PathVariable String operationType, @PathVariable Integer pid) {
        GdTraDj gdTraDj = gdTraDjService.get(pid);
        model.addAttribute("gdTraDj", gdTraDj);
        if ("0".equals(operationType)) {
            return "bus/gdTraDjSubmit";
        } else {
            return "bus/gdTraJdSubmit";
        }
    }

    /**
     * 修改审批状态
     */
    @RequestMapping(value = "/changeApplyStatus/{pid}")
    @ResponseBody
    public Map<String, Object> changeApplyStatus(@PathVariable Integer pid) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            GdTraDj gdTraDj = new GdTraDj();
            gdTraDj.setPid(pid);
            gdTraDj.setStatus("4");
            gdTraDjService.updateSelective(gdTraDj);
            tips.put(WebCode.SUCCESS, true);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 获取股东信息
     */
    @RequestMapping(value = "/getShareholderInfo")
    @ResponseBody
    public Map<String, Object> getShareholderInfo(String code, String name) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            tips = GdTraUtil.getShareholder(code);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }
}
