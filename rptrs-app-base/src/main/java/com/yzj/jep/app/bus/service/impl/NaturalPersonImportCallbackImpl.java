package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.NaturalInviolableRights;
import com.yzj.jep.app.bus.model.NaturalPerson;
import com.yzj.jep.app.bus.model.NaturalRel;
import com.yzj.jep.app.bus.service.NaturalInviolableRightsService;
import com.yzj.jep.app.bus.service.NaturalPersonService;
import com.yzj.jep.app.bus.service.NaturalRelService;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 自然人模板导出实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = NaturalPersonImportCallbackImpl.BEANID)
public class NaturalPersonImportCallbackImpl implements XlsImportCallback {


    public static final String BEANID = "NaturalPersonImportCallbackImpl";

    //配置导入的模板（XML）文件
    private static final String XML_TEMP_FILE = "NaturalPersonImportMapper.xml";
    private static final String IMPORT_DATAS = "naturalPersons";
    private static final String IMPORT_INVIOLABLE = "naturalInviolableRightss";
    private static final String IMPORT_NATURALRELS = "naturalRels";

    @Override
    public String getImportXmlTemplate() {
        return XLSTEMPLATE_PATH + XML_TEMP_FILE;
    }

    @Override
    public void setXlsReaderBeans(Map beans) {
        //一般而言，有几个sheet页，就有几个元素
        beans.put(IMPORT_DATAS, new ArrayList());
        beans.put(IMPORT_INVIOLABLE, new ArrayList());
        beans.put(IMPORT_NATURALRELS, new ArrayList());
    }

    @Override
    public XlsMessage executeSaveCallback(Map parameters, Map beans) throws ServiceException {
        XlsMessage res = null;
        Boolean flag1 = false;
        Boolean flag2 = false;
        Boolean flag3 = false;
        @SuppressWarnings("unchecked")
        List<NaturalPerson> list = (List<NaturalPerson>) beans.get(IMPORT_DATAS);
        List<NaturalInviolableRights> listInviolable = (List<NaturalInviolableRights>) beans.get(IMPORT_INVIOLABLE);
        List<NaturalRel> listNaturalRels = (List<NaturalRel>) beans.get(IMPORT_NATURALRELS);
        if (list != null && list.size() > 0) {
            //需要调用合适的事务方法保存导入的数据
            for (NaturalPerson entity : list) {
                NaturalPersonService naturalPersonService = SpringContextHolder.getBean(NaturalPersonService.class);
                NaturalPerson naturalPerson = new NaturalPerson();
                naturalPerson.setPid(entity.getPid());
                naturalPerson.setRegCode(entity.getRegCode());
                naturalPerson.setOrignalOrg(entity.getOrignalOrg());
                naturalPerson.setOrignalOrgName(entity.getOrignalOrgName());
                naturalPerson.setName(entity.getName());
                naturalPerson.setSex(entity.getSex());
                naturalPerson.setNationality(entity.getNationality());
                naturalPerson.setDomesticOrForeign(entity.getDomesticOrForeign());
                naturalPerson.setRace(entity.getRace());
                naturalPerson.setIdentifyType(entity.getIdentifyType());
                naturalPerson.setIdentifyNumber(entity.getIdentifyNumber());
                naturalPerson.setBirthdate(entity.getBirthdate());
                naturalPerson.setMobile(entity.getMobile());
                naturalPerson.setEmail(entity.getEmail());
                naturalPerson.setReportVersion(entity.getReportVersion());
                naturalPerson.setDutyCode(entity.getDutyCode());
                naturalPerson.setValidStatus(entity.getValidStatus());
                naturalPerson.setIsReported(entity.getIsReported());
                naturalPerson.setActiveTime(entity.getActiveTime());
                naturalPerson.setInvalidTime(entity.getInvalidTime());
                naturalPersonService.insert(naturalPerson);
                System.out.println("导入成功：" + naturalPerson.getRegCode());
            }
            flag1 = true;
            if (listInviolable != null && listInviolable.size() > 0) {
                NaturalInviolableRightsService inviolableRightsService = SpringContextHolder.getBean(NaturalInviolableRightsService.class);
                for (NaturalInviolableRights entity : listInviolable) {
                    inviolableRightsService.insert(entity);
                    System.out.println("导入成功：" + entity.getNaturalRegCode() + "权益信息");
                }
                flag2 = true;
            }
            if (listNaturalRels != null && listNaturalRels.size() > 0) {
                NaturalRelService naturalRelService = SpringContextHolder.getBean(NaturalRelService.class);
                for (NaturalRel entity : listNaturalRels) {
                    naturalRelService.insert(entity);
                }
                flag3 = true;
            }
            res = new XlsMessage();
            if (flag1 == true && flag2 == true && flag3 == true) {
                res.setMessage("成功导入" + list.size() + "个自然人信息、" + listInviolable.size() + "个权益信息、" + listNaturalRels.size() + "关系人信息");
            } else if (flag1 == true && flag2 == false && flag3 == true) {
                res.setMessage("成功导入" + list.size() + "个自然人信息、" + listNaturalRels.size() + "关系人信息");
            } else if (flag1 == true && flag2 == true && flag3 == false) {
                res.setMessage("成功导入" + list.size() + "个自然人信息、" + listInviolable.size() + "个权益信息");
            } else {
                res.setMessage("成功导入" + list.size() + "个自然人信息。");
            }
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
        List<NaturalPerson> list = (List<NaturalPerson>) mapReaded.get(IMPORT_DATAS);
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
        List<NaturalPerson> list = (List<NaturalPerson>) beans.get(IMPORT_DATAS);

        if (CollectionUtils.isNotEmpty(list)) {
            for (NaturalPerson entity : list) {
                if (StringUtils.isEmpty(entity.getRegCode())) {
                    //TODO 演示对从excel读取后的数据在做导入处理前做业务处理
                    System.out.println("演示，在这里对：" + entity.getRegCode() + "做进一步处理。");
                }
            }
        }

        return beans;
    }
}
