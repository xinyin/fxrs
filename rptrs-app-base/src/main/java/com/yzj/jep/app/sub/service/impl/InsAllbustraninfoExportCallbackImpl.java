package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.ins.model.TraFundsVo;
import com.yzj.jep.app.ins.service.impl.InsTradeFundsExportCallbackImpl;
import com.yzj.jep.app.sub.model.InsAllbustraninfo;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.InsAllbustraninfoService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = InsAllbustraninfoExportCallbackImpl.BEANID)
public class InsAllbustraninfoExportCallbackImpl implements XlsExportCallback {

    public static final String BEANID = "InsAllbustraninfoExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "insAllbustraninfoExportCallbackImpl.xls";

    private static final String DICTCODE = "dictCode";

    @Autowired
    private InsAllbustraninfoService insAllbustraninfoService;

    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private SublogService sublogService;


    /**关联方类型*/
    private final Map<String, String> relType = new HashMap<>();
    /**交易方向*/
    private final Map<String, String> Direction = new HashMap<>();


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "关联交易明细表_" + sf.format(new Date()) + ".xls";
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

    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        Map resultMap = new HashMap();
        init();
        //参数
        List<Filter> filters = builderFilter(parameters);
        List<InsAllbustraninfo> insAllbustraninfoList = insAllbustraninfoService.select(filters);
        List<InsAllbustraninfo> allbustraninfoVos = new ArrayList<>();
        if (insAllbustraninfoList == null || insAllbustraninfoList.size() == 0) {
            resultMap.put("insAllbustraninfo", insAllbustraninfoList);
            return resultMap;
        }
        for (InsAllbustraninfo insAllbustraninfo : insAllbustraninfoList) {
            InsAllbustraninfo insAllbustraninfo1 = new InsAllbustraninfo();
            builderBasicInfo(insAllbustraninfo,insAllbustraninfo1);
            allbustraninfoVos.add(insAllbustraninfo1);
        }
        resultMap.put("insAllbustraninfo",allbustraninfoVos);
        return resultMap;

    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
    //初始化字典
    private void init(){
        //关联方类型
        List<Filter> filters = new Filter.Builder().eq("dictCode", "INS-relType").build();
        List<DictItem> dictItem = dictItemService.select(filters);
        for (DictItem dic : dictItem) {
            relType.put(dic.getItemValue(), dic.getItemLabel());
        }

        List<Filter> filters1 = new Filter.Builder().eq("dictCode", "INS-Tra-Direction").build();
        List<DictItem> dictItem1 = dictItemService.select(filters1);
        for (DictItem dic : dictItem1) {
            Direction.put(dic.getItemValue(), dic.getItemLabel());
        }
    }
    public List<Filter> builderFilter(Map parameters) {
        String data = (String) parameters.get("id");
        List<Filter> filters = new ArrayList<>();
        String[] ids = data.split(",");
        String pid = ids[0];
        Sublog sublog = sublogService.get(Integer.valueOf(pid));
        String businessSeq = sublog.getBatchDecimal();
        filters.add(new Filter("allbustraninfoSeq",businessSeq));
        filters = new Filter.Builder().eq("delflag", "1").build();

        return filters;
    }
    private void builderBasicInfo(InsAllbustraninfo allbustraninfoOld,InsAllbustraninfo allbustraninfoNew){
        BeanUtils.copyProperties(allbustraninfoOld, allbustraninfoNew);
        allbustraninfoNew.setRelType(relType.get(allbustraninfoOld.getRelType()));
        allbustraninfoNew.setTradingDirection(Direction.get(allbustraninfoOld.getTradingDirection()));
    }
}
