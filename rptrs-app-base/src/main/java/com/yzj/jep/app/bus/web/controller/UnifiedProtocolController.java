package com.yzj.jep.app.bus.web.controller;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.service.UnifiedProtocolService;
import com.yzj.jep.app.bus.vo.ProtocolVo;
import com.yzj.jep.app.bus.vo.RelFundsVo;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yzj.jep.app.bus.service.RelFundsService;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: UnifiedProtocolController
 * @Author Whx
 * @Date: 2021/7/16 16:01
 * @Version 1.0
 * 统一协议执行情况统计    表  ins_tra_business  ins_tra_interest   ins_tra_service
 */
@Controller
@RequestMapping("/bus/protocol")
public class UnifiedProtocolController {

    @Autowired
    private UnifiedProtocolService unifiedProtocolService;

    @Autowired
    private RelFundsService relFundsService;

    /**
     * 跳转到列表页面
     *
     * @return
     */
    @RequestMapping("/list")
    public String ToListView() {
        return "bus/protocolList";
    }

    @RequestMapping("/queryByConditions")
    @ResponseBody
    public Map<String, Object> queryByConditions(Integer pageNumber, Integer pageSize, String year, String quarter, String contractNo) {
        Map<String, Object> result = new HashMap<>();
        PageInfo<ProtocolVo> pageInfo = unifiedProtocolService.queryByConditions(pageNumber, pageSize, year, quarter, contractNo);
        result.put("total", pageInfo.getTotal());
        result.put("rows", pageInfo.getList());
        return result;
    }

    @RequestMapping("/isAgreementImpl")
    @ResponseBody
    public Map<String,Object> isAgreementImpl(Page<ProtocolVo> page , HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String relNumber = request.getParameter("__LIKES_relNumber");//关联方号码
        String relName = request.getParameter("__LIKES_relName");//关联方名称
        PageHelper.start(page);
        List<ProtocolVo> list = unifiedProtocolService.getIsAgreementImpl(relNumber,relName);
        PageInfo pageInfo = new PageInfo(list);
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
        return map;

    }
}
