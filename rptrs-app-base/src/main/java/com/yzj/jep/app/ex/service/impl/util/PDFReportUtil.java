package com.yzj.jep.app.ex.service.impl.util;

//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.AcroFields;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.core.holder.SpringContextHolder;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PDFReportUtil {
    private static Logger logger = LoggerFactory.getLogger(PDFReportUtil.class);

    /**
     * 根据模板生成pdf
     *
     * @param pdfName 文件名
     * @param data    Map(String,Object)
     * @return 文件保存全路径文件
     */
    public static Map<String,Object> createPDF(String pdfName, Map<String, Object> data) {
        Configs configs = SpringContextHolder.getBean(Configs.class);

        Map<String,Object> tips = new HashMap<>();
//        PdfReader reader = null;
//        AcroFields s = null;
//        PdfStamper ps = null;
//        ByteArrayOutputStream bos = null;
//        //String realPath = "C:/app/share/template";
//        String realPath = configs.getSave();
//        String dateFolder = DateFormatUtils.format(new Date(), "yyyyMMdd");
//        String folderPath = realPath + File.separator + dateFolder;
//        //创建上传文件目录
//        File folder = new File(folderPath);
//        if (!folder.exists()) {
//            folder.mkdirs();
//        }
//        //设置文件名
//        //String newFileName = fileNameConversion(pdfName);
//        String fileName = pdfName + "_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + ".pdf";
//        String savePath = folderPath + File.separator + fileName;
//        try {
//            //String file ="C:/home/rptrs/关联交易办公室审计记录表" + ".pdf";
//            String file = configs.getTemplate() + ".pdf";
//            //设置字体 String font ="C:/app/share/template" + "/msyh.ttf";
//            String font = configs.getFont() + "/msyh.ttf";
//            reader = new PdfReader(file);
//            bos = new ByteArrayOutputStream();
//            ps = new PdfStamper(reader, bos);
//            s = ps.getAcroFields();
//            //使用中文字体 使用 AcroFields填充值的不需要在程序中设置字体，在模板文件中设置字体为中文字体 Adobe 宋体 std L
//            BaseFont bfChinese = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            //设置编码格式
//            s.addSubstitutionFont(bfChinese);
//            // 遍历data 给pdf表单表格赋值
//            for (String key : data.keySet()) {
//                if (data.get(key) != null) {
//                    s.setField(key, data.get(key).toString());
//                }
//            }
//            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
//            ps.setFormFlattening(true);
//            ps.close();
//            FileOutputStream fos = new FileOutputStream(savePath);
//            fos.write(bos.toByteArray());
//            fos.flush();
//            fos.close();
//            logger.info("savePath=" + savePath);
//            tips.put("savePath",savePath);
//            tips.put("fileName",fileName);
//            tips.put("re","成功");
//            return tips;
//        } catch (IOException | DocumentException e) {
//            logger.error("读取文件异常");
//            e.printStackTrace();
//            tips.put("re","失败");
//            return tips;
//        } finally {
//            try {
//                bos.close();
//                reader.close();
//            } catch (IOException e) {
//                logger.error("关闭流异常");
//                e.printStackTrace();
//            }
//        }
        return tips;
    }

    /**
     * 其他证件号码脱敏（除第一位和最后一位全部替换为*）
     * @param code
     * @return
     */
    public static String otherCodeMasking(String code){
        String temp = "";
        if (code.length() > 2) {
            int nameLength = code.length() - 2;
            while (nameLength > 0) {
                temp += "*";
                nameLength--;
            }
            code = code.substring(0, 1) + temp + code.substring(code.length() - 1, code.length());
        }
        return code;
    }

    /**
     * 转换文件名称
     *
     * @param fileName
     * @return
     */
    public static String fileNameConversion(String fileName) {
        String newFileName = "";
        //财险
        if ("Cx_CenterApprove".equals(fileName)) {
            newFileName = "CX_一般关联交易备批单（针对需集中审议的关联交易）";
        } else if ("Cx_Exemption".equals(fileName)) {
            newFileName = "CX_关联交易备案单（免于审议））";
        } else if ("Cx_GeneralSingle".equals(fileName)) {
            newFileName = "CX_一般关联交易逐笔审批单（针对需逐笔审议的一般关联交易）";
        } else if ("Cx_MajorOrUnit".equals(fileName)) {
            newFileName = "CX_重大关联交易或统一交易协议审查单";
            //集团
        } else if ("Jt_CenterApprove".equals(fileName)) {
            newFileName = "JT_一般关联交易备批单（针对需集中审议的关联交易）";
        } else if ("Jt_Exemption".equals(fileName)) {
            newFileName = "JT_关联交易备案单（免于审议））";
        } else if ("Jt_GeneralSingle".equals(fileName)) {
            newFileName = "JT_一般关联交易逐笔审批单（针对需逐笔审议的一般关联交易）";
        } else if ("Jt_MajorOrUnit".equals(fileName)) {
            newFileName = "JT_重大关联交易或统一交易协议审查单";
            //寿险
        } else if ("Sx_CenterApprove".equals(fileName)) {
            newFileName = "SX_一般关联交易备批单（针对需集中审议的关联交易）";
        } else if ("Sx_Exemption".equals(fileName)) {
            newFileName = "SX_关联交易备案单（免于审议））";
        } else if ("Sx_GeneralSingle".equals(fileName)) {
            newFileName = "SX_一般关联交易逐笔审批单（针对需逐笔审议的一般关联交易）";
        } else if ("Sx_MajorOrUnit".equals(fileName)) {
            newFileName = "SX_重大关联交易或统一交易协议审查单";
            //养老
        } else if ("Yl_CenterApprove".equals(fileName)) {
            newFileName = "YL_一般关联交易备批单（针对需集中审议的关联交易）";
        } else if ("Yl_Exemption".equals(fileName)) {
            newFileName = "YL_关联交易备案单（免于审议））";
        } else if ("Yl_GeneralSingle".equals(fileName)) {
            newFileName = "YL_一般关联交易逐笔审批单（针对需逐笔审议的一般关联交易）";
        } else if ("Yl_MajorOrUnit".equals(fileName)) {
            newFileName = "YL_重大关联交易或统一交易协议审查单";
            //资管
        } else if ("Zg_CenterApprove".equals(fileName)) {
            newFileName = "ZG_一般关联交易备批单（针对需集中审议的关联交易）";
        } else if ("Zg_Exemption".equals(fileName)) {
            newFileName = "ZG_关联交易备案单（免于审议））";
        } else if ("Zg_GeneralSingle".equals(fileName)) {
            newFileName = "ZG_一般关联交易逐笔审批单（针对需逐笔审议的一般关联交易）";
        } else if ("Zg_MajorOrUnit".equals(fileName)) {
            newFileName = "ZG_重大关联交易或统一交易协议审查单";
        }
        return newFileName;
    }

//        public static void main(String[] args) {
//        Map<String, Object> data = new HashMap();
//        data.put("starterName", "董江坤发发");
//        data.put("tradeSubject", "123");
//        data.put("relNumber", "343354354434");
//        data.put("agreementAmount", "99.000000");
//        data.put("tranType", "ORG");
//        data.put("businessType", "4");
//        data.put("pricingPolicy", "2");
//        data.put("fairnessStatement", "1");
//
//        data.put("complianceStatement", "12");
//        String necessityStatement = "版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111版本11111";
// //       String temp = repla(necessityStatement, 20);
////        System.out.println(temp);
//        data.put("necessityStatement", necessityStatement);
//        data.put("mechanismFinanceInfluences", "33");
//
//        data.put("consumerInterestInfluences", "666");
//        data.put("isTranslations", "是，该交易已完成备案");
//        String fileName = "Yl_MajorOrUnit";
////        PDFReportUtil util = new PDFReportUtil();
////        String path = util.createPDF(fileName,data);
//        Configs configs = new Configs();
//        configs.setFilePath("D:\\upload");
//        configs.setApprovaltemplate("D:\\approvaltemplate");
//        configs.setFontPath("D:\\approvaltemplate");
//        String path = PDFReportUtil.createPDF(fileName, data, configs);
//    }
//    public static void main(String[] args) {
//        System.out.println(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));;
//    }
}
