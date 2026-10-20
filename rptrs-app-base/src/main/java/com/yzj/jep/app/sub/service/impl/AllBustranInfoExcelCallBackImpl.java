package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.model.InsAllbustraninfo;
import com.yzj.jep.app.sub.service.InsAllbustraninfoService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.orm.mybatis.util.StringUtil;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = AllBustranInfoExcelCallBackImpl.BEANID)
public class AllBustranInfoExcelCallBackImpl implements XlsExportCallback {
    private static Logger logger = LoggerFactory.getLogger(AllFundsExcelCallBackImpl.class);
    public static final String BEANID = "AllBustranInfoExcelCallBackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "allBusTran.xls";
    private static final String DICTCODE = "dictCode";
    @Autowired
    private InsAllbustraninfoService insAllbustraninfoService;
    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        return null;
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
        logger.info("业务逻辑开始。。。。。");
        Map resultMap = new HashMap();
        String years=parameters.get("subYear").toString();
        String quarter=parameters.get("subQuarter").toString();
        if(StringUtil.isEmpty(years)){
            Date year = new Date();
            SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
            years=sdfYear.format(year);
        }
        try {
            Date date = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateString=null ;
            switch (quarter){
                case "1":
                    //第二季度第一天才能看到第一季度的数据
                    dateString=years+"-04-01 00:00:00";
                    break;
                case "2":
                    //第三季度第一天才能看到第二季度的数据
                    dateString=years+"-07-01 00:00:00";
                    break;
                case "3":
                    //第四季度的第一天才能看到第三季度的数据
                    dateString=years+"-10-01 00:00:00";
                    break;
                case "4":
                    //下一年第一季度的第一天才能看到上一年第四季度的数据
                    dateString=(Integer.parseInt(years)+1)+"-01-01 00:00:00";
                    break;
            }
            Date date1 = sdf.parse(dateString);
            //如果当前时间小于查询季度的下季度第一天则没有数据返回页面
            if (date.getTime() < date1.getTime()) {
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Filter> filterss = new ArrayList<>();
        filterss.add(new Filter("subYear",years));
        filterss.add(new Filter("subQuarter",quarter));
        filterss.add(new Filter("status","3"));
        filterss.add(new Filter("delflag", "1"));//表示没删除的数据
        List<InsAllbustraninfo> insAllbustraninfos = insAllbustraninfoService.select(filterss);
        resultMap.put("allBustran",insAllbustraninfos);
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
