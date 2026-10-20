package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.sys.util.UserProfileUtils;
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
import java.util.stream.Collectors;

/**
 * @author llc
 */
@Service(value = ProInfoExportCallbackImpl.BEANID)
public class ProInfoExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "ProInfoExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusRelPro.xls";

    RelProService relProService;

    @Autowired
    DictItemService dictItemService;

    private static final String PREFIX = "金融产品关系_";

    private static final String SUFFIX = ".xls";

    private static final String PLACEHOLDER = "%";

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
        relProService = SpringContextHolder.getBean(RelProService.class);

        Map resultMap = new HashMap(16);
        //字典查询
        Map<String, Map<String, String>> dictCode = obtainDictInfo("dictCode");
        List<RelProOutVo> relProOutVos = new ArrayList<>();
        //参数
        String data = (String) parameters.get("id");
        List<Filter> filters = null;
        //获取要导出自然人的id
        String[] ids = data.split(",");
        //查询产品信息
        //产品名称
        String proName = ids[0];
        //产品分类
        String proType = ids[1];
        //产品备案号
        String effectRelation = ids[2];
        String approval = ids[3];
        String gesmodifiedTime = ids[4];
        //String lesmodifiedTime = ids[5];
        //管护机构名称
        String mgerName = UserProfileUtils.getUserProfile().getUnitName();
        filters = new Filter.Builder().eq("delflag", "1").build();
        if (!PLACEHOLDER.equals(proName)) {
            filters.add(new Filter("proName", Operator.LIKE, proName));
        }
        if (!PLACEHOLDER.equals(proType)) {
            filters.add(new Filter("proType", Operator.LIKE, proType));
        }
        if (!PLACEHOLDER.equals(approval)) {
            filters.add(new Filter("proProvider", Operator.LIKE, approval));
        }
        if ((!PLACEHOLDER.equals(effectRelation))) {
            filters.add(new Filter("effectRelation", Operator.LIKE, effectRelation));
        }
        if ((!PLACEHOLDER.equals(gesmodifiedTime))) {
            filters.add(new Filter("modifiedTime", Operator.GE, gesmodifiedTime));
        }
//        if ((!PLACEHOLDER.equals(lesmodifiedTime))) {
//            filters.add(new Filter("modifiedTime", Operator.LE, lesmodifiedTime));
//   }
        List<RelPro> relPros = relProService.select(filters);
        for (RelPro pro : relPros) {
            //产品的基本信息
            RelProOutVo proOutVo = builderExportVo(pro, mgerName, dictCode);
            relProOutVos.add(proOutVo);
        }
//        }
        resultMap.put("relProVoS", relProOutVos);
        return resultMap;
    }

    /**
     * @param dictCode
     * @return
     * @desc 获取字典信息
     */
    public Map<String, Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        //产品分类
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_PRO.getCode()));
        Map proMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("proMap", proMap);
        //境内外标识
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_DOMESTICORFOREIGN.getCode()));
        Map domesticOrForeignMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("domesticOrForeignMap", domesticOrForeignMap);
        //机构证件号码
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ORGIDENTIFYTYPE.getCode()));
        Map orgIdentMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("orgIdentMap", orgIdentMap);
        //机构状态
        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ORGANIZATIONSTATUS.getCode()));
        Map statusMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("statusMap", statusMap);
        //关联关系类型
        List<DictItem> dictItems8 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_RELCATEGORY.getCode()));
        Map relCategoryMap = dictItems8.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("relCategoryMap", relCategoryMap);
        //关系
        List<DictItem> dictItems9 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_REL.getCode()));
        Map relMap = dictItems9.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("relMap", relMap);
        //是否有效
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_VALID.getCode()));
        Map validMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("validMap", validMap);
        //是否对前一层持有
        List<DictItem> dictItems6 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.WHETHERTOSHARE.getCode()));
        Map whetherToShareMap = dictItems6.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("WhetherToShareMap", whetherToShareMap);
        return dictCodeMap;
    }

    /**
     * @param property1
     * @param value1
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(String property1, String value1) {
        List<Filter> filters = new Filter.Builder().eq(property1, value1).build();
        return filters;
    }

    /**
     * @param relPro
     * @param mgerName
     * @param dictCode
     * @return
     * @desc 构建关系未查出的产品信息
     */
    public RelProOutVo builderExportVo(RelPro relPro, String mgerName, Map<String, Map<String, String>> dictCode) {
        //产品的基本信息
        RelProOutVo outVo = new RelProOutVo();
        outVo.setProName(relPro.getProName());
        outVo.setProType(dictCode.get("proMap").get(relPro.getProType()));
        outVo.setProProvider(relPro.getProProvider());
        outVo.setProCode(relPro.getProCode());
        outVo.setProRegOrg(relPro.getProRegOrg());
        outVo.setAdName(relPro.getAdName());
        outVo.setAdCreditCode(relPro.getAdCreditCode());
        outVo.setCareName(relPro.getCareName());
        outVo.setCareCreditCode(relPro.getCareCreditCode());
        outVo.setMgerName(mgerName);
        outVo.setApprovalStatus(relPro.getApprovalStatus());
        return outVo;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}
