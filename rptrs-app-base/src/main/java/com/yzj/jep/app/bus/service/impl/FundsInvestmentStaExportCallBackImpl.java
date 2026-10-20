package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.FundsInvestment;
import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.FundsInvestmentService;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.bus.vo.FundsInvestmentStaVo;
import com.yzj.jep.app.bus.vo.RelFundsVo;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wll
 */
@Service(value = FundsInvestmentStaExportCallBackImpl.BEANID)
public class FundsInvestmentStaExportCallBackImpl implements XlsExportCallback {
    public static final String BEANID = "FundsInvestmentStaExportCallBackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "fundsInvestmentSta2.xls";
    private static final String PREFIX = "资金运用类关联交易统计表_";
    private static final String SUFFIX = ".xls";
    private static final String PLACEHOLDER = "%";

    @Autowired
    private FundsInvestmentService fundsInvestmentService;

    @Autowired
    private RootOrgInfoService rootOrgInfoService;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map map) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = PREFIX + sf.format(new Date()) + SUFFIX;
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

    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        fundsInvestmentService = SpringContextHolder.getBean(FundsInvestmentService.class);
        List<FundsInvestmentStaVo> fList = new ArrayList<>();
        Map resultMap = new HashMap(16);
        List<Filter> filters = builderFilter(parameters);
        //参数
        String data = (String) parameters.get("id");
        //获取要导出自然人的id
        String[] ids = data.split("-");
        String investmentBalanceTime = ids[0];
        String quarter = ids[1];
        //计数器
        //Integer baseCount = 1;
        if(" ".equals(quarter)){
            List<FundsInvestment> fundsInvestmentss = fundsInvestmentService.queryYearFunds(investmentBalanceTime);
            for (FundsInvestment fundsInvestment : fundsInvestmentss) {
                FundsInvestmentStaVo fundsInvestmentStaVo = computeInfo(fundsInvestment);
                //fundsInvestmentStaVo.setNo(baseCount.toString());
               // baseCount += 1;
                fundsInvestmentStaVo.setYears(investmentBalanceTime);
                fList.add(fundsInvestmentStaVo);
            }
            resultMap.put("RelFundsVo",fList);
            return resultMap;
        }
        List<FundsInvestment> fundsInvestments = fundsInvestmentService.select(filters);
        if (fundsInvestments == null || fundsInvestments.size() == 0){
            resultMap.put("fundsInvestment",fList);
            return resultMap;
        }
        for (FundsInvestment fundsInvestment : fundsInvestments) {
            FundsInvestmentStaVo fundsInvestmentStaVo = computeInfo(fundsInvestment);
//            fundsInvestmentStaVo.setNo(baseCount.toString());
//            baseCount += 1;
            fList.add(fundsInvestmentStaVo);
        }
        resultMap.put("FundsInvestmentStaVo", fList);
        return resultMap;
    }


    @Override
    public Map executeExportCallback(HttpServletRequest httpServletRequest) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }

    /**
     * @param parameters
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(Map parameters) {
        //参数
        String data = (String) parameters.get("id");
        //获取要导出自然人的id
        String[] ids = data.split("-");
        String investmentBalanceTime = ids[0];
        String quarter = ids[1];
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").build();
        if(" ".equals(quarter)){
            return null;
        }
        if (!PLACEHOLDER.equals(investmentBalanceTime) && !PLACEHOLDER.equals(quarter)) {
            FundsInvestmentStaVo fundsInvestmentStaVo = fundsInvestmentService.queryStaList(quarter, investmentBalanceTime);
            filters.add(new Filter("investmentBalanceTime", Operator.GE, fundsInvestmentStaVo.getStartInvestmentBalanceTime()));
            filters.add(new Filter("investmentBalanceTime", Operator.LE, fundsInvestmentStaVo.getEndInvestmentBalanceTime()));
        }
        return filters;
    }

    //计算各类资产占总资产比例
    private FundsInvestmentStaVo computeInfo(FundsInvestment fundsInvestment) {
        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.selectAll();
        RootOrgInfo rootOrgInfo = null;
        if (CollectionUtils.isNotEmpty(rootOrgInfos)) {
            rootOrgInfo = rootOrgInfos.get(0);
        }

        FundsInvestmentStaVo fundsInvestmentStaVo = new FundsInvestmentStaVo();
        BeanUtils.copyProperties(fundsInvestment, fundsInvestmentStaVo);

        if (rootOrgInfo != null) {
            //总资产(上年末)
            fundsInvestmentStaVo.setTotalCapital(rootOrgInfo.getTotalCapital());
            //净资产(上年末)
            fundsInvestmentStaVo.setNetCaptial(rootOrgInfo.getNetCaptial().longValue());
            //未上市权益类资产占总资产比例
            fundsInvestmentStaVo.setUnlistedPro(fundsInvestmentStaVo.getUnlistedBalance().divide(rootOrgInfo.getTotalCapital(),6,BigDecimal.ROUND_HALF_UP));
            //不动产类资产占总资产比例
            fundsInvestmentStaVo.setRealEstatePro(fundsInvestmentStaVo.getRealEstateBalance().divide(rootOrgInfo.getTotalCapital(),6,BigDecimal.ROUND_HALF_UP));
            //其他金融资产-合计
            fundsInvestmentStaVo.setOtherTotal(fundsInvestmentStaVo.getBankFinancialBalance()
                                .add(fundsInvestmentStaVo.getBankNegotiableSecuritiesBalance()
                                .add(fundsInvestmentStaVo.getTrustSchemeBalance()
                                .add(fundsInvestmentStaVo.getNegotiableSecuritiesSpecialPlanBalance()
                                .add(fundsInvestmentStaVo.getOtherFinanceBalance())))));
            //其他金融资产占总资产比例
            fundsInvestmentStaVo.setOtherTotalPro(fundsInvestmentStaVo.getOtherTotal().divide(rootOrgInfo.getTotalCapital(),6,BigDecimal.ROUND_HALF_UP));
            //境外投资占总资产比例
            fundsInvestmentStaVo.setForeignPro(fundsInvestmentStaVo.getForeignBalance().divide(rootOrgInfo.getTotalCapital(),6, BigDecimal.ROUND_HALF_UP));
            //全部投资余额（含活期存款）
            fundsInvestmentStaVo.setAllBalance(fundsInvestmentStaVo.getRealEstateBalance()
                                .add(fundsInvestmentStaVo.getUnlistedBalance()
                                .add(fundsInvestmentStaVo.getForeignBalance()
                                .add(fundsInvestmentStaVo.getOtherFinanceBalance()
                                .add(fundsInvestmentStaVo.getFixedIncomeBalance()
                                .add(fundsInvestmentStaVo.getListedBalance()
                                .add(fundsInvestmentStaVo.getInsuranceManagementBalance()
                                .add(fundsInvestmentStaVo.getBankFinancialBalance()
                                .add(fundsInvestmentStaVo.getBankNegotiableSecuritiesBalance()
                                .add(fundsInvestmentStaVo.getTrustSchemeBalance()
                                .add(fundsInvestmentStaVo.getNegotiableSecuritiesSpecialPlanBalance()
                                .add(fundsInvestmentStaVo.getInsuranceFundsSupportPlanBalance()
                                .add(fundsInvestmentStaVo.getOtherInvestmentBalance())))))))))))));
            //全部投资余额占总资产比例allInvestProForTotal
            fundsInvestmentStaVo.setAllInvestProForTotal(fundsInvestmentStaVo.getAllBalance().divide(rootOrgInfo.getTotalCapital(),6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
            //全部投资余额占净资产比例allInvestProForNetAsset
            fundsInvestmentStaVo.setAllInvestProForNetAsset(fundsInvestmentStaVo.getAllBalance().divide(rootOrgInfo.getNetCaptial(),6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        }
        return fundsInvestmentStaVo;
    }
}
