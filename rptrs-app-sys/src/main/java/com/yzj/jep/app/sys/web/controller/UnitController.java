/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017-06-21 10:43:08
 *
 */

package com.yzj.jep.app.sys.web.controller;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Treeable;
import com.yzj.jep.sys.code.UnitKind;
import com.yzj.jep.sys.dto.StaffDto;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.entity.TreeNode;
import com.yzj.jep.web.util.NVUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/unit")
public class UnitController extends CrudController<Unit> {
    Logger logger = LoggerFactory.getLogger(UnitController.class);

    @Autowired
    private UnitService unitService;


    /**
     * 页面跳转到unitTree
     *
     * @param model
     * @return 树显示页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "sys/unitTree";
    }

    @RequestMapping(value = "/dialog")
    public String dialog(Model model) {

        return "sys/unitDlg";
    }

    /**
     * 移除Unit节点
     *
     * @param unitCode
     * @return
     */
    @RequestMapping(value = "/removeNode/{unitCode}")
    @ResponseBody
    public Map<String, Object> removeNode(@PathVariable String unitCode) {
        Map<String, Object> tips = new HashMap<String, Object>();

        /*查询当前节点是否有子节点*/
        long count = unitService.count(new Filter.Builder().eq("parentId", unitCode).build());

        /*总数大于0，提示先删除子节点，反之进行删除*/
        if (count > 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.DELETE_SUBNODE_FIRST);
        } else {
            try {
                unitService.deleteByPrimaryKey(unitCode);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            } catch (ServiceException e) {
                logger.info(e.getMessage());
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
            }

        }
        return tips;
    }

    /**
     * 重命名节点
     *
     * @param unitCode 节点key
     * @param newName  节点新的名称
     * @return
     */
    @RequestMapping(value = "/renameNode/{unitCode}")
    @ResponseBody
    public Map<String, Object> renameNode(@PathVariable String unitCode, String newName) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            Unit unit = new Unit();
            unit.setUnitCode(unitCode);
            unit.setUnitName(newName);
            unitService.update(unit, new String[]{"UnitName"});

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_RENAME);
        } catch (Exception ex) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_RENAME);
        }
        return tips;
    }

    /**
     * 新增节点
     *
     * @param unit
     * @return
     */
    @RequestMapping(value = "/updateNode")
    @ResponseBody
    public Map<String, Object> updateNode(Unit unit) {
        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(unit);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }

        try {
            /*调用更新机构方法*/
            unitService.updateUnit(unit);

            tips.put(Treeable.TREE_ID, unit.getUnitCode());
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

    /**
     * 初始化加载数据
     *
     * @param parentId
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/treeNode")
    @ResponseBody
    public List<TreeNode> treeNode(String parentId, Model model, HttpServletRequest request) {
        List<Filter> filters = null;

        if (parentId == null) {
            filters = new Filter.Builder().nl("parentId").build();
        } else {
            filters = new Filter.Builder().eq("parentId", parentId).build();
        }

        List<Unit> list = unitService.select(filters);

        String[] columns = new String[]{
                "unitCode",
                "unitName",
                "unitDesc",
                "displayName",
                "isBizDept",
                "bizTypeCode",
                "bizTypeName",
                "hierarchy",
                "kind",
                "owner_",
                "pathCode",
                "pathName",
                "level",
                "parentId",
                "areaCode",
                "fax",
                "mailCode",
                "officeAddr",
                "postCode",
                "telphone",
                "status",
                "tenantId",
        };

        List<TreeNode> nodes = TreeUtils.parse(list, columns);
        return nodes;
    }

    /**
     * 初始化加载数据
     *
     * @param parentId
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectUnit/lazy/treeNode")
    @ResponseBody
    public List<TreeNode> selectUnitLazy(String parentId, String includes, String excludes, Model model, HttpServletRequest request) {

        UserProfile profile = UserProfileUtils.getUserProfile();
        StaffDto staff = new StaffDto();

        staff.setStaffCode(profile.getStaffCode());
        staff.setStaffName(profile.getStaffName());
        staff.setUnitCode(profile.getUnitCode());
        staff.setUnitName(profile.getUnitName());
        staff.setDeptCode(profile.getDeptCode());
        staff.setDeptName(profile.getDeptName());
        staff.setDeptBizType(profile.getDeptBizType());

        List<Unit> units = unitService.selectUnitLazy(staff, parentId, includes, excludes);
        String[] columns = new String[]{
                "unitCode",
                "unitName",
                "unitDesc",
                "displayName",
                "bizType",
                "hierarchy",
                "kind",
                "owner_",
                "pathName",
                "level",
                "parentId",
                "areaCode",
                "fax",
                "mailCode",
                "officeAddr",
                "postCode",
                "telphone",
                "status",
                "tenantId",
        };

        List<TreeNode> nodes = TreeUtils.parse(units, columns);
        return nodes;
    }

    /**
     * 初始化加载数据
     *
     * @param parentId
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectUnit/eager/treeNode")
    @ResponseBody
    public List<TreeNode> selectUnitEager(String includes, String excludes, Model model, HttpServletRequest request) {

        UserProfile profile = UserProfileUtils.getUserProfile();
        StaffDto staff = new StaffDto();

        staff.setStaffCode(profile.getStaffCode());
        staff.setStaffName(profile.getStaffName());
        staff.setUnitCode(profile.getUnitCode());
        staff.setUnitName(profile.getUnitName());
        staff.setDeptCode(profile.getDeptCode());
        staff.setDeptName(profile.getDeptName());
        staff.setDeptBizType(profile.getDeptBizType());

        List<Unit> units = unitService.selectUnitEager(staff, includes, excludes);

        String[] columns = new String[]{
                "unitCode",
                "unitName",
                "unitDesc",
                "displayName",
                "bizType",
                "hierarchy",
                "kind",
                "owner_",
                "pathName",
                "level",
                "parentId",
                "areaCode",
                "fax",
                "mailCode",
                "officeAddr",
                "postCode",
                "telphone",
                "status",
                "tenantId",
        };

        List<TreeNode> nodes = TreeUtils.parse(units, columns);
        return nodes;
    }


    /**
     * 验证提交的dictCode是否已经存在数据库中
     *
     * @param dictCode
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String unitCode) {

        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = unitService.exists(unitCode);
        if (isExist) {
            /*被验证的unitCode已存在*/
            tips.put("valid", false);
        } else {
            tips.put("valid", true);
        }
        return tips;
    }

    /**
     * 验证当前机构的机构类型是否为ORG
     *
     * @param unitCode 机构编码
     * @return
     */
    @RequestMapping(value = "/isOrg")
    @ResponseBody
    public Map<String, Object> isOrg(String unitCode) {

        Map<String, Object> tips = new HashMap<String, Object>();
        Unit unit = unitService.get(unitCode);
        if (unit != null) {
            if (UnitKind.UNIT.name().equals(unit.getKind())) {
                tips.put("valid", true);
            } else {
                tips.put("valid", false);
            }
        }
        return tips;
    }

    /**
     * 测试mybatis
     *
     * @param unitCode 机构编码
     * @return
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public List<TreeNode> test() {

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("rootCodes", "select z.DEPT_CODE_ from sys_biz_dept_type z  where z.biz_type_code_ = 'INVESTMENT'");
        List<Unit> units1 = unitService.queryUnitByTestParams(map1);

        String[] columns = new String[]{
                "unitCode",
                "unitName",
                "unitDesc",
                "displayName",
                "bizType",
                "hierarchy",
                "kind",
                "owner",
                "pathName",
                "level",
                "parentId",
                "areaCode",
                "fax",
                "mailCode",
                "officeAddr",
                "postCode",
                "telphone",
                "status",
                "tenantId",
        };

        List<TreeNode> nodes = TreeUtils.parse(units1, columns);
        return nodes;
    }


    /**
     * 根据以“,”分割的机构编码查询，对应的机构
     *
     * @param staffCodes
     * @return
     */
    @RequestMapping(value = "/selectUnitByCodes/{unitCodes}")
    @ResponseBody
    public List<NameValuePair> selectUnitByCodes(@PathVariable String unitCodes) {
        if (StringUtils.isBlank(unitCodes)) {
            return null;
        }
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("unitCode", Operator.IN, unitCodes.split(",")));
        List<Unit> units = unitService.select(filters);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Unit unit : units) {
            nameValuePairs.add(new BasicNameValuePair(unit.getDisplayName(), unit.getUnitCode()));
        }
        return nameValuePairs;

    }

    /**
     * 查询全部机构
     *
     * @return
     */
    @RequestMapping(value = "/selectAllUnit")
    @ResponseBody
    public List<NameValuePair> selectAllUnit() {
        List<Unit> units = unitService.selectAll();
        List<NameValuePair> nameValuePairs = NVUtils.convert(units, units.size(), "unitCode", "unitName");
        nameValuePairs.add(0, new BasicNameValuePair("N/A", ""));
        return nameValuePairs;

    }

    /**
     * 查询机构
     *
     * @return
     */
    @RequestMapping(value = "/selectUnit")
    @ResponseBody
    public List<NameValuePair> selectUnit() {

        List<Filter> filters = new Filter.Builder().eq("kind", "UNIT").build();
        List<Unit> units = unitService.select(filters);
        List<NameValuePair> nameValuePairs = NVUtils.convert(units, units.size(), "unitCode", "unitName");
        return nameValuePairs;

    }

    /**
     * 查询机构下部门
     *
     * @return
     */
    @RequestMapping(value = "/selectDept")
    @ResponseBody
    public List selectDept(String unitCodes) {
//		List<Filter> filters=new Filter.Builder().eq("kind","DEPT").build();
        List<Unit> units = unitService.selectUnitAllOrDeptAll(unitCodes);
//		List<Unit> units = unitService.select(filters);
        List<Map> list = new ArrayList<>();
        for (Unit unit : units) {
            Map map = new HashMap();
            map.put("id", unit.getUnitCode());
            map.put("text", unit.getPathName());
            list.add(map);
        }
//				List<NameValuePair> nameValuePairs = NVUtils.convert(units, units.size(), "unitCode", "pathName");
//		nameValuePairs.add(0, new BasicNameValuePair("N/A",""));
        return list;

    }

    /**
     * 显示机构下部门
     *
     * @return
     */
    @RequestMapping(value = "/selecttoDept")
    @ResponseBody
    public List<NameValuePair> selecttoDept() {

        List<Filter> filters = new Filter.Builder().eq("kind", "DEPT").build();
        List<Unit> units = unitService.select(filters);
        List<NameValuePair> nameValuePairs = NVUtils.convert(units, units.size(), "unitCode", "pathName");
        nameValuePairs.add(0, new BasicNameValuePair("N/A", ""));
        return nameValuePairs;

    }


    /**
     * 以特殊格式获取所有子部门
     *
     * @return json格式返回
     */
    @RequestMapping(value = "/queryDep/{unitCode}")
    @ResponseBody
    public Map<String, Object> queryDep(@PathVariable String unitCode) {

        Map<String, Object> tips = new HashMap<String, Object>();
        String eid = UserProfileUtils.getUserProfile().getUnitCode();
        List<Filter> filters = new Filter.Builder().like("unitCode", unitCode).build();
        ;
        List<Unit> list = unitService.select(filters);
        tips.put("unitName", list.get(0).getUnitName());

        return tips;
    }

}
