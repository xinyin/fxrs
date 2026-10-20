package com.yzj.jep.app.sub.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author LONG
 * @date 2020/6/20
 * @description
 */

@Component
public class DownFileUtils {
    private static Logger logger = LoggerFactory.getLogger(DownFileUtils.class);
    private static String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};

    //通过浏览器进行下载
    public static boolean downServerFile(String filePath, String fileName, HttpServletRequest request, HttpServletResponse response) {
        boolean flag = false;
        FileInputStream ips = null;
        ServletOutputStream out = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                //1.读取要下载的内容
                ips = new FileInputStream(file);
                //2.通过浏览器下载文件名中文乱码
                boolean isMSIE = isMSBrowser(request);
                if (isMSIE) {
                    //IE浏览器的乱码问题解决
                    fileName = URLEncoder.encode(fileName, "UTF-8");
                } else {
                    //万能乱码问题解决
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                }
                //3.设置下载文件的mineType，告诉浏览器下载文件类型
                String mineType = request.getServletContext().getMimeType(fileName);
                response.setContentType(mineType);
                //4.设置一个响应头，无论是否被浏览器解析，都下载
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                response.setHeader("Content-Length", String.valueOf(file.length()));
                //5.将要下载的文件内容通过输出流写到浏览器
                out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024 * 10];
                while ((len = ips.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                ips.close();
                out.close();
                logger.info("下载文件成功");
                flag = true;
            } else {
                logger.info("文件不存在");
                flag = false;
            }
        } catch (Exception e) {
            logger.info("下载文件失败！" + e.getMessage());
            flag = false;
        }
        return flag;
    }

    /**
     * 判断是否是ie浏览器
     *
     * @param request
     * @return
     */
    public static boolean isMSBrowser(HttpServletRequest request) {

        String userAgent = request.getHeader("User-Agent");
        for (String signal : IEBrowserSignals) {
            if (userAgent.contains(signal)) {
                return true;
            }
        }
        return false;
    }

    //从服务器上默认进行下载
    public static boolean saveUrlFile(String ServerUrl, String filePath, String filename) {
        logger.info("下载文件开始！！！");
        boolean flag = false;
        FileOutputStream fileOut = null;
        FileInputStream inputStream = null;
        File fi = new File(ServerUrl);
        if (!fi.exists()) {
            logger.info("服务器文件不存在！");
            flag = false;
        }
        //创建不同的文件夹目录
        File file = new File(filePath);
        //判断文件夹是否存在
        if (!file.exists()) {
            logger.info("文件夹不存在 创建新的文件夹");
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        try {
            logger.debug("通过文件流来进行下载");
            //获取网络输入流
            inputStream = new FileInputStream(ServerUrl);
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
            if (!filePath.endsWith("/")) {
                filePath += "/";
            }

           /*
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            ServletOutputStream os = response.getOutputStream();*/


            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(filePath + filename);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);
            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while (length != -1) {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            flag = true;
            logger.info("报送文件下载成功！");
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
            logger.info("报送文件下载失败！");
        }
        return flag;
    }
}