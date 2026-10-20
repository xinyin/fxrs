package com.yzj.jep.app.bus;

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
 * @ClassName TestDisTask
 * @Description T0D0
 * @Author 米
 * @Date 11:18
 * @Version 1.0
 **/
@ScheduledJob("TestDisTask")
public class TestDisTask implements Job {
    Logger logger = LoggerFactory.getLogger(TestDisTask.class);

    @Override
    public void execute(JobContext context) throws JobExecutionException {

        logger.info("----------开始执行调度任务------------");
        Map<String, Object> map = context.getJobData();
        int pid = Integer.parseInt(String.valueOf(map.get("pid")));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (pid < 100) {
            logger.info("----------开始执行任务-------------时间：" + df.format(new Date()));
        } else {
            logger.info("----------开始执行任务-------------pid:" + map.get("pid"));
        }
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


}
