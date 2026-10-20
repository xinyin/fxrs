/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年12月25日 10:52:48
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.code.MetaQueryStatus;
import com.yzj.jep.sys.code.MetaQueryType;
import com.yzj.jep.sys.model.MetaQuery;
import com.yzj.jep.sys.model.MetaRegionTable;
import com.yzj.jep.sys.model.MetaTable;
import com.yzj.jep.sys.query.constant.QueryOptions;
import com.yzj.jep.sys.query.constant.QueryRelationType;
import com.yzj.jep.sys.query.dto.QueryDto;
import com.yzj.jep.sys.query.dto.QueryResultDto;
import com.yzj.jep.sys.service.MetaQueryService;
import com.yzj.jep.sys.service.MetaRegionTableService;
import com.yzj.jep.sys.service.MetaRelationService;
import com.yzj.jep.sys.service.MetaTableService;
import com.yzj.jep.sys.util.JSONDateValueProcessor;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/metaQuery")
public class MetaQueryController extends CrudController<MetaQuery> {
    private static Logger logger = LoggerFactory.getLogger(MetaQueryController.class);

    @Autowired
    private MetaQueryService metaQueryService;

    @Autowired
    private MetaRegionTableService metaRegionTableService;

    @Autowired
    private MetaRelationService metaRelationService;

    @Autowired
    private MetaTableService metaTableService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/metaQueryList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<MetaQuery> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        metaQueryService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sys/metaQueryEdit";
    }


    /**
     * 查看页面
     *
     * @param queryId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{queryId}")
    public String view(@PathVariable String queryId, Model model) {

        MetaQuery metaQuery = metaQueryService.get(queryId);
        metaQuery.setQueryType(MetaQueryType.getLabel(metaQuery.getQueryType()));
        metaQuery.setStatus(MetaQueryStatus.getLabel(metaQuery.getStatus()));
        model.addAttribute("metaQuery", metaQuery);

        return "sys/metaQueryView";
    }

    /**
     * 保存
     *
     * @param metaQuery
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(MetaQuery metaQuery) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = metaQueryService.exists(metaQuery.getQueryId());
        try {
            if (isExist) {

                metaQueryService.updateSelective(metaQuery);
            } else {
                UserProfile profile = UserProfileUtils.getUserProfile();
                if (profile == null) {
                    throw new ServiceException("user session may be expired.");
                }

                /* 验证同已用户在同一张表下名字不能重复*/
                List<Filter> filters = new Filter.Builder().eq("staffCode", profile.getStaffCode())
                        .eq("tableId", metaQuery.getTableId()).eq("queryName", metaQuery.getQueryName()).build();
                boolean existsQueryName = metaQueryService.exists(filters);
                if (existsQueryName) {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "查询名称重复，请重新录入！");
                    return tips;
                }

                metaQuery.setUnitCode(profile.getUnitCode());
                metaQuery.setUnitName(profile.getUnitName());
                metaQuery.setDeptCode(profile.getDeptCode());
                metaQuery.setDeptName(profile.getDeptName());
                metaQuery.setStaffCode(profile.getStaffCode());
                metaQuery.setStaffName(profile.getStaffName());
                metaQuery.setStatus(MetaQueryStatus.FORCE.getValue());

                metaQueryService.insert(metaQuery);
            }

            tips.put(WebCode.SUCCESS, true);
            tips.put("queryId", metaQuery.getQueryId());
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
     * @param queryId
     * @return
     */
    @RequestMapping(value = "/remove/{queryId}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String queryId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            metaQueryService.deleteByPrimaryKey(queryId);
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
     * 验证提交的queryId是否已经存在数据库中
     *
     * @param queryId
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String queryId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = metaQueryService.exists(queryId);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 根据一个tableId初始化metaQuery对象
     *
     * @param tableId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMeatQuery(Page<MetaQuery> page, String regionTableId, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            throw new ServiceException("user session may be expired.");
        }

        /*获取regionTable对象*/
        MetaRegionTable regionTable = metaRegionTableService.get(regionTableId);

        /*初始化一个query对象*/
        QueryDto query = metaQueryService.init(regionTable.getTableId(), regionTableId, profile.getStaffCode());
        query.setTableId(regionTableId);

        /*获得脚步*/
        String script = query.getScript();
        JSONObject jsonScript = JSONObject.parseObject(script);
        jsonScript.put("limit", page.getPageSize());
        jsonScript.put("pageNo", page.getPageNumber());

        query.setScript(jsonScript.toJSONString());

        /*查询结果*/
        QueryResultDto result = metaQueryService.query(query, QueryOptions.BUILD_OPTION_PRIVILEGE_REQUIRED, profile.getStaffCode());

        /*初始化所有关联表数据*/
        map.put("joinTable", metaRelationService.getJoinTablesByTableId(regionTable.getTableId(), profile.getStaffCode()));
        map.put("masterTable", metaRelationService.getMasterTablesByTableId(regionTable.getTableId(), profile.getStaffCode()));

        map.put(WebCode.SUCCESS, true);
        map.put("query", query);
        map.put("result", result);
        map.put("resultData", JSONDateValueProcessor.Object2JsonString(result.getResult()));

        return map;
    }


    /**
     * 查询一个metaQuery
     *
     * @param queryId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryMeatQuery(String queryId, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            throw new ServiceException("user session may be expired.");
        }

        MetaQuery metaQuery = metaQueryService.get(queryId);

        /*初始化queryDto对象*/
        QueryDto query = new QueryDto();
        query.setScript(metaQuery.getScript());

        QueryResultDto result = metaQueryService.query(query, QueryOptions.BUILD_OPTION_PRIVILEGE_REQUIRED, profile.getStaffCode());
        ;

        MetaRegionTable metaRegionTable = metaRegionTableService.get(metaQuery.getTableId());
        MetaTable metaTable = metaTableService.get(metaRegionTable.getTableId());

        /*初始化所有关联表数据*/
        map.put("joinTable", metaRelationService.getJoinTablesByTableId(metaTable.getTableId(), profile.getStaffCode()));
        map.put("masterTable", metaRelationService.getMasterTablesByTableId(metaTable.getTableId(), profile.getStaffCode()));

        map.put(WebCode.SUCCESS, true);
        map.put("metaQuery", metaQuery);
        map.put("result", result);
        map.put("resultData", JSONDateValueProcessor.Object2JsonString(result.getResult()));

        return map;
    }

    /**
     * 根据脚本查询数据
     *
     * @param script
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryResult")
    @ResponseBody
    public Map<String, Object> queryResult(String script, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            throw new ServiceException("user session may be expired.");
        }
        /*初始化queryDto对象*/
        QueryDto query = new QueryDto();
        query.setScript(script);

        /*查询结果*/
        QueryResultDto result = metaQueryService.query(query, QueryOptions.BUILD_OPTION_PRIVILEGE_REQUIRED, profile.getStaffCode());
        ;
        map.put(WebCode.SUCCESS, true);
        map.put("result", result);
        map.put("query", query);
        map.put("resultData", JSONDateValueProcessor.Object2JsonString(result.getResult()));

        return map;
    }

    /**
     * 增加关联表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/join")
    public String addJoinTable(Model model) {
        return "sys/metaQueryJoinDlg";
    }

    /**
     * 获取选择表页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/tableDlg")
    public String joinTableDlg(String joinAlias, Model model) {
        model.addAttribute("joinAlias", joinAlias);
        return "sys/metaQueryTableDlg";
    }

    /**
     * 选择关联条件
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/joinCons")
    public String joinCondition(Model model) {
        return "sys/metaQueryJoinConsDlg";
    }

    /**
     * 获取选择字段页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectColumn")
    public String selectColumn(Model model) {
        return "sys/metaQuerySelectColumnDlg";
    }

    /**
     * 获取自定义字段页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/customColumn")
    public String customColumn(Model model) {
        return "sys/metaQueryCustomColumnDlg";
    }

    /**
     * 获取选择条件页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/condition")
    public String condition(Model model) {
        return "sys/metaQueryConditionDlg";
    }

    /**
     * 获取选择排序字段页面
     *
     * @return 选择字段页面
     */
    @RequestMapping(value = "/order")
    public String modelSelectedColumns(Model model) {
        return "sys/metaQueryOrderColumn";
    }

    /**
     * 选择分组汇总字段
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/groupColumns")
    public String groupColumns(Model model) {
        return "sys/metaQueryGroupColumn";
    }

    /**
     * 验证自定义查询是否是自己创建
     *
     * @param queryId
     * @return
     */
    @RequestMapping(value = "/verify")
    @ResponseBody
    public Map<String, Object> verify(String queryId) {
        Map<String, Object> map = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            throw new ServiceException("user session may be expired.");
        }

        MetaQuery metaQuery = metaQueryService.get(queryId);

        if (metaQuery != null) {
            if (metaQuery.getStaffCode().equals(profile.getStaffCode())) {
                map.put(WebCode.SUCCESS, true);
            } else {
                map.put(WebCode.SUCCESS, false);
            }
        }
        return map;
    }

    /**
     * 另存为视图
     *
     * @param queryDto
     * @param metaTable
     * @return
     */
    @RequestMapping(value = "/saveView")
    @ResponseBody
    public Map<String, Object> saveView(QueryDto queryDto, MetaTable metaTable, String regionId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            }

            MetaRegionTable metaRegionTable = metaQueryService.saveAsView(queryDto, metaTable, regionId, profile.getStaffCode());
            tips.put("tableId", metaRegionTable.getRegionTableId());
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

    /**
     * 查询子表数据
     *
     * @param masterTableId
     * @param relativeTableId
     * @param data
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryChild", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryChild(String masterTableId, String masterColumnCode, String joinTableId, String joinColumnCode, String data, String relationType, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        data = URLDecoder.decode(data, "UTF-8");

        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            throw new ServiceException("user session may be expired.");
        }

        /*关联表表 regionTable对象*/
        MetaRegionTable masterRegionTable = null;

        /*当前表 regionTable对象*/
        MetaRegionTable relativeRegionTable = null;
        String regionTableId = "";

        /*查询主表*/
        if (QueryRelationType.valueOf(relationType).equals(QueryRelationType.MASTER)) {
            /*关联表表 regionTable对象*/
            masterRegionTable = metaRegionTableService.get(joinTableId);

            /*当前表 regionTable对象*/
            relativeRegionTable = metaRegionTableService.get(masterTableId);
            regionTableId = relativeRegionTable.getRegionTableId();
        } else {
            /*关联表表 regionTable对象*/
            masterRegionTable = metaRegionTableService.get(masterTableId);

            /*当前表 regionTable对象*/
            relativeRegionTable = metaRegionTableService.get(joinTableId);
            regionTableId = masterRegionTable.getRegionTableId();
        }

        /*初始化query对象*/
        QueryDto query = null;
        QueryResultDto result = null;

        map.put(WebCode.SUCCESS, true);

        try {
            query = metaQueryService.init(masterRegionTable.getTableId(), masterColumnCode, relativeRegionTable.getTableId(), joinColumnCode, regionTableId, QueryRelationType.valueOf(relationType), data);
            query.setTableId(masterTableId);

            /*查询结果*/
            result = metaQueryService.query(query, QueryOptions.BUILD_OPTION_PRIVILEGE_REQUIRED, profile.getStaffCode());
        } catch (Exception e) {
            map.put(WebCode.SUCCESS, false);
            map.put(WebCode.MSG, "查询数据失败。");
        }

        /*初始化所有关联表数据*/
        map.put("joinTable", metaRelationService.getJoinTablesByTableId(metaRegionTableService.get(masterTableId).getTableId(), profile.getStaffCode()));
        map.put("masterTable", metaRelationService.getMasterTablesByTableId(metaRegionTableService.get(masterTableId).getTableId(), profile.getStaffCode()));

        map.put("query", query);
        map.put("result", result);
        map.put("resultData", JSONDateValueProcessor.Object2JsonString(result.getResult()));
        return map;
    }


    /**
     * 查看数据
     *
     * @param queryId
     * @param model
     * @return
     */
    @RequestMapping("/viewMetaDataProvider/{queryId}")
    public String viewMetaDataProvider(@PathVariable String queryId, Model model) {
        MetaQuery metaQuery = metaQueryService.get(queryId);
        metaQuery.setQueryType(MetaQueryType.getLabel(metaQuery.getQueryType()));
        metaQuery.setStatus(MetaQueryStatus.getLabel(metaQuery.getStatus()));
        model.addAttribute("metaQuery", metaQuery);
        return "sys/metaDataProviderView";
    }
}