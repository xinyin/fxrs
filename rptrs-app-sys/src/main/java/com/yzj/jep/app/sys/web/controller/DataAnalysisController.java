package com.yzj.jep.app.sys.web.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.orm.entity.Treeable;
import com.yzj.jep.sys.dto.MetaRegionTableDto;
import com.yzj.jep.sys.model.MetaQuery;
import com.yzj.jep.sys.model.MetaRegionTable;
import com.yzj.jep.sys.model.MetaTable;
import com.yzj.jep.sys.query.constant.QueryOptions;
import com.yzj.jep.sys.query.constant.QueryRelationType;
import com.yzj.jep.sys.query.dto.QueryDto;
import com.yzj.jep.sys.query.utils.JdbcUtil;
import com.yzj.jep.sys.service.MetaQueryService;
import com.yzj.jep.sys.service.MetaRegionService;
import com.yzj.jep.sys.service.MetaRegionTableService;
import com.yzj.jep.sys.service.MetaRelationService;
import com.yzj.jep.sys.service.MetaTableService;
import com.yzj.jep.sys.util.SQLRegexUtil;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.TreeNode;
import com.yzj.jep.web.util.TreeUtils;


@Controller
@RequestMapping("/sys/dataAnalysis")
public class DataAnalysisController extends CrudController<MetaQuery> {
    protected static Logger logger = LoggerFactory.getLogger(DataAnalysisController.class);
    @Autowired
    private MetaQueryService metaQueryService;

    @Autowired
    private MetaRelationService metaRelationService;

    @Autowired
    private MetaTableService metaTableService;

    @Autowired
    private MetaRegionTableService metaRegionTableService;

    @Autowired
    private MetaRegionService metaRegionService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/main")
    public String list(Model model) {
        return "sys/dataAnalysisMain";
    }

    /**
     * 点击metaTable打开tab页，跳转到画面
     *
     * @param tableId
     */
    @RequestMapping(value = "/dataQuery")
    public String queryTableData(Model model, String id, String type, String masterTableId, String joinTableId, String data, String relationType, String masterColumnCode, String joinColumnCode) {

        model.addAttribute("id", id);
        model.addAttribute("type", type);
        model.addAttribute("masterTableId", masterTableId);
        model.addAttribute("joinTableId", joinTableId);
        model.addAttribute("data", data);
        model.addAttribute("relationType", relationType);
        model.addAttribute("masterColumnCode", masterColumnCode);
        model.addAttribute("joinColumnCode", joinColumnCode);

        /*默认为行模式*/
        String modelView = "L";
        if (!StringUtils.isEmpty(relationType) && QueryRelationType.valueOf(relationType) == QueryRelationType.JOIN) {
            modelView = "C";
        }
        model.addAttribute("modelView", modelView);

        /*获取用户信息*/
        UserProfile curUser = UserProfileUtils.getUserProfile();
        model.addAttribute("curUser", curUser);

        return "sys/dataAnalysisQuery";
    }

    /***
     * 查询数据分析器 tree
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryTableTree")
    @ResponseBody
    public List<TreeNode> queryTableTree(String id) {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        List<Treeable> treeables;
        try {
            /*获取用户信息*/
            UserProfile curUser = UserProfileUtils.getUserProfile();
            treeables = metaQueryService.getMetaRegionsByRegionId(id, curUser.getStaffCode());
            String[] columns = new String[]{"type", "title", "regionCode"};
            nodes = TreeUtils.parse(treeables, columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }

    /***
     * 查询当前登录人员能访问的metaRegion
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryMetaregionTree")
    @ResponseBody
    public List<TreeNode> queryMetaregionTree(String id) {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        List<Treeable> treeables;
        try {
            /*获取用户信息*/
            UserProfile curUser = UserProfileUtils.getUserProfile();
            treeables = metaQueryService.canAccessMetaRegionByRegionId(id, curUser.getStaffCode());
            String[] columns = new String[]{"type", "title", "regionCode"};
            nodes = TreeUtils.parse(treeables, columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }

    /**
     * 设置界面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/set")
    public String setTableData(String script, Model model) {
        model.addAttribute("script", script);
        return "sys/dataAnalysisSet";
    }

    /**
     * 查看Sql画面跳转
     *
     * @return
     */
    @RequestMapping(value = "/viewSql")
    public String viewSql(Model model, String querySql) {

        try {

            String sql = URLDecoder.decode(querySql, "UTF-8");

            model.addAttribute("sql", SQLRegexUtil.replace(sql));
        } catch (UnsupportedEncodingException e) {
        }
        return "sys/metaQuerySqlView";
    }

    /**
     * 保存界面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/queryName")
    public String meatQuerySave(Model model) {

        return "sys/metaQueryName";
    }

    /**
     * 查询一个关联表
     *
     * @param model
     * @param tableId
     * @return
     */
    @RequestMapping(value = "/selectChild/{regionTableId}")
    @ResponseBody
    public Map<String, Object> getChild(Model model, @PathVariable String regionTableId) {
        Map<String, Object> map = new HashMap<String, Object>();

        /*获取regionTable对象*/
        MetaRegionTable regionTable = metaRegionTableService.get(regionTableId);

        /*获取关联表*/
        List<MetaRegionTableDto> metaTables = metaRelationService.getAnalysisJoinTable(regionTable.getTableId());

        map.put(WebCode.SUCCESS, true);
        map.put("result", metaTables);

        return map;
    }

    /**
     * 另存为视图
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveView")
    public String saveView(Model model) {

        /*获取当前模式*/
        model.addAttribute("schema", JdbcUtil.getCurrentSchema());
        return "sys/dataAnalysisSaveView";
    }


    /**
     * 验证视图名字是否已存在
     *
     * @param request
     * @param metaTable
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkViewName")
    @ResponseBody
    public Map<String, Object> checkViewName(Model model, MetaTable metaTable) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<MetaTable> metatables = metaTableService.queryTables(metaTable.getSchema(), metaTable.getTableCode());
        map.put(WebCode.SUCCESS, true);
        map.put("tableExists", CollectionUtils.isNotEmpty(metatables));
        return map;
    }

    /**
     * IE浏览器查看列模式，全部列
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/columnView")
    public String columnView(Model model) {

        return "sys/dataAnalysisQueryColumn";
    }

//	/**
//	 * 获取关联表父级节点
//	 * @param relativeTableId
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/getParents", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> queryChild(String relativeTableId, HttpServletRequest request) throws Exception {
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		
//		/*获取关联表 regionTable对象*/
//		MetaRegionTable relativeRegionTable = metaRegionTableManager.getMetaRegionTable(relativeTableId);
//		 
//		/*获取当前表上面所有的父节点*/
//		String parents = metaRegionManager.getParents(relativeRegionTable.getRegionId());
//		
//		map.put(MessageConstant.SUCCESS, true); 
//		map.put("joinTrees", parents);
//		return map;
//	}
//	
//	

    /**
     * 查询导出
     *
     * @param model
     * @param request
     * @param response
     * @param conFileName
     */
    @RequestMapping(value = "/queryExport")
    public void export(HttpServletRequest request, HttpServletResponse response, String tableId, String script) {
        response.setContentType("text/html;charset=utf-8");
        response.setContentType("application/x-msdownload;");
        BufferedOutputStream bos = null;
        try {

            /*获取关联表 regionTable对象*/
            MetaRegionTable relativeRegionTable = metaRegionTableService.get(tableId);

            /*初始化查询对象*/
            QueryDto queryDto = new QueryDto();
            queryDto.setScript(script);
            UserProfile curUser = UserProfileUtils.getUserProfile();
            /*查询导出的数据*/
            byte[] outBody = metaQueryService.toXlsData(queryDto, QueryOptions.BUILD_OPTION_PRIVILEGE_REQUIRED, curUser.getStaffCode());

            /*查询tableCode*/
            MetaTable metaTable = metaTableService.get(relativeRegionTable.getTableId());
            String fileName = metaTable.getTableCode() + ".xlsx";
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);

            response.setHeader("Content-Length", String.valueOf(outBody.length));
            bos = new BufferedOutputStream(response.getOutputStream());
            bos.write(outBody, 0, outBody.length);
        } catch (IOException e) {
            logger.error("下载失败：" + e.getMessage());
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                logger.error("关闭IO流缓冲失败：" + e.getMessage());
            }
        }
    }
}
