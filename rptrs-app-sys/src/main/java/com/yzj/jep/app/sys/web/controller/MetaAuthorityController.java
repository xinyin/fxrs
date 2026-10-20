/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年12月25日 10:52:48
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.sys.dto.PermitTypeDto;
import com.yzj.jep.core.code.PermitType;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.code.AuthTargetType;
import com.yzj.jep.sys.code.AuthType;
import com.yzj.jep.sys.model.MetaAuthority;
import com.yzj.jep.sys.model.MetaRegion;
import com.yzj.jep.sys.service.MetaAuthorityService;
import com.yzj.jep.sys.service.MetaRegionService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/metaAuthority")
public class MetaAuthorityController extends CrudController<MetaAuthority> {
    private static Logger logger = LoggerFactory.getLogger(MetaAuthorityController.class);

    @Autowired
    private MetaAuthorityService metaAuthorityService;

    @Autowired
    private MetaRegionService metaRegionService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/metaAuthorityList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<MetaAuthority> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        metaAuthorityService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/metaAuthorityEdit";
    }

    /**
     * 编辑页面
     *
     * @param authId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{authId}")
    public String edit(@PathVariable String authId, Model model) {

        MetaAuthority metaAuthority = metaAuthorityService.get(authId);
        model.addAttribute("metaAuthority", metaAuthority);
        model.addAttribute("opt", "edit");

        return "sys/metaAuthorityEdit";
    }

    /**
     * 查看页面
     *
     * @param authId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{authId}")
    public String view(@PathVariable String authId, Model model) {

        MetaAuthority metaAuthority = metaAuthorityService.get(authId);
        model.addAttribute("metaAuthority", metaAuthority);

        return "sys/metaAuthorityView";
    }

    /**
     * 保存
     *
     * @param metaAuthority
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(MetaAuthority metaAuthority) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = metaAuthorityService.exists(metaAuthority.getAuthId());
        try {
            if (isExist) {

                metaAuthorityService.update(metaAuthority);
            } else {
                metaAuthorityService.insert(metaAuthority);
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
     * @param authId
     * @return
     */
    @RequestMapping(value = "/remove/{authId}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String authId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            metaAuthorityService.deleteByPrimaryKey(authId);
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
     * 验证提交的authId是否已经存在数据库中
     *
     * @param authId
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String authId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = metaAuthorityService.exists(authId);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 给角色赋上这些数据区域及其下的表的权限
     *
     * @param idStr    表ID
     * @param roleCode 角色编号
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/saveRegionAuth"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveRegionAuth(String idStr, String roleCode) throws Exception {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            metaAuthorityService.saveRegionAuth(roleCode, idStr);
            tips.put("success", true);
            tips.put("msg", "保存成功。");
        } catch (ServiceException se) {
            tips.put("success", false);
            tips.put("msg", "保存失败。");
            logger.error("系统出现异常：" + se.getMessage());
            throw se;
        }
        return tips;
    }

    /**
     * 给角色赋上这些数据区域及其下的表的权限
     *
     * @param idStr    表ID
     * @param roleCode 角色编号
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/saveTableAuth"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveTableAuth(String regionTableIds, String roleCode, String regionId) throws Exception {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            metaAuthorityService.saveTableAuth(roleCode, regionTableIds, regionId);
            tips.put("success", true);
            tips.put("msg", "保存成功。");
        } catch (ServiceException se) {
            tips.put("success", false);
            tips.put("msg", "保存失败。");
            logger.error("系统出现异常：" + se.getMessage());
            throw se;
        }
        return tips;
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/metaAuthorityDlg")
    public String metaTableDlg(Model model, String roleCode) {
        model.addAttribute("roleCode", roleCode);
        /*查询已经授权的数据区域*/
        List<MetaRegion> regions = metaRegionService.queryAuthMetaRegionByRoleCode(roleCode);
        JSONArray json = new JSONArray();
        for (MetaRegion region : regions) {
            JSONObject jo = new JSONObject();
            jo.put("id", region.getRegionId());
            json.add(jo);
        }
        model.addAttribute("nodes", json);

        return "sys/metaAuthorityDlg";
    }

    /**
     * 登录页面
     *
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/permitTypes")
    @ResponseBody
    public Map<String, Object> permitTypes(String tableId, String roleCode, String type, Model model, HttpServletRequest request) throws Exception {
        Map<String, Object> data = new HashMap<>();
        List<PermitTypeDto> items = new ArrayList<PermitTypeDto>();

        /*只有当tableId和角色都不等于空的时候，才是正确的请求*/
        if (StringUtils.isNotBlank(tableId) && StringUtils.isNotBlank(roleCode)) {
            /*当类型为表格的时候，获取数据权限*/
            if (AuthTargetType.TABLE.getValue().equals(type)) {
                /*获取到实体类对应的数据权限定义*/
                PermitType[] permitTypes = PermitType.values();
                MetaAuthority authority = new MetaAuthority();
                authority.setAuthTarget(tableId);
                authority.setRoleCode(roleCode);
                authority.setAuthType(AuthType.DAK.getValue());
                authority = metaAuthorityService.get(authority);

                for (PermitType permitType : permitTypes) {
                    if (authority != null && permitType.getCode().equals(authority.getAuthData())) {
                        items.add(new PermitTypeDto(permitType.getName(), permitType.getCode(), true));
                    } else if (!PermitType.FORBIDDEN.getCode().equals(permitType.getCode())) {
                        items.add(new PermitTypeDto(permitType.getName(), permitType.getCode(), false));
                    }
                }
            }
        }
        data.put("total", items.size());
        data.put("rows", items);
        return data;
    }

    /**
     * 保存数据权限到这个角色
     *
     * @param idStr    数据权限编码
     * @param roleCode 角色编号
     * @param tableId  表ID
     * @return
     */
    @RequestMapping(value = {"/saveAuthorityBelongDAK"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveAuthorityBelongDAK(String idStr, String roleCode, String tableId) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            metaAuthorityService.saveAuthorityBelongDAK(roleCode, tableId, idStr);
            tips.put("success", true);
            tips.put("msg", "保存成功。");
        } catch (ServiceException se) {
            tips.put("success", false);
            tips.put("msg", "保存失败。");
            logger.error("系统出现异常：" + se.getMessage());
            throw se;
        }
        return tips;
    }

}