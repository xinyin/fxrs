
package com.yzj.jep.app.bus.sch;

import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 定时从源数据表结构抽取信息到关联信息表结构
 */
@ScheduledJob("UpdateSourceTask")
public class UpdateSourceTask implements Job {
    Logger logger = LoggerFactory.getLogger(UpdateSourceTask.class);
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
    /**
     * 获取sysDate
     * @return 日期
     */
    private String getDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar date=Calendar.getInstance();
        date.setTime(new Date());
        date.add(Calendar.DATE,-1);
        return sdf.format(date.getTime());
    }

    @Override
    public void execute(JobContext context) throws JobExecutionException {
        logger.info("==========UpdateSourceTask 开始调度执行============");
        logger.info("==========UpdateSourceTask 开始调度执行============");
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
        Map<String,Object> map = context.getJobData();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //抽取开始
        String nowDate=getDate();
        updateSourceTask(map,nowDate);
        logger.info("源数据抽取完成----当前时间："+df.format(new Date()));
        logger.info("==========UpdateSourceTask 结束调度执行============");

    }

    public void updateSourceTask(Map map,String nowDate) {
        //抽取自然人信息
        try {
            personService.extractPerson(map,nowDate);
        }catch (Exception e){
            logger.error("抽取自然人信息异常：",e);
        }
        //抽取法人机构
        try {
            organService.extracOrgan(map,nowDate);
        }catch (Exception e){
            logger.error("抽取法人机构异常：",e);
        }
        //抽取关联关系
        try {
            relatedRelationService.extractRelatedRelation(map,nowDate);
        }catch (Exception e){
            logger.error("抽取关联关系异常：",e);
        }
        //抽取金融产品
        try {
            finProductService.extractFinProduct(map,nowDate);
        }catch (Exception e){
            logger.error("抽取金融产品异常：",e);
        }
        //抽取投资入股类
//        try{
//            insInvestmentService.extractInsInestment(map,nowDate);
//        }catch(Exception e){
//            logger.error("抽取投资入股类异常：",e);
//        }
        //抽取资金运用类
        try{
            logger.info("抽取资金运用类关联交易begin：");
            insFundsService.extractInsFunds(map,nowDate);
            logger.info("抽取资金运用类关联交易end：");
        }catch(Exception e){
            logger.error("抽取资金运用类异常：",e);
        }
        //抽取保险业务类
        try{
            logger.info("抽取保险业务类关联交易begin：");
            insBusinessService.extractInsBusiness(map,nowDate);
            logger.info("抽取保险业务类关联交易end：");
        }catch(Exception e){
            logger.error("抽取保险业务类异常：",e);
        }
        //抽取利益转移类
        try{
            logger.info("抽取利益转移类关联交易begin：");
            insInterestService.extractInsInterest(map,nowDate);
            logger.info("抽取利益转移类关联交易end：");
        }catch(Exception e){
            logger.error("抽取利益转移类异常：",e);
        }
        //抽取提供服务类
        try{
            logger.info("抽取提供服务类关联交易begin：");
            insServiceService.extractInsService(map,nowDate);
            logger.info("抽取提供服务类关联交易end：");
        }catch(Exception e){
            logger.error("抽取提供服务类异常：",e);
        }
        //抽取其他类
//        try{
//            insOtherService.extractInsOther(map,nowDate);
//        }catch(Exception e){
//            logger.error("抽取其他类异常：",e);
//        }
    }


}
