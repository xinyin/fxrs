package com.yzj.jep.app.bus.sch;

import com.yzj.jep.app.sou.service.SourceDataService;
import com.yzj.jep.app.sub.service.TransbalanceService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 批量统计分析
 */
@ScheduledJob("StatisticsAnalysisTask")
public class StatisticsAnalysisTask implements Job {
    Logger logger = LoggerFactory.getLogger(StatisticsAnalysisTask.class);
    private SourceDataService sourceDataService;

    private TransbalanceService transbalanceService;

    @Override
    public void execute(JobContext context) throws JobExecutionException {
        sourceDataService = SpringContextHolder.getBean(SourceDataService.class);
        transbalanceService = SpringContextHolder.getBean(TransbalanceService.class);
        Map<String, Object> map = context.getJobData();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //抽取开始
        try {
            transbalanceService.getRelOrgList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("批量统计分析完成----当前时间：" + df.format(new Date()));

    }


}
