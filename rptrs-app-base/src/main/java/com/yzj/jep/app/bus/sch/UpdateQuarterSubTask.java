
package com.yzj.jep.app.bus.sch;

import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 定时从源数据表结构抽取信息到关联信息表结构
 */
@ScheduledJob("UpdateQuarterSubTask")
public class UpdateQuarterSubTask implements Job {
    Logger logger = LoggerFactory.getLogger(UpdateQuarterSubTask.class);
    private PersonService personService;
    private OrganService organService;
    private RelatedRelationService relatedRelationService;
    private FinProductService finProductService;
    private CreditService creditService;
    private LoanService loanService;
    private NonloanService nonloanService;
    private InsAllbustraninfoService insAllbustraninfoService;
    private InsInvestmentService insInvestmentService;
    private InsFundsService insFundsService;
    private InsBusinessService insBusinessService;
    private InsInterestService insInterestService;
    private InsServiceService insServiceService;
    private InsOtherService insOtherService;

    private InsAllfundsService insAllfundsService;
    private InsRelfundsService insRelfundsService;

    @Override
    public void execute(JobContext context) throws JobExecutionException {
        personService=SpringContextHolder.getBean(PersonService.class);
        organService=SpringContextHolder.getBean(OrganService.class);
        relatedRelationService=SpringContextHolder.getBean(RelatedRelationService.class);
        finProductService=SpringContextHolder.getBean(FinProductService.class);
        creditService=SpringContextHolder.getBean(CreditService.class);
        loanService=SpringContextHolder.getBean(LoanService.class);
        nonloanService=SpringContextHolder.getBean(NonloanService.class);
        insAllbustraninfoService=SpringContextHolder.getBean(InsAllbustraninfoService.class);
        insInvestmentService=SpringContextHolder.getBean(InsInvestmentService.class);
        insFundsService=SpringContextHolder.getBean(InsFundsService.class);
        insBusinessService=SpringContextHolder.getBean(InsBusinessService.class);
        insInterestService=SpringContextHolder.getBean(InsInterestService.class);
        insServiceService = SpringContextHolder.getBean(InsServiceService.class);
        insOtherService = SpringContextHolder.getBean(InsOtherService.class);
        insAllfundsService = SpringContextHolder.getBean(InsAllfundsService.class);
        insRelfundsService = SpringContextHolder.getBean(InsRelfundsService.class);
        Map<String,Object> map = context.getJobData();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //抽取开始
        updateSourceTask(map);
        logger.info("源数据抽取完成----当前时间："+df.format(new Date()));
        logger.info("==========UpdateQuarterSubTask 结束调度执行============");


    }

    public void updateSourceTask(Map map) {
        logger.info("==========UpdateQuarterSubTask 开始调度执行============");
        //抽取关联交易明细表
        try {
            insAllbustraninfoService.extractCredit(map);
        }catch (Exception e){
            logger.error("抽取关联交易明细表异常：",e);
        }
        //抽取资金运用类统计表
        try {
            insAllfundsService.extractCredit(map);
        }catch (Exception e){
            logger.error("抽取资金运用统计表异常：",e);
        }
        try {
            insRelfundsService.extractCredit(map);
        }catch (Exception e){
            logger.error("抽取与单一法人资金运用统计表异常：",e);
        }
    }


}
