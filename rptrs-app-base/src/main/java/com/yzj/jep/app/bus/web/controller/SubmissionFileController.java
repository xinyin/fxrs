/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月16日 13:38:15
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.apache.tools.ant.Project;
import org.apache.tools.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yzj.jep.app.util.ConfigFileUtil;
import com.yzj.jep.app.util.ZipFileUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.SubmissionFile;
import com.yzj.jep.app.bus.service.SubmissionFileService;

@Controller
@RequestMapping("/bus/submissionFile")
public class SubmissionFileController extends CrudController<SubmissionFile> {
    private static Logger logger = LoggerFactory.getLogger(SubmissionFileController.class);

    @Autowired
    private SubmissionFileService submissionFileService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/submissionFileList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<SubmissionFile> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        submissionFileService.query(page, filters, orders);
    }


    /**
     * 按条件查询
     *
     * @param page
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/queryByCon")
    public Map<String, Object> queryByCon(Page<SubmissionFile> page, HttpServletRequest httpServletRequest) {

        //@PathVariable Date beginDate,@PathVariable Date endDate,
        //										   @PathVariable String file_type,@PathVariable Integer submission_status ,Model model
        Map<String, Object> result = new HashMap<String, Object>();
        Map map = new HashMap();
        String beginDate = httpServletRequest.getHeader("beginDate");
        String endDate = httpServletRequest.getHeader("endDate");
        String submission_status = httpServletRequest.getHeader("submission_status");
        String file_type = httpServletRequest.getHeader("file_type");
        map.put("beginDate", beginDate);
        map.put("endDate", endDate);
        map.put("file_type", file_type);
        map.put("submission_status", submission_status);
        List<SubmissionFile> list = submissionFileService.queryByCon(map);
        page.setTotal(list.size());
        int pages = (list.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<SubmissionFile> submissionFile = new ArrayList<>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < list.size()) {
                submissionFile.add(list.get(i));
            }
        }
        page.setResult(submissionFile);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;

    }


    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/submissionFileEdit";
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

        SubmissionFile submissionFile = submissionFileService.get(pid);
        model.addAttribute("submissionFile", submissionFile);
        model.addAttribute("opt", "edit");

        return "bus/submissionFileEdit";
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

        SubmissionFile submissionFile = submissionFileService.get(pid);
        model.addAttribute("submissionFile", submissionFile);

        return "bus/submissionFileView";
    }

    /**
     * 保存
     *
     * @param submissionFile
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(SubmissionFile submissionFile) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = submissionFileService.exists(submissionFile.getPid());
        try {
            if (isExist) {

                submissionFileService.update(submissionFile);
            } else {
                submissionFileService.insert(submissionFile);
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
            submissionFileService.deleteByPrimaryKey(pid);
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

        boolean exist = submissionFileService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


    @RequestMapping(value = "/downloadFile", produces = "text/plain;charset=utf-8")
    public void downloadFile(HttpServletResponse response, HttpServletRequest request) {

        String ids = request.getParameter("pid");
        String[] list = ids.split(",");
        int id;
        List<String> filePaths = new ArrayList<>();
        if (list.length < 2) {
            id = Integer.parseInt(list[0].trim());
            SubmissionFile SubmissionFile = submissionFileService.queryByID(id);
            String fileName = SubmissionFile.getFileName();
            String buildFileType = SubmissionFile.getBuildFileType();
            String path = SubmissionFile.getFileUrl() + "/";
            String txtPath = path + fileName + "." + buildFileType;
            logger.info("---------------------------txtPath" + txtPath);
            ZipFileUtil.downFile(response, txtPath, request);

        } else {
            for (int i = 0; i < list.length; i++) {
                id = Integer.parseInt(list[i].trim());
                SubmissionFile SubmissionFile = submissionFileService.queryByID(id);
                SubmissionFile ttsModel = submissionFileService.queryByID(id);
                String fileName = SubmissionFile.getFileName();
                String buildFileType = SubmissionFile.getBuildFileType();
                String path = SubmissionFile.getFileUrl() + "/";
                String txtPath = path + fileName + "." + buildFileType;
                logger.info("-----------------------------txtPath" + txtPath);

                filePaths.add(txtPath);
            }
//            String zipBasePath = "E:";
//            String zipName="DownLoad.zip";
            //临时文件路径
            //String zipFilePath = "\\home\\tomcat\\down";
            String zipFilePath = (String) ConfigFileUtil.getParamFromProp("zipFilePath");
            File f = new File(zipFilePath);
            ZipOutputStream zos = null;
            try {
                zos = new ZipOutputStream(new FileOutputStream(zipFilePath));
                ZipFileUtil.zipFile(f, filePaths, zos, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //删除压缩包
            if (f.delete()) {
                logger.info("----------------------删除临时文件成功------------------------");
            } else {
                logger.info("----------------------删除临时文件失败-----------------------");
            }
        }

    }


}