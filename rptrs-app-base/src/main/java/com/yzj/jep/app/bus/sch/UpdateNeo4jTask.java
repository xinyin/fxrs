package com.yzj.jep.app.bus.sch;

import com.yzj.jep.app.bus.service.RelPsnAndOrgOfProService;
import com.yzj.jep.app.bus.service.RelService;
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
@ScheduledJob("UpdateNeo4jTask")
public class UpdateNeo4jTask implements Job {
    Logger logger = LoggerFactory.getLogger(UpdateNeo4jTask.class);
    private RelService relService;
    private RelPsnAndOrgOfProService relPsnAndOrgOfProService;

    /**
     * 获取sysDate
     *
     * @return 日期
     */
    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.add(Calendar.DATE, -1);
        return sdf.format(date.getTime());
    }

    @Override
    public void execute(JobContext context) throws JobExecutionException {
        logger.info("==========UpdateNeo4jTask 开始调度执行============");
        relService = SpringContextHolder.getBean(RelService.class);
        relPsnAndOrgOfProService = SpringContextHolder.getBean(RelPsnAndOrgOfProService.class);

        Map<String, Object> map = context.getJobData();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //抽取开始
        String nowDate = getDate();
        relService.updateNeo4j(map, nowDate);
        relPsnAndOrgOfProService.updateNeo4j(map, nowDate);
        logger.info("源数据抽取完成----当前时间：" + df.format(new Date()));
        logger.info("==========UpdateNeo4jTask 结束调度执行============");
    }
}
