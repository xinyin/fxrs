package com.yzj.jep.app.pub.web.controller;

import com.alibaba.fastjson.JSON;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.ins.web.controller.Commons;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.FundsReportService;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pub/fundsReport")
public class FundsReportController extends CrudController<Publog> {
    @Autowired
    private PublogService publogService;
    @Autowired
    private FundsReportService fundsReportService;
    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "资金运用类报告管理",description = "列表数据查询页面")
    public String list(Model model) {
        return "pub/fundsReportList";
    }

    /**
     * 新增报送文件
     */
    @RequestMapping(value = "/add")
    public String add(Model model){
        return "pub/fundsReportAdd";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Publog> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(Filter.eq("delflag", "1"));
        orders.add(Order.desc("maxPercentInNetAssets"));
        filters.add(Filter.eq("pubType","8"));

        publogService.query(page, filters, orders);

    }

    /**
     * 为新增报告文件页面准备数据
     */
    @RequestMapping(value = "/allReport")
    @ResponseBody
    public void allReport(Page<PublogRegularVo> page, HttpServletRequest request, HttpServletResponse response) {

        List<Filter> filters = RequestUtils.buildFilters(request);

        Map<String, Object> result = new HashMap<>();

        UserProfile userProfile = UserProfileUtils.getUserProfile();
        String unitCode = userProfile.getUnitCode();
        String userid = userProfile.getUserid();
        AuthorityUtil authorityUtil = new AuthorityUtil();
        filters = authorityUtil.getFilterByRole(filters);
        Commons.common(request, filters);
        filters.add(Filter.eq("delflag", "1"));
        //处理交易截止日期modifiedTime时间，往后加一天
        List<Filter> handleFilters = new ArrayList<>();
//        防止有缓存
        handleFilters.addAll(filters);
        com.yzj.jep.app.sys.util.Constants.conditions.clear();
        com.yzj.jep.app.sys.util.Constants.conditions.put("filters", handleFilters);
        List<PublogRegularVo> publogRegularVos = fundsReportService.getAll();



        page.setTotal(publogRegularVos.size());
        int pages = (publogRegularVos.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<PublogRegularVo> publogRegularVoList = new ArrayList<PublogRegularVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < publogRegularVos.size()) {
                publogRegularVoList.add(publogRegularVos.get(i));
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
    }
}
