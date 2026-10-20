package com.yzj.jep.app.pub.web.controller;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.service.PubFileTemplateService;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.sub.util.FileUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author wll
 * 归档文件管理
 */
@Controller
@RequestMapping("/pub/archManage")
public class PubArchManageController extends CrudController<Publog> {
    private static Logger logger = LoggerFactory.getLogger(PubArchManageController.class);
    private static final int BUFFER_SIZE = 2 * 1024;
    @Autowired
    private PubFileTemplateService pubFileTemplateService;

    @Autowired
    private PublogService publogService;

    @Autowired
    private Configs configs;

    //归档文件管理页面
    @RequestMapping(value = "/List")
    public String archManageList(Model model) {

        return "pub/archManageList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Publog> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        publogService.query(page, filters, orders);
    }

    /**
     * 为列表页面做数据准备
     */
    @RequestMapping(value = {"/query1"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> query1(@RequestParam("quarter") String quarter, @RequestParam("archDate") String archDate,String month, Integer pageSize, Integer pageNumber, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageInfo<Publog> page =  publogService.queryByConditions(pageNumber,pageSize,archDate,quarter,month);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getList());
        return result;
    }


    //归档文件管理-文件下载-单条
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public File download(@RequestParam("pid") String pid, HttpServletResponse response, HttpServletRequest request) throws Exception {
        File file = null;
        System.out.println("下载pid" + pid);
        String zipPath = "D:/downLoad1/";
        List<File> files = new ArrayList<File>();
        List<String> name = new ArrayList<>();
        //创建zip输出流
        ZipOutputStream zos = null;
        List<Filter> filters = new Filter.Builder().eq("pid", pid).build();
        List<Publog> puibfiles = publogService.select(filters);
        String fileUrl = puibfiles.get(0).getFileUrl();
        String fileName = puibfiles.get(0).getPubFileName();
        files.add(new File(fileUrl));
        name.add(fileName);
        String zipName = UUID.randomUUID().toString().replace("-", "") + ".zip";
        FileUtils.compressToZip(fileUrl, zipPath, zipName);

        file = new File(zipName);
        //1.获取要下载的文件的绝对路径
        String realPath = zipName;
        //2.获取要下载的文件名
        String fileName1 = realPath.substring(realPath.lastIndexOf(File.separator) + 1);
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName1.getBytes(), "ISO8859-1"));
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


    //文件下载(批量)
    @RequestMapping(value = "/downloadAll", method = RequestMethod.GET)
    @ResponseBody
    public File download(HttpServletResponse response, HttpServletRequest request) throws Exception {
        File file = null;
        String zipPath = "D:/downLoad1/";
        List<File> files = new ArrayList<File>();
        List<String> name = new ArrayList<>();
        //创建zip输出流
        ZipOutputStream zos = null;
        List<Filter> filters = RequestUtils.buildFilters(request);
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        filters.add(new Filter("status", Operator.EQ, "1"));
        List<Publog> puibfiles = publogService.select(filters);
        for (Publog publogs : puibfiles) {
            //   D:\westForDocFile\1c9740b1-ed32-46ba-8ed0-32b7e6e139a0
//            int index = publogs.getFileUrl().indexOf("\\");
//            int index2 = publogs.getFileUrl().indexOf("\\",index + 1);
            int index2 = publogs.getFileUrl().lastIndexOf("\\");
            String fileUrl = publogs.getFileUrl().substring(0,index2);
            System.out.println(fileUrl);
            String fileName = publogs.getPubFileName();
            files.add(new File(fileUrl));
            name.add(fileName);
            String zipName = UUID.randomUUID().toString().replace("-", "") + ".zip";

            //sourceFilePath    源文件路径
            //zipFilePath    压缩后文件存储路径
            //zipFilename    压缩文件名
            FileUtils.compressToZip(fileUrl, zipPath, zipName);
            file = new File(zipName);
            //1.获取要下载的文件的绝对路径
            String realPath = zipName;
            //2.获取要下载的文件名
            String fileName1 = realPath.substring(realPath.lastIndexOf(File.separator) + 1);
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName1.getBytes(), "ISO8859-1"));
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
        }

        return file;
    }
}
