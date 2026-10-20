package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelOrgVo;
import com.yzj.jep.app.bus.model.RelPsnVo;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.bus.vo.OrgInfoVo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.Dict;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.service.ApiListing;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 内部人模板导出实现
 *
 * @author ke
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = sinOrBatOrgExportImpl.BEANID)
public class sinOrBatOrgExportImpl implements XlsExportCallback {

    public static final String BEANID = "sinOrBatOrgExportImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusRelOrg.xls";

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
        return "机构信息_" + sf.format(new Date()) + ".xls";
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
        //获取要导出机构的id
        String[] condition = data.split(",");


        List<RelPsnVo> relPsnVoList = new ArrayList<>();
        ;
        List<RelOrgVo> tzOrgPsn = new ArrayList<>();
        List<OrgInfoVo> orgInfoVos = new ArrayList<>();

        //证件类型
        List<Filter> filters = new Filter.Builder().eq("dictCode", "BUS-ORGIdentifyType").build();
        List<DictItem> dictItems = dictItemService.select(filters);
        Map map = new HashMap();
        for (DictItem da : dictItems) {
            map.put(da.getItemValue(), da.getItemLabel());
        }
        //境内外标识
        List<Filter> filters1 = new Filter.Builder().eq("dictCode", "BUS-DomesticOrForeign").build();
        List<DictItem> dictItems1 = dictItemService.select(filters1);
        Map map1 = new HashMap();
        for (DictItem da1 : dictItems1) {
            map1.put(da1.getItemValue(), da1.getItemLabel());
        }
        //管户机构
        List<Filter> filters2 = new Filter.Builder().eq("dictCode", "BUS_GHOrg").build();
        List<DictItem> dictItems2 = dictItemService.select(filters2);
        Map map2 = new HashMap();
        for (DictItem da2 : dictItems2) {
            map2.put(da2.getItemValue(), da2.getItemLabel());
        }
        //机构状态
        List<Filter> filters5 = new Filter.Builder().eq("dictCode", "BUS-Status").build();
        List<DictItem> dictItems5 = dictItemService.select(filters5);
        Map map5 = new HashMap();
        for (DictItem da5 : dictItems5) {
            map5.put(da5.getItemValue(), da5.getItemLabel());
        }

        List<Filter> filtersData = new Filter.Builder().eq("approvalStatus", "6").eq("delflag", "1").build();

        if (condition[0] != null) {
            filtersData.add(new Filter("mgerOrg", Operator.EQ, condition[0]));
        }
        if (!"undefined".equals(condition[1])) {
            filtersData.add(new Filter("orgName", Operator.LIKE, condition[1]));
        }
        if (condition[2] == "" || !"undefined".equals(condition[2])) {
            filtersData.add(new Filter("identifyType", Operator.EQ, condition[2]));
        }
        if (!"undefined".equals(condition[3])) {
            filtersData.add(new Filter("uniscCode", Operator.EQ, condition[3]));
        }
        List<RelOrg> select = relOrgService.select(filtersData);
        //循环次数
        int size;
        for (RelOrg relOrg : select) {
            OrgInfoVo orgInfoVo = new OrgInfoVo();
            orgInfoVo.setOrgName(relOrg.getOrgName());
            orgInfoVo.setIdentifyType((String) map.get(relOrg.getIdentifyType()));
            orgInfoVo.setUniscCode(relOrg.getUniscCode());
            orgInfoVo.setDomesticOrForeign((String) map1.get(relOrg.getDomesticOrForeign()));
            orgInfoVo.setMgerOrg((String) map2.get(relOrg.getMgerOrg()));
            orgInfoVos.add(orgInfoVo);

    }
        resultMap.put("orgInfoVo",orgInfoVos);
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
