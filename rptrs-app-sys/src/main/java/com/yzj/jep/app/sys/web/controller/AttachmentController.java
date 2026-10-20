package com.yzj.jep.app.sys.web.controller;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.sys.model.Attachment;
import com.yzj.jep.sys.model.AttachmentType;
import com.yzj.jep.sys.model.Role;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.AttachmentService;
import com.yzj.jep.sys.service.AttachmentTypeService;
import com.yzj.jep.sys.service.PKeyService;
import com.yzj.jep.sys.util.AttachmentUtil;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//import com.yzj.jep.util.ResourceUtil;

/**
 * 处理附件上传
 *
 * @author Michael
 * @time 2017年6月12日 上午10:00:34
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sys/attach")
public class AttachmentController extends CrudController<Role> {

    Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    private static final String ENTITY_ID_NOT_ASSIGNED = "ENTITY_ID_NOT_ASSIGNED";

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentTypeService attachmentTypeService;

    @Autowired
    private PKeyService pKeyService;

    @Autowired
    private AppConfigService appConfigService;

    /**
     * 初始化上传参数
     * 插件初始化时，调用该方法获取如下参数：
     * 1. uploadSession	上传会话
     * 2. maxFiles		上传文件个数限制
     * 3. maxFilesize	上传文件大小限制
     * 4. includes		允许上传的文件类型, 逗号分隔
     * 5. excludes		禁止上传的文件类型， 逗号分隔
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/params", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> params(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();

        /*生成uploadSession*/
        String uploadSession = pKeyService.getUUIDKey();
        /*单次允许上传的文件个数*/
        int maxFiles = AttachmentUtil.getMaxFiles();
        /*获得上传文件大小限制(MB)*/
        int maxFilesize = AttachmentUtil.getMaxSizeInMB();
        /*获得允许上传的文件类型*/
        String includes = appConfigService.getConfigValue("UPLOADINCTYPES");
        /*获得禁止上传的文件类型*/
        String excludes = appConfigService.getConfigValue("UPLOADEXCTYPES");
        tips.put("uploadSession", uploadSession);
        tips.put("maxFiles", maxFiles);
        tips.put("maxFileSize", maxFilesize);
        tips.put("includes", includes);
        tips.put("excludes", excludes);
        return tips;
    }

    /**
     * 删除单个附件
     *
     * @param entityId
     * @param uploadSession
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String id, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            attachmentService.deleteById(id);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "删除成功.");
        } catch (Exception ex) {
            ex.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "删除失败.");
        }
        return tips;
    }

    /**
     * 查询已有的附件
     *
     * @param entityId
     * @param uploadSession
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> query(String entityId, String uploadSession, String entityType, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<Attachment> list = null;
        try {
            list = attachmentService.select(entityId, uploadSession, entityType);
        } catch (Exception ex) {
            list = new ArrayList<>();
        }
        tips.put(WebCode.SUCCESS, true);
        tips.put("files", list);
        return tips;
    }


    /**
     * 上传文件
     *
     * @param request
     * @param response
     * @return 操作提示信息
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {

            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            if (commonsMultipartResolver.isMultipart(request)) {

                MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);

                String attachTypeCode = multipartRequest.getParameter("attachTypeCode");
                String entityId = multipartRequest.getParameter("entityId");
                String uploadSession = multipartRequest.getParameter("uploadSession");

                if (StringUtils.isEmpty(entityId)) {
                    entityId = ENTITY_ID_NOT_ASSIGNED;
                }

                Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
                if (StringUtils.isNotBlank(attachTypeCode)) {

                    int fileSizeInMb = AttachmentUtil.getMaxSizeInMB();
                    long fileSize = AttachmentUtil.getMaxSizeInByte();

                    boolean proceed = true;
                    for (Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                        MultipartFile file = entry.getValue();

                        /*判断文件大小及类型*/
                        if (!AttachmentUtil.accept(file)) {
                            tips.put(WebCode.SUCCESS, false);
                            tips.put(WebCode.MSG, "不允许上传的文件类型.");
                            proceed = false;
                            break;
                        }

                        if (fileSize != -1 && file.getSize() > fileSize) {
                            tips.put(WebCode.SUCCESS, false);
                            tips.put(WebCode.MSG, "文件大小超过限制[" + fileSizeInMb + "(MB)].");
                            proceed = false;
                            break;
                        }

                    }
                    if (proceed) {
                        UserProfile staff = UserProfileUtils.getUserProfile();
                        List<Attachment> list = attachmentService.upload(staff.getStaffCode(), entityId, attachTypeCode, uploadSession, fileMap);
                        tips.put(WebCode.SUCCESS, true);
                        tips.put(WebCode.MSG, "上传成功。");
                        tips.put("files", list);
                    }
                } else {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "上传失败，未定义附件类型。");
                }
            }
        } catch (Exception e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传失败，系统出现未知异常.");
            logger.error("上传失败：" + e.getMessage());
        }
        return tips;
    }

    /**
     * 下载文件
     *
     * @param ids
     * @param request
     * @param response
     */
    @RequestMapping(value = "/download/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setContentType("application/x-msdownload;");

        BufferedInputStream bis = null;
        ByteArrayOutputStream bbos = null;
        BufferedOutputStream bos = null;
        try {
            String downLoadPath = AttachmentUtil.getUploadPath();
            Attachment attachment = attachmentService.get(id);
            AttachmentType type = attachmentTypeService.get(attachment.getAttachTypeCode());
            if (null != attachment) {
                String saveName = attachment.getSaveFileName() + "." + attachment.getFileFormat();
                String srcName = attachment.getSrcFileName() + "." + attachment.getFileFormat();

                String savePath = type.getAttachPath();
                if (StringUtils.isNotBlank(savePath)) {
                    downLoadPath += File.separator + savePath + File.separator;
                }

                if (StringUtils.isNotBlank(saveName)) {
                    downLoadPath += saveName;
                }
                long fileLength = new File(downLoadPath).length();

                if (srcName != null) {
                    /*
                     * FireFox浏览器的文件编码方式不同，需单独处理
                     * FireFox的userAgent：Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0
                     */
                    String userAgent = request.getHeader("User-Agent");
                    String conFileName = URLEncoder.encode(srcName, "UTF-8");
                    if (!StringUtils.isEmpty(userAgent) && userAgent.indexOf("Firefox") >= 0) {
                        conFileName = new String(srcName.getBytes("GBK"), "ISO-8859-1");
                    }
                    response.setHeader("Content-disposition", "attachment; filename=" + conFileName);
                }
                response.setHeader("Content-Length", String.valueOf(fileLength));
                bis = new BufferedInputStream(new FileInputStream(downLoadPath));
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
    }


    /**
     * 跳转到附件浏览页面
     *
     * @param entityId
     * @param event
     * @param req
     * @return
     */
    @RequestMapping(value = "/image/{id}")
    public void image(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        Attachment attachment = attachmentService.get(id);

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            String downLoadPath = AttachmentUtil.getUploadPath();
            if (attachment != null) {
                AttachmentType type = attachmentTypeService.get(attachment.getAttachTypeCode());
                response.setContentType("image/" + attachment.getFileFormat());

                String saveName = attachment.getSaveFileName() + "." + attachment.getFileFormat();
                String savePath = type.getAttachPath();
                if (StringUtils.isNotBlank(savePath)) {
                    downLoadPath += File.separator + savePath + File.separator;
                }
                if (StringUtils.isNotBlank(saveName)) {
                    downLoadPath += saveName;
                }

                File file = new File(downLoadPath);
                InputStream in = null;
                if (!file.exists()) {
                    in = null;//ResourceUtil.getResourceAsStream("com/yzj/jep/console/sys/web/controller/timg.jpg");
                } else {
                    in = new FileInputStream(file);
                }

                bis = new BufferedInputStream(in);
                bos = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[2048];
                int bytesRead;
                while ((bytesRead = bis.read(buff, 0, buff.length)) != -1) {
                    bos.write(buff, 0, bytesRead);
                }
                bos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    }


    /**
     * 查看文本内容
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/text/{id}")
    @ResponseBody
    public Map<String, Object> text(@PathVariable String id, Model model) {
        Map<String, Object> map = new HashMap<>();
        Attachment attachment = attachmentService.get(id);
        StringBuffer buf = new StringBuffer();
        BufferedReader reader = null;

        try {
            String downLoadPath = AttachmentUtil.getUploadPath();
            if (attachment != null) {
                AttachmentType type = attachmentTypeService.get(attachment.getAttachTypeCode());

                String saveName = attachment.getSaveFileName() + "." + attachment.getFileFormat();
                String savePath = type.getAttachPath();
                if (StringUtils.isNotBlank(savePath)) {
                    downLoadPath += File.separator + savePath + File.separator;
                }
                if (StringUtils.isNotBlank(saveName)) {
                    downLoadPath += saveName;
                }

                reader = new BufferedReader(new InputStreamReader(new FileInputStream(downLoadPath), "UTF-8"));
                String tmp = null;
                while ((tmp = reader.readLine()) != null) {
                    buf.append(tmp).append("\r\n");
                }

                map.put(WebCode.SUCCESS, true);
                map.put("content", buf.toString());
            }
        } catch (Exception e) {

            logger.info("读取文件内容失败。");
            map.put(WebCode.SUCCESS, false);

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                logger.error("关闭IO流缓冲失败：" + e.getMessage());
            }
        }

        return map;
    }

    /**
     * 查看文本内容
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/associate")
    @ResponseBody
    public Map<String, Object> associate(String entityId, String uploadSession, Model model) {

        Map<String, Object> map = new HashMap<>();

        try {

            int count = attachmentService.associate(entityId, uploadSession);
            map.put(WebCode.SUCCESS, true);
            map.put(WebCode.MSG, "关联成功.");
            map.put("count", count);

        } catch (Exception e) {
            e.printStackTrace();
            map.put(WebCode.SUCCESS, false);
            map.put(WebCode.MSG, "关联失败.");
        }

        return map;

    }

}
