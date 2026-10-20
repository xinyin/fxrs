package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.bus.vo.OrgInfoVo;
import com.yzj.jep.app.bus.vo.PsnInfoVo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.workflow.api.external.service.SysUnitService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = SusOrgInfoExportCallbackImpl.BEANID)
public class SusOrgInfoExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "SusOrgInfoExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusRelOrgSuspect.xls";

    @Autowired
    RelPsnSuspectService relPsnService;
    @Autowired
    OrgSuspectService orgSuspectService;
    @Autowired
    RelService relService;
    @Autowired
    RelOrgService relOrgService;
    @Autowired
    RelOrgSuspectService relOrgSuspectService;
    @Autowired
    DictItemService dictItemService;
    @Autowired
    SysUnitService sysUnitService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "疑似机构信息_" + sf.format(new Date()) + ".xls";
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
        //获取要导出机构的id
        String[] param = data.split(",");
        String orgName = param[0];
        String identifyType = param[1];
        String uniscCode = param[2];
        String approvalStatus = param[3];
        List<com.yzj.jep.orm.entity.Filter> filters = new Filter.Builder().eq("delflag", "1").build();
        if (!"%".equals(orgName)) {
            filters.add(new Filter("orgName", Operator.LIKE, orgName));
        }
        if (!"%".equals(identifyType)) {
            filters.add(new Filter("identifyType", identifyType));
        }
        if (!"%".equals(uniscCode)) {
            filters.add(new Filter("uniscCode", uniscCode));
        }
        if (!"%".equals(approvalStatus)) {
            filters.add(new Filter("approvalStatus", approvalStatus));
        }
        List<RelOrgSuspect> relOrgSuspectList = relOrgSuspectService.select(filters);

//        RelOrgSuspect relOrgSuspect;
        OrgInfoVo orgInfoVo;
        List<OrgSuspect> relPsnVoList = new ArrayList<>();
        List<OrgSuspect> tzOrgPsn = new ArrayList<>();
        List<OrgInfoVo> orgInfoVos = new ArrayList<>();
        //循环次数
        int size;
//        for (int i=0;i<param.length;i++){
        for (RelOrgSuspect relOrgSuspect : relOrgSuspectList) {
            relOrgSuspect = relOrgSuspectService.get(relOrgSuspect.getPid());
            //查找归属关系  含自然人
            List<OrgSuspect> relPsnVoListUp = orgSuspectService.queryOrgSubInfo(relOrgSuspect.getPid());
            relPsnVoList.addAll(relPsnVoListUp);
            // 含机构
            List<OrgSuspect> tzOrgPsnUp = orgSuspectService.getOrgInvest(relOrgSuspect.getPid());
            tzOrgPsn.addAll(tzOrgPsnUp);

            size = Math.max(relPsnVoListUp.size(), tzOrgPsnUp.size());
            if (size == 0) {
                orgInfoVo = new OrgInfoVo();
//                    BeanUtils.copyProperties(relPsn,psnInfoVo);
                orgInfoVo.setOrgName(relOrgSuspect.getOrgName());
                //将证件类型编码更改为名称
                List<Filter> filterList = new Filter.Builder().eq("dictCode", "BUS-ORGIdentifyType").build();
                List<DictItem> dictItem1 = dictItemService.select(filterList);
                Map map1 = new HashMap();
                for (DictItem dic : dictItem1) {
                    map1.put(dic.getItemValue(), dic.getItemLabel());
                }
                //境内境外
                List<Filter> dictCode2 = new Filter.Builder().eq("dictCode", "BUS-DomesticOrForeign").build();
                List<DictItem> dictItem2 = dictItemService.select(dictCode2);
                Map map2 = new HashMap();
                for (DictItem dic : dictItem2) {
                    map2.put(dic.getItemValue(), dic.getItemLabel());
                }
                orgInfoVo.setIdentifyType(map1.get(relOrgSuspect.getIdentifyType()).toString());
                orgInfoVo.setUniscCode(relOrgSuspect.getUniscCode());
                orgInfoVo.setDomesticOrForeign(map2.get(relOrgSuspect.getDomesticOrForeign()).toString());
                //管户机构
                List<Filter> dictCode3 = new Filter.Builder().eq("unitCode", relOrgSuspect.getMgerOrg()).build();
                List<Unit> dictItem3 = sysUnitService.select(dictCode3);
//                if (dictCode3.size()!=0){
//                orgInfoVo.setMgerOrg(dictItem3.get(0).getUnitName());
//                }
                orgInfoVo.setDataSource(relOrgSuspect.getDataSource());
                orgInfoVo.setStatus(relOrgSuspect.getStatus());
                orgInfoVo.setStatusComment(relOrgSuspect.getStatusComment());
                orgInfoVo.setRegAddress(relOrgSuspect.getRegAddress());
                orgInfoVo.setLegalPerson(relOrgSuspect.getLegalPerson());
                //法定代表人证件类型
                List<Filter> dictCode4 = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").build();
                List<DictItem> dictItem4 = dictItemService.select(dictCode4);
                Map map4 = new HashMap();
                for (DictItem dic : dictItem4) {
                    map4.put(dic.getItemValue(), dic.getItemLabel());
                }
                String IDType = relOrgSuspect.getLegalIdentifyType();
                if (IDType!=null){
                    orgInfoVo.setLegalIdentifytype(map4.get(IDType).toString());
                }
                orgInfoVo.setLegalIdentifyNumber(relOrgSuspect.getLegalIdentifyNumber());
                //企业类型
                List<Filter> dictCode5 = new Filter.Builder().eq("dictCode", "BUS-CompanyType").build();
                List<DictItem> dictItem5 = dictItemService.select(dictCode5);
                Map map5 = new HashMap();
                for (DictItem dic : dictItem5) {
                    map5.put(dic.getItemValue(), dic.getItemLabel());
                }
                String OrgType1 = relOrgSuspect.getOrgType();
                if (OrgType1!=null){
                    orgInfoVo.setOrgType(map5.get(OrgType1).toString());
                }
                orgInfoVo.setBusinessScope(relOrgSuspect.getBusinessScope());
                String regCapital = relOrgSuspect.getRegCapital() == null ? "0" : String.valueOf(relOrgSuspect.getRegCapital());
//                relOrgSuspect.setRegCapital(Float.parseFloat(regCapital));
                orgInfoVo.setRegCapital(regCapital);
                //注册资本币种代码
                List<Filter> dictCode6 = new Filter.Builder().eq("dictCode", "BUS-Currency").build();
                List<DictItem> dictItem6 = dictItemService.select(dictCode6);
                Map map6 = new HashMap();
                for (DictItem dic : dictItem6) {
                    map6.put(dic.getItemValue(), dic.getItemLabel());
                }
//                orgInfoVo.setRegCapitalCurrency(map6.get(relOrgSuspect.getRegCapitalCurrency()).toString());
                orgInfoVo.setGroupName(relOrgSuspect.getGroupName());
                orgInfoVo.setMemGroup(relOrgSuspect.getMemGroup());
                orgInfoVo.setIsShareholder(relOrgSuspect.getIsShareholder());
                orgInfoVo.setIsMainGd(relOrgSuspect.getIsMainGd());
                orgInfoVo.setCapitalNumber(relOrgSuspect.getCapitalNumber());
                orgInfoVo.setShareholdersCode(relOrgSuspect.getShareholdersCode());
                orgInfoVo.setOpenBank(relOrgSuspect.getOpenBank());
                orgInfoVo.setTradeAccount(relOrgSuspect.getTradeAccount());
                orgInfoVo.setInTime(relOrgSuspect.getInTime());
                orgInfoVo.setInPrice(relOrgSuspect.getInPrice());
                orgInfoVo.setHoleShareAmount(relOrgSuspect.getHoleShareAmount());
                orgInfoVo.setShareholdingProportion(relOrgSuspect.getShareholdingProportion());
                orgInfoVo.setFrozenShareAmount(relOrgSuspect.getFrozenShare());
                orgInfoVo.setPledgedShareAmount(relOrgSuspect.getPledgedShare());
                orgInfoVo.setIsNoShareVote(relOrgSuspect.getIsNoShareVote());
                orgInfoVo.setVoteProportion(relOrgSuspect.getVoteProportion());
                orgInfoVo.setLimitVoteSituation(relOrgSuspect.getLimitVoteSituation());
                orgInfoVo.setNo(relOrgSuspect.getPid().toString());
                orgInfoVos.add(orgInfoVo);
            }
            for (int j = 0; j < size; j++) {
                orgInfoVo = new OrgInfoVo();
//                    BeanUtils.copyProperties(relPsn,psnInfoVo);
                orgInfoVo.setOrgName(relOrgSuspect.getOrgName());
                orgInfoVo.setIdentifyType(relOrgSuspect.getIdentifyType());
                orgInfoVo.setUniscCode(relOrgSuspect.getUniscCode());
                orgInfoVo.setDomesticOrForeign(relOrgSuspect.getDomesticOrForeign());
                orgInfoVo.setMgerOrg(relOrgSuspect.getMgerOrg());
                orgInfoVo.setDataSource(relOrgSuspect.getDataSource());
                orgInfoVo.setStatus(relOrgSuspect.getStatus());
                orgInfoVo.setStatusComment(relOrgSuspect.getStatusComment());
                orgInfoVo.setRegAddress(relOrgSuspect.getRegAddress());
                orgInfoVo.setLegalPerson(relOrgSuspect.getLegalPerson());
                orgInfoVo.setLegalIdentifytype(relOrgSuspect.getLegalIdentifyType());
                orgInfoVo.setLegalIdentifyNumber(relOrgSuspect.getLegalIdentifyNumber());
                orgInfoVo.setStatus(relOrgSuspect.getStatus());
                orgInfoVo.setOrgType(relOrgSuspect.getOrgType());
                orgInfoVo.setBusinessScope(relOrgSuspect.getBusinessScope());
                orgInfoVo.setRegCapital(relOrgSuspect.getRegCapital() + "");
                orgInfoVo.setRegCapitalCurrency(relOrgSuspect.getRegCapitalCurrency());
                orgInfoVo.setGroupName(relOrgSuspect.getGroupName());
                orgInfoVo.setMemGroup(relOrgSuspect.getMemGroup());
                orgInfoVo.setIsShareholder(relOrgSuspect.getIsShareholder());
                orgInfoVo.setIsMainGd(relOrgSuspect.getIsMainGd());
                orgInfoVo.setCapitalNumber(relOrgSuspect.getCapitalNumber());
                orgInfoVo.setShareholdersCode(relOrgSuspect.getShareholdersCode());
                orgInfoVo.setOpenBank(relOrgSuspect.getOpenBank());
                orgInfoVo.setTradeAccount(relOrgSuspect.getTradeAccount());
                orgInfoVo.setInTime(relOrgSuspect.getInTime());
                orgInfoVo.setInPrice(relOrgSuspect.getInPrice());
                orgInfoVo.setHoleShareAmount(relOrgSuspect.getHoleShareAmount());
                orgInfoVo.setShareholdingProportion(relOrgSuspect.getShareholdingProportion());
                orgInfoVo.setFrozenShareAmount(relOrgSuspect.getFrozenShare());
                orgInfoVo.setPledgedShareAmount(relOrgSuspect.getPledgedShare());
                orgInfoVo.setIsNoShareVote(relOrgSuspect.getIsNoShareVote());
                orgInfoVo.setVoteProportion(relOrgSuspect.getVoteProportion());
                orgInfoVo.setLimitVoteSituation(relOrgSuspect.getLimitVoteSituation());
                orgInfoVo.setNo(relOrgSuspect.getPid().toString());
                if (relPsnVoListUp.size() >= (j + 1)) {
                    OrgSuspect orgSuspect = relPsnVoListUp.get(j);
                    orgInfoVo.setPsnName(orgSuspect.getName());
                    orgInfoVo.setPsnIdentifyType(orgSuspect.getIdentifyType());
                    orgInfoVo.setPsnIdentifyNumber(orgSuspect.getIdentifyNumber());
                    orgInfoVo.setPsnDomesticOrForeign("");
                    orgInfoVo.setPsnResidentialAddress("");
                    orgInfoVo.setPsnRelCategory(orgSuspect.getRelCategory());
                    orgInfoVo.setPsnRelCategorySub(orgSuspect.getRelCategorySub());
                }

                if (tzOrgPsnUp.size() >= (j + 1)) {
                    OrgSuspect org = tzOrgPsnUp.get(j);
                    orgInfoVo.setOrgOrgName(org.getOrgName());
                    orgInfoVo.setOrgIdentifyType(org.getIdentifyType());
                    orgInfoVo.setOrgUniscCode(org.getUniscCode());
                    orgInfoVo.setOrgDomesticOrForeign("");
                    orgInfoVo.setOrgRegAddress("");
                    orgInfoVo.setOrgStatus("");
                    orgInfoVo.setOrgLegalPerson("");
                    orgInfoVo.setOrgLegalIdentifytype(org.getIdentifyType());
                    orgInfoVo.setOrgLegalIdentifyNumber("");
                    orgInfoVo.setOrgRelCategory(org.getRelCategory());
                    orgInfoVo.setOrgRelCategorySub(org.getRelCategorySub());
                    orgInfoVo.setOrgShareholdingRatio(org.getShareholdingRatio() + "");
                }
                orgInfoVos.add(orgInfoVo);
            }
        }

//        }
        resultMap.put("orgInfoVo", orgInfoVos);
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
