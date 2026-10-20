package com.yzj.jep.app.bus.sch;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import com.yzj.jep.sys.mail.IEmailSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

/**
 * 1.6.扫描所有关联交易表是否有待确认数据，并发邮件提醒
 *
 * @author root
 */
@ScheduledJob("UnSortOutTask")
public class UnSortOutTask implements Job {

    private static final Logger logger = LoggerFactory.getLogger(UnSortOutTask.class);


    @Override
    public void execute(JobContext context) throws JobExecutionException, IOException {
        UnSortOutService unSortOutService = SpringContextHolder.getBean(UnSortOutService.class);
        unSortOutService.scheduledTasks();
    }

}
