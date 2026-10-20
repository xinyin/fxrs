/*
 * Powered By xTOOLs
 * Dev by BgRisk@hiSoft.com
 * Copyright © 2011 - 2012 hiSoft.com. All rights reserved.
 */

package com.yzj.jep.app.sys.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.CommonException;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.io.imp.Template;
import com.yzj.jep.core.io.imp.TemplateColumn;
import com.yzj.jep.core.io.imp.TemplateEngine;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.PKeyService;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sys/import")
public class ImportController extends CrudController {
    private static Logger logger = LoggerFactory.getLogger(ImportController.class);


    @Autowired(required = false)
    private TemplateEngine templateEngine;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private PKeyService pKeyService;


    /**
     * 获取列表页面
     *
     * @return 返回列表页面
     */
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String list() {
        return "sys/import";
    }

    /**
     * 获取参数 参数包括: 1. 模板对应的列定义 2.上传文件的大小限制
     *
     * @param code 模板的代码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getParam", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getParam(@RequestParam(value = "code", required = true) String code) throws Exception {
        Map<String, Object> tips = new HashMap<String, Object>();

        try {

            Template template = templateEngine.getTemplate(code);
            if (template != null) {
                List<TemplateColumn> columns = template.getColumns();

                tips.put("success", true);
                tips.put("columns", columns);
                tips.put("title", template.getName());
                tips.put("uploadSizeLimit", getUploadSizeLimit());
            } else {
                tips.put("success", false);
                tips.put("msg", "模板不存在");
            }
        } catch (Exception e) {
            logger.error("获取参数失败：" + e.getMessage());
            tips.put("success", false);
            tips.put("msg", "获取数据失败。");
        }

        return tips;
    }

    /**
     * 下载导入模板
     *
     * @param page
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam(value = "code", required = true) String code,
                         @RequestParam(value = "version", required = false) String version, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        /*获得模板*/
        Template template = templateEngine.getTemplate(code);
        /*生成 excel 文件*/

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(template.getName() + "下载模板");
        XSSFRow row = sheet.createRow(0);

        List<TemplateColumn> columns = template.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(columns.get(i).getLabel());
            sheet.autoSizeColumn(i);
            int width = sheet.getColumnWidth(i);
            if (width <= 2500) {
                sheet.setColumnWidth(i, 2500);
            }
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=import_template.xlsx");
        OutputStream output = response.getOutputStream();
        workbook.write(output);
    }

    /**
     * 返回上传文件的保存目录
     *
     * @return
     * @throws Exception
     */
    private String getBasePath() throws Exception {
        String uploadPath = getUploadPath() + File.separator + "import";
        File basedir = new File(uploadPath);
        if (!basedir.exists()) {
            basedir.mkdirs();
        }
        return uploadPath;
    }

    /**
     * 返回指定ID的上传文件
     *
     * @param uuid
     * @return
     * @throws Exception
     */
    private File getUploadFile(String uuid) throws Exception {
        return new File(getBasePath() + File.separator + uuid + ".tmp");
    }

    /**
     * 获取指定文件的预读取内容
     *
     * @param uuid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFilePath", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getFilePath(@RequestParam(value = "uuid", required = true) String uuid) throws Exception {
        Map<String, Object> tips = new HashMap<String, Object>();
        String path = getBasePath() + File.separator + uuid + ".tmp";
        tips.put("success", true);
        tips.put("path", URLEncoder.encode(path, "UTF-8"));
        return tips;
    }

    /**
     * 获取指定文件的预读取内容
     *
     * @param uuid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFileData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getFileData(@RequestParam(value = "code", required = true) String code,
                                           @RequestParam(value = "uuid", required = true) String uuid) throws Exception {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            Template template = templateEngine.getTemplate(code);
            //检测模板是否存在
            if (template == null) {
                tips.put("success", false);
                tips.put("msg", "参数错误。");
                return tips;
            }

            //检测文件是否存在
            File file = getUploadFile(uuid);
            if (!file.exists()) {
                tips.put("success", false);
                tips.put("msg", "参数错误。");
                return tips;
            }

            //解析 excel 文件
            try {
                int format = getExcelFormat(file);
                if (format == EXCEL_FORMAT_UNKNOWN) {
                    tips.put("success", false);
                    tips.put("msg", "文件类型未知");
                    return tips;
                }

                /*总行数*/
                int totalRowCount = 0;
                /*预读取行数*/
                int rowCount = 0;
                /*返回的数据*/
                List<Map<String, String>> content = new ArrayList<Map<String, String>>();
                /*匹配标题和索引*/
                Map<Integer, String> indexKeyMap = new HashMap<Integer, String>();

                /*
                 * Excel 2003 格式
                 */
                if (format == EXCEL_FORMAT_2003) {
                    Workbook workbook = createworkbook(new FileInputStream(file));
                    /*只读取第一张表格*/
                    Sheet sheet = workbook.getSheetAt(0);
                    if (sheet == null) {
                        tips.put("success", false);
                        tips.put("msg", "找不到表格数据");
                        return tips;
                    }

                    totalRowCount = sheet.getLastRowNum() + 1;
                    rowCount = Math.min(PRE_READ_ROWS, sheet.getLastRowNum() + 1);
                    if (rowCount <= 1) {
                        tips.put("success", false);
                        tips.put("msg", "行数小于最小要求行数");
                        return tips;
                    }

                    /*从第一列中获取标题，并根据标题来匹配数据*/
                    Row firstRow = sheet.getRow(0);
                    int lastCellNum = firstRow.getLastCellNum();


                    for (int i = 0; i <= lastCellNum; i++) {
                        Cell cell = firstRow.getCell(i);
                        if (cell != null) {
                            try {
                                String value = cell.getStringCellValue();
                                for (int j = 0; j < template.getColumns().size(); j++) {
                                    String name = template.getColumns().get(j).getLabel();
                                    if (value.equals(name)) {
                                        indexKeyMap.put(i, template.getColumn(j).getName());
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                            }
                        }
                    }

                    if (indexKeyMap.size() < template.getColumns().size()) {
                        tips.put("success", false);
                        tips.put("msg", "列数不足。");
                        return tips;
                    }

                    /*从第二行开始解析导入正文*/
                    for (int i = 1; i < rowCount; i++) {
                        Row row = sheet.getRow(i);
                        if (row == null) {
                            continue;
                        }

                        /*当前行的数据*/
                        Map<String, String> data = new HashMap<String, String>();
                        for (int j = 0; j < lastCellNum; j++) {
                            Cell cell = row.getCell(j);
                            if (cell == null) {
                                continue;
                            }
                            if (!indexKeyMap.containsKey(j)) {
                                continue;
                            }

                            String key = indexKeyMap.get(j);
                            String value = null;

                            switch (cell.getCellType()) {
                                case XSSFCell.CELL_TYPE_BLANK:
                                    value = "";
                                    break;
                                case XSSFCell.CELL_TYPE_BOOLEAN:
                                    value = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case XSSFCell.CELL_TYPE_ERROR:
                                    value = "!ERROR";
                                    break;
                                case XSSFCell.CELL_TYPE_FORMULA:
                                    value = String.valueOf(formatNumericValue(cell.getNumericCellValue()));
                                    break;
                                case XSSFCell.CELL_TYPE_NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        value = DateUtils.formatDate(cell.getDateCellValue());
                                    } else {
                                        value = formatNumericValue(cell.getNumericCellValue());
                                    }
                                    break;
                                case XSSFCell.CELL_TYPE_STRING:
                                    value = cell.getStringCellValue();
                                    break;
                                default:
                                    value = cell.getStringCellValue();
                                    break;
                            }
                            data.put(key, value);
                        }
                        content.add(data);
                    }
                }

                /*
                 * Excel 2007 格式
                 */
                if (format == EXCEL_FORMAT_2007) {
                    XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));

                    /*只读取第一张表格*/
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    if (sheet == null) {
                        tips.put("success", false);
                        tips.put("msg", "找不到表格数据");
                        return tips;
                    }

                    totalRowCount = sheet.getLastRowNum() + 1;
                    rowCount = Math.min(PRE_READ_ROWS, sheet.getLastRowNum() + 1);

                    if (rowCount <= 1) {
                        tips.put("success", false);
                        tips.put("msg", "行数小于最小要求行数");
                        return tips;
                    }

                    /*从第一列中获取标题，并根据标题来匹配数据*/
                    XSSFRow firstRow = sheet.getRow(0);
                    int lastCellNum = firstRow.getLastCellNum();


                    for (int i = 0; i <= lastCellNum; i++) {
                        XSSFCell cell = firstRow.getCell(i);
                        if (cell != null) {
                            try {
                                String value = cell.getStringCellValue();
                                for (int j = 0; j < template.getColumns().size(); j++) {
                                    String name = template.getColumns().get(j).getLabel();
                                    if (value.equals(name)) {
                                        indexKeyMap.put(i, template.getColumn(j).getName());
                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                            }
                        }
                    }


                    if (indexKeyMap.size() < template.getColumns().size()) {
                        tips.put("success", false);
                        tips.put("msg", "列数不足。");
                        return tips;
                    }

                    for (int i = 1; i < rowCount; i++) {
                        XSSFRow row = sheet.getRow(i);
                        if (row == null) {
                            continue;
                        }

                        /*当前行的数据*/
                        Map<String, String> data = new HashMap<String, String>();
                        for (int j = 0; j < lastCellNum; j++) {
                            XSSFCell cell = row.getCell(j);
                            if (cell == null) {
                                continue;
                            }
                            if (!indexKeyMap.containsKey(j)) {
                                continue;
                            }

                            String key = indexKeyMap.get(j);
                            String value = null;

                            switch (cell.getCellType()) {
                                case XSSFCell.CELL_TYPE_BLANK:
                                    value = "";
                                    break;
                                case XSSFCell.CELL_TYPE_BOOLEAN:
                                    value = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case XSSFCell.CELL_TYPE_ERROR:
                                    value = "!ERROR";
                                    break;
                                case XSSFCell.CELL_TYPE_FORMULA:
                                    value = String.valueOf(formatNumericValue(cell.getNumericCellValue()));
                                    break;
                                case XSSFCell.CELL_TYPE_NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        value = DateUtils.formatDate(cell.getDateCellValue());
                                    } else {
                                        value = String.valueOf(formatNumericValue(cell.getNumericCellValue()));
                                    }
                                    break;
                                case XSSFCell.CELL_TYPE_STRING:
                                    value = String.valueOf(cell.getStringCellValue());
                                    break;
                                default:
                                    value = String.valueOf(cell.getStringCellValue());
                                    break;
                            }
                            data.put(key, value);
                        }
                        content.add(data);
                    }
                }

                /*生成列标题*/
                List<NameValue> columnNames = new ArrayList<NameValue>();
                if (template.getColumns().size() > 0) {
                    for (int i = 0; i < template.getColumns().size(); i++) {
                        String name = template.getColumn(i).getLabel();
                        String value = template.getColumn(i).getName();
                        NameValue nv = new NameValue(name, value);
                        columnNames.add(nv);
                    }
                }

                tips.put("success", true);
                tips.put("rowCount", rowCount);
                tips.put("totalRowCount", totalRowCount);
                tips.put("columnCount", template.getColumns().size());
                if (rowCount > 0) {
                    tips.put("columnNames", columnNames);
                    tips.put("content", content);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                tips.put("success", false);
                tips.put("msg", "文件解析失败。");
                return tips;
            }

        } catch (Exception e) {
            logger.error("获取数据失败：" + e.getMessage());
            tips.put("success", false);
            tips.put("msg", "获取数据失败。");
        }

        return tips;
    }

    @SuppressWarnings("unused")
    private class NameValue {
        private String name;
        private String value;

        public NameValue(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 静态方法  解决创建Workbook 创建产生的问题
     *
     * @param inp
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    private static Workbook createworkbook(InputStream inp) throws IOException, InvalidFormatException {

        if (!inp.markSupported()) {
            inp = new PushbackInputStream(inp, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(inp)) {
            return new HSSFWorkbook(inp);
        }
        if (POIXMLDocument.hasOOXMLHeader(inp)) {
            return new XSSFWorkbook(OPCPackage.open(inp));
        }
        return null;
    }

    /**
     * 处理上传文件
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public void handleUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            commonsMultipartResolver.setDefaultEncoding("utf-8");
            response.setContentType("text/html;charset=UTF-8");
            //判断 request 是否有文件上传,即多部分请求 
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
                Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

                /*只处理一个文件*/
                for (MultipartFile file : fileMap.values()) {
                    try {
                        String uuid = getUUID();
                        File uploadFile = getUploadFile(uuid);
                        FileCopyUtils.copy(file.getBytes(), uploadFile);

                        /*上传成功, 返回该文件对应的ID*/
                        response.getWriter().write(uuid);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        response.getWriter().write('0');
                    }
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("上传失败：" + e.getMessage());
            response.getWriter().write('0');
        }
    }

    /**
     * 获得唯一的上传key
     *
     * @return
     * @throws ServiceException
     */
    @RequestMapping(value = "/getUUID", method = RequestMethod.POST)
    @ResponseBody
    public String getUUID() throws ServiceException {
        try {
            return pKeyService.getUUIDKey();
        } catch (CommonException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 获得上传文件的大小限制,单位: byte
     *
     * @return
     */
    public double getUploadSizeLimit() throws ServiceException {
        try {
            String value = appConfigService.getConfigValue("UPLOAD_SIZE_LIMIT");
            if (value != null) {
                return Double.parseDouble(value.replace("M", "")) * 1024 * 1024;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 10L * 1024 * 1024;
    }

    /**
     * 获取上传磁盘路径
     *
     * @return
     * @throws ServiceException
     */
    public String getUploadPath() throws ServiceException {
        return appConfigService.getConfigValue("UPLOADPATH");
    }

}