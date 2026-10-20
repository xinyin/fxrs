package com.yzj.jep.app.pub.service.impl;

import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.pub.model.PubQuarterVo;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.PublogQuarterService;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(value = PublogQuarterExcelCallBackMImpl.BEANID)
public class PublogQuarterExcelCallBackMImpl implements XlsExportCallback {

    private static Logger logger = LoggerFactory.getLogger(PublogQuarterExcelCallBackMImpl.class);

    public static final String BEANID = "PublogQuarterExcelCallBackMImpl";

    private static final String EXPORT_TEMPLATE_NAME = "PublogQuarter1.xls";
    private static final String DICTCODE = "dictCode";

    @Autowired
    private PublogService publogService;
    @Autowired
    private PublogQuarterService publogQuarterService;

    @Autowired
    private DictItemService dictItemService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    public String setExportTemplate(Integer fileId) {
        return null;

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
        List<PubQuarterVo> listPub = new ArrayList<PubQuarterVo>();
        //字典码
        String dictCode = DICTCODE;
        //获取字典码
        Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
        //获取参数
        String data = (String) parameters.get("data");
        String[] param = data.split(",");
        String years = param[1];
        String quarter = param[2];
        String pubQuarterge = null;
        String pubQuarterle = null;
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(years) && StringUtils.isNotBlank(quarter)) {
            switch (quarter) {
                case "1":
                    pubQuarterge = years + "-01-01";
                    pubQuarterle = years + "-03-31";
                    map.put("startTime", pubQuarterge);
                    map.put("endTime", pubQuarterle);
                    resultMap.put("quarter", "一");
                    break;
                case "2":
                    pubQuarterge = years + "-04-01";
                    pubQuarterle = years + "-06-30";
                    map.put("startTime", pubQuarterge);
                    map.put("endTime", pubQuarterle);
                    resultMap.put("quarter", "二");
                    break;
                case "3":
                    pubQuarterge = years + "-07-01";
                    pubQuarterle = years + "-09-30";
                    map.put("startTime", pubQuarterge);
                    map.put("endTime", pubQuarterle);
                    resultMap.put("quarter", "三");
                    break;
                default:
                    pubQuarterge = years + "-10-01";
                    pubQuarterle = years + "-12-31";
                    map.put("startTime", pubQuarterge);
                    map.put("endTime", pubQuarterle);
                    resultMap.put("quarter", "四");
                    break;
            }
            logger.info("根据年度和季度查询所匹配的数据开始。。。。");
            List<PublogRegularVo> publogRegularVos = new ArrayList<>();
            //根据年度和季度查询所匹配的数据
            publogRegularVos = publogQuarterService.getQuarterList1(map);
            for (int i = 0; i < publogRegularVos.size(); i++) {
                publogRegularVos.get(i).setNum(i);
                publogRegularVos.get(i).setTranType(dictCodeMap.get("tranType").get(publogRegularVos.get(i).getTranType()));
            }
            resultMap.put("publog", publogRegularVos);
            resultMap.put("year", years);

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
     * @param dictCode
     * @return
     * @desc 获取字典信息
     */
    public Map<String, Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        //交易类型
        List<DictItem> dictItems0 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_TRANTYPE.getCode()));
        Map tranTypeMap = dictItems0.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("tranType", tranTypeMap);
        return dictCodeMap;
    }
}
