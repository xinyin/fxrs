package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.mapper.InsiderOrgMapper;
import com.yzj.jep.app.bus.mapper.RelPsnMapper;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.sub.mapper.EffectiveRelatedPartyMapper;
import com.yzj.jep.app.sub.model.Bus_rel_list;
import com.yzj.jep.app.sub.model.Bus_rel_list_hi;
import com.yzj.jep.app.sub.service.EffectiveRelatedPartyService;
import com.yzj.jep.sys.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EffectiveRelatedPartyServiceImpl implements EffectiveRelatedPartyService {

    Logger logger = LoggerFactory.getLogger(EffectiveRelatedPartyServiceImpl.class);

    @Autowired
    private EffectiveRelatedPartyMapper effectiveRelatedPartyMapper;

    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private RelPsnMapper relPsnMapper;
    @Autowired
    private InsiderOrgMapper insiderOrgMapper;



    com.yzj.jep.app.ins.service.TraServiceService traServiceService;
    TraAssetService traAssetService;
    com.yzj.jep.app.ins.service.TraOtherService traOtherService;
    TraCreditService traCreditService;
    TraFundsService traFundsService;
    RelService relService;
    RelPsnService relPsnService;
    TraUsecreditService traUsecreditService;
    StaffService staffService;
    TraInterestService traInterestService;
    TraInvestmentService traInvestmentService;
    TraBusinessService traBusinessService;

    /**
     * 关联交易-交易类型，01-投资入股类，02-资金运用类，03-保险业务类，04-利益转移类，05-提供货物或服务类，06-其他类
     */
    public static final String TRAN_TYPE_01 ="01";
    public static final String TRAN_TYPE_02 ="02";
    public static final String TRAN_TYPE_03 ="03";
    public static final String TRAN_TYPE_04 ="04";
    public static final String TRAN_TYPE_05 ="05";
    public static final String TRAN_TYPE_06 ="06";



    @Override
    public void insertBusRelList(Bus_rel_list busRelList) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //清空表数据
        Boolean truncate = effectiveRelatedPartyMapper.truncateBybusRellist();
        if (truncate){
            logger.info("Bus_rel_list 表数据清楚成功");
        }
        //查询 org存在有效关系的数据
        List<RelOrg> relOrgs = insiderOrgMapper.selectByEffective();
        if (null == relOrgs || relOrgs.size() > 0) {
            logger.info("查询 org 信息失败");
        }
        relOrgs.forEach(relOrg ->{
            Bus_rel_list busRels = new Bus_rel_list();
            Bus_rel_list_hi busRelsHi = new Bus_rel_list_hi();
            //关联方名称
            busRels.setRelName(relOrg.getOrgName());
            //关联方类型
            busRels.setRelType("1");
            //证件号码
            busRels.setRelUscCode(relOrg.getLegalIdentifyNumber());
            //证件类型
            busRels.setRelUscType(relOrg.getIdentifyType());
            busRels.setVersion(sdf.format(date));
            BeanUtils.copyProperties(busRels,busRelsHi);
            Boolean insertBusRels = effectiveRelatedPartyMapper.insertBusRelList(busRels);
            Boolean relListHi = effectiveRelatedPartyMapper.insertBusRelListHi(busRelsHi);
            if (insertBusRels && relListHi){
                logger.info("添加 Bus_rel_list || Bus_rel_list_hi  数据失败");
            }
        });
        //查询 psn 存在的有效关系
        List<RelPsn> relPsns = relPsnMapper.selectByProEffective();
        if (null == relPsns || relPsns.size() > 0) {
            logger.info("查询 prs 信息失败");
        }
        relOrgs.forEach(relPsn ->{
            Bus_rel_list busRel = new Bus_rel_list();
            Bus_rel_list_hi busRelsHi = new Bus_rel_list_hi();
            //关联方名称
            busRel.setRelName(relPsn.getOrgName());
            //关联方类型
            busRel.setRelType("2");
            //证件号码
            busRel.setRelUscCode(relPsn.getLegalIdentifyNumber());
            //证件类型
            busRel.setRelUscType(relPsn.getIdentifyType());
            busRel.setVersion(sdf.format(date));
            BeanUtils.copyProperties(busRel,busRelsHi);
            Boolean relListHi = effectiveRelatedPartyMapper.insertBusRelListHi(busRelsHi);
            Boolean insertBusRels = effectiveRelatedPartyMapper.insertBusRelList(busRel);
            if (insertBusRels && relListHi){
                logger.info("添加 Bus_rel_list || Bus_rel_list_hi 数据失败");
            }
        });
    }
}
