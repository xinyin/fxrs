package com.yzj.jep.app.bus.sch;

import com.alibaba.fastjson.JSONObject;
import com.cims.sync.dto.UserBean;
import com.cims.sync.util.PullUtil;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import com.yzj.jep.sys.model.User;
import com.yzj.jep.sys.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LONG
 * @date 2020/10/10
 * @description 合同制在职员工数据同步接口 定时任务调度
 */

@ScheduledJob("EmployeeDataTask")
public class EmployeeDataTask implements Job {

    private static final Integer NUMBER = 50;

    @Autowired
    private Configs configs;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UpdateNeo4jTask.class);

    @Override
    public void execute(JobContext jobContext) throws JobExecutionException, IOException {
        logger.info("人员同步定时任务开始========");
        String appId =configs.getAppId();
        String secretKey =configs.getSecretKey();
        String url =configs.getEmpUrl();
        //这个参数的含义是拉取这个时间戳之后的数据，需要保存上一次拉取的时间，全量拉取可以传null
        String syncTime=String.valueOf(System.currentTimeMillis());
        try {
            java.util.List<com.cims.sync.dto.UserBean> list = PullUtil.pullUserFromCims(appId,secretKey,url,null);
            //计算分批次数
            int limit = (list.size() + NUMBER - 1) / NUMBER;
            //分成limit次发请求到数据库，in（）操作时   可以把多条数据分割成多组请求
            Stream.iterate(0, n -> n + 1).limit(limit).forEach(a -> {
                //拿到这个参数的流的 （a * NUMBER）后面的数据  .limit（NUMBER）->后面数据的50条  .collect(Collectors.toList()->组成一个toList
                List<UserBean> userList = list.stream().skip(a * NUMBER).limit(NUMBER).collect(Collectors.toList());
                for (Object user:userList){
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(user);
                    User sysUser = new User();
                    sysUser.setUserId(jsonObject.getString("principalId"));
                    ByteSource salt = new SimpleByteSource(sysUser.getUserId());
                    //Object md5Password = new Md5Hash("123456", salt, 1);
                    Object md5Password = new Md5Hash("Abcd1234@", salt, 1);
                    sysUser.setPassword(md5Password.toString());
                    JSONObject attr = JSONObject.parseObject(jsonObject.getString("attributes"));
                    sysUser.setUserName(attr.getString("realname"));
                    sysUser.setAccountNonExpired(1);
                    sysUser.setAccountNonLocked(0);
                    sysUser.setCredentialNonExpired(1);
                    sysUser.setEnabled(1);
                    sysUser.setCreateTime(new Date());
                    sysUser.setModifiedTime(new Date());
                    userService.insert(sysUser);
                }
            });
        }catch (Exception e){
            logger.info("人员同步定时任务异常========");
        }
    }

}