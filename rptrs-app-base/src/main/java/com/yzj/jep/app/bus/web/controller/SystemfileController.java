/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月13日 15:32:42
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yzj.jep.app.util.ZipUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yzj.jep.app.bus.model.Systemfile;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.SystemfileService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/bus/systemfile")
public class SystemfileController extends CrudController<Systemfile> {
    private static Logger logger = LoggerFactory.getLogger(SystemfileController.class);

    @Autowired
    private SystemfileService systemfileService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    //行内制度管理
    @RequestMapping(value = "/I")
    public String listForInner(Model model) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        model.addAttribute("type", "I");
        model.addAttribute("entity", "systemFileInner");
        model.addAttribute("id", uuid);
        return "bus/systemfileList";
    }

    //行外监管制度管理
    @RequestMapping(value = "/O")
    public String listForOutside(Model model) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        model.addAttribute("type", "O");
        model.addAttribute("entity", "systemFileOutSide");
        model.addAttribute("id", uuid);
        return "bus/systemfileList";
    }

    //同业监管制度管理
    @RequestMapping(value = "/S")
    public String listForSame(Model model) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        model.addAttribute("type", "S");
        model.addAttribute("entity", "systemFileSame");
        model.addAttribute("id", uuid);
        return "bus/systemfileList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Systemfile> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        String beginDate = "";
        String endDate = "";
        int index;
        for (int i = 0; i < filters.size(); i++) {
            if ("beginDate".equals(filters.get(i).getProperty())) {
                index = i;
                beginDate = filters.get(i).getValue().toString();
                filters.remove(index);
                if (!StringUtils.isEmpty(beginDate)) {
                    filters.add(new Filter("approvalTime", Operator.GE, beginDate));
                }
            }
            if ("endDate".equals(filters.get(i).getProperty())) {
                endDate = filters.get(i).getValue().toString();
                index = i;
                filters.remove(index);
                if (!StringUtils.isEmpty(beginDate)) {
                    filters.add(new Filter("approvalTime", Operator.LE, endDate));
                }
            }
        }
        filters.add(new Filter("isDel", Operator.EQ, 1));
        systemfileService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/systemfileEdit";
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

        Systemfile systemfile = systemfileService.get(pid);
        model.addAttribute("systemfile", systemfile);
        model.addAttribute("opt", "edit");

        return "bus/systemfileEdit";
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

        Systemfile systemfile = systemfileService.get(pid);
        model.addAttribute("systemfile", systemfile);

        return "bus/systemfileView";
    }

    /**
     * 保存
     *
     * @param systemfile
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(Systemfile systemfile) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = systemfileService.exists(systemfile.getPid());
        try {
            if (isExist) {

                systemfileService.update(systemfile);
            } else {
                systemfileService.insert(systemfile);
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
    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> remove(@RequestParam("pid") List<Integer> pids) {
//		Map<String, Object> tips = new HashMap<String, Object>();
//		if (pids.size()>0 &&pids!=null) {
//				for (Integer pid : pids) {
//					try {
//					List<Filter> filters = new Filter.Builder().eq("pid", pid).build();
//					List<Systemfile> systemfiles = systemfileService.select(filters);
//					String fileUrl = systemfiles.get(0).getFileUrl();
//					File file = new File(fileUrl);
//					if (file.exists()) {
//						if (file.delete()) {
//							System.out.println("文件删除成功");
//						} else {
//							System.out.println("文件删除失败");
//						}
//					} else {
//						System.out.println("文件不存在");
//					}
//					systemfileService.deleteByPrimaryKey(pid);
//					tips.put(WebCode.SUCCESS, true);
//					tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
//				} catch (ServiceException e) {
//					logger.info(e.getMessage());
//					tips.put(WebCode.SUCCESS, false);
//					tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
//				}
//				}
//		}else {
//			tips.put(WebCode.SUCCESS, false);
//			tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
//		}
//		return tips;
        Map<String, Object> tips = new HashMap<String, Object>();
        if (pids != null && pids.size() > 0) {
            List<Filter> filters = null;
            for (Integer pid : pids) {
                filters = new Filter.Builder().eq("pid", pid).build();
                List<Systemfile> systemfiles = systemfileService.select(filters);
                Systemfile systemfile = systemfiles.get(0);
                systemfile.setIsDel(0);
                systemfile.setEnableStatus(1);
                systemfileService.update(systemfile);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } else {
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

        boolean exist = systemfileService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /*
     * 区分制度类型展示数据
     * */
    @RequestMapping(value = "/systemType/{type}")
    @ResponseBody
    public Map<String, Object> systemType(String type, Page<Systemfile> page, HttpServletRequest request) {

        String fileName = request.getParameter("__LIKES_fileName");
        String enableStatus = request.getParameter("__EQS_enableStatus");
        String modifiedTime = request.getParameter("__EQS_modifiedTime");
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = null;
        List<Order> orders = null;
        if (!StringUtils.isEmpty(fileName)) {
            filters = new Filter.Builder().eq("systemType", type).like("fileName", fileName).eq("isDel", 1).build();
            orders = new Order.Builder().desc("modifiedTime").build();
        }
        if (!StringUtils.isEmpty(enableStatus)) {
            filters = new Filter.Builder().eq("systemType", type).like("enableStatus", enableStatus).eq("isDel", 1).build();
            orders = new Order.Builder().desc("modifiedTime").build();
        }
        if (!StringUtils.isEmpty(modifiedTime)) {
            filters = new Filter.Builder().eq("systemType", type).like("modifiedTime", modifiedTime).eq("isDel", 1).build();
            orders = new Order.Builder().desc("modifiedTime").build();
        }
        if (!StringUtils.isEmpty(fileName) && !StringUtils.isEmpty(enableStatus) && !StringUtils.isEmpty(modifiedTime)) {
            filters = new Filter.Builder().eq("systemType", type).like("fileName", fileName).eq("isDel", 1).eq("enableStatus", enableStatus).like("modifiedTime", modifiedTime).build();
            orders = new Order.Builder().desc("modifiedTime").build();
        }
        if (!StringUtils.isEmpty(fileName) && !StringUtils.isEmpty(enableStatus)) {
            filters = new Filter.Builder().eq("systemType", type).like("fileName", fileName).eq("isDel", 1).eq("enableStatus", enableStatus).build();
            orders = new Order.Builder().desc("modifiedTime").build();
        }
        if (!StringUtils.isEmpty(fileName) && !StringUtils.isEmpty(modifiedTime)) {
            filters = new Filter.Builder().eq("systemType", type).like("fileName", fileName).eq("isDel", 1).like("modifiedTime", modifiedTime).build();
            orders = new Order.Builder().desc("modifiedTime").build();
        }
        if (!StringUtils.isEmpty(enableStatus) && !StringUtils.isEmpty(modifiedTime)) {
            filters = new Filter.Builder().eq("systemType", type).like("enableStatus", enableStatus).eq("isDel", 1).like("modifiedTime", modifiedTime).build();
            orders = new Order.Builder().desc("modifiedTime").build();
        }
        if (StringUtils.isEmpty(fileName) && StringUtils.isEmpty(enableStatus) && StringUtils.isEmpty(modifiedTime)) {
            filters = new Filter.Builder().eq("systemType", type).eq("isDel", 1).build();
            orders = new Order.Builder().desc("modifiedTime").build();
        }
        List<Systemfile> systemfiles = systemfileService.select(filters, orders);
        page.setTotal(systemfiles.size());
        int pages = (systemfiles.size() + page.getPageSize() - 1) / page.getPageSize();
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<Systemfile> systemfileList = new ArrayList<>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < systemfiles.size()) {
                systemfileList.add(systemfiles.get(i));
            }
        }
        page.setResult(systemfileList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

//	/*
//	 * 跳转到文件上传网页
//	 * */
//	@RequestMapping(value = "/pageForUpload/{typeSys}")
//	@ResponseBody
//	public String pageForUpload(String typeSys ,Model model){
//		model.addAttribute("typeSys", typeSys);
//		return "网页路径";
//	}

    /*
     * 文件上传
     * */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file, @RequestParam("type") String type, Model model) {
        model.addAttribute("type", type);
        if (file.isEmpty()) {
            return "false," + type;
        }
        String fileNameAll = file.getOriginalFilename(); //文件全名
        String fileName = fileNameAll.substring(0, fileNameAll.lastIndexOf("."));  //文件名
        String[] strArray = fileNameAll.split("\\.");
        int suffixIndex = strArray.length - 1;
        String fileType = strArray[suffixIndex] + ""; //文件类型
        System.out.println("文件类型为 " + fileType);
        double size = file.getSize();
        double fileSizeMB = new BigDecimal(size / 1024 / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //转换单位为MB
        System.out.println(fileNameAll + "-->" + fileSizeMB + "MB");

        String path = "E:/test/" + type;
        File dest = new File(path + "/" + fileNameAll);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            Systemfile systemfile = new Systemfile();
            Random r = new Random();
            int a = (int) (r.nextDouble() * 6666666);
            systemfile.setPid(a); //主键
            systemfile.setSystemType(type);  // 制度类型
            systemfile.setFileName(fileName); //文件名称
//					systemfile.setCreateByName(name); //上传人
            systemfile.setFileType(fileType); //文件类型
            systemfile.setFileUrl(path + "/" + fileNameAll); //文件路径
            systemfile.setApproveStatus(0); //审批状态
            systemfile.setIsDel(1);//删除状态 0代表是（查询接口不传删除的数据) 1代表否
//			        Integer en = Integer.valueOf(enableStatus);
//			        systemfile.setEnableStatus(en);//制度状态(是否启用) 0启用 1禁用
//			        systemfile.setFileVersion(fileVersion);//文件版本号
            systemfile.setFileSize(fileSizeMB);//文件大小
//			        systemfile.setEffectiveTime(effectiveTime);//生效时间
            systemfile.setCreateTime(new Date()); //创建时间
            systemfile.setCreateTimeString(new Date().toString()); //创建时间
            systemfile.setModifiedTime(new Date()); //修改时间
            systemfile.setModifiedTimeString(new Date().toString()); //修改时间
            systemfileService.insert(systemfile);
            return "true," + type;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false," + type;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false," + type;
        }
    }

    /*
     * 文件下载(批量)
     * */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public File download(@RequestParam("pid") List<String> pids, HttpServletResponse response, HttpServletRequest request) throws Exception {
        File file = null;
        String zipPath = "D:/downSys/";
        if (pids.size() != 1) {
            List<File> files = new ArrayList<File>();
            String zipName = null;
            List<String> name = new ArrayList<>();
            //文件打包
            for (String s : pids) {
                List<Filter> filters = new Filter.Builder().eq("pid", s).build();
                List<Systemfile> systemfiles = systemfileService.select(filters);
                String fileUrl = systemfiles.get(0).getFileUrl();
                String fileName = systemfiles.get(0).getFileName();
                files.add(new File(fileUrl));
                name.add(fileName);
            }
            String names = name.get(0);
//			String str = names.substring(0, names.indexOf("."));
            //后续更改zip的路径 把打出来的zip文件放到指定文件夹下 定时任务和批量删除
            String str1 = names + "等.zip";
            zipName = str1;
            File[] files1 = files.toArray(new File[0]);
            ZipUtil.zip(zipPath, zipName, files1);
            file = new File(zipName);
            //1.获取要下载的文件的绝对路径
            String realPath = zipName;
            //2.获取要下载的文件名
            String fileName = realPath.substring(realPath.lastIndexOf(File.separator) + 1);
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
            //获取文件输入流
            InputStream in = new FileInputStream(realPath);
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                //将缓冲区的数据输出到客户端浏览器
                out.write(buffer, 0, len);
            }
            in.close();
            return file;
        } else {
            List<Filter> filters = new Filter.Builder().eq("pid", pids.get(0)).build();
            List<Systemfile> systemfiles = systemfileService.select(filters);
            String fileUrl = systemfiles.get(0).getFileUrl();
            String fileName1 = systemfiles.get(0).getFileName();
            String fileType = systemfiles.get(0).getFileType();
            file = new File(fileUrl);
            //1.获取要下载的文件的绝对路径
            String realPath = fileUrl;
            //2.获取要下载的文件名
//			String fileName = realPath.substring(realPath.lastIndexOf(File.separator)+1);
            String fileName = fileName1 + "." + fileType;
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            //获取文件输入流
            InputStream in = new FileInputStream(realPath);
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                //将缓冲区的数据输出到客户端浏览器
                out.write(buffer, 0, len);
            }
            in.close();
            return file;
        }
    }

    //获取上传制度机构
    @RequestMapping(value = "/queryUpload/{type}")
    @ResponseBody
    public List<String> qureyUpload(String type) {
        List<String> listFor = null;
        List<Filter> filters = new Filter.Builder().eq("systemType", type).eq("isDel", 1).build();
        List<Systemfile> systemfiles = systemfileService.select(filters);
        for (Systemfile systemfile : systemfiles) {
            String list = systemfile.getFileUrl(); //
            listFor.add(list);
        }

        return listFor;
    }

    //行内制度管理 条件查询
    @RequestMapping(value = "/innerQueryTerm")
    @ResponseBody
    public Map<String, Object> queryForInnerTerm(String type, Page<Systemfile> page, HttpServletRequest request) {
        String sysIssueOrg = request.getParameter("__LIKES_sysIssueOrg");
        String articleNumber = request.getParameter("__EQS_articleNumber");
        String enableStatus = request.getParameter("__EQS_enableStatus");
        String effectiveTime1 = request.getParameter("__LIKES_effectiveTime1");
        String effectiveTime2 = request.getParameter("__LIKES_effectiveTime2");
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = null;
        List<Order> orders = null;
        if (!StringUtils.isEmpty(sysIssueOrg)) {
            filters = new Filter.Builder().eq("systemType", type).like("sysIssueOrg", sysIssueOrg).eq("isDel", 1).build();
            orders = new Order.Builder().desc("modifiedTime").build();
        }
        List<Systemfile> systemfiles = systemfileService.select(filters, orders);
        page.setTotal(systemfiles.size());
        int pages = (systemfiles.size() + page.getPageSize() - 1) / page.getPageSize();
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<Systemfile> systemfileList = new ArrayList<>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < systemfiles.size()) {
                systemfileList.add(systemfiles.get(i));
            }
        }
        page.setResult(systemfileList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    //行内制度管理 查询
    @RequestMapping(value = "/innerQuery")
    @ResponseBody
    public Map<String, Object> queryForInner(Page<Systemfile> page, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = null;
        List<Order> orders = null;
        filters = new Filter.Builder().eq("systemType", "I").eq("isDel", 1).build();
        orders = new Order.Builder().desc("modifiedTime").build();
        List<Systemfile> systemfiles = systemfileService.select(filters, orders);
        page.setTotal(systemfiles.size());
        int pages = (systemfiles.size() + page.getPageSize() - 1) / page.getPageSize();
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<Systemfile> systemfileList = new ArrayList<>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < systemfiles.size()) {
                systemfileList.add(systemfiles.get(i));
            }
        }
        page.setResult(systemfileList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    //行外监管制度管理
    @RequestMapping(value = "/outSideQuery")
    @ResponseBody
    public Map<String, Object> queryForOutSide(Page<Systemfile> page, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = null;
        List<Order> orders = null;
        filters = new Filter.Builder().eq("systemType", "O").eq("isDel", 1).build();
        orders = new Order.Builder().desc("modifiedTime").build();
        List<Systemfile> systemfiles = systemfileService.select(filters, orders);
        page.setTotal(systemfiles.size());
        int pages = (systemfiles.size() + page.getPageSize() - 1) / page.getPageSize();
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<Systemfile> systemfileList = new ArrayList<>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < systemfiles.size()) {
                systemfileList.add(systemfiles.get(i));
            }
        }
        page.setResult(systemfileList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }
}