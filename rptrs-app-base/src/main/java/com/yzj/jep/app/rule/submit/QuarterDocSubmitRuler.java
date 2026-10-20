package com.yzj.jep.app.rule.submit;

import com.yzj.jep.app.bus.vo.QuarterSubmitVo;
import com.yzj.jep.orm.mybatis.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 * 季度报送 校验规则
 * */
@Component
public class QuarterDocSubmitRuler {
    /*
     * 校验自然人报送信息
     * psnList  存放自然人数据信息
     * rule：此处校验规则暂时定为 信息未失效
     * in  0代表校验  1代表入库
     * */
    public String checkPsnDocSubmit(QuarterSubmitVo vo) {
        Date dateNow = new Date();
        String str = "";
        if (vo.getActiveTime() == null) {
            str += vo.getOrgName() + "未填写有效日期;";
        }
        if (vo.getActiveTime() != null && dateNow.compareTo(vo.getActiveTime()) == 1) {
            str += vo.getOrgName() + "已过有效日期;";
        }
        if (StringUtil.isEmpty(vo.getUniscCode())) {
            str += vo.getOrgName() + "身份证信息为空;";
        }
        return str;
    }
}
