/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月20日 11:07:48
 *
 */

package com.yzj.jep.app.pub.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.pub.mapper.PublogMapper;
import com.yzj.jep.app.pub.model.ArchManageVo;
import com.yzj.jep.app.pub.model.PubQuarterVo;
import com.yzj.jep.app.pub.model.PubYearVo;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.mybatis.entity.EntityFilter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class PublogServiceImpl extends MyBatisCRUDService<Publog, Integer> implements PublogService {

    Logger logger = LoggerFactory.getLogger(PublogServiceImpl.class);

    @Autowired
    private PublogMapper publogMapper;

    @Override
    public Mapper<Publog, Integer> getMapper() {
        return publogMapper;
    }

    @Override
    public List<PubYearVo> queryDate(String pubYear) {
        return publogMapper.queryDate(pubYear);
    }

    @Override
    public BigDecimal selectTotal(String tranType, String pubYear) {
        return publogMapper.selectTotal(tranType, pubYear);
    }

    @Override
    public List<PubQuarterVo> selectQuarter(String pubYear, String pubQuarter) {
        return publogMapper.selectQuarter(pubYear, pubQuarter);
    }

    @Override
    public BigDecimal selectLastAmt(String relName, String tranType, String date) {
        return publogMapper.selectLastAmt(relName, tranType, date);
    }

    @Override
    public Integer majorTradeCount(String relName, String tranType, String pubYear) {
        return publogMapper.majorTradeCount(relName, tranType, pubYear);
    }

    @Override
    public BigDecimal selectQuarterTotal(String tranType, String pubYear, String pubQuarter) {
        return publogMapper.selectQuarterTotal(tranType, pubYear, pubQuarter);
    }

    @Override
    public List<PubQuarterVo> queryQuarterDate(String pubQuarterge, String pubQuarterle) {
        return publogMapper.queryQuarterDate(pubQuarterge, pubQuarterle);
    }

    @Override
    public void del(Integer pid) {
        publogMapper.del(pid);
    }

    @Override
    public Publog selectById(int pid) {

        EntityFilter filter = new EntityFilter(Publog.class, "pid", Operator.EQ, pid);
        List<EntityFilter> filters = new ArrayList<>();
        filters.add(filter);
        Publog publog = publogMapper.selectOneByFilter(filters);
        return publog;
    }

    @Override
    public List<Publog> selectList(Publog publog) {
        return publogMapper.selectList(publog);
    }

    @Override
    public ArchManageVo queryList(String quarter, String archDate) {
        //开始时间
        String startArchDate;
        //结束时间
        String endArchDate;
        String archDate1;
        String quarter1;
        quarter1=quarter.substring(1,quarter.length());
        archDate1=archDate.substring(1,archDate.length());
        archDate1=archDate.replace(',',' ');
        if (StringUtils.isBlank(archDate1)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            archDate1 = sdf.format(date);
        }
        switch (quarter1) {
            case "1":
                startArchDate = archDate1 + "-01-01";
                endArchDate = archDate1 + "-03-31";
                break;
            case "2":
                startArchDate = archDate1 + "-04-01";
                endArchDate = archDate1 + "-06-30";
                break;
            case "3":
                startArchDate = archDate1 + "-07-01";
                endArchDate = archDate1 + "-09-31";
                break;
            case "4":
                startArchDate = archDate1 + "-10-01";
                endArchDate = archDate1 + "-12-31";
                break;
            default:
                startArchDate = archDate1 + "-01-01";
                endArchDate = archDate1 + "-12-31";
                break;
        }
        ArchManageVo archManageVo = new ArchManageVo();
        archManageVo.setStartArchDate(startArchDate);
        archManageVo.setEndArchDate(endArchDate);
        return archManageVo;
    }

    /**
     * 多条件查询
     * @param pageNumber
     * @param pageSize
     * @param archDate
     * @param quarter
     * @param month
     * @return
     */
    @Override
    public PageInfo<Publog> queryByConditions(Integer pageNumber, Integer pageSize, String archDate, String quarter, String month) {
        PageHelper.startPage(pageNumber,pageSize);
        /**构造查询条件*/
        Map<String,Object> param = new HashMap<>();
        if(StrUtil.isNotBlank(archDate)) {
            param.put("year",archDate);
        }
        if(StrUtil.isNotBlank(quarter)) {
            String begin = null;
            String center = null;
            String end = null;
            if("1".equals(quarter)) {
                begin = "-01-";
                center = "-02-";
                end = "-03-";
            }else if("2".equals(quarter)) {
                begin = "-04-";
                center = "-05-";
                end = "-06-";
            } else if ("3".equals(quarter)) {
                begin = "-07-";
                center = "-08-";
                end = "-09-";
            }else if ("4".equals(quarter)) {
                begin = "-10-";
                center = "-11-";
                end = "-12-";
            }

            param.put("begin",begin);
            param.put("center",center);
            param.put("end",end);
        }
        if(StrUtil.isNotBlank(month) && !"all".equals(month)){
            if(month.length() == 1) {
                month = "0".concat(month);
            }
            month = "-".concat(month).concat("-");
            param.put("month",month);
        }

        List<Publog> list = publogMapper.selectByConditions(param);
        return new PageInfo<>(list);
    }
}