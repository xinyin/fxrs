package com.yzj.jep.app.rule.submit;

import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.DocSubmitService;
import com.yzj.jep.orm.mybatis.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * 自然人管理方校验规则
 * */
@Component
public class PsnDocSubmitRuler {
    @Autowired
    private DocSubmitService docSubmitService;

    /*
     * 校验自然人报送信息
     * psnList  存放自然人数据信息
     * rule：此处校验规则暂时定为 信息未失效
     * in  0代表校验  1代表入库
     * */
    public String checkPsnDocSubmit(RelPsn psn) {
        Date dateNow = new Date();
        String str = "";
        if (psn.getInvalidTime() == null) {
            str += psn.getName() + "无有效期;";
        }
        if (psn.getInvalidTime() != null && dateNow.compareTo(psn.getInvalidTime()) == 1) {
            str += psn.getName() + "已过有效日期;";
        }
        if (StringUtil.isEmpty(psn.getIdentifyNumber())) {
            str += psn.getName() + "身份证信息为空;";
        }
        return str;
    }
}
