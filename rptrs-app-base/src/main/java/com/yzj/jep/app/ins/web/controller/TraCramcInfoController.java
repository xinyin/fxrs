/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月12日 11:10:44
 *
 */

package com.yzj.jep.app.ins.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yzj.jep.app.base.constant.IsStatusDict;
import com.yzj.jep.app.bus.model.DocFile;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.DocFileService;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.dto.StatisticsDto;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Role;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.wf.service.HisProcInstService;
import net.bytebuddy.matcher.FilterableList;
import org.drools.rule.From;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/ins/traCramcInfo")
public class TraCramcInfoController extends CrudController<TraCramcInfo> {
    private static Logger logger = LoggerFactory.getLogger(TraCramcInfoController.class);

    @Autowired
    private TraCramcInfoService traCramcInfoService;
    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private TraFundsService traFundsService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private TraInvestmentService traInvestmentService;
    @Autowired
    private TraServiceService traServiceService;
    @Autowired
    private TraOtherService traOtherService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private HisProcInstService hisProcInstService;
    @Autowired
    private DocFileService docFileService;

    /**
     * 待关联交易页面跳转
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "ins/traCramcInfoList";
    }

    /**
     * 实际交易信息关联弹窗跳转
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/win")
    public String win(Model model) {
        return "ins/traCramcInfoWin";
    }

    /**
     * 从交易点击实际交易数据管理跳转
     */
    @RequestMapping(value = "/ActualTra/{transNum}")
    public String ActualTra(@PathVariable String transNum, Model model) {
        model.addAttribute("transNum", transNum);
        return "ins/traCramcInfoActualTra";
    }


    /**
     * 新建关联
     */
    @RequestMapping(value = "/relationCramc")
    @ResponseBody
    public Map<String, Object> relationCramsc(String transNum, String pid) {
        Map<String, Object> map = new HashMap<String, Object>();
        TraCramcInfo traCramcInfo = traCramcInfoService.get(Integer.valueOf(pid));
        traCramcInfo.setTransNum(transNum);
        traCramcInfo.setIsRelation(IsStatusDict.Y.getValue());
        traCramcInfoService.update(traCramcInfo);
        tradeAmountAdd(transNum, traCramcInfo.getFamount());
        map.put(WebCode.SUCCESS, true);
        map.put(WebCode.MSG, WebCode.SUCCESS_UPDATE);
        return map;
    }

    /**
     * 实际交易数据查询
     *
     * @param transNum
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/query/{transNum}"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryVo(@PathVariable String transNum, Page<TraCramcInfo> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        //如果交易编号为空
        if ("".equals(transNum) || null == transNum) {
            filters.add(new Filter("isRelation", Operator.EQ, IsStatusDict.N.getValue()));

        } else {
            filters.add(new Filter("isRelation", Operator.EQ, IsStatusDict.Y.getValue()));
            filters.add(new Filter("transNum", Operator.EQ, transNum));
        }
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("modifiedTime"));
        PageHelper.start(page);
        List<TraCramcInfo> traCramcInfoList = traCramcInfoService.select(filters);
        PageHelper.end(page, traCramcInfoList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }


    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraCramcInfo> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("isRelation", IsStatusDict.N.getValue()));
        traCramcInfoService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "ins/traCramcInfoEdit";
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

        TraCramcInfo traCramcInfo = traCramcInfoService.get(pid);
        model.addAttribute("traCramcInfo", traCramcInfo);
        model.addAttribute("opt", "edit");

        return "ins/traCramcInfoEdit";
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

        TraCramcInfo traCramcInfo = traCramcInfoService.get(pid);
        model.addAttribute("traCramcInfo", traCramcInfo);

        return "ins/traCramcInfoView";
    }

    /**
     * 保存
     *
     * @param traCramcInfo
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(TraCramcInfo traCramcInfo) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = traCramcInfoService.exists(traCramcInfo.getPid());
        try {
            if (isExist) {

                traCramcInfoService.update(traCramcInfo);
            } else {
                traCramcInfoService.insert(traCramcInfo);
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
            TraCramcInfo traCramcInfo = traCramcInfoService.get(pid);
            tradeAmountSubtract(traCramcInfo.getTransNum(), traCramcInfo.getFamount());
            traCramcInfo.setIsRelation(IsStatusDict.N.getValue());
            traCramcInfo.setTransNum("");
            traCramcInfoService.update(traCramcInfo);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }

    private void tradeAmountSubtract(String transNum, BigDecimal famount) {
        famount = famount.divide(new BigDecimal("10000"));
        List<Filter> filters = new Filter.Builder().eq("transNum", transNum).build();
        if (traInvestmentService.exists(filters)) {
            TraInvestment traInvestment = traInvestmentService.get(filters);
            traInvestment.setTradeAmount(traInvestment.getTradeAmount().subtract(famount));
            traInvestmentService.update(traInvestment);
        }
        if (traFundsService.exists(filters)) {
            TraFunds traFunds = traFundsService.get(filters);
            traFunds.setTradeAmount(traFunds.getTradeAmount().subtract(famount));
            traFundsService.update(traFunds);
        }
        if (traBusinessService.exists(filters)) {
            TraBusiness traBusiness = traBusinessService.get(filters);
            traBusiness.setTradeAmount(traBusiness.getTradeAmount().subtract(famount));
            traBusinessService.update(traBusiness);
        }
        if (traInterestService.exists(filters)) {
            TraInterest traInterest = traInterestService.get(filters);
            traInterest.setTradeAmount(traInterest.getTradeAmount().subtract(famount));
            traInterestService.update(traInterest);
        }
        if (traServiceService.exists(filters)) {
            TraService traService = traServiceService.get(filters);
            traService.setTradeAmount(traService.getTradeAmount().subtract(famount));
            traServiceService.update(traService);
        }
        if (traOtherService.exists(filters)) {
            TraOther traOther = traOtherService.get(filters);
            traOther.setTradeAmount(traOther.getTradeAmount().subtract(famount));
            traOtherService.update(traOther);
        }
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

        boolean exist = traCramcInfoService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 关联实际交易数据后 将成交价格累加到被关联交易的交易金额上
     */
    public void tradeAmountAdd(String transNum, BigDecimal famount) {
        famount = famount.divide(new BigDecimal("10000"));
        List<Filter> filters = new Filter.Builder().eq("transNum", transNum).build();
        if (traInvestmentService.exists(filters)) {
            TraInvestment traInvestment = traInvestmentService.get(filters);
            traInvestment.setTradeAmount(traInvestment.getTradeAmount().add(famount));
            traInvestmentService.update(traInvestment);
        }
        if (traFundsService.exists(filters)) {
            TraFunds traFunds = traFundsService.get(filters);
            traFunds.setTradeAmount(traFunds.getTradeAmount().add(famount));
            traFundsService.update(traFunds);
        }
        if (traBusinessService.exists(filters)) {
            TraBusiness traBusiness = traBusinessService.get(filters);
            traBusiness.setTradeAmount(traBusiness.getTradeAmount().add(famount));
            traBusinessService.update(traBusiness);
        }
        if (traInterestService.exists(filters)) {
            TraInterest traInterest = traInterestService.get(filters);
            traInterest.setTradeAmount(traInterest.getTradeAmount().add(famount));
            traInterestService.update(traInterest);
        }
        if (traServiceService.exists(filters)) {
            TraService traService = traServiceService.get(filters);
            traService.setTradeAmount(traService.getTradeAmount().add(famount));
            traServiceService.update(traService);
        }
        if (traOtherService.exists(filters)) {
            TraOther traOther = traOtherService.get(filters);
            traOther.setTradeAmount(traOther.getTradeAmount().add(famount));
            traOtherService.update(traOther);
        }
    }


    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void pdfStreamHandler(HttpServletRequest request, HttpServletResponse response) {
        String pidStr = request.getParameter("pid");
        if (StringUtils.isEmpty(pidStr)) {
            return;
        }
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.EQ, Integer.valueOf(pidStr)));
        List<DocFile> docFiles = docFileService.select(filters);
        String path = "";
        String fileName = "";
        if (docFiles != null && docFiles.size() > 0) {
            path = docFiles.get(0).getFileUrl();
            fileName = docFiles.get(0).getFileName();
        }
        File file = new File(path);

        if (file.exists()) {
            byte[] data = null;
            try {
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                FileInputStream input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
                System.out.println("pdf文件处理异常：" + e);
            }

        } else {
            return;
        }
    }
}