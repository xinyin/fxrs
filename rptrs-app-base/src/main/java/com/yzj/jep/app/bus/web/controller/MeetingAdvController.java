package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.bus.model.KnowledgeBase;
import com.yzj.jep.app.bus.service.KnowledgeBaseService;
import com.yzj.jep.app.sub.util.IdWorker;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.util.ZipUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//==========================会议通知==============================
@Controller
@RequestMapping("/bus/meetingAdv")
public class MeetingAdvController extends CrudController<KnowledgeBase> {
    private static Logger logger	= LoggerFactory.getLogger(MeetingAdvController.class);


    @Autowired
    private KnowledgeBaseService knowledgeBaseService;
    @Autowired
    private Configs configs;

    /**
     * 获取列表页面
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "/bus/meetingAdv/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "bus/meetingAdvList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<KnowledgeBase> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("submoduleType", Operator.EQ, "meetingAdvice"));
        knowledgeBaseService.query(page, filters, orders);
    }

    /**
     * 删除
     * @param pid
     * @return
     */
    @RequestMapping(value = "/remove/{pid}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer pid){

        Map<String, Object> tips = new HashMap<String, Object>();

        try{
            knowledgeBaseService.deleteByPrimaryKey(pid);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        }
        catch (ServiceException e)
        {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }
    //上傳文件
    @RequestMapping(value = "/upload/file", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveFile(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<KnowledgeBase> knowledgeBaseResult = new ArrayList<>();
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
                String pidForDocFiles = multipartRequest.getParameter("pidForDocFiles");//拿到文件数组
                String pidForDoc = multipartRequest.getParameter("pidForDoc");//文件pid
                List<MultipartFile> fileList = multipartRequest.getFiles("file");//文件链表
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
                        String path = configs.getKnowledgebase();//文件路劲
                        File dest = new File(path + "/" + fileNameAll);
                        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                            dest.getParentFile().mkdir();
                        }
                        file.transferTo(dest); //保存文件
                        KnowledgeBase knowledgeBaseFile = new KnowledgeBase();
                        //1.生成文件pid
                        int pid = (int) new IdWorker().nextId();
                        //2.设置文件pid
                        knowledgeBaseFile.setPid(pid);
                        //3.设置文件名称
                        knowledgeBaseFile.setFileName(fileName);
                        //4.设置文件类型
                        knowledgeBaseFile.setFileType(fileType);
                        //5.设置文件路径
                        knowledgeBaseFile.setFilePath(path + "/" + fileNameAll);

                        //6.设置文件大小
                        knowledgeBaseFile.setFileSize(String.valueOf(fileSizeMB)+"M");
                        //7.设置上传部门
                        knowledgeBaseFile.setDepartment(UserProfileUtils.getUserProfile().getDeptName());
                        //8.上传时间数据库使用currentTime
                        //9.设置子模块类型
                        knowledgeBaseFile.setSubmoduleType("meetingAdvice");//========================================子模块类型

                        knowledgeBaseService.insert(knowledgeBaseFile);

                        List<Filter> filterForDocFile = new Filter.Builder().eq("pid", pid).build();
                        List<KnowledgeBase> knowledgeBases = knowledgeBaseService.select(filterForDocFile);
                        if (knowledgeBases != null && knowledgeBases.size() > 0) {
                            KnowledgeBase knowledgeBase=knowledgeBases.get(0);
                            knowledgeBaseResult.add(knowledgeBase);
                        }
                        if (!StringUtils.isEmpty(pidForDocFiles)) {
                            String[] arrayForFilePids = pidForDocFiles.split(",");
                            for (String arrayForFilePid : arrayForFilePids) {
                                List<Filter> filterForDocFile2 = new Filter.Builder().eq("pid", arrayForFilePid).build();
                                List<KnowledgeBase> knowledgeBaseList = knowledgeBaseService.select(filterForDocFile2);
                                if (knowledgeBaseList != null && knowledgeBaseList.size() > 0) {
                                    knowledgeBaseResult.add(knowledgeBaseList.get(0));
                                }
                            }
                        }
                        tips.put(WebCode.SUCCESS, true);
                        tips.put(WebCode.MSG, "上传成功.");
                        tips.put("fileData", knowledgeBaseResult);
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
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public File download(@RequestParam("pid") String pid, HttpServletResponse response, HttpServletRequest request) {
        System.out.println("下载pid"+pid);
        File file = null;
        List<File> files = new ArrayList<>();
        List<File> filesBig = new ArrayList<>();
        List<Filter> filterForDoc = null;
        List<Filter> filterFoeDocFile = null;
        List<String> sysOutName = new ArrayList<>();
        String zipPath = configs.getKnowledgebaseDownload();
        BufferedInputStream bis = null;
        ByteArrayOutputStream bbos = null;
        BufferedOutputStream bos = null;
        List<String> pids = new ArrayList<>();
        pids.add(pid);
        try {
            if (pids != null && pids.size() > 0) {
                if (pids.size() == 1) {
                    filterForDoc = new Filter.Builder().eq("pid", new Integer(pids.get(0))).build();
                    List<KnowledgeBase> docs = knowledgeBaseService.select(filterForDoc);
                    System.out.println("下载文件对象"+docs);
                    if (docs != null && docs.size() > 0) {
                        String zipName = docs.get(0).getFileName();
                        filterFoeDocFile = new Filter.Builder().eq("docPid", docs.get(0).getPid()).build();
//						List<KnowledgeBase> docFiles = knowledgeBaseService.select(filterFoeDocFile);
                        if (docs != null && docs.size() > 0) {
                            for (KnowledgeBase docFile : docs) {
                                files.add(new File(docFile.getFilePath()));
                            }
                            File[] files1 =files.toArray(new File[0]);
                            String zipPathAll = zipPath + "/"+ zipName + ".zip";
                            String zipName11 = zipName + ".zip";
                            ZipUtil.zip(zipPath, zipName, files1);
                            file = new File(zipPathAll);
                            long fileLength = file.length();
                            //1.获取要下载的文件的绝对路径
                            String realPath = zipPathAll;
                            //2.获取要下载的文件名
//					String fileName = realPath.substring(realPath.lastIndexOf(File.separator)+1);
                            response.reset();
                            response.setCharacterEncoding("UTF-8");
                            response.setContentType("application/json");
                            zipName11= URLEncoder.encode(zipName11,"utf-8");
                            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
                            response.addHeader("Content-Disposition", "attachment;filename=" + new String(zipName11.getBytes()));
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
                    for (String pid1 : pids) {
                        filterForDoc = new Filter.Builder().eq("pid", pid).build();
                        List<KnowledgeBase> docs = knowledgeBaseService.select(filterForDoc);
                        if (docs != null && docs.size() > 0) {
                            String zipName = docs.get(0).getFileName();
                            sysOutName.add(docs.get(0).getFileName());
                            filterFoeDocFile = new Filter.Builder().eq("docPid", docs.get(0).getPid()).build();
                            List<KnowledgeBase> docFiles = knowledgeBaseService.select(filterFoeDocFile);
                            if (docFiles != null && docFiles.size() > 0) {
                                for (KnowledgeBase docFile : docFiles) {
                                    files.add(new File(docFile.getFilePath()));
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
}