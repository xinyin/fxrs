/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月05日 16:32:51
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.GdFile;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.GdFileService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping("/bus/gdFile")
public class GdFileController extends CrudController<GdFile> {
    private static Logger logger = LoggerFactory.getLogger(GdFileController.class);

    @Autowired
    private GdFileService gdFileService;

    /**
     * 获取列表页面gdTraZrEdit
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/gdFileList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<GdFile> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        gdFileService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/gdFileEdit";
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

        GdFile gdFile = gdFileService.get(pid);
        model.addAttribute("gdFile", gdFile);
        model.addAttribute("opt", "edit");

        return "bus/gdFileEdit";
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

        GdFile gdFile = gdFileService.get(pid);
        model.addAttribute("gdFile", gdFile);

        return "bus/gdFileView";
    }

    /**
     * 保存
     *
     * @param gdFile
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(GdFile gdFile) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = gdFileService.exists(gdFile.getPid());
        try {
            if (isExist) {

                gdFileService.update(gdFile);
            } else {
                gdFileService.insert(gdFile);
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
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            gdFileService.deleteByPrimaryKey(pid);
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
     * 验证提交的pid是否已经存在数据库中
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = gdFileService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    @RequestMapping(value = "/queryList")
    @ResponseBody
    public Map<String, Object> query(Page<GdFile> page, Model model, HttpServletRequest request, String traSerial) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        filters.add(new Filter("traSerial", Operator.EQ, traSerial));
        prepareQuery(page, filters, orders, request);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /*
     * 股权交易附件上传
     * */
    @RequestMapping("/uploadDocFile")
    @ResponseBody
    public Map<String, Object> load(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        Map<String, Object> tips = new HashMap<String, Object>();

        List<GdFile> docFileResult = new ArrayList<>();
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
                //
                List<MultipartFile> fileList = multipartRequest.getFiles("file");
                for (MultipartFile file : fileList) {
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

                        String path = "/home/docFile/upload";
//                        String path = "E:/home/docFile/upload";
                        File dest = new File(path + "/" + fileNameAll);
                        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                            dest.getParentFile().mkdir();
                        }
                        file.transferTo(dest); //保存文件
                        GdFile gdFile = new GdFile();
                        gdFile.setFileName(fileName); //文件名称
                        gdFile.setFileType(fileType); //文件类型
                        gdFile.setFileUrl(path + "/" + fileNameAll); //文件路径
                        gdFile.setDelflag("1");//删除状态 0代表是（查询接口不传删除的数据) 1代表否
                        gdFile.setFileSize(String.valueOf(fileSizeMB));
                        gdFileService.insert(gdFile);
                        Integer pid = gdFileService.select(gdFile).get(0).getPid();
                        List<Filter> filterForDocFile = new Filter.Builder().eq("pid", pid).eq("delflag", 1).build();
                        List<GdFile> gdFiles = gdFileService.select(filterForDocFile);
                        if (gdFiles != null && gdFiles.size() > 0) {
                            GdFile gdFile1 = gdFiles.get(0);
                            docFileResult.add(gdFile1);
                        }
                        tips.put(WebCode.SUCCESS, true);
                        tips.put(WebCode.MSG, "上传成功.");
                        tips.put("fileData", docFileResult);
                    } else {
                        tips.put(WebCode.SUCCESS, false);
                        tips.put(WebCode.MSG, "上传失败,未收到文件.");
                    }
                }
            }
        } catch (Exception e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传失败，系统出现未知异常.");
            logger.error("上传失败：" + e.getMessage());
            e.printStackTrace();
        }
        return tips;
    }

    //PDF文件预览
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void pdfStreamHandler(HttpServletRequest request, HttpServletResponse response) {
        String pidStr = request.getParameter("pid");
        if (StringUtils.isEmpty(pidStr)) {
            return;
        }
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.EQ, Integer.valueOf(pidStr)));
        List<GdFile> gdFiles = gdFileService.select(filters);
        String path = "";
        String fileName = "";
        if (gdFiles != null && gdFiles.size() > 0) {
            path = gdFiles.get(0).getFileUrl();
            fileName = gdFiles.get(0).getFileName();
        }
        File file = new File(path);

        if (file.exists()) {
            byte[] data = null;
            try {
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                FileInputStream input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
                System.out.println("pdf文件处理异常：" + e);
            }

        } else {
            return;
        }
    }

    /**
     * 移除文件
     */
    @RequestMapping("/removeFile/{pids}")
    @ResponseBody
    @Transactional
    public Map<String, Object> removeDoc(@PathVariable String pids) {
        Map<String, Object> tips = new HashMap<>();
        try {
            if (StringUtils.isEmpty(pids)) {
                tips.put(WebCode.FAILED, false);
                tips.put(WebCode.MSG, "未选择删除的文件");
                return tips;
            }
            String[] StrPidArr = pids.split(",");
            for (String str : StrPidArr) {
                GdFile gdFile = new GdFile();
                gdFile.setPid(Integer.valueOf(str));
                gdFile.setDelflag("0");
                gdFileService.updateSelective(gdFile);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (Exception e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return tips;
    }
}