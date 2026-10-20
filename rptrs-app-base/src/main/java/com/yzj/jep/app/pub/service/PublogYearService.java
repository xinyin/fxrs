package com.yzj.jep.app.pub.service;

import com.yzj.jep.app.pub.model.PublogRegularVo;

import java.util.List;
import java.util.Map;

public interface PublogYearService {
    public List<PublogRegularVo> getYearList(Map<String,String> map);
}
