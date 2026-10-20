/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 14:21:39
 *
 */

package com.yzj.jep.app.sub.web.controller;

import com.google.common.collect.Maps;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.bus.model.KnowledgeBase;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.sub.model.Credit;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.model.Template;
import com.yzj.jep.app.sub.service.CreditService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sub.service.TemplateDetailsService;
import com.yzj.jep.app.sub.service.TemplateService;
import com.yzj.jep.app.sub.util.DownFileUtils;
import com.yzj.jep.app.util.ZipUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/sub/credit")
public class CreditController extends CrudController<Credit> {
    private static Logger logger = LoggerFactory.getLogger(CreditController.class);

    @Autowired
    private static Configs configs;

    @Autowired
    private CreditService creditService;

    @Autowired
    private SublogService sublogService;

    @Autowired
    private TemplateDetailsService templateDetailsService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private DownFileUtils downFileUtils;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "报送管理", description = "季度列表查询页面")
    public String list(Model model) {

        return "sub/creditList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Credit> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        creditService.query(page, filters, orders);
    }

    /**
     * 查询报送列表数据
     *
     * @param type
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/query/{type}"}, method = {RequestMethod.GET})
    @ResponseBody
    @Log(module = "报送管理", description = "季度查询报送列表数据")
    public Map<String, Object> queryVo(@PathVariable String type, Page<Rel> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        switch (type) {
            case "organ":
                filters.add(new Filter("subType", "0"));
                break;
            case "person":
                filters.add(new Filter("subType", "1"));
                break;
            case "rel":
                filters.add(new Filter("subType", "2"));
                break;
            case "pro":
                filters.add(new Filter("subType", "3"));
                break;
            case "credit":
                filters.add(new Filter("subType", "4"));
                break;
            case "loan":
                filters.add(new Filter("subType", "5"));
                break;
            case "noLoan":
                filters.add(new Filter("subType", "6"));
                break;
            case "balance":
                filters.add(new Filter("subType", "7"));
                break;
            default:
        }
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("modifiedTime"));
        PageHelper.start(page);
        List<Sublog> subLogs = sublogService.select(filters);
        PageHelper.end(page, subLogs);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 报送文件的下载
     * serverUrl 从服务器上要下载的路径
     * filePath 要保存到本地的路径
     * fileName 文件名
     */
    @RequestMapping(value = "/subDownFile/{pid}")
    @ResponseBody
    @Log(module = "报送管理", description = "报送文件的下载")
    public Map<String, Object> subDownFile(@PathVariable String pid, HttpServletRequest request, HttpServletResponse response) {
        logger.info("报送文件的下载开始：");
        Map<String, Object> tipes = Maps.newHashMap();
        //通过pid查询sublog记录表 获取文件名和下载路径
        Sublog sublog = sublogService.get(Integer.valueOf(pid));
        int number = sublog.getDownAmt();   //获取下载次数
        //文件名
        String fileName = sublog.getFileName();
        //下载服务器的路径  后期在sublog记录表中添加保存路径字段
        configs = SpringContextHolder.getBean(Configs.class);
        String serverUrl = configs.getReport() + "/" + fileName;

        //浏览器下载
        boolean aaa = DownFileUtils.downServerFile(serverUrl, fileName, request, response);
        if (aaa) {
            logger.info("下载文件成功！");
            sublog.setPid(Integer.valueOf(pid));
            sublog.setDownStatus("0");
            sublog.setDownAmt(number + 1);
            sublogService.updateSelective(sublog);
            tipes.put(WebCode.SUCCESS, true);
            tipes.put(WebCode.MSG, WebCode.DOWN_SUCCESS_FILE);
        } else {
            logger.info("下载文件失败！");
            tipes.put(WebCode.SUCCESS, false);
            tipes.put(WebCode.MSG, WebCode.DOWN_FAILED_FILE);
        }
        return tipes;
    }

    /**
     * 下载East报送文件
     * @param pid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/subEastDownFile/{pid}")
    @ResponseBody
    @Log(module = "报送管理",description = "报送文件的下载")
    public Map<String,Object> subEastDownFile(@PathVariable String pid,HttpServletRequest request,HttpServletResponse response){
        logger.info("报送文件的下载开始：");
        Map<String,Object> tipes =Maps.newHashMap();
        //通过pid查询sublog记录表 获取文件名和下载路径
        Sublog sublog = sublogService.get(Integer.valueOf(pid));
        int number = sublog.getDownAmt();   //获取下载次数
        //文件名
        String fileName = sublog.getFileName();
        //下载服务器的路径  后期在sublog记录表中添加保存路径字段
        configs= SpringContextHolder.getBean(Configs.class);
        String serverUrl = configs.getEastFile() +"/"+ fileName;
//		String serverUrl = "/home/rptrs/subFile/" + fileName ;
//		String serverUrl = "F:\\credit\\"+fileName;
        //浏览器下载
        boolean aaa = DownFileUtils.downServerFile(serverUrl,fileName,request,response);
        if (aaa){
            logger.info("下载文件成功！");
            sublog.setPid(Integer.valueOf(pid));
            sublog.setDownStatus("0");
            sublog.setDownAmt(number + 1);
            sublogService.updateSelective(sublog);
            tipes.put(WebCode.SUCCESS, true);
            tipes.put(WebCode.MSG, WebCode.DOWN_SUCCESS_FILE);
        }else{
            logger.info("下载文件失败！");
            tipes.put(WebCode.SUCCESS, false);
            tipes.put(WebCode.MSG, WebCode.DOWN_FAILED_FILE);
        }
        return tipes;
    }
    /**
     * 关联交易季报  生成报送文件
     */
    @RequestMapping(value = "/subFile/{subType}")
    @ResponseBody
    @Log(module = "报送管理", description = "关联交易季报生成报送文件")
    public Map<String, Object> subQuarterFile(Model model, @PathVariable String subType, String subYear, String subQuarter, HttpServletRequest request) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            List<Filter> filters = RequestUtils.buildFilters(request);
            List<Filter> list = RequestUtils.buildFilters(request);
            switch (subType) {
                case "organ":
                    filters.add(new Filter("subType", "0"));
                    break;
                case "person":
                    filters.add(new Filter("subType", "1"));
                    break;
                case "rel":
                    filters.add(new Filter("subType", "2"));
                    break;
                case "pro":
                    filters.add(new Filter("subType", "3"));
                    break;
                case "credit":
                    filters.add(new Filter("subType", "4"));
                    if (StringUtils.isNotBlank(subYear)) {
                        if (StringUtils.isNotBlank(subQuarter)) {
                            switch (subQuarter) {
                                case "1":
                                    list.add(Filter.ge("creditConfirmDate", DateUtils.parseDate(subYear + "-01-01")));
                                    list.add(Filter.le("creditConfirmDate", DateUtils.parseDate(subYear + "-03-31")));
                                    break;
                                case "2":
                                    list.add(Filter.ge("creditConfirmDate", DateUtils.parseDate(subYear + "-04-01")));
                                    list.add(Filter.le("creditConfirmDate", DateUtils.parseDate(subYear + "-06-30")));
                                    break;
                                case "3":
                                    list.add(Filter.ge("creditConfirmDate", DateUtils.parseDate(subYear + "-07-01")));
                                    list.add(Filter.le("creditConfirmDate", DateUtils.parseDate(subYear + "-09-30")));
                                    break;
                                case "4":
                                    list.add(Filter.ge("creditConfirmDate", DateUtils.parseDate(subYear + "-10-01")));
                                    list.add(Filter.le("creditConfirmDate", DateUtils.parseDate(subYear + "-12-31")));
                                    break;
                                default:
                            }
                            break;
                        }
                    }
                    break;
                case "loan":
                    filters.add(new Filter("subType", "5"));
                    if (StringUtils.isNotBlank(subYear)) {
                        if (StringUtils.isNotBlank(subQuarter)) {
                            switch (subQuarter) {
                                case "1":
                                    list.add(Filter.ge("transStartDate", DateUtils.parseDate(subYear + "-01-01")));
                                    list.add(Filter.le("transStartDate", DateUtils.parseDate(subYear + "-03-31")));
                                    break;
                                case "2":
                                    list.add(Filter.ge("transStartDate", DateUtils.parseDate(subYear + "-04-01")));
                                    list.add(Filter.le("transStartDate", DateUtils.parseDate(subYear + "-06-30")));
                                    break;
                                case "3":
                                    list.add(Filter.ge("transStartDate", DateUtils.parseDate(subYear + "-07-01")));
                                    list.add(Filter.le("transStartDate", DateUtils.parseDate(subYear + "-09-30")));
                                    break;
                                case "4":
                                    list.add(Filter.ge("transStartDate", DateUtils.parseDate(subYear + "-10-01")));
                                    list.add(Filter.le("transStartDate", DateUtils.parseDate(subYear + "-12-31")));
                                    break;
                                default:
                            }
                            break;
                        }
                    }
                    break;
                case "noLoan":
                    filters.add(new Filter("subType", "6"));
                    if (StringUtils.isNotBlank(subYear)) {
                        if (StringUtils.isNotBlank(subQuarter)) {
                            switch (subQuarter) {
                                case "1":
                                    list.add(Filter.ge("transStartDate", DateUtils.parseDate(subYear + "-01-01")));
                                    list.add(Filter.le("transStartDate", DateUtils.parseDate(subYear + "-03-31")));
                                    break;
                                case "2":
                                    list.add(Filter.ge("transStartDate", DateUtils.parseDate(subYear + "-04-01")));
                                    list.add(Filter.le("transStartDate", DateUtils.parseDate(subYear + "-06-30")));
                                    break;
                                case "3":
                                    list.add(Filter.ge("transStartDate", DateUtils.parseDate(subYear + "-07-01")));
                                    list.add(Filter.le("transStartDate", DateUtils.parseDate(subYear + "-09-30")));
                                    break;
                                case "4":
                                    list.add(Filter.ge("transStartDate", DateUtils.parseDate(subYear + "-10-01")));
                                    list.add(Filter.le("transStartDate", DateUtils.parseDate(subYear + "-12-31")));
                                    break;
                                default:
                            }
                            break;
                        }
                    }
                    break;
                case "balance":
                    filters.add(new Filter("subType", "7"));
                    if (StringUtils.isNotBlank(subYear)) {
                        if (StringUtils.isNotBlank(subQuarter)) {
                            switch (subQuarter) {
                                case "1":
                                    list.add(Filter.ge("modifiedTime", DateUtils.parseDate(subYear + "-01-01")));
                                    list.add(Filter.le("modifiedTime", DateUtils.parseDate(subYear + "-03-31")));
                                    break;
                                case "2":
                                    list.add(Filter.ge("modifiedTime", DateUtils.parseDate(subYear + "-04-01")));
                                    list.add(Filter.le("modifiedTime", DateUtils.parseDate(subYear + "-06-30")));
                                    break;
                                case "3":
                                    list.add(Filter.ge("modifiedTime", DateUtils.parseDate(subYear + "-07-01")));
                                    list.add(Filter.le("modifiedTime", DateUtils.parseDate(subYear + "-09-30")));
                                    break;
                                case "4":
                                    list.add(Filter.ge("modifiedTime", DateUtils.parseDate(subYear + "-10-01")));
                                    list.add(Filter.le("modifiedTime", DateUtils.parseDate(subYear + "-12-31")));
                                    break;
                                default:
                            }
                            break;
                        }
                    }
                    break;
                //10.21zdz新加
                case "insAllbustraninfo":
                    filters.add(new Filter("subType", "8"));
                    if (StringUtils.isNotBlank(subYear)) {
                        if (StringUtils.isNotBlank(subQuarter)) {
                            switch (subQuarter) {
                                case "1":
                                    list.add(Filter.ge("creditConfirmDate", DateUtils.parseDate(subYear + "-01-01")));
                                    list.add(Filter.le("creditConfirmDate", DateUtils.parseDate(subYear + "-03-31")));
                                    break;
                                case "2":
                                    list.add(Filter.ge("creditConfirmDate", DateUtils.parseDate(subYear + "-04-01")));
                                    list.add(Filter.le("creditConfirmDate", DateUtils.parseDate(subYear + "-06-30")));
                                    break;
                                case "3":
                                    list.add(Filter.ge("creditConfirmDate", DateUtils.parseDate(subYear + "-07-01")));
                                    list.add(Filter.le("creditConfirmDate", DateUtils.parseDate(subYear + "-09-30")));
                                    break;
                                case "4":
                                    list.add(Filter.ge("creditConfirmDate", DateUtils.parseDate(subYear + "-10-01")));
                                    list.add(Filter.le("creditConfirmDate", DateUtils.parseDate(subYear + "-12-31")));
                                    break;
                                default:
                            }
                            break;
                        }
                    }
                    break;
                default:
            }
            Template template = templateService.get(filters);
            if (template != null) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                boolean flag = templateDetailsService.createSubFile(template.getPid(), list, subYear, subQuarter, template.getTemplateName() + sf.format(new Date()), template.getSubType());
                if (flag) {
                    logger.info("报送文件已生成，请查看");
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, WebCode.FAILURE_SUCCESS_FILE);
                } else {
                    logger.info("暂无报送数据生成");
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, WebCode.FAILURE_NODATA_FILE);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_FAILED_FILE);
        }
        return tips;
    }

    /**
     * 提交审批
     */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    @Log(module = "报送管理", description = "季度报送审批流程")
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Credit credit = creditService.get(pid);
        if ("2".equals(credit.getStatus()) || "5".equals(credit.getStatus())) {
            //4 代表审批中
            credit.setPid(pid);
            credit.setStatus("3");
            //更新数据
            creditService.updateSelective(credit);
            tips.put("data", credit);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sub/creditEdit";
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

        Credit credit = creditService.get(pid);
        model.addAttribute("credit", credit);
        model.addAttribute("opt", "edit");

        return "sub/creditEdit";
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

        Credit credit = creditService.get(pid);
        model.addAttribute("credit", credit);

        return "sub/creditView";
    }

    /**
     * 保存
     *
     * @param credit
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(Credit credit) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = creditService.exists(credit.getPid());
        try {
            if (isExist) {

                creditService.update(credit);
            } else {
                creditService.insert(credit);
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
            creditService.deleteByPrimaryKey(pid);
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

        boolean exist = creditService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


}