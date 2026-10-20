
package com.yzj.jep.app.util;

import com.aspose.cells.License;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class ExcelToPdf {

    /*    public static void main(String[] args) throws Exception {
            excelToPdf("C://Users//admin//Desktop//pdf//12.xlsx", "C://Users//admin//Desktop//pdf//12.pdf");
        }*/
    private static Logger logger = LoggerFactory.getLogger(ExcelToPdf.class);

    public static void excelToPdf(String inFilePath, String outFilePath) throws Exception {
        logger.info("xls转成pdf开始。。。。");
        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            logger.info("获取pdf输出路径" + outFilePath);
            File pdfFile = new File(outFilePath);// 输出路径
            logger.info("获取xls输入路径" + inFilePath);
            Workbook wb = new Workbook(inFilePath);// 原始excel路径
            logger.info("获取Excel成功");
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            logger.info("把内容放在一张PDF 页面上");
            pdfSaveOptions.setOnePagePerSheet(true);//把内容放在一张PDF 页面上；
            FileOutputStream fileOS = new FileOutputStream(pdfFile);
            logger.info("只放一张纸； 我的文件转为横向了");
            wb.save(fileOS, pdfSaveOptions);//  只放一张纸； 我的文件专为横向了
            //wb.save(fileOS, SaveFormat.PDF);  //不做限制 ，   导出的为纵向
            fileOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = ExcelToPdf.class.getClassLoader().getResourceAsStream("license.xml"); //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

