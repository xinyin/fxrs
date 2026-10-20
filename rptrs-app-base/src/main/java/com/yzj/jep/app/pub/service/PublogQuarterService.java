package com.yzj.jep.app.pub.service;

import com.yzj.jep.app.pub.model.PublogRegularVo;

import java.util.List;
import java.util.Map;

public interface PublogQuarterService {
    public List<PublogRegularVo> getQuarterList(Map<String,String> map);
    public Map<String ,String > getTime(String quarter,String year);
    public List<PublogRegularVo> getQuarterList1(Map<String,String> map);
}
