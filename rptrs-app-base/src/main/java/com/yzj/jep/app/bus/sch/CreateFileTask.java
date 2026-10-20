package com.yzj.jep.app.bus.sch;

import com.yzj.jep.app.sub.util.BatchUtils;
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
 * 定时生成保送文件
 */
@ScheduledJob("CreateFileTask")
public class CreateFileTask implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateFileTask.class);

    @Override
    public void execute(JobContext context) throws JobExecutionException {
        try {
            BatchUtils.batchCreateFile();
            Map<String, Object> map = context.getJobData();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            LOGGER.info("源数据内部校验完成----当前时间：" + df.format(new Date()));
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

    }
}
