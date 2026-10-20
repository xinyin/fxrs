package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.model.RelatedRelation;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sub.vo.PronInfoVo;
import com.yzj.jep.app.sub.model.FinProduct;
import com.yzj.jep.app.sub.model.Template;
import com.yzj.jep.app.sub.service.TemplateService;
import com.yzj.jep.app.sub.vo.RelateInfoVo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.service.CRUDService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value= RelateInfoExportCallbackImpl.BEANID)
public class RelateInfoExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "RelateInfoExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusRelRelate.xls";

    @Autowired
    private TemplateService templateService;

    @Autowired
    private SublogService sublogService;


    @Override
    public String getExportTemplate() {
        return  XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName="RelateAtlas_"+sf.format(new Date())+".xls";
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
        List<Filter> filters = new ArrayList<Filter>();
        List<Filter> filter = new ArrayList<Filter>();
        RelateInfoVo relateInfoVo;
        Sublog sublog;
        List<RelateInfoVo> proInfoVos=new ArrayList<>();
        //参数
        String data=(String) parameters.get("id");
        sublog = sublogService.get(Integer.valueOf(data));
        sublog.setDownStatus("0");
        sublogService.update(sublog);
        filters.add(new Filter("subType", sublog.getSubType()));
        Template template = templateService.get(filters);
        String service = template.getService();
        CRUDService crudService = SpringContextHolder.getBean(service);
        filter.add(Filter.eq("status", 3));
        //关联关系图谱
            List<RelatedRelation> list = crudService.select(filter);
            for (int i=0;i<list.size();i++) {
                relateInfoVo = new RelateInfoVo();
                relateInfoVo.setActiveTime(list.get(i).getActiveTime());
                relateInfoVo.setEntityName(list.get(i).getEntityName());
                relateInfoVo.setEntityType(list.get(i).getEntityType());
                relateInfoVo.setEntityUniscUode(list.get(i).getEntityUniscUode());
                relateInfoVo.setHoldingPercent(list.get(i).getHoldingPercent());
                relateInfoVo.setInvalidTime(list.get(i).getInvalidTime());
                relateInfoVo.setIsShareHolding(list.get(i).getIsShareHolding());
                relateInfoVo.setRegulator(list.get(i).getRegulator());
                relateInfoVo.setRelationInfo(list.get(i).getRelationInfo());
                relateInfoVo.setRelationType(list.get(i).getRelationType());
                relateInfoVo.setSuperEntityName(list.get(i).getSuperEntityName());
                relateInfoVo.setSuperEntityType(list.get(i).getSuperEntityType());
                relateInfoVo.setSuperEntityUniscCode(list.get(i).getSuperEntityUniscCode());
                relateInfoVo.setValidStatus(list.get(i).getValidStatus());
                proInfoVos.add(relateInfoVo);

            }
        resultMap.put("proInfoVo",proInfoVos);
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
