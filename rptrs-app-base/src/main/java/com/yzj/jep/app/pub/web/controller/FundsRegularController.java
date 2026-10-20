package com.yzj.jep.app.pub.web.controller;

import com.alibaba.fastjson.JSON;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.FundsRegularService;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.controller.CrudController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/pub/fundsRegular")
public class FundsRegularController  extends CrudController<Publog> {
    @Autowired
    private PublogService publogService;
    @Autowired
    private FundsRegularService fundsRegularService;
    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "资金运用类披露管理")
    public String lists(Model model) {
        return "pub/fundsRegularList";
    }

    @RequestMapping(value = "/generalList")
    @Log(module = "资金运用类披露管理")
    public String generalList(Model model) {
        return "pub/fundsGeneralRegularList";
    }

    @RequestMapping(value = "/general" , method = {RequestMethod.GET})
    @ResponseBody
    public void allQuarter( Page<Publog> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(Filter.eq("delflag", "1"));
        orders.add(Order.desc("maxPercentInNetAssets"));
        filters.add(Filter.eq("pubType","9"));
        publogService.query(page, filters, orders);
    }
    @RequestMapping(value = "/generalAdd")
    public String generalAdd(Model model) {
        return "pub/fundsGeneralRegularAdd";
    }

    @RequestMapping(value = "/allGeneral")
    @ResponseBody
    public void allGeneral(Page<PublogRegularVo> page, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        //资金运用一般审议
        List<PublogRegularVo> fundsRegularVos = fundsRegularService.getConsiderTypeOne();
        page.setTotal(fundsRegularVos.size());
        int pages = (fundsRegularVos.size() + page.getPageSize() -
                1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<PublogRegularVo> publogRegularVoList = new ArrayList<PublogRegularVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < fundsRegularVos.size()) {
                publogRegularVoList.add(fundsRegularVos.get(i));
            }
        }

        page.setResult(publogRegularVoList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        try {
            response.getWriter().print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
//
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Publog> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(Filter.eq("delflag", "1"));
        orders.add(Order.desc("maxPercentInNetAssets"));
        filters.add(Filter.eq("pubType","7"));
        publogService.query(page, filters, orders);

    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {
        return "pub/fundsRegularAdd";
    }

    @RequestMapping(value = "/allFunds")
    @ResponseBody
    public void allFunds(Page<PublogRegularVo> page, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        //资金运用类逐笔审议审议
        List<PublogRegularVo> fundsRegularVos = fundsRegularService.getAll();
        page.setTotal(fundsRegularVos.size());
        int pages = (fundsRegularVos.size() + page.getPageSize() -
                1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<PublogRegularVo> publogRegularVoList = new ArrayList<PublogRegularVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < fundsRegularVos.size()) {
                publogRegularVoList.add(fundsRegularVos.get(i));
            }
        }

        page.setResult(publogRegularVoList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        try {
            response.getWriter().print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
//
    }
}
