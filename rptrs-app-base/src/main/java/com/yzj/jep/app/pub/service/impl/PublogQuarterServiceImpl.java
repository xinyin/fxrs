package com.yzj.jep.app.pub.service.impl;

import com.yzj.jep.app.pub.mapper.PublogQuarterMapper;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.PublogQuarterService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PublogQuarterServiceImpl  extends MyBatisCRUDService<PublogRegularVo, Integer> implements PublogQuarterService {
    @Autowired
    private PublogQuarterMapper publogQuarterMapper;

    @Override
    public Mapper<PublogRegularVo, Integer> getMapper() {
        return publogQuarterMapper;
    }


    @Override
    public List<PublogRegularVo> getQuarterList(Map<String,String> map){
        return publogQuarterMapper.getQuarterList(map.get("startTime"),map.get("endTime"));
    }
    @Override
    public List<PublogRegularVo> getQuarterList1(Map<String,String> map){
        return publogQuarterMapper.getQuarterList1(map.get("startTime"),map.get("endTime"));
    }
    public Map<String ,String > getTime(String quarters,String years){
        Map<String ,String > map=new HashMap<>();
        switch (quarters){
            case "1" :
                map.put("startTime",years+"-01-01");
                map.put("endTime",years+"-03-31");
                break;
            case "2":
                map.put("startTime",years+"-04-01");
                map.put("endTime",years+"-06-30");
                break;
            case "3":
                map.put("startTime",years+"-07-01");
                map.put("endTime",years+"-09-30");
                break;
            case "4":
                map.put("startTime",years+"-10-01");
                map.put("endTime",years+"-12-31");
                break;
            default:
                map.put("startTime",years+"-01-01");
                map.put("endTime",years+"-12-31");
        }
        map.put("year",years);
        return map;
    }
}
