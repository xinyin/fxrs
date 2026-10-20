/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月29日 14:02:55
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.bus.model.DocSubmit;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.model.SubmitRecord;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.bus.vo.QuarterSubmitVo;
import com.yzj.jep.app.rule.submit.QuarterDocSubmitRuler;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/bus/quarterDocSubmit")
@PropertySource("classpath:submit.properties")
public class QuarterDocSubmitController extends CrudController<DocSubmit> {
    private static Logger logger = LoggerFactory.getLogger(QuarterDocSubmitController.class);

    @Autowired
    private DocSubmitService docSubmitService;

    @Autowired
    private QuarterDocSubmitRuler quarterDocSubmitRuler;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelOrgSuspectService relOrgSuspectService;

    @Autowired
    private SubmitRecordService submitRecordService;

    @Value("${quarterSubmitFilePath}")
    private String filePath;
    @Value("${quarterSubmitTemplatePath}")
    private String quarterSubmitTemplatePath;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/quarterDocSubmitList";
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
        filters.add(new Filter("submitType", Operator.EQ, "0004"));
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

        return "bus/quarterDocSubmitEdit";
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

        return "bus/quarterDocSubmitEdit";
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

        return "bus/quarterDocSubmitView";
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
            docSubmitService.deleteByPrimaryKey(pid);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return "bus/quarterDocSubmitList";
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

    @RequestMapping(value = "/checkSubmit", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> checkSubmit(DocSubmit docSubmit) {

        Map<String, Object> tips = new HashMap<String, Object>();
        String year = docSubmit.getYear();
        String quarter = docSubmit.getQuarter();
        String month = docSubmit.getMonth();
        //默认报送机构都是成都农商行

        //获取全部数据
        List<QuarterSubmitVo> list = new ArrayList<>();
        List<RelPsn> relPsnList = relPsnService.selectAll();
        QuarterSubmitVo quarterSubmitVo;
        for (int i = 0; i < relPsnList.size(); i++) {
            quarterSubmitVo = new QuarterSubmitVo();
            quarterSubmitVo.setOrgName(relPsnList.get(i).getName());
            quarterSubmitVo.setUniscCode(relPsnList.get(i).getIdentifyNumber());
            quarterSubmitVo.setActiveTime(relPsnList.get(i).getActiveTime());
            list.add(quarterSubmitVo);
        }
        List<RelOrg> relOrgList = relOrgService.selectAll();
        for (int j = 0; j < relOrgList.size(); j++) {
            quarterSubmitVo = new QuarterSubmitVo();
            quarterSubmitVo.setOrgName(relOrgList.get(j).getOrgName());
            quarterSubmitVo.setUniscCode(relOrgList.get(j).getUniscCode());
            quarterSubmitVo.setActiveTime(relOrgList.get(j).getActiveTime());
            list.add(quarterSubmitVo);
        }

        Iterator<QuarterSubmitVo> it = list.iterator();
        QuarterSubmitVo vo = null;
        Integer psnYear;
        Integer psnMonth;
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        while (it.hasNext()) {
            vo = it.next();
            Date date = vo.getActiveTime();
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
                psnMonth = Integer.parseInt(sdf1.format(date));
                if ("one".equals(quarter)) {
                    if (psnMonth == 1 || psnMonth == 2 || psnMonth == 3) {
                    } else {
                        it.remove();
                        continue;
                    }
                } else if ("two".equals(quarter)) {
                    if (psnMonth == 4 || psnMonth == 5 || psnMonth == 6) {
                    } else {
                        it.remove();
                        continue;
                    }

                } else if ("three".equals(quarter)) {
                    if (psnMonth == 7 || psnMonth == 8 || psnMonth == 9) {
                    } else {
                        it.remove();
                        continue;
                    }
                } else if ("four".equals(quarter)) {
                    if (psnMonth == 10 || psnMonth == 11 || psnMonth == 12) {
                    } else {
                        it.remove();
                        continue;
                    }
                }
            }

        }
        //筛选完成之后的数据  开始进行规则判断
        List<String> list1 = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            String str = quarterDocSubmitRuler.checkPsnDocSubmit(list.get(j));
            if (str.length() > 0) {
                list1.add(str);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (list.size() == 0) {
            //代表数据没有问题
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "共" + list.size() + "条记录，全部无问题");
        } else {
            //数据存在问题
            for (int i = 0; i < list1.size(); i++) {
                stringBuilder.append(list1.get(i) + "<br/>");
            }
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据存在问题");
            tips.put("check", stringBuilder.toString());
        }
        return tips;
    }


    /*
     * 生成报送文件
     * */
    @RequestMapping(value = "/makeSubmitFile", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> makeSubmitFile(DocSubmit docSubmit) {

        Map<String, Object> tips = new HashMap<String, Object>();
        String year = docSubmit.getYear();
        String quarter = docSubmit.getQuarter();
        String month = docSubmit.getMonth();
        //默认报送机构都是成都农商行

        //获取全部数据
        List<QuarterSubmitVo> list = new ArrayList<>();
        List<RelPsn> relPsnList = relPsnService.selectAll();
        QuarterSubmitVo quarterSubmitVo;
        for (int i = 0; i < relPsnList.size(); i++) {
            quarterSubmitVo = new QuarterSubmitVo();
            quarterSubmitVo.setOrgName(relPsnList.get(i).getName());
            quarterSubmitVo.setUniscCode(relPsnList.get(i).getIdentifyNumber());
            quarterSubmitVo.setActiveTime(relPsnList.get(i).getActiveTime());
            quarterSubmitVo.setFromPid(relPsnList.get(i).getPid().toString());
            quarterSubmitVo.setType("PSN");
            list.add(quarterSubmitVo);
        }
        List<RelOrg> relOrgList = relOrgService.selectAll();
        for (int j = 0; j < relOrgList.size(); j++) {
            quarterSubmitVo = new QuarterSubmitVo();
            quarterSubmitVo.setOrgName(relOrgList.get(j).getOrgName());
            quarterSubmitVo.setUniscCode(relOrgList.get(j).getUniscCode());
            quarterSubmitVo.setActiveTime(relOrgList.get(j).getActiveTime());
            quarterSubmitVo.setFromPid(relOrgList.get(j).getPid().toString());
            quarterSubmitVo.setType("ORG");
            list.add(quarterSubmitVo);
        }

        Iterator<QuarterSubmitVo> it = list.iterator();
        QuarterSubmitVo vo = null;
        Integer psnYear;
        Integer psnMonth;
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        while (it.hasNext()) {
            vo = it.next();
            Date date = vo.getActiveTime();
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
                psnMonth = Integer.parseInt(sdf1.format(date));
                if ("one".equals(quarter)) {
                    if (psnMonth == 1 || psnMonth == 2 || psnMonth == 3) {
                    } else {
                        it.remove();
                        continue;
                    }
                } else if ("two".equals(quarter)) {
                    if (psnMonth == 4 || psnMonth == 5 || psnMonth == 6) {
                    } else {
                        it.remove();
                        continue;
                    }

                } else if ("three".equals(quarter)) {
                    if (psnMonth == 7 || psnMonth == 8 || psnMonth == 9) {
                    } else {
                        it.remove();
                        continue;
                    }
                } else if ("four".equals(quarter)) {
                    if (psnMonth == 10 || psnMonth == 11 || psnMonth == 12) {
                    } else {
                        it.remove();
                        continue;
                    }
                }
            }

        }


        //开始入库

        //入报送记录
        Integer batchMaxPid = relOrgSuspectService.getMaxId("bus_submit_batch");
        Integer recordMaxPid = relOrgSuspectService.getMaxId("bus_submit_record");
        if (batchMaxPid == null) {
            batchMaxPid = 0;
        }
        if (recordMaxPid == null) {
            recordMaxPid = 0;
        }
        SubmitRecord submitRecord;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String formatStr = formatter.format(date);
        String fileName = "季报信息_" + formatStr + ".xls";
        String path = filePath;
        docSubmit.setPid(batchMaxPid + 1);
        docSubmit.setFileName(fileName);
        docSubmit.setBatchNo(formatStr);
        docSubmit.setGenerateTime(date);
        docSubmit.setSubmitType("0004");
        //头次生成默认下载次数为0
        docSubmit.setDownloadCount(0);
        docSubmit.setDelflag("1");
        Calendar now = Calendar.getInstance();
        //年份
        if (StringUtil.isEmpty(docSubmit.getYear())) {
            docSubmit.setYear(now.get(Calendar.YEAR) + "");
        }
        docSubmit.setMonth((now.get(Calendar.MONTH) + 1) + "");

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

        docSubmit.setFilePath(path + "/" + fileName);
        StringBuilder stringBuilder = new StringBuilder();
        //记录失败个数
        int count = 0;
        for (int j = 0; j < list.size(); j++) {
            submitRecord = new SubmitRecord();
            recordMaxPid++;
            submitRecord.setPid(recordMaxPid);
            String str = quarterDocSubmitRuler.checkPsnDocSubmit(list.get(j));
            submitRecord.setFailCause(str);
            submitRecord.setBatchNo(formatStr);
            submitRecord.setFromPid(list.get(j).getFromPid());
            // 失败
            if (str.length() > 0) {
                submitRecord.setIsSuccess("0");
                count++;
                stringBuilder.append(str);
            } else {
                submitRecord.setIsSuccess("1");
            }
            submitRecord.setSubmitType("0004");
            submitRecord.setDelflag("1");
            submitRecordService.insertSelective(submitRecord);
            //
        }
        // 存储具体的错误信息
        docSubmit.setFailCause(stringBuilder.toString());
        if (count == list.size()) {
            //代表全部存在问题
            docSubmit.setIsSuccess("03");
        } else if (count < list.size() && count > 0) {
            //代表部分存在问题
            docSubmit.setIsSuccess("02");
        } else if (count == 0) {
            //代表没有问题
            docSubmit.setIsSuccess("01");
        }
        //入报送
        String srcFilePath = quarterSubmitTemplatePath;
        String destFilePath = docSubmit.getFilePath();
        Map<String, List<QuarterSubmitVo>> beanParams = new HashMap<String, List<QuarterSubmitVo>>();
        beanParams.put("quarterSubmitVo", list);

        XLSTransformer former = new XLSTransformer();
        int doc = 0;
        try {
            former.transformXLS(srcFilePath, beanParams, destFilePath);
//            doc=1;
            doc = docSubmitService.insertSelective(docSubmit);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (doc == 1) {
            //代表数据没有问题
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "生成成功");
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "生成失败");
        }
        return tips;
    }

    /**
     * @param pid
     * @return
     */
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

//        return tips;
    }


}