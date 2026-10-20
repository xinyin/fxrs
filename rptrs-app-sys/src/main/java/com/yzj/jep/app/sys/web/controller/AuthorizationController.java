/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年07月04日 14:28:28
 *
 */

package com.yzj.jep.app.sys.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.code.ResourceKind;
import com.yzj.jep.sys.model.Authorization;
import com.yzj.jep.sys.model.Menu;
import com.yzj.jep.sys.service.AuthorizationService;
import com.yzj.jep.sys.service.MenuService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 授权管理
 */
@Controller
@RequestMapping("/sys/authorization")
public class AuthorizationController extends CrudController<Authorization> {
    private static Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private MenuService menuService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/authorizationList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Authorization> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        authorizationService.query(page, filters, orders);
    }

    /**
     * 授权菜单
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/authorizeMenu")
    public String authorizeMenu(String code, String name, String type, Model model) {
        model.addAttribute("code", code);
        model.addAttribute("name", name);
        model.addAttribute("type", type);

        /*查询当前编码（code）的已分配菜单权限*/
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("auzTarget", code));
        filters.add(new Filter("kind", ResourceKind.MENU.name()));
        List<Authorization> list = authorizationService.select(filters);

        JSONArray json = new JSONArray();
        for (Authorization authorization : list) {
            JSONObject jo = new JSONObject();
            jo.put("id", authorization.getResourceCode());
            json.add(jo);
        }
        model.addAttribute("nodes", json);

        return "sys/authorizeMenu";
    }

    /**
     * 保存
     *
     * @param changeAllSelectNodes  菜单与其子菜单全部被选中
     * @param changePartSelectNodes 被部分选中的父菜单，部分选中的，只需将其本身进行关联插入，不需要查其所有子菜单
     * @param changeCancelNodes     被取消选中的菜单
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(String changeAllSelectNodes, String changePartSelectNodes, String changeCancelNodes, String code, String type) {

        Map<String, Object> tips = new HashMap<String, Object>();

        List<Menu> allMenuList = JSON.parseArray(changeAllSelectNodes, Menu.class);
        List<Menu> cancelMenuList = JSON.parseArray(changeCancelNodes, Menu.class);
        List<Menu> partSelectMenuList = JSON.parseArray(changePartSelectNodes, Menu.class);

        try {
            /*删除此次被取消勾选的菜单*/
            if (cancelMenuList.size() > 0) {
                List<Filter> filtersRemove = new ArrayList<>();
                filtersRemove.add(new Filter("auzTarget", code));
                filtersRemove.add(new Filter("kind", ResourceKind.MENU.name()));
                List<String> resourceCodes = new ArrayList<String>();
                for (Menu cancelMenu : cancelMenuList) {
                    resourceCodes.add(cancelMenu.getMenuCode());
                    List<String> menus = menuService.selectSubmenuByParentId(cancelMenu.getMenuCode());
                    resourceCodes.addAll(menus);
                }
                filtersRemove.add(Filter.in("resourceCode", resourceCodes));
                authorizationService.deleteByFilter(filtersRemove);
            }

            /*为防止重复插入，先用list去承载所有需要增加的菜单编码，然后遍历list进行插入*/
            List<String> newAddMenuCode = new ArrayList<String>();
            /*全选菜单的权限，要将旗下所有子菜单也进行权限关联*/
            for (Menu menu : allMenuList) {
                newAddMenuCode.add(menu.getMenuCode());
                List<String> menus = menuService.selectSubmenuByParentId(menu.getMenuCode());
                newAddMenuCode.addAll(menus);
            }
            /*部分选中的菜单，只需要将其本身进行权限关联*/
            for (Menu menu : partSelectMenuList) {
                newAddMenuCode.add(menu.getMenuCode());
            }
            /*遍历需要插入的权限关联数据，进行数据插入*/
            newAddMenuCode.forEach(addMenu -> {
                List<Filter> filters = new ArrayList<Filter>();
                filters.add(new Filter("auzTarget", code));
                filters.add(new Filter("kind", ResourceKind.MENU.name()));
                filters.add(new Filter("resourceCode", addMenu));
                boolean exists = authorizationService.exists(filters);
                if (!exists) {
                    this.insertAuthorizationByMenuCodes(addMenu, code, type);
                }
            });
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
     * 保存
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/savePermit")
    @ResponseBody
    public Map<String, Object> savePermit(String permitType, String menuCode, String roleCode, String type) {

        Map<String, Object> tips = new HashMap<String, Object>();

        String[] permitTypeArr = permitType.split(",");
        try {
            /*先删除，再添加*/
            List<Filter> filtersRemove = new ArrayList<>();
            filtersRemove.add(new Filter("auzTarget", roleCode));
            filtersRemove.add(new Filter("kind", ResourceKind.FP.name()));
            filtersRemove.add(new Filter("menuCode", menuCode));
            filtersRemove.add(new Filter("auzType", type));
            authorizationService.deleteByFilter(filtersRemove);

            /*添加新增的菜单权限*/
            for (String permit : permitTypeArr) {
                Authorization authorization = new Authorization();
                authorization.setAuzTarget(roleCode);
                authorization.setAuzType(type);
                authorization.setKind(ResourceKind.FP.name());
                authorization.setResourceCode(permit);
                authorization.setMenuCode(menuCode);
                authorizationService.insert(authorization);
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
     * 根据menuCode插入关联数据
     *
     * @param menuCodes 菜单编码
     * @param code      岗位编码
     * @param type      授权类型
     */
    private void insertAuthorizationByMenuCodes(String menuCodes, String code, String type) {
        Authorization authorization = new Authorization();
        authorization.setAuzTarget(code);
        authorization.setAuzType(type);
        authorization.setKind(ResourceKind.MENU.name());
        authorization.setResourceCode(menuCodes);
        authorizationService.insert(authorization);
    }
}