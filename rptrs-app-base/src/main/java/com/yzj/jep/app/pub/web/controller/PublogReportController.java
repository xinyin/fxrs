package com.yzj.jep.app.pub.web.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.TraEnclosure;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.ins.web.controller.Commons;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.PublogReportService;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.sub.util.DownFileUtils;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pub/publogReport")
public class PublogReportController extends CrudController<Publog> {
    private static Logger logger = LoggerFactory.getLogger(PublogReportController.class);
    @Autowired
    private PublogService publogService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private PublogReportService publogReportService;
    @Autowired
    private TraInterestService traInterestService;

    @Autowired
    private TraInvestmentService traInvestmentService;

    @Autowired
    private TraFundsService traFundsService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private TraBusinessService traBusinessService;

    @Autowired
    private TraServiceService traServiceService;

    @Autowired
    private TraOtherService traOtherService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "报告管理",description = "列表数据查询页面")
    public String list(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || tenantId ==""){
            model.addAttribute("subOrg", "");
        }else {
            model.addAttribute("subOrg", tenantId);
        }
        return "pub/publogReportList";
    }

    /**
     * 新增报送文件
     */
    @RequestMapping(value = "/add")
    public String add(Model model){
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || tenantId ==""){
            model.addAttribute("subOrg", "");
        }else {
            model.addAttribute("subOrg", tenantId);
        }
        return "pub/publogReportAdd";
    }


    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Publog> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(Filter.eq("delflag", "1"));
        orders.add(Order.desc("maxPercentInNetAssets"));
        filters.add(Filter.eq("pubType","3"));

        publogService.query(page, filters, orders);

    }


    /**
     * 为新增报告文件页面准备数据
     */
    @RequestMapping(value = "/allReport")
    @ResponseBody
    public void allReport(Page<PublogRegularVo> page, HttpServletRequest request, HttpServletResponse response) {

        List<Filter> filters = RequestUtils.buildFilters(request);

        Map<String, Object> result = new HashMap<>();
//        ?traSubName=0001,0001-ZZHK&majorTrade=0
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        String unitCode = userProfile.getUnitCode();
        String userid = userProfile.getUserid();
        AuthorityUtil authorityUtil = new AuthorityUtil();
        filters = authorityUtil.getFilterByRole(filters);
        Commons.common(request, filters);
        filters.add(Filter.eq("delflag", "1"));
//        orders.add(Order.desc("modifiedTime"));
        //处理交易截止日期modifiedTime时间，往后加一天
        List<Filter> handleFilters = new ArrayList<>();
//        防止有缓存
        handleFilters.addAll(filters);
        com.yzj.jep.app.sys.util.Constants.conditions.clear();
        com.yzj.jep.app.sys.util.Constants.conditions.put("filters", handleFilters);
        List<PublogRegularVo> publogRegularVos = publogReportService.getAll();



        page.setTotal(publogRegularVos.size());
        int pages = (publogRegularVos.size() + page.getPageSize() - 1) / page.getPageSize();
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
     * 报送文件的下载
     * serverUrl 从服务器上要下载的路径
     * filePath 要保存到本地的路径
     * fileName 文件名
     */
    @RequestMapping(value = "/downloadFile/{pid}")
    @ResponseBody
    @Log(module = "报告管理",description = "报告文件的下载")
    public Map<String,Object> pubDownFile(@PathVariable String pid, HttpServletRequest request, HttpServletResponse response){
        logger.info("报告文件的下载开始：");
        Map<String,Object> tipes = Maps.newHashMap();
        //通过pid查询sublog记录表 获取文件名和下载路径
        Publog publog = publogService.get(Integer.valueOf(pid));
        int number = publog.getDownAmt();   //获取下载次数
        //文件名
        String fileName = publog.getPubFileName();
        //下载服务器的路径  后期在sublog记录表中添加保存路径字段
        String serverUrl = publog.getFileUrl();
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

    /*
     * 文件上传
     * */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam("pid") Integer pid, @RequestParam("fileDate") String fileDate, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> tips = new HashMap<String, Object>();
        TraEnclosure enclosure = null;
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
            List<MultipartFile> multipartFileList = multipartRequest.getFiles("file");
            Publog plog = publogService.get(pid);
            String path = plog.getFileUrl();
            for (MultipartFile file : multipartFileList) {
                if (!file.isEmpty()) {
                    String fileNameAll = file.getOriginalFilename(); //文件全名
                    String fileName = fileNameAll.substring(0, fileNameAll.lastIndexOf("."));  //文件名
                    String[] strArray = fileNameAll.split("\\.");
                    int suffixIndex = strArray.length - 1;
                    String fileType = strArray[suffixIndex] + ""; //文件类型
                    double size = file.getSize();
                    System.out.println("文件类型为 " + fileType);
                    double fileSizeMB = new BigDecimal(size / 1024 / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
                    System.out.println(fileNameAll + "-->" + fileSizeMB + "MB");
                    File dest = new File(path + "/" + fileNameAll);
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdir();
                    }
                    file.transferTo(dest);
                    enclosure = new TraEnclosure();
                    tips.put("fileUrl", enclosure);

                }
            }
            Publog publog = new Publog();
            publog.setStatus("02");
            publog.setPid(pid);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            publog.setArchDate(sdf.parse(fileDate));
            publogService.updateSelective(publog);
            switch (plog.getTranType()) {
                case "01"://投资入股类
                    TraInvestment traInterestment = new TraInvestment();
                    traInterestment.setPid(plog.getRelId());
                    traInterestment.setArchivingStatus("2");
                    traInvestmentService.updateSelective(traInterestment);
                    break;
                case "02"://资金运用类
                    TraFunds traFunds = new TraFunds();
                    traFunds.setPid(plog.getRelId());
                    traFunds.setArchivingStatus("2");
                    traFundsService.updateSelective(traFunds);
                    break;
                case "03"://保险业务类
                    TraBusiness traBusiness = new TraBusiness();
                    traBusiness.setPid(plog.getRelId());
                    traBusiness.setArchivingStatus("2");
                    traBusinessService.updateSelective(traBusiness);
                    break;
                case "04"://  利益转移类
                    TraInterest traInterest = new TraInterest();
                    traInterest.setPid(plog.getRelId());
                    traInterest.setArchivingStatus("2");
                    traInterestService.updateSelective(traInterest);
                    break;
                case "05": //提供服务类
                    TraService traService=new TraService();
                    traService.setPid(plog.getRelId());
                    traService.setArchivingStatus("2");
                    traServiceService.updateSelective(traService);
                    break;
                default://其他类
                    TraOther traOther=new TraOther();
                    traOther.setPid(plog.getRelId());
                    traOther.setArchivingStatus("2");
                    traOtherService.updateSelective(traOther);
                    break;
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传失败");
        }
        return tips;
    }
}
