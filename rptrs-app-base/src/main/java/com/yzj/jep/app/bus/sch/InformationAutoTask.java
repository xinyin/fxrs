package com.yzj.jep.app.bus.sch;

import com.yzj.jep.app.contract.service.Impl.InformationAutoServiceImpl;
import com.yzj.jep.app.contract.service.InformationAutoService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 合同签订信息自动获取调度任务
 */
@ScheduledJob("InformationAutoTask")
public class InformationAutoTask implements Job {
    Logger logger = LoggerFactory.getLogger(InformationAutoTask.class);
    private InformationAutoService informationAutoService;

    @Override
    public void execute(JobContext context) throws JobExecutionException, IOException {
        logger.info("*******begin the task*********");
        informationAutoService = SpringContextHolder.getBean(InformationAutoService.class);
        informationAutoService.getSignedContractList();
        logger.info("******************************");
    }
}
