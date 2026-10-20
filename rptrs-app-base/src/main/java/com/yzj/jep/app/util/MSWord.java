/*
package com.yzj.jep.app.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yzj.jep.app.ins.model.TraInvestment;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Bookmark;
import org.apache.poi.hwpf.usermodel.Bookmarks;
import org.apache.poi.hwpf.usermodel.Range;

*/
/**
 * 使用POI,进行Word相关的操作
 *
 *//*

public class MSWordTool {


    private static void replaceDoc(Map<String, String> dataMap, HWPFDocument doc) throws IOException {
        if (doc != null) {
            Bookmarks bookmarks = doc.getBookmarks();
            int count = bookmarks.getBookmarksCount();
            for (int i = 0; i < count; i++) {
                Bookmark bookmark = null;
                try {
                    bookmark = bookmarks.getBookmark(i);
                } catch (Exception e) {
                    System.err.println(e.getCause());
                    continue;
                }
                if (bookmark.getName() != null && bookmark.getName().startsWith("PO_")) {
                    String bookmarkName = bookmark.getName().trim().toUpperCase();
                    String data = dataMap.get(bookmarkName) != null?dataMap.get(bookmarkName).trim():"";
                    Range range = new Range(bookmark.getStart(), bookmark.getEnd(), doc);
                    range.insertBefore(data.replaceAll("\n", "\r"));//word的\n换行不起作用，需要转为\r
                    System.out.println(bookmark.getStart()+"："+bookmark.getEnd());
                }
            }
        }
    }
    public static String insertWord(Map<String, String> dataMap){
        try {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            //你的模板路径
            String template = "C:\\apache-tomcat-8.0.33\\webapps\\Resume\\resumeimgs\\pdf\\shikai-model1.docx";
            File source = new File(template);
            //这里是自己的业务，我将部分数据作为了新word的名字
            String fileName = dataMap.get("PO_CANDIDATENAME")+"-"+dataMap.get("PO_ADDRESS")+sdf.format(d);

            //做一个新的word，将模板内容copy过来，
            String templateBak = "C:\\apache-tomcat-8.0.33\\webapps\\Resume\\resumeimgs\\pdf\\"+fileName+".docx";
            File source1 = new File(templateBak);

            //copy模板内容到新的word中
            copyFileUsingJava7Files(source, source1);


            File outfile = new File(templateBak);
            HWPFDocument doc = null;
            InputStream is = null;
            is = new FileInputStream(outfile);
            doc = new HWPFDocument(is);
            replaceDoc(dataMap, doc);
            OutputStream os = new FileOutputStream(templateBak);
            // 把doc输出到输出流中
            doc.write(os);
            closeStream(os);
            closeStream(is);
            WordToPdfUtil.officeToPDF(templateBak, templateBak.replaceAll(".docx", ".pdf"));
            return null;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) throws IOException {
        Map<String, String> dataMap = new HashMap<>();
        TraInvestment ti = new TraInvestment();
        ArrayList<TraInvestment> list  = new ArrayList();

        final String relName = ti.getRelName();
        dataMap.put("guanlianfang",list.set(relName) );
        dataMap.put("jinrongchanpingname", "金融产品名称");
        dataMap.put("guanlianfangjiancheng", "关联方简称" +
                "");
        dataMap.put("PO_EDUCATIONEXPERIENCE", "2");
        dataMap.put("PO_POSTION", "java");
        dataMap.put("PO_POSTIONTITLE", "java工程师");
        dataMap.put("PO_PROJECTEXPERIENCE", "3                         \n asass");
        dataMap.put("PO_REMARK", "啊33");
        dataMap.put("PO_SEX", "男");
        dataMap.put("PO_SPECIALTYDATE", "33啊");
        dataMap.put("PO_WORKEXPERIENCE", "33");
        insertWord(dataMap);
    }

    */
/**
     * 关闭输入流
     *
     * @param is
     *//*

    private static void closeStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    */
/**
     * 关闭输出流
     *
     * @param os
     *//*

    private static void closeStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void copyFileUsingJava7Files(File source, File dest)
            throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

}

*/
