package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.enums.Submitted;
import com.yzj.jep.app.ins.enums.RelatedPartyCategoryEnum;
import com.yzj.jep.app.sub.mapper.EastZDGLJYMXMapper;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.app.util.GuuidUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.mvel2.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EastZDGLJYMXServiceImpl extends MyBatisCRUDService<EastZDGLJYMX,Integer> implements EastZDGLJYMXService {
    Logger logger = LoggerFactory.getLogger(EastZDGLJYMXServiceImpl.class);
    @Autowired
    private EastZDGLJYMXMapper eastZDGLJYMXMapper;

    @Override
    public Mapper<EastZDGLJYMX, Integer> getMapper() {
        return eastZDGLJYMXMapper;
    }

    @Autowired
    private InsInvestmentService insInvestmentService;
    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private EastGLFXXService eastGLFXXService;
    @Autowired
    private InsServiceService insServiceService;
    @Autowired
    private InsFundsService insFundsService;
    @Autowired
    private InsBusinessService insBusinessService;
    @Autowired
    private InsInterestService insInterestService;
    @Autowired
    private InsOtherService insOtherService;

    @Override
    public void eastExtract() {
        /**
         *  重大关联交易明细表，从报送管理单笔报送中抽数。抽数规则为交易日期为上个月未报送的数据。
         */
        //重大投资入股类
        //extraInv();

        //重大资金运用类
        extraFun();

        //保险业务类
        extraBus();

        //利益转移类
        extraInte();

        //提供服务类
        extraSer();

        //其他类
       // extraOth();
    }

    /**
     * 投资入股类开始抽取
     */
    private void extraInv() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
        List<Filter> filters1 = new Filter.Builder().eq("isExtract", "0").build();
        List<InsInvestment> insInvestments = insInvestmentService.select(filters1);
        if (!CollectionUtils.isEmpty(insInvestments)) {
            Integer n=0;
            EastZDGLJYMX zdgljymx = new EastZDGLJYMX();
            for (InsInvestment ii : insInvestments) {
                //关联方编号
                List<Filter> filters=new ArrayList<>();
           //     filters.add(Filter.eq("name",ii.getRelName()));
                filters.add(Filter.eq("identifyNumber",ii.getRelNumber()));
                RelPsn psn = relPsnService.get(filters);
                if (psn==null ){
                    filters.clear();
               //     filters.add(Filter.eq("orgName",ii.getRelName()));
                    filters.add(Filter.eq("uniscCode",ii.getRelNumber()));
                    RelOrg relOrg = relOrgService.get(filters);
                    zdgljymx.setGlfbh(relOrg.getRelateNumber());
                }else{
                    zdgljymx.setGlfbh(psn.getRelateNumber());
                }
                zdgljymx.setGlfmc(ii.getRelName());//关联方名称
                zdgljymx.setGlflb(changeRelType(ii.getRelType()));//关联方类型
                EastZDGLJYMX tn = eastZDGLJYMXMapper.getTransNum();
                zdgljymx.setHtqdrq(DateUtils.formatDateShort(ii.getTranDate()));//交易日期
                String s = sf.format(ii.getTranDate());
                if (tn != null && tn.getJybh() != null) {
                    n = Integer.valueOf(tn.getJybh().substring(17));
                    zdgljymx.setJybh("669903084-" + s + "-" + String.format("%03d", ++n));//交易编号
                } else {
                    zdgljymx.setJybh("669903084-" + s + "-" + "001");//交易编号
                }
                zdgljymx.setGljyywlx("投资入股类");//交易类型
                zdgljymx.setGljyje(ii.getTradeAmount());//关联交易金额
                zdgljymx.setDelflag("1");
                zdgljymx.setCreateTime(new Date());
                zdgljymx.setEastStatus(1);
                getMapper().insertSelective(zdgljymx);
                ii.setIsExtract("1");
                insInvestmentService.updateSelective(ii);
            }
        }
    }


    /**
     * 资金运用类开始抽取
     */
    private void extraFun() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
        List<Filter> filters1 = new Filter.Builder().eq("isExtract", "0").build();
//        List<Filter> filters2 = new Filter.Builder().eq("isExtract", "0").build();
        List<InsFunds> insFunds = insFundsService.select(filters1);
        if (!CollectionUtils.isEmpty(insFunds)) {
            //获取当前报送时间
            EastZDGLJYMX zdgljymx = new EastZDGLJYMX();
            for (InsFunds ii : insFunds) {
                //关联方编号
                List<Filter> filters=new ArrayList<>();
               // filters.add(Filter.eq("name",ii.getRelName()));
                filters.add(Filter.eq("identifyNumber",ii.getRelNumber()));
                RelPsn psn = relPsnService.get(filters);
                if (psn==null ){
                    filters.clear();
                 //   filters.add(Filter.eq("orgName",ii.getRelName()));
                    filters.add(Filter.eq("uniscCode",ii.getRelNumber()));
                    RelOrg relOrg = relOrgService.get(filters);
                    zdgljymx.setGlfbh(relOrg.getRelateNumber());
                }else{
                    zdgljymx.setGlfbh(psn.getRelateNumber());
                }
                zdgljymx.setGlfmc(ii.getRelName());
                zdgljymx.setGlflb(changeRelType(ii.getRelType()));
                zdgljymx.setHtqdrq(DateUtils.formatDateShort(ii.getTranDate()));
                EastZDGLJYMX tn = eastZDGLJYMXMapper.getTransNum();
                String s = sf.format(ii.getTranDate());;
                if(tn!=null && tn.getJybh()!=null){
                    Integer n = Integer.valueOf(tn.getJybh().substring(17));
                    zdgljymx.setJybh("669903084-"+s+"-"+String.format("%03d",++n));//交易编号
                }else {
                    zdgljymx.setJybh("669903084-"+s+"-"+"001");//交易编号
                }
                zdgljymx.setGljyywlx("资金运用类");
                zdgljymx.setGljyje(ii.getTradeAmount());
                zdgljymx.setDelflag("1");
                zdgljymx.setCreateTime(new Date());
                zdgljymx.setEastStatus(1);
                getMapper().insertSelective(zdgljymx);
                ii.setIsExtract("1");
                insFundsService.updateSelective(ii);
            }
        }
    }

    /**
     * 保险业务类开始抽取， 抽数规则为交易日期为上个月未报送的数据。
     */
    private void extraBus() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
        List<Filter> filters1 = new Filter.Builder().eq("isExtract", "0").build();
//        List<Filter> filters3 = new Filter.Builder().eq("isExtract", "0").build();
        List<InsBusiness> insBusinesses = insBusinessService.select(filters1);
        if (!CollectionUtils.isEmpty(insBusinesses)) {
            EastZDGLJYMX zdgljymx = new EastZDGLJYMX();
            for (InsBusiness ii : insBusinesses) {
                //关联方编号
                List<Filter> filters=new ArrayList<>();
              //  filters.add(Filter.eq("name",ii.getRelName()));
                filters.add(Filter.eq("identifyNumber",ii.getRelNumber()));
                RelPsn psn = relPsnService.get(filters);
                if (psn==null ){
                    filters.clear();
                 //   filters.add(Filter.eq("orgName",ii.getRelName()));
                    filters.add(Filter.eq("uniscCode",ii.getRelNumber()));
                    RelOrg relOrg = relOrgService.get(filters);
                    zdgljymx.setGlfbh(relOrg.getRelateNumber());
                }else{
                    zdgljymx.setGlfbh(psn.getRelateNumber());
                }
                zdgljymx.setGlfmc(ii.getRelName());
                zdgljymx.setGlflb(changeRelType(ii.getRelType()));
                zdgljymx.setHtqdrq(DateUtils.formatDateShort(ii.getTranDate()));
                EastZDGLJYMX tn = eastZDGLJYMXMapper.getTransNum();
                String s = sf.format(ii.getTranDate());
                if(tn!=null && tn.getJybh()!=null){
                    Integer n = Integer.valueOf(tn.getJybh().substring(17));
                    zdgljymx.setJybh("669903084-"+s+"-"+String.format("%03d",++n));//交易编号
                }else {
                    zdgljymx.setJybh("669903084-"+s+"-"+"001");//交易编号
                }
                zdgljymx.setGljyywlx("保险业务类");
                zdgljymx.setGljyje(ii.getTradeAmount());
                zdgljymx.setDelflag("1");
                zdgljymx.setCreateTime(new Date());
                zdgljymx.setEastStatus(1);
                getMapper().insertSelective(zdgljymx);
                ii.setIsExtract("1");
                insBusinessService.updateSelective(ii);
            }
        }
    }

    /**
     * 利益转移类开始抽取
     */
    private void extraInte() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
        List<Filter> filters4 = new Filter.Builder().eq("isExtract", "0").build();
//        List<Filter> filters4 = new Filter.Builder().eq("isExtract", "0").build();
        List<InsInterest> insInterests = insInterestService.select(filters4);
        if (!CollectionUtils.isEmpty(insInterests)) {
            //利益转移类开始抽取
            EastZDGLJYMX zdgljymx = new EastZDGLJYMX();
            for (InsInterest ii : insInterests) {
                //关联方编号
                List<Filter> filters=new ArrayList<>();
             //   filters.add(Filter.eq("name",ii.getRelName()));
                filters.add(Filter.eq("identifyNumber",ii.getRelNumber()));
                RelPsn psn = relPsnService.get(filters);
                if (psn==null ){
                    filters.clear();
                 //   filters.add(Filter.eq("orgName",ii.getRelName()));
                    filters.add(Filter.eq("uniscCode",ii.getRelNumber()));
                    RelOrg relOrg = relOrgService.get(filters);
                    zdgljymx.setGlfbh(relOrg.getRelateNumber());
                }else {
                    zdgljymx.setGlfbh(psn.getRelateNumber());
                }
                zdgljymx.setGlfmc(ii.getRelName());
                zdgljymx.setGlflb(changeRelType(ii.getRelType()));
                zdgljymx.setHtqdrq(DateUtils.formatDateShort(ii.getTranDate()));
                EastZDGLJYMX tn = eastZDGLJYMXMapper.getTransNum();
                String s = sf.format(ii.getTranDate());
                if(tn!=null && tn.getJybh()!=null){
                    Integer n = Integer.valueOf(tn.getJybh().substring(17));
                    zdgljymx.setJybh("669903084-"+s+"-"+String.format("%03d",++n));//交易编号
                }else {
                    zdgljymx.setJybh("669903084-"+s+"-"+"001");//交易编号
                }
                zdgljymx.setGljyywlx("利益转移类");
                zdgljymx.setGljyje(ii.getTradeAmount());
                zdgljymx.setDelflag("1");
                zdgljymx.setCreateTime(new Date());
                zdgljymx.setEastStatus(1);
                getMapper().insertSelective(zdgljymx);
                ii.setIsExtract("1");
                insInterestService.updateSelective(ii);
            }
        }
    }

    /**
     * 提供服务类开始抽取
     */
    private void extraSer() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
        List<Filter> filters5 = new Filter.Builder().eq("isExtract", "0").build();
//        List<Filter> filters5 = new Filter.Builder().eq("isExtract", "0").build();
        List<InsService> insServices = insServiceService.select(filters5);
        if (!CollectionUtils.isEmpty(insServices)) {
            EastZDGLJYMX zdgljymx = new EastZDGLJYMX();
            for (InsService ii : insServices) {
                //关联方编号
                List<Filter> filters=new ArrayList<>();
            //    filters.add(Filter.eq("name",ii.getRelName()));
                filters.add(Filter.eq("identifyNumber",ii.getRelNumber()));
                RelPsn psn = relPsnService.get(filters);
                if (psn==null ){
                    filters.clear();
               //     filters.add(Filter.eq("orgName",ii.getRelName()));
                    filters.add(Filter.eq("uniscCode",ii.getRelNumber()));
                    RelOrg relOrg = relOrgService.get(filters);
                    zdgljymx.setGlfbh(relOrg.getRelateNumber());
                }else{
                    zdgljymx.setGlfbh(psn.getRelateNumber());
                }
                zdgljymx.setGlfmc(ii.getRelName());
                zdgljymx.setGlflb(changeRelType(ii.getRelType()));
                zdgljymx.setHtqdrq(DateUtils.formatDateShort(ii.getTranDate()));
                EastZDGLJYMX tn = eastZDGLJYMXMapper.getTransNum();
                String s = sf.format(ii.getTranDate());
                if(tn!=null && tn.getJybh()!=null){
                    Integer n = Integer.valueOf(tn.getJybh().substring(17));
                    zdgljymx.setJybh("669903084-"+s+"-"+String.format("%03d",++n));//交易编号
                }else {
                    zdgljymx.setJybh("669903084-"+s+"-"+"001");//交易编号
                }
                zdgljymx.setGljyywlx("提供服务类");
                zdgljymx.setGljyje(ii.getTradeAmount());
                zdgljymx.setDelflag("1");
                zdgljymx.setCreateTime(new Date());
                zdgljymx.setEastStatus(1);
                getMapper().insertSelective(zdgljymx);
                ii.setIsExtract("1");
                insServiceService.updateSelective(ii);
            }
        }
    }


    /**
     * @throws ParseException 其他类开始抽取
     */
    private void extraOth() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
        List<Filter> filters6 = new Filter.Builder().eq("isExtract", "0").build();
//        List<Filter> filters6 = new Filter.Builder().eq("isExtract", "0").build();
        List<InsOther> insOthers = insOtherService.select(filters6);
        if (!CollectionUtils.isEmpty(insOthers)) {
            //其他类开始抽取
            EastZDGLJYMX zdgljymx = new EastZDGLJYMX();
            for (InsOther ii : insOthers) {
                //关联方编号
                List<Filter> filters=new ArrayList<>();
           //     filters.add(Filter.eq("name",ii.getRelName()));
                filters.add(Filter.eq("identifyNumber",ii.getRelNumber()));
                RelPsn psn = relPsnService.get(filters);
                if (psn==null ){
                    filters.clear();
             //       filters.add(Filter.eq("orgName",ii.getRelName()));
                    filters.add(Filter.eq("uniscCode",ii.getRelNumber()));
                    RelOrg relOrg = relOrgService.get(filters);
                    zdgljymx.setGlfbh(relOrg.getRelateNumber());
                }else{
                    zdgljymx.setGlfbh(psn.getRelateNumber());
                }
                zdgljymx.setGlfmc(ii.getRelName());
                zdgljymx.setGlflb(changeRelType(ii.getRelType()));
                zdgljymx.setHtqdrq(DateUtils.formatDateShort(ii.getTranDate()));
                EastZDGLJYMX tn = eastZDGLJYMXMapper.getTransNum();
                String s = sf.format(ii.getTranDate());
                if(tn!=null && tn.getJybh()!=null){
                    Integer n = Integer.valueOf(tn.getJybh().substring(17));
                    zdgljymx.setJybh("669903084-"+s+"-"+String.format("%03d",++n));//交易编号
                }else {
                    zdgljymx.setJybh("669903084-"+s+"-"+"001");//交易编号
                }
                zdgljymx.setGljyywlx("其他类");
                zdgljymx.setGljyje(ii.getContractAmount());
                zdgljymx.setDelflag("1");
                zdgljymx.setCreateTime(new Date());
                zdgljymx.setEastStatus(1);
                getMapper().insertSelective(zdgljymx);
                ii.setIsExtract("1");
                insOtherService.updateSelective(ii);
            }
        }
    }

    public String changeRelType(String relType){
        if(RelatedPartyCategoryEnum.LEGALPERSON.getSysCode().equals(relType)){
            return "自然人";
        }else if(RelatedPartyCategoryEnum.NATURALPERSON.getSysCode().equals(relType)){
            return "法人或其他组织";
        }else {
            return "";
        }
    }

}
