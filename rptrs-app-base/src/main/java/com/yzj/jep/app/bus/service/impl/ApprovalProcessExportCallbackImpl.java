package com.yzj.jep.app.bus.service.impl;


import com.yzj.jep.app.bus.service.ApprovalProcessService;
import com.yzj.jep.app.bus.vo.ProcInstVo;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.workflow.api.code.ProcInstStatus;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.impl.engine.Activiti5WorkflowEngine;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import com.yzj.jep.workflow.impl.model.TaskInstImpl;
import com.yzj.jep.workflow.wf.model.HisProcInst;
import com.yzj.jep.workflow.wf.model.ProcInst;
import com.yzj.jep.workflow.wf.service.HisProcInstService;
import com.yzj.jep.workflow.wf.service.ProcInstService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = ApprovalProcessExportCallbackImpl.BEANID)
public class ApprovalProcessExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "ApprovalProcessExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusApprovalProcess.xls";

    private static final String PREFIX = "我的审批流程表_";

    private static final String SUFFIX = ".xls";

    private static final String DICTCODE = "dictCode";
    private static final String PLACEHOLDER = "%";

    ProcInstService procInstService;
//    HisProcInstService hisProcInstService;
    @Autowired
    DictItemService dictItemService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private ApprovalProcessService approvalProcessService;
    @Autowired
    private WorkflowEngine workflowEngine;


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
        procInstService= SpringContextHolder.getBean(ProcInstService.class);
        UserProfile user = UserProfileUtils.getUserProfile();
        if (user == null) {
            throw new ServiceException("user session may be expired.");
        }
        StaffImpl staff = new StaffImpl();
        BeanUtils.copyProperties(user, staff);
        List<ProcInstVo> procInstVoList = new ArrayList<>();
        Map resultMap = new HashMap(16);
        //字典码
        String dictCode = DICTCODE;
        //获取字典码
//        Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
        //获取查询的filter
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").build();
        List<Order> orders = buildOrders();
        List<ProcInst> procInstSum=new ArrayList<>();
        List<HisProcInst> hisProcInstSum=new ArrayList<>();
        Page page=new Page();
        page.setPageNumber(1);
        page.setPageSize(99999);
        Page page1 =workflowEngine.getRuntimeService().queryTaskInst(page, staff, filters,  orders);

        for(int i=0;i<page1.getResult().size();i++){
            ProcInst pp=new ProcInst();
            BeanUtils.copyProperties(page1.getResult().get(i),pp);
            procInstSum.add(pp);
        }
//        List<HisProcInst> procInsts2 = hisProcInstService.select(filters);
        Page page2=workflowEngine.getHistoryService().queryDoneProcInst(page, user.getStaffCode(), filters, orders);

        for(int i=0;i<page2.getResult().size();i++){
            HisProcInst p3=new HisProcInst();
            BeanUtils.copyProperties(page2.getResult().get(i),p3);
            hisProcInstSum.add(p3);
        }
        if ((hisProcInstSum == null || hisProcInstSum.size() == 0)&&(procInstSum == null || procInstSum.size() == 0)) {
            resultMap.put("proInst", procInstVoList);
            return resultMap;
        }
//        构建发起的工作流的基本信息
        for (ProcInst procInst : procInstSum) {
            ProcInstVo procInstVo=new ProcInstVo();
            //自然人的基本信息
            builderPsnBasicInfo(procInstVo, procInst, null);
            procInstVoList.add(procInstVo);
        }
        for (HisProcInst procInst : hisProcInstSum) {
            ProcInstVo procInstVo=new ProcInstVo();
            //自然人的基本信息
            builderPsnBasicInfoHis(procInstVo, procInst, null);
            procInstVoList.add(procInstVo);
        }

        resultMap.put("proInst", procInstVoList);
        return resultMap;
    }


    public void builderPsnBasicInfo(ProcInstVo procInstVo, ProcInst procInst, Map<String, Map<String, String>> dictCodeMap) {
        procInstVo.setModuleName(procInst.getModuleName());
        procInstVo.setStarterDeptName(procInst.getStarterDeptName());
        procInstVo.setStarterName(procInst.getStarterName());
        procInstVo.setStarterDeptName(procInst.getStarterDeptName());
        procInstVo.setStartTime(procInst.getStartTime());
        procInstVo.setStatus(ProcInstStatus.getLabel(procInst.getStatus()));
    }
    public void builderPsnBasicInfoHis(ProcInstVo procInstVo, HisProcInst procInst, Map<String, Map<String, String>> dictCodeMap) {
        procInstVo.setModuleName(procInst.getModuleName());
        procInstVo.setStarterDeptName(procInst.getStarterDeptName());
        procInstVo.setStarterName(procInst.getStarterName());
        procInstVo.setStarterDeptName(procInst.getStarterDeptName());
        procInstVo.setStartTime(procInst.getStartTime());
        procInstVo.setStatus(ProcInstStatus.getLabel(procInst.getStatus()));
    }

    public static List<Order> buildOrders() {
        List<Order> orders = new ArrayList();
        orders.add(new Order("startTime", OrderType.DESC));
        return orders;
    }


    /**
     * @param property1
     * @param property2
     * @param value1
     * @param value2
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(String property1, String value1, String property2, String value2) {
        if ("".equals(property2.trim())) {
            List<Filter> filters = new Filter.Builder().eq(property1, value1).build();
            return filters;
        }
        List<Filter> filters = new Filter.Builder().eq(property1, value1).eq(property2, value2).build();
        return filters;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}
