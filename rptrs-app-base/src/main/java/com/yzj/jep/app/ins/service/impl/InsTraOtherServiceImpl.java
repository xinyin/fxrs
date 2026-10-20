/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月08日 19:41:07
 *
 */

package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.vo.QuarterListVo;
import com.yzj.jep.app.ins.mapper.InsTraOtherMapper;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.model.TraOther;
import com.yzj.jep.app.ins.service.TraOtherService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InsTraOtherServiceImpl extends MyBatisCRUDService<TraOther, Integer> implements TraOtherService {
	
	Logger	logger	= LoggerFactory.getLogger(InsTraOtherServiceImpl.class);
	
	@Autowired
	private InsTraOtherMapper traOtherMapper;
	
	@Override
	public Mapper<TraOther, Integer> getMapper() {
		return traOtherMapper;
	}

	@Override
	public BigDecimal getCurrYearTotalTradeAmount(Map map) {
		return traOtherMapper.getCurrYearTotalTradeAmount(map);
	}

    @Override
    public List<TraOther> selectByYearAndQuarter(String subYear, String subQuarter, String subOrg) {
        return traOtherMapper.selectByYearAndQuarter(subYear,subQuarter,subOrg);
    }
    @Override
    public List<TraOther> selectHisByYearAndQuarter(String subYear, String subQuarter, String subOrg) {
        return traOtherMapper.selectHisByYearAndQuarter(subYear,subQuarter,subOrg);
    }

	@Override
	public BigDecimal getInRoadTotalTradeAmount(Map map) {
		return traOtherMapper.getInRoadTotalTradeAmount(map);
	}

	@Override
	public void grandTradeZero(Map map) {
		traOtherMapper.grandTradeZero(map);
	}

	@Override
	public BigDecimal getAllSumTradeAmount() {
		return traOtherMapper.getAllSumTradeAmount();
	}

	@Override
	public List<QuarterListVo> getOther(Map<String, Object> map) {
		return traOtherMapper.getOther(map);
	}

    @Override
    public PageInfo<TraOther> queryBatchSubmit(Integer pageNumber, Integer pageSize, String year, String quarter) {
		PageHelper.startPage(pageNumber,pageSize);
		//构造查询条件
		Map<String,Object> param = new HashMap<>();
		if(StrUtil.isNotBlank(year)){
			param.put("year",year.concat("%"));
		}else {
			Integer yearInt = DateUtil.year(new Date());
			param.put("year",String.valueOf(yearInt).concat("%"));
		}
		if(StrUtil.isNotBlank(quarter)){
			String begin=null;
			String center=null;
			String end=null;
			if("1".equals(quarter)){
				begin = "%".concat("-01-").concat("%");
				center = "%".concat("-02-").concat("%");
				end = "%".concat("-03-").concat("%");
			}else if("2".equals(quarter)){
				begin = "%".concat("-04-").concat("%");
				center = "%".concat("-05-").concat("%");
				end = "%".concat("-06-").concat("%");
			}else if("3".equals(quarter)){
				begin = "%".concat("-07-").concat("%");
				center = "%".concat("-08-").concat("%");
				end = "%".concat("-09-").concat("%");
			}else {
				begin = "%".concat("-10-").concat("%");
				center = "%".concat("-11-").concat("%");
				end = "%".concat("-12-").concat("%");
			}
			param.put("begin",begin);
			param.put("center",center);
			param.put("end",end);
		}
		List<TraOther> list= traOtherMapper.queryByConditions(param);
		return new PageInfo<>(list);
    }



	@Override
	public TraOther traContractNo(String contractNo) {
		return traOtherMapper.traOtherNo(contractNo);
	}

	@Override
	public Boolean updateByTradeAmount(Integer pid, Date contractDate, String relTrandeAmount) {
		return traOtherMapper.updateByTradeAmount(pid,contractDate,relTrandeAmount);
	}

	@Override
	public List<TraOther> traContractNoList(String contractNo) {
		return traOtherMapper.traOtherList(contractNo);
	}



}