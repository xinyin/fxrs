package com.yzj.jep.app.contract.util;


import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 合同接口-相关工具方法
 */
public class ContractUtil {

    private static final Logger logger = LoggerFactory.getLogger(ContractUtil.class);

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static long getTimeStamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 获取数据签名
     *
     * @param secretKey
     * @param nonce
     * @param data
     * @return
     */
    public static String getSign(String secretKey, String nonce, String data) {
        String sign = nonce.concat(data).concat(secretKey);
        return MD5FileUtil.getMD5String(sign);
    }

    /**
     * 解压缩zip文件
     *
     * @param path
     * @param name
     */
    public static void unZipFile(String path, String name) {
        ZipInputStream zipInputStream = null;
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            String directory = path + File.separator + name;
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String zipPath = directory + ".zip";
            //读取压缩文件
            zipInputStream = new ZipInputStream(new FileInputStream(zipPath), Charset.forName("GBK"));
            //zip文件实体类
            ZipEntry zipEntry = null;
            //遍历压缩文件内部 文件数量
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (!zipEntry.isDirectory()) {
                    //文件输出流
                    fileOutputStream = new FileOutputStream(directory + File.separator + zipEntry.getName());
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    int length = 0;
                    byte[] buf = new byte[1024];
                    while ((length = zipInputStream.read(buf)) != -1) {
                        bufferedOutputStream.write(buf, 0, length);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("failed to extract file:{}", e);
        } finally {
            IOUtils.closeQuietly(bufferedOutputStream);
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(zipInputStream);
        }
    }

    /**
     * 删除文件以及文件中文件
     *
     * @param file
     */
    public static boolean delete(File file) {
        try {
            if (!file.exists()) {
                return false;
            }
            if (file.isFile()) {
                return file.delete();
            } else {
                File[] files = file.listFiles();
                for (File f : files) {
                    delete(f);
                }
                return file.delete();
            }
        } catch (Exception e) {
            logger.error("delete file exception:{}", e);
            return false;
        }
    }

}
