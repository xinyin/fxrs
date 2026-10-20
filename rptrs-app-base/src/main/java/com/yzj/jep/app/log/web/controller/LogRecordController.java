/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月16日 14:41:49
 *
 */

package com.yzj.jep.app.log.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
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

import com.yzj.jep.app.log.model.LogRecord;
import com.yzj.jep.app.log.service.LogRecordService;


@Controller
@RequestMapping("/log/logRecord")
public class LogRecordController extends CrudController<LogRecord> {
    private static Logger logger = LoggerFactory.getLogger(LogRecordController.class);

    @Autowired
    private LogRecordService logRecordService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "日志记录", description = "操作日志列表数据展示")
    public String list(Model model) {

        return "log/logRecordList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<LogRecord> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        logRecordService.query(page, filters, orders);
    }

    @RequestMapping("/query")
    public Map<String, Object> query(Page<LogRecord> page, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("modifiedTime"));
        PageHelper.start(page);
        //查询日志操作内容
        List<LogRecord> logRecord = logRecordService.select(filters);
        if (logRecord.size() > 0) {
            PageHelper.end(page, logRecord);
            result.put(RESULT_TOTAL, page.getTotal());
            result.put(RESULT_ROWS, page.getResult());
        }
        return result;
    }


    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "log/logRecordEdit";
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

        LogRecord logRecord = logRecordService.get(pid);
        model.addAttribute("logRecord", logRecord);
        model.addAttribute("opt", "edit");

        return "log/logRecordEdit";
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

        LogRecord logRecord = logRecordService.get(pid);
        model.addAttribute("logRecord", logRecord);

        return "log/logRecordView";
    }

    /**
     * 保存
     *
     * @param logRecord
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(LogRecord logRecord) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = logRecordService.exists(logRecord.getPid());
        try {
            if (isExist) {

                logRecordService.update(logRecord);
            } else {
                logRecordService.insert(logRecord);
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
            logRecordService.deleteByPrimaryKey(pid);
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

        boolean exist = logRecordService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }
}