package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.base.constant.AccumulatType;
import com.yzj.jep.app.base.constant.IsStatusDict;
import com.yzj.jep.app.bus.model.PsnAndOrgVo;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.ReportAnnual;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = MajorAlysislExportCallBackImpl.BEANID)
public class MajorAlysislExportCallBackImpl implements XlsExportCallback {
    public static final String BEANID = "MajorAlysislExportCallBackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "majorRelTranAnalysisData.xls";

    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private TraFundsService traFundsService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private TraInvestmentService traInvestmentService;
    @Autowired
    private TraServiceService traServiceService;
    @Autowired
    private TraOtherService traOtherService;
    @Autowired
    private AppConfigService appConfigService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private BaseService baseService;

    /**
     * @desc 文件路径
     * @return
     */
    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    /**
     * @desc 文件名
     * @param parameters
     *            URL参数集合
     * @return
     * @throws ServiceException
     */
    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "重大关联交易统计分析_" + sf.format(new Date()) + ".xls";
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

    /**
     * @desc 业务代码
     * @param parameters
     * @return
     * @throws ServiceException
     */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        Map result = new HashMap();
        String lastYearNetAsset = appConfigService.getConfigValue("netCaptialLastYear");//上年末净资产(万元)
        String baseAmt = appConfigService.getConfigValue("major_base_amt");//是否重大关联交易基数

        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();

        List<PsnAndOrgVo> psnAndOrgVoList = new ArrayList<>();

        List<Filter> filtersORG = new Filter.Builder()
                .eq("delflag", "1")
                .eq("approvalStatus", "6")
                .eq("mgerOrg", unitCode)
                .build();

        List<RelOrg> relOrgs = relOrgService.select(filtersORG);

        if (CollectionUtils.isNotEmpty(relOrgs)) {
            for (RelOrg relOrg : relOrgs) {
                PsnAndOrgVo psnAndOrgVo1 = new PsnAndOrgVo();
                psnAndOrgVo1.setPid(String.valueOf(relOrg.getPid()));
                psnAndOrgVo1.setType("ORG");
                psnAndOrgVo1.setName(relOrg.getOrgName());
                psnAndOrgVo1.setDomesticOrForeign(relOrg.getDomesticOrForeign());
                psnAndOrgVo1.setMgerOrg(relOrg.getMgerOrg());
                psnAndOrgVo1.setIdentifyTypeORG(relOrg.getIdentifyType());
                psnAndOrgVo1.setIdentifyNumber(relOrg.getUniscCode());
                psnAndOrgVo1.setCaliber(relOrg.getRegulatorOth1());
                psnAndOrgVo1.setIsBlack(relOrg.getIsBlack());
                psnAndOrgVoList.add(psnAndOrgVo1);
            }
        }
        List<PsnAndOrgVo> psnAndOrgVos =new ArrayList<>();
        if (CollectionUtils.isNotEmpty(psnAndOrgVoList)) {


            for (int i = 0; i < psnAndOrgVoList.size(); i++) {
                PsnAndOrgVo psnAndOrgVo = psnAndOrgVoList.get(i);
                PsnAndOrgVo psnAndOrgVoRes = new PsnAndOrgVo();
                Map mapResult = new HashMap();
                BigDecimal currYearTotalTradeAmount = baseService.getCurrYearTotalTradeAmount(Integer.parseInt(psnAndOrgVo.getPid()), psnAndOrgVo.getType(), psnAndOrgVo.getMgerOrg(), AccumulatType.ZERO_YES.getHandler(), AccumulatType.COUNT_PART.getHandler());
                BigDecimal currYearTotalTradeAmountBD = currYearTotalTradeAmount.setScale(6, BigDecimal.ROUND_HALF_UP);//累积交易金额
                BigDecimal lastYearNetAssetBD = new BigDecimal(lastYearNetAsset).setScale(6, BigDecimal.ROUND_HALF_UP);//上年度末总资产
                BigDecimal baseAmtBD = new BigDecimal(baseAmt).setScale(6, BigDecimal.ROUND_HALF_UP);//基数3000W

                BigDecimal percentNum = new BigDecimal(1);//档位
                BigDecimal lastYearNetAssertPercent = lastYearNetAssetBD.multiply(BigDecimal.valueOf(0.01));//上年度末净资产
                if (lastYearNetAssertPercent.compareTo(baseAmtBD) >= 0) {//上年末的百分一大于等于3000W
                    percentNum = currYearTotalTradeAmountBD.divide(lastYearNetAssetBD, 2, BigDecimal.ROUND_DOWN).
                            multiply(BigDecimal.valueOf(100)).add(BigDecimal.valueOf(1));
                } else {//上年末的百分一小于3000W
                    percentNum = currYearTotalTradeAmountBD.divide(baseAmtBD, 0, BigDecimal.ROUND_DOWN).add(BigDecimal.valueOf(1));
                }
                if (currYearTotalTradeAmount.compareTo(BigDecimal.valueOf(0)) <= 0) {
                    continue;
                }
                String tempName = psnAndOrgVo.getName();
                //名称
                psnAndOrgVoRes.setName(psnAndOrgVo.getName());
                //mapResult.put("relName", tempName);
                //类别
                psnAndOrgVoRes.setType(psnAndOrgVo.getType());
                //mapResult.put("relType",psnAndOrgVo.getType());
                //关联方证件类型
                psnAndOrgVoRes.setIdentifyType(psnAndOrgVo.getIdentifyTypeORG());
                //mapResult.put("identifyType",psnAndOrgVo.getIdentifyType());
                //关联方证件号码
                psnAndOrgVoRes.setIdentifyNumber(psnAndOrgVo.getIdentifyNumber());
                // mapResult.put("identNumber",psnAndOrgVo.getIdentifyNumber());
                psnAndOrgVoRes.setTotalTradeAmount(currYearTotalTradeAmount);
                //mapResult.put("totolTradeAmount", currYearTotalTradeAmount);
//                BigDecimal total = currYearTotalTradeAmount.setScale(2, RoundingMode.HALF_UP);
//                BigDecimal all = percentNum.multiply(baseAmtBD).setScale(2, RoundingMode.HALF_UP);
                Double num1 = Double.valueOf(String.valueOf(currYearTotalTradeAmount));
                Double num2 = Double.valueOf(String.valueOf(percentNum.multiply(baseAmtBD)));
                double res = num1 / num2;
                BigDecimal res1 = new BigDecimal(String.valueOf(res)).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
                String s = String.valueOf(res1);
                psnAndOrgVoRes.setRattotalTradeAmount(s+"%");
                //mapResult.put("ratTotolTradeAmount", s);
                psnAndOrgVos.add(psnAndOrgVoRes);
                resultList.add(mapResult);
            }
        }

        Collections.sort(psnAndOrgVos, new Comparator<PsnAndOrgVo>() {
            @Override
            public int compare(PsnAndOrgVo o1, PsnAndOrgVo o2) {
                if (o1.getTotalTradeAmount().compareTo( o2.getTotalTradeAmount()) > 0){
                    return -1;
                }else if(o1.getTotalTradeAmount().compareTo( o2.getTotalTradeAmount()) < 0){
                    return 1;
                }else{
                    return 0;
                }
            }
        });

        int size = psnAndOrgVos.size();
        if (size > 10) {
            size = 10;
        }

       /* List<Map<String, Object>> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(resultList.get(i));
        }*/
        map.put("psnAndOrgVo",psnAndOrgVos);
      return map;
    }



    /**
     * @desc 获取字典信息
     * @param dictCode
     * @return
     */
    public Map<String,Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String,String>> dictCodeMap = new HashMap<>();
        //交易类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode,"INS-tranType"));
        Map tranTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("tranTypeMap",tranTypeMap);
        //交易主体的名称
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode,"INS-subjectName"));
        Map subjectMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("subjectMap", subjectMap);
        //关联方类型
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode,"INS-relType"));
        Map relTypeMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("relTypeMap", relTypeMap);
        //审议类型
        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, "INS-considerType"));
        Map considerTypeMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("considerTypeMap", considerTypeMap);
        //交易状态
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode,"BUS-Applystatus"));
        Map applystatusMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("applystatusMap", applystatusMap);
        return dictCodeMap;
    }

    /**
     * @desc  构建查询的filter
     * @param property1
     * @param value1
     * @return
     */
    public List<Filter> builderFilter(String property1, String value1) {
        List<Filter> filters = new Filter.Builder().eq(property1, value1).build();
        return filters;
    }

    public String conversionStr (String str) {
        if (str.equals("ALL")) {
            return "%";
        }
        return str;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}
