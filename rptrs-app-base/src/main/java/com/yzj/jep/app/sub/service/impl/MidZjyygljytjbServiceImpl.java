package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.model.FundsInvestment;
import com.yzj.jep.app.bus.service.FundsInvestmentService;
import com.yzj.jep.app.enums.RdsDataSrc;
import com.yzj.jep.app.sub.mapper.MidZjyygljytjbMapper;
import com.yzj.jep.app.sub.model.MidZjyygljytjb;
import com.yzj.jep.app.sub.service.MidZjyygljytjbService;
import com.yzj.jep.app.util.DateExtendUtil;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


@Service
@Transactional
public class MidZjyygljytjbServiceImpl extends MyBatisCRUDService<MidZjyygljytjb, Integer>
        implements MidZjyygljytjbService {

    @Resource
    private MidZjyygljytjbMapper midZjyygljytjbMapper;

    @Resource
    private FundsInvestmentService fundsInvestmentService;

    @Override
    public Mapper<MidZjyygljytjb, Integer> getMapper() {
        return midZjyygljytjbMapper;
    }

    @Override
    public void createEastMidZjyygljytjb(String batchNo) {
        // 获取上月日期开始
        String quarterBeginDate = DateExtendUtil.preMonthBeginDateStr(new Date());
        // 获取上月日期截止
        String quarterEndDate = DateExtendUtil.preMonthEndDateStr(new Date());
        FundsInvestment fundsInvestment = fundsInvestmentService.selectFundsInvestment(quarterBeginDate, quarterEndDate);
        if (ObjectUtil.isNotEmpty(fundsInvestment)) {
            MidZjyygljytjb east = new MidZjyygljytjb();
            east.setSerialno(batchNo + fundsInvestment.getPid());
            east.setBatchno(batchNo);
            east.setBussno("000169");

            east.setMakedate(new Date());
            east.setBussdate(new Date());
            east.setDatasrc(RdsDataSrc.GLJY.getValue());
            east.setOthersign("");
            east.setRemark("");
            east.setBxjgdm("");
            east.setBxjgmc("");
            // 流动性资产投资余额
            east.setLdxzctzye(fundsInvestment.getMobilityBalance());
            // 固定收益类资产投资余额
            east.setGdsylzctzye(fundsInvestment.getFixedIncomeBalance());
            // 权益类资产投资余额
            east.setQylzctzye(fundsInvestment.getBalanceInvestment());
            // 不动产类资产投资余额
            east.setBdclzctzye(fundsInvestment.getRealEstateBalance());
            // 保险资产管理产品投资余额
            east.setBxzcglcptzye(fundsInvestment.getInsuranceManagementBalance());
            // 商业银行理财产品投资余额
            east.setSyyhlccptzye(fundsInvestment.getBankFinancialBalance());
            // 银行业金融机构信贷资产支持证券投资余额
            east.setYhyjrjgxdzczczqtzye(fundsInvestment.getBankNegotiableSecuritiesBalance());
            // 信托公司集合资金信托计划投资余额
            east.setXtgsjhjjxtjhtzye(fundsInvestment.getTrustSchemeBalance());
            // 证券公司专项资产管理计划投资余额
            east.setZqgszxzcgljhtzye(fundsInvestment.getNegotiableSecuritiesSpecialPlanBalance());
            // 保险资管公司发行资产支持计划投资余额
            east.setBxzggsfxzczcjhtzye(fundsInvestment.getInsuranceFundsSupportPlanBalance());
            // 其他金融资产投资余额
            east.setQtjrzctzye(fundsInvestment.getOtherInvestmentBalance());
            // 全部投资余额
            east.setQbtzye(fundsInvestment.getAllInvestmentBalance());
            // 境外投资余额
            east.setJwtzye(fundsInvestment.getForeignBalance());
            // 采集日期
            east.setCjrq(DateExtendUtil.endOfLastMonth(new Date()));
            // 监管辖区代码
            east.setJgxqdm("");

            this.insert(east);
        }


    }
}
