/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 10:02:38
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.bus.model.WarnConfig;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.WarnConfigService;
import com.yzj.jep.app.bus.vo.WarnConfigVo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus/earlyWarning")
public class EarlyWarningController extends CrudController<WarnConfig> {
    private static Logger logger = LoggerFactory.getLogger(EarlyWarningController.class);

    @Autowired
    private WarnConfigService warnConfigService;

    @Autowired
    private RelOrgService relOrgService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/EarlyWarningList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<WarnConfig> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        warnConfigService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/warnConfigEdit";
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

        WarnConfig warnConfig = warnConfigService.get(pid);
        model.addAttribute("warnConfig", warnConfig);
        model.addAttribute("opt", "edit");

        return "bus/warnConfigEdit";
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

        WarnConfig warnConfig = warnConfigService.get(pid);
        model.addAttribute("warnConfig", warnConfig);

        return "bus/warnConfigView";
    }

    /**
     * 保存
     *
     * @param warnConfigVo
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(WarnConfigVo warnConfigVo) {

        Map<String, Object> tips = new HashMap<String, Object>();
        WarnConfig warnConfig1 = new WarnConfig();
        WarnConfig warnConfig2 = new WarnConfig();
        BeanUtils.copyProperties(warnConfigVo, warnConfig1);

        Boolean isExist = warnConfigService.exists(1);
        warnConfig1.setPid(2);
        warnConfig2.setPid(1);
        warnConfig2.setWarnThreshold1l(warnConfigVo.getSwarnThreshold1l());
        warnConfig2.setWarnThreshold2l(warnConfigVo.getSwarnThreshold2l());
        warnConfig2.setWarnThreshold3l(warnConfigVo.getSwarnThreshold3l());
        try {
            if (isExist) {
                warnConfigService.update(warnConfig1);
                warnConfigService.update(warnConfig2);
            } else {
                warnConfigService.insert(warnConfig1);
                warnConfigService.insert(warnConfig2);
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
            warnConfigService.deleteByPrimaryKey(pid);
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

        boolean exist = warnConfigService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }
}