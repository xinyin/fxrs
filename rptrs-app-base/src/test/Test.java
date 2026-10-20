import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.sch.SchDelRelTask;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.JobContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Test {

    @Autowired
    private RelService relService;
    @Autowired
    private DelRelService delRelService;
    @Autowired
    private  RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private DelRelHistoryService delRelHistoryService;
    @Autowired
    private SchDelRelTask schDelRelTask;
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

    @org.junit.Test
    public void execute1() {
        System.out.println("==========SchDelRelTask 开始调度执行============");
        JobContext context  =null;
        schDelRelTask.execute(context);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //抽取开始,获当前日期的前一天日期
        String nowDate = getDate();
        List<Filter> filters = new Filter.Builder()
                .like("invalidTime", nowDate)//失效日期 当前日期的前一天
                //  .eq("isValid", "1")//是否有效
                .eq("delflag", "1")//删除标记
                .eq("approvalStatus", "6")//00 已归档  01 新增  02删除  03归档-删除
                .build();
        List<Rel> select = relService.select(filters);
        try {
//            logger.info("开始更新关联关系有效性---");
            System.out.println("开始更新关联关系有效性---");
            if (select != null && select.size() > 0) {
                for (Rel rl : select) {
                    //关联关系主体类型   PSN:个人 ORG:机构
                    String fromType = rl.getFromType();
                    //关联关系pid
                    String fromPid = rl.getFromPid();
                    //删除关系
                    rl.setIsValid("0");
                    relService.delRelation(fromType, fromPid);
                    //删除关系图谱
                    relService.deleteNeo4j(rl);
                    relService.updateSelective(rl);
                }
            }
//            logger.info("结束更新关联关系有效性---当前时间：" + DateUtils.formatCurrentDateTime());
            System.out.println("结束更新关联关系有效性---当前时间：" + DateUtils.formatCurrentDateTime());

//            logger.info("==========SchDelRelTask 结束调度执行============");
            System.out.println("==========SchDelRelTask 结束调度执行============");

        } catch (Exception e) {
            System.out.println("更新关联关系有效性异常");
            e.printStackTrace();
        }
    }
}
