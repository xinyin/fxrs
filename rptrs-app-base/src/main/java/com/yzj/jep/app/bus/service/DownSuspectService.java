package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.suspect.DownSuspect;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DownSuspectService {
    /*
     * 获取自然人的下级信息
     * */
    List<DownSuspect> getDownPsnInfo(Integer pid);
}
