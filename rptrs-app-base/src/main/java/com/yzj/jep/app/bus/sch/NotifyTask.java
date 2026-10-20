package com.yzj.jep.app.bus.sch;

import com.yzj.jep.app.bus.service.TermReminderService;
import com.yzj.jep.app.sou.service.SourceDataService;
import com.yzj.jep.app.sub.service.PersonService;
import com.yzj.jep.app.sub.util.BatchUtils;
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
 * 定时校验业务数据
 */
@ScheduledJob("NotifyTask")
public class NotifyTask implements Job {
    Logger logger = LoggerFactory.getLogger(NotifyTask.class);

    @Autowired
    private TermReminderService termReminderService;

    @Override
    public void execute(JobContext context) throws JobExecutionException {
        try {
            termReminderService = SpringContextHolder.getBean(TermReminderService.class);
            Map<String, Object> map = context.getJobData();
            termReminderService.selectTermReminder(map);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            logger.info("源数据内部校验完成----当前时间：" + df.format(new Date()));
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

    }


}
