package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.bus.vo.OrgInfoVo;
import com.yzj.jep.app.bus.vo.PersonOrgVo;
import com.yzj.jep.app.bus.vo.PersonRelationVo;
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

@Service(value = SusPsnInfoExportCallbackImpl.BEANID)
public class SusPsnInfoExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "SusPsnInfoExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusRelPsnSuspect.xls";

    @Autowired
    RelPsnSuspectService relPsnSuspectService;
    @Autowired
    OrgSuspectService orgSuspectService;
    @Autowired
    RelService relService;
    @Autowired
    RelOrgService relOrgService;
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
        String fileName = "疑似自然人信息_" + sf.format(new Date()) + ".xls";
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
        String[] param = data.split(",");
        String psnName = param[0];
        String identifyType = param[1];
        String identifyNumber = param[2];
        String approvalStatus = param[3];
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").build();
        if (!"%".equals(psnName)) {
            filters.add(new Filter("name", Operator.LIKE, psnName));
        }
        if (!"%".equals(identifyType)) {
            filters.add(new Filter("identifyType", identifyType));
        }
        if (!"%".equals(identifyNumber)) {
            filters.add(new Filter("identifyNumber", Operator.LIKE, identifyNumber));
        }
        if (!"%".equals(approvalStatus)) {
            filters.add(new Filter("status", approvalStatus));
        }
        List<RelPsnSuspect> relPsnSuspectList = relPsnSuspectService.select(filters);
        List<PsnInfoVo> psnInfoVos = new ArrayList<>();
        for (int i = 0; i < relPsnSuspectList.size(); i++) {
            PsnInfoVo psnInfoVo = new PsnInfoVo();
            RelPsnSuspect relPsnSuspect = relPsnSuspectList.get(i);
            psnInfoVo.setName(relPsnSuspect.getName());
            //将证件类型编码更改为名称
            List<Filter> filterList = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").build();
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
            psnInfoVo.setIdentifyType(map1.get(relPsnSuspect.getIdentifyType()).toString());
            psnInfoVo.setIdentifyNumber(relPsnSuspect.getIdentifyNumber());
            psnInfoVo.setDomesticOrForeign(map2.get(relPsnSuspect.getDomesticOrForeign()).toString());
            //管户机构
//            if (relPsnSuspect.getMgerOrg() != null){
//                List<Filter> dictCode3 = new Filter.Builder().eq("unitCode", relPsnSuspect.getMgerOrg()).build();
//                List<Unit> dictItem3 = sysUnitService.select(dictCode3);
//                psnInfoVo.setMgerOrg(dictItem3.get(0).getUnitName());
//            }
            psnInfoVo.setRegulatorYbj(relPsnSuspect.getRegulatorYbj());
            //数据来源
            List<Filter> dictCode4 = new Filter.Builder().eq("dictCode", "BUS-DataSource").build();
            List<DictItem> dictItem4 = dictItemService.select(dictCode4);
            Map map3 = new HashMap();
            for (DictItem dic : dictItem4) {
                map3.put(dic.getItemValue(), dic.getItemLabel());
            }
            psnInfoVo.setDataSource(map3.get(relPsnSuspect.getDataSource()).toString());
            psnInfoVo.setStatus(relPsnSuspect.getStatus());
            psnInfoVo.setStatusComment(relPsnSuspect.getStatusComment());
            psnInfoVo.setIsBlack(relPsnSuspect.getIsBlack());
            psnInfoVo.setSex(relPsnSuspect.getSex());
            psnInfoVo.setRace(relPsnSuspect.getRace());
            psnInfoVo.setPoliticsStatus(relPsnSuspect.getPoliticsStatus());
            psnInfoVo.setMaritalStatus(relPsnSuspect.getMaritalStatus());
            psnInfoVo.setEducationBackground(relPsnSuspect.getEducationBackground());
            psnInfoVo.setMobile(relPsnSuspect.getMobile());
            psnInfoVo.setEmail(relPsnSuspect.getEmail());
            psnInfoVo.setResidentialAddress(relPsnSuspect.getResidentialAddress());
            psnInfoVo.setBirthdate(relPsnSuspect.getBirthdate());
            psnInfoVo.setIsShareholder(relPsnSuspect.getIsShareholder());
            psnInfoVo.setIsMainGd(relPsnSuspect.getIsMainGd());
            psnInfoVo.setCapitalNumber(relPsnSuspect.getCapitalNumber());
            psnInfoVo.setShareholdersCode(relPsnSuspect.getShareholdersCode());
            psnInfoVo.setOpenBank(relPsnSuspect.getOpenBank());
            psnInfoVo.setTradeAccount(relPsnSuspect.getTradeAccount());
            psnInfoVo.setInTime("");
            psnInfoVo.setInPrice(relPsnSuspect.getInPrice());
            psnInfoVo.setHoleShareAmount(relPsnSuspect.getHoleShareAmount());
            psnInfoVo.setShareholdingProportion(relPsnSuspect.getShareholdingProportion());
            psnInfoVo.setFrozenShareAmount(relPsnSuspect.getFrozenShareAmount());
            psnInfoVo.setPledgedShareAmount(relPsnSuspect.getPledgedShareAmount());
            psnInfoVo.setIsNoShareVote(relPsnSuspect.getIsNoShareVote());
            psnInfoVo.setVoteProportion(relPsnSuspect.getVoteProportion());
            psnInfoVo.setLimitVoteSituation(relPsnSuspect.getLimitVoteSituation());
            psnInfoVo.setNo(i + "");

            psnInfoVos.add(psnInfoVo);
        }
        resultMap.put("psnInfoVo", psnInfoVos);
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
