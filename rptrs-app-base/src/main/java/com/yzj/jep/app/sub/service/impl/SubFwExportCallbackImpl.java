package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.model.MidFwlgljyxyxxb;
import com.yzj.jep.app.sub.service.MidFwlgljyxyxxbService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = SubFwExportCallbackImpl.BEANID)
public class SubFwExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "SubFwExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "SubFwExportTemplate.xls";

    private static final String DICTCODE = "dictCode";

    @Autowired
    private MidFwlgljyxyxxbService midFwlgljyxyxxbService;

    @Autowired
    DictItemService dictItemService;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "服务类交易信息_" + sf.format(new Date()) + ".xls";
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

        //参数
        List<Filter> filters = builderFilter(parameters);
        List<MidFwlgljyxyxxb> select = midFwlgljyxyxxbService.select(filters);

        if (select == null || select.size() == 0) {
            resultMap.put("east", select);
            return resultMap;
        }
//        for (TraFunds traFunds : traFundssList) {
//            TraFundsVo traFundsVo = new TraFundsVo();
//            builderPsnBasicInfo(traFundsVo,traFunds, dictCodeMap);
//            traFundsVos.add(traFundsVo);
//        }
        resultMap.put("east", select);
        return resultMap;
    }

    public List<Filter> builderFilter(Map parameters) {
        String data = (String) parameters.get("id");
        List<Filter> filters = new ArrayList<>();
        String[] ids = data.split(",");
        String type = ids[0];

        filters = new Filter.Builder().eq("delflag", "1").build();
        if (!"%".equals(type.trim())) {
            filters.add(new Filter("batchno", type));
        }

        return filters;
    }





    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }

    private String selectByValue(String value, String simpleName) {
        try {
            List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
            List<DictItem> list = dictItemService.select(filters);
            //过滤掉不要的字典项
            if (!CollectionUtils.isEmpty(list)) {
                for (DictItem dictItem : list) {
                    if (dictItem.getItemValue().equals(value)) {
                        return dictItem.getItemLabel();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
