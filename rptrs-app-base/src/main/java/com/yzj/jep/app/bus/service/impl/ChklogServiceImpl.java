package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.ChklogMapper;
import com.yzj.jep.app.bus.model.Chklog;
import com.yzj.jep.app.bus.service.ChklogService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ChklogServiceImpl implements ChklogService {

    @Autowired
    private ChklogMapper chklogMapper;

    @Override
    public List<Integer> selectByisinsder0() {
        List<Integer> list1 = chklogMapper.selectbyisinsderbylevela();

        List<Integer> list3 = chklogMapper.selectbyisinsderbytyped();


        List<Integer> list2 = chklogMapper.selectbyisinsderbylevelf();

        List<Integer> list4 = chklogMapper.selectbyisinsderbytypei();


        List<Integer> list = new ArrayList<>();

        list1.addAll(list3);
        list2.addAll(list4);


        list.addAll(list1);
        list.addAll(list2);
        return list;
    }
}
