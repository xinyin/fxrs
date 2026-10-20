package com.yzj.jep.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    private static Logger logger = LoggerFactory.getLogger(ZipUtil.class);

    public static boolean zip(String zipPath, String zipName, File[] files) {
        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            byte[] buffer = new byte[1024];
            File file = new File(zipPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String zipFullName = zipPath + File.separator + zipName + ".zip";
            File zipFile = new File(zipFullName);
            if (zipFile.exists()) {
                zipFile.delete();
            }
            zipFile.createNewFile();
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < files.length; i++) {
                fileInputStream = new FileInputStream(files[i]);
                zipOutputStream.putNextEntry(new ZipEntry(files[i].getName()));
                int length = 0;
                while ((length = fileInputStream.read(buffer)) != -1) {
                    zipOutputStream.write(buffer, 0, length);
                }
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
            zipOutputStream.close();
        } catch (IOException e) {
            logger.error("打包文件异常:{}", e);
            return false;
        } finally {
            try {
                if (zipOutputStream != null) {
                    zipOutputStream.closeEntry();
                }
            } catch (IOException e) {
            }
            try {
                if (zipOutputStream != null) {
                    zipOutputStream.close();
                }
            } catch (IOException e) {
            }
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
            }
        }
        return true;
    }
}
