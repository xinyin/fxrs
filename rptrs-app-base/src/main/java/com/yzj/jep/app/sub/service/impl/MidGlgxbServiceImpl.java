/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2025 pactera.com. All rights reserved.
 *
 * Generated at 2025年01月23日 15:42:07
 *
 */

package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.enums.RdsDataSrc;
import com.yzj.jep.app.enums.RelApplyStatus;
import com.yzj.jep.app.sub.model.MidGlgxb;
import com.yzj.jep.app.util.DateExtendUtil;
import com.yzj.jep.app.util.DictUtil;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.sub.mapper.MidGlgxbMapper;
import com.yzj.jep.app.sub.service.MidGlgxbService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class MidGlgxbServiceImpl extends MyBatisCRUDService<MidGlgxb, String> implements MidGlgxbService {

    Logger logger = LoggerFactory.getLogger(MidGlgxbServiceImpl.class);

    @Autowired
    private MidGlgxbMapper midGlgxbMapper;

    @Resource
    private RelService relService;

    @Override
    public Mapper<MidGlgxb, String> getMapper() {
        return midGlgxbMapper;
    }

    /**
     * East关联关系
     *
     * @param batchNo
     */
    @Override
    public void createEastMidGlgxb(String batchNo) {
        //全量的归档的关联关系
        List<Filter> filters = new Filter.Builder()
                .eq("delflag", "1")
                .eq("approvalStatus", RelApplyStatus.ARCHIVED.getValue())
                .build();
        List<Rel> rel = relService.select(filters);
        //字典
        //关联方类型	BUS-OrgOrPsn
        Map<String, String> orgOrPsnMap = DictUtil.convertToMap("BUS-OrgOrPsn");
        //与前一层之间是否存在权益份额 BUS-RelequityShare
        Map<String, String> relequityShareMap = DictUtil.convertToMap("BUS-RelequityShare");
        //是否有效	BUS-isNo
        Map<String, String> isNoMap = DictUtil.convertToMap("BUS-isNo");
        //与前一层的关联关系  BUS-RelZZD
        Map<String, String> relZZDMap = DictUtil.convertToMap("BUS-RelZZD");
        for (Rel r : rel) {

            MidGlgxb midGlgxb = new MidGlgxb();

            //East要求公共字段
            midGlgxb.setSerialno(batchNo + r.getPid());
            midGlgxb.setBatchno(batchNo);
            midGlgxb.setBussno(r.getFromIdentifyNumber() + r.getToIdentifyNumber() + r.getRelPsnOrg());
            midGlgxb.setMakedate(new Date());
            midGlgxb.setBussdate(new Date());
            midGlgxb.setDatasrc(RdsDataSrc.GLJY.getValue());
            midGlgxb.setOthersign("");
            midGlgxb.setRemark("");
            midGlgxb.setBxjgmc("");
            midGlgxb.setBxjgmc("");

            //关联方名称/关联方中间节点名称	GLFMCGLFZJJDMC		是
            midGlgxb.setGlfmcglfzjjdmc(r.getFromMemo());
            //关联方代码/关联方中间节点代码	GLFDMGLFZJJDDM		是
            midGlgxb.setGlfdmglfzjjddm(r.getFromIdentifyNumber());
            //关联方/关联方中间节点类型	GLFGLFZJJDLX		是	填报中文名称
            midGlgxb.setGlfglfzjjdlx(orgOrPsnMap.get(r.getFromType()));
            //前一层关联方名称/关联方中间节点名称	QYCGLFMCGLFZJJDMC		是
            midGlgxb.setQycglfmcglfzjjdmc(r.getToMemo());
            //前一层关联方代码/关联方中间节点代码	QYCGLFDMGLFZJJDDM		是
            midGlgxb.setQycglfdmglfzjjddm(r.getToIdentifyNumber());
            //前一层关联方/关联方中间节点类型	QYCGLFGLFZJJDLX		是	填报中文名称
            midGlgxb.setQycglfglfzjjdlx(orgOrPsnMap.get(r.getToType()));
            //与前一层的关联关系	YQYCDGLGX		是	与前一层的关联关系，填报中文描述
            midGlgxb.setYqycdglgx(relZZDMap.get(r.getRelPsnOrg()));
            //与前一层之间是否有权益份额	YQYCZJSFYQYFE		是	填报中文描述
            midGlgxb.setYqyczjsfyqyfe(relequityShareMap.get(r.getEquityShare()));
            if (!ObjectUtil.equals("0", r.getEquityShare())) {
                //持有比例	CYBL		否
                midGlgxb.setCybl(ObjectUtil.isNotNull(r.getShareholdingRatio()) ? new BigDecimal(String.valueOf(r.getShareholdingRatio())).setScale(2, RoundingMode.HALF_UP) : null);
                //质押股权比例	ZYGQBL		否
                midGlgxb.setZygqbl(ObjectUtil.isNotNull(r.getPledgeRatio()) ? new BigDecimal(r.getPledgeRatio()).setScale(2, RoundingMode.HALF_UP) : null);
            }
            //关联关系说明	GLGXSM		是
            midGlgxb.setGlgxsm(r.getRelStatement());
            //关系有效标志	GXYXBZ		是	填报代码
            midGlgxb.setGxyxbz(Long.valueOf(r.getIsValid()));
            //生效日期	SXRQ1		是
            midGlgxb.setSxrq1(r.getActiveTime());
            //失效日期	SXRQ2		否
            midGlgxb.setSxrq2(r.getInvalidTime());
            //采集日期	CJRQ		是	按月采集的表，采集日期为报送数据所属月度月末最后一天
            midGlgxb.setCjrq(DateExtendUtil.endOfLastMonth(new Date()));
            //公共字段
            midGlgxb.setCreateTime(new Date());
            midGlgxb.setModifiedTime(new Date());

            this.insert(midGlgxb);
        }

    }

}
