package com.yzj.jep.app.bus.batch;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelHistoryService;
import com.yzj.jep.app.bus.service.RelOrgHistoryService;
import com.yzj.jep.app.bus.service.RelPsnHistoryService;
import com.yzj.jep.core.holder.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * @author by Liqiang
 * @Date 2023/2/3
 * @Description TODO
 */
public class BatchInsertHistoryData {

    private static final Logger logger = LoggerFactory.getLogger(BatchInsertHistoryData.class);
    private static final RelPsnHistoryService relPsnHistoryService = SpringContextHolder.getBean(RelPsnHistoryService.class);
    private static final RelOrgHistoryService relOrgHistoryService = SpringContextHolder.getBean(RelOrgHistoryService.class);
    private static final RelHistoryService relHistoryService = SpringContextHolder.getBean(RelHistoryService.class);


    /**
     * 批量插入数据
     *
     * @param relPsnList 自然人备份对象集合
     */
    public static void insertPsnHistoryTable(List<RelPsnHistory> relPsnList) {
        long start = System.currentTimeMillis();
        relPsnList.forEach(relPsnHistoryService::insertSelective);
        logger.info(String.valueOf((System.currentTimeMillis() - start)));
    }

    public static void createPsnData(List<RelPsnHistory> relPsnHistories, RelPsn relPsn, String type, int pid) {
        RelPsnHistory relPsnHistory = new RelPsnHistory();
        BeanUtils.copyProperties(relPsn, relPsnHistory);
        relPsnHistory.setPid(null);
        relPsnHistory.setRecordType(String.valueOf(type));
        relPsnHistory.setSourceId(pid);
        relPsnHistory.setDelflag("1");
        relPsnHistory.setCreateTime(new Date());
        relPsnHistories.add(relPsnHistory);
    }

    /**
     * 批量插入数据
     *
     * @param relOrgHistories 法人或非法人组织人备份对象集合
     */
    public static void insertOrgHistoryTable(List<RelOrgHistory> relOrgHistories) {
        long start = System.currentTimeMillis();
        relOrgHistories.forEach(relOrgHistoryService::insertSelective);
        logger.info(String.valueOf((System.currentTimeMillis() - start)));
    }

    public static void createOrgData(List<RelOrgHistory> relOrgHistories, RelOrg relOrg, String type, int pid) {
        RelOrgHistory relOrgHistory = new RelOrgHistory();
        BeanUtils.copyProperties(relOrg, relOrgHistory);
        relOrgHistory.setPid(null);
        relOrgHistory.setRecordType(String.valueOf(type));
        relOrgHistory.setSourceId(pid);
        relOrgHistory.setDelflag("1");
        relOrgHistory.setCreateTime(new Date());
        relOrgHistories.add(relOrgHistory);
    }

    /**
     * 批量插入数据
     *
     * @param relHistories 关系备份对象集合
     */
    public static void insertRelHistoryTable(List<RelHistory> relHistories) {
        long start = System.currentTimeMillis();
        relHistories.forEach(relHistoryService::insertSelective);
        logger.info(String.valueOf((System.currentTimeMillis() - start)));
    }

    public static void createRelData(List<RelHistory> relHistories, Rel rel, String type, int pid) {
        RelHistory relHistory = new RelHistory();
        BeanUtils.copyProperties(rel, relHistory);
        relHistory.setPid(null);
        relHistory.setRecordType(Integer.valueOf(type));
        relHistory.setSourceId(pid);
        relHistory.setDelflag("1");
        relHistory.setCreateTime(new Date());
        relHistories.add(relHistory);
    }
}
