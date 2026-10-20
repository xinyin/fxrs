package com.yzj.jep.app.stat.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @title: InsStatisticsService
 * @Author Whx
 * @Date: 2021/7/12 14:12
 * @Version 1.0
 * 台账
 */
public interface InsStatisticsService {
    Map statIns(Integer pageSize, Integer pageNumber, String year, String quarter, String startTime, String endTime);

    PageInfo<Map<String, Object>> sumIns(Integer pageNumber, Integer pageSize, String procType, String starterDeptName, String starterName, String status, String startTime, String endTime);
}
