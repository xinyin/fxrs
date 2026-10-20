package com.yzj.jep.app.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.ins.enums.RelatedPartyCategoryEnum;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author by Liqiang
 * @Date 2022/10/18
 * @Description 关联关系失效时需要失效关联方数据并解除关联方到解除关联方库中
 */
public class UpdateRelatedData {
    private static final Logger logger = LoggerFactory.getLogger(UpdateRelatedData.class);

    private static final RelPsnService relPsnService = SpringContextHolder.getBean(RelPsnService.class);
    private static final DelRelService delRelService = SpringContextHolder.getBean(DelRelService.class);
    private static final RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
    private static final RelService relService = SpringContextHolder.getBean(RelService.class);
    private static final RelProService relProService = SpringContextHolder.getBean(RelProService.class);

    private static final DelRelHistoryService delRelHistoryService = SpringContextHolder.getBean(DelRelHistoryService.class);

    public static void updateDatabaseData(Rel rel) {

        String fromType = rel.getFromType();
        Integer fromPid = Integer.valueOf(rel.getFromPid());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        /* 关联关系是否有效 */
        boolean flag = true;
        if (RelatedPartyCategoryEnum.NATURALPERSON.getSysCode().equals(fromType)) {
            //机构
            //查询下级关系自然人、机构、金融产品
            List<Filter> downRel1 = new Filter.Builder().eq("fromPid", fromPid).eq("fromType", RelatedPartyCategoryEnum.NATURALPERSON.getSysCode()).eq("delflag", "1").eq("relStatus", "00").build();
            List<Rel> downRelVoList1 = relService.select(downRel1);
            /// 存在关系需要判断失效日期
            if (!downRelVoList1.isEmpty()) {
                Date invalidTime = downRelVoList1.get(0).getInvalidTime();

                /* 当前时间大于失效日期,该关联方失效 */
                if (null != invalidTime && !LocalDate.now().isBefore(LocalDate.parse(DateUtils.formatDate(invalidTime), dtf))) {
                    flag = false;
                }
            } else {
                flag = false;
            }
            if (flag) {
                //存在有效关系删除解除关系库数据
                List<Filter> tfilter = new Filter.Builder().eq("relPid", fromPid).build();
                delRelService.deleteByFilter(tfilter);
                //更新有效性
                RelOrg relOrg = relOrgService.get(fromPid);
                relOrg.setRegulatorOth1(null);
                //是否为关联方 1否，0是
                relOrg.setIsAssoParty("0");
                //是否存在有效关系 1 有、0无
                relOrg.setEffectRelation("1");
                //审批状态说明
                relOrgService.updateSelective(relOrg);
            } else {
                RelOrg relOrg = relOrgService.get(fromPid);
                relOrg.setRegulatorOth1(null);
                //是否为关联方 1否，0是
                relOrg.setIsAssoParty("1");
                //是否存在有效关系 1 有、0无
                relOrg.setEffectRelation("0");
                relOrgService.updateSelective(relOrg);
                delRel(rel.getFromPid(), fromType, DateUtils.formatDate(rel.getInvalidTime()));
            }
        } else if (RelatedPartyCategoryEnum.FINANCIALPRODUCTS.getSysCode().equals(fromType)) {
            //查询下级关系自然人、机构、金融产品
            List<Filter> downFilterList = new Filter.Builder().
                    eq("fromPid", fromPid).
                    eq("fromType", RelatedPartyCategoryEnum.FINANCIALPRODUCTS.getSysCode()).
                    eq("delflag", "1").
                    /*eq("isValid", "1").*/eq("relStatus", "00").build();
            List<Rel> downRelList = relService.select(downFilterList);
            // 存在关系需要判断失效日期
            if (!downRelList.isEmpty()) {
                Date invalidTime = downRelList.get(0).getInvalidTime();
                /* 当前时间大于失效日期,该关联方失效 */
                if (null != invalidTime && !LocalDate.now().isBefore(LocalDate.parse(DateUtils.formatDate(invalidTime), dtf))) {
                    flag = false;
                }
            } else {
                flag = false;
            }
            if (flag) {
                //存在有效关系删除解除关系库数据
                List<Filter> tfilter = new Filter.Builder().eq("relPid", fromPid).build();
                delRelService.deleteByFilter(tfilter);
                //修改为关联方pid
                RelPro relPro = relProService.get(fromPid);
                //是否存在有效关系  1有、0无
                relPro.setEffectRelation("1");
                relProService.updateSelective(relPro);
            } else {
                //修改为关联方pid
                RelPro relPro = relProService.get(fromPid);
                relPro.setEffectRelation("0");
                relProService.updateSelective(relPro);
                delRel(rel.getFromPid(), fromType, DateUtils.formatDate(rel.getInvalidTime()));


            }
        } else if (RelatedPartyCategoryEnum.LEGALPERSON.getSysCode().equals(fromType)) {
            //查询下级关系自然人、机构、金融产品
            List<Filter> downFilterList = new Filter.Builder().
                    eq("fromPid", fromPid).
                    eq("fromType", RelatedPartyCategoryEnum.LEGALPERSON.getSysCode()).
                    eq("delflag", "1").
                    /*eq("isValid", "1").*/eq("relStatus", "00").build();
            List<Rel> downRelList = relService.select(downFilterList);
            // 存在关系需要判断失效日期
            if (!downRelList.isEmpty()) {
                Date invalidTime = downRelList.get(0).getInvalidTime();
                /* 当前时间大于失效日期,该关联方失效 */
                if (null != invalidTime && !LocalDate.now().isBefore(LocalDate.parse(DateUtils.formatDate(invalidTime), dtf))) {
                    flag = false;
                }
            } else {
                flag = false;
            }
            if (flag) {
                //存在有效关系删除解除关系库数据
                List<Filter> tfilter = new Filter.Builder().eq("relPid", fromPid).build();
                delRelService.deleteByFilter(tfilter);
                //修改为关联方pid
                RelPsn relPsn = relPsnService.get(fromPid);
                //是否关联方 1否、0是
                relPsn.setIsAssoParty("0");
                //是否存在有效关系  1有、0无
                relPsn.setEffectRelation("1");
                relPsnService.updateSelective(relPsn);
            } else {
                //修改为关联方pid
                RelPsn relPsn = relPsnService.get(fromPid);
                relPsn.setRegulatorOth1(null);
                //是否为关联方 1否，0是
                relPsn.setIsAssoParty("1");
                //是否存在有效关系 1 有、0无
                relPsn.setEffectRelation("0");
                relPsnService.updateSelective(relPsn);
                delRel(rel.getFromPid(), fromType, DateUtils.formatDate(rel.getInvalidTime()));
            }
        }
    }

    public static void delRel(String pid, String type, String time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localTime = LocalDate.now();
        if (StringUtils.isNotEmpty(time)) {
            localTime = LocalDate.parse(time, dtf);
        }
        if (!LocalDate.now().isBefore(localTime)) {
            logger.info("审批：解除关联关系入库开始===========================");
            if (RelatedPartyCategoryEnum.LEGALPERSON.getSysCode().equals(type)) {
                logger.info("审批：解除关联关系入库===========================自然人");
                boolean exists = relPsnService.exists(Integer.parseInt(pid));
                if (exists) {
                    List<Filter> tfilter = new Filter.Builder().eq("relPid", pid).eq("type", type).build();
                    tfilter.add(new Filter("delflag", Operator.EQ, "1"));
                    if (!delRelService.exists(tfilter)) {
                        DelRel delRel = new DelRel();
                        RelPsn relPsn = relPsnService.get(Integer.parseInt(pid));
                        delRel.setRelPid(relPsn.getPid());
                        delRel.setName(relPsn.getName());
                        delRel.setIdentifyNumber(relPsn.getIdentifyNumber());
                        delRel.setIdentifyType(relPsn.getIdentifyType());
                        delRel.setMgerOrg(relPsn.getMgerOrg());
                        delRel.setType(RelatedPartyCategoryEnum.LEGALPERSON.getSysCode());
                        delRel.setDomesticOrForeign(relPsn.getDomesticOrForeign());
                        delRel.setRegulator(relPsn.getRegulatorOth1());
                        delRel.setTransactionDep(relPsn.getTransactionDep());
                        delRel.setOperator(relPsn.getOperator());
                        delRel.setCreateTime(new Date());
                        delRelService.insertSelective(delRel);
                        //同步失效记录到解除关联关系历史表
                        DelRelHistory delRelHistory = new DelRelHistory();
                        delRelHistory.setRelPid(relPsn.getPid());
                        //名称
                        delRelHistory.setName(relPsn.getName());
                        //证件号码
                        delRelHistory.setIdentifyNumber(relPsn.getIdentifyNumber());
                        //证件类型
                        delRelHistory.setIdentifyType(relPsn.getIdentifyType());
                        delRelHistory.setMgerOrg(relPsn.getMgerOrg());
                        //关联方类型
                        delRelHistory.setType(RelatedPartyCategoryEnum.LEGALPERSON.getSysCode());
                        //境内/境外
                        delRelHistory.setDomesticOrForeign(relPsn.getDomesticOrForeign());
                        delRelHistory.setRegulator(relPsn.getRegulatorOth1());
                        //经办部门
                        delRelHistory.setTransactionDep(relPsn.getTransactionDep());
                        //经办人
                        delRelHistory.setOperator(relPsn.getOperator());
                        delRelHistoryService.insertSelective(delRelHistory);
                    }

                }
            }
            if (RelatedPartyCategoryEnum.NATURALPERSON.getSysCode().equals(type)) {
                logger.info("审批：解除关联关系入库===========================机构");

                if (relOrgService.exists(Integer.parseInt(pid))) {
                    List<Filter> tfilter = new Filter.Builder().eq("relPid", pid).eq("type", type).build();
                    tfilter.add(new Filter("delflag", Operator.EQ, "1"));
                    if (!delRelService.exists(tfilter)) {
                        DelRel delRel = new DelRel();
                        RelOrg relOrg = relOrgService.get(Integer.parseInt(pid));
                        delRel.setRelPid(relOrg.getPid());
                        delRel.setName(relOrg.getOrgName());
                        delRel.setIdentifyNumber(relOrg.getUniscCode());
                        delRel.setIdentifyType(relOrg.getIdentifyType());
                        delRel.setMgerOrg(relOrg.getMgerOrg());
                        delRel.setType(RelatedPartyCategoryEnum.NATURALPERSON.getSysCode());
                        delRel.setDomesticOrForeign(relOrg.getDomesticOrForeign());
                        delRel.setRegulator(relOrg.getRegulatorOth1());
                        delRel.setTransactionDep(relOrg.getTransactionDep());
                        delRel.setOperator(relOrg.getOperator());
                        delRel.setCreateTime(new Date());
                        delRelService.insertSelective(delRel);
                        //同步失效记录到解除关联关系历史表
                        DelRelHistory delRelHistory = new DelRelHistory();
                        delRelHistory.setRelPid(relOrg.getPid());
                        //名称
                        delRelHistory.setName(relOrg.getOrgName());
                        //证件号码
                        delRelHistory.setIdentifyNumber(relOrg.getUniscCode());
                        //证件类型
                        delRelHistory.setIdentifyType(relOrg.getIdentifyType());
                        delRelHistory.setMgerOrg(relOrg.getMgerOrg());
                        //关联方类型
                        delRelHistory.setType(RelatedPartyCategoryEnum.NATURALPERSON.getSysCode());
                        //境内/境外
                        delRelHistory.setDomesticOrForeign(relOrg.getDomesticOrForeign());
                        delRelHistory.setRegulator(relOrg.getRegulatorOth1());
                        //经办部门
                        delRelHistory.setTransactionDep(relOrg.getTransactionDep());
                        //经办人
                        delRelHistory.setOperator(relOrg.getOperator());
                        delRelHistoryService.insertSelective(delRelHistory);
                    }
                }
            }

            if (RelatedPartyCategoryEnum.FINANCIALPRODUCTS.getSysCode().equals(type)) {
                logger.info("审批：解除关联关系入库===========================产品");

                if (relProService.exists(Integer.parseInt(pid))) {
                    List<Filter> tfilter = new Filter.Builder().eq("relPid", pid).eq("type", type).build();
                    tfilter.add(new Filter("delflag", Operator.EQ, "1"));
                    if (!delRelService.exists(tfilter)) {
                        DelRel delRel = new DelRel();
                        RelPro relPro = relProService.get(Integer.parseInt(pid));
                        delRel.setRelPid(relPro.getPid());
                        delRel.setName(relPro.getProName());
                        delRel.setIdentifyNumber(relPro.getProProvider());
                        delRel.setIdentifyType(relPro.getProType());
                        delRel.setMgerOrg(relPro.getMgerOrg());
                        delRel.setType(RelatedPartyCategoryEnum.FINANCIALPRODUCTS.getSysCode());
                        delRel.setCreateTime(new Date());
                        delRelService.insertSelective(delRel);
                        //同步失效记录到解除关联关系历史表
                        DelRelHistory delRelHistory = new DelRelHistory();
                        delRelHistory.setRelPid(relPro.getPid());
                        //名称
                        delRelHistory.setName(relPro.getProName());
                        //证件号码
                        delRelHistory.setIdentifyNumber(relPro.getProProvider());
                        //证件类型
                        delRelHistory.setIdentifyType(relPro.getProType());
                        delRelHistory.setMgerOrg(relPro.getMgerOrg());
                        //关联方类型
                        delRelHistory.setType(RelatedPartyCategoryEnum.FINANCIALPRODUCTS.getSysCode());
                        delRelHistoryService.insertSelective(delRelHistory);
                    }

                }
            }
        }
    }


    /**
     * 判断是否需要失效关联方和关系
     *
     * @param rel 关联关系数据
     */
    public static void updateDatabaseDataNew(Rel rel) {


        /* 首先判断该条数据是否已经失效*/
        Date invalidTime = rel.getInvalidTime();
        // 失效状态下需要判断与该关联方的数据是否需要也进行失效处理
        if (null != invalidTime &&  (DateUtil.now().compareTo(DateUtil.format(invalidTime, "yyyy-MM-dd" ))) >0 ) {
            // 查询本层关联方是否存在其他关系
            boolean flag = checkRel(rel);
            /* 先把该关系失效 */
            delData(rel, rel.getInvalidTime(), flag);
            if(!flag){
                /* 1、本层关联方存在有效关系时进行关系失效，并针对该关联方为前一层关联方进行关联关系查询
                 *  2、不存在有效关系时不操作
                 */

                jungleExistToRel(rel, rel.getInvalidTime());
            }

        }
    }


    /**
     * 判断该关联方作为前一层是否存在关系
     * 1、存在时判断该关系是否有效：
     * 1）有效 不操作
     * 2）失效 把该关系制成失效，把该关联方失效；继续判断该关联方做为前一层是否存在关系
     * 2、不存在时直接跳过不操作
     *
     * @param rel 关联关系数据
     */
    public static void jungleExistToRel(Rel rel, Date invalidTimeFrom) {
        // 本层关联方
        Integer fromPid = Integer.valueOf(rel.getFromPid());

        // 查询本层关联方为fromPid是否存在关系，存在关系判断失效日期，未失效只把改关系制成失效
        List<Filter> upFilter = new Filter.Builder().eq("toPid", fromPid).eq("toType", rel.getFromType()).eq("delflag", "1").eq("relStatus", "00").build();
        List<Rel> upRelVoList = relService.select(upFilter);
        /// 存在关系需要判断失效日期
        if (!upRelVoList.isEmpty()) {
            for (Rel rel1 : upRelVoList) {
                // .ne("toPid", rel.getFromPid()).ne("toType", rel.getToType())
                //关联方失效，以此关联方为上层的关系都失效
                boolean flag = checkRel(rel1);
                delData(rel1, invalidTimeFrom, flag);
                //判断此失效关系，是否此关系的本层关联方与上层的关系是否都失效

                if (!flag){
                    jungleExistToRel(rel1, invalidTimeFrom);
                }
                //判断此失效关系，是否此关系的本层关联方与上层的关系是否都失效
//                List<Filter> downFilter = new Filter.Builder().eq("fromPid", rel1.getFromPid()).eq("fromType", rel1.getFromType()).eq("delflag", "1").eq("relStatus", "00").build();
//                List<Rel> downRelVoList = relService.select(downFilter);
//                if (CollUtil.isNotEmpty(downRelVoList)) {
//                    for (Rel rel2 : downRelVoList) {
//
//                        Date invalidTime = rel2.getInvalidTime();
//                        /* 当前时间大于失效日期,该关联方未失效 */
//                        if (null == invalidTime || (DateUtil.now().compareTo(DateUtil.format(invalidTime, "yyyy-MM-dd"))) < 0 ) {
//                          //  delData(rel2, invalidTimeFrom, false);
//                            boolean flag = checkRel(rel2);
//                            if (!flag){
//                                jungleExistToRel(rel2, invalidTimeFrom);
//                            }
//                        }
//
//                    }
//                }
                // 判断本层是否与其他关联方存在关系
//                delData(rel1, invalidTimeFrom, false);
            }
        }
    }


    /**
     * 把关系、关联方失效
     *
     * @param rel             关联关系数据
     * @param invalidTimeFrom 失效时间
     */
    public static void delData(Rel rel, Date invalidTimeFrom, boolean flag) {


        String fromType = rel.getFromType();
        Integer fromPid = Integer.valueOf(rel.getFromPid());


        if (RelatedPartyCategoryEnum.NATURALPERSON.getSysCode().equals(fromType)) {
            // 只要该关联方存有有效关系就不需要失效该关联方
            if (flag) {
                //存在有效关系删除解除关系库数据
                List<Filter> tfilter = new Filter.Builder().eq("relPid", fromPid).build();
                delRelService.deleteByFilter(tfilter);
                //更新有效性
                RelOrg relOrg = relOrgService.get(fromPid);
                relOrg.setRegulatorOth1(null);
                //是否为关联方 1否，0是
                relOrg.setIsAssoParty("0");
                //是否存在有效关系 1 有、0无
                relOrg.setEffectRelation("1");
                //审批状态说明
                relOrgService.updateSelective(relOrg);
            } else {
                RelOrg relOrg = relOrgService.get(fromPid);
                relOrg.setRegulatorOth1(null);
                //是否为关联方 1否，0是
                relOrg.setIsAssoParty("1");
                //是否存在有效关系 1 有、0无
                relOrg.setEffectRelation("0");
                relOrgService.updateSelective(relOrg);
                delRel(rel.getFromPid(), fromType, DateUtils.formatDate(invalidTimeFrom));
            }
        } else if (RelatedPartyCategoryEnum.FINANCIALPRODUCTS.getSysCode().equals(fromType)) {
            // 只要该关联方存有有效关系就不需要失效该关联方
            if (flag) {
                //存在有效关系删除解除关系库数据
                List<Filter> tfilter = new Filter.Builder().eq("relPid", fromPid).build();
                delRelService.deleteByFilter(tfilter);
                //修改为关联方pid
                RelPro relPro = relProService.get(fromPid);
                //是否存在有效关系  1有、0无
                relPro.setEffectRelation("1");
                relProService.updateSelective(relPro);
            } else {
                //修改为关联方pid
                RelPro relPro = relProService.get(fromPid);
                relPro.setEffectRelation("0");
                relProService.updateSelective(relPro);
                // 金融产品不需要入库到解除关联方标中
//                delRel(rel.getFromPid(), fromType, DateUtils.formatDate(invalidTimeFrom));


            }
        } else if (RelatedPartyCategoryEnum.LEGALPERSON.getSysCode().equals(fromType)) {
            // 只要该关联方存有有效关系就不需要失效该关联方
            if (flag) {
                //存在有效关系删除解除关系库数据
                List<Filter> tfilter = new Filter.Builder().eq("relPid", fromPid).build();
                delRelService.deleteByFilter(tfilter);
                //修改为关联方pid
                RelPsn relPsn = relPsnService.get(fromPid);
                //是否关联方 1否、0是
                relPsn.setIsAssoParty("0");
                //是否存在有效关系  1有、0无
                relPsn.setEffectRelation("1");
                relPsnService.updateSelective(relPsn);
            } else {
                //修改为关联方pid
                RelPsn relPsn = relPsnService.get(fromPid);
                relPsn.setRegulatorOth1(null);
                //是否为关联方 1否，0是
                relPsn.setIsAssoParty("1");
                //是否存在有效关系 1 有、0无
                relPsn.setEffectRelation("0");
                relPsnService.updateSelective(relPsn);
                delRel(rel.getFromPid(), fromType, DateUtils.formatDate(invalidTimeFrom));
            }
        }
        // 把关系制成失效
        delRelation(rel, invalidTimeFrom);
    }


    public static boolean checkRel(Rel rel){
        String fromType = rel.getFromType();
        Integer fromPid = Integer.valueOf(rel.getFromPid());

        /* 关联关系是否有效 */
        boolean flag = false;
        //判断该关联方是否还存在其他关系
        List<Filter> downFilterList = new Filter.Builder().
                eq("fromPid", fromPid).
                eq("fromType", fromType).
                eq("isValid","1").
                eq("delflag", "1").
                eq("relStatus", "00").build();
        List<Rel> downRelVoList1 = relService.select(downFilterList);

        if (!downRelVoList1.isEmpty()) {
            for (Rel rel1 : downRelVoList1) {
                Date invalidTime = rel1.getInvalidTime();
//                /* 该关系不是本条需要失效的关系 */
                if ( !rel.getToType().equals(rel1.getToType()) || !rel.getToPid().equals(rel1.getToPid())) {

                    /* 当前时间大于失效日期,该关联方有效 */
                    if (null != invalidTime &&  (DateUtil.now().compareTo(DateUtil.format(invalidTime, "yyyy-MM-dd"))) < 0 ) {
                        flag = true;
                        break;
                    }
                    if(null == invalidTime && Dict.SF_1.equals(rel1.getIsValid())){
                        flag = true;
                        break;
                    }
                }
            }
        }

        return flag;
    }
    /**
     * 失效关系
     *
     * @param rel             关系数据
     * @param invalidTimeFrom 失效时间
     */
    public static void delRelation(Rel rel, Date invalidTimeFrom) {
        // 把关系制成失效
        Rel updateRel = new Rel();
        updateRel.setPid(rel.getPid());
        updateRel.setIsValid("0");
        updateRel.setInvalidTime(invalidTimeFrom);
        relService.updateSelective(updateRel);
        // 图数据库清楚关系
        try {
           // relService.deleteNeo4j(rel);
        } catch (Exception e) {
            logger.error("图数据库删除关系失败！");
        }
    }
}
