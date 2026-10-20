package com.yzj.jep.app.bus.service.impl;

import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.model.TraOther;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.TraOtherService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.TraUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 内部授信类导入实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = TraOtherImportCallbackImpl.BEANID)
public class TraOtherImportCallbackImpl implements XlsImportCallback {

    @Autowired
    DictItemService dictItemService;
    @Autowired
    RelOrgService relOrgService;

    @Autowired
    RelPsnService relPsnService;

    @Autowired
    TraOtherService traOtherService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TraUtil.class);


    public static final String BEANID = "TraOtherImportCallbackImpl";

    //配置导入的模板（XML）文件
    private static final String XML_TEMP_FILE = "BusTraOtherImportMapper.xml";
    private static final String IMPORT_DATAS = "traOtherzs";

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
        /*
         * 存疑逻辑：以下逻辑未完成
         * 页面新增：(金融)产品名称；未显示是否重大关联、
         *
         * */
        XlsMessage res = new XlsMessage();
        int num = 0;
        String unitCode = null;
        String relatedName = null;
        String identNumber = null;
        String identlfyType = null;

        @SuppressWarnings("unchecked")
        List<TraOther> list = (List<TraOther>) beans.get(IMPORT_DATAS);
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        //参考新增反显功能
        Map<String, Object> tips = Maps.newHashMap();

        if (list != null && list.size() > 0) {
            //业务类型
            List<Filter> filter2 = new Filter.Builder().eq("dictCode", "BUS-OtherType").build();
            List<DictItem> dictItemList2 = dictItemService.select(filter2);
            Map bustransfer = new HashMap();
            for (DictItem dic : dictItemList2) {
                //busUseCredit.put(dic.getItemValue(), dic.getItemLabel());
                bustransfer.put(dic.getItemLabel(), dic.getItemValue());
            }

            for (TraOther traOther : list) {
                if (null == traOther.getIdentNumber()) {
                    continue;
                }

                unitCode = userProfile.getUnitCode();
                relatedName = traOther.getRelatedName();
                identNumber = traOther.getIdentNumber();
                identlfyType = traOther.getIdentlfyType();
                tips = TraUtil.getRelInfo(unitCode, identNumber, identlfyType, relatedName);

                if (null == tips) {
                    continue;
                }
                if ((Boolean) tips.get("success")) {

                    if ("PSN".equals(tips.get("relType"))) {
                        traOther.setRelId((Integer) tips.get("relId"));
                        traOther.setIdentlfyType((String) tips.get("identifyType"));
                        traOther.setIdentNumber((String) tips.get("identifyNumber"));
                        traOther.setRegulatorOth1((String) tips.get("regulatorOth1"));
                        traOther.setRelType("PSN");
                        traOther.setRelatedName((String) tips.get("name"));
                    }
                    if ("ORG".equals(tips.get("relType"))) {
                        traOther.setRelId((Integer) tips.get("relId"));
                        traOther.setIdentlfyType((String) tips.get("identifyType"));
                        traOther.setIdentNumber((String) tips.get("identifyNumber"));
                        traOther.setRegulatorOth1((String) tips.get("regulatorOth1"));
                        traOther.setRelType("ORG");
                        traOther.setRelatedName((String) tips.get("name"));
                    }
                } else {
                    //导入数据关联方信息不存在
                    continue;
                }

                List<Filter> filters = new Filter.Builder()
                        .eq("contractNo", traOther.getContractNo()).build();
                TraOther traO = traOtherService.get(filters);

                traOther.setContractSignDate(dateStr(traOther.getContractSignDate()));
                traOther.setBusinessEndTime(dateStr(traOther.getBusinessEndTime()));
                traOther.setBusinessStartTime(dateStr(traOther.getBusinessStartTime()));
                traOther.setBusinessType((String) bustransfer.get(traOther.getBusinessType()));
                traOther.setHouseManageOrg(userProfile.getUnitCode());
                traOther.setDataResource("1");
                traOther.setDelflag("1");
                traOther.setStatus("3");
                traOther.setTranType("4");
                if (null != traO) {
                    traOther.setPid(traO.getPid());
                    traOtherService.updateSelective(traOther);

                } else {
                    traOther.setTransNum(BatchNumberUtil.makeBatchNumber());//交易编号
                    traOtherService.insert(traOther);
                }
                num++;
            }
            res.setMessage("成功导入" + num);
        } else {
            //导入失败时，添加提示信息
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
        List<TraOther> list = (List<TraOther>) mapReaded.get(IMPORT_DATAS);
        //检查导入文件中是否有数据
        if (list != null && list.size() > 0) {
            //TODO 执行导入前的检查，如xxx编码是否存在，xxx引用的编码不存在等

            for (TraOther traO : list) {
                if (null == traO.getContractNo()) {
                    break;
                }
                //验证文件中是否存在证件号为空的数据
                if (null == traO.getContractNo()) {
                    result = "导入数据中存在合同编号为空";
                    break;
                }
            }
        } else {
            result = "导入数据为空。";
        }
        return result;
    }

    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXLS) throws ServiceException {
        //对导入的数据做特殊处理，如不处理则直接返回beans
        @SuppressWarnings("unchecked")
        List<TraOther> list = (List<TraOther>) beans.get(IMPORT_DATAS);

        if (CollectionUtils.isNotEmpty(list)) {
            for (TraOther traO : list) {
                if (StringUtils.isEmpty(traO.getRelatedName())) {
                    //TODO 演示对从excel读取后的数据在做导入处理前做业务处理
                    System.out.println("演示，在这里对：" + traO.getRelatedName() + "做进一步处理。");
                }
            }
        }
        return beans;
    }

    /*
     * 判断是否重大关联
     * */
    /*public boolean judgeIsImportTrade(TraCredit traCredit, String relType, Integer relId, Integer pid) {
        BigDecimal diff = null;
        BigDecimal creditAmount = BigDecimal.valueOf(traCredit.getCreditAmount());//授信金额
        if (pid != null) {
            TraCredit credit = traCreditService.get(pid);
            if ("1".equals(credit.getIsEffective())) {//是否生效
                Long creditAmount1 = credit.getCreditAmount();
                diff = creditAmount.subtract(BigDecimal.valueOf(creditAmount1));
            }
        }
        if (TraUtil.isImportantTrade(creditAmount, relId, relType, diff)) {
            traCredit.setImportantTrade("1");
            traCreditService.update(traCredit);
        } else {
            traCredit.setImportantTrade("0");
            traCreditService.update(traCredit);
        }

        return TraUtil.isImportantTrade(creditAmount, relId, relType, diff);
    }
*/
    private Date strDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (str != null && !"".equals(str.trim())) {
            return HSSFDateUtil.getJavaDate(Double.parseDouble(str));
        }
        return null;
    }

    private String dateStr(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (str != null && !"".equals(str.trim())) {
            return sdf.format(HSSFDateUtil.getJavaDate(Double.parseDouble(str)));
        }
        return null;
    }
}
