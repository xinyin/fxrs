/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月01日 15:10:59
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.GdTraUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.GdTraZy;
import com.yzj.jep.app.bus.service.GdTraZyService;


@Controller
@RequestMapping("/bus/gdTraZy")
public class GdTraZyController extends CrudController<GdTraZy> {
    private static Logger logger = LoggerFactory.getLogger(GdTraZyController.class);

    @Autowired
    private GdTraZyService gdTraZyService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/gdTraZyList";
    }

    /**
     * 质押跳转页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit")
    public String editZy(Model model) {

        return "bus/gdTraZyEdit1";
    }

    /**
     * 质押编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit1/{pid}")
    public String edit(@PathVariable Integer pid, Model model) {

        GdTraZy gdTraZy = gdTraZyService.get(pid);
        model.addAttribute("gdTraZy", gdTraZy);
        model.addAttribute("opt", "edit");

        return "bus/gdTraZyEdit1";
    }

    /**
     * 跳转新增解押页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/addJy")
    public String addJy(Integer pid, Model model) {
        GdTraZy gdTraZy = gdTraZyService.get(pid);
        gdTraZy.setCurrPledgedAmount(gdTraZy.getOperationAmount());
        String shareholdersCode = gdTraZy.getShareholdersCode();
        Map<String, Object> shareholderInfo = getShareholderInfo(shareholdersCode, null);
        String pledgedShareAmount = (String) shareholderInfo.get("pledgedShareAmount");
        gdTraZy.setPledgedShareAmount(pledgedShareAmount);
        gdTraZy.setOperationAmount(null);
        model.addAttribute("gdTraZy", gdTraZy);
        model.addAttribute("opt", "edit");
        return "bus/gdTraJyEdit";
    }

    /**
     * 跳转修改解押页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/editJy/{pid}")
    public String editJy(@PathVariable Integer pid, Model model) {
        GdTraZy gdTraZy = gdTraZyService.get(pid);
        model.addAttribute("gdTraZy", gdTraZy);
        model.addAttribute("opt", "edit");
        return "bus/gdTraJyEdit";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<GdTraZy> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", Operator.EQ, 1));
        gdTraZyService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/gdTraZyEdit";
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

        GdTraZy gdTraZy = gdTraZyService.get(pid);
        model.addAttribute("gdTraZy", gdTraZy);
        String operationType = gdTraZy.getOperationType();
        if ("1".equals(operationType)) {
            return "bus/gdTraZyView";
        }

        return "bus/gdTraJyView";
    }

    /**
     * 保存
     *
     * @param gdTraZy
     * @return
     */
    @RequestMapping(value = "/save/{operationType}")
    @ResponseBody
    public Map<String, Object> save(GdTraZy gdTraZy, @PathVariable String operationType) {

        Map<String, Object> tips = new HashMap<String, Object>();
        Map<String, Object> shareholder = GdTraUtil.getShareholder(gdTraZy.getShareholdersCode());
        if (!(boolean) shareholder.get("success")) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "不是股东");
            return tips;
        }
        Boolean isExist = gdTraZyService.exists(gdTraZy.getPid());
        try {
            String shareholdersType = gdTraZy.getShareholdersType();
            String shareholdersCode = gdTraZy.getShareholdersCode();
            String shareholdersName = gdTraZy.getShareholdersName();
            String operationAmount = gdTraZy.getOperationAmount();
            if ("1".equals(operationType)) {
                boolean b = GdTraUtil.checkUseAmount(shareholdersType, shareholdersCode, shareholdersName, operationAmount);
                if (!b) {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "可用股数不足");
                    return tips;
                }
            }
            if ("2".equals(operationType)) {
                String pledgedContract = gdTraZy.getPledgedContract();
                GdTraZy traZy = new GdTraZy();
                traZy.setPledgedContract(pledgedContract);
                traZy.setOperationType("1");
                traZy.setDelflag("1");
                GdTraZy zy = gdTraZyService.get(traZy);
                Integer amount = zy.getOperationAmount() == null ? 0 : Integer.parseInt(zy.getOperationAmount());
                Integer unPledgedAmount = zy.getUnpledgedAmount() == null ? 0 : Integer.parseInt(zy.getUnpledgedAmount());
                if (Integer.parseInt(gdTraZy.getOperationAmount()) > (amount - unPledgedAmount)) {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "质押股数不足");
                    return tips;
                }
            }
            if (isExist) {
                gdTraZy.setStatus("3");
                gdTraZyService.updateSelective(gdTraZy);
            } else {
                gdTraZy.setDelflag("1");
                gdTraZy.setOperationType(operationType);
                gdTraZy.setStatus("3");
                if ("1".equals(operationType)) {
                    gdTraZy.setPledgedContract(BatchNumberUtil.makeBatchNumber());
                }
                if ("2".equals(operationType)) {
                    GdTraZy gdTraZy1 = new GdTraZy();
                    gdTraZy1.setOperationType("1");
                    gdTraZy1.setPledgedContract(gdTraZy.getPledgedContract());
                    GdTraZy traZy = gdTraZyService.get(gdTraZy1);
                    String operationAmount1 = traZy.getOperationAmount();
                    gdTraZy.setCurrPledgedAmount(operationAmount1);
                }
                gdTraZyService.insert(gdTraZy);
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
            GdTraZy gdTraZy = new GdTraZy();
            gdTraZy.setPid(pid);
            gdTraZy.setDelflag("0");
            gdTraZyService.updateSelective(gdTraZy);
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

        boolean exist = gdTraZyService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 提交页面跳转
     */
    @RequestMapping(value = "submit/{pid}/{operationType}")
    public String submit(Model model, @PathVariable Integer pid, @PathVariable String operationType) {
        GdTraZy gdTraZy = gdTraZyService.get(pid);
        model.addAttribute("gdTraZy", gdTraZy);
        if ("1".equals(operationType)) {
            return "bus/gdTraZySubmit";
        }
        return "bus/gdTraJySubmit";
    }

    /**
     * 改审批状态
     */
    @RequestMapping(value = "/changeApplyStatus/{pid}")
    @ResponseBody
    public Map<String, Object> changeApplyStatus(@PathVariable Integer pid) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            GdTraZy gdTraZy = new GdTraZy();
            gdTraZy.setPid(pid);
            gdTraZy.setStatus("4");
            gdTraZyService.updateSelective(gdTraZy);
            tips.put(WebCode.SUCCESS, true);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 查询股东信息
     */
    @RequestMapping(value = "/getShareholderInfo")
    @ResponseBody
    public Map<String, Object> getShareholderInfo(String shareholdersCode, String shareholdersName) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            tips = GdTraUtil.getShareholder(shareholdersCode);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }
}