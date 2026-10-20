package com.yzj.jep.app.bus.sch;

import com.yzj.jep.app.sou.service.SourceDataService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 定时生产报送文件并发起报送
 */
@ScheduledJob("SubmissionTask")
public class SubmissionTask implements Job {
    Logger logger = LoggerFactory.getLogger(SubmissionTask.class);
    private SourceDataService sourceDataService;

    @Override
    public void execute(JobContext context) throws JobExecutionException {
        sourceDataService = SpringContextHolder.getBean(SourceDataService.class);

        Map<String, Object> map = context.getJobData();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //抽取开始
//        updateSourceTask(map);
        logger.info("定时生产报送文件并发起报送完成----当前时间：" + df.format(new Date()));

    }


}
