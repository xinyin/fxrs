package com.yzj.jep.app.rule.submit;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.service.DocSubmitService;
import com.yzj.jep.orm.mybatis.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 * 自然人管理方校验规则
 * */
@Component
public class OrgDocSubmitRuler {
    @Autowired
    private DocSubmitService docSubmitService;

    /*
     * 校验自然人报送信息
     * orgList  存放自然人数据信息
     * rule：此处校验规则暂时定为 信息未失效
     * in  0代表校验  1代表入库
     * */
    public String checkOrgDocSubmit(RelOrg org) {
        Date dateNow = new Date();
        String str = "";
        if (org.getInvalidTime() == null) {
            str += org.getOrgName() + "无有效期,";
        }
        if (org.getInvalidTime() != null && dateNow.compareTo(org.getInvalidTime()) == 1) {
            str += org.getOrgName() + "已过有效日期,";
        }
        if (StringUtil.isEmpty(org.getUniscCode())) {
            str += org.getOrgName() + "社会统一信用代码信息为空;";
        }
        return str;
    }
}
