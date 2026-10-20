package com.yzj.jep.app.sub.web.controller;

import com.yzj.jep.app.sub.model.EastBatch;
import com.yzj.jep.app.sub.model.EastBatchAddVo;
import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.util.RequestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/east/batch")
public class EastBatchController {


    @Resource
    private EastBatchService eastBatchService;


    /**
     * 页面
     */
    @RequestMapping("/list")
    public String list() {
        return "sub/eastBatchList";
    }

    /**
     * 查询生成报送文件页面
     */
    @RequestMapping(value = {"/queryEastBatch"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryEASTVo(Page<EastBatch> page, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("createTime", OrderType.DESC));
        PageHelper.start(page);
        List<EastBatch> eastBatches = eastBatchService.select(filters, orders);
        PageHelper.end(page, eastBatches);
        result.put("total", page.getTotal());
        result.put("rows", page.getResult());
        return result;
    }

    /**
     * 新增页面
     */
    @RequestMapping(value = "/add")
    public String add() {
        return "sub/eastBatchAdd";
    }

    /**
     * 新增页面
     */
    @RequestMapping(value = {"/addData"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> addData(Page<EastBatchAddVo> page) {
        Map<String, Object> result = new HashMap<>();
        PageHelper.start(page);
        List<EastBatchAddVo> eastBatchAddVos = EastBatchAddVo.eastBatchAddVoList();
        PageHelper.end(page, eastBatchAddVos);
        result.put("total", page.getTotal());
        result.put("rows", page.getResult());
        return result;
    }

    /**
     * 判断日期是否为15号之后
     */
    @RequestMapping(value = "/judgeDate")
    @ResponseBody
    public Map<String, Object> judgeDate() {
        Map<String, Object> tips = new HashMap<>();
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 获取当前月份的15号
        LocalDate fifteenthOfMonth = currentDate.withDayOfMonth(15);
        // 判断当前日期是否在15号之后
        if (currentDate.isAfter(fifteenthOfMonth)) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "当前无法创建报送文件/当前无法确认报送文件！");
        } else {
            tips.put(WebCode.SUCCESS, true);
        }
        tips.put(WebCode.SUCCESS, true);
        return tips;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/remove/{id}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer id) {
        Map<String, Object> tips = new HashMap<>();
        try {
            eastBatchService.deleteByPrimaryKey(id);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, e.getMessage());
        }
        return tips;
    }

    /**
     * 确定
     */
    @RequestMapping(value = "/confirm/{id}")
    @ResponseBody
    public Map<String, Object> confirm(@PathVariable Integer id) {
        return eastBatchService.pushEast(id);
    }

    /**
     * 分发任务
     */
    @RequestMapping(value = "/distribute/{ids}")
    @ResponseBody
    public Map<String, Object> distribute(@PathVariable String ids) {
        return eastBatchService.distribute(ids);
    }


}
