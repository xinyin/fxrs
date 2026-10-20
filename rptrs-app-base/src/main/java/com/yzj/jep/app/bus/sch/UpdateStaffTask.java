package com.yzj.jep.app.bus.sch;

import com.yzj.jep.app.bus.model.DeptEntitys;
import com.yzj.jep.app.bus.service.UpdateStaffService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.service.UserProfileService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.DeptUtil;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.schedule.api.JobExecutionException;
import com.yzj.jep.schedule.api.job.Job;
import com.yzj.jep.schedule.api.job.JobContext;
import com.yzj.jep.schedule.client.annotation.ScheduledJob;
import com.yzj.jep.sys.code.NotifyConfigResult;
import com.yzj.jep.sys.model.NotifyConfig;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.NotifyConfigService;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.UnitService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @author yls
 * @since 2020/10/22
 */
@ScheduledJob("UpdateStaffTask")
public class UpdateStaffTask implements Job {

    Logger logger = LoggerFactory.getLogger(UpdateStaffTask.class);
    private StaffService staffService;
    private UnitService unitService;
    private UserProfileService userProfileService;
    private UpdateStaffService updateStaffService;

    private NotifyConfigService notifyConfigService;

    @Override
    public void execute(JobContext context) throws JobExecutionException, IOException {
        System.out.println("------------------------------------------------");
        jsonToEntitysList();
        System.out.println("-------------------------------------------------");
    }

    public void jsonToEntitysList() throws IOException {
        staffService = SpringContextHolder.getBean(StaffService.class);
        updateStaffService = SpringContextHolder.getBean(UpdateStaffService.class);
        unitService = SpringContextHolder.getBean(UnitService.class);
        userProfileService = SpringContextHolder.getBean(UserProfileService.class);
        notifyConfigService = SpringContextHolder.getBean(NotifyConfigService.class);

        DeptUtil deptUtil = new DeptUtil();
//        List<DeptEntitys> deptEntitysList = deptUtil.getStaff();
        List<DeptEntitys> deptEntitysList = updateStaffService.getStaff();
//        List<DeptEntitys> deptEntitysList = deptUtil.readTxt();
        /*
         * 拿到接口数据遍历进行更新
         * */
        for (DeptEntitys deptEntitys : deptEntitysList) {
            Staff staff = new Staff();
            staff.setStaffCode(deptEntitys.getUserId()); //用户编号
            staff.setStaffName(deptEntitys.getUserName());  //用户名称
            String userStatus = deptEntitys.getUserStatus(); //状态
            if ("01".equals(userStatus)) {
                staff.setStatus("ENABLE");
            } else if ("02".equals(userStatus)) {
                staff.setStatus("DISABLE");
            } else {
                staff.setStatus("DISABLE");
            }
            staff.setEmail(deptEntitys.getEmail());  //邮箱
            staff.setTelephone(deptEntitys.getTelephone());  //电话号码
            staff.setDeptCode(deptEntitys.getDeptId());  //所属部门编号
            //从部门信息获取相关基本数据
            Unit unit = unitService.get(deptEntitys.getDeptId());
            staff.setDeptName(unit.getUnitName());//部门名称
            staff.setUnitCode(unit.getOwner_());//机构编码
            staff.setUnitName(unitService.get(unit.getOwner_()).getUnitName());//机构名称
            //根据staffcode判断数据是否存在,存在则更新,不存在则插入
            boolean success = true;
            if (!(staffService.exists(deptEntitys.getUserId()))) {
                staff.setGender("MAN");//性别
                staff.setStatus("ENABLE");//状态
                staff.setCreateTime(new Date());//创建时间
                try {
                    staffService.insert(staff);
                    userProfileService.saveUser(staff);
                } catch (Exception e) {
                    success = false;
                    e.printStackTrace();
                    logger.info("接口数据更新失败");
                }
            } else {
                try {
                    staffService.updateSelective(staff);
                } catch (Exception e) {
                    success = false;
                    e.printStackTrace();
                    logger.info("接口数据更失败!");
                }
            }
            try {
                if(success) {
                    if (StringUtils.isNotBlank(staff.getEmail())) {
                        List<Filter> filters = new Filter.Builder().eq("staffCode", staff.getStaffCode()).build();
                        List<NotifyConfig> notifyConfigs = notifyConfigService.select(filters);
                        if (notifyConfigs != null && notifyConfigs.size() > 0) {
                            NotifyConfig notifyConfig = notifyConfigs.get(0);
                            String isReceiveEmail = notifyConfig.getIsReceiveEmail();
                            if (StringUtils.isBlank(isReceiveEmail) || isReceiveEmail.equals(NotifyConfigResult.REFUSE.getValue())) {
//                                notifyConfig.setStaffCode(staff.getStaffCode());
                                notifyConfig.setStaffName(staff.getStaffName());
                                notifyConfig.setStaffEmail(staff.getEmail());
                                notifyConfig.setEmailReceiveType("ANNOUNCE,TODO,REMIND,MESSAGE");
                                notifyConfig.setIsReceiveEmail(NotifyConfigResult.RECEIVE.getValue());
                                notifyConfig.setIsReceiveRemind(NotifyConfigResult.RECEIVE.getValue());
                                notifyConfig.setIsReceiveMsg(NotifyConfigResult.RECEIVE.getValue());
                                notifyConfig.setInboxRetentionTime("30");
                                notifyConfig.setTrashRetentionTime("30");
                                notifyConfigService.updateSelective(notifyConfig);
                            }
                        } else {
                            NotifyConfig config = new NotifyConfig();
                            config.setId(UUID.randomUUID().toString());
                            config.setStaffCode(staff.getStaffCode());
                            config.setStaffName(staff.getStaffName());
                            config.setStaffEmail(staff.getEmail());
                            config.setEmailReceiveType("ANNOUNCE,TODO,REMIND,MESSAGE");
                            config.setIsReceiveEmail(NotifyConfigResult.RECEIVE.getValue());
                            config.setIsReceiveRemind(NotifyConfigResult.RECEIVE.getValue());
                            config.setIsReceiveMsg(NotifyConfigResult.RECEIVE.getValue());
                            config.setInboxRetentionTime("30");
                            config.setTrashRetentionTime("30");
                            notifyConfigService.insertSelective(config);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("同步更新到通知配置表失败");
            }
        }
        /*
         * 把不包含接口数据中的原本数据的状态更改为未引用
         * */
        List<String> stringList = new ArrayList<>();
        for (DeptEntitys de : deptEntitysList) {
            stringList.add(de.getUserId());
        }
        List<Staff> staffList = staffService.selectAll();
        for (Staff staff : staffList) {
            if (!(ifInclude(stringList, staff.getStaffCode())) && !staff.getUnitCode().contains("ZZ_Delegation") && !staff.getDeptCode().contains("ZZHK")) {
                Staff staff1 = new Staff();
                staff1.setStaffCode(staff.getStaffCode());
                staff1.setStatus("DISABLE");
                staffService.updateSelective(staff1);
            }

        }
        Staff staff = new Staff();
        staff.setStaffCode("ZZAdmin");
        staff.setStatus("ENABLE");
        staffService.updateSelective(staff);


//        List<Filter> filters = new Filter.Builder().like("deptCode", "ZZ_Delegation").build();
//        for (Staff sf : staffService.select(filters)) {
//            sf.setStatus("ENABLE");
//        }
    }

    /**
     * 判断List<String>里是否包含某字符串
     *
     * @param list
     * @param str
     * @return
     */
    public boolean ifInclude(List<String> list, String str) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).indexOf(str) != -1) {
                return true;
            }
        }
        return false;
    }

}
