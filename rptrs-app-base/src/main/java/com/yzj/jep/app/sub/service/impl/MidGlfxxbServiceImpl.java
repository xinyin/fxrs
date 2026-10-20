/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2025 pactera.com. All rights reserved.
 *
 * Generated at 2025年01月23日 14:55:45
 *
 */

package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.enums.*;
import com.yzj.jep.app.sub.model.MidGlfxxb;
import com.yzj.jep.app.util.DateExtendUtil;
import com.yzj.jep.app.util.DictUtil;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.sub.mapper.MidGlfxxbMapper;
import com.yzj.jep.app.sub.service.MidGlfxxbService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;


@Service
@Transactional
public class MidGlfxxbServiceImpl extends MyBatisCRUDService<MidGlfxxb, String> implements MidGlfxxbService {

	Logger	logger	= LoggerFactory.getLogger(MidGlfxxbServiceImpl.class);

	@Resource
	private MidGlfxxbMapper midGlfxxbMapper;
	@Resource
	private RelPsnService relPsnService;
	@Resource
	private RelOrgService relOrgService;

	@Override
	public Mapper<MidGlfxxb, String> getMapper() {
		return midGlfxxbMapper;
	}

	/**
	 * East关联方
	 *
	 * @param batchNo
	 */
	@Override
	public void createEastMidGlfxxb(String batchNo) {
		//取上月日期区间
		Calendar cal = CalendarUtil.calendar(new Date());
		cal.add(Calendar.MONTH, -1);
		Date lastMonth = cal.getTime();
		List<Filter> filters = new Filter.Builder()
				.eq("delflag", "1")
				.build();
		List<RelPsn> psnList = relPsnService.select(filters);
		//按照需求，使用证件号码去重
		Map<String, RelPsn> relPsnMap = psnList.stream().collect(Collectors.toMap(RelPsn::getIdentifyNumber, obj -> obj, BinaryOperator.maxBy(Comparator.comparing(RelPsn::getModifiedTime))));
		//公共字典初始化
		/**
		 *	境内、境外 BUS-DomesticOrForeign
		 *	注册资本币种  BUS-regCapitalCurrency
		 *  是否存在有效关系  BUS-EffectRelation
		 */
		Map<String, String> domesticOrForeignMap = DictUtil.convertToMap("BUS-DomesticOrForeign");
		//处理数据
		relPsnMap.forEach((k, relPsn) -> {
			MidGlfxxb midGlfxxb = new MidGlfxxb();
			//East要求公共字段
			midGlfxxb.setSerialno(batchNo+relPsn.getPid());
			midGlfxxb.setBatchno(batchNo);
			midGlfxxb.setBussno(relPsn.getIdentifyNumber());
			midGlfxxb.setMakedate(new Date());
			midGlfxxb.setBussdate(new Date());
			midGlfxxb.setDatasrc(RdsDataSrc.GLJY.getValue());
			midGlfxxb.setOthersign("");
			midGlfxxb.setRemark("");
			midGlfxxb.setBxjgmc("");
			midGlfxxb.setBxjgmc("");
			//业务字段
			//关联方代码	GLFDM
			midGlfxxb.setGlfdm(relPsn.getIdentifyNumber());
			//关联方名称	GLFMC
			midGlfxxb.setGlfmc(relPsn.getName());
			//境内外性质	JNWXZ
			midGlfxxb.setJnwxz(domesticOrForeignMap.get(relPsn.getDomesticOrForeign()));
			//关联方类型	GLFLX
			midGlfxxb.setGlflx(EastRelType.PSN.getValue());
			//证件类型	ZJLX
			midGlfxxb.setZjlx(convertPsnIdTypeToEast(relPsn.getIdentifyType()));
			//证件类型中文名称	ZJLXZWMC
			midGlfxxb.setZjlxzwmc(EastPsnIdType.getLabelByValue(midGlfxxb.getZjlx()));
			//关联方证件号码	GLFZJHM
			midGlfxxb.setGlfzjhm(relPsn.getIdentifyNumber());
			//经济性质或类型	JJXZHLX
			//主营业务或经营范围	ZYYWHJYFW
			//法定代表人姓名	FDDBRXM
			//注册地	ZCD
			//注册资本	ZCZB
			//注册资本币种	ZCZBBZ
			//有效标志	YXBZ
			midGlfxxb.setYxbz(Long.valueOf(relPsn.getEffectRelation()));
			//采集日期	CJRQ
			midGlfxxb.setCjrq(DateExtendUtil.endOfLastMonth(new Date()));
			//关联交易系统公共字段
			midGlfxxb.setCreateTime(new Date());
			midGlfxxb.setModifiedTime(new Date());
			this.insert(midGlfxxb);
		});
		//排除复星保德信本身
		filters.add(new Filter("uniscCode", Operator.NE, "9131000005459537XP"));
		List<RelOrg> orgList = relOrgService.select(filters);
		Map<String, RelOrg> relOrgMap = orgList.stream().collect(Collectors.toMap(RelOrg::getUniscCode, obj -> obj, BinaryOperator.maxBy(Comparator.comparing(RelOrg::getModifiedTime))));
		//法人关联方
		relOrgMap.forEach((k, relOrg) -> {
			MidGlfxxb midGlfxxb = new MidGlfxxb();
			//East要求公共字段
			midGlfxxb.setSerialno(batchNo+relOrg.getPid());
			midGlfxxb.setBatchno(batchNo);
			midGlfxxb.setBussno(relOrg.getUniscCode());
			midGlfxxb.setMakedate(new Date());
			midGlfxxb.setBussdate(new Date());
			midGlfxxb.setDatasrc(RdsDataSrc.GLJY.getValue());
			midGlfxxb.setOthersign("");
			midGlfxxb.setRemark("");
			midGlfxxb.setBxjgmc("");
			midGlfxxb.setBxjgmc("");
			//业务字段
			//关联方代码	GLFDM
			midGlfxxb.setGlfdm(relOrg.getUniscCode());
			//关联方名称	GLFMC
			midGlfxxb.setGlfmc(relOrg.getOrgName());
			//境内外性质	JNWXZ
			midGlfxxb.setJnwxz(domesticOrForeignMap.get(relOrg.getDomesticOrForeign()));
			//关联方类型	GLFLX
			midGlfxxb.setGlflx(EastRelType.ORG.getValue());
			//证件类型	ZJLX
			midGlfxxb.setZjlx(convertOrgIdTypeToEast(relOrg.getIdentifyType()));
			//证件类型中文名称	ZJLXZWMC
			midGlfxxb.setZjlxzwmc(EastOrgIdType.getLabelByValue(midGlfxxb.getZjlx()));
			//关联方证件号码	GLFZJHM
			midGlfxxb.setGlfzjhm(relOrg.getUniscCode());
			//经济性质或类型	JJXZHLX
			midGlfxxb.setJjxzhlx(relOrg.getOrgType());
			//主营业务或经营范围	ZYYWHJYFW
			midGlfxxb.setZyywhjyfw(relOrg.getBusinessScope());
			//法定代表人姓名	FDDBRXM
			midGlfxxb.setFddbrxm(relOrg.getLegalPerson());
			//注册地	ZCD
			midGlfxxb.setZcd(relOrg.getRegAddress());
			//注册资本	ZCZB
			Float regCapital = relOrg.getRegCapital();
			if (ObjectUtil.isNotNull(regCapital)) {
				midGlfxxb.setZczb(new BigDecimal(String.valueOf(regCapital)));
			}
			//注册资本币种	ZCZBBZ
			midGlfxxb.setZczbbz(relOrg.getRegCapitalCurrency());
			//有效标志	YXBZ
			midGlfxxb.setYxbz(Long.valueOf(relOrg.getEffectRelation()));
			// 采集日期	CJRQ
			midGlfxxb.setCjrq(DateExtendUtil.endOfLastMonth(new Date()));
			//公共字段
			midGlfxxb.setCreateTime(new Date());
			midGlfxxb.setModifiedTime(new Date());
			this.insert(midGlfxxb);
		});
	}

	/**
	 * 证件类型转换为East
	 * @param identifyType
	 * @return
	 */
	private String convertOrgIdTypeToEast(String identifyType) {
		/**
		 *
		 */
		if (ObjectUtil.equals(identifyType, OrgIdType.UNIFIED_SOCIAL_CREDIT_CODE.getValue())) {
			return EastOrgIdType.UNIFIED_SOCIAL_CREDIT_CODE.getValue();
		} else if (ObjectUtil.equals(identifyType, OrgIdType.OTHER.getValue())) {
			return EastOrgIdType.OTHER_CERTIFICATE.getValue();
		} else if (ObjectUtil.equals(identifyType, OrgIdType.BUSINESS_REGISTRATION_NUMBER.getValue())) {
			return EastOrgIdType.BUSINESS_LICENSE.getValue();
		} else if (ObjectUtil.equals(identifyType, OrgIdType.FOREIGN_VALID_CODE.getValue())) {
			return EastOrgIdType.FOREIGN_REGISTRATION_PROOF.getValue();
		}
		return EastOrgIdType.OTHER_CERTIFICATE.getValue();
	}

	/**
	 * 证件类型转换为East代码
	 * @param identifyType
	 * @return
	 */
	private String convertPsnIdTypeToEast(String identifyType) {
		/**
		 * 映射规则
		 * 1 居民身份证 1010 居民身份证
		 * 2居民户口簿 1040 户口簿
		 * 4护照 1050 护照
		 * 3军官证 1022 中国人民解放军军官证
		 * 5其他证件 1999 其他证件（个人）
		 */
		if (ObjectUtil.equals(identifyType, PsnIdType.RESIDENT_ID_CARD.getValue())) {
			return EastPsnIdType.RESIDENT_ID_CARD.getValue();
		} else if (ObjectUtil.equals(identifyType, PsnIdType.HOUSEHOLD_REGISTER.getValue())) {
			return EastPsnIdType.HOUSEHOLD_REGISTER.getValue();
		} else if (ObjectUtil.equals(identifyType, PsnIdType.PASSPORT.getValue())) {
			return EastPsnIdType.PASSPORT.getValue();
		} else if (ObjectUtil.equals(identifyType, PsnIdType.MILITARY_OFFICER_CARD.getValue())) {
			return EastPsnIdType.MILITARY_OFFICER_CARD.getValue();
		} else if (ObjectUtil.equals(identifyType, PsnIdType.OTHER_DOCUMENTS.getValue())) {
			return EastPsnIdType.OTHERS.getValue();
		}
		return EastPsnIdType.OTHERS.getValue();
	}
}
