package com.yzj.jep.app.pub.service;

import com.yzj.jep.app.pub.model.PublogRegularVo;

import java.util.List;

public interface FundsRegularService {
    public List<PublogRegularVo> getAll();
    public List<PublogRegularVo> getConsiderTypeOne();
}
