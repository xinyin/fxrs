package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.ins.model.TraOther;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.bus.vo.PsnInfoVo;
import com.yzj.jep.app.ins.model.TraOther;
import com.yzj.jep.app.ins.model.TraRelInfo;
import com.yzj.jep.app.ins.service.BaseService;
import com.yzj.jep.app.ins.service.TraOtherService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;


/**
 * 内部交易管理导入实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = InsTraOtherImportCallbackImpl.BEANID)
public class InsTraOtherImportCallbackImpl implements XlsImportCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsTraOtherImportCallbackImpl.class);

    @Autowired
    DictItemService dictItemService;

    @Autowired
    BaseService baseService;

    @Autowired
    RootOrgInfoService rootOrgInfoService;

    public static final String BEANID = "InsTraOtherImportCallbackImpl";

    /**
     * 配置导入的模板（XML）文件
     */
    private static final String XML_TEMP_FILE = "InsTraOtherImportMapper.xml";
    private static final String IMPORT_DATAS = "traOther";

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
        int index = 0;
        List<TraOther> list = (List<TraOther>) beans.get(IMPORT_DATAS);
        String falNum = "";
        String resMsg = "";

        //业务类型 INT-InsBusinessType
        List<Filter> filter1 = new Filter.Builder().eq("dictCode", "INT-InsBusinessType").build();
        List<DictItem> dictItemList1 = dictItemService.select(filter1);
        Map insBusinessTypeMap = new HashMap();
        for (DictItem dic : dictItemList1) {
            insBusinessTypeMap.put(dic.getItemLabel(), dic.getItemValue());
        }

        //交易是否国规  IsStatusDict
        List<Filter> filter2 = new Filter.Builder().eq("dictCode", "IsStatusDict").build();
        List<DictItem> dictItemList2 = dictItemService.select(filter2);
        Map isStatusDictMap = new HashMap();
        for (DictItem dic : dictItemList2) {
            isStatusDictMap.put(dic.getItemLabel(), dic.getItemValue());
        }

        //是0   否1
        List<Filter> filter3 = new Filter.Builder().eq("dictCode", "BUS-isOrNo").build();
        List<DictItem> dictItemList3 = dictItemService.select(filter3);
        Map isNoMap = new HashMap();
        for (DictItem dic : dictItemList3) {
            isNoMap.put(dic.getItemLabel(), dic.getItemValue());
        }
        if (CollectionUtils.isNotEmpty(list)) {
            //需要调用合适的事务方法保存导入的数据
            TraOtherService traOtherService = SpringContextHolder.getBean(TraOtherService.class);
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            String unitCode = userProfile.getUnitCode();
            List<Filter> filterList = new ArrayList<>();
            filterList.add(new Filter("delflag", "1"));
            int year = DateUtil.year(new Date());
            filterList.add(new Filter("years", String.valueOf(year - 1)));
            filterList.add(new Filter("quarter", "4"));
            filterList.add(new Filter("approvalStatus", "6"));
            List<Order> orders = new ArrayList<>();
            orders.add(new Order("modifiedTime", OrderType.DESC));
            List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filterList);
            String lastYearNetAsset = "0";
            if (CollectionUtil.isNotEmpty(rootOrgInfos)) {
                lastYearNetAsset = String.valueOf(rootOrgInfos.get(0).getNetCaptial());
                //上年度末总资产
            }
            BigDecimal lastYearNetAssetBD = new BigDecimal(lastYearNetAsset).setScale(6, BigDecimal.ROUND_HALF_UP);
            for (int i = 0; i < list.size(); i++) {
                TraOther traOther = list.get(i);
                try {
                    if (traOther.getPid()==null) {
                        index++;
                        continue;
                    }
                    Map<String, Object> relInfo = baseService.getRelInfo(traOther.getRelNumber(), traOther.getRelName());
                    boolean flag = (boolean) relInfo.get("success");
                    if (!flag) {
                        falNum += i + 1 + ",";
                        resMsg += "第" + (i + 1) + "条此关联方不存在,";
                        index++;
                        continue;
                    }
                    TraOther other = new TraOther();
                    BeanUtils.copyProperties(traOther, other);

                    if (StringUtils.isNotBlank(traOther.getBusStartTimeString())) {
                        other.setBusStartTime(DateUtils.parseDate(traOther.getBusStartTimeString().trim()));
                    }
                    if (StringUtils.isNotBlank(traOther.getBusEndTimeString())) {
                        other.setBusEndTime(DateUtils.parseDate(traOther.getBusEndTimeString().trim()));
                    }
                    if (StringUtils.isNotBlank(traOther.getTranDateString())) {
                        other.setTranDate(DateUtils.parseDate(traOther.getTranDateString().trim()));
                    }
//                    String caliber = relInfo.get("caliber").toString();
                    String relType = trimStr(relInfo.get("relType"));
                    Integer relId = Integer.valueOf(trimStr(relInfo.get("relId"))) ;
                    BigDecimal totolTradeAmount = (BigDecimal) relInfo.get("totalTradeAmount");
                    String relName = trimStr(relInfo.get("relName"));
                    String relNumber = trimStr(relInfo.get("relNumber"));
                    String certType = trimStr(relInfo.get("certType"));
//                    BigDecimal totalTradeAmount = (BigDecimal) relInfo.get("totalTradeAmount");
                    BigDecimal tradeAmount = (BigDecimal) relInfo.get("tradeAmount");

                    TraRelInfo traRelInfo = new TraRelInfo();
                    traRelInfo.setRelType(relType);
                    traRelInfo.setRelName(relName);
                    traRelInfo.setRelNumber(relNumber);
                    traRelInfo.setRelId(relId);
                    traRelInfo.setCertType(certType);
                    traRelInfo.setTranOpponent(traOther.getTranOpponent());
                    traRelInfo.setTotolTradeAmount(totolTradeAmount);
                    traRelInfo.setTradeAmount(tradeAmount);

                    List<TraRelInfo> list1 = new ArrayList<>();
                    list1.add(traRelInfo);
                    String s = JSONObject.toJSONString(list1);
                    other.setRelInfoListStr(s);

                    other.setManagerOrg(traOther.getManagerOrg());//管理机构名称
                    other.setTranName(traOther.getTranName());//交易名称
                    other.setIsHoldingSubsidiary((String) isNoMap.get(traOther.getIsHoldingSubsidiary()));//发起方是否控股子公司
                    other.setHoldingSubsidiaryName(traOther.getHoldingSubsidiaryName());//发起方公司全称
                    other.setHoldingSubsidiaryType(traOther.getHoldingSubsidiaryType());//发起方公司证件类型
                    other.setHoldingSubsidiaryNumber(traOther.getHoldingSubsidiaryNumber());//发起方公司证件号码
                    if ("1".equals(traOther.getIsHoldingSubsidiary())) {
                        if (StringUtils.isEmpty(traOther.getHoldingSubsidiaryName()) ||
                                StringUtils.isEmpty(traOther.getHoldingSubsidiaryType()) ||
                                StringUtils.isEmpty(traOther.getHoldingSubsidiaryNumber())) {
                            other.setStatus("1");
                        } else {
                            other.setStatus("3");
                        }
                    }
                    other.setRelName(relName);//关联方名称
                    other.setRelNumber(relNumber);//关联方证件号码
                    other.setTranType("03");//交易类型
                    other.setBusType((String) insBusinessTypeMap.get(trimStr(traOther.getBusType())));//业务类型
                    other.setContractNo(trimStr(traOther.getContractNo()));//合同编号
                    other.setContractDate(traOther.getContractDate());//合同签署日期
                    other.setRelTradeAmount(traOther.getRelTradeAmount());//关联交易金额
                    other.setIsDealPrice((String) isNoMap.get(trimStr(traOther.getIsDealPrice())));//交易定价是否为国家规定
                    other.setFinancialImpact(trimStr(traOther.getFinancialImpact()));//对财务状况及经营成果的影响

//                    business.setTotolTradeAmount(baseService.getCurrYearTotalTradeAmount(relId, relType));
                    other.setRelId(relId);
//                    business.setCaliber(caliber);
                    other.setTotolTradeAmount(totolTradeAmount);
                    other.setRelType(relType);
                    other.setTradeOrg(unitCode);
                    other.setDataSources("1");
                    other.setDelflag("1");
                    other.setModifiedTime(new Date());

                    List<Filter> filters = new Filter.Builder().eq("contractNo", other.getContractNo()).build();
                    List<TraOther> tarBLists = traOtherService.select(filters);

                    if (null != tarBLists && tarBLists.size() > 0) {
                        other.setPid(tarBLists.get(0).getPid());
                        traOtherService.updateSelective(other);
                    } else {
                        other.setTransNum(BatchNumberUtil.makeBatchNumber());
                        traOtherService.insertSelective(other);
                    }
                } catch (Exception e) {
                    index++;
                    LOGGER.info(e.getMessage());
                }
            }
        }
        XlsMessage res = new XlsMessage();
        String result = "";
        if (StringUtils.isNotBlank(falNum)) {
            result = ",第[" + falNum + "]条数据保存失败,";
        }
        res.setMessage("成功导入" + (list.size() - index) + "条信息" + result);
        return res;
    }

    /**
     * 处理字段空格(使用字符串)
     * @param obj
     * @return
     */
    private String trimStr(Object obj) {
        if(obj != null) {
            return obj.toString().trim();
        }
        return null;
    }


    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        //如果通过检查，就保持result为null，否则给出返回错误描述
        String result = null;
        @SuppressWarnings("unchecked")
        List<TraOther> list = (List<TraOther>) mapReaded.get(IMPORT_DATAS);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                TraOther traOther = list.get(i);
                //检查基本信息
                String resBasic = checkBasicInfo(traOther);
                if (!"".equals(resBasic)) {
                    result = "检测到第" + (i + 1) + "条数据问题含有：" + resBasic;
                    return result;
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
//        @SuppressWarnings("unchecked")
//        List<PsnInfoVo> list = (List<PsnInfoVo>) beans.get(IMPORT_DATAS);

//        if(CollectionUtils.isNotEmpty(list))
//        {
//            for(PsnInfoVo entity: list)
//            {
//                if (StringUtils.isEmpty(entity.getOrgName()))
//                {
//                    //TODO 演示对从excel读取后的数据在做导入处理前做业务处理
//                    System.out.println("演示，在这里对：" + entity.getOrgName() + "做进一步处理。");
//                }
//            }
//        }

        return beans;
    }

    public String checkBasicInfo(TraOther traOther) {
        StringBuilder sb = new StringBuilder();
//        if (StringUtils.isBlank(traOther.getNo())) {
//            return "导入失败，失败原因:序号为空";
//        }
//        if (StringUtils.isBlank(traOther.getRelNumber())) {
//            sb.append("关联方证件号码为空");
//        }
//
//        if (StringUtils.isBlank(traOther.getRelName())) {
//            sb.append("关联方名称为空");
//        }
//        if (StringUtils.isBlank(traOther.getContractNo())) {
//            sb.append("合同编号为空");
//        }
//        if (StringUtils.isBlank(traOther.getTradeAmount())) {
//            sb.append("交易金额为空");
//        }
//        if (StringUtils.isBlank(traOther.getBusStartTimeString())) {
//            sb.append("业务起始日期为空");
//        }
//        if (StringUtils.isBlank(traOther.getTradePrice())) {
//            sb.append("交易价格为空");
//        }
//        if (StringUtils.isBlank(traOther.getPricingPolicy())) {
//            sb.append("定价政策为空");
//        }
//        if (StringUtils.isBlank(traOther.getTradeSubject())) {
//            sb.append("交易标的为空");
//        }
//        if (StringUtils.isBlank(traOther.getBusType())) {
//            sb.append("业务类型为空");
//        }
//        if (StringUtils.isBlank(traOther.getTradeOccur())) {
//            sb.append("交易发生原因及合同、证明文件为空");
//        }
        return sb.toString();
    }
}
