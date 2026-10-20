package com.yzj.jep.app.bus.sch;

import com.yzj.jep.app.sub.service.EastGLFXXService;
import com.yzj.jep.app.sub.service.EastGLGXService;
import com.yzj.jep.app.sub.service.EastGLJYHZService;
import com.yzj.jep.app.sub.service.EastZDGLJYMXService;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 定时从源数据表结构抽取信息到EAST表
 */
@ScheduledJob("EASTReportTask")
public class EASTReportTask implements Job {
    Logger logger = LoggerFactory.getLogger(EASTReportTask.class);
    private EastGLFXXService eastGLFXXService;
    private EastGLJYHZService eastGLJYHZService;
    private EastZDGLJYMXService eastZDGLJYMXService;
    private EastGLGXService eastGLGXService;



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
        eastGLFXXService = SpringContextHolder.getBean(EastGLFXXService.class);
        eastGLJYHZService = SpringContextHolder.getBean(EastGLJYHZService.class);
        eastZDGLJYMXService = SpringContextHolder.getBean(EastZDGLJYMXService.class);
        eastGLGXService = SpringContextHolder.getBean(EastGLGXService.class);
        Map<String,Object> map = context.getJobData();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //抽取开始
        String nowDate=getDate();
        Collection<Object> values = map.values();
        updateSourceTask(nowDate);
        logger.info("EASTReportTask源数据抽取完成----当前时间："+df.format(new Date()));
        logger.info("==========EASTReportTask 结束调度执行============");
    }

    public void updateSourceTask(String date) {
        logger.info("==========EASTReportTask 开始调度执行============");

        //east抽取关联方信息
        try {
            logger.info("开始east抽取关联方信息---");
            eastGLFXXService.eastExtract();
            logger.info("结束east抽取关联方信息---当前时间："+ DateUtils.formatCurrentDateTime());
        }catch (Exception e){
            logger.error("east抽取关联方信息异常：",e);
        }
        //east抽取重大关联交易
        try {
            logger.info("开始east抽取重大关联交易---");
            eastZDGLJYMXService.eastExtract();
            logger.info("结束east抽取重大关联交易---当前时间："+ DateUtils.formatCurrentDateTime());
        }catch (Exception e){
            logger.error("east抽取重大关联交易：",e);
        }
        //east抽取关联关系
        /*try {
            logger.info("开始east抽取关联关系---");
            eastGLGXService.eastExtract();
            logger.info("结束east抽取关联关系---当前时间："+ DateUtils.formatCurrentDateTime());
        }catch (Exception e){
            logger.error("east抽取关联关系异常：",e);
        }*/

        //east关联交易汇总
        try {
            logger.info("开始east关联交易汇总---");
            eastGLJYHZService.eastExtract(date);
            logger.info("结束east关联交易汇总---当前时间："+ DateUtils.formatCurrentDateTime());
        }catch (Exception e){
            logger.error("east关联交易汇总异常：",e);
        }
    }
}
