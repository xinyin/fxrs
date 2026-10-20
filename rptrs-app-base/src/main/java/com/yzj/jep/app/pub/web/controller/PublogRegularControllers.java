package com.yzj.jep.app.pub.web.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.ins.web.controller.Commons;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.PublogRegularService;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.util.DownFileUtils;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("/pub/publogRegular")
public class PublogRegularControllers extends CrudController<Publog> {
    private static Logger logger = LoggerFactory.getLogger(PublogRegularControllers.class);
    @Autowired
    private PublogService publogService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private PublogRegularService publogRegularService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/regularList")
    @Log(module = "披露管理", description = "查询生成披露文件列表")
    public String lists(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            model.addAttribute("pubSubject", "");
        } else {
            model.addAttribute("pubSubject", tenantId);
        }
        return "pub/publogRegularList";
    }


    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Publog> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(Filter.eq("delflag", "1"));
        orders.add(Order.desc("maxPercentInNetAssets"));
        filters.add(Filter.eq("pubType","1"));

//        UserProfile profile = UserProfileUtils.getUserProfile();
//        String userid = profile.getUserid();
//        String unitCode = profile.getUnitCode();

//        if (unitCode.contains("0001")) {
//            filters.add(new Filter("createByName", userid));
//        } else {
//            List<Filter> uFilters = new Filter.Builder().like("deptCode", profile.getDeptCode()).build();
//            List<Staff> select = staffService.select(uFilters);
//            List<String> list = new ArrayList<>();
//            for (Staff s : select) {
//                String deptName = s.getDeptName();
//                list.add(deptName);
//            }
//            filters.add(new Filter("pubSubject", Operator.NE, list));
//        }

        publogService.query(page, filters, orders);

    }



    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        if (unitCode.contains("0001")) {
            model.addAttribute("unitCodeAdd", "0001,0001-ZZHK");
        } else {
            model.addAttribute("unitCodeAdd", unitCode);
        }
        if (unitCode.contains("ZZ_Delegation")) {
            model.addAttribute("unitCodeAdd", unitCode);
        }

        return "pub/publogRegularAdd";
    }


    @RequestMapping(value = "/allFunds")
    @ResponseBody
    public void allFunds(Page<PublogRegularVo> page, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        //是重大或是统一交易协议 且 状态为6，9，12
        List<PublogRegularVo> publogRegularVos = publogRegularService.getAll();
        page.setTotal(publogRegularVos.size());
        int pages = (publogRegularVos.size() + page.getPageSize() -
                1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<PublogRegularVo> publogRegularVoList = new ArrayList<PublogRegularVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < publogRegularVos.size()) {
                publogRegularVoList.add(publogRegularVos.get(i));
            }
        }

        page.setResult(publogRegularVoList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        try {
            response.getWriter().print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 披露文件的下载
     * serverUrl 从服务器上要下载的路径
     * filePath 要保存到本地的路径
     * fileName 文件名
     */
    @RequestMapping(value = "/downloadFile/{pid}")
    @ResponseBody
    @Log(module = "披露管理",description = "披露文件的下载")
    public Map<String,Object> pubDownFile(@PathVariable String pid, HttpServletRequest request, HttpServletResponse response){
        logger.info("报送文件的下载开始：");
        Map<String,Object> tipes = Maps.newHashMap();
        //通过pid查询sublog记录表 获取文件名和下载路径
        Publog publog = publogService.get(Integer.valueOf(pid));
        int number = publog.getDownAmt();   //获取下载次数
        //文件名
        String fileName = publog.getPubFileName();
        //下载服务器的路径  后期在sublog记录表中添加保存路径字段
        String serverUrl = publog.getFileUrl()+"/"+fileName;
//		String serverUrl = "/home/rptrs/subFile/" + fileName ;
//		String serverUrl = "F:\\credit\\"+fileName;
        //浏览器下载
        boolean aaa = DownFileUtils.downServerFile(serverUrl,fileName,request,response);
        if (aaa){
            logger.info("下载文件成功！");
            publog.setPid(Integer.valueOf(pid));
            publog.setDownStatus("0");
            publog.setDownAmt(number + 1);
            publogService.updateSelective(publog);
            tipes.put(WebCode.SUCCESS, true);
            tipes.put(WebCode.MSG, WebCode.DOWN_SUCCESS_FILE);
        }else{
            logger.info("下载文件失败！");
            tipes.put(WebCode.SUCCESS, false);
            tipes.put(WebCode.MSG, WebCode.DOWN_FAILED_FILE);
        }
        return tipes;
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
            publogService.deleteByPrimaryKey(pid);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }


}
