package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.bus.vo.PsnInfoVo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Service(value = sinOrBatPsnExportImpl.BEANID)
public class sinOrBatPsnExportImpl implements XlsExportCallback {

    public static final String BEANID = "sinOrBatPsnExportImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusRelPsn.xls";

    @Autowired
    DictItemService dictItemService;

    RelPsnService relPsnService;
    RelOrgService relOrgService;
    RelService relService;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return "自然人信息_" + sf.format(new Date()) + ".xls";
    }

    @Override
    public Map<Integer, List<int[]>> getMergeColumns() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<Integer, Integer> getMergeStartRowNum() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        relService = SpringContextHolder.getBean(RelService.class);

        Map resultMap = new HashMap();
        //参数
        String data = (String) parameters.get("id");
        //获取要导出自然人的id
        String[] condition = data.split(",");
        List<PsnInfoVo> psnInfoVos = new ArrayList<>();

        //证件类型
        List<Filter> filters = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").build();
        List<DictItem> dictItems = dictItemService.select(filters);
        Map map = new HashMap();
        for (DictItem da : dictItems) {
            map.put(da.getItemValue(), da.getItemLabel());
        }
        //政治面貌
        List<Filter> filters2 = new Filter.Builder().eq("dictCode", "BUS-DomesticOrForeign").build();
        List<DictItem> dictItems2 = dictItemService.select(filters2);
        Map map2 = new HashMap();
        for (DictItem da2 : dictItems2) {
            map2.put(da2.getItemValue(), da2.getItemLabel());
        }


//condition
        List<Filter> filtersData = new Filter.Builder().eq("status", "6").eq("delflag", "1").build();

        if (condition[0] != null) {
            filtersData.add(new Filter("mgerOrg", Operator.EQ, condition[0]));
        }
        if (!"undefined".equals(condition[1])) {
            filtersData.add(new Filter("name", Operator.LIKE, condition[1]));
        }
        if (condition[2] == "" || !"undefined".equals(condition[2])) {
            filtersData.add(new Filter("identifyType", Operator.EQ, condition[2]));
        }
        if (!"undefined".equals(condition[3])) {
            filtersData.add(new Filter("identifyNumber", Operator.EQ, condition[3]));
        }

        List<RelPsn> select = relPsnService.select(filtersData);
        for (RelPsn relPsn : select) {
            PsnInfoVo psnInfoVo = new PsnInfoVo();
            psnInfoVo.setName(relPsn.getName());
            psnInfoVo.setIdentifyType((String) map.get(relPsn.getIdentifyType()));
            //获取身份证号码进行截取，隐藏身份证号码，只保留后四位
            String str = relPsn.getIdentifyNumber();
            String s1 = str.substring(0, str.length() - 4);
            String s2 = str.substring(str.length() - 4);
            for (int h = 0; h < s1.length(); h++) {
                s1 = s1.replace(String.valueOf(s1.charAt(h)), "*");
            }
            psnInfoVo.setIdentifyNumber(s1 + s2);
            psnInfoVo.setDomesticOrForeign((String) map2.get(relPsn.getDomesticOrForeign()));
            psnInfoVo.setMgerOrg(relPsn.getMgerOrg());
            psnInfoVos.add(psnInfoVo);
        }
        resultMap.put("psnInfoVo", psnInfoVos);
        return resultMap;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {
        // TODO Auto-generated method stub

    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

}
