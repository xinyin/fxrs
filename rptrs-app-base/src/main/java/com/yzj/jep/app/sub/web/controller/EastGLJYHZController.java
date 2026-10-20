package com.yzj.jep.app.sub.web.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.sub.mapper.EastGLJYHZMapper;
import com.yzj.jep.app.sub.model.EastGljyhzb;
import com.yzj.jep.app.sub.model.EastGljyhzbView;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.EastGLJYHZService;
import com.yzj.jep.app.sub.service.EastGLJYHZViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sub.service.TemplateDetailsService;
import com.yzj.jep.app.sub.service.impl.EastExportCallBackImpl;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/east/gljyhz")
public class EastGLJYHZController extends CrudController<EastGljyhzb> {


    private static Logger logger = LoggerFactory.getLogger(EastGLJYHZController.class);
    @Autowired
    EastGLJYHZService eastGljyhzbService;
    @Autowired
    TemplateDetailsService templateDetailsService;
    @Autowired
    SublogService sublogService;
    @Autowired
    EastGLJYHZViewService eastGljyhzbViewService;
    @Autowired
    EastExportCallBackImpl eastExportCallBack;


    /**
     * 获取列表页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model) {
        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "east/gljyhz/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "sub/eastGljyhzList";

    }

    /**
     * 为列表做数据准备
     *
     * @param page
     * @param filters
     * @param orders
     * @param request
     */
    @Override
    protected void prepareQuery(Page<EastGljyhzb> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        eastGljyhzbService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model) {
        return "sub/eastGljyhzAdd";
    }




    /**
     * 关联交易汇总表 生成报送文件
     */
    @RequestMapping(value = "/createSubRelParFile/", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createSubRelParFile(@RequestParam(value = "subType", required = true) String subType) {
        //用来存储返回结果
        Map<String, Object> tips = Maps.newHashMap();
        //用来存储获取文件名的条件
        Map<String, Object> map = Maps.newHashMap();
        map.put("subType", subType);
        try {
            templateDetailsService.creatExcel("EastExportCallBackImpl",eastExportCallBack.getExportFilename(map),map);
        } catch (Exception e) {
            logger.info("生成报送文件失败");
            tips.put(WebCode.SUCCESS,false);
            tips.put(WebCode.MSG,WebCode.FAILURE_FAILED_FILE);
            e.printStackTrace();
            return tips;
        }
        try {
            map.put("subType", subType);
            List<Filter> filters = new ArrayList<>();
            filters.add(Filter.eq("eastStatus", 1));
            filters.add(Filter.eq("delflag", "1"));
            /**
             * 调用模板类将数据备份到对应的view表与sublog表中，返回一个boolean值用来判断是否成功
             */
            boolean flag = templateDetailsService.addEastView(filters, eastExportCallBack.getExportFilename(map), subType);
            if (flag) {
                logger.info("报送文件数据已生成");
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.FAILURE_SUCCESS_FILE);
            } else {
                logger.info("暂无报送数据生成");
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.FAILURE_NODATA_FILE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_FAILED_FILE);
        }
        return tips;
    }
    //返回季度开始时间和结束时间
    public String[] yearQuarter(String year , String quarter){
        StringBuffer staTIme = new StringBuffer();
        StringBuffer endTIme = new StringBuffer();
        if(StringUtils.isEmpty(quarter)){
            quarter = "1";
        }
        if(StringUtils.isEmpty(year)){
            LocalDate now = LocalDate.now();
            year = String.valueOf(now.getYear());
        }
        switch (quarter){
            case "1" :
                staTIme = staTIme.append(year).append("-01-01");
                endTIme = endTIme.append(year).append("-03-31");
                break;
            case "2" :
                staTIme = staTIme.append(year).append("-04-01");
                endTIme = endTIme.append(year).append("-06-31");
                break;
            case "3" :
                staTIme = staTIme.append(year).append("-07-01");
                endTIme = endTIme.append(year).append("-09-31");
                break;
            case "4" :
                staTIme = staTIme.append(year).append("-10-01");
                endTIme = endTIme.append(year).append("-12-31");
                break;
        }
        String[] list =  {staTIme.toString(),endTIme.toString(),year,quarter};

        return list;
    }
    /**
     * 查看页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view/{id}")
    public String view(@PathVariable("id") String id, Model model) {
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("batchDecimal",Operator.EQ,id));
        Sublog subLogs = sublogService.get(filters);
        String pattern = "yyyy年MM月dd日";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String time ="";
        if(null != subLogs.getSubmissionDate()){
            time = simpleDateFormat.format(subLogs.getSubmissionDate());
        }
        model.addAttribute("time",time);
        model.addAttribute("id",id);
        return "sub/eastGljyhzbView";
    }

    @RequestMapping("/viewDetail")
    @ResponseBody
    public Map<String, Object> viewDetail(HttpServletRequest request,String id,Page<EastGljyhzbView> page) {
        List<Filter> filters = RequestUtils.buildFilters(request);
        Map<String, Object> result = Maps.newHashMap();
       // filters.add(Filter.eq("pch", id));
        List<EastGljyhzbView> query = eastGljyhzbViewService.queryOne(id);

        PageInfo<EastGljyhzbView> pageInfo = new PageInfo<>(query);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;

    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/remove/{id}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable("id") Integer id) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            Sublog sublog = sublogService.get(id);
            List<Filter> filters = new ArrayList<>();
            filters.add(Filter.eq("pch", sublog.getBatchDecimal()));
            //删除sublog中的数据
            sublogService.deleteByPrimaryKey(id);
            List<EastGljyhzbView> select = eastGljyhzbViewService.select(filters);
            for (EastGljyhzbView eastGljyhzbView : select) {
                EastGljyhzb eastGljyhzb = eastGljyhzbService.get(eastGljyhzbView.getRelid());
                eastGljyhzb.setEastStatus(1);
                eastGljyhzb.setDelflag("1");
                eastGljyhzbService.updateSelective(eastGljyhzb);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return tips;
    }

    /**
     * 为待报送准备数据
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping("/querySubFile")
    @ResponseBody
    public Map<String, Object> querySubFile(Page<EastGljyhzb> page, HttpServletRequest request) {
        Map<String, Object> map = Maps.newHashMap();
        PageHelper.start(page);
        List<EastGljyhzb> relPars = eastGljyhzbService.queryAll();
        PageInfo<EastGljyhzb> pageInfo = new PageInfo<>(relPars);
        map.put(RESULT_TOTAL, pageInfo.getTotal());
        map.put(RESULT_ROWS, pageInfo.getList());
        return map;
    }

}
