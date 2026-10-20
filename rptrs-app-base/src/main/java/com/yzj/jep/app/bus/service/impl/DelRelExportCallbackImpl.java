package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.DelRel;
import com.yzj.jep.app.bus.service.DelRelService;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.util.TraUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解除关联关系库
 */
@Service(value = DelRelExportCallbackImpl.BEANID)
public class DelRelExportCallbackImpl implements XlsExportCallback {

    public static final String BEANID = "DelRelExportCallbackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "BusDelRel.xls";

    @Autowired
    RelPsnService relPsnService;
    @Autowired
    RelOrgService relOrgService;
    @Autowired
    private DelRelService delRelService;
    @Autowired
    private UnitService unitService;
    @Autowired
    DictItemService dictItemService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "解除关联关系库_" + sf.format(new Date()) + ".xls";
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
        String data = (String) parameters.get("id");
        //获取导出的ID集合
        String[] ids = data.split(",");
        String name = ids[0];//关联关系名称
        String identifyNumber = ids[1];//证件号码
        String type = ids[2];//关联关系类别

        List<Filter> filters = new Filter.Builder().build();
        if (StringUtils.isNotBlank(name) && !"undefined".equals(name)) {
            filters.add(new Filter("name", Operator.LIKE, name));
        }
        if (StringUtils.isNotBlank(identifyNumber) && !"undefined".equals(identifyNumber)) {
            filters.add(new Filter("identifyNumber", identifyNumber));
        }
        if (StringUtils.isNotBlank(type) && !"undefined".equals(type)) {
            filters.add(new Filter("type", type));
        }
        //获取符合条件的数据集合
        List<DelRel> delRelList = delRelService.select(filters);

        //关联方证件类型
        List<Filter> filters1 = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").build();
        List<DictItem> dictItems1 = dictItemService.select(filters1);
        Map idTypeMap = new HashMap();
        for (DictItem dic : dictItems1) {
            idTypeMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        //境内外标识
        List<Filter> filters2 = new Filter.Builder().eq("dictCode", "BUS-DomesticOrForeign").build();
        List<DictItem> dictItems2 = dictItemService.select(filters2);
        Map foreignMap = new HashMap();
        for (DictItem dic : dictItems2) {
            foreignMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        List<Filter> filtersType = new Filter.Builder().eq("dictCode", "BUS-OrgOrPsn").build();
        List<DictItem> dictItemsType= dictItemService.select(filtersType);
        Map typeMap = new HashMap();
        for (DictItem dic : dictItemsType) {
            typeMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        List<Filter> filters3 = new Filter.Builder().eq("dictCode", "BUS-ORGIdentifyType").build();
        List<DictItem> dictItems3 = dictItemService.select(filters3);
        Map orgIdTypeMap = new HashMap();

        // 获取金融产品产品类型字典
        List<DictItem> proTypeItemList = TraUtil.getDictItems("BUS-pro");
        for (DictItem dic : dictItems3) {
            orgIdTypeMap.put(dic.getItemValue(), dic.getItemLabel());
        }

        for (DelRel delRel : delRelList) {
            //境内外
            String domesticOrForeign = delRel.getDomesticOrForeign();
            domesticOrForeign = String.valueOf(foreignMap.get(domesticOrForeign));
            delRel.setDomesticOrForeign(domesticOrForeign);
            //联方证件
            String identifyType = delRel.getIdentifyType();

            //自然人类型脱敏处理
            String relType = delRel.getType();
//            String number = delRel.getIdentifyNumber();
            if (relType.equals("PSN")) {
//                String substring = number.substring(number.length() - 4, number.length());
//                delRel.setIdentifyNumber("*****" + substring);
                if (delRel.getIdentifyType().equals("1")) {
                    delRel.setIdentifyNumber(idEncrypt(delRel.getIdentifyNumber()));
                }else{
                    delRel.setIdentifyNumber(otherIdEncrypt(delRel.getIdentifyNumber()));
                }
                identifyType = String.valueOf(idTypeMap.get(identifyType));
                delRel.setIdentifyType(identifyType);
                relType= String.valueOf(typeMap.get(relType));
                delRel.setType(relType);
            }
            if(relType.equals("ORG")){
                identifyType = String.valueOf(orgIdTypeMap.get(identifyType));
                delRel.setIdentifyType(identifyType);
                relType= String.valueOf(typeMap.get(relType));
                delRel.setType(relType);
            }
            if ("PRO".equals(relType)) {
                // 金融产品类型转换
               identifyType = TraUtil.getIdentifyType(proTypeItemList, delRel.getIdentifyType());
                delRel.setIdentifyType(identifyType);
                relType= String.valueOf(typeMap.get(relType));
                delRel.setType(relType);
                delRel.setDomesticOrForeign("-");
            }
        }
        resultMap.put("delRelVO", delRelList);
        return resultMap;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }

    //身份证前六后四脱敏
    public static String idEncrypt(String id) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(id)) {
            return id;
        }

        if (id.length() <= 10) {
            return id.replaceAll("(?<=.).(?=.)", "*");
        }
        return id.replaceAll("(?<=\\w{6})\\w(?=\\w{4})", "*");
    }
    //自然人非身份证脱敏
    public static String otherIdEncrypt(String id) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{2})", "*");
    }

}
