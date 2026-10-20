/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月20日 11:49:15
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.google.common.collect.Maps;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.bus.model.Doc;
import com.yzj.jep.app.bus.model.DocFile;
import com.yzj.jep.app.bus.service.DocFileService;
import com.yzj.jep.app.bus.service.DocService;
import com.yzj.jep.app.sub.util.DownFileUtils;
import com.yzj.jep.app.sub.util.IdWorker;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.EmailSend;
import com.yzj.jep.app.util.ZipUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bus/docFile")
public class DocFileController extends CrudController<DocFile> {
    private static Logger logger = LoggerFactory.getLogger(DocFileController.class);

    @Autowired
    private DocFileService docFileService;
    @Autowired
    private DocService docService;
    @Autowired
    private static Configs configs;
    @Value("${file.path.systematic}")
    private String systematicPath;

    @Autowired
    private EmailSend emailSend;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        String uploadDep = UserProfileUtils.getUserProfile().getDeptName();
        String uploadOrg = UserProfileUtils.getUserProfile().getUnitName();
        model.addAttribute("uploadDep", uploadDep);
        model.addAttribute("uploadOrg", uploadOrg);

        return "bus/docFileList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DocFile> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        String docPid = request.getParameter("docPid");
        if (StringUtils.isEmpty(docPid)) {
            //filters.add(new Filter("pid", Operator.EQ, 0));
        } else {
            filters.add(new Filter("docPid", Operator.EQ, Integer.parseInt(docPid)));
        }
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        orders.add(new Order("createTime", OrderType.DESC));
        docFileService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/docFileEdit";
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

        DocFile docFile = docFileService.get(pid);
        model.addAttribute("docFile", docFile);
        model.addAttribute("opt", "edit");

        return "bus/docFileEdit";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public void view(@PathVariable Integer pid, HttpServletResponse response,HttpServletRequest request) {

        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.EQ, pid));
        List<DocFile> docFiles = docFileService.select(filters);
        String path = "";
        String fileName = "";
        if (docFiles != null && docFiles.size() > 0) {
            path = docFiles.get(0).getFileUrl();
            fileName = docFiles.get(0).getFileName()+"."+docFiles.get(0).getFileType();
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
                e.printStackTrace();
                System.out.println("pdf文件处理异常：" + e);
            }

        } else {
            return;
        }


    }

    @Override
    public Map<String, Object> query(Page<DocFile> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        int flag = Integer.parseInt(request.getParameter("flag"));
        List<Filter> filters = RequestUtils.buildFilters(request);
        Filter filter = new Filter("uploadType", flag);
        List<Order> orders = RequestUtils.buildOrders(request);
        filters.add(filter);
        prepareQuery(page, filters, orders, request);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 保存
     *
     * @param docFile
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(DocFile docFile) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = docFileService.exists(docFile.getPid());
        try {
            if (isExist) {

                docFileService.update(docFile);
            } else {
                docFileService.insert(docFile);
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
            docFileService.deleteByPrimaryKey(pid);
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

        boolean exist = docFileService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /*
     * 行内外制度文件上传
     * */
    @RequestMapping("/uploadDocFile")
    @ResponseBody
    public Map<String, Object> load(@RequestParam("flag") int flag, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        Map<String, Object> tips = new HashMap<String, Object>();

        List<DocFile> docFileResult = new ArrayList<>();
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
                String pidForDocFiles = multipartRequest.getParameter("pidForDocFiles");
                String pidForDoc = multipartRequest.getParameter("pidForDoc");
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
                        if (size>50.00)
                        {
                            tips.put(WebCode.SUCCESS, false);
                            tips.put(WebCode.MSG, "上传文件过大，请控制在50M内.");
                        }
                        System.out.println(fileNameAll + "-->" + fileSizeMB + "MB");
                        String path = systematicPath;
//                        String path = "D://home//rptrs//upload";
                        File dest = new File(path + "/" + fileNameAll);
                        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                            dest.getParentFile().mkdir();
                        }
                        file.transferTo(dest); //保存文件
                        DocFile docFile = new DocFile();
                        int pid = (int) new IdWorker().nextId();
                        docFile.setPid(pid); //主键
                        docFile.setFileName(fileName); //文件名称
//					    systemfile.setCreateByName(name); //上传人
                        docFile.setFileType(fileType); //文件类型
                        docFile.setFileUrl(path + "/" + fileNameAll); //文件路径
//						docFile.setApproveStatus(0); //审批状态
                        docFile.setDelflag("1");//删除状态 0代表是（查询接口不传删除的数据) 1代表否
                        docFile.setFileSize(fileSizeMB);//文件大小
//			            systemfile.setEffectiveTime(effectiveTime);//生效时间
                        docFile.setCreateTime(new Date()); //创建时间
                        docFile.setCreateTimeString(new Date().toString()); //创建时间
                        docFile.setModifiedTime(new Date()); //修改时间
                        docFile.setModifiedTimeString(new Date().toString()); //修改时间
                        docFile.setUploadType(flag);
//                        docFile.setUploadType(type);
                        docFile.setUploadDeptName(UserProfileUtils.getUserProfile().getDeptName());
                        docFile.setUploadOrgName(UserProfileUtils.getUserProfile().getUnitName());
                        if (!StringUtils.isEmpty(pidForDoc)) {
                            docFile.setDocPid(new Integer(pidForDoc));//制度的pid
                        }
                        docFileService.insert(docFile);

                        List<Filter> filterForDocFile = new Filter.Builder().eq("pid", pid).eq("delflag", 1).build();
                        List<DocFile> docFiles = docFileService.select(filterForDocFile);
                        if (docFiles != null && docFiles.size() > 0) {
                            DocFile docFile1 = docFiles.get(0);
                            docFileResult.add(docFile1);
                        }
                        if (!StringUtils.isEmpty(pidForDocFiles)) {
                            String[] arrayForFilePids = pidForDocFiles.split(",");
                            for (String arrayForFilePid : arrayForFilePids) {
                                List<Filter> filterForDocFile2 = new Filter.Builder().eq("pid", arrayForFilePid).eq("delflag", 1).build();
                                List<DocFile> docFileList = docFileService.select(filterForDocFile2);
                                if (docFileList != null && docFileList.size() > 0) {
                                    docFileResult.add(docFileList.get(0));
                                }
                            }
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
        }
        return tips;
    }

    /*
     * 编辑/新增页制度文件删除
     * */
    @RequestMapping(value = "/removeDocFile/{pid}")
    @ResponseBody
    public Map<String, Object> delDocFileForPid(@PathVariable String pid) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = null;
        if (!StringUtils.isEmpty(pid)) {
            filters = new Filter.Builder().eq("pid", pid).eq("delflag", 1).build();
            List<DocFile> docFiles = docFileService.select(filters);
            if (docFiles != null && docFiles.size() > 0) {
                DocFile docFile = docFiles.get(0);
                docFile.setDelflag("0");
                docFileService.update(docFile);
            }
            result.put(WebCode.SUCCESS_DELETE, true);
            result.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } else {
            result.put(WebCode.FAILURE_DELETE, false);
            result.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return result;
    }

    @RequestMapping(value = "/download/{pid}")
    @ResponseBody
    public Map<String, Object> download(@PathVariable("pid") String pid, HttpServletRequest request, HttpServletResponse response) {
        logger.info("内部制度文件的下载开始：");
        Map<String, Object> tipes = Maps.newHashMap();
        DocFile docFile = docFileService.get(Integer.valueOf(pid));
        String fileName = docFile.getFileName()+"."+docFile.getFileType();
        configs = SpringContextHolder.getBean(Configs.class);
        String serverUrl = configs.getSystematic() + "/" + fileName;
        boolean aaa = false;
        try {
             DownFileUtils.downServerFile(serverUrl, fileName, request, response);
            logger.info("下载文件成功！");
            tipes.put(WebCode.SUCCESS, true);
            tipes.put(WebCode.MSG, WebCode.DOWN_SUCCESS_FILE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("下载文件失败！");
            tipes.put(WebCode.SUCCESS, false);
            tipes.put(WebCode.MSG, WebCode.DOWN_FAILED_FILE);
        }
        return tipes;

    }

    /* *//*
     * 文件导出(下载功能)
     * *//*
     *//*@RequestMapping(value = "/download/{pids}")
    @ResponseBody
    public void download(@PathVariable String pids, HttpServletResponse response, HttpServletRequest request) {
        File file = null;
        List<File> files = new ArrayList<>();
        List<File> filesBig = new ArrayList<>();
        List<Filter> filterForDoc = null;
        List<Filter> filterFoeDocFile = null;
        List<String> sysOutName = new ArrayList<>();
        String zipPath = "D:/downSys/";
        BufferedInputStream bis = null;
        ByteArrayOutputStream bbos = null;
        BufferedOutputStream bos = null;
        try {
            String[] arrayForPids = pids.split(",");
            if (arrayForPids != null && arrayForPids.length > 0) {
                if (arrayForPids.length == 1) {
                    filterForDoc = new Filter.Builder().eq("pid", new Integer(arrayForPids[0])).build();
                    List<Doc> docs = docService.select(filterForDoc);
                    if (docs != null && docs.size() > 0) {
                        String zipName = docs.get(0).getSysName();
                        filterFoeDocFile = new Filter.Builder().eq("docPid", docs.get(0).getPid()).eq("delflag", "1").build();
                        List<DocFile> docFiles = docFileService.select(filterFoeDocFile);
                        if (docFiles != null && docFiles.size() > 0) {
                            for (DocFile docFile : docFiles) {
                                files.add(new File(docFile.getFileUrl()));
                            }
                            File[] files1 = files.toArray(new File[0]);
                            String zipPathAll = zipPath + zipName + ".zip";
                            String zipName11 = zipName + ".zip";
                            ZipUtil.zip(zipPath, zipPathAll, files1);
                            file = new File(zipPathAll);
                            long fileLength = file.length();
                            //1.获取要下载的文件的绝对路径
                            String realPath = zipPathAll;
                            //2.获取要下载的文件名
//					String fileName = realPath.substring(realPath.lastIndexOf(File.separator)+1);
                            response.reset();
                            response.setCharacterEncoding("UTF-8");
                            response.setContentType("application/json");
                            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
                            response.addHeader("Content-Disposition", "attachment;filename=" + new String(zipName11.getBytes(), "ISO8859-1"));
                            response.setHeader("Content-Length", String.valueOf(fileLength));

                            bis = new BufferedInputStream(new FileInputStream(realPath));
                            bbos = new ByteArrayOutputStream();
                            bos = new BufferedOutputStream(response.getOutputStream());
                            byte[] buff = new byte[2048];
                            int bytesRead;
                            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                                bbos.write(buff, 0, bytesRead);
                            }
                            byte[] outBody = bbos.toByteArray();
                            bos.write(outBody, 0, outBody.length);
                        }
                    }
                } else {
                    for (String pid : arrayForPids) {
                        filterForDoc = new Filter.Builder().eq("pid", pid).build();
                        List<Doc> docs = docService.select(filterForDoc);
                        if (docs != null && docs.size() > 0) {
                            String zipName = docs.get(0).getSysName();
                            sysOutName.add(docs.get(0).getSysName());
                            filterFoeDocFile = new Filter.Builder().eq("docPid", docs.get(0).getPid()).eq("delflag", "1").build();
                            List<DocFile> docFiles = docFileService.select(filterFoeDocFile);
                            if (docFiles != null && docFiles.size() > 0) {
                                for (DocFile docFile : docFiles) {
                                    files.add(new File(docFile.getFileUrl()));
                                }
                                File[] files1 = files.toArray(new File[0]);
                                String zipPathAll = zipPath + zipName + ".zip";
                                filesBig.add(new File(zipPathAll));
                                ZipUtil.zip(zipPath, zipPathAll, files1);
                                files.clear();
                            }
                        }
                    }
                    String zipBigNameAll = zipPath + sysOutName.get(0) + "等.zip";
                    String zipBigName = sysOutName.get(0) + "等.zip";
                    File[] filesBig1 = filesBig.toArray(new File[0]);
                    ZipUtil.zip(zipPath, zipBigNameAll, filesBig1);
                    file = new File(zipBigNameAll);
                    long fileLength = file.length();
                    //1.获取要下载的文件的绝对路径
                    String realPath = zipBigNameAll;
                    //2.获取要下载的文件名
//					String fileName = realPath.substring(realPath.lastIndexOf(File.separator)+1);
                    response.reset();
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
                    response.addHeader("Content-Disposition", "attachment;filename=" + new String(zipBigName.getBytes(), "ISO8859-1"));
                    response.setHeader("Content-Length", String.valueOf(fileLength));
                    bis = new BufferedInputStream(new FileInputStream(realPath));
                    bbos = new ByteArrayOutputStream();
                    bos = new BufferedOutputStream(response.getOutputStream());
                    byte[] buff = new byte[2048];
                    int bytesRead;
                    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                        bbos.write(buff, 0, bytesRead);
                    }

                    byte[] outBody = bbos.toByteArray();
                    bos.write(outBody, 0, outBody.length);
                }
            }
        } catch (Exception e) {
            logger.error("下载失败：" + e.getMessage());
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                logger.error("关闭IO流缓冲失败：" + e.getMessage());
            }
        }
    }
*/
    /*
     * 根据制度主键查询制度文件信息
     * */
    @RequestMapping(value = "/selectDocFileForPid/{pid}")
    @ResponseBody
    public Map<String, Object> selectDocFileForPid(@PathVariable String pid) {
        Map<String, Object> tips = new HashMap<>();
        List<DocFile> docFiles = new ArrayList<>();
        if (!StringUtils.isEmpty(pid)) {
            List<Filter> filterForDoc = new Filter.Builder().eq("pid", pid).eq("delflag", 1).build();
            List<Doc> docs = docService.select(filterForDoc);
            if (docs != null && docs.size() > 0) {
                Doc doc = docs.get(0);
                List<Filter> filterForDocFile = new Filter.Builder().eq("docPid", doc.getPid()).eq("delflag", 1).build();
                List<DocFile> docFile1 = docFileService.select(filterForDocFile);
                if (docFile1 != null && docFile1.size() > 0) {
                    for (DocFile docFile : docFile1) {
                        docFiles.add(docFile);
                    }
                }
            }
            tips.put(WebCode.SUCCESS_OPERATE, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
            tips.put("fileData", docFiles);
        } else {
            tips.put(WebCode.FAULURE_OPERATE, true);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return tips;
    }

    @RequestMapping(value = "/selectDocFile")
    @ResponseBody
    public Map<String, Object> selectDocFile(Page<DocFile> page, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = null;
        List<Order> orders = null;
        String docPid = request.getParameter("docPid");
        String docFilePids = request.getParameter("docFilePids");
        if (!StringUtils.isEmpty(docPid)) {
            List<Filter> filtersForDoc = new Filter.Builder().eq("delflag", 1).eq("pid", docPid).build();
            List<Doc> docs = docService.select(filtersForDoc);
            if (docs != null && docs.size() > 0) {
                Doc doc = docs.get(0);
                filters = new Filter.Builder().eq("delflag", 1).eq("docPid", doc.getPid()).build();
                orders = new Order.Builder().desc("createTime").build();
                List<DocFile> docFiles = docFileService.select(filters, orders);
                page.setTotal(docFiles.size());
                int pages = (docFiles.size() + page.getPageSize() - 1) / page.getPageSize();
                page.setPages(pages);
                int index = (page.getPageNumber() - 1) * page.getPageSize();
                List<DocFile> docFileList = new ArrayList<>();
                for (int i = index; i < index + page.getPageSize(); i++) {
                    if (i < docFiles.size()) {
                        docFileList.add(docFiles.get(i));
                    }
                }
                page.setResult(docFileList);
            }
        } else {
            if (!StringUtils.isEmpty(docFilePids)) {
                String[] arrayForFilePids = docFilePids.split(",");
                filters = new Filter.Builder().eq("delflag", 1).in("pid", arrayForFilePids).build();
                orders = new Order.Builder().desc("createTime").build();
                List<DocFile> docFiles = docFileService.select(filters, orders);
                page.setTotal(docFiles.size());
                int pages = (docFiles.size() + page.getPageSize() - 1) / page.getPageSize();
                page.setPages(pages);
                int index = (page.getPageNumber() - 1) * page.getPageSize();
                List<DocFile> docFileList = new ArrayList<>();
                for (int i = index; i < index + page.getPageSize(); i++) {
                    if (i < docFiles.size()) {
                        docFileList.add(docFiles.get(i));
                    }
                }
                page.setResult(docFileList);
            }
        }
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 移除文件
     */
    @RequestMapping("/removeDoc/{pids}")
    @ResponseBody
    public Map<String, Object> removeDoc(@PathVariable String pids) {
        Map<String, Object> tips = new HashMap<>();
        try {
            if (StringUtils.isEmpty(pids)) {
                tips.put(WebCode.FAILED, false);
                tips.put(WebCode.MSG, "未选择删除的文件");
                return tips;
            }
            String[] StrPidArr = pids.split(",");
            List<Integer> parmList = new ArrayList<>();
            for (String s : StrPidArr) {
                parmList.add(Integer.valueOf(s));
            }
            List<Filter> filters = new ArrayList<>();
            filters.add(new Filter("pid", Operator.IN, parmList));
            docFileService.deleteByFilter(filters);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (Exception e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
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
        List<DocFile> docFiles = docFileService.select(filters);
        String path = "";
        String fileName = "";
        if (docFiles != null && docFiles.size() > 0) {
            path = docFiles.get(0).getFileUrl();
            fileName = docFiles.get(0).getFileName();
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

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public File download(@RequestParam("pid") List<String> pids, HttpServletResponse response, HttpServletRequest request) {
        File file = null;
        List<File> files = new ArrayList<>();
        List<File> filesBig = new ArrayList<>();
        List<Filter> filterForDoc = null;
        List<Filter> filterFoeDocFile = null;
        List<String> sysOutName = new ArrayList<>();
        String zipPath = "/home/docFile/zip";
        BufferedInputStream bis = null;
        ByteArrayOutputStream bbos = null;
        BufferedOutputStream bos = null;
        try {
//			String[] arrayForPids = pids.split(",");
            if (pids != null && pids.size() > 0) {
                if (pids.size() == 1) {
                    filterForDoc = new Filter.Builder().eq("pid", new Integer(pids.get(0))).build();
                    List<Doc> docs = docService.select(filterForDoc);
                    if (docs != null && docs.size() > 0) {
                        String zipName = docs.get(0).getSysName();
                        filterFoeDocFile = new Filter.Builder().eq("docPid", docs.get(0).getPid()).eq("delflag", "1").build();
                        List<DocFile> docFiles = docFileService.select(filterFoeDocFile);
                        if (docFiles != null && docFiles.size() > 0) {
                            for (DocFile docFile : docFiles) {
                                files.add(new File(docFile.getFileUrl()));
                            }
                            File[] files1 = files.toArray(new File[0]);
                            String zipPathAll = zipPath + zipName + ".zip";
                            String zipName11 = zipName + ".zip";
                            ZipUtil.zip(zipPath, zipPathAll, files1);
                            file = new File(zipPathAll);
                            long fileLength = file.length();
                            //1.获取要下载的文件的绝对路径
                            String realPath = zipPathAll;
                            //2.获取要下载的文件名
//					String fileName = realPath.substring(realPath.lastIndexOf(File.separator)+1);
                            response.reset();
                            response.setCharacterEncoding("UTF-8");
                            response.setContentType("application/json");
                            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
                            response.addHeader("Content-Disposition", "attachment;filename=" + new String(zipName11.getBytes(), "ISO8859-1"));
                            response.setHeader("Content-Length", String.valueOf(fileLength));

                            bis = new BufferedInputStream(new FileInputStream(realPath));
                            bbos = new ByteArrayOutputStream();
                            bos = new BufferedOutputStream(response.getOutputStream());
                            byte[] buff = new byte[2048];
                            int bytesRead;
                            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                                bbos.write(buff, 0, bytesRead);
                            }
                            byte[] outBody = bbos.toByteArray();
                            bos.write(outBody, 0, outBody.length);
                        }
                    }
                } else {
                    for (String pid : pids) {
                        filterForDoc = new Filter.Builder().eq("pid", pid).build();
                        List<Doc> docs = docService.select(filterForDoc);
                        if (docs != null && docs.size() > 0) {
                            String zipName = docs.get(0).getSysName();
                            sysOutName.add(docs.get(0).getSysName());
                            filterFoeDocFile = new Filter.Builder().eq("docPid", docs.get(0).getPid()).eq("delflag", "1").build();
                            List<DocFile> docFiles = docFileService.select(filterFoeDocFile);
                            if (docFiles != null && docFiles.size() > 0) {
                                for (DocFile docFile : docFiles) {
                                    files.add(new File(docFile.getFileUrl()));
                                }
                                File[] files1 = files.toArray(new File[0]);
                                String zipPathAll = zipPath + zipName + ".zip";
                                filesBig.add(new File(zipPathAll));
                                ZipUtil.zip(zipPath, zipPathAll, files1);
                                files.clear();
                            }
                        }
                    }
                    String zipBigNameAll = zipPath + sysOutName.get(0) + "等.zip";
                    String zipBigName = sysOutName.get(0) + "等.zip";
                    File[] filesBig1 = filesBig.toArray(new File[0]);
                    ZipUtil.zip(zipPath, zipBigNameAll, filesBig1);
                    file = new File(zipBigNameAll);
                    long fileLength = file.length();
                    //1.获取要下载的文件的绝对路径
                    String realPath = zipBigNameAll;
                    //2.获取要下载的文件名
//					String fileName = realPath.substring(realPath.lastIndexOf(File.separator)+1);
                    response.reset();
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
                    response.addHeader("Content-Disposition", "attachment;filename=" + new String(zipBigName.getBytes(), "ISO8859-1"));
                    response.setHeader("Content-Length", String.valueOf(fileLength));
                    bis = new BufferedInputStream(new FileInputStream(realPath));
                    bbos = new ByteArrayOutputStream();
                    bos = new BufferedOutputStream(response.getOutputStream());
                    byte[] buff = new byte[2048];
                    int bytesRead;
                    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                        bbos.write(buff, 0, bytesRead);
                    }

                    byte[] outBody = bbos.toByteArray();
                    bos.write(outBody, 0, outBody.length);
                }
            }
        } catch (Exception e) {
            logger.error("下载失败：" + e.getMessage());
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                logger.error("关闭IO流缓冲失败：" + e.getMessage());
            }
        }
        return file;
    }


    /*
     * 根据制度主键查询制度文件信息
     * */
    @RequestMapping(value = "/· ")
    @ResponseBody
    public Map<String, Object> uploadDocFileAndEmail(@RequestParam("flag") int flag, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        Map<String, Object> tips = new HashMap<String, Object>();

        List<DocFile> docFileResult = new ArrayList<>();
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
                String pidForDocFiles = multipartRequest.getParameter("pidForDocFiles");
                String pidForDoc = multipartRequest.getParameter("pidForDoc");
                List<MultipartFile> fileList = multipartRequest.getFiles("file");
                String fileName = null;
                for (MultipartFile file : fileList) {
                    if (!file.isEmpty()) {
                        String fileNameAll = file.getOriginalFilename(); //文件全名
                         fileName = fileNameAll.substring(0, fileNameAll.lastIndexOf("."));  //文件名
                        String[] strArray = fileNameAll.split("\\.");
                        int suffixIndex = strArray.length - 1;
                        String fileType = strArray[suffixIndex] + ""; //文件类型
                        double size = file.getSize();
                        System.out.println("文件类型为 " + fileType);
                        double fileSizeMB = new BigDecimal(size / 1024 / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
                        if (size>50.00)
                        {
                            tips.put(WebCode.SUCCESS, false);
                            tips.put(WebCode.MSG, "上传文件过大，请控制在50M内.");
                        }
                        System.out.println(fileNameAll + "-->" + fileSizeMB + "MB");
                        String path = systematicPath;
//                        String path = "D://home//rptrs//upload";
                        File dest = new File(path + "/" + fileNameAll);
                        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                            dest.getParentFile().mkdir();
                        }
                        file.transferTo(dest); //保存文件
                        DocFile docFile = new DocFile();
                        int pid = (int) new IdWorker().nextId();
                        docFile.setPid(pid); //主键
                        docFile.setFileName(fileName); //文件名称
//					    systemfile.setCreateByName(name); //上传人
                        docFile.setFileType(fileType); //文件类型
                        docFile.setFileUrl(path + "/" + fileNameAll); //文件路径
//						docFile.setApproveStatus(0); //审批状态
                        docFile.setDelflag("1");//删除状态 0代表是（查询接口不传删除的数据) 1代表否
                        docFile.setFileSize(fileSizeMB);//文件大小
//			            systemfile.setEffectiveTime(effectiveTime);//生效时间
                        docFile.setCreateTime(new Date()); //创建时间
                        docFile.setCreateTimeString(new Date().toString()); //创建时间
                        docFile.setModifiedTime(new Date()); //修改时间
                        docFile.setModifiedTimeString(new Date().toString()); //修改时间
                        docFile.setUploadType(flag);
//                        docFile.setUploadType(type);
                        docFile.setUploadDeptName(UserProfileUtils.getUserProfile().getDeptName());
                        docFile.setUploadOrgName(UserProfileUtils.getUserProfile().getUnitName());
                        if (!StringUtils.isEmpty(pidForDoc)) {
                            docFile.setDocPid(new Integer(pidForDoc));//制度的pid
                        }
                        docFileService.insert(docFile);

                        List<Filter> filterForDocFile = new Filter.Builder().eq("pid", pid).eq("delflag", 1).build();
                        List<DocFile> docFiles = docFileService.select(filterForDocFile);
                        if (docFiles != null && docFiles.size() > 0) {
                            DocFile docFile1 = docFiles.get(0);
                            docFileResult.add(docFile1);
                        }
                        if (!StringUtils.isEmpty(pidForDocFiles)) {
                            String[] arrayForFilePids = pidForDocFiles.split(",");
                            for (String arrayForFilePid : arrayForFilePids) {
                                List<Filter> filterForDocFile2 = new Filter.Builder().eq("pid", arrayForFilePid).eq("delflag", 1).build();
                                List<DocFile> docFileList = docFileService.select(filterForDocFile2);
                                if (docFileList != null && docFileList.size() > 0) {
                                    docFileResult.add(docFileList.get(0));
                                }
                            }
                        }
                        tips.put(WebCode.SUCCESS, true);
                        tips.put(WebCode.MSG, "上传成功.");
                        tips.put("fileData", docFileResult);


                    } else {
                        tips.put(WebCode.SUCCESS, false);
                        tips.put(WebCode.MSG, "上传失败,未收到文件.");
                    }
                }
                List list = emailSend.selectPerpleHg();
                emailSend.emailSends(list,"6",fileName);
            }
        } catch (Exception e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传失败，系统出现未知异常.");
            logger.error("上传失败：" + e.getMessage());
        }
        return tips;
    }


    /*
     * 根据制度主键查询制度文件信息
     * */
    @RequestMapping(value = "/uploadDocFileAndEmails")
    @ResponseBody
    public Map<String, Object> uploadDocFileAndEmails(@RequestParam("flag") int flag, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        Map<String, Object> tips = new HashMap<String, Object>();

        List<DocFile> docFileResult = new ArrayList<>();
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
                String pidForDocFiles = multipartRequest.getParameter("pidForDocFiles");
                String pidForDoc = multipartRequest.getParameter("pidForDoc");
                List<MultipartFile> fileList = multipartRequest.getFiles("file");
                String fileName = null;
                for (MultipartFile file : fileList) {
                    if (!file.isEmpty()) {
                        String fileNameAll = file.getOriginalFilename(); //文件全名
                        fileName = fileNameAll.substring(0, fileNameAll.lastIndexOf("."));  //文件名
                        String[] strArray = fileNameAll.split("\\.");
                        int suffixIndex = strArray.length - 1;
                        String fileType = strArray[suffixIndex] + ""; //文件类型
                        double size = file.getSize();
                        System.out.println("文件类型为 " + fileType);
                        double fileSizeMB = new BigDecimal(size / 1024 / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
                        if (size>50.00)
                        {
                            tips.put(WebCode.SUCCESS, false);
                            tips.put(WebCode.MSG, "上传文件过大，请控制在50M内.");
                        }
                        System.out.println(fileNameAll + "-->" + fileSizeMB + "MB");
                        String path = systematicPath;
//                        String path = "D://home//rptrs//upload";
                        File dest = new File(path + "/" + fileNameAll);
                        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                            dest.getParentFile().mkdir();
                        }
                        file.transferTo(dest); //保存文件
                        DocFile docFile = new DocFile();
                        int pid = (int) new IdWorker().nextId();
                        docFile.setPid(pid); //主键
                        docFile.setFileName(fileName); //文件名称
//					    systemfile.setCreateByName(name); //上传人
                        docFile.setFileType(fileType); //文件类型
                        docFile.setFileUrl(path + "/" + fileNameAll); //文件路径
//						docFile.setApproveStatus(0); //审批状态
                        docFile.setDelflag("1");//删除状态 0代表是（查询接口不传删除的数据) 1代表否
                        docFile.setFileSize(fileSizeMB);//文件大小
//			            systemfile.setEffectiveTime(effectiveTime);//生效时间
                        docFile.setCreateTime(new Date()); //创建时间
                        docFile.setCreateTimeString(new Date().toString()); //创建时间
                        docFile.setModifiedTime(new Date()); //修改时间
                        docFile.setModifiedTimeString(new Date().toString()); //修改时间
                        docFile.setUploadType(flag);
//                        docFile.setUploadType(type);
                        docFile.setUploadDeptName(UserProfileUtils.getUserProfile().getDeptName());
                        docFile.setUploadOrgName(UserProfileUtils.getUserProfile().getUnitName());
                        if (!StringUtils.isEmpty(pidForDoc)) {
                            docFile.setDocPid(new Integer(pidForDoc));//制度的pid
                        }
                        docFileService.insert(docFile);

                        List<Filter> filterForDocFile = new Filter.Builder().eq("pid", pid).eq("delflag", 1).build();
                        List<DocFile> docFiles = docFileService.select(filterForDocFile);
                        if (docFiles != null && docFiles.size() > 0) {
                            DocFile docFile1 = docFiles.get(0);
                            docFileResult.add(docFile1);
                        }
                        if (!StringUtils.isEmpty(pidForDocFiles)) {
                            String[] arrayForFilePids = pidForDocFiles.split(",");
                            for (String arrayForFilePid : arrayForFilePids) {
                                List<Filter> filterForDocFile2 = new Filter.Builder().eq("pid", arrayForFilePid).eq("delflag", 1).build();
                                List<DocFile> docFileList = docFileService.select(filterForDocFile2);
                                if (docFileList != null && docFileList.size() > 0) {
                                    docFileResult.add(docFileList.get(0));
                                }
                            }
                        }
                        tips.put(WebCode.SUCCESS, true);
                        tips.put(WebCode.MSG, "上传成功.");
                        tips.put("fileData", docFileResult);


                    } else {
                        tips.put(WebCode.SUCCESS, false);
                        tips.put(WebCode.MSG, "上传失败,未收到文件.");
                    }
                }
                List list = emailSend.selectPerpleHg();
                emailSend.emailSends(list,"7",fileName);
            }
        } catch (Exception e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传失败，系统出现未知异常.");
            logger.error("上传失败：" + e.getMessage());
        }
        return tips;
    }

}