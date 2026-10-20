package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.vo.InsiderImportVo;
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
 * 内部交易管理导入实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = InsiderImportCallbackImpl.BEANID)
public class InsiderImportCallbackImpl implements XlsImportCallback {

    @Autowired
    DictItemService dictItemService;
    public static final String BEANID = "InsiderImportCallbackImpl";

    //配置导入的模板（XML）文件
    private static final String XML_TEMP_FILE = "InImportMapper.xml";
    private static final String IMPORT_DATAS = "insiders";

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
        List<InsiderImportVo> list = (List<InsiderImportVo>) beans.get(IMPORT_DATAS);
//		List<RelPsn> list = (List<RelPsn>) beans.get(IMPORT_DATAS);
        if (list != null && list.size() > 0) {
            //需要调用合适的事务方法保存导入的数据
            RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
//			RelPsnService relPsnService = SpringContextHolder.getBean(RelPsnService.class);
            for (InsiderImportVo entity : list) {
//				List<Filter> filters = new Filter.Builder().eq("identifyNumber",entity.getIdentifyNumber()).build();
//				List<RelPsn> relPsnList = relPsnService.select(filters);
                //将证件类型名称转编码
//				List<Filter> filterList=new Filter.Builder().eq("itemLabel",entity.getIdentifyType()).eq("dictCode","BUS-IdentifyType").build();
//				DictItem dictItem=dictItemService.get(filterList);

                //将证件类型编码更改为名称
                List<Filter> filterList = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").build();
                List<DictItem> dictItem1 = dictItemService.select(filterList);
                Map map1 = new HashMap();
                for (DictItem dic : dictItem1) {
                    map1.put(dic.getItemLabel(), dic.getItemValue());
                }

                //将境内/境外编码更改为名称
                List<Filter> fil = new Filter.Builder().eq("dictCode", "BUS-DomesticOrForeign").build();
                List<DictItem> dict = dictItemService.select(fil);
                Map map2 = new HashMap();
                for (DictItem dic : dict) {
                    map2.put(dic.getItemLabel(), dic.getItemValue());
                }
                //更改企业类型编码格式
                List<Filter> CompanyType = new Filter.Builder().eq("dictCode", "BUS-CompanyType").build();
                List<DictItem> BCompanyType = dictItemService.select(CompanyType);
                Map map3 = new HashMap();
                for (DictItem dic : BCompanyType) {
                    map3.put(dic.getItemLabel(), dic.getItemValue());
                }
                //更改机构状态编码格式
                List<Filter> Status = new Filter.Builder().eq("dictCode", "BUS-Status").build();
                List<DictItem> SStatus = dictItemService.select(Status);
                Map map4 = new HashMap();
                for (DictItem dic : SStatus) {
                    map4.put(dic.getItemLabel(), dic.getItemValue());
                }


                //将预计额度币种编码更改为名称
                List<Filter> filters1 = new Filter.Builder().eq("dictCode", "BUS-Currency").build();
                List<DictItem> dictItem = dictItemService.select(filters1);
                Map map = new HashMap();
                for (DictItem dic : dictItem) {
                    map.put(dic.getItemLabel(), dic.getItemValue());
                }

                String iShareholder = isYes(entity.getIsShareholder());
                String ismainhoder = isYes(entity.getIsmainhoder());
                String isNoShareVote = isYes(entity.getIsNoShareVote());
//				List<Filter> filters = new Filter.Builder().eq("orgName",entity.getOrgName()).eq("uniscCode",entity.getUniscCode()).eq("identifyType",dictItem.getItemValue()).eq("isInsider","1").build();
//				RelOrg relOrg = relOrgService.get(filters);
                RelOrg relOrg = new RelOrg();
                relOrg.setIsInsider("1");
                relOrg.setOrgName(entity.getOrgName());
                relOrg.setIdentifyType((String) map1.get(entity.getIdentifyType()));
                relOrg.setUniscCode(entity.getUniscCode());
                relOrg.setDomesticOrForeign((String) map2.get(entity.getDomesticOrForeign()));
                relOrg.setRegAddress(entity.getRegAddress());
                relOrg.setLegalPerson(entity.getLegalPerson());
                relOrg.setLegalIdentifyType((String) map1.get(entity.getLegalIdentifyType()));
                relOrg.setLegalIdentifyNumber(entity.getLegalIdentifyNumber());
                relOrg.setStatus((String) map4.get(entity.getStatus()));
                relOrg.setOrgType((String) map3.get(entity.getOrgType()));
                relOrg.setBusinessScope(entity.getBusinessScope());
                relOrg.setRegCapital(entity.getRegCapital());
                relOrg.setRegCapitalCurrency((String) map.get(entity.getRegCapitalCurrency()));
                relOrg.setGroupName(entity.getGroupName());
                relOrg.setMemGroup(entity.getMemGroup());
                relOrg.setIsShareholder(iShareholder);
                relOrg.setIsMainGd(ismainhoder);
                relOrg.setCapitalNumber(entity.getCapitalNumber());
                relOrg.setShareholdersCode(entity.getShareholdersCode());
                relOrg.setOpenBank(entity.getOpenBank());
                relOrg.setTradeAccount(entity.getTradeAccount());
                relOrg.setInTime(entity.getInTime());
                relOrg.setInPrice(entity.getInPrice());
                relOrg.setHoleShareAmount(entity.getHoleShareAmount());
                relOrg.setIsNoShareVote(isNoShareVote);
                relOrg.setVoteProportion(entity.getVoteProportion());
                relOrg.setLimitVoteSituation(entity.getLimitVoteSituation());

                int a = relOrgService.insert(relOrg);

                if (a == 0) {
                    index++;
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
        List<InsiderImportVo> list = (List<InsiderImportVo>) mapReaded.get(IMPORT_DATAS);

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
        List<InsiderImportVo> list = (List<InsiderImportVo>) beans.get(IMPORT_DATAS);

        if (CollectionUtils.isNotEmpty(list)) {
            for (InsiderImportVo entity : list) {
                if (StringUtils.isEmpty(entity.getOrgName())) {
                    //TODO 演示对从excel读取后的数据在做导入处理前做业务处理
                    System.out.println("演示，在这里对：" + entity.getOrgName() + "做进一步处理。");
                }
            }
        }

        return beans;
    }

    public static String isYes(String value) {
        String result = null;
        if (value == null) {
            result = null;
        } else if (value == "是") {
            result = "1";
        } else {
            result = "0";
        }
        return result;
    }
}
