package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.stat.service.InsStatisticsService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = ProcessSummaryExportCallbackImpl.BEANID)
public class ProcessSummaryExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "ProcessSummaryExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusProcessSummary.xls";

    private static final String PREFIX = "流程汇总表_";

    private static final String SUFFIX = ".xls";

    @Autowired
    private InsStatisticsService insStatisticsService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = PREFIX + sf.format(new Date()) + SUFFIX;
        return fileName;
    }

    @Override
    public Map<Integer, List<int[]>> getMergeColumns() {
        return null;
    }

    @Override
    public Map<Integer, Integer> getMergeStartRowNum() {
        return null;
    }

    /**
     * 编写业务代码
     */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        /**流程状态码*/
        /**
         * 流程状态吗
         *
         *      DRAFT("草稿", "10"),
         *     RUN("运行中", "30"),
         *     REJECT("已退回", "20"),
         *     COMPLETE("已完成", "40"),
         *     SUSPEND("挂起", "50"),
         *     TERMINATE("终止", "60");
         */
        Map<String, Object> statusMap = new HashMap<>();
        statusMap.put("10", "草稿");
        statusMap.put("20", "已退回");
        statusMap.put("30", "运行中");
        statusMap.put("40", "已完成");
        statusMap.put("50", "挂起");
        statusMap.put("60", "终止");
        Map<String, Object> result = new HashMap<>();
        String params = (String) parameters.get("id");
        JSONObject jsonObject = JSONObject.parseObject(params);
        /**
         * var data={
         *             "procType":procType,
         *             "starterDeptName":starterDeptName,
         *             "starterName":starterName,
         *             "status":status,
         *             "startTime":startTime,
         *             "endTime":endTime
         *         }
         */
        String procType = jsonObject.getString("procType");
        String starterDeptName = jsonObject.getString("starterDeptName");
        String starterName = jsonObject.getString("starterName");
        String status = jsonObject.getString("status");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        PageInfo<Map<String, Object>> pageInfo = insStatisticsService.sumIns(1, Integer.MAX_VALUE, procType, starterDeptName, starterName, status, startTime, endTime);
        List<Map<String, Object>> list = pageInfo.getList();
        if (CollectionUtil.isNotEmpty(list)) {
            /**处理审批状态*/
            for (Map<String, Object> map : list) {
                map.put("status", statusMap.get((String) map.get("status")));
            }
        }
        result.put("hisProcInst", list);
        return result;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}
