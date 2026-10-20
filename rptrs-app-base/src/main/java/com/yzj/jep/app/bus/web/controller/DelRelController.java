/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月22日 11:51:25
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.DelRel;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.DelRelService;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.util.ConfidentialInfoUtil;
import com.yzj.jep.app.util.TraUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;
import static com.yzj.jep.app.util.ConfidentialInfoUtil.otherIdEncrypt;

@Controller
@RequestMapping("/bus/delRel")
public class DelRelController extends CrudController<DelRel> {
    private static Logger logger = LoggerFactory.getLogger(DelRelController.class);

    @Autowired
    private DelRelService delRelService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/delRelList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DelRel> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag",Operator.EQ, "1"));

        delRelService.query(page, filters, orders);

        List<DelRel> result = page.getResult();
        //获取法人或其他组织证件类型字典
        List<DictItem> orgDicItems = TraUtil.getDictItems("BUS-ORGIdentifyType");
        //获取自然人关联方证件类型字典
        List<DictItem> psnDicItems = TraUtil.getDictItems("BUS-IdentifyType");
        // 获取金融产品产品类型字典
        List<DictItem> proTypeItemList = TraUtil.getDictItems("BUS-pro");
        //根据关联方类型，查询证件类型
        for (DelRel delRel : result) {
            //使用工具类让证件号码/手机号/姓名*******************显示
//            delRel.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(delRel.getIdentifyNumber()));
            //关联方类型为自然人
            if (delRel.getType().equals("PSN")) {
                //证件号码脱敏，身份证保留前6后3位，其余保留前3后2位
                if (delRel.getIdentifyType().equals("1")) {
                    delRel.setIdentifyNumber(idEncrypt(delRel.getIdentifyNumber()));
                }else{
                    delRel.setIdentifyNumber(otherIdEncrypt(delRel.getIdentifyNumber()));
                }
                String identifyType = TraUtil.getIdentifyType(psnDicItems, delRel.getIdentifyType());
                delRel.setIdentifyType(identifyType);
            } else if (delRel.getType().equals("ORG")) {
                //关联方类型为法人或其他组织
                String identifyType = TraUtil.getIdentifyType(orgDicItems, delRel.getIdentifyType());
                delRel.setIdentifyType(identifyType);
            } else if ("PRO".equals(delRel.getType())) {
                // 金融产品类型转换
                String identifyType = TraUtil.getIdentifyType(proTypeItemList, delRel.getIdentifyType());
                delRel.setIdentifyType(identifyType);
            }
        }
        page.setResult(result);
    }


    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/query"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> query(Page<DelRel> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        prepareQuery(page, filters, orders, request);

        /*page.getResult().forEach(delRel -> {
            if (delRel.getType().equals("PSN")) {
                String identifyNumber = delRel.getIdentifyNumber();
                String substring = identifyNumber.substring(identifyNumber.length() - 4, identifyNumber.length());
                delRel.setEncodeIdentifyNummber("*****" + substring);
            } else if (delRel.getType().equals("ORG")) {
                delRel.setEncodeIdentifyNummber(delRel.getIdentifyNumber());
            }
        });*/
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, Model model) {

        DelRel delRel = delRelService.get(pid);
        model.addAttribute("delRel", delRel);
        model.addAttribute("opt", "edit");

        return "bus/delRelEdit";
    }

    /**
     * eye操作/查看
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model) {

        DelRel delRel = delRelService.get(pid);
        String type = delRel.getType();
        String identifyNumber = delRel.getIdentifyNumber();
        if ("PSN".equalsIgnoreCase(type)) {
            RelPsn relPsn = relPsnService.selectByIdentifyNumber(identifyNumber);
            model.addAttribute("relPsn", relPsn);
            return "bus/delRelPsnView";
        } else {
            RelOrg relOrg = relOrgService.selectByIdentifyNumber(identifyNumber);
            model.addAttribute("relOrg", relOrg);
            return "bus/delRelOrgView";
        }
    }


    /**
     * 查看页面
     *
     * @param relId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{relId}/{type}")
    public String view(@PathVariable Integer relId,@PathVariable String type, Model model) {

//        DelRel delRel = delRelService.get(pid);
//        Integer relPid = delRel.getRelPid();
        if ("PSN".equalsIgnoreCase(type)) {
            RelPsn relPsn = relPsnService.get(relId);
//            relPsn.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relPsn.getIdentifyNumber()));
            model.addAttribute("relPsn", relPsn);
            return "bus/delRelPsnView";
        } else {
            RelOrg relOrg = relOrgService.get(relId);
            model.addAttribute("relOrg", relOrg);
            return "bus/delRelOrgView";
        }
    }

    @RequestMapping(value = "/viewPSN/{pid}")
    public String viewPSN(@PathVariable Integer pid, Model model) {
        RelPsn relPsn = relPsnService.get(pid);
        relPsn.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relPsn.getIdentifyNumber()));
        model.addAttribute("relPsn", relPsn);
        return "bus/delRelPsnView";
    }


    @RequestMapping(value = "/viewORG/{pid}")
    public String viewORG(@PathVariable Integer pid, Model model) {
        RelOrg relOrg = relOrgService.get(pid);
        model.addAttribute("relOrg", relOrg);
        return "bus/delRelOrgView";
    }


    /**
     * 保存
     *
     * @param delRel
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(DelRel delRel) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = delRelService.exists(delRel.getPid());
        try {
            if (isExist) {

                delRelService.update(delRel);
            } else {
                delRelService.insert(delRel);
            }

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
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
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            delRelService.deleteByPrimaryKey(pid);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
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

        boolean exist = delRelService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 单一查询（自然人、机构）
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/delRelQuery")
    @ResponseBody
    @Log(module = "关联方管理", description = "解除关联关系库")
    public Map<String, Object> delRelQuery(Page<DelRel> page, Model model, HttpServletRequest request) {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        DelRel delRel = new DelRel();
        for (Filter filter : filters) {
            if ("identifyNumber".equals(filter.getProperty())) {
                delRel.setIdentifyNumber(String.valueOf(filter.getValue()));
            } else if ("name".equals(filter.getProperty())) {
                delRel.setName(String.valueOf(filter.getValue()));
            } else if ("identifyType".equals(filter.getProperty())) {
                delRel.setIdentifyType(String.valueOf(filter.getValue()));
            }else if ("type".equals(filter.getProperty())) {
                delRel.setType(String.valueOf(filter.getValue()));
            }
        }
        //新建一个list集合，用以存储列表所要展示的内容
        List<DelRel> delRelList = new ArrayList<>();
        //类表初始化的时候，不展示数据
        if (delRel.getType().equals("psn")) {
            List<Filter> filtersPSN = new Filter.Builder().eq("status", "6").eq("delflag", "1").build();
            if (delRel.getIdentifyNumber() != null) {
                filtersPSN.add(new Filter("identifyNumber", Operator.EQ, delRel.getIdentifyNumber()));
            }
            if (delRel.getName() != null) {
                filtersPSN.add(new Filter("name", Operator.LIKE, delRel.getName()));
            }
            if (delRel.getMgerOrg() != null) {
                filtersPSN.add(new Filter("mgerOrg", Operator.EQ, delRel.getMgerOrg()));
            }
            if (delRel.getIdentifyType() != null) {
                filtersPSN.add(new Filter("identifyType", Operator.EQ, delRel.getIdentifyType()));
            }
            //查询自然人
            List<RelPsn> relPsns = relPsnService.select(filtersPSN);
            if (relPsns != null && relPsns.size() > 0) {
                for (RelPsn relPsn : relPsns) {
                    DelRel delRel_psn = new DelRel();
                    delRel_psn.setPid(Integer.valueOf(relPsn.getPid()));
                    delRel_psn.setName(relPsn.getName());
                    delRel_psn.setDomesticOrForeign(relPsn.getDomesticOrForeign());
                    delRel_psn.setMgerOrg(relPsn.getMgerOrg());
                    delRel_psn.setIdentifyType(relPsn.getIdentifyType());
                    //隐藏关联人证件号
                    delRel_psn.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relPsn.getIdentifyNumber()));
                    delRelList.add(delRel_psn);
                }
            }
        } else if (delRel.getType().equals("org")) {
            List<Filter> filtersORG = new Filter.Builder().eq("delflag", "1").eq("approvalStatus", "6").build();
            if (delRel.getIdentifyNumber() != null) {
                filtersORG.add(new Filter("uniscCode", Operator.EQ, delRel.getIdentifyNumber()));
            }
            if (delRel.getName() != null) {
                filtersORG.add(new Filter("orgName", Operator.LIKE, delRel.getName()));
            }
            if (delRel.getIdentifyType() != null) {
                filtersORG.add(new Filter("identifyType", Operator.EQ, delRel.getIdentifyType()));
            }
            if (delRel.getMgerOrg() != null) {
                filtersORG.add(new Filter("mgerOrg", Operator.EQ, delRel.getMgerOrg()));
            }
            //查询关联组织
            List<RelOrg> relOrgs = relOrgService.select(filtersORG);
            if (relOrgs != null && relOrgs.size() > 0) {
                for (RelOrg relOrg : relOrgs) {
//                    PsnAndOrgVo psnAndOrgVo1 = new PsnAndOrgVo();
                    DelRel delRel_org = new DelRel();
                    delRel_org.setPid(Integer.valueOf(relOrg.getPid()));
                    delRel_org.setName(relOrg.getOrgName());
                    delRel_org.setDomesticOrForeign(relOrg.getDomesticOrForeign());
                    delRel_org.setMgerOrg(relOrg.getMgerOrg());
                    delRel_org.setIdentifyType(relOrg.getIdentifyType());
                    delRel_org.setIdentifyNumber(relOrg.getUniscCode());
                    delRelList.add(delRel_org);
                }
            }
        }
        page.setTotal(delRelList.size());
        int pages = (delRelList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<DelRel> delRels = new ArrayList<DelRel>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < delRelList.size()) {
                delRels.add(delRelList.get(i));
            }
        }
        tips.put(RESULT_TOTAL, page.getTotal());
        tips.put(RESULT_ROWS, page.getResult());
        return tips;
    }


    //根据关联方类型查询关联方证件类型
    @RequestMapping(value = "/queryIdentifyType")
    @ResponseBody
    public List<NameValuePair> getDictItems(@RequestParam("type") String type) {
        List<NameValuePair> items = new ArrayList<>();
        try {
            //自然人关联方证件类型字典
            List<DictItem> psnDictItem = TraUtil.getDictItems("BUS-IdentifyType");
            //机构关联方证件类型字典
            List<DictItem> orgDictItem = TraUtil.getDictItems("BUS-ORGIdentifyType");
            //金产产品关联方证件类型字典
            List<DictItem> proDictItem = TraUtil.getDictItems("BUS-pro");
            if ("PSN".equals(type)) {
                //过滤掉不要的字典项
                if (!CollectionUtils.isEmpty(psnDictItem)) {
                    for (int i = 0; i < psnDictItem.size(); i++) {
                        DictItem item = psnDictItem.get(i);
                        BasicNameValuePair nv = new BasicNameValuePair(item.getItemLabel(), item.getItemValue());
                        items.add(nv);
                    }
                }
            } else if ("ORG".equals(type)) {
                for (int i = 0; i < orgDictItem.size(); i++) {
                    DictItem item = orgDictItem.get(i);
                    BasicNameValuePair nv = new BasicNameValuePair(item.getItemLabel(), item.getItemValue());
                    items.add(nv);
                }
            } else {
                for (int i = 0; i < proDictItem.size(); i++) {
                    DictItem item = proDictItem.get(i);
                    BasicNameValuePair nv = new BasicNameValuePair(item.getItemLabel(), item.getItemValue());
                    items.add(nv);
                }
            }
            return items;
        } catch (ServiceException se) {
            logger.error("系统出现异常：" + se.getMessage());
            throw se;
        }
    }

}
