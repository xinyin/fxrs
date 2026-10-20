package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
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
 * 内部人模板导出实现
 *
 * @author ke
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = QuotaExportCallbackImpl.BEANID)
public class QuotaExportCallbackImpl implements XlsExportCallback {

    @Autowired
    DictItemService dictItemService;

    @Autowired
    UnitService unitService;

    public static final String BEANID = "QuotaExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "QuotaForm.xls";

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String data = (String) parameters.get("id");
        String[] datas = data.split(",");
        String orgName = datas[0];
        String identifyType = datas[1];
        String uniscCode = datas[2];
        return "内部人模板_" + orgName + "  " + identifyType + "  " + uniscCode + sf.format(new Date()) + ".xls";
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
        Map resultMap = new HashMap();
	/*	if (1==1) {
			RelOrgService relOrgService= SpringContextHolder.getBean(RelOrgService.class);
			List<RelOrg> list=relOrgService.selectAll();
			resultMap.put("quota",list);
			return  resultMap;
		}*/
        String data = (String) parameters.get("id");
        String[] datas = data.split(",");
        String orgName = datas[0];
        String identifyType = datas[1];
        String uniscCode = datas[2];
        RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-YYYY HH:mm:ss");
        List<Filter> filter = new Filter.Builder().like("orgName", orgName).like("identifyType", identifyType).like("uniscCode", uniscCode).eq("isInsider", "1").nnl("presetLimit").build();
        List<RelOrg> list = relOrgService.select(filter);
        //将预计额度币种编码更改为名称
        List<Filter> filters1 = new Filter.Builder().eq("dictCode", "BUS-Currency").build();
        List<DictItem> dictItem = dictItemService.select(filters1);
        Map map = new HashMap();
        for (DictItem dic : dictItem) {
            map.put(dic.getItemValue(), dic.getItemLabel());
        }

        //将证件类型编码更改为名称
        List<Filter> filterList = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").build();
        List<DictItem> dictItem1 = dictItemService.select(filterList);
        Map map1 = new HashMap();
        for (DictItem dic : dictItem1) {
            map1.put(dic.getItemValue(), dic.getItemLabel());
        }

        //将法定代表人证件类型编码更改为名称
        List<Filter> fil = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").build();
        List<DictItem> dict = dictItemService.select(fil);
        Map map2 = new HashMap();
        for (DictItem dic : dict) {
            map2.put(dic.getItemValue(), dic.getItemLabel());
        }

        for (RelOrg relOrg : list) {
            //将创建时间date格式存为String
//			Date date=relOrg.getCreateTime();
            if (relOrg.getCreateTime() != null) {
                relOrg.setCreaTime(simpleDateFormat.format(relOrg.getCreaTime()));
            }
            if (relOrg.getModifiedTime() != null) {
                relOrg.setModTime(simpleDateFormat.format(relOrg.getModifiedTime()));
            }
            //将管户机构编码更改为名称
            List<Filter> filters = new Filter.Builder().eq("unitCode", relOrg.getMgerOrg()).build();
            Unit unit = unitService.get(filters);
            if (unit == null) {
                relOrg.setMgerOrg(null);
            } else {
                String unitname = unit.getUnitName();
                relOrg.setMgerOrg(unitname);
            }

            relOrg.setPresetLimitCurrency((String) map.get(relOrg.getPresetLimitCurrency()));
            relOrg.setIdentifyType((String) map1.get(relOrg.getIdentifyType()));
            relOrg.setLegalIdentifyType((String) map2.get(relOrg.getLegalIdentifyType()));


        }
        resultMap.put("quota", list);
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
