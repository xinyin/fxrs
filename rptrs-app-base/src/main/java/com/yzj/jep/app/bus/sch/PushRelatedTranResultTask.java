package com.yzj.jep.app.bus.sch;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.yzj.jep.app.bus.model.ApprovalPush;
import com.yzj.jep.app.ins.service.ApprovalPushService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * @author by Liqiang
 * @Date 2023/2/27
 * @Description 推送OA系统关联交易结果失败后重新推送
 */

@ScheduledJob("PushRelatedTranResultTask")
public class PushRelatedTranResultTask implements Job {

    private static final Logger log = LoggerFactory.getLogger(PushRelatedTranResultTask.class);
    private static final ApprovalPushService approvalPushService = SpringContextHolder.getBean(ApprovalPushService.class);

    @Override
    public void execute(JobContext context) throws JobExecutionException {
        // 查询关联交易推送失败的数据
        List<Filter> filters = new Filter.Builder().nin("code", "200").build();

        List<ApprovalPush> approvalPushList = approvalPushService.select(filters);
        if (!CollUtil.isEmpty(approvalPushList)) {
            for (ApprovalPush approvalPush : approvalPushList) {
                try {
                    // 重新推送给OA系统
                    approvalPushService.pushResult(approvalPush.getContractno(), approvalPush.getStatus());
                } catch (Exception e) {
                    // 失败数据进行日志记录
                    String template = "关联交易唯一编码为:{}的关联交易在{}推送审批结果给OA系统失败!";
                    throw new RuntimeException(CharSequenceUtil.format(template, approvalPush.getContractno(), DateUtil.format(new Date(), DatePattern.CHINESE_DATE_TIME_FORMAT)));
                }
                approvalPushService.delete(approvalPush);
            }
        }
    }
}
