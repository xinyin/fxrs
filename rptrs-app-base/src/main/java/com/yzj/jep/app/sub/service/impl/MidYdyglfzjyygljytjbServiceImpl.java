package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.app.bus.service.RelFundsService;
import com.yzj.jep.app.enums.RdsDataSrc;
import com.yzj.jep.app.sub.mapper.MidYdyglfzjyygljytjbMapper;
import com.yzj.jep.app.sub.model.MidYdyglfzjyygljytjb;
import com.yzj.jep.app.sub.service.MidYdyglfzjyygljytjbService;
import com.yzj.jep.app.util.DateExtendUtil;
import com.yzj.jep.app.util.DictUtil;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.util.Date;

@Service
@Transactional
public class MidYdyglfzjyygljytjbServiceImpl extends MyBatisCRUDService<MidYdyglfzjyygljytjb, Integer>
        implements MidYdyglfzjyygljytjbService {

    @Resource
    private MidYdyglfzjyygljytjbMapper midYdyglfzjyygljytjbMapper;

    @Resource
    private RelFundsService relFundsService;

    @Override
    public Mapper<MidYdyglfzjyygljytjb, Integer> getMapper() {
        return midYdyglfzjyygljytjbMapper;
    }

    @Override
    public void createEastMidYdyglfzjyygljytjb(String batchNo) {
        // 获取上月日期开始
        String quarterBeginDate = DateExtendUtil.preMonthBeginDateStr(new Date());
        // 获取上月日期截止
        String quarterEndDate = DateExtendUtil.preMonthEndDateStr(new Date());
        RelFunds relFunds = relFundsService.selectRelFunds(quarterBeginDate, quarterEndDate);
        if (ObjectUtil.isNotEmpty(relFunds)) {
            MidYdyglfzjyygljytjb east = new MidYdyglfzjyygljytjb();
            // 流水号
            east.setSerialno(batchNo + relFunds.getPid());
            // 原始数据批次号
            east.setBatchno(batchNo);
            // 业务号
            east.setBussno(relFunds.getRelIdentifyNumber());
            // 申请日期
            east.setMakedate(new Date());
            // 标识日期
            east.setBussdate(new Date());
            // 数据来源
            east.setDatasrc(RdsDataSrc.GLJY.getValue());
            // 状态位标签
            east.setOthersign("");
            // 备注说明
            east.setRemark("");
            // 保险机构代码
            east.setBxjgdm("");
            // 保险机构名称
            east.setBxjgmc("");
            // 关联方类型
            east.setGlflx(DictUtil.convertRelType(relFunds.getRelType()));
            // 关联方名称
            east.setGlfmc(relFunds.getRelName());
            // 关联方代码
            east.setGlfdm(relFunds.getRelIdentifyNumber());
            // 资金运用投资余额
            east.setZjyytzye(relFunds.getFundsInvestmentBalance().setScale(4, RoundingMode.HALF_UP));
            // 采集日期
            east.setCjrq(DateExtendUtil.endOfLastMonth(new Date()));
            // 监管辖区代码
            east.setJgxqdm("");


            this.insert(east);
        }

    }
}
