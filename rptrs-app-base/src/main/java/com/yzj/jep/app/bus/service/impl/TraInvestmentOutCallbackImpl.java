package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.RelOrgMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.bus.vo.OrgInfoVo;
import com.yzj.jep.app.bus.vo.PsnInfoVo;
import com.yzj.jep.app.bus.vo.RelOrgInfoVo;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.service.UnitService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.yzj.jep.orm.entity.Filter;

@Service(value = TraInvestmentOutCallbackImpl.BEANID)
public class TraInvestmentOutCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TraInvestmentOutCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "TraInvestments.xls";

    RelPsnService relPsnService;
    RelOrgService relOrgService;
    RelService relService;
    @Autowired
    private UnitService unitService;
//    @Autowired
//    private RelOrgMapper relOrgMapper;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "投资入股类模板下载_" + sf.format(new Date()) + ".xls";
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

    /*
     * 编写业务代码
     * */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        Map resultMap = new HashMap();
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
