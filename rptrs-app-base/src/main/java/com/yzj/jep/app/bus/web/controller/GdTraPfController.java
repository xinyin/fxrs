/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月02日 16:21:56
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.GdPfInfo;
import com.yzj.jep.app.bus.model.GdPfInfoVo;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.GdPfInfoService;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yzj.jep.app.bus.model.GdTraPf;
import com.yzj.jep.app.bus.service.GdTraPfService;


@Controller
@RequestMapping("/bus/gdTraPf")
public class GdTraPfController extends CrudController<GdTraPf> {
    private static Logger logger = LoggerFactory.getLogger(GdTraPfController.class);

    @Autowired
    private GdTraPfService gdTraPfService;

    @Autowired
    private GdPfInfoService gdPfInfoService;

    @Autowired
    private RootOrgInfoService rootOrgInfoService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/gdTraPfList";
    }

    /**
     * 跳转新增分配方案页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/listAdd")
    public String listPF(Model model) {


        return "bus/gdTraPfListAdd";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<GdTraPf> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        filters.add(new Filter("delflag", Operator.EQ, 1));
        gdTraPfService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/gdTraPfEdit";
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

        GdTraPf gdTraPf = gdTraPfService.get(pid);
        model.addAttribute("gdTraPf", gdTraPf);
        model.addAttribute("opt", "edit");

        return "bus/gdTraPfEdit";
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

        GdTraPf gdTraPf = gdTraPfService.get(pid);
        model.addAttribute("gdTraPf", gdTraPf);

        return "bus/gdTraPfInfoView";
    }

    /**
     * 保存
     *
     * @param gdTraPf
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional
    public Map<String, Object> save(GdTraPf gdTraPf) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = gdTraPfService.exists(gdTraPf.getPid());
        try {
            if (isExist) {

                gdTraPfService.updateSelective(gdTraPf);
                tips.put("pid", gdTraPf.getPid());
            } else {
                gdTraPf.setDelflag("1");
                gdTraPf.setStatus("3");
                RootOrgInfo rootOrgInfo = rootOrgInfoService.get(1);
                String totalShareAmount = rootOrgInfo.getTotalShareAmount();
                gdTraPf.setTotalShare(totalShareAmount);
                gdTraPf.setAllocationAmount(totalShareAmount);
                gdTraPfService.insert(gdTraPf);
                Integer pid = gdTraPfService.get(gdTraPf).getPid();
                GdPfInfo gdPfInfo = new GdPfInfo();
                gdPfInfo.setPfPid(pid);
                gdPfInfo.setBonusIssue(gdTraPf.getBonusIssue());
                gdPfInfo.setCashBonus(gdTraPf.getCashBonus());
                gdPfInfoService.createNewPfInfo(gdPfInfo);
                tips.put("pid", pid);
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
            GdTraPf gdTraPf = new GdTraPf();
            gdTraPf.setPid(pid);
            gdTraPf.setDelflag("0");
            gdTraPfService.updateSelective(gdTraPf);
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

        boolean exist = gdTraPfService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 跳转审批页面
     */
    @RequestMapping(value = "/submit/{pid}")
    public String submit(@PathVariable Integer pid, Model model) {
        GdTraPf gdTraPf = gdTraPfService.get(pid);
        model.addAttribute("gdTraPf", gdTraPf);
        return "bus/gdTraPfSubmit";
    }

    /**
     * 审批页面获取数据
     */
    @RequestMapping(value = "/getSubmit/{pid}")
    @ResponseBody
    public Map<String, Object> queryGdPfInfo(Page page, Model model, HttpServletRequest request, @PathVariable Integer pid) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            List<Filter> filters = RequestUtils.buildFilters(request);
            List<Order> orders = RequestUtils.buildOrders(request);
            GdTraPf gdTraPf = new GdTraPf();
            gdTraPf.setPid(pid);
            PageHelper.start(page);
            List<GdTraPf> gdTraPfs = gdTraPfService.select(gdTraPf);
            PageInfo<GdTraPf> pageInfo = new PageInfo<>(gdTraPfs);
            tips.put(RESULT_TOTAL, pageInfo.getTotal());
            tips.put(RESULT_ROWS, pageInfo.getList());
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    @RequestMapping(value = "/infoSubmit/{pid}")
    public String infoSubmit(@PathVariable Integer pid, Model model) {

        GdTraPf gdTraPf = gdTraPfService.get(pid);
        model.addAttribute("gdTraPf", gdTraPf);
        model.addAttribute("opt", "edit");

        return "ex/gdTraPfInfoSubmit";
    }

    /**
     * 更改提交审批状态
     */
    @RequestMapping(value = "/changeApplyStatus/{pid}")
    @ResponseBody
    public Map<String, Object> changeApplyStatus(@PathVariable Integer pid) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            GdTraPf gdTraPf = new GdTraPf();
            gdTraPf.setStatus("4");
            gdTraPf.setPid(pid);
            gdTraPfService.updateSelective(gdTraPf);
            tips.put(WebCode.SUCCESS, true);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    /**
     * 点击导出根据年份查询数据
     */
    @RequestMapping(value = "/selectGdTraPfExportByYear/{pid}")
    @ResponseBody
    public void selectGdTraPfExportByYear(@PathVariable Integer pid, HttpServletResponse response, HttpServletRequest request) {
        //设置文件生成时间
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String fileTime = sf.format(new Date());
        Date fileCreateTime = null;
        try {
            fileCreateTime = sf.parse(fileTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        GdPfInfoVo gdPfInfoVo = new GdPfInfoVo();
        gdPfInfoVo.setPfPid(pid);
        for (Filter filter : filters) {
            if ("shareholdCode".equals(filter.getProperty())) {
                gdPfInfoVo.setShareholdCode(filter.getValue().toString());
            } else if ("name".equals(filter.getProperty())) {
                gdPfInfoVo.setName(filter.getValue().toString());
            } else if ("domesticOrForeign".equals(filter.getProperty())) {
                gdPfInfoVo.setDomesticOrForeign(filter.getValue().toString());
            }
        }


        List<GdPfInfoVo> gdPfInfoVos = gdPfInfoService.queryGdPfInfo(gdPfInfoVo);
        //设置导出文件名称
        sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "股东权益派发信息_" + sf.format(new Date());
        //清空空白行
        response.reset();
        //设置文件名response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
        try {
            response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //下载格式
        response.setContentType("application/msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        HSSFRow rows;
        HSSFCell cells;
        //设置表格第一行的列名
        // 获得表格第一行
        int i = 0;
        rows = sheet.createRow(i);
        String[] strings = {"股东账号", "股东名称", "股东类型", "境内/境外", "证件类型", "证件号码", "账户冻结", "交易账户账号", "持股数量", "持股占比",
                "已冻结股数", "已质押股数", "奖金分红", "派发红股"};
        for (int j = 0; j < strings.length; j++) {
            // 根据需要给第一行每一列设置标题
            cells = rows.createCell(j);
            cells.setCellValue(strings[j]);
        }

        for (GdPfInfoVo gdTraPf : gdPfInfoVos) {
            i++;
            rows = sheet.createRow(i);
            //股东账号
            cells = rows.createCell(0);
            cells.setCellValue(gdTraPf.getShareholdCode());
            //股东名称
            cells = rows.createCell(1);
            cells.setCellValue(gdTraPf.getName());

            //股东类型
            cells = rows.createCell(2);
            cells.setCellValue(gdTraPf.getGdType());
            //境内/境外
            cells = rows.createCell(3);
            cells.setCellValue(gdTraPf.getDomesticOrForeign());
            //证件类型
            cells = rows.createCell(4);
            cells.setCellValue(gdTraPf.getIdentifyType());
            //证件号码
            cells = rows.createCell(5);
            cells.setCellValue(gdTraPf.getIdentifyNumber());
            //账户冻结
            cells = rows.createCell(6);
            cells.setCellValue(gdTraPf.getGdDj());
            //交易账户账号
            cells = rows.createCell(7);
            cells.setCellValue(gdTraPf.getTradeAccount());
            //持股数量
            cells = rows.createCell(8);
            cells.setCellValue(gdTraPf.getHoleShareAmount());

            //持股占比
            cells = rows.createCell(9);
            cells.setCellValue(gdTraPf.getShareholdingProportion());
            //已冻结股数
            cells = rows.createCell(10);
            cells.setCellValue(gdTraPf.getFrozenShareAmount());
            //已质押股数
            cells = rows.createCell(11);
            cells.setCellValue(gdTraPf.getPledgedShareAmount());
            //奖金分红
            cells = rows.createCell(12);
            cells.setCellValue(gdTraPf.getCashBonus());
            //派发红股
            cells = rows.createCell(13);
            cells.setCellValue(gdTraPf.getBonusIssue());

        }
        try {
            OutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}