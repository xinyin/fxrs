package com.yzj.jep.app.pub.web.controller;

import com.alibaba.fastjson.JSON;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.bus.model.TraEnclosure;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.pub.model.PubFileTemplate;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.model.PublogRegularVo;
import com.yzj.jep.app.pub.service.PubFileTemplateService;
import com.yzj.jep.app.pub.service.PublogQuarterService;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.pub.service.PublogYearService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.core.utils.xlsio.transformer.XlsTransformer4Merge;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.util.StringUtil;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/pub/publogYear")
public class PublogYearController extends CrudController<Publog> {
    private static Logger logger = LoggerFactory.getLogger(PublogYearController.class);
    @Autowired
    private PublogService publogService;
    @Autowired
    private PublogQuarterService publogQuarterService;
    @Autowired
    private PublogYearService publogYearService;
    @Autowired
    private static Configs configs;
    @Autowired
    private PubFileTemplateService pubFileTemplateService;
    @Value("D:\\westForDocFile")
    private String disclosePath;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private TraInvestmentService traInvestmentService;

    @Autowired
    private TraFundsService traFundsService;

    @Autowired
    private TraServiceService traServiceService;

    @Autowired
    private TraOtherService traOtherService;
    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private UnSortOutService unSortOutService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "季度定期披露管理", description = "查询季度定期披露列表")
    public String list(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = profile.getTenantId();
        if (tenantId == null || "".equals(tenantId)) {
            model.addAttribute("pubSubject", "");
        } else {
            model.addAttribute("pubSubject", tenantId);
        }
        return "pub/publogYearList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Publog> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(Filter.eq("delflag", "1"));
        orders.add(Order.desc("maxPercentInNetAssets"));
        filters.add(Filter.eq("pubType", "3"));

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
    public String adds(Model model) {

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

        return "pub/publogYearAdd";
    }

    /**
     * 为新增定期年度披露文件页面做数据准备
     */

    @RequestMapping("/allQuarter")
    @ResponseBody
    public void allQuarter(@RequestParam("quarter") String quarter, @RequestParam("years") String years, Page<PublogRegularVo> page, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        //传进来的quarter和years去掉“,”
        String yearss;
        String quarters;
        quarters=quarter.substring(1,quarter.length());
        yearss=years.substring(1,years.length());
        yearss=years.replace(',',' ');
        if (StringUtils.isBlank(yearss)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            yearss = sdf.format(date);
        }
        Map<String, String> map = publogQuarterService.getTime(quarters, yearss);

        List<PublogRegularVo> publogRegularVos = publogYearService.getYearList(map);


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
                //累计金额为亿元 万元除10000 在保留两位小数四舍五入
                publogRegularVos.get(i).setSumQuarter(publogRegularVos.get(i).getSumQuarter().divide(new BigDecimal(10000),6,BigDecimal.ROUND_HALF_UP));
                publogRegularVos.get(i).setSumYear(publogRegularVos.get(i).getSumYear().divide(new BigDecimal(10000),6,BigDecimal.ROUND_HALF_UP));
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
     * @param bean
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/yearExcel/{bean}/{data}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> exportXls(@PathVariable String bean, @PathVariable String data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<>();
        //参数为空 则不处理
        if (StringUtils.isNotBlank(bean) && StringUtils.isNotBlank(data)) {
            //获取bean
            XlsExportCallback eCallback = (XlsExportCallback) SpringContextHolder.getBean(bean);
            try {
                //请求参数上下文
                Map parameters = new HashMap();

                parameters.put("data", data);//添加请求中的路径参数
                Enumeration e = (Enumeration) request.getParameterNames();
                while (e.hasMoreElements()) {
                    String parameterName = (String) e.nextElement();
                    if (StringUtils.isNotBlank(parameterName)) {
                        parameters.put(parameterName, request.getParameter(parameterName));
                    }
                }
                String[] ids = data.split(",");
                String filename = ids[0] + ".xls";
//                String filePid = ids[3];
                String years = ids[1];
                String quarter = ids[2];
//                PubFileTemplate pubFileTemplate = pubFileTemplateService.get(Integer.parseInt(filePid));

                String templateName = eCallback.getExportTemplate();

                if (StringUtils.isBlank(filename)) {
                    filename = templateName.substring(templateName.lastIndexOf("/") + 1);
                }
//                filename = URLEncoder.encode(filename, "utf8");

                //调用业务方法,组装导出XLS报表所需数据
                Map context = eCallback.executeExportCallback(parameters);
                if (context == null) {
                    context = eCallback.executeExportCallback(request);
                }
                String uuid = UUID.randomUUID().toString();
                //文件存放路径
                String filePath1 = disclosePath + '/' + uuid;
                String filePath = filePath1 + "/" + filename;
                File dest = new File(filePath);
                if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                OutputStream out = new FileOutputStream(dest);
                XlsTransformer4Merge transformer = new XlsTransformer4Merge();
                transformer.setBeanId(bean);
                transformer.transformXLS(templateName, context, out);

                if (null != context) {
                    context.clear();
                }
                if (null != parameters) {
                    parameters.clear();
                }
                //查询
                Publog publog = new Publog();
                //根据relId和交易类型
                publog.setPubYear(years);
                publog.setPubQuarter(quarter);
                List<Publog> publogs = publogService.select(publog);
                if (CollectionUtils.isNotEmpty(publogs) && publogs.size() == 1) {
                    //更新
                    publog = publogs.get(0);
                    publog.setPubFileName(filename);
                    publog.setPubSubject(UserProfileUtils.getUserProfile().getDeptName());
                    publog.setMaxPercentInNetAssets(new Date());
                    publog.setDownAmt(0);
                    publog.setStatus("0");
                    publog.setDownStatus("1");
                    publog.setPubType("3");
                    publog.setFileUrl(filePath1);
                    publogService.updateSelective(publog);
                } else if (CollectionUtils.isEmpty(publogs) && publogs.size() == 0) {
                    //新增
                    publog.setPubFileName(filename);
                    publog.setPubSubject(UserProfileUtils.getUserProfile().getDeptName());
                    publog.setMaxPercentInNetAssets(new Date());
                    publog.setDownAmt(0);
                    publog.setStatus("0");
                    publog.setDownStatus("1");
                    publog.setPubType("3");
                    publog.setFileUrl(filePath1);
                    publog.setPubYear(years);
                    publog.setPubQuarter(quarter);
                    publogService.insertSelective(publog);
                }
                map.put("code", "200");
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", "400");
            }
            return map;
        }
        map.put("code", "400");
        return map;
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
            publog.setStatus("1");
            publog.setPid(pid);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            publog.setArchDate(sdf.parse(fileDate));
            publogService.updateSelective(publog);

            Map<String, String> map = publogQuarterService.getTime(plog.getPubQuarter(), plog.getPubYear());
            List<PublogRegularVo> publogRegularVos = publogYearService.getYearList(map);
            for (int i = 0; i < publogRegularVos.size(); i++) {
                switch (publogRegularVos.get(i).getTranType()) {
                    case "01"://投资入股类
                        TraInvestment traInterestment = new TraInvestment();
                        traInterestment.setPid(publogRegularVos.get(i).getPid());
                        traInterestment.setYearArcStatus("1");
                        traInvestmentService.updateSelective(traInterestment);
                        break;
                    case "02"://资金运用类
                        TraFunds traFunds = new TraFunds();
                        traFunds.setPid(publogRegularVos.get(i).getPid());
                        traFunds.setYearArcStatus("1");
                        traFundsService.updateSelective(traFunds);
                        break;
                    case "03"://保险业务类
                        TraBusiness traBusiness = new TraBusiness();
                        traBusiness.setPid(publogRegularVos.get(i).getPid());
                        traBusiness.setYearArcStatus("1");
                        traBusinessService.updateSelective(traBusiness);
                        break;
                    case "04"://  利益转移类
                        TraInterest traInterest = new TraInterest();
                        traInterest.setPid(publogRegularVos.get(i).getPid());
                        traInterest.setYearArcStatus("1");
                        traInterestService.updateSelective(traInterest);
                        break;
                    case "05": //提供服务类
                        TraService traService = new TraService();
                        traService.setPid(publogRegularVos.get(i).getPid());
                        traService.setYearArcStatus("1");
                        traServiceService.updateSelective(traService);
                        break;
                    default://其他类
                        TraOther traOther = new TraOther();
                        traOther.setPid(publogRegularVos.get(i).getPid());
                        traOther.setYearArcStatus("1");
                        traOtherService.updateSelective(traOther);
                        break;
                }
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
    /**
     * 检查未归类交易数据和未确定交易类型数据存不存在
     */
    @RequestMapping("/checkData")
    @ResponseBody
    public Map<String,Object> checkData(HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String, Object> res = new HashMap<String, Object>();
        //未归类存在交易数据 返回201 不可生成文件
        long count=unSortOutService.count();
        if(count>1){
            res.put("code","201");
            return res;
        }
        res.put("code","200");
        return res;
    }
}
