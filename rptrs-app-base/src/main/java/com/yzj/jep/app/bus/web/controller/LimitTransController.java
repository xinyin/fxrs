/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月03日 09:44:42
 *
 */

package com.yzj.jep.app.bus.web.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.app.bus.model.TraEnclosureVo;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.bus.service.RelFileService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.LimitTrans;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.LimitTransService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/bus/limitTrans")
public class LimitTransController extends CrudController<LimitTrans> {
    private static Logger logger = LoggerFactory.getLogger(LimitTransController.class);

    @Autowired
    private LimitTransService limitTransService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RelFileService relFileService;
    @Autowired
    private TenantService tenantService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "额度调剂", description = "额度调剂获取列表页面")
    public String list(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            model.addAttribute("tranOrg", "");
        } else {
            model.addAttribute("tranOrg", tenantId);
        }
        return "bus/limitTransList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<LimitTrans> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        limitTransService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    @Log(module = "额度调剂", description = "额度调剂新增")
    public String add(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId != null || !"".equals(tenantId)) {

            model.addAttribute("staffName", tenantId);
        }
        return "bus/limitTransAdd";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    @Log(module = "额度调剂", description = "额度调剂编辑")
    public String edit(@PathVariable Integer pid, Model model) {

        LimitTrans limitTrans = limitTransService.get(pid);
        model.addAttribute("limitTrans", limitTrans);
        model.addAttribute("opt", "edit");

        return "bus/limitTransEdit";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    @Log(module = "额度调剂", description = "额度调剂查看")
    public String view(@PathVariable Integer pid, Model model) {

        LimitTrans limitTrans = limitTransService.get(pid);
        model.addAttribute("limitTrans", limitTrans);

        return "bus/limitTransView";
    }

    /**
     * @param limitTrans     额度调剂信息
     * @param traEnclosureVo 附件信息表
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @Log(module = "额度调剂", description = "额度调剂保存")
    public Map<String, Object> save(LimitTrans limitTrans, TraEnclosureVo traEnclosureVo) {

        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = false;
        String relSerial = "";
        if (limitTrans.getPid() != null && !"".equals(limitTrans.getPid())) {
            isExist = limitTransService.exists(limitTrans.getPid());
            LimitTrans limitTrans1 = limitTransService.get(limitTrans.getPid());
            limitTrans.setSeqTrade(limitTrans1.getSeqTrade());
        }
        Integer befPid = limitTrans.getBefPid();
        Integer latPid = limitTrans.getLatPid();
        try {
            if (isExist) {
                limitTransService.updateSelective(limitTrans);
            } else {
                //获取到的proname是一个数组，将以字符串split的形式进行分割，第一级为产品树层级级别，二级为pid，三级为名称，四级为父级pid
                if ("0".equals(limitTrans.getProName().split("-")[0]) || "1".equals(limitTrans.getProName().split("-")[0])) {
                    limitTrans.setProName(null);
                } else {
                    limitTrans.setProName(limitTrans.getProName().split("-")[2]);
                }
                if ("0".equals(limitTrans.getProNameL().split("-")[0]) || "1".equals(limitTrans.getProNameL().split("-")[0])) {
                    limitTrans.setProNameL(null);
                } else {
                    limitTrans.setProNameL(limitTrans.getProNameL().split("-")[2]);
                }

                limitTrans.setStatus("3");//状态改为待提交
                limitTrans.setDelflag("1");//逻辑删除标记
                limitTrans.setSeqTrade(BatchNumberUtil.makeBatchNumber());//绑定的附件上传的流水号
                limitTransService.insert(limitTrans);
            }
            //保存附件信息
            relSerial = limitTrans.getSeqTrade();
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            String merge = limitTrans.getTranOrg();
            relFileService.updateRelSerial(docTableSelectItem, relSerial, merge);

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);

            //当调剂的额度已经修改时,就应该把列表(product)里面的数据的状态改为待审批,
            // 在此条数据审批之后,列表里面的数据状态应该随之改变
            Product product = productService.get(befPid);
            Product product1 = productService.get(latPid);
            product.setApproveStatus("4");//状态改为待提交
            product1.setApproveStatus("4");
            productService.updateSelective(product);
            productService.updateSelective(product1);
            //在审批完成之后一定要把列表(product)状态改为已归档

        } catch (Exception e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

    /**
     * 删除
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/remove/{pid}")
    @ResponseBody
    @Log(module = "额度调剂", description = "额度调剂删除")
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            if (pid != null) {
                LimitTrans limitTrans = limitTransService.get(pid);
                limitTrans.setDelflag("0");//逻辑删除标记
                limitTransService.updateSelective(limitTrans);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return tips;
    }

    /**
     * 验证提交的pid是否已经存在数据库中
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = limitTransService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 提交审批
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    @Log(module = "额度调剂", description = "额度调剂审批")
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        LimitTrans limitTrans = limitTransService.get(pid);
        //状态为待提交或审批退回方可提交
        if ("3".equals(limitTrans.getStatus()) || "5".equals(limitTrans.getStatus())) {
            //4 代表审批中
            limitTrans.setStatus("4");
            limitTransService.updateSelective(limitTrans);
            tips.put("data", limitTrans);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }


    @RequestMapping("/selectbuinessL")
    @ResponseBody
    @Log(module = "额度调剂", description = "额度调剂新增或编辑反显")
    public Map<String, Object> selectbuinessL(String unitCodes) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] split = unitCodes.split("-");
        String level = split[0];//层级级别
        Integer pid = Integer.parseInt(split[1]);//pid
        Product product = productService.get(pid);
        String preLimit1 = product.getPreLimit();//预计额度
        if (preLimit1 == null || "".equals(preLimit1) || !("6".equals(product.getApproveStatus()))) {
            map.put(WebCode.SUCCESS, false);
        } else {
            String preLimit = product.getPreLimit();
            String remLimit = product.getRemLimit();
            String warnLeve = product.getWarnLeve();
            if ("0".equals(level)) {
                map.put("tradesType", pid.toString());
                map.put("businessType", "0");
            } else if ("1".equals(level)) {
                map.put("tradesType", product.getPaProId().toString());
                map.put("businessType", pid.toString());
            } else if ("2".equals(level)) {
                map.put("tradesType", productService.get(productService.get(pid).getPaProId()).getPaProId().toString());
                map.put("businessType", product.getPaProId().toString());
            }
            map.put("preLimit", preLimit);
            map.put("remLimit", remLimit);
            map.put("warnLeve", warnLeve);
            map.put(WebCode.SUCCESS, true);
        }
        return map;
    }

    /**
     * @param preLimit
     * @param pasLimit
     * @param preLimitL
     * @param pasLimitL
     * @param tranAmount
     * @param befPid
     * @param latPid
     * @return
     */
    @RequestMapping(value = "/subtractTranAmount")
    @ResponseBody
    @Log(module = "额度调剂", description = "减去调剂额度")
    public Map<String, Object> subtractTranAmount(Integer preLimit, Integer pasLimit, Integer preLimitL, Integer pasLimitL, Integer tranAmount, Integer befPid, Integer latPid) {
        /*
preLimitLater   预计额度
pasLimitLater   剩余额度
preLimitLaterL  预计额度
pasLimitLaterL  剩余额度
        * */
        Map<String, Object> tips = new HashMap<String, Object>();

        Integer preLimitLater = preLimit - tranAmount;
        Integer pasLimitLater = pasLimit - tranAmount;
        if (preLimitLater > 0 || pasLimitLater > 0) {

            Integer preLimitLaterL = preLimitL + tranAmount;
            Integer pasLimitLaterL = pasLimitL + tranAmount;


            String str1 = limitTransService.warnUse(befPid, (preLimitLater - pasLimitLater) / preLimitLater);
            String str2 = limitTransService.warnUse(latPid, (preLimitLaterL - pasLimitLaterL) / preLimitLaterL);

            tips.put("preLimitLater", preLimitLater);
            tips.put("pasLimitLater", pasLimitLater);
            tips.put("preLimitLaterL", preLimitL + tranAmount);
            tips.put("pasLimitLaterL", pasLimitL + tranAmount);
            tips.put("warnLeveLater", str1);
            tips.put("warnLeveLaterL", str2);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);

        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "调剂金额不得大于预计额度或剩余额度！");
        }
        return tips;
    }

    /*  *//**
     * 额度调剂查询预计额度
     * @param unitCodes
     * @return
     *//*
    @RequestMapping("/selectbuinessL")
    @ResponseBody
    public Map<String, Object> selectDept(String unitCodes) {
        System.out.println(unitCodes);
        Map<String, Object> map = new HashMap<String, Object>();
        String[] split = unitCodes.split("-");
        String level = split[0];
        Integer pid = Integer.parseInt(split[1]);
        Integer paProId = productService.get(pid).getPaProId();
        String status = productService.get(pid).getStatus();
        if ("6".equals(status)){
            //反显交易类型和业务类型
            if (level.equals("0")) {
                map.put("tradesType", pid.toString());
                map.put("businessType", "0");
            } else if (level.equals("1")) {
                map.put("tradesType", productService.get(pid).getPaProId().toString());
                map.put("businessType", pid.toString());
            } else if (level.equals("2")) {
                map.put("tradesType", productService.get(productService.get(pid).getPaProId()).getPaProId().toString());
                map.put("businessType", productService.get(pid).getPaProId().toString());
            }
            map.put(WebCode.SUCCESS, true);
            return map;
        }else{
            map.put(WebCode.SUCCESS, false);
        }


        return map;
    }
*/

}
