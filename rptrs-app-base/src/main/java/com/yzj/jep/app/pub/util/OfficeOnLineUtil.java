package com.yzj.jep.app.pub.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.contract.util.ContractUtil;
import com.yzj.jep.app.contract.util.MD5FileUtil;
import com.yzj.jep.app.contract.util.MD5Util;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 文件编造在线工具类
 */
public class OfficeOnLineUtil {

    private static final Logger logger = LoggerFactory.getLogger(OfficeOnLineUtil.class);

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
        return md5(sign);
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

    public static String md5(String text) {
        String encodeStr = DigestUtils.md5Hex(text);
        return encodeStr.toUpperCase();
    }

}
