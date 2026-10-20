/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月10日 15:18:01
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yzj.jep.app.bus.model.RelFile;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.MeetingFile;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.MeetingFileService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping("/bus/meetingFile")
public class MeetingFileController extends CrudController<MeetingFile> {
    private static Logger logger = LoggerFactory.getLogger(MeetingFileController.class);

    @Autowired
    private MeetingFileService meetingFileService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/meetingFileList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<MeetingFile> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        /*filters.add(new Filter("",""));*/
        meetingFileService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/meetingFileEdit";
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

        MeetingFile meetingFile = meetingFileService.get(pid);
        model.addAttribute("meetingFile", meetingFile);
        model.addAttribute("opt", "edit");

        return "bus/meetingFileEdit";
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

        MeetingFile meetingFile = meetingFileService.get(pid);
        model.addAttribute("meetingFile", meetingFile);

        return "bus/meetingFileView";
    }

    /**
     * 保存
     *
     * @param meetingFile
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(MeetingFile meetingFile) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = meetingFileService.exists(meetingFile.getPid());
        try {
            if (isExist) {

                meetingFileService.update(meetingFile);
            } else {
                meetingFileService.insert(meetingFile);
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
            meetingFileService.deleteByPrimaryKey(pid);
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

        boolean exist = meetingFileService.exists(pid);
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
//		String opt = request.getParameter("opt");//判断新增还是编辑
//		String relSerial = "";
//		if(opt.equals("edit"));
//			relSerial = request.getParameter("relSerial");
//		}

        UserProfile profile = UserProfileUtils.getUserProfile();


        Map<String, Object> tips = new HashMap<String, Object>();
        MeetingFile meetingFile = null;
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartRequest.getFile("file");
            if (!file.isEmpty()) {
                /*文件大小，上传机构，上传部门*/
                String fileNameAll = file.getOriginalFilename(); //文件全名
                String fileName = fileNameAll.substring(0, fileNameAll.lastIndexOf("."));  //文件名
                String[] strArray = fileNameAll.split("\\.");
                int suffixIndex = strArray.length - 1;
                String fileType = strArray[suffixIndex] + ""; //文件类型
                double size = file.getSize();
                System.out.println("文件类型为 " + fileType);
                double fileSizeMB = new BigDecimal(size / 1024 / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
                System.out.println(fileNameAll + "-->" + fileSizeMB + "MB");

                String path = "D:/test";
                File dest = new File(path + "/" + fileNameAll);
                if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                file.transferTo(dest); //保存文件
                meetingFile = new MeetingFile();
                //存入数据
                /*增加个会议管理的id*/

                Integer yyMMddhhmmss = Integer.valueOf(new SimpleDateFormat("yyMdhhmm").format(new Date()).toString());
                meetingFile.setMeetingPid(yyMMddhhmmss);
                meetingFile.setFileName(fileName);
                meetingFile.setFileType(fileType);
                meetingFile.setFileUrl(path + "/" + fileNameAll);
                meetingFile.setDelflag("1");
                meetingFile.setFileSize(String.valueOf(fileSizeMB));

                String uploaddepartment = profile.getDeptName();//用户所在部门名称
                String uploadagency = profile.getUnitName();//用户所在机构名称
                meetingFile.setUploadAgency(uploadagency);
                meetingFile.setUploadDepartment(uploaddepartment);

                meetingFileService.insert(meetingFile);
                MeetingFile meetingFile1 = meetingFileService.select(meetingFile).get(0);
			/*	relFile = new RelFile();
				//存入数据
				relFile.setFileType(fileType);
				relFile.setFileUrl(path+"/"+fileNameAll);
				relFile.setFileName(fileName);
				relFile.setDelflag("1");
				relFile.setFileSize(String.valueOf(fileSizeMB));
//				relFile.setRelSerial(relSerial);
				relFileService.insert(relFile);
				RelFile relFile1 = relFileService.select(relFile).get(0);*/
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, "上传成功");
                tips.put("meetingFile", meetingFile1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "上传失败");
        }
        return tips;
    }

    @RequestMapping(value = "queryList/{meetingPid}")
    @ResponseBody
    public Map<String, Object> query(Page<MeetingFile> page, Model model, HttpServletRequest request, @PathVariable String meetingPid) {

        System.out.println("meetingPid = " + meetingPid);
        System.out.println("page = " + page);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        filters.add(new Filter("meetingPid", Operator.EQ, meetingPid));
        prepareQuery(page, filters, orders, request);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }
}