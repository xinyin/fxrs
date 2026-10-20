package com.yzj.jep.app.sub.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.enums.Submitted;
import com.yzj.jep.app.ins.constants.TranTypeConstant;
import com.yzj.jep.app.ins.enums.RelatedPartyCategoryEnum;
import com.yzj.jep.app.sub.mapper.EastGLJYHZMapper;
import com.yzj.jep.app.sub.model.EastGLFXX;
import com.yzj.jep.app.sub.model.EastGljyhzb;
import com.yzj.jep.app.sub.model.InsBalance;
import com.yzj.jep.app.sub.service.EastGLFXXService;
import com.yzj.jep.app.sub.service.EastGLJYHZService;
import com.yzj.jep.app.sub.service.InsBalanceService;
import com.yzj.jep.app.util.GuuidUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.entity.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EastGLJYHZServiceImpl extends MyBatisCRUDService<EastGljyhzb, Integer> implements EastGLJYHZService {

    Logger logger = LoggerFactory.getLogger(EastGLJYHZServiceImpl.class);

    @Autowired
    EastGLJYHZMapper eastGljyhzbMapper;
    @Autowired
    EastGLFXXService eastGLFXXService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private DictItemService dictItemService;

    @Override
    public Mapper<EastGljyhzb, Integer> getMapper() {
        return eastGljyhzbMapper;
    }

    @Autowired
    private InsBalanceService insBalanceService;


    public String selectByValue(String value, String simpleName) {
        List<NameValuePair> items = new ArrayList<>();
        try {
            List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
            List<DictItem> list = dictItemService.select(filters);
            //过滤掉不要的字典项
            if (!org.springframework.util.CollectionUtils.isEmpty(list)) {
                for (DictItem dictItem : list) {
                    if (dictItem.getItemValue().equals(value)) {
                        return dictItem.getItemLabel();
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void eastExtract(String date) {
        String newDate = null;
        int quarter = DateUtils.getQuarter(new Date());
        if (quarter == 1) {
            newDate = String.valueOf((DateUtils.getYear(new Date()) - 1)) + "Q4";
        } else {
            newDate = String.valueOf(DateUtils.getYear(new Date())) + "Q" + (quarter - 1);
        }
        //前一天的合同日期
//        List<Filter> filter = new Filter.Builder().like("contractSigningDate", date).
//                eq("isExtract", "0").build();
        /**
         * 关联交易汇总表抽取合同日期为上个月的，未报送的数据
         */
        List<Filter> filter = new Filter.Builder().eq("isExtract", "0").build();
        List<InsBalance> list = insBalanceService.select(filter);
        if (list != null && list.size() > 0) {
            for (InsBalance ib : list) {
                EastGljyhzb eg = new EastGljyhzb();
                logger.info("关联交易汇总的数据为："+JSONObject.toJSON(ib));
                //关联方编号
                List<Filter> filters = new ArrayList<>();
             //   filters.add(Filter.eq("name", ib.getRelName()));
                filters.add(Filter.eq("identifyNumber", ib.getRelNumber()));
                RelPsn psn = relPsnService.get(filters);
                if (psn == null) {
                    filters.clear();
             //       filters.add(Filter.eq("orgName", ib.getRelName()));
                    filters.add(Filter.eq("uniscCode", ib.getRelNumber()));
                    RelOrg relOrg = relOrgService.get(filters);
                    eg.setGlfbh(relOrg.getRelateNumber());
                }else{
                    eg.setGlfbh(psn.getRelateNumber());
                }
                eg.setGlfmc(ib.getRelName());//关联方名称
                eg.setGljyywlx(changeTranType(ib.getTranType()));//交易类型
                eg.setTjzq(newDate);//统计周期
                eg.setGlflb(changeRelType(ib.getRelType()));//关联方类别
                eg.setCreateTime(new Date());
                eg.setDelflag("1");
                EastGljyhzb one = getMapper().selectOne(eg);
                if (one != null) {
                    BigDecimal add = ib.getTradeAmount().add(one.getGljyje());//关联交易金额
                    one.setGljyje(add);
                    getMapper().updateSelective(one);
                } else {
                    eg.setGljyje(ib.getTradeAmount());
                    eg.setEastStatus(1);
                    getMapper().insert(eg);
                }
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

    public String changeTranType(String tranType){
        String tranTypeStr;
        if(TranTypeConstant.TRAN_TYPE_1.equals(tranType)){
            tranTypeStr = "资金运用类";
        }else if(TranTypeConstant.TRAN_TYPE_2.equals(tranType)){
            tranTypeStr = "提供货物或服务类";
        }else if(TranTypeConstant.TRAN_TYPE_3.equals(tranType)){
            tranTypeStr = "利益转移类";
        }else {
            tranTypeStr = "保险业务类";
        }
        return tranTypeStr;
    }

    @Override
    public List<EastGljyhzb> queryAll() {
        List<EastGljyhzb> relPars = eastGljyhzbMapper.queryAll();
        return relPars;
    }
}
