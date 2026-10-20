package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.TraCredit;
import com.yzj.jep.app.bus.service.TraCreditService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 内部授信类导入实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = TraCreditImportCallbackImpl.BEANID)
public class TraCreditImportCallbackImpl implements XlsImportCallback {

    @Autowired
    DictItemService dictItemService;
    public static final String BEANID = "TraCreditImportCallbackImpl";

    //配置导入的模板（XML）文件
    private static final String XML_TEMP_FILE = "BusTraCreditImportMapper.xml";
    private static final String IMPORT_DATAS = "traCreditzs";

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
        List<TraCredit> list = (List<TraCredit>) beans.get(IMPORT_DATAS);

        if (list != null && list.size() > 0) {

            //需要调用合适的事务方法保存导入的数据
            //  RelOrgService relOrgService=SpringContextHolder.getBean(TraCreditService.class);
           /* for (TraCredit traCredit : list) {
                //将证件类型名称转编码
                List<Filter> filterList=new Filter.Builder().eq("itemLabel",relOrg.getIdentifyType()).eq("dictCode","BUS-IdentifyType").build();
                DictItem dictItem=dictItemService.get(filterList);

                List<Filter> filters = new Filter.Builder().eq("orgName",relOrg.getOrgName()).eq("uniscCode",relOrg.getUniscCode()).eq("identifyType",dictItem.getItemValue()).eq("isInsider","1").build();
                RelOrg relOrg1 = relOrgService.get(filters);
                if(relOrg1 == null){
                    index++;
                }else {
                    relOrg.setPresetLimitCurrency( relOrg.getPresetLimitCurrency());
                    relOrg.setPresetLimit(relOrg.getPresetLimit());
                    relOrgService.update(relOrg);

                }

            }*/
            TraCreditService traCreditService = SpringContextHolder.getBean(TraCreditService.class);
            for (TraCredit traCredit : list) {


                //将证件类型名称更改为编码
                List<Filter> filterList = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").build();
                List<DictItem> dictItem1 = dictItemService.select(filterList);
                Map map1 = new HashMap();
                for (DictItem dic : dictItem1) {
                    map1.put(dic.getItemLabel(), dic.getItemValue());
                }


                //将交易类型名称转换为编码
                List<Filter> dictCode = new Filter.Builder().eq("dictCode", "BUS-TtradeType").build();
                List<DictItem> dictItem2 = dictItemService.select(dictCode);
                Map map2 = new HashMap();
                for (DictItem dic : dictItem2) {
                    map2.put(dic.getItemLabel(), dic.getItemValue());
                }

                /*业务类型*/
                List<Filter> dictCode3 = new Filter.Builder().eq("dictCode", "BUS-BusinessType").build();
                List<DictItem> dictItem3 = dictItemService.select(dictCode3);
                Map map3 = new HashMap();
                for (DictItem dic : dictItem3) {
                    map3.put(dic.getItemLabel(), dic.getItemValue());

                }
                //是否重大交易
                List<Filter> Status = new Filter.Builder().eq("dictCode", "BUS-isNo").build();
                List<DictItem> SStatus = dictItemService.select(Status);
                Map map4 = new HashMap();
                for (DictItem dic : SStatus) {
                    map4.put(dic.getItemLabel(), dic.getItemValue());
                }

                //交易机构
                List<Filter> dictCode5 = new Filter.Builder().eq("dictCode", "BUS-GHOrg").build();
                List<DictItem> dictItem5 = dictItemService.select(dictCode5);
                Map map5 = new HashMap();
                for (DictItem dictItem : dictItem5) {
                    map5.put(dictItem.getItemLabel(), dictItem.getItemValue());
                }

                //数据来源

                List<Filter> dictCode6 = new Filter.Builder().eq("dictCode", "BUS-GHOrg").build();
                List<DictItem> dictItem6 = dictItemService.select(dictCode6);
                Map map6 = new HashMap();
                for (DictItem dictItem : dictItem6) {
                    map6.put(dictItem.getItemLabel(), dictItem.getItemValue());
                }

                traCredit.setTraPriceType((String) map2.get(traCredit.getTraPriceType()));
                traCredit.setBusinessType((String) map3.get(traCredit.getBusinessType()));
                traCredit.setIdentifyType((String) map1.get(traCredit.getIdentifyType()));
                traCredit.setImportantTrade((String) map4.get(traCredit.getImportantTrade()));

                traCredit.setManageOrg((String) map5.get(traCredit.getManageOrg()));
                traCredit.setDataResource((String) map6.get(traCredit.getDataResource()));

                traCredit.setStatus("1");
                traCredit.setIsInsider("1");//是内部人
                traCredit.setDelflag("1");
                traCreditService.insert(traCredit);
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
        List<TraCredit> list = (List<TraCredit>) mapReaded.get(IMPORT_DATAS);

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
        List<TraCredit> list = (List<TraCredit>) beans.get(IMPORT_DATAS);

        if (CollectionUtils.isNotEmpty(list)) {
            for (TraCredit traCredit : list) {
                if (StringUtils.isEmpty(traCredit.getRelatedName())) {
                    //TODO 演示对从excel读取后的数据在做导入处理前做业务处理
                    System.out.println("演示，在这里对：" + traCredit.getRelatedName() + "做进一步处理。");
                }
            }
        }

        return beans;
    }
}
