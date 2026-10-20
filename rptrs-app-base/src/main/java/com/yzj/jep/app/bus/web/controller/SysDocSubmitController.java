/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月29日 14:02:55
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.model.Doc;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.util.ZipUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.util.StringUtil;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import net.sf.jxls.transformer.XLSTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/bus/sysDocSubmit")
@PropertySource("classpath:submit.properties")
public class SysDocSubmitController extends CrudController<DocSubmit> {
    private static Logger logger = LoggerFactory.getLogger(SysDocSubmitController.class);

    @Autowired
    private DocSubmitService docSubmitService;

    @Autowired
    private DocService docService;


    @Autowired
    private RelOrgSuspectService relOrgSuspectService;

    @Autowired
    private SubmitRecordService submitRecordService;

    @Autowired
    private DocFileService docFileService;

    @Value("${sysSubmitFilePath}")
    private String filePath;
    @Value("${sysSubmitTemplatePath}")
    private String sysSubmitTemplatePath;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/sysDocSubmitList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DocSubmit> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        orders.add(new Order("generateTime", OrderType.DESC));
        Iterator<Filter> it = filters.iterator();
        //删除all
        while (it.hasNext()) {
            Filter x = it.next();
            if ("all".equals(x.getValue())) {
                it.remove();
            }
        }

        //做日期判断
        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < filters.size(); i++) {
            if ("beginDate".equals(filters.get(i).getProperty())) {
                startIndex = i;
                String startDate = filters.get(i).getValue().toString();
                filters.add(new Filter("generateTime", Operator.GE, startDate));
            }
            if ("endDate".equals(filters.get(i).getProperty())) {
                endIndex = i;
                String endDate = filters.get(i).getValue().toString();
                filters.add(new Filter("generateTime", Operator.LE, endDate));
            }
        }
        //删除两个过滤器  --注意：此处先删除endDate  本着从后往前删除的原则
        if (endIndex != -1) {
            filters.remove(endIndex);
        }
        if (startIndex != -1) {
            filters.remove(startIndex);
        }


        //逻辑删除标记
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        for (int i = 0; i < filters.size(); i++) {

        }
        filters.add(new Filter("submitType", Operator.EQ, "0005"));
        docSubmitService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/sysDocSubmitEdit";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, Model model) {

        DocSubmit docSubmit = docSubmitService.get(pid);
        model.addAttribute("docSubmit", docSubmit);
        model.addAttribute("opt", "edit");

        return "bus/sysDocSubmitEdit";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model) {

        DocSubmit docSubmit = docSubmitService.get(pid);
        model.addAttribute("docSubmit", docSubmit);
        //查询制度的详情信息
        List<Filter> sysFilter = new ArrayList<>();
        sysFilter.add(new Filter("batchNo", Operator.EQ, docSubmit.getBatchNo()));
        List<SubmitRecord> submitRecordList = submitRecordService.select(sysFilter);
        List<Doc> docList = new ArrayList<>();
        Doc doc;
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        formater.applyPattern("yyyy-MM-dd");
        for (int i = 0; i < submitRecordList.size(); i++) {
            doc = docService.get(Integer.parseInt(submitRecordList.get(i).getFromPid()));
            String eff = formater.format(doc.getEffectiveTime());
            try {
                doc.setEffectiveTime(formater.parse(eff));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            docList.add(doc);
        }
        model.addAttribute("doc", docList);
        return "bus/sysDocSubmitView";
    }

    /**
     * 保存
     *
     * @param docSubmit
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(DocSubmit docSubmit) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = docSubmitService.exists(docSubmit.getPid());
        try {
            if (isExist) {

                docSubmitService.updateSelective(docSubmit);
            } else {
                docSubmitService.insertSelective(docSubmit);
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
     * @param pid
     * @return
     */
    @RequestMapping(value = "/remove/{pid}")
    public String remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            DocSubmit docSubmit = docSubmitService.get(pid);
            docSubmit.setDelflag("0");
            docSubmitService.updateSelective(docSubmit);
//			docSubmitService.deleteByPrimaryKey(pid);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return "bus/sysDocSubmitList";
    }

    /**
     * 验证提交的pid是否已经存在数据库中
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = docSubmitService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


    /*
     * 统计制度文件
     * */
    @RequestMapping(value = "/checkSubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> checkSubmit(DocSubmit docSubmit, Model model) {

        Map<String, Object> tips = new HashMap<String, Object>();
        String year = docSubmit.getYear();
        String quarter = docSubmit.getQuarter();
        String month = docSubmit.getMonth();
        //默认报送机构都是成都农商行

        //获取全部数据
        List<Filter> filters = new ArrayList<>();
        //筛选出行内
        filters.add(new Filter("systemType", Operator.EQ, "I"));
        List<Doc> docList = docService.select(filters);
        Iterator<Doc> it = docList.iterator();
        Doc doc = null;
        Integer docYear;
        Integer docMonth;
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        while (it.hasNext()) {
            doc = it.next();
            Date date = doc.getEffectiveTime();
            if (date == null) {
                it.remove();
                continue;
            }
            //年度筛选
            if (StringUtil.isNotEmpty(year)) {
                if (Integer.parseInt(sdf0.format(date)) != Integer.parseInt(year)) {
                    it.remove();
                    continue;
                }
            }
            //季度筛选
            if (StringUtil.isNotEmpty(quarter) && !"all".equals(quarter)) {
                docMonth = Integer.parseInt(sdf1.format(date));
                if ("one".equals(quarter)) {
                    if (docMonth == 1 || docMonth == 2 || docMonth == 3) {
                    } else {
                        it.remove();
                        continue;
                    }
                } else if ("two".equals(quarter)) {
                    if (docMonth == 4 || docMonth == 5 || docMonth == 6) {
                    } else {
                        it.remove();
                        continue;
                    }

                } else if ("three".equals(quarter)) {
                    if (docMonth == 7 || docMonth == 8 || docMonth == 9) {
                    } else {
                        it.remove();
                        continue;
                    }
                } else if ("four".equals(quarter)) {
                    if (docMonth == 10 || docMonth == 11 || docMonth == 12) {
                    } else {
                        it.remove();
                        continue;
                    }
                }
            }

            //月份筛选
            if (StringUtil.isNotEmpty(month) && !"all".equals(month)) {
                docMonth = Integer.parseInt(sdf1.format(date));
                if (docMonth != Integer.parseInt(month)) {
                    it.remove();
                    continue;
                }
            }
        }
        tips.put(WebCode.MSG, "共" + docList.size() + "条记录!");
        tips.put(WebCode.SUCCESS, true);
        //将查到的数据返回给页面
        tips.put("check", "统计无误！");
        tips.put("docList", docList);
        model.addAttribute("docList", docList);
        return tips;
    }


    /*
     * 生成报送文件
     * */
    @RequestMapping(value = "/makeSubmitFile/{pids}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> makeSubmitFile(DocSubmit docSubmit, @PathVariable String pids) {

        Map<String, Object> tips = new HashMap<String, Object>();
        String year = docSubmit.getYear();
        String quarter = docSubmit.getQuarter();
        String month = docSubmit.getMonth();
        //默认报送机构都是成都农商行
        Integer docYear;
        Integer docMonth;
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");


        Integer batchMaxPid = relOrgSuspectService.getMaxId("bus_submit_batch");
        Integer recordMaxPid = relOrgSuspectService.getMaxId("bus_submit_record");
        if (batchMaxPid == null) {
            batchMaxPid = 0;
        }
        if (recordMaxPid == null) {
            recordMaxPid = 0;
        }
        //入报送
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String formatStr = formatter.format(date);
        String fileName = "";
        String path = filePath;
        docSubmit.setPid(batchMaxPid + 1);
        docSubmit.setFileName(fileName);
        docSubmit.setBatchNo(formatStr);
        docSubmit.setGenerateTime(date);
        docSubmit.setSubmitType("0005");
        //头次生成默认下载次数为0
        docSubmit.setDownloadCount(0);
        docSubmit.setDelflag("1");
        Calendar now = Calendar.getInstance();
        //月份
        if ("all".equals(docSubmit.getMonth())) {
            docSubmit.setMonth((now.get(Calendar.MONTH) + 1) + "");
        }
        //年份
        if (StringUtil.isEmpty(docSubmit.getYear())) {
            docSubmit.setYear(now.get(Calendar.YEAR) + "");
        }
        //季度
        if ("all".equals(docSubmit.getQuarter())) {
            Integer mon = Integer.parseInt(docSubmit.getMonth());
            if (mon == 1 || mon == 2 || mon == 3) {
                docSubmit.setQuarter("one");
            } else if (mon == 4 || mon == 5 || mon == 6) {
                docSubmit.setQuarter("two");
            } else if (mon == 7 || mon == 8 || mon == 9) {
                docSubmit.setQuarter("three");
            } else if (mon == 10 || mon == 11 || mon == 12) {
                docSubmit.setQuarter("four");
            }
        }
        String[] pidArr = pids.split(",");
        Integer pid = null;
        for (int i = 0; i < pidArr.length; i++) {
            pid = Integer.parseInt(pidArr[i]);
            //获取全部数据
            Doc doc = docService.get(pid);
            fileName = doc.getSysName() + formatStr + ".zip";
            String zipFileName = path + "/" + doc.getSysName() + formatStr + ".zip";
            docSubmit.setFilePath(zipFileName);
            if (docSubmit.getMonth() == null) {
                docSubmit.setMonth(formatStr.substring(3, 2));
            }

            String srcFilePath = sysSubmitTemplatePath;
            String destFilePath = path + "/" + fileName;
            Map<String, Doc> beanParams = new HashMap<String, Doc>();
            beanParams.put("doc", doc);

            //入报送记录
            SubmitRecord submitRecord = new SubmitRecord();
            recordMaxPid++;
            submitRecord.setPid(recordMaxPid);
            submitRecord.setBatchNo(formatStr);
            submitRecord.setFromPid(pid.toString());
            submitRecord.setDelflag("1");
            submitRecord.setSubmitType("0005");
            XLSTransformer former = new XLSTransformer();
            try {
                former.transformXLS(srcFilePath, beanParams, destFilePath);
                /*
                 * 生成压缩包文件
                 * */
                List<File> fileList = new ArrayList<>();
                //生成制度信息文件
                File file1 = new File(destFilePath);
                if (file1.exists()) {
                    fileList.add(file1);
                }
                doc = docService.get(Integer.parseInt(submitRecord.getFromPid()));
                List<Filter> docFilter = new ArrayList<>();
                docFilter.add(new Filter("docPid", Operator.EQ, doc.getPid().toString()));
                List<DocFile> docFiles = docFileService.select(docFilter);
                //附件
                File file2 = new File(docFiles.get(0).getFileUrl());
                if (file2.exists()) {
                    fileList.add(file2);
                }
                File[] array = fileList.toArray(new File[0]);
                try {
                    if (array.length > 0) {
                        ZipUtil.zip(filePath, zipFileName, array);
                    }
                    submitRecordService.insertSelective(submitRecord);
                } catch (Exception e) {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "生成失败");
                    e.printStackTrace();
                    return tips;
                }
            } catch (Exception e) {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, "生成失败");
                e.printStackTrace();
                return tips;
            }

        }
        docSubmitService.insertSelective(docSubmit);
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, "生成成功");
        return tips;
    }

    /*
     * 下载制度文件  一个压缩包
     * */
    @RequestMapping(value = "/download/{pid}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer pid) {
        DocSubmit docSubmit = docSubmitService.get(pid);
        Map<String, Object> tips = new HashMap<String, Object>();
        String filename = docSubmit.getFileName();
        String filePath = docSubmit.getFilePath();
        Integer count = docSubmit.getDownloadCount();
        if (count == null) {
            count = 0;
        }
        File file = new File(filePath);

        if (file.exists()) {
            OutputStream out = null;
            FileInputStream in = null;
            try {
                // 1.读取要下载的内容
                in = new FileInputStream(file);

                // 2. 告诉浏览器下载的方式以及一些设置
                // 解决文件名乱码问题，获取浏览器类型，转换对应文件名编码格式，IE要求文件名必须是utf-8, firefo要求是iso-8859-1编码
                String agent = request.getHeader("user-agent");
                if (agent.contains("FireFox")) {
                    filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
                } else {
                    filename = URLEncoder.encode(filename, "UTF-8");
                }
                // 设置下载文件的mineType，告诉浏览器下载文件类型
                String mineType = request.getServletContext().getMimeType(filename);
                response.setContentType(mineType);
                // 设置一个响应头，无论是否被浏览器解析，都下载
                response.setHeader("Content-disposition", "attachment; filename=" + filename);
                // 将要下载的文件内容通过输出流写到浏览器
                out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                count++;
                docSubmit.setDownloadCount(count);
                docSubmitService.updateSelective(docSubmit);
                tips.put("valid", "下载成功");
            } catch (Exception e) {
                e.printStackTrace();
                tips.put("valid", "下载失败");
            } finally {

                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {

            try {
                PrintWriter out = response.getWriter();
                out.write("文件不存在");
            } catch (IOException e) {
                e.printStackTrace();
            }
            tips.put("valid", "文件不存在");
        }
    }
}