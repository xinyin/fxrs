/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月02日 14:16:57
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.TermReminderMapper;
import com.yzj.jep.app.bus.model.TermReminder;
import com.yzj.jep.app.bus.service.TermReminderService;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.NotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class TermReminderServiceImpl extends MyBatisCRUDService<TermReminder, Integer> implements TermReminderService {

    Logger logger = LoggerFactory.getLogger(TermReminderServiceImpl.class);

    @Autowired
    private TermReminderMapper termReminderMapper;

    @Autowired
    private TermReminderService termReminderService;
    @Autowired
    private NotifyService notifyService;

    @Autowired
    private DutyService dutyService;

    @Override
    public Mapper<TermReminder, Integer> getMapper() {
        return termReminderMapper;
    }


    @Override
    public List<TermReminder> selectRelPsnInfo() {
        return termReminderMapper.selectRelPsnInfo();
    }

    @Override
    public List<TermReminder> selectRelOrgInfo() {
        return termReminderMapper.selectRelOrgInfo();
    }

    @Override
    @Transactional(readOnly = false)
    public void insertList() {

        List<TermReminder> relOrgList = termReminderService.selectRelOrgInfo();
        List<TermReminder> relPsnList = termReminderService.selectRelPsnInfo();
        Map<String, List<TermReminder>> map = new HashMap<>();


        //判断查询结果集
        if (relOrgList.size() > 0) {
            map.put("relOrgList", relOrgList);
        }
        if (relPsnList.size() > 0) {
            map.put("relPsnList", relPsnList);
        }

        if (map.size() > 0) {
            for (Map.Entry<String, List<TermReminder>> entry : map.entrySet()) {
                for (TermReminder termReminder : entry.getValue()) {

                    List<Filter> filters1 = new Filter.Builder().eq("relPid", termReminder.getRelPid()).build();

                    //无论是否为新数据都需要做 任期时长的更新
                    if (null != termReminder.getRelDataSource() && "1".equals(termReminder.getRelDataSource())) {
                        //跑批数据  在任职时长的基础上 加上一天  原因：跑批数据存在昨天生效，今天录入，有时间差
                        termReminder.setWorkHours(Integer.toString(Integer.parseInt(termReminder.getWorkHours()) + 1));
                    }
                    /*
                    setIsAlreadyRemind  "是否已提醒 0-未提醒 1-已提醒";
                    setIsNeedRemind     "是否需要要提醒 0-不需要 1-需要";
                    relStatus           "00 已归档  01 新增  02删除  03归档-删除"*/
                    int workLong = Integer.parseInt(termReminder.getWorkHours());
                    if (termReminderService.exists(filters1)) {
                        for (TermReminder ter : termReminderService.select(filters1)) {
                            ter.setWorkHours(termReminder.getWorkHours());
                            ter.setWorkHours(termReminder.getRelStatus());
                            //任期时长，审核状态满足任意条件  数据修改为  已提醒-1,不需要-0
                            if ("00".equals(ter.getRelStatus()) || workLong > 15) {
                                ter.setIsAlreadyRemind("1");
                                ter.setIsNeedRemind("0");
                                ter.setRemindTime("9,14");

                            } else if (workLong > 10 && workLong <= 15) {
                                ter.setIsAlreadyRemind("0");
                                termReminder.setIsNeedRemind("1");
                                ter.setRemindTime("9,14");

                            } else if (workLong <= 10) {
                                ter.setIsAlreadyRemind("1");
                            }
                            termReminderService.update(ter);
                        }
                    } else {
                        //任期时长，审核状态满足任意条件  数据修改为  已提醒-1,不需要-0
                        if ("00".equals(termReminder.getRelStatus()) || workLong > 15) {
                            termReminder.setIsAlreadyRemind("1");
                            termReminder.setIsNeedRemind("0");
                            termReminder.setRemindTime("9,14");
                        } else if (workLong > 10 && workLong <= 15) {
                            termReminder.setIsAlreadyRemind("0");
                            termReminder.setIsNeedRemind("1");
                            termReminder.setRemindTime("9,14");
                        } else if (workLong <= 10) {
                            termReminder.setIsAlreadyRemind("0");
                            termReminder.setIsNeedRemind("1");
                            termReminder.setRemindTime("9");
                        }
                        termReminderMapper.insertList(termReminder);
                    }
                }

            }

        }
    }

    @Override
    @Transactional(readOnly = false)
    public void selectTermReminder(Map map) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -15);
        Map<String, Object> tips = new HashMap<>();

        this.insertList();

        List<Filter> filers = new Filter.Builder().eq("isNeedRemind", "1").build();
        List<TermReminder> termReminderList = termReminderService.select(filers);
        try {
            for (TermReminder ter : termReminderList) {
                if (ter.getModifiedTime().before(c.getTime())) {
                    ter.setIsAlreadyRemind("1");
                    ter.setIsNeedRemind("0");
                    termReminderService.update(ter);
                    continue;

                }
                String[] times = ter.getRemindTime().split(",");
                if (null != times) {

                    for (int i = 0; i < times.length; i++) {

                        //9时间段做提醒   14时间段做提醒
                        if (map.get("start").equals(times[i])) {

                            List<Duty> dutys = dutyService.select(new Filter.Builder()
                                    .like("positionCode", "200@" + ter.getMgerOrg())
                                    .like("dutySource", DutySource.ROLE.toString())
                                    .build());
                            if (CollectionUtils.isNotEmpty(dutys)) {
                                for (Duty duty : dutys) {
                                    //通知补录人
                                    /*创建提醒*/
                                    notifyService.createRemind(duty.getStaffCode(), "自然人[" + ter.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到自然人列表完成补录。", "自然人[" + ter.getRelName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到自然人列表完成补录。");
                                }
                                //设置为已通知
                                ter.setIsAlreadyRemind("1");
                                termReminderService.update(ter);
                                logger.info("通知补录时间" + sdf.format(new Date()) + "通知成功");
                            } else {
                                logger.info(sdf.format(new Date()) + "暂无通知数据");
                            }
                        }
                    }
                }


            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
        }
    }

}