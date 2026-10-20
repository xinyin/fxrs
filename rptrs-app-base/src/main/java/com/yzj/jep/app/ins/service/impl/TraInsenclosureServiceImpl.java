/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月22日 11:31:54
 *
 */

package com.yzj.jep.app.ins.service.impl;

import com.yzj.jep.app.ins.mapper.TraInsenclosureMapper;
import com.yzj.jep.app.ins.model.BusinessTypeVo;
import com.yzj.jep.app.ins.model.TraInsenclosure;
import com.yzj.jep.app.ins.service.TraInsenclosureService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TraInsenclosureServiceImpl extends MyBatisCRUDService<TraInsenclosure, Integer> implements TraInsenclosureService {

    Logger logger = LoggerFactory.getLogger(TraInsenclosureServiceImpl.class);

    @Autowired
    private TraInsenclosureMapper traInsenclosureMapper;

    @Override
    public Mapper<TraInsenclosure, Integer> getMapper() {
        return traInsenclosureMapper;
    }

    @Override
    public void updateTransNum(List<Integer> docTableSelectItem, String transNum) {
        if (docTableSelectItem != null) {
            for (Integer pid : docTableSelectItem) {
                TraInsenclosure traEnclosure = new TraInsenclosure();
                traEnclosure.setPid(pid);
                traEnclosure.setTransNum(transNum);
                updateSelective(traEnclosure);
            }
        }
    }

    @Override
    public void setTypeValue(String typeCode, String type, BusinessTypeVo businessTypeVo) {
        if("Y01".equals(typeCode) ){
            if("service".equals(type)){
                businessTypeVo.setType("服务类交易的协议");
            }else if("interest".equals(type)){
                businessTypeVo.setType("利益转移类交易的协议");
            }else if("fund".equals(type)){
                businessTypeVo.setType("资金运用类交易的协议");
            }else if("business".equals(type)){
                businessTypeVo.setType("交易协议");
            }
        }
        if("fund".equals(type)){
            if("Z02".equals(typeCode)){
                businessTypeVo.setBeiFeng("如关联交易涉及填报机构控股股东及其关联方的，则必须上传");
            }else if("Z03".equals(typeCode)){
                businessTypeVo.setBeiFeng("如关联交易涉及填报机构控股股东及其关联方的，则必须上传");
                businessTypeVo.setType("填报机构控股股东及其关联方上季度末征信评级报告");
            }else if("Z05".equals(typeCode)){
                businessTypeVo.setBeiFeng("如有中介服务机构出具的专业报告，则必须上传");
            }
        }else if("business".equals(type)){
            if("Z02".equals(typeCode)){
                businessTypeVo.setBeiFeng("如关联交易涉及填报机构控股股东及其关联方的，需提供");
            }else if("Z03".equals(typeCode)){
                businessTypeVo.setBeiFeng("如关联交易涉及填报机构控股股东及其关联方的，需提供");
            }else if("Z05".equals(typeCode)){
                businessTypeVo.setBeiFeng("如交易中涉及，则必须上传。");
            }else if("Z06".equals(typeCode)){
                businessTypeVo.setBeiFeng("如有其他补充说明文档，则可以上传。");
            }
        }else if("service".equals(type)){
            if("Z02".equals(typeCode)){
                businessTypeVo.setBeiFeng("如关联交易涉及填报机构控股股东及其关联方的，需提供");
            }else if("Z03".equals(typeCode)){
                businessTypeVo.setBeiFeng("如关联交易涉及填报机构控股股东及其关联方的，需提供");
            }else if("Z05".equals(typeCode)){
                businessTypeVo.setBeiFeng("如交易中涉及，则必须上传。");
            }else if("Z06".equals(typeCode)){
                businessTypeVo.setBeiFeng("如有其他补充说明文档，则可以上传。");
            }else if("Y01".equals(typeCode)){
                businessTypeVo.setBeiFeng("如为统一交易协议，则此处上传统一交易协议");
            }
        }else if("interest".equals(type)){
            if("Z02".equals(typeCode)){
                businessTypeVo.setBeiFeng("如关联交易涉及填报机构控股股东及其关联方的，则必须上传。");
            }else if("Z03".equals(typeCode)){
                businessTypeVo.setBeiFeng("如关联交易涉及填报机构控股股东及其关联方的，则必须上传。");
            }else if("Y01".equals(typeCode)){
                businessTypeVo.setBeiFeng("如为统一交易协议，则此处上传统一交易协议");
            }
        }
    }
}
