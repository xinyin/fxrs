package com.yzj.jep.app.util;

import com.yzj.jep.app.bus.service.SubmissionFileService;
import org.apache.commons.lang.time.StopWatch;

import java.util.zip.ZipEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.tools.zip.ZipOutputStream;

/**
 * @author
 */
@Component
public class ZipFileUtil {

    @Autowired
    private SubmissionFileService submissionFileService;
    private static ZipFileUtil zipFileUtil;

    @PostConstruct
    public void init() {
        zipFileUtil = this;
        zipFileUtil.submissionFileService = this.submissionFileService;
    }

    /**
     * 解压缩zipFile
     *
     * @param file      要解压的zip文件对象
     * @param outputDir 要解压到某个指定的目录下
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static List unZip(File file, String outputDir) throws IOException {
        Logger logger = LoggerFactory.getLogger(ZipFileUtil.class);
        ZipFile zipFile = null;
        List list = new ArrayList();
        String path = null;
        String fileName = null;
        String random = String.valueOf(System.currentTimeMillis()).substring(8);
        try {
            Charset cP866 = Charset.forName("CP866");
            zipFile = new ZipFile(file, cP866);
            createDirectory(outputDir, null);
            //创建输出目录

            Enumeration<?> enums = zipFile.entries();
            while (enums.hasMoreElements()) {

                ZipEntry entry = (ZipEntry) enums.nextElement();
                /*fileName = entry.getName();
                logger.info("解压:" + fileName);
                //List ids1 = zipFileUtil.submissionFileService.(entry.getName());
                logger.info("------------------------------------ids1:" + ids1);
                int pos1 = fileName.lastIndexOf(".");
                String str1 = fileName.substring(pos1 + 1).toLowerCase();
                if (ids1.size() > 0) {
                    fileName = fileName.substring(0, fileName.lastIndexOf(".")) + random + "." + str1;
                }*/
                if (entry.isDirectory()) {
                    //是目录
                    createDirectory(outputDir, fileName);
                    path = outputDir + "/" + fileName;
                    list.add(path);
                    return null;
                    //创建空目录
                } else {
                    //是文件
                    File tmpFile = new File(outputDir + "/" + fileName);
                    int pos = fileName.lastIndexOf(".");
                    String str = fileName.substring(pos + 1).toLowerCase();
                    logger.info("" + tmpFile.getParent());
                    if ("pcm".equals(str) || "wav".equals(str)) {
                        createDirectory(tmpFile.getParent() + "/", null);
                        //创建输出目录

                        InputStream in = null;
                        OutputStream out = null;
                        try {
                            in = zipFile.getInputStream(entry);
                            out = new FileOutputStream(tmpFile);
                            int length = 0;

                            byte[] b = new byte[2048];
                            while ((length = in.read(b)) != -1) {
                                out.write(b, 0, length);
                            }
                            in.close();
                            out.close();
                            path = outputDir + "/" + fileName;
                            list.add(path);
                        } catch (IOException ex) {
                            throw ex;
                        } finally {
                            if (in != null) {
                                in.close();
                            }
                            if (out != null) {
                                out.close();
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new IOException("解压缩文件出现异常", e);
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }

        }
        return list;
    }

    /**
     * 构建目录
     *
     * @param outputDir
     * @param subDir
     */
    public static void createDirectory(String outputDir, String subDir) {
        File file = new File(outputDir);
        if (!(subDir == null || "".trim().equals(subDir))) {
            //子目录不为空
            file = new File(outputDir + "/" + subDir);
        }
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.mkdirs();
        }
    }


    /**
     * 单文件下载
     *
     * @param response
     * @param txtPath
     * @param request
     */
    public static void downFile(HttpServletResponse response, String txtPath, HttpServletRequest request) {

        Logger logger = LoggerFactory.getLogger(ZipFileUtil.class);
        try {
            // path是指欲下载的文件的路径。
            File file = new File(txtPath);
            // 取得文件名。
            String fileName = file.getName();
            // 取得文件的后缀名。
            //String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(new File(txtPath)));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header方便下载
           /* response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(),"utf-8"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");*/

            String userAgent = request.getHeader("User-Agent");
            //针对IE或者以IE为内核的浏览器：
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
                //非IE浏览器的处理：
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //response.setCharacterEncoding("UTF-8");
            //response.setHeader(fileName,"utf-8");
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            logger.info("-------------------------文件路径没找到---------------------------");
            ex.printStackTrace();
        }
    }

    /**
     * 批量下载成zip格式文件
     *
     * @param zipFilePath
     * @param filePaths
     * @param zos
     * @param response
     * @return
     * @throws IOException
     */
    public static String zipFile(File zipFilePath,
                                 List<String> filePaths, ZipOutputStream zos, HttpServletResponse response)
            throws IOException {

        Logger logger = LoggerFactory.getLogger(ZipFileUtil.class);
        StopWatch watch = new StopWatch();
        watch.start();
        int len = 0;
        FileInputStream zipInput = new FileInputStream(zipFilePath);
        ;
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[2 * 1024];  //设置读取数据缓存大小
        BufferedInputStream bis = null;
        //循环读取文件路径集合，获取每一个文件的路径
        try {
            for (String filePath : filePaths) {
                File inputFile = new File(filePath);  //根据文件路径创建文件
                //创建zip输出流
                // 取得文件名。
                if (inputFile.exists()) { //判断文件是否存在
                    if (inputFile.isFile()) {  //判断是否属于文件，还是文件夹
                        //创建输入流读取文件
                        bis = new BufferedInputStream(new FileInputStream(inputFile));
                        //将文件写入zip内，即将文件进行打包
                        logger.info("------------------------inputFile.getName()-----------------------" + inputFile.getName());
                        zos.putNextEntry(new org.apache.tools.zip.ZipEntry(inputFile.getName()));
                        //zos.setEncoding(System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding")));
                        //zos.setEncoding(System.getProperty("sun.jnu.encoding"));
                        zos.setEncoding("GBK");
                        //写入文件的方法，同上
                        while ((len = bis.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }
                        //response.setHeader("Content-Disposition", "attachment; filename=\"" + inputFile.getName() + "\"; filename*=utf-8''" + inputFile.getName());
                    } else {  //如果是文件夹，则使用穷举的方法获取文件，写入zip
                        logger.info("-----------------------------------未找到文件---------------------------------------");
                    }
                }
            }
            //解决中文名称乱码
            zos.flush();
            zos.close();
            bis.close();
            zos.closeEntry();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("DownLoad.zip", "UTF-8"));
            //response.setCharacterEncoding("UTF-8");
            while ((len = zipInput.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                logger.info("------------------------压缩完成，耗时：" + watch.getTime() + " ms");
                out.flush();
                out.close();
                zipInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        watch.stop();
        return null;
    }
}
