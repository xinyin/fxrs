/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月22日 11:31:54
 *
 */

package com.yzj.jep.app.ins.web.controller;

import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.contract.util.ContractUtil;
import com.yzj.jep.app.ins.model.BusinessTypeVo;
import com.yzj.jep.app.ins.model.TraInsenclosure;
import com.yzj.jep.app.ins.service.TraInsenclosureService;
import com.yzj.jep.app.sub.util.DownFileUtils;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.UUIDUtils;
import com.yzj.jep.app.util.ZipUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/ins/traInsenclosure")
public class TraInsenclosureController extends CrudController<TraInsenclosure> {
    private static Logger logger = LoggerFactory.getLogger(TraInsenclosureController.class);

    @Autowired
    private TraInsenclosureService traInsenclosureService;

    @Autowired
    private Configs configs;

    @Autowired
    private DictItemService dictItemService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "ins/traInsenclosureList";
    }

    @RequestMapping(value = "queryList")
    @ResponseBody
    public Map<String, Object> query(Page<TraInsenclosure> page, Model model, HttpServletRequest request, String transNum) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        filters.add(new Filter("transNum", Operator.EQ, transNum));
        prepareQuery(page, filters, orders, request);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraInsenclosure> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        traInsenclosureService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "ins/traInsenclosureEdit";
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

        TraInsenclosure traInsenclosure = traInsenclosureService.get(pid);
        model.addAttribute("traInsenclosure", traInsenclosure);
        model.addAttribute("opt", "edit");

        return "ins/traInsenclosureEdit";
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

        TraInsenclosure traInsenclosure = traInsenclosureService.get(pid);
        model.addAttribute("traInsenclosure", traInsenclosure);

        return "ins/traInsenclosureView";
    }

    /**
     * 保存
     *
     * @param traInsenclosure
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(TraInsenclosure traInsenclosure) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = traInsenclosureService.exists(traInsenclosure.getPid());
        try {
            if (isExist) {

                traInsenclosureService.update(traInsenclosure);
            } else {
                traInsenclosureService.insert(traInsenclosure);
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
            traInsenclosureService.deleteByPrimaryKey(pid);
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

        boolean exist = traInsenclosureService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 文件上传
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(String businessType, HttpServletRequest request, HttpServletResponse response, Model model) {

        Map<String, Object> tips = new HashMap<String, Object>();
        TraInsenclosure enclosure = null;
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartRequest.getFile("file");
            if (!file.isEmpty()) {
                String fileNameAll = file.getOriginalFilename(); //文件全名
                String fileNameOne = file.getOriginalFilename(); //文件全名
                fileNameOne = UUID.randomUUID() + ".pdf";
                String fileName = fileNameAll.substring(0, fileNameAll.lastIndexOf("."));  //文件名
                String[] strArray = fileNameAll.split("\\.");
                int suffixIndex = strArray.length - 1;
                String fileType = strArray[suffixIndex] + ""; //文件类型
                double size = file.getSize();
                System.out.println("文件类型为 " + fileType);
                double fileSizeMB = new BigDecimal(size / 1024 / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB

                System.out.println(fileNameAll + "-->" + fileSizeMB + "MB");

                String path = configs.getFilePath();
//				String path = "/home/rptrs/upload";
                File dest = new File(path + "/" + fileNameOne);
                if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                file.transferTo(dest); //保存文件
                enclosure = new TraInsenclosure();
                //存入数据
                //确保唯一
                enclosure.setEnclosurePid(BatchNumberUtil.makeBatchNumber());
                enclosure.setFileType(fileType);
                enclosure.setPath(path + "/" + fileNameOne);
                enclosure.setRelatedName(fileName);
                enclosure.setDelflag("1");
                enclosure.setFileSize(String.valueOf(fileSizeMB));
                enclosure.setBusinessType(businessType);
                enclosure.setCreateTime(new Date());
//				enclosure.setModifiedTime(new Date());
                enclosure.setUploadTime(DateUtils.formatDate(new Date()));
                UserProfile userProfile = UserProfileUtils.getUserProfile();
                enclosure.setUploadDept(userProfile.getDeptName());
                enclosure.setUploadOrg(userProfile.getUnitCode());
                traInsenclosureService.insert(enclosure);
                enclosure.setCreateTimeString(new Date().toString());
//				enclosure.setModifiedTimeString(new Date().toString());
                Integer pid = traInsenclosureService.select(enclosure).get(0).getPid();
                enclosure.setPid(pid);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, "上传成功");
                tips.put("traEnclosure", enclosure);
            }
        } catch (Exception e) {
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传失败");
        }
        return tips;
    }

    /**
     * 文件下载
     *
     * @param pids
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downLoadFile/{pids}")
    @ResponseBody
    public void downLoadFile(@PathVariable String pids, HttpServletRequest request,
                                            HttpServletResponse response) {
        String uuid = UUIDUtils.getUUID();//压缩包名称
        String tempPath = configs.getTemp();//压缩包保存路径
        String fileName = uuid + ".zip";//文件名称
        String filePath = tempPath + File.separator + uuid + ".zip";//压缩文件路径
        try {
            if (org.apache.commons.lang.StringUtils.isNotBlank(pids)) {
                List<String> parames = Arrays.asList(pids.split(","));
                List<Filter> filters = new Filter.Builder().in("pid", parames).build();
                List<TraInsenclosure> traEnclosures = traInsenclosureService.select(filters);
                if (traEnclosures != null) {
                    File[] files = new File[traEnclosures.size()];
                    for (int i = 0; i < traEnclosures.size(); i++) {
                        TraInsenclosure traInsenclosure = traEnclosures.get(i);
                        String path = traInsenclosure.getPath();
                        files[i] = new File(path);
                    }
                    boolean status = ZipUtil.zip(tempPath, uuid, files);
                    if (status) {
                        DownFileUtils.downServerFile(filePath, fileName, request, response);
                    }
                }
            }
        } catch (ServiceException e) {
            logger.error("压缩文件下载异常:{}", e);
        } finally {
            File file = new File(filePath);
            ContractUtil.delete(file);
        }
    }

    /**
     * 关联交易类-多文件上传方法
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/uploadFiles")
    @ResponseBody
    public Map<String, Object> uploadFiles(String businessType, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, "上传成功");
        TraInsenclosure enclosure = null;
        List<TraInsenclosure> traInsenclosureList = new ArrayList<>();
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
            //创建文件存储路径
            String targetFolder = configs.getTemp() + File.separator + UUIDUtils.getUUID();
            File directory = new File(targetFolder);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String transNum =request.getParameter("transNum");
            //循环保存文件
            List<MultipartFile> fileList = multipartRequest.getFiles("file");

            Iterator<MultipartFile> iterator = fileList.iterator();
            while (iterator.hasNext()) {
                MultipartFile file = iterator.next();
                if (!file.isEmpty()) {
                    String fileFullName = file.getOriginalFilename(); //文件全名
                    String fileName = fileFullName.substring(0, fileFullName.lastIndexOf("."));  //文件名
                    String fileType = fileFullName.substring(fileFullName.lastIndexOf(".") + 1);
                    double size = file.getSize();
                    double fileSizeMB = new BigDecimal(size / 1024 / 1024).setScale(2,
                            BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
                    //保存文件
                    String targetPath = targetFolder + File.separator + fileFullName;
                    File targetFile = new File(targetPath);
                    if (!targetFile.exists()) {
                        targetFile.createNewFile();
                    }
                    file.transferTo(targetFile);
                    //信息入库
                    enclosure = new TraInsenclosure();
                    //存入数据
                    Date currentDate = new Date();
                    //存入交易流水号
                    enclosure.setTransNum(transNum);
                    String currentDateString = currentDate.toString();
                    enclosure.setEnclosurePid(BatchNumberUtil.makeBatchNumber());//确保唯一
                    enclosure.setFileType(fileType);
                    enclosure.setPath(targetPath);
                    enclosure.setRelatedName(fileName);
                    enclosure.setDelflag("1");
                    enclosure.setFileSize(String.valueOf(fileSizeMB));
                    enclosure.setBusinessType(businessType);
                    enclosure.setCreateTime(currentDate);
                    enclosure.setUploadTime(DateUtils.formatDate(new Date()));
                    UserProfile userProfile = UserProfileUtils.getUserProfile();
                    if (userProfile != null) {
                        enclosure.setUploadDept(userProfile.getDeptName());
                        enclosure.setUploadOrg(userProfile.getUnitCode());
                    }
                    traInsenclosureService.insertSelective(enclosure);
                    enclosure.setCreateTimeString(currentDateString);
                    List<TraInsenclosure> result = traInsenclosureService.select(enclosure);
                    if (result != null) {
                        Integer pid = result.get(0).getPid();
                        enclosure.setPid(pid);
                    }
                    traInsenclosureList.add(enclosure);
                }else{
                        tips.put(WebCode.SUCCESS, false);
                        tips.put(WebCode.MSG, "文件不存在，上传失败");
                }
            }
            tips.put("traEnclosure", traInsenclosureList);
        } catch (Exception e) {
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传失败");
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
        List<TraInsenclosure> traEnclosures = traInsenclosureService.select(filters);
        String path = "";
        String fileName = "";
        if (traEnclosures != null && traEnclosures.size() > 0) {
            path = traEnclosures.get(0).getPath();
            fileName = traEnclosures.get(0).getRelatedName();
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
                TraInsenclosure traEnclosure = new TraInsenclosure();
                traEnclosure.setPid(Integer.valueOf(str));
                traEnclosure.setDelflag("0");
                traInsenclosureService.updateSelective(traEnclosure);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (Exception e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return tips;
    }


    @RequestMapping(value = "queryListDict")
    @ResponseBody
    public Map<String, Object> queryList(Page<DictItem> page, Model model, HttpServletRequest request, String transNum, String type,String isConsider) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        if(StrUtil.isEmpty(isConsider)){
            filters.add(new Filter("dictCode", Operator.EQ, "BUS-AttachmentBusType1"));
        }else if("3".equals(isConsider)){
            filters.add(new Filter("dictCode", Operator.EQ, "BUS-AttachmentBusType"));
        }
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("itemValue", OrderType.ASC));
        dictItemService.query(page, filters, orders);

        //判断属于该附件类型的文件是否上传，键是字典编码，值是0或1，0是未上传，1是已上传
        //Map<String,String> isUploadMap = new LinkedHashMap<>();
        List<BusinessTypeVo> rows = new ArrayList<>();
        //获取附件类型字典
        List<DictItem> dictItems = page.getResult();
        for (DictItem dictItem : dictItems) {
            BusinessTypeVo businessTypeVo = new BusinessTypeVo();
            //业务类型在字典中的编码
            String typeCode = dictItem.getItemValue();

            //判断该附件类型是否上传文件
            List<Filter> fileFilter = new Filter.Builder().eq("delflag", 1).build();
            fileFilter.add(new Filter("transNum", Operator.EQ, transNum));
            fileFilter.add(new Filter("uploadFileType", typeCode));
            //查询该文件类型的文件个数
            List<TraInsenclosure> select = traInsenclosureService.select(fileFilter);
            if (select.size() != 0) {
                businessTypeVo.setIsUpload("已上传");
                businessTypeVo.setPid(String.valueOf(select.get(0).getPid()));
                businessTypeVo.setBelongOrg(select.get(0).getUploadDept());
                businessTypeVo.setUploadTime(select.get(0).getUploadTime());
            } else {
                businessTypeVo.setIsUpload("未上传");
            }

            //附件类型
            businessTypeVo.setType(dictItem.getItemLabel());
            traInsenclosureService.setTypeValue(typeCode,type,businessTypeVo);
//            if("Y01".equals(typeCode) ){
//                if("service".equals(type)){
//                    businessTypeVo.setType("服务类交易的协议");
//                }else if("interest".equals(type)){
//                    businessTypeVo.setType("利益转移类交易的协议");
//                }else if("fund".equals(type)){
//                    businessTypeVo.setType("资金运用类交易的协议");
//                }else if("business".equals(type)){
//                    businessTypeVo.setType("保险业务和其他类交易的协议");
//                }
//            }
            //附件类型编码
            businessTypeVo.setTypeCode(typeCode);
            if (typeCode.startsWith("Y")) {
                businessTypeVo.setIsMust("逐笔报告必传");
            } else {
                businessTypeVo.setIsMust("否");
            }

            rows.add(businessTypeVo);
        }

        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTAL, page.getTotal());
        return result;
    }

    @RequestMapping("/uploadFilesList")
    @ResponseBody
    public Map<String, Object> uploadFilesList(String businessType, HttpServletRequest request,
                                               HttpServletResponse response, Model model,String typeCode) {
        Map<String, Object> tips = new HashMap<String, Object>();
        TraInsenclosure enclosure = null;
        List<TraInsenclosure> traInsenclosureList = new ArrayList<>();
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
            //创建文件存储路径
            String targetFolder = configs.getTemp() + File.separator + UUIDUtils.getUUID();
            File directory = new File(targetFolder);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            //循环保存文件
            List<MultipartFile> fileList = multipartRequest.getFiles("file");

            Iterator<MultipartFile> iterator = fileList.iterator();
            while (iterator.hasNext()) {
                MultipartFile file = iterator.next();
                if (!file.isEmpty()) {
                    String fileFullName = file.getOriginalFilename(); //文件全名
                    String fileName = fileFullName.substring(0, fileFullName.lastIndexOf("."));  //文件名
                    String fileType = fileFullName.substring(fileFullName.lastIndexOf(".") + 1);
                    double size = file.getSize();
                    double fileSizeMB = new BigDecimal(size / 1024 / 1024).setScale(2,
                            BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
                    //保存文件
                    String targetPath = targetFolder + File.separator + fileFullName;
                    File targetFile = new File(targetPath);
                    if (!targetFile.exists()) {
                        targetFile.createNewFile();
                    }
                    file.transferTo(targetFile);
                    //信息入库
                    enclosure = new TraInsenclosure();
                    //存入数据
                    Date currentDate = new Date();
                    String currentDateString = currentDate.toString();
                    enclosure.setEnclosurePid(BatchNumberUtil.makeBatchNumber());//确保唯一
                    enclosure.setFileType(fileType);
                    enclosure.setPath(targetPath);
                    enclosure.setRelatedName(fileName);
                    enclosure.setDelflag("1");
                    enclosure.setFileSize(String.valueOf(fileSizeMB));
                    enclosure.setBusinessType(businessType);
                    enclosure.setCreateTime(currentDate);
                    enclosure.setUploadFileType(typeCode);
                    //enclosure.setTransNum(transNum);
                    enclosure.setUploadTime(DateUtils.formatDate(new Date()));
                    UserProfile userProfile = UserProfileUtils.getUserProfile();
                    if (userProfile != null) {
                        enclosure.setUploadDept(userProfile.getDeptName());
                        enclosure.setUploadOrg(userProfile.getUnitCode());
                    }
                    traInsenclosureService.insertSelective(enclosure);
                    enclosure.setCreateTimeString(currentDateString);
                    List<TraInsenclosure> result = traInsenclosureService.select(enclosure);
                    if (result != null) {
                        Integer pid = result.get(0).getPid();
                        enclosure.setPid(pid);
                    }
                    traInsenclosureList.add(enclosure);
                }
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "上传成功");
            tips.put("traEnclosure", traInsenclosureList);
        } catch (Exception e) {
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传失败");
        }
        return tips;
    }

    /**
     * 文件下载
     *
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downFileHisLog/{id}")
    @ResponseBody
    public void downFileHisLog(@PathVariable String id, HttpServletRequest request,
                             HttpServletResponse response) {
        String uuid = UUIDUtils.getUUID();//压缩包名称
        String tempPath = configs.getTemp();//压缩包保存路径
        String fileName = uuid + ".zip";//文件名称
        String filePath = tempPath + File.separator + uuid + ".zip";//压缩文件路径
        try {
            if (org.apache.commons.lang.StringUtils.isNotBlank(id)) {
                List<Filter> filters = new Filter.Builder().eq("transNum", id).build();
                List<TraInsenclosure> traEnclosures = traInsenclosureService.select(filters);
                if (traEnclosures != null) {
                    File[] files = new File[traEnclosures.size()];
                    for (int i = 0; i < traEnclosures.size(); i++) {
                        TraInsenclosure traInsenclosure = traEnclosures.get(i);
                        String path = traInsenclosure.getPath();
                        files[i] = new File(path);
                    }
                    boolean status = ZipUtil.zip(tempPath, uuid, files);
                    if (status) {
                        DownFileUtils.downServerFile(filePath, fileName, request, response);
                    }
                }
            }
        } catch (ServiceException e) {
            logger.error("压缩文件下载异常:{}", e);
        } finally {
            File file = new File(filePath);
            ContractUtil.delete(file);
        }
    }

}
