package com.yzj.jep.app.stat.web.controller;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.stat.service.InsStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @title: InsStatisticsController
 * @Author Whx
 * @Date: 2021/7/12 9:40
 * @Version 所有流程信息统计，包括审批完成的和审批未完成的，包含所有流程定义
 */
@Controller
@RequestMapping("/stat/ins")
public class InsStatisticsController {

    @Autowired
    private InsStatisticsService insStatisticsService;

    /**
     * 点击菜单时调用
     *
     * @return
     */
    @GetMapping("/toInsView")
    public String toInsView() {
        return "stat/insList";
    }

    /**
     * 第一次调用方法时显示列表数据
     *
     * @return
     */
    @GetMapping("/statIns")
    @ResponseBody
    public Map statIns(Integer pageSize, Integer pageNumber, String year, String quarter, String startTime, String endTime) {
        Map map = insStatisticsService.statIns(pageSize, pageNumber, year, quarter, startTime, endTime);
        return map;
    }

    @GetMapping("/sumIns")
    @ResponseBody
    public Map<String, Object> sumIns(Integer pageNumber, Integer pageSize, String procType,
                                      String starterDeptName, String starterName, String status,
                                      String startTime, String endTime) {
        Map<String, Object> result = new HashMap<>();
        PageInfo<Map<String, Object>> pageInfo = insStatisticsService.sumIns(pageNumber, pageSize, procType, starterDeptName, starterName, status, startTime, endTime);
        result.put("total", pageInfo.getTotal());
        result.put("rows", pageInfo.getList());
        return result;
    }
}
