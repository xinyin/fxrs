/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月30日 15:51:59
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yzj.jep.app.bus.model.GdFile;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
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

import com.yzj.jep.app.bus.model.TraEnclosure;
import com.yzj.jep.app.bus.service.TraEnclosureService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping("/bus/traEnclosure")
public class TraEnclosureController extends CrudController<TraEnclosure> {
    private static Logger logger = LoggerFactory.getLogger(TraEnclosureController.class);

    @Autowired
    private TraEnclosureService traEnclosureService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/traEnclosureList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<TraEnclosure> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        traEnclosureService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/traEnclosureEdit";
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

        TraEnclosure traEnclosure = traEnclosureService.get(pid);
        model.addAttribute("traEnclosure", traEnclosure);
        model.addAttribute("opt", "edit");

        return "bus/traEnclosureEdit";
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

        TraEnclosure traEnclosure = traEnclosureService.get(pid);
        model.addAttribute("traEnclosure", traEnclosure);

        return "bus/traEnclosureView";
    }

    /**
     * 保存
     *
     * @param traEnclosure
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(TraEnclosure traEnclosure) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = traEnclosureService.exists(traEnclosure.getPid());
        try {
            if (isExist) {

                traEnclosureService.update(traEnclosure);
            } else {
                traEnclosureService.insert(traEnclosure);
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
            traEnclosureService.deleteByPrimaryKey(pid);
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

        boolean exist = traEnclosureService.exists(pid);
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
    public Map<String, Object> uploadFile(HttpServletRequest request, HttpServletResponse response, Model model) {
        String businessType = request.getParameter("businessType");//业务类型
        Map<String, Object> tips = new HashMap<String, Object>();
        TraEnclosure enclosure = null;
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartRequest.getFile("file");
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

                String path = "D:/testForDocFile";
//				String path = "/home/rptrs/upload";
                File dest = new File(path + "/" + fileNameAll);
                if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                file.transferTo(dest); //保存文件
                enclosure = new TraEnclosure();
                //存入数据
                enclosure.setFileType(fileType);
                enclosure.setPath(path + "/" + fileNameAll);
                enclosure.setRelatedName(fileName);
                enclosure.setDelflag("1");
                enclosure.setFileSize(String.valueOf(fileSizeMB));
                enclosure.setBusinessType(businessType);
                enclosure.setCreateTime(new Date());
                enclosure.setModifiedTime(new Date());
                enclosure.setUploadTime(DateUtils.formatDate(new Date()));
                UserProfile userProfile = UserProfileUtils.getUserProfile();
                enclosure.setUploadDept(userProfile.getDeptName());
                enclosure.setUploadOrg(userProfile.getUnitCode());
                enclosure.setCreateTimeString(new Date().toString());
                enclosure.setModifiedTimeString(new Date().toString());
                traEnclosureService.insert(enclosure);
                Integer pid = traEnclosureService.select(enclosure).get(0).getPid();
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

    @RequestMapping(value = "queryList")
    @ResponseBody
    public Map<String, Object> query(Page<TraEnclosure> page, Model model, HttpServletRequest request, String transNum) {
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

    //PDF文件预览
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void pdfStreamHandler(HttpServletRequest request, HttpServletResponse response) {
        String pidStr = request.getParameter("pid");
        if (StringUtils.isEmpty(pidStr)) {
            return;
        }
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.EQ, Integer.valueOf(pidStr)));
        List<TraEnclosure> traEnclosures = traEnclosureService.select(filters);
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
                TraEnclosure traEnclosure = new TraEnclosure();
                traEnclosure.setPid(Integer.valueOf(str));
                traEnclosure.setDelflag("0");
                traEnclosureService.updateSelective(traEnclosure);
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