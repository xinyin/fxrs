package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.FundsInvestmentService;
import com.yzj.jep.app.bus.service.RelFundsService;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.bus.vo.FundsInvestmentStaVo;
import com.yzj.jep.app.bus.vo.RelFundsVo;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
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
@Service(value = RelFundsStaExportCallBackImpl.BEANID)
public class RelFundsStaExportCallBackImpl implements XlsExportCallback {
    public static final String BEANID = "RelFundsStaExportCallBackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "fundsSta.xls";
    private static final String PREFIX = "与单一法人资金运用类关联交易统计表_";
    private static final String SUFFIX = ".xls";
    private static final String PLACEHOLDER = "%";

    @Autowired
    private RelFundsService relFundsService;

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
        relFundsService = SpringContextHolder.getBean(RelFundsService.class);
        List<RelFundsVo> fList = new ArrayList<>();
        Map resultMap = new HashMap(16);
        List<Filter> filters = builderFilter(parameters);
        //参数
        String data = (String) parameters.get("id");
        //获取要导出自然人的id
        String[] ids = data.split("-");
        String investmentBalanceTime = ids[0];
        String quarter = ids[1];
        //计数器
        Integer baseCount = 1;
        if(" ".equals(quarter)){
            List<RelFunds> relFundss = relFundsService.queryYearFunds(investmentBalanceTime);
            for (RelFunds relFund : relFundss) {
                RelFundsVo relFundsVo = computeInfo(relFund);
                relFundsVo.setNo(baseCount.toString());
                baseCount += 1;
                fList.add(relFundsVo);
            }
            resultMap.put("RelFundsVo",fList);
            return resultMap;
        }
        List<Order> orders=new Order.Builder().desc("investmentBalanceTime").build();
        List<RelFunds> relFunds = relFundsService.select(filters,orders);
        if (relFunds == null || relFunds.size() == 0){
            resultMap.put("relFunds",fList);
            return resultMap;
        }
            RelFundsVo relFundsVo = computeInfo(relFunds.get(0));
            relFundsVo.setNo(baseCount.toString());
            fList.add(relFundsVo);
        resultMap.put("RelFundsVo",fList);
        return resultMap;
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
        String relName = ids[2];
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").build();
        if(" ".equals(quarter)){
            return null;
        }
        if (!PLACEHOLDER.equals(investmentBalanceTime) && !PLACEHOLDER.equals(quarter)) {
            RelFundsVo relFundsVo = relFundsService.queryList(quarter, investmentBalanceTime);
            filters.add(new Filter("investmentBalanceTime", Operator.GE, relFundsVo.getStartInvestmentBalanceTime()));
            filters.add(new Filter("investmentBalanceTime", Operator.LE, relFundsVo.getEndInvestmentBalanceTime()));
        }
        if (!PLACEHOLDER.equals(relName)){
            filters.add(new Filter("relName",Operator.LIKE,relName));
        }
        return filters;
    }


    //比例计算
    private RelFundsVo computeInfo(RelFunds RelFunds) {
        List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.selectAll();
        RootOrgInfo rootOrgInfo = null;
        if (CollectionUtils.isNotEmpty(rootOrgInfos)) {
            rootOrgInfo = rootOrgInfos.get(0);
        }
        RelFundsVo relFundsVo = new RelFundsVo();
        BeanUtils.copyProperties(RelFunds, relFundsVo);
            if (rootOrgInfo != null) {
                //占总资产比例(为该法人投资余额占中邮保险上年末总资产的比例。计算公式为：该法人的投资余额/中邮上年末总资产*100%)allInvestProForTotal
                relFundsVo.setAllInvestProForTotal(relFundsVo.getFundsInvestmentBalance().divide(rootOrgInfo.getTotalCapital(),6, BigDecimal.ROUND_HALF_UP));
                //占法人主体总资产比例(该法人的投资余额/该法人上年末总资产*100%)allInvestProForOrgTotal
                relFundsVo.setAllInvestProForOrgTotal(relFundsVo.getFundsInvestmentBalance().divide(relFundsVo.getRelTotalAssets(),6,BigDecimal.ROUND_HALF_UP));
            }
        return relFundsVo;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest httpServletRequest) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}
