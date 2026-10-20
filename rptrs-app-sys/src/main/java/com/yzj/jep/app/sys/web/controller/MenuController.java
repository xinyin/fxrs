package com.yzj.jep.app.sys.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Treeable;
import com.yzj.jep.sys.code.SecurityConstant;
import com.yzj.jep.sys.model.Menu;
import com.yzj.jep.sys.service.MenuService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.TreeNode;
import com.yzj.jep.web.util.TreeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 系统菜单
 *
 * @author ZhangYan
 * @time 2017年6月8日 上午10:00:06
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController extends CrudController<Menu> {

    Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    MenuService menuService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        return "sys/menuTree";
    }

    @RequestMapping(value = "/removeNode/{id:.+}")
    @ResponseBody
    public Map<String, Object> removeNode(@PathVariable String id) {
        Map<String, Object> tips = new HashMap<String, Object>();
        long count = menuService.count(new Filter.Builder().eq("parentId", id).build());
        if (count > 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.DELETE_SUBNODE_FIRST);
        } else {
            //this.menuService.deleteByPrimaryKey(id);
            if (!"-1".equals(id)) {
                menuService.removeMenu(id);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return tips;
    }

    @RequestMapping(value = "/renameNode/{id}")
    @ResponseBody
    public Map<String, Object> renameNode(@PathVariable String menuCode, String newName) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            Menu menu = new Menu();
            menu.setMenuCode(menuCode);
            menu.setMenuName(newName);
            menuService.update(menu, new String[]{"menuName"});
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_RENAME);
        } catch (Exception ex) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_RENAME);
        }
        return tips;
    }

    @RequestMapping(value = "/updateNode")
    @ResponseBody
    public Map<String, Object> updateNode(Menu menu, String isChrildrenUpdate) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
			/*Menu exist = menuService.get(menu.getMenuCode());
			if(exist == null)
			{
				menuService.insert(menu);
			}
			else
			{
				menuService.update(menu);
			}*/
            menuService.saveMenu(menu, isChrildrenUpdate);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            tips.put(Treeable.TREE_ID, menu.getMenuCode());
        } catch (Exception ex) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }


    @RequestMapping(value = "/treeNode")
    @ResponseBody
    public List<TreeNode> treeNode(String parentId, Model model, HttpServletRequest request) {
        List<Filter> filters = null;
        if (StringUtils.isEmpty(parentId)) {
            filters = new Filter.Builder().nl("parentId").build();
        } else {
            filters = new Filter.Builder().eq("parentId", parentId).build();
        }
        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order("displayOrder", OrderType.ASC));

        List<Menu> list = menuService.select(filters, orders);
        String[] columns = {"menuCode", "menuName", "url", "displayOrder", "publicMenu", "enabled", "visibled", "tips", "menuLevel", "leafNode", "menuImage"};
        List<TreeNode> nodes = TreeUtils.parse(list, columns);

        return nodes;
    }

    /**
     * 查询当前用户已授权的菜单
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/loadCurrentMenu")
    @ResponseBody
    public List<TreeNode> loadCurrentMenu(Model model, HttpServletRequest request) {

        /*先获得当前用户*/
        UserProfile currentUser = UserProfileUtils.getUserProfile();

        List<Menu> list = null;
        /*查询当前用户存在权限的菜单*/
        list = menuService.selectMenuByStaffCode(currentUser.getUsername(), null);

        /*非超级管理员不能查看【租户管理】和【菜单管理】*/
        if (!SecurityConstant.ADMIN_USER_NAME.equals(currentUser.getStaffCode())) {
            Iterator<Menu> iter = list.iterator();
            while (iter.hasNext()) {
                Menu item = iter.next();
                if ("sys/tenant/list".equals(item.getUrl()) || "sys/menu/list".equals(item.getUrl())) {
                    iter.remove();
                }
            }
        }

        String[] columns = {"menuCode", "menuName", "url", "displayOrder", "menuLevel", "leafNode", "parentId", "menuImage"};
        List<TreeNode> nodes = TreeUtils.parse(list, columns);

        return nodes;
    }

    /**
     * 验证菜单编码是否已经存在数据库中
     *
     * @param menuCode
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String menuCode) {

        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = menuService.exists(menuCode);
        if (isExist) {
            /*被验证的menuCode已存在*/
            tips.put("valid", false);
        } else {
            tips.put("valid", true);
        }
        return tips;
    }

    /**
     * 面包屑导航
     *
     * @param model
     * @param menuCode
     * @return
     */
    @RequestMapping(value = "/navMenu")
    public String queryChildMenus(Model model, String menuCode) {
        UserProfile UserProfile = UserProfileUtils.getUserProfile();

        List<Menu> list = menuService.selectMenuByStaffCode(UserProfile.getStaffCode(), menuCode);

        model.addAttribute("menuList", JSONObject.toJSON(list));
        return "sys/navMenu";
    }
}
