package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.vo.QuotaVo;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 内部交易管理导入实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = QuotaImportCallbackImpl.BEANID)
public class QuotaImportCallbackImpl implements XlsImportCallback {

    @Autowired
    DictItemService dictItemService;
    public static final String BEANID = "QuotaImportCallbackImpl";

    //配置导入的模板（XML）文件
    private static final String XML_TEMP_FILE = "QuotaImportMapper.xml";
    private static final String IMPORT_DATAS = "quotas";

    @Override
    public String getImportXmlTemplate() {
        return XLSTEMPLATE_PATH + XML_TEMP_FILE;
    }

    @Override
    public void setXlsReaderBeans(Map beans) {
        //一般而言，有几个sheet页，就有几个元素
        beans.put(IMPORT_DATAS, new ArrayList());
    }

    @Override
    public XlsMessage executeSaveCallback(Map parameters, Map beans) throws ServiceException {
        XlsMessage res = null;
        int index = 0;
        @SuppressWarnings("unchecked")
        List<QuotaVo> list = (List<QuotaVo>) beans.get(IMPORT_DATAS);
//		List<RelPsn> list = (List<RelPsn>) beans.get(IMPORT_DATAS);
        if (list != null && list.size() > 0) {
            //需要调用合适的事务方法保存导入的数据
            RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
//			RelPsnService relPsnService = SpringContextHolder.getBean(RelPsnService.class);
            for (QuotaVo entity : list) {
//				List<Filter> filters = new Filter.Builder().eq("identifyNumber",entity.getIdentifyNumber()).build();
//				List<RelPsn> relPsnList = relPsnService.select(filters);
                //将证件类型名称转编码
                List<Filter> filterList = new Filter.Builder().eq("itemLabel", entity.getIdentifyType()).eq("dictCode", "BUS-IdentifyType").build();
                DictItem dictItem = dictItemService.get(filterList);

                List<Filter> filters = new Filter.Builder().eq("orgName", entity.getOrgName()).eq("uniscCode", entity.getUniscCode()).eq("identifyType", dictItem.getItemValue()).eq("isInsider", "1").build();
                RelOrg relOrg = relOrgService.get(filters);
                if (relOrg == null) {
                    index++;
                } else {
                    /*List<Filter> filter = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").eq("itemLabel",entity.getIdentifyType()).build();
                    DictItem dictItem = dictItemService.get(filter);
                    String itemValue=dictItem.getItemValue();*/
//                    relOrg.setIdentifyType(itemValue);
                    relOrg.setPresetLimitCurrency(entity.getPresetLimitCurrency());
                    relOrg.setPresetLimit(entity.getPresetLimit());
                    relOrgService.update(relOrg);
                }

            }
            res = new XlsMessage();
            int size = list.size() - index;

            res.setMessage("成功导入" + size + "预计额度信息,未能导入信息" + index + "条");
        } else {
            //导入失败时，添加提示信息
            res = new XlsMessage();
            res.setMessage("导入失败：");
            res.setException("导入失败，请检查导入数据。");
        }
        return res;
    }

    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        //如果通过检查，就保持result为null，否则给出返回错误描述
        String result = null;
        @SuppressWarnings("unchecked")
        List<QuotaVo> list = (List<QuotaVo>) mapReaded.get(IMPORT_DATAS);

        if (list != null && list.size() > 0) {
            //TODO 执行导入前的检查，如xxx编码是否存在，xxx引用的编码不存在等


        } else {
            result = "导入数据为空。";
        }
        return result;
    }

    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXLS) throws ServiceException {
        //对导入的数据做特殊处理，如不处理则直接返回beans
        @SuppressWarnings("unchecked")
        List<QuotaVo> list = (List<QuotaVo>) beans.get(IMPORT_DATAS);

        if (CollectionUtils.isNotEmpty(list)) {
            for (QuotaVo entity : list) {
                if (StringUtils.isEmpty(entity.getOrgName())) {
                    //TODO 演示对从excel读取后的数据在做导入处理前做业务处理
                    System.out.println("演示，在这里对：" + entity.getOrgName() + "做进一步处理。");
                }
            }
        }

        return beans;
    }
}
