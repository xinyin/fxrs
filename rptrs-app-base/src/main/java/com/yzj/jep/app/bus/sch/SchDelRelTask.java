package com.yzj.jep.app.bus.sch;

import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.util.UpdateRelatedData;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author yls
 * @since 2020/01/20
 */
@ScheduledJob("SchDelRelTask")
public class SchDelRelTask implements Job{

    Logger logger = LoggerFactory.getLogger(SchDelRelTask.class);
    DelRelService delRelService;
    RelPsnService relPsnService;
    RelOrgService relOrgService;

    /**
     * 获取sysDate 获取前一天
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
        logger.info("==========SchDelRelTask 开始调度执行============");

        RelService relService = SpringContextHolder.getBean(RelService.class);
        delRelService = SpringContextHolder.getBean(DelRelService.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        //抽取开始,获当前日期的前一天日期
        String nowDate = getDate();
        List<Filter> filters = new Filter.Builder()
                //失效日期 当前日期的前一天
                .le("invalidTime", nowDate)
                //删除标记
                .eq("delflag", "1")
                //00 已归档  01 新增  02删除  03归档-删除
                .eq("relStatus", "00")
                .build();
        List<Rel> select = relService.select(filters);
        try {
            logger.info("开始更新关联关系有效性---");
            if (!select.isEmpty()) {
                for (Rel rl : select) {

                    UpdateRelatedData.updateDatabaseDataNew(rl);
                }
            }
            logger.info("结束更新关联关系有效性---当前时间：" + DateUtils.formatCurrentDateTime());

            logger.info("==========SchDelRelTask 结束调度执行============");

        } catch (Exception e) {
            logger.error("更新关联关系有效性异常",e);
            e.printStackTrace();
        }
    }






}
