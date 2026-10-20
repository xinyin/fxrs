//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yzj.jep.app.sys.service.impl;
import com.yzj.jep.app.sys.model.ApproveMap;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.app.sys.service.ApproveMapService ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yzj.jep.app.sys.mapper.ApproveMapper;
@Service("approveMapService")
@Transactional(
        readOnly = true
)
public class ApproveMapServiceImpl extends MyBatisCRUDService<ApproveMap, Integer> implements ApproveMapService {
    Logger logger = LoggerFactory.getLogger(ApproveMapServiceImpl.class);
    @Autowired
     ApproveMapper  approveMapper;

    public ApproveMapServiceImpl() {
    }

    public Mapper<ApproveMap, Integer> getMapper() {
        return this.approveMapper;
    }
}
