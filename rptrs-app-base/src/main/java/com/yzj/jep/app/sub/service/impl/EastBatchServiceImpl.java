package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.sub.enums.EastBatchEnum;
import com.yzj.jep.app.sub.mapper.*;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.app.util.DateUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.web.code.WebCode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class EastBatchServiceImpl extends MyBatisCRUDService<EastBatch, Integer> implements EastBatchService {

    @Resource
    private EastBatchMapper eastBatchMapper;

    @Resource
    private RdsMiddatalogService rdsMiddatalogService;

    @Resource
    private RdsMiddatalogMapper rdsMiddatalogMapper;

    @Resource
    private MidGlfxxbService midGlfxxbService;

    @Resource
    private MidGlfxxb2Mapper midGlfxxb2Mapper;

    @Resource
    private MidJrcpxxbService midJrcpxxbService;

    @Resource
    private MidJrcpxxb2Mapper midJrcpxxb2Mapper;

    @Resource
    private MidGlgxbService midGlgxbService;

    @Resource
    private MidGlgxxxb2Mapper midGlgxxb2Mapper;

    @Resource
    private MidZjyylgljyxyxxbService midZjyylgljyxyxxbService;

    @Resource
    private MidZjyylgljyxyxxb2Mapper midZjyylgljyxyxxb2Mapper;

    @Resource
    private MidFwlgljyxyxxbService midFwlgljyxyxxbService;

    @Resource
    private MidFwlgljyxyxxb2Mapper midFwlgljyxyxxb2Mapper;

    @Resource
    private MidLyzylgljyxyxxbService midLyzylgljyxyxxbService;

    @Resource
    private MidLyzylgljyxyxxb2Mapper midLyzylgljyxyxxb2Mapper;

    @Resource
    private MidBxywhqtlgljyxyxxbService midBxywhqtlgljyxyxxbService;

    @Resource
    private MidBxywhqtlgljyxyxxb2Mapper midBxywhqtlgljyxyxxb2Mapper;

    @Resource
    private MidGljymxbService midGljymxbService;

    @Resource
    private MidGljymxb2Mapper midGljymxb2Mapper;

    @Resource
    private MidZjyygljytjbService midZjyygljytjbService;

    @Resource
    private MidZjyygljytjb2Mapper midZjyygljytjb2Mapper;

    @Resource
    private MidYdyglfzjyygljytjbService midYdyglfzjyygljytjbService;

    @Resource
    private MidYdyglfzjyygljytjb2Mapper midYdyglfzjyygljytjb2Mapper;

    private static final String dataSource = "08";

    private static final String ACK = "2";


    @Override
    public Mapper<EastBatch, Integer> getMapper() {
        return eastBatchMapper;
    }

    /**
     * 分发任务
     *
     * @param ids 类型集合
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> distribute(String ids) {
        Map<String, Object> tips = new HashMap<>();
        try {
            String[] split = ids.split(",");
            // 生成批次号
            String batchNo = rdsMiddatalogService.createBatchNo();
            for (String s : split) {
                String eastType = EastBatchEnum.getType(s);
                // 插入中间表，页面生产批次数据
                if (EastBatchEnum.RELATED.getType().equals(eastType)) {
                    midGlfxxbService.createEastMidGlfxxb(batchNo);
                } else if (EastBatchEnum.PRODUCT.getType().equals(eastType)) {
                    midJrcpxxbService.createEastMidJrcpxxb(batchNo);
                } else if (EastBatchEnum.RELATION.getType().equals(eastType)) {
                    midGlgxbService.createEastMidGlgxb(batchNo);
                } else if (EastBatchEnum.FUNDS.getType().equals(eastType)) {
                    midZjyylgljyxyxxbService.createEastMidZjyylgljyxyxxb(batchNo);
                } else if (EastBatchEnum.SERVICE.getType().equals(eastType)) {
                    midFwlgljyxyxxbService.createEastMidFwlgljyxyxxb(batchNo);
                } else if (EastBatchEnum.INTEREST.getType().equals(eastType)) {
                    midLyzylgljyxyxxbService.createEastMidLyzylgljyxyxxb(batchNo);
                } else if (EastBatchEnum.BUSINESS.getType().equals(eastType)) {
                    midBxywhqtlgljyxyxxbService.createEastMidBxywhqtlgljyxyxxb(batchNo);
                } else if (EastBatchEnum.TRANSACTION.getType().equals(eastType)) {
                    midGljymxbService.createEastMidGljymxb(batchNo);
                } else if (EastBatchEnum.ALL_FUNDS.getType().equals(eastType)) {
                    midZjyygljytjbService.createEastMidZjyygljytjb(batchNo);
                } else if (EastBatchEnum.REL_FUNDS.getType().equals(eastType)) {
                    midYdyglfzjyygljytjbService.createEastMidYdyglfzjyygljytjb(batchNo);
                }
                insertEastBatch(s, batchNo);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "新增成功");
        } catch (RuntimeException e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, e.getMessage());
        }
        return tips;
    }

    /**
     * 生产报送文件批次
     *
     * @param id      类型id
     * @param batchNo 原始数据批次号
     */
    public void insertEastBatch(String id, String batchNo) {
        // 获取上月日期开始
        String dataStartDate = DateUtils.preMonthBeginDateStr(new Date());
        // 获取上月日期截止
        String dataEndDate = DateUtils.preMonthEndDateStr(new Date());

        EastBatch eastBatch = new EastBatch();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 文件名称
        eastBatch.setFileName(EastBatchEnum.getName(id) + "_" + simpleDateFormat.format(new Date()));
        eastBatch.setFileType(EastBatchEnum.getType(id));
        eastBatch.setBatchNo(batchNo);
        eastBatch.setDataStartDate(dataStartDate);
        eastBatch.setDataEndDate(dataEndDate);
        // 确认状态
        eastBatch.setAckStatus("1");
        this.insert(eastBatch);
    }


    /**
     * 确定后向中间表推送数据
     *
     * @param id 批次id
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> pushEast(Integer id) {
        Map<String, Object> tips = new HashMap<>();
        try {
            // 查询批次信息
            EastBatch eastBatch = this.get(id);
            if (ObjectUtil.isNotEmpty(eastBatch)) {
                // 文件类型
                String eastType = eastBatch.getFileType();
                // 确认状态
                String ackStatus = eastBatch.getAckStatus();

                List<Filter> filter = new Filter.Builder().eq("batchno", eastBatch.getBatchNo()).build();
                if (EastBatchEnum.RELATED.getType().equals(eastType)) {
                    pushEastRelated(ackStatus, filter);
                } else if (EastBatchEnum.PRODUCT.getType().equals(eastType)) {
                    pushEastProduct(ackStatus, filter);
                } else if (EastBatchEnum.RELATION.getType().equals(eastType)) {
                    pushEastRelation(ackStatus, filter);
                } else if (EastBatchEnum.FUNDS.getType().equals(eastType)) {
                    pushEastFunds(ackStatus, filter);
                } else if (EastBatchEnum.SERVICE.getType().equals(eastType)) {
                    pushEastService(ackStatus, filter);
                } else if (EastBatchEnum.INTEREST.getType().equals(eastType)) {
                    pushEastInterest(ackStatus, filter);
                } else if (EastBatchEnum.BUSINESS.getType().equals(eastType)) {
                    pushEastBusiness(ackStatus, filter);
                } else if (EastBatchEnum.TRANSACTION.getType().equals(eastType)) {
                    pushEastTransaction(ackStatus, filter);
                } else if (EastBatchEnum.ALL_FUNDS.getType().equals(eastType)) {
                    pushEastAllFunds(ackStatus, filter);
                } else if (EastBatchEnum.REL_FUNDS.getType().equals(eastType)) {
                    pushEastRelFunds(ackStatus, filter);
                }

                // 保留一条数据记录
                RdsMiddatalog2 dataLog2 = new RdsMiddatalog2();
                dataLog2.setBatchNo(eastBatch.getBatchNo());
                List<RdsMiddatalog2> rdsMiddatalog2s = rdsMiddatalogMapper.select(dataLog2);
                if (CollUtil.isEmpty(rdsMiddatalog2s)) {
                    RdsMiddatalog2 dataLog = new RdsMiddatalog2();
                    BeanUtils.copyProperties(eastBatch, dataLog);
                    dataLog.setLogId(ObjectUtil.toString(System.currentTimeMillis()));
                    dataLog.setDataSource(dataSource);
                    dataLog.setDealStatus("S");
                    rdsMiddatalogMapper.insert(dataLog);
                }

                // 修改批次表
                eastBatch.setAckStatus(ACK);
                this.update(eastBatch);

                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, "确认成功");
            }
        } catch (Exception e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "操作失败，请联系管理员");
        }
        return tips;
    }


    /**
     * east关联方信息
     *
     * @param ackStatus 确认状态
     * @param filter    查询条件
     */
    private void pushEastRelated(String ackStatus, List<Filter> filter) {
        List<MidGlfxxb> eastRelated = midGlfxxbService.select(filter);
        for (MidGlfxxb midGlfxxb : eastRelated) {
            MidGlfxxb2 midGlfxxb2 = new MidGlfxxb2();
            BeanUtils.copyProperties(midGlfxxb, midGlfxxb2);
            if (ACK.equals(ackStatus)) {
                MidGlfxxb2 glf = new MidGlfxxb2();
                glf.setSerialno(midGlfxxb.getSerialno());
                midGlfxxb2Mapper.delete(glf);
            }
            midGlfxxb2Mapper.insert(midGlfxxb2);
        }
    }


    /**
     * east金融产品信息表
     *
     * @param ackStatus 确认状态
     * @param filter    查询条件
     */
    private void pushEastProduct(String ackStatus, List<Filter> filter) {
        List<MidJrcpxxb> eastProduct = midJrcpxxbService.select(filter);
        for (MidJrcpxxb midJrcpxxb : eastProduct) {
            MidJrcpxxb2 midJrcpxxb2 = new MidJrcpxxb2();
            BeanUtils.copyProperties(midJrcpxxb, midJrcpxxb2);
            if (ACK.equals(ackStatus)) {
                MidJrcpxxb2 jrcp = new MidJrcpxxb2();
                jrcp.setSerialno(midJrcpxxb.getSerialno());
                midJrcpxxb2Mapper.delete(jrcp);
            }
            midJrcpxxb2Mapper.insert(midJrcpxxb2);
        }
    }


    /**
     * east关联关系表
     *
     * @param ackStatus 确认状态
     * @param filter    查询条件
     */
    private void pushEastRelation(String ackStatus, List<Filter> filter) {
        List<MidGlgxb> eastRelation = midGlgxbService.select(filter);
        for (MidGlgxb midGlgxb : eastRelation) {
            MidGlgxb2 midGlgxxxb2 = new MidGlgxb2();
            BeanUtils.copyProperties(midGlgxb, midGlgxxxb2);
            if (ACK.equals(ackStatus)) {
                MidGlgxb2 glgx = new MidGlgxb2();
                glgx.setSerialno(midGlgxxxb2.getSerialno());
                midGlgxxb2Mapper.delete(glgx);
            }
            midGlgxxb2Mapper.insert(midGlgxxxb2);
        }
    }


    /**
     * east资金运用类关联交易协议信息表
     *
     * @param ackStatus 确认状态
     * @param filter    查询条件
     */
    private void pushEastFunds(String ackStatus, List<Filter> filter) {
        List<MidZjyylgljyxyxxb> eastFunds = midZjyylgljyxyxxbService.select(filter);
        for (MidZjyylgljyxyxxb midZjyylgljyxyxxb : eastFunds) {
            MidZjyylgljyxyxxb2 midZjyylgljyxyxxb2 = new MidZjyylgljyxyxxb2();
            BeanUtils.copyProperties(midZjyylgljyxyxxb, midZjyylgljyxyxxb2);
            if (ACK.equals(ackStatus)) {
                MidZjyylgljyxyxxb2 zjyylg = new MidZjyylgljyxyxxb2();
                zjyylg.setSerialno(midZjyylgljyxyxxb2.getSerialno());
                midZjyylgljyxyxxb2Mapper.delete(zjyylg);
            }
            midZjyylgljyxyxxb2Mapper.insert(midZjyylgljyxyxxb2);
        }
    }


    /**
     * east服务类关联交易协议信息表
     *
     * @param ackStatus 确认状态
     * @param filter    查询条件
     */
    private void pushEastService(String ackStatus, List<Filter> filter) {
        List<MidFwlgljyxyxxb> eastService = midFwlgljyxyxxbService.select(filter);
        for (MidFwlgljyxyxxb midFwlgljyxyxxb : eastService) {
            MidFwlgljyxyxxb2 midFwlgljyxyxxb2 = new MidFwlgljyxyxxb2();
            BeanUtils.copyProperties(midFwlgljyxyxxb, midFwlgljyxyxxb2);
            if (ACK.equals(ackStatus)) {
                MidFwlgljyxyxxb2 fwlgljyxyxxb = new MidFwlgljyxyxxb2();
                fwlgljyxyxxb.setSerialno(midFwlgljyxyxxb2.getSerialno());
                midFwlgljyxyxxb2Mapper.delete(fwlgljyxyxxb);
            }
            midFwlgljyxyxxb2Mapper.insert(midFwlgljyxyxxb2);
        }
    }


    /**
     * east利益转移类关联交易协议信息表
     *
     * @param ackStatus 确认状态
     * @param filter    查询条件
     */
    private void pushEastInterest(String ackStatus, List<Filter> filter) {
        List<MidLyzylgljyxyxxb> eastInterest = midLyzylgljyxyxxbService.select(filter);
        for (MidLyzylgljyxyxxb midLyzylgljyxyxxb : eastInterest) {
            MidLyzylgljyxyxxb2 midLyzylgljyxyxxb2 = new MidLyzylgljyxyxxb2();
            BeanUtils.copyProperties(midLyzylgljyxyxxb, midLyzylgljyxyxxb2);
            if (ACK.equals(ackStatus)) {
                MidLyzylgljyxyxxb2 lyzylg = new MidLyzylgljyxyxxb2();
                lyzylg.setSerialno(midLyzylgljyxyxxb2.getSerialno());
                midLyzylgljyxyxxb2Mapper.delete(lyzylg);
            }
            midLyzylgljyxyxxb2Mapper.insert(midLyzylgljyxyxxb2);
        }
    }


    /**
     * east保险业务和其他类关联交易协议信息表
     *
     * @param ackStatus 确认状态
     * @param filter    查询条件
     */
    private void pushEastBusiness(String ackStatus, List<Filter> filter) {
        List<MidBxywhqtlgljyxyxxb> eastBxywhqtl = midBxywhqtlgljyxyxxbService.select(filter);
        for (MidBxywhqtlgljyxyxxb midBxywhqtlgljyxyxxb : eastBxywhqtl) {
            MidBxywhqtlgljyxyxxb2 midBxywhqtlgljyxyxxb2 = new MidBxywhqtlgljyxyxxb2();
            BeanUtils.copyProperties(midBxywhqtlgljyxyxxb, midBxywhqtlgljyxyxxb2);
            if (ACK.equals(ackStatus)) {
                MidBxywhqtlgljyxyxxb2 bxywhqtl = new MidBxywhqtlgljyxyxxb2();
                bxywhqtl.setSerialno(midBxywhqtlgljyxyxxb2.getSerialno());
                midBxywhqtlgljyxyxxb2Mapper.delete(bxywhqtl);
            }
            midBxywhqtlgljyxyxxb2Mapper.insert(midBxywhqtlgljyxyxxb2);
        }
    }


    /**
     * east关联交易明细表
     *
     * @param ackStatus 确认状态
     * @param filter    查询条件
     */
    private void pushEastTransaction(String ackStatus, List<Filter> filter) {
        List<MidGljymxb> eastTransaction = midGljymxbService.select(filter);
        for (MidGljymxb midGljymxb : eastTransaction) {
            MidGljymxb2 midGljymxb2 = new MidGljymxb2();
            BeanUtils.copyProperties(midGljymxb, midGljymxb2);
            if (ACK.equals(ackStatus)) {
                MidGljymxb2 gljymxb = new MidGljymxb2();
                gljymxb.setSerialno(midGljymxb2.getSerialno());
                midGljymxb2Mapper.delete(gljymxb);
            }
            midGljymxb2Mapper.insert(midGljymxb2);
        }
    }


    /**
     * east资金运用关联交易统计表
     *
     * @param ackStatus 确认状态
     * @param filter    查询条件
     */
    private void pushEastAllFunds(String ackStatus, List<Filter> filter) {
        List<MidZjyygljytjb> eastAllFunds = midZjyygljytjbService.select(filter);
        for (MidZjyygljytjb midZjyygljytjb : eastAllFunds) {
            MidZjyygljytjb2 midZjyygljytjb2 = new MidZjyygljytjb2();
            BeanUtils.copyProperties(midZjyygljytjb, midZjyygljytjb2);
            if (ACK.equals(ackStatus)) {
                MidZjyygljytjb2 zjyygljytjb = new MidZjyygljytjb2();
                zjyygljytjb.setSerialno(midZjyygljytjb2.getSerialno());
                midZjyygljytjb2Mapper.delete(zjyygljytjb);
            }
            midZjyygljytjb2Mapper.insert(midZjyygljytjb2);
        }
    }


    /**
     * east与单一关联方资金运用关联交易统计表
     *
     * @param ackStatus 确认状态
     * @param filter    查询条件
     */
    private void pushEastRelFunds(String ackStatus, List<Filter> filter) {
        List<MidYdyglfzjyygljytjb> eastRelFunds = midYdyglfzjyygljytjbService.select(filter);
        for (MidYdyglfzjyygljytjb midYdyglfzjyygljytjb : eastRelFunds) {
            MidYdyglfzjyygljytjb2 midYdyglfzjyygljytjb2 = new MidYdyglfzjyygljytjb2();
            BeanUtils.copyProperties(midYdyglfzjyygljytjb, midYdyglfzjyygljytjb2);
            if (ACK.equals(ackStatus)) {
                MidYdyglfzjyygljytjb2 ydyglfzjyygljytjb = new MidYdyglfzjyygljytjb2();
                ydyglfzjyygljytjb.setSerialno(midYdyglfzjyygljytjb2.getSerialno());
                midYdyglfzjyygljytjb2Mapper.delete(ydyglfzjyygljytjb);
            }
            midYdyglfzjyygljytjb2Mapper.insert(midYdyglfzjyygljytjb2);
        }
    }


}
