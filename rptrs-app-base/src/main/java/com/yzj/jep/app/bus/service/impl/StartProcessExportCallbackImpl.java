package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.vo.ProcInstVo;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.workflow.api.code.ProcInstStatus;
import com.yzj.jep.workflow.wf.model.HisProcInst;
import com.yzj.jep.workflow.wf.service.HisProcInstService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Service(value = StartProcessExportCallbackImpl.BEANID)
public class StartProcessExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "StartProcessExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusStartProcess.xls";

    private static final String PREFIX = "我发起的流程表_";

    private static final String SUFFIX = ".xls";

    private static final String DICTCODE = "dictCode";


    private static final String PLACEHOLDER = "%";


    HisProcInstService hisProcInstService;
    @Autowired
    DictItemService dictItemService;
    @Autowired
    private UnitService unitService;

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
        hisProcInstService = SpringContextHolder.getBean(HisProcInstService.class);
        List<ProcInstVo> procInstVoList = new ArrayList<>();
        Map resultMap = new HashMap(16);
        //字典码
        String dictCode = DICTCODE;
        //获取字典码
//        Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
        //获取查询的filter
        List<Filter> filters = builderFilter(parameters);
        List<HisProcInst> hisProcInsts = hisProcInstService.select(filters);
        if (hisProcInsts == null || hisProcInsts.size() == 0) {
            resultMap.put("hisProcInst", procInstVoList);
            return resultMap;
        }
//        构建发起的工作流的基本信息
        for (HisProcInst hisProcInst : hisProcInsts) {
           ProcInstVo procInstVo=new ProcInstVo();
            //自然人的基本信息
            builderPsnBasicInfo(procInstVo, hisProcInst, null);
            procInstVoList.add(procInstVo);
        }
        resultMap.put("hisProcInst", procInstVoList);
        return resultMap;
    }

    /**
     * @param psnInfoVo
     * @param relPsn
     * @param dictCodeMap
     * @desc 构建自然人的基本信息
     */
    public void builderPsnBasicInfo(ProcInstVo procInstVo, HisProcInst hisProcInst, Map<String, Map<String, String>> dictCodeMap) {
        procInstVo.setModuleName(hisProcInst.getModuleName());
        procInstVo.setStarterDeptName(hisProcInst.getStarterDeptName());
        procInstVo.setStartTime(hisProcInst.getStartTime());
        procInstVo.setStatus(ProcInstStatus.getLabel(hisProcInst.getStatus()));
    }

    /**
     * @param parameters
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(Map parameters) {

        String unitCode = UserProfileUtils.getUserProfile().getStaffCode();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").eq("starterCode", unitCode).build();
        String data = (String) parameters.get("id");
        //获取要导出自然人的id
        String[] ids = data.split(",");
        String name = ids[0];
        String status = ids[1];
        String startTime = ids[2];
        String endTime = ids[3];
        if (!PLACEHOLDER.equals(name)) {
            filters.add(new Filter("moduleName", Operator.LIKE, name));
        }

        if (!PLACEHOLDER.equals(status)) {
            filters.add(new Filter("status", Operator.IN, status));
        }

        if (!PLACEHOLDER.equals(startTime)) {
            filters.add(new Filter("startTime", Operator.GE,startTime));
        }

        if (!PLACEHOLDER.equals(endTime)) {
            filters.add(new Filter("startTime",Operator.LE, endTime));
        }
        return filters;
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
