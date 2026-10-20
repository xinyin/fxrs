/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年06月28日 16:09:20
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.MsgDeployService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;


@Controller
@RequestMapping("/bus/msgDeploy")
public class MsgDeployController extends CrudController<MsgDeploy> {
    private static Logger logger = LoggerFactory.getLogger(MsgDeployController.class);

    @Autowired
    private MsgDeployService msgDeployService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private Configs configs;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/msgDeployList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<MsgDeploy> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        msgDeployService.query(page, filters);

    }


    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/query"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> query(Page<MsgDeploy> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        prepareQuery(page, filters, orders, request);

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

        return "bus/msgDeployEdit";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    @Log(module = "信息发布管理", description = "编辑信息发布数据")
    public String edit(@PathVariable Integer pid, Model model, HttpServletRequest request, HttpServletResponse response) {
        MsgDeploy msgDeploy = msgDeployService.get(pid);
        model.addAttribute("msgDeploy", msgDeploy);
        String filepath = msgDeploy.getImg1();
        String path = configs.getMsgimg() + '/' + filepath;
        List<String> names = new ArrayList<>();
        try {
            File f = new File(path);
            if (f.isDirectory()) {
                File[] fList = f.listFiles();
                model.addAttribute("fList", fList.length);
                for (int j = 0; j < fList.length; j++) {
                    File file = fList[j];
                    if (file.isFile()) {
                        names.add(file.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("names", names);

        return "bus/msgDeployEdit";
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

        MsgDeploy msgDeploy = msgDeployService.get(pid);
        model.addAttribute("msgDeploy", msgDeploy);
        String filepath = msgDeploy.getImg1();
        String path = configs.getMsgimg() + '/' + filepath;
        List<String> names = new ArrayList<>();
        try {
            File f = new File(path);
            if (f.isDirectory()) {
                File[] fList = f.listFiles();
                model.addAttribute("fList", fList.length);
                for (int j = 0; j < fList.length; j++) {
                    File file = fList[j];
                    if (file.isFile()) {
                        names.add(file.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("names", names);

        return "bus/msgDeployView";
    }

    /**
     * 保存
     *
     * @param msgDeploy
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(MsgDeploy msgDeploy) {
        //未发布
        msgDeploy.setReleaseType("WFB");
        //未置顶
        msgDeploy.setTopType("WZD");
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = msgDeployService.exists(msgDeploy.getPid());
        try {
            if (isExist) {
                msgDeployService.updateSelective(msgDeploy);
            } else {
                int i = msgDeployService.insertSelective(msgDeploy);
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

    @RequestMapping(value = "/saveAndRelease")
    @ResponseBody
    public Map<String, Object> saveAndRelease(MsgDeploy msgDeploy) {
        //未发布
        msgDeploy.setReleaseType("YFB");
        //未置顶
        msgDeploy.setTopType("WZD");
        //设置发布时间为当时
        msgDeploy.setReleaseTime(new Date());
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = msgDeployService.exists(msgDeploy.getPid());
        try {
            if (isExist) {

				msgDeployService.updateSelective(msgDeploy);
            } else {
				int i = msgDeployService.insertSelective(msgDeploy);
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
            msgDeployService.deleteByPrimaryKey(pid);
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

        boolean exist = msgDeployService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


    /*
     * 文件上传
     * */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public Map<String, Object> fileUpload(HttpServletRequest request, HttpServletResponse response, Model model) {
        return msgDeployService.fileUpload(request, response);


    }

    /**
     * 读取文件
     *
     * @param filePath
     * @param request
     * @param response
     */
    @RequestMapping(value = "/image")
    public void image(@RequestParam("filePath") String filePath, HttpServletRequest request, HttpServletResponse response) {
        String path = configs.getMsgimg() + '/' + filePath;
        response.setContentType("image/**");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File(path);
            InputStream in = new FileInputStream(file);
            bis = new BufferedInputStream(in);
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while ((bytesRead = bis.read(buff, 0, buff.length)) != -1) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
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
     * 发布
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/inRelease/{pid}")
    @ResponseBody
    public Map<String, Object> inRelease(@PathVariable Integer pid) {
        Map<String, Object> tips = new HashMap<String, Object>();
        MsgDeploy msgDeploy=msgDeployService.get(pid);
        msgDeploy.setReleaseType("YFB");
        msgDeploy.setReleaseTime(new Date());
        int i =msgDeployService.updateSelective(msgDeploy);
        try {
            if(i>0){
                tips.put(WebCode.SUCCESS, true);
            }else{
                tips.put(WebCode.SUCCESS, false);
            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
        }
        return tips;
    }

    /**
     * 取消发布
     * @param pid
     * @return
     */
    @RequestMapping(value = "/unRelease/{pid}")
    @ResponseBody
    public Map<String, Object> unRelease(@PathVariable Integer pid) {
        Map<String, Object> tips = new HashMap<String, Object>();
        MsgDeploy msgDeploy=msgDeployService.get(pid);
        msgDeploy.setReleaseType("WFB");
        msgDeploy.setReleaseTime(null);
        int i =msgDeployService.update(msgDeploy);
        try {
            if(i>0){
                tips.put(WebCode.SUCCESS, true);
            }else{
                tips.put(WebCode.SUCCESS, false);
            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
        }
        return tips;
    }

    /**
     * 置顶
     * @param pid
     * @return
     */
    @RequestMapping(value = "/inTop/{pid}")
    @ResponseBody
    public Map<String, Object> inTop(@PathVariable Integer pid) {
        Map<String, Object> tips = new HashMap<String, Object>();
        MsgDeploy msgDeploy=msgDeployService.get(pid);
        msgDeploy.setTopType("YZD");
        msgDeploy.setTopTime(new Date());
        int i =msgDeployService.updateSelective(msgDeploy);
        try {
            if(i>0){
                tips.put(WebCode.SUCCESS, true);
            }else{
                tips.put(WebCode.SUCCESS, false);
            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
        }
        return tips;
    }

    /**
     * 取消置顶
     * @param pid
     * @return
     */
    @RequestMapping(value = "/unTop/{pid}")
    @ResponseBody
    public Map<String, Object> unTop(@PathVariable Integer pid) {
        Map<String, Object> tips = new HashMap<String, Object>();
        MsgDeploy msgDeploy=msgDeployService.get(pid);
        msgDeploy.setTopType("WZD");
        msgDeploy.setTopTime(null);
        int i =msgDeployService.update(msgDeploy);
        try {
            if(i>0){
                tips.put(WebCode.SUCCESS, true);
            }else{
                tips.put(WebCode.SUCCESS, false);
            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
        }
        return tips;
    };

    /**
     * 首页信息咨询5条
     * @return
     */
    @RequestMapping(value = "/selectReleaseMsg")
    @ResponseBody
    public List<MsgDeploy> selectRelaseMsg() {
        List<MsgDeploy> msgDeploy=msgDeployService.selectReleaseMsg();
        return msgDeploy;
    }

    @RequestMapping(value = "/selectReleaseNews")
    @ResponseBody
    public List<MsgDeploy> selectRelaseNews() {
        List<MsgDeploy> msgDeploys=msgDeployService.selectReleaseNews();
        return msgDeploys;
    }

    @RequestMapping(value = "/NewsImage")
    public void NewsImage(@RequestParam("pid") Integer pid, HttpServletRequest request, HttpServletResponse response) {
        MsgDeploy msgDeploy=msgDeployService.get(pid);
        String filePath = configs.getMsgimg() + '/' + msgDeploy.getImg1();
        try {
            File f = new File(filePath);
            if (f.isDirectory()) {
                File[] fList = f.listFiles();
                    File file = fList[0];
                String Path = configs.getMsgimg() + '/' + msgDeploy.getImg1()+'/'+ file.getName();
                response.setContentType("image/**");
                BufferedInputStream bis = null;
                BufferedOutputStream bos = null;
                try {
                    File file1 = new File(Path);
                    InputStream in = new FileInputStream(file1);
                    bis = new BufferedInputStream(in);
                    bos = new BufferedOutputStream(response.getOutputStream());
                    byte[] buff = new byte[2048];
                    int bytesRead;
                    while ((bytesRead = bis.read(buff, 0, buff.length)) != -1) {
                        bos.write(buff, 0, bytesRead);
                    }
                    bos.flush();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}