package com.yzj.jep.app.rule.submit;

import com.yzj.jep.app.bus.vo.QuarterSubmitVo;
import com.yzj.jep.app.bus.vo.TraSubmitVo;
import com.yzj.jep.orm.mybatis.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 * 重大关联交易 校验规则
 * */
@Component
public class TraDocSubmitRuler {

    public String checkTraDocSubmit(TraSubmitVo vo) {
        Date dateNow = new Date();
        String str = "";
        if (vo.getValidityContract() == null) {
            str += vo.getName() + "未填写合同有效日期;";
        }
        if (vo.getValidityContract() != null && dateNow.compareTo(vo.getValidityContract()) == 1) {
            str += vo.getName() + "已过合同有效日期;";
        }
        if (StringUtil.isEmpty(vo.getIdentNumber())) {
            str += vo.getName() + "证件信息为空;";
        }
        return str;
    }
}
