package com.yzj.jep.app.ins.web.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.ins.constants.TranTypeConstant;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.web.code.WebCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;


/**
 * @author by Liqiang
 * @Date 2022/11/16
 * @Description 调整关联交易经办人
 */

@Controller
@RequestMapping("/ins/tran")
public class TranManagementController {

    @Resource
    private TraFundsService traFundsService;
    @Resource
    private TraServiceService traServiceService;
    @Resource
    private TraInterestService traInterestService;
    @Resource
    private TraBusinessService traBusinessService;

    /**
     * 结果中的记录总数
     */
    protected static final String RESULT_TOTAL = "total";

    /**
     * 结果中的记录行数
     */
    protected static final String RESULT_ROWS = "rows";

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/traFunds")
    public String traFunds(Model model) {

        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "ins/tran/traFunds";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "ins/tran/traFundsList";
    }


    /**
     * 资金运用类-为列表页面做数据准备
     */
    @RequestMapping(value = "/queryFundsPage", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    protected Map<String, Object> prepareFundsQuery(Page<TraFunds> page, String transNum) {
        Map<String, Object> result = new HashMap<>(2);
        PageHelper.start(page);
        List<TraFunds> resultList = traFundsService.getTranNum(transNum);
        resultList.forEach(e -> e.setRelNumber(idEncrypt(e.getRelNumber())));
        PageInfo pageInfo = new PageInfo(resultList);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 跳转查看页面
     * @param pid               ID
     * @param tranType          交易类型
     * @param model             model对象
     * @param request           request对象
     * @return                  返回数据
     */

    @RequestMapping(value = "/view/{pid}/{tranType}")
    public String view(@PathVariable Integer pid, @PathVariable String tranType, Model model, HttpServletRequest request) {
        Commons.common2(request, model);
        if (TranTypeConstant.TRAN_TYPE_1.equals(tranType)) {
            TraFunds traFunds = traFundsService.get(pid);
            //获取交易对手列表信息
            model.addAttribute("traFunds", traFunds);
            model.addAttribute("opt", "view");

            return "ins/traFundsEdit";
        } else if (TranTypeConstant.TRAN_TYPE_2.equals(tranType)) {
            TraService traService = traServiceService.get(pid);
            //获取交易对手列表信息
            model.addAttribute("traService", traService);
            model.addAttribute("opt", "view");

            return "ins/traServiceEdit";
        } else if (TranTypeConstant.TRAN_TYPE_3.equals(tranType)) {
            TraInterest traInterest = traInterestService.get(pid);
            //获取交易对手列表信息
            model.addAttribute("traInterest", traInterest);
            model.addAttribute("opt", "view");

            return "ins/traInterestEdit";
        }
        TraBusiness traBusiness = traBusinessService.get(pid);
        //获取交易对手列表信息
        model.addAttribute("traBusiness", traBusiness);
        model.addAttribute("opt", "view");

        return "ins/traBusinessEdit";
    }


    /**
     * 服务类关联交易-获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/traService")
    public String traService(Model model) {
        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "ins/tran/traService";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "ins/tran/traServiceList";
    }


    /**
     * 服务类-为列表页面做数据准备
     */
    @RequestMapping(value = "/queryServicePage", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    protected Map<String, Object> prepareServiceQuery(Page<TraService> page, String transNum) {
        Map<String, Object> result = new HashMap<>(2);
        PageHelper.start(page);
        List<TraService> resultList = traServiceService.getTranNum(transNum);
        resultList.forEach(e -> e.setRelNumber(idEncrypt(e.getRelNumber())));
        PageInfo pageInfo = new PageInfo(resultList);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }


    /**
     * 利益转移类关联交易-获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/traInterest")
    public String traInterest(Model model) {

        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "ins/tran/traInterest";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "ins/tran/traInterestList";
    }

    /**
     * 利益转移类-为列表页面做数据准备
     */
    @RequestMapping(value = "/queryInterestPage", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    protected Map<String, Object> prepareInterestQuery(Page<TraInterest> page, String transNum) {
        Map<String, Object> result = new HashMap<>(2);
        PageHelper.start(page);
        List<TraInterest> resultList = traInterestService.getTranNum(transNum);
        resultList.forEach(e -> e.setRelNumber(idEncrypt(e.getRelNumber())));
        PageInfo pageInfo = new PageInfo(resultList);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 保险业务和其他类关联交易-获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/traBusiness")
    public String traBusiness(Model model) {

        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "ins/tran/traBusiness";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "ins/tran/traBusinessList";
    }

    /**
     * 保险业务和其他类-为列表页面做数据准备
     */
    @RequestMapping(value = "/queryBusinessPage", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    protected Map<String, Object> prepareBusinessQuery(Page<TraBusiness> page, String transNum) {
        Map<String, Object> result = new HashMap<>(2);
        PageHelper.start(page);
        List<TraBusiness> resultList = traBusinessService.getTranNum(transNum);
        resultList.forEach(e -> e.setRelNumber(idEncrypt(e.getRelNumber())));
        PageInfo pageInfo = new PageInfo(resultList);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 为更新经办人做准备
     */
    @RequestMapping(value = "/changeAmend/{data}")
    protected String changeAmend(@PathVariable String data, Model model) {
        model.addAttribute("data", data);
        String pid = data.split(",")[0];
        String amendId = data.split(",")[1];
        String amendName = data.split(",")[2];
        String tranType = data.split(",")[3];
        model.addAttribute("pid", pid);
        model.addAttribute("staffCode", amendId);
        model.addAttribute("amendName", amendName);
        model.addAttribute("relRandom", "relTran" + BatchNumberUtil.makeBatchNumber());
        model.addAttribute("tranType", tranType);
        return "ins/tran/changeAmend";
    }


    /**
     * 更新经办人
     *
     * @param staffCode 人员ID
     * @param amendName 人员名称
     * @param pid       交易ID
     * @param tranType  交易类型
     * @return 返回结果
     */
    @RequestMapping(value = "/updataAmend")
    @ResponseBody
    public Map<String, Object> updataAmend(String staffCode, String amendName, int pid, String tranType) {
        Map<String, Object> tips = new HashMap<>(2);

        if (StringUtils.isBlank(staffCode) || StringUtils.isBlank(amendName) || ObjectUtil.isEmpty(pid)) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据参数不正确！");
            return tips;
        }

        if (TranTypeConstant.TRAN_TYPE_1.equals(tranType)) {
            TraFunds funds = new TraFunds();
            funds.setAmendId(staffCode);
            funds.setAmendName(amendName);
            funds.setPid(pid);
            traFundsService.updateSelective(funds);
        }
        if (TranTypeConstant.TRAN_TYPE_2.equals(tranType)) {
            TraService service = new TraService();
            service.setAmendId(staffCode);
            service.setAmendName(amendName);
            service.setPid(pid);
            traServiceService.updateSelective(service);
        }
        if (TranTypeConstant.TRAN_TYPE_3.equals(tranType)) {
            TraInterest interest = new TraInterest();
            interest.setAmendId(staffCode);
            interest.setAmendName(amendName);
            interest.setPid(pid);
            traInterestService.updateSelective(interest);
        }
        if (TranTypeConstant.TRAN_TYPE_4.equals(tranType)) {
            TraBusiness business = new TraBusiness();
            business.setAmendId(staffCode);
            business.setAmendName(amendName);
            business.setPid(pid);
            traBusinessService.updateSelective(business);
        }
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        return tips;
    }

}
