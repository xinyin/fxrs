package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.sub.model.EastGLFXX;
import com.yzj.jep.app.sub.model.EastGLGX;
import com.yzj.jep.app.sub.model.EastGljyhzb;
import com.yzj.jep.app.sub.model.EastZDGLJYMX;
import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = EastExportCallBackImpl.BEANID)
public class EastExportCallBackImpl implements XlsExportCallback {
    private static final String PREFIX_E1 = "关联方信息表_";
    private static final String PREFIX_E2 = "金融产品信息表_";
    private static final String PREFIX_E3 = "关联关系表_";
    private static final String PREFIX_E4 = "重大关联交易明细表_";
    private static final String PREFIX_E5 = "关联交易汇总表_";
    public static final String BEANID = "EastExportCallBackImpl";

    @Autowired
    EastGLJYHZService eastGljyhzbService;
    @Autowired
    EastGLFXXService eastGLFXXService;
    @Autowired
    EastGLJYHZViewService eastGljyhzbViewService;
    @Autowired
    static Configs configs;
    @Autowired
    SublogService sublogService;

    @Autowired
    private EastGLGXService eastGLGXService;
    @Autowired
    private EastZDGLJYMXService eastZDGLJYMXService;

    @Override
    public String getExportTemplate() {
        return null;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        int month1 = DateUtils.getMonth(new Date());
        String month=null;
        if(month1==1)
        {
            month=String.valueOf((DateUtils.getYear(new Date())-1))+"M12";
        }
        else {
            month=String.valueOf((DateUtils.getYear(new Date())))+"M"+(month1-1);
        }
        String date=null;
        int quarter = DateUtils.getQuarter(new Date());
        if (quarter==1)
        {
            date= String.valueOf((DateUtils.getYear(new Date())-1))+"Q4";
        }
        else {
            date=String.valueOf(DateUtils.getYear(new Date()))+"Q"+(quarter-1);
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = null;
        String subType = (String) parameters.get("subType");
        switch (subType) {
            case "E1":
                fileName = PREFIX_E1 + sf.format(new Date()) + ".xls";
                break;
            case "E2":
                fileName = PREFIX_E2 + sf.format(new Date()) + ".xls";
                break;
            case "E3":
                fileName = PREFIX_E3 + sf.format(new Date()) + ".xls";
                break;
            case "E4":
                fileName = PREFIX_E4 +month + ".xls";
                break;
            case "E5":
                fileName = PREFIX_E5  +"-"+date+ ".xls";
                break;
            default:
        }

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
     * 为Execl准备数据
     * E1为关联关系表
     * E4为重大关联交易明细表
     * E5为关联交易汇总表
     *
     * @param parameters
     * @return
     * @throws ServiceException
     */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        Map resultMap = new HashMap();
        String subType = (String) parameters.get("subType");
        if ("E5".equals(subType)) {
            List<EastGljyhzb> eastGljyhzbVos = new ArrayList<>();
           /* List<Filter> filters = new ArrayList<>();
            filters.add(Filter.eq("eastStatus", 1));
            filters.add(Filter.eq("delflag", "1"));*/
            eastGljyhzbVos = eastGljyhzbService.queryAll();
            if (eastGljyhzbVos != null || eastGljyhzbVos.size() > 0) {
                eastGljyhzbVos.forEach(it->{
                    BigDecimal je = it.getGljyje().multiply(new BigDecimal(10000));
                    it.setGljyje(je);
                });
                resultMap.put("eastGljyhzbVo", eastGljyhzbVos);
            } else {
                return null;
            }
        }
        if ("E1".equals(subType)) {
            EastGLFXX eastGLFXXVo;
            List<EastGLFXX> eastGLFXXVos = new ArrayList<>();
            List<Filter> filters = new ArrayList<>();
            filters.add(Filter.eq("eastStatus", 1));
            filters.add(Filter.eq("delflag", "1"));
            eastGLFXXVos = eastGLFXXService.select(filters);
            if (eastGLFXXVos.size() > 0 || eastGLFXXVos != null) {
                resultMap.put("eastGLFXXVo", eastGLFXXVos);
            } else {
                return null;
            }
        }

        if ("E4".equals(subType)) {
            List<EastZDGLJYMX> eastZDGLJYMXVos = new ArrayList<>();
            List<Filter> filters = new ArrayList<>();
            filters.add(Filter.eq("eastStatus", 1));
            filters.add(Filter.eq("delflag", "1"));
            eastZDGLJYMXVos = eastZDGLJYMXService.select(filters);
            if (eastZDGLJYMXVos.size() > 0) {
                eastZDGLJYMXVos.forEach(it->{
                    BigDecimal je = it.getGljyje().multiply(new BigDecimal(10000));
                    it.setGljyje(je);
                });
                resultMap.put("eastZDGLJYMXVo", eastZDGLJYMXVos);
            } else {
                return null;
            }
        }
        return resultMap;

    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}
